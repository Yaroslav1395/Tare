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

function showInstruction() {
    const instruction = document.getElementById('instruction');
    instruction.classList.add('slide-in');
}

function hideInstruction() {
    const instruction = document.getElementById('instruction');
    instruction.classList.remove('slide-in');
}

function showCreateForm() {
    const form = document.getElementById('create-form');
    form.classList.add('slide-in');
}

function hideCreateForm() {
    const form = document.getElementById('create-form');
    form.classList.remove('slide-in');
}

function hideUpdateForm() {
    const form = document.getElementById('edit-form');
    form.classList.remove('slide-in');
}

function previewImage() {
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

function validateForm() {
    const nameError = document.getElementById('nameError');
    const imageError = document.getElementById('imageError');
    const nameInput = document.getElementById('name');
    const imageInput = document.getElementById('image');
    const form = event.target;

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
        return isValid;
    }

    if(imageInput.files[0].size > 60 * 1024) {
        imageError.textContent = 'Размер файла не должен превышать 60 KB';
        imageError.style.display = 'block';
        isValid = false;
    }
    return isValid;
}