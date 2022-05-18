package vezbe.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class TipKupca implements Serializable {

    public enum Tip {
        Zlatni,
        Srebrni,
        Bronzani
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Tip ime;
    @Column
    private double popust;
    @Column
    private int broj_bodova;

    public TipKupca() {
    }

    public TipKupca(Tip ime, double popust, int broj_bodova) {
        this.ime = ime;
        this.popust = popust;
        this.broj_bodova = broj_bodova;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tip getIme() {
        return ime;
    }

    public void setIme(Tip ime) {
        this.ime = ime;
    }

    public double getPopust() {
        return popust;
    }

    public void setPopust(double popust) {
        this.popust = popust;
    }

    public double getBroj_bodova() {
        return broj_bodova;
    }

    public void setBroj_bodova(int broj_bodova) {
        this.broj_bodova = broj_bodova;
    }

    @Override
    public String toString() {
        return "TipKupca{" +
                "ime=" + ime +
                ", popust=" + popust +
                ", broj_bodova=" + broj_bodova +
                '}';
    }
}
