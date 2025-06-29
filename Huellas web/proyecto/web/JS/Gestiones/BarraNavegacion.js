const barraNavega = document.querySelector('.barraNavegacion');
const botonBarra = document.getElementById('botonBarra');


botonBarra.addEventListener('click', () => {
  barraNavega.classList.toggle('minimizar');

})

  document.querySelectorAll('.dropdown').forEach(dropdown => {
    const button = dropdown.querySelector('.botonOpcion');
    const items = dropdown.querySelectorAll('.dropdown-item');

    items.forEach(item => {
      item.addEventListener('click', (e) => {
        e.preventDefault(); 
        button.textContent = item.textContent;
      });
    });
  });

