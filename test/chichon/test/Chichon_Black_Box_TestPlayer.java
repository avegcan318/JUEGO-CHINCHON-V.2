package chichon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import chinchon.dominio.HumanPlayer;
import chinchon.dominio.Player;

/**
 * Pruebas unitarias para la clase Player.
 **/
public class Chichon_Black_Box_TestPlayer {

	private Player player;

	@BeforeEach
	void setUp() {
		player = new HumanPlayer("Test");
	}

	// addPoints / getScore()
	
	@ParameterizedTest
	@CsvSource({ 
		"0,    0", 
		"15,  15", 
		"-10, -10" 
	})
	void testAddPoints_different_partitions_accumulates_correctly(int points, int expectedScore) {
		player.addPoints(points);
		assertEquals(expectedScore, player.getScore());
	}
	
	@Test
	void testAddPoints_twoSums_accumulatesBoth() {
		player.addPoints(10);
		player.addPoints(5);
		assertEquals(15, player.getScore());
	}
}
