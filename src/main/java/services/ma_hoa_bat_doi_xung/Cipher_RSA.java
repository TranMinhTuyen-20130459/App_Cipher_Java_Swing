package services.ma_hoa_bat_doi_xung;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Cipher_RSA {
    private KeyPair key_pair;
    private PublicKey public_key;
    private PrivateKey private_key;

    public void generateKey(int key_size) throws Exception {
        KeyPairGenerator keyGenerator = null;
        keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(key_size);
        key_pair = keyGenerator.generateKeyPair();
        public_key = key_pair.getPublic();
        private_key = key_pair.getPrivate();
    }

    public byte[] encrypt(String text, String transformation) throws Exception {
        // Thuật toán Bất Đối Xứng dùng public_key để mã hóa
        if (public_key == null) return new byte[]{};
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, public_key);
        var byte_text = text.getBytes(StandardCharsets.UTF_8);
        return cipher.doFinal(byte_text);
    }

    public String encryptToBase64(String text, String transformation) throws Exception {

        if (public_key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, public_key);
        var byte_text = text.getBytes(StandardCharsets.UTF_8);
        var byte_encrypted = cipher.doFinal(byte_text);

        return Base64.getEncoder().encodeToString(byte_encrypted);
    }

    public String decrypt(byte[] byte_encrypted, String transformation) throws Exception {
        if (private_key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, private_key);
        var byte_decrypted = cipher.doFinal(byte_encrypted);
        return new String(byte_decrypted, StandardCharsets.UTF_8);
    }

    public String decryptFromBase64(String encrypted, String transformation) throws Exception {
        if (private_key == null) return "";
        var byte_encrypted = Base64.getDecoder().decode(encrypted.getBytes());

        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, private_key);

        var byte_decrypted = cipher.doFinal(byte_encrypted);
        return new String(byte_decrypted, StandardCharsets.UTF_8);
    }

    public String exportPublicKey() {
        if (public_key == null) return null;
        var byte_public_key = public_key.getEncoded();
        return Base64.getEncoder().encodeToString(byte_public_key);
    }

    public String exportPrivateKey() {
        if (private_key == null) return null;
        var byte_private_key = private_key.getEncoded();
        return Base64.getEncoder().encodeToString(byte_private_key);
    }

    public PublicKey importPublicKey(String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (publicKey == null || publicKey.isEmpty()) return null;

        var byte_public_key = Base64.getDecoder().decode(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(byte_public_key);
        public_key = keyFactory.generatePublic(keySpec);
        return public_key;
    }

    public PrivateKey importPrivateKey(String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (privateKey == null || privateKey.isEmpty()) return null;

        byte[] byte_private_key = Base64.getDecoder().decode(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(byte_private_key);
        private_key = keyFactory.generatePrivate(keySpec);
        return private_key;
    }

    public static void main(String[] args) throws Exception {
        Cipher_RSA rsa = new Cipher_RSA();
        rsa.generateKey(560);

        System.out.println("Public Key: " + rsa.exportPublicKey());
        System.out.println("Private Key: " + rsa.exportPrivateKey());
//
//        String plain_text = "Khoa CNTT - Trường Đại Học Nông Lâm TPHCM";
//        String transformation_1 = "RSA/ECB/PKCS1Padding";
//        String transformation_2 = "RSA";
//
//        var byte_encrypted = rsa.encrypt(plain_text, transformation_1);
//        String decrypted = rsa.decrypt(byte_encrypted, transformation_1);
//        System.out.println("Decrypted Text: " + decrypted);
//
//        String encrypted_text_1 = rsa.encryptToBase64(plain_text, transformation_1);
//        String encrypted_text_2 = rsa.encryptToBase64(plain_text, transformation_2);
//
//        String decrypted_text_1 = rsa.decryptFromBase64(encrypted_text_1, transformation_1);
//        String decrypted_text_2 = rsa.decryptFromBase64(encrypted_text_2, transformation_2);
//
//        System.out.println("Encrypted Text 1: " + encrypted_text_1);
//        System.out.println("Encrypted Text 2: " + encrypted_text_2);
//        System.out.println("Decrypted Text 1: " + decrypted_text_1);
//        System.out.println("Decrypted Text 2: " + decrypted_text_2);

        rsa.importPrivateKey("MIIBcAIBADANBgkqhkiG9w0BAQEFAASCAVowggFWAgEAAkcArUMBGTMR51WHFDqkd2RE2Q64rTaPQ+MRKHFMNinMQvB9MyaSMY2weXnOP5xS8m8gL7MRsxCTaRlUASSUtdhwcL+V70B/YQIDAQABAkYCAUNdHamMgCF95w9R593BlsFllLrr7c/cJFJ90nZABI0oVzoiTRn6QvtPnltPGE1NlfLuPQq1KLIwsHdsUARuSM5909fhAiQA7wSfmq5nCkepy2dXfV6MDAe7UykGcGl1ngrMzB64Q8AeWZECJAC5kl90Zkgz4RrvXE1ioiVe+d48V7xa1VzQLRHjmKA6jTNg0QIjCGNlJoyQWTACqXzK+T9kMtfinxyFeVR6KD3AyjkZxmbrqcECJACwZzc8NDqwrmznWsvpVg/GYCplHFXd1vMapa1auGygHDfZ8QIkAMF2qI3b9PpvGTC3vhPSu/uPPWNIWWkopOAfRcYjF5d13fZR");
        System.out.println(rsa.exportPrivateKey());

    }


}
