package kg.zavod.Tare.domain.delivery;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс описывает район
 */
@Entity
@Table(name = "district")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "division_id")
    private DivisionEntity division;
    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DistrictCapacityPriceEntity> districtCapacities;
}
