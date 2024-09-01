package kg.zavod.Tare.dto.product.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Класс необходим для пагинации продуктов")
public class PageForProduct {
    @Schema(description = "Количество страниц")
    private Integer totalPages;
    @Schema(description = "Общее количество элементов")
    private Long totalElements;
    @Schema(description = "Количество элементов на странице")
    private Integer productsAmount;
    @Schema(description = "Продукты")
    private List<ProductDto> products;

    public static PageForProduct buildPageForProduct(List<ProductDto> products, Integer totalPages, Long totalElements, Integer productAmount){
        return PageForProduct.builder()
                .products(products)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .productsAmount(productAmount)
                .build();
    }
}
