let selectedRow = null;
let selectedId = null;

function selectRow(row) {
    if (selectedRow) {
        selectedRow.classList.remove('selected');
    }

    selectedRow = row;
    row.classList.add('selected');
    selectedId = row.cells[0].innerText;

    document.getElementById('edit-button').classList.remove('control-btn-disabled');
    document.getElementById('delete-button').classList.remove('control-btn-disabled');
    document.getElementById('idToDelete').value = selectedId;
}

function checkDeleteButtonState() {
    const deleteButton = document.getElementById('delete-button');
    return !deleteButton.classList.contains('control-btn-disabled');
}

function showUpdateForm() {
    if (!selectedRow) return;

    const id = selectedRow.cells[0].textContent;
    const name = selectedRow.cells[1].textContent;
    const imagePath = selectedRow.cells[4].textContent;

    document.getElementById('id').value = id;
    document.getElementById('nameUpdate').value = name;

    const imagePreview = document.getElementById('imagePreviewUpdate');
    const fileInput = document.getElementById('categoryImageUpdate');

    if (imagePath) {
        imagePreview.src = imagePath;
        imagePreview.style.display = 'block';
    } else {
        imagePreview.style.display = 'none';
    }

    const formContainer = document.getElementById('edit-form');
    formContainer.classList.add('slide-in');
}

function hideUpdateForm() {
    const form = document.getElementById('edit-form');
    form.classList.remove('slide-in');
}


function showCreateForm() {
    const form = document.getElementById('create-form');
    form.classList.add('slide-in');
}


function hideCreateForm() {
    const form = document.getElementById('create-form');
    form.classList.remove('slide-in');
}

function showInstruction() {
    const instruction = document.getElementById('instruction');
    instruction.classList.add('slide-in');
}

function hideInstruction() {
    const instruction = document.getElementById('instruction');
    instruction.classList.remove('slide-in');
}


function previewImage() {
    const imageInput = document.getElementById('categoryImage');
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

function previewImageUpdate() {
    const imageInput = document.getElementById('categoryImageUpdate');
    const imageUpdatePreview = document.getElementById('imagePreviewUpdate');
    const imageUpdateError = document.getElementById('imageErrorUpdate');
    const saveButton = document.getElementById('saveButton');

    const maxSize = 60 * 1024; // 60 KB

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
    } else {
        imageUpdatePreview.style.display = 'none';
        imageUpdateError.textContent = 'Выберите картинку';
        imageUpdateError.style.display = 'block';
        saveButton.disabled = true;
    }
}

function validateForm() {
    const nameError = document.getElementById('nameError');
    const imageError = document.getElementById('imageError');
    const nameInput = document.getElementById('name');
    const imageInput = document.getElementById('categoryImage');

    nameError.style.display = 'none';
    imageError.style.display = 'none';

    let isValid = true;

    if (nameInput.value.trim() === "") {
        nameError.style.display = 'block';
        isValid = false;
    }

    if (imageInput.files.length === 0) {
        imageError.textContent = 'Выберите картинку';
        imageError.style.display = 'block';
        isValid = false;
    }

    if(imageInput.files[0].size > 60 * 1024) {
        imageError.textContent = 'Размер файла не должен превышать 60 KB';
        imageError.style.display = 'block';
        isValid = false;
    }
    return isValid;
}

function validateUpdateForm() {
    const nameErrorUpdate = document.getElementById('nameErrorUpdate');
    const imageErrorUpdate = document.getElementById('imageErrorUpdate');
    const nameUpdateInput = document.getElementById('nameUpdate');
    const categoryImageUpdateInput = document.getElementById('categoryImageUpdate');

    nameErrorUpdate.style.display = 'none';
    imageErrorUpdate.style.display = 'none';

    let isValid = true;

    if (nameUpdateInput.value.trim() === "") {
        nameErrorUpdate.style.display = 'block';
        isValid = false;
    }

    if (categoryImageUpdateInput.files.length === 0) {
        imageErrorUpdate.textContent = 'Выберите картинку';
        imageErrorUpdate.style.display = 'block';
        isValid = false;
    }

    if(categoryImageUpdateInput.files[0].size > 60 * 1024) {
        imageErrorUpdate.textContent = 'Размер файла не должен превышать 60 KB';
        imageErrorUpdate.style.display = 'block';
        isValid = false;
    }
    return isValid;
}