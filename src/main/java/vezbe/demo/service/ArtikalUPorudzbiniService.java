package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.ArtikalUPorudzbini;
import vezbe.demo.repository.ArtikalUPorudzbiniRepository;

@Service
public class ArtikalUPorudzbiniService {

    @Autowired
    //ArtikalUPorudzbiniService artikalUPorudzbiniService;
    ArtikalUPorudzbiniRepository artikalUPorudzbiniRepository; // dodo

    public void save(ArtikalUPorudzbini ap){
        artikalUPorudzbiniRepository.save(ap);
    }

    public void delete(ArtikalUPorudzbini ap) { artikalUPorudzbiniRepository.delete(ap);}
}
