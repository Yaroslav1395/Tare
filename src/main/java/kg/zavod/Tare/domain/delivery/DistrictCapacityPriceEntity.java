package kg.zavod.Tare.domain.delivery;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс описывает стоимость доставки для определенного объема в конкретный район
 */
@Entity
@Table(name = "delivery_prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictCapacityPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer price;
    @ManyToOne
    @JoinColumn(name = "capacity_id")
    private CapacityEntity capacity;
    @ManyToOne
    @JoinColumn(name = "district_id")
    private DistrictEntity district;
}
