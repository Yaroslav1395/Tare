function changeImage(thumbnail) {
    // Найти родительский элемент .certificate-item
    const certificateItem = thumbnail.closest('.certificate-item');
    // Найти основное изображение внутри этого родителя
    const mainImage = certificateItem.querySelector('.zoomable-image');
    // Заменить источник основного изображения на выбранное
    mainImage.src = thumbnail.src;

    // Убрать класс 'active' у всех миниатюр внутри родителя
    const thumbnails = certificateItem.querySelectorAll('.certificate-thumbnail');
    thumbnails.forEach(img => img.classList.remove('active'));

    // Добавить класс 'active' к выбранной миниатюре
    thumbnail.classList.add('active');
}