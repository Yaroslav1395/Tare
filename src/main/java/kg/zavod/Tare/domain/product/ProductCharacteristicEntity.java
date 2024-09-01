package kg.zavod.Tare.domain.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_characteristics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCharacteristicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    @ManyToOne
    @JoinColumn(name = "characteristic_id")
    private CharacteristicEntity characteristic;
    @Column(name = "val")
    private Integer value;
}
