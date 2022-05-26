package vezbe.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vezbe.demo.model.Artikal;
import vezbe.demo.model.Lokacija;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

public class RestoranDto {


    private String naziv;

    private String tipRestorana;

    private Lokacija lokacija;


    public RestoranDto() {
    }

    public RestoranDto(String naziv, String tipRestorana, Lokacija lokacija) {
        this.naziv = naziv;
        this.tipRestorana = tipRestorana;
        this.lokacija = lokacija;
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

    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }
}
