package vezbe.demo.dto;

import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;

import java.util.List;

public class MenadzerDto {

    private Restoran restoran;

    private List<Porudzbina> porudzbinaList;

    public MenadzerDto() {
    }

    public MenadzerDto(Restoran restoran, List<Porudzbina> porudzbinaList) {
        this.restoran = restoran;
        this.porudzbinaList = porudzbinaList;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public List<Porudzbina> getPorudzbinaList() {
        return porudzbinaList;
    }

    public void setPorudzbinaList(List<Porudzbina> porudzbinaList) {
        this.porudzbinaList = porudzbinaList;
    }
}
