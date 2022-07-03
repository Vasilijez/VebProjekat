package vezbe.demo.dto;

import vezbe.demo.model.Restoran;

public class KomentarDto {

    private Restoran restoran;
    private String tekst;
    private int ocena;

    public KomentarDto() {
    }

    public KomentarDto(Restoran restoran, String tekst, int ocena) {

        this.restoran = restoran;
        this.tekst = tekst;
        this.ocena = ocena;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    @Override
    public String toString() {
        return "KomentarDto{" +
                "restoran=" + restoran +
                ", tekst='" + tekst + '\'' +
                ", ocena=" + ocena +
                '}';
    }
}
