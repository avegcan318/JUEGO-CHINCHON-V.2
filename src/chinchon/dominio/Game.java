package chinchon.dominio;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import chinchon.app.ConsoleInput;
import chinchon.util.Colors;

/**
 * Gestiona el flujo principal y las reglas de una partida de Chinchón.
 * 
 * Se encarga de coordinar las rondas, los turnos de los jugadores (humanos e
 * IA), el reparto de cartas, la gestión de los mazos y la determinación del
 * ganador.
 * 
 * * @author Antonio Miguel Vega Cano
 * 
 * @version 1.0
 */
public class Game {
	private final List<Player> players;
	private final Deck mainDeck;
	private final Deck discardPile;
	private final ConsoleInput ci;
	private final int scoreLimit;
	private int roundNumber;
	private boolean chinchonWon;

	/**
	 * Inicializa una nueva instancia del juego con los parámetros configurados.
	 *
	 * @param players    lista de jugadores participantes.
	 * @param mainDeck   mazo principal de cartas.
	 * @param ci         gestor de entrada por consola.
	 * @param scoreLimit límite de puntos para la eliminación.
	 */
	public Game(List<Player> players, Deck mainDeck, ConsoleInput ci, int scoreLimit) {
		this.players = players;
		this.mainDeck = mainDeck;
		this.discardPile = new Deck();
		this.ci = ci;
		this.scoreLimit = scoreLimit;
		this.roundNumber = 1;
		this.chinchonWon = false;
	}

	/**
	 * Inicia el bucle principal del juego hasta que se cumpla la condición de fin.
	 */
	public void play() {
		mainDeck.shuffleDeck();
		while (!isGameOver()) {
			playRound();
			roundNumber++;
		}
		showFinalResult();
	}

	/**
	 * Ejecuta una ronda completa de juego, incluyendo el reparto y los turnos
	 * individuales.
	 */
	private void playRound() {
		boolean roundOver = false;
		int turnNumber = 1;
		int closingPlayerIndex = -1;
		Player current;

		System.out.printf("\n%sRONDA %d%s\n", Colors.CYAN, roundNumber, Colors.RESET);
		dealCards();

		while (!roundOver) {
			for (int i = 0; i < players.size() && !roundOver; i++) {
				current = players.get(i);

				System.out.printf("\n%sTurno de %s%s\n", Colors.YELLOW, current.getName(), Colors.RESET);

				if (current instanceof AiPlayer) {
					roundOver = playAiTurn((AiPlayer) current, turnNumber);
				} else {
					roundOver = playHumanTurn(current, turnNumber);
				}

				if (roundOver) {
					closingPlayerIndex = i;
				}
				turnNumber++;
			}
		}

		endRound(closingPlayerIndex);
		players.removeIf(p -> p.isEliminated(scoreLimit));
	}
	/**
	 * Realiza el reparto inicial de 7 cartas a cada jugador y prepara el descarte.
	 */
	private void dealCards() {
		for (Player p : players) {
			while (!p.getHand().isEmpty()) {
				mainDeck.addCard(p.getHand().removeCard(0));
			}
		}

		while (!discardPile.isEmpty()) {
			mainDeck.addCard(discardPile.removeCard(0));
		}
		mainDeck.shuffleDeck();

		for (int i = 0; i < 7; i++) {
			for (Player p : players) {
				p.getHand().addCard(mainDeck.drawCard());
			}
		}

		// Primera carta visible
		discardPile.addCard(mainDeck.drawCard());
		System.out.printf("Carta inicial de descarte: %s%s%s\n", Colors.BLUE, discardPile.getCards().get(0),Colors.RESET);
	}

	/**
	 * Gestiona el turno de un jugador humano.
	 * 
	 * @param player     el jugador activo.
	 * @param turnNumber número correlativo del turno en la ronda actual.
	 * @return {@code true} si el jugador decide y puede cerrar la ronda.
	 */
	private boolean playHumanTurn(Player player, int turnNumber) {
		boolean wantsToClose;
		boolean isFirstTurn;

		showPlayerHand(player);
		drawPhase(player);
		showPlayerHand(player);

		if (player.hasChinchon()) {
			System.out.printf("%s¡¡CHINCHÓN!! %s gana la partida!! %s\n", Colors.GREEN, player.getName(), Colors.RESET);
			chinchonWon = true;
			return true;
		}

		isFirstTurn = turnNumber <= players.size();

		if (!isFirstTurn && player.canClose(scoreLimit, isFirstTurn)) {

			System.out.printf("%s¿Deseas cerrar la ronda? (s/n): %s", Colors.PURPLE, Colors.RESET);
			wantsToClose = ci.readBooleanUsingChar('s', 'n');

			if (wantsToClose) {
				discardPhase(player);
				return true;
			}
		}

		discardPhase(player);
		return false;
	}

	/**
	 * Lógica automatizada para el turno de la IA.
	 * 
	 * @param ai         instancia de AiPlayer.
	 * @param turnNumber número del turno actual.
	 * @return {@code true} si la IA decide cerrar la ronda.
	 */
	private boolean playAiTurn(AiPlayer ai, int turnNumber) {
		int discardIndex;
		boolean closes;
		boolean isFirstTurn = turnNumber <= players.size();
		Card discarded;
		Card discardTop = discardPile.isEmpty() ? null : discardPile.getCards().get(discardPile.size() - 1);

		System.out.printf("La IA %s está pensando...\n", ai.getName());


		if (ai.shouldDrawFromDiscard(discardTop)) {
			ai.getHand().addCard(discardPile.removeCard(discardPile.size() - 1));

			System.out.printf("La IA %s roba del descarte: %s\n", ai.getName(), discardTop);
		} else {
			recycleDiscardIfNeeded();
			if (mainDeck.isEmpty()) {
				System.out.println("No quedan cartas para robar.");
				return false;
			}

			ai.getHand().addCard(mainDeck.drawCard());

			System.out.printf("La IA %s roba del mazo.\n", ai.getName());
		}


		if (ai.hasChinchon()) {
			System.out.printf("%s¡¡CHINCHÓN!! La IA %s gana la partida!! %s\n", Colors.GREEN, ai.getName(),
					Colors.RESET);
			chinchonWon = true;
			return true;
		}

		// DESCARTE DESPUÉS DE ROBAR
		discardIndex = ai.chooseCardToDiscard();
		discarded = ai.getHand().removeCard(discardIndex);
		closes = !isFirstTurn && ai.canClose(scoreLimit, isFirstTurn);

		if (closes) {
			System.out.printf("La IA %s decide cerrar la ronda.\n", ai.getName());
		}

		discardPile.addCard(discarded);
		System.out.printf("La IA %s descarta: %s\n", ai.getName(), discarded);
		return closes;
	}

	/**
	 * Ejecuta la fase de robo, permitiendo elegir entre mazo o descarte.
	 * 
	 * @param player jugador que realiza la acción.
	 */
	private void drawPhase(Player player) {
		Card discardTop = discardPile.isEmpty() ? null : discardPile.getCards().get(discardPile.size() - 1);
		char choice;

		if (discardTop != null) {
			System.out.printf("Carta visible del descarte: %s%s%s\n", Colors.BLUE, discardTop, Colors.RESET);
			System.out.print("¿De dónde robas? b = baraja / d = descarte: ");
			choice = ci.readChar();

			if (choice == 'd' || choice == 'D') {
				player.getHand().addCard(discardPile.removeCard(discardPile.size() - 1));

			} else {
				recycleDiscardIfNeeded();
				if (mainDeck.isEmpty()) {
					System.out.println("No quedan cartas.");
					return;
				}
				player.getHand().addCard(mainDeck.drawCard());
			}
		} else {
			recycleDiscardIfNeeded();
			if (mainDeck.isEmpty()) {
				System.out.println("No quedan cartas.");
				return;
			}
			player.getHand().addCard(mainDeck.drawCard());
		}
	}

	/**
	 * Ejecuta la fase de descarte de una carta de la mano del jugador.
	 * 
	 * * @param player jugador que realiza la acción.
	 */
	private void discardPhase(Player player) {
		int index;
		Card discarded;

		showPlayerHand(player);

		System.out.printf("¿Qué carta descartas? (1-%d): ", player.getHand().size());
		index = ci.readIntInRange(1, player.getHand().size()) - 1;
		discarded = player.getHand().removeCard(index);
		discardPile.addCard(discarded);

		System.out.printf("Has descartado: %s%s%s\n", Colors.RED, discarded, Colors.RESET);
	}

	/**
	 * Recicla el montón de descartes si el mazo principal se agota.
	 */
	private void recycleDiscardIfNeeded() {
		Card top;
		if (!mainDeck.isEmpty()) {
			return;
		}

		if (discardPile.size() <= 1) {
			return;
		}

		System.out.printf("%sEl mazo está vacío. Reciclando descarte...%s\n", Colors.CYAN, Colors.RESET);
		top = discardPile.removeCard(discardPile.size() - 1);

		while (!discardPile.isEmpty()) {
			mainDeck.addCard(discardPile.removeCard(0));
		}

		mainDeck.shuffleDeck();
		discardPile.addCard(top);
	}

	/**
	 * Finaliza la ronda, calcula puntos y aplica bonificaciones por cierre.
	 * 
	 * @param closingPlayerIndex índice del jugador que cerró la ronda.
	 */
	private void endRound(int closingPlayerIndex) {
		int points;
		Player p;
		HandEvaluator ev;

		System.out.printf("\n%sFin de ronda%s\n", Colors.CYAN, Colors.RESET);

		for (int i = 0; i < players.size(); i++) {
			p = players.get(i);
			points = p.calculateHandPoints();

			if (points < 0) {
				points = 0;
			}
			p.addPoints(points);


			if (i == closingPlayerIndex) {
				ev = new HandEvaluator(p.getHand().getCards());

				if (ev.countCombinedCards() == 7) {
					p.addPoints(-10);
					System.out.printf("%s cierra con 7 combinadas: -10 puntos extra.\n", p.getName());
				}
			}

			System.out.printf("%-10s → %d puntos (Total: %d)\n", p.getName(), points, p.getScore());
		}
	}

	/**
	 * Determina si la partida ha finalizado. * @return {@code true} si hay un
	 * ganador por Chinchón o solo queda un jugador.
	 */
	private boolean isGameOver() {
		if (chinchonWon) {
			return true;
		}
		return players.size() <= 1;
	}

	/**
	 * Muestra el ranking final y anuncia al ganador.
	 */
	private void showFinalResult() {
		List<Player> ranking;
		Optional<Player> winner;
		System.out.printf("\n%s     RESULTADO FINAL     %s\n", Colors.GREEN, Colors.RESET);

		if (players.isEmpty()) {
			System.out.println("No quedan jugadores.");
			return;
		}

		ranking = players.stream()
				.sorted(Comparator.comparingInt(Player::getScore))
				.collect(Collectors.toList());

		winner = ranking.stream().findFirst();

		for (Player p : ranking) {
			System.out.printf("%-10s -> %d puntos\n", p.getName(), p.getScore());
		}

		if (chinchonWon) {
			return;
		}
		winner.ifPresent(w -> System.out.printf("\n%sGANADOR: %s con %d puntos%s\n", Colors.GREEN, w.getName(),
				w.getScore(), Colors.RESET));
	}

	/**
	 * Imprime por consola la mano del jugador resaltando las combinaciones.
	 * 
	 * @param player el jugador cuya mano se va a mostrar.
	 */
	private void showPlayerHand(Player player) {
		HandEvaluator evaluator = new HandEvaluator(player.getHand().getCards());
		List<Card> combined = evaluator.findBestCombinedCards();
		List<Card> cards = player.getHand().getCards();

		System.out.printf("\nMano de %s:\n", player.getName());
		for (int i = 0; i < cards.size(); i++) {
			Card c = cards.get(i);

			if (combined.contains(c)) {
				System.out.printf("%s%d %s%s  ", Colors.GREEN, i + 1, c, Colors.RESET);
			} else {
				System.out.printf("%d %s  ", i + 1, c);
			}
		}

		System.out.printf("\n(Puntos estimados: %d)\n", evaluator.calculatePoints());
	}

	/**
	 * Clase interna para la construcción fluida de instancias de {@link Game}.
	 */
	public static class Builder {
		private List<Player> players;
		private Deck mainDeck;
		private ConsoleInput ci;
		private int scoreLimit = 100;

		public Builder players(List<Player> players) {
			this.players = players;
			return this;
		}

		public Builder deck(Deck deck) {
			this.mainDeck = deck;
			return this;
		}

		public Builder consoleInput(ConsoleInput ci) {
			this.ci = ci;
			return this;
		}

		public Builder scoreLimit(int scoreLimit) {
			this.scoreLimit = scoreLimit;
			return this;
		}

		public Game build() {
			return new Game(players, mainDeck, ci, scoreLimit);
		}
	}
}