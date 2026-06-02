package chichon.test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import chinchon.dominio.Deck;

/**
 * Pruebas unitarias para la clase Deck.
 **/
public class Chichon_Black_Box_TestDeck {


	@ParameterizedTest
	@CsvSource({ 
			"1, 40",
			"2, 80"
		})
	void testSize_numberDecks_returns_for_40(int numDecks, int expectedSize) {
		Deck deck = new Deck(numDecks);
		assertEquals(expectedSize, deck.size());
	}


}
