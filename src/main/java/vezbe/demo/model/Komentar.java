package vezbe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Enabled;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Komentar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Kupac kupac;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Restoran restoran;
    @Column
    private String tekst_komentara;
    @Column
    private int ocena; // pri pisanju logike aplikacije implementiracemo proveru vrednosti ocene (1-5)

    public Komentar() {
    }

    public Komentar(Kupac kupac, Restoran restoran, String tekst_komentara, int ocena) {
        this.kupac = kupac;
        this.restoran = restoran;
        this.tekst_komentara = tekst_komentara;
        this.ocena = ocena;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kupac getKupac() {
        return kupac;
    }

    public void setKupac(Kupac kupac) {
        this.kupac = kupac;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public String getTekst_komentara() {
        return tekst_komentara;
    }

    public void setTekst_komentara(String tekst_komentara) {
        this.tekst_komentara = tekst_komentara;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    @Override
    public String toString() {
        return "Komentar{" +
                "kupac=" + kupac +
                ", restoran=" + restoran +
                ", tekst_komentara='" + tekst_komentara + '\'' +
                ", ocena=" + ocena +
                '}';
    }
}
