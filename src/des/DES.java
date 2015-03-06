/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

import java.math.BigInteger;

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
    final static int[] INVIP = {40,8,48,16,56,24,64,32,39,07,47,15,55,23,63,31,38,06,46,14,54,22,62,30,37,05,45,13,53,21,61,29,36,04,44,12,52,20,60,28,35,03,43,11,51,19,59,27,34,02,42,10,50,18,58,26,33,01,41,9,49,17,57,25};
    public static void main(String[] args) {
        // TODO code application logic here

        byte[] text = "No body can see me".getBytes();
        byte[] textH = new BigInteger("0123456789ABCDEF", 16).toByteArray();
        String[] key = keyGenerator("133457799BBCDFF1");
        String cText = "";
        for (int i = 0; i < textH.length; i++) {
            String s1 = String.format("%8s", Integer.toBinaryString(textH[i] & 0xFF)).replace(' ', '0');
            cText += String.format("%8s", Integer.toBinaryString(textH[i] & 0xFF)).replace(' ', '0');
            //System.out.println(s1 + " , " + byteToSting(text[i]));
        }
        cText = PERMUTATION(cText,IP);
        //cText = STAGE2(cText);

    }

    public static String[] keyGenerator(String key) {
        
        byte[] text = new BigInteger(key, 16).toByteArray();
        String[] keys = new String[16];
        String temp = "";
        
        for (int i = 0; i < text.length; i++) {
            
            String s1 = String.format("%8s", Integer.toBinaryString(text[i] & 0xFF)).replace(' ', '0');
            temp += String.format("%8s", Integer.toBinaryString(text[i] & 0xFF)).replace(' ', '0');

        }

        String keyP = PERMUTATION(temp,PC1);
        String Ci = keyP.substring(0, 28);
        String Di = keyP.substring(28, 56);
        String[] CD = new String[15];
        
        System.out.println("C0:\t" + Ci + " D0:\t" + Di);

        for (int i = 1; i <= 16; i++) {
            Ci = LSi(Ci, i);
            System.out.print("C" + i + ":\t" + Ci);
            Di = LSi(Di, i);
            System.out.println(" D" + i + ":\t" + Di);
            keys[i - 1] = PERMUTATION(Ci + Di,PC2);
        }

        return keys;
    }

    public static String PERMUTATION(String key , int [] permu) {
        
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

    public static String STAGE2(String cText,String[] keys) {
        
        String Li = cText.substring(0, 32);
        String Ri = cText.substring(32,64);
        String cMesage = "";
        
        for (int i = 1; i <= 16; i++) {
            
            Li = Ri;
            System.out.print("L" + i + ":\t" + Li);
            Ri = XOR(Li,inerF(Ri,keys[i-1])) ;
            System.out.println(" R" + i + ":\t" + Ri);
        }
        
        return cMesage;
    }
    
    public static String XOR(String Li,String inerF){
        
        byte LI = Byte.parseByte(Li, 2);
        byte INF = Byte.parseByte(inerF, 2);
        byte ans =(byte) (LI ^ INF);
        return String.format("%8s", Integer.toBinaryString(ans & 0xFF)).replace(' ', '0');
    }
    
    public static String inerF(String Ri,String ki) {
        
        return null;
    }
    
    public static String expansion (){
        
        return null;
    }
    
    public static String SBOX (){
        
        return null;
    }
    
    public static String permutation (){
        
        return null;
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
