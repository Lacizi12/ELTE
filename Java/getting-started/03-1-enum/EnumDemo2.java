
public enum Rank { ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING }
public enum Suit { SPADES, CLUBS, HEARTS, DIAMONDS }
public enum GirlsBestFriend { DIAMONDS }

public record Card(Rank rank, Suit suit) {}

void main() {
    // all the values of the enum in an array
    // az enum összes értéke egy tömbben
    for (var rank : Rank.values()) {
        for (var suit : Suit.values()) {
            IO.println(rank + " of " + suit);
        }
    }
}
