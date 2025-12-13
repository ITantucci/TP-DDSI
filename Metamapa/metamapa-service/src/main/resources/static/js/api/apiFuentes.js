// Obtener todas las fuentes registradas en el agregador
async function obtenerFuentes() {
    const resp = await fetch(`${window.METAMAPA.API_AGREGADOR}/fuenteDeDatos`);
    return resp.ok ? resp.json() : [];
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

async function cargarCSV(idFuenteDeDatos, archivo) {
    const formData = new FormData();
    formData.append("file", archivo);
    try {
        const response = await fetch(
            `http://localhost:8080/${idFuenteDeDatos}/csv`,
            {
                method: "POST",
                body: formData
            }
        );
        const texto = await response.text();
        if (!response.ok) {
            throw new Error(texto);
        }
        alert(texto);
    } catch (error) {
        console.error(error);
        alert("Error al cargar CSV");
    }
}