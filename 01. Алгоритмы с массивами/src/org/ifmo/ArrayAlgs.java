package org.ifmo;

/**
 * Некоторые классические алгоритмы работы с массивами.
 */
public class ArrayAlgs {
    /**
     * Простой (линейный) поиск в массиве.
     * @param a Массив, в котором производится поиск.
     * @param key Ключ поиска.
     * @return Индекс найденного элемента или -1,
     *     если элемент не найден.
     */
    static int indexOf(int[] a, int key) {
        for (int i = 0; i < a.length; ++i) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Двоичный поиск в упорядоченном массиве
     * (аналог стандартного java.util.Arrays.biarySearch)
     * @param a Массив, в котором производится поиск.
     * @param key Ключ поиска.
     * @return Индекс найденного элемента или -1,
     *     если элемент не найден.
     */
    static int binSearch(int[] a, int key) {
        int low = 0;
        int high = a.length;
        // Inv: если ключ есть в массиве,
        //      то он находится среди элементов a[low...high-1]
        while (low < high) {
            int mid = (low + high) / 2;
            if (a[mid] == key) return mid;
            if (a[mid] < key) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return -1;
    }

    /**
     * Поиск максимального элемента в массиве.
     * @param a Массив, в котором ищем максимум.
     * @return Максимальный элемент (непустого) массива.
     */
    static int getMax(int[] a) {
        int max = Integer.MIN_VALUE;
        for (int e : a) {
            if (e > max) max = e;
        }
        return max;
    }

    /**
     * Поиск нескольких максимальных элементов в массиве.
     * @param a Исходный массив.
     * @param k Количество максимальных элементов
     *     (это число должно быть много меньше длины массива).
     * @return Массив нескольких максимальных элементов.
     */
    static int[] getManyMax(int[] a, int k) {
        int[] max = new int[k];
        java.util.Arrays.fill(max, Integer.MIN_VALUE);
        for (int e : a) {
            // Надо ли помещать элемент e в массив?
            if (e <= max[k-1]) continue;
            // Поиск места для очередного элемента в массиве max.
            int ind = k - 1;
            while (ind > 0 && max[ind-1] < e) --ind;
            // Помещаем элемент на свое место в массив max.
            for (int j = k-1; j > ind; --j) {
                max[j] = max[j-1];
            }
            max[ind] = e;
        }
        return max;
    }

    /**
     * "Переворачивание" массива.
     * @param a Исходный массив.
     */
    static void reverse(int[] a) {
        int len = a.length;
        for (int i = 0; i < len / 2; ++i) {
            int e = a[i];
            a[i] = a[len - i - 1];
            a[len - i - 1] = e;
        }
    }

    /**
     * Поэлементное сравнение массивов.
     * @param a1 Первый массив.
     * @param a2 Второй массив.
     * @return Результат сравнения.
     */
    static boolean equals(char[] a1, char[] a2) {
        if (a1 == null) return a2 == null;
        if (a2 == null) return false;
        if (a1.length != a2.length) return false;
        for (int i = 0; i < a1.length; ++i) {
            if (a1[i] != a2[i]) return false;
        }
        return true;
    }

    /**
     * Заполнение массива элементом с заданным значением.
     * @param array Исходный массив.
     * @param value Значение для заполнения.
     */
    static void fill(int[] array, int value) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                array[i] = value;
            }
        }
    }

    /**
     * Копирование в новый массив с возможным изменением размера.
     * @param aSrc Исходный массив.
     * @param newLength Требуемая длина нового массива.
     * @return Новый массив.
     */
    static double[] copyOf(double[] aSrc, int newLength) {
        double[] copy = new double[newLength];
        int minLength = Math.min(aSrc.length, newLength);
        for (int i = 0; i < minLength; ++i) {
            copy[i] = aSrc[i];
        }
        return copy;
    }


    /**
     * Копирование фрагмента массива в новый массив с возможным изменением размера.
     * @param aSrc Исходный массив.
     * @param from Индекс начала фрагмента.
     * @param to Индекс конца фрагмента.
     * @return Новый массив.
     */
    static char[] copyOfRange(char[] aSrc, int from, int to) {
        if (from < 0 || from > aSrc.length) {
            throw new IndexOutOfBoundsException();
        }
        if (from > to) throw new IllegalArgumentException();
        char[] copy = new char[to - from];
        int lastIndex = Math.min(aSrc.length - from, to - from);
        for (int i = 0; i < lastIndex; ++i) {
            copy[i] = aSrc[from + i];
        }
        return copy;
    }

    /**
     * "Переворачивание" фрагмента массива.
     * @param a Исходный массив.
     * @param low Индекс первого элемента переворачиваемого фрагмента.
     * @param high Индекс первого элемента,
     *     следующего за переворачиваемым фрагментом
     */
    static void reverse(int[] a, int low, int high) {
        for (int i = low; i < (low + high) / 2; ++i) {
            int e = a[i];
            a[i] = a[low + high - i - 1];
            a[low + high - i - 1] = e;
        }
    }

    /**
     * Версия функции переворачивания массива, использующая
     * функцию переворачивания фрагмента массива.
     * В качестве "фрагмента" берется весь массив.
     * @param a Исходный массив.
     */
    static void reverse1(int[] a) { reverse(a, 0, a.length); }

    /**
     * Циклический сдвиг элементов массива влево на заданное число позиций.
     * Функция использует инверсию фрагментов массива.
     * @param a Исходный массив.
     * @param shift Величина сдвига (неотрицательная).
     */
    static void cycleLeft(int[] a, int shift) {
        reverse(a, 0, shift);
        reverse(a, shift, a.length);
        reverse(a);
    }

    /**
     * Циклический сдвиг элементов массива вправо на заданное число позиций.
     * Функция использует сдвиг влево.
     * @param a Исходный массив.
     * @param shift Величина сдвига (неотрицательная).
     */
    static void cycleRight(int[] a, int shift) {
        cycleLeft(a, a.length - shift);
    }

    /**
     * Фильтрация элементов массива по заданному значению.
     * После фильтрации в начале массива будут расположены элементы,
     * меньшие заданного значения, а в конце - большие.
     * @param a     Исходный массив.
     * @param val   Фильтрующее значение.
     * @return      Индекс, начиная с которого все элементы массива больше val.
     */
    static int filter(int[] a, int val) {
        int low = 0;
        int high = a.length;
        // Inv: элементы с индексами < low имеют значения <= val;
        //      элементы с индексами >= high имеют значения > val;
        //      low <= high
        while (low < high) {
            while (low < high && a[low] <= val) ++low;
            while (low < high && a[high-1] > val) -- high;
            if (low < high) {
                int e = a[low]; a[low] = a[high-1]; a[high-1] = e;
            }
        }
        return low;  // == high
    }

    /**
     * Поиск максимальной возрастающей подпоследовательности в массиве.
     * @param array Исходный массив.
     * @return Индекс начала найденной подпоследовательности.
     */
    static int subsequence(int[] array) {
        int lenMax = 0;
        int indMax = 0;
        int lenCurrent = 0;
        int indCurrent = 0;
        int current = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] >= current) {
                if (++lenCurrent > lenMax) {
                    indMax = indCurrent;
                    lenMax = lenCurrent;
                }
            } else {
                indCurrent = i;
                lenCurrent = 1;
            }
            current = array[i];
        }
        return indMax;
    }

    /**
     * Печать всех элементов массива в одну строку.
     * @param a Печатаемый массив.
     */
    static void print(int[] a) {
        for (int e : a) {
            System.out.print(e + " ");
        }
        System.out.println();
    }

    /**
     * Генерация случайного целочисленного массива заданной длины.
     * @param len Длина.
     * @return Сгенерированный массив.
     */
    static int[] randomArray(int len) {
        java.util.Random random = new java.util.Random();
        int[] a = new int[len];
        // Заполняем массив случайными значениями из интервала 0..(5*len - 1)
        for (int i = 0; i < len; ++i) {
            a[i] = random.nextInt(5*len);
        }
        return a;
    }

    /**
     * Проверка работоспособности описанных алгоритмов.
     * @param args
     */
    public static void main(String[] args) {
        // Генерируем массив длины 15
        int[] array = randomArray(15);
        // Поиск в случайном массиве.
        System.out.println("Исходный массив:");
        print(array);
        System.out.format(
                "Ищем элемент %d с номером %d. Результат поиска: %d\n",
                array[12], 12, indexOf(array, array[12]));
        System.out.format(
                "Ищем элемент %d. Результат поиска: %d\n",
                50, indexOf(array, 50));

        // Поиск максимальных элементов.
        System.out.format("Максимальный элемент в этом массиве: %d\n", getMax(array));
        System.out.println("Три максимальных элемента в этом массиве:");
        print(getManyMax(array, 3));

        // Переворачивание массива.
        reverse(array);
        System.out.println("Перевернутый массив:");
        print(array);

        // Сдвиги массива.
        cycleRight(array, 3);
        System.out.println("Циклический сдвиг вправо на 3 позиции:");
        print(array);
        cycleLeft(array, 5);
        System.out.println("А теперь циклический сдвиг влево на 5 позиций:");
        print(array);

        // Фильтруем массив.
        System.out.format(
                "Разделяем элементы на меньшие и большие %d. Граница проходит по элементу с индексом %d.\n",
                30, filter(array, 30));
        print(array);

        // В полученном массиве ищем максимальную возрастающую подпоследовательность.
        System.out.format("Индекс начала максимальной возрастающей подпоследовательности: %d\n",
                subsequence(array));

        // Поиск в упорядоченном массиве.
        java.util.Arrays.sort(array);
        System.out.println("Упорядоченный массив:");
        print(array);
        System.out.format(
                "Ищем элемент %d с номером %d. Результат поиска: %d\n",
                array[11], 11, binSearch(array, array[11]));
        System.out.format(
                "Ищем элемент %d. Результат поиска: %d\n",
                50, indexOf(array, 50));
    }

}
