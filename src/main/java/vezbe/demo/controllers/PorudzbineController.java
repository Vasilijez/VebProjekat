package vezbe.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vezbe.demo.model.*;
import vezbe.demo.service.KorisnikService;
import vezbe.demo.service.PorudzbinaService;
import vezbe.demo.service.SessionService;

import javax.servlet.http.HttpSession;
import java.util.*;

@RequestMapping("api")
@RestController
public class PorudzbineController {

    @Autowired
    SessionService sessionService;

    @Autowired
    KorisnikService korisnikService;

    @Autowired
    PorudzbinaService porudzbinaService;

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

    @GetMapping(
            value = "/porudzbina/{id}",
            /*consumes = MediaType.APPLICATION_JSON_VALUE, - bio problem u ovome*/
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity pronadjiArtikal(@PathVariable("id") UUID id, HttpSession session) {
        if (sessionService.validateUloga(session,"Menadzer") && sessionService.validateSession(session)) {

            Optional<Porudzbina> porudzbina;



            try {
                porudzbina = porudzbinaService.findById(id.toString());
            } catch (Exception e) {
                return new ResponseEntity("Neispravan id", HttpStatus.BAD_REQUEST);
            }
            // todo dto, mrzi me

            return new ResponseEntity(porudzbina, HttpStatus.OK);
        } else
            return new ResponseEntity("Neovlascen pristup", HttpStatus.FORBIDDEN);
    }

}
