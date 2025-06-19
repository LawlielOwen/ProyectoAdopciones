function Registro() {
    const nombre = document.getElementById("nombre").value;
    const app = document.getElementById("app").value;
    const apm = document.getElementById("apm").value;
    const correo = document.getElementById("correo").value;
    const telefono = document.getElementById("telefono").value;
    const contra = document.getElementById("contra").value;
    const genero = document.getElementById("genero").value;
    const fechaNacimiento = document.getElementById("fechaNacimiento").value;
    const alerta = document.getElementById("fallo");
    if (nombre === "" || app === "" || apm === "" || correo === "" || telefono === "" || contra === "" || genero === "" || fechaNacimiento === "") {
        document.getElementById("mensaje-error").innerHTML = "Por favor, completa todos los campos.";
        alerta.classList.remove("d-none");
        alerta.classList.add("show");
        return;
    }
}
function cerrarAlerta() {
    const alerta = document.getElementById("fallo");
    alerta.classList.add("d-none");
    alerta.classList.remove("show");
}
document.getElementById("formularioRegistro").addEventListener("submit", function (event) {
    event.preventDefault();
    Registro();
});
