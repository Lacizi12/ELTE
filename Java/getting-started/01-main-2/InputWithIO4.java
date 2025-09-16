import java.io.IO;

void main() {
    var age = IO.readln("What is your age? ");

    var oldAge = age + 1;
    IO.println("You will be " + oldAge + " next year, right?");
}
