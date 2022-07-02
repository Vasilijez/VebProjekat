package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.Komentar;
import vezbe.demo.model.Restoran;
import vezbe.demo.repository.KomentarRepository;

import java.util.List;

@Service
public class KomentarService {

    @Autowired
    KomentarRepository komentarRepository;

    public List<Komentar> findAllByRestoran(Restoran restoran) {
        return komentarRepository.findAllByRestoran(restoran);
    }

    public void delete(Komentar komentar) {
        komentarRepository.delete(komentar);
    }

    public double averageMark(Restoran restoran) {
        List<Komentar> komentari = findAllByRestoran(restoran);
        double ocena = 0;
        int i = 0;

        for (Komentar komentar : komentari) {
            i++;
            ocena += komentar.getOcena();
        }

        return ocena/i;
    }

}
