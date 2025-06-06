const estacoesData = {
    'palmeiras': {
        nome: 'Palmeiras Barra Funda',
        linha: 'Linha 7 - Rubi',
        tempo: '3 min',
        cor: '#D7515E'
    },
    'luz': {
        nome: 'Luz',
        linha: 'Linha 1 - Azul / Linha 4 - Amarela / Linha 7 - Rubi',
        tempo: '5 min',
        cor: '#0066CC'
    },
    'bras': {
        nome: 'Brás',
        linha: 'Linha 3 - Vermelha / Linha 11 - Coral / Linha 12 - Safira',
        tempo: '2 min',
        cor: '#D7515E'
    }
};

// Função para buscar rotas
document.getElementById('partida').addEventListener('input', function(e) {
    const partida = e.target.value.toLowerCase();

    if (partida.length > 2) {
        console.log(`Local de partida: ${partida}`);
        // Simular busca de estações próximas
        setTimeout(() => {
            if (partida.includes('palmeiras') || partida.includes('barra funda')) {
                alert('Estação Palmeiras Barra Funda encontrada!');
            }
        }, 500);
    }
});

document.getElementById('chegada').addEventListener('input', function(e) {
    const chegada = e.target.value.toLowerCase();

    if (chegada.length > 2) {
        console.log(`Local de chegada: ${chegada}`);
        // Simular cálculo de rota
        setTimeout(() => {
            const partida = document.getElementById('partida').value;
            if (partida && chegada) {
                alert(`Calculando rota de ${partida} para ${chegada}...`);
            }
        }, 500);
    }
});


// Interação com marcadores do mapa
document.querySelectorAll('.marker').forEach(marker => {
    marker.addEventListener('click', function() {
        const estacao = this.getAttribute('data-estacao');
        alert(`Você clicou na ${estacao}`);
    });
});