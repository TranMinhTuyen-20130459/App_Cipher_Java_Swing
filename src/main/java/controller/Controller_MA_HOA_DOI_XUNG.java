package controller;

import helper.Algorithm;
import helper.DecryptFile;
import helper.EncryptFile;
import helper.Languague;
import services.ma_hoa_doi_xung.*;
import services.ma_hoa_doi_xung.Cipher_Vigenere;

public class Controller_MA_HOA_DOI_XUNG {

    // đây là function tạo Key cho thuật toán Hill,Vigenere
    public static String createKeyRandomFor_Hill_Vigenere(String algorithm, String language, int key_size) {
        try {
            if (algorithm == null || language == null) return null;
            if (algorithm.isEmpty() || language.isEmpty()) return "";
            switch (algorithm.toUpperCase()) {

                case Algorithm.VIGENERE: {

                    if (language.equalsIgnoreCase(Languague.ENGLISH)) {

                        Cipher_Vigenere vigEnglish = new Cipher_Vigenere(Languague.ENGLISH);

                        if (key_size > 0) vigEnglish.createKeyRandom(key_size);
                        else vigEnglish.createKeyRandom(10); //=> key có độ lớn mặc định là 10 kí tự

                        return vigEnglish.exportKey();
                    }

                    if (language.equalsIgnoreCase(Languague.VIETNAM)) {

                        Cipher_Vigenere vigVietNam = new Cipher_Vigenere(Languague.VIETNAM);

                        if (key_size > 0) vigVietNam.createKeyRandom(key_size);
                        else vigVietNam.createKeyRandom(10);

                        return vigVietNam.exportKey();

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

    // đây là function tạo Key cho thuật toán AES,DES,TwoFish,Serpent
    public static String createKeyRandom(String algorithm, int key_size) {

        try {

            if (algorithm == null || algorithm.isEmpty()) return null;

            switch (algorithm.toUpperCase()) {

                case Algorithm.DES: {

                    Cipher_DES des = new Cipher_DES();

                    if (key_size > 0) des.createKeyRandom(key_size);
                    else des.createKeyRandom(56); // => đây là key_size mặc định của thuật toán

                    return des.exportKey();

                }

                case Algorithm.AES: {

                    Cipher_AES aes = new Cipher_AES();

                    if (key_size > 0) aes.createKeyRandom(key_size);
                    else aes.createKeyRandom(128);

                    return aes.exportKey();

                }

                case Algorithm.TWO_FISH: {

                    Cipher_TwoFish twoFish = new Cipher_TwoFish();

                    if (key_size > 0) twoFish.createKeyRandom(key_size);
                    else twoFish.createKeyRandom(128);

                    return twoFish.exportKey();
                }

                case Algorithm.BLOW_FISH: {

                    Cipher_BlowFish blowFish = new Cipher_BlowFish();

                    if (key_size > 0) blowFish.createKeyRandom(key_size);
                    else blowFish.createKeyRandom(128);

                    return blowFish.exportKey();
                }

                case Algorithm.SERPENT: {

                    Cipher_Serpent serpent = new Cipher_Serpent();

                    if (key_size > 0) serpent.createKeyRandom(key_size);
                    else serpent.createKeyRandom(128);

                    return serpent.exportKey();
                }

                default:
                    return "NOT_FOUND_ALGORITHM";

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // đây là function mã hóa văn bản của các thuật toán AES,DES,TwoFish,Serpent
    public static String encryptTextWithKeyBase64(String algorithm,
                                                  String language,
                                                  String plain_text,
                                                  String key,
                                                  String name_mode_padding) {

        try {

            if (algorithm == null || language == null || plain_text == null || key == null || name_mode_padding == null)
                return "Error";

            if (algorithm.isEmpty() || language.isEmpty() || plain_text.isEmpty() || key.isEmpty() || name_mode_padding.isEmpty())
                return "Error";

            switch (algorithm.toUpperCase()) {

                case Algorithm.DES: {

                    Cipher_DES des = new Cipher_DES();
                    des.importKey(key);
                    des.setTransformation(algorithm + "/" + name_mode_padding + "Padding");

                    return des.encryptToBase64(plain_text);
                }

                case Algorithm.AES: {

                    Cipher_AES aes = new Cipher_AES();
                    aes.importKey(key);
                    aes.setTransformation(algorithm + "/" + name_mode_padding + "Padding");

                    return aes.encryptToBase64(plain_text);
                }

                case Algorithm.TWO_FISH: {

                    Cipher_TwoFish twoFish = new Cipher_TwoFish();
                    twoFish.importKey(key);
                    twoFish.setTransformation(algorithm + "/" + name_mode_padding + "Padding");

                    return twoFish.encryptToBase64(plain_text);

                }

                case Algorithm.BLOW_FISH: {

                    Cipher_BlowFish blowFish = new Cipher_BlowFish();
                    blowFish.importKey(key);

                    blowFish.setTransformation(algorithm + "/" + name_mode_padding + "Padding");
                    return blowFish.encryptToBase64(plain_text);

                }

                case Algorithm.SERPENT: {

                    Cipher_Serpent serpent = new Cipher_Serpent();
                    serpent.importKey(key);
                    serpent.setTransformation(algorithm + "/" + name_mode_padding + "Padding");

                    return serpent.encryptToBase64(plain_text);

                }
                default:
                    return "";
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
        }
    }

    // đây là function giải mã văn bản của các thuật toán AES,DES,TwoFish,Serpent
    public static String decryptTextWithKeyBase64(String algorithm,
                                                  String language,
                                                  String encrypt_text,
                                                  String key,
                                                  String name_mode_padding) {

        try {

            if (algorithm == null || language == null || encrypt_text == null || key == null || name_mode_padding == null)
                return "Error";

            if (algorithm.isEmpty() || language.isEmpty() || encrypt_text.isEmpty() || key.isEmpty() || name_mode_padding.isEmpty())
                return "Error";

            switch (algorithm.toUpperCase()) {

                case Algorithm.DES: {

                    Cipher_DES des = new Cipher_DES();
                    des.importKey(key);
                    des.setTransformation(algorithm + "/" + name_mode_padding + "Padding");

                    return des.decryptFromBase64(encrypt_text);

                }

                case Algorithm.AES: {

                    Cipher_AES aes = new Cipher_AES();
                    aes.importKey(key);
                    aes.setTransformation(algorithm + "/" + name_mode_padding + "Padding");

                    return aes.decryptFromBase64(encrypt_text);

                }

                case Algorithm.TWO_FISH: {

                    Cipher_TwoFish twoFish = new Cipher_TwoFish();
                    twoFish.importKey(key);
                    twoFish.setTransformation(algorithm + "/" + name_mode_padding + "Padding");

                    return twoFish.decryptFromBase64(encrypt_text);
                }

                case Algorithm.BLOW_FISH: {

                    Cipher_BlowFish blowFish = new Cipher_BlowFish();
                    blowFish.importKey(key);

                    blowFish.setTransformation(algorithm + "/" + name_mode_padding + "Padding");
                    return blowFish.decryptFromBase64(encrypt_text);

                }

                case Algorithm.SERPENT: {
                    Cipher_Serpent serpent = new Cipher_Serpent();
                    serpent.importKey(key);
                    serpent.setTransformation(algorithm + "/" + name_mode_padding + "Padding");

                    return serpent.decryptFromBase64(encrypt_text);
                }
                default:
                    return "";

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
        }

    }

    // đây là function mã hóa File của các thuật toán AES,DES,TwoFish,Serpent
    public static int encryptFileWithKeyBase64(String algorithm,
                                               String language,
                                               String srcFile,
                                               String destFile,
                                               String key,
                                               String name_mode_padding) {

        /**
         * return -1 => đã xảy ra lỗi trong quá trình Mã Hóa File
         * return 0 => không thể Mã Hóa File do không có Thuật Toán phù hợp
         * return 1 => Mã Hóa File "Thành Công"
         */

        try {

            if (algorithm == null || language == null || srcFile == null || destFile == null || key == null || name_mode_padding == null) {
                return EncryptFile.ERROR;
            }

            switch (algorithm.toUpperCase()) {
                case Algorithm.DES: {
                    Cipher_DES des = new Cipher_DES();
                    des.importKey(key);

                    des.setTransformation(algorithm + "/" + name_mode_padding + "Padding");
                    des.encryptFile(srcFile, destFile);

                    return EncryptFile.SUCCESS;
                }

                case Algorithm.AES: {
                    Cipher_AES aes = new Cipher_AES();
                    aes.importKey(key);

                    aes.setTransformation(algorithm + "/" + name_mode_padding + "Padding");
                    aes.encryptFile(srcFile, destFile);

                    return EncryptFile.SUCCESS;
                }

                case Algorithm.TWO_FISH: {
                    Cipher_TwoFish twoFish = new Cipher_TwoFish();
                    twoFish.importKey(key);

                    twoFish.setTransformation(algorithm + "/" + name_mode_padding + "Padding");
                    twoFish.encryptFile(srcFile, destFile);

                    return EncryptFile.SUCCESS;
                }

                case Algorithm.BLOW_FISH: {
                    Cipher_BlowFish blowFish = new Cipher_BlowFish();
                    blowFish.importKey(key);

                    blowFish.setTransformation(algorithm + "/" + name_mode_padding + "Padding");
                    blowFish.encryptFile(srcFile, destFile);

                    return EncryptFile.SUCCESS;
                }

                case Algorithm.SERPENT: {

                    Cipher_Serpent serpent = new Cipher_Serpent();
                    serpent.importKey(key);

                    serpent.setTransformation(algorithm + "/" + name_mode_padding + "Padding");
                    serpent.encryptFile(srcFile, destFile);

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

    // đây là function giải mã File của các thuật toán AES,DES,TwoFish,Serpent
    public static int decryptFileWithKeyBase64(String algorithm,
                                               String language,
                                               String srcFile,
                                               String destFile,
                                               String key,
                                               String name_mode_padding) {

        try {

            if (algorithm == null || language == null || srcFile == null || destFile == null || key == null || name_mode_padding == null) {
                return DecryptFile.ERROR;
            }

            switch (algorithm.toUpperCase()) {
                case Algorithm.DES: {
                    Cipher_DES des = new Cipher_DES();
                    des.importKey(key);

                    des.setTransformation(algorithm + "/" + name_mode_padding + "Padding");
                    des.decryptFile(srcFile, destFile);

                    return DecryptFile.SUCCESS;
                }

                case Algorithm.AES: {
                    Cipher_AES aes = new Cipher_AES();
                    aes.importKey(key);

                    aes.setTransformation(algorithm + "/" + name_mode_padding + "Padding");
                    aes.decryptFile(srcFile, destFile);

                    return DecryptFile.SUCCESS;
                }

                case Algorithm.TWO_FISH: {
                    Cipher_TwoFish twoFish = new Cipher_TwoFish();
                    twoFish.importKey(key);

                    twoFish.setTransformation(algorithm + "/" + name_mode_padding + "Padding");
                    twoFish.decryptFile(srcFile, destFile);

                    return DecryptFile.SUCCESS;
                }

                case Algorithm.BLOW_FISH: {

                    Cipher_BlowFish blowFish = new Cipher_BlowFish();
                    blowFish.importKey(key);

                    blowFish.setTransformation(algorithm + "/" + name_mode_padding + "Padding");
                    blowFish.decryptFile(srcFile, destFile);

                    return DecryptFile.SUCCESS;
                }

                case Algorithm.SERPENT: {

                    Cipher_Serpent serpent = new Cipher_Serpent();
                    serpent.importKey(key);

                    serpent.setTransformation(algorithm + "/" + name_mode_padding + "Padding");
                    serpent.decryptFile(srcFile, destFile);
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
                        Cipher_Vigenere vigEnglish = new Cipher_Vigenere(Languague.ENGLISH);
                        vigEnglish.importKey(key);
                        return vigEnglish.encrypt(plain_text);
                    }

                    if (language.equalsIgnoreCase(Languague.VIETNAM)) {

                        Cipher_Vigenere vigVietNam = new Cipher_Vigenere(Languague.VIETNAM);
                        vigVietNam.importKey(key);

                        return vigVietNam.encrypt(plain_text);

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

                        Cipher_Vigenere vigEnglish = new Cipher_Vigenere(Languague.ENGLISH);
                        vigEnglish.importKey(key);
                        return vigEnglish.decrypt(encrypted_text);
                    }

                    if (language.equalsIgnoreCase(Languague.VIETNAM)) {

                        Cipher_Vigenere vigVietNam = new Cipher_Vigenere(Languague.VIETNAM);
                        vigVietNam.importKey(key);
                        return vigVietNam.decrypt(encrypted_text);
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
