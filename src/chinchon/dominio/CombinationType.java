package chinchon.dominio;

/**
 * Representa los tipos de combinación válidos en el Chinchón.
 */
public enum CombinationType {
	/** Grupo: tres o más cartas del mismo valor y distinto palo. */
	GROUP,

	/** Escalera: tres o más cartas consecutivas del mismo palo. */
	LADDER,

	/** Chinchón: escalera completa con las siete cartas del mismo palo. */
	CHINCHON,

	/** Indica que el conjunto de cartas analizado no forma ninguna combinación válida. */
	NONE;

}
