package controller;

import helper.Algorithm;
import helper.DecryptFile;
import helper.EncryptFile;
import model.services.ma_hoa_doi_xung.Cipher_DES;

public class Controller_MA_HOA_DOI_XUNG {

    public static String createKeyFromInputOfUser(String algorithm, String keyText) {
        try {

            if (keyText == null || algorithm == null || keyText.isEmpty() || algorithm.isEmpty()) {
                return "";
            }

            switch (algorithm.toUpperCase()) {
                case Algorithm.DES: {

                    Cipher_DES des = new Cipher_DES();
                    des.createKeyFromInput(keyText);

                    System.out.println(des.exportKey());
                    return des.exportKey();
                }
                default:
                    return "";
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
        }
    }

    public static String createKeyRandom(String algorithm) {

        try {

            if (algorithm == null || algorithm.isEmpty()) return "";

            switch (algorithm.toUpperCase()) {

                case Algorithm.DES: {

                    Cipher_DES des = new Cipher_DES();
                    des.createKeyRandom();

                    return des.exportKey();

                }
                default:
                    return "";

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
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

    public static int encryptFile(String algorithm,
                                  String language,
                                  String srcFile,
                                  String destFile,
                                  String key) {

        /**
         * return -1 => đã xảy ra lỗi trong quá trình Mã Hóa File
         * return 0 => không thể Mã Hóa File do không có Thuật Toán phù hợp
         * return 1 => Mã Hóa File "Thành Công"
         */

        try {

            if (algorithm == null || language == null || srcFile == null || destFile == null || key == null) {
                return EncryptFile.ERROR;
            }

            switch (algorithm.toUpperCase()) {
                case Algorithm.DES: {
                    Cipher_DES des = new Cipher_DES();
                    des.importKey(key);
                    des.encryptFile(srcFile, destFile);
                    return EncryptFile.SUCCESS;
                }
                default:
                    return EncryptFile.NOT_FOUND_ALGORITHM;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return EncryptFile.ERROR;
        }
    }

    public static int decryptFile(String algorithm,
                                  String language,
                                  String srcFile,
                                  String destFile,
                                  String key) {

        try {

            if (algorithm == null || language == null || srcFile == null || destFile == null || key == null) {
                return DecryptFile.ERROR;
            }

            switch (algorithm.toUpperCase()) {
                case Algorithm.DES: {
                    Cipher_DES des = new Cipher_DES();
                    des.importKey(key);
                    des.decryptFile(srcFile, destFile);
                    return DecryptFile.SUCCESS;
                }
                default:
                    return DecryptFile.NOT_FOUND_ALGORITHM;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return DecryptFile.ERROR;
        }

    }
}