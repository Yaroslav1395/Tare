function showUpdateForm() {
    if (!selectedRow) return;

    const id = selectedRow.cells[0].textContent;
    const name = selectedRow.cells[1].textContent;
    const categoryName = selectedRow.cells[4].textContent;
    const imagePath = selectedRow.cells[5].textContent;

    const categorySelect = document.getElementById('subcategoryUpdate');
    Array.from(categorySelect.options).forEach(option => {
             console.log(option);
            if (option.textContent === categoryName) {
                console.log(true);
                option.selected = true;
            }
    });
    document.getElementById('id').value = id;
    document.getElementById('nameUpdate').value = name;

    const imagePreview = document.getElementById('imagePreviewUpdate');
    const fileInput = document.getElementById('subcategoryImageUpdate');

    if (imagePath) {
        imagePreview.src = imagePath;
        imagePreview.style.display = 'block';
    } else {
        imagePreview.style.display = 'none';
    }

    const formContainer = document.getElementById('edit-form');
    formContainer.classList.add('slide-in');
}

function previewImageUpdate() {
    const imageInput = document.getElementById('subcategoryImageUpdate');
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
    }
}

function validateUpdateForm() {
    const nameErrorUpdate = document.getElementById('nameErrorUpdate');
    const imageErrorUpdate = document.getElementById('imageErrorUpdate');
    const nameUpdateInput = document.getElementById('nameUpdate');
    const categoryImageUpdateInput = document.getElementById('subcategoryImageUpdate');

    nameErrorUpdate.style.display = 'none';
    imageErrorUpdate.style.display = 'none';

    let isValid = true;

    if (nameUpdateInput.value.trim() === "") {
        nameErrorUpdate.style.display = 'block';
        isValid = false;
    }

    if(categoryImageUpdateInput.files[0].size > 60 * 1024) {
        imageErrorUpdate.textContent = 'Размер файла не должен превышать 60 KB';
        imageErrorUpdate.style.display = 'block';
        isValid = false;
    }
    return isValid;
}