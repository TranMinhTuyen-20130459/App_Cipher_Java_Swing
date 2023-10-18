package model.services.ma_hoa_doi_xung;

import model.services.ma_hoa_doi_xung.interfaces.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Cipher_DES implements I_Encrypt, I_Decrypt, I_Export, I_Import, I_Create {
    private SecretKey key;

    @Override
    public SecretKey createKeyFromInput(String text) throws Exception {
        var textBytes = text.getBytes("UTF-8");

        // Kiểm tra xem độ dài của mảng byte có đủ 8 byte không (56 bit)
        if (textBytes.length != 8) {
            throw new IllegalArgumentException("Text length must be 8 bytes for a DES key.");
        }

        key = new SecretKeySpec(textBytes,"DES");
        return key;
    }

    @Override
    public SecretKey createKeyRandom() throws Exception {
        KeyGenerator key_generator = KeyGenerator.getInstance("DES");
        key_generator.init(56);
        key = key_generator.generateKey();
        return key;
    }

    @Override
    public byte[] encrypt(String text) throws Exception {
        if (key == null) return new byte[]{};
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        var plain_text = text.getBytes("UTF-8");
        return cipher.doFinal(plain_text);
    }

    @Override
    public String encryptToBase64(String text) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        var plain_text = text.getBytes("UTF-8");
        var cipher_text = cipher.doFinal(plain_text);
        return Base64.getEncoder().encodeToString(cipher_text);
    }

    @Override
    public void encryptFile(String srcFile, String destFile) throws Exception {

    }

    @Override
    public String decrypt(byte[] encrypt) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        var plain_text = cipher.doFinal(encrypt);
        return new String(plain_text);
    }

    @Override
    public String decryptFromBase64(String text) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        var plain_text = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(plain_text, "UTF-8");
    }

    @Override
    public void decryptFile(String srcFile, String destFile) throws Exception {

    }

    @Override
    public String exportKey() throws Exception {
        if (key == null) return "";
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    @Override
    public SecretKey importKey(String keyText) throws Exception {

        if (keyText == null || keyText.isEmpty()) {
            throw new IllegalArgumentException("Invalid key text");
        }

        try {
            byte[] keyBytes = Base64.getDecoder().decode(keyText);
            SecretKey importedKey = new SecretKeySpec(keyBytes, "DES");
            this.key = importedKey;
            return importedKey;
        } catch (Exception e) {
            throw new Exception("Failed to import key: " + e.getMessage());
        }
    }

    public SecretKey getKey() {
        return key;
    }

    public static void main(String[] args) throws Exception {

        var plain_text = "I am a student. I study at Đại Học Nông Lâm";
        Cipher_DES des = new Cipher_DES();
        des.createKeyRandom();

        var encrypt_bytes = des.encrypt(plain_text);
        var encrypt_text = des.encryptToBase64(plain_text);

        System.out.println("Key: " + des.exportKey());
        System.out.println("------------------------------------");
        System.out.println("Encrypt To Base64: " + encrypt_text);
        System.out.println(des.decryptFromBase64(encrypt_text));
        System.out.println("------------------------------------");
        System.out.println("Encrypt To Bytes: " + encrypt_bytes);
        System.out.println(des.decrypt(encrypt_bytes));
    }

}
