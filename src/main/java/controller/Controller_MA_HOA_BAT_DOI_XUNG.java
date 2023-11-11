package controller;
import helper.Algorithm;
import services.ma_hoa_bat_doi_xung.*;


public class Controller_MA_HOA_BAT_DOI_XUNG {
    public static String[] createKeyRandom(String name_algorithm, int key_size) {
        if (name_algorithm == null || name_algorithm.isEmpty()) return null;
        try {
            var arr_key = new String[2];
            switch (name_algorithm.toUpperCase()) {
                case Algorithm.RSA: {
                    Cipher_RSA rsa = new Cipher_RSA();
                    rsa.generateKey(key_size);

                    arr_key[0] = rsa.exportPublicKey();
                    arr_key[1] = rsa.exportPrivateKey();
                    return arr_key;
                }
                default:
                    return arr_key;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String encryptText(String algorithm, String plain_text, String public_key, String transformation) {
        if (algorithm == null || plain_text == null || public_key == null || transformation == null) return null;
        if (algorithm.isEmpty() || plain_text.isEmpty() || public_key.isEmpty() || transformation.isEmpty())
            return null;
        try {
            switch (algorithm.toUpperCase()) {
                case Algorithm.RSA: {

                    Cipher_RSA rsa = new Cipher_RSA();
                    rsa.importPublicKey(public_key);

                    return rsa.encryptToBase64(plain_text, transformation);
                }
                default:
                    return "";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String decryptText(String algorithm, String encrypted_text, String private_key, String transformation) {
        if (algorithm == null || encrypted_text == null || private_key == null || transformation == null) return null;
        if (algorithm.isEmpty() || encrypted_text.isEmpty() || private_key.isEmpty() || transformation.isEmpty())
            return null;

        try {
            switch (algorithm.toUpperCase()) {

                case Algorithm.RSA: {
                    Cipher_RSA rsa = new Cipher_RSA();
                    rsa.importPrivateKey(private_key);

                    return rsa.decryptFromBase64(encrypted_text, transformation);
                }
                default:
                    return "";

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
