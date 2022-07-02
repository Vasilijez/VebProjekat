package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.dto.ArtikalUKorpiDto;
import vezbe.demo.dto.KorpaDto;
import vezbe.demo.model.*;
import vezbe.demo.repository.ArtikalUPorudzbiniRepository;
import vezbe.demo.repository.PorudzbinaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PorudzbinaService {

    @Autowired
    PorudzbinaRepository porudzbinaRepository;

    @Autowired
    ArtikalUPorudzbiniRepository artikalUPorudzbiniRepository;

    @Autowired
    ArtikalService artikalService;

    @Autowired
    RestoranService restoranService;

    @Autowired
    ArtikalUPorudzbiniService artikalUPorudzbiniService;

    public List<Porudzbina> findAllByRestoran(Restoran restoran) {
        return porudzbinaRepository.findAllByRestoran(restoran);
    }

    public Artikal findArtikalById(Long id){
        return artikalService.nadjiArtikal(id);
    }

    public void save(Porudzbina porudzbina){
        porudzbinaRepository.save(porudzbina);
    }

    public Porudzbina findById(UUID id) { return porudzbinaRepository.findById(id).get(); }

    public boolean ArtikaluRestoranu(Long id1, Long id2){
        return restoranService.ArtikaluRestoranu(id1, id2);
    }

    public Restoran findRestoranById(Long id){
        return restoranService.findRestoranById(id);
    }

    public void saveArtikalUPorudzbini(ArtikalUPorudzbini ap){ artikalUPorudzbiniRepository.save(ap); }

    public void saveKupac(Kupac kupac){
        KupacService.save(kupac);
    }

    public Boolean ArtikalUKorpi(Kupac kupac, Long id) {

        Set<Porudzbina> porudzbineKupca = kupac.getIstorija_porudzbina();
        Porudzbina korpa = new Porudzbina();
        boolean b = false;

        for(Porudzbina p : porudzbineKupca) {
            if(p.getStatus().equals(Porudzbina.Status.u_korpi)) {
                korpa = p;
                b = true;
            }
        }

        if(!b)
            return false;

        for(ArtikalUPorudzbini ap : korpa.getArtikliUPorudzbini()) {
            if(ap.getArtikal().getId().equals(id))
                return true;
        }

        return false;
    }

    public void izbaciIzKorpe(Kupac kupac, Long id) {

        Porudzbina korpa = kupac.korpa();

        for(ArtikalUPorudzbini ap : korpa.getArtikliUPorudzbini()) {
            if(ap.getArtikal().getId().equals(id)) {
                if(ap.getKolicina() != 1) {
                    ap.setKolicina(ap.getKolicina() - 1);
                    saveArtikalUPorudzbini(ap);
                    korpa.setCena(korpa.getCena() - ap.getArtikal().getCena());
                    save(korpa);
                    saveKupac(kupac);
                    return;
                }
                else {
                    korpa.getArtikliUPorudzbini().remove(ap);
                    korpa.setCena(korpa.getCena() - ap.getArtikal().getCena());
                    artikalUPorudzbiniService.delete(ap);
                    save(korpa);
                    saveKupac(kupac);
                    return;
                }
            }
        }

        if(korpa.getArtikliUPorudzbini().isEmpty()) {
            kupac.getIstorija_porudzbina().remove(korpa);
            saveKupac(kupac);
        }

    }

    public KorpaDto getKorpa(Kupac kupac) {

        Porudzbina korpa = kupac.korpa();

        List<ArtikalUKorpiDto> ap = new ArrayList<>();
        for(ArtikalUPorudzbini a : korpa.getArtikliUPorudzbini()) {
            ap.add(new ArtikalUKorpiDto(a.getArtikal().getNaziv(), a.getArtikal().getCena(), a.getKolicina()));
        }

        KorpaDto korpaDto = new KorpaDto(ap, korpa.getCena());
        return korpaDto;

    }

    public String submitKorpe(Kupac kupac) {

        Porudzbina korpa = kupac.korpa();

        korpa.setStatus(Porudzbina.Status.obrada);

        save(korpa);
        saveKupac(kupac);

        return "Porudzbina je uspesno napravljena!";
    }

    public String UPripremi(UUID id) {

        Porudzbina porudzbina = findById(id);

        if(porudzbina.getStatus().equals(Porudzbina.Status.obrada)) {

            porudzbina.setStatus((Porudzbina.Status.u_pripremi);
            save(porudzbina);

            return "Uspesno izmenjeno stanje korpe iz obrada u u pripremi!";
        }
        else
            return "Ova opcija je moguca samo za porudzbinu koja je u stanju obrade!";

    }

    public String CekaDostavljaca(UUID id) {

        Porudzbina porudzbina = findById(id);

        if(porudzbina.getStatus().equals(Porudzbina.Status.u_pripremi)) {

            porudzbina.setStatus(Porudzbina.Status.ceka_dostavljaca);
            save(porudzbina);
            return "Uspesno izmenjeno stanje porudzbine iz u pripremi u ceka dostavljaca!";

        }
        else
            return "Ova opcija je moguca samo za porudzbinu koja je u pripremi!";

    }

    public String UTransportu(UUID id) {

        Porudzbina porudzbina = findById(id);

        if(porudzbina.getStatus().equals(Porudzbina.Status.ceka_dostavljaca)) {

            porudzbina.setStatus(Porudzbina.Status.u_transportu);
            save(porudzbina);
            return "Uspesno izmenjeno stanje porudzbine iz ceka dostavljaca u u transportu!";

        }
        else
            return "Ova opcija je moguca samo za porudzbinu koja  ceka dostavljaca!";

    }

    public String Dostavljena(UUID id) {

        Porudzbina porudzbina = findById(id);

        if(porudzbina.getStatus().equals(Porudzbina.Status.u_transportu)) {

            porudzbina.setStatus(Porudzbina.Status.dostavljena);
            save(porudzbina);
            Kupac kupac = porudzbina.getKupac();

            kupac.setBroj_bodova((kupac.getBroj_bodova() + porudzbina.getCena()/1000*133));
            saveKupac(kupac);
            return "Uspesno izmenjeno stanje porudzbine iz u transportu u dostavljena!";
        }
        else
            return "Ova opcija je moguca samo za porudzbinu koja je u transportu!";

    }

    public boolean MenadzerOfPorudzbina(Menadzer menadzer, UUID id) {

        Porudzbina porudzbina = findById(id);

        if(porudzbina.getRestoran().getId().equals(menadzer.getRestoran().getId()))
            return true;

        return false;
    }

    public boolean DostavljacOfPorudzbina(Dostavljac dostavljac, UUID id) {

        Porudzbina porudzbina = findById(id);

        if(dostavljac.getPorudzbine().contains(porudzbina))
            return true;

        return false;
    }


}
