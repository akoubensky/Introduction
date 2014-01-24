package org.ifmo;

import java.util.Arrays;

/**
 * ��������� ���� ������������ ��������� � �������.
 */
public class Permutations {
	/**
	 * �������� ������� ��������� ������������.
	 * @param arr �������� ������.
	 */
	public static void permutations(int[] arr) {
		permutations(arr, 0);
	}

	/**
	 * ����������� ������� ��������� ��������� ������������,
	 * ����������� �������� ������ �� ���������.
	 * @param arr ������, �������� �������� ������������ ������������.
	 * @param fixed �����, �������� ���������� ��������� "�������������"
	 *     ��������� �������. ������������ ������������ ������ ��������
	 *     � ���������, �������� fixed.
	 */
	private static void permutations(int[] arr, int fixed) {
		int n = arr.length;
		if (fixed == n) {
			// ��� �������� �����������, ������������ ������������
			// ��������� ������������.
			System.out.println(Arrays.toString(arr));
			return;
		}
		// ��������� ���������� ������������� ���������.
		// ����� ������� �� ����� � �������� fixed �� �������
		// ������ �� "���������������" ���������.
		permutations(arr, fixed+1);
		for (int i = fixed+1; i < n; ++i) {
			int elem = arr[fixed]; arr[fixed] = arr[i]; arr[i] = elem;
			permutations(arr, fixed+1);
		}
		// ����� ���� ���������, ��� ����� ������� ������ �������
		// permutations ��� �������� �������� �� ����� ������.
		// ������� ���������� ������������ �������������� ������� ���������.
		int elem = arr[n-1];
		for (int i = n-1; i > fixed; --i) {
			arr[i] = arr[i-1];
		}
		arr[fixed] = elem;
	}

	/**
	 * �������� ������� ���������� ������������ �������� ����� �����.
	 * @param args
	 */
	public static void main(String[] args) {
		// ������ ���������� ���������.
		int n = args.length;
		if (n == 0) {
			System.out.println("�� ������ ����� ��� ������������");
			return;
		}
		int[] array = new int[n];
		try {
			for (int i = 0; i < n; ++i) {
				array[i] = Integer.parseInt(args[i]);
			}
		} catch (NumberFormatException e) {
			System.out.println("������� ������ ��������� ���������: ������ ���� ����� �����");
			return;
		}

		// ���������� ��������� ������������.
		permutations(array);
	}

}
