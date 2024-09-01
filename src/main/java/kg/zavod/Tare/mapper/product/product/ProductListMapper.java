package kg.zavod.Tare.mapper.product.product;

import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.product.product.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ProductListMapper {
    List<ProductDto> mapToProductDtoList(List<ProductEntity> products);
}
