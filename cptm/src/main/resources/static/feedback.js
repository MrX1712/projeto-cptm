document.addEventListener('DOMContentLoaded', () => {
    const stars = document.querySelectorAll('.star');
    let selectedRating = 0;

    function highlightStars(rating) {
        stars.forEach((star, index) => {
            if (index < rating) {
                star.textContent = '★';   // estrela cheia
                star.style.color = '#f44336'; // vermelho
            } else {
                star.textContent = '☆';   // estrela vazia
                star.style.color = '#ccc'; // cinza
            }
        });
    }

    stars.forEach((star, index) => {
        star.addEventListener('mouseenter', () => {
            highlightStars(index + 1);
        });

        star.addEventListener('mouseleave', () => {
            highlightStars(selectedRating);
        });

        star.addEventListener('click', () => {
            selectedRating = index + 1;
            highlightStars(selectedRating);
        });
    });

    // Inicializar com 0 estrelas
    highlightStars(0);

    // Validação e envio do formulário
    document.getElementById('feedbackForm').addEventListener('submit', function(e) {
        e.preventDefault();

        const area = document.getElementById('feedback-area').value;
        const comentarios = document.getElementById('comments').value.trim();

        if (!area) {
            alert('Por favor, selecione uma área.');
            return;
        }

        if (selectedRating === 0) {
            alert('Por favor, selecione uma nota de 1 a 5 estrelas.');
            return;
        }

        if (!comentarios) {
            alert('Por favor, insira seus comentários.');
            return;
        }

        // Simular envio
        const botaoEnviar = document.querySelector('.send-button');
        const textoOriginal = botaoEnviar.textContent;

        botaoEnviar.textContent = 'Enviando...';
        botaoEnviar.disabled = true;

        setTimeout(() => {
            alert(`Feedback enviado com sucesso!\n\nÁrea: ${area}\nNota: ${selectedRating} estrelas\nComentários: ${comentarios}`);

            // Reset do formulário
            document.getElementById('feedbackForm').reset();
            selectedRating = 0;
            highlightStars(0);

            botaoEnviar.textContent = textoOriginal;
            botaoEnviar.disabled = false;
        }, 2000);
    });
});