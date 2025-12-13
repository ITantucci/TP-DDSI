// === Visualización de estadísticas MetaMapa ===
console.log("✅ stats js cargado correctamente");
const getEstadisticas = async () => (await fetch(`${API_BASE}/estadisticas`)).json();

const crearChart = (id, type, label, data, extraOptions = {}) => {
    const ctx = document.getElementById(id);
    if (!ctx || !data) return;
    new Chart(ctx, {
        type,
        data: {
            labels: Object.keys(data),
            datasets: [{ label, data: Object.values(data) }]
        },
        options: { responsive: true, plugins: { legend: { display: false } }, ...extraOptions }
    });
};

// De una colección, ¿en qué provincia se agrupan la mayor cantidad de hechos reportados?
async function obtenerProvinciaMasReportadaColeccion(uuid) {
    try {
        const response = await fetch(`${window.METAMAPA.API_ESTADISTICA}/coleccion/${uuid}/provincia-mas-reportada`);
        if (response.status === 204) return null; // No hay datos
        const data = await response.json();
        return data.provincia;
    } catch (error) {
        console.error("Error al obtener la provincia más reportada de la colección:", error);
        return null;
    }
}

// ¿Cuál es la categoría con mayor cantidad de hechos reportados?
async function obtenerCategoriaMasReportada() {
    try {
        const response = await fetch(`${window.METAMAPA.API_ESTADISTICA}/categoria`);
        if (response.status === 204) return null;
        const data = await response.json();
        return data.categoria;
    } catch (error) {
        console.error("Error al obtener la categoría más reportada:", error);
        return null;
    }
}

// ¿En qué provincia se presenta la mayor cantidad de hechos de una cierta categoría?
async function obtenerProvinciaMasReportadaPorCategoria(categoria) {
    try {
        const params = new URLSearchParams({ categoria });
        const response = await fetch(`${window.METAMAPA.API_ESTADISTICA}/hechos/provincia-mas-reportada?${params}`);
        if (response.status === 204) return null; // No hay datos
        const data = await response.json();
        return data.provincia;
    } catch (error) {
        console.error("Error al obtener la provincia más reportada por categoría:", error);
        return null;
    }
}

//  ¿A qué hora del día ocurren la mayor cantidad de hechos de una cierta categoría?
async function obtenerHoraMasReportadaPorCategoria(categoria) {
    try {
        const params = new URLSearchParams({ categoria });
        const response = await fetch(`${window.METAMAPA.API_ESTADISTICA}/hechos/hora?${params}`);
        if (response.status === 204) return null;
        const data = await response.json();
        return parseInt(data.hora, 10);
    } catch (error) {
        console.error("Error al obtener la hora más reportada por categoría:", error);
        return null;
    }
}

// ¿Cuántas solicitudes de eliminación son spam?
async function obtenerCantidadSolicitudesSpam() {
    try {
        const response = await fetch(`${window.METAMAPA.API_ESTADISTICA}/spam`);
        if (!response.ok) throw new Error("Error al obtener solicitudes spam");
        const data = await response.json();
        return parseInt(data.cantidad) || 0;
    } catch (error) {
        console.error("Error al contar solicitudes spam:", error);
        return 0;
    }
}

// Muestra gráficos dentro del contenedor principal
async function renderEstadisticas() {
    const cont = document.getElementById("contenido");
    cont.innerHTML = `<h2>Estadísticas</h2>
    <div class="charts">
      <canvas id="chartCategorias"></canvas>
      <canvas id="chartProvincias"></canvas>
      <canvas id="chartSolicitudes"></canvas>
    </div>`;

    try {
        const data = await getEstadisticas();

        // 1. Hechos por categoría
        crearChart("chartCategorias", "bar", "Hechos por categoría", data.hechosPorCategoria);
        // 2. Hechos por provincia
        crearChart("chartProvincias", "pie", "Hechos por provincia", data.hechosPorProvincia, {
            plugins: { legend: { display: true } }
        });
        // 3. Solicitudes de eliminación (spam / válidas)
        if (data.solicitudes) {
            const { spam = 0, validas = 0 } = data.solicitudes;
            new Chart(document.getElementById("chartSolicitudes"), {
                type: "doughnut",
                data: {
                    labels: ["Spam", "Válidas"],
                    datasets: [{
                        data: [spam, validas],
                        backgroundColor: ["#dc3545", "#28a745"]
                    }]
                },
                options: { responsive: true }
            });
        }
    } catch (err) {
        cont.innerHTML = `<div class="error">Error cargando estadísticas: ${err}</div>`;
    }
}