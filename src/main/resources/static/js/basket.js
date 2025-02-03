document.addEventListener('DOMContentLoaded', () => {
    // Обновляем счетчик при загрузке страницы
    updateBasketCounter();
});

function addToCart(button) {
    // Получаем ID продукта из атрибута data-id
    const productId = button.getAttribute('data-id');

    // Проверяем, есть ли инпут с количеством
    const quantityInput = document.getElementById('quantity');
    const quantity = quantityInput ? parseInt(quantityInput.value, 10) || 1 : 1; // Берем значение или используем 1 по умолчанию

    // Получаем текущую корзину из localStorage
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    // Проверяем, есть ли уже продукт в корзине
    const existingProduct = cart.find(item => item.id === productId);

    if (existingProduct) {
        // Если продукт уже в корзине, увеличиваем его count на выбранное количество
        existingProduct.count += quantity;
    } else {
        // Если продукта нет, добавляем его в корзину с указанным количеством
        cart.push({ id: productId, count: quantity });
    }

    // Сохраняем обновленную корзину в localStorage
    localStorage.setItem('cart', JSON.stringify(cart));

    // Обновляем счетчик корзины
    updateBasketCounter();

    // Сообщение об успешном добавлении
    showNotification('Товар добавлен в корзину!');
}

function updateBasketCounter() {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    const totalItems = cart
        .filter(item => typeof item === 'object' && item.count) // Оставляем только объекты с count
        .reduce((sum, item) => sum + item.count, 0);
    // Обновляем текст в счетчике
    const counterElement = document.getElementById('basket-counter');
    counterElement.textContent = totalItems;
    const basketCounter = document.getElementById('basket-counter-small');
    if (totalItems === 0) {
        basketCounter.style.display = 'none';
    } else {
        basketCounter.style.display = 'block';
    }
}

function showNotification(message) {
    const notification = document.getElementById('notification');
    notification.textContent = message;

    // Показать уведомление
    notification.classList.remove('hidden');
    notification.classList.add('visible');

    // Скрыть уведомление через 3 секунды
    setTimeout(() => {
        notification.classList.remove('visible');
        notification.classList.add('hidden');
    }, 3000);
}

function submitBasket() {
    // Получаем данные корзины из localStorage
    let basketData = localStorage.getItem('cart');
    console.log(basketData);

    if (basketData) {
        try {
            // Парсим данные корзины
            let products = JSON.parse(basketData);

            // Преобразуем каждый элемент в объект {id, count}, если это число
            products = products.map(item => {
                if (typeof item === 'number') {
                    return { id: item, count: 0 }; // Если элемент - число, задаем count = 0
                }
                return item; // Если элемент уже объект, оставляем его без изменений
            });

            console.log(products);

            // Заполняем скрытое поле формы данными корзины
            document.getElementById('productsInput').value = JSON.stringify(products);

            // Отправляем форму
            document.getElementById('basketForm').submit();
        } catch (error) {
            console.error("Ошибка при обработке данных корзины: ", error);
            alert("Не удалось обработать данные корзины.");
        }
    }
}

function updateQuantity(change, productId) {
        const quantityInput = document.getElementById(`quantity-${productId}`);
        console.log(quantityInput);
        let currentQuantity = parseInt(quantityInput.value);
        console.log(currentQuantity);
        if (isNaN(currentQuantity)) currentQuantity = 1;

        const newQuantity = currentQuantity + change;
        if (newQuantity >= 1) {
            quantityInput.value = newQuantity;
            recalculateTotal(productId);
            recalculateOrderContainer();
            updateProductInLocalStorage(productId, newQuantity);
        }
}

function recalculateTotal(productId) {
    console.log('Recalculating total for productId:', productId);

    // Получаем элементы
    const quantityInput = document.getElementById(`quantity-${productId}`);
    const totalElement = document.getElementById(`total-${productId}`);
    const priceElement = document.getElementById(`product-price-${productId}`);
    const packagingElement = document.getElementById(`product-packaging-${productId}`);

    // Проверяем, найдены ли элементы
    if (!quantityInput || !totalElement || !priceElement || !packagingElement) {
        console.error('One or more elements are missing!');
        return;
    }

    // Получаем значения
    const priceText = priceElement.textContent.trim();
    const packagingText = packagingElement.textContent.trim();

    // Преобразуем значения в числа
    const priceValue = parseFloat(priceText.replace(" сом/шт", "").trim());
    const packagingValue = parseFloat(packagingText.replace(" шт", "").trim());
    const quantity = parseInt(quantityInput.value, 10);

    console.log('Price:', priceValue, 'Packaging:', packagingValue, 'Quantity:', quantity);

    // Проверяем, являются ли значения числами
    if (isNaN(priceValue) || isNaN(packagingValue) || isNaN(quantity)) {
        console.error('Invalid numeric values:', { priceValue, packagingValue, quantity });
        return;
    }

    // Пересчитываем итог
    const total = priceValue * packagingValue * quantity;
    console.log('Total:', total);

    // Обновляем текст
    totalElement.textContent = total.toFixed(2) + " сом";
}

function recalculateOrderContainer() {
    let totalItems = 0;
    let grandTotal = 0;

    // Перебираем все продукты в корзине
    const basketItems = document.querySelectorAll('.basket-item');
    basketItems.forEach(item => {
        const quantityInput = item.querySelector('input[type="number"]');
        const totalElement = item.querySelector('.basket-item-total-price p');

        if (quantityInput && totalElement) {
            const quantity = parseInt(quantityInput.value, 10);
            const totalPriceText = totalElement.textContent.trim();
            const totalPriceValue = parseFloat(totalPriceText.replace(" сом", "").trim());

            if (!isNaN(quantity)) {
                totalItems += quantity;
            }
            if (!isNaN(totalPriceValue)) {
                grandTotal += totalPriceValue;
            }
        }
    });

    // Обновляем количество и стоимость в order-container
    document.getElementById('total-items').textContent = totalItems;
    document.getElementById('total-items-price').textContent = grandTotal.toFixed(2) + " сом";
    document.getElementById('grand-total').textContent = grandTotal.toFixed(2) + " сом";
}

document.addEventListener('DOMContentLoaded', function () {
        recalculateOrderContainer();
});

function updateProductInLocalStorage(productId, newQuantity) {
    // Получаем данные о товарах из localStorage
    let basket = JSON.parse(localStorage.getItem('cart')) || [];

    // Ищем товар в корзине по id
    const productIndex = basket.findIndex(product => product.id == productId);

    if (productIndex !== -1) {
        // Обновляем количество товара
        basket[productIndex].count = newQuantity;
    } else {
        // Если товара нет в корзине, добавляем его с новым количеством
        basket.push({ id: productId, count: newQuantity });
    }

    // Сохраняем обновлённую корзину обратно в localStorage
    localStorage.setItem('cart', JSON.stringify(basket));
}

function deleteProduct(productId) {
    // Удаляем товар из DOM
    const productElement = document.getElementById(`product-${productId}`);
    if (productElement) {
        productElement.remove();
    }

    // Удаляем товар из localStorage
    removeProductFromLocalStorage(productId);

    // Перерасчитываем корзину
    recalculateOrderContainer();
}

// Функция для удаления товара из localStorage
function removeProductFromLocalStorage(productId) {
    console.log('basket');
    console.log(productId);
    // Получаем корзину из localStorage
    let basket = JSON.parse(localStorage.getItem('cart')) || [];
    console.log(basket);
    // Удаляем товар с указанным id, преобразуя productId в строку
    basket = basket.filter(product => product.id !== String(productId)); // Преобразуем productId в строку
    console.log(basket);
    // Сохраняем обновлённую корзину обратно в localStorage
    localStorage.setItem('cart', JSON.stringify(basket));
    updateBasketCounter();
}