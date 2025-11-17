// Egy mezo adatait taroljuk el ide

//enum tipus mukodeset szimulalo cucc, enum 3 valtozos cucc
//objektum
const FieldState = {
    REVEALED: 1,
    UNREVEALED: 2,
    FLAGGED: 4
}

class Field {
    isMine = false;
    state = FieldState.UNREVEALED;
    neigborCount = 0;
}