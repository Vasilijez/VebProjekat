package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Korisnik;
import vezbe.demo.repository.KupacRepository;

import javax.management.InvalidAttributeValueException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
public class LoginService {


    @Autowired
    private KupacRepository kupacRepository;

    public Korisnik Login(String korisnicko_ime, String lozinka) throws AccountNotFoundException, InvalidAttributeValueException {
        Korisnik korisnik = FindKorisnik(korisnicko_ime, (List<Korisnik>) ((List<?>)kupacRepository.findAll()));

        if (korisnik == null)
            throw new AccountNotFoundException("Korisnik sa datim korisnickim imenom nije pronadjen.");

        if (!korisnik.getLozinka().equals(lozinka))
            throw new InvalidAttributeValueException("Uneta lozinka nije tacna.");

        return korisnik;
    }

    private Korisnik FindKorisnik(String korisnicko_ime, List<Korisnik> niz) {
        for(Korisnik k : niz){
            if (k.getKorisnicko_ime().equals(korisnicko_ime))
                return k;
        }
        return null;
    }
}
