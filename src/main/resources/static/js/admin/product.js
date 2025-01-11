function filterTableBySubcategory() {
    const selectedOption = document.getElementById('subcategorySelect').selectedOptions[0];
    const selectedSubcategoryId = selectedOption.value;  // Получаем id подкатегории
    const selectedSubcategoryName = selectedOption.text;  // Получаем текст подкатегории

    console.log("Selected ID: " + selectedSubcategoryId);
    console.log("Selected Text: " + selectedSubcategoryName);


    const tableRows = document.querySelectorAll('#productTable tbody tr');

    tableRows.forEach(row => {
        const subcategoryRowName = row.getAttribute('data-subcategory-id');
        if (selectedSubcategoryName === "Все подкатегории" || selectedSubcategoryName === subcategoryRowName) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });
}