package vezbe.demo.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vezbe.demo.model.Korisnik;
import vezbe.demo.model.Kupac;
import vezbe.demo.model.TipKupca;
import vezbe.demo.repository.TipKupcaRepository;

import javax.persistence.Column;
import java.util.Date;


public class RegisterDto {
    private String korisnickoIme;
    private String lozinka;
    private String ime;
    private String prezime;
    private Korisnik.Pol pol;  // dodao
    private Date datum_rodjenja;  // dodao

    public RegisterDto() {
    }

    public RegisterDto(String korisnicko_ime, String lozinka, String ime, String prezime, Korisnik.Pol pol, Date datum_rodjenja) {
        this.korisnickoIme = korisnicko_ime;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.pol = pol;
        this.datum_rodjenja = datum_rodjenja;
    }

    public Korisnik.Pol getPol() {
        return pol;
    }

    public void setPol(Korisnik.Pol pol) {
        this.pol = pol;
    }

    public Date getDatum_rodjenja() {
        return datum_rodjenja;
    }

    public void setDatum_rodjenja(Date datum_rodjenja) {
        this.datum_rodjenja = datum_rodjenja;
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

    public Kupac ToKupac(){
        return new Kupac(korisnickoIme, lozinka, ime, prezime, pol, datum_rodjenja, Korisnik.Uloga.Kupac, null, 0, null);  // dodao
    }

}
