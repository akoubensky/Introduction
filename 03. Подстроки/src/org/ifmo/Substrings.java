package org.ifmo;

import java.util.HashMap;

/**
 * Методы этого класса представляют собой классические алгоритмы поиска подстроки в заданной строке.
 */
public class Substrings {
	// Пример строки, в которой производится поиск
	static final String where = 
			"Мы старались написать учебник по построению алгоритмов и структур данных, " +
            "который могли бы использовать преподаватели и студенты - от первокурсников " +
            "до аспирантов. Книга может быть использована и для самообразования " +
            "профессиональных программистов.";

	// Пример образца, который мы ищем в исходной строке, и который в строке есть.
	static final String whatExists = "программист";
	// Пример образца, который мы ищем в исходной строке, и которого в строке нет.
	static final String whatNotFound = "программа";
	
    // Модуль: простое число, не превосходящее MAXLONG / sizeof(char).
	// Используется в алгоритме Рабина - Карпа.
    private static final long q = 36028797018963913L;

    // Длина таблицы символов и соответствующая hash-функция для символов, которые
    // используются в алгоритме Бойера - Мура.
    private static final int shLen = 256;
    private static int hash(char c) { return c & 0xFF; }

    /**
     * Алгоритм "наивного" поиска подстроки в строке.
     *
     * @param where Исходная строка, в которой ищут.
     * @param what  Подстрока, которую ищут.
     * @return      Найденный индекс начала первого вхождения образца в строку
     *              или -1, если образец не найден в строке
     */
    public static int simpleSearch(String where, String what) {
        int n = where.length();
        int m = what.length();
        extLoop:    // Внешний цикл поиска в исходной строке
        for (int i = 0; i <= n-m; i++) {
            // Внутренний цикл сравнения:
            for (int j = 0; j < m; j++) {
                if (where.charAt(i+j) != what.charAt(j))
                    continue extLoop;
            }
            return i;
        }
        return -1;
    }

    /**
     * Алгоритм Рабина - Карпа поиска подстроки в строке. Для образца вычисляется значение
     * некоторой hash-функции, а затем для фрагментов исходной строки также вычисляются
     * значения этой же hash-функции. Если значения hash-функций совпали, то совпадение
     * образца с фрагментом исходной строки проверяется посимвольно.
     *
     * @param where Исходная строка, в которой ищут.
     * @param what  Подстрока, которую ищут.
     * @return      Найденный индекс начала первого вхождения образца в строку
     *              или -1, если образец не найден в строке
     */
    public static int rabinKarp(String where, String what) {
        int n = where.length();   // Длина строки, в которой происходит поиск
        int m = what.length();    // Длина подстроки
        
        // Каждая позиция в строке образца имеет свой hash-модуль. При сдвиге по
        // исходной строке значение hash-функции вычисляется по двум символам -
        // вытесняемому символу и добавляемому символу. При этом код символа
        // умножается на hash-модуль, соответствующий позиции этого символа.
        long h = 1;               // hash-модуль вытесняемой буквы
        for (int k = 1; k <= m-1; k++) {
            h <<= 8;
            h %= q;
        }

        long p = 0;     // hash-функция подстроки - вычисляется один раз
        long t = 0;     // hash-функция очередного фрагмента подстроки
        for (int k = 0; k < m; k++) {
            p = ((p << 8) | (byte)what.charAt(k)) % q;
            t = ((t << 8) | (byte)where.charAt(k)) % q;
        }

        // Внешний цикл по исходной строке
        extLoop:
        for (int i = 0; i <= n-m; i++) {
            if (p == t) {
                // hash-функции фрагмента исходной строки и образца совпали;
            	// проверяем, не холостое ли это срабатывание
                for (int j = 0; j < m; j++) {
                    if (where.charAt(i+j) != what.charAt(j)) {
                        // символы не совпали - продолжаем поиск
                        continue extLoop;
                    }
                }
                // подстрока найдена!
                return i;
            } else if (i < n-m) {
                // сдвиг по исходной строке
                t = (((t - h * (byte)where.charAt(i)) << 8) | (byte)where.charAt(i+m)) % q;
            }
        }
        return -1;
    }

    /**
     * Алгоритм Бойена - Мура поиска подстроки в строке. Поиск образца происходит, начиная
     * каждый раз с конца образца. При несовпадении символов вычисляется максимально
     * возможный сдвиг образца, который вычисляется по предварительно построенной таблице
     * символов образца, содержащей расстояния от этих символов до конца образца.
     *
     * @param where Исходная строка, в которой ищут.
     * @param what  Подстрока, которую ищут.
     * @return      Найденный индекс начала первого вхождения образца в строку
     *              или -1, если образец не найден в строке
     */
    public static int boyerMoore(String where, String what) {
        int n = where.length();    // Длина исходной строки
        int m = what.length();     // Длина образца
        
        // Таблица сдвигов формируется в предположении, что коды символов
        // находятся в диапазоне от 0 до 255, однако для символов с большими кодами
        // производится хеширование.
        
        // Таблица сдвигов
        HashMap<Character, Integer> shifts = new HashMap<>();
        // Для символов из образца сдвиг равен расстоянию от
        // последнего вхождения символа в образец до конца образца
        for (int i = 0; i < m-1; i++) {
            shifts.put(what.charAt(i), m-i-1);
        }

        for (int i = 0; i <= n-m; ) {
            // Сравнение начинается с конца образца
            for (int j = m-1; j>=0; j--) {
                if (where.charAt(i+j) == what.charAt(j)) {
                	// Проверяем, не найден ли уже образец.
                    if (j == 0) return i;
                } else {
                	// Нашли несовпадение, осуществляем сдвиг
                    break;
                }
            }
            // Сдвиг производится в соответствии с кодом последнего из сравниваемых символов
            Integer shift = shifts.get(where.charAt(i+m-1));
            if (shift == null) shift = m;
            i += shift;
        }
        return -1;
    }

    /**
     * Алгоритм Кнута - Морриса - Пратта поиска подстроки в строке.
     * В алгоритме строится таблица префиксов, в которой для каждого символа образца
     * кроме последнего указывается длина участка образца, который заканчивается
     * этим символом и совпадает с префиксом строки-образца.
     *
     * @param where Исходная строка, в которой ищут.
     * @param what  Подстрока, которую ищут.
     * @return      Найденный индекс начала первого вхождения образца в строку
     *              или -1, если образец не найден в строке
     */
    public static int knuthMorrisPratt(String where, String what) {
        int n = where.length();   // Длина строки, в которой происходит поиск
        int m = what.length();    // Длина подстроки
    
        // Формирование таблицы сдвигов происходит по тому же алгоритму,
        // что и основной поиск, при этом используется уже построенная
        // часть таблицы сдвигов.
        int[] table = new int[m-1];
        table[0] = 0;
        int shift = 0;
        for (int q = 1; q < m - 1; q++) {
            while (shift > 0 && what.charAt(shift) != what.charAt(q)) {
                shift = table[shift-1];
            }
            if (what.charAt(shift) == what.charAt(q)) shift++;
            table[q] = shift;
        }
      
        // Поиск с использованием таблицы сдвигов
        shift = 0;
        for (int i = 0; i < n; i++) {
            while (shift > 0 && what.charAt(shift) != where.charAt(i)) {
                shift = table[shift-1];
            }
            if (what.charAt(shift) == where.charAt(i)) shift++;
            if (shift == m) return i-m+1;    // подстрока найдена
        }
        return -1;    // подстрока не найдена
    }
    
    /**
     * Проверяет, верно ли, что заданный образец является подстрокой заданной исходной строки,
     * причем входит в строку, начиная с заданного индекса. Функция используется только
     * для тестирования.
     * 
     * @param where	Исходная строка
     * @param what	Образец
     * @param index	Индекс
     * @return		true, если образец является подстрокой исходной строки, иначе false. 
     */
    private static boolean isSubstring(String where, String what, int index) {
    	int n = where.length();
    	int m = what.length();
    	if (index < 0 || index > n - m) return false;
    	for (int i = 0; i < m; ++i) {
    		if (where.charAt(index + i) != what.charAt(i)) return false;
    	}
    	return true;
    }

    public static void main(String[] args) {
    	// Проверяем, что все алгоритмы дают правильный результат.
    	int indFound = simpleSearch(where, whatExists);
    	int indNotFound = simpleSearch(where, whatNotFound);
    	System.out.println("Наивный алгоритм работает " + 
    			(indNotFound == -1 && isSubstring(where, whatExists, indFound) ? "правильно" : "неправильно"));
    	
    	indFound = rabinKarp(where, whatExists);
    	indNotFound = rabinKarp(where, whatNotFound);
    	System.out.println("Алгоритм Рабина - Карпа работает " + 
    			(indNotFound == -1 && isSubstring(where, whatExists, indFound) ? "правильно" : "неправильно"));

    	indFound = boyerMoore(where, whatExists);
    	indNotFound = boyerMoore(where, whatNotFound);
    	System.out.println("Алгоритм Бойера - Мура работает " + 
    			(indNotFound == -1 && isSubstring(where, whatExists, indFound) ? "правильно" : "неправильно"));

    	indFound = knuthMorrisPratt(where, whatExists);
    	indNotFound = knuthMorrisPratt(where, whatNotFound);
    	System.out.println("Алгоритм Кнута - Морриса - Пратта работает " + 
    			(indNotFound == -1 && isSubstring(where, whatExists, indFound) ? "правильно" : "неправильно"));

   }
}
