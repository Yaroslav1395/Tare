function showUpdateForm() {
    if (!selectedRow) return;

    const id = selectedRow.cells[0].textContent;
    const capacityFrom = selectedRow.cells[1].textContent;
    const capacityTo = selectedRow.cells[2].textContent;

    document.getElementById('id').value = id;
    document.getElementById('capacityFromUpdate').value = capacityFrom;
    document.getElementById('capacityToUpdate').value = capacityTo;

    const formContainer = document.getElementById('edit-form');
    formContainer.classList.add('slide-in');
}