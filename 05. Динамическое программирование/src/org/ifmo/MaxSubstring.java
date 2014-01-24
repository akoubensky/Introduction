package org.ifmo;

/**
 * Нахождение максимальной общей подпоследовательности двух последовательностей
 * (на примере строк символов).
 */
public class MaxSubstring {
	/**
	 * Функция порождает матрицу максимальных длин общих подпоследовательностей
	 * для префиксов заданных строк.
	 * @param a Первая строка.
	 * @param b Вторая строка.
	 * @return Матрица максимальных длин.
	 */
	static int[][] getMaxMatrix(String a, String b) {
		int n = a.length();  // Длина первой строки.
		int m = b.length();  // Длина второй строки.
		int[][] matrix = new int[n+1][m+1];  // Матрица максимальных длин.

		// Матрица заполняется по строкам.
		for (int i = 1; i <= n; ++i) {
			for (int j = 1; j <= m; ++j) {
				if (a.charAt(i-1) == b.charAt(j-1)) {
					// Делаем шаг "по диагонали".
					matrix[i][j] = matrix[i-1][j-1] + 1;
				} else {
					// Делаем шаг "по горизонтали" или "по вертикали".
					matrix[i][j] = Math.max(matrix[i-1][j], matrix[i][j-1]);
				}
			}
		}
		return matrix;
	}

	/**
	 * Функция вычисления максимальной общей подпоследовательности символов.
	 * Использует вычисление матрицы длин, а затем анализирует ее для получения результата.
	 * @param a Первая строка.
	 * @param b Вторая строка.
	 * @return Максимальная общая подпоследовательность.
	 */
	static String getMaxSubstring1(String a, String b) {
		// Вычисляем матрицу максимальных длин.
		int[][] matrix = getMaxMatrix(a, b);
		// В буфере buf накапливается общая подпоследовательность (в обратном порядке).
		StringBuffer buf = new StringBuffer();
		// Начинаем с правого нижнего угла матрицы.
		int i = a.length(), j = b.length();
		while (matrix[i][j] != 0) {
			if (a.charAt(i-1) == b.charAt(j-1)) {
				// Был сделан шаг "по диагонали"
				buf.append(a.charAt(i-1));
				i--; j--;
			} else if (matrix[i][j-1] < matrix[i-1][j]) {
				// Возможно, был шаг "по вертикали".
				// Если возможен также и шаг "по горизонтали", то выберем "по горизонтали".
				i--;
			} else {
				// Делаем шаг "по горизонтали".
				j--;
			}
		}
		// Переворачиваем результат перед выдачей.
		return buf.reverse().toString();
	}

	/**
	 * Более эффективная по памяти функция вычисления максимальной общей
	 * подпоследовательности символов. Хранится только последняя строка матрицы
	 * максимальных длин, дополнительно хранятся битовые шкалы, показывающие, как
	 * изменялась матрица от строки к строке. На обратном ходе элементы матрицы
	 * длин восстанавливаются, при этом опять хранится только текущая строка.
	 * @param a Первая строка.
	 * @param b Вторая строка.
	 * @return Максимальная общая подпоследовательность символов.
	 */
	static String getMaxSubstring2(String a, String b) {
		final byte WORD_LEN = 32;  // Длина слова (целое).
		final byte WORD_LEN_LOG = 5;  // Логарифм длины слова.
		int n = a.length(), m = b.length();  // Длины исходных строк
		int[] maxLen = new int[m+1];  // Текущая строка матрицы максимальных длин. 
		int[][] delta = new int[n][(m + WORD_LEN) >> WORD_LEN_LOG];  // Матрица изменений.

		// 1. Построение "матрицы длин" и матрицы изменений.
		for (int i = 1; i <= n; ++i) {
			for (int j = 1; j <= m; ++j) {
				// Запоминаем текущий элемент для вычисления изменения.
				int prev = maxLen[j];
				if (a.charAt(i-1) == b.charAt(j-1)) {
					// Делаем шаг "по диагонали".
					maxLen[j] = maxLen[j-1] + 1;
				} else if (maxLen[j-1] > prev) {
					// Делаем шаг "по вертикали" или "по горизонтали".
					maxLen[j] = maxLen[j-1];
				}
				// Записываем "бит изменения" в матрицу, используя битовую арифметику.
				if (maxLen[j] > prev) {
					delta[i-1][j >> WORD_LEN_LOG] |= 1 << (j & (WORD_LEN - 1));
				} else {
					delta[i-1][j >> WORD_LEN_LOG] &= ~(1 << (j & (WORD_LEN - 1)));
				}
			}
		}

		// 2. Обратный ход и вычисление результата.
		StringBuffer result = new StringBuffer();
		int i = n, j = m;  // Начинаем с правого нижнего угла "матрицы"
		while (maxLen[j] != 0) {
			boolean up = true;  // Признак "шага по вертикали" - надо перевычислить предыдущую строку матрицы.
			if (a.charAt(i-1) == b.charAt(j-1)) {
				// Шаг "по диагонали".
				result.append(a.charAt(i-1));
				j--;
			} else if (maxLen[j-1] >= maxLen[j]) {
				// Шаг "по горизонтали".
				up = false;
				j--;
			}
			// Перевычисление части строки матрицы длин, если необходимо.
			if (up) {
				i--;
				for (int k = j; k >= 0; --k) {
					if ((delta[i][k >> WORD_LEN_LOG] & (1 << (k & (WORD_LEN - 1)))) != 0) {
						// Было изменение в элементе матрицы.
						maxLen[k]--;
					}
				}
			}
		}
		// Переворачиваем результат перед выдачей.
		return result.reverse().toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] strings = {
			"КАЗАКИ ДЕЛИЛИ ПРИБЫЛЬ",
			"ДУРАКИ ПИЛИЛИ КРЫЛЬЯ"
		};
		if (args.length > 0) {
			strings = args;
		}
		for (int i = 0; i < strings.length; i+=2) {
			String a = strings[i];
			String b = strings[i+1];
			System.out.format("Исходные строки: [%s] и [%s].\n", a, b);
			System.out.format("Результат, полученный первым способом: [%s]\n", getMaxSubstring1(a, b));
			System.out.format("Результат, полученный вторым способом: [%s]\n", getMaxSubstring2(a, b));
		}
	}

} 
