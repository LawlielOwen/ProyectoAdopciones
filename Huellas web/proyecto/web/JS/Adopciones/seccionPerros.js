animales = [];
let centros = [];
let paginaActual = 1;
const animalesPorPagina = 6;
document.addEventListener("DOMContentLoaded", function () {
  cargarMascotas();
  cargarCentros();
});
function cargarCentros() {
  fetch("http://localhost:8080/ProyectoHuellas/api/centros/getAll")
    .then(res => res.json())
    .then(data => {
      centros = data;
    });
}



function cargarMascotas() {
  fetch("http://localhost:8080/ProyectoHuellas/api/mascotas/getPerros")
    .then(res => res.json())
    .then(data => {
      animales = data;
      paginaActual = 1; 
      mostrarPagina(animales);
    });
}
function mostrarPagina(listaAnimales) {
  const contenedor = document.getElementById("contenedorCartas");
  contenedor.innerHTML = "";

  const inicio = (paginaActual - 1) * animalesPorPagina;
  const fin = inicio + animalesPorPagina;
  const pagina = listaAnimales.slice(inicio, fin);

  if (pagina.length === 0) {
    contenedor.innerHTML = `<p class="text-center w-100">No se encontraron resultados.</p>`;
    return;
  }

  pagina.forEach(a => {
    contenedor.innerHTML += `
      <div class="col">
        <div class="card cartaAnimal">
          <img src="${a.foto}" class="card-img-top" alt="Foto de ${a.nombreAnimal}">
          <div class="card-body">
            <p class="card-text categoria">${a.especie}</p>
            <div class="info">
              <h5 class="card-title fw-bold">${a.nombreAnimal}</h5>
              <div class="row">
                <div class="col-6">
                  <p class="card-text nowrap">Edad: ${a.edad} años</p>
                </div>
                <div class="col-6">
                  <p class="card-text nowrap">Sexo: ${a.genero}</p>
                </div>
              </div>
              <p class="card-text mt-1">Carácter: ${a.caracter}</p>
            </div>
            <div class="text-center">
              <button class="btn button-carta" data-bs-toggle="modal" data-bs-target="#info"
                onclick="mostrarInfo(${a.idAnimal})">
                Adóptame 🐾
              </button>
            </div>
          </div>
        </div>
      </div>`;
  });

  mostrarControlesPaginacion(listaAnimales.length);
}
function mostrarControlesPaginacion(totalAnimales) {
  const totalPaginas = Math.ceil(totalAnimales / animalesPorPagina);
  const paginacion = document.getElementById("paginacion");
  
  let html = `
    <nav aria-label="Paginación de animales">
      <ul class="pagination">
        <li class="page-item ${paginaActual === 1 ? 'disabled' : ''}">
          <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual - 1})">Anterior</a>
        </li>`;

  for (let i = 1; i <= totalPaginas; i++) {
    html += `
      <li class="page-item ${i === paginaActual ? 'active' : ''}">
        <a class="page-link" href="#" onclick="cambiarPagina(${i})">${i}</a>
      </li>`;
  }

  html += `
        <li class="page-item ${paginaActual === totalPaginas ? 'disabled' : ''}">
          <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual + 1})">Siguiente</a>
        </li>
      </ul>
    </nav>`;

  paginacion.className = "pagination-container";
  paginacion.innerHTML = html;
}
function cambiarPagina(pagina) {
  const totalPaginas = Math.ceil(animales.length / animalesPorPagina);
  if (pagina >= 1 && pagina <= totalPaginas) {
    paginaActual = pagina;
    mostrarPagina(animales);
  }
}


function mostrarInfo(id) {
  const animal = animales.find(a => a.idAnimal === id);
  if (!animal) return;
  const centro = centros.find(c => c.idCentro === animal.idCentro);
  const informacion = `
    <div class="modal-dialog modal-dialog-centered modal-lg">
  <div class="modal-content border-0 shadow-lg rounded-4">
    <div class="modal-header headerModal text-white border-0 rounded-top">
      <h5 class="modal-title w-100 text-center fs-4 fw-bold">
        <i class='bx bx-cat'></i> ${animal.nombreAnimal}
      </h5>
      <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
    </div>

    <div class="modal-body p-0">
      <div class="row g-0">
        <div class="col-md-5 d-flex align-items-center bg-light p-4">
          <img src="${animal.foto}" alt="Foto de ${animal.nombreAnimal}" 
               class="img-fluid rounded-3 imagen object-fit-cover w-100" style="height: 300px;">
        </div>
        <div class="col-md-7 p-4">
          <div class="row mb-3">
            <div class="col-6">
              <p class="mt-2">
                <span class="fw-semibold text-secondary">Edad:</span><br>
                <span class="text-dark"><i class='bx bx-calendar-heart'></i> ${animal.edad} años</span>
              </p>
              <p class="mt-2">
                <span class="fw-semibold text-secondary">Género:</span><br>
                <span class="text-dark"><i class='bx bx-${animal.genero === 'Hembra' ? 'female' : 'male'}'></i> ${animal.genero}</span>
              </p>
              <p class="mb-1">
                <span class="fw-semibold text-secondary">Raza:</span><br>
                <span class="text-dark"><i class="bx bx-shield"></i> ${animal.raza}</span>
              </p>
            </div>

            <div class="col-6">
              <p class="mt-2">
                <span class="fw-semibold text-secondary">Peso:</span><br>
                <span class="text-dark"><i class="bx bx-line-chart"></i> ${animal.peso} kg</span>
              </p>
              <p class="mt-2">
                <span class="fw-semibold text-secondary">Tamaño:</span><br>
                <span class="text-dark"><i class="bx bx-expand"></i> ${animal.tamano}</span>
              </p>
              <p class="mb-2">
                <span class="fw-semibold text-secondary">Centro:</span><br>
                <span class="text-dark"><i class="bx bx-home-alt"></i> ${centro ? centro.nombreCentro : 'No disponible'}</span>
              </p>
            </div>
          </div>

          <div class="border-top pt-3 mb-4">
            <h6 class="fw-semibold text-secondary mb-2">Descripción</h6>
            <p class="text-muted lh-lg fs-6 ">${animal.descripcion || 'Sin descripción disponible.'}</p>
          </div>
          <div class="d-flex flex-wrap gap-2">
           <button class="btn btn-success px-4 py-2 rounded-3 d-flex align-items-center gap-2 shadow-sm"
            onclick="revisarSesion(${animal.idAnimal})">
              <i class='bx bxs-heart'></i> Adóptame
            </button>
            <button class="btn btn-outline-dark px-4 py-2 rounded-3 d-flex align-items-center gap-2 shadow-sm" data-bs-dismiss="modal">
              <i class='bx bx-x-circle'></i> Cerrar
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

`;


  document.getElementById("informacion").innerHTML = informacion;

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
  fetch("http://localhost:8080/ProyectoHuellas/api/inicio/buscarPerro", {
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
function actualizarTabla(animalesFiltrados) {
  animales = animalesFiltrados;
  paginaActual = 1;
  mostrarPagina(animales);
}

function aplicarFiltros() {
  const genero = document.getElementById("filtroSexo").value;
  const tamano = document.getElementById("filtroTamano").value;
   const caracter = document.getElementById("filtroCaracter").value;
  fetch("http://localhost:8080/ProyectoHuellas/api/inicio/filtroPerros", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      genero: genero,
      tamano: tamano,
      caracter : caracter
    })
  })
    .then(res => res.json())
    .then(data => {

      actualizarTabla(data);
    })
    ;
}
