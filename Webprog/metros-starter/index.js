const nameInput = document.querySelector("#player-name");
const startButton = document.querySelector("#start-game-btn");
const rulesButton = document.querySelector("#rules-btn");

startButton.addEventListener("click", () => {
    const playerName = nameInput.value.trim();

    //Ha a nev nem ures akkor atvisz a jatek  oldalra  es tarolja a nevet

    if (playerName === "") {
        nameInput.style.borderColor = "red";
        nameInput.placeholder = "Kérlek, add meg a neved!";
        return;
    }
    localStorage.setItem("budapestMetroPlayer", playerName);

    window.location.href = "game.html";
});
// Szabályok  gomb  atvisz  a szabalyok  oldalra
rulesButton.addEventListener("click", () => {
    window.location.href = "rules.html"
    
});