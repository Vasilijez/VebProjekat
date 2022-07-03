package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Dostavljac;
import vezbe.demo.model.Korisnik;
import vezbe.demo.model.Menadzer;
import vezbe.demo.repository.DostavljacRepository;
import vezbe.demo.repository.MenadzerRepository;

import java.util.List;

@Service
public class DostavljacService {

    @Autowired
    DostavljacRepository dostavljacRepository;

    public Dostavljac save(Dostavljac dostavljac) {
        dostavljac.setUloga(Korisnik.Uloga.Dostavljac);
        return dostavljacRepository.save(dostavljac);
    }

    public List<Dostavljac> findAll() {
        return dostavljacRepository.findAll();
    }
}