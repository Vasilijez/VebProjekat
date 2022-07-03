package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.RegisterDto;
import vezbe.demo.model.Kupac;
import vezbe.demo.model.TipKupca;
import vezbe.demo.repository.KupacRepository;
import vezbe.demo.repository.TipKupcaRepository;

import java.util.List;

@Service
public class KupacService {

    @Autowired
    TipKupcaRepository tipKupcaRepository;

    @Autowired
    private KupacRepository kupacRepository;

    public List<Kupac> findAll() {
        return kupacRepository.findAll();
    }

    public void postaviTip(Kupac kupac) {
        TipKupca tipKupca = new TipKupca(TipKupca.Tip.Bronzani, 0, 0); // dodao
        tipKupcaRepository.save(tipKupca);
        kupac.setTip(tipKupca);
    }

    public Kupac update(RegisterDto registerDto){
        Kupac kupac = kupacRepository.findKupacByKorisnickoIme(registerDto.getKorisnickoIme());

        kupac.setLozinka(registerDto.getLozinka());
        kupac.setIme(registerDto.getIme());
        kupac.setPrezime(registerDto.getPrezime());

        kupacRepository.save(kupac);

        return kupac;
    }


    public void save(Kupac kupac){
        kupacRepository.save(kupac);
    }
}
