package vezbe.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Lokacija implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double duzina;
    @Column
    private double sirina;
    @Column
    private String adresa;

    public Lokacija() {
    }

    public Lokacija(double duzina, double sirina, String adresa) {
        this.duzina = duzina;
        this.sirina = sirina;
        this.adresa = adresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDuzina() {
        return duzina;
    }

    public void setDuzina(double duzina) {
        this.duzina = duzina;
    }

    public double getSirina() {
        return sirina;
    }

    public void setSirina(double sirina) {
        this.sirina = sirina;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    @Override
    public String toString() {
        return "Lokacija{" +
                "duzina=" + duzina +
                ", sirina=" + sirina +
                ", adresa='" + adresa + '\'' +
                '}';
    }
}
