package vezbe.demo.model;

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
    @ManyToMany
    @JoinTable(name = "porudzbine_artikli",
            joinColumns = { @JoinColumn(name = "porudzbina_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "artikal_id", referencedColumnName = "id") })
    private Set<Artikal> artikli = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)   // fetch, cascade
    private Restoran restoran;
    @Column
    private Date datum_vreme;
    @Column
    private double cena;
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

    public Porudzbina(Set<Artikal> artikli, Restoran restoran, Date datum_vreme, double cena, Kupac kupac, Status status) {
        this.artikli = artikli;
        this.restoran = restoran;
        this.datum_vreme = datum_vreme;
        this.cena = cena;
        this.kupac = kupac;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<Artikal> getArtikli() {
        return artikli;
    }

    public void setArtikli(Set<Artikal> artikli) {
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
