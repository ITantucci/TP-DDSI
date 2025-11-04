// Crear o actualizar colección
async function crearColeccion(e) {
    e.preventDefault();
    const f = e.target;
    // --- recolectar criterios ---
    const criterios = [...document.querySelectorAll("#criteriosContainer .criterio-box")].map(armarCriterio);
    const data = {
        titulo: f.titulo.value.trim(),
        descripcion: f.descripcion.value.trim(),
        consenso: f.consenso.value,
        criterios
    };
    const id = f.idColeccion.value;
    const url = id
        ? `${window.METAMAPA.API_COLECCIONES}/${id}`
        : `${window.METAMAPA.API_COLECCIONES}/`;
    const method = id ? "PUT" : "POST";
    console.log("Enviando colección:", data);
    try {
        const resp = await fetch(url, {
            method,
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        });
        if (resp.ok) {
            const json = await resp.json();
            // Cerrar modal actual
            const modal = bootstrap.Modal.getInstance(document.getElementById("modalColeccion"));
            modal.hide();
            limpiarFormularioColeccion();
            // Mostrar mensaje en modal
            mostrarModal(`✅ Colección ${id ? "actualizada" : "creada"} (${json.handle || json.id})`, "Colección exitosa");
            await mostrar("colecciones");
        } else {
            const txt = await resp.text();
            mostrarModal(`❌ Error al crear la colección: ${txt}`, "Error");
        }
    } catch (err) {
        mostrarModal(`❌ Error inesperado: ${err.message}`, "Error");
    }
}

// Obtener todas las colecciones
async function obtenerColecciones() {
    const resp = await fetch(`${window.METAMAPA.API_COLECCIONES}`);
    return resp.ok ? resp.json() : [];
}

// Obtener hechos de una colección con filtros opcionales
async function obtenerHechosColeccionFiltrados(idColeccion, params) {
    const query = params?.toString();
    const url = `${window.METAMAPA.API_COLECCIONES}/${idColeccion}/hechos${query ? "?" + query : ""}`;
    try {
        const resp = await fetch(url);
        if (!resp.ok) throw new Error("Respuesta no OK del servidor");
        return await resp.json();
    } catch (e) {
        console.error("Error al obtener hechos filtrados de la colección:", e);
        throw e;
    }
}

// Modificar consenso de una colección
async function modificarConsensoColeccion(id, consenso) {
    const resp = await fetch(`${window.METAMAPA.API_COLECCIONES}/${id}`, {
        method: "PATCH",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({consenso})
    });
    return resp.ok;
}

// Actualizar una colección
async function actualizarColeccion(id, coleccionDTO) {
    try {
        const resp = await fetch(`${window.METAMAPA.API_COLECCIONES}/${id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(coleccionDTO)
        });
        if (!resp.ok) {
            const errorText = await resp.text();
            throw new Error(`Error al actualizar la colección: ${errorText}`);
        }
        return await resp.json(); // Devuelve la colección actualizada
    } catch (error) {
        console.error("❌ Error en actualizarColeccion:", error);
        throw error;
    }
}

// Helper para armar un criterio desde un div
function armarCriterio(div) {
    const get = n => div.querySelector(`[name="${n}"]`)?.value?.trim() || null;
    const num = n => parseFloat(get(n));
    return {
        tipo: get("tipo"),
        valor: get("valor"),
        inclusion: get("inclusion") === "true",
        ...(get("fechaDesde") && { fechaDesde: get("fechaDesde") }),
        ...(get("fechaHasta") && { fechaHasta: get("fechaHasta") }),
        ...(get("idFuenteDeDatos") && { idFuenteDeDatos: parseInt(get("idFuenteDeDatos")) }),
        ...(num("latitud") && { latitud: num("latitud") }),
        ...(num("longitud") && { longitud: num("longitud") }),
        ...(num("radio") && { radio: num("radio") }),
        ...(get("tipoMultimedia") && { tipoMultimedia: get("tipoMultimedia") })
    };
}