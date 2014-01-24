package org.ifmo;

/**
 * ���������� ���������� ����������� ����� (������� �������).
 */
public class Complex {
  /**
   * ������������ ����� ������������ �����.
   */
  private double re;
  
  /**
   * ������ ����� ������������ �����.
   */
  private double im;

  /**
   * ������ �������.
   */
  public static final Complex i = new Complex(0, 1);

  /**
   * �����������, ��������� ����������� ����� �� ��� ������������ � ������ �����.
   * @param re ������������ ����� �����.
   * @param im ������ ����� �����.
   */
  public Complex(double re, double im) {
    this.re = re; this.im = im;
  }
  
  /**
   * �����������, ��������� ����������� ����� �� �������������.
   * @param re ������������ ����� �����.
   */
  public Complex(double re) {
    this(re, 0);
  }
  
  //==================== ������� ������� =======================
  
  /**
   * ������������ ����� ������������ �����.
   * @return ������������ ����� ������������ �����.
   */
  public double re() { return re; }
  
  /**
   * ������ ����� ������������ �����.
   * @return ������ ����� ������������ �����.
   */
  public double im() { return im; }
  
  //==================== ����������� ���������� =======================
  
  /**
   * �������� ����������� �����.
   * @param c1 ������ ��������.
   * @param c2 ������ ��������.
   * @return ����� ���� ����������� �����.
   */
  public static Complex add(Complex c1, Complex c2) {
    return new Complex(c1.re + c2.re, c1.im + c2.im);
  }
  
  /**
   * �������� ������������ ����� � ����������.
   * @param c2 ������ ��������.
   * @return ����� ���� ����������� �����.
   */
  public Complex add(Complex c2) {
    return add(this, c2);
  }
  
  /**
   * ��������� ����������� �����.
   * @param c1 ������ ��������.
   * @param c2 ������ ��������.
   * @return �������� ���� ����������� �����.
   */
  public static Complex sub(Complex c1, Complex c2) {
    return new Complex(c1.re - c2.re, c1.im - c2.im);
  }

  /**
   * ��������� ������������ ����� �� �������.
   * @param c2 ����������.
   * @return �������� ���� ����������� �����.
   */
  public Complex sub(Complex c2) {
    return sub(this, c2);
  }

  /**
   * ��������� ������������ �����.
   * @param c �����.
   * @return ��������� ���������.
   */
  public static Complex negate(Complex c) {
    return new Complex(-c.re, -c.im);
  }
  
  /**
   * ��������� ������������ �����.
   * @return ���������.
   */
  public Complex negate() {
    return negate(this);
  }
  
  /**
   * ��������� ����������� �����.
   * @param c1 ������ ��������.
   * @param c2 ������ ��������.
   * @return ������������ ���� ����������� �����.
   */
   public static Complex mult(Complex c1, Complex c2) {
    return new Complex(c1.re * c2.re - c1.im * c2.im,
                       c1.re * c2.im + c1.im * c2.re);
  }
  
   /**
    * ��������� �� ����������� �����.
    * @param c2 ������ ��������.
    * @return ������������ ����������� �����.
    */
  public Complex mult(Complex c2) {
    return mult(this, c2);
  }
  
  /**
   * ������� ����������� �����.
   * @param c1 ������ ��������.
   * @param c2 ������ ��������.
   * @return ������� ���� ����������� �����.
   */
  public static Complex div(Complex c1, Complex c2) {
    double mod2 = mod(c2);
    // ���� ������ �������� ����� ����, �� ����� ���������� ����������.
    return new Complex((c1.re * c2.re + c1.im * c2.im) / mod2,
                       (c1.im * c2.re - c1.re * c2.im) / mod2);
  }
  
  /**
   * ������� �� ����������� �����.
   * @param c2 ������ ��������.
   * @return ������� ���� ����������� �����.
   */
  public Complex div(Complex c2) {
    return div(this, c2);
  }

  /**
   * ���������� ������ ������������ �����.
   * @param c ����������� �����.
   * @return ������ ���������.
   */
  public static double mod(Complex c) {
    return Math.sqrt(c.re * c.re + c.im * c.im);
  }
  
  /**
   * ���������� ������ ������������ �����.
   * @return ������ �����.
   */
  public double mod() {
    return mod(this);
  }
  
  /**
   * ���������� ��������� ������������ �����.
   * @param c ����������� �����.
   * @return �������� ���������.
   */
  public static double arg(Complex c) {
    return Math.atan2(c.im, c.re);
  }
  
  /**
   * ���������� ��������� ������������ �����.
   * @return �������� �����.
   */
  public double arg() {
    return arg(this);
  }
  
  //=============== ��������������� ������� ������ Object ===================
  
  /**
   * ��������� ���� ����������� �����.
   * @param c1 ������ ����������� �����.
   * @param c2 ������ ����������� �����.
   * @return true, ���� ����� �����, false � ��������� ������.
   */
  public static boolean equals(Complex c1, Complex c2) {
    return c1.re == c2.re && c2.im == c2.im;
  }
  
  /**
   * ��������� � ����������� ������.
   * @param c2 ������ ����������� �����.
   * @return true, ���� ����� �����, false � ��������� ������.
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
  
  //====================== ���� =========================
  
  /**
   * �������� ������� ��������� ��� �������� ���������, ���������, �������������� � ������.
   * @param args �� ������������.
   */
  public static void main(String[] args) {
    System.out.println("����������� ����� i = " + Complex.i);
    System.out.println("����������� ����� i � �������� = " + Complex.i.mult(Complex.i));
    if (Complex.i.mult(Complex.i).equals(-1)) {
      System.out.println("������� ������ ������� ���� -1");
    } else {
      System.out.println("������� ������ ������� �� ���� -1");
    }
    Complex c = new Complex(-1, -1);
    System.out.format("��� ����� %s ������ r = %f, �������� theta = %f\n", 
        c.toString(), c.mod(), c.arg());
  }

}
