// js/auth.js

// Clases de elementos de la interfaz
const loginRequired = document.querySelectorAll('.login-required, .contribuyente-level'); // Combina ambas clases para Contribuyente/Admin
const adminOnly = document.querySelectorAll('.admin-only'); // Funciones exclusivas de Admin
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

function actualizarVisibilidadPorRoles(roles, nombre) {
    const isContribOrAdmin = esContribuyenteOAdmin(roles);
    const isAdminRole = esAdministrador(roles);

    // 1. Visibilidad de Login/Logout
    if (btnLogin) btnLogin.classList.add('d-none');
    if (btnLogout) btnLogout.classList.remove('d-none');

    // Opcional: Mostrar nombre de usuario
    if (nombreUsuarioDisplay) {
        nombreUsuarioDisplay.textContent = nombre || 'Usuario';
    }

    // 2. Controlar botones de Contribuyente/Admin (Hechos, +Hecho, Mis Solicitudes)
    loginRequired.forEach(btn => {
        if (isContribOrAdmin) {
            btn.classList.remove('d-none');
        } else {
            btn.classList.add('d-none');
        }
    });

    // 3. Controlar botones de Administrador (Fuentes, +Colección, Curar/Actualizar)
    adminOnly.forEach(btn => {
        if (isAdminRole) {
            btn.classList.remove('d-none');
        } else {
            btn.classList.add('d-none');
        }
    });
}


function ocultarTodoYMostrarLogin() {
    // Oculta todos los elementos protegidos
    loginRequired.forEach(btn => btn.classList.add('d-none'));
    adminOnly.forEach(btn => btn.classList.add('d-none'));

    // Muestra el botón de Iniciar Sesión
    if (btnLogin) btnLogin.classList.remove('d-none');
    if (btnLogout) btnLogout.classList.add('d-none');

    if (nombreUsuarioDisplay) {
        nombreUsuarioDisplay.textContent = '';
    }
}


// --- FUNCIÓN CLAVE: Verificar Sesión contra el Backend ---

async function verificarSesionYActualizarUI() {
    try {
        // Llama al endpoint de tu M-Usuarios-Service que valida el JWT
        const resp = await fetch(`${window.METAMAPA.API_USUARIOS}/api-auth/me`);

        if (resp.ok) {
            const usuario = await resp.json();

            // Si el backend te devuelve los roles vacíos (por Lazy Initialization),
            // esto fallará. Asumo que el JSON se ve así: {id: 2, roles: ["ADMINISTRADOR"], nombre: "..."}
            const roles = usuario.roles || [];

            // Asumiendo que el campo 'nombre' viene en la respuesta del backend
            actualizarVisibilidadPorRoles(roles, usuario.nombre);

        } else {
            // Error 401/404/500: Sesión no válida o expirada
            ocultarTodoYMostrarLogin();
        }
    } catch (e) {
        console.error("Error verificando sesión:", e);
        // Fallo de red: asumir no autenticado
        ocultarTodoYMostrarLogin();
    }
}

// --- Event Listeners y Inicialización ---

// Llamar a la función al cargar el DOM para revisar si hay una sesión JWT activa
document.addEventListener('DOMContentLoaded', verificarSesionYActualizarUI);

// Cerrar sesión
if (btnLogout) {
    btnLogout.addEventListener('click', () => {
        // En un entorno SSO real, deberías redirigir al logout de Auth0/SAS
        // Aquí simplemente limpiamos la sesión local y redirigimos al login de SAS para limpiar cookies.
        window.location.href = `${window.METAMAPA.API_USUARIOS}/logout`;
    });
}
function iniciarSesionSSO() {
    // 🚨 Esta URL inicia el flujo de autenticación en tu propio servidor de autorización (SAS).
    // Los parámetros deben coincidir con los que definiste en tu SecurityConfig y RegisteredClient.

    const CLIENT_ID = 'metamapa-service';
    const REDIRECT_URI = 'http://localhost:9000/callback'; // Donde el Cliente Liviano recibirá el código
    const SCOPE = 'openid read';
    const RESPONSE_TYPE = 'code';

    // Generación de PKCE (Proof Key for Code Exchange) es crucial para seguridad en navegadores.
    // Por simplicidad en esta prueba, usaremos valores fijos (como en tu SecurityConfig),
    // pero idealmente deberías generar el code_challenge dinámicamente.
    const CODE_CHALLENGE = 'xyz'; // Placeholder
    const CODE_CHALLENGE_METHOD = 'S256';

    const url = `${window.METAMAPA.API_USUARIOS}/oauth2/authorize?` +
        `response_type=${RESPONSE_TYPE}` +
        `&client_id=${CLIENT_ID}` +
        `&scope=${SCOPE}` +
        `&redirect_uri=${REDIRECT_URI}` +
        `&code_challenge=${CODE_CHALLENGE}` +
        `&code_challenge_method=${CODE_CHALLENGE_METHOD}`;

    // Redirigir el navegador
    window.location.href = url;
}