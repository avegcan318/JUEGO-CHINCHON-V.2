package chichon.test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chinchon.dominio.Card;
import chinchon.dominio.CardType;
import chinchon.dominio.CardValue;

/**
 * Pruebas unitarias para la clase Card.
 **/
public class Chinchon_White_Box_TestCard {

	@Test
	void testEquals_sameTypeAndValue_returnsTrue() {
		Card card1 = new Card(CardType.GOLD, CardValue.THREE);
		Card card2 = new Card(CardType.GOLD, CardValue.THREE);
		assertTrue(card1.equals(card2));
	}

	@Test
	void testEquals_distinctSuit_returnsFalse() {
		Card card1 = new Card(CardType.GOLD, CardValue.THREE);
		Card card2 = new Card(CardType.CUPS, CardValue.THREE);
		assertFalse(card1.equals(card2));
	}

	@Test
	void testEquals_distinctValue_returnsFalse() {
		Card card1 = new Card(CardType.GOLD, CardValue.THREE);
		Card card2 = new Card(CardType.GOLD, CardValue.FIVE);
		assertFalse(card1.equals(card2));
	}

	@Test
	void testEquals_withNull_returnsFalse() {
		Card card = new Card(CardType.GOLD, CardValue.THREE);
		assertFalse(card.equals(null));
	}
}
