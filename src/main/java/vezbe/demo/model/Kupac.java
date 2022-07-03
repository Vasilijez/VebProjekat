package vezbe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Kupac extends Korisnik implements Serializable {



    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Porudzbina> istorija_porudzbina = new HashSet<>();

    @Column
    private int broj_bodova;

    @ManyToOne(fetch = FetchType.LAZY)
    private TipKupca tip;

    public Kupac() {
    }

    public Kupac(String korisnicko_ime, String lozinka, String ime, String prezime) {
        super(korisnicko_ime, lozinka, ime, prezime);
    }

    public Kupac(String korisnicko_ime, String lozinka, String ime, String prezime, Pol pol, Date datum_rodjenja, Uloga uloga, Set<Porudzbina> istorija_porudzbina, int broj_bodova, TipKupca tip) {
        super(korisnicko_ime, lozinka, ime, prezime, pol, datum_rodjenja, uloga);
        this.istorija_porudzbina = istorija_porudzbina;
        this.broj_bodova = broj_bodova;
        this.tip = tip;
    }

    public Set<Porudzbina> getIstorija_porudzbina() {
        return istorija_porudzbina;
    }

    public void setIstorija_porudzbina(Set<Porudzbina> istorija_porudzbina) {
        this.istorija_porudzbina = istorija_porudzbina;
    }

    public int getBroj_bodova() {
        return broj_bodova;
    }

    public void setBroj_bodova(int broj_bodova) {
        this.broj_bodova = broj_bodova;
    }

    public TipKupca getTip() {
        return tip;
    }

    public void setTip(TipKupca tip) {
        this.tip = tip;
    }

    public void dodajPorudzbinu(Porudzbina porudzbina) {
        this.istorija_porudzbina.add(porudzbina);
    }

    public Porudzbina korpa() {
        for(Porudzbina p : istorija_porudzbina) {
            if (p.getStatus().equals(Porudzbina.Status.u_korpi))
                return p;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + "Kupac{" +
                "istorija_porudzbina=" + istorija_porudzbina +
                ", broj_bodova=" + broj_bodova +
                ", tip=" + tip +
                '}';
    }
}
