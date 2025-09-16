
void main() {
    useLightSwitchV1(true, false, false, false, true);
    IO.println("-------------------");
    useLightSwitchV2(true, false, false, false, true);
    IO.println("-------------------");
    useLightSwitchV3(true, false, false, false, true);
    IO.println("-------------------");
}

void useLightSwitchV1(boolean... clicks) {
    var state = "ON";
    for (int i = 0; i < clicks.length; i++) {
        if (state.equals("ON")) {
            state = clicks[i] ? "OFF" : "ON";
        } else {
            state = clicks[i] ? "ON" : "OFF";
        }

        IO.print(state + " ");
    }
    IO.println();
}

void useLightSwitchV2(boolean... clicks) {
    var state = "ON";
    // "foreach loop" ("enhanced for loop")
    for (boolean click : clicks) {
        if (state.equals("ON")) {
            state = click ? "OFF" : "ON";
        } else {
            state = click ? "ON" : "OFF";
        }

        IO.print(state + " ");
    }
    IO.println();
}

void useLightSwitchV3(boolean... clicks) {
    var state = true;
    for (boolean click : clicks) {
        // fancy "xor" operation (rare)
        state = state ^ click;
        IO.print(state ? "ON " : "OFF ");
    }
    IO.println();
}
