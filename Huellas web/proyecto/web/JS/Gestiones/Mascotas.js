animales = [];
buscarid = [];
centros = [];
let paginaActual = 1;
const animalesPorPagina = 5;
let idAnimalEliminar = null;
let especieSeleccionada = "Todos";
let estatusSeleccionado = "Todos";


document.addEventListener("DOMContentLoaded", function () {
    cargarMascotas();
    cargarCentros();
    cargarContadores();
   document.getElementById("todos").addEventListener("click", () => {
    especieSeleccionada = "Todos";
    filtrarTodo(especieSeleccionada, estatusSeleccionado);
});
document.getElementById("perros").addEventListener("click", () => {
    especieSeleccionada = "Perros";
    filtrarTodo(especieSeleccionada, estatusSeleccionado);
});
document.getElementById("gatos").addEventListener("click", () => {
    especieSeleccionada = "Gatos";
    filtrarTodo(especieSeleccionada, estatusSeleccionado);
});

document.getElementById("todosAnimales").addEventListener("click", () => {
    estatusSeleccionado = "Todos";
    filtrarTodo(especieSeleccionada, estatusSeleccionado);
});
document.getElementById("adopcion").addEventListener("click", () => {
    estatusSeleccionado = 1;
    filtrarTodo(especieSeleccionada, estatusSeleccionado);
});
document.getElementById("adoptado").addEventListener("click", () => {
    estatusSeleccionado = 2;
    filtrarTodo(especieSeleccionada, estatusSeleccionado);
});


});
function cargarCentros() {
    fetch("http://localhost:8080/ProyectoHuellas/api/centros/getAll")
        .then(res => res.json())
        .then(data => {
            centros = data;
            let opciones = `<option value="">Selecciona una opción</option>`;
            data.forEach(c => {
                opciones += `<option value="${c.idCentro}">${c.nombreCentro}</option>`;
            });

            document.getElementById("centro").innerHTML = opciones;
            document.getElementById("centroMod").innerHTML = opciones;
        });
}


function cargarMascotas() {
    fetch("http://localhost:8080/ProyectoHuellas/api/mascotas/getAll")
        .then(res => res.json())
        .then(data => {
            animales = data;
            paginaActual = 1;
            mostrarAnimalesPorPagina();
            mostrarBotonesPaginacion();
        });
}
function mostrarAnimalesPorPagina() {
    const inicio = (paginaActual - 1) * animalesPorPagina;
    const fin = inicio + animalesPorPagina;
    const animalesPagina = animales.slice(inicio, fin);

    let filas = "";
    animalesPagina.forEach(a => {
        const foto = a.foto || "img/default.jpg";
        filas += `
            <tr>
                <td class="mascotasCol"><span><img src="${foto}" alt="Foto de mascota">${a.nombreAnimal}</span></td>
                <td><span class="activado ${a.estatus === 1 ? 'bg-success' : a.estatus === 2 ? 'bg-warning' : ''}"></span> ${a.estatusTexto}</td>
                <td>${a.genero}</td>
                <td>${a.edad}</td>
                <td>${a.codigoAnimal}</td>
                <td>${a.raza}</td>
                <td>${a.peso} kg</td>
                <td>${a.especie}</td>
                <td>
                    <button class="btn btn-sm btn-outline-secondary me-1" data-bs-toggle="modal"
                        data-bs-target="#modAnimal" onclick="cargarAnimal(${a.idAnimal})">
                        <i class='bx bxs-edit'></i>
                    </button>
                    <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal"
                        data-bs-target="#eliminarAnimal" onclick="prepararEliminar(${a.idAnimal})">
                        <i class='bx bx-trash'></i>
                    </button>
                    <button class="btn btn-sm btn-outline-info" data-bs-toggle="modal"
                        data-bs-target="#infoAnimal" onclick="mostrarInfoAnimal(${a.idAnimal})">
                        <i class='bx bxs-info-circle'></i>
                    </button>
                </td>
            </tr>
        `;
    });

    document.getElementById("cuerpoTabla").innerHTML = filas;
}
function mostrarBotonesPaginacion() {
    const totalPaginas = Math.ceil(animales.length / animalesPorPagina);
    let botones = "";

    botones += `
        <li class="page-item ${paginaActual === 1 ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual - 1})">Anterior</a>
        </li>
    `;

    for (let i = 1; i <= totalPaginas; i++) {
        botones += `
            <li class="page-item ${paginaActual === i ? 'active' : ''}">
                <a class="page-link" href="#" onclick="cambiarPagina(${i})">${i}</a>
            </li>
        `;
    }

    botones += `
        <li class="page-item ${paginaActual === totalPaginas ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual + 1})">Siguiente</a>
        </li>
    `;

    document.querySelector(".pagination").innerHTML = botones;
}
function cambiarPagina(pagina) {
    const totalPaginas = Math.ceil(animales.length / animalesPorPagina);
    if (pagina >= 1 && pagina <= totalPaginas) {
        paginaActual = pagina;
        mostrarAnimalesPorPagina();
        mostrarBotonesPaginacion();
    }
}

function actualizarTabla(data) {
     animales = data;
    paginaActual = 1;
    mostrarAnimalesPorPagina();
    mostrarBotonesPaginacion();
    let filas = "";
    data.forEach(a => {
        filas += `
            <tr>
                <td class="mascotasCol"><span><img src="${a.foto}" alt="Foto de mascota">${a.nombreAnimal}</span></td>
                <td> <span class="activado ${a.estatus === 1 ? 'bg-success' : a.estatus === 2 ? 'bg-warning' : ''}"></span> ${a.estatusTexto}</td>
                <td>${a.genero}</td>
                <td>${a.edad}</td>
                <td>${a.codigoAnimal}</td>
                <td>${a.raza}</td>
                <td>${a.peso}</td>
                <td>${a.especie}</td>
                <td>
                     <button class="btn btn-sm btn-outline-secondary me-1" data-bs-toggle="modal"
                                    data-bs-target="#modAnimal"  onclick="cargarAnimal(${a.idAnimal})">
                                    <i class='bx bxs-edit'></i>
                                </button>
                                <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal"
                                    data-bs-target="#eliminarAnimal"  onclick="prepararEliminar(${a.idAnimal})"><i class='bx bx-trash'></i></button>
                                <button class="btn btn-sm btn-outline-info"  data-bs-toggle="modal" data-bs-target="#infoAnimal"
                                onclick="mostrarInfoAnimal(${a.idAnimal})">
                                    <i class='bx bxs-info-circle' ></i>
                                </button>
                </td>
            </tr>
        `;
    });
    document.getElementById("cuerpoTabla").innerHTML = filas;
}

function filtrarTodo(especie, estatus) {
    const cuerpo = {};

    cuerpo.especie = especie && especie !== "" ? especie : "Todos";

   
    if (estatus === "Todos" || estatus === "" || estatus === null || estatus === undefined) {
        cuerpo.estatus = 0;
    } else {
        cuerpo.estatus = estatus; 
    }

    
    if (cuerpo.especie === "Todos" && cuerpo.estatus === 0) {
        cargarMascotas();
        return;
    }

    fetch("http://localhost:8080/ProyectoHuellas/api/mascotas/filtroTodos", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(cuerpo)
    })
    .then(res => res.json())
    .then(data => {
        actualizarTabla(data);
    })
    .catch(error => {
        console.error("Error en filtrarTodo:", error);
    });
}



function agregarMascota() {
    const nombre = document.getElementById("nombreAnimal").value.trim();
    const genero = document.getElementById("generoAnimal").value;
    const edad = document.getElementById("edadAnimal").value.trim();
    const peso = document.getElementById("pesoAnimal").value.trim();
    const especie = document.getElementById("especie").value;
    const raza = document.getElementById("razaAnimal").value.trim();
    const tamanio = document.getElementById("tamanioAnimal").value;
    const fotoFile = document.getElementById("fotoAnimal").files[0];
    const centro = document.getElementById("centro").value;
    const descripcion = document.getElementById("descripcionAnimal").value.trim();
    const caracter = document.getElementById("caracter").value;
 const alerta = document.getElementById("fallo");

    if (!nombre || !genero || !edad || !peso || !especie || !fotoFile || !centro || !descripcion || !raza || !tamanio || !caracter) {
        document.getElementById("mensaje-error").innerHTML = "Por favor, completa todos los campos.";
       alerta.classList.remove("d-none");
       alerta.classList.add("show");
       return;
    }


    const reader = new FileReader();
    reader.onloadend = function () {

        const base64Foto = reader.result;


        const nuevaMascota = {
            nombreAnimal: nombre,
            genero: genero,
            edad: edad,
            peso: parseFloat(peso),
            especie: especie,
            descripcion: descripcion,
            raza: raza,
            tamano: tamanio,
            foto: base64Foto,
            idCentro: parseInt(centro),
            caracter: caracter
        };


        fetch("http://localhost:8080/ProyectoHuellas/api/mascotas/saveAnimal", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(nuevaMascota)
        })
            .then(res => res.json())
            .then(data => {

                cargarMascotas();
                cargarContadores();
            })
    };

    reader.readAsDataURL(fotoFile);
}
function cargarAnimal(id) {
    const animal = animales.find(a => a.idAnimal == id);
    if (animal) {
        document.getElementById("idAnimalMod").value = animal.idAnimal;
        document.getElementById("nombreMod").value = animal.nombreAnimal;
        document.getElementById("generoMod").value = animal.genero;
        document.getElementById("edadMod").value = animal.edad;
        document.getElementById("pesoMod").value = animal.peso;
        document.getElementById("especieMod").value = animal.especie;
        document.getElementById("razaMod").value = animal.raza;
        document.getElementById("tamanioMod").value = animal.tamano;
        document.getElementById("descripcionMod").value = animal.descripcion;
        document.getElementById("centroMod").value = animal.idCentro;
        document.getElementById("fotoVistaMod").src = animal.foto;
        document.getElementById("caracterMod").value = animal.caracter;
    }
}

function modificarAnimal() {

    const animal = {
        idAnimal: parseInt(document.getElementById("idAnimalMod").value),
        nombreAnimal: document.getElementById("nombreMod").value,
        genero: document.getElementById("generoMod").value,
        edad: document.getElementById("edadMod").value,
        peso: parseFloat(document.getElementById("pesoMod").value),
        especie: document.getElementById("especieMod").value,
        raza: document.getElementById("razaMod").value,
        tamano: document.getElementById("tamanioMod").value,
        descripcion: document.getElementById("descripcionMod").value,
        idCentro: parseInt(document.getElementById("centroMod").value),
        caracter: document.getElementById("caracterMod").value
    };
    if( !animal.nombreAnimal || !animal.genero || !animal.edad || !animal.peso || !animal.especie || !animal.raza || !animal.tamano || !animal.descripcion || !animal.idCentro || !animal.caracter) {
        const alerta = document.getElementById("fallo");
        document.getElementById("mensaje-error").innerHTML = "Por favor, completa todos los campos.";
        alerta.classList.remove("d-none");
        alerta.classList.add("show");
        return;
    }

    fetch("http://localhost:8080/ProyectoHuellas/api/mascotas/updateAnimal", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(animal)
    })
        .then(res => res.json())
        .then(() => {
            obtenerAnimales();

        })

}


function prepararEliminar(id) {
    idAnimalEliminar = id;
     const animal = animales.find(a => a.idAnimal == id);
     if(animal){
        document.getElementById("nombreA").innerText = `${animal.nombreAnimal}`;
     }
}

function eliminarAnimal() {


    const animal = {
        idAnimal: idAnimalEliminar
    };

    fetch("http://localhost:8080/ProyectoHuellas/api/mascotas/deleteAnimal", {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(animal)
    })
        .then(res => res.json())
        .then(() => {
            cargarMascotas();
            cargarContadores();
        })

}
document.getElementById("buscar").addEventListener("keydown", function (e) {
    if (e.key === "Enter") {
        const termino = this.value.trim();

        if (termino === "") {
            cargarMascotas();
        } else {
            buscarMascotasPorNombre(termino);
        }
    }
});

function buscarMascotasPorNombre(nombre) {
    fetch("http://localhost:8080/ProyectoHuellas/api/mascotas/buscarAnimal", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ nombreAnimal: nombre })
    })
        .then(res => res.json())
        .then(data => {
            actualizarTabla(data);
        })
        ;
}
function cargarContadores() {

    fetch("http://localhost:8080/ProyectoHuellas/api/mascotas/contarDisponibles")
        .then(res => res.json())
        .then(num => {
            document.getElementById("contadorDisponibles").innerHTML = num;
        })


    fetch("http://localhost:8080/ProyectoHuellas/api/mascotas/contarAdoptados")
        .then(res => res.json())
        .then(num => {
            document.getElementById("contadorAdoptados").innerHTML = num;
        })

}
function mostrarInfoAnimal(idAnimal) {
  const animal = animales.find(a => a.idAnimal == idAnimal);
   const centro = centros.find(c => c.idCentro == animal.idCentro);
  if (!animal) return;


  document.getElementById('infoFotoAnimal').src = animal.foto;
  document.getElementById('infoNombre').textContent = animal.nombreAnimal;
  document.getElementById('infoCodigo').textContent = animal.codigoAnimal;
  document.getElementById('infoGenero').textContent = animal.genero;
  document.getElementById('infoEdad').textContent = animal.edad;
  document.getElementById('infoTamanio').textContent = animal.tamano;
  document.getElementById('infoEspecie').textContent = animal.especie;
  document.getElementById('infoRaza').textContent = animal.raza;
  document.getElementById('infoPeso').textContent = `${animal.peso} kg`;
  document.getElementById('infoCentro').textContent = centro.nombreCentro;
  document.getElementById('infoDescripcion').textContent = animal.descripcion;

  const estatusBadge = document.getElementById('infoEstatusBadge');
  const estatusText = document.getElementById('infoEstatus');
  
  if (animal.estatus === 1) {
    estatusBadge.className = 'badge bg-success py-2 px-3 rounded-pill';
    estatusText.textContent = 'Disponible';
  } else if (animal.estatus === 2) {
    estatusBadge.className = 'badge bg-warning py-2 px-3 rounded-pill';
    estatusText.textContent = 'Adoptado';
  } else {
    estatusBadge.className = 'badge bg-secondary py-2 px-3 rounded-pill';
    estatusText.textContent = 'No disponible';
  }

 
}
function cerrarAlerta() {
    const alerta = document.getElementById("fallo");
    alerta.classList.add("d-none");
    alerta.classList.remove("show");
}

document.getElementById('agregarAnimal').addEventListener('hidden.bs.modal', function () {
    cerrarAlerta(); 
});
document.getElementById('modAnimal').addEventListener('hidden.bs.modal', function () {
    cerrarAlerta(); 
});