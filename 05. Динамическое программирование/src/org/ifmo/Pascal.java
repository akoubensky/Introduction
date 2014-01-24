package org.ifmo;

/**
 * ���������� ����� ��������� �� m �� n ������� ������������� ����������������.
 */
public class Pascal {
  /**
   * ���������� ����� ��������� C(m, n).
   * @param m
   * @param n
   * @return
   */
  public static int comb(int m, int n) {
    // �������� ������� ����������: n <= m/2
    if (m < n) { int c = m; m = n; n = c; }
    if (n > m/2) { n = m - n; }
    // ���������� ���������������� ����� ������������ �������.
    int[] c = new int[n+1];
    c[n] = 1;
    for (int j = 0; j < m; ++j) {
      for (int i = 0; i < n; i++) {
        c[i] += c[i+1];
      }
    }
    return c[0];
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    int m = 10, n = 4;
    if (args.length == 2) {
      // ��������� ������ ����� m � n.
      try {
        m = Integer.parseInt(args[0]);
        n = Integer.parseInt(args[1]);
      } catch (NumberFormatException x) {
        System.out.println("��������� " + args[0] + " � " + args[1] + " ������ �������");
      }
    }
    System.out.println("����� ��������� �� " + m + " �� " + n + " ����� " + comb(m, n));
  }

}
