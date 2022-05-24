package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.KorisnikAzuriranDto;
import vezbe.demo.model.Korisnik;
import vezbe.demo.repository.KorisnikRepository;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class KorisnikService {

    @Autowired
    KorisnikRepository korisnikRepository;

    public Korisnik findByUsername(String username) {
        return korisnikRepository.findByKorisnickoIme(username);
    }

    public List<Korisnik> findAll() {
        return korisnikRepository.findAll();
    }

    public Korisnik azuriraj(KorisnikAzuriranDto korisnikAzuriranDto, Korisnik korisnik, HttpSession session) {

        if (korisnikAzuriranDto.getIme() != null)
            korisnik.setIme(korisnikAzuriranDto.getIme());
        if (korisnikAzuriranDto.getPrezime() != null)
            korisnik.setPrezime(korisnikAzuriranDto.getPrezime());
        if (korisnikAzuriranDto.getLozinka() != null)
            korisnik.setLozinka(korisnikAzuriranDto.getLozinka());
        if (korisnikAzuriranDto.getP() != null)
            korisnik.setP(korisnikAzuriranDto.getP());
        if (korisnikAzuriranDto.getDatum_rodjenja() != null)
            korisnik.setDatum_rodjenja(korisnikAzuriranDto.getDatum_rodjenja());
        if (korisnikAzuriranDto.getKorisnickoIme() != null) {
            session.removeAttribute("korisnicko_ime");
            session.setAttribute("korisnicko_ime", korisnikAzuriranDto.getKorisnickoIme());
            korisnik.setKorisnickoIme(korisnikAzuriranDto.getKorisnickoIme());
        }

        return korisnikRepository.save(korisnik);
    }

    public boolean CheckUserAgainst(String korisnicko_ime) {
        List<Korisnik> listaKorisnika = korisnikRepository.findAll();

        for(Korisnik k : listaKorisnika){
            if (k.getKorisnickoIme().equals(korisnicko_ime))
                return true;
        }

        return false;
    }
}
