function carregarEstacoes() {
    const linhaId = document.getElementById('linha').value;
    const estacaoSelect = document.getElementById('estacao');
    const loadingIndicator = document.getElementById('loading-estacoes');

    console.log("Linha selecionada:", linhaId);

    if (linhaId) {
        loadingIndicator.style.display = "block";
        estacaoSelect.classList.add("loading");
        estacaoSelect.disabled = true;
        estacaoSelect.innerHTML = '<option value="">Carregando...</option>';

        const url = `/cptm+/adm/painel-administrativo/notificacoes/estacoes/${linhaId}`;
        console.log("URL da requisição:", url);

        fetch(url, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                console.log("Status da resposta:", response.status);

                if (!response.ok) {
                    throw new Error(`Erro HTTP: ${response.status} - ${response.statusText}`);
                }

                return response.json();
            })
            .then(data => {
                console.log("Dados recebidos:", data);
                console.log("Quantidade de estações:", data ? data.length : 0);

                estacaoSelect.innerHTML = '<option value="">Todas as estações</option>';

                if (Array.isArray(data) && data.length > 0) {
                    data.forEach((estacao, index) => {
                        console.log(`Estação ${index}:`, estacao);

                        const option = document.createElement("option");
                        option.value = estacao.id;
                        option.textContent = estacao.nome;
                        estacaoSelect.appendChild(option);
                    });
                } else {
                    estacaoSelect.innerHTML = '<option value="">Nenhuma estação encontrada</option>';
                    console.log("Nenhuma estação encontrada");
                }
            })
            .catch(error => {
                console.error('Erro detalhado:', error);
                estacaoSelect.innerHTML = '<option value="">Erro ao carregar estações</option>';
                alert(`Erro ao carregar estações: ${error.message}`);
            })
            .finally(() => {
                loadingIndicator.style.display = "none";
                estacaoSelect.classList.remove("loading");
                estacaoSelect.disabled = false;
            });
    } else {
        estacaoSelect.innerHTML = '<option value="">Todas as estações</option>';
        loadingIndicator.style.display = "none";
        estacaoSelect.classList.remove("loading");
        estacaoSelect.disabled = false;
    }
}
