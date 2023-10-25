import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Francisco Cavazos", 'W');
    }
    @Test
    void getName() {
        assertEquals("Francisco Cavazos",player.getName());
    }

    @Test
    void getStone() {
        assertEquals('W',player.getStone());
    }
}