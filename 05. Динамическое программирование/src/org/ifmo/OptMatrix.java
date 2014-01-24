package org.ifmo;

/**
 * ѕрограмма дл€ вычислени€ оптимального способа расстановки скобок
 * дл€ минимизации числа операций при перемножении матриц.
 */
public class OptMatrix {
  /**
   * ‘ункци€ вычисл€ет оптимальное число операций и точки оптимальной
   * расстановки скобок дл€ перемножени€ матриц.
   * @param sizes –азмеры матриц.
   *              ¬сего матриц n, размер матрицы A[i] составл€ет sizes[i] x sizes[i+1]
   * @return ћатрица промежуточных результатов процесса вычислений.
   *         ќптимальное число операций содержитс€ в элементе m[0][n-1].
   *         “очки расстановки скобок наход€тс€ в треугольнике ниже главной диагонали.
   */
  public static int[][] opt(int[] sizes) {
    //  оличество матриц:
    int n = sizes.length - 1;
    // ћатрица количеств умножений и точек расстановки скобок:
    int[][] m = new int[n][n];
    
    // ÷икл по диагонал€м от главной (не включа€) до углов матрицы.
    // c - номер диагонали.
    for (int c = 1; c < n; ++c) {
      // ÷икл по элементам диагонали номер c.
      for (int i = 0; i < n-c; ++i) {
        int j = i+c;
        // ƒл€ i-го элемента диагонали индексы в матрице будут i и j.
        // ¬ычисл€ем оптимальное количество операций m[i][j] и запоминаем точку,
        // где став€тс€ скобки: m[j][i] - номер матрицы, перед которой будут вставлены скобки. 
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
   * " расивый" вывод результата - нагл€дное представление оптимальной скобочной структуры.
   * @param res - вычисленный промежуточный результат.
   */
  public static void printResult(int[][] res) {
    int n = res.length - 1;
    System.out.println("ќптимальное число операций " + res[0][n] +
        " достигаетс€ при следующей расстановке скобок:");
    System.out.println(brackets(res, 0, n));
  }

  /**
   * ѕредставление расстановки скобок в виде строки.
   * @param res - матрица промежуточного результата.
   * @param from - номер первой матрицы.
   * @param to - номер последней матрицы.
   * @return - —трока, представл€юща€ оптимальную расстановку скобок.
   */
  private static String brackets(int[][] res, int from, int to) {
    if (from == to) return "A" + from;
    int point = res[to][from];
    return "(" + brackets(res, from, point-1) + "x" + brackets(res, point, to) + ")";
  }
  
  /**
   * @param args - размеры перемножаемых матриц - целые числа в количестве
   * на единицу большем, чем число матриц.
   */
  public static void main(String[] args) {
    int[] sizes = new int[] { 3, 10, 2, 5, 12, 6 }; // тест "по умолчанию".
    if (args.length > 0) {
      try {
        int n = args.length;
        sizes = new int[n];
        for (int i = 0; i < n; ++i) {
          sizes[i] = Integer.parseInt(args[i]);
        }
      } catch (NumberFormatException x) {
        System.out.println("Ќеверно заданы размеры матриц");
      }
    }
    printResult(opt(sizes));
  }

}
