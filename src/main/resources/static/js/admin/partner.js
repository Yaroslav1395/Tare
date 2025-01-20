function previewImageLogo() {
    const imageInput = document.getElementById('logoImage');
    const imagePreview = document.getElementById('imageLogoPreview');
    const imageError = document.getElementById('logoImageError');
    const saveButton = document.getElementById('saveButton');
    const maxSize = 6000 * 1024; // 60 KB

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

function previewImageProduct() {
    const imageInput = document.getElementById('productImage');
    const imagePreview = document.getElementById('imageProductPreview');
    const imageError = document.getElementById('productImageError');
    const saveButton = document.getElementById('saveButton');
    const maxSize = 100 * 1024; // 60 KB

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

function validateSaveForm() {
    const productImageError = document.getElementById('productImageError');
    const logoImageError = document.getElementById('logoImageError');
    const productImageInput = document.getElementById('productImage');
    const logoImageInput = document.getElementById('logoImage');
    const form = event.target;

    imageError.style.display = 'none';

    let isValid = true;

    if (logoImageInput.files.length === 0) {
        logoImageError.textContent = 'Выберите картинку';
        logoImageError.style.display = 'block';
        isValid = false;
        return isValid;
    }

    if(logoImageInput.files[0].size > 6000 * 1024) {
        logoImageError.textContent = 'Размер файла не должен превышать 60 KB';
        logoImageError.style.display = 'block';
        isValid = false;
    }

    if(productImageInput.files.length !== 0 && productImageInput.files[0].size > 100 * 1024) {
        logoImageError.textContent = 'Размер файла не должен превышать 100 KB';
        logoImageError.style.display = 'block';
        isValid = false;
    }
    return isValid;
}

function showUpdateForm() {
    if (!selectedRow) return;

    const id = selectedRow.cells[0].textContent;
    const name = selectedRow.cells[1].textContent;
    const partnerLogo = selectedRow.cells[6].textContent;
    const partnerProduct = selectedRow.cells[7].textContent;
    const description = selectedRow.cells[8].textContent;

    document.getElementById('id').value = id;
    document.getElementById('nameUpdate').value = name;
    document.getElementById('descriptionUpdate').value = description;

    const logoImagePreview = document.getElementById('imageLogoPreviewUpdate');
    const logoImageInput = document.getElementById('logoImageUpdate');
    const productImagePreview = document.getElementById('imageProductPreviewUpdate');
    const productImageInput = document.getElementById('productImageUpdate');

     if (partnerLogo) {
        logoImagePreview.src = partnerLogo;
        logoImagePreview.style.display = 'block';
     } else {
        logoImagePreview.style.display = 'none';
     }

     if (partnerProduct) {
         productImagePreview.src = partnerProduct;
         productImagePreview.style.display = 'block';
     } else {
         productImagePreview.style.display = 'none';
     }

     const formContainer = document.getElementById('edit-form');
     formContainer.classList.add('slide-in');
}

function previewImageProductUpdate() {
    const imageInput = document.getElementById('productImageUpdate');
    const imagePreview = document.getElementById('imageProductPreviewUpdate');
    const imageError = document.getElementById('productImageUpdateError');
    const saveButton = document.getElementById('saveButtonUpdate');
    const maxSize = 100 * 1024;


    if(imageInput.files.length !== 0) {
        const file = imageInput.files[0];

        const reader = new FileReader();
        reader.onload = function (e) {
            imagePreview.src = e.target.result;
            imagePreview.style.display = 'block';
        };
        reader.readAsDataURL(file);
    }

    if(imageInput.files.length !== 0 && imageInput.files[0].size > maxSize) {
        imageError.textContent = 'Размер файла не должен превышать 100 KB';
        imageError.style.display = 'block';
        saveButton.disabled = true;
    }else {
        imageError.style.display = 'none';
        saveButton.disabled = false;
    }
}

function previewImageLogoUpdate() {
    const imageInput = document.getElementById('logoImageUpdate');
    const imagePreview = document.getElementById('imageLogoPreviewUpdate');
    const imageError = document.getElementById('logoImageUpdateError');
    const saveButton = document.getElementById('saveButtonUpdate');
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