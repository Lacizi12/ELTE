
import module java.base;

// osztály
public class Person {
    // állapot: adattagok (mezők)
    private String name;
    private int age;

    // inicializáció: konstruktorok
    public Person(/* Person this, */ String name, int age) {
        if (name.equals("")) throw new IllegalArgumentException("Wrong name: " + name);
        if (age < 0)         throw new IllegalArgumentException("Wrong age: " + age);

        // elfedett név elérhető a segítségével
        this.name = name;
        this.age  = age;
    }

    // műveletek: (példány)metódusok
    public void   ageUp(/* Person this */)   { ++this.age; }
    public int    getAge(/* Person this */)  { return this.age; }
    public String getName(/* Person this */) { return this.name; }

    // szöveggé alakítás (nem kiírás!)
    @Override public String toString() {
        // Java 15+
        return "%s (%d)".formatted(name, age);

//        // Java 5+
//        return String.format("%s (%d)", name, age);
//
//        // hatékony szövegösszefűzéssel
//        var sb = new StringBuilder();
//        sb.append(name);
//        sb.append(" (");
//        sb.append(age);
//        sb.append(")");
//        return sb.toString();
    }

    @Override public boolean equals(Object other) {
        if (other == this)                return true;
        if (!(other instanceof Person p)) return false;

        return Objects.equals(this.name, p.name) &&
               this.age == p.age;
    }

    @Override public int hashCode() {
        return Objects.hash(this.name, this.age);
    }
}
