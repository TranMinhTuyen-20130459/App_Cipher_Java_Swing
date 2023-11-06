package model.services.ma_hoa_bat_doi_xung;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class Cipher_RSA {
    private KeyPair key_pair;
    private PublicKey public_key;
    private PrivateKey private_key;

    public void generateKey(int key_size) {
        KeyPairGenerator keyGenerator = null;
        try {
            keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(key_size);
            key_pair = keyGenerator.generateKeyPair();
            public_key = key_pair.getPublic();
            private_key = key_pair.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
        return new String(byte_decrypted);
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


    public static void main(String[] args) throws Exception {
        Cipher_RSA rsa = new Cipher_RSA();
        rsa.generateKey(560);

        System.out.println("Public Key: " + rsa.exportPublicKey());
        System.out.println("Private Key: " + rsa.exportPrivateKey());

        String plain_text = "Khoa CNTT - Trường Đại Học Nông Lâm TPHCM";
        String transformation_1 = "RSA/ECB/PKCS1Padding";
        String transformation_2 = "RSA";

        System.out.println("Encrypted Text: " + rsa.encryptToBase64(plain_text, transformation_1));
        System.out.println("Encrypted Text: " + rsa.encryptToBase64(plain_text, transformation_2));

        var byte_encrypted = rsa.encrypt(plain_text, transformation_1);
        String decrypted = rsa.decrypt(byte_encrypted, transformation_1);
        System.out.println("Decrypted Text: " + decrypted);
    }


}
