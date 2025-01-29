function showUpdateForm() {
    if (!selectedRow) return;

    const id = selectedRow.cells[0].textContent;
    const name = selectedRow.cells[1].textContent;
    const divisionName = selectedRow.cells[2].textContent;

    const divisionSelect = document.getElementById('divisionUpdate');
    Array.from(divisionSelect.options).forEach(option => {
            if (option.textContent === divisionName) {
                option.selected = true;
            }
    });
    document.getElementById('id').value = id;
    document.getElementById('nameUpdate').value = name;

    const formContainer = document.getElementById('edit-form');
    formContainer.classList.add('slide-in');
}