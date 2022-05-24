package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Korisnik;
import vezbe.demo.model.Kupac;
import vezbe.demo.repository.KorisnikRepository;
import vezbe.demo.repository.KupacRepository;

import java.util.List;

@Service
public class RegisterService {

    @Autowired
    private KupacRepository kupacRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    public void Register(Korisnik korisnik, String uloga) throws Exception {
        CheckUserAgainst(korisnik.getKorisnickoIme(), (List<Korisnik>) ((List<?>)korisnikRepository.findAll())); // Iako registrujemo samo kupca, njegov username moze biti koriscen i za druge uloge odnosno korisnike

        if (uloga.equals("Kupac")){ // Promenio "kupac" u "Kupac", jer je "Kupac" prosledjen. Uloge pisem velikim pocetnim slovom
            kupacRepository.save((Kupac) korisnik);
        }
        else{
            throw new Exception("Uloga " + uloga + "je nepoznata ili nekompatibilna za registrovanje.");
        }
    }

    private void CheckUserAgainst(String korisnicko_ime, List<Korisnik> set) throws Exception {
        for(Korisnik k : set){
            if (k.getKorisnickoIme().equals(korisnicko_ime))
                ThrowUsernameInvalid(korisnicko_ime);
        }
    }

    private void ThrowUsernameInvalid(String korisnicko_ime) throws Exception {
        throw new Exception("Korisnik sa korisnickim imenom '" + korisnicko_ime + " vec postoji.");
    }
}
