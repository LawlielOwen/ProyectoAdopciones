// Variables globales
let empleados = [];
let idEmpleadoEliminar = null;

// Esperar a que el DOM esté completamente cargado
document.addEventListener("DOMContentLoaded", function() {
    // Inicializar eventos
    inicializarEventos();
    
    // Cargar datos iniciales
    cargarEmpleados();
    cargarCentros();
});

function inicializarEventos() {
    // Formulario para agregar empleado
    const formAgregar = document.getElementById("formAgregarEmpleado");
    if (formAgregar) {
        formAgregar.addEventListener("submit", function(e) {
            e.preventDefault();
            agregarEmpleado();
        });
    }

    // Formulario para editar empleado
    const formEditar = document.getElementById("formEditarEmpleado");
    if (formEditar) {
        formEditar.addEventListener("submit", function(e) {
            e.preventDefault();
            editarEmpleado();
        });
    }

    // Botón de confirmación para eliminar empleado
    const btnConfirmarEliminar = document.getElementById("confirmarEliminar");
    if (btnConfirmarEliminar) {
        btnConfirmarEliminar.addEventListener("click", eliminarEmpleado);
    }

    // Filtros de estatus
    document.getElementById("todosEstatus")?.addEventListener("click", () => cargarEmpleados());
    document.getElementById("activos")?.addEventListener("click", () => filtrarEmpleadosPorEstatus(1));
    document.getElementById("inactivos")?.addEventListener("click", () => filtrarEmpleadosPorEstatus(0));

    // Buscador
    document.getElementById("btnBuscar")?.addEventListener("click", buscarEmpleados);
    document.getElementById("buscarEmpleado")?.addEventListener("keypress", function(e) {
        if (e.key === "Enter") {
            buscarEmpleados();
        }
    });
}

// Función para cargar la lista de empleados
function cargarEmpleados() {
    const API_BASE = "http://localhost:8080/ProyectoHuellas/api/Empleados/listar";
    
    fetch(API_BASE)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            empleados = data;
            mostrarEmpleados(data);
        })
        .catch(error => {
            console.error("Error al cargar empleados:", error);
            mostrarError("No se pudo cargar la lista de empleados. Intente nuevamente.");
        });
}

// Función para mostrar empleados en la tabla
function mostrarEmpleados(empleados) {
    const tbody = document.getElementById("cuerpoTablaEmpleados");
    if (!tbody) return;

    tbody.innerHTML = "";

    if (empleados.length === 0) {
        tbody.innerHTML = `<tr><td colspan="9" class="text-center">No se encontraron empleados</td></tr>`;
        return;
    }

    empleados.forEach(empleado => {
        const tr = document.createElement("tr");
        
        // Determinar el texto y clase del estatus
        let estatusTexto, estatusClase;
        if (empleado.estatus === 1) {
            estatusTexto = "Activo";
            estatusClase = "bg-success";
        } else {
            estatusTexto = "Inactivo";
            estatusClase = "bg-danger";
        }

        tr.innerHTML = `
            <td>${empleado.nombre} ${empleado.app} ${empleado.apm || ''}</td>
            <td><span class="active ${estatusClase}"></span> ${estatusTexto}</td>
            <td>${empleado.genero || 'No especificado'}</td>
            <td>${empleado.fechaNacimiento || 'No especificada'}</td>
            <td>${empleado.codigo || 'N/A'}</td>
            <td>${empleado.rol || 'No especificado'}</td>
            <td>${empleado.telefono || 'N/A'}</td>
            <td>${empleado.correo || 'N/A'}</td>
            <td>
                <button class="btn btn-sm btn-outline-secondary me-1" 
                        data-bs-toggle="modal" data-bs-target="#modEmpleado" 
                        onclick="cargarDatosEdicion(${empleado.idEmpleado})">
                    <i class='bx bxs-edit'></i>
                </button>
                <button class="btn btn-sm btn-outline-danger" 
                        data-bs-toggle="modal" data-bs-target="#eliminarEmpleado" 
                        onclick="prepararEliminacion(${empleado.idEmpleado}, '${empleado.nombre} ${empleado.app}')">
                    <i class='bx bx-trash'></i>
                </button>
            </td>
        `;
        
        tbody.appendChild(tr);
    });
}

// Función para cargar centros en los select
function cargarCentros() {
    fetch("http://localhost:8080/ProyectoHuellas/api/centros/getAll")
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const selectAgregar = document.getElementById("centro");
            const selectEditar = document.getElementById("centroMod");
            
            if (selectAgregar && selectEditar) {
                let options = '<option value="" selected disabled>Seleccione un centro</option>';
                data.forEach(centro => {
                    options += `<option value="${centro.idCentro}">${centro.nombreCentro}</option>`;
                });
                
                selectAgregar.innerHTML = options;
                selectEditar.innerHTML = options;
            }
        })
        .catch(error => {
            console.error("Error al cargar centros:", error);
        });
}

// Función para agregar un nuevo empleado
function agregarEmpleado() {
    const form = document.getElementById("formAgregarEmpleado");
    const fotoInput = document.getElementById("foto");
    
    if (!form || !fotoInput) return;

    // Validar que se haya seleccionado una foto
    if (fotoInput.files.length === 0) {
        mostrarError("Debe seleccionar una fotografía");
        return;
    }

    // Leer la foto como base64
    const reader = new FileReader();
    reader.onload = function() {
        const empleado = {
            nombre: form.nombre.value.trim(),
            app: form.app.value.trim(),
            apm: form.apm.value.trim(),
            fechaNacimiento: form.fechaNacimiento.value,
            genero: form.genero.value,
            correo: form.correo.value.trim(),
            contraseña: form.contraseña.value,
            foto: reader.result,
            telefono: form.telefono.value.trim(),
            direccion: form.direccion.value.trim(),
            CP: form.CP.value.trim(),
            rol: form.rol.value,
            centro: form.centro.value
        };

        // Enviar datos al servidor
        fetch("http://localhost:8080/ProyectoHuellas/api/Empleados/insertar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(empleado)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            // Cerrar modal y recargar lista
            const modal = bootstrap.Modal.getInstance(document.getElementById("agregarEmpleado"));
            modal.hide();
            form.reset();
            cargarEmpleados();
            mostrarExito("Empleado agregado correctamente");
        })
        .catch(error => {
            console.error("Error al agregar empleado:", error);
            mostrarError("No se pudo agregar el empleado. Verifique los datos.");
        });
    };
    reader.readAsDataURL(fotoInput.files[0]);
}

// Función para cargar datos en el modal de edición
function cargarDatosEdicion(idEmpleado) {
    const empleado = empleados.find(e => e.idEmpleado == idEmpleado);
    if (!empleado) return;

    // Llenar el formulario de edición con los datos del empleado
    document.getElementById("idEmpleadoMod").value = empleado.idEmpleado;
    document.getElementById("nombreMod").value = empleado.nombre;
    document.getElementById("appMod").value = empleado.app;
    document.getElementById("apmMod").value = empleado.apm || '';
    document.getElementById("fechaNacimientoMod").value = empleado.fechaNacimiento;
    document.getElementById("generoMod").value = empleado.genero;
    document.getElementById("correoMod").value = empleado.correo;
    document.getElementById("telefonoMod").value = empleado.telefono;
    document.getElementById("direccionMod").value = empleado.direccion;
    document.getElementById("CPMod").value = empleado.CP;
    document.getElementById("rolMod").value = empleado.rol;
    document.getElementById("centroMod").value = empleado.centro;
    
    // Mostrar la foto actual
    const fotoPreview = document.getElementById("fotoVistaMod");
    if (fotoPreview && empleado.foto) {
        fotoPreview.src = empleado.foto;
    }
}

// Función para editar un empleado
function editarEmpleado() {
    const form = document.getElementById("formEditarEmpleado");
    if (!form) return;

    const empleado = {
        idEmpleado: document.getElementById("idEmpleadoMod").value,
        nombre: form.nombreMod.value.trim(),
        app: form.appMod.value.trim(),
        apm: form.apmMod.value.trim(),
        fechaNacimiento: form.fechaNacimientoMod.value,
        genero: form.generoMod.value,
        correo: form.correoMod.value.trim(),
        telefono: form.telefonoMod.value.trim(),
        direccion: form.direccionMod.value.trim(),
        CP: form.CPMod.value.trim(),
        rol: form.rolMod.value,
        centro: form.centroMod.value
    };

    fetch("http://localhost:8080/ProyectoHuellas/api/Empleados/modificar", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(empleado)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        // Cerrar modal y recargar lista
        const modal = bootstrap.Modal.getInstance(document.getElementById("modEmpleado"));
        modal.hide();
        cargarEmpleados();
        mostrarExito("Empleado actualizado correctamente");
    })
    .catch(error => {
        console.error("Error al editar empleado:", error);
        mostrarError("No se pudo actualizar el empleado. Verifique los datos.");
    });
}

// Función para preparar la eliminación de un empleado
function prepararEliminacion(idEmpleado, nombreCompleto) {
    idEmpleadoEliminar = idEmpleado;
    document.getElementById("nombreEmpleadoEliminar").textContent = nombreCompleto;
}

// Función para eliminar un empleado
function eliminarEmpleado() {
    if (!idEmpleadoEliminar) return;

    fetch(`http://localhost:8080/ProyectoHuellas/api/Empleados/eliminar/${idEmpleadoEliminar}`, {
        method: "DELETE"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        // Cerrar modal y recargar lista
        const modal = bootstrap.Modal.getInstance(document.getElementById("eliminarEmpleado"));
        modal.hide();
        cargarEmpleados();
        mostrarExito("Empleado eliminado correctamente");
        idEmpleadoEliminar = null;
    })
    .catch(error => {
        console.error("Error al eliminar empleado:", error);
        mostrarError("No se pudo eliminar el empleado. Intente nuevamente.");
    });
}

// Función para filtrar empleados por estatus
function filtrarEmpleadosPorEstatus(estatus) {
    fetch(`http://localhost:8080/ProyectoHuellas/api/Empleados/listar?estatus=${estatus}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            mostrarEmpleados(data);
        })
        .catch(error => {
            console.error("Error al filtrar empleados:", error);
            mostrarError("No se pudo filtrar la lista de empleados.");
        });
}

// Función para buscar empleados
function buscarEmpleados() {
    const termino = document.getElementById("buscarEmpleado")?.value.trim();
    if (!termino || termino.length < 2) {
        cargarEmpleados();
        return;
    }

    fetch(`http://localhost:8080/ProyectoHuellas/api/Empleados/buscar?filtro=${encodeURIComponent(termino)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            mostrarEmpleados(data);
        })
        .catch(error => {
            console.error("Error al buscar empleados:", error);
            mostrarError("No se pudo realizar la búsqueda. Intente nuevamente.");
        });
}

// Funciones auxiliares para mostrar mensajes
function mostrarError(mensaje) {
    // Implementar lógica para mostrar mensajes de error
    console.error(mensaje);
    alert(mensaje); // Reemplazar con un sistema de notificaciones mejorado
}

function mostrarExito(mensaje) {
    // Implementar lógica para mostrar mensajes de éxito
    console.log(mensaje);
    alert(mensaje); // Reemplazar con un sistema de notificaciones mejorado
}