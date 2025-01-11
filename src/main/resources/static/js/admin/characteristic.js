function showUpdateForm() {
    if (!selectedRow) return;

    const id = selectedRow.cells[0].textContent;
    const name = selectedRow.cells[1].textContent;

    const categorySelect = document.getElementById('characteristicUpdate');
    document.getElementById('id').value = id;
    document.getElementById('nameUpdate').value = name;

    const formContainer = document.getElementById('edit-form');
    formContainer.classList.add('slide-in');
}