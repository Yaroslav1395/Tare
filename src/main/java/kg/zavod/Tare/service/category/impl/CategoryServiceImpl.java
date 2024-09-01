package kg.zavod.Tare.service.category.impl;

import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.dto.category.CategoryDto;
import kg.zavod.Tare.dto.category.CategoryForSaveDto;
import kg.zavod.Tare.dto.category.CategoryForUpdateDto;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.mapper.cutegory.CategoryListMapper;
import kg.zavod.Tare.mapper.cutegory.CategoryMapper;
import kg.zavod.Tare.repository.category.CategoryRepository;
import kg.zavod.Tare.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryListMapper categoryListMapper;
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    /**
     * Метод позволяет получить категорию с подкатегориями по id
     * @param id - id категории
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @return - категлоия с подкатегориями
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
        logger.info("Поск всех категорий");
        List<CategoryEntity> categories = categoryRepository.findAll();
        if(categories.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одной категории");
        return categoryListMapper.mapToCategoryDtoList(categories);
    }

    /**
     * Метод позволяет сохранить категорию
     * @param categoryForSaveDto - категория для сохранения
     * @return - сохраненная категория
     */
    @Override
    @Transactional
    public CategoryDto saveCategory(CategoryForSaveDto categoryForSaveDto) {
        logger.info("Попытка сохранения категории");
        CategoryEntity category = categoryMapper.mapToCategoryEntity(categoryForSaveDto);
        CategoryEntity savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToCategoryDto(savedCategory);
    }

    /**
     * Метод позволят редактировать категорию меняя ее названиее и картинку
     * @param categoryForUpdateDto - категория для редактирования
     * @throws EntityNotFoundException - в случае если при редактировании не найдено
     * @return - отредактированная категория
     */
    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryForUpdateDto categoryForUpdateDto) throws EntityNotFoundException {
        logger.info("Поиск категории по id для редактирования");
        CategoryEntity category = categoryRepository.findById(categoryForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По id не найдено категории для редактирования"));
        logger.info("Изменение найденой категории");
        CategoryEntity updatedCategory = categoryMapper.mapToCategoryEntity(categoryForUpdateDto, category);
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
