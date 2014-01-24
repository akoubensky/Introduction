package org.ifmo;

/**
 * Реализация арифметики комплексных чисел (базовые функции).
 */
public class Complex {
  /**
   * Вещественная часть комплексного числа.
   */
  private double re;
  
  /**
   * Мнимая часть комплексного числа.
   */
  private double im;

  /**
   * Мнимая единица.
   */
  public static final Complex i = new Complex(0, 1);

  /**
   * Конструктор, создающий комплексное число по его вещественной и мнимой части.
   * @param re Вещественная часть числа.
   * @param im Мнимая часть числа.
   */
  public Complex(double re, double im) {
    this.re = re; this.im = im;
  }
  
  /**
   * Конструктор, создающий комплексное число по вещественному.
   * @param re Вещественная часть числа.
   */
  public Complex(double re) {
    this(re, 0);
  }
  
  //==================== Функции доступа =======================
  
  /**
   * Вещественная часть комплексного числа.
   * @return Вещественная часть комплексного числа.
   */
  public double re() { return re; }
  
  /**
   * Мнимая часть комплексного числа.
   * @return Мнимая часть комплексного числа.
   */
  public double im() { return im; }
  
  //==================== Комплексная арифметика =======================
  
  /**
   * Сложение комплексных чисел.
   * @param c1 Первый аргумент.
   * @param c2 Второй аргумент.
   * @return Сумма двух комплексных чисел.
   */
  public static Complex add(Complex c1, Complex c2) {
    return new Complex(c1.re + c2.re, c1.im + c2.im);
  }
  
  /**
   * Сложение комплексного числа с аргументом.
   * @param c2 Второй аргумент.
   * @return Сумма двух комплексных чисел.
   */
  public Complex add(Complex c2) {
    return add(this, c2);
  }
  
  /**
   * Вычитание комплексных чисел.
   * @param c1 Первый аргумент.
   * @param c2 Второй аргумент.
   * @return Разность двух комплексных чисел.
   */
  public static Complex sub(Complex c1, Complex c2) {
    return new Complex(c1.re - c2.re, c1.im - c2.im);
  }

  /**
   * Вычитание комплексного числа из данного.
   * @param c2 Вычитаемое.
   * @return Разность двух комплексных чисел.
   */
  public Complex sub(Complex c2) {
    return sub(this, c2);
  }

  /**
   * Отрицание комплексного числа.
   * @param c Число.
   * @return Отрицание аргумента.
   */
  public static Complex negate(Complex c) {
    return new Complex(-c.re, -c.im);
  }
  
  /**
   * Отрицание комплексного числа.
   * @return Отрицание.
   */
  public Complex negate() {
    return negate(this);
  }
  
  /**
   * Умножение комплексных чисел.
   * @param c1 Первый аргумент.
   * @param c2 Второй аргумент.
   * @return Произведение двух комплексных чисел.
   */
   public static Complex mult(Complex c1, Complex c2) {
    return new Complex(c1.re * c2.re - c1.im * c2.im,
                       c1.re * c2.im + c1.im * c2.re);
  }
  
   /**
    * Умножение на комплексное число.
    * @param c2 Второй аргумент.
    * @return Произведение комплексных чисел.
    */
  public Complex mult(Complex c2) {
    return mult(this, c2);
  }
  
  /**
   * Деление комплексных чисел.
   * @param c1 Первый аргумент.
   * @param c2 Второй аргумент.
   * @return Частное двух комплексных чисел.
   */
  public static Complex div(Complex c1, Complex c2) {
    double mod2 = mod(c2);
    // Если модуль делителя равен нулю, то будет возбуждено прерывание.
    return new Complex((c1.re * c2.re + c1.im * c2.im) / mod2,
                       (c1.im * c2.re - c1.re * c2.im) / mod2);
  }
  
  /**
   * Деление на комплексное число.
   * @param c2 Второй аргумент.
   * @return Частное двух комплексных чисел.
   */
  public Complex div(Complex c2) {
    return div(this, c2);
  }

  /**
   * Вычисление модуля комплексного числа.
   * @param c Комплексное число.
   * @return Модуль аргумента.
   */
  public static double mod(Complex c) {
    return Math.sqrt(c.re * c.re + c.im * c.im);
  }
  
  /**
   * Вычисление модуля комплексного числа.
   * @return Модуль числа.
   */
  public double mod() {
    return mod(this);
  }
  
  /**
   * Вычисление аргумента комплексного числа.
   * @param c Комплексное число.
   * @return Аргумент аргумента.
   */
  public static double arg(Complex c) {
    return Math.atan2(c.im, c.re);
  }
  
  /**
   * Вычисление аргумента комплексного числа.
   * @return Аргумент числа.
   */
  public double arg() {
    return arg(this);
  }
  
  //=============== Переопределения функций класса Object ===================
  
  /**
   * Сравнение двух комплексных чисел.
   * @param c1 Первое комплексное число.
   * @param c2 Второе комплексное число.
   * @return true, если числа равны, false в противном случае.
   */
  public static boolean equals(Complex c1, Complex c2) {
    return c1.re == c2.re && c2.im == c2.im;
  }
  
  /**
   * Сравнение с комплексным числом.
   * @param c2 Второе комплексное число.
   * @return true, если числа равны, false в противном случае.
   */
  public boolean equals(Complex c2) {
    return equals(this, c2);
  }

  /**
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Complex) {
      return equals((Complex)o);
    } else if (o instanceof Number) {
      return equals(new Complex(((Number)o).doubleValue()));
    } else {
      throw new ClassCastException();
    }
  }

  /**
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ((Double)re).hashCode() ^ ((Double)im).hashCode();
  }
  
  /**
   * @see Object#toString()
   */
  @Override
  public String toString() {
    if (im == 0) return Double.toString(re);
    if (re == 0) return im + "i";
    return re + (im >= 0 ? " + " + im : " - " + (-im)) + "i";
  }
  
  //====================== Тест =========================
  
  /**
   * Тестовая функция проверяет как работают умножение, сравнение, преобразование в строку.
   * @param args Не используется.
   */
  public static void main(String[] args) {
    System.out.println("Комплексное число i = " + Complex.i);
    System.out.println("Комплексное число i в квадрате = " + Complex.i.mult(Complex.i));
    if (Complex.i.mult(Complex.i).equals(-1)) {
      System.out.println("Квадрат мнимой единицы есть -1");
    } else {
      System.out.println("Квадрат мнимой единицы не есть -1");
    }
    Complex c = new Complex(-1, -1);
    System.out.format("Для числа %s модуль r = %f, аргумент theta = %f\n", 
        c.toString(), c.mod(), c.arg());
  }

}
