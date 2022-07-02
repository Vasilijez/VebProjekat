package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;
import vezbe.demo.repository.PorudzbinaRepository;

import java.util.List;

@Service
public class PorudzbinaService {

    @Autowired
    PorudzbinaRepository porudzbinaRepository;

    public List<Porudzbina> findAllByRestoran(Restoran restoran) {
        return porudzbinaRepository.findAllByRestoran(restoran);
    }

    public void delete(Porudzbina porudzbina) {
        porudzbinaRepository.delete(porudzbina);
    }


}
