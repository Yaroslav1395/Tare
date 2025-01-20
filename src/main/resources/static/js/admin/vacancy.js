function showUpdateForm() {
    if (!selectedRow) return;

    const id = selectedRow.cells[0].textContent;
    const name = selectedRow.cells[1].textContent;
    const description = selectedRow.cells[2].textContent;
    const offer = selectedRow.cells[3].textContent;

    document.getElementById('id').value = id;
    document.getElementById('nameUpdate').value = name;
    document.getElementById('descriptionUpdate').value = description;
    document.getElementById('offerUpdate').value = offer;

    const formContainer = document.getElementById('edit-form');
    formContainer.classList.add('slide-in');
}