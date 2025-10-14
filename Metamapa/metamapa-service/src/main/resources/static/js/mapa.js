function renderMapa(hechos) {
    const map = L.map("map").setView([-34.6, -58.4], 4);
    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        maxZoom: 18
    }).addTo(map);

    hechos.forEach(h => {
        if (h.latitud && h.longitud) {
            L.marker([h.latitud, h.longitud])
                .addTo(map)
                .bindPopup(`<strong>${h.titulo}</strong><br>${h.categoria || ""}`);
        }
    });
}
