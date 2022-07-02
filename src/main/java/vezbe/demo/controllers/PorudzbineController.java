package vezbe.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.KomentarDto;
import vezbe.demo.dto.KorpaDto;
import vezbe.demo.model.*;
import vezbe.demo.service.ArtikalService;
import vezbe.demo.service.KorisnikService;
import vezbe.demo.service.PorudzbinaService;
import vezbe.demo.service.SessionService;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequestMapping("api")
@RestController
public class PorudzbineController {

    @Autowired
    SessionService sessionService;

    @Autowired
    KorisnikService korisnikService;

    @Autowired
    PorudzbinaService porudzbinaService;

    @Autowired
    KomentarDto komentarDto;

    @GetMapping("porudzbine")
    public ResponseEntity pregledPorudzbina(HttpSession session) {
        if (sessionService.validateUloga(session,"Kupac") && sessionService.validateSession(session)) {
            Korisnik korisnik = korisnikService.findByUsername((String) session.getAttribute("korisnicko_ime"));

            Set<Porudzbina> listaPorudzbina = ((Kupac)korisnik).getIstorija_porudzbina();

            return new ResponseEntity(listaPorudzbina, HttpStatus.OK);
        }
        else if (sessionService.validateUloga(session,"Dostavljac") && sessionService.validateSession(session)) {
            Korisnik korisnik = korisnikService.findByUsername((String) session.getAttribute("korisnicko_ime"));

            Set<Porudzbina> listaPorudzbina = ((Dostavljac)korisnik).getPorudzbine();

            for (Porudzbina porudzbina : listaPorudzbina) {
                if (porudzbina.getStatus() != Porudzbina.Status.ceka_dostavljaca) {
                    listaPorudzbina.remove(porudzbina);
                }
            }

            return new ResponseEntity(listaPorudzbina, HttpStatus.OK);
        }
        else if (sessionService.validateUloga(session,"Menadzer") && sessionService.validateSession(session)) {
            Korisnik korisnik = korisnikService.findByUsername((String) session.getAttribute("korisnicko_ime"));

            List<Porudzbina> listaPorudzbina = porudzbinaService.findAllByRestoran(((Menadzer)korisnik).getRestoran());

            return new ResponseEntity(listaPorudzbina, HttpStatus.OK);
        }
        else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);

    }

    @PostMapping("dodajUKorpu/{id1}/{id2}") // id1 - restoran, id2 artikal
    public ResponseEntity<String> dodajUKorpu(@PathVariable(name = "id1") Long id1, @PathVariable(name = "id2") Long id2, HttpSession session) {

        Korisnik ulogovani = (Korisnik) session.getAttribute("Korisnik");

        if (ulogovani == null)
            return new ResponseEntity<>("Morate da se ulogujete, da biste mogli da vrsite ovu akciju!", HttpStatus.BAD_REQUEST);
        if (ulogovani.getUloga() != Korisnik.Uloga.Kupac)
            return new ResponseEntity<>("Dodavanje artikla u korpu je dozvoljeno samo kupcima!", HttpStatus.BAD_REQUEST);

        Artikal artikal = porudzbinaService.findArtikalById(id2);

        if (artikal == null)
            return new ResponseEntity<>("Zeljeni artikal nije pronadjen!", HttpStatus.NOT_FOUND);
        if (!porudzbinaService.ArtikaluRestoranu(id1, id2))
            return new ResponseEntity<>("Zeljeni restoran nema dati artikal u svojoj ponudi!", HttpStatus.NOT_FOUND);

        Kupac ulogovaniKupac = (Kupac) ulogovani;
        Set<Porudzbina> porudzbine = ulogovaniKupac.getIstorija_porudzbina();
        Porudzbina porudzbina = new Porudzbina();
        porudzbina.setRestoran(porudzbinaService.findRestoranById(id1));
        porudzbina.setKupac(ulogovaniKupac);
        porudzbina.setStatus(Porudzbina.Status.u_korpi);
        porudzbinaService.save(porudzbina);

        for (Porudzbina p : porudzbine) {
            if (p.getStatus().equals(Porudzbina.Status.u_korpi)) {
                porudzbina = p;
                break;
            }
        }

        // novokreirana porudzbina - zna se samo da je "u korpi"
        if (porudzbina.getRestoran() == null) {
            porudzbina.setRestoran(porudzbinaService.findRestoranById(id1));
            porudzbinaService.save(porudzbina);
        }
        if (porudzbina.getKupac() == null) {
            porudzbina.setKupac(ulogovaniKupac);
            porudzbinaService.save(porudzbina);
        }

        // proveravam da li vec postoji taj artikal u porudzbini, ako da, povecavam kolicinu
        for (ArtikalUPorudzbini ap : porudzbina.getArtikliUPorudzbini()) {
            if (ap.getArtikal().getId().equals(id2)) {
                ap.setKolicina(ap.getKolicina() + 1);     // povecavam kolicinu
                porudzbinaService.saveArtikalUPorudzbini(ap);
                porudzbina.setCena(porudzbina.getCena() + artikal.getCena()); // povecavam cenu
                porudzbinaService.save(porudzbina);
                porudzbinaService.saveKupac(ulogovaniKupac);

                return ResponseEntity.ok("Artikal je uspesno dodat u korpu!");
            }
        }

        ArtikalUPorudzbini ap = new ArtikalUPorudzbini(artikal, 1);
        porudzbinaService.saveArtikalUPorudzbini(ap);
        porudzbina.getArtikliUPorudzbini().add(ap);
        porudzbina.setCena(porudzbina.getCena() + artikal.getCena());
        porudzbinaService.save(porudzbina);
        ulogovaniKupac.dodajPorudzbinu(porudzbina);
        porudzbinaService.saveKupac(ulogovaniKupac);

        return ResponseEntity.ok("Artikal je uspesno dodat u korpu!");
    }

    @DeleteMapping("izbaciIzKorpe/{id}")
    public ResponseEntity<String> izbaciIzKorpe(@PathVariable Long id ,HttpSession session) {

        Korisnik ulogovani = (Korisnik) session.getAttribute("Korisnik");

        if (ulogovani == null)
            return new ResponseEntity<>("Morate da se ulogujete, da biste mogli da vrsite ovu akciju!", HttpStatus.BAD_REQUEST);
        if (ulogovani.getUloga() != Korisnik.Uloga.Kupac)
            return new ResponseEntity<>("Brisanje artikla iz korpe je dozvoljeno samo kupcima!", HttpStatus.BAD_REQUEST);

        Artikal artikal = porudzbinaService.findArtikalById(id);

        if(artikal == null)
            return new ResponseEntity<>("Artikal sa unetim id-om ne postoji", HttpStatus.BAD_REQUEST);

        Kupac ulogovaniKupac = (Kupac) ulogovani;

        //provera da li artikal postoji u korpi
        if(!porudzbinaService.ArtikalUKorpi(ulogovaniKupac, id))
            return new ResponseEntity<>("Dati artikal ne postoji u korpi!", HttpStatus.BAD_REQUEST);

        porudzbinaService.izbaciIzKorpe(ulogovaniKupac, id);

        return ResponseEntity.ok("Uspesno ste izbacili artikal iz korpe");
    }

    @GetMapping("pregledKorpe")
    public ResponseEntity<KorpaDto> pregledKorpe(HttpSession session) {

        Korisnik ulogovani = (Korisnik) session.getAttribute("Korisnik");

        if (ulogovani == null)
            return new ResponseEntity("Morate da se ulogujete, da biste mogli da vrsite ovu akciju!", HttpStatus.BAD_REQUEST);
        if (ulogovani.getUloga() != Korisnik.Uloga.Kupac)
            return new ResponseEntity("Pregled sadrzaja korpe je dozvoljen samo kupcima!", HttpStatus.BAD_REQUEST);

        Kupac ulogovaniKupac = (Kupac) ulogovani;

        return ResponseEntity.ok(porudzbinaService.getKorpa(ulogovaniKupac));

    }

    @PutMapping("submitKorpe")
    public ResponseEntity<String> submitKorpe(HttpSession session) {

        Korisnik ulogovani = (Korisnik) session.getAttribute("Korisnik");

        if (ulogovani == null)
            return new ResponseEntity("Morate da se ulogujete, da biste mogli da vrsite ovu akciju!", HttpStatus.BAD_REQUEST);
        if (ulogovani.getUloga() != Korisnik.Uloga.Kupac)
            return new ResponseEntity("Submit korpe je dozvoljen samo kupcima!", HttpStatus.BAD_REQUEST);

        Kupac ulogovaniKupac = (Kupac) ulogovani;

        return ResponseEntity.ok(porudzbinaService.submitKorpe(ulogovaniKupac));
    }

    @PutMapping("porudzbinaUPripremi/{id}")
    public ResponseEntity<String> porudzbinaUPripremi(@PathVariable UUID id, HttpSession session) {

        Korisnik ulogovani = (Korisnik) session.getAttribute("Korisnik");

        if (ulogovani == null)
            return new ResponseEntity("Niste ulogovani!", HttpStatus.BAD_REQUEST);

        if (ulogovani.getUloga() != Korisnik.Uloga.Menadzer)
            return new ResponseEntity("Ova funkcionalnost je dozvoljena samo menadzerima!", HttpStatus.BAD_REQUEST);

        if(porudzbinaService.findById(id) == null)
            return new ResponseEntity("Porudzbina sa datim UUID-om ne postoji!", HttpStatus.BAD_REQUEST);

        Menadzer ulogovaniMenadzer = (Menadzer) ulogovani;

        if(!porudzbinaService.MenadzerOfPorudzbina(ulogovaniMenadzer,id)) {
            return new ResponseEntity("Upravljanje porudzbina je dozvoljeno samo za vase porudzbine!", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(porudzbinaService.UPripremi(id));
    }


    @PutMapping("porudzbinaCekaDostavljaca/{id}")
    public ResponseEntity<String> porudzbinaCekaDostavljaca(@PathVariable UUID id, HttpSession session) {

        Korisnik ulogovani = (Korisnik) session.getAttribute("Korisnik");

        if (ulogovani == null)
            return new ResponseEntity("Niste ulogovani!", HttpStatus.BAD_REQUEST);

        if (ulogovani.getUloga() != Korisnik.Uloga.Menadzer)
            return new ResponseEntity("Ova funkcionalnost je dozvoljena samo menadzerima!", HttpStatus.BAD_REQUEST);

        if(porudzbinaService.findById(id) == null)
            return new ResponseEntity("Porudzbina sa datim UUID-om ne postoji!", HttpStatus.BAD_REQUEST);

        Menadzer ulogovaniMenadzer = (Menadzer) ulogovani;

        if(!porudzbinaService.MenadzerOfPorudzbina(ulogovaniMenadzer,id)) {
            return new ResponseEntity("Upravljanje porudzbina je dozvoljeno samo za vase porudzbine!", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(porudzbinaService.CekaDostavljaca(id));
    }


    @PutMapping("porudzbinaUTransportu/{id}")
    public ResponseEntity<String> porudzbinaUTransportu(@PathVariable UUID id, HttpSession session) {

        Korisnik ulogovani = (Korisnik) session.getAttribute("Korisnik");

        if (ulogovani == null)
            return new ResponseEntity("Niste ulogovani!", HttpStatus.BAD_REQUEST);

        if (ulogovani.getUloga() != Korisnik.Uloga.Dostavljac)
            return new ResponseEntity("Ova funkcionalnost je dozvoljena samo dostavljacima!", HttpStatus.BAD_REQUEST);

        if(porudzbinaService.findById(id) == null)
            return new ResponseEntity("Porudzbina sa datim UUID-om ne postoji!", HttpStatus.BAD_REQUEST);

        Dostavljac ulogovaniDostavljac = (Dostavljac) ulogovani;

        if(!porudzbinaService.DostavljacOfPorudzbina(ulogovaniDostavljac,id)) {
            return new ResponseEntity("Upravljanje porudzbina je dozvoljeno samo za vase porudzbine!", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(porudzbinaService.UTransportu(id));
    }

    @PutMapping("porudzbinaDostavljena/{id}")
    public ResponseEntity<String> porudzbinaDostavljena(@PathVariable UUID id, HttpSession session) {

        Korisnik ulogovani = (Korisnik) session.getAttribute("Korisnik");

        if (ulogovani == null)
            return new ResponseEntity("Niste ulogovani!", HttpStatus.BAD_REQUEST);

        if (ulogovani.getUloga() != Korisnik.Uloga.Dostavljac)
            return new ResponseEntity("Ova funkcionalnost je dozvoljena samo dostavljacima!", HttpStatus.BAD_REQUEST);

        if(porudzbinaService.findById(id) == null)
            return new ResponseEntity("Porudzbina sa datim UUID-om ne postoji!", HttpStatus.BAD_REQUEST);

        Dostavljac ulogovaniDostavljac = (Dostavljac) ulogovani;

        if(!porudzbinaService.DostavljacOfPorudzbina(ulogovaniDostavljac,id)) {
            return new ResponseEntity("Upravljanje porudzbina je dozvoljeno samo za vase porudzbine!", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(porudzbinaService.Dostavljena(id));
    }

    @PutMapping("dodajKomentar/{id}")
    public ResponseEntity<String> dodajKomentar(@PathVariable UUID id, HttpSession session){

        Korisnik ulogovani = (Korisnik) session.getAttribute("Korisnik");

        if (ulogovani == null)
            return new ResponseEntity("Niste ulogovani!", HttpStatus.BAD_REQUEST);

        if (ulogovani.getUloga() != Korisnik.Uloga.Kupac)
            return new ResponseEntity("Ova funkcionalnost je dozvoljena samo kupcima!", HttpStatus.BAD_REQUEST);

        if(porudzbinaService.findById(id) == null)
            return new ResponseEntity("Porudzbina sa datim UUID-om ne postoji!", HttpStatus.BAD_REQUEST);

        if(porudzbinaService.findById(id).getStatus() == Porudzbina.Status.dostavljena)
            return new ResponseEntity("Ostavljanje komentara je dozvoljeno samo ako je porudzbina dostavljena!", HttpStatus.BAD_REQUEST);

        Porudzbina p = porudzbinaService.findById(id);
        Restoran restoran = p.getRestoran();

        Komentar komentar = new Komentar();

        if(komentarDto.getRestoran() != restoran)
            return new ResponseEntity("Nije moguce uneti komentar za restoran koji nije vezan za datu porudzbinu!", HttpStatus.BAD_REQUEST);

        if(komentarDto.getTekst().isEmpty())
            return new ResponseEntity("Niste uneli tekst komentara!", HttpStatus.BAD_REQUEST);
        else
            komentar.setTekst_komentara(komentarDto.getTekst());

        if(komentarDto.getOcena() == null)
            return new ResponseEntity("Niste uneli ocenu!", HttpStatus.BAD_REQUEST);
        else
            komentar.setOcena(komentarDto.getOcena());

        restoran.dodajKomentar(komentar);

    }

}
