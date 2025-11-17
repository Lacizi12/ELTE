/*
[x] 1. UI, statikus prototípus
[~] 2. Data
[ ] 3. Event handler
*/
// Data
// console.log(movies);

// Elements
const filter = document.querySelector("#filter");
const list = document.querySelector("#list");
const details = document.querySelector("#details");

// Event handler
filter.addEventListener("input", onFilter);
function onFilter(e) {
  const filterText = filter.value;
  //////////////////////////
  const filteredMovies = filterText.trim() === "" 
    ? [] 
    : movies
        .filter(m => 
          m.title.toLowerCase().includes(filterText.toLowerCase()))
        .slice(0, 10);
        //az első 10 elemet adja vissza
        
        //id nélkül: próbáld ki úgy, hogy két azonos című film van a movies-ban!
list.innerHTML = filteredMovies
    .map(m => `<li>${m.title}</li>`)
    .join("");
}

list.addEventListener("click", onClick);
function onClick(e) {
   //console.log(e.target);
  if (e.target.matches("li")) {
    const movie = movies.find(m => m.title === e.target.innerHTML);
    details.innerHTML = `
      <h2>${movie.title}</h2>
      <p>${movie.director} (${movie.year})</p>
    `;
  }
}

//id-val: próbáld ki két azonos című filmmel!
//map minden filmobjektumhoz egy HTML sort készít: <li data-id="...">Cím</li>.A data-id="${m.id}" egy egyedi azonosítót  tesz az <li>-re. Ezt később a kattintásnál ki tudjuk olvasni.
/*list.innerHTML = filteredMovies
    .map(m => `<li data-id="${m.id}">${m.title}</li>`)
    .join("");
        }

list.addEventListener("click", onClick);
function onClick(e) {
  if (e.target.matches("li")) {
    console.log(e.target.dataset); //dataset egy objektum, ami az összes data-id attribútumot tartalmazza
    console.log(e.target.dataset.id); //a data-id attribútum értéke stringként
   // A kattintott <li> data-id értéke string, számmá alakítjuk.
    const id = parseInt(e.target.dataset.id);
    const movie = movies.find(m => m.id === id);
    details.innerHTML = `
      <h2>${movie.title}</h2>
      <p>${movie.director} (${movie.year})</p>
    `;
  }
}*/
