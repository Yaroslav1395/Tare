const container = document.querySelector('.category-container');
const totalItems = container.children.length;
const itemsPerRow = 5; // Количество колонок
const missingItems = itemsPerRow - (totalItems % itemsPerRow);

if (missingItems < itemsPerRow) {
    for (let i = 0; i < missingItems; i++) {
        const emptyDiv = document.createElement('div');
        emptyDiv.style.visibility = 'hidden';
        container.appendChild(emptyDiv);
    }
}