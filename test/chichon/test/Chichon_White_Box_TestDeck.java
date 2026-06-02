package chichon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import chinchon.dominio.Card;
import chinchon.dominio.CardType;
import chinchon.dominio.CardValue;
import chinchon.dominio.Deck;

/**
 * Pruebas unitarias para la clase Deck.
 **/
public class Chichon_White_Box_TestDeck {

	// drawCard
	@Test
	void testDrawCard_withoutStealing_IntactSize() {
		Deck deck = new Deck(1);
		assertEquals(40, deck.size());
	}

	@Test
	void testDrawCard_aCard_reducesToOne() {
		Deck deck = new Deck(1);
		Card drawn = deck.drawCard();
		assertNotNull(drawn);
		assertEquals(39, deck.size());
	}

	// removeCard
	@Test
	void testRemoveCard_validIndex_eliminateTheLetter() {
		Deck deck = new Deck();
		Card card = new Card(CardType.GOLD, CardValue.TWO);
		deck.addCard(card);
		Card removed = deck.removeCard(0);
		assertEquals(card, removed);
		assertTrue(deck.isEmpty());
	}


	@Test
	void testRemoveCard_indiceNegativo_lanzaExcepcion() {
		Deck deck = new Deck(1);
		assertThrows(IndexOutOfBoundsException.class, () -> deck.removeCard(-1));
	}


	@Test
	void testRemoveCard_outOfRangeIndex_throwsException() {
		Deck deck = new Deck(1);
		assertThrows(IndexOutOfBoundsException.class, () -> deck.removeCard(999));
	}

	// isEmpty

	@Test
	void testIsEmpty_mazoVacio_devuelveTrue() {
		Deck deck = new Deck();
		assertTrue(deck.isEmpty());
	}

	@Test
	void testIsEmpty_deckWithCards_returnsFalse() {
		Deck deck = new Deck(1);
		assertFalse(deck.isEmpty());
	}
}
