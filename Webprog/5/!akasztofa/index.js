// Application state
const theWord = wordList[random(0, wordList.length - 1)];
const guesses = [];
const buttons = "aábcdeéfghiíjklmnoóöőpqrstuúüűvwxyz";
const MAX_WRONG_GUESSES = 9;

function random(a, b) {
  return Math.floor(Math.random() * (b - a + 1)) + a;
}
/*
isWon() megmondja, hogy a játékos már kitalálta-e az összes betűt a szóban.
split: kitalálandó szót szétdarabolja karakterekre. every függvény akkor ad vissza true-t, ha a tömb MINDEN elemére igaz
guesses.includes(c), vagyis „a játékos tippjei között benne van-e az adott betű
Ha minden betű szerepel a találatok között → a játékos nyert.
Ha akár egyetlen betű hiányzik → még nem nyert.
*/
function isWon() {
  return theWord.split("").every(c => guesses.includes(c));
}
function isLost() {
  return getWrongGuesses().length === MAX_WRONG_GUESSES;
}
//végigmegy a találgatás tömbön, és kiszűri azokat, amik nincsenek 
// az adott szóban, tehát a rossz tippeket
function getWrongGuesses() {
  return guesses.filter(c => !theWord.includes(c));
}

// Elements
const divEnd = document.querySelector("#vege");
const divWord = document.querySelector("#szo");
const divButtons = document.querySelector("#betuk");
const divScore = document.querySelector("#eredmeny");

// Rendering
/*split: a kiválaszott szó karaktereit tömbbe rendezi
map: Minden karakterhez (c) legenerál egy <span> HTML elemet, amelybe vagy a betű, vagy üres szöveg kerül.
Ha a játékos vesztett (isLost() igaz), ÉS az adott betűt nem találta el (!guesses.includes(c)), akkor a <span> kap egy "hianyzo" CSS osztályt.
Ha a játékos vesztett → minden betűt kiírunk (felfedjük a szót).
Ha a játékos még nem vesztett, de az adott betűt már kitalálta → kiírjuk azt a betűt.
Egyébként → üres string (""), vagyis a betű helyén üres mező lesz.
*/
function renderWord() {
  divWord.innerHTML =
    theWord.split("").map(c =>
      `<span class="${isLost() && !guesses.includes(c) ? "hianyzo" : ""}">
        ${isLost() || guesses.includes(c) ? c : ""}
      </span>`).join("")
}
//join: <button>a</button>,<button>b</button> helyett "<button>a</button><button>b</button>
//A .join("") azért kell, hogy a map() által visszaadott tömb HTML-darabjai egyetlen szöveggé álljanak össze.
function renderButtons() {
  divButtons.innerHTML =
    buttons.split("").map(c => `<button ${guesses.includes(c) ? "disabled" : ""}>
      ${c}
    </button>`).join("");
}
function renderEnd() {
  divEnd.innerHTML = isWon()
    ? "Nyertél!"
    : isLost()
      ? "Vesztettél!"
      : "";
}
function renderScore() {
  divScore.innerHTML = `${getWrongGuesses().length} / ${MAX_WRONG_GUESSES}`;
}

//hány részt kell kirajzolni az akasztófa figurából.
function updateSVG() {
  for (let i = 1; i <= getWrongGuesses().length; i++) {
    document.querySelector(`svg *:nth-child(${i})`)
      .classList.add("rajzol");
  }
}

// Event handlers
//kattintás csak játék közben számítson, és csak gomboknál.
divButtons.addEventListener("click", function (e) {
  if (!isWon() && !isLost() && e.target.matches("button")) {
    const c = e.target.innerText;
    guesses.push(c);
    /*renderWord() → frissíti a kirakott szót (mutatja az új betűt, ha helyes volt).
      renderButtons() → letiltja a kattintott gombot, hogy ne lehessen újra rákattintani.
      renderEnd() → megnézi, vége van-e a játéknak, és kiírja az üzenetet.
      renderScore() → frissíti a pontszámot/állást.*/
    renderWord();
    renderButtons();
    renderEnd();
    renderScore();
    updateSVG()
    if (isWon()) {
      divWord.classList.add("nyer");
    }
  }
});

// Start
renderWord();
renderButtons();
renderScore();
renderEnd();
updateSVG();

