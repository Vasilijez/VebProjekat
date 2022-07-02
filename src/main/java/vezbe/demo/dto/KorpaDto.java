package vezbe.demo.dto;

import java.util.*;

public class KorpaDto {

    private List<ArtikalUKorpiDto> artikli = new ArrayList<ArtikalUKorpiDto>() {};
    private double ukupnaCena;

    public KorpaDto(List<ArtikalUKorpiDto> artikli, double ukupnaCena) {
        this.artikli = artikli;
        this.ukupnaCena = ukupnaCena;
    }

    public List<ArtikalUKorpiDto> getArtikli() {
        return artikli;
    }

    public void setArtikli(List<ArtikalUKorpiDto> artikli) {
        this.artikli = artikli;
    }

    public double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }
}
