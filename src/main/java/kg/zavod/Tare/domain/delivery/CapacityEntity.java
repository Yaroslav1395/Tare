package kg.zavod.Tare.domain.delivery;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс описывает объем доставки
 */
@Entity
@Table(name = "capacity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CapacityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "capacity_from")
    private Integer capacityFrom;
    @Column(name = "capacity_to")
    private Integer capacityTo;
    @OneToMany(mappedBy = "capacity", fetch = FetchType.LAZY)
    private List<DistrictCapacityPriceEntity> districtsPrices;
}
