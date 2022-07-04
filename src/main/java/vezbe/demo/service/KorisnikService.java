package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.KorisnikAzuriranDto;
import vezbe.demo.dto.KorisnikDto;
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

    public Korisnik azuriraj(KorisnikDto KorisnikDto, Korisnik korisnik, HttpSession session) {

        if (KorisnikDto.getIme() != null)
            korisnik.setIme(KorisnikDto.getIme());
        if (KorisnikDto.getPrezime() != null)
            korisnik.setPrezime(KorisnikDto.getPrezime());
        if (KorisnikDto.getLozinka() != null)
            korisnik.setLozinka(KorisnikDto.getLozinka());
        if (KorisnikDto.getP() != null)
            korisnik.setP(KorisnikDto.getP());
        if (KorisnikDto.getDatum_rodjenja() != null)
            korisnik.setDatum_rodjenja(KorisnikDto.getDatum_rodjenja());
        if (KorisnikDto.getKorisnickoIme() != null) {
            session.removeAttribute("korisnicko_ime");
            session.setAttribute("korisnicko_ime", KorisnikDto.getKorisnickoIme());
            korisnik.setKorisnickoIme(KorisnikDto.getKorisnickoIme());
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
