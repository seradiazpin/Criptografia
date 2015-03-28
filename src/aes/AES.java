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

    final static String roundC[] = {"01", "02", "04", "08", "10", "20", "40", "80", "1b", "36", "6c"};

    public static void main(String[] args) {

        String message = "41 45 53 20 65 73 20 6d 75 79 20 66 61 63 69 6c";
        String key = "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c";
        String[][] messageM = toMatrix(message);
        String[][] keyM = toMatrix(key);
        keyGenerator(keyM);

    }

    static String[][][] keyGenerator(String[][] keyM) {
        String[] columns = new String[44];
        String aux = new String();
        for (int i = 0; i < keyM[0].length; i++) {
            for (int j = 0; j < keyM.length; j++) {
                aux += keyM[j][i];
            }
            columns[i] = aux;
            aux = "";
        }

        String keys[][][] = new String[10][4][4];
        for (int i = 4; i < 44; i++) {
            if (0 == i % 4) {
                columns[i] = (xorHex(columns[i - 4], xorHex(roundC((i - 4) / 4), sbox(shiftLeft(columns[i - 1], 2)))));
            } else {
                columns[i] = xorHex(columns[i - 4], columns[i - 1]);
            }

            aux += columns[i].substring(0, 2) + " " + columns[i].substring(2, 4) + " "
                    + columns[i].substring(4, 6) + " " + columns[i].substring(6, 8);
            if (3 == i % 4) {
                keys[(i - 4) / 4] = toMatrix(aux);
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

    public static String[][] toMatrix(String text) {
        String[] array = text.split(" ");
        String[][] matriz = new String[4][4];
        for (int i = 0; i < array.length; i++) {
            matriz[i % 4][i / 4] = array[i];
        }
        return matriz;
    }

    static String hexToDec(String s) {
        return new BigInteger(s, 16).toString();

    }

    static String xorHex(String one, String two) {
        BigInteger oneDec = new BigInteger(hexToDec(one));
        BigInteger twoDec = new BigInteger(hexToDec(two));
        String aux = oneDec.xor(twoDec).toString(16);
        while (aux.length() < 8) {
            aux = "0" + aux;
        }
        return aux;
    }

    static String shiftLeft(String one, int i) {
        int size = one.length();
        return one.substring(i, size) + one.substring(0, i);
    }

    static String sbox(String hex) {
        String subWord = "";
        for (int i = 0; i < 8; i = i + 2) {
            subWord += sbox[Integer.parseInt(hexToDec(hex.substring(i, i + 1)))][Integer.parseInt(hexToDec(hex.substring(i + 1, i + 2)))];
        }
        return subWord;
    }

    static String roundC(int i) {

        return roundC[i] + "000000";
    }
}
