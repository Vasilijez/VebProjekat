package vezbe.demo.dto;

import vezbe.demo.model.Korisnik;

import java.util.Date;

public class KorisnikAzuriranDto {

    private String korisnickoIme;
    private String lozinka;
    private String ime;
    private String prezime;
    private Korisnik.Pol p;
    private Date datum_rodjenja;

    public KorisnikAzuriranDto() {
    }


    public KorisnikAzuriranDto(String korisnickoIme, String lozinka, String ime, String prezime, Korisnik.Pol p, Date datum_rodjenja) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.p = p;
        this.datum_rodjenja = datum_rodjenja;
    }

    public KorisnikAzuriranDto(Korisnik korisnik) {
        this.korisnickoIme = korisnik.getKorisnickoIme();
        this.lozinka = korisnik.getLozinka();
        this.ime = korisnik.getIme();
        this.prezime = korisnik.getPrezime();
        this.p = korisnik.getP();
        this.datum_rodjenja = korisnik.getDatum_rodjenja();
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
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


}
