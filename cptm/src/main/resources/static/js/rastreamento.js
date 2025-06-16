const sentidoPorLinha = {
    rubi: ["Luz", "Jundiaí"],
    turquesa: ["Luz", "Rio Grande da Serra"],
    coral: ["Luz", "Estudantes"],
    safira: ["Brás", "Calmon Viana"],
    jade: ["Eng. Goulart", "Aeroporto Guarulhos"]
};

const colorMap = {
    rubi: '#9b101f',
    turquesa: '#41e1d0',
    coral: '#fe7f51',
    safira: '#810081',
    jade: '#01a86a'
};

let linhaSelecionada = null;

function manusearClique(botao) {
    document.querySelectorAll('.linha-card').forEach(b => b.classList.remove('selecionada'));

    botao.classList.add('selecionada');

    const linhaClass = botao.getAttribute('data-linha');
    const nomeCompleto = botao.getAttribute('data-nome');
    const idLinha = botao.getAttribute('data-id');

    selecionarLinha(linhaClass, nomeCompleto, idLinha);
}

function selecionarLinha(linhaClass, nomeCompleto, idLinha) {
    linhaSelecionada = linhaClass;

    const textoElement = document.getElementById('linhaSelecionadaTexto');
    textoElement.textContent = nomeCompleto;
    textoElement.style.color = colorMap[linhaClass];

    const select = document.getElementById('estacoesSelect');
    const sentidoSelect = document.getElementById('sentidoSelect');

    select.innerHTML = '<option value="">Carregando estações...</option>';
    select.style.borderColor = colorMap[linhaClass];

    fetch('/cptm+/rastreamento/estacoes?idLinha=' + idLinha)
        .then(res => res.json())
        .then(estacoes => {
            select.innerHTML = '<option value="">Selecione uma estação</option>';
            estacoes.forEach(est => {
                const option = document.createElement('option');
                option.value = est.id;
                option.textContent = est.nome;
                select.appendChild(option);
            });

            select.style.borderColor = colorMap[linhaClass];
            sentidoSelect.style.borderColor = colorMap[linhaClass];
        })
        .catch(error => {
            console.error('Erro ao carregar estações:', error);
            select.innerHTML = '<option value="">Erro ao carregar estações</option>';
        });

    resetarSelecoes();
}

function resetarSelecoes() {
    const estacaoTexto = document.getElementById('estacaoSelecionadaTexto');
    const sentidoTexto = document.getElementById('sentidoSelecionadoTexto');
    const sentidoSelect = document.getElementById('sentidoSelect');

    estacaoTexto.textContent = 'Nenhuma estação selecionada';
    estacaoTexto.style.color = '#333';

    sentidoTexto.textContent = 'Nenhum sentido selecionado';
    sentidoTexto.style.color = '#333';

    sentidoSelect.innerHTML = '<option value="">Selecionar sentido</option>';
    document.getElementById('trainBox').style.display = 'none';
}

function atualizarTempoTrem() {
    const tempoElement = document.getElementById('tempoChegada');
    if (tempoElement && linhaSelecionada) {
        const tempo = Math.floor(Math.random() * 8) + 1;
        tempoElement.textContent = tempo + ' min';
        tempoElement.style.color = 'white';
    }
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('estacoesSelect').addEventListener('change', function () {
        if (this.value !== "") {
            const estacaoTexto = this.options[this.selectedIndex].text;
            const estacaoElement = document.getElementById('estacaoSelecionadaTexto');

            estacaoElement.textContent = estacaoTexto;
            estacaoElement.style.color = colorMap[linhaSelecionada];

            const sentidoSelect = document.getElementById('sentidoSelect');
            sentidoSelect.innerHTML = '<option value="">Selecionar sentido</option>';

            sentidoPorLinha[linhaSelecionada].forEach(sentido => {
                const opt = document.createElement('option');
                opt.value = sentido;
                opt.textContent = sentido;
                sentidoSelect.appendChild(opt);
            });

            const sentidoTextoElement = document.getElementById('sentidoSelecionadoTexto');
            sentidoTextoElement.textContent = 'Nenhum sentido selecionado';
            sentidoTextoElement.style.color = '#333';
        }
    });

    const sentidoSelect = document.getElementById('sentidoSelect');
    sentidoSelect.addEventListener('change', function (event) {
        const valorSelecionado = event.target.value;

        if (valorSelecionado && valorSelecionado !== '') {
            const textoSentido = event.target.options[event.target.selectedIndex].text;

            const elementoTexto = document.getElementById('sentidoSelecionadoTexto');
            elementoTexto.textContent = textoSentido;
            elementoTexto.style.color = colorMap[linhaSelecionada];

            const trainBox = document.getElementById('trainBox');
            trainBox.style.display = 'block';

            setTimeout(() => {
                trainBox.style.opacity = '1';
                trainBox.style.transform = 'translateY(0)';
            }, 100);

            atualizarTempoTrem();

            setInterval(atualizarTempoTrem, 30000);
        }
    });

    const trainBox = document.getElementById('trainBox');
    trainBox.style.opacity = '0';
    trainBox.style.transform = 'translateY(20px)';
    trainBox.style.transition = 'all 0.5s ease';
});