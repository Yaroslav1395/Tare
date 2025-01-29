const decrementBtn = document.querySelector('.decrement');
const incrementBtn = document.querySelector('.increment');
const quantityInput = document.getElementById('quantity');

decrementBtn.addEventListener('click', () => {
    let value = parseInt(quantityInput.value);
    if (value > 1) {
        quantityInput.value = value - 1;
    }
});

incrementBtn.addEventListener('click', () => {
    let value = parseInt(quantityInput.value);
    quantityInput.value = value + 1;
});

function changeImage(thumbnail) {
    // Получаем элемент с главным изображением
    const mainImage = document.getElementById('product-main-img');
    // Устанавливаем src главного изображения на src миниатюры
    mainImage.src = thumbnail.src;
}

document.addEventListener('DOMContentLoaded', () => {
    const buttons = document.querySelectorAll('.product-characteristics-title-btn button');
    const sections = {
        description: document.getElementById('description'),
        characteristics: document.getElementById('characteristics'),
    };

    buttons.forEach(button => {
        button.addEventListener('click', () => {
            // Убираем класс 'active' у всех кнопок
            buttons.forEach(btn => btn.classList.remove('active'));
            // Добавляем класс 'active' к нажатой кнопке
            button.classList.add('active');

            // Скрываем все секции
            Object.values(sections).forEach(section => section.classList.remove('active'));

            // Показываем выбранную секцию
            const targetId = button.getAttribute('data-target');
            if (sections[targetId]) {
                sections[targetId].classList.add('active');
            }
        });
    });
});