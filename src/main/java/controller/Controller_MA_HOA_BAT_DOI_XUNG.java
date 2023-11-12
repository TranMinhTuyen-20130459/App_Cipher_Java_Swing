package controller;

import helper.Algorithm;
import helper.DecryptFile;
import helper.EncryptFile;
import services.ma_hoa_bat_doi_xung.Cipher_RSA;


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

    public static int encryptFileWithPublicKeyRSA(
            String srcFile,
            String destFile,
            String symmetry_algorithm,
            String symmetry_mode_padding,
            String symmetry_key,
            String public_key) {

        if (srcFile == null || destFile == null || symmetry_algorithm == null || symmetry_mode_padding == null || symmetry_key == null || public_key == null)
            return EncryptFile.ERROR;

        if (srcFile.isEmpty() || destFile.isEmpty() || symmetry_algorithm.isEmpty() || symmetry_mode_padding.isEmpty() || symmetry_key.isEmpty() || public_key.isEmpty())
            return EncryptFile.ERROR;

        try {

            Cipher_RSA rsa = new Cipher_RSA();

            String transformation = symmetry_algorithm + "/" + symmetry_mode_padding + "Padding";
            rsa.encryptFile(srcFile, destFile, symmetry_algorithm, transformation, symmetry_key, public_key);

            return EncryptFile.SUCCESS;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return EncryptFile.ERROR;
        }

    }

    public static int decryptFileWithPrivateKeyRSA(String srcFile,
                                                   String destFile,
                                                   String symmetric_algorithm,
                                                   String symmetry_mode_padding,
                                                   String private_key) {

        if (srcFile == null || destFile == null || symmetric_algorithm == null || symmetry_mode_padding == null || private_key == null)
            return DecryptFile.ERROR;

        if (srcFile.isEmpty() || destFile.isEmpty() || symmetric_algorithm.isEmpty() || symmetry_mode_padding.isEmpty() | private_key.isEmpty())
            return DecryptFile.ERROR;

        try {
            Cipher_RSA rsa = new Cipher_RSA();

            String transformation = symmetric_algorithm + "/" + symmetry_mode_padding + "Padding";
            rsa.decryptFile(srcFile, destFile, symmetric_algorithm, transformation, private_key);

            return DecryptFile.SUCCESS;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return DecryptFile.ERROR;
        }

    }
}
