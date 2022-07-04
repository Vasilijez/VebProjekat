package vezbe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vezbe.demo.model.ArtikalPorudzbina;
import vezbe.demo.repository.ArtikalPorudzbinaRepository;

@Service
public class ArtikalPorudzbinaService {
    @Autowired
    ArtikalPorudzbinaRepository artikalPorudzbinaRepository;


    public ArtikalPorudzbina findByArtikalId(Long id) {
        return artikalPorudzbinaRepository.findByArtikalId(id);
    }

    public void delete(ArtikalPorudzbina artikalPorudzbina) {
        artikalPorudzbinaRepository.delete(artikalPorudzbina);
    }
}
