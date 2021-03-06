package bg.softuni.mobilelele.repository;


import bg.softuni.mobilelele.model.entity.BrandEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    Optional<BrandEntity> findByName(String name);
}
