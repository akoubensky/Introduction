package org.ifmo;

import java.util.Arrays;

/**
 * ���������� "������������� �������" � ������������
 * ���������� � �������� ��������� �� ���� ������.
 */
public class IntArray {
	private int[] array;  // ������ ��� �������� ��������� "� �������"
	private int length;   // ���������� ���������

	/**
	 * ����������� ������� � �������� ��������� ����������� ����������������� ������.
	 * ���������� ��������� �������� ��������� - 10, ���� ������ ������� ����������,
	 * �� ������������� 10 ��������� � ������.
	 * 
	 * @param length	��������� ������ ������.
	 */
	public IntArray(int length) {
		array = new int[Math.max(length, 10)];
		this.length = length;
	}

	/**
	 * ����������� "�� ���������", ������������� ����������� ���������� ������.
	 */
	public IntArray() { this(0); }

	/**
	 * ����� ������� - ���������� ��������� � ���.
	 * 
	 * @return	����� ����������� ��������� �������
	 */
	public int length() { return length; }

	/**
	 * ���������� ������� ��� ������� ��������.
	 * 
	 * @param index	������ �� �������
	 * @return		������� � �������� ��������
	 */
	public int get(int index) {
		if (index < 0 || index >= length)
			throw new IndexOutOfBoundsException("Illegal index: " + index);
		return array[index];
	}

	/**
	 * ���������� ������� ��� ������ ��������.
	 * 
	 * @param index	������ �� �������
	 * @param elem	�������, ���������� ������
	 */
	public void set(int index, int elem) {
		if (index < 0 || index >= length)
			throw new IndexOutOfBoundsException("Illegal index: " + index);
		array[index] = elem;
	}

	/**
	 * ���������� �������� � ����� �������.
	 * 
	 * @param newElem	����������� �������
	 */
	public void add(int newElem) {
		enlarge();				// ����������� ����� ���������,
								// ��� ������������� �������������� ������
		set(length-1, newElem);	// ���������� ������� � ����� �������
	}

	/**
	 * �������� ��������� ����� ��������� �� ����� �������.
	 * 
	 * @param delta	���������� ��������� ���������. ���� ��� ����� ���������
	 * 				����� ��������� �������, �� ��������� ��� ��������
	 */
	public void remove(int delta) {
		delta = Math.min(length, Math.max(delta, 0));
		length -= delta;
	}

	/**
	 * ��������������� �������, ����������� ������ ��� �������������.
	 */
	private void enlarge() {
		if (++length > array.length) {
			array = Arrays.copyOf(array, 2 * array.length);
		}
	}

	/**
	 * ����������� ���������
	 * @param args	�� ������������.
	 */
	public static void main(String[] args) {
		// ������� ������
		IntArray array = new IntArray();
		// ���������� 100 ���������
		for (int i = 1; i <= 100; ++i) {
			array.add(2*i);
		}
		// ������� 10 ���������
		array.remove(10);
		// ������ ��� ���������� 10 ������ ���������
		for (int i = 1; i <= 10; ++i) {
			array.add(10*i);
		}
		// ������� �� ������� ��� ��������
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
