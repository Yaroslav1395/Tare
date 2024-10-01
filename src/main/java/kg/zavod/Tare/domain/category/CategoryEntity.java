package kg.zavod.Tare.domain.category;

import jakarta.persistence.*;
import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.dto.exception.DeleteException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "category_image")
    private String categoryImage;
    @Column(name = "category_image_name")
    private String categoryImageName;
    @Column(name = "category_image_type")
    @Enumerated(EnumType.STRING)
    private ImageType imageType;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<SubcategoryEntity> subcategories;

    @PreRemove
    private void checkUsersBeforeRemove() throws DeleteException {
        if (!subcategories.isEmpty()) {
            throw new DeleteException("Категория не может быть удалена, так как с ней связаны подкатегории.");
        }
    }
}
