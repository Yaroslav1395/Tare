package kg.zavod.Tare.service.category.impl;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.domain.category.SubcategoryEntity;
import kg.zavod.Tare.domain.product.ImageEntity;
import kg.zavod.Tare.domain.product.ProductEntity;
import kg.zavod.Tare.dto.category.CategoryDto;
import kg.zavod.Tare.dto.category.CategoryForSaveDto;
import kg.zavod.Tare.dto.category.CategoryForUpdateDto;
import kg.zavod.Tare.dto.category.mvc.CategoryForHomeDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.mapper.category.CategoryListMapper;
import kg.zavod.Tare.mapper.category.CategoryMapper;
import kg.zavod.Tare.repository.category.CategoryRepository;
import kg.zavod.Tare.repository.category.SubcategoryRepository;
import kg.zavod.Tare.repository.product.ImageRepository;
import kg.zavod.Tare.repository.product.ProductRepository;
import kg.zavod.Tare.service.category.CategoryService;
import kg.zavod.Tare.service.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryListMapper categoryListMapper;
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    /**
     * Метод позволяет получить все категории для главной страницы MVC
     * @return - список категорий
     * @throws EntitiesNotFoundException - в случае если не найдены категории продуктов
     */
    @Override
    @Transactional
    public List<CategoryForHomeDto> getAllCategoriesForHomePage() throws EntitiesNotFoundException {
        /*logger.info("Получение всех категорий для главной страницы");
        List<CategoryEntity> categories = categoryRepository.findAll();
        List<SubcategoryEntity> subcategories = subcategoryRepository.findAllByCategoryId(
                categories.stream()
                        .map(CategoryEntity::getId)
                        .collect(Collectors.toList()));
        List<ImageEntity> images = imageRepository.findImagesByCategoryIds(List.of(1, 2));
        List<ProductEntity> products = productRepository.findAllById(images.stream().map(ImageEntity::get))
        List<CategoryEntity> categories = categoryRepository.findAll();
        if(categories.isEmpty()) throw new EntitiesNotFoundException("Не найдены категории продуктов");*/
        return categoryListMapper.mapToCategoryForHomeDtoList(null);
    }

    /**
     * Метод позволяет получить категорию с подкатегориями по id
     * @param id - id категории
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @return - категория с подкатегориями
     */
    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Integer id) throws EntityNotFoundException {
        logger.info("Поиск категории по id");
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Категория по id не найдена"));
        return categoryMapper.mapToCategoryDto(category);
    }

    /**
     * Метод позволяет получить все категории с подкатегориями
     * @throws EntitiesNotFoundException - в случае если ни оной категории не найдено
     * @return - список категорий
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() throws EntitiesNotFoundException {
        logger.info("Поиск всех категорий");
        List<CategoryEntity> categories = categoryRepository.findAll();
        if(categories.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одной категории");
        return categoryListMapper.mapToCategoryDtoList(categories);
    }

    /**
     * Метод позволяет сохранить категорию
     * @param categoryForSaveDto - категория для сохранения
     * @return - сохраненная категория
     * @throws DuplicateEntityException - в случае если дублируется название категории
     * @throws EntityNotFoundException - в случае если формат картинки не будет найден
     */
    @Override
    @Transactional
    public CategoryDto saveCategory(CategoryForSaveDto categoryForSaveDto) throws DuplicateEntityException, EntityNotFoundException {
        logger.info("Попытка сохранения категории");
        boolean isDuplicateName = categoryRepository.findByName(categoryForSaveDto.getName()).isPresent();
        if(isDuplicateName) throw new DuplicateEntityException("Такое название категории уже существует");
        ImageType categoryImageType = UtilService.getImageTypeFrom(categoryForSaveDto.getMultipartFile());
        CategoryEntity category = categoryMapper.mapToCategoryEntity(categoryForSaveDto, categoryImageType);
        CategoryEntity savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToCategoryDto(savedCategory);
    }

    /**
     * Метод позволят редактировать категорию меняя ее название и картинку
     * @param categoryForUpdateDto - категория для редактирования
     * @return - отредактированная категория
     * @throws EntityNotFoundException - если не будет найдено категории для редактирования или формата картинки
     * @throws DuplicateEntityException - если дублируется название другой категории
     */
    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryForUpdateDto categoryForUpdateDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Поиск категории по id для редактирования");
        boolean isDuplicateName = categoryRepository.findByNameAndIdIsNot(categoryForUpdateDto.getName(), categoryForUpdateDto.getId()).isPresent();
        if(isDuplicateName) throw new DuplicateEntityException("При редактировании дублируется название другой категории");
        CategoryEntity category = categoryRepository.findById(categoryForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено категории для редактирования"));
        logger.info("Изменение найденной категории");
        ImageType categoryImageType = UtilService.getImageTypeFrom(categoryForUpdateDto.getMultipartFile());
        CategoryEntity updatedCategory = categoryMapper.mapToCategoryEntity(categoryForUpdateDto, categoryImageType, category);
        logger.info("Сохранение отредактированных данных категории");
        CategoryEntity savedCategory = categoryRepository.save(updatedCategory);
        return categoryMapper.mapToCategoryDto(savedCategory);
    }

    /**
     * Метод позволяет удалять категорию
     * @param id - id категории
     * @return - удалена или нет
     */
    @Override
    @Transactional
    public Boolean deleteCategoryById(Integer id) {
        logger.info("Удаление категории");
        categoryRepository.deleteById(id);
        return !categoryRepository.existsById(id);
    }
}
