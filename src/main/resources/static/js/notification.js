document.addEventListener("DOMContentLoaded", () => {
    const errorMessage = document.getElementById("error-message");
    const closeButton = document.getElementById("close-btn-error");

    // Показать сообщение с анимацией
    setTimeout(() => {
        errorMessage.classList.add("visible");
    }, 100);

    // Закрыть сообщение через 10 секунд
    setTimeout(() => {
        closeError();
    }, 7000);

    // Закрыть сообщение при клике на кнопку
    closeButton.addEventListener("click", closeError);

    function closeError() {
        errorMessage.classList.remove("visible");
        // Убираем элемент после анимации
        setTimeout(() => {
            errorMessage.style.display = "none";
        }, 500); // Должно совпадать с transition в CSS
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const successMessage = document.getElementById("success-message");
    const closeButton = document.getElementById("close-btn-success");

    // Показать сообщение с анимацией
    setTimeout(() => {
        successMessage.classList.add("visible");
    }, 100);

    // Закрыть сообщение через 10 секунд
    setTimeout(() => {
        closeError();
    }, 7000);

    // Закрыть сообщение при клике на кнопку
    closeButton.addEventListener("click", closeError);

    function closeError() {
        successMessage.classList.remove("visible");
        // Убираем элемент после анимации
        setTimeout(() => {
            successMessage.style.display = "none";
        }, 500); // Должно совпадать с transition в CSS
    }
});