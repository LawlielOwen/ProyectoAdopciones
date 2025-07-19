document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector('.donation-form');
    const amountBtns = document.querySelectorAll('.amount-btn');
    const amountInput = document.querySelector('.amount-input');
    let selectedAmount = null;

    // Manejar los botones de importe
    amountBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            amountBtns.forEach(b => b.classList.remove('selected'));
            this.classList.add('selected');
            selectedAmount = parseFloat(this.innerText.replace('$', '').trim());
            amountInput.value = '';
        });
    });

    // Manejar input personalizado
    amountInput.addEventListener('input', function() {
        amountBtns.forEach(b => b.classList.remove('selected'));
        selectedAmount = parseFloat(amountInput.value);
    });

    form.addEventListener('submit', async function(e) {
        e.preventDefault();

        // Obtener valores
        const nombreDonante = document.getElementById('nombre').value.trim();
        const centroBeneficiadoDonacion = document.getElementById('institucion').value.trim();
        // El id_centro lo dejamos fijo en 1 como en el backend o ajústalo según tu lógica

        // Validar monto
        if (!selectedAmount || selectedAmount < 1) {
            alert("Por favor, seleccione o escriba un importe válido.");
            return;
        }
        if (!centroBeneficiadoDonacion || !nombreDonante) {
            alert("Por favor, completa todos los campos requeridos.");
            return;
        }

        // Construir el objeto Donaciones
        const donacion = {
            idDonacion: 0,
            nombreDonante: nombreDonante,
            montoDonacion: selectedAmount,
            centroDonacion: centroBeneficiadoDonacion
        };

        // Enviar a la API
        try {
            const response = await fetch('http://localhost:8080/holaM/api/Saludo/saveDonacion', {
                method: 'POST',
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: "datosDonacion=" + encodeURIComponent(JSON.stringify(donacion))
            });

            const result = await response.json();

            if (result.error) {
                alert("Ocurrió un error: " + result.error);
            } else {
                alert("¡Gracias por tu donación!");
                form.reset();
                amountBtns.forEach(b => b.classList.remove('selected'));
                selectedAmount = null;
            }
        } catch (err) {
            alert("Hubo un error al registrar la donación. Inténtalo más tarde.");
            console.error(err);
        }
    });
});
