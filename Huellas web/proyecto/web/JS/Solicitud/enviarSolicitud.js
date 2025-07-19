document.addEventListener("DOMContentLoaded", function () {
    const usuarioData = localStorage.getItem("usuario");
    const animalData = localStorage.getItem("animalSeleccionado");

    if (usuarioData && animalData) {
        const usuario = JSON.parse(usuarioData);
        const animal = JSON.parse(animalData);

        const nombreInput = document.getElementById("nombre");
        const animalInput = document.getElementById("animal");
       

        if (nombreInput && animalInput ) {
            nombreInput.value = `${usuario.nombre} ${usuario.app} ${usuario.apm}`;
            
            animalInput.value = animal.nombre;
        } 
        window.idAnimal = animal.id;
        window.idAdoptante = usuario.idAdoptante;

    }
    
});



function enviar(){

const telefono = document.getElementById("telefono").value;
const correo = document.getElementById("correo").value;
const direccion = document.getElementById("direccion").value;
const motivo = document.getElementById("motivo").value;

const nuevaSoli = {
    motivo: motivo,
    telefono: telefono,
    correo: correo,
    direccion: direccion,
    idAnimal: parseInt(window.idAnimal),
    idAdoptante: parseInt(window.idAdoptante),
}
fetch("http://localhost:8080/ProyectoHuellas/api/solicitudes/saveSolicitud", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(nuevaSoli)
        })
            .then(res => res.json())
            .then(data => {

              window.location.href="inicioAdopciones.html"
            })
    };
