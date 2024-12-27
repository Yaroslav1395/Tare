const swiper = new Swiper('.swiper', {
    loop: true,
    grabCursor: true,
    slidesPerView: 1,
    navigation: false,
    pagination: false,
});

// Синхронизация индикаторов
swiper.on('slideChange', function () {
    const indicators = document.querySelectorAll('.home-slider-control-indicator');
    indicators.forEach((indicator, index) => {
        if (index === swiper.realIndex) {
            indicator.classList.add('active');
        } else {
            indicator.classList.remove('active');
        }
    });
});

document.querySelectorAll('.home-slider-control-indicator').forEach((indicator, index) => {
    indicator.addEventListener('click', () => swiper.slideToLoop(index));
});