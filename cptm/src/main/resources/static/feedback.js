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
        // Validações
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

        // Garante que o campo hidden tenha o valor correto
        ratingInput.value = selectedRating;
        // ⚠️ O formulário será enviado normalmente para o Spring
    });
});

// Pop-up (mantido para uso futuro via AJAX, mas não será chamado com submit tradicional)
function showFeedbackPopup() {
    const overlay = document.getElementById('feedback-popup-overlay');
    if (overlay) {
        overlay.style.display = 'flex';
        document.body.style.overflow = 'hidden';
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

document.addEventListener('click', function(e) {
    const overlay = document.getElementById('feedback-popup-overlay');
    const container = document.querySelector('.feedback-popup-container');

    if (overlay && overlay.style.display === 'flex' &&
        !container.contains(e.target)) {
        closeFeedbackPopup();
    }
});

document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        const overlay = document.getElementById('feedback-popup-overlay');
        if (overlay && overlay.style.display === 'flex') {
            closeFeedbackPopup();
        }
    }
});
