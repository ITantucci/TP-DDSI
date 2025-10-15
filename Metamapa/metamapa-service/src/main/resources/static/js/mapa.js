let mapa;
let markersLayer;
let legendDiv;

// Paleta de colores por categoría
const categoriaColores = {
    "Delito": "red",
    "Accidente": "orange",
    "Emergencia": "blue",
    "Clima": "purple",
    "Otro": "green"
};

function colorPorCategoria(cat) {
    if (!cat) return categoriaColores["Otro"];
    return categoriaColores[cat] || categoriaColores["Otro"];
}

function inicializarMapa(divId = "mapa") {
    mapa = L.map(divId).setView([-34.61, -58.38], 11);
    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution: '&copy; OpenStreetMap contributors'
    }).addTo(mapa);
    markersLayer = L.layerGroup().addTo(mapa);
    agregarLeyenda();
}

function limpiarMarcadores() {
    if (markersLayer) markersLayer.clearLayers();
}

function agregarMarcador(hecho) {
    if (!hecho.latitud || !hecho.longitud) return;

    const color = colorPorCategoria(hecho.categoria);
    const icono = L.divIcon({
        html: `<div style="background:${color}; width:16px; height:16px; border-radius:50%; border:2px solid white"></div>`,
        className: ""
    });

    const popup = `
    <b>${hecho.titulo}</b><br>
    ${hecho.descripcion}<br>
    <small>${hecho.categoria || "Sin categoría"} | ${hecho.fechaHecho}</small>
  `;
    const marker = L.marker([hecho.latitud, hecho.longitud], { icon: icono })
        .bindPopup(popup)
        .on("click", () => mostrarDetalleHecho(hecho));
    marker.addTo(markersLayer);
}

function mostrarHechosEnMapa(hechos) {
    limpiarMarcadores();
    hechos.forEach(agregarMarcador);
    actualizarLeyenda(hechos);

    if (hechos.length > 0 && hechos[0].latitud) {
        mapa.setView([hechos[0].latitud, hechos[0].longitud], 11);
    }
}

// === Leyenda dinámica ===
function agregarLeyenda() {
    legendDiv = L.control({ position: "bottomright" });
    legendDiv.onAdd = function () {
        this._div = L.DomUtil.create("div", "mapa-leyenda");
        this._div.innerHTML = "<strong>Leyenda</strong><br><small>Sin datos</small>";
        return this._div;
    };
    legendDiv.addTo(mapa);
}

function actualizarLeyenda(hechos) {
    if (!legendDiv || !legendDiv._div) return;
    const cats = [...new Set(hechos.map(h => h.categoria || "Otro"))];
    if (!cats.length) {
        legendDiv._div.innerHTML = "<strong>Leyenda</strong><br><small>Sin hechos</small>";
        return;
    }

    legendDiv._div.innerHTML = `
    <strong>Leyenda</strong><br>
    ${cats.map(c => {
        const color = colorPorCategoria(c);
        return `<div style="display:flex;align-items:center;margin-bottom:2px">
                <div style="background:${color};width:12px;height:12px;border-radius:50%;margin-right:6px"></div>
                ${c}
              </div>`;
    }).join("")}
  `;
}
// ==========================
// 🗺️ Mapa selector para crear hecho
// ==========================
let mapaSeleccion;
let marcadorSeleccion;

function inicializarMapaSeleccion() {
    // Evitar reinicialización múltiple
    if (mapaSeleccion) {
        setTimeout(() => mapaSeleccion.invalidateSize(), 200);
        return;
    }

    // Crear mapa base
    mapaSeleccion = L.map("mapaSeleccion").setView([-34.61, -58.38], 11);

    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution: '&copy; OpenStreetMap contributors'
    }).addTo(mapaSeleccion);

    // Intentar centrar en ubicación del usuario
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(pos => {
            const { latitude, longitude } = pos.coords;
            mapaSeleccion.setView([latitude, longitude], 13);
        });
    }

    // Al hacer clic, agregar o mover marcador
    mapaSeleccion.on("click", function (e) {
        const { lat, lng } = e.latlng;
        colocarMarcadorSeleccion(lat, lng);
    });
}

// Crear o mover marcador y actualizar inputs
function colocarMarcadorSeleccion(lat, lng) {
    // Si no existe el marcador, crearlo
    if (!marcadorSeleccion) {
        marcadorSeleccion = L.marker([lat, lng], { draggable: true }).addTo(mapaSeleccion);
        marcadorSeleccion.on("drag", function (ev) {
            const { lat, lng } = ev.latlng;
            actualizarInputsLatLng(lat, lng);
        });
        marcadorSeleccion.on("dragend", function (ev) {
            const { lat, lng } = ev.target.getLatLng();
            actualizarInputsLatLng(lat, lng);
        });
    } else {
        marcadorSeleccion.setLatLng([lat, lng]);
    }

    actualizarInputsLatLng(lat, lng);
}

// Actualiza los campos del formulario con la posición actual
function actualizarInputsLatLng(lat, lng) {
    document.getElementById("latitud").value = lat.toFixed(6);
    document.getElementById("longitud").value = lng.toFixed(6);
}

// Limpia el marcador actual (si existe)
function limpiarMapaSeleccion() {
    if (mapaSeleccion && marcadorSeleccion) {
        mapaSeleccion.removeLayer(marcadorSeleccion);
        marcadorSeleccion = null;
    }
}
