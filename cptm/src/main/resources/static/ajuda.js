function changeColor(select) {
    const colorMap = {
        rubi: '#9b111e',
        turquesa: '#40E0D0',
        coral: '#FF7F50',
        safira: '#800080',
        jade: '#00A96B'
    };

    const selectedValue = select.value;
    select.style.backgroundColor = colorMap[selectedValue] || '#aaa';
}

const textarea = document.getElementById('comentario');
const placeholderText = textarea.getAttribute('placeholder');



