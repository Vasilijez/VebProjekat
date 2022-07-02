package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Lokacija;
import vezbe.demo.model.Restoran;
import vezbe.demo.repository.RestoranRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class RestoranService {

    @Autowired
    RestoranRepository restoranRepository;

    public boolean CheckNazivAgainst(String naziv) {
        List<Restoran> listaRestorana = restoranRepository.findAll();

        for(Restoran r : listaRestorana){
            if (r.getNaziv().equals(naziv))
                return true;
        }

        return false;
    }

    public boolean CheckAdresaAgainst(String nazivAdrese) {
        List<Restoran> listaRestorana = restoranRepository.findAll();

        for(Restoran r : listaRestorana){
            if (r.getLokacija().getAdresa().equals(nazivAdrese))
                return true;
        }

        return false;
    }

    public Restoran save(Restoran restoran) {
        return restoranRepository.save(restoran);
    }

    public void delete(Restoran restoran) {
        restoranRepository.delete(restoran);
    }


    public Restoran findByNaziv(String naziv) {
        return restoranRepository.findByNaziv(naziv);
    }

    public List<Restoran> findAll() {
        return restoranRepository.findAll();
    }

    public List<Restoran> findByNazivLikeCaseInsensitive(String naziv) {
        return restoranRepository.findByNazivLikeCaseInsensitive(naziv);
    }

    public List<Restoran> findByTipLikeCaseInsensitive(String tip) {
        return restoranRepository.findByTipLikeCaseInsensitive(tip);
    }

    public List<Restoran> searchByLokacija(Lokacija lokacija) {
        List<Restoran> listaRestorana = restoranRepository.findAll();
        List<Restoran> filtriranaLista = new ArrayList<>();

        for (Restoran r: listaRestorana) {
            if ((lokacija.getDuzina() == r.getLokacija().getDuzina()) && (lokacija.getSirina() == r.getLokacija().getSirina())) { // ukoliko pronadjemo restoran sa identicnim koordinatam, odmah ga vracamo, u suprotnom radimo pretragu na osnovu adrese, kod adrese nam nije bitan broj dovoljan je naziv ulice
                filtriranaLista.clear();
                filtriranaLista.add(r);
                break;
            }
            if ((r.getLokacija().getAdresa().toLowerCase(Locale.ROOT).contains(lokacija.getAdresa().toLowerCase(Locale.ROOT)))) {
                filtriranaLista.add(r);
            }
        }

        return filtriranaLista;
    }

    public List<Restoran> searchByLokacijaAdresa(String adresa) {
        List<Restoran> listaRestorana = restoranRepository.findAll();
        List<Restoran> filtriranaLista = new ArrayList<>();

        for (Restoran r: listaRestorana) {
            if ((r.getLokacija().getAdresa().toLowerCase(Locale.ROOT).contains(adresa.toLowerCase(Locale.ROOT)))) {
                filtriranaLista.add(r);
            }
        }

        return filtriranaLista;
    }


    public Restoran nadjiRestoran(Long id) throws Exception {
        return restoranRepository.findById(id).get();
    }


}
