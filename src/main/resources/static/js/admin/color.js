function showUpdateForm() {
    if (!selectedRow) return;

    const id = selectedRow.cells[0].textContent;
    const name = selectedRow.cells[1].textContent;
    const hexCode = selectedRow.cells[2].textContent;

    const categorySelect = document.getElementById('colorUpdate');
    document.getElementById('id').value = id;
    document.getElementById('nameUpdate').value = name;
    document.getElementById('hexCodeUpdate').value = hexCode;

    const formContainer = document.getElementById('edit-form');
    formContainer.classList.add('slide-in');
}