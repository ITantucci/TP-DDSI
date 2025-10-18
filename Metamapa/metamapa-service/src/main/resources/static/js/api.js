console.log("api.js cargado correctamente");
// Crear un nuevo hecho en la fuente dinámica
async function crearHecho(e) {
    e.preventDefault();
    const f = e.target;

    // === Armar el objeto idéntico al JSON de Postman ===
    const data = {
        titulo: f.titulo.value.trim(),
        descripcion: f.descripcion.value.trim(),
        categoria: f.categoria.value.trim(),
        latitud: parseFloat(f.latitud.value),
        longitud: parseFloat(f.longitud.value),
        fechaHecho: f.fechaHecho?.value || new Date().toISOString().split("T")[0],
        idUsuario: parseInt(f.idUsuario.value),
        fuenteId: parseInt(f.idFuente.value),
        anonimo: f.anonimo.checked
    };

    console.log("Enviando hecho:", data);

    // === Validar que todos los obligatorios estén presentes ===
    if (!data.titulo || !data.descripcion || isNaN(data.fuenteId)) {
        alert("Debes completar al menos título, descripción y fuente.");
        return;
    }

    // === Enviar al backend exactamente como Postman ===
    const formData = new FormData();

    formData.append("hecho", JSON.stringify(data));

    // ✅ Agregar todos los archivos seleccionados
    const input = document.getElementById("inputMultimedia");
    for (let i = 0; i < input.files.length; i++) {
        formData.append("archivos", input.files[i]);
    }

    // Enviar al backend
    const resp = await fetch(`http://localhost:9001/api-fuentesDeDatos/${data.fuenteId}/hechos`, {
        method: "POST",
        body: formData // NO JSON.stringify, NO headers
    });

    const res = document.getElementById("resultadoHecho");
    if (resp.ok) {
        const json = await resp.json();
        res.innerHTML = `✅ Hecho creado correctamente (ID: ${json.id || "sin id"})`;
        res.className = "text-success";

        // Cerrar modal y limpiar
        const modal = bootstrap.Modal.getInstance(document.getElementById("modalHecho"));
        modal.hide();
        limpiarFormularioHecho();

    } else {
        const errorTxt = await resp.text();
        res.innerHTML = `Error al crear el hecho: ${errorTxt}`;
        res.className = "text-danger";
    }
}


// Obtener todos los hechos curados del agregador
async function obtenerHechos() {
    const resp = await fetch(`${window.METAMAPA.API_AGREGADOR}/hechos`);
    return resp.ok ? resp.json() : [];
}

// Obtener hechos específicos de una colección
async function obtenerHechosDeColeccion(id) {
    const resp = await fetch(`${window.METAMAPA.API_COLECCIONES}/${id}/hechos`);
    return resp.ok ? resp.json() : [];
}

// Crear o actualizar colección
async function crearColeccion(e) {
    e.preventDefault();
    const f = e.target;

    // --- recolectar criterios ---
    const criterios = [];
    document.querySelectorAll("#criteriosContainer .criterio-box").forEach(div => {
        const criterio = {
            tipo: div.querySelector('[name="tipo"]').value,
            valor: div.querySelector('[name="valor"]').value || null,
            inclusion: div.querySelector('[name="inclusion"]').value === "true"
        };

        const fd = div.querySelector('[name="fechaDesde"]')?.value;
        const fh = div.querySelector('[name="fechaHasta"]')?.value;
        if (fd) criterio.fechaDesde = fd;
        if (fh) criterio.fechaHasta = fh;

        const idFuente = div.querySelector('[name="idFuenteDeDatos"]')?.value;
        if (idFuente) criterio.idFuenteDeDatos = parseInt(idFuente);

        const lat = div.querySelector('[name="latitud"]')?.value;
        const lon = div.querySelector('[name="longitud"]')?.value;
        const radio = div.querySelector('[name="radio"]')?.value;
        if (radio) criterio.radio = parseFloat(radio);
        if (lat) criterio.latitud = parseFloat(lat);
        if (lon) criterio.longitud = parseFloat(lon);

        const tm = div.querySelector('[name="tipoMultimedia"]')?.value;
        if (tm) criterio.tipoMultimedia = tm;

        criterios.push(criterio);
    });

    const data = {
        titulo: f.titulo.value.trim(),
        descripcion: f.descripcion.value.trim(),
        consenso: f.consenso.value,
        criterios: criterios
    };

    const id = f.idColeccion.value;
    const url = id
        ? `${window.METAMAPA.API_COLECCIONES}/${id}`
        : `${window.METAMAPA.API_COLECCIONES}/`;
    const method = id ? "PUT" : "POST";

    console.log("Enviando colección:", data);

    const resp = await fetch(url, {
        method,
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    });

    const res = document.getElementById("resultadoColeccion");
    if (resp.ok) {
        const json = await resp.json();
        res.innerHTML = `✅ Colección ${id ? "actualizada" : "creada"} (${json.handle || json.id})`;
        res.className = "text-success";

        const modal = bootstrap.Modal.getInstance(document.getElementById("modalColeccion"));
        modal.hide();
        limpiarFormularioColeccion();
        mostrar("colecciones");
    } else {
        const txt = await resp.text();
        res.innerHTML = `Error: ${txt}`;
        res.className = "text-danger";
    }
}


// Obtener todas las colecciones
async function obtenerColecciones() {
    const resp = await fetch(`${window.METAMAPA.API_COLECCIONES}`);
    return resp.ok ? resp.json() : [];
}

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

// Pedir al agregador que actualice los hechos desde las fuentes
async function actualizarHechos() {
    const resp = await fetch(`${window.METAMAPA.API_AGREGADOR}/actualizarHechos`, {method: "POST"});
    alert(resp.ok ? "Hechos actualizados desde las fuentes." : "⚠️ Error al actualizar hechos.");
}

// Ejecutar curado/consenso de hechos en el agregador
async function curarHechos() {
    const resp = await fetch(`${window.METAMAPA.API_AGREGADOR}/consensuarHechos`, {method: "POST"});
    alert(resp.ok ? "Curado completado correctamente." : "⚠️ Error al curar hechos.");
}