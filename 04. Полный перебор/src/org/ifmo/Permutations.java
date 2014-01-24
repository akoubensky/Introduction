package org.ifmo;

import java.util.Arrays;

/**
 * Генерация всех перестановок элементов в массиве.
 */
public class Permutations {
	/**
	 * Основная функция генерации перестановок.
	 * @param arr Исходный массив.
	 */
	public static void permutations(int[] arr) {
		permutations(arr, 0);
	}

	/**
	 * Рекурсивная функция частичной генерации перестановок,
	 * выполняющая основную работу по генерации.
	 * @param arr Массив, элементы которого подвергаются перестановке.
	 * @param fixed Число, задающее количество начальных "фиксированных"
	 *     элементов массива. Перестановке подвергаются только элементы
	 *     с индексами, большими fixed.
	 */
	private static void permutations(int[] arr, int fixed) {
		int n = arr.length;
		if (fixed == n) {
			// Все элементы фиксированы, обрабатываем единственную
			// возможную перестановку.
			System.out.println(Arrays.toString(arr));
			return;
		}
		// Расширяем количество фиксированных элементов.
		// Будем ставить на место с индексом fixed по очереди
		// каждый из "нефиксированных" элементов.
		permutations(arr, fixed+1);
		for (int i = fixed+1; i < n; ++i) {
			int elem = arr[fixed]; arr[fixed] = arr[i]; arr[i] = elem;
			permutations(arr, fixed+1);
		}
		// Нужно быть уверенным, что после каждого вызова функции
		// permutations все элементы остались на своих местах.
		// Поэтому необходимо восстановить первоначальный порядок элементов.
		int elem = arr[n-1];
		for (int i = n-1; i > fixed; --i) {
			arr[i] = arr[i-1];
		}
		arr[fixed] = elem;
	}

	/**
	 * Тестовая функция генерирует перестановки заданных целых чисел.
	 * @param args
	 */
	public static void main(String[] args) {
		// Чтение аргументов программы.
		int n = args.length;
		if (n == 0) {
			System.out.println("Не заданы числа для перестановки");
			return;
		}
		int[] array = new int[n];
		try {
			for (int i = 0; i < n; ++i) {
				array[i] = Integer.parseInt(args[i]);
			}
		} catch (NumberFormatException e) {
			System.out.println("Неверно заданы аргументы программы: должны быть целые числа");
			return;
		}

		// Собственно генерация перестановок.
		permutations(array);
	}

}
