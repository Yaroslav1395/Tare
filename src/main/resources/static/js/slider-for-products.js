const swiper = new Swiper('.swiper', {
    slidesPerView: 4, // Показываем 4 слайда одновременно
    spaceBetween: 20, // Расстояние между слайдами
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
    },
    loop: false, // Если хотите бесконечный слайдер, установите true
    grabCursor: true, // Удобный курсор при перетаскивании
    breakpoints: {
        768: {
            slidesPerView: 2, // На меньших экранах показываем 2 слайда
        },
        480: {
            slidesPerView: 1, // На телефонах показываем 1 слайд
        },
    },
});