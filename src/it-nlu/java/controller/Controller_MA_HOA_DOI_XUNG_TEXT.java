package controller;

import helper.Algorithm;
import model.services.ma_hoa_doi_xung.Cipher_DES;

public class Controller_MA_HOA_DOI_XUNG_TEXT {
    public static String createKeyRandom(String algorithm) {

        try {

            if (algorithm == null || algorithm.isEmpty()) return "";

            switch (algorithm.toUpperCase()) {

                case Algorithm.DES: {

                    Cipher_DES des = new Cipher_DES();
                    des.createKey();

                    return des.exportKey();

                }
                default:
                    return "";

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static String encryptText(String algorithm,
                                     String language,
                                     String plain_text,
                                     String key) {

        try {

            if (key == null || key.isEmpty()) return "";

            switch (algorithm.toUpperCase()) {

                case Algorithm.DES: {

                    Cipher_DES des = new Cipher_DES();
                    des.importKey(key);

                    return des.encryptToBase64(plain_text);
                }
                default:
                    return "";
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
        }
    }

    public static String decryptText(String algorithm,
                                     String language,
                                     String encrypt_text,
                                     String key) {

        try {

            if (key == null || key.isEmpty()) return "";

            switch (algorithm.toUpperCase()) {

                case Algorithm.DES: {

                    Cipher_DES des = new Cipher_DES();
                    des.importKey(key);

                    return des.decryptFromBase64(encrypt_text);

                }
                default:
                    return "";

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
        }

    }
}
