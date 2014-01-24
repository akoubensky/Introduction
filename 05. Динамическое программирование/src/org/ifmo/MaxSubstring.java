package org.ifmo;

/**
 * ���������� ������������ ����� ��������������������� ���� �������������������
 * (�� ������� ����� ��������).
 */
public class MaxSubstring {
	/**
	 * ������� ��������� ������� ������������ ���� ����� ����������������������
	 * ��� ��������� �������� �����.
	 * @param a ������ ������.
	 * @param b ������ ������.
	 * @return ������� ������������ ����.
	 */
	static int[][] getMaxMatrix(String a, String b) {
		int n = a.length();  // ����� ������ ������.
		int m = b.length();  // ����� ������ ������.
		int[][] matrix = new int[n+1][m+1];  // ������� ������������ ����.

		// ������� ����������� �� �������.
		for (int i = 1; i <= n; ++i) {
			for (int j = 1; j <= m; ++j) {
				if (a.charAt(i-1) == b.charAt(j-1)) {
					// ������ ��� "�� ���������".
					matrix[i][j] = matrix[i-1][j-1] + 1;
				} else {
					// ������ ��� "�� �����������" ��� "�� ���������".
					matrix[i][j] = Math.max(matrix[i-1][j], matrix[i][j-1]);
				}
			}
		}
		return matrix;
	}

	/**
	 * ������� ���������� ������������ ����� ��������������������� ��������.
	 * ���������� ���������� ������� ����, � ����� ����������� �� ��� ��������� ����������.
	 * @param a ������ ������.
	 * @param b ������ ������.
	 * @return ������������ ����� ���������������������.
	 */
	static String getMaxSubstring1(String a, String b) {
		// ��������� ������� ������������ ����.
		int[][] matrix = getMaxMatrix(a, b);
		// � ������ buf ������������� ����� ��������������������� (� �������� �������).
		StringBuffer buf = new StringBuffer();
		// �������� � ������� ������� ���� �������.
		int i = a.length(), j = b.length();
		while (matrix[i][j] != 0) {
			if (a.charAt(i-1) == b.charAt(j-1)) {
				// ��� ������ ��� "�� ���������"
				buf.append(a.charAt(i-1));
				i--; j--;
			} else if (matrix[i][j-1] < matrix[i-1][j]) {
				// ��������, ��� ��� "�� ���������".
				// ���� �������� ����� � ��� "�� �����������", �� ������� "�� �����������".
				i--;
			} else {
				// ������ ��� "�� �����������".
				j--;
			}
		}
		// �������������� ��������� ����� �������.
		return buf.reverse().toString();
	}

	/**
	 * ����� ����������� �� ������ ������� ���������� ������������ �����
	 * ��������������������� ��������. �������� ������ ��������� ������ �������
	 * ������������ ����, ������������� �������� ������� �����, ������������, ���
	 * ���������� ������� �� ������ � ������. �� �������� ���� �������� �������
	 * ���� �����������������, ��� ���� ����� �������� ������ ������� ������.
	 * @param a ������ ������.
	 * @param b ������ ������.
	 * @return ������������ ����� ��������������������� ��������.
	 */
	static String getMaxSubstring2(String a, String b) {
		final byte WORD_LEN = 32;  // ����� ����� (�����).
		final byte WORD_LEN_LOG = 5;  // �������� ����� �����.
		int n = a.length(), m = b.length();  // ����� �������� �����
		int[] maxLen = new int[m+1];  // ������� ������ ������� ������������ ����. 
		int[][] delta = new int[n][(m + WORD_LEN) >> WORD_LEN_LOG];  // ������� ���������.

		// 1. ���������� "������� ����" � ������� ���������.
		for (int i = 1; i <= n; ++i) {
			for (int j = 1; j <= m; ++j) {
				// ���������� ������� ������� ��� ���������� ���������.
				int prev = maxLen[j];
				if (a.charAt(i-1) == b.charAt(j-1)) {
					// ������ ��� "�� ���������".
					maxLen[j] = maxLen[j-1] + 1;
				} else if (maxLen[j-1] > prev) {
					// ������ ��� "�� ���������" ��� "�� �����������".
					maxLen[j] = maxLen[j-1];
				}
				// ���������� "��� ���������" � �������, ��������� ������� ����������.
				if (maxLen[j] > prev) {
					delta[i-1][j >> WORD_LEN_LOG] |= 1 << (j & (WORD_LEN - 1));
				} else {
					delta[i-1][j >> WORD_LEN_LOG] &= ~(1 << (j & (WORD_LEN - 1)));
				}
			}
		}

		// 2. �������� ��� � ���������� ����������.
		StringBuffer result = new StringBuffer();
		int i = n, j = m;  // �������� � ������� ������� ���� "�������"
		while (maxLen[j] != 0) {
			boolean up = true;  // ������� "���� �� ���������" - ���� ������������� ���������� ������ �������.
			if (a.charAt(i-1) == b.charAt(j-1)) {
				// ��� "�� ���������".
				result.append(a.charAt(i-1));
				j--;
			} else if (maxLen[j-1] >= maxLen[j]) {
				// ��� "�� �����������".
				up = false;
				j--;
			}
			// �������������� ����� ������ ������� ����, ���� ����������.
			if (up) {
				i--;
				for (int k = j; k >= 0; --k) {
					if ((delta[i][k >> WORD_LEN_LOG] & (1 << (k & (WORD_LEN - 1)))) != 0) {
						// ���� ��������� � �������� �������.
						maxLen[k]--;
					}
				}
			}
		}
		// �������������� ��������� ����� �������.
		return result.reverse().toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] strings = {
			"������ ������ �������",
			"������ ������ ������"
		};
		if (args.length > 0) {
			strings = args;
		}
		for (int i = 0; i < strings.length; i+=2) {
			String a = strings[i];
			String b = strings[i+1];
			System.out.format("�������� ������: [%s] � [%s].\n", a, b);
			System.out.format("���������, ���������� ������ ��������: [%s]\n", getMaxSubstring1(a, b));
			System.out.format("���������, ���������� ������ ��������: [%s]\n", getMaxSubstring2(a, b));
		}
	}

} 
