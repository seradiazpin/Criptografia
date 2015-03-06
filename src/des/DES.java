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
        cText = IP(cText);
        /*
        for (int i = 0; i < key.length; i++) {
            System.out.println("k"+(i+1)+":\t"+key[i]);
        }
        */

    }
    public static String [] keyGenerator(String key) {
        byte[] text = new BigInteger(key, 16).toByteArray();
        String[] keys = new String[16];
        String temp = "";
        for (int i = 0; i < text.length; i++) {
            String s1 = String.format("%8s", Integer.toBinaryString(text[i] & 0xFF)).replace(' ', '0');
            temp += String.format("%8s", Integer.toBinaryString(text[i] & 0xFF)).replace(' ', '0');
            //System.out.println(s1 + " , " + byteToSting(text[i]));
        }
        //System.out.println(temp);
        String keyP = PC1(temp);
        String Ci = keyP.substring(0,28);
        String Di = keyP.substring(28, 56);
        String [] CD = new String[15];
        /*
        System.out.println("C0: 1111000011001100101010101111 D0: 0101010101100110011110001111");
        */
        System.out.println("C0:\t"+Ci+" D0:\t"+Di);
        
        for (int i = 1; i <= 16; i++) {
            Ci = LSi(Ci, i);
            System.out.print("C"+i+":\t"+Ci);
            Di = LSi(Di, i);
            System.out.println(" D"+i+":\t"+Di);
            keys[i-1]= PC2(Ci+Di);
        }
        
        /*
        System.out.println("C1: 1110000110011001010101011111 D1: 1010101011001100111100011110");
        System.out.println("C1: "+C0+" D1: "+D0);
        System.out.println("PC2:"+ PC2(C0+D0));
        */
        return keys;
    }
    /*
    01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56
    57 49 41 33 25 17 09 01 58 50 42 34 26 18 10 02 59 51 43 35 27 19 11 03 60 52 44 36 63 55 47 39 31 23 15 07 62 54 46 38 30 22 14 06 61 53 45 37 29 21 13 05 28 20 12 04
    */
    public static String PC1(String key) {
        int [] PC1 = {57,49,41,33,25,17,9,01,58,50,42,34,26,18,10,02,59,51,43,35,27,19,11,03,60,52,44,36,63,55,47,39,31,23,15,07,62,54,46,38,30,22,14,06,61,53,45,37,29,21,13,05,28,20,12,04};
        String newK = "";
        for (int i = 0; i < PC1.length; i++) {
            newK+=key.charAt(PC1[i]-1);
            //System.out.println(newK+",key "+key.charAt(PC1[i]));
        }
        return newK;
    }
    private static String LSi(String bits,int i){
        String keyI = "";
        if (i == 1||i==2||i==9||i==16){
            keyI = bits.substring(1, 28)+ bits.substring(0,1);
        }else{
            keyI = bits.substring(2,28)+bits.substring(0,2);
        }
        //System.out.println(keyI);
        return keyI;
    }
    public static String PC2(String key) {
        int [] PC2 = {14,17,11,24,01,05,03,28,15,06,21,10,23,19,12,04,26,8,16,07,27,20,13,02,41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32};
        String newK = "";
        for (int i = 0; i < PC2.length; i++) {
            newK+=key.charAt(PC2[i]-1);
            //System.out.println(newK+",key "+key.charAt(PC1[i]));
        }   
        return newK;
    }
    
    public static String IP(String text){
        int [] IP = {58,50,42,34,26,18,10,02,60,52,44,36,28,20,12,04,62,54,46,38,30,22,14,06,64,56,48,40,32,24,16,8,57,49,41,33,25,17,9,01,59,51,43,35,27,19,11,03,61,53,45,37,29,21,13,05,63,55,47,39,31,23,15,07};
        String newK = "";
        for (int i = 0; i < IP.length; i++) {
            newK+=text.charAt(IP[i]-1);
            //System.out.println(newK+",key "+key.charAt(PC1[i]));
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
