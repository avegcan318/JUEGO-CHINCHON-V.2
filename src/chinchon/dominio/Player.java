package chinchon.dominio;

/**
 * Representa un jugador de la partida de Chinchón.
 * 
 * Gestiona el estado individual del participante, incluyendo su nombre, la
 * puntuación acumulada y su mano actual de cartas. Esta clase sirve como base
 * para {@link HumanPlayer} y {@link AiPlayer}.
 * 
 */
public class Player {

	/** Nombre del jugador. */
	private String name;

	/** Puntuación acumulada a lo largo de la partida. */
	private int score;

	/** Mano de cartas del jugador. */
	private Deck hand;

	/**
	 * Crea un jugador con el nombre indicado, puntuación inicial 0 y crea una mano
	 * vacía.
	 *
	 * @param name nombre del jugador
	 */
	public Player(String name) {
		this.name = name;
		score = 0;
		hand = new Deck();
	}

	/**
	 * Obtiene el nombre del jugador.
	 *
	 * @return cadena con el nombre del jugador
	 */
	public String getName() {
		return name;
	}

	/**
	 * Obtiene la puntuación acumulada durante toda la partida.
	 *
	 * @return total de puntos acumulados.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Obtine la mano de cartas del jugador.
	 *
	 * @return objeto {@link Deck} que representa la mano.
	 */
	public Deck getHand() {
		return hand;
	}

	/**
	 * Suma los puntos indicados a la puntuación acumulada del jugador.
	 *
	 * @param points cantidad de puntos a sumar al total.
	 */
	public void addPoints(int points) {
		score += points;
	}

	/**
	 * Comprueba si el jugador ha alcanzado o superado el límite de puntos
	 * establecido.
	 *
	 * @param scoreLimit puntuación máxima permitida
	 * @return {@code true} si el jugador debe ser eliminado; {@code false} en caso
	 *         contrario.
	 */
	public boolean isEliminated(int scoreLimit) {
		return score >= scoreLimit;
	}

	/**
	 * Evalúa y calcula el valor numérico de las cartas no combinadas en la mano
	 * actual.
	 * 
	 * Utiliza un {@link HandEvaluator} para determinar qué cartas penalizan al
	 * jugador.
	 *
	 * @return puntos de la mano actual
	 */
	public int calculateHandPoints() {
		return new HandEvaluator(hand.getCards()).calculatePoints();
	}

	/**
	 * Determina si el jugador cumple los requisitos legales para cerrar la ronda.
	 * 
	 * Para cerrar, tras el robo (teniendo {@code 8} cartas), el jugador debe tener
	 * al menos {@code 6} cartas combinadas. Si tiene {@code 6}, la carta restante
	 * debe valer {@code 5} puntos o menos. Si tiene {@code 7}, puede cerrar
	 * siempre.
	 *
	 * return {@code true} si se cumplen las reglas de cierre; {@code false} de lo
	 * contrario.
	 */
	public boolean canClose(int scoreLimit, boolean isFirstTurn) {
		HandEvaluator evaluator = new HandEvaluator(hand.getCards());
		int combined = evaluator.countCombinedCards();
		int looseCardPoints;
		
		if (isFirstTurn || score >= scoreLimit) {
			return false;
		}

		if (combined == 7) {
			return true;
		}

		if (combined == 6) {
			looseCardPoints = hand.getCards().stream()
					.filter(c -> !evaluator.findBestCombinedCards().contains(c))
					.mapToInt(Card::getPoints).sum();
			return looseCardPoints <= 5;
		}
		return false;
	}

	/**
	 * Verifica si el jugador tiene Chinchón (escalera completa de 7 cartas del
	 * mismo palo).
	 *
	 * Se considera Chinchón si posee una escalera completa de las siete cartas del
	 * mismo palo.
	 *
	 * @return {@code true} si la mano es un Chinchón; {@code false} en caso
	 *         contrario.
	 */
	public boolean hasChinchon() {
		return new HandEvaluator(hand.getCards()).isChinchon();
	}

	/**
	 * Devuelve una descripción detallada del estado del jugador.
	 * 
	 * @return cadena descriptiva del jugador
	 */
	@Override
	public String toString() {
		return String.format("%s | Mano: %s | Puntos totales: %d", name, hand, score);
	}
}