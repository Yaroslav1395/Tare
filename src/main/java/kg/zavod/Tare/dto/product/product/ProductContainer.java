package kg.zavod.Tare.dto.product.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс необходим  для сохранения продукта контейнер")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductContainer {
    private ProductForSaveDto productForSaveDto;
    private MultipartFile multipartFile;
}
