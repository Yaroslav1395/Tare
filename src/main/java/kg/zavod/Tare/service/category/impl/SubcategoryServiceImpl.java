package kg.zavod.Tare.service.category.impl;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.domain.category.SubcategoryEntity;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.subcategory.SubcategoryDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForSaveDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForUpdateDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForSaveAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForUpdateAdminDto;
import kg.zavod.Tare.dto.subcategory.mvc.SubcategoryForUserDto;
import kg.zavod.Tare.mapper.subcategory.SubcategoryListMapper;
import kg.zavod.Tare.mapper.subcategory.SubcategoryMapper;
import kg.zavod.Tare.repository.category.CategoryRepository;
import kg.zavod.Tare.repository.category.SubcategoryRepository;
import kg.zavod.Tare.service.category.SubcategoryService;
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

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryMapper subcategoryMapper;
    private final SubcategoryListMapper subcategoryListMapper;
    @Value("${file.save.basic.path}")
    private String basicPath;
    @Value("${base.url.load}")
    private String baseUrlForLoad;
    private static final Logger logger = LoggerFactory.getLogger(SubcategoryServiceImpl.class);

    /**
     * Метод позволяет получить подкатегорию для клиента MVC.
     * @param subcategoryId - id подкатегории
     * @return - подкатегория
     * @throws EntityNotFoundException - в случае если подкатегория не будет найдена
     */
    @Override
    public SubcategoryForUserDto getSubcategoryForUserById(Integer subcategoryId) throws EntityNotFoundException {
        logger.info("Попытка получения подкатегории по id");
        SubcategoryEntity subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new EntityNotFoundException("Не найдена подкатегория по id"));
        return subcategoryMapper.mapToSubcategoryForUserDto(subcategory);
    }

    /**
     * Метод позволяет получить подкатегории для страницы подкатегории по id категории. Используется в MVC
     * @param categoryId - id категории
     * @return - список подкатегорий
     * @throws EntitiesNotFoundException - в случае если ничего не найдено
     */
    @Override
    public List<SubcategoryForUserDto> getSubcategoryForUserByCategoryId(Integer categoryId) throws EntitiesNotFoundException {
        logger.info("Попытка получения подкатегорий для страницы подкатегории по id категории");
        List<SubcategoryEntity> subcategories = subcategoryRepository.findAllByCategoryId(categoryId);
        if(subcategories.isEmpty()) throw new EntitiesNotFoundException("Подкатегорий не найдено");
        return subcategoryListMapper.mapToSubcategoryForUserDtoList(subcategories);
    }

    /**
     * Ме6тод позволяет получить подкатегорию по id продукта. Используется в MVC
     * @param productId - id продукта
     * @return - подкатегория
     * @throws EntityNotFoundException - в случае если подкатегория не будет найдена
     */
    @Override
    public SubcategoryForUserDto getSubcategoryByProductId(Integer productId) throws EntityNotFoundException {
        logger.info("Попытка получения подкатегорий для страницы продукта по id продукта");
        return null;
    }

    /**
     * Метод позволяет получить подкатегории для админки. Используется в MVC
     * @return - список подкатегорий
     * @throws EntitiesNotFoundException - в случае если ничего не найдено
     */
    @Override
    public List<SubcategoryForAdminDto> getSubcategoriesForAdmin() throws EntitiesNotFoundException {
        logger.info("Попытка получить подкатегории для админки");
        List<SubcategoryEntity> subcategories = subcategoryRepository.findAll();
        if(subcategories.isEmpty()) throw new EntitiesNotFoundException("Подкатегорий не найдено");
        List<SubcategoryForAdminDto> subcategoriesDto = subcategoryListMapper.mapToSubcategoryForAdminDtoList(subcategories);
        subcategoriesDto.forEach(subcategory -> subcategory.setSubcategoryImage(baseUrlForLoad + subcategory.getSubcategoryImage()));
        subcategoriesDto.sort(Comparator.comparing(SubcategoryForAdminDto::getCategoryName));
        return subcategoriesDto;
    }

    /**
     * Метод позволяет сохранить подкатегорию. Для админки MVC
     * @param subcategoryForSaveDto - данные подкатегории
     * @throws DuplicateEntityException - в случае если подкатегория уже существует
     * @throws IOException - в случае если не удалось сохранить картинку
     * @throws EntityNotFoundException - в случае если формат картинки не поддерживается
     */
    @Override
    public void saveSubcategory(SubcategoryForSaveAdminDto subcategoryForSaveDto) throws DuplicateEntityException, IOException, EntityNotFoundException {
        logger.info("Попытка сохранение подкатегории через админку MVC");
        boolean isDuplicate = subcategoryRepository.findByNameAndCategoryId(subcategoryForSaveDto.getName(), subcategoryForSaveDto.getCategoryId()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("Подкатегория с таким названием уже существует в этой категории");
        CategoryEntity categoryEntity = categoryRepository.findById(subcategoryForSaveDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Не найдена категория по id"));
        String path = UtilService.saveImage(subcategoryForSaveDto.getSubcategoryImage(), "subcategories", basicPath);
        ImageType subcategoryImageType = UtilService.getImageTypeFrom(subcategoryForSaveDto.getSubcategoryImage());
        SubcategoryEntity subcategoryEntity = subcategoryMapper.mapToSubcategoryEntity(subcategoryForSaveDto, path, subcategoryImageType, categoryEntity);
        subcategoryRepository.save(subcategoryEntity);
    }

    /**
     * Метод позволяет отредактировать подкатегорию. Для админки MVC
     * @param subcategoryForUpdate - данные подкатегории для редактирования
     * @throws DuplicateEntityException - в случае если подкатегория уже существует
     * @throws IOException - в случае если не удалось сохранить картинку
     * @throws EntityNotFoundException - в случае если формат не картинки поддерживается
     */
    @Override
    @Transactional
    public void updateSubcategory(SubcategoryForUpdateAdminDto subcategoryForUpdate) throws EntityNotFoundException, IOException, DuplicateEntityException {
        logger.info("Попытка редактирования подкатегории через админку MVC");
        boolean isDuplicate = subcategoryRepository.findByNameAndIdIsNotAndCategoryId(subcategoryForUpdate.getName(),
                subcategoryForUpdate.getId(), subcategoryForUpdate.getCategoryId()).isPresent();
        if(isDuplicate) throw new DuplicateEntityException("В этой категории уже есть подкатегория с таким названием");
        SubcategoryEntity subcategory = subcategoryRepository.findById(subcategoryForUpdate.getId())
                .orElseThrow(() -> new EntityNotFoundException("По Id не найдено подкатегории"));
        CategoryEntity category = categoryRepository.findById(subcategoryForUpdate.getCategoryId())
                        .orElseThrow(() -> new EntityNotFoundException("По id не найдено категории"));
        logger.info("Изменение подкатегории");
        if(subcategoryForUpdate.getSubcategoryImage().getSize() < 1) {
            subcategory.setName(subcategoryForUpdate.getName());
            subcategory.setCategory(category);
        } else {
            ImageType subcategoryImageType = UtilService.getImageTypeFrom(subcategoryForUpdate.getSubcategoryImage());
            String path = UtilService.saveImage(subcategoryForUpdate.getSubcategoryImage(), "subcategories", basicPath);
            subcategoryMapper.mapToSubcategoryEntity(subcategoryForUpdate, subcategoryImageType, path, category, subcategory);
        }
        logger.info("Сохранение отредактированных данных подкатегории. MVC");
        categoryRepository.save(category);
    }


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
     * @throws EntityNotFoundException - в случае если не найдена категория для подкатегории или формат картинки
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
        ImageType subcategoryImageType = UtilService.getImageTypeFrom(subcategoryForSaveDto.getSubcategoryImage());
        SubcategoryEntity subcategoryForSave = subcategoryMapper.mapToSubcategoryEntity(subcategoryForSaveDto, subcategoryImageType, categoryEntity);
        SubcategoryEntity savedSubcategory = subcategoryRepository.save(subcategoryForSave);
        return subcategoryMapper.mapToSubcategoryDto(savedSubcategory);
    }

    /**
     * Метод позволят редактировать подкатегорию меняя ее название, картинку и категорию
     * @param subcategoryForUpdateDto - подкатегория для редактирования
     * @return - отредактированная подкатегория
     * @throws EntityNotFoundException - в случае если при редактировании не найдена подкатегории или категории или формат картинки
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
        ImageType subcategoryImageType = UtilService.getImageTypeFrom(subcategoryForUpdateDto.getSubcategoryImage());
        subcategoryMapper.updateSubcategoryFromDto(subcategoryForUpdateDto, subcategoryImageType, subcategory);
        SubcategoryEntity updatedSubcategory = subcategoryRepository.save(subcategory);
        return subcategoryMapper.mapToSubcategoryDto(updatedSubcategory);
    }

    /**
     * Метод позволяет удалять подкатегорию
     * @param id - id подкатегории
     * @return - удалена или нет
     */
    @Override
    @Transactional
    public boolean deleteSubcategoryById(Integer id) {
        logger.info("Попытка удаления подкатегории");
        subcategoryRepository.deleteById(id);
        return !subcategoryRepository.existsById(id);
    }

}
