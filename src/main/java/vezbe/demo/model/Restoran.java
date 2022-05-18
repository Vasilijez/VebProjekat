package vezbe.demo.model;

import vezbe.demo.model.Artikal;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Restoran implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String naziv;

    @Column
    private String tip_restorana;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Artikal> artikli = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    private Lokacija lokacija;

    public Restoran() {
    }

    public Restoran(String naziv, String tip_restorana, Set<Artikal> artikli, Lokacija lokacija) {
        this.naziv = naziv;
        this.tip_restorana = tip_restorana;
        this.artikli = artikli;
        this.lokacija = lokacija;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTip_restorana() {
        return tip_restorana;
    }

    public void setTip_restorana(String tip_restorana) {
        this.tip_restorana = tip_restorana;
    }

    public Set<Artikal> getArtikli() {
        return artikli;
    }

    public void setArtikli(Set<Artikal> artikli) {
        this.artikli = artikli;
    }

    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }

    @Override
    public String toString() {
        return "Restoran{" +
                " naziv='" + naziv + '\'' +
                ", tip_restorana=" + tip_restorana +
                ", artikli=" + artikli +
                ", lokacija=" + lokacija +
                '}';
    }
}
