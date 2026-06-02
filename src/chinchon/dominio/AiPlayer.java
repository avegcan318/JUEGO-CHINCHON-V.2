package chinchon.dominio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Representa a un jugador controlado por la IA.
 *
 * La IA toma decisiones automáticas durante la partida, analizando las cartas
 * del descarte y evaluando la utilidad de cada carta en su mano para minimizar
 * la puntuación total y buscar el cierre de la ronda.
 * 
 * @author TuNombre
 * @version 1.0
 */
public class AiPlayer extends Player {
	/**
	 * Crea un nuevo jugador controlado por la IA con el nombre indicado.
	 *
	 * @param name cadena de texto con el nombre identificativo de la IA.
	 */
	public AiPlayer(String name) {
		super(name);
	}

	/**
	 * Decide si conviene robar la carta superior del descarte.
	 *
	 * La IA simula añadir la carta a su mano y descartar posteriormente
	 * la peor carta posible. Si la puntuación resultante de la mano es
	 * menor que la actual, decide robar del descarte.
	 *
	 * @param discardTopCard carta superior del montón de descarte
	 * @return {@code true} si la carta a robar mejora la mano,
	 *         {@code false} en caso contrario
	 */
	public boolean shouldDrawFromDiscard(Card discardTopCard) {
		if (discardTopCard == null) {
			return false;
		}
		HandEvaluator current = new HandEvaluator(getHand().getCards());
		int pointsBefore = current.calculatePoints();
		List<Card> simulated = new ArrayList<>(getHand().getCards());


		simulated.add(discardTopCard);
		simulated.remove(findWorstCard(simulated));

		return new HandEvaluator(simulated).calculatePoints() < pointsBefore;
	}

	/**
	 * Elige qué carta descartar de la mano actual.
	 *
	 * La IA selecciona la carta considerada menos útil según
	 * la evaluación de la mano.
	 *
	 * @return índice de la carta a descartar
	 */
	public int chooseCardToDiscard() {
		List<Card> cards = getHand().getCards();
		return cards.indexOf(findWorstCard(cards));
	}

	/**
	 * Busca la peor carta de la mano.
	 *
	 * Primero intenta encontrar la carta de mayor valor que no forme parte de
	 * ninguna combinación válida. Si todas las cartas pertenecen a combinaciones,
	 * devuelve la carta de mayor puntuación de la mano.
	 *
	 * @param cards cartas a evaluar
	 * @return el objeto {@link Card} considerado menos útil para el jugador.
	 */
	private Card findWorstCard(List<Card> cards) {
		List<Card> combined = new HandEvaluator(cards).findBestCombinedCards();
		return cards.stream()
				.filter(c -> !combined.contains(c))
				.max(Comparator.comparingInt(Card::getPoints))
				.orElse(cards.stream()
				.max(Comparator.comparingInt(Card::getPoints))
				.orElse(cards.get(cards.size() - 1)));
	}
}
