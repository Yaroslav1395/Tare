package kg.zavod.Tare.service.category.impl;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.domain.category.SubcategoryEntity;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.subcategory.SubcategoryForAdminDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForSaveAdminDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForUpdateAdminDto;
import kg.zavod.Tare.dto.subcategory.SubcategoryForUserDto;
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
        List<SubcategoryForUserDto> subcategoriesDto = subcategoryListMapper.mapToSubcategoryForUserDtoList(subcategories);
        subcategoriesDto.forEach(subcategory -> subcategory.setSubcategoryImage(baseUrlForLoad + subcategory.getSubcategoryImage()));
        return subcategoriesDto;
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
     * Метод позволяет удалять подкатегорию
     *
     * @param id - id подкатегории
     */
    @Override
    @Transactional
    public void deleteSubcategoryById(Integer id) {
        logger.info("Попытка удаления подкатегории");
        subcategoryRepository.deleteById(id);
        subcategoryRepository.existsById(id);
    }

}
