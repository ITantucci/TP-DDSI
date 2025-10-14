
const { API_HECHOS, API_COLECCIONES, API_SOLICITUDES, API_ESTADISTICAS,API_AGREGADOR } = window.METAMAPA;

async function testConexion() {
    const statusEl = document.createElement("div");
    statusEl.id = "estado-conexion";
    statusEl.style.padding = "6px";
    statusEl.style.fontWeight = "bold";
    document.body.prepend(statusEl);

    try {
        const resp = await fetch(`${window.METAMAPA.API_HECHOS}/hechos`, { method: "GET" });
        if (resp.ok) {
            statusEl.textContent = "🟢 Conectado al agregador";
            statusEl.style.color = "green";
        } else {
            statusEl.textContent = `🟡 Error HTTP ${resp.status}`;
            statusEl.style.color = "orange";
        }
    } catch (err) {
        statusEl.textContent = "🔴 No se pudo conectar al agregador";
        statusEl.style.color = "red";
        console.error("Error al conectar:", err);
    }
}

document.addEventListener("DOMContentLoaded", testConexion);

// Hechos
async function getHechos() {
    const r = await fetch(`${API_AGREGADOR}/hechos`);
    return r.json();
}

// Colecciones
async function getColecciones() {
    const r = await fetch(`${API_COLECCIONES}`);
    return r.json();
}

// Fuentes (si tenés otro controlador, agregalo igual)
async function getFuentes() {
    const r = await fetch(`${API_HECHOS}/fuentes`);
    return r.json();
}

// Estadísticas
async function getEstadisticas() {
    const r = await fetch(`${API_ESTADISTICAS}`);
    return r.json();
}
