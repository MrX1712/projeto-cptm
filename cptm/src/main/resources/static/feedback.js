document.addEventListener('DOMContentLoaded', () => {
    const stars = document.querySelectorAll('.star');
    const ratingInput = document.getElementById('rating');
    const form = document.getElementById('feedbackForm');
    const logado = document.body.getAttribute("data-logado") === "true";

    let selectedRating = 0;

    function highlightStars(rating) {
        stars.forEach((star, index) => {
            if (index < rating) {
                star.textContent = '★';
                star.style.color = '#f44336';
            } else {
                star.textContent = '☆';
                star.style.color = '#ccc';
            }
        });
    }

    stars.forEach((star, index) => {
        star.addEventListener('mouseenter', () => highlightStars(index + 1));
        star.addEventListener('mouseleave', () => highlightStars(selectedRating));
        star.addEventListener('click', () => {
            selectedRating = index + 1;
            ratingInput.value = selectedRating;
            highlightStars(selectedRating);
        });
    });

    highlightStars(0);

    form.addEventListener('submit', (e) => {
        if (!logado) {
            e.preventDefault();
            alert("Você precisa estar logado para enviar feedback.");
            window.location.href = "/cptm+/login";
            return;
        }

        const tipo = document.getElementById('tipo').value;
        const comentario = document.getElementById('comments').value.trim();

        if (!tipo || !selectedRating || !comentario) {
            e.preventDefault();
            alert("Preencha todos os campos e selecione uma nota.");
        }
    });
});
