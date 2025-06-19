function LogIn(){
   const correo = document.getElementById("email").value;
   const contra = document.getElementById("password").value;
   const alerta = document.getElementById("fallo");
   if (correo === "" || contra === "") {
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
document.getElementById("formularioLogin").addEventListener("submit", function (event) {
    event.preventDefault();
    Registro();
});
