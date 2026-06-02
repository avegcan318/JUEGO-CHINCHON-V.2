package chinchon.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa un conjunto de cartas de la baraja española, funcionando como
 * mazo, mano de jugador o montón de descartes.
 * 
 * Permite la gestión de una colección de objetos {@link Card}, proporcionando
 * funcionalidades para inicializar barajas completas, barajar, extraer y añadir
 * cartas.
 * 
 * @author Antonio Miguel Vega Cano
 * @version 1.0
 * 
 */
public class Deck {

	/** Lista de cartas que componen el mazo. */
	private List<Card> cards;

	/** Número de barajas con las que se inicializa el mazo. */
	private int numberOfDecks;

	/**
	 * Crea un mazo completo con el número de barajas indicado.
	 * 
	 * Se generan todas las combinaciones posibles de palos y valores por cada
	 * unidad de baraja especificada.
	 *
	 * @param numberOfDecks número de barajas a incluir (debe ser mayor que 0)
	 */
	public Deck(int numberOfDecks) {
		this.numberOfDecks = numberOfDecks;
		cards = new ArrayList<>();
		initializeDeck();
	}

	 /**
     * Crea un mazo vacío.
     * 
     * Usado para representar la mano de un jugador o el pile de descarte.
     */
    public Deck() {
        numberOfDecks = 0;
        cards = new ArrayList<>();
    }

    /**
     * Rellena el mazo con todas las cartas correspondientes al número de barajas.
     */
    private void initializeDeck() {
        for (int i = 0; i < numberOfDecks; i++) {
            for (CardType type : CardType.values()) {
                for (CardValue value : CardValue.values()) {
                    cards.add(new Card(type, value));
                }
            }
        }
    }

    /**
     * Baraja las cartas del mazo de forma aleatoria.
     */
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    /**
	 * Extrae y devuelve la carta situada en la parte superior del mazo.
	 *
	 * @return el objeto {@link Card} extraído.
	 * @throws IllegalStateException si el mazo no contiene cartas al intentar
	 *                               extraer.
	 */
    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("El mazo está vacío.");
        }
        return cards.remove(0);
    }

    /**
	 * Añade una carta al final del mazo actual.
	 *
	 * @param card la {@link Card} que se desea introducir en el mazo.
	 */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
	 * Elimina y devuelve la carta situada en una posición específica.
	 *
	 * @param i índice de la carta (basado en 0)
	 * @return el objeto {@link Card} que se encontraba en dicha posición.
	 * @throws IndexOutOfBoundsException si el índice es negativo o superior al
	 *                                   tamaño del mazo.
	 */
    public Card removeCard(int i) {
        if (i < 0 || i >= cards.size()) {
            throw new IndexOutOfBoundsException("Índice de carta inválido: " + i);
        }
        return cards.remove(i);
    }

    /**
	 * Devuelve la lista de cartas del mazo.
	 *
	 * @return una lista de {@link Card}
	 */
    public List<Card> getCards() {
        return cards;
    }

    /**
	 * Obtiene el número de cartas que contiene el mazo.
	 *
	 * @return número total de cartas
	 */
    public int size() {
        return cards.size();
    }

    /**
	 * Comprueba si el mazo no contiene cartas.
	 *
	 * @return {@code true} si el mazo está vacío; {@code false} en caso contrario
	 */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
	 * Devuelve una representación textual numerada de todas las cartas del mazo.
	 * 
	 * Cada carta se muestra con su índice de posición (empezando en 1) y su
	 * símbolo.
	 *
	 * @return cadena con la lista de cartas separadas por espacios.
	 */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
			sb.append(String.format("%d %s", i + 1, cards.get(i)));
            if (i < cards.size() - 1) {
                sb.append("  ");
            }
        }
        return sb.toString();
    }
}