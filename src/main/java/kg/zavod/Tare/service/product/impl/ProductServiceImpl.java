package kg.zavod.Tare.service.product.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.domain.category.SubcategoryEntity;
import kg.zavod.Tare.domain.product.ProductCharacteristicEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.product.product.*;
import kg.zavod.Tare.dto.product.product.mvc.*;
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
import org.springframework.beans.factory.annotation.Value;
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
    private final ObjectMapper objectMapper;
    @Value("${base.url.load}")
    private String baseUrlForLoad;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * Метод позволяет найти продукты для корзины. Принимает строку JSON и преобразует в список объектов
     * @param products - список продуктов как JSON
     * @return - список продуктов для корзины
     */
    @Override
    @Transactional
    public List<ProductForBasketDto> getProductsForBasket(String products) throws JsonProcessingException {
        logger.info("Попытка получения продуктов для корзины");
        List<ProductForOpenBasketDto> productList = parseJsonToProductForOpenBasket(products);
        List<Integer> productsId = getProductIdsFromProductForOpenBasket(productList);
        List<ProductEntity> productsEntity = productRepository.findAllById(productsId);
        List<ProductForUserDto> productsForUser = productListMapper.mapToProductForUserDtoList(productsEntity);
        setHostToImageLoad(productsForUser);
        return buildProductForBasketList(productList, productsForUser);
    }

    /**
     * Метод позволяет найти продукт по id для клиента MVC
     * @param productId - id продукта
     * @return - продукт
     * @throws EntityNotFoundException - в случае если продукт не найден
     */
    @Override
    @Transactional
    public ProductForUserDto getProductForUserById(Integer productId) throws EntityNotFoundException {
        logger.info("Попытка получения продукта по id для клиента MVC");
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден"));
        ProductForUserDto productDto = productMapper.mapToProductForUserDto(product);
        prepareProductForProductPage(product, productDto);
        return productDto;
    }

    /**
     * Метод позволяет получить продукты для клиента по id подкатегории. Используется в клиенте MVC
     * @param subcategoryId - id подкатегории
     * @return - список продуктов
     * @throws EntitiesNotFoundException - в случае если продукты не найдены
     */
    @Override
    @Transactional
    public List<ProductForUserDto> getProductsForUserBySubcategoryId(Integer subcategoryId) throws EntitiesNotFoundException {
        logger.info("Попытка получения продуктов по id подкатегории для клиента MVC");
        List<ProductEntity> products = productRepository.findAllBySubcategoryId(subcategoryId);
        if(products.isEmpty()) throw new EntitiesNotFoundException("Продуктов не найдено");
        List<ProductForUserDto> productsDto = productListMapper.mapToProductForUserDtoList(products);
        setHostToImageLoad(productsDto);
        return productsDto;
    }

    /**
     * Метод позволяет найти продукты по поисковому запросу. Используется клиентом
     * @param search - поисковая строка
     * @return - список продуктов
     * @throws EntitiesNotFoundException - в случае если продукты не найдены
     */
    @Override
    @Transactional
    public List<ProductForUserDto> getProductsBySearch(String search) throws EntitiesNotFoundException {
        logger.info("Попытка получения продуктов по поисковому запросу");
        List<ProductEntity> products = productRepository.findByNameContainingIgnoreCase(search);
        if(products.isEmpty()) throw new EntitiesNotFoundException("Продуктов не найдено");
        List<ProductForUserDto> productsDto = productListMapper.mapToProductForUserDtoList(products);
        setHostToImageLoad(productsDto);
        return productsDto;
    }

    /**
     * Метод позволяет получить все продукты. Используется в админке MVC
     * @return - список продуктов
     * @throws EntitiesNotFoundException - в случае если продукты не найдены
     */
    @Override
    @Transactional
    public List<ProductForAdminDto> getProductsForAdmin() throws EntitiesNotFoundException {
        logger.info("Попытка получения продуктов для админки MVC");
        List<ProductEntity> products = productRepository.findAll();
        if(products.isEmpty()) throw new EntitiesNotFoundException("Продуктов не найдено");
        List<ProductForAdminDto> productsDto = productListMapper.mapToProductForAdminDtoMvc(products);
        productsDto.forEach(product -> product.getImages().forEach(image ->
                image.setProductImage(baseUrlForLoad + image.getProductImage())));
        productsDto.sort(Comparator.comparing(ProductForAdminDto::getSubcategory));
        return productsDto;
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

    /**
     * Метод позволяет отредактировать продукт. Используется в админке MVC
     * @param productForUpdateAdminDto - продукт для редактирования
     * @throws EntityNotFoundException - в случае если продукт не будет найден
     * @throws EntitiesNotFoundException - в случае если подкатегория, цвет для картинки или характеристика не будут найдены
     * @throws IOException - в случае если не получится сохранить картинку
     */
    @Override
    @Transactional
    public void updateProductAdminMvc(ProductForUpdateAdminDto productForUpdateAdminDto) throws EntityNotFoundException, EntitiesNotFoundException, IOException {
        logger.info("Попытка редактирования продукта mvc");
        ProductEntity product = productRepository.findById(productForUpdateAdminDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено продукта по id для редактирования"));
        SubcategoryEntity subcategory = subcategoryRepository.findById(productForUpdateAdminDto.getSubcategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдено по id подкатегории для продукта"));
        productMapper.updateProductEntity(productForUpdateAdminDto, subcategory, product);
        productRepository.save(product);
        characteristicValueService.updateCharacteristicsValueMvc(productForUpdateAdminDto.getCharacteristicsValue(), product);
        imageService.updateImagesAdminMvc(productForUpdateAdminDto.getImages(), product);
    }

    /**
     * Метод позволяет получить продукты категории бутылки и банки для главной страницы. Берется по
     * 2 продукта из каждой подкатегории.
     * @return - словарь продуктов по категориям
     */
    @Override
    @Transactional
    public Map<Integer, List<ProductForUserDto>> getProductsForHomePage() {
        Map<Integer, List<ProductForUserDto>> productsDtoByCategoryId = new HashMap<>();
        List<ProductEntity> products = productRepository.findProductInAllSubcategoryForBottleAndJarCategoryWithLimit();
        Map<Integer, List<ProductEntity>> productsByCategory = products.stream()
                .collect(Collectors.groupingBy(p -> p.getSubcategory().getCategory().getId()));
        List<ProductForUserDto> productBottle = productListMapper.mapToProductForUserDtoList(productsByCategory.get(1));
        setHostToImageLoad(productBottle);
        productsDtoByCategoryId.put(1, productBottle);
        List<ProductForUserDto> productJar = productListMapper.mapToProductForUserDtoList(productsByCategory.get(2));
        setHostToImageLoad(productJar);
        productsDtoByCategoryId.put(2, productJar);
        return productsDtoByCategoryId;
    }

    /**
     * Метод позволяет удалить продукт
     *
     * @param id - id продукта
     */
    @Override
    public void deleteProduct(Integer id) {
        logger.info("Попытка удаления продукта по id");
        productRepository.deleteById(id);
        productRepository.existsById(id);
    }

    /**
     * Метод подготавливает продукт для страницы продукта. Данные необходимы для навигации
     * @param product - сущность продукта
     * @param productForUserDto - dto продукта
     */
    private void prepareProductForProductPage(ProductEntity product, ProductForUserDto productForUserDto) {
        SubcategoryEntity subcategory = product.getSubcategory();
        CategoryEntity category = subcategory.getCategory();
        productForUserDto.setCategoryId(category.getId());
        productForUserDto.setCategoryName(category.getName());
        productForUserDto.setSubcategoryId(subcategory.getId());
        productForUserDto.setSubcategoryName(subcategory.getName());
        productForUserDto.getImages().forEach(image -> image.setProductImage(baseUrlForLoad + image.getProductImage()));
    }

    /**
     * Метод позволяет преобразовать JSON в объект для открытия страницы корзина
     * @param json - список как JSON
     * @return - список продуктов
     * @throws JsonProcessingException - в случае если не удалось преобразовать
     */
    private List<ProductForOpenBasketDto> parseJsonToProductForOpenBasket(String json) throws JsonProcessingException {
        logger.info("Преобразование из JSON в объект");
        return objectMapper.readValue(json, new TypeReference<>() {});
    }

    /**
     * Метод позволяет получить список id продуктов преобразовывая их из String в Integer
     * @param products - список продуктов
     * @return - список id
     */
    private List<Integer> getProductIdsFromProductForOpenBasket(List<ProductForOpenBasketDto> products) {
        logger.info("Получение списка id из списка продуктов");
        return products.stream()
                .map(product -> Integer.parseInt(product.getId())).toList();
    }

    /**
     * Метод добавляет к картинкам хост для загрузки
     * @param productsForUser - список продуктов
     */
    private void setHostToImageLoad(List<ProductForUserDto> productsForUser) {
        logger.info("Добавления хоста к пути картинки");
        productsForUser.forEach(product -> product.getImages().forEach(
                image -> image.setProductImage(baseUrlForLoad + image.getProductImage())));
    }

    /**
     * Метод позволяет сопоставить продукт на его количество, и создает объект для отображения в корзине
     * @param productList - список с количеством
     * @param productsForUser - список объектов
     * @return - сопоставленный список продуктов на количество
     */
    private List<ProductForBasketDto> buildProductForBasketList(List<ProductForOpenBasketDto> productList, List<ProductForUserDto> productsForUser){
        logger.info("Сопоставление продукта с количеством");
        List<ProductForBasketDto> productsForBasket = new ArrayList<>();
        for(ProductForOpenBasketDto product : productList){
            for(ProductForUserDto productForUser : productsForUser) {
                if (Integer.parseInt(product.getId()) == productForUser.getId()) {
                    productsForBasket.add(new ProductForBasketDto(productForUser, product.getCount()));
                }
            }
        }
        return productsForBasket;
    }

    /**
     * Метод вернет словарь характеристик, где ключ это название
     * @param characteristics - характеристики продукта
     * @return - словарь характеристик, название, значение
     */
    private Map<String, Double> getCharacteristicsValue(List<ProductCharacteristicEntity> characteristics){
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
            Map<String, Double> characteristicValues = getCharacteristicsValue(productEntity.getProductCharacteristics());
            Double price = characteristicValues.get("Цена");
            if (price == null) return baseMessage;
            double positionPriceSum = price * product.getAmount();
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
