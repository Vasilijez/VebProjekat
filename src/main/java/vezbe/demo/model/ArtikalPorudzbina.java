package vezbe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class ArtikalPorudzbina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "artikal_id")
    private Artikal artikal;

    @JsonIgnore // dodao
    @ManyToOne
    @JoinColumn(name = "porudzbina_id")
    private Porudzbina porudzbina;

    private int kolicina;

    public ArtikalPorudzbina() {
    }

    public ArtikalPorudzbina(Artikal artikal, Porudzbina porudzbina, int kolicina) {
        this.artikal = artikal;
        this.porudzbina = porudzbina;
        this.kolicina = kolicina;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    public Porudzbina getPorudzbina() {
        return porudzbina;
    }

    public void setPorudzbina(Porudzbina porudzbina) {
        this.porudzbina = porudzbina;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
}
