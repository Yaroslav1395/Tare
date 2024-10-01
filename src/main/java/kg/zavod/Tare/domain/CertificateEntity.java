package kg.zavod.Tare.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "certificates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @Column(name = "certificate_image")
    private String certificateImage;
    @Column(name = "certificate_image_name")
    private String certificateImageName;
    @Column(name = "certificate_image_type")
    private String certificateImageType;
}
