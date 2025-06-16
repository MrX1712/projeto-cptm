let formEnviado = false;

function validarSenhas() {
    const novaSenha = document.getElementById('novaSenha').value;
    const confirmarSenha = document.getElementById('confirmarSenha').value;

    if (novaSenha !== confirmarSenha) {
        showErroPopup('As senhas não coincidem. Tente novamente.');
        return false;
    }

    if (novaSenha.length < 6) {
        showErroPopup('A senha deve ter pelo menos 6 caracteres.');
        return false;
    }

    return true;
}

function showErroPopup(mensagem) {
    console.log('Mostrando erro:', mensagem);
    document.getElementById('mensagemErro').textContent = mensagem;
    document.getElementById('erroPopup').classList.add('active');
}

function showLoadingPopup() {
    console.log('Mostrando loading...');
    document.getElementById('loadingPopup').classList.add('active');
}

function hideLoadingPopup() {
    console.log('Escondendo loading...');
    document.getElementById('loadingPopup').classList.remove('active');
}

function showSucessoPopup() {
    console.log('=== MOSTRANDO POP-UP DE SUCESSO ===');
    hideLoadingPopup();
    const popup = document.getElementById('sucessoPopup');
    popup.classList.add('active');
    console.log('Pop-up adicionado classe active:', popup.classList.contains('active'));

    const form = document.getElementById('recuperarForm');
    const inputs = form.querySelectorAll('input, button');
    inputs.forEach(input => input.disabled = true);
}

function hidePopup(popupId) {
    document.getElementById(popupId).classList.remove('active');
}

function irParaLogin() {
    console.log('Redirecionando para login...');
    window.location.href = '/cptm+/login';
}

document.querySelectorAll('.popup-overlay').forEach(overlay => {
    overlay.addEventListener('click', function (e) {
        if (e.target === this && this.id !== 'loadingPopup') {
            this.classList.remove('active');
        }
    });
});

document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') {
        document.querySelectorAll('.popup-overlay.active').forEach(popup => {
            if (popup.id !== 'loadingPopup') {
                popup.classList.remove('active');
            }
        });
    }
});

document.getElementById('recuperarForm').addEventListener('submit', function (e) {
    console.log('=== FORMULÁRIO SENDO ENVIADO ===');

    if (formEnviado) {
        console.log('Formulário já foi enviado, prevenindo reenvio');
        e.preventDefault();
        return;
    }

    if (!validarSenhas()) {
        console.log('Validação falhou');
        e.preventDefault();
        return;
    }

    formEnviado = true;
    showLoadingPopup();

    const submitBtn = this.querySelector('button[type="submit"]');
    submitBtn.disabled = true;
    submitBtn.textContent = 'Processando...';

    console.log('Formulário validado, enviando para servidor...');
});

document.addEventListener('DOMContentLoaded', function () {
    console.log('=== DOM CARREGADO ===');

    const sucessoElement = document.querySelector('meta[name="senha-alterada"]');
    const sucesso1 = sucessoElement ? sucessoElement.getAttribute('content') === 'true' : false;
    console.log('Sucesso via meta tag:', sucesso1);

    const urlParams = new URLSearchParams(window.location.search);
    const sucesso2 = urlParams.get('sucesso') === 'true';
    console.log('Sucesso via URL param:', sucesso2);

    const hiddenElement = document.getElementById('senhaAlteradaFlag');
    const sucesso3 = hiddenElement ? hiddenElement.value === 'true' : false;
    console.log('Sucesso via hidden element:', sucesso3);

    const sucesso = sucesso1 || sucesso2 || sucesso3;

    console.log('SUCESSO FINAL:', sucesso);

    if (sucesso) {
        console.log('=== SUCESSO DETECTADO, MOSTRANDO POP-UP ===');
        setTimeout(() => {
            showSucessoPopup();
        }, 100);

        setTimeout(() => {
            irParaLogin();
        }, 3000);
    }

    const erroElement = document.querySelector('meta[name="erro-senha"]');
    const erro = erroElement ? erroElement.getAttribute('content') : '';

    console.log('Erro detectado:', erro);

    if (erro && erro.trim() !== '') {
        console.log('Mostrando erro do servidor:', erro);
        hideLoadingPopup();
        showErroPopup(erro);
        formEnviado = false;

        const submitBtn = document.querySelector('button[type="submit"]');
        if (submitBtn) {
            submitBtn.disabled = false;
            submitBtn.textContent = 'Alterar Senha';
        }
    }

    console.log('Para testar o pop-up manualmente, execute: showSucessoPopup()');
});

document.getElementById('confirmarSenha').addEventListener('input', function () {
    const novaSenha = document.getElementById('novaSenha').value;
    const confirmarSenha = this.value;

    if (confirmarSenha.length > 0) {
        if (novaSenha === confirmarSenha) {
            this.style.borderColor = '#4CAF50';
        } else {
            this.style.borderColor = '#f44336';
        }
    } else {
        this.style.borderColor = '#f44336';
    }
});

document.getElementById('novaSenha').addEventListener('input', function () {
    const confirmarSenha = document.getElementById('confirmarSenha');
    if (confirmarSenha.value.length > 0) {
        confirmarSenha.style.borderColor = '#f44336';
    }
    confirmarSenha.dispatchEvent(new Event('input'));
});
