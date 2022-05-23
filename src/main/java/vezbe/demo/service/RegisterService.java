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
    private KorisnikRepository korisnikRepository;

    @Autowired
    private KupacRepository kupacRepository;

    public void Register(Korisnik korisnik, String uloga) throws Exception {
        CheckUserAgainst(korisnik.getKorisnicko_ime(), (List<Korisnik>) ((List<?>)korisnikRepository.findAll()));

        if (uloga.equals("kupac")){
            kupacRepository.save((Kupac) korisnik);
        }
        else{
            throw new Exception("Uloga " + uloga + "je nepoznata ili nekompatibilna za registrovanje.");
        }
    }

    private void CheckUserAgainst(String korisnicko_ime, List<Korisnik> set) throws Exception {
        for(Korisnik k : set){
            if (k.getKorisnicko_ime().equals(korisnicko_ime))
                ThrowUsernameInvalid(korisnicko_ime);
        }
    }

    private void ThrowUsernameInvalid(String korisnicko_ime) throws Exception {
        throw new Exception("Korisnik sa korisnickim imenom '" + korisnicko_ime + " vec postoji.");
    }
}
