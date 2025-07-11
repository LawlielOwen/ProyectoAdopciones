

function cargarAdoptante() {
    fetch("http://localhost:8080/ProyectoHuellas/api/adoptante/getAll")
        .then(res => res.json())
        .then(data => {
        cliente = data;
        });
}

function sesionIniciada(){
     const usuarioElemento = document.getElementById("usuario");
    const usuarioData = localStorage.getItem("usuario");
    if (usuarioData) {
        const usuario = JSON.parse(usuarioData);
        usuarioElemento.innerHTML = `
            <span class="me-2 sesionP">
            <button class="btn btn-outline-light  dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
            <i class='bx bxs-user-circle'></i>  ${usuario.nombre}
          </button>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item datosUsuario" href="#">${usuario.nombre} ${usuario.app} ${usuario.apm}<br>${usuario.correo}</a></li>
            <li><a class="dropdown-item" href="#" onclick="cerrarSesion()">Cerrar sesión</a></li>
          </ul></span>
        `;
    } else {
        usuarioElemento.innerHTML = `
            <a class="nav-link text-light" href="Login.html">Iniciar sesión / Crear cuenta</a>
        `;
    }
}
function cerrarSesion() {
    localStorage.removeItem("usuario");
    window.location.href = "inicioAdopciones.html";
}

function cerrarAviso() {
    const alerta = document.getElementById("fallo");
    alerta.classList.add("d-none");
    alerta.classList.remove("show");
}
function revisarSesion(){
    const alerta = document.getElementById("fallo");
    const usuarioData = localStorage.getItem("usuario");

    if (usuarioData) {
        window.location.href = "solicitudAdopcion.html";
    } else {
        document.getElementById("mensaje-error").innerText = "Debes iniciar sesión para realizar una adopcion.";
        alerta.classList.remove("d-none");
        alerta.classList.add("show");
    }
}
