package vezbe.demo.dto;

import vezbe.demo.model.Artikal;

import javax.persistence.Column;

public class ArtikalDto {

    private String naziv;

    private double cena;

    private Artikal.Tip tip;

    private String slika; // ovo ne znam kako da implementiram

    private String opis;

    private int kolicina;

    public ArtikalDto() {
    }

    public ArtikalDto(String naziv, double cena, Artikal.Tip tip, String slika, String opis, int kolicina) {
        this.naziv = naziv;
        this.cena = cena;
        this.tip = tip;
        this.slika = slika;
        this.opis = opis;
        this.kolicina = kolicina;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Artikal.Tip getTip() {
        return tip;
    }

    public void setTip(Artikal.Tip tip) {
        this.tip = tip;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
}
