package vezbe.demo.dto;

import vezbe.demo.model.TipKupca;

public class KupacDto {

    public TipKupca tip_kupca;
    public int broj_bodova;

    public KupacDto() {
    }

    public KupacDto(TipKupca tip_kupca, int broj_bodova) {
        this.tip_kupca = tip_kupca;
        this.broj_bodova = broj_bodova;
    }

    public TipKupca getTip_kupca() {
        return tip_kupca;
    }

    public void setTip_kupca(TipKupca tip_kupca) {
        this.tip_kupca = tip_kupca;
    }

    public int getBroj_bodova() {
        return broj_bodova;
    }

    public void setBroj_bodova(int broj_bodova) {
        this.broj_bodova = broj_bodova;
    }

    @Override
    public String toString() {
        return "KupacDto{" +
                "tip_kupca=" + tip_kupca +
                ", broj_bodova=" + broj_bodova +
                '}';
    }
}
