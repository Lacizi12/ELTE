// --- 1. LÉPÉS: HTML ELEMEK KIVÁLASZTÁSA ---
const vaszon = document.querySelector("#game-canvas");
const rajzolo = vaszon.getContext("2d");

const jatekosNevKijelzo = document.querySelector("#player-name-display");
const aktualisVonalKijelzo = document.querySelector("#current-line-display");
const idozitoKijelzo = document.querySelector("#timer-display");

const kartyaKijelzo = document.querySelector("#card-display");
const passzGomb = document.querySelector("#skip-btn");
const forduloSorrendKijelzo = document.querySelector("#turn-order-display");
const vonatPontKijelzo = document.querySelector("#train-score-display");
const osszPontKijelzo = document.querySelector("#total-score-display");
const vonatPontCsuszka = document.querySelector("#train-score-slider");

// --- 2. LÉPÉS: JÁTÉK KONSTANSOK ---
const RACS_MERET = 10;
const TABLA_MERET = 600;
const CELLA_MERET = TABLA_MERET / RACS_MERET;

// A Duna vonala (koordináták a rácsvonalakon)
const DUNA_SZAKASZOK = [
    { p1: {x: 5, y: 0}, p2: {x: 5, y: 6} },
    { p1: {x: 5, y: 6}, p2: {x: 4, y: 7} },
    { p1: {x: 4, y: 7}, p2: {x: 4, y: 10} }
];

// Pályaudvar pontok sorozata
const VONAT_PONTOK = [0, 1, 2, 4, 6, 8, 11, 14, 17, 21, 25];

vaszon.width = TABLA_MERET;
vaszon.height = TABLA_MERET;

// --- 3. LÉPÉS: A JÁTÉK ÁLLAPOTA ---
let jatekAllapot = {
    jatekosNev: "",
    allomasok: [],
    vonalak: [],
    vonalSorrend: [],
    aktualisVonalIndex: 0,
    
    aktualisKartyaPakli: [],
    aktualisKartya: null,
    korSzamlalo: 0,

    megrajzoltSzakaszok: [],
    aktualisVonalVegpontok: [], // Ez tárolja, honnan folytathatod
    
    pontszamok: {
        vonatIndex: 0, 
        forduloPontok: [],
        osszesen: 0,
    },
    
    aktualisForduloStatisztika: {
        dunaAtkelesek: 0,
        keruletek: {},
    },

    kezdesIdo: null,
    jatekVegeVan: false
};

let kivalasztottKezdoAllomas = null;

// --- 4. INDÍTÁS ---

async function jatekInicializalasa() {
    jatekAllapot.jatekosNev = localStorage.getItem("budapestMetroPlayer") || "Játékos";
    jatekosNevKijelzo.textContent = jatekAllapot.jatekosNev;

    jatekAllapot.kezdesIdo = Date.now();
    idozitoInditasa();
    vonatCsuszkaFrissitese(); 

    try {
        const [allomasValasz, vonalValasz] = await Promise.all([
            fetch('stations.json'),
            fetch('lines.json')
        ]);
        
        jatekAllapot.allomasok = await allomasValasz.json();
        jatekAllapot.vonalak = await vonalValasz.json();

        jatekInditasa();

    } catch (hiba) {
        console.error("Hiba:", hiba);
    }
}

function jatekInditasa() {
    const vonalAzonositok = jatekAllapot.vonalak.map(vonal => vonal.id);
    jatekAllapot.vonalSorrend = tombKeverese(vonalAzonositok);
    forduloSorrendKirajzolasa();
    forduloInditasa();
    jatekRajzolasa();
}

// --- 5. RAJZOLÁS ---

function jatekRajzolasa() {
    rajzolo.clearRect(0, 0, vaszon.width, vaszon.height);

    racsRajzolasa();       
    dunaRajzolasa();      
    szakaszokRajzolasa();   
    allomasokRajzolasa();   
    kijelolesRajzolasa();
}

function racsRajzolasa() {
    rajzolo.fillStyle = "#ebebeb"; 
    rajzolo.fillRect(0, 0, TABLA_MERET, TABLA_MERET);
    rajzolo.strokeStyle = "#ffffff"; 
    rajzolo.lineWidth = 3; 

    rajzolo.beginPath();
    for (let sor = 0; sor < RACS_MERET; sor++) {
        for (let oszlop = 0; oszlop < RACS_MERET; oszlop++) {
            const x = oszlop * CELLA_MERET; const y = sor * CELLA_MERET;
            rajzolo.moveTo(x, y); rajzolo.lineTo(x + CELLA_MERET, y + CELLA_MERET);
            rajzolo.moveTo(x + CELLA_MERET, y); rajzolo.lineTo(x, y + CELLA_MERET);
        }
    }
    rajzolo.stroke();

    rajzolo.beginPath();
    for (let i = 0; i <= RACS_MERET; i++) {
        rajzolo.moveTo(0, i * CELLA_MERET); rajzolo.lineTo(TABLA_MERET, i * CELLA_MERET);
        rajzolo.moveTo(i * CELLA_MERET, 0); rajzolo.lineTo(i * CELLA_MERET, TABLA_MERET);
    }
    rajzolo.stroke();
}

function dunaRajzolasa() {
    rajzolo.beginPath();
    rajzolo.moveTo(DUNA_SZAKASZOK[0].p1.x * CELLA_MERET, DUNA_SZAKASZOK[0].p1.y * CELLA_MERET);
    for (const szakasz of DUNA_SZAKASZOK) {
        rajzolo.lineTo(szakasz.p2.x * CELLA_MERET, szakasz.p2.y * CELLA_MERET);
    }
    
    rajzolo.lineWidth = 12; 
    rajzolo.strokeStyle = "#a3d5ff"; 
    rajzolo.lineCap = "butt"; 
    rajzolo.lineJoin = "round"; 
    rajzolo.stroke();
}

function allomasokRajzolasa() {
    const kezdoAllomasSzinek = {};
    jatekAllapot.vonalak.forEach(vonal => kezdoAllomasSzinek[vonal.start] = vonal.color);

    jatekAllapot.allomasok.forEach(allomas => {
        const x = allomas.x * CELLA_MERET + (CELLA_MERET / 2);
        const y = allomas.y * CELLA_MERET + (CELLA_MERET / 2);
        let r = 19, kitoltes = "#000", szovegSzin = "#FFF", betutipus = "bold 20px Verdana";

        if (kezdoAllomasSzinek[allomas.id]) {
            kitoltes = kezdoAllomasSzinek[allomas.id]; r = 21;
            if (kitoltes === 'yellow' || kitoltes === '#FFD700') szovegSzin = "#000";
        } 
        if (allomas.type === "?") kitoltes = "#000"; 

        rajzolo.beginPath(); rajzolo.arc(x, y, r, 0, 2 * Math.PI);
        rajzolo.fillStyle = kitoltes; rajzolo.fill();
        rajzolo.strokeStyle = "white"; rajzolo.lineWidth = 3; rajzolo.stroke();
        
        rajzolo.fillStyle = szovegSzin; rajzolo.textAlign = "center"; rajzolo.textBaseline = "middle"; 
        if (allomas.type === "?") {
            rajzolo.font = "bold 11px Verdana";
            rajzolo.fillText("Deák", x, y - 6); rajzolo.fillText("tér", x, y + 6);
        } else {
            rajzolo.font = betutipus; rajzolo.fillText(allomas.type, x, y); 
        }

        if (allomas.train) {
            const bx = x + r * 0.8, by = y + r * 0.8;
            rajzolo.beginPath(); rajzolo.arc(bx, by, 9, 0, 2 * Math.PI);
            rajzolo.fillStyle = "#0078D7"; rajzolo.fill();
            rajzolo.strokeStyle = "white"; rajzolo.lineWidth = 2; rajzolo.stroke();
            rajzolo.fillStyle = "white"; rajzolo.font = "12px Arial"; rajzolo.fillText("🚂", bx, by + 1); 
        }
    });
}

function szakaszokRajzolasa() {
    jatekAllapot.megrajzoltSzakaszok.forEach(szakasz => {
        const szin = vonalSzinLekerese(szakasz.line);
        const kx = szakasz.from.x * CELLA_MERET + CELLA_MERET/2;
        const ky = szakasz.from.y * CELLA_MERET + CELLA_MERET/2;
        const vx = szakasz.to.x * CELLA_MERET + CELLA_MERET/2;
        const vy = szakasz.to.y * CELLA_MERET + CELLA_MERET/2;

        rajzolo.beginPath(); rajzolo.moveTo(kx, ky); rajzolo.lineTo(vx, vy);
        rajzolo.strokeStyle = szin; rajzolo.lineWidth = 10; rajzolo.lineCap = "round"; rajzolo.stroke();
    });
}

function kijelolesRajzolasa() {
    if (kivalasztottKezdoAllomas) {
        const x = kivalasztottKezdoAllomas.x * CELLA_MERET + CELLA_MERET/2;
        const y = kivalasztottKezdoAllomas.y * CELLA_MERET + CELLA_MERET/2;
        rajzolo.beginPath(); rajzolo.arc(x, y, CELLA_MERET/2.1, 0, 2*Math.PI);
        rajzolo.strokeStyle = "#00bcd4"; rajzolo.lineWidth = 4; 
        rajzolo.setLineDash([6, 6]); rajzolo.stroke(); rajzolo.setLineDash([]);
    }
}

// --- 6. JÁTÉKMENET ÉS PONTOZÁS ---

function forduloInditasa() {
    if (jatekAllapot.jatekVegeVan) return;

    const aktualisVonalId = jatekAllapot.vonalSorrend[jatekAllapot.aktualisVonalIndex];
    const aktualisVonal = jatekAllapot.vonalak.find(v => v.id === aktualisVonalId);
    
    aktualisVonalKijelzo.textContent = aktualisVonal.name;
    aktualisVonalKijelzo.style.color = aktualisVonal.color;
    aktualisVonalKijelzo.style.backgroundColor = "#fff";
    aktualisVonalKijelzo.style.padding = "2px 5px";
    aktualisVonalKijelzo.style.borderRadius = "4px";

    const kezdoAllomas = jatekAllapot.allomasok.find(a => a.id === aktualisVonal.start);
    
    jatekAllapot.aktualisVonalVegpontok = [{ x: kezdoAllomas.x, y: kezdoAllomas.y }];
    jatekAllapot.korSzamlalo = 0;
    
    jatekAllapot.aktualisForduloStatisztika = { dunaAtkelesek: 0, keruletek: {} };
    keruletStatisztikaHozzaadasa(kezdoAllomas);

    const pakli = ['A', 'B', 'C', 'D', 'Joker', 'A', 'B', 'C', 'D', 'Joker'];
    jatekAllapot.aktualisKartyaPakli = tombKeverese(pakli);

    forduloSorrendKirajzolasa();
    kartyaHuzasa();
}

function kartyaHuzasa() {
    if (jatekAllapot.korSzamlalo >= 8) {
        forduloVege(); 
        return;
    }

    const kartya = jatekAllapot.aktualisKartyaPakli.pop();
    jatekAllapot.aktualisKartya = kartya;

    const kartyaElem = kartyaKijelzo.querySelector('.card-content') || kartyaKijelzo;
    kartyaElem.textContent = kartya;
    kartyaElem.style.color = (kartya === 'Joker') ? "#4b006e" : "#333";
    
    jatekAllapot.korSzamlalo++;
}

function forduloVege() {
    const statisztika = jatekAllapot.aktualisForduloStatisztika;
    const keruletekSzama = Object.keys(statisztika.keruletek).length;
    const maxAllomasEgyKeruletben = Math.max(...Object.values(statisztika.keruletek));
    const dunaPontok = statisztika.dunaAtkelesek;

    const forduloPontszam = (keruletekSzama * maxAllomasEgyKeruletben) + dunaPontok;
    jatekAllapot.pontszamok.forduloPontok.push(forduloPontszam);
    jatekAllapot.pontszamok.osszesen += forduloPontszam;

    alert(`FORDULÓ VÉGE!\n\n` +
          `Érintett kerületek: ${keruletekSzama}\n` +
          `Max állomás egy kerületben: ${maxAllomasEgyKeruletben}\n` +
          `Duna átkelés: ${dunaPontok}\n\n` +
          `PONT: ${forduloPontszam}`);

    feluletFrissitese();

    jatekAllapot.aktualisVonalIndex++;
    if (jatekAllapot.aktualisVonalIndex >= jatekAllapot.vonalak.length) {
        jatekVege();
    } else {
        forduloInditasa();
    }
}

function jatekVege() {
    jatekAllapot.jatekVegeVan = true;
    
    const allomasSzamlalo = {};
    jatekAllapot.megrajzoltSzakaszok.forEach(szakasz => {
        const p1 = `${szakasz.from.x},${szakasz.from.y}`;
        const p2 = `${szakasz.to.x},${szakasz.to.y}`;
        if (!allomasSzamlalo[p1]) allomasSzamlalo[p1] = new Set();
        if (!allomasSzamlalo[p2]) allomasSzamlalo[p2] = new Set();
        allomasSzamlalo[p1].add(szakasz.line);
        allomasSzamlalo[p2].add(szakasz.line);
    });

    let csomopontPontok = 0;
    for (const kulcs in allomasSzamlalo) {
        const vonalSzam = allomasSzamlalo[kulcs].size;
        if (vonalSzam === 2) csomopontPontok += 2;
        if (vonalSzam === 3) csomopontPontok += 5;
        if (vonalSzam === 4) csomopontPontok += 9;
    }

    const vonatPontok = VONAT_PONTOK[Math.min(jatekAllapot.pontszamok.vonatIndex, VONAT_PONTOK.length - 1)];
    const vegsoPontszam = jatekAllapot.pontszamok.osszesen + vonatPontok + csomopontPontok;

    osszPontKijelzo.textContent = vegsoPontszam;
    
    setTimeout(() => {
        alert(`JÁTÉK VÉGE!\n\n` +
              `Fordulók pontjai: ${jatekAllapot.pontszamok.osszesen}\n` +
              `Pályaudvar bónusz: ${vonatPontok}\n` +
              `Csomópont bónusz: ${csomopontPontok}\n\n` +
              `VÉGSŐ PONTSZÁM: ${vegsoPontszam}`);
    }, 100);
}

// --- 7. INTERAKCIÓK ---

vaszon.addEventListener('click', kattintasKezeles);
passzGomb.addEventListener('click', () => {
    if (jatekAllapot.jatekVegeVan) return;
    kivalasztottKezdoAllomas = null;
    jatekRajzolasa();
    kartyaHuzasa();
});

function kattintasKezeles(e) {
    if (jatekAllapot.jatekVegeVan) return;
    const teglalap = vaszon.getBoundingClientRect();
    const x = e.clientX - teglalap.left; const y = e.clientY - teglalap.top;
    const cx = Math.floor(x / CELLA_MERET); const cy = Math.floor(y / CELLA_MERET);
    const allomas = allomasLekerese(cx, cy);

    if (!allomas) { kivalasztottKezdoAllomas = null; jatekRajzolasa(); return; }
    if (!kivalasztottKezdoAllomas) { kivalasztottKezdoAllomas = allomas; jatekRajzolasa(); } 
    else { vonalEllenorzesEsRajzolas(kivalasztottKezdoAllomas, allomas); kivalasztottKezdoAllomas = null; }
}

function vonalEllenorzesEsRajzolas(kezdo, veg) {
    if (kezdo.id === veg.id) return;

    const vegpontE = jatekAllapot.aktualisVonalVegpontok.find(p => p.x === kezdo.x && p.y === kezdo.y);
    if (!vegpontE) { alert("Csak a vonal végéről folytathatod!"); jatekRajzolasa(); return; }

    const dx = Math.abs(kezdo.x - veg.x); const dy = Math.abs(kezdo.y - veg.y);
    if (!((dx===0 && dy>0) || (dy===0 && dx>0) || (dx===dy && dx>0))) {
        console.warn("Nem egyenes!"); jatekRajzolasa(); return;
    }

    const kartya = jatekAllapot.aktualisKartya;
    const ervenyes = (kartya === 'Joker') || (veg.type === '?') || (veg.type === kartya);
    if (!ervenyes) { alert(`Rossz állomás! Kártya: ${kartya}`); jatekRajzolasa(); return; }

    const koztesPontok = szakaszPontjainakLekerese(kezdo, veg);
    for (let i = 1; i < koztesPontok.length - 1; i++) {
        const p = koztesPontok[i];
        if (allomasLekerese(p.x, p.y)) {
            alert("Nem mehetsz át állomáson!"); jatekRajzolasa(); return;
        }
    }

    let vanMetszes = false;
    for (const szakasz of jatekAllapot.megrajzoltSzakaszok) {
        const regiPontok = szakaszPontjainakLekerese(szakasz.from, szakasz.to);
        for (let i = 1; i < koztesPontok.length - 1; i++) {
            for (let j = 1; j < regiPontok.length - 1; j++) {
                if (koztesPontok[i].x === regiPontok[j].x && koztesPontok[i].y === regiPontok[j].y) {
                    vanMetszes = true; break;
                }
            }
            if (vanMetszes) break;
        }
        if (vanMetszes) break;
    }
    
    const duplikalt = jatekAllapot.megrajzoltSzakaszok.some(s => 
        (s.from.x===kezdo.x && s.from.y===kezdo.y && s.to.x===veg.x && s.to.y===veg.y) ||
        (s.from.x===veg.x && s.from.y===veg.y && s.to.x===kezdo.x && s.to.y===kezdo.y)
    );

    if (vanMetszes || duplikalt) { 
        alert("Kereszteződés tilos!"); 
        jatekRajzolasa(); 
        return; 
    }

    const vonalId = jatekAllapot.vonalSorrend[jatekAllapot.aktualisVonalIndex];
    jatekAllapot.megrajzoltSzakaszok.push({ line: vonalId, from: {x:kezdo.x,y:kezdo.y}, to: {x:veg.x,y:veg.y} });
    
    pontozasFrissitese(kezdo, veg);

    const elsoSzakaszE = jatekAllapot.megrajzoltSzakaszok.filter(s => s.line === vonalId).length === 1;
    if (!elsoSzakaszE) {
        jatekAllapot.aktualisVonalVegpontok = jatekAllapot.aktualisVonalVegpontok.filter(p => p.x!==kezdo.x || p.y!==kezdo.y);
    }
    jatekAllapot.aktualisVonalVegpontok.push({ x: veg.x, y: veg.y });

    jatekRajzolasa();
    kartyaHuzasa();
}

function pontozasFrissitese(kezdo, veg) {
    if (dunaAtkelesEllenorzes(kezdo, veg)) {
        jatekAllapot.aktualisForduloStatisztika.dunaAtkelesek++;
    }

    keruletStatisztikaHozzaadasa(veg);
    
    if (veg.train) {
        jatekAllapot.pontszamok.vonatIndex++;
        vonatCsuszkaFrissitese();
    }
    feluletFrissitese();
}

function keruletStatisztikaHozzaadasa(allomas) {
    const kerulet = allomas.district;
    if (kerulet) {
        jatekAllapot.aktualisForduloStatisztika.keruletek[kerulet] = (jatekAllapot.aktualisForduloStatisztika.keruletek[kerulet] || 0) + 1;
    }
}

function szakaszPontjainakLekerese(p1, p2) {
    const pontok = [];
    const dx = Math.abs(p2.x - p1.x);
    const dy = Math.abs(p2.y - p1.y);
    const lepesek = Math.max(dx, dy);
    
    const lepesX = (p2.x - p1.x) / lepesek;
    const lepesY = (p2.y - p1.y) / lepesek;
    
    for (let i = 0; i <= lepesek; i++) {
        pontok.push({
            x: Math.round(p1.x + i * lepesX),
            y: Math.round(p1.y + i * lepesY)
        });
    }
    return pontok;
}

function dunaAtkelesEllenorzes(p1, p2) {
    
    const ccw = (a, b, c) => (b.x-a.x)*(c.y-a.y) - (c.x-a.x)*(b.y-a.y);
    
    return DUNA_SZAKASZOK.some(dunaSzakasz => {
        const p3 = dunaSzakasz.p1; const p4 = dunaSzakasz.p2;
        const d1 = ccw(p3, p4, p1), d2 = ccw(p3, p4, p2);
        const d3 = ccw(p1, p2, p3), d4 = ccw(p1, p2, p4);
        return (((d1>0 && d2<0)||(d1<0 && d2>0)) && ((d3>0 && d4<0)||(d3<0 && d4>0)));
    });
}

function allomasLekerese(x, y) { return jatekAllapot.allomasok.find(s => s.x===x && s.y===y); }
function vonalSzinLekerese(id) { return jatekAllapot.vonalak.find(l => l.id===id)?.color || "#000"; }
function idozitoInditasa() { setInterval(() => {
    const s = Math.floor((Date.now()-jatekAllapot.kezdesIdo)/1000);
    idozitoKijelzo.textContent = `${Math.floor(s/60).toString().padStart(2,'0')}:${(s%60).toString().padStart(2,'0')}`;
}, 1000); }
function tombKeverese(tomb) { for(let i=tomb.length-1;i>0;i--){const j=Math.floor(Math.random()*(i+1));[tomb[i],tomb[j]]=[tomb[j],tomb[i]];} return tomb; }
function feluletFrissitese() {
    vonatPontKijelzo.textContent = VONAT_PONTOK[Math.min(jatekAllapot.pontszamok.vonatIndex, VONAT_PONTOK.length-1)];
    osszPontKijelzo.textContent = jatekAllapot.pontszamok.osszesen;
}
function vonatCsuszkaFrissitese() {
    vonatPontCsuszka.innerHTML = "";
    VONAT_PONTOK.forEach((pont, index) => {
        const potty = document.createElement("div");
        potty.className = "train-dot";
        potty.style.width = "20px"; potty.style.height = "20px";
        potty.style.borderRadius = "50%";
        potty.style.display = "inline-flex"; potty.style.alignItems = "center"; potty.style.justifyContent = "center";
        potty.style.fontSize = "10px"; potty.style.color = "white";
        potty.style.marginRight = "2px";
        potty.innerText = pont;
        potty.style.backgroundColor = (index <= jatekAllapot.pontszamok.vonatIndex) ? "#0078D7" : "#ccc";
        vonatPontCsuszka.appendChild(potty);
    });
}
function forduloSorrendKirajzolasa() {
    forduloSorrendKijelzo.innerHTML = "";
    jatekAllapot.vonalSorrend.forEach((vonalId, idx) => {
        const vonal = jatekAllapot.vonalak.find(l => l.id === vonalId);
        const potty = document.createElement("div");
        potty.className = "turn-icon";
        potty.style.backgroundColor = vonal.color;
        if (idx === jatekAllapot.aktualisVonalIndex) {
            potty.style.border = "3px solid black";
            potty.style.transform = "scale(1.2)";
        }
        forduloSorrendKijelzo.appendChild(potty);
    });
}

jatekInicializalasa();