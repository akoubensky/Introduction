package org.ifmo;

/**
 * Вычисление числа сочетаний из m по n методом динамического программирования.
 */
public class Pascal {
  /**
   * Вычисление числа сочетаний C(m, n).
   * @param m
   * @param n
   * @return
   */
  public static int comb(int m, int n) {
    // Проверка задания аргументов: n <= m/2
    if (m < n) { int c = m; m = n; n = c; }
    if (n > m/2) { n = m - n; }
    // Вычисления последовательных строк треугольника Паскаля.
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
      // Аргументы задают числа m и n.
      try {
        m = Integer.parseInt(args[0]);
        n = Integer.parseInt(args[1]);
      } catch (NumberFormatException x) {
        System.out.println("Аргументы " + args[0] + " и " + args[1] + " заданы неверно");
      }
    }
    System.out.println("Число сочетаний из " + m + " по " + n + " равно " + comb(m, n));
  }

}
