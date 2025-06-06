const colorMap = {
    rubi: '#9b111e',
    turquesa: '#40E0D0',
    coral: '#FF7F50',
    safira: '#800080',
    jade: '#00A96B'
};

// Estações por linha (uma de cada)
const estacoesPorLinha = {
    rubi: [{ value: 'palmeiras-barra-funda', text: 'Palmeiras Barra Funda' }],
    turquesa: [{ value: 'santo-andre', text: 'Santo André' }],
    coral: [{ value: 'corinthians-itaquera', text: 'Corinthians-Itaquera' }],
    safira: [{ value: 'bras', text: 'Brás' }],
    jade: [{ value: 'aeroporto-guarulhos', text: 'Aeroporto-Guarulhos' }]
};

function changeColor(select) {
    const selectedValue = select.value;
    if (selectedValue && colorMap[selectedValue]) {
        select.style.backgroundColor = colorMap[selectedValue];
        select.style.color = 'white';
        select.style.borderColor = colorMap[selectedValue];
    } else {
        select.style.backgroundColor = 'white';
        select.style.color = '#333';
        select.style.borderColor = '#e0e0e0';
    }
}

// Quando a página carregar
document.addEventListener('DOMContentLoaded', function() {
    const textarea = document.getElementById('comentario');
    const placeholderText = textarea.getAttribute('placeholder');

    // Event listener para seleção de linha
    document.getElementById('linha').addEventListener('change', function() {
        const linhaSelecionada = this.value;
        const estacaoSelect = document.getElementById('estacao');

        // Limpar estações
        estacaoSelect.innerHTML = '<option value="">Selecione uma estação</option>';
        estacaoSelect.style.backgroundColor = 'white';
        estacaoSelect.style.color = '#333';
        estacaoSelect.style.borderColor = '#e0e0e0';

        // Adicionar estação se linha foi selecionada
        if (linhaSelecionada && estacoesPorLinha[linhaSelecionada]) {
            estacoesPorLinha[linhaSelecionada].forEach(estacao => {
                const option = document.createElement('option');
                option.value = estacao.value;
                option.textContent = estacao.text;
                estacaoSelect.appendChild(option);
            });
        }
    });

    // Event listener para seleção de estação
    document.getElementById('estacao').addEventListener('change', function() {
        const linhaSelecionada = document.getElementById('linha').value;

        if (this.value && linhaSelecionada && colorMap[linhaSelecionada]) {
            this.style.backgroundColor = colorMap[linhaSelecionada];
            this.style.color = 'white';
            this.style.borderColor = colorMap[linhaSelecionada];
        } else if (this.value) {
            this.style.backgroundColor = '#9b111e';
            this.style.color = 'white';
            this.style.borderColor = '#9b111e';
        } else {
            this.style.backgroundColor = 'white';
            this.style.color = '#333';
            this.style.borderColor = '#e0e0e0';
        }
    });

    // Validação e envio do formulário
    document.getElementById('ajudaForm').addEventListener('submit', function(e) {
        e.preventDefault();

        const linha = document.getElementById('linha').value;
        const estacao = document.getElementById('estacao').value;
        const problema = document.getElementById('problema').value;
        const comentario = document.getElementById('comentario').value.trim();

        if (!linha) {
            alert('Por favor, selecione uma linha.');
            return;
        }

        if (!estacao) {
            alert('Por favor, selecione uma estação.');
            return;
        }

        if (!problema) {
            alert('Por favor, selecione o tipo de problema.');
            return;
        }

        if (!comentario) {
            alert('Por favor, descreva o problema nos comentários.');
            return;
        }

        // Simular acionamento
        const botaoAcionar = document.querySelector('.acionar-btn');
        const textoOriginal = botaoAcionar.textContent;

        botaoAcionar.textContent = 'Acionando...';
        botaoAcionar.disabled = true;

        setTimeout(() => {
            const estacaoTexto = document.getElementById('estacao').options[document.getElementById('estacao').selectedIndex].text;
            const linhaTexto = document.getElementById('linha').options[document.getElementById('linha').selectedIndex].text;
            const problemaTexto = document.getElementById('problema').options[document.getElementById('problema').selectedIndex].text;

            alert(`Ajuda acionada com sucesso!\n\nLinha: ${linhaTexto}\nEstação: ${estacaoTexto}\nProblema: ${problemaTexto}\n\nUma equipe será enviada em breve.`);

            // Reset do formulário
            document.getElementById('ajudaForm').reset();
            document.getElementById('linha').style.backgroundColor = 'white';
            document.getElementById('linha').style.color = '#333';
            document.getElementById('estacao').style.backgroundColor = 'white';
            document.getElementById('estacao').style.color = '#333';
            document.getElementById('estacao').innerHTML = '<option value="">Selecione uma linha primeiro</option>';

            botaoAcionar.textContent = textoOriginal;
            botaoAcionar.disabled = false;
        }, 2000);
    });
});