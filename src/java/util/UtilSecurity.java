/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Arrays;
import java.util.Collections;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author CR104978
 */
public class UtilSecurity {

    static final String low = "abcdefghijhlmnopqrtuwxyz";
    static final String up = "ABCDEFGHIJQLMNOPQRTSUVWXYZ";
    static final String esp = "+-*=%&#!.";
    static final String num = "1234567890";

    public static String encript(String password) {
        String encriptMD5 = DigestUtils.md5Hex(password);
        return encriptMD5;
    }

    public static String randomPassword(int numLow, int numCap, int numSpe, int numNum) {
        String password = "";
        password = random(low, numLow) + random(up, numCap) + random(esp, numSpe) + random(num, numNum);
        return reorganize(password);
    }

    public static String reorganize(String result) {
        String arrayPalabras[] = result.split("");
        Collections.shuffle(Arrays.asList(arrayPalabras));
        result = "";
        for (int i = 0; arrayPalabras.length > i; i++) {
            result = result + arrayPalabras[i];
        }
        return result;
    }

    public static String random(String words, int quantity) {
        String result = "";
        String[] arrayWords = words.split("");
        int randomNumber = 0;
        for (int i = 0; i < quantity; i++) {
            randomNumber = (int) (Math.random() * (arrayWords.length - 1) + 0);
            result = result + arrayWords[randomNumber];
        }
        return result;
    }

}
