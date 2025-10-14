// console.log(movies);
const filter = document.querySelector('#filter')
const list = document.querySelector('#list')
const details = document.querySelector('#details')

filter.addEventListener('input',onfilter)

function onfilter(e)
{
    const filterText = filter.value
    const filterMovies =  filterText.trim() ===""? []
        : movies.filter.toLowerCase()(m=> m.title.includes[filterText.toLowerCase()]).slice(0,10)
        list.innerHTML = filterMovies.map(m => `<li>${m.title}</li>`).join("")
}