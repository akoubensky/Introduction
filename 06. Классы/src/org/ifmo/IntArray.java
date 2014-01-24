package org.ifmo;

import java.util.Arrays;

/**
 * Реализация "динамического массива" с возможностью
 * добавления и удаления элементов по ходу работы.
 */
public class IntArray {
	private int[] array;  // массив для хранения элементов "с запасом"
	private int length;   // количество элементов

	/**
	 * Конструктор массива с заданным начальным количеством зарезервированной памяти.
	 * Минимально возможное значение аргумента - 10, если задано меньшее количество,
	 * то резервируется 10 элементов в памяти.
	 * 
	 * @param length	Начальный размер памяти.
	 */
	public IntArray(int length) {
		array = new int[Math.max(length, 10)];
		this.length = length;
	}

	/**
	 * Конструктор "по умолчанию", резервируется минимальное количество памяти.
	 */
	public IntArray() { this(0); }

	/**
	 * Длина массива - количество элементов в нем.
	 * 
	 * @return	Число заполненных элементов массива
	 */
	public int length() { return length; }

	/**
	 * Индексация массива для выборки элемента.
	 * 
	 * @param index	Индекс по массиву
	 * @return		Элемент с заданным индексом
	 */
	public int get(int index) {
		if (index < 0 || index >= length)
			throw new IndexOutOfBoundsException("Illegal index: " + index);
		return array[index];
	}

	/**
	 * Индексация массива для записи элемента.
	 * 
	 * @param index	Индекс по массиву
	 * @param elem	Элемент, подлежащий записи
	 */
	public void set(int index, int elem) {
		if (index < 0 || index >= length)
			throw new IndexOutOfBoundsException("Illegal index: " + index);
		array[index] = elem;
	}

	/**
	 * Добавление элемента в конец массива.
	 * 
	 * @param newElem	Добавляемый элемент
	 */
	public void add(int newElem) {
		enlarge();				// Увеличиваем число элементов,
								// при необходимости перезаказываем память
		set(length-1, newElem);	// Записываем элемент в конец массива
	}

	/**
	 * Удаление заданного числа элементов из конца массива.
	 * 
	 * @param delta	Количество удаляемых элементов. Если это число превышает
	 * 				число элементов массива, то удаляются все элементы
	 */
	public void remove(int delta) {
		delta = Math.min(length, Math.max(delta, 0));
		length -= delta;
	}

	/**
	 * Вспомогательная функция, расширяющая массив при необходимости.
	 */
	private void enlarge() {
		if (++length > array.length) {
			array = Arrays.copyOf(array, 2 * array.length);
		}
	}

	/**
	 * Тестирующая процедура
	 * @param args	Не используется.
	 */
	public static void main(String[] args) {
		// Заводим массив
		IntArray array = new IntArray();
		// Записываем 100 элементов
		for (int i = 1; i <= 100; ++i) {
			array.add(2*i);
		}
		// Удаляем 10 элементов
		array.remove(10);
		// Вместо них записываем 10 других элементов
		for (int i = 1; i <= 10; ++i) {
			array.add(10*i);
		}
		// Выводим на консоль все элементы
		try {
			for (int i = 0; ; ++i) {
				System.out.print(" " + array.get(i));
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println();
			System.out.println(e.getMessage());
		}
	}
}
