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
        const tipo = document.getElementById('tipo').value;
        const comentario = document.getElementById('comments').value.trim();

        if (!logado) {
            e.preventDefault();
            alert("Você precisa estar logado para enviar feedback.");
            window.location.href = "/cptm+/login";
            return;
        }

        if (!tipo || !selectedRating || !comentario) {
            e.preventDefault();
            alert("Preencha todos os campos e selecione uma nota.");
            return;
        }

        ratingInput.value = selectedRating;
    });

    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('success') === 'true' || document.body.hasAttribute('data-feedback-enviado')) {
        setTimeout(() => {
            showFeedbackPopup();
            if (urlParams.get('success')) {
                window.history.replaceState({}, document.title, window.location.pathname);
            }
        }, 500);
    }
});

function showFeedbackPopup() {
    const overlay = document.getElementById('feedback-popup-overlay');
    if (overlay) {
        overlay.style.display = 'flex';
        document.body.style.overflow = 'hidden';

        resetForm();
    }
}

function closeFeedbackPopup() {
    const overlay = document.getElementById('feedback-popup-overlay');
    if (overlay) {
        overlay.style.display = 'none';
        document.body.style.overflow = 'auto';

        setTimeout(() => {
            window.location.href = '/cptm+/home';
        }, 100);
    }
}

function resetForm() {
    const form = document.getElementById('feedbackForm');
    const stars = document.querySelectorAll('.star');
    const ratingInput = document.getElementById('rating');

    if (form) {
        form.reset();
    }

    stars.forEach(star => {
        star.textContent = '☆';
        star.style.color = '#ccc';
    });

    if (ratingInput) {
        ratingInput.value = '';
    }

    if (typeof selectedRating !== 'undefined') {
        selectedRating = 0;
    }
}

document.addEventListener('click', function (e) {
    const overlay = document.getElementById('feedback-popup-overlay');
    const container = document.querySelector('.feedback-popup-container');

    if (overlay && overlay.style.display === 'flex' &&
        !container.contains(e.target)) {
        closeFeedbackPopup();
    }
});

document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') {
        const overlay = document.getElementById('feedback-popup-overlay');
        if (overlay && overlay.style.display === 'flex') {
            closeFeedbackPopup();
        }
    }
});