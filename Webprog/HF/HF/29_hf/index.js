const temps = [3.4, 1.2, -0.7, -2, 20, 6]

//+feladat
//„Igaz-e, hogy van legalább egy olyan pozitív szám, aminek a tizedesrésze 0.4 vagy nagyobb?”
const vanIlyen = temps
  .filter(t => t > 0)
  .some(t => t % 1 >= 0.4);

console.log(vanIlyen);
// → true (mert 3.4 tizedesrésze 0.4)

//++feladat
//„Add meg az első olyan negatív számot, amely kisebb, mint -1!”
const elso = temps
  .filter(t => t < 0)
  .find(t => t < -1);

console.log(elso); 
 