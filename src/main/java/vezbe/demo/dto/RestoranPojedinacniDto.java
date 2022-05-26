package vezbe.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vezbe.demo.model.Artikal;
import vezbe.demo.model.Komentar;
import vezbe.demo.model.Lokacija;
import vezbe.demo.model.Restoran;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class RestoranPojedinacniDto {

    private String naziv;

    private String tipRestorana;



    public enum Status {
        RADI,
        NE_RADI
    }
    private Status status;

    private Lokacija lokacija;

    private Double prosecnaOcena;

    private List<Komentar> komentari;

    private Set<Artikal> artikli;

    public RestoranPojedinacniDto() {
    }

    public RestoranPojedinacniDto(Restoran restoran) {
        this.naziv = restoran.getNaziv();
        this.tipRestorana = restoran.getTipRestorana();
        this.lokacija = restoran.getLokacija();
        this.artikli = restoran.getArtikli();
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTipRestorana() {
        return tipRestorana;
    }

    public void setTipRestorana(String tipRestorana) {
        this.tipRestorana = tipRestorana;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }

    public Double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(Double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

    public List<Komentar> getKomentari() {
        return komentari;
    }

    public void setKomentari(List<Komentar> komentari) {
        this.komentari = komentari;
    }

    public Set<Artikal> getArtikli() {
        return artikli;
    }

    public void setArtikli(Set<Artikal> artikli) {
        this.artikli = artikli;
    }
}
