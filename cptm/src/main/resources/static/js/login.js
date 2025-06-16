function showLoginInvalidoPopup() {
    document.getElementById('loginInvalidoPopup').classList.add('active');
}

function mostrarVerificacaoRecuperacao() {
    document.getElementById('verificacaoRecuperacaoPopup').classList.add('active');
}

function processarVerificacao() {
    const email = document.getElementById('emailRecuperacao').value;
    const cpf = document.getElementById('cpfRecuperacao').value;

    if (!email || !cpf) {
        showErroVerificacao('Por favor, preencha todos os campos.');
        return;
    }

    if (!validarEmail(email)) {
        showErroVerificacao('Por favor, insira um email válido.');
        return;
    }

    if (!validarCPF(cpf)) {
        showErroVerificacao('Por favor, insira um CPF válido.');
        return;
    }

    fetch('/cptm+/verificar-dados-recuperacao', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            email: email,
            cpf: cpf
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.sucesso) {
                window.location.href = `/cptm+/recuperar-senha?id=${data.usuarioId}`;
            } else {
                showErroVerificacao('Não foi encontrada uma conta com estes dados.');
            }
        })
        .catch(error => {
            console.error('Erro:', error);
            showErroVerificacao('Erro interno. Tente novamente mais tarde.');
        });
}

function validarEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function validarCPF(cpf) {
    cpf = cpf.replace(/[^\d]/g, '');

    if (cpf.length !== 11) {
        return false;
    }

    if (/^(\d)\1{10}$/.test(cpf)) {
        return false;
    }

    return true;
}

function showErroVerificacao(mensagem) {
    document.getElementById('mensagemErroVerificacao').textContent = mensagem;
    document.getElementById('erroVerificacaoPopup').classList.add('active');
}

function limparCamposVerificacao() {
    document.getElementById('emailRecuperacao').value = '';
    document.getElementById('cpfRecuperacao').value = '';
}

function hidePopup(popupId) {
    document.getElementById(popupId).classList.remove('active');

    if (popupId === 'verificacaoRecuperacaoPopup') {
        limparCamposVerificacao();
    }
}

document.querySelectorAll('.popup-overlay').forEach(overlay => {
    overlay.addEventListener('click', function (e) {
        if (e.target === this) {
            this.classList.remove('active');
            if (this.id === 'verificacaoRecuperacaoPopup') {
                limparCamposVerificacao();
            }
        }
    });
});

document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') {
        document.querySelectorAll('.popup-overlay.active').forEach(popup => {
            popup.classList.remove('active');
            if (popup.id === 'verificacaoRecuperacaoPopup') {
                limparCamposVerificacao();
            }
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const loginModal = document.getElementById('loginModal');
    if (loginModal) {
        showLoginInvalidoPopup();
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const cpfInput = document.getElementById('cpfRecuperacao');
    if (cpfInput) {
        cpfInput.addEventListener('input', function (e) {
            let value = e.target.value.replace(/\D/g, '');
            value = value.replace(/(\d{3})(\d)/, '$1.$2');
            value = value.replace(/(\d{3})(\d)/, '$1.$2');
            value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
            e.target.value = value;
        });
    }
});

