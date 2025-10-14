//Allapotter
let pixels = []
let color = "FF0000"

function initPixels(w, h) {
  pixels = []

  for (let i = 0; i < h; i++) {
    const row = []
    for (j = 0; j < w; j++) {
      row.push("")
    }
    pixels.push(row)
  }
}

function select(x, y, color) {
  pixels[y][x] = color
}

function XYCoord(td) {

  return {x, y}
}


//Elemek
const inputWidth=document.querySelector("#width")
const inputHeight=document.querySelector("#height")
const editor=document.querySelector("#editor")
const btnGenerate=document.querySelector("#btnGenerate")

//html generáló

function genTable(pixels) {
  return `
  <table class="edit">
    ${pixels.map((row) =>
      `<tr>
        ${row.map(() => `<td></td>`).join("")}
      </tr>`
    ).join("")}
  </table>
  `
}


//Eseménykezelés

btnGenerate.addEventListener("click", onGenerate)

function onGenerate(e) {

  const w = inputWidth.valueAsNumber
  const h = inputHeight.valueAsNumber

  initPixels(w, h)
  editor.innerHTML = genTable(pixels)
}

editor.addEventListener("click", onCellClick)

function onCellClick(e) {
  if (e.target.matches("td")) {

    console.log(e.target);
    const { x, y } = XYCoord(e.target)
    select(x, y, color)
    e.target.style.backgroundColor = color
  }
}
