package chichon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import chinchon.dominio.Card;
import chinchon.dominio.CardType;
import chinchon.dominio.CardValue;

/**
 * Pruebas unitarias para la clase Card.
 **/
public class Chinchon_Black_Box_TestCard {

	@ParameterizedTest
	@CsvSource({
		"ONE,   1",
		"TWO,   2",
		"THREE, 3",
		"FOUR,  4",
		"FIVE,  5",
		"SIX,   6",
		"SEVEN, 7" })
	void testGetPoints_numCard_returnValue(String cardValueName, int expectedPoints) {
		CardValue value = CardValue.valueOf(cardValueName.trim());
		Card card = new Card(CardType.GOLD, value);
		assertEquals(expectedPoints, card.getPoints());
	}

	@ParameterizedTest
    @CsvSource({
        "JACK,   10",
        "KNIGHT, 11",
        "KING,   12"
    })
	void testGetPoints_figure_returnValue(String cardValueName, int expectedPoints) {
        CardValue value = CardValue.valueOf(cardValueName.trim());
        Card card = new Card(CardType.CUPS, value);
		assertEquals(expectedPoints, card.getPoints());
	
	}

}
