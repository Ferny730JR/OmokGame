package omok;

import java.awt.*;

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

    /** Color of this player. */
    private final Color color;

    /** Create a new player with the given name and ASCII stone.
     *  Used for creating ConsoleUI players.
     */
    public Player(String name, char stone) {
        this.name = name;
        this.stone = stone;
        this.color = Color.GREEN;
    }

    /** Create a new player with the given name and color.
     *  Used for creating GUI players.
     */
    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.stone = ' ';
    }

    /** Return the name of this player. */
    public String getName() {
        return name;
    }

    /** Return the stone piece of this player. */
    public char getStone() {
        return stone;
    }

    /** Return the color of this player. */
    public Color getColor() { return color; }
}
