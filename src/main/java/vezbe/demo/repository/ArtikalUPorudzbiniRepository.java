package vezbe.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vezbe.demo.model.ArtikalUPorudzbini;

@Repository
public interface ArtikalUPorudzbiniRepository extends JpaRepository<ArtikalUPorudzbini, Long> {
}
