package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Artikal;
import vezbe.demo.model.Porudzbina;
import vezbe.demo.model.Restoran;

import java.util.List;

@Repository
public interface PorudzbinaRepository extends JpaRepository<Porudzbina, String> {

    public List<Porudzbina> findAllByRestoran(Restoran restoran);

}
