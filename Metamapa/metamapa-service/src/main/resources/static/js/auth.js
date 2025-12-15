// Elementos fijos de la interfaz (navbar)
const btnLogin = document.getElementById('btnLogin');
const btnLogout = document.getElementById('btnLogout');
const nombreUsuarioDisplay = document.getElementById('nombreUsuarioDisplay'); // Asumiendo un elemento para mostrar el nombre

// --- Helpers de Roles ---

function esContribuyenteOAdmin(roles) {
    // Si el array de roles incluye CONTRIBUYENTE o ADMINISTRADOR
    return roles.includes("CONTRIBUYENTE") || roles.includes("ADMINISTRADOR");
}

function esAdministrador(roles) {
    // Si el array de roles incluye ADMINISTRADOR
    return roles.includes("ADMINISTRADOR");
}

// --- Funciones de Actualización de UI (Basadas en Roles) ---

let rolesUsuarioActual = [];
function actualizarVisibilidadPorRoles(roles, nombre) {
    // Guardamos los roles en memoria para reusar si hace falta
    if (roles) rolesUsuarioActual = roles;

    const isContribOrAdmin = esContribuyenteOAdmin(rolesUsuarioActual);
    const isAdminRole = esAdministrador(rolesUsuarioActual);

    // BÚSQUEDA DINÁMICA: tomamos los elementos en cada llamada
    const btnsVisualizador = document.querySelectorAll('.visualizador-level');
    const btnsLoginRequired = document.querySelectorAll('.login-required, .contribuyente-level');
    const btnsAdminOnly = document.querySelectorAll(' .admin-only');

    // Visibilidad de Login/Logout
    if (btnLogin) btnLogin.classList.add('d-none');
    if (btnLogout) btnLogout.classList.remove('d-none');

    // Opcional: Mostrar nombre de usuario
    if (nombreUsuarioDisplay) {
        nombreUsuarioDisplay.textContent = nombre || 'Usuario';
    }

    btnsVisualizador.forEach(btn => btn.classList.remove('d-none'));

    // Contribuyente o Admin (Hechos, +Hecho, etc.)
    btnsLoginRequired.forEach(btn => {
        if (isContribOrAdmin) {
            btn.classList.remove('d-none');
        } else {
            btn.classList.add('d-none');
        }
    });

    // Solo Administrador (Fuentes, +Colección, Curar/Actualizar)
    btnsAdminOnly.forEach(btn => {
        if (isAdminRole) {
            btn.classList.remove('d-none');
        } else {
            btn.classList.add('d-none');
        }
    });
}

function ocultarTodoYMostrarLogin() {
    // BÚSQUEDA DINÁMICA TAMBIÉN ACÁ
    const btnsLoginRequired = document.querySelectorAll('.login-required, .contribuyente-level');
    const btnsAdminOnly = document.querySelectorAll('.admin-only');

    // Oculta todos los elementos protegidos
    btnsLoginRequired.forEach(btn => btn.classList.add('d-none'));
    btnsAdminOnly.forEach(btn => btn.classList.add('d-none'));

    // Muestra el botón de Iniciar Sesión
    if (btnLogin) btnLogin.classList.remove('d-none');
    if (btnLogout) btnLogout.classList.add('d-none');

    if (nombreUsuarioDisplay) {
        nombreUsuarioDisplay.textContent = '';
    }
}

let usuarioActual = null;

async function verificarSesionYActualizarUI() {
    try {
        const resp = await fetch(`${window.METAMAPA.API_USUARIOS}/api-auth/me`, { credentials: 'include' });
        if (resp.ok) {
            usuarioActual = await resp.json();
            const roles = usuarioActual.roles || [];
            actualizarVisibilidadPorRoles(roles, usuarioActual.nombre);
        } else {
            usuarioActual = null;
            ocultarTodoYMostrarLogin();
        }
    } catch (e) {
        usuarioActual = null;
        ocultarTodoYMostrarLogin();
    }
}

// --- Event Listeners y Inicialización ---

// Llamar a la función al cargar el DOM para revisar si hay una sesión JWT activa
document.addEventListener('DOMContentLoaded', verificarSesionYActualizarUI);

function iniciarSesionSSO() {
    // Lleva al login del SSO
    window.location.href = `${window.METAMAPA.API_USUARIOS}/login`;
}

function cerrarSesion() {
    // Logout contra el SSO
    fetch(`${window.METAMAPA.API_USUARIOS}/logout`, {
        method: 'POST',
        credentials: 'include'
    })
        .catch(err => {
            console.error("Error al hacer logout:", err);
        })
        .finally(() => {
            // Limpio la UI del lado del frontend
            ocultarTodoYMostrarLogin();
            // Vuelvo al index del Metamapa
            window.location.href = 'http://localhost:9000/index.html';
        });
}

document.addEventListener('DOMContentLoaded', () => {
    const formLogin = document.getElementById('formLogin');
    if (formLogin && window.METAMAPA && window.METAMAPA.API_USUARIOS) {
        formLogin.action = `${window.METAMAPA.API_USUARIOS}/login`;
    }
});
