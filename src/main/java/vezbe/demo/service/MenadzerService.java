package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Korisnik;
import vezbe.demo.model.Menadzer;
import vezbe.demo.model.Restoran;
import vezbe.demo.repository.MenadzerRepository;

import java.util.List;

@Service
public class MenadzerService {

    @Autowired
    MenadzerRepository menadzerRepository;

    public Menadzer save(Menadzer menadzer) {
        menadzer.setUloga(Korisnik.Uloga.Menadzer);
        return menadzerRepository.save(menadzer);
    }

    public boolean restoranImaMenadzera(Restoran restoran) {
        List<Menadzer> menadzerList = menadzerRepository.findAll();

        for (Menadzer menadzer : menadzerList) {
            if (menadzer.getRestoran() == restoran)
                return true;
        }

        return false;
    }

    public Menadzer menadzerKonkretnogRestorana(Restoran restoran) {
        List<Menadzer> menadzerList = menadzerRepository.findAll();

        for (Menadzer menadzer : menadzerList) {
            if (menadzer.getRestoran() == restoran)
                return menadzer;
        }

        return null;
    }

    public List<Menadzer> findAll() {
        return menadzerRepository.findAll();
    }
}
