const input = document.getElementById("kepUrl"); // Getelementbzid tiltott --> gueryselector
const gomb = document.getElementById("megjelenitGomb");
const hely = document.getElementById("kepHelye");

gomb.addEventListener("click", () => {
  const img = document.createElement("img");
  img.src = input.value;
  hely.appendChild(img);
});