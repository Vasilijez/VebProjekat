package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.ArtikalDto;
import vezbe.demo.model.Artikal;
import vezbe.demo.repository.ArtikalRepository;

import java.util.List;
import java.util.Locale;

@Service
public class ArtikalService {

    @Autowired
    ArtikalRepository artikalRepository;

    public boolean CheckNazivAgainst(String nazivArtikla) {
        List<Artikal> listaArtikala = artikalRepository.findAll();

        for(Artikal artikal : listaArtikala){
            if (artikal.getNaziv().toLowerCase(Locale.ROOT).equals(nazivArtikla.toLowerCase(Locale.ROOT)))
                return true;
        }

        return false;
    }

    public void save(Artikal artikal) {
        artikalRepository.save(artikal);
    }

    public void azuriraj(ArtikalDto artikalDto, Artikal artikal) {

        if (artikalDto.getNaziv() != null)
            artikal.setNaziv(artikalDto.getNaziv());
        if (artikalDto.getKolicina() > 0)
            artikal.setKolicina(artikalDto.getKolicina());
        if (artikalDto.getTip() != null)
            artikal.setTip(artikalDto.getTip());
        if (artikalDto.getCena() > 0)
            artikal.setCena(artikalDto.getCena());
        if (artikalDto.getOpis() != null)
            artikal.setOpis(artikalDto.getOpis());
        // ostaje provera za sliku

        artikalRepository.save(artikal);
    }

    public Artikal nadjiArtikal(Long id) {
        return artikalRepository.findById(id).get();
    }

    public void delete(Artikal artikal) {
        artikalRepository.delete(artikal);
    }

}
