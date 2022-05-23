package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.RegisterDto;
import vezbe.demo.model.Kupac;
import vezbe.demo.repository.KupacRepository;

import java.util.List;

@Service
public class KupacService {


    @Autowired
    private KupacRepository kupacRepository;

    public List<Kupac> findAll() {
        return kupacRepository.findAll();
    }

    public Kupac update(RegisterDto registerDto){
        Kupac kupac = kupacRepository.findKupacByKorisnickoIme(registerDto.getKorisnicko_ime());

        kupac.setLozinka(registerDto.getLozinka());
        kupac.setIme(registerDto.getIme());
        kupac.setPrezime(registerDto.getPrezime());

        kupacRepository.save(kupac);

        return kupac;
    }
}
