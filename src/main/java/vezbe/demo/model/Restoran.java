package vezbe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String tipRestorana;

    @JsonIgnore // dodao
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Artikal> artikli = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    private Lokacija lokacija;

    private Set<Komentar> komentari = new HashSet<>();

    public Restoran() {
    }

    public Restoran(String naziv, String tip_restorana, Set<Artikal> artikli, Lokacija lokacija) {
        this.naziv = naziv;
        this.tipRestorana = tip_restorana;
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

    public String getTipRestorana() {
        return tipRestorana;
    }

    public void setTipRestorana(String tip_restorana) {
        this.tipRestorana = tip_restorana;
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

    public Set<Komentar> getKomentari() {
        return komentari;
    }

    public void setKomentari(Set<Komentar> komentari) {
        this.komentari = komentari;
    }

    public void dodajKomentar(Komentar komentar){
        komentari.add(komentar);
    }

    @Override
    public String toString() {
        return "Restoran{" +
                " naziv='" + naziv + '\'' +
                ", tip_restorana=" + tipRestorana +
                ", artikli=" + artikli +
                ", lokacija=" + lokacija +
                '}';
    }
}
