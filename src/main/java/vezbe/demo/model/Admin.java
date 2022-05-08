package vezbe.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Admin extends Korisnik implements Serializable {

    public Admin() {
    }

    public Admin(String korisnicko_ime, String lozinka, String ime, String prezime, Pol p, Date datum_rodjenja, Uloga uloga) {
        super(korisnicko_ime, lozinka, ime, prezime, p, datum_rodjenja, uloga);
    }



}
