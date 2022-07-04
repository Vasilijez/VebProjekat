package vezbe.demo.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.dto.*;
import vezbe.demo.model.*;
import vezbe.demo.repository.MenadzerRepository;
import vezbe.demo.service.*;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTML;
import java.util.*;


@RequestMapping("/api")
@RestController
public class KorisnikController {

    @Autowired
    SessionService sessionService;

    @Autowired
    KorisnikService korisnikService;

    @Autowired
    PorudzbinaService porudzbinaService;

    @Autowired
    MenadzerService menadzerService;

    @Autowired
    DostavljacService dostavljacService;

    @Autowired
    RestoranService restoranService;

    @Autowired
    LokacijaService lokacijaService;

    @GetMapping("profil")
    public ResponseEntity pregledProfila(HttpSession session) {
        if (sessionService.validateSession(session)) {
            Korisnik korisnik = korisnikService.findByUsername((String) session.getAttribute("korisnicko_ime"));
            KorisnikDto korisnikDto = new KorisnikDto(korisnik);

            return new ResponseEntity(korisnikDto, HttpStatus.OK);
        } else {
            return new ResponseEntity("Neispravna sesija, ulogujte se ponovo", HttpStatus.BAD_REQUEST);
        }
    }

    // metoda kojom cemo znati kome sta da prikazemo
    @GetMapping(
            value = "vratiUlogu",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity vratiUlogu(HttpSession session) {
        if (sessionService.validateSession(session)) {
            Korisnik ulogovani = korisnikService.findByUsername(sessionService.getKorisnicko_Ime(session)); // dodao

            return new ResponseEntity(ulogovani.getUloga(), HttpStatus.OK);
        } else
            return new ResponseEntity("neovlascen_pristup", HttpStatus.FORBIDDEN);
    }


    @PutMapping("izmeni-profil")
    public ResponseEntity izmeniProfil(@RequestBody KorisnikAzuriranDto korisnikAzuriranDto, HttpSession session) {
        if (sessionService.validateSession(session)) {
            HashMap<String, String> errorDic = ValidateIzmeni(korisnikAzuriranDto);

            if (!errorDic.isEmpty()){
                return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);
            }

            Korisnik korisnik = korisnikService.findByUsername((String) session.getAttribute("korisnicko_ime"));
            korisnikService.azuriraj(korisnikAzuriranDto, korisnik, session);
            KorisnikAzuriranDto korisnikAzuriranDtoIzlaz = new KorisnikAzuriranDto(korisnik);

            return new ResponseEntity(korisnikAzuriranDtoIzlaz, HttpStatus.OK);
        } else {
            return new ResponseEntity("Neispravna sesija, ulogujte se ponovo", HttpStatus.BAD_REQUEST);
        }
    }

    private HashMap<String, String> ValidateIzmeni(KorisnikAzuriranDto korisnikAzuriranDto){
        HashMap<String, String> errorDic = new HashMap<>();

        if (korisnikAzuriranDto.getIme() != null && (korisnikAzuriranDto.getIme().isEmpty() || korisnikAzuriranDto.getIme().length()>25))
            errorDic.put("Ime", "Polje ime je neispravno uneseno");
        if (korisnikAzuriranDto.getPrezime() != null && (korisnikAzuriranDto.getPrezime().isEmpty() || korisnikAzuriranDto.getPrezime().length()>25))
            errorDic.put("Prezime", "Polje prezime je neispravno uneseno");
        if (korisnikAzuriranDto.getKorisnickoIme() != null && (korisnikAzuriranDto.getKorisnickoIme().isEmpty() || korisnikAzuriranDto.getKorisnickoIme().length()>25))
            errorDic.put("Korisnicko ime", "Polje korisnicko ime je neispravno uneseno");
        if (korisnikAzuriranDto.getKorisnickoIme() != null && (korisnikService.CheckUserAgainst(korisnikAzuriranDto.getKorisnickoIme())))
            errorDic.put("Korisnicko ime", "Korisnicko ime vec postoji, unesite drugo");
        if (korisnikAzuriranDto.getLozinka() != null && (korisnikAzuriranDto.getLozinka().isEmpty() || korisnikAzuriranDto.getLozinka().length()>25))
            errorDic.put("Lozinka", "Polje lozinka je neispravno uneseno");
        if (korisnikAzuriranDto.getDatum_rodjenja() != null && (korisnikAzuriranDto.getDatum_rodjenja().after(new Date()) || (korisnikAzuriranDto.getDatum_rodjenja().before(new Date(1900-1900, 01,01)))))
            errorDic.put("Datum", "Polje datum je neispravno uneseno");

        return errorDic;
    }

    @GetMapping(value = "admin",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity pregledKorisnikaAdmina(HttpSession session) {
        if (sessionService.validateUloga(session,"Admin") && sessionService.validateSession(session)) {
            List<KorisnikDto> listaKorisnikaDto = new ArrayList<>();
            List<Korisnik> listaKorisnika = korisnikService.findAll();

            for (Korisnik k : listaKorisnika) {
                KorisnikDto korisnikDto = new KorisnikDto(k);
                listaKorisnikaDto.add(korisnikDto);
            }

            return new ResponseEntity(listaKorisnikaDto, HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);
    }

    @GetMapping("menadzer")
    public ResponseEntity pregledKorisnikaMenadzera(HttpSession session) {
        if (sessionService.validateUloga(session,"Menadzer") && sessionService.validateSession(session)) {
            Menadzer menadzer = (Menadzer) korisnikService.findByUsername((String) session.getAttribute("korisnicko_ime"));

            List<Porudzbina> porudzbinaList = porudzbinaService.findAllByRestoran(menadzer.getRestoran());

            MenadzerDto menadzerDto = new MenadzerDto(menadzer.getRestoran(), porudzbinaList);

            return new ResponseEntity(menadzerDto, HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);
    }

    @PostMapping(
    value = "dodaj-menadzera",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity dodajMenadzera(@RequestBody Menadzer menadzer, HttpSession session) {
        if (sessionService.validateUloga(session,"Admin") && sessionService.validateSession(session)) {
            HashMap<String, String> errorDic = Validate(menadzer);

            if (!errorDic.isEmpty()){
                return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);
            }

            menadzerService.save(menadzer);

            return new ResponseEntity("Uspesno dodat menadzer", HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);
    }

    @PostMapping(
            value = "dodaj-dostavljaca",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity dodajDostavljaca(@RequestBody Dostavljac dostavljac, HttpSession session) {
        if (sessionService.validateUloga(session,"Admin") && sessionService.validateSession(session)) {
            HashMap<String, String> errorDic = Validate(dostavljac);

            if (!errorDic.isEmpty()){
                return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);
            }
            dostavljacService.save(dostavljac);
            return new ResponseEntity("Uspesno dodat dostavljac", HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);
    }

    private HashMap<String, String> Validate(Korisnik korisnik){
        HashMap<String, String> errorDic = new HashMap<>();

        if (korisnik.getIme() == null || korisnik.getIme().isEmpty() || korisnik.getIme().length()>25)
            errorDic.put("Ime", "Polje ime je neispravno uneseno");
        if (korisnik.getPrezime() == null || korisnik.getPrezime().isEmpty() || korisnik.getPrezime().length()>25)
            errorDic.put("Prezime", "Polje prezime je neispravno uneseno");
        if (korisnik.getKorisnickoIme() == null || korisnik.getKorisnickoIme().isEmpty() || korisnik.getKorisnickoIme().length()>25)
            errorDic.put("Korisnicko ime", "Polje korisnicko ime je neispravno uneseno");
        if (korisnikService.CheckUserAgainst(korisnik.getKorisnickoIme()))
            errorDic.put("Korisnicko ime", "Korisnicko ime vec postoji, unesite drugo");
        if (korisnik.getLozinka() == null || korisnik.getLozinka().isEmpty() || korisnik.getLozinka().length()>25)
            errorDic.put("Lozinka", "Polje lozinka je neispravno uneseno");
        if (korisnik.getDatum_rodjenja() == null || korisnik.getDatum_rodjenja().after(new Date()) || (korisnik.getDatum_rodjenja().before(new Date(1900-1900, 01,01))))
            errorDic.put("Datum", "Polje datum je neispravno uneseno");
        if (korisnik.getP() == null)
            errorDic.put("Pol", "Polje pol je neispravno uneseno");

        return errorDic;
    }

    @PostMapping(
    value = "dodaj-restoran",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity dodajRestoran(@RequestBody RestoranDto restoranDto, HttpSession session) {
        if (sessionService.validateUloga(session,"Admin") && sessionService.validateSession(session)) {
            HashMap<String, String> errorDic = ValidateRestoran(restoranDto);

            if (!errorDic.isEmpty()){
                return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);
            }
            Lokacija lokacija = restoranDto.getLokacija();
            lokacijaService.save(lokacija);
            Restoran restoran = new Restoran(restoranDto.getNaziv(), restoranDto.getTipRestorana(), null, lokacija);
            restoranService.save(restoran);

            return new ResponseEntity("Uspesno dodat restoran", HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);

    }

    private HashMap<String, String> ValidateRestoran(RestoranDto restoranDto){
        HashMap<String, String> errorDic = new HashMap<>();

        if (restoranDto.getNaziv() == null || restoranDto.getNaziv().isEmpty() || restoranDto.getNaziv().length()>25)
            errorDic.put("Naziv", "Polje naziv je neispravno uneseno");
        if (restoranService.CheckNazivAgainst(restoranDto.getNaziv()))
            errorDic.put("Naziv", "Naziv vec postoji, unesite drugi");
        if (restoranDto.getTipRestorana() == null || restoranDto.getTipRestorana().isEmpty() || restoranDto.getTipRestorana().length()>25)
            errorDic.put("Tip restorana", "Polje tip restorana je neispravno uneseno");
        if (restoranDto.getLokacija().getAdresa() == null || restoranDto.getLokacija().getAdresa().isEmpty() || restoranDto.getLokacija().getAdresa().length()>25)
            errorDic.put("Adresa", "Polje adresa je neispravno uneseno");
        if (restoranService.CheckAdresaAgainst(restoranDto.getLokacija().getAdresa()))
            errorDic.put("Adresa", "Adresa vec postoji, unesite drugu");
        if (restoranDto.getLokacija().getDuzina() == 0 || restoranDto.getLokacija().getDuzina()>180 || restoranDto.getLokacija().getDuzina()<0)
            errorDic.put("Duzina", "Polje duzina je neispravno uneseno");
        if (restoranDto.getLokacija().getSirina() == 0 || restoranDto.getLokacija().getSirina()>90 || restoranDto.getLokacija().getSirina()<0)
            errorDic.put("Sirina", "Polje sirina je neispravno uneseno");

        return errorDic;
    }

    @GetMapping("postavi-menadzera/{korisnicko_ime}/{naziv_restorana}")
    public ResponseEntity postaviMenadzera(HttpSession session, @PathVariable("korisnicko_ime") String korisnickoIme, @PathVariable("naziv_restorana") String naziv_restorana) {
        if (sessionService.validateUloga(session,"Admin") && sessionService.validateSession(session)) {
            HashMap<String, String> errorDic = ValidatePostavljanjeMenadzera(korisnickoIme, naziv_restorana);

            if (!errorDic.isEmpty()) {
                return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);
            }

            Korisnik korisnik = korisnikService.findByUsername(korisnickoIme);
            Restoran restoran = restoranService.findByNaziv(naziv_restorana);
            if (korisnik.getUloga() == Korisnik.Uloga.Menadzer) {
                if (menadzerService.restoranImaMenadzera(restoran))
                    return new ResponseEntity("Restoran vec ima menadzera", HttpStatus.BAD_REQUEST);
                else if (((Menadzer)korisnik).getRestoran() != null) {
                    return new ResponseEntity("Menadzer vec ima restoran", HttpStatus.BAD_REQUEST);
                }
                else {
                    Menadzer menadzer = (Menadzer) korisnik;
                    menadzer.setRestoran(restoran);
                    menadzerService.save(menadzer);
                }
            } else
                return new ResponseEntity("Korisnik nije menadzer", HttpStatus.BAD_REQUEST);

            return new ResponseEntity("Uspesno postavljen menadzer", HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);


    }

    private HashMap<String, String> ValidatePostavljanjeMenadzera(String korisnickoIme, String nazivRestorana){
        HashMap<String, String> errorDic = new HashMap<>();

        if (korisnickoIme == null || korisnickoIme.isEmpty() || korisnickoIme.length()>25)
            errorDic.put("Korisnicko ime", "Polje korisnicko ime je neispravno uneseno");
        if (!korisnikService.CheckUserAgainst(korisnickoIme))
            errorDic.put("Korisnicko ime", "korisnicko ime NE postoji, unesite drugo");
        if (nazivRestorana == null || nazivRestorana.isEmpty() || nazivRestorana.length()>25)
            errorDic.put("Naziv", "Polje naziv je neispravno uneseno");
        if (!restoranService.CheckNazivAgainst(nazivRestorana))
            errorDic.put("Naziv restorana", "Naziv restorana NE postoji, unesite drugi");

        return errorDic;
    }

    @GetMapping(
            value = "menadzeri",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity pregledMenadzera(HttpSession session) {
        if (sessionService.validateUloga(session,"Admin") && sessionService.validateSession(session)) {
            List<Menadzer> menadzerList = menadzerService.findAll();

            // todo preko MenadzerDTO, mrzi me sada

            return new ResponseEntity(menadzerList, HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);
    }

    @GetMapping(
            value = "/menadzeri-bez-restorana",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity prikazMenadzeraBezRestorana(HttpSession session) {
        if (sessionService.validateUloga(session,"Admin") && sessionService.validateSession(session)) {

            List<Menadzer> menadzerList = menadzerService.findAll();
            List<Menadzer> listaZaOdstrel = new ArrayList<>();
            for (Menadzer menadzer:
                    menadzerList) {
                listaZaOdstrel.add(menadzer);
            }

            List<Menadzer> finalnaLista = new ArrayList<>();
            for (Menadzer menadzer: listaZaOdstrel) {
                if (menadzer.getRestoran() == null) {
                    finalnaLista.add(menadzer);
                }
            }

            // todo preko MenadzerDTO, mrzi me sada

            return new ResponseEntity(finalnaLista, HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);
    }


}
