document.getElementById("hozzaad").addEventListener("click", () => {
  const tb = document.querySelector("#tabla tbody");
  const tr = document.createElement("tr");
  tr.innerHTML =
    `<td>${document.getElementById("oszlop1").value}</td>` +
    `<td>${document.getElementById("oszlop2").value}</td>` +
    `<td>${document.getElementById("oszlop3").value}</td>`;
  tb.appendChild(tr);
});
