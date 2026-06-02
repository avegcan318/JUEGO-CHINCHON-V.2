package chichon.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chinchon.dominio.HumanPlayer;
import chinchon.dominio.Player;

/**
 * Pruebas unitarias para la clase Player.
 **/
public class Chinchon_White_Box_TestPlayer {
	private Player player;

	@BeforeEach
	void setUp() {
		player = new HumanPlayer("Test");
	}

	// isEliminated
	@Test
	void testIsEliminated_justBelow_returnsFalse() {
		player.addPoints(99);
		assertFalse(player.isEliminated(100));
	}

	@Test
	void testIsEliminated_exactlyAtTheLimit_returnsTrue() {
		player.addPoints(100);
		assertTrue(player.isEliminated(100));
	}

	@Test
	void testIsEliminated_above_returnsTrue() {
		player.addPoints(101);
		assertTrue(player.isEliminated(100));
	}

}
