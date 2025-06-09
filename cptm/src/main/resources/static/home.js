const track = document.querySelector('.notification-track');
const slides = document.querySelectorAll('.notification-item');

let currentIndex = 0;
let isUserScrolling = false;
let autoScrollTimeout;

function scrollToIndex(index) {
    if (index < 0) index = slides.length - 1;
    if (index >= slides.length) index = 0;
    currentIndex = index;
    const slideHeight = slides[0].offsetHeight + 15;
    track.scrollTo({ top: slideHeight * index, behavior: 'smooth' });
}

function autoScroll() {
    if (isUserScrolling) return;
    scrollToIndex(currentIndex + 1);
    autoScrollTimeout = setTimeout(autoScroll, 8000);
}

track.addEventListener('scroll', () => {
    isUserScrolling = true;
    clearTimeout(autoScrollTimeout);

    clearTimeout(track.userScrollTimeout);
    track.userScrollTimeout = setTimeout(() => {
        isUserScrolling = false;
        autoScroll();
    }, 3000);

    const scrollTop = track.scrollTop;
    const slideHeight = slides[0].offsetHeight + 15;
    currentIndex = Math.round(scrollTop / slideHeight);
});

autoScroll();

// === FUNÇÕES DOS POP-UPS ===

// Função para verificar se o usuário está logado
function isUserLoggedIn() {
    return document.body.getAttribute('data-logado') === 'true';
}

// Função para verificar login e redirecionar para Ajuda
function verificarLoginERedirecionarAjuda() {
    if (isUserLoggedIn()) {
        location.href = '/cptm+/ajuda';
    } else {
        abrirPopup('popup-ajuda');
    }
}

// Função para verificar login e redirecionar para Feedback
function verificarLoginERedirecionarFeedback() {
    if (isUserLoggedIn()) {
        location.href = '/cptm+/feedback';
    } else {
        abrirPopup('popup-feedback');
    }
}

// Função para abrir pop-up
function abrirPopup(popupId) {
    const popup = document.getElementById(popupId);
    if (popup) {
        popup.classList.add('active');
    }
}

// Função para fechar pop-up
function fecharPopup(popupId) {
    const popup = document.getElementById(popupId);
    if (popup) {
        popup.classList.remove('active');
    }
}

// Verificar se existe mensagem ao carregar a página
document.addEventListener('DOMContentLoaded', function() {
    const popup = document.getElementById('popup-mensagem');
    if (popup) {
        // Mostrar o pop-up automaticamente se existe
        abrirPopup('popup-mensagem');
    }
});

// Fechar pop-up clicando no fundo escuro
document.addEventListener('click', function(e) {
    const popups = ['popup-mensagem', 'popup-ajuda', 'popup-feedback'];

    popups.forEach(popupId => {
        const popup = document.getElementById(popupId);
        if (popup && e.target === popup) {
            fecharPopup(popupId);
        }
    });
});

// Fechar pop-up com tecla ESC
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        const popups = ['popup-mensagem', 'popup-ajuda', 'popup-feedback'];

        popups.forEach(popupId => {
            const popup = document.getElementById(popupId);
            if (popup && popup.classList.contains('active')) {
                fecharPopup(popupId);
            }
        });
    }
});