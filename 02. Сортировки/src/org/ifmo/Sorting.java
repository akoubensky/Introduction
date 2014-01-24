package org.ifmo;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

/**
 * Класс Sorting содержит описания различных алгоритмов сортировки массивов.
 * Все алгоритмы описаны в виде статических публичных функций класса.
 */
public class Sorting {
    static int[] randomArray(int length) {
        int[] array = new int[length];
        Random rnd = new Random();
        for (int i = 0; i < length; ++i) {
            array[i] = rnd.nextInt(5*length);
        }
        return array;
    }

    static void print(int[] array) {
        System.out.println(Arrays.toString(array));
    }
    /**
     * Главная функция класса осуществляет тестовый вызов всех описанных методов сортировки.
     *
     * @param args параметры программы - не используются
     */
    public static void main(String[] args) {
        // Сначала проверим правильность работы алгоритма на массивах небольшого размера
        sort("bubbleSort", 25);     // Сортировка "пузырьком"
        sort("shakerSort", 25);     // Шейкер-сортировка
        sort("simpleSort", 25);     // Сортировка простыми вставками
        sort("binInsertSort", 25);  // Сортировка двоичными вставками
        sort("shellSort", 25);      // Сортировка Шелла
        sort("quickSort", 25);      // Быстрая сортировка
        sort("heapSort", 25);       // Пирамидальная сортировка
        sort("mergeSort", 25);      // Сортировка слиянием
        sort("digitSort", 25);      // Цифровая сортировка

        // Теперь запускаем сортировку на сравнительно большом объеме данных
        sort("bubbleSort", 1000);     // Сортировка "пузырьком"
        sort("shakerSort", 1000);     // Шейкер-сортировка
        sort("simpleSort", 1000);     // Сортировка простыми вставками
        sort("binInsertSort", 1000);  // Сортировка двоичными вставками
        sort("shellSort", 1000);      // Сортировка Шелла
        sort("quickSort", 1000);      // Быстрая сортировка
        sort("heapSort", 1000);       // Пирамидальная сортировка
        sort("mergeSort", 1000);      // Сортировка слиянием
        sort("digitSort", 1000);      // Цифровая сортировка

        // Наконец запускаем сортировку на "реальном" объеме данных
        sort("bubbleSort", 50000);     // Сортировка "пузырьком"
        sort("shakerSort", 50000);     // Шейкер-сортировка
        sort("simpleSort", 50000);     // Сортировка простыми вставками
        sort("binInsertSort", 50000);  // Сортировка двоичными вставками
        sort("shellSort", 50000);      // Сортировка Шелла
        sort("quickSort", 50000);      // Быстрая сортировка
        sort("heapSort", 50000);       // Пирамидальная сортировка
        sort("mergeSort", 50000);      // Сортировка слиянием
        sort("digitSort", 50000);      // Цифровая сортировка
    }

    /**
     * Вызов заданного метода сортировки.
     *
     * @param methodName    - имя функции - метода сортировки
     * @param length        - количество (случайных) данных
     */
    private static void sort(String methodName, int length) {
        int[] data = randomArray(length);                // Массив для хранения исходных и отсортированных данных
        boolean outData = length <= 30;

        if (outData) {
            System.out.println("Before sorting:");
            print(data);
        }

        try {
            Method method = Sorting.class.getMethod(methodName, new Class[] { int[].class });
            long tm = Calendar.getInstance().getTimeInMillis();
            // Вызов метода сортировки
            method.invoke(null, new Object[] { data });
            // Вывод информации о времени работы алгоритма
            System.out.println("After " + methodName + " sorting (elasped time is " +
                    (Calendar.getInstance().getTimeInMillis() - tm) + " ms)");
            // Вывод результатов сортировки
            if (outData) print(data);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    /**
     * Метод сортировки "пузырьком".
     * Достоинства метода:
     *    - простота;
     *    - устойчивость;
     *    - сортировка "на месте";
     *    - сравнительно высокая скорость работы на небольших массивах;
     *    - высокая скорость работы на массивах, уже имеющих некоторую упорядоченность
     * Недостатки метода:
     *    - невысокая скорость работы на длинных массивах.
     * Оценки скорости работы:
     *    - в лучшем случае - О(n);
     *    - в среднем       - O(n*n);
     *    - в худшем случае - O(n*n)
     *
     * @param data   inout - сортируемый массив и реультат сортировки
     */
    public static void bubbleSort(int[] data) {
        int n = data.length;
        boolean inversed = true;
        for (int i = 1; i < n && inversed; i++) {
            inversed = false;
            for (int j = 0; j < n-i; j++) {
                if (data[j] > data [j+1]) {
                    int tmp = data[j];
                    data[j] = data[j+1];
                    data[j+1] = tmp;
                    inversed = true;
                }
            }
        }
    }


    /**
     * Метод шейкре-сортировки.
     * Достоинства и недостатки метода - см. сортировку "пузырьком".
     * Оценки скорости работы:
     *    - в лучшем случае - О(n);
     *    - в среднем       - O(n*n);
     *    - в худшем случае - O(n*n)
     *
     * @param data   inout - сортируемый массив и реультат сортировки
     */
    public static void shakerSort(int[] data) {
        int n = data.length;
        boolean inversed = true;
        int low = 0, high = n;
        while (low < high && inversed) {
            inversed = false;
            for (int j = low+1; j < high; j++) {
                if (data[j-1] > data [j]) {
                    int tmp = data[j];
                    data[j] = data[j-1];
                    data[j-1] = tmp;
                    inversed = true;
                }
            }
            high--;
            for (int j = high; j > low; j--) {
                if (data[j-1] > data [j]) {
                    int tmp = data[j];
                    data[j] = data[j-1];
                    data[j-1] = tmp;
                    inversed = true;
                }
            }
            low++;
        }
    }

    /**
     * Метод сортировки простыми вставками.
     * Достоинства метода:
     *    - простота;
     *    - устойчивость;
     *    - сортировка "на месте";
     *    - высокая скорость работы на небольших массивах;
     *    - высокая скорость работы на массивах, уже имеющих некоторую упорядоченность
     * Недостатки метода:
     *    - невысокая скорость работы на длинных массивах.
     * Оценки скорости работы:
     *    - в лучшем случае - О(n);
     *    - в среднем       - O(n*n);
     *    - в худшем случае - O(n*n)
     *
     * @param data   inout - сортируемый массив и реультат сортировки
     */
    public static void simpleSort(int[] data) {
        simpleSort(data, 0, data.length-1);
    }

    /**
     * Сортировка заданного фрагмента массива методом простых вставок
     *
     * @param data  - массив, содержащий участок, подлежащий сортировке
     * @param from  - индекс начала сортируемого участка
     * @param to    - индекс конца сортируемого участка
     */
    private static void simpleSort(int[] data, int from, int to) {
        int i, j;
        for (i = from+1; i <= to; i++) {
            int c = data[i];
            for (j = i-1; j >= from && data[j] > c; j--) {
                data[j+1] = data[j];
            }
            data[j+1] = c;
        }
    }

    /**
     * Метод сортировки двоичными вставками. Отличается от метода простых вставок
     * только алгоритмом поиска места вставляемого ключа.
     * Достоинства метода (по сравнению с методом простых вставок):
     *    - меньшее по сравнению с методом простых вставок число сравнений;
     * Недостатки метода (по сравнению с методом простых вставок):
     *    - отсутствие устойчивости;
     * Оценки скорости работы:
     *    - в лучшем случае - О(n);
     *    - в среднем       - O(n*n);
     *    - в худшем случае - O(n*n)
     *
     * @param data   inout - сортируемый массив и реультат сортировки
     */
    public static void binInsertSort(int[] data) {
        int n = data.length;      // Длина массива
        for (int i = 1; i < n; i++) {
            int c = data[i];      // Вставляемое значение
            // Организация поиска места для вставки значения c
            int low = 0, high = i;
            // Inv : (low <= high) && место для c - внутри data[low:high]
            while (low < high) {
                int m = (low+high) >> 1;
                // low <= m < high
                if (data[m] < c) low = m+1; else high = m;
            }
            // Найдено место вставки - low
            // Сдвигаем элементы в сторону больших индексов.
            for (int j = i-1; j >= low; j--) {
                data[j+1] = data[j];
            }
            // Заносим значение на найденное место
            data[low] = c;
        }
    }

    /**
     * Метод сортировки Шелла.
     * Достоинства метода:
     *    - простота;
     *    - сортировка "на месте";
     *    - относительно высокая скорость работы даже на больших массивах;
     * Недостатки метода:
     *    - отсутствие устойчивости;
     *    - отсутствие хорошей теории.
     * Оценки скорости работы:
     *    - в лучшем случае - О(n);
     *    - в среднем       - ?;
     *    - в худшем случае - ?
     *
     * @param data   inout - сортируемый массив и реультат сортировки
     */
    public static void shellSort(int[] data) {
        int n = data.length;    // Длина массива
        int step = n;           // Шаг поисков и вставки
        do {
            int i, j;
            // Вычисляем новый шаг
            step = step / 3 + 1;
            // Производим сортировку простыми вставками с заданным шагом
            for (i = step; i < n; i++) {
                int c = data[i];
                for (j = i-step; j >= 0 && data[j] > c; j -= step) {
                    data[j+step] = data[j];
                }
                data[j+step] = c;
            }
        } while (step != 1);
    }

    /**
     * Метод сортировки "быстрый".
     * Достоинства метода:
     *    - сортировка "на месте";
     *    - высокая скорость работы на массивах любого размера;
     * Недостатки метода:
     *    - отсутствие устойчивости.
     *    - невысокая скорость работы на массивах, уже имеющих некоторую упорядоченность
     * Оценки скорости работы:
     *    - в лучшем случае - О(n * log(n));
     *    - в среднем       - O(n * log(n));
     *    - в худшем случае - O(n*n)
     *
     * @param data   inout - сортируемый массив и реультат сортировки
     */
    public static void quickSort(int[] data) {
        quickSort(data, 0, data.length-1);
    }

    /**
     * Алгоритм сортировки заданного фрагмента массива методом "быстрой" сортировки.
     * Рекурсивная функция, вызывающая в качестве базового случая метод сортировки простыми вставками
     *
     * @param data    - inout - сортируемый массив
     * @param from    - индекс начала сортируемого фрагмента
     * @param to      - индекс конца сортируемого фрагмента
     */
    private static void quickSort(int[] data, int from, int to) {
        if (to-from < 50)
            // Небольшие фрагменты быстрее сортировать методом простых вставок
            simpleSort(data, from, to);
        else {
            int c = data[from];    // Выбираем некоторый элемент
            // Распределяем элементы массива на значения меньшие и большие c.
            int low = from, high = to+1;
            // Inv: (low <= high) && data[from:(low-1)] <= c && data[high:to] >= c
            while (low < high) {
                while (low < high && data[--high] >= c) ;
                data[low] = data[high];
                while (low < high && data[++low] <= c) ;
                data[high] = data[low];
            }
            // Вставляем элемент на свое место
            data[low] = c;
            // Независимо сортируем верхнюю и нижнюю половины массива
            quickSort(data, from, low-1);
            quickSort(data, low+1, to);
        }
    }

    /**
     * Метод пирамидальной сортировки.
     * Достоинства метода:
     *    - сортировка "на месте";
     *    - сравнительно высокая скорость работы на массивах любой длины и упорядоченности;
     * Недостатки метода:
     *    - отсутствие устойчивости;
     *    - сложность алгоритма;
     *    - сравнительно невысокая скорость работы на длинных случайных массивах.
     * Оценки скорости работы:
     *    - в лучшем случае - О(n * log(n));
     *    - в среднем       - O(n * log(n));
     *    - в худшем случае - O(n * log(n))
     *
     * @param data   inout - сортируемый массив и реультат сортировки
     */
    public static void heapSort(int[] data) {
        int n = data.length;   // Длина массива

        // Построение пирамиды
        buildPyramid:
        for (int i = 1; i < n; i++) {
            // Элементы data[0:i-1] уже образуют пирамиду;
            // Вставляем в пирамиду элемент data[i]
            int c = data[i];   // "Протаскиваемое" значение
            int p = i, q;      // Индексы для "протаскивания" вверх к вершине
            do {
                q = p;
                if ((p = (q-1) >> 1) >= 0 && data[p] < c) {
                    data[q] = data[p];
                } else {
                    data[q] = c;
                    continue buildPyramid;
                }
            } while(true);
        }

        // Постепенное разрушение пирамиды
        meltPyramid:
        for (int i = n-1; i > 0; i--) {
            // Элемент с вершины отправляем в конец пирамиды, а последний элемент
            // протаскиваем по направлению "вниз"
            int c = data[i];
            data[i] = data[0];
            int q, p = 0;      // Индексы для протаскивания
            do {
                q = p;
                p = (q << 1) | 1;
                if (p >= i) {
                    // Вышли за границу пирамиды
                    data[q] = c;
                    continue meltPyramid;
                }
                if (p < i-1 && data[p+1] > data[p]) p++;
                if (data[p] > c) {
                    data[q] = data[p];
                } else {
                    data[q] = c;
                    continue meltPyramid;
                }
            } while (true);
        }
    }

    /**
     * Сортировка участка массива методом слияния.
     * @param a     исходный массив, участок которого сортируется
     * @param from  индекс начала фрагмента
     * @param to    индекс конца фрагмента
     */
    private static void mergeFragment(int[] a, int from, int to) {
        // База рекурсии: короткий массив уже отсортирован
        if (to - from == 1) return;

        // Делим массив пополам и сортируем по отдельности обе половины
        int m = (from + to) / 2;
        mergeFragment(a, from, m);
        mergeFragment(a, m, to);

        // Сливаем два фрагмента в один упорядоченный массив
        int[] combined = new int[to - from];
        int i1 = from, n1 = m;
        int i2 = m, n2 = to;
        int ic = 0;
        while (i1 < n1 && i2 < n2) {
            if (a[i1] < a [i2]) {
                combined[ic++] = a[i1++];
            } else {
                combined[ic++] = a[i2++];
            }
        }
        while (i1 < n1) combined[ic++] = a[i1++];
        while (i2 < n2) combined[ic++] = a[i2++];

        // Переписываем элементы массива в исходный массив
        for (int i = from; i < to; ++i) {
            a[i] = combined[i-from];
        }
    }

    /**
     * Метод сортировки слиянием (фон Неймана).
     * Достоинства метода:
     *    - простота;
     *    - высокая скорость работы;
     * Недостатки метода:
     *    - отсутствие устойчивости;
     *    - требуется дополнительная память размера n;
     * Оценки скорости работы:
     *    - в лучшем случае - О(n * log(n));
     *    - в среднем       - O(n * log(n));
     *    - в худшем случае - O(n * log(n))
     *
     * @param data   inout - сортируемый массив и реультат сортировки
     */
    public static void mergeSort(int[] data) {
        mergeFragment(data, 0, data.length);
    }

    /**
     * Метод цифровой сортировки.
     * Достоинства метода:
     *    - устойчивость;
     *    - высокая скорость работы для данных, состоящих из небольшого количества "цифр";
     * Недостатки метода:
     *    - требуется дополнительная память размера n;
     * Оценки скорости работы:
     *    - в лучшем случае - О(n);
     *    - в среднем       - O(n);
     *    - в худшем случае - O(n)
     *
     * @param data   inout - сортируемый массив и реультат сортировки
     */
    public static void digitSort(int[] data) {
        int n = data.length;        // Длина массива
        int[] data2 = new int[n];   // Вспомогательный массив
        for (int step = 0; step < 8; step++) {  // Step - номер "цифры"
            // Сортировка "подсчетом" по цифре с заданным номером
            countSort(data, step, data2);
            // Меняем указатели на массивы
            int[] temp = data; data = data2; data2 = temp;
        }
    }

    /**
     * Сортировка методом подсчета.
     * При сравнении элементов принимается во внимание толко одна 16-ричная цифра
     *
     * @param src     - сортируемый массив
     * @param nDig    - номер цифры (начиная с младших разрядов)
     * @param dest    - массив назначения
     */
    private static void countSort (int[] src, int nDig, int[] dest) {
        int n = src.length;          // Длина массива
        int[] count = new int[16];   // Массив для подсчета элементов
        // 1. Подсчет
        nDig <<= 2;
        for (int i = 0; i < n; i++) {
            count[(src[i] & (0xF << nDig)) >> nDig]++;
        }
        // 2. Суммирование
        for (int i = 1; i < 16; i++) {
            count[i] += count[i-1];
        }
        // 3. Расстановка
        for (int i = n-1; i >= 0; i--) {
            dest[--count[(src[i] & (0xF << nDig)) >> nDig]] = src[i];
        }
    }
}
