package kg.zavod.Tare.service.category.impl;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.dto.category.*;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.subcategory.SubcategorySimpleDto;
import kg.zavod.Tare.mapper.category.CategoryListMapper;
import kg.zavod.Tare.mapper.category.CategoryMapper;
import kg.zavod.Tare.repository.category.CategoryRepository;
import kg.zavod.Tare.service.category.CategoryService;
import kg.zavod.Tare.service.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryListMapper categoryListMapper;
    @Value("${file.save.basic.path}")
    private String basicPath;
    @Value("${base.url.load}")
    private String baseUrlForLoad;
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    /**
     * Метод позволяет получить все категории с подкатегориями
     * @throws EntitiesNotFoundException - в случае если ни оной категории не найдено
     * @return - список категорий
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryForUserDto> getAllCategories() throws EntitiesNotFoundException {
        logger.info("Поиск всех категорий");
        List<CategoryEntity> categories = categoryRepository.findAll();
        if(categories.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одной категории");
        List<CategoryForUserDto> categoriesDto =  categoryListMapper.mapToCategoryDtoList(categories);
        categoriesDto.forEach(category -> category.setCategoryImage(baseUrlForLoad + category.getCategoryImage()));
        return sortBySubcategoryName(categoriesDto);
    }

    /**
     * Метод позволяет получить категорию для клиента по id
     * @param categoryId - id категории
     * @return - категория
     * @throws EntityNotFoundException - в случае если категория не будет найдена
     */
    @Override
    public CategorySimpleForUserDto getCategoryForUserById(Integer categoryId) throws EntityNotFoundException {
        logger.info("Попытка получения категории по id");
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Не найдена категория по id"));
        return categoryMapper.mapToCategoryForUserDto(category);
    }

    /**
     * Метод позволяет получить все категории для админки MVC
     * @return - список категорий
     * @throws EntitiesNotFoundException - в случае если ничего не найдено
     */
    @Override
    public List<CategoryForAdminDto> getAllCategoriesForAdmin() throws EntitiesNotFoundException {
        logger.info("Получение всех категорий для админки");
        List<CategoryEntity> categories = categoryRepository.findAll();
        if(categories.isEmpty()) throw new EntitiesNotFoundException("Категорий не найдено");
        categories.forEach(category -> category.setCategoryImage(baseUrlForLoad + category.getCategoryImage()));
        return categoryListMapper.mapToCategoryForAdminDtoList(categories);
    }

    /**
     * Метод позволяет сохранить категорию. Для админки MVC
     * @param categoryForSaveDto - данные категории
     * @throws DuplicateEntityException - в случае если категория уже существует
     * @throws IOException - в случае если не удалось сохранить картинку
     * @throws EntityNotFoundException - в случае если формат не картинки поддерживается
     */
    @Override
    @Transactional
    public void saveCategory(CategoryForSaveAdminDto categoryForSaveDto) throws DuplicateEntityException, IOException, EntityNotFoundException {
        logger.info("Попытка сохранение категории через админку MVC");
        boolean isDuplicateName = categoryRepository.findByName(categoryForSaveDto.getName()).isPresent();
        if(isDuplicateName) throw new DuplicateEntityException("Такое название категории уже существует");
        String path = UtilService.saveImage(categoryForSaveDto.getMultipartFile(), "categories", basicPath);
        ImageType categoryImageType = UtilService.getImageTypeFrom(categoryForSaveDto.getMultipartFile());
        CategoryEntity category = categoryMapper.mapToCategoryEntity(categoryForSaveDto, path, categoryImageType);
        categoryRepository.save(category);
    }

    /**
     * Метод позволяет отредактировать категорию. Для админки MVC
     * @param categoryForUpdate - данные категории для редактирования
     * @throws DuplicateEntityException - в случае если категория уже существует
     * @throws IOException - в случае если не удалось сохранить картинку
     * @throws EntityNotFoundException - в случае если формат не картинки поддерживается
     */
    @Override
    public void updateCategory(CategoryForUpdateAdminDto categoryForUpdate) throws EntityNotFoundException, IOException, DuplicateEntityException {
        logger.info("Поиск категории по id для редактирования через админку MVC");
        boolean isDuplicateName = categoryRepository.findByNameAndIdIsNot(categoryForUpdate.getName(), categoryForUpdate.getId()).isPresent();
        if(isDuplicateName) throw new DuplicateEntityException("При редактировании дублируется название другой категории");
        CategoryEntity category = categoryRepository.findById(categoryForUpdate.getId())
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено категории для редактирования"));
        logger.info("Изменение найденной категории. MVC");
        if(categoryForUpdate.getMultipartFile().getSize() < 1) {
            category.setName(categoryForUpdate.getName());
        }else {
            ImageType categoryImageType = UtilService.getImageTypeFrom(categoryForUpdate.getMultipartFile());
            String path = UtilService.saveImage(categoryForUpdate.getMultipartFile(), "categories", basicPath);
            categoryMapper.mapToCategoryEntity(categoryForUpdate, categoryImageType, path, category);
        }
        logger.info("Сохранение отредактированных данных категории. MVC");
        categoryRepository.save(category);
    }

    /**
     * Метод позволяет удалять категорию
     *
     * @param id - id категории
     */
    @Override
    @Transactional
    public void deleteCategoryById(Integer id) {
        logger.info("Удаление категории");
        categoryRepository.deleteById(id);
        categoryRepository.existsById(id);
    }

    /**
     * Метод позволяет отсортировать подкатегории для каждой категории
     * @param categories - список категорий
     * @return - отсортированный список категорий по подкатегориям
     */
    private List<CategoryForUserDto> sortBySubcategoryName(List<CategoryForUserDto> categories) {
        return categories.stream()
                .peek(category -> category.setSubcategories(
                        category.getSubcategories().stream()
                                .sorted(Comparator.comparing(SubcategorySimpleDto::getName))
                                .collect(Collectors.toList())))
                .toList();
    }
}
