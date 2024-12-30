const swiperJar = new Swiper(".jar-swiper", {
  slidesPerView: 4,
  spaceBetween: 25,

  navigation: {
    nextEl: ".swiper-button-next-custom-jar",
    prevEl: ".swiper-button-prev-custom-jar",
  },

  on: {
    slideChange: function () {
      updateJarNavigationButtons(this);
    },
    init: function () {
      updateJarNavigationButtons(this);
    },
  },
});

// Функция управления видимостью кнопок для jar
function updateJarNavigationButtons(swiperInstance) {
  const prevButton = document.querySelector(".swiper-button-prev-custom-jar");
  const nextButton = document.querySelector(".swiper-button-next-custom-jar");

  if (swiperInstance.isBeginning) {
    prevButton.style.display = "none";
  } else {
    prevButton.style.display = "flex";
  }

  if (swiperInstance.isEnd) {
    nextButton.style.display = "none";
  } else {
    nextButton.style.display = "flex";
  }
}
