const swiperBottle = new Swiper(".bottle-swiper", {
  slidesPerView: 4,
  spaceBetween: 25,
  roundLengths: true,

  breakpoints: {
    1200: {
      slidesPerView: 4,
    },
    991: {
      slidesPerView: 3,
    },
    768: {
      slidesPerView: 2,
    },
    576: {
      slidesPerView: 3,
      spaceBetween: 10,
    },
    375: {
      slidesPerView: 1,
      spaceBetween: 10,
    },
  },

  navigation: {
    nextEl: ".swiper-button-next-custom-bottle",
    prevEl: ".swiper-button-prev-custom-bottle",
  },

  on: {
    slideChange: function () {
      updateBottleNavigationButtons(this);
    },
    init: function () {
      updateBottleNavigationButtons(this);
    },
  },
});

// Функция управления видимостью кнопок для bottle
function updateBottleNavigationButtons(swiperInstance) {
  const prevButton = document.querySelector(".swiper-button-prev-custom-bottle");
  const nextButton = document.querySelector(".swiper-button-next-custom-bottle");

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