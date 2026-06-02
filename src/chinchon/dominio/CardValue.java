package chinchon.dominio;

/**
 * Representa los posibles valores de una carta de la baraja española.
 * 
 * Cada valor lleva asociado un símbolo para mostrar por consola y una
 * puntuación numérica que se usa al calcular los puntos de la mano.
 */
public enum CardValue {
	/** Carta con valor 1. */
	ONE("1", 1),

	/** Carta con valor 2. */
	TWO("2", 2),

	/** Carta con valor 3. */
	THREE("3", 3),

	/** Carta con valor 4. */
	FOUR("4", 4),

	/** Carta con valor 5. */
	FIVE("5", 5),

	/** Carta con valor 6. */
	SIX("6", 6),

	/** Carta con valor 7. */
	SEVEN("7", 7),

	/** Sota (valor 10). */
	JACK("10", 10),

	/** Caballo (valor 11). */
	KNIGHT("11", 11),

	/** Rey (valor 12). */
	KING("12", 12);

	/** Símbolo numérico que se muestra por consola. */
	private final String symbol;

	/** Puntuación de la carta. */
	private final int points;

	/**
	 * Constructor del enum.
	 *
	 * @param symbol representación textual del valor
	 * @param points puntuación asociada a la carta
	 */
	CardValue(String symbol, int points) {
		this.symbol = symbol;
		this.points = points;
	}

	/**
	 * Devuelve la representación textual del valor.
	 *
	 * @return símbolo del valor
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Devuelve el valor de la carta.
	 *
	 * @return puntuación numérica
	 */
	public int getValue() {
		return points;
	}

	/**
	 * Devuelve la puntuación de la carta.
	 *
	 * @return puntuación numérica
	 */
	public int getPoints() {
		return points;
	}

}