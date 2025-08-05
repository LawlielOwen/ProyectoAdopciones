let solicitudes = [];
let animales = [];
let adoptantes = [];
let idsolicitud = null;
document.addEventListener("DOMContentLoaded", async function () {
  await cargarAdoptante();
  await cargarMascotas();
  cargarSolicitudes();
  cargarContadores();
  document.getElementById("Todos").addEventListener("click", () => cargarTodas());
  document.getElementById("Aceptada").addEventListener("click", () => filtrarPorEstatus(1));
  document.getElementById("Pendiente").addEventListener("click", () => filtrarPorEstatus(2));
  document.getElementById("Rechazada").addEventListener("click", () => filtrarPorEstatus(3));
});

function cargarSolicitudes() {
  fetch("http://localhost:8080/ProyectoHuellas/api/solicitudes/getAll")
    .then(res => res.json())
    .then(data => {
      solicitudes = data;
      let filas = "";

      solicitudes.forEach(soli => {
        const animal = animales.find(a => a.idAnimal === soli.idAnimal);

        filas += `
          <tr>
            <td class="mascotasCol">
              <span>
                <img src="${animal.foto}" alt="Foto de ${animal.nombreAnimal}" style="width: 50px; height: 50px; object-fit: cover;">
                ${animal.nombreAnimal}
              </span>
            </td>
            <td><span class="activado  bg-${soli.estatus === 1 ? 'success' : soli.estatus === 2 ? 'warning' : 'danger'}"></span> 
                ${soli.estatus === 1 ? 'Aceptada' : soli.estatus === 2 ? 'Pendiente' : 'Rechazada'}
            </td>
            <td>${soli.fecha}</td>
            <td>${soli.nombreAdoptante}</td>
            <td>${soli.correo}</td>
            <td>
              <button class="btn btn-sm btn-outline-info" data-bs-toggle="modal" data-bs-target="#revisarSolicitud" onclick="mostrarSolicitud(${soli.idSolicitud})">
                <i class='bx bxs-info-circle'></i>
              </button>
              <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal" data-bs-target="#eliminarSolicitud" onclick="elimacionCarga(${soli.idSolicitud})">
                <i class='bx bx-trash'></i>
              </button>
            </td>
          </tr>
        `;
      });

      document.getElementById("cuerpoTabla").innerHTML = filas;
    });
}
async function cargarMascotas() {
  const res = await fetch("http://localhost:8080/ProyectoHuellas/api/mascotas/getAll");
  const data = await res.json();
  animales = data;
}

async function cargarAdoptante() {
  const res = await fetch("http://localhost:8080/ProyectoHuellas/api/adoptante/getAll");
  const data = await res.json();
  adoptantes = data;
}

function mostrarSolicitud(idSolicitud) {
  const solicitud = solicitudes.find(s => s.idSolicitud === idSolicitud);
  if (!solicitud) return;
  idsolicitud = solicitud.idSolicitud;
  idAnimalSeleccionado = solicitud.idAnimal;

  const animal = animales.find(a => a.idAnimal === solicitud.idAnimal);
  const adoptante = adoptantes.find(d => d.idAdoptante === solicitud.idAdoptante);
  const estatusTexto = solicitud.estatus === 1 ? "Aceptada" : solicitud.estatus === 2 ? "Pendiente" : solicitud.estatus === 3 ? "Rechazada" : "Desconocido";
  const estatusColor = solicitud.estatus === 1 ? "bg-success" : solicitud.estatus === 2 ? "bg-warning" : solicitud.estatus === 3 ? "bg-danger" : "bg-secondary";

  const informacion = ` <div class="modal-header bg-primary text-white">
        <h5 class="modal-title d-flex align-items-center gap-2" id="revisarSolicitudLabel">
          <i class='bx bxs-file-doc fs-4'></i> Detalles de Solicitud de Adopción
        </h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
     
      <div class="modal-body">
        <div class="row">
     
          <div class="col-md-6">
            <div class="card mb-3 border-0">
              <div class="card-header bg-light fw-bold">Información Principal</div>
              <div class="card-body">
                <div class="mb-3">
                  <label class="form-label text-muted small">Mascota:</label>
                  <p class="fw-bold" id="solicitudMascota">${solicitud.nombreAnimal}</p>
                </div>
                <div class="mb-3">
                  <label class="form-label text-muted small">Adoptante:</label>
                  <p class="fw-bold" id="solicitudAdoptante">${solicitud.nombreAdoptante}</p>
                </div>
                <div class="mb-3">
                  <label class="form-label text-muted small">Fecha de solicitud:</label>
                  <p class="fw-bold" id="solicitudFecha">${solicitud.fecha}</p>
                </div>
                <div class="mb-3">
                  <label class="form-label text-muted small">Estatus actual:</label>
                  <p><span class="badge ${estatusColor}" id="solicitudEstatus">${estatusTexto}</span></p>

                </div>
              </div>
            </div>
          </div>
          
          <div class="col-md-6">
            <div class="card mb-3 border-0">
              <div class="card-header bg-light fw-bold">Datos de Contacto</div>
              <div class="card-body">
                <div class="mb-3">
                  <label class="form-label text-muted small">Teléfono:</label>
                  <p class="fw-bold" id="solicitudContacto">${solicitud.telefono}</p>
                </div>
                <div class="mb-3">
                  <label class="form-label text-muted small">Correo electrónico:</label>
                  <p class="fw-bold" id="solicitudCorreo">${solicitud.correo}</p>
                </div>
                <div class="mb-3">
                  <label class="form-label text-muted small">Dirección:</label>
                  <p class="fw-bold" id="solicitudDireccion">${solicitud.direccion}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="dropdown d-grid">
  <button class="btn btn-secondary border dropdown-toggle py-2" type="button" 
          data-bs-toggle="dropdown" aria-expanded="false"
          style="font-weight: bold;">
    <i class='bx bx-expand-alt me-2'></i>Ver motivo completo
  </button>
  <div class="dropdown-menu w-100 p-3">
    <p class="mb-0" style="white-space: pre-line;">${solicitud.motivo}</p>
  </div>
</div>
 

      <div class="modal-footer d-flex  align-items-center justify-content-center">
        <button type="submit" class="btn btn-success px-4" id="btnAceptar" onclick="aceptarSolicitud()">
          <i class='bx bx-check-circle me-2'></i>Aprobar
        </button>
        <button type="submit" class="btn btn-danger px-4" id="btnRechazar" onclick="rechazarSolicitud()">
          <i class='bx bx-x-circle me-2'></i>Rechazar
        </button>
        <button type="button" class="btn btn-secondary px-4" data-bs-dismiss="modal">
          <i class='bx bx-door-open me-2'></i>Cerrar
        </button>
      </div>
    `;

  document.getElementById("informacion").innerHTML = informacion;
  const btnAceptar = document.getElementById("btnAceptar");
  const btnRechazar = document.getElementById("btnRechazar");
if (solicitud.estatus === 1) {
 
  btnAceptar.style.display = "none";
  btnRechazar.style.display = "none";
} else if (solicitud.estatus === 3) {
  
  btnAceptar.style.display = "none";
  btnRechazar.style.display = "none";
} else {
 
  btnAceptar.style.display = "inline-block";
  btnRechazar.style.display = "inline-block";
}
}
function aceptarSolicitud() {

  const soli = {
    idSolicitud: idsolicitud,
    idAnimal: idAnimalSeleccionado
  };
  fetch("http://localhost:8080/ProyectoHuellas/api/solicitudes/aceptarSoli", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(soli)
  })
    .then(res => res.json())
    .then(() => {
      cargarSolicitudes();
      cargarContadores();
      const modal = bootstrap.Modal.getInstance(document.getElementById('revisarSolicitud'));
      modal.hide();
    })

}
function rechazarSolicitud() {

  const soli = {
    idSolicitud: idsolicitud
  };
  fetch("http://localhost:8080/ProyectoHuellas/api/solicitudes/rechazarSoli", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(soli)
  })
    .then(res => res.json())
    .then(() => {
      cargarSolicitudes();
      cargarContadores();
      const modal = bootstrap.Modal.getInstance(document.getElementById('revisarSolicitud'));
      modal.hide();
    })

}
function cargarContadores() {

  fetch("http://localhost:8080/ProyectoHuellas/api/solicitudes/contarDisponibles")
    .then(res => res.json())
    .then(num => {
      document.getElementById("contadorPendientes").innerHTML = num;
    })

  fetch("http://localhost:8080/ProyectoHuellas/api/solicitudes/contarAceptadas")
    .then(res => res.json())
    .then(num => {
      document.getElementById("contadorAceptadas").innerHTML = num;
    })

  fetch("http://localhost:8080/ProyectoHuellas/api/solicitudes/contarRechazadas")
    .then(res => res.json())
    .then(num => {
      document.getElementById("contadorRechazadas").innerHTML = num;
    })
}
function filtrarPorEstatus(estatus) {
  fetch("http://localhost:8080/ProyectoHuellas/api/solicitudes/filtroEstatus", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ estatus: estatus })
  })
    .then(res => res.json())
    .then(data => {
      solicitudes = data;
      actualizarTabla(data);
    });
}
function actualizarTabla(data) {
  let filas = "";
  data.forEach(a => {
    const animal = animales.find(an => an.idAnimal === a.idAnimal);

    filas += `
          <tr>
            <td class="mascotasCol">
              <span>
                <img src="${animal.foto}" alt="Foto de ${animal.nombreAnimal}" style="width: 50px; height: 50px; object-fit: cover;">
                ${animal.nombreAnimal}
              </span>
            </td>
            <td><span class="activado  bg-${a.estatus === 1 ? 'success' : a.estatus === 2 ? 'warning' : 'danger'}"></span> 
                ${a.estatus === 1 ? 'Aceptada' : a.estatus === 2 ? 'Pendiente' : 'Rechazada'}
            </td>
            <td>${a.fecha}</td>
            <td>${a.nombreAdoptante}</td>
            <td>${a.correo}</td>
            <td>
              <button class="btn btn-sm btn-outline-info" data-bs-toggle="modal" data-bs-target="#revisarSolicitud" onclick="mostrarSolicitud(${a.idSolicitud})">
                <i class='bx bxs-info-circle'></i>
              </button>
              <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal" data-bs-target="#eliminarSolicitud" onclick="prepararEliminar(${a.idSolicitud})">
                <i class='bx bx-trash'></i>
              </button>
            </td>
          </tr>
        `;
  });
  document.getElementById("cuerpoTabla").innerHTML = filas;
}
document.getElementById("buscar").addEventListener("keydown", function (e) {
    if (e.key === "Enter") {
        const termino = this.value.trim();

        if (termino === "") {
            cargarSolicitudes();
        } else {
            buscarSolicitudPorNombre(termino);
        }
    }
});
function buscarSolicitudPorNombre(nombre) {
    fetch("http://localhost:8080/ProyectoHuellas/api/solicitudes/buscarSoli", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({nombreAnimal: nombre, nombreAdoptante: nombre })
    })
        .then(res => res.json())
        .then(data => {
            actualizarTabla(data);
        })
        ;
}
async function cargarTodas() {
 
    const res = await fetch("http://localhost:8080/ProyectoHuellas/api/solicitudes/getTodas");
    const data = await res.json();
    solicitudes = data;
    actualizarTabla(data);

}
function elimacionCarga(id){
  idsolicitud = id;
}
function eliminarSolicitud(){
  
  const soli = {
    idSolicitud: idsolicitud
  };
  fetch("http://localhost:8080/ProyectoHuellas/api/solicitudes/eliminarSoli", {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(soli)
  })
    .then(res => res.json())
    .then(() => {
      cargarSolicitudes();
      cargarContadores();
    })

}