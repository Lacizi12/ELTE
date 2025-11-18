// --- 1. LÉPÉS: HTML ELEMEK KIVÁLASZTÁSA ---
const canvas = document.querySelector("#game-canvas");
const ctx = canvas.getContext("2d");

const playerNameDisplay = document.querySelector("#player-name-display");
const currentLineDisplay = document.querySelector("#current-line-display");
const timerDisplay = document.querySelector("#timer-display");

const cardDisplay = document.querySelector("#card-display");
const skipButton = document.querySelector("#skip-btn");
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

    drawnSegments: [],
    
    currentLineEndpoints: [], 
    
    scores: {
        train: 0,
        total: 0,
    },
    startTime: null,
};

let selectedStartStation = null;

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
    const lineIds = gameState.lines.map(line => line.id);
    gameState.lineOrder = shuffleArray(lineIds);
    startTurn();
    drawGame();
}

// --- 5. LÉPÉS: FŐ RAJZOLÓ FÜGGVÉNYEK ---

function drawGame() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    drawGrid();       
    drawRiver();      
    drawSegments();   
    drawStations();   
    
    drawSelectionIndicator();
}

function drawGrid() {
    ctx.fillStyle = "#ebebeb"; 
    ctx.fillRect(0, 0, BOARD_SIZE, BOARD_SIZE);

    ctx.strokeStyle = "#ffffff"; 
    ctx.lineWidth = 3; 

    ctx.beginPath();
    for (let row = 0; row < GRID_SIZE; row++) {
        for (let col = 0; col < GRID_SIZE; col++) {
            const x = col * CELL_SIZE;
            const y = row * CELL_SIZE;
            
            ctx.moveTo(x, y);
            ctx.lineTo(x + CELL_SIZE, y + CELL_SIZE);
            
            ctx.moveTo(x + CELL_SIZE, y);
            ctx.lineTo(x, y + CELL_SIZE);
        }
    }
    ctx.stroke();

    ctx.beginPath();
    for (let i = 0; i <= GRID_SIZE; i++) {
        ctx.moveTo(0, i * CELL_SIZE);
        ctx.lineTo(BOARD_SIZE, i * CELL_SIZE);
        
        ctx.moveTo(i * CELL_SIZE, 0);
        ctx.lineTo(i * CELL_SIZE, BOARD_SIZE);
    }
    ctx.stroke();
}

function drawRiver() {
    ctx.beginPath();
    ctx.moveTo(6 * CELL_SIZE, 0);
    ctx.lineTo(6 * CELL_SIZE, 3 * CELL_SIZE);
    ctx.lineTo(5 * CELL_SIZE, 4 * CELL_SIZE);
    ctx.lineTo(5 * CELL_SIZE, 7 * CELL_SIZE);
    ctx.lineTo(7 * CELL_SIZE, 9 * CELL_SIZE);
    ctx.lineTo(7 * CELL_SIZE, 10 * CELL_SIZE);
    
    ctx.lineWidth = 12; 
    ctx.strokeStyle = "#a3d5ff"; 
    ctx.lineCap = "butt"; 
    ctx.lineJoin = "round"; 
    ctx.stroke();
}

function drawStations() {
    const startStationColors = {};
    gameState.lines.forEach(line => {
        startStationColors[line.start] = line.color;
    });

    gameState.stations.forEach(station => {
        const xPos = station.x * CELL_SIZE + (CELL_SIZE / 2);
        const yPos = station.y * CELL_SIZE + (CELL_SIZE / 2);

        let radius = 19; 
        let fillColor = "#000000"; 
        let textColor = "#FFFFFF"; 
        let fontSize = "bold 20px Verdana, Arial, sans-serif"; 

        if (startStationColors[station.id]) {
            fillColor = startStationColors[station.id]; 
            radius = 21; 
            if (fillColor === 'yellow' || fillColor === '#FFD700') textColor = "#000000";
        } 
        
        if (station.type === "?") fillColor = "#000"; 

        ctx.beginPath();
        ctx.arc(xPos, yPos, radius, 0, 2 * Math.PI);
        ctx.fillStyle = fillColor;
        ctx.fill();
        
        ctx.strokeStyle = "white";
        ctx.lineWidth = 3; 
        ctx.stroke();
        
        ctx.fillStyle = textColor;
        ctx.textAlign = "center";
        ctx.textBaseline = "middle"; 
        
        if (station.type === "?") {
            ctx.font = "bold 11px Verdana";
            ctx.fillText("Deák", xPos, yPos - 6); 
            ctx.fillText("tér", xPos, yPos + 6);
        } else {
            ctx.font = fontSize;
            ctx.fillText(station.type, xPos, yPos); 
        }

        if (station.train) {
            const badgeDistance = radius * 0.8; 
            const badgeX = xPos + badgeDistance;
            const badgeY = yPos + badgeDistance;
            const badgeRadius = 9;
            
            ctx.beginPath();
            ctx.arc(badgeX, badgeY, badgeRadius, 0, 2 * Math.PI);
            ctx.fillStyle = "#0078D7";
            ctx.fill();
            ctx.strokeStyle = "white";
            ctx.lineWidth = 2;
            ctx.stroke();
            
            ctx.fillStyle = "white";
            ctx.font = "12px Arial"; 
            ctx.fillText("🚂", badgeX, badgeY + 1); 
        }
    });
}

function drawSegments() {
    gameState.drawnSegments.forEach(segment => {
        const color = getLineColor(segment.line);
        
        const fromX = segment.from.x * CELL_SIZE + (CELL_SIZE / 2);
        const fromY = segment.from.y * CELL_SIZE + (CELL_SIZE / 2);
        const toX = segment.to.x * CELL_SIZE + (CELL_SIZE / 2);
        const toY = segment.to.y * CELL_SIZE + (CELL_SIZE / 2);

        ctx.beginPath();
        ctx.moveTo(fromX, fromY);
        ctx.lineTo(toX, toY);
        ctx.strokeStyle = color;
        ctx.lineWidth = 10; 
        ctx.lineCap = "round"; 
        ctx.stroke();
    });
}

function drawSelectionIndicator() {
    if (selectedStartStation) {
        const xPos = selectedStartStation.x * CELL_SIZE + (CELL_SIZE / 2);
        const yPos = selectedStartStation.y * CELL_SIZE + (CELL_SIZE / 2);

        ctx.beginPath();
        ctx.arc(xPos, yPos, CELL_SIZE / 2.1, 0, 2 * Math.PI);
        ctx.strokeStyle = "#00bcd4";
        ctx.lineWidth = 4;
        ctx.setLineDash([6, 6]);
        ctx.stroke();
        ctx.setLineDash([]);
    }
}

// --- 6. LÉPÉS: JÁTÉKMENET ---

function startTurn() {
    const currentLineId = gameState.lineOrder[gameState.currentLineIndex];
    const currentLine = gameState.lines.find(line => line.id === currentLineId);
    
    if (!currentLine) return;

    currentLineDisplay.textContent = currentLine.name;
    currentLineDisplay.style.color = currentLine.color;
    currentLineDisplay.style.backgroundColor = "#fff";
    currentLineDisplay.style.padding = "2px 5px";
    currentLineDisplay.style.borderRadius = "4px";

    const startStation = gameState.stations.find(s => s.id === currentLine.start);
    if (!startStation) return;

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
            alert("VÉGE A JÁTÉKNAK!");
            return;
        } else {
            startTurn();
        }
        return;
    }

    const card = gameState.currentCardDeck.pop();
    gameState.currentCard = card;

    const cardEl = cardDisplay.querySelector('.card-content') || cardDisplay;
    cardEl.textContent = card;
    if (card === 'Joker') {
        cardEl.style.color = "#4b006e";
    } else {
        cardEl.style.color = "#333";
    }
    
    gameState.turnCounter++;
}

// --- 7. LÉPÉS: ESEMÉNYKEZELÉS ÉS SZABÁLYOK ---

canvas.addEventListener('click', handleCanvasClick);

skipButton.addEventListener('click', () => {
    console.log("Kártya passzolva!");
    selectedStartStation = null;
    drawGame();
    drawCard();
});

function handleCanvasClick(event) {
    const rect = canvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;

    const cellX = Math.floor(x / CELL_SIZE);
    const cellY = Math.floor(y / CELL_SIZE);

    const clickedStation = getStationAt(cellX, cellY);

    if (!clickedStation) {
        selectedStartStation = null;
        drawGame();
        return;
    }

    if (selectedStartStation === null) {
        selectedStartStation = clickedStation;
        drawGame();
    } else {
        validateAndDrawLine(selectedStartStation, clickedStation);
        selectedStartStation = null;
    }
}

function validateAndDrawLine(startStation, endStation) {
    // 1. SZABÁLY: Nem önmagába
    if (startStation.id === endStation.id) return;

    // 2. SZABÁLY: Vonal vége
    const isEndpoint = gameState.currentLineEndpoints.find(
        p => p.x === startStation.x && p.y === startStation.y
    );
    if (!isEndpoint) {
        alert("Csak a vonal végéről folytathatod!");
        drawGame();
        return;
    }

    // 3. SZABÁLY: Egyenes vonal
    const dx = startStation.x - endStation.x;
    const dy = startStation.y - endStation.y;
    const absDx = Math.abs(dx);
    const absDy = Math.abs(dy);
    
    const isStraight = (absDx === 0 && absDy > 0) || 
                       (absDy === 0 && absDx > 0) || 
                       (absDx === absDy && absDx > 0);
                       
    if (!isStraight) {
        console.warn("Nem egyenes vonal!");
        drawGame(); 
        return;
    }

    // 4. SZABÁLY: Kártya
    const card = gameState.currentCard;
    const isCardValid = (card === 'Joker') || (endStation.type === '?') || (endStation.type === card);
    
    if (!isCardValid) {
        alert(`Rossz állomás! A kártya: ${card}, te erre kattintottál: ${endStation.type}`);
        drawGame();
        return;
    }

    // 5. SZABÁLY: Köztes állomás
    const steps = Math.max(absDx, absDy); 
    const stepX = (endStation.x - startStation.x) / steps;
    const stepY = (endStation.y - startStation.y) / steps;

    for (let i = 1; i < steps; i++) {
        const checkX = startStation.x + i * stepX;
        const checkY = startStation.y + i * stepY;
        
        if (getStationAt(checkX, checkY)) {
            alert("HIBA: Nem haladhatsz át más állomáson!");
            drawGame();
            return;
        }
    }

    // 6. SZABÁLY (ÚJ): Kereszteződés ellenőrzése
    // Nem lehet párhuzamos vonal ugyanott, és nem metszhet más vonalat a nyílt pályán.
    
    // Először nézzük meg, van-e már ilyen vonal (duplikáció)
    const isDuplicate = gameState.drawnSegments.some(seg => 
        (seg.from.x === startStation.x && seg.from.y === startStation.y && seg.to.x === endStation.x && seg.to.y === endStation.y) ||
        (seg.from.x === endStation.x && seg.from.y === endStation.y && seg.to.x === startStation.x && seg.to.y === startStation.y)
    );
    if (isDuplicate) {
        alert("Itt már van vonal!");
        drawGame();
        return;
    }

    // Most nézzük a metszést
    const hasIntersection = gameState.drawnSegments.some(seg => 
        doSegmentsIntersect(
            {x: startStation.x, y: startStation.y}, 
            {x: endStation.x, y: endStation.y}, 
            seg.from, 
            seg.to
        )
    );
    if (hasIntersection) {
        alert("HIBA: A vonalak nem keresztezhetik egymást a nyílt pályán!");
        drawGame();
        return;
    }

    // --- SIKERES LÉPÉS! ---

    const currentLineId = gameState.lineOrder[gameState.currentLineIndex];
    
    gameState.drawnSegments.push({ 
        line: currentLineId, 
        from: { x: startStation.x, y: startStation.y }, 
        to: { x: endStation.x, y: endStation.y } 
    });
    
    const segmentsForThisLine = gameState.drawnSegments.filter(s => s.line === currentLineId);
    const isFirstSegment = segmentsForThisLine.length === 1;

    if (!isFirstSegment) {
        gameState.currentLineEndpoints = gameState.currentLineEndpoints.filter(
            p => p.x !== startStation.x || p.y !== startStation.y
        );
    }

    gameState.currentLineEndpoints.push({ x: endStation.x, y: endStation.y });

    drawGame();
    drawCard();
}


// --- 8. LÉPÉS: SEGÉDFÜGGVÉNYEK ---

function getStationAt(x, y) {
    return gameState.stations.find(station => station.x === x && station.y === y);
}

function getLineColor(lineId) {
    const line = gameState.lines.find(l => l.id === lineId);
    return line ? line.color : "#000";
}

// Matematikai segédfüggvény a metszésvizsgálathoz (CCW algoritmus)
function ccw(a, b, c) {
    return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
}

function doSegmentsIntersect(p1, p2, p3, p4) {
    // p1-p2 az új szakasz, p3-p4 egy régi szakasz
    
    // Ha van közös végpontjuk, az NEM hiba (állomáson találkoznak), kivéve ha ugyanaz a vonal (párhuzamos), de azt már szűrtük.
    if ((p1.x === p3.x && p1.y === p3.y) || (p1.x === p4.x && p1.y === p4.y) ||
        (p2.x === p3.x && p2.y === p3.y) || (p2.x === p4.x && p2.y === p4.y)) {
        return false;
    }

    const d1 = ccw(p3, p4, p1);
    const d2 = ccw(p3, p4, p2);
    const d3 = ccw(p1, p2, p3);
    const d4 = ccw(p1, p2, p4);

    // Ha mindkét szorzás negatív, akkor a szakaszok "keresztben" vannak egymáshoz képest
    // Ez a "szigorú" metszés esete (valódi X alak)
    if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
        ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))) {
        return true;
    }

    return false;
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

initializeGame();