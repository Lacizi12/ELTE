// --- 1. LÉPÉS: HTML ELEMEK KIVÁLASZTÁSA ---
const canvas = document.querySelector("#game-canvas");
const ctx = canvas.getContext("2d");

const playerNameDisplay = document.querySelector("#player-name-display");
const currentLineDisplay = document.querySelector("#current-line-display");
const timerDisplay = document.querySelector("#timer-display");

const cardDisplay = document.querySelector("#card-display");
const turnOrderDisplay = document.querySelector("#turn-order-display");
const trainScoreDisplay = document.querySelector("#train-score-display");
const totalScoreDisplay = document.querySelector("#total-score-display");

// --- 2. LÉPÉS: JÁTÉK KONSTANSOK ÉS BEÁLLÍTÁSOK ---
const GRID_SIZE = 10;
const BOARD_SIZE = 600;
const CELL_SIZE = BOARD_SIZE / GRID_SIZE;

canvas.width = BOARD_SIZE;
canvas.height = BOARD_SIZE;

// --- 3. LÉPÉS: A JÁTÉK ÁLLAPOTA (GAME STATE) ---
let gameState = {
    playerName: "",
    stations: [],
    lines: [],
    lineOrder: [],
    currentLineIndex: 0,
    
    currentCardDeck: [],
    currentCard: null,
    turnCounter: 0,

    drawnSegments: [], // Pl: { line: 0, from: {x:1, y:2}, to: {x:3, y:2} }
    
    currentLineEndpoints: [],
    
    scores: {
        train: 0,
        total: 0,
    },
    startTime: null,
};

let selectedStartStation = null; // Globális változó a kétlépéses kattintás kezeléséhez

// --- 4. LÉPÉS: INDÍTÁS ÉS ADATBETÖLTÉS ---

async function initializeGame() {
    gameState.playerName = localStorage.getItem("budapestMetroPlayer") || "Játékos";
    playerNameDisplay.textContent = gameState.playerName;

    gameState.startTime = Date.now();
    startTimer();

    try {
        const [stationsRes, linesRes] = await Promise.all([
            fetch('stations.json'),
            fetch('lines.json')
        ]);
        
        gameState.stations = await stationsRes.json();
        gameState.lines = await linesRes.json();

        startGame();

    } catch (error) {
        console.error("Hiba az adatok betöltésekor:", error);
        alert("Hiba a játékfájlok betöltésekor. Kérlek, frissítsd az oldalt.");
    }
}

function startGame() {
    console.log("Adatok betöltve, a játék indul!", gameState.stations, gameState.lines);

    const lineIds = gameState.lines.map(line => line.id);
    gameState.lineOrder = shuffleArray(lineIds);
    console.log("Vonalak sorrendje:", gameState.lineOrder);

    startTurn();
    drawGame();
}

// --- 5. LÉPÉS: FŐ RAJZOLÓ FÜGGVÉNYEK ---

function drawGame() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    drawGrid();
    drawStations();
    drawSegments(); // Kirajzolja az elmentett vonalakat
    drawSelectionIndicator(); // Kirajzolja a kijelölést
}

function drawGrid() {
    ctx.strokeStyle = "#eee";
    ctx.lineWidth = 1;
    for (let i = 0; i <= GRID_SIZE; i++) {
        ctx.beginPath();
        ctx.moveTo(0, i * CELL_SIZE);
        ctx.lineTo(BOARD_SIZE, i * CELL_SIZE);
        ctx.stroke();

        ctx.beginPath();
        ctx.moveTo(i * CELL_SIZE, 0);
        ctx.lineTo(i * CELL_SIZE, BOARD_SIZE);
        ctx.stroke();
    }
}

function drawStations() {
    const kiinduloAllomasok = gameState.lines.map(line => line.start);

    gameState.stations.forEach(station => {
        const xPos = station.x * CELL_SIZE + (CELL_SIZE / 2);
        const yPos = station.y * CELL_SIZE + (CELL_SIZE / 2);

        let radius = 10;
        let color = "#555";
        let lineWidth = 3;

        if (kiinduloAllomasok.includes(station.id)) {
            radius = 15;
            color = "#000";
            lineWidth = 4;
        } else if (station.train) {
            radius = 12;
            color = "#4b006e";
            lineWidth = 4;
        }

        ctx.beginPath();
        ctx.arc(xPos, yPos, radius, 0, 2 * Math.PI);
        ctx.fillStyle = "white";
        ctx.fill();
        ctx.strokeStyle = color;
        ctx.lineWidth = lineWidth;
        ctx.stroke();
        
        ctx.fillStyle = color;
        ctx.font = "bold 16px Arial";
        ctx.textAlign = "center";
        ctx.textBaseline = "middle";
        ctx.fillText(station.type, xPos, yPos);
    });
}

function drawSegments() {
    gameState.drawnSegments.forEach(segment => {
        const color = getLineColor(segment.line);
        
        // Pixel koordináták kiszámítása (cella közepe)
        const fromX = segment.from.x * CELL_SIZE + (CELL_SIZE / 2);
        const fromY = segment.from.y * CELL_SIZE + (CELL_SIZE / 2);
        const toX = segment.to.x * CELL_SIZE + (CELL_SIZE / 2);
        const toY = segment.to.y * CELL_SIZE + (CELL_SIZE / 2);

        // Vonal rajzolása
        ctx.beginPath();
        ctx.moveTo(fromX, fromY);
        ctx.lineTo(toX, toY);
        ctx.strokeStyle = color;
        ctx.lineWidth = 6; // Legyen vastagabb a vonal
        ctx.stroke();
    });
}

// ÚJ: Vizuális visszajelzés a kijelöléshez
function drawSelectionIndicator() {
    if (selectedStartStation) {
        const xPos = selectedStartStation.x * CELL_SIZE + (CELL_SIZE / 2);
        const yPos = selectedStartStation.y * CELL_SIZE + (CELL_SIZE / 2);

        ctx.beginPath();
        ctx.arc(xPos, yPos, CELL_SIZE / 2.5, 0, 2 * Math.PI); // Nagyobb kör
        ctx.strokeStyle = "cyan"; // Élénk szín (JAVÍTVA)
        ctx.lineWidth = 4;
        ctx.setLineDash([5, 5]); // Szaggatott vonal
        ctx.stroke();
        ctx.setLineDash([]); // Vonal stílus visszaállítása
    }
}

// --- 6. LÉPÉS: JÁTÉKMENET ---

function startTurn() {
    const currentLineId = gameState.lineOrder[gameState.currentLineIndex];
    const currentLine = gameState.lines.find(line => line.id === currentLineId);
    
    if (!currentLine) {
        console.error("Nem található a vonal:", currentLineId);
        return;
    }

    currentLineDisplay.textContent = currentLine.name;
    currentLineDisplay.style.color = currentLine.color;

    const startStation = gameState.stations.find(s => s.id === currentLine.start);
    if (!startStation) {
        console.error("Nem található a kezdőállomás:", currentLine.start);
        return;
    }

    gameState.currentLineEndpoints = [{ x: startStation.x, y: startStation.y }];
    gameState.turnCounter = 0;

    const deck = ['A', 'B', 'C', 'D', 'Joker', 'A', 'B', 'C', 'D', 'Joker'];
    gameState.currentCardDeck = shuffleArray(deck);

    drawCard();
}

function drawCard() {
    if (gameState.turnCounter >= 8) {
        console.log("FORDULÓ VÉGE");
        gameState.currentLineIndex++; 

        if (gameState.currentLineIndex >= gameState.lines.length) {
            console.log("JÁTÉK VÉGE!");
            alert("VÉGE A JÁTÉKNAK! Pontszám: " + gameState.scores.total);
            return;
        } else {
            startTurn();
        }
        return;
    }

    const card = gameState.currentCardDeck.pop();
    gameState.currentCard = card;

    cardDisplay.querySelector('span').textContent = card;
    
    gameState.turnCounter++;
}

// --- 7. LÉPÉS: ESEMÉNYKEZELÉS ---

canvas.addEventListener('click', handleCanvasClick);

function handleCanvasClick(event) {
    const rect = canvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;

    const cellX = Math.floor(x / CELL_SIZE);
    const cellY = Math.floor(y / CELL_SIZE);

    const clickedStation = getStationAt(cellX, cellY);

    if (!clickedStation) {
        console.log("Kattintás az üres rácsra. Kijelölés törölve.");
        selectedStartStation = null;
        drawGame(); // Újrarajzolás a kijelölés eltüntetéséhez
        return;
    }

    console.log("Elkaptál egy állomást:", clickedStation.id);

    if (selectedStartStation === null) {
        selectedStartStation = clickedStation;
        console.log("KIINDULÓ ÁLLOMÁS KIJELÖLVE:", selectedStartStation.id);
        drawGame(); // Újrarajzolás a kijelölés megjelenítéséhez
    } else {
        console.log("CÉL ÁLLOMÁS KIJELÖLVE:", clickedStation.id);
        
        validateAndDrawLine(selectedStartStation, clickedStation);
        
        selectedStartStation = null;
        drawGame(); // Újrarajzolás a kijelölés eltüntetéséhez
    }
}

// FRISSÍTVE: A legnehezebb függvény, most már alap logikával
function validateAndDrawLine(startStation, endStation) {
    console.log("Vonal ellenőrzése (CSAK 45/90 FOK):", startStation.id, "->", endStation.id);

    // 1. Szabály: Nem húzhatsz vonalat önmagába
    if (startStation.id === endStation.id) {
        console.warn("Hiba: Nem húzhatsz vonalat önmagába.");
        return;
    }

    // 2. Szabály (Játékszabályok): Végpont, Kártya, Hurok ellenőrzés...
    // ...EZEKET MOST FIGYELMEN KÍVÜL HAGYJUK A KÉRÉS SZERINT.

    // 3. Szabály: A vonalnak egyenesnek kell lennie (45/90 fok)
    // Ez az egyetlen szabály, ami most számít.
    const dx = Math.abs(startStation.x - endStation.x);
    const dy = Math.abs(startStation.y - endStation.y);
    
    // isStraight = (függőleges) VAGY (vízszintes) VAGY (45 fokos átlós)
    const isStraight = (dx === 0 && dy > 0) || // Függőleges
                       (dy === 0 && dx > 0) || // Vízszintes
                       (dx === dy && dx > 0);   // Átlós
                       
    if (!isStraight) {
        console.warn("Hiba: A vonal nem egyenes (nem 45 vagy 90 fok)!");
        return;
    }
    
    console.log("SZABÁLYOS LÉPÉS (45/90 FOK)!");

    // Ha a szög szabályos, csak rajzoljuk ki, de ne lépjünk tovább a játékban.
    
    // Használjuk az aktuális vonal színét a rajzoláshoz
    const currentLineId = gameState.lineOrder[gameState.currentLineIndex];
    
    // 1. Mentsd el a szakaszt
    gameState.drawnSegments.push({ 
        line: currentLineId, 
        from: { x: startStation.x, y: startStation.y }, 
        to: { x: endStation.x, y: endStation.y } 
    });
    
    // 2. Végpont frissítés FIGYELMEN KÍVÜL HAGYVA
    
    // 3. Rajzold újra a játékteret, hogy a vonal megjelenjen
    drawGame();
    
    // 4. Új kártya húzása FIGYELMEN KÍVÜL HAGYVA
}


// --- 8. LÉPÉS: SEGÉDFÜGGVÉNYEK ---

function getStationAt(x, y) {
    return gameState.stations.find(station => station.x === x && station.y === y);
}

function getLineColor(lineId) {
    const line = gameState.lines.find(l => l.id === lineId);
    return line ? line.color : "#000"; // Alapértelmezett fekete, ha hiba van
}

function startTimer() {
    setInterval(() => {
        const secondsElapsed = Math.floor((Date.now() - gameState.startTime) / 1000);
        const minutes = Math.floor(secondsElapsed / 60).toString().padStart(2, '0');
        const seconds = (secondsElapsed % 60).toString().padStart(2, '0');
        timerDisplay.textContent = `${minutes}:${seconds}`;
    }, 1000);
}

function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}

// --- INDÍTÁS ---
initializeGame();