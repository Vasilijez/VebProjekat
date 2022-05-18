package vezbe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Menadzer extends Korisnik implements Serializable {



    @OneToOne(fetch = FetchType.LAZY)
    private Restoran restoran;

    public Menadzer() {
    }

    public Menadzer(String korisnicko_ime, String lozinka, String ime, String prezime, Pol pol, Date datum_rodjenja, Restoran restoran, Uloga uloga) {
        super(korisnicko_ime, lozinka, ime, prezime, pol, datum_rodjenja, uloga);
        this.restoran = restoran;
    }


    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    @Override
    public String toString() {
        return  super.toString() + "Menadzer{" +
                " restoran=" + restoran +
                '}';
    }
}
