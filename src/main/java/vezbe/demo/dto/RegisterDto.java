package vezbe.demo.dto;

import vezbe.demo.model.Kupac;

public class RegisterDto {

    private String korisnicko_ime;
    private String lozinka;
    private String ime;
    private String prezime;

    public RegisterDto() {
    }

    public RegisterDto(String korisnicko_ime, String lozinka, String ime, String prezime) {
        this.korisnicko_ime = korisnicko_ime;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
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


    public Kupac ToKupac(){
        return new Kupac(korisnicko_ime, lozinka, ime, prezime);
    }

}
