package org.ifmo;

/**
 * Вычисление n-го числа Фибоначчи с помощью прямой рекурсии (крайне неэффективно!)
 * и методом "динамического программирования".
 */
public class DynFibonacci {
	/**
	 * Рекурсивное (очень неэффективное) решение.
	 * @param n номер числа Фибоначчи.
	 * @return n-е число Фибоначчи.
	 */
	public static int fibRec(int n) {
		return n <= 2 ? 1 : fibRec(n-1) + fibRec(n-2);
	}

	/**
	 * Более эффективное решение, использующее вспомогательные переменные
	 * для хранения промежуточных результатов вычислений.
	 * @param n номер числа Фибоначчи.
	 * @return n-е число Фибоначчи.
	 */
	public static int fib(int n) {
		int f0 = 1, f1 = 1;
		for (int i = 2; i < n; ++i) {
			int f = f0 + f1; f0 = f1; f1 = f;
		}
		return f1;
	}

	/**
	 * Нахождение n-го числа Фибоначчи. Значение параметра можно задать
	 * в качестве одного из аргументов программы.
	 * @param args
	 */
	public static void main(String[] args) {
		int n = 45;  // Значение "по умолчанию"
		if (args.length >= 1) {
			try {
				n = Integer.parseInt(args[0]);
			} catch (NumberFormatException x) {
				System.out.println("Неправильно задан аргумент (число Фибоначчи): " + args[0]);
			}
		}
		System.out.println("Число Фибоначчи нерекурсивно: F(" + n + ") = " + fib(n));
		System.out.println("Число Фибоначчи рекурсивно: F(" + n + ") = " + fibRec(n));
	}

}
