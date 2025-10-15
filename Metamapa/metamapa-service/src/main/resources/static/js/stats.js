// === Visualización de estadísticas MetaMapa ===
console.log("✅ stats js cargado correctamente");
// Consulta al backend
async function getEstadisticas() {
    const r = await fetch(`${API_BASE}/estadisticas`);
    return r.json();
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
        if (data.hechosPorCategoria) {
            new Chart(document.getElementById("chartCategorias"), {
                type: "bar",
                data: {
                    labels: Object.keys(data.hechosPorCategoria),
                    datasets: [{
                        label: "Hechos por categoría",
                        data: Object.values(data.hechosPorCategoria)
                    }]
                },
                options: { responsive: true, plugins: { legend: { display: false } } }
            });
        }

        // 2. Hechos por provincia
        if (data.hechosPorProvincia) {
            new Chart(document.getElementById("chartProvincias"), {
                type: "pie",
                data: {
                    labels: Object.keys(data.hechosPorProvincia),
                    datasets: [{
                        label: "Hechos por provincia",
                        data: Object.values(data.hechosPorProvincia)
                    }]
                },
                options: { responsive: true }
            });
        }

        // 3. Solicitudes de eliminación (spam / válidas)
        if (data.solicitudes) {
            const spam = data.solicitudes.spam ?? 0;
            const validas = data.solicitudes.validas ?? 0;
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
