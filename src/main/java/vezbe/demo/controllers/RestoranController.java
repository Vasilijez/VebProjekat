package vezbe.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.ArtikalDto;
import vezbe.demo.dto.RestoranDto;
import vezbe.demo.dto.RestoranNazivDto;
import vezbe.demo.dto.RestoranPojedinacniDto;
import vezbe.demo.model.*;
import vezbe.demo.service.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RequestMapping("api")
@RestController
public class RestoranController {

    @Autowired
    RestoranService restoranService;

    @Autowired
    KomentarService komentarService;

    @Autowired
    SessionService sessionService;

    @Autowired
    ArtikalService artikalService;

    @Autowired
    KorisnikService korisnikService;

    @Autowired
    MenadzerService menadzerService;

    @Autowired
    PorudzbinaService porudzbinaService;

    @Autowired
    KupacService kupacService;

    @Autowired
    DostavljacService dostavljacService;

    @GetMapping(
    value = "/restorani",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestoranDto> prikazRestorana() {
        List<Restoran> listaRestoran = restoranService.findAll();
        List<RestoranDto> restoranDtoList = new ArrayList<>();

        for (Restoran restoran : listaRestoran) {
            RestoranDto restoranDto = new RestoranDto(restoran.getNaziv(), restoran.getTipRestorana(), restoran.getLokacija());
            restoranDtoList.add(restoranDto);
        }

        return restoranDtoList;
    }

    @GetMapping(
            value = "/restorani-bez-menadzera",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity prikazRestoranaBezMenadzera(HttpSession session) {
        if (sessionService.validateUloga(session,"Admin") && sessionService.validateSession(session)) {

            List<Restoran> listaRestoran = restoranService.findAll();
            List<RestoranDto> restoranDtoList = new ArrayList<>();

            List<Menadzer> menadzerList = menadzerService.findAll();
            List<Restoran> finalnaLista = new ArrayList<>();
            for (Restoran restoran : listaRestoran) {
                finalnaLista.add(restoran);
            }

            for (Restoran restoran:
                    listaRestoran) {
                for (Menadzer menadzer : menadzerList) {
                    if (menadzer.getRestoran() == restoran) {
                        finalnaLista.remove(restoran);
                        break;
                    }
                }
            }

            for (Restoran restoran : finalnaLista) {
                RestoranDto restoranDto = new RestoranDto(restoran.getNaziv(), restoran.getTipRestorana(), restoran.getLokacija());
                restoranDtoList.add(restoranDto);
            }

            return new ResponseEntity(restoranDtoList, HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);
    }


    @GetMapping("search/naziv/{naziv}") // ok
    public List<RestoranDto> search(@PathVariable("naziv") String naziv) {
        List<Restoran> listaRestoran = restoranService.findByNazivLikeCaseInsensitive(naziv);
        List<RestoranDto> restoranDtoList = new ArrayList<>();

        for (Restoran restoran : listaRestoran) {
            RestoranDto restoranDto = new RestoranDto(restoran.getNaziv(), restoran.getTipRestorana(), restoran.getLokacija());
            restoranDtoList.add(restoranDto);
        }

        return restoranDtoList;
    }

    @GetMapping("search/tip/{tip}") // ok
    public List<RestoranDto> searchByTip(@PathVariable("tip") String tip) {
        List<Restoran> listaRestoran = restoranService.findByTipLikeCaseInsensitive(tip);
        List<RestoranDto> restoranDtoList = new ArrayList<>();

        for (Restoran restoran : listaRestoran) {
            RestoranDto restoranDto = new RestoranDto(restoran.getNaziv(), restoran.getTipRestorana(), restoran.getLokacija());
            restoranDtoList.add(restoranDto);
        }

        return restoranDtoList;
    }

    @GetMapping("search/adresa/{adresa}") // ok
    public List<RestoranDto> searchByAdresa(@PathVariable("adresa") String adresa) {
        List<Restoran> listaRestoran = restoranService.searchByLokacijaAdresa(adresa);
        List<RestoranDto> restoranDtoList = new ArrayList<>();

        for (Restoran restoran : listaRestoran) {
            RestoranDto restoranDto = new RestoranDto(restoran.getNaziv(), restoran.getTipRestorana(), restoran.getLokacija());
            restoranDtoList.add(restoranDto);
        }

        return restoranDtoList;
    }

    @PostMapping("search")  // ok
    public List<RestoranDto> searchByNazivTipLokacija(@RequestBody RestoranDto restoranDto) {
        List<Restoran> listaRestorana;
        List<RestoranDto> restoranDtoList = new ArrayList<>();

        if (!restoranDto.getNaziv().isEmpty() && restoranDto.getNaziv() != null) {        // Prvo pretrazujemo pomocu naslova
            listaRestorana = restoranService.findByNazivLikeCaseInsensitive(restoranDto.getNaziv());
            if (!listaRestorana.isEmpty()) {
                for (Restoran restoran : listaRestorana) {
                    RestoranDto restoranDtoIzlaz = new RestoranDto(restoran.getNaziv(), restoran.getTipRestorana(), restoran.getLokacija());
                    restoranDtoList.add(restoranDtoIzlaz);
                }
                return restoranDtoList;
            }
        }

        if (restoranDto.getLokacija() != null &&
                ( ((restoranDto.getLokacija().getAdresa() != null) && (!restoranDto.getLokacija().getAdresa().isEmpty())) ||
                        (restoranDto.getLokacija().getSirina() != 0)  ||
                        (restoranDto.getLokacija().getSirina() != 0)
                )
        ) {
            listaRestorana = restoranService.searchByLokacija(restoranDto.getLokacija());
            if (!listaRestorana.isEmpty()) {
                for (Restoran restoran : listaRestorana) {
                    RestoranDto restoranDtoIzlaz = new RestoranDto(restoran.getNaziv(), restoran.getTipRestorana(), restoran.getLokacija());
                    restoranDtoList.add(restoranDtoIzlaz);
                }
                return restoranDtoList;
            }
        }

        if (!restoranDto.getTipRestorana().isEmpty() || restoranDto.getTipRestorana() != null) {          // Na kraju poslednja sansa preko tipa restorana
            listaRestorana = restoranService.findByTipLikeCaseInsensitive(restoranDto.getTipRestorana());
            if (!listaRestorana.isEmpty()) {
                for (Restoran restoran : listaRestorana) {
                    RestoranDto restoranDtoIzlaz = new RestoranDto(restoran.getNaziv(), restoran.getTipRestorana(), restoran.getLokacija());
                    restoranDtoList.add(restoranDtoIzlaz);
                }
                return restoranDtoList;
            }
        }

        return restoranDtoList;
    }

    @GetMapping("/restoran/{restoranID}")
    public ResponseEntity izaberiRestoran(@PathVariable("restoranID") Long restoranID) {

        Restoran restoran;

        try {
            restoran = restoranService.nadjiRestoran(restoranID);
        } catch (Exception e) {
            return new ResponseEntity("Neispravan id", HttpStatus.BAD_REQUEST);
        }

        RestoranPojedinacniDto restoranPojedinacniDto = new RestoranPojedinacniDto(restoran);

        restoranPojedinacniDto.setKomentari(komentarService.findAllByRestoran(restoran));
        restoranPojedinacniDto.setProsecnaOcena(komentarService.averageMark(restoran));
        restoranPojedinacniDto.setStatus(RestoranPojedinacniDto.Status.RADI);

        return new ResponseEntity(restoranPojedinacniDto, HttpStatus.OK);
    }

    @PostMapping("/restoran/dodaj-artikal")
    public ResponseEntity dodajArtikal(@RequestBody ArtikalDto artikalDto, HttpSession session) {
        if (sessionService.validateUloga(session,"Menadzer") && sessionService.validateSession(session)) {
            HashMap<String, String> errorDic = ValidateArtikal(artikalDto);

            if (!errorDic.isEmpty()){
                return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);
            }

            Artikal artikal = new Artikal(artikalDto.getNaziv(), artikalDto.getCena(), artikalDto.getTip(), artikalDto.getKolicina(), artikalDto.getOpis());
            artikalService.save(artikal);

            return new ResponseEntity("Uspesno dodat artikal", HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);
    }

    private HashMap<String, String> ValidateArtikal(ArtikalDto artikalDto){
        HashMap<String, String> errorDic = new HashMap<>();

        if (artikalDto.getNaziv() == null || artikalDto.getNaziv().isEmpty() || artikalDto.getNaziv().length()>25)
            errorDic.put("Naziv", "Polje naziv je neispravno uneseno");
        if (artikalService.CheckNazivAgainst(artikalDto.getNaziv()))
            errorDic.put("Naziv", "Naziv artikla vec postoji, unesite drugi");
        if (artikalDto.getCena() <= 0)
            errorDic.put("Cena", "Polje cena je neispravno uneseno");
        if (artikalDto.getTip() == null)
            errorDic.put("Tip", "Polje tip nije uneseno");
        // ostaje provera za sliku da se odradi
        if (artikalDto.getKolicina() <= 0)
            errorDic.put("Kolicina", "Polje kolicina je neispravno uneseno");


        return errorDic;
    }

    @PutMapping("/restoran/izmeni-artikal/{id}")
    public ResponseEntity izmeniArtikal(@RequestBody ArtikalDto artikalDto, @PathVariable Long id, HttpSession session) {
        if (sessionService.validateUloga(session,"Menadzer") && sessionService.validateSession(session)) {
            HashMap<String, String> errorDic = ValidateArtikalPriMenjanju(artikalDto);

            if (!errorDic.isEmpty()){
                return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);
            }

            Artikal artikal;
            try {
                artikal = artikalService.nadjiArtikal(id);
            } catch (Exception e) {
                return new ResponseEntity("Neispravan id", HttpStatus.BAD_REQUEST);
            }

            artikalService.azuriraj(artikalDto, artikal);

            return new ResponseEntity("Uspesno azuriran artikal", HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);
    }

    private HashMap<String, String> ValidateArtikalPriMenjanju(ArtikalDto artikalDto){
        HashMap<String, String> errorDic = new HashMap<>();

        if (artikalDto.getNaziv() != null && (artikalDto.getNaziv().isEmpty() || artikalDto.getNaziv().length()>25))
            errorDic.put("Naziv", "Polje naziv je neispravno uneseno");
        if (artikalDto.getNaziv() != null && (artikalService.CheckNazivAgainst(artikalDto.getNaziv())))
            errorDic.put("Naziv", "Naziv artikla vec postoji, unesite drugi");
        if (artikalDto.getCena() <= 0)
            errorDic.put("Cena", "Polje cena je neispravno uneseno");
        // ostaje provera za sliku da se odradi
        if (artikalDto.getKolicina() <= 0)
            errorDic.put("Kolicina", "Polje kolicina je neispravno uneseno");


        return errorDic;
    }

    @GetMapping("/restoran/obrisi-artikal/{id}")
    public ResponseEntity obrisiArtikal(@PathVariable Long id, HttpSession session) {
        if (sessionService.validateUloga(session,"Menadzer") && sessionService.validateSession(session)) {

            Korisnik korisnik = korisnikService.findByUsername((String) session.getAttribute("korisnicko_ime"));
            Restoran restoran = ((Menadzer) korisnik).getRestoran();

            Artikal artikal;
            try {
                artikal = artikalService.nadjiArtikal(id);
            } catch (Exception e) {
                return new ResponseEntity("Neispravan id", HttpStatus.BAD_REQUEST);
            }

            if (restoran.getArtikli().contains(artikal)) {
                restoran.getArtikli().remove(artikal);
                restoranService.save(restoran);
                return new ResponseEntity("Uspesno obrisan artikal", HttpStatus.OK);
            }

            return new ResponseEntity("Artikal ne pripada restoranu", HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);
    }

    @PostMapping(
    value = "obrisi-restoran")
    public ResponseEntity obrisiRestoran(@RequestBody RestoranNazivDto restoranNazivDto, HttpSession session) {
        if (sessionService.validateUloga(session,"Admin") && sessionService.validateSession(session)) {

            Restoran restoran = restoranService.findByNaziv(restoranNazivDto.getNaziv()); // naziv je 100% ispravan, jer ga uzimamo iz baze
            List<Komentar> komentarList = komentarService.findAllByRestoran(restoran);

            Menadzer menadzer = menadzerService.menadzerKonkretnogRestorana(restoran);
            menadzer.setRestoran(null);



            List<Kupac> kupacList = kupacService.findAll();
            List<Porudzbina> tmp = new ArrayList<>();
            for (Kupac kupac:kupacList) {
                for (Porudzbina porudzbina: kupac.getIstorija_porudzbina()) {
                    if (porudzbina.getRestoran() == restoran) {
                        tmp.add(porudzbina);
                        //kupac.getIstorija_porudzbina().remove(porudzbina);
                    }
                }
                for (Porudzbina porudzbina: tmp) {
                    kupac.getIstorija_porudzbina().remove(porudzbina);
                }
            }



            List<Dostavljac> dostavljacList = dostavljacService.findAll();
            List<Porudzbina> tmp2 = new ArrayList<>();
            for (Dostavljac dostavljac:dostavljacList) {
                for (Porudzbina porudzbina: dostavljac.getPorudzbine()) {
                    if (porudzbina.getRestoran() == restoran) {
                        tmp2.add(porudzbina);
                        //dostavljac.getPorudzbine().remove(porudzbina);
                    }
                }
                for (Porudzbina porudzbina: tmp) {
                    dostavljac.getPorudzbine().remove(porudzbina);
                }
            }

            List<Porudzbina> porudzbinaList = porudzbinaService.findAllByRestoran(restoran);
            for (Porudzbina porudzbina:porudzbinaList) {
                porudzbinaService.delete(porudzbina);
            }

            for (Komentar komentar:komentarList) {
                komentarService.delete(komentar);
            }
            restoranService.delete(restoran);

            return new ResponseEntity("Uspesno obrisan restoran", HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);
    }


}
