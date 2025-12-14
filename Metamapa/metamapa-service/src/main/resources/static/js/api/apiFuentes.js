// Obtener todas las fuentes registradas en el agregador
async function obtenerFuentes() {
    const resp = await fetch(`${window.METAMAPA.API_AGREGADOR}/fuenteDeDatos`);
    return resp.ok ? resp.json() : [];
}

async function obtenerFuentesDinamicas() {
    const resp = await fetchSeguro(`${window.METAMAPA.API_FUENTE_DINAMICA}/`);
    if (!resp)
        return { disponible: false, fuentes: [] };
    if (!resp.ok)
        return { disponible: true, fuentes: [] };
    return {
        disponible: true,
        fuentes: await resp.json()
    };
}

async function obtenerFuentesEstaticas() {
    const resp = await fetchSeguro(`${window.METAMAPA.API_FUENTE_ESTATICA}/`);
    if (!resp)
        return { disponible: false, fuentes: [] };
    if (!resp.ok)
        return { disponible: true, fuentes: [] };
    return {
        disponible: true,
        fuentes: await resp.json()
    };
}

async function obtenerFuentesDemo() {
    const resp = await fetchSeguro(`${window.METAMAPA.API_FUENTE_DEMO}/`);
    if (!resp)
        return { disponible: false, fuentes: [] };
    if (!resp.ok)
        return { disponible: true, fuentes: [] };
    return {
        disponible: true,
        fuentes: await resp.json()
    }
}

async function obtenerFuentesMetamapa() {
    const resp = await fetchSeguro(`${window.METAMAPA.API_FUENTE_METAMAPA}/`);
    if (!resp)
        return { disponible: false, fuentes: [] };
    if (!resp.ok)
        return { disponible: true, fuentes: [] };
    return {
        disponible: true,
        fuentes: await resp.json()
    }
}

async function fetchSeguro(url) {
    const controller = new AbortController();
    const timeout = setTimeout(() => controller.abort(), 1000);
    try {
        return await fetch(url, { signal: controller.signal });
    } catch {
        console.warn("Servicio no disponible:", url);
        return null;
    } finally {
        clearTimeout(timeout);
    }
}

async function crearFuenteDinamica(nombre) {
    const resp = await fetch(`${window.METAMAPA.API_FUENTE_DINAMICA}/`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre })
    });
    return resp.ok ? await resp.json() : null;
}

async function crearFuenteEstatica(nombre) {
    const resp = await fetch(`${window.METAMAPA.API_FUENTE_ESTATICA}/`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({nombre })
    });
    return resp.ok ? await resp.json() : null;
}

async function crearFuenteDemo(nombre, url) {
    const resp = await fetch(`${window.METAMAPA.API_FUENTE_DEMO}/`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre, url })
    });
    return resp.ok ? await resp.json() : null;
}

async function crearFuenteMetamapa(nombre, endpoint) {
    const resp = await fetch(`${window.METAMAPA.API_FUENTE_METAMAPA}/`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre, endpoint })
    });
    return resp.ok ? await resp.json() : null;
}

// Registrar una nueva fuente de datos en el agregador
async function registrarFuente(url) {
    const resp = await fetch(`${window.METAMAPA.API_AGREGADOR}/fuenteDeDatos`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({url})
    });
    return resp.ok;
}

async function cargarCSV(idFuenteDeDatos) {
    const input = document.createElement("input");
    input.type = "file";
    input.accept = ".csv";
    input.onchange = async () => {
        const file = input.files[0];
        if (!file) return;
        const formData = new FormData();
        formData.append("file", file);
        try {
            const resp = await fetch(
                `${window.METAMAPA.API_FUENTE_ESTATICA}/${idFuenteDeDatos}/csv`,
                {
                    method: "POST",
                    body: formData
                }
            );
            if (!resp.ok) {
                const error = await resp.text();
                throw new Error(error);
            }
            const mensaje = await resp.text();
            mostrarModal(mensaje, "CSV cargado en fuente estática");
        } catch (error) {
            console.error("Error cargando CSV:", error);
            alert("Error al cargar el CSV");
        }
    };
    input.click();
}