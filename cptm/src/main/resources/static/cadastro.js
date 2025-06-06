function gerarAnos() {
    const anoAtual = new Date().getFullYear();
    const selectAno = document.getElementById('ano');

    for (let ano = anoAtual; ano >= 1900; ano--) {
        const option = document.createElement('option');
        option.value = ano;
        option.textContent = ano;
        selectAno.appendChild(option);
    }
}

// Função para gerar meses
function gerarMeses() {
    const meses = [
        'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
        'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'
    ];
    const selectMes = document.getElementById('mes');

    meses.forEach((mes, index) => {
        const option = document.createElement('option');
        option.value = index + 1;
        option.textContent = mes;
        selectMes.appendChild(option);
    });
}

// Função para gerar dias (depende do mês e ano selecionados)
function gerarDias() {
    const selectDia = document.getElementById('dia');
    const mes = parseInt(document.getElementById('mes').value);
    const ano = parseInt(document.getElementById('ano').value);

    // Limpar dias existentes
    selectDia.innerHTML = '<option value="">Dia</option>';

    if (mes && ano) {
        // Calcular quantos dias tem o mês
        const diasNoMes = new Date(ano, mes, 0).getDate();

        for (let dia = 1; dia <= diasNoMes; dia++) {
            const option = document.createElement('option');
            option.value = dia;
            option.textContent = dia;
            selectDia.appendChild(option);
        }
    } else {
        // Gerar 31 dias por padrão
        for (let dia = 1; dia <= 31; dia++) {
            const option = document.createElement('option');
            option.value = dia;
            option.textContent = dia;
            selectDia.appendChild(option);
        }
    }
}

// Máscara para CPF
function aplicarMascaraCPF(input) {
    let value = input.value.replace(/\D/g, '');
    if (value.length <= 11) {
        value = value.replace(/(\d{3})(\d)/, '$1.$2');
        value = value.replace(/(\d{3})(\d)/, '$1.$2');
        value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    }
    input.value = value;
}

// Validar CPF (algoritmo básico)
function validarCPF(cpf) {
    cpf = cpf.replace(/[^\d]/g, '');
    if (cpf.length !== 11) return false;

    // Verifica se todos os dígitos são iguais
    if (/^(\d)\1{10}$/.test(cpf)) return false;

    // Validação dos dígitos verificadores
    let soma = 0;
    for (let i = 0; i < 9; i++) {
        soma += parseInt(cpf.charAt(i)) * (10 - i);
    }
    let resto = 11 - (soma % 11);
    let dv1 = resto < 10 ? resto : 0;

    soma = 0;
    for (let i = 0; i < 10; i++) {
        soma += parseInt(cpf.charAt(i)) * (11 - i);
    }
    resto = 11 - (soma % 11);
    let dv2 = resto < 10 ? resto : 0;

    return dv1 === parseInt(cpf.charAt(9)) && dv2 === parseInt(cpf.charAt(10));
}

// Quando a página carregar
document.addEventListener('DOMContentLoaded', function() {
    gerarAnos();
    gerarMeses();
    gerarDias();

    // Event listeners para atualizar dias quando mês/ano mudar
    document.getElementById('mes').addEventListener('change', gerarDias);
    document.getElementById('ano').addEventListener('change', gerarDias);

    // Aplicar máscara no CPF
    document.getElementById('cpf').addEventListener('input', function() {
        aplicarMascaraCPF(this);
    });

    // Validação do formulário
    document.getElementById('cadastroForm').addEventListener('submit', function(e) {
        e.preventDefault();

        const nome = document.getElementById('nomeCompleto').value.trim();
        const dia = document.getElementById('dia').value;
        const mes = document.getElementById('mes').value;
        const ano = document.getElementById('ano').value;
        const email = document.getElementById('email').value.trim();
        const cpf = document.getElementById('cpf').value;
        const senha = document.getElementById('senha').value;

        // Validações
        if (!nome || nome.length < 2) {
            alert('Por favor, insira um nome válido.');
            return;
        }

        if (!dia || !mes || !ano) {
            alert('Por favor, selecione uma data de nascimento válida.');
            return;
        }

        // Verificar se é maior de idade
        const dataNascimento = new Date(ano, mes - 1, dia);
        const hoje = new Date();
        const idade = hoje.getFullYear() - dataNascimento.getFullYear();
        const m = hoje.getMonth() - dataNascimento.getMonth();
        if (m < 0 || (m === 0 && hoje.getDate() < dataNascimento.getDate())) {
            idade--;
        }

        if (idade < 16) {
            alert('Você deve ter pelo menos 16 anos para se cadastrar.');
            return;
        }

        // Validar e-mail
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            alert('Por favor, insira um e-mail válido.');
            return;
        }

        // Validar CPF
        if (!validarCPF(cpf)) {
            alert('Por favor, insira um CPF válido.');
            return;
        }

        // Validar senha
        if (senha.length < 6) {
            alert('A senha deve ter pelo menos 6 caracteres.');
            return;
        }

        // Simular cadastro
        const botaoCadastrar = document.querySelector('.btn-submit');
        const textoOriginal = botaoCadastrar.textContent;

        botaoCadastrar.textContent = 'Cadastrando...';
        botaoCadastrar.disabled = true;

        setTimeout(() => {
            alert(`Cadastro realizado com sucesso!\n\nBem-vindo(a), ${nome}!`);

            // Reset do formulário
            document.getElementById('cadastroForm').reset();
            gerarDias(); // Resetar dias para o padrão

            botaoCadastrar.textContent = textoOriginal;
            botaoCadastrar.disabled = false;

            // Redirecionar para login
            // window.location.href = '/cptm+/login';
        }, 2000);
    });
});