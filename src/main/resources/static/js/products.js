function updateProductImage(colorElement) {
    const colorElementId = colorElement.id;
    console.log(colorElementId);
    const parts = colorElementId.split('*');
    const productId = parts[0];
    const imageSrc = parts[1];
    const imageContainer = document.getElementById('image-container-' + productId);
    if (imageContainer) {
         imageContainer.src = imageSrc;
    }
}