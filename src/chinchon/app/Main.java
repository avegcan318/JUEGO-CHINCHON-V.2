package chinchon.app;

/**
 * Clase principal que actúa como punto de entrada de la aplicación.
 * 
 * Inicia la instancia de entrada de consola y el administrador de juego para
 * comenzar la ejecución.
 * 
 * @author Antonio Miguel Vega Cano
 * 
 * @version 1.0
 */
public class Main {

	/**
	 * Inicializa los componentes básicos y muestra el menú principal.
	 */
	public void show() {
		ConsoleInput ci = ConsoleInput.getInstance();
		GameManager manager = new GameManager(ci);
		manager.show();
	}

	/**
	 * Método principal de ejecución.
	 */
	public static void main(String[] args) {
		new Main().show();
	}
}
