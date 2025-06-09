
    const sentidoPorLinha = {
    rubi: ["Luz", "Jundiaí"],
    turquesa: ["Luz", "Rio Grande da Serra"],
    coral: ["Luz", "Estudantes"],
    safira: ["Brás", "Calmon Viana"],
    jade: ["Eng. Goulart", "Aeroporto Guarulhos"]
};

    const colorMap = {
    rubi: '#D7515E',
    turquesa: '#40E0D0',
    coral: '#FFA07A',
    safira: '#8A2BE2',
    jade: '#00A96B'
};

    let linhaSelecionada = null;

    function manusearClique(botao) {
        // Remove classe 'selecionada' de todos os botões
        document.querySelectorAll('.linha-btn').forEach(b => b.classList.remove('selecionada'));

        // Adiciona a classe ao botão clicado
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
    select.innerHTML = '<option value="">Carregando estações...</option>';

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
    select.style.backgroundColor = colorMap[linhaClass];
    select.style.borderColor = colorMap[linhaClass];
    select.style.color = 'white';
});

    document.getElementById('estacaoSelecionadaTexto').textContent = 'Nenhuma estação selecionada';
    document.getElementById('sentidoSelecionadoTexto').textContent = 'Nenhum sentido selecionado';
    document.getElementById('sentidoSelect').innerHTML = '<option value="">Selecionar sentido</option>';
    document.getElementById('trainBox').style.display = 'none';
}

    document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('estacoesSelect').addEventListener('change', function () {
        const estacaoTexto = this.options[this.selectedIndex].text;
        document.getElementById('estacaoSelecionadaTexto').textContent = estacaoTexto;
        document.getElementById('estacaoSelecionadaTexto').style.color = colorMap[linhaSelecionada];

        const sentidoSelect = document.getElementById('sentidoSelect');
        sentidoSelect.innerHTML = '<option value="">Selecionar sentido</option>';
        sentidoPorLinha[linhaSelecionada].forEach(sentido => {
            const opt = document.createElement('option');
            opt.value = sentido;
            opt.textContent = sentido;
            sentidoSelect.appendChild(opt);
        });
        sentidoSelect.style.backgroundColor = colorMap[linhaSelecionada];
        sentidoSelect.style.color = 'white';
    });

    document.getElementById('sentidoSelect').addEventListener('change', function () {
    const sentidoTexto = this.options[this.selectedIndex].text;
    document.getElementById('sentidoSelecionadoTexto').textContent = sentidoTexto;
    document.getElementById('sentidoSelecionadoTexto').style.color = colorMap[linhaSelecionada];
    document.getElementById('trainBox').style.display = 'block';
    document.getElementById('trainInfo').innerHTML = 'Próximo trem: Chegando em <span id="tempoChegada" style="font-weight: bold;">3 min</span>';
    document.getElementById('tempoChegada').style.color = colorMap[linhaSelecionada];

});
});

