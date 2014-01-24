package org.ifmo;

/**
 * ��������� ��� ���������� ������������ ������� ����������� ������
 * ��� ����������� ����� �������� ��� ������������ ������.
 */
public class OptMatrix {
  /**
   * ������� ��������� ����������� ����� �������� � ����� �����������
   * ����������� ������ ��� ������������ ������.
   * @param sizes ������� ������.
   *              ����� ������ n, ������ ������� A[i] ���������� sizes[i] x sizes[i+1]
   * @return ������� ������������� ����������� �������� ����������.
   *         ����������� ����� �������� ���������� � �������� m[0][n-1].
   *         ����� ����������� ������ ��������� � ������������ ���� ������� ���������.
   */
  public static int[][] opt(int[] sizes) {
    // ���������� ������:
    int n = sizes.length - 1;
    // ������� ��������� ��������� � ����� ����������� ������:
    int[][] m = new int[n][n];
    
    // ���� �� ���������� �� ������� (�� �������) �� ����� �������.
    // c - ����� ���������.
    for (int c = 1; c < n; ++c) {
      // ���� �� ��������� ��������� ����� c.
      for (int i = 0; i < n-c; ++i) {
        int j = i+c;
        // ��� i-�� �������� ��������� ������� � ������� ����� i � j.
        // ��������� ����������� ���������� �������� m[i][j] � ���������� �����,
        // ��� �������� ������: m[j][i] - ����� �������, ����� ������� ����� ��������� ������. 
        m[i][j] = Integer.MAX_VALUE;
        for (int k = i+1; k <= j; ++k) {
          int q = m[i][k-1] + m[k][j] + sizes[i]*sizes[k]*sizes[j+1];
          if (q < m[i][j]) {
            m[i][j] = q;
            m[j][i] = k;
          }
        }
      }
    }
    return m;
  }

  /**
   * "��������" ����� ���������� - ��������� ������������� ����������� ��������� ���������.
   * @param res - ����������� ������������� ���������.
   */
  public static void printResult(int[][] res) {
    int n = res.length - 1;
    System.out.println("����������� ����� �������� " + res[0][n] +
        " ����������� ��� ��������� ����������� ������:");
    System.out.println(brackets(res, 0, n));
  }

  /**
   * ������������� ����������� ������ � ���� ������.
   * @param res - ������� �������������� ����������.
   * @param from - ����� ������ �������.
   * @param to - ����� ��������� �������.
   * @return - ������, �������������� ����������� ����������� ������.
   */
  private static String brackets(int[][] res, int from, int to) {
    if (from == to) return "A" + from;
    int point = res[to][from];
    return "(" + brackets(res, from, point-1) + "x" + brackets(res, point, to) + ")";
  }
  
  /**
   * @param args - ������� ������������� ������ - ����� ����� � ����������
   * �� ������� �������, ��� ����� ������.
   */
  public static void main(String[] args) {
    int[] sizes = new int[] { 3, 10, 2, 5, 12, 6 }; // ���� "�� ���������".
    if (args.length > 0) {
      try {
        int n = args.length;
        sizes = new int[n];
        for (int i = 0; i < n; ++i) {
          sizes[i] = Integer.parseInt(args[i]);
        }
      } catch (NumberFormatException x) {
        System.out.println("������� ������ ������� ������");
      }
    }
    printResult(opt(sizes));
  }

}
