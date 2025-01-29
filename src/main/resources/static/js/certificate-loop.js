// Получение элементов
const modal = document.getElementById("image-modal");
const modalImg = document.getElementById("modal-img");
const images = document.querySelectorAll(".zoomable-image");
const closeBtn = document.querySelector(".image-modal .close");

// Добавление события на клик для увеличения изображения
images.forEach(image => {
    image.addEventListener("click", () => {
        modal.style.display = "block";
        modalImg.src = image.src;
    });
});

// Закрытие модального окна при нажатии на крестик
closeBtn.addEventListener("click", () => {
    modal.style.display = "none";
});

// Закрытие модального окна при клике вне изображения
modal.addEventListener("click", (e) => {
    if (e.target === modal) {
        modal.style.display = "none";
    }
});

function changeImage(thumbnail) {
    // Получаем основной элемент изображения
    const mainImage = document.getElementById('main-certificate');

    // Меняем источник основного изображения на выбранное
    mainImage.src = thumbnail.src;

    // Убираем класс 'active' у всех миниатюр
    const thumbnails = document.querySelectorAll('.thumbnail');
    thumbnails.forEach(img => img.classList.remove('active'));

    // Добавляем класс 'active' к выбранной миниатюре
    thumbnail.classList.add('active');
}