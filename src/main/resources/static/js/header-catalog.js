const headerCatalogBackground = document.getElementById("headerCatalogBackground");
const headerCatalogModal = document.getElementById("headerCatalogModal");
const catalogBtn = document.querySelector('.catalog-button');
const catalogIconClose = document.getElementById("catalogIconClose");
const catalogIcon = document.getElementById("catalogIcon");

// Функция для переключения видимости модального окна и иконок
function toggleCatalog(isOpen) {
    headerCatalogBackground.style.display = isOpen ? "block" : "none";
    headerCatalogModal.style.display = isOpen ? "block" : "none";
    catalogIconClose.style.display = isOpen ? "block" : "none";
    catalogIcon.style.display = isOpen ? "none" : "block";
}

// Закрытие модального окна при клике вне модального содержимого
headerCatalogBackground.addEventListener("click", (e) => {
    if (e.target === headerCatalogBackground) {
        toggleCatalog(false);
    }
});

// Открытие/закрытие модального окна при клике на кнопку
catalogBtn.addEventListener("click", () => {
    const isOpen = headerCatalogModal.style.display !== 'block';
    toggleCatalog(isOpen);
});