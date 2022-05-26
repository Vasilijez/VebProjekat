package vezbe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Dostavljac extends Korisnik implements Serializable {


    @OneToMany(fetch = FetchType.LAZY)
    private Set<Porudzbina> porudzbine = new HashSet<>();

    public Dostavljac() {
    }

    public Dostavljac(String korisnicko_ime, String lozinka, String ime, String prezime, Pol pol, Date datum_rodjenja, Uloga uloga, Set<Porudzbina> porudzbine) {
        super(korisnicko_ime, lozinka, ime, prezime, pol, datum_rodjenja, uloga);
        this.porudzbine = porudzbine;
    }

    public Set<Porudzbina> getPorudzbine() {
        return porudzbine;
    }

    public void setPorudzbine(Set<Porudzbina> porudzbine) {
        this.porudzbine = porudzbine;
    }

    @Override
    public String toString() {
        return  super.toString()+ "Dostavljac{" +
                "porudzbine=" + porudzbine +
                '}';
    }
}
