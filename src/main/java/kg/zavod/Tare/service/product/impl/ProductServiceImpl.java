package kg.zavod.Tare.service.product.impl;

import kg.zavod.Tare.domain.category.SubcategoryEntity;
import kg.zavod.Tare.domain.product.ProductCharacteristicEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.characteristicValue.CharacteristicValueDto;
import kg.zavod.Tare.dto.product.image.ImageDto;
import kg.zavod.Tare.dto.product.product.*;
import kg.zavod.Tare.dto.product.product.mvc.ProductForAdminDto;
import kg.zavod.Tare.dto.product.product.mvc.ProductForHomeDto;
import kg.zavod.Tare.dto.product.product.mvc.ProductForSaveAdminDto;
import kg.zavod.Tare.mapper.product.product.ProductListMapper;
import kg.zavod.Tare.mapper.product.product.ProductMapper;
import kg.zavod.Tare.repository.category.SubcategoryRepository;
import kg.zavod.Tare.repository.product.ProductRepository;
import kg.zavod.Tare.service.product.CharacteristicValueService;
import kg.zavod.Tare.service.product.ImageService;
import kg.zavod.Tare.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ImageService imageService;
    private final CharacteristicValueService characteristicValueService;
    private final ProductMapper productMapper;
    private final ProductListMapper productListMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * Метод позволяет получить все продукты. Используется в админке MVC
     * @return - список продуктов
     * @throws EntitiesNotFoundException - в случае если продукты не найдены
     */
    @Override
    public List<ProductForAdminDto> getProductsForAdmin() throws EntitiesNotFoundException {
        logger.info("Попытка получения продуктов для админки MVC");
        List<ProductEntity> products = productRepository.findAll();
        if(products.isEmpty()) throw new EntitiesNotFoundException("Продуктов не найдено");
        List<ProductForAdminDto> productsDto = productListMapper.mapToProductForAdminDtoMvc(products);
        productsDto.sort(Comparator.comparing(ProductForAdminDto::getSubcategory));
        return productListMapper.mapToProductForAdminDtoMvc(products);
    }

    /**
     * Метод позволяет сохранить продукт. Используется в админке MVC
     * @param productForSaveAdminDto - продукт для сохранения
     * @throws EntityNotFoundException - в случае если подкатегория, цвет для картинки или характеристика не будут найдены
     */
    @Override
    @Transactional
    public void saveProductAdminMvc(ProductForSaveAdminDto productForSaveAdminDto) throws EntityNotFoundException, EntitiesNotFoundException, IOException {
        logger.info("Попытка сохранения продукта");
        SubcategoryEntity subcategory = subcategoryRepository.findById(productForSaveAdminDto.getSubcategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено по id подкатегории для продукта"));
        ProductEntity productForSave = productMapper.mapToProductEntity(productForSaveAdminDto, subcategory);
        ProductEntity savedProduct = productRepository.save(productForSave);
        imageService.saveImagesAdminMvc(productForSaveAdminDto.getImages(), savedProduct);
        characteristicValueService.saveCharacteristicsValueMvc(productForSaveAdminDto.getCharacteristicsValue(), savedProduct);
    }


    @Override
    @Transactional
    public Map<Integer, List<ProductForHomeDto>> getProductsForHomePage() {
        Map<Integer, List<ProductForHomeDto>> productsDtoByCategoryId = new HashMap<>();
        List<ProductEntity> p1 = productRepository.findAllBySubcategoryId(3);
        List<ProductEntity> p2 = productRepository.findAllBySubcategoryId(6);
        List<ProductForHomeDto> firsProducts = productListMapper.mapToProductForHomeDtoList(p1);
        List<ProductForHomeDto> secondProducts = productListMapper.mapToProductForHomeDtoList(p2);
        productsDtoByCategoryId.put(1, firsProducts);
        productsDtoByCategoryId.put(2, secondProducts);
        return productsDtoByCategoryId;
    }

    /**
     * Метод позволяет получить продукт по id
     * @param id - id продукта
     * @return - найденный продукт
     * @throws EntityNotFoundException - в случае если продукт не будет найден
     */
    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(Integer id) throws EntityNotFoundException {
        logger.info("Попытка поиска продукта по id");
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не найдено продукта по id"));
        return productMapper.mapToProductDto(product);
    }

    /**
     * Метод позволяет получить продукты входящие в подкатегорию по ее id c пагинацией
     * @param subcategoryId - id подкатегории
     * @param pageable - пагинация
     * @return - продукты с пагинацией
     * @throws EntitiesNotFoundException - в случае если ничего не будет найдено
     */
    @Override
    @Transactional(readOnly = true)
    public PageForProduct getProductsBySubcategoryId(Integer subcategoryId, Pageable pageable) throws EntitiesNotFoundException {
        logger.info("Попытка поиска продуктов в подкатегории");
        Page<ProductEntity> products = productRepository.findAllBySubcategoryId(subcategoryId, pageable);
        List<ProductEntity> productEntities = products.get().toList();
        if(productEntities.isEmpty()) throw new EntitiesNotFoundException("Не найдено продуктов");
        List<ProductDto> productsDto = productListMapper.mapToProductDtoList(productEntities);
        return PageForProduct.buildPageForProduct(productsDto, products.getTotalPages(), products.getTotalElements(), products.getNumberOfElements());
    }

    /**
     * Метод позволяет сохранить продукт
     * @param productForSaveDto - продукт для сохранения
     * @return - сохраненный продукт
     * @throws EntityNotFoundException - в случае если подкатегория, цвет или характеристика не будет найден
     * @throws EntitiesNotFoundException - в случае если ни одного цвета или характеристик не найдено
     */
    @Override
    @Transactional
    public ProductDto saveProduct(ProductForSaveDto productForSaveDto) throws EntityNotFoundException, EntitiesNotFoundException {
        logger.info("Попытка создания продукта");
        logger.info("Поиск подкатегории для продукта");
        SubcategoryEntity subcategory = subcategoryRepository.findById(productForSaveDto.getSubcategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено по id подкатегории для продукта"));
        ProductEntity productForSave = productMapper.mapToProductEntity(productForSaveDto, subcategory);
        ProductEntity savedProduct = productRepository.save(productForSave);
        List<ImageDto> savedImages = imageService.saveImages(productForSaveDto.getImages(), savedProduct);
        List<CharacteristicValueDto> savedCharacteristicsValue = characteristicValueService.saveCharacteristicsValueWithProduct(productForSaveDto.getCharacteristicsValues(), savedProduct);
        return productMapper.mapToProductDto(savedProduct, savedCharacteristicsValue, savedImages);
    }

    /**
     * Метод позволяет отредактировать продукт
     * @param productForUpdateDto - объект для редактирования
     * @return - отредактированный продукт
     * @throws EntityNotFoundException - в случае если не будет найдено данных для редактирования
     */
    @Override
    @Transactional
    public ProductDto updateProduct(ProductForUpdateDto productForUpdateDto) throws EntityNotFoundException {
        logger.info("Попытка редактирования продукта");
        logger.info("Поиск продукта по id");
        ProductEntity product = productRepository.findById(productForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено продукта по id"));
        List<ImageDto> updatedImages = imageService.updateImages(productForUpdateDto.getImages(), product);
        List<CharacteristicValueDto> updatedCharacteristicsValue  = characteristicValueService.updateCharacteristicValueWithProduct(
                productForUpdateDto.getCharacteristics(), product);
        logger.info("Поиск подкатегории");
        SubcategoryEntity subcategoryEntity = subcategoryRepository.findById(productForUpdateDto.getSubcategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Подкатегория не найдена"));
        ProductEntity productEntity = productMapper.updateProduct(productForUpdateDto, subcategoryEntity);
        ProductEntity updatedProduct = productRepository.save(productEntity);
        return productMapper.mapToProductDto(updatedProduct, updatedCharacteristicsValue, updatedImages);
    }

    /**
     * Метод позволяет удалить продукт
     * @param id - id продукта
     * @return - удален или нет
     */
    @Override
    public Boolean deleteProduct(Integer id) {
        logger.info("Попытка удаления продукта по id");
        productRepository.deleteById(id);
        return !productRepository.existsById(id);
    }

    /**
     * Метод позволяет получить ссылку на whats app с корзиной клиента
     * @param products - корзина клиента
     * @return - ссылка на whats app
     */
    @Override
    @Transactional
    public String getUrlForWhatsAppWithProductBasket(List<ProductFromBasketDro> products) {
        logger.info("Попытка формирования ссылки");
        String baseMessage = "https://wa.me/996505349113?text=Здравствуйте,%20хочу%20приобрести%20у%20вас%20товар";
        StringBuilder message = new StringBuilder(baseMessage);
        List<Integer> productIds = getProductIdFrom(products);
        List<ProductEntity> productsEntity = productRepository.findAllById(productIds);
        if (productsEntity.size() < products.size())  return baseMessage;
        Map<Integer, ProductEntity> productMap = productsEntity.stream()
                .collect(Collectors.toMap(ProductEntity::getId, productEntity -> productEntity));
        return buildUrl(products, productMap, baseMessage, message);
    }

    /**
     * Метод позволит получить id продуктов
     * @param products - продукты из корзины
     * @return - id продуктов
     */
    private List<Integer> getProductIdFrom(List<ProductFromBasketDro> products){
        return products.stream()
                .map(ProductFromBasketDro::getId)
                .toList();
    }

    /**
     * Метод вернет словарь характеристик, где ключ это название
     * @param characteristics - характеристики продукта
     * @return - словарь характеристик, название, значение
     */
    private Map<String, Integer> getCharacteristicsValue(List<ProductCharacteristicEntity> characteristics){
        Set<String> requiredCharacteristics = Set.of("Цена", "Объем");
        return characteristics.stream()
                .filter(c -> requiredCharacteristics.contains(c.getCharacteristic().getName()))
                .collect(Collectors.toMap(c -> c.getCharacteristic().getName(), ProductCharacteristicEntity::getValue));
    }

    /**
     * Метод построит url для перехода на whats app. В случае если не будет найдена ценовая характеристика для какого либо продукта
     * то вернется url c базовым сообщением.
     * @param products - продукты из корзины
     * @param productMap - продукты их базы
     * @param baseMessage - базовое сообщение
     * @param message - сообщение которое должно сформироваться для whats app
     * @return - url
     */
    private String buildUrl(List<ProductFromBasketDro> products, Map<Integer, ProductEntity> productMap, String baseMessage, StringBuilder message){
        int count = 1;
        int totalSum = 0;
        for (ProductFromBasketDro product : products) {
            ProductEntity productEntity = productMap.get(product.getId());
            if (productEntity == null) return baseMessage;
            Map<String, Integer> characteristicValues = getCharacteristicsValue(productEntity.getProductCharacteristics());
            Integer price = characteristicValues.get("Цена");
            if (price == null) return baseMessage;
            int positionPriceSum = price * product.getAmount();
            message.append("%0A").append(count).append(".%20").append(productEntity.getName()).append("%20Количество:%20")
                    .append(product.getAmount()).append("%0A%20").append("Цена:%20").append(price).append("%20Сумма:%20")
                    .append(positionPriceSum);
            count++;
            totalSum += positionPriceSum;
        }
        message.append("%0A%0AОбщая%20сумма:%20").append(totalSum);
        return message.toString().length() > 2000 ? baseMessage : message.toString();
    }
}
