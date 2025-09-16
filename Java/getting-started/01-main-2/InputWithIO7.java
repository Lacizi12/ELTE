
void main() {
    int CURRENT_YEAR = 2025;

    String ageTxt = IO.readln("What is your age? ");
    int age = Integer.parseInt(ageTxt);

    IO.println("You were born in " + (CURRENT_YEAR - age));
    IO.println("You will be " + (age+1) + " next year");
    IO.println("And %d the year after that%n".formatted(age+2));

    var year = Integer.parseInt(IO.readln("Input a year: "));
    if (year < CURRENT_YEAR) {
        IO.println("You are joking, right?!");
        return;
    }

    var maxAge = Integer.parseInt(IO.readln("How long do you expect to live? "));
    var diff = year - CURRENT_YEAR;
    IO.println("""
    The year %d is %d year%s away.
    You will be %d then.
    You will be %s.
    """.formatted(
        year,
        diff,
        diff == 1 ? "" : "s",
        age + diff,
        age + diff > maxAge ? "dead" : "alive"
    ));
}
