/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Dictionary;
import java.util.Random;

/**
 *
 * @author sergioalejandrodiazpinilla
 */
public class DES {


    final static int[] PC1 = {57, 49, 41, 33, 25, 17, 9, 01, 58, 50, 42, 34, 26, 18, 10, 02, 59, 51, 43, 35, 27, 19, 11, 03, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 07, 62, 54, 46, 38, 30, 22, 14, 06, 61, 53, 45, 37, 29, 21, 13, 05, 28, 20, 12, 04};
    final static int[] PC2 = {14, 17, 11, 24, 01, 05, 03, 28, 15, 06, 21, 10, 23, 19, 12, 04, 26, 8, 16, 07, 27, 20, 13, 02, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
    final static int[] IP = {58, 50, 42, 34, 26, 18, 10, 02, 60, 52, 44, 36, 28, 20, 12, 04, 62, 54, 46, 38, 30, 22, 14, 06, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 01, 59, 51, 43, 35, 27, 19, 11, 03, 61, 53, 45, 37, 29, 21, 13, 05, 63, 55, 47, 39, 31, 23, 15, 07};
    final static int[] INVIP = {40, 8, 48, 16, 56, 24, 64, 32, 39, 07, 47, 15, 55, 23, 63, 31, 38, 06, 46, 14, 54, 22, 62, 30, 37, 05, 45, 13, 53, 21, 61, 29, 36, 04, 44, 12, 52, 20, 60, 28, 35, 03, 43, 11, 51, 19, 59, 27, 34, 02, 42, 10, 50, 18, 58, 26, 33, 01, 41, 9, 49, 17, 57, 25};
    final static int[] E = {32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1};
    final static int[] P = {16,07,20,21,29,12,28,17,01,15,23,26,05,18,31,10,02,8,24,14,32,27,03,9,19,13,30,06,22,11,04,25};
    final static Sbox sBoxes = new Sbox();
    
    static String [] Stages = new String[4];
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        String cText = Encryption("0123456789ABCDEF", "133457799BBCDFF1");
        System.out.println("Ctext:"+cText);
        System.out.println(Decryption(cText, "133457799BBCDFF1"));
        
    }
    public static String Encryption(String mesage,String keyS){
        byte[] textH = new BigInteger(mesage, 16).toByteArray();
        String[] key = keyGenerator(keyS);
        String cText ="";
        for (int i = 0; i < textH.length; i++) {
            String s1 = String.format("%8s", Integer.toBinaryString(textH[i] & 0xFF)).replace(' ', '0');
            cText += String.format("%8s", Integer.toBinaryString(textH[i] & 0xFF)).replace(' ', '0');
            //System.out.println(i + " , " + key[i]);
        }
        cText = PERMUTATION(cText, IP);
        cText = STAGE2(cText, key);
        cText = PERMUTATION(cText, INVIP);
        cText = String.format("%16X", Long.parseLong(cText,2));
        return cText;
    }
    
    public static String Decryption(String cTextS,String keyS){
        byte[] textH = new BigInteger(cTextS, 16).toByteArray();
        String[] key = keyGenerator(keyS);
        String cText ="";
        for (int i = 0; i < textH.length; i++) {
            String s1 = String.format("%8s", Integer.toBinaryString(textH[i] & 0xFF)).replace(' ', '0');
            cText += String.format("%8s", Integer.toBinaryString(textH[i] & 0xFF)).replace(' ', '0');
            //System.out.println(i + " , " + key[i]);
        }
        cText = PERMUTATION(cText, IP);
        cText = INVSTAGE2(cText, key);
        cText = PERMUTATION(cText, INVIP);
        cText = String.format("%16X", Long.parseLong(cText,2));
        return cText;
        
    }

    public static String[] keyGenerator(String key) {

        byte[] text = new BigInteger(key, 16).toByteArray();
        String[] keys = new String[16];
        String temp = "";

        for (int i = 0; i < text.length; i++) {

            String s1 = String.format("%8s", Integer.toBinaryString(text[i] & 0xFF)).replace(' ', '0');
            temp += String.format("%8s", Integer.toBinaryString(text[i] & 0xFF)).replace(' ', '0');

        }

        String keyP = PERMUTATION(temp, PC1);
        String Ci = keyP.substring(0, 28);
        String Di = keyP.substring(28, 56);
        String[] CD = new String[15];
        String ans = "";
        ans += "C0:\t" + Ci + " D0:\t" + Di+"\n";
                
        for (int i = 1; i <= 16; i++) {
            Ci = LSi(Ci, i);
            ans += "C" + i + ":\t" + Ci;
            Di = LSi(Di, i);
            ans += " D" + i + ":\t" + Di+"\n";
            keys[i - 1] = PERMUTATION(Ci + Di, PC2);
        }
        Stages[0] = ans;
        return keys;
    }

    public static String PERMUTATION(String key, int[] permu) {

        String newK = "";

        for (int i = 0; i < permu.length; i++) {

            newK += key.charAt(permu[i] - 1);

        }
        return newK;
    }

    private static String LSi(String bits, int i) {

        String keyI = "";

        if (i == 1 || i == 2 || i == 9 || i == 16) {
            keyI = bits.substring(1, 28) + bits.substring(0, 1);
        } else {
            keyI = bits.substring(2, 28) + bits.substring(0, 2);
        }

        return keyI;
    }

    public static String STAGE2(String cText, String[] keys) {

        String Li = cText.substring(0, 32);
        String Ri = cText.substring(32, 64);
        String ans = "";
        ans +="L0" + ": " + Li+"\tR0" + ": " + Ri+"\n";
        String cMesage = "";

        for (int i = 1; i <= 16; i++) {
            String temp = Li;
            Li = Ri;
            Ri = XOR(temp, inerF(Ri, keys[i - 1]));
            ans += "L" + i + ": " + Li+"\tR" + i + ": " + Ri+"\n";
        }
        Stages[1] = ans;
        cMesage = Li + Ri;
        return cMesage;
    }
    
    public static String INVSTAGE2(String cText, String[] keys) {

        String Li = cText.substring(0, 32);
        String Ri = cText.substring(32, 64);
        String ans = "";
        ans += "L16" + ": " + Li+"\tR16" + ": " + Ri+"\n";
        String cMesage = "";
        for (int i = 16; i >= 1; i--) {
           
            String temp = Ri;
            Ri = Li;
            Li = XOR(temp, inerF(Li, keys[i - 1]));
            ans+="L" + i + ": " + Li+"\tR" + i + ": " + Ri+"\n";
        }
        Stages[2] = ans;
        cMesage = Li + Ri;
        return cMesage;
    }
    

    public static String XORiner(String Li, String inerF) {

        BigInteger LI = new BigInteger(Li, 2);
        BigInteger INF = new BigInteger(inerF, 2);
        byte [] temp = LI.xor(INF).toByteArray();
        String s1 = "";
        for (int i = 0; i < 6; i++) {
            s1 += String.format("%8s", Integer.toBinaryString(temp[i] & 0xFF)).replace(' ', '0');
        }
        
        return s1;

    }
    public static String XOR(String Li, String inerF) {

        BigInteger LI = new BigInteger(Li, 2);
        BigInteger INF = new BigInteger(inerF, 2);
        String res  = LI.xor(INF).toString(2);
        
        while(res.length() <  32){
            res = "0" + res;
        }
        return res;

    }
    
    public static String inerF(String Ri, String ki) {

        Ri = expansion(Ri);
        Ri = XORiner(Ri, ki);
        String c = "";
        String prueba = "";
        for (int i = 0; i < 8; i++) {

            if (i == 7) {
                c += sBoxSearch(Ri.substring((i * 6), 48), i);
            } else {
                c += sBoxSearch(Ri.substring(i * 6, 6 * (i + 1)), i);

            }
            
        }
        c = permutation(c);
        return c;
    }

    public static String sBoxSearch(String Bj, int sBox) {
        int[][] temp = Sbox.getsBoxes(sBox);
        Integer r = Integer.parseInt(Bj.substring(0, 1) + Bj.substring(5, 6), 2);
        Integer c = Integer.parseInt(Bj.substring(1, 5), 2);
        return String.format("%4s", Integer.toBinaryString(temp[r][c] & 0xFF)).replace(' ', '0');

    }

    public static String expansion(String Ri) {

        String newK = "";

        for (int i = 0; i < E.length; i++) {

            newK += Ri.charAt(E[i] - 1);

        }

        return newK;
    }

    public static String permutation(String c) {

        String newK = "";

        for (int i = 0; i < P.length; i++) {

            newK += c.charAt(P[i] - 1);

        }

        return newK;
    }
}
