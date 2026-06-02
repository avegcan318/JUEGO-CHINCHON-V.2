package chinchon.dominio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Evalúa una mano de cartas del juego Chinchón.
 *
 * Permite: detectar combinaciones válidas,calcular puntos, detectar Chinchón,
 * obtener las mejores combinaciones posibles.
 */
public class HandEvaluator {

	/** Mano ordenada por valor ascendente. */
	private final List<Card> hand;

	/**
	 * Construye un evaluador para una lista específica de cartas.
	 *
	 * Automáticamente ordena las cartas por su valor numérico ascendente 
	 * para optimizar la detección de escaleras.
	 * 
	 * @param hand lista de objetos {@link Card} a evaluar.
	 */
	public HandEvaluator(List<Card> hand) {
		this.hand = hand.stream()
				.sorted(Comparator.comparingInt(c -> c.getCardValue().getValue()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Clasifica una lista de cartas según las reglas de combinación del juego.
	 * 
	 * *@param combo lista de cartas a identificar.
	 * @return el tipo de combinación detectada ({@link CombinationType}).
	 */
	public CombinationType identifyCombination(List<Card> combo) {
		if (isChinchon(combo)) {
			return CombinationType.CHINCHON;
		}

		if (isGroup(combo)) {
			return CombinationType.GROUP;
		}

		if (isLadder(combo)) {
			return CombinationType.LADDER;
		}

		return CombinationType.NONE;
	}


	public boolean isChinchon() {
		return isChinchon(hand);
	}

	/**
	 * Comprueba si una lista de cartas forma Chinchón.
	 */
	public boolean isChinchon(List<Card> cards) {
		long suits;
		List<Card> sorted;

		if (cards.size() != 7) {
			return false;
		}

		suits = cards.stream()
				.map(Card::getCardType)
				.distinct()
				.count();

		if (suits != 1) {
			return false;
		}

		sorted = cards.stream().sorted(Comparator.comparingInt(c -> c.getCardValue().getValue()))
				.collect(Collectors.toList());

		return areConsecutive(sorted);
	}


	/**
	 * Comprueba si una combinación es grupo.
	 * 
	 * param combo cartas a evaluar.
	 * 
	 * @return {@code true} si forman un grupo válido; {@code false} de lo
	 *         contrario.
	 */
	public boolean isGroup(List<Card> cards) {
		long uniqueValues;

		if (cards.size() < 3) {
			return false;
		}

		uniqueValues = cards.stream().map(Card::getCardValue).distinct().count();

		return uniqueValues == 1;
	}

	/**
	 * Busca todos los grupos válidos.
	 * 
	 * @return lista con todas las cartas que forman parte de una combinación
	 *         válida.
	 */
	public List<Card> findGroups() {
		List<Card> allGroups = new ArrayList<>();
		List<Card> sameValue;

		for (CardValue value : CardValue.values()) {
			sameValue = hand.stream()
					.filter(c -> c.getCardValue() == value)
					.collect(Collectors.toList());

			if (sameValue.size() >= 3) {
				allGroups.addAll(sameValue);
			}
		}

		return allGroups;
	}


	/**
	 * Comprueba si una combinación es escalera.
	 */
	public boolean isLadder(List<Card> cards) {
		long suits;
		List<Card> sorted;

		if (cards.size() < 3) {
			return false;
		}

		suits = cards.stream().map(Card::getCardType).distinct().count();

		if (suits != 1) {
			return false;
		}

		sorted = cards.stream().sorted(Comparator.comparingInt(c -> c.getCardValue().getValue()))
				.collect(Collectors.toList());

		return areConsecutive(sorted);
	}

	/**
	 * Busca todas las escaleras válidas.
	 */
	public List<Card> findLadders(List<Card> cards) {
		List<Card> allLadderCards = new ArrayList<>();
		List<Card> sameSuit;
		List<Card> currentRun = new ArrayList<>();
		int prev, curr;

		for (CardType type : CardType.values()) {
			sameSuit = cards.stream().filter(c -> c.getCardType() == type)
					.sorted(Comparator.comparingInt(c -> c.getCardValue().getValue())).collect(Collectors.toList());

			if (!sameSuit.isEmpty()) {
				currentRun = new ArrayList<>();
				currentRun.add(sameSuit.get(0));

				for (int i = 1; i < sameSuit.size(); i++) {
					prev = sameSuit.get(i - 1).getCardValue().getValue();
					curr = sameSuit.get(i).getCardValue().getValue();

					if (curr == prev + 1 || (prev == 7 && curr == 10)) {
						currentRun.add(sameSuit.get(i));
					} else {

						if (currentRun.size() >= 3) {
							allLadderCards.addAll(currentRun);
						}
						currentRun = new ArrayList<>();
						currentRun.add(sameSuit.get(i));
					}
				}

				if (currentRun.size() >= 3) {
					allLadderCards.addAll(currentRun);
				}
			}
		}
		return allLadderCards;
	}

	/**
	 * Obtiene las mejores cartas combinadas.
	 */
	public List<Card> findBestCombinedCards() {

		List<Card> groups = findGroups();
		List<Card> remaining = hand.stream().filter(c -> !groups.contains(c)).collect(Collectors.toList());
		List<Card> ladders = findLadders(remaining);
		List<Card> combined = new ArrayList<>();

		combined.addAll(groups);
		combined.addAll(ladders);

		return combined;
	}

	/**
	 * Número de cartas combinadas.
	 * 
	 * @return número entero de cartas combinadas.
	 */
	public int countCombinedCards() {
		return findBestCombinedCards().size();
	}


	/**
	 * Calcula puntos de cartas no combinadas.
	 * 
	 * @param combinedCards lista de cartas ya identificadas como combinadas.
	 * @return total de puntos de penalización.
	 */
	public int calculateUncombinedPoints(List<Card> combinedCards) {
		return hand.stream()
				.filter(c -> !combinedCards.contains(c))
				.mapToInt(Card::getPoints)
				.sum();
	}

	/**
	 * Calcula puntos totales de la mano.
	 * 
	 * @return puntos totales de la mano actual.
	 */
	public int calculatePoints() {
		List<Card> combined = findBestCombinedCards();

		return calculateUncombinedPoints(combined);
	}


	/**
	 * Comprueba si las cartas son consecutivas.
	 * 
	 * @param cards lista de cartas a verificar.
	 * @return {@code true} si son consecutivas; {@code false} en caso contrario.
	 */
	public boolean areConsecutive(List<Card> cards) {
		int prev, curr;
		if (cards.size() < 2) {
			return true;
		}

		for (int i = 1; i < cards.size(); i++) {
			prev = cards.get(i - 1).getCardValue().getValue();
			curr = cards.get(i).getCardValue().getValue();

			if (!(curr == prev + 1 || (prev == 7 && curr == 10))) {
				return false;
			}
		}
		return true;
	}
}