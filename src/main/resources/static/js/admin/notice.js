function previewSaveImage() {
    const imageInput = document.getElementById('image');
    const imagePreview = document.getElementById('imagePreview');
    const imageError = document.getElementById('imageError');
    const saveButton = document.getElementById('saveButton');
    const maxSize = 60 * 1024; // 60 KB

    if (imageInput.files.length > 0) {
        const file = imageInput.files[0];

        const reader = new FileReader();
        reader.onload = function (e) {
            imagePreview.src = e.target.result;
            imagePreview.style.display = 'block';
        };
        reader.readAsDataURL(file);

        if (file.size > maxSize) {
            imageError.textContent = 'Размер файла не должен превышать 60 KB';
            imageError.style.display = 'block';
            saveButton.disabled = true;
        } else {
            imageError.style.display = 'none';
            saveButton.disabled = false;
        }
    } else {
        imagePreview.style.display = 'none';
        imageError.textContent = 'Выберите картинку';
        imageError.style.display = 'block';
        saveButton.disabled = true;
    }
}

function showUpdateForm() {
    if (!selectedRow) return;

    const id = selectedRow.cells[0].textContent;
    const title = selectedRow.cells[1].textContent;
    const imagePath = selectedRow.cells[5].textContent;
    const description = selectedRow.cells[6].textContent;
    document.getElementById('id').value = id;
    document.getElementById('nameUpdate').value = title;
    document.getElementById('descriptionUpdate').value = description;

    const imagePreview = document.getElementById('imageUpdatePreview');

    if (imagePath) {
        imagePreview.src = imagePath;
        imagePreview.style.display = 'block';
    } else {
        imagePreview.style.display = 'none';
    }

    const formContainer = document.getElementById('edit-form');
    formContainer.classList.add('slide-in');
}

function previewUpdateImage() {
    const imageInput = document.getElementById('imageUpdate');
    const imageUpdatePreview = document.getElementById('imageUpdatePreview');
    const imageUpdateError = document.getElementById('imageUpdateError');
    const saveButton = document.getElementById('saveUpdateButton');

    const maxSize = 60 * 1024;

    if (imageInput.files.length > 0) {
        const file = imageInput.files[0];

        const reader = new FileReader();
        reader.onload = function (e) {
            imageUpdatePreview.src = e.target.result;
            imageUpdatePreview.style.display = 'block';
        };
        reader.readAsDataURL(file);

        if (file.size > maxSize) {
            imageUpdateError.textContent = 'Размер файла не должен превышать 60 KB';
            imageUpdateError.style.display = 'block';
            saveButton.disabled = true;
        } else {
            imageUpdateError.style.display = 'none';
            saveButton.disabled = false;
        }
    }
}

function validateUpdateForm() {
    const imageErrorUpdate = document.getElementById('imageUpdateError');
    const imageUpdateInput = document.getElementById('imageUpdate');

    imageErrorUpdate.style.display = 'none';

    let isValid = true;

    if(imageUpdateInput.files[0].size > 60 * 1024) {
        imageErrorUpdate.textContent = 'Размер файла не должен превышать 60 KB';
        imageErrorUpdate.style.display = 'block';
        isValid = false;
    }
    return isValid;
}