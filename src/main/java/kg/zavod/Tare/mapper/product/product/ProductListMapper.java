package kg.zavod.Tare.mapper.product.product;

import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.product.product.ProductForAdminDto;
import kg.zavod.Tare.dto.product.product.ProductForHomeDto;
import kg.zavod.Tare.dto.product.product.ProductForUserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ProductListMapper {
    /**
     * Метод позволяет преобразовать список сущностей продукта в список DTO. Используется для клиента MVC
     * @param products - список сущностей продукта
     * @return - список DTO продукта
     */
    List<ProductForUserDto> mapToProductForUserDtoList(List<ProductEntity> products);

    /**
     * Метод позволяет преобразовать список сущностей продукта в DTO.
     * Используется для админки MVC
     * @param products - список сущностей продукта
     * @return - список DTO продукта
     */
    List<ProductForAdminDto> mapToProductForAdminDtoMvc(List<ProductEntity> products);

    /**
     * Метод позволяет преобразовать список сущностей продукта в список DTO. Используется на главной
     * странице MVC
     * @param products - список сущностей продукта
     * @return - список DTO продукта
     */
    List<ProductForHomeDto> mapToProductForHomeDtoList(List<ProductEntity> products);
}
