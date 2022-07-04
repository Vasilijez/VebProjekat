package vezbe.demo.dto;

import vezbe.demo.model.Korisnik;

import javax.persistence.Column;
import java.util.Date;

public class KorisnikDto {

    private String korisnickoIme;
    private String ime;
    private String prezime;
    private Korisnik.Pol p;
    private Date datum_rodjenja;
    private Korisnik.Uloga uloga;
    private String lozinka;

    public KorisnikDto() {
    }

    public KorisnikDto(String korisnickoIme, String ime, String prezime, Korisnik.Pol p, Date datum_rodjenja, Korisnik.Uloga uloga, String lozinka) {
        this.korisnickoIme = korisnickoIme;
        this.ime = ime;
        this.prezime = prezime;
        this.p = p;
        this.datum_rodjenja = datum_rodjenja;
        this.uloga = uloga;
        this.lozinka = lozinka;
    }

    public KorisnikDto(Korisnik korisnik) {
        this.korisnickoIme = korisnik.getKorisnickoIme();
        this.ime = korisnik.getIme();
        this.prezime = korisnik.getPrezime();
        this.p = korisnik.getP();
        this.datum_rodjenja = korisnik.getDatum_rodjenja();
        this.uloga = korisnik.getUloga();
        this.lozinka = korisnik.getLozinka();
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
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

    public Korisnik.Pol getP() {
        return p;
    }

    public void setP(Korisnik.Pol p) {
        this.p = p;
    }

    public Date getDatum_rodjenja() {
        return datum_rodjenja;
    }

    public void setDatum_rodjenja(Date datum_rodjenja) {
        this.datum_rodjenja = datum_rodjenja;
    }

    public Korisnik.Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Korisnik.Uloga uloga) {
        this.uloga = uloga;
    }



}
