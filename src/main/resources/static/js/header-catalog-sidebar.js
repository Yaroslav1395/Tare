document.addEventListener("DOMContentLoaded", function () {
    let menuIcon = document.getElementById("menuIcon");
    let sidebar = document.querySelector(".header-catalog-sidebar-wrapper");
    let closeIcon = document.getElementById("headerCatalogIconClose");

    menuIcon.addEventListener("click", function () {
        sidebar.classList.add("active");
        document.body.classList.add("menu-open"); // Запрет прокрутки
    });

    closeIcon.addEventListener("click", function () {
        sidebar.classList.remove("active");
        document.body.classList.remove("menu-open"); // Разрешение прокрутки
    });
});

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".toggle-subcategories").forEach(button => {
        button.addEventListener("click", function () {
            let categoryContainer = this.closest(".header-catalog-sidebar-item");
            let subcategoryList = categoryContainer.querySelector(".subcategory-list");

            // Переключаем отображение подкатегорий
            let isOpen = subcategoryList.style.display === "flex";
            subcategoryList.style.display = isOpen ? "none" : "flex";

            // Меняем значок стрелки
            this.textContent = isOpen ? "▼" : "▲";
        });
    });
});