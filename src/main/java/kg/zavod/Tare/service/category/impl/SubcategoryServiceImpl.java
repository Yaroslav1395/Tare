package kg.zavod.Tare.service.category.impl;

import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.domain.category.SubcategoryEntity;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.subcategory.SubcategoryDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForSaveDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForUpdateDto;
import kg.zavod.Tare.mapper.subcategory.SubcategoryListMapper;
import kg.zavod.Tare.mapper.subcategory.SubcategoryMapper;
import kg.zavod.Tare.repository.category.CategoryRepository;
import kg.zavod.Tare.repository.category.SubcategoryRepository;
import kg.zavod.Tare.service.category.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryMapper subcategoryMapper;
    private final SubcategoryListMapper subcategoryListMapper;
    private static final Logger logger = LoggerFactory.getLogger(SubcategoryServiceImpl.class);

    /**
     * Метод позволяет получить подкатегорию со списком id продуктов по id
     * @throws EntityNotFoundException  - в случае если по id ничего не найдено
     * @param id - id подкатегории
     * @return - подкатегория со списком id продуктов
     */
    @Override
    @Transactional(readOnly = true)
    public SubcategoryDto getSubcategoryById(Integer id) throws EntityNotFoundException {
        logger.info("Поиск подкатегории по id");
        SubcategoryEntity subcategoryEntity = subcategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено подкатегории"));
        return subcategoryMapper.mapToSubcategoryDto(subcategoryEntity);
    }

    /**
     * Метод позволяет получить все подкатегории со списком id продуктов
     * @throws EntitiesNotFoundException - в случае если ни оной подкатегории не найдено
     * @return - список подкатегорий
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubcategoryDto> getAllSubcategories() throws EntitiesNotFoundException {
        logger.info("Попытка получить все подкатегории");
        List<SubcategoryEntity> subcategories = subcategoryRepository.findAll();
        if(subcategories.isEmpty()) throw new EntitiesNotFoundException("Не найдено ни одной подкатегории");
        return subcategoryListMapper.mapToSubcategoryDtoList(subcategories);
    }

    /**
     * Метод позволяет сохранить подкатегорию
     * @param subcategoryForSaveDto - подкатегория для сохранения
     * @return - сохраненная подкатегория
     * @throws EntityNotFoundException - в случае если не найдена категория для подкатегории
     * @throws DuplicateEntityException - в случае если в категории уже есть подкатегория с таким названием
     */
    @Override
    @Transactional
    public SubcategoryDto saveSubcategory(SubcategoryForSaveDto subcategoryForSaveDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Попытка сохранения подкатегории");
        boolean isDuplicate = subcategoryRepository.findByNameAndCategoryId(subcategoryForSaveDto.getName(), subcategoryForSaveDto.getCategoryId()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Подкатегория с таким названием уже существует в этой категории");
        CategoryEntity categoryEntity = categoryRepository.findById(subcategoryForSaveDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдена категория по id"));
        SubcategoryEntity subcategoryForSave = subcategoryMapper.mapToSubcategoryEntity(subcategoryForSaveDto, categoryEntity);
        SubcategoryEntity savedSubcategory = subcategoryRepository.save(subcategoryForSave);
        return subcategoryMapper.mapToSubcategoryDto(savedSubcategory);
    }

    /**
     * Метод позволят редактировать подкатегорию меняя ее название, картинку и категорию
     * @param subcategoryForUpdateDto - подкатегория для редактирования
     * @return - отредактированная подкатегория
     * @throws EntityNotFoundException - в случае если при редактировании не найдено подкатегории или категории
     * @throws DuplicateEntityException - в случае если в категории дублируется подкатегория
     */
    @Override
    @Transactional
    public SubcategoryDto updateSubcategory(SubcategoryForUpdateDto subcategoryForUpdateDto) throws EntityNotFoundException, DuplicateEntityException {
        logger.info("Попытка редактирования подкатегории");
        boolean isDuplicate = subcategoryRepository.findByNameAndIdIsNotAndCategoryId(subcategoryForUpdateDto.getName(),
                subcategoryForUpdateDto.getId(), subcategoryForUpdateDto.getCategoryId()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("В этой категории уже есть подкатегория с таким названием");
        SubcategoryEntity subcategory = subcategoryRepository.findById(subcategoryForUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено подкатегории"));
        logger.info("Изменение подкатегории");
        subcategoryMapper.updateSubcategoryFromDto(subcategoryForUpdateDto, subcategory);
        SubcategoryEntity updatedSubcategory = subcategoryRepository.save(subcategory);
        return subcategoryMapper.mapToSubcategoryDto(updatedSubcategory);
    }

    /**
     * Метод позволяет удалять подкатегорию
     * @param id - id подкатегории
     * @return - удалена или нет
     */
    @Override
    public boolean deleteSubcategoryById(Integer id) {
        logger.info("Попытка удаления подкатегории");
        subcategoryRepository.deleteById(id);
        return !subcategoryRepository.existsById(id);
    }

}
