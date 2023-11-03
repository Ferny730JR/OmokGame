package omok;

/**
 * A player in an Omok game. It holds the name of the player and
 * can be used to identify a specific player throughout the game.
 * The omok.Player class helps to keep track of the moves made by each
 * player during the game.
 */
public class Player {

    /** Name of this player. */
    private final String name;

    /** Stone of this player. */
    private final char stone;

    /** Create a new player with the given name. */
    public Player(String name, char stone) {
        this.name = name;
        this.stone = stone;
    }

    /** Return the name of this player. */
    public String getName() {
        return name;
    }

    /** Return the stone piece of this player. */
    public char getStone() {
        return stone;
    }
}
