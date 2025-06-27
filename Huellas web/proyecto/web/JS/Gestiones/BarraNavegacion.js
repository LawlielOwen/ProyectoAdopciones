const barraNavega = document.querySelector('.barraNavegacion');
const botonBarra = document.getElementById('botonBarra');


botonBarra.addEventListener('click', () => {
  barraNavega.classList.toggle('minimizar');

})