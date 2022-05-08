package vezbe.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Pol {
        MUSKO,
        ZENSKO
    }
    public enum Uloga {
        Menadzer,
        Admin,
        Kupac,
        Dostavljac
    }

    @Column
    private String korisnicko_ime;
    @Column
    private String lozinka;
    @Column
    private String ime;
    @Column
    private String prezime;
    @Column
    private Pol p;
    @Column
    private Date datum_rodjenja;
    @Column
    private Uloga uloga;

    public Korisnik() {
    }

    public Korisnik(String korisnicko_ime, String lozinka, String ime, String prezime, Pol p, Date datum_rodjenja, Uloga uloga) {
        this.korisnicko_ime = korisnicko_ime;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.p = p;
        this.datum_rodjenja = datum_rodjenja;
        this.uloga = uloga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Pol getP() {
        return p;
    }

    public void setP(Pol p) {
        this.p = p;
    }

    public Date getDatum_rodjenja() {
        return datum_rodjenja;
    }

    public void setDatum_rodjenja(Date datum_rodjenja) {
        this.datum_rodjenja = datum_rodjenja;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "korisnicko_ime='" + korisnicko_ime + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", p=" + p +
                ", datum_rodjenja=" + datum_rodjenja +
                ", uloga=" + uloga +
                '}';
    }
}
