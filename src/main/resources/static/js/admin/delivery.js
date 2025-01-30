function showButtons(input) {
    let form = input.closest("form");
    let buttons = form.querySelectorAll(".confirm-btn, .cancel-btn");
    buttons.forEach(btn => btn.style.display = "inline-block");
}

function hideButtonsIfNotEditing(input) {
    setTimeout(() => {
        let form = input.closest("form");
        let buttons = form.querySelectorAll(".confirm-btn, .cancel-btn");
        let originalValue = input.getAttribute("value");
        let currentValue = input.value;

        // Если значение не изменилось, скрываем кнопки
        if (originalValue === currentValue) {
            buttons.forEach(btn => btn.style.display = "none");
        }
    }, 200);
}

function cancelEdit(button) {
    let form = button.closest("form");
    let input = form.querySelector("input[name='price']");
    let buttons = form.querySelectorAll(".confirm-btn, .cancel-btn");

    input.value = input.getAttribute("value"); // Возвращаем исходное значение
    buttons.forEach(btn => btn.style.display = "none"); // Скрываем кнопки
}