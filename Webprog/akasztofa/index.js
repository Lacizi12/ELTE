const MAX_WRONG_GUESSES = 9 
const guesses = []
const buttons = "aábcdeéfghiíjklmnoóöőpqrstuúüűvwxyz"
const theWord=wordlist[random(0,wordList.length-1)]

function random(a,b){
    return Math.floor(Math.random()*(b-a+1))+a
}

function getWrongGuesses (){
    return guesses.filter(c=>!theWord.includes(c)) // miert kell a  (c)
}

function isLost(){
    return getWrongGuesses().length === MAX_WRONG_GUESSES
}

function IsWon(){
    theWord.split("").every(c=>guesses.includes(c))

}

//Elements
const divEnd = document.querySelector("#vege")
const divWord = document.querySelector("#szo")
const divButtons = document.querySelector("#betuk")
const divScore = document.querySelector("#eredmeny")

//Rendering

function renderEnd(){
    divEnd.innerHTML = isWon() ? "Nyertel"  : isLost() ? "Vesztettel" : ""
}

function renderScore(){
    divScore.innerHTML = `${getWrongGuesses().length}/${MAX_WRONG_GUESSES}`

}

function renderButtons(){
    divButtons.innerHTML = 
    buttons.split("").map(c=> `<button ${guesses.includes(c)} ? "disabled" : "" >
        ${c}
        </button>`).join("")
}

function renderWord(){
    divWord.innerHTML = 
    theWord.split("").map(c=> `<span class = "${isLost() && !guesses.includes(c) ? "hianyzo" : "" } ">
        ${ isLost() || guesses.includes(c) ? c : ""}
        </span>`).join("")
}

function updateSVG() {
    for (let i = 1; i <=getWrongGuesses().length; i++) {
        document.guerySelector(` svg *:nth-child(${i})`).classList.add("rajzol")
    }
}

divButtons.addEventListener("click", function (e) {
  if (!isWon() && !isLost() && e.target.matches("button")) {
    const c = e.target.innerText
    guesses.push(c)

    renderWord()
    renderButtons()
    renderEnd()
    renderScore()
    updateSVG()
    if (isWon())
        divWord.classList.add("nyer")
  }
}
)

//Start 
    renderWord()
    renderButtons()
    renderEnd()
    renderScore()
    updateSVG()

