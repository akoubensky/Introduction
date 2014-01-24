package org.ifmo;

/**
 * ������ ����� ������ ������������ ����� ������������ ��������� ������ ��������� � �������� ������.
 */
public class Substrings {
	// ������ ������, � ������� ������������ �����
	static final String where = 
			"�� ��������� �������� ������� �� ���������� ���������� � �������� ������, " +
            "������� ����� �� ������������ ������������� � �������� - �� �������������� " +
            "�� ����������. ����� ����� ���� ������������ � ��� ��������������� " +
            "���������������� �������������.";

	// ������ �������, ������� �� ���� � �������� ������, � ������� � ������ ����.
	static final String whatExists = "�����������";
	// ������ �������, ������� �� ���� � �������� ������, � �������� � ������ ���.
	static final String whatNotFound = "���������";
	
    // ������: ������� �����, �� ������������� MAXLONG / sizeof(char).
	// ������������ � ��������� ������ - �����.
    private static final long q = 36028797018963913L;

    // ����� ������� �������� � ��������������� hash-������� ��� ��������, �������
    // ������������ � ��������� ������ - ����.
    private static final int shLen = 256;
    private static int hash(char c) { return c & 0xFF; }

    /**
     * �������� "��������" ������ ��������� � ������.
     *
     * @param where �������� ������, � ������� ����.
     * @param what  ���������, ������� ����.
     * @return      ��������� ������ ������ ������� ��������� ������� � ������
     *              ��� -1, ���� ������� �� ������ � ������
     */
    public static int simpleSearch(String where, String what) {
        int n = where.length();
        int m = what.length();
        extLoop:    // ������� ���� ������ � �������� ������
        for (int i = 0; i <= n-m; i++) {
            // ���������� ���� ���������:
            for (int j = 0; j < m; j++) {
                if (where.charAt(i+j) != what.charAt(j))
                    continue extLoop;
            }
            return i;
        }
        return -1;
    }

    /**
     * �������� ������ - ����� ������ ��������� � ������. ��� ������� ����������� ��������
     * ��������� hash-�������, � ����� ��� ���������� �������� ������ ����� �����������
     * �������� ���� �� hash-�������. ���� �������� hash-������� �������, �� ����������
     * ������� � ���������� �������� ������ ����������� �����������.
     *
     * @param where �������� ������, � ������� ����.
     * @param what  ���������, ������� ����.
     * @return      ��������� ������ ������ ������� ��������� ������� � ������
     *              ��� -1, ���� ������� �� ������ � ������
     */
    public static int rabinKarp(String where, String what) {
        int n = where.length();   // ����� ������, � ������� ���������� �����
        int m = what.length();    // ����� ���������
        
        // ������ ������� � ������ ������� ����� ���� hash-������. ��� ������ ��
        // �������� ������ �������� hash-������� ����������� �� ���� �������� -
        // ������������ ������� � ������������ �������. ��� ���� ��� �������
        // ���������� �� hash-������, ��������������� ������� ����� �������.
        long h = 1;               // hash-������ ����������� �����
        for (int k = 1; k <= m-1; k++) {
            h <<= 8;
            h %= q;
        }

        long p = 0;     // hash-������� ��������� - ����������� ���� ���
        long t = 0;     // hash-������� ���������� ��������� ���������
        for (int k = 0; k < m; k++) {
            p = ((p << 8) | (byte)what.charAt(k)) % q;
            t = ((t << 8) | (byte)where.charAt(k)) % q;
        }

        // ������� ���� �� �������� ������
        extLoop:
        for (int i = 0; i <= n-m; i++) {
            if (p == t) {
                // hash-������� ��������� �������� ������ � ������� �������;
            	// ���������, �� �������� �� ��� ������������
                for (int j = 0; j < m; j++) {
                    if (where.charAt(i+j) != what.charAt(j)) {
                        // ������� �� ������� - ���������� �����
                        continue extLoop;
                    }
                }
                // ��������� �������!
                return i;
            } else if (i < n-m) {
                // ����� �� �������� ������
                t = (((t - h * (byte)where.charAt(i)) << 8) | (byte)where.charAt(i+m)) % q;
            }
        }
        return -1;
    }

    /**
     * �������� ������ - ���� ������ ��������� � ������. ����� ������� ����������, �������
     * ������ ��� � ����� �������. ��� ������������ �������� ����������� �����������
     * ��������� ����� �������, ������� ����������� �� �������������� ����������� �������
     * �������� �������, ���������� ���������� �� ���� �������� �� ����� �������.
     *
     * @param where �������� ������, � ������� ����.
     * @param what  ���������, ������� ����.
     * @return      ��������� ������ ������ ������� ��������� ������� � ������
     *              ��� -1, ���� ������� �� ������ � ������
     */
    public static int boyerMoore(String where, String what) {
        int n = where.length();    // ����� �������� ������
        int m = what.length();     // ����� �������
        
        // ������� ������� ����������� � �������������, ��� ���� ��������
        // ��������� � ��������� �� 0 �� 255, ������ ��� �������� � �������� ������
        // ������������ �����������.
        
        // ������� �������
        int[] shifts = new int[shLen];
        // ��� ��������, ������������� � �������, ����� ����� ����� �������
        for (int i = 0; i < shLen; i++) {
            shifts[i] = m;
        }
        // ��� �������� �� ������� ����� ����� ���������� ��
        // ���������� ��������� ������� � ������� �� ����� �������
        for (int i = 0; i < m-1; i++) {
            shifts[hash(what.charAt(i))] = m-i-1;
        }

        for (int i = 0; i <= n-m; ) {
            // ��������� ���������� � ����� �������
            for (int j = m-1; j>=0; j--) {
                if (where.charAt(i+j) == what.charAt(j)) {
                	// ���������, �� ������ �� ��� �������.
                    if (j == 0) return i;
                } else {
                	// ����� ������������, ������������ �����
                    break;
                }
            }
            // ����� ������������ � ������������ � ����� ���������� �� ������������ ��������
            i += shifts[hash(where.charAt(i+m-1))];
        }
        return -1;
    }

    /**
     * �������� ����� - ������� - ������ ������ ��������� � ������.
     * � ��������� �������� ������� ���������, � ������� ��� ������� ������� �������
     * ����� ���������� ����������� ����� ������� �������, ������� �������������
     * ���� �������� � ��������� � ��������� ������-�������.
     *
     * @param where �������� ������, � ������� ����.
     * @param what  ���������, ������� ����.
     * @return      ��������� ������ ������ ������� ��������� ������� � ������
     *              ��� -1, ���� ������� �� ������ � ������
     */
    public static int knuthMorrisPratt(String where, String what) {
        int n = where.length();   // ����� ������, � ������� ���������� �����
        int m = what.length();    // ����� ���������
    
        // ������������ ������� ������� ���������� �� ���� �� ���������,
        // ��� � �������� �����, ��� ���� ������������ ��� �����������
        // ����� ������� �������.
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
      
        // ����� � �������������� ������� �������
        shift = 0;
        for (int i = 0; i < n; i++) {
            while (shift > 0 && what.charAt(shift) != where.charAt(i)) {
                shift = table[shift-1];
            }
            if (what.charAt(shift) == where.charAt(i)) shift++;
            if (shift == m) return i-m+1;    // ��������� �������
        }
        return -1;    // ��������� �� �������
    }
    
    /**
     * ���������, ����� ��, ��� �������� ������� �������� ���������� �������� �������� ������,
     * ������ ������ � ������, ������� � ��������� �������. ������� ������������ ������
     * ��� ������������.
     * 
     * @param where	�������� ������
     * @param what	�������
     * @param index	������
     * @return		true, ���� ������� �������� ���������� �������� ������, ����� false. 
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
    	// ���������, ��� ��� ��������� ���� ���������� ���������.
    	int indFound = simpleSearch(where, whatExists);
    	int indNotFound = simpleSearch(where, whatNotFound);
    	System.out.println("������� �������� �������� " + 
    			(indNotFound == -1 && isSubstring(where, whatExists, indFound) ? "���������" : "�����������"));
    	
    	indFound = rabinKarp(where, whatExists);
    	indNotFound = rabinKarp(where, whatNotFound);
    	System.out.println("�������� ������ - ����� �������� " + 
    			(indNotFound == -1 && isSubstring(where, whatExists, indFound) ? "���������" : "�����������"));

    	indFound = boyerMoore(where, whatExists);
    	indNotFound = boyerMoore(where, whatNotFound);
    	System.out.println("�������� ������ - ���� �������� " + 
    			(indNotFound == -1 && isSubstring(where, whatExists, indFound) ? "���������" : "�����������"));

    	indFound = knuthMorrisPratt(where, whatExists);
    	indNotFound = knuthMorrisPratt(where, whatNotFound);
    	System.out.println("�������� ����� - ������� - ������ �������� " + 
    			(indNotFound == -1 && isSubstring(where, whatExists, indFound) ? "���������" : "�����������"));

   }
}
