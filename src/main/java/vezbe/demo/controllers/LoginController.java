package vezbe.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.dto.LoginDto;
import vezbe.demo.model.Korisnik;
import vezbe.demo.service.LoginService;
import vezbe.demo.service.SessionService;

import javax.management.InvalidAttributeValueException;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.Hashtable;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private SessionService sessionService;

    @PostMapping("api/login")
    public ResponseEntity Login(@RequestBody LoginDto loginDto, HttpSession session){
        Hashtable<String, String> errorDic = new Hashtable<>();

        if(loginDto.getKorisnickoIme() == null || loginDto.getKorisnickoIme().isEmpty())
            errorDic.put("korisnicko_ime", "Korisnicko ime je obavezno");
        if (loginDto.getLozinka() == null || loginDto.getLozinka().isEmpty())
            errorDic.put("lozinka", "Lozinka je obavezna");

        if (!errorDic.isEmpty())
            return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);

        Korisnik korisnik = null;

        try {
            korisnik = loginService.Login(loginDto.getKorisnickoIme(), loginDto.getLozinka());
        } catch (AccountNotFoundException e) {
            errorDic.put("Korisnicko ime", e.getMessage());
        } catch (InvalidAttributeValueException e) {
            errorDic.put("Lozinka", e.getMessage());
        }

        if (!errorDic.isEmpty() || korisnik == null)
            return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);

        session.setAttribute("uloga", korisnik.getUloga()); // mislim da ovo nije dobro, getClass().getName()? - izmenio
        session.setAttribute("korisnicko_ime", korisnik.getKorisnickoIme());         // videcu da li ce mi ovo koristiti korisnickoIme - po potrebi menjati

        return new ResponseEntity(korisnik, HttpStatus.OK);
    }

    @PostMapping("api/logout")
    public ResponseEntity Logout(HttpSession session){
        String korisnicko_ime = sessionService.getKorisnicko_Ime(session);
        String uloga = sessionService.getUloga(session);

        if (korisnicko_ime.isEmpty() || uloga.isEmpty())
            return new ResponseEntity("Zabranjeno", HttpStatus.FORBIDDEN);

        session.invalidate();
        return new ResponseEntity("Ok", HttpStatus.OK);
    }

}
