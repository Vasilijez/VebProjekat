package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.Restoran;

import java.util.List;
import java.util.Optional;


@Repository
public interface RestoranRepository extends JpaRepository<Restoran, Long> {

    Restoran findByNaziv(String naziv);

    @Query(value = "SELECT * FROM restoran r WHERE LOWER(r.naziv) LIKE LOWER(CONCAT('%', ?1,'%'))", nativeQuery = true)
    List<Restoran> findByNazivLikeCaseInsensitive(String naziv);

    @Query(value = "SELECT * FROM restoran r WHERE LOWER(r.tip_restorana) LIKE LOWER(CONCAT('%', ?1,'%'))", nativeQuery = true)
    List<Restoran> findByTipLikeCaseInsensitive(String tip);

    Optional<Restoran> findById(Long id);

    void delete(Restoran restoran);

}
