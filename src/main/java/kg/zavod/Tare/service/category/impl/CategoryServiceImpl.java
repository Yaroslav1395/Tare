package kg.zavod.Tare.service.category.impl;

import kg.zavod.Tare.domain.ImageType;
import kg.zavod.Tare.domain.category.CategoryEntity;
import kg.zavod.Tare.dto.category.CategoryDto;
import kg.zavod.Tare.dto.category.CategoryForSaveDto;
import kg.zavod.Tare.dto.category.CategoryForUpdateDto;
import kg.zavod.Tare.dto.category.mvc.CategoryForAdminDto;
import kg.zavod.Tare.dto.category.mvc.CategoryForHomeDto;
import kg.zavod.Tare.dto.category.mvc.CategoryForSaveAdminDto;
import kg.zavod.Tare.dto.category.mvc.CategoryForUpdateAdminDto;
import kg.zavod.Tare.dto.exception.DuplicateEntityException;
import kg.zavod.Tare.dto.exception.EntitiesNotFoundException;
import kg.zavod.Tare.dto.exception.EntityNotFoundException;
import kg.zavod.Tare.dto.subcategory.SubcategorySimpleDto;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//TODO: путь для сохранения
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryListMapper categoryListMapper;

    @Value("${file.save.basic.path}")
    private String basicPath;
    @Value("${base.url.load}")
    private String baseUrlForLoad;
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);


    /**
     * Метод позволяет получить все категории для админки MVC
     * @return - список категорий
     * @throws EntitiesNotFoundException - в случае если ничего не найдено
     */
    @Override
    public List<CategoryForAdminDto> getAllCategoriesForAdminPage() throws EntitiesNotFoundException {
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
        String path = saveImage(categoryForSaveDto.getMultipartFile(), "categories");
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
            String path = saveImage(categoryForUpdate.getMultipartFile(), "categories");
            categoryMapper.mapToCategoryEntity(categoryForUpdate, categoryImageType, path, category);
        }
        logger.info("Сохранение отредактированных данных категории. MVC");
        categoryRepository.save(category);
    }

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
        List<CategoryDto> categoriesDto =  categoryListMapper.mapToCategoryDtoList(categories);
        return sortBySubcategoryName(categoriesDto);
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

    /**
     * Метод позволяет отсортировать подкатегории для каждой категории
     * @param categories - список категорий
     * @return - отсортированный список категорий по подкатегориям
     */
    private List<CategoryDto> sortBySubcategoryName(List<CategoryDto> categories) {
        return categories.stream()
                .peek(category -> category.setSubcategories(
                        category.getSubcategories().stream()
                                .sorted(Comparator.comparing(SubcategorySimpleDto::getName))
                                .collect(Collectors.toList())))
                .toList();
    }


    public String saveImage(MultipartFile file, String folder) throws IOException {
        String uploadDir = basicPath + folder;
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            boolean created = uploadPath.mkdirs();
            if (!created) {
                throw new IOException("Не удалось создать директорию для загрузки файлов: " + uploadDir);
            }
        }

        if (file.isEmpty()) {
            throw new IOException("Файл отсутствует или пустой");
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + extension;
        File newfile = new File(uploadPath, uniqueFilename);
        file.transferTo(newfile);
        return folder + "/" + uniqueFilename;
    }
}
