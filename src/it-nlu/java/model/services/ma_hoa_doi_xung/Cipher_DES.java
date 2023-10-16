package model.services.ma_hoa_doi_xung;

import model.services.ma_hoa_doi_xung.interfaces.I_Decrypt;
import model.services.ma_hoa_doi_xung.interfaces.I_Encrypt;
import model.services.ma_hoa_doi_xung.interfaces.I_Export;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class Cipher_DES implements I_Encrypt, I_Decrypt, I_Export {
    private SecretKey key;

    @Override
    public SecretKey createKey() throws Exception {
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

    public SecretKey getKey() {
        return key;
    }

    public static void main(String[] args) throws Exception {

        var plain_text = "I am a student. I study at Đại Học Nông Lâm";
        Cipher_DES des = new Cipher_DES();
        des.createKey();

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
