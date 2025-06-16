function carregarEstacoes() {
    const linhaId = document.getElementById('linha').value;
    const estacaoSelect = document.getElementById('estacao');

    estacaoSelect.innerHTML = '<option value="">Todas as estações</option>';

    if (linhaId) {
        fetch(`/cptm+/adm/painel-administrativo/notificacoes/estacoes/${linhaId}`)
            .then(response => response.json())
            .then(estacoes => {
                estacoes.forEach(estacao => {
                    const option = document.createElement('option');
                    option.value = estacao.id;
                    option.textContent = estacao.nome;
                    estacaoSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Erro ao carregar estações:', error));
    }
}
