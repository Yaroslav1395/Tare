const swiper_nuv = new Swiper('.home-slider-container', {
    loop: true,
    grabCursor: true,
    slidesPerView: 1,
    navigation: false,
    pagination: false,
});

// Синхронизация индикаторов
swiper_nuv.on('slideChange', function () {
    const indicators = document.querySelectorAll('.home-slider-control-indicator');
    indicators.forEach((indicator, index) => {
        if (index === swiper_nuv.realIndex) {
            indicator.classList.add('active');
        } else {
            indicator.classList.remove('active');
        }
    });
});

document.querySelectorAll('.home-slider-control-indicator').forEach((indicator, index) => {
    indicator.addEventListener('click', () => swiper_nuv.slideToLoop(index));
});