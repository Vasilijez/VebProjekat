package vezbe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Porudzbina implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "porudzbina", cascade = CascadeType.ALL)
    private Set<ArtikalPorudzbina> artikli;

    @ManyToOne(fetch = FetchType.LAZY)   // fetch, cascade
    private Restoran restoran;

    @Column
    private Date datum_vreme;

    @Column
    private double cena;

    @JsonIgnore // dodao
    @ManyToOne(fetch = FetchType.LAZY)
    private Kupac kupac;

    public enum Status {
        obrada,
        u_pripremi,
        ceka_dostavljaca,
        u_transportu,
        dostavljena,
        otkazana
    }
    @Column
    private Status status;

    public Porudzbina() {
    }

    public Porudzbina(Restoran restoran, Kupac kupac, Status status) {
        this.restoran = restoran;
        this.kupac = kupac;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<ArtikalPorudzbina> getArtikli() {
        return artikli;
    }

    public void setArtikli(Set<ArtikalPorudzbina> artikli) {
        this.artikli = artikli;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public Date getDatum_vreme() {
        return datum_vreme;
    }

    public void setDatum_vreme(Date datum_vreme) {
        this.datum_vreme = datum_vreme;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Kupac getKupac() {
        return kupac;
    }

    public void setKupac(Kupac kupac) {
        this.kupac = kupac;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Porudzbina{" +
                " artikli=" + artikli +
                ", restoran=" + restoran +
                ", datum_vreme=" + datum_vreme +
                ", cena=" + cena +
                ", kupac='" + kupac + '\'' +
                ", status=" + status +
                '}';
    }
}
