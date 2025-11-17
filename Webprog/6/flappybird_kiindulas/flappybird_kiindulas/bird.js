const canvas = document.querySelector("#jatek")
const ctx = canvas.getContext("2d")

const madar = {
    x: 10,
    y: canvas.height / 2 - 30,
    width: 50,
    height: 30,
    ay: 300,
    vy: -300
}

const RES = 150
const OSZLOP_TAVOLSAG = 300
const OSZLOP_SEBESSEG = -200
let vege = false
const oszlopok = []
let elozoido = 0;

function random(a, b) {
    return Math.floor(Math.random() * (b - a + 1)) + a;
}

function utkozikE(a, b) {
    // a és b téglalap ütközik-e?
    return !(
        //b teljesen a fölött
        b.y + b.height < a.y ||
        //a teljesen b bal oldalán
        a.x + a.width < b.x ||
        //a teljesen b fölött
        a.y + a.height < b.y ||
        //b teljesen a bal oldalán
        b.x + b.width < a.x
    );
}

function jatekciklus(most = 0) {
    if (!vege) {
        requestAnimationFrame(jatekciklus)
        const dt = (most - elozoido) / 1000
        //console.log(dt);
        elozoido = most
        valtoztat(dt)
        rajzol()
    }
}

function valtoztat(dt) {
    //madar
    madar.vy += madar.ay * dt
    madar.y += madar.vy * dt
    //oszlopok eldobasa

    if (oszlopok.length > 20) {
        oszlopok.shift()
        oszlopok.shift()
    }
    //oszlopok hozzadasa

    const utolso = oszlopok[oszlopok.length - 1]

    if (!utolso || canvas.width - utolso.x > OSZLOP_TAVOLSAG) {
        const felsoOszlopMagassaga = random(10, 300)

        oszlopok.push(
            {
                //felső
                x: canvas.width,
                y: 0,
                width: 50,
                height: felsoOszlopMagassaga
            },
            {
                //alsó
                x: canvas.width,
                y: felsoOszlopMagassaga + RES,
                width: 50,
                height: canvas.height - RES - felsoOszlopMagassaga
            }
        )
    }
    //mozgatas
    for (const oszlop of oszlopok) {
        oszlop.x += OSZLOP_SEBESSEG * dt
        if (utkozikE(madar, oszlop)) { vege = true }
    }
}

function rajzol() {
    //vaszon
    ctx.fillStyle = "lightblue"
    ctx.fillRect(0, 0, canvas.width, canvas.height)

    //madar
    ctx.fillStyle = "green"
    ctx.fillRect(madar.x, madar.y, madar.width, madar.height)
    //oszlopok


    oszlopok.forEach((oszlop) => {
        ctx.fillStyle = "purple"
        ctx.fillRect(oszlop.x, oszlop.y, oszlop.width, oszlop.height)
    })

    if (vege) {

        ctx.fillStyle = "red"
        ctx.font = "100px serif"
        ctx.fillText("Vége", canvas.width / 2, canvas.height / 2)
    }
}

jatekciklus()

document.addEventListener("keydown",bill)

function bill(e) {
    madar.vy+=-200
}