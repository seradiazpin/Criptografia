/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author sergioalejandrodiazpinilla
 */
public class DES {

    /**
     * @param args the command line arguments
     */
    final static int[] PC1 = {57, 49, 41, 33, 25, 17, 9, 01, 58, 50, 42, 34, 26, 18, 10, 02, 59, 51, 43, 35, 27, 19, 11, 03, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 07, 62, 54, 46, 38, 30, 22, 14, 06, 61, 53, 45, 37, 29, 21, 13, 05, 28, 20, 12, 04};
    final static int[] PC2 = {14, 17, 11, 24, 01, 05, 03, 28, 15, 06, 21, 10, 23, 19, 12, 04, 26, 8, 16, 07, 27, 20, 13, 02, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
    final static int[] IP = {58, 50, 42, 34, 26, 18, 10, 02, 60, 52, 44, 36, 28, 20, 12, 04, 62, 54, 46, 38, 30, 22, 14, 06, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 01, 59, 51, 43, 35, 27, 19, 11, 03, 61, 53, 45, 37, 29, 21, 13, 05, 63, 55, 47, 39, 31, 23, 15, 07};
    final static int[] INVIP = {40, 8, 48, 16, 56, 24, 64, 32, 39, 07, 47, 15, 55, 23, 63, 31, 38, 06, 46, 14, 54, 22, 62, 30, 37, 05, 45, 13, 53, 21, 61, 29, 36, 04, 44, 12, 52, 20, 60, 28, 35, 03, 43, 11, 51, 19, 59, 27, 34, 02, 42, 10, 50, 18, 58, 26, 33, 01, 41, 9, 49, 17, 57, 25};
    final static int[] E = {32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1};
    final static int[] P = {16,07,20,21,29,12,28,17,01,15,23,26,05,18,31,10,02,8,24,14,32,27,03,9,19,13,30,06,22,11,04,25};
    final static Sbox sBoxes = new Sbox();

    public static void main(String[] args) {
        // TODO code application logic here

        byte[] text = "No body can see me".getBytes();
        byte[] textH = new BigInteger("0123456789ABCDEF", 16).toByteArray();
        String[] key = keyGenerator("133457799BBCDFF1");
        String cText = "";
        for (int i = 0; i < textH.length; i++) {
            String s1 = String.format("%8s", Integer.toBinaryString(textH[i] & 0xFF)).replace(' ', '0');
            cText += String.format("%8s", Integer.toBinaryString(textH[i] & 0xFF)).replace(' ', '0');
            //System.out.println(i + " , " + key[i]);
        }
        cText = PERMUTATION(cText, IP);
        cText = STAGE2(cText, key);
        cText = PERMUTATION(cText, INVIP);
        cText = String.format("%16X", Long.parseLong(cText,2)) ;


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

        System.out.println("C0:\t" + Ci + " D0:\t" + Di);

        for (int i = 1; i <= 16; i++) {
            Ci = LSi(Ci, i);
            System.out.print("C" + i + ":\t" + Ci);
            Di = LSi(Di, i);
            System.out.println(" D" + i + ":\t" + Di);
            keys[i - 1] = PERMUTATION(Ci + Di, PC2);
            //System.out.println("k"+i+": "+keys[i-1]);
        }

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
        System.out.print("L0" + ":\t" + Li);
        System.out.println(" R0" + ":\t" + Ri);
        String cMesage = "";

        for (int i = 1; i <= 16; i++) {
            String temp = Li;
            Li = Ri;
            System.out.print("L" + i + ":\t" + Li);
            Ri = XOR(temp, inerF(Ri, keys[i - 1]));//"00100011010010101010100110111011");
            System.out.println(" R" + i + ":\t" + Ri);
        }
        cMesage = Li + Ri;
        return cMesage;
    }

    public static String XORiner(String Li, String inerF) {

        //System.out.println(" Le:\t"+inerF);
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

        //System.out.println(" Le:\t"+inerF);
        BigInteger LI = new BigInteger(Li, 2);
        BigInteger INF = new BigInteger(inerF, 2);
        /*
        byte [] temp = LI.xor(INF).toByteArray();
        String s1 = "";
        System.out.println("\ntemp size: "+temp.length);
        for (int i = 0; i < temp.length; i++) {
            s1 += String.format("%8s", Integer.toBinaryString(temp[i] & 0xFF)).replace(' ', '0');
        }
        */
        String res  = LI.xor(INF).toString(2);
        
        while(res.length() <  32){
            res = "0" + res;
            //System.out.println("\nres: "+res +" ,"+res.length());
        }
        return res;

    }
    
    // 011110 100001 010101 010101 011110 100001 010101 010101
    // 011110 100001 010101 010101 011110 100001 010101 010101
    
    //011000 010001 011110 111010 100001 100110 010100 100111
    //011000 010001 011110 111010 100001 100110 010100 100111
    

    public static String inerF(String Ri, String ki) {

        Ri = expansion(Ri);
        //System.out.println("\nRI EX: "+Ri);
        Ri = XORiner(Ri, ki);
        //System.out.println("\nRI XOR: "+Ri);
        //System.out.println(Ri);
        //System.out.println("\nRi: " + Ri +",size "+ Ri.length());
        String c = "";
        String prueba = "";
        //String t = "";
        for (int i = 0; i < 8; i++) {

            if (i == 7) {
                //prueba += Ri.substring((i * 6) - 1, 47);
                c += sBoxSearch(Ri.substring((i * 6), 48), i);
                //t +=Ri.substring((i * 6), 48);
                //System.out.println("LOLLLL: " + i + ":" + Ri.substring((i * 6), 48));
                //System.out.println("c: "+c);
            } else {
                //prueba += Ri.substring(i * 6, 6 * (i + 1));
                c += sBoxSearch(Ri.substring(i * 6, 6 * (i + 1)), i);
                //t+=Ri.substring(i * 6, 6 * (i + 1));
                //System.out.println("LOLLLL: " + i + ":" + Ri.substring(i * 6, 6 * (i + 1)));
                //System.out.println("c: "+c);
            }
            //c += sBoxSearch(Ri.substring(i*6,6*(i+1)), i);
            //System.out.println("pr"+prueba);
            //System.out.println("R2: "+t);
            //0101 1100 1000 0010 1011 0101 1001 0111
            //0101 1100 1000 0010 1011 0101 1001 0111
            
        }
 
        //System.out.println("\nc: " + c+ c.length());
        c = permutation(c);
        //System.out.println("\nc:\t" + c+ " ,"+c.length());
        return c;
    }

    public static String sBoxSearch(String Bj, int sBox) {
        int[][] temp = Sbox.getsBoxes(sBox);
        Integer r = Integer.parseInt(Bj.substring(0, 1) + Bj.substring(5, 6), 2);
        Integer c = Integer.parseInt(Bj.substring(1, 5), 2);
        //System.out.println(Bj + " LOOOL2 " + Bj.substring(0, 1) + Bj.substring(5, 6) + " , " + r);
        //System.out.println("LLOLLL3 " + Bj.substring(1, 5) + " , " + c);
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

    public static String byteToSting(byte b) {

        String s1 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
        int charCode = Integer.parseInt(s1, 2);
        String str = new Character((char) charCode).toString();
        return str;
    }

    private static String swapChars(String str, int lIdx, int rIdx) {

        StringBuilder sb = new StringBuilder(str);
        char l = sb.charAt(lIdx), r = sb.charAt(rIdx);
        sb.setCharAt(lIdx, r);
        sb.setCharAt(rIdx, l);
        return sb.toString();
    }

    private static String deleteChars(String str, int lIdx) {

        StringBuilder sb = new StringBuilder(str);
        sb.deleteCharAt(lIdx);
        return sb.toString();
    }

}
