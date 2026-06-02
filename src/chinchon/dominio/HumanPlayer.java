package chinchon.dominio;

/**
 * Representa a un jugador humano de la partida.
 *
 * Esta clase extiende la funcionalidad de {@link Player} para identificar
 * específicamente a los participantes controlados por personas,
 * diferenciándolos de las entidades controladas por la lógica del juego como
 * {@link AiPlayer}.
 *
 * @author TuNombre
 * @version 1.0
 */
public class HumanPlayer extends Player {
	/**
	 * Crea un jugador humano con el nombre indicado.
	 *
	 * @param name cadena de texto con el nombre que identificará al jugador.
	 */
	public HumanPlayer(String name) {
		super(name);
	}
}
