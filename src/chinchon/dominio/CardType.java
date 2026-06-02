package chinchon.dominio;

/**
 * Representa los cuatro palos de la baraja española.
 * 
 * Cada palo lleva asociado un símbolo (un emoji), que se usa al mostrar las
 * cartas por consola.
 */
public enum CardType {
	/** Palo de oros. */
    GOLD("\uD83E\uDE99"),

    /** Palo de copas. */
    CUPS("\uD83C\uDF77"),

    /** Palo de bastos. */
    CLUBS("\uD83E\uDEB5"),

    /** Palo de espadas. */
    SWORDS("\u2694\uFE0F");

    /** Emoji que representa visualmente el palo. */
	private final String symbol;

	/**
	 * Constructor del enum.
	 *
	 * @param symbol emoji asociado al palo
	 */
	CardType(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Devuelve el emoji asociado al palo.
	 *
	 * @return símbolo emoji del palo
	 */
	public String getSymbol() {
		return symbol;
	}
}
