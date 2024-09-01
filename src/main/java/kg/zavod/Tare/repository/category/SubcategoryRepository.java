package kg.zavod.Tare.repository.category;

import kg.zavod.Tare.domain.category.SubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Integer> {
}
