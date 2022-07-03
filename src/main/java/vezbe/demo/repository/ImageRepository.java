package vezbe.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vezbe.demo.model.Image;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
}
