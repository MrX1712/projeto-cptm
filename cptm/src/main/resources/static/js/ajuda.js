document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("formAjuda");
    const logado = document.body.getAttribute("data-logado") === "true";

    console.log("Status de login:", logado);

    const mensagemElement = document.querySelector('.mensagem span');
    if (mensagemElement && mensagemElement.textContent.trim()) {
        showSuccessPopup();
    }

    form.addEventListener("submit", function (e) {
        if (!logado) {
            e.preventDefault();
            showLoginRequiredPopup();
        }
    });
});

function filtrarEstacoes() {
    const linhaSelect = document.getElementById("linha");
    const estacaoSelect = document.getElementById("estacaoSelect");
    const linhaSelecionada = linhaSelect.value;

    estacaoSelect.value = "";

    const opcoes = estacaoSelect.querySelectorAll("option");
    opcoes.forEach(opcao => {
        if (opcao.value === "") {
            opcao.style.display = "block";
            return;
        }

        const idLinha = opcao.getAttribute("data-idlinha");
        if (!linhaSelecionada || idLinha === linhaSelecionada) {
            opcao.style.display = "block";
        } else {
            opcao.style.display = "none";
        }
    });
}

function showSuccessPopup() {
    document.getElementById('successPopup').classList.add('active');
}

function showLoginRequiredPopup() {
    document.getElementById('loginRequiredPopup').classList.add('active');
}

function hidePopup(popupId) {
    document.getElementById(popupId).classList.remove('active');
}

function goToLogin() {
    window.location.href = "/cptm+/login";
}

document.querySelectorAll('.popup-overlay').forEach(overlay => {
    overlay.addEventListener('click', function (e) {
        if (e.target === this) {
            this.classList.remove('active');
        }
    });
});

document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') {
        document.querySelectorAll('.popup-overlay.active').forEach(popup => {
            popup.classList.remove('active');
        });
    }
});