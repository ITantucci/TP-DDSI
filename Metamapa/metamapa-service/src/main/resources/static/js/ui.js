const cont = document.getElementById("contenido");

async function mostrar(tipo) {
    cont.innerHTML = "<p>Cargando...</p>";
    try {
        if (tipo === "hechos") {
            const data = await getHechos();
            renderHechos(data);
        } else if (tipo === "colecciones") {
            const data = await getColecciones();
            renderColecciones(data);
        } else if (tipo === "fuentes") {
            const data = await getFuentes();
            renderFuentes(data);
        }
    } catch (e) {
        cont.innerHTML = `<p class="error">Error: ${e}</p>`;
    }
}

function renderHechos(hechos) {
    cont.innerHTML = `<h2>Hechos (${hechos.length})</h2><div id="map"></div>`;
    renderMapa(hechos);
}

function renderColecciones(cols) {
    cont.innerHTML = `
    <h2>Colecciones (${cols.length})</h2>
    <ul>${cols.map(c => `<li><b>${c.titulo}</b> — ${c.descripcion}</li>`).join("")}</ul>`;
}

function renderFuentes(fs) {
    cont.innerHTML = `
    <h2>Fuentes (${fs.length})</h2>
    <ul>${fs.map(f => `<li>${f.nombre} (${f.tipoFuente}) — ${f.url ?? "-"}</li>`).join("")}</ul>`;
}
async function mostrar(tipo) {
    cont.innerHTML = "<p>Cargando...</p>";
    try {
        if (tipo === "hechos") {
            const data = await getHechos();
            renderHechos(data);
        } else if (tipo === "colecciones") {
            const data = await getColecciones();
            renderColecciones(data);
        } else if (tipo === "fuentes") {
            const data = await getFuentes();
            renderFuentes(data);
        } else if (tipo === "estadisticas") {
            await renderEstadisticas();
        }
    } catch (e) {
        cont.innerHTML = `<p class="error">Error: ${e}</p>`;
    }
}