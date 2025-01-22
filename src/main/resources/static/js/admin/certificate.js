function previewImageCertificateRu() {
    const imageInput = document.getElementById('imageCertificateRu');
    const imagePreview = document.getElementById('imageCertificateRuPreview');
    const imageError = document.getElementById('imageCertificateRuError');
    const saveButton = document.getElementById('saveButton');
    const maxSize = 100 * 1024;

    if (imageInput.files.length > 0) {
        const file = imageInput.files[0];

        const reader = new FileReader();
        reader.onload = function (e) {
            imagePreview.src = e.target.result;
            imagePreview.style.display = 'block';
        };
        reader.readAsDataURL(file);

        if (file.size > maxSize) {
            imageError.textContent = 'Размер файла не должен превышать 100 KB';
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

function previewImageCertificateKg() {
    const imageInput = document.getElementById('imageCertificateKg');
    const imagePreview = document.getElementById('imageCertificateKgPreview');
    const imageError = document.getElementById('imageCertificateKgError');
    const saveButton = document.getElementById('saveButton');
    const maxSize = 100 * 1024;

    if (imageInput.files.length > 0) {
        const file = imageInput.files[0];

        const reader = new FileReader();
        reader.onload = function (e) {
            imagePreview.src = e.target.result;
            imagePreview.style.display = 'block';
        };
        reader.readAsDataURL(file);

        if (file.size > maxSize) {
            imageError.textContent = 'Размер файла не должен превышать 100 KB';
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
    const name = selectedRow.cells[1].textContent;
    const description = selectedRow.cells[2].textContent;
    const certificateImage = selectedRow.cells[7].textContent;
    const certificateImageKg = selectedRow.cells[8].textContent;

    document.getElementById('id').value = id;
    document.getElementById('nameUpdate').value = name;
    document.getElementById('descriptionUpdate').value = description;

    const imageCertificateRuPreviewUpdate = document.getElementById('imageCertificateRuPreviewUpdate');
    const imageCertificateRuUpdate = document.getElementById('imageCertificateRuUpdate');

    const imageCertificateKgPreviewUpdate = document.getElementById('imageCertificateKgPreviewUpdate');
    const imageCertificateKgUpdate = document.getElementById('imageCertificateKgUpdate');


    if (imageCertificateRuPreviewUpdate) {
        imageCertificateRuPreviewUpdate.src = certificateImage;
        imageCertificateRuPreviewUpdate.style.display = 'block';
    } else {
        imageCertificateRuPreviewUpdate.style.display = 'none';
    }

    if (imageCertificateKgPreviewUpdate) {
        imageCertificateKgPreviewUpdate.src = certificateImageKg;
        imageCertificateKgPreviewUpdate.style.display = 'block';
    } else {
        imageCertificateKgPreviewUpdate.style.display = 'none';
    }

    const formContainer = document.getElementById('edit-form');
    formContainer.classList.add('slide-in');
}

function previewImageCertificateRuUpdate() {
    const imageInput = document.getElementById('imageCertificateRuUpdate');
    const imagePreview = document.getElementById('imageCertificateRuPreviewUpdate');
    const imageError = document.getElementById('imageCertificateRuErrorUpdate');
    const saveButton = document.getElementById('saveButtonUpdate');
    const maxSize = 100 * 1024;

    if (imageInput.files.length > 0) {
        const file = imageInput.files[0];

        const reader = new FileReader();
        reader.onload = function (e) {
            imagePreview.src = e.target.result;
            imagePreview.style.display = 'block';
        };
        reader.readAsDataURL(file);

        if (file.size > maxSize) {
            imageError.textContent = 'Размер файла не должен превышать 100 KB';
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

function previewImageCertificateKgUpdate() {
    const imageInput = document.getElementById('imageCertificateKgUpdate');
    const imagePreview = document.getElementById('imageCertificateKgPreviewUpdate');
    const imageError = document.getElementById('imageCertificateKgErrorUpdate');
    const saveButton = document.getElementById('saveButtonUpdate');
    const maxSize = 100 * 1024;

    if (imageInput.files.length > 0) {
        const file = imageInput.files[0];

        const reader = new FileReader();
        reader.onload = function (e) {
            imagePreview.src = e.target.result;
            imagePreview.style.display = 'block';
        };
        reader.readAsDataURL(file);

        if (file.size > maxSize) {
            imageError.textContent = 'Размер файла не должен превышать 100 KB';
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