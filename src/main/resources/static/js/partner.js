function togglePartnerDetails(button) {
    // Исправляем селектор, добавляем точку перед "partner-item"
    const parentItemWrapper = button.closest('.partner-item-wrapper');
    const parentItem = parentItemWrapper.querySelector('.partner-item');

    // Проверяем, какие классы сейчас у элемента
    const isExpanded = parentItem.classList.contains('partner-item-show');

    // Переключаем классы
    if (isExpanded) {
        parentItem.classList.remove('partner-item-show');
        parentItem.classList.add('partner-item-hidden');
    } else {
        parentItem.classList.remove('partner-item-hidden');
        parentItem.classList.add('partner-item-show');
    }

    // Меняем текст кнопки
    /*button.textContent = isExpanded ? 'Подробнее' : 'Скрыть';*/

    // Меняем иконку внутри кнопки
    const icon = button.querySelector('i');
    if (icon) {
            if (isExpanded) {
                icon.classList.remove('fa-arrow-up');
                icon.classList.add('fa-arrow-down');
            } else {
                icon.classList.remove('fa-arrow-down');
                icon.classList.add('fa-arrow-up');
            }
        }
}