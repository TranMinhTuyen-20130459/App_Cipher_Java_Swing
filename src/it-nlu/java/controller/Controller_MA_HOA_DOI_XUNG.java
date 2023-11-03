package controller;

import helper.Algorithm;
import helper.DecryptFile;
import helper.EncryptFile;
import helper.Languague;
import model.services.ma_hoa_doi_xung.*;

public class Controller_MA_HOA_DOI_XUNG {

    // đây là function tạo Key cho thuật toán Hill,Vigenere
    public static String createKeyRandomFor_Hill_Vigenere(String algorithm, String language, String plain_text) {
        try {
            if (algorithm == null || plain_text == null || language == null) return null;
            if (algorithm.isEmpty() || plain_text.isEmpty() || language.isEmpty()) return "";
            switch (algorithm.toUpperCase()) {

                case Algorithm.VIGENERE: {

                    if (language.equalsIgnoreCase(Languague.ENGLISH)) {
                        Cipher_Vigenere_English vigenereEnglish = new Cipher_Vigenere_English();
                        vigenereEnglish.createKeyRandom(plain_text.length());

                        return vigenereEnglish.exportKey();
                    }

                    if (language.equalsIgnoreCase(Languague.VIETNAM)) {
                        Cipher_Vigenere_Vietnamese vigenereVietnamese = new Cipher_Vigenere_Vietnamese();
                        vigenereVietnamese.createKeyRandom(plain_text.length());

                        return vigenereVietnamese.exportKey();
                    }

                    return "";

                }

                case Algorithm.HILL: {
                    return "HILL";
                }

                default:
                    return "NOT_FOUND_ALGORITHM";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // đây là function tạo Key cho thuật toán AES,DES,TwoFish
    public static String createKeyRandom(String algorithm) {

        try {

            if (algorithm == null || algorithm.isEmpty()) return null;

            switch (algorithm.toUpperCase()) {

                case Algorithm.DES: {

                    Cipher_DES des = new Cipher_DES();
                    des.createKeyRandom(56);

                    return des.exportKey();

                }

                case Algorithm.AES: {

                    Cipher_AES aes = new Cipher_AES();
                    aes.createKeyRandom(128);

                    return aes.exportKey();

                }

                case Algorithm.TWO_FISH: {

                    Cipher_TwoFish twoFish = new Cipher_TwoFish();
                    twoFish.createKeyRandom(128);

                    return twoFish.exportKey();
                }

                default:
                    return "NOT_FOUND_ALGORITHM";

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // đây là function mã hóa văn bản của các thuật toán AES,DES,TwoFish
    public static String encryptTextWithKeyBase64(String algorithm,
                                                  String language,
                                                  String plain_text,
                                                  String key) {

        try {

            if (key == null || key.isEmpty()) return "Error";

            switch (algorithm.toUpperCase()) {

                case Algorithm.DES: {

                    Cipher_DES des = new Cipher_DES();
                    des.importKey(key);

                    return des.encryptToBase64(plain_text);
                }

                case Algorithm.AES: {

                    Cipher_AES aes = new Cipher_AES();
                    aes.importKey(key);

                    return aes.encryptToBase64(plain_text);
                }

                case Algorithm.TWO_FISH: {

                    Cipher_TwoFish twoFish = new Cipher_TwoFish();
                    twoFish.importKey(key);

                    return twoFish.encryptToBase64(plain_text);

                }
                default:
                    return "";
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
        }
    }

    // đây là function giải mã văn bản của các thuật toán AES,DES,TwoFish
    public static String decryptTextWithKeyBase64(String algorithm,
                                                  String language,
                                                  String encrypt_text,
                                                  String key) {

        try {

            if (key == null || key.isEmpty()) return "Error";

            switch (algorithm.toUpperCase()) {

                case Algorithm.DES: {

                    Cipher_DES des = new Cipher_DES();
                    des.importKey(key);

                    return des.decryptFromBase64(encrypt_text);

                }

                case Algorithm.AES: {

                    Cipher_AES aes = new Cipher_AES();
                    aes.importKey(key);

                    return aes.decryptFromBase64(encrypt_text);

                }

                case Algorithm.TWO_FISH: {

                    Cipher_TwoFish twoFish = new Cipher_TwoFish();
                    twoFish.importKey(key);

                    return twoFish.decryptFromBase64(encrypt_text);
                }
                default:
                    return "";

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
        }

    }

    // đây là function mã hóa File của các thuật toán AES,DES,TwoFish
    public static int encryptFileWithKeyBase64(String algorithm,
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

                case Algorithm.AES: {
                    Cipher_AES aes = new Cipher_AES();
                    aes.importKey(key);
                    aes.encryptFile(srcFile, destFile);
                    return EncryptFile.SUCCESS;
                }

                case Algorithm.TWO_FISH: {
                    Cipher_TwoFish twoFish = new Cipher_TwoFish();
                    twoFish.importKey(key);
                    twoFish.encryptFile(srcFile, destFile);
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

    // đây là function giải mã File của các thuật toán AES,DES,TwoFish
    public static int decryptFileWithKeyBase64(String algorithm,
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

                case Algorithm.AES: {
                    Cipher_AES aes = new Cipher_AES();
                    aes.importKey(key);
                    aes.decryptFile(srcFile, destFile);
                    return DecryptFile.SUCCESS;
                }

                case Algorithm.TWO_FISH: {
                    Cipher_TwoFish twoFish = new Cipher_TwoFish();
                    twoFish.importKey(key);
                    twoFish.decryptFile(srcFile, destFile);
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

    // đây là function mã hóa văn bản của các thuật toán Hill,Viginere
    public static String encryptTextWithKeyText(String algorithm, String language, String plain_text, String key) {
        try {

            if (algorithm == null || language == null || plain_text == null || key == null) return null;
            if (algorithm.isEmpty() || language.isEmpty() || plain_text.isEmpty() || key.isEmpty()) return null;

            switch (algorithm.toUpperCase()) {

                case Algorithm.HILL: {
                    return "HILL";
                }
                case Algorithm.VIGENERE: {

                    if (language.equalsIgnoreCase(Languague.ENGLISH)) {
                        Cipher_Vigenere_English vigenereEnglish = new Cipher_Vigenere_English();
                        vigenereEnglish.importKey(key);
                        return vigenereEnglish.encrypt(plain_text);
                    }

                    if (language.equalsIgnoreCase(Languague.VIETNAM)) {
                        Cipher_Vigenere_Vietnamese vigenereVietnamese = new Cipher_Vigenere_Vietnamese();
                        vigenereVietnamese.importKey(key);
                        return vigenereVietnamese.encrypt(plain_text);
                    }

                    return "";
                }

                default:
                    return "NOT_FOUND_ALGORITHM";

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // đây là function giải mã văn bản của các thuật toán Hill,Viginere
    public static String decryptTextWithKeyText(String algorithm, String language, String encrypted_text, String key) {

        try {

            if (algorithm == null || language == null || encrypted_text == null || key == null) return null;
            if (algorithm.isEmpty() || language.isEmpty() || encrypted_text.isEmpty() || key.isEmpty()) return null;

            switch (algorithm.toUpperCase()) {

                case Algorithm.HILL: {
                    return "HILL";
                }
                case Algorithm.VIGENERE: {

                    if (language.equalsIgnoreCase(Languague.ENGLISH)) {
                        Cipher_Vigenere_English vigenereEnglish = new Cipher_Vigenere_English();
                        vigenereEnglish.importKey(key);
                        return vigenereEnglish.decrypt(encrypted_text);
                    }

                    if (language.equalsIgnoreCase(Languague.VIETNAM)) {
                        Cipher_Vigenere_Vietnamese vigenereVietnamese = new Cipher_Vigenere_Vietnamese();
                        vigenereVietnamese.importKey(key);
                        return vigenereVietnamese.decrypt(encrypted_text);
                    }

                    return "";
                }

                default:
                    return "NOT_FOUND_ALGORITHM";

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
