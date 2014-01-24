package org.ifmo;

/**
 * ���������� n-�� ����� ��������� � ������� ������ �������� (������ ������������!)
 * � ������� "������������� ����������������".
 */
public class DynFibonacci {
	/**
	 * ����������� (����� �������������) �������.
	 * @param n ����� ����� ���������.
	 * @return n-� ����� ���������.
	 */
	public static int fibRec(int n) {
		return n <= 2 ? 1 : fibRec(n-1) + fibRec(n-2);
	}

	/**
	 * ����� ����������� �������, ������������ ��������������� ����������
	 * ��� �������� ������������� ����������� ����������.
	 * @param n ����� ����� ���������.
	 * @return n-� ����� ���������.
	 */
	public static int fib(int n) {
		int f0 = 1, f1 = 1;
		for (int i = 2; i < n; ++i) {
			int f = f0 + f1; f0 = f1; f1 = f;
		}
		return f1;
	}

	/**
	 * ���������� n-�� ����� ���������. �������� ��������� ����� ������
	 * � �������� ������ �� ���������� ���������.
	 * @param args
	 */
	public static void main(String[] args) {
		int n = 45;  // �������� "�� ���������"
		if (args.length >= 1) {
			try {
				n = Integer.parseInt(args[0]);
			} catch (NumberFormatException x) {
				System.out.println("����������� ����� �������� (����� ���������): " + args[0]);
			}
		}
		System.out.println("����� ��������� ������������: F(" + n + ") = " + fib(n));
		System.out.println("����� ��������� ����������: F(" + n + ") = " + fibRec(n));
	}

}
