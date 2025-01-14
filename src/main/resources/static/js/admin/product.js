function filterTableBySubcategory() {
    const selectedOption = document.getElementById('subcategorySelect').selectedOptions[0];
    const selectedSubcategoryId = selectedOption.value;
    const selectedSubcategoryName = selectedOption.text;

    const tableRows = document.querySelectorAll('#productTable tbody tr');

    tableRows.forEach(row => {
        const subcategoryRowName = row.getAttribute('data-subcategory-id');
        if (selectedSubcategoryName === "Все подкатегории" || selectedSubcategoryName === subcategoryRowName) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });
}

function addImageCard() {
    const cards = document.querySelectorAll('.form-product-left-images-item');
    const hiddenCards = Array.from(cards).filter(card => card.style.display === 'none');

    if (hiddenCards.length > 0) {
        const cardToShow = hiddenCards[0];
        cardToShow.style.display = 'flex';
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const cards = document.querySelectorAll('.form-product-left-images-item');
    cards.forEach(card => {
        card.style.display = 'none';
    });
    if (cards.length > 0) {
        cards[0].style.display = 'flex';
    }
});

function previewImagesProduct(input) {
    const inputId = input.id;
    const index = inputId.match(/\d+/)[0];
    const imgId = `file-img-${index}`;
    const imgElement = document.getElementById(imgId);
    const imageError = document.getElementById('imageError');
    const saveButton = document.getElementById('saveButton');
    if (imgElement && input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function (e) {
            imgElement.src = e.target.result;
            imgElement.style.display = 'block';
        };
        reader.readAsDataURL(input.files[0]);
    }

    const maxSize = 60 * 1024; // 60 KB
    if (input.files.length > 0) {
        const file = input.files[0];
        if (file.size > maxSize) {
            imageError.textContent = 'Размер файла не должен превышать 60 KB';
            imageError.style.display = 'block';
            saveButton.disabled = true;
        } else {
            imageError.style.display = 'none';
            saveButton.disabled = false;
        }
    } else {
        imageError.textContent = 'Выберите картинку';
        imageError.style.display = 'block';
        saveButton.disabled = true;
    }
}

function validateSaveProductForm(form) {
    const imageError = document.getElementById('imageError');
    let isValid = true;
    imageError.style.display = 'none';

    let isImageValid = false;
    const imageItems = form.querySelectorAll('.form-product-left-images-item');
    imageItems.forEach((imageItem, index) => {
        const imageInput = imageItem.querySelector('input[type="file"]');
        const file = imageInput.files[0];
        const colorSelect = imageItem.querySelector('select.color-select');
        const colorId = colorSelect.value;

        if (file && colorId) {
            if (file.size <= 60 * 1024) {
                isImageValid = true;
            } else {
                imageError.textContent = 'Размер файла не должен превышать 60 KB';
                imageError.style.display = 'block';
                isValid = false;
                return isValid;
            }
        } else if (file) {
            imageError.textContent = 'Цвет и картинка обязательны';
            imageError.style.display = 'block';
            isValid = false;
            return isValid;
        }
    });
    if (!isImageValid) {
        imageError.style.display = 'block';
        isValid = false;
    }
    return isValid;
}

document.addEventListener('DOMContentLoaded', () => {
    const fileInputs = document.querySelectorAll('.image-input');
    fileInputs.forEach(input => {
        input.addEventListener('change', function () {
            previewImagesProduct(this); // Передаем текущий input в функцию
        });
    });
});

function removeImage(button) {
    const buttonId = button.id;
    const index = buttonId.match(/\d+/)[0];
    const imageItem = document.getElementById('file-img-' + index);
    const fileInput = document.getElementById('file-input-' + index);
    fileInput.value = '';
    const parentContainer = document.querySelector('.form-product-left-images');
    const item = parentContainer.querySelector('.form-product-left-images-item:nth-child(' + (parseInt(index) + 1) + ')');
    item.style.display = 'none';
    const imgElement = document.getElementById('file-img-' + index);
    if (imgElement) {
        imgElement.style.display = 'none';
    }
    if (imageItem) {
        imageItem.style.display = 'none';
    }
}


function showUpdateForm() {
    if (!selectedRow) return;

    const id = selectedRow.cells[0].textContent;
    const name = selectedRow.cells[1].textContent;
    const idFromFactoryBd = selectedRow.cells[2].textContent;
    const subcategoryName = selectedRow.cells[3].textContent;

    document.getElementById('id').value = id;
    document.getElementById('nameUpdate').value = name;
    document.getElementById('idFromFactoryBdUpdate').value = idFromFactoryBd;
    const subcategorySelect = document.getElementById('subcategoryUpdate');
    Array.from(subcategorySelect.options).forEach(option => {
        if (option.textContent === subcategoryName) {
            option.selected = true;
        }
    });

    const productCharacteristics = JSON.parse(productCharacteristicsMap);
    const productChar = productCharacteristics[id] || {};

    for (const [characteristicId, value] of Object.entries(productChar)) {
        console.log(value.characteristicId);
        const characteristicId = document.getElementById(`characteristic_id-update-` + value.characteristicId);
        const characteristicValueId = document.getElementById(`characteristic_value-id-update-` + value.characteristicId);
        if (characteristicId && characteristicValueId) {
            characteristicId.value = value.value;
            characteristicValueId.value = value.id;
        }
    }

    const formContainer = document.getElementById('edit-form');
    formContainer.classList.add('slide-in');
}