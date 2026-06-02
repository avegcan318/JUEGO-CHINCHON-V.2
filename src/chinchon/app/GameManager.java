package chinchon.app;

import java.util.ArrayList;
import java.util.List;

import chinchon.dominio.AiPlayer;
import chinchon.dominio.Deck;
import chinchon.dominio.Game;
import chinchon.dominio.HumanPlayer;
import chinchon.dominio.Player;
import chinchon.util.Colors;

/**
 * Controlador principal que gestiona el inicio y la configuración de las
 * partidas.
 * 
 * Se encarga de mostrar el menú principal, gestionar la creación de jugadores
 * (humanos e IA), definir los límites de puntuación y lanzar la ejecución del
 * juego.
 * 
 * * @author Antonio Miguel Vega Cano
 * 
 * @version 1.0
 */
public class GameManager {
	private static final int MIN_PLAYERS = 2;
	private static final int MAX_PLAYERS = 5;
	private static final int SCORE_LIMIT = 100;
	private final ConsoleInput ci;

	/**
	 * Crea un administrador de juego. * @param ci instancia de {@link ConsoleInput}
	 * para la lectura de datos
	 */
	public GameManager(ConsoleInput ci) {
		this.ci = ci;
	}

	public void show() {
		int opcion;
		printWelcome();

		boolean exit = false;

		while (!exit) {
			System.out.printf("\n%s¿Qué deseas hacer?%s\n", Colors.CYAN, Colors.RESET);
			System.out.println(" 1 - Nueva partida");
			System.out.println(" 2 - Salir");
			System.out.print("Opción: ");

			opcion = ci.readIntInRange(1, 2);
			switch (opcion) {
				case 1 -> startNewGame();
				case 2 -> exit = true;
			}
		}
		System.out.println("\nSaliendo ...");
	}

	private void startNewGame() {
		Deck deck;
		Game game;
		int totalPlayers, mode, numDecks;
		List<Player> players;
		System.out.printf("\nNúmero de jugadores (%d-%d): ", MIN_PLAYERS, MAX_PLAYERS);
		totalPlayers = ci.readIntInRange(MIN_PLAYERS, MAX_PLAYERS);
 
        System.out.println("\nTipo de partida:");
        System.out.println(" 1 - Solo humanos");
        System.out.println(" 2 - Humanos y IA");
        System.out.println(" 3 - Solo IA");
        System.out.print("Opción: ");
		mode = ci.readIntInRange(1, 3);
 
		players = buildPlayers(totalPlayers, mode);

        System.out.print("\n¿Con cuántas barajas jugar? (1 o 2): ");
		numDecks = ci.readIntInRange(1, 2);
 
		deck = new Deck(numDecks);
 
		game = new Game.Builder()
		        .players(players)
		        .deck(deck)
		        .consoleInput(ci)
		        .scoreLimit(SCORE_LIMIT)
		        .build();
		 
        game.play();
    }
 
    private List<Player> buildPlayers(int total, int mode) {
        List<Player> players = new ArrayList<>();
		String name;
		int numHumans, numAi;

        switch (mode) {
            case 1 -> {
                for (int i = 1; i <= total; i++) {
                    System.out.printf("Nombre del jugador %d: ", i);
					name = ci.readString();
                    players.add(new HumanPlayer(name.isBlank() ? "Jugador " + i : name));
                }
            }
            case 2 -> {
                System.out.printf("¿Cuántos son humanos? (1-%d): ", total - 1);
				numHumans = ci.readIntInRange(1, total - 1);
				numAi = total - numHumans;
 
                for (int i = 1; i <= numHumans; i++) {
                    System.out.printf("Nombre del jugador humano %d: ", i);
					name = ci.readString();
                    players.add(new HumanPlayer(name.isBlank() ? "Jugador " + i : name));
                }
                for (int i = 1; i <= numAi; i++) {
                    players.add(new AiPlayer("IA-" + i));
                }
            }
            case 3 -> {
                for (int i = 1; i <= total; i++) {
                    players.add(new AiPlayer("IA-" + i));
                }
            }
        }

        return players;
    }
 
	/**
	 * Imprime el logotipo de bienvenida.
	 */
    private void printWelcome() {
		System.out.printf("%s%n", Colors.YELLOW);
        System.out.println("  ██████╗██╗  ██╗██╗███╗   ██╗ ██████╗██╗  ██╗ ██████╗ ███╗   ██╗");
        System.out.println(" ██╔════╝██║  ██║██║████╗  ██║██╔════╝██║  ██║██╔═══██╗████╗  ██║");
        System.out.println(" ██║     ███████║██║██╔██╗ ██║██║     ███████║██║   ██║██╔██╗ ██║");
        System.out.println(" ██║     ██╔══██║██║██║╚██╗██║██║     ██╔══██║██║   ██║██║╚██╗██║");
        System.out.println(" ╚██████╗██║  ██║██║██║ ╚████║╚██████╗██║  ██║╚██████╔╝██║ ╚████║");
        System.out.println("  ╚═════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝");
        System.out.printf("%s%n", Colors.RESET);
        System.out.println("         IES Saladillo · CFGS DAM · Proyecto Final");
    }
}
