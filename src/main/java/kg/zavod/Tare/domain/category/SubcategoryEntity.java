package kg.zavod.Tare.domain.category;

import jakarta.persistence.*;
import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.DeleteException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "subcategories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubcategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "subcategory_image")
    private String subcategoryImage;
    @Column(name = "subcategory_image_name")
    private String subcategoryImageName;
    @Column(name = "subcategory_image_type")
    @Enumerated(EnumType.STRING)
    private ImageType subcategoryImageType;
    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private CategoryEntity category;
    @OneToMany(mappedBy = "subcategory", fetch = FetchType.LAZY)
    private List<ProductEntity> products;

    @PreRemove
    private void checkUsersBeforeRemove() throws DeleteException {
        if (!products.isEmpty()) {
            throw new DeleteException("Подкатегория не может быть удалена, так как с ней связаны продукты.");
        }
    }
}
