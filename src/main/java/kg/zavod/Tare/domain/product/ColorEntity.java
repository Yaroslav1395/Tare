package kg.zavod.Tare.domain.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "colors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "hex_code")
    private String hexCode;
    private String name;
    @OneToMany(mappedBy = "color", fetch = FetchType.LAZY)
    private List<ImageEntity> images;
}
