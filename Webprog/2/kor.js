function kerulet (r)
{
    return 2 * r * Math.PI;
}

const gomb = document.querySelector('#szamol');
gomb.addEventListener('click', kattintas);

function kattintas()
{
    //beolvasas, hol az adat amit tarolok
    // kivalasztjuk az input mezot
    const input = document.querySelector('#sugar');
    // console.log(input); Ezzel  teszteljuk hogy sikerult e
    const r = parseFloat(input.value); // Ez az inputnak megnezi  a valuejat, es floatta alakitja
    //feldolgozas
    const ker = kerulet(r);
    //kiiras, inner html megvaltoztatasa
    const output = document.querySelector('#kerulet'); // ezzel kivalasztjuk  a spant aminek ay id-je az hogy kerulet
    output.innerHTML = ker;


    
}