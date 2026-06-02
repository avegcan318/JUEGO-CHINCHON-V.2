package chinchon.dominio;

import java.util.Objects;

/**
 * Representa una carta de la baraja española.
 * 
 * Una carta está definida de forma inmutable por su palo ({@link CardType}) y
 * su valor ({@link CardValue}). Una vez instanciada, sus atributos no pueden
 * ser modificados.
 * 
 * @author Antonio Miguel Vega Cano
 * @version 1.0
 */
public class Card {
	/** Palo de la carta. */
	private final CardType type;

	/** Valor de la carta. */
	private final CardValue value;

	/**
	 * Crea una carta con el palo y valor indicados.
	 *
	 * @param type  palo de la carta
	 * @param value valor de la carta
	 */
	public Card(CardType type, CardValue value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Obtiene el palo al que pertenece la carta.
	 *
	 * @return el ({@link CardType}) de la carta
	 */
	public CardType getCardType() {
		return type;
	}

	/**
	 * Obtiene el valor de la carta.
	 *
	 * @return value ({@link CardValue})
	 */
	public CardValue getCardValue() {
		return value;
	}

	/**
	 * Obtiene la puntuación de la carta utilizada para el recuento final.
	 *
	 * @return value entero de los puntos según la jerarquía de la baraja
	 */
	public int getPoints() {
		return value.getPoints();
	}

	/**
	 * Indica si esta carta es igual a otro objeto. Dos cartas se consideran iguales
	 * si y solo si coinciden tanto en su palo como en su valor.
	 *
	 * @param o objeto con el que comparar
	 * @return {@code true} si palo y valor coinciden; {@code false} en caso
	 *         contrario
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Card))
			return false;

		Card card = (Card) o;

		return type == card.type && value == card.value;
	}

	/**
	 * Genera un código hash único para la carta basado en su estado.
	 *
	 * @return código hash calculado a partir del palo y el valor.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(type, value);
	}

	/**
	 * Devuelve una representación textual de la carta.
	 *
	 * @return cadena con el símbolo del valor seguido del emoji del palo
	 */
	@Override
	public String toString() {
		return value.getSymbol() + type.getSymbol();
	}
}
