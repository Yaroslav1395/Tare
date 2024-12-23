document.addEventListener("DOMContentLoaded", () => {
    const errorMessage = document.getElementById("error-message");
    const closeButton = document.getElementById("close-btn");

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