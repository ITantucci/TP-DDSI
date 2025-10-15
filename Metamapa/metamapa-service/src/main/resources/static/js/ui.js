console.log("✅ ui.js cargado correctamente");
const cont = document.getElementById("contenido");

// ==================================================
// 🔹 MOSTRAR SECCIONES
// ==================================================
async function mostrar(seccion) {
    if (seccion === "hechos") {
        cont.innerHTML = `
      <h3>Hechos curados</h3>
      <div id="mapa" class="mapa"></div>
      <div id="tablaHechos" class="mt-3"></div>
    `;

        setTimeout(() => inicializarMapa(), 100);
        const hechos = await obtenerHechos();
        setTimeout(() => mostrarHechosEnMapa(hechos), 300);
        document.getElementById("tablaHechos").innerHTML = renderTablaHechos("Hechos curados", hechos);
    }

    if (seccion === "colecciones") {
        cont.innerHTML = `
      <div id="coleccionesView">
        <div class="d-flex justify-content-between align-items-center mb-3">
          <h4>Colecciones</h4>
          <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalColeccion">+ Nueva Colección</button>
        </div>

        <div class="mb-3">
          <label for="modoNav" class="form-label">Modo de navegación:</label>
          <select id="modoNav" class="form-select form-select-sm" style="width:auto; display:inline-block;">
            <option value="IRRESTRICTA">Irrestricta</option>
            <option value="CURADA">Curada</option>
          </select>
        </div>

        <div id="filtrosColeccion" class="border p-3 rounded mb-3 bg-light">
          <h6>Filtros temporales</h6>
          <div class="row g-2">
            <div class="col-md-4">
              <input id="tituloNP" class="form-control form-control-sm" placeholder="Excluir título...">
            </div>
            <div class="col-md-4">
              <input id="categoriaP" class="form-control form-control-sm" placeholder="Incluir categoría...">
            </div>
            <div class="col-md-4">
              <button class="btn btn-sm btn-outline-success w-100" onclick="aplicarFiltrosColeccion()">Aplicar filtros</button>
            </div>
          </div>
        </div>

        <div id="listaColecciones" class="mb-3"></div>
        <div id="mapaColeccion" class="mapa"></div>
      </div>`;

        setTimeout(() => inicializarMapa("mapaColeccion"), 100);
        await mostrarColecciones();
    }

    if (seccion === "fuentes") {
        cont.innerHTML = "<p>Cargando fuentes...</p>";
        const fuentes = await obtenerFuentes();
        cont.innerHTML = `
      <h3>Fuentes registradas (${fuentes.length})</h3>
      <ul class="list-group">
        ${fuentes.map(u => `<li class="list-group-item">${u}</li>`).join("")}
      </ul>`;
    }
}

// ==================================================
// 🔹 COLECCIONES Y MAPEO POR COLOR
// ==================================================
let coleccionSeleccionada = null;
let coloresColeccion = {};

function colorAleatorio() {
    const letras = "0123456789ABCDEF";
    let color = "#";
    for (let i = 0; i < 6; i++) color += letras[Math.floor(Math.random() * 16)];
    return color;
}

// Mostrar todas las colecciones
async function mostrarColecciones() {
    const cont = document.getElementById("listaColecciones");
    cont.innerHTML = "<p class='text-muted'>Cargando colecciones...</p>";

    try {
        const resp = await fetch(`${window.METAMAPA.API_COLECCIONES}`);
        const colecciones = await resp.json();

        coloresColeccion = {};
        cont.innerHTML = colecciones.map((c, i) => {
            const color = colorAleatorio();
            coloresColeccion[c.handle] = color;
            return `
        <div class="card mb-2 p-2" style="border-left: 6px solid ${color};">
          <div class="d-flex justify-content-between align-items-center">
            <div>
              <h6 class="fw-bold mb-1">${c.titulo}</h6>
              <p class="small mb-0">${c.descripcion}</p>
            </div>
            <button class="btn btn-sm btn-outline-primary" onclick="verHechosColeccion('${c.handle}')">Ver hechos</button>
          </div>
        </div>`;
        }).join("");

        // Cargar hechos de todas las colecciones para verlos en el mapa con colores distintos
        for (const c of colecciones) {
            const modo = document.getElementById("modoNav").value;
            const url = `${window.METAMAPA.API_COLECCIONES}/${c.handle}/hechos?modoNav=${modo}`;
            const respHechos = await fetch(url);
            if (!respHechos.ok) continue;
            const hechos = await respHechos.json();
            mostrarHechosEnMapaPorColeccion(hechos, coloresColeccion[c.handle], c.handle);
        }
    } catch (e) {
        cont.innerHTML = `<div class="alert alert-danger">❌ Error al cargar colecciones</div>`;
        console.error("Error al cargar colecciones:", e);
    }
}

// Mostrar hechos en el mapa con color específico
function mostrarHechosEnMapaPorColeccion(hechos, color, handle) {
    if (!window.mapa) return;
    hechos.forEach(h => {
        if (h.latitud && h.longitud) {
            const marker = L.circleMarker([h.latitud, h.longitud], {
                color,
                radius: 6,
                fillOpacity: 0.8,
            }).addTo(mapa);
            marker.bindTooltip(`${h.titulo} (${handle.substring(0, 5)}...)`, { direction: "top" });
            marker.on("click", () => mostrarDetalleHecho(h));
            marker.coleccionId = handle; // para filtrar después
        }
    });
}

// Ver solo los hechos de una colección
function verHechosColeccion(idColeccion) {
    coleccionSeleccionada = idColeccion;
    if (!window.mapa) return;

    mapa.eachLayer(layer => {
        if (layer instanceof L.CircleMarker) {
            if (layer.coleccionId === idColeccion) {
                layer.setStyle({ opacity: 1, fillOpacity: 0.9 });
            } else {
                layer.setStyle({ opacity: 0.1, fillOpacity: 0.1 });
            }
        }
    });
}

// ==================================================
// 🔹 FILTROS TEMPORALES
// ==================================================
async function aplicarFiltrosColeccion() {
    if (!coleccionSeleccionada) return alert("Seleccioná una colección primero.");

    const modo = document.getElementById("modoNav").value;
    const params = new URLSearchParams();

    const tituloNP = document.getElementById("tituloNP").value.trim();
    const categoriaP = document.getElementById("categoriaP").value.trim();
    if (tituloNP) params.append("tituloNP", tituloNP);
    if (categoriaP) params.append("categoriaP", categoriaP);
    params.append("modoNav", modo);

    const url = `${window.METAMAPA.API_COLECCIONES}/${coleccionSeleccionada}/hechos?${params.toString()}`;
    console.log("📡 Aplicando filtros:", url);

    try {
        const resp = await fetch(url);
        if (!resp.ok) throw new Error("Respuesta no OK del servidor");
        const hechos = await resp.json();

        mapa.eachLayer(l => { if (l instanceof L.CircleMarker) mapa.removeLayer(l); });
        mostrarHechosEnMapaPorColeccion(hechos, coloresColeccion[coleccionSeleccionada], coleccionSeleccionada);
    } catch (e) {
        alert("Error al aplicar filtros");
        console.error(e);
    }
}

// ==================================================
// 🔹 INICIALIZACIÓN AL CARGAR
// ==================================================
document.addEventListener("DOMContentLoaded", async () => {
    console.log("🚀 Iniciando MetaMapa...");
    await mostrar("hechos"); // 👈 arranca con la vista de Hechos
});
