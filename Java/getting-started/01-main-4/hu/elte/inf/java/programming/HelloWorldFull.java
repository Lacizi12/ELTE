// egyszerűsített programban ilyen nem lehet
// compact source files can't have this
package hu.elte.inf.java.programming;

// a "sztenderd eszközkészlet" teljes tartalma
// egyszerűsített programban implicit jelen van, itt nincs
// this explicitly allows us to use all "standard tools"
// compact source files implicitly have this, normal ones don't
import module java.base;

// fully qualified name: hu.elte.inf.java.programming.HelloWorldFull
//
// Compile: `javac hu/elte/inf/java/programming/HelloWorldFull.java`
// Run:     `java  hu.elte.inf.java.programming.HelloWorldFull`
public class HelloWorldFull {
    public static void main(String[] args) {
        System.out.println("Helló világ! 👍");
    }
}
