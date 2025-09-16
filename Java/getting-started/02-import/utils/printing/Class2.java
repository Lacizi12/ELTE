package utils.printing;

import module java.base;

public class Class2 {
    // impure function: has side effects (e.g. reads, writes) 👿
    // mellékhatásos függvény: nem tiszta (pl. beolvas, kiír) 👿
    public static void inputAndPrint(String prompt) {
        String txt = IO.readln(prompt);
        IO.println(txt + txt);
    }
}
