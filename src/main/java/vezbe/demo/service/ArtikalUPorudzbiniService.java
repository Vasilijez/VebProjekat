package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.ArtikalUPorudzbini;

@Service
public class ArtikalUPorudzbiniService {

    @Autowired
    ArtikalUPorudzbiniService artikalUPorudzbiniService;

    public void save(ArtikalUPorudzbini ap){
        artikalUPorudzbiniService.save(ap);
    }

    public void delete(ArtikalUPorudzbini ap) { artikalUPorudzbiniService.delete(ap);}
}
