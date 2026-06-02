package chinchon.app;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInput {
	private Scanner sc;
	int value = 0;

	private static ConsoleInput instance;

	public static ConsoleInput getInstance() {
		if (instance == null) {
			instance = new ConsoleInput(new Scanner(System.in));
		}
		return instance;
	}

	private ConsoleInput(Scanner keyboard) {
		this.sc = keyboard;
	}


	// Limpia el buffer de entrada
	private void cleanInput() {
		sc.nextLine();
	}

	// Lee un entero del usuario
	public int readInt() {
		boolean isInteger = false;

		do {
			try {
				// System.out.print("Introduzca número: ");
				value = sc.nextInt();
				isInteger = true;
			} catch (InputMismatchException e) {
				System.err.println("Error... El número introduzido es incorrecto.\n");
			} finally {
				cleanInput();
			}
		} while (!isInteger);

		return value;
	}

	// Lee un entero menor que upperBound
	public int readIntLessThan(int upperBound) {

		do {
			value = readInt();
			if (value >= upperBound) {
				System.err.printf("Error... El número debe ser menor que  %d\n ", upperBound);
			}
		} while (value >= upperBound);

		return value;
	}

	// Lee un entero menor o igual a upperBound
	public int readIntLessOrEqualThan(int upperBound) {

		do {
			value = readInt();
			if (value > upperBound) {
				System.err.printf("Error... El número introducido debe de ser menor que %d\n", upperBound);
			}
		} while (value > upperBound);

		return value;
	}

	// Lee un entero mayor que lowerBound
	public int readIntGreaterThan(int lowerBound) {
		do {
			value = readInt();
			if (value <= lowerBound) {
				System.err.printf("Error... El número introducido debe ser mayor que %d\n", lowerBound);
			}
		} while (value <= lowerBound);

		return value;
	}

	// Lee un entero menor que lowerBound
	public int readIntGreaterOrEqualThan(int lowerBound) {
		do {
			value = readInt();
			if (value < lowerBound) {
				System.err.printf("Error... El número introducido debe ser mayor que  %d\n", lowerBound);
			}
		} while (value < lowerBound);

		return value;
	}

	// Lee un entero dentro de un rango [lowerBound, upperBound]
	public int readIntInRange(int lowerBound, int upperBound) {

		if (lowerBound > upperBound) {
			throw new IllegalArgumentException("\nEl limite inferior no puede ser mayor a el limite superior.");
		}

		do {
			value = readInt();
			if (value < lowerBound || value > upperBound) {
				System.err.printf("Error... El número debe estar entre %d y %d\n", lowerBound, upperBound);
			}
		} while (value < lowerBound || value > upperBound);

		return value;
	}

	// Lee un double del usuario
	public double readDouble() {
		boolean isDouble = false;
		double doubleValue = 0;

		do {
			try {
				System.out.print("Introduzca número (decimal): ");
				doubleValue = sc.nextDouble();
				isDouble = true;
			} catch (InputMismatchException e) {
				System.err.println("Error... El número introducido es incorrecto.\n");
			} finally {
				cleanInput(); // Limpia el buffer tanto si hay error como si no
			}
		} while (!isDouble);

		return doubleValue;
	}

	// Lee un double menor que upperBound
	public double readDoubleLessThan(double upperBound) {
		double doubleValue;
		do {
			doubleValue = readDouble();
			if (doubleValue >= upperBound) {
				System.err.printf("Error... El número debe ser menor que %.2f\n", upperBound);
			}
		} while (doubleValue >= upperBound);

		return doubleValue;
	}

	// Lee un double menor o igual a upperBound
	public double readDoubleLessOrEqualThan(double upperBound) {
		double doubleValue;
		do {
			doubleValue = readDouble();
			if (doubleValue > upperBound) {
				System.err.printf("Error... El número introducido debe ser menor o igual que %.2f\n", upperBound);
			}
		} while (doubleValue > upperBound);

		return doubleValue;
	}

	// Lee un double mayor que lowerBound
	public double readDoubleGreaterThan(double lowerBound) {
		double doubleValue;
		do {
			doubleValue = readDouble();
			if (doubleValue <= lowerBound) {
				System.err.printf("Error... El número introducido debe ser mayor que %.2f\n", lowerBound);
			}
		} while (doubleValue <= lowerBound);

		return doubleValue;
	}

	// Lee un double mayor o igual a lowerBound
	public double readDoubleGreaterOrEqualThan(double lowerBound) {
		double doubleValue;
		do {
			doubleValue = readDouble();
			if (doubleValue < lowerBound) {
				System.err.printf("Error... El número introducido debe ser mayor o igual que %.2f\n", lowerBound);
			}
		} while (doubleValue < lowerBound);

		return doubleValue;
	}

	// Lee un double dentro de un rango [lowerBound, upperBound]
	public double readDoubleInRange(double lowerBound, double upperBound) {
		if (lowerBound > upperBound) {
			throw new IllegalArgumentException("\nEl límite inferior no puede ser mayor al límite superior.");
		}

		double doubleValue;
		do {
			doubleValue = readDouble();
			if (doubleValue < lowerBound || doubleValue > upperBound) {
				System.err.printf("Error... El número debe estar entre %.2f y %.2f\n", lowerBound, upperBound);
			}
		} while (doubleValue < lowerBound || doubleValue > upperBound);

		return doubleValue;
	}

	// Metodo para valores 'Char'
	public char readChar() {
		String chain = "";
		char value = ' ';

		do {
			System.out.print("\nIntroduzca un caracter: ");
			chain = sc.nextLine();

			if (chain.isEmpty()) {
				System.err.println("Error... El caracter no puede estar vacio.\n");
			} else if (chain.length() != 1) {
				System.err.println("Error... Se debe introducir un único caracter.\n");
			} else {
				value = chain.charAt(0);
			}

		} while (chain.isEmpty() || chain.length() != 1);

		return value;
	}

	// Metodo para valores 'String'
	public String readString() {
		String value;

	// System.out.print("Introduzca una cadena: ");
		value = sc.nextLine();

		return value;
	}

	// Metodo sobrecargado para introducir un mensaje especifico
	public String readString(String message) {
		String value;

		System.out.print(message);
		value = sc.nextLine();

		return value;
	}

	public String readString(int maxLength) {
		String value;

		if (maxLength <= 0) {
			throw new IllegalArgumentException("El número maximo de la cadena no debe ser ni cero ni negativo.\n");
		}

		do {
			value = readString();
		} while (value.length() > maxLength);

		return value;
	}

	// Metodo para valores 'boolean'
	public boolean readBooleanUsingChar(char affirmativeValue, char negativeValue) {
		char c1, c2, c3;
		boolean value = false;
		c2 = Character.toLowerCase(affirmativeValue);
		c3 = Character.toLowerCase(negativeValue);

		if (c2 == c3) {
			throw new IllegalArgumentException("Error... Los valores deben ser distintos entre ellos.\n");
		}

		do {
			c1 = readChar();
			c1 = Character.toLowerCase(c1);

			if (c1 == c2) {
				value = true;
			} else if (c1 == c3) {
				value = false;
			} else {
				System.err.printf("\nError... Debes de introducir el caracter %c ó %c\n", affirmativeValue,
						negativeValue);
				value = false;
			}
		} while (c1 != c2 && c1 != c3);

		return value;
	}
}
