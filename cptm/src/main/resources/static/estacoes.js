let map;
let directionsService;
let directionsRenderer;

window.initMap = function () {
    map = new google.maps.Map(document.getElementById("google-map"), {
        zoom: 14,
        center: { lat: -23.533773, lng: -46.625290 } // Centro de SP
    });

    directionsService = new google.maps.DirectionsService();
    directionsRenderer = new google.maps.DirectionsRenderer();
    directionsRenderer.setMap(map);
};

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('estacaoForm');
    const buscarButton = document.getElementById('buscar-estacao');

    // Adicionar listener ao botão
    buscarButton.addEventListener('click', buscarEstacao);

    // Adicionar listener ao form para prevenir submit padrão
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        buscarEstacao();
    });
});

async function buscarEstacao() {
    const endereco = document.getElementById("partida").value.trim();
    const linha = document.getElementById("linha").value;
    const infoDiv = document.getElementById("info-estacao");

    // Limpar resultado anterior
    infoDiv.innerHTML = "";

    // Validação
    if (!endereco || !linha) {
        mostrarErro("Preencha todos os campos.");
        return;
    }

    // Mostrar loading
    mostrarLoading();

    try {
        // 1. Geocodifica o endereço
        const geoRes = await fetch(`https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(endereco)}&key=AIzaSyB-tgvCbmlPEYQuEj_Chai_FmI-Ege3eMQ`);
        const geoData = await geoRes.json();

        if (geoData.status !== "OK") {
            mostrarErro("Endereço não encontrado. Verifique o local digitado.");
            return;
        }

        const origem = geoData.results[0].geometry.location;

        // 2. Chama o backend para pegar a estação mais próxima da linha
        const res = await fetch(`/cptm+/estacoes/mais-proxima?linha=${linha}&lat=${origem.lat}&lng=${origem.lng}`);

        if (!res.ok) {
            throw new Error('Erro na busca da estação');
        }

        const estacao = await res.json();

        if (!estacao || !estacao.latitude || !estacao.longitude) {
            mostrarErro("Não foi possível encontrar uma estação para essa linha.");
            return;
        }

        // 3. Atualiza o texto com nome da estação
        mostrarResultado(linha, estacao.nome);

        // 4. Traça a rota até a estação
        const destino = { lat: estacao.latitude, lng: estacao.longitude };

        const rota = {
            origin: origem,
            destination: destino,
            travelMode: google.maps.TravelMode.WALKING
        };

        directionsService.route(rota, function (result, status) {
            if (status === "OK") {
                directionsRenderer.setDirections(result);
            } else {
                console.error("Não foi possível traçar a rota:", status);
            }
        });

    } catch (error) {
        console.error('Erro:', error);
        mostrarErro("Ocorreu um erro ao buscar a estação. Tente novamente.");
    }
}

function mostrarLoading() {
    const infoDiv = document.getElementById("info-estacao");
    infoDiv.innerHTML = `
        <div style="text-align: center; color: #666;">
            <div style="margin-bottom: 10px;">🔍</div>
            Buscando estação mais próxima...
        </div>
    `;
}

function mostrarResultado(linha, nomeEstacao) {
    const infoDiv = document.getElementById("info-estacao");
    const linhaFormatada = formatarNomeLinha(linha);

    infoDiv.innerHTML = `
        <div style="text-align: center;">
            <div style="margin-bottom: 8px; color: #f44336; font-size: 18px;">📍</div>
            <div>Estação mais próxima da <strong>${linhaFormatada}</strong>:</div>
            <div style="font-size: 18px; font-weight: bold; color: #f44336; margin-top: 5px;">
                ${nomeEstacao}
            </div>
        </div>
    `;
}

function mostrarErro(mensagem) {
    const infoDiv = document.getElementById("info-estacao");
    infoDiv.innerHTML = `
        <div style="text-align: center; color: #d32f2f;">
            <div style="margin-bottom: 8px; font-size: 18px;">⚠️</div>
            ${mensagem}
        </div>
    `;
}

function formatarNomeLinha(linhaId) {
    const mapeamento = {
        'Rubi': 'Linha 7 - Rubi',
        'Turquesa': 'Linha 10 - Turquesa',
        'Coral': 'Linha 11 - Coral',
        'Safira': 'Linha 12 - Safira',
        'Jade': 'Linha 13 - Jade'
    };
    // como linha agora é um UUID, essa função é só pra fallback
    return mapeamento[linhaId] || `Linha`;
}
