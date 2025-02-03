document.addEventListener("DOMContentLoaded", function () {
        const img = document.getElementById("zoomable-image");
        const panzoom = Panzoom(img, {
            maxScale: 17, // Максимальное увеличение (4x)
            minScale: 1, // Минимальное уменьшение (0.5x)
            contain: "outside", // Запрещает выход за границы
            canvas: true, // Позволяет двигать изображение
        });

        // Включаем увеличение через колесико мыши
        img.parentElement.addEventListener("wheel", panzoom.zoomWithWheel);
    });