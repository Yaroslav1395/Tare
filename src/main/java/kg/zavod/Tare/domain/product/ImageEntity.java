package kg.zavod.Tare.domain.product;

import jakarta.persistence.*;
import kg.zavod.Tare.domain.ImageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "product_image")
    private String productImage;
    @Column(name = "product_image_type")
    @Enumerated(EnumType.STRING)
    private ImageType imageType;
    @ManyToOne
    @JoinColumn(name = "color_id")
    private ColorEntity color;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
