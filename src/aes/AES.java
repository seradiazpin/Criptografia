/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

import java.math.BigInteger;

/**
 *
 * @author JuKa
 */
public class AES {

    final static String[][] sbox = {{"63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76"},
    {"ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0"},
    {"b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15"},
    {"04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75"},
    {"09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84"},
    {"53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf"},
    {"d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8"},
    {"51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2"},
    {"cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73"},
    {"60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db"},
    {"e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79"},
    {"e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08"},
    {"ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a"},
    {"70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e"},
    {"e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df"},
    {"8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16"}};

    final static String[][] invSbox = {{"52", "09", "6a", "d5", "30", "36", "a5", "38", "bf", "40", "a3", "9e", "81", "f3", "d7", "fb"},
    {"7c", "e3", "39", "82", "9b", "2f", "ff", "87", "34", "8e", "43", "44", "c4", "de", "e9", "cb"},
    {"54", "7b", "94", "32", "a6", "c2", "23", "3d", "ee", "4c", "95", "0b", "42", "fa", "c3", "4e"},
    {"08", "2e", "a1", "66", "28", "d9", "24", "b2", "76", "5b", "a2", "49", "6d", "8b", "d1", "25"},
    {"72", "f8", "f6", "64", "86", "68", "98", "16", "d4", "a4", "5c", "cc", "5d", "65", "b6", "92"},
    {"6c", "70", "48", "50", "fd", "ed", "b9", "da", "5e", "15", "46", "57", "a7", "8d", "9d", "84"},
    {"90", "d8", "ab", "00", "8c", "bc", "d3", "0a", "f7", "e4", "58", "05", "b8", "b3", "45", "06"},
    {"d0", "2c", "1e", "8f", "ca", "3f", "0f", "02", "c1", "af", "bd", "03", "01", "13", "8a", "6b"},
    {"3a", "91", "11", "41", "4f", "67", "dc", "ea", "97", "f2", "cf", "ce", "f0", "b4", "e6", "73"},
    {"96", "ac", "74", "22", "e7", "ad", "35", "85", "e2", "f9", "37", "e8", "1c", "75", "df", "6e"},
    {"47", "f1", "1a", "71", "1d", "29", "c5", "89", "6f", "b7", "62", "0e", "aa", "18", "be", "1b"},
    {"fc", "56", "3e", "4b", "c6", "d2", "79", "20", "9a", "db", "c0", "fe", "78", "cd", "5a", "f4"},
    {"1f", "dd", "a8", "33", "88", "07", "c7", "31", "b1", "12", "10", "59", "27", "80", "ec", "5f"},
    {"60", "51", "7f", "a9", "19", "b5", "4a", "0d", "2d", "e5", "7a", "9f", "93", "c9", "9c", "ef"},
    {"a0", "e0", "3b", "4d", "ae", "2a", "f5", "b0", "c8", "eb", "bb", "3c", "83", "53", "99", "61"},
    {"17", "2b", "04", "7e", "ba", "77", "d6", "26", "e1", "69", "14", "63", "55", "21", "0c", "7d"}};

    final static String[][] eTable = {{"01", "03", "05", "0f", "11", "33", "55", "ff", "1a", "2e", "72", "96", "a1", "f8", "13", "35"},
    {"5f", "e1", "38", "48", "d8", "73", "95", "a4", "f7", "02", "06", "0a", "1e", "22", "66", "aa"},
    {"e5", "34", "5c", "e4", "37", "59", "eb", "26", "6a", "be", "d9", "70", "90", "ab", "e6", "31"},
    {"53", "f5", "04", "0c", "14", "3c", "44", "cc", "4f", "d1", "68", "b8", "d3", "6e", "b2", "cd"},
    {"4c", "d4", "67", "a9", "e0", "3b", "4d", "d7", "62", "a6", "f1", "08", "18", "28", "78", "88"},
    {"83", "9e", "b9", "d0", "6b", "bd", "dc", "7f", "81", "98", "b3", "ce", "49", "db", "76", "9a"},
    {"b5", "c4", "57", "f9", "10", "30", "50", "f0", "0b", "1d", "27", "69", "bb", "d6", "61", "a3"},
    {"fe", "19", "2b", "7d", "87", "92", "ad", "ec", "2f", "71", "93", "ae", "e9", "20", "60", "a0"},
    {"fb", "16", "3a", "4e", "d2", "6d", "b7", "c2", "5d", "e7", "32", "56", "fa", "15", "3f", "41"},
    {"c3", "5e", "e2", "3d", "47", "c9", "40", "c0", "5b", "ed", "2c", "74", "9c", "bf", "da", "75"},
    {"9f", "ba", "d5", "64", "ac", "ef", "2a", "7e", "82", "9d", "bc", "df", "7a", "8e", "89", "80"},
    {"9b", "b6", "c1", "58", "e8", "23", "65", "af", "ea", "25", "6f", "b1", "c8", "43", "c5", "54"},
    {"fc", "1f", "21", "63", "a5", "f4", "07", "09", "1b", "2d", "77", "99", "b0", "cb", "46", "ca"},
    {"45", "cf", "4a", "de", "79", "8b", "86", "91", "a8", "e3", "3e", "42", "c6", "51", "f3", "0e"},
    {"12", "36", "5a", "ee", "29", "7b", "8d", "8c", "8f", "8a", "85", "94", "a7", "f2", "0d", "17"},
    {"39", "4b", "dd", "7c", "84", "97", "a2", "fd", "1c", "24", "6c", "b4", "c7", "52", "f6", "01"}};

    final static String[][] lTable = {{"00", "00", "19", "01", "32", "02", "1a", "c6", "4b", "c7", "1b", "68", "33", "ee", "df", "03"},
    {"64", "04", "e0", "0e", "34", "8d", "81", "ef", "4c", "71", "08", "c8", "f8", "69", "1c", "c1"},
    {"7d", "c2", "1d", "b5", "f9", "b9", "27", "6a", "4d", "e4", "a6", "72", "9a", "c9", "09", "78"},
    {"65", "2f", "8a", "05", "21", "0f", "e1", "24", "12", "f0", "82", "45", "35", "93", "da", "8e"},
    {"96", "8f", "db", "bd", "36", "d0", "ce", "94", "13", "5c", "d2", "f1", "40", "46", "83", "38"},
    {"66", "dd", "fd", "30", "bf", "06", "8b", "62", "b3", "25", "e2", "98", "22", "88", "91", "10"},
    {"7e", "6e", "48", "c3", "a3", "b6", "1e", "42", "3a", "6b", "28", "54", "fa", "85", "3d", "ba"},
    {"2b", "79", "0a", "15", "9b", "9f", "5e", "ca", "4e", "d4", "ac", "e5", "f3", "73", "a7", "57"},
    {"af", "58", "a8", "50", "f4", "ea", "d6", "74", "4f", "ae", "e9", "d5", "e7", "e6", "ad", "e8"},
    {"2c", "d7", "75", "7a", "eb", "16", "0b", "f5", "59", "cb", "5f", "b0", "9c", "a9", "51", "a0"},
    {"7f", "0c", "f6", "6f", "17", "c4", "49", "ec", "d8", "43", "1f", "2d", "a4", "76", "7b", "b7"},
    {"cc", "bb", "3e", "5a", "fb", "60", "b1", "86", "3b", "52", "a1", "6c", "aa", "55", "29", "9d"},
    {"97", "b2", "87", "90", "61", "be", "dc", "fc", "bc", "95", "cf", "cd", "37", "3f", "5b", "d1"},
    {"53", "39", "84", "3c", "41", "a2", "6d", "47", "14", "2a", "9e", "5d", "56", "f2", "d3", "ab"},
    {"44", "11", "92", "d9", "23", "20", "2e", "89", "b4", "7c", "b8", "26", "77", "99", "e3", "a5"},
    {"67", "4a", "ed", "de", "c5", "31", "fe", "18", "0d", "63", "8c", "80", "c0", "f7", "70", "07"}};

    final static String[] roundConst = {"01", "02", "04", "08", "10", "20", "40", "80", "1b", "36", "6c"};

    public static void main(String[] args) {

        String strMessage = "41 45 53 20 65 73 20 6d 75 79 20 66 61 63 69 6c";
        String strKey = "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c";

        String[][] message = strColToMatrix(strMessage);
        String[][] key = strColToMatrix(strKey);
        System.out.println("Generated Keys");
        String[][][] keys = keyGenerator(key);
        System.out.println("Encrypted message");
        String[][] encrypt = encryptMessage(message, key, keys);
        System.out.println("Decrypted message");
        printM(decryptMessage(encrypt, key, keys));

    }

    static String[][] encryptMessage(String[][] message, String[][] masterKey, String[][][] keys) {
        String[][] encrypt = xorHex(masterKey, message);

        for (int i = 0; i < 9; i++) {
            encrypt = xorHex(keys[i], mixColumn(shiftRow(sBox(encrypt))));
        }
        printM(xorHex(shiftRow(sBox(encrypt)), keys[9]));
        return xorHex(shiftRow(sBox(encrypt)), keys[9]);
    }

    static String[][][] keyGenerator(String[][] keyM) {
        String[] columns = new String[44];
        String[] keyColumns = matrixToCol(keyM);
        String aux = "";
        for (int i = 0; i < keyColumns.length; i++) {
            columns[i] = keyColumns[i];
        }

        String keys[][][] = new String[10][4][4];
        for (int i = 4; i < 44; i++) {
            if (0 == i % 4) {
                columns[i] = (AES.xorHex(columns[i - 4], AES.xorHex(roundConst((i - 4) / 4), AES.sBox(shiftLeft(columns[i - 1], 2)))));
            } else {
                columns[i] = AES.xorHex(columns[i - 4], columns[i - 1]);
            }

            aux += columns[i].substring(0, 2) + " " + columns[i].substring(2, 4) + " "
                    + columns[i].substring(4, 6) + " " + columns[i].substring(6, 8);
            if (3 == i % 4) {
                keys[(i - 4) / 4] = strColToMatrix(aux);
                printM(keys[(i - 4) / 4]);
                aux = "";
            } else {
                aux += " ";
            }
        }
        return keys;
    }

    public static void printM(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {

            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static String[][] strColToMatrix(String matrixString) {
        String[] array = matrixString.split(" ");
        String[][] matrix = new String[4][4];
        for (int i = 0; i < array.length; i++) {
            matrix[i % 4][i / 4] = array[i];
        }
        return matrix;
    }

    public static String[][] strRowToMatrix(String matrixString) {
        String[] array = matrixString.split(" ");
        String[][] matrix = new String[4][4];
        for (int i = 0; i < array.length; i++) {
            matrix[i / 4][i % 4] = array[i];
        }
        return matrix;
    }

    static String hexToDec(String s) {
        return new BigInteger(s, 16).toString();

    }

    static String xorHex(String one, String two) {
        int size = one.length();
        BigInteger oneDec = new BigInteger(hexToDec(one));
        BigInteger twoDec = new BigInteger(hexToDec(two));
        String aux = oneDec.xor(twoDec).toString(16);
        while (aux.length() < size) {
            aux = "0" + aux;
        }
        return aux;
    }

    static String[][] xorHex(String[][] one, String[][] two) {
        String[][] xor = new String[4][4];
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {
                xor[i][j] = AES.xorHex(one[i][j], two[i][j]);
            }
        }
        return xor;
    }

    static String shiftLeft(String one, int i) {
        int size = one.length();
        return one.substring(i, size) + one.substring(0, i);
    }

    static String sBox(String hex) {
        String boxed = "";
        for (int i = 0; i < 8; i = i + 2) {
            boxed += sbox[Integer.parseInt(hexToDec(hex.substring(i, i + 1)))][Integer.parseInt(hexToDec(hex.substring(i + 1, i + 2)))];
        }
        return boxed;
    }

    static String[][] sBox(String[][] matrix) {
        String[][] boxed = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boxed[i][j] = sbox[Integer.parseInt(hexToDec(matrix[i][j].substring(0, 1)))][Integer.parseInt(hexToDec(matrix[i][j].substring(1, 2)))];
            }
        }
        return boxed;
    }

    static String roundConst(int i) {
        return roundConst[i] + "000000";
    }

    static String[] matrixToCol(String[][] matrix) {
        String aux = "";
        String[] columns = new String[matrix.length];
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                aux += matrix[j][i];
            }
            columns[i] = aux;
            aux = "";
        }
        return columns;
    }

    static String[] matrixToRow(String[][] matrix) {
        String aux = "";
        String[] rows = new String[matrix.length];
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                aux += matrix[i][j];
            }
            rows[i] = aux;
            aux = "";
        }
        return rows;
    }

    static BigInteger lTableValueDec(String index) {
        return new BigInteger(lTable[Integer.parseInt(hexToDec(index.substring(0, 1)))][Integer.parseInt(hexToDec(index.substring(1, 2)))], 16);
    }

    static BigInteger eTableValueDec(String index) {
        return new BigInteger(eTable[Integer.parseInt(hexToDec(index.substring(0, 1)))][Integer.parseInt(hexToDec(index.substring(1, 2)))], 16);
    }

    static String multGalois(String one, String two) {
        String sumHex = lTableValueDec(one).add(lTableValueDec(two)).mod(BigInteger.valueOf(255)).toString(16);
        while (sumHex.length() < 2) {
            sumHex = "0" + sumHex;
        }
        String mult = eTableValueDec(sumHex).toString(16);
        while (mult.length() < 2) {
            mult = "0" + mult;
        }
        return mult;
    }

    static String[][] mixColumn(String[][] matrix) {
        String[][] constant = {{"02", "03", "01", "01"}, {"01", "02", "03", "01"}, {"01", "01", "02", "03"}, {"03", "01", "01", "02"}};
        String[][] mult = new String[4][4];
        String[] ColumnMatrix = matrixToCol(matrix);
        String[] RowConstant = matrixToRow(constant);

        for (int i = 0; i < mult.length; i++) {
            for (int j = 0; j < mult[0].length; j++) {
                String one = multGalois(ColumnMatrix[j].substring(0, 2), RowConstant[i].substring(0, 2));
                String two = multGalois(ColumnMatrix[j].substring(2, 4), RowConstant[i].substring(2, 4));
                String three = multGalois(ColumnMatrix[j].substring(4, 6), RowConstant[i].substring(4, 6));
                String four = multGalois(ColumnMatrix[j].substring(6, 8), RowConstant[i].substring(6, 8));
                mult[i][j] = AES.xorHex(one, AES.xorHex(two, AES.xorHex(three, four)));
            }
        }

        return mult;
    }

    static String[][] shiftRow(String[][] unshift) {
        String[] rows = matrixToRow(unshift);
        String matrixString = "";

        for (int i = 0; i < 4; i++) {
            rows[i] = shiftLeft(rows[i], i * 2);
            matrixString += rows[i].substring(0, 2) + " " + rows[i].substring(2, 4) + " " + rows[i].substring(4, 6) + " " + rows[i].substring(6, 8) + " ";

        }

        return strRowToMatrix(matrixString.substring(0, matrixString.length() - 1));
    }

    static String[][] invMixColumn(String[][] matrix) {
        String[][] constant = {{"0e", "0b", "0d", "09"}, {"09", "0e", "0b", "0d"}, {"0d", "09", "0e", "0b"}, {"0b", "0d", "09", "0e"}};
        String[][] mult = new String[4][4];
        String[] ColumnMatrix = matrixToCol(matrix);
        String[] RowConstant = matrixToRow(constant);

        for (int i = 0; i < mult.length; i++) {
            for (int j = 0; j < mult[0].length; j++) {
                String one = multGalois(ColumnMatrix[j].substring(0, 2), RowConstant[i].substring(0, 2));
                String two = multGalois(ColumnMatrix[j].substring(2, 4), RowConstant[i].substring(2, 4));
                String three = multGalois(ColumnMatrix[j].substring(4, 6), RowConstant[i].substring(4, 6));
                String four = multGalois(ColumnMatrix[j].substring(6, 8), RowConstant[i].substring(6, 8));
                mult[i][j] = AES.xorHex(one, AES.xorHex(two, AES.xorHex(three, four)));
            }
        }

        return mult;
    }

    static String[][] invShiftRow(String[][] unshift) {
        String[] rows = matrixToRow(unshift);
        String matrixString = "";

        for (int i = 0; i < 4; i++) {
            rows[i] = shiftLeft(rows[i], 8 - (i * 2));
            matrixString += rows[i].substring(0, 2) + " " + rows[i].substring(2, 4) + " " + rows[i].substring(4, 6) + " " + rows[i].substring(6, 8) + " ";

        }

        return strRowToMatrix(matrixString.substring(0, matrixString.length() - 1));
    }

    static String[][] invSbox(String[][] matrix) {
        String[][] boxed = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boxed[i][j] = invSbox[Integer.parseInt(hexToDec(matrix[i][j].substring(0, 1)))][Integer.parseInt(hexToDec(matrix[i][j].substring(1, 2)))];
            }
        }
        return boxed;
    }

    static String[][] decryptMessage(String[][] encrypt, String[][] masterKey, String[][][] keys) {

        String[][] decrypt = invSbox(invShiftRow(xorHex(encrypt, keys[9])));

        for (int i = 8; i >= 0; i--) {
            decrypt = invSbox(invShiftRow(invMixColumn(xorHex(decrypt, keys[i]))));
        }

        return xorHex(decrypt, masterKey);
    }
}
