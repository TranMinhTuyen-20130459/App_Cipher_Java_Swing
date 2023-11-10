package model.services.ma_hoa_doi_xung;

import model.services.ma_hoa_doi_xung.interfaces.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

public class Cipher_AES implements I_Create, I_Encrypt, I_Decrypt, I_Export, I_Import {
    private SecretKey key;
    private String transformation;

    @Override
    public SecretKey createKeyRandom(int key_size) throws Exception {
        KeyGenerator key_generator = KeyGenerator.getInstance("AES");
        key_generator.init(key_size); // Độ dài của khóa (128,192 hoặc 256 bit)
        key = key_generator.generateKey();
        return key;
    }

    @Override
    public byte[] encrypt(String text) throws Exception {
        if (key == null) return new byte[]{};
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
        else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));

        var text_bytes = text.getBytes("UTF-8");
        return cipher.doFinal(text_bytes);
    }

    @Override
    public String encryptToBase64(String text) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
        else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));

        var text_bytes = text.getBytes("UTF-8");
        var encrypted_text_bytes = cipher.doFinal(text_bytes);
        return Base64.getEncoder().encodeToString(encrypted_text_bytes);
    }

    @Override
    public void encryptFile(String srcFile, String destFile) throws Exception {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            File file = new File(srcFile);
            if (file.isFile()) {
                Cipher cipher = Cipher.getInstance(transformation);

                if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
                else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));

                fis = new FileInputStream(file);
                fos = new FileOutputStream(destFile);

                byte[] input_byte = new byte[1024];
                int bytes_read;

                while ((bytes_read = fis.read(input_byte)) != -1) {

                    byte[] output_byte = cipher.update(input_byte, 0, bytes_read);
                    if (output_byte != null) fos.write(output_byte);
                }

                byte[] output_byte = cipher.doFinal();
                if (output_byte != null) fos.write(output_byte);

                fos.flush();
                System.out.println("Done Encrypted File");
            }
        } finally {
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }

    }

    @Override
    public String decrypt(byte[] encrypt) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
        else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));

        var decrypted_text_bytes = cipher.doFinal(encrypt);
        return new String(decrypted_text_bytes, "UTF-8");
    }

    @Override
    public String decryptFromBase64(String text) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
        else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));

        var encrypted_text_bytes = Base64.getDecoder().decode(text);
        var decrypted_text_bytes = cipher.doFinal(encrypted_text_bytes);
        return new String(decrypted_text_bytes, "UTF-8");
    }

    @Override
    public void decryptFile(String srcFile, String destFile) throws Exception {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {

            File file = new File(srcFile);
            if (file.isFile()) {

                Cipher cipher = Cipher.getInstance(transformation);

                if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
                else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));

                fis = new FileInputStream(file);
                fos = new FileOutputStream(destFile);

                byte[] input_byte = new byte[1024];
                int byte_read;

                while ((byte_read = fis.read(input_byte)) != -1) {

                    byte[] output_byte = cipher.update(input_byte, 0, byte_read);
                    if (output_byte != null) fos.write(output_byte);
                }

                byte[] output_byte = cipher.doFinal();
                if (output_byte != null) fos.write(output_byte);

                fos.flush();
                System.out.println("Done Decrypted File");
            }

        } finally {
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }
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
            byte[] key_bytes = Base64.getDecoder().decode(keyText.getBytes());
            key = new SecretKeySpec(key_bytes, "AES");
            return key;
        } catch (Exception e) {
            throw new Exception("Failed to import key: " + e.getMessage());
        }
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public static void main(String[] args) throws Exception {

        String plain_text = "Khoa CNTT-Trường Đại Học Nông Lâm-TPHCM";

        Cipher_AES aes = new Cipher_AES();
        aes.setTransformation("AES/CBC/PKCS5Padding");
        aes.createKeyRandom(128);

        byte[] encrypted_text_bytes = aes.encrypt(plain_text);
        String decrypted_text_bytes = aes.decrypt(encrypted_text_bytes);

        String encrypted_text_base64_one = aes.encryptToBase64(plain_text);
        String decrypted_text_base64_one = aes.decryptFromBase64(encrypted_text_base64_one);

        System.out.println("----------------------------------------");
        System.out.println("Export Key Random: " + aes.exportKey());
        System.out.println("Encrypted Text Bytes: " + encrypted_text_bytes);
        System.out.println("Decrypted Text Bytes: " + decrypted_text_bytes);
        System.out.println("Encrypted Text Base 64: " + encrypted_text_base64_one);
        System.out.println("Decrypted Text Base 64: " + decrypted_text_base64_one);

        System.out.println("----------------------------------------");

        System.out.println("Export Key Input: " + aes.exportKey());
        String encrypted_text_base64_two = aes.encryptToBase64(plain_text);
        String decrypted_text_base64_two = aes.decryptFromBase64(encrypted_text_base64_two);
        System.out.println("Encrypted Text Base 64: " + encrypted_text_base64_two);
        System.out.println("Decrypted Text Base 64: " + decrypted_text_base64_two);

//        System.out.println("----------------------------------------");
//
//        String srcFileEncrypt = "C:/Users/tmt01/Downloads/Nhom5_Ionic_App_Ban_Giay.pptx";
//        String destFileEncrypt = "C:/Users/tmt01/Downloads/AES_FILE_ENCRYPT_Nhom5_Ionic_App_Ban_Giay.pptx";
//        String destFileDecrypt = "C:/Users/tmt01/Downloads/AES_FILE_DECRYPT_Nhom5_Ionic_App_Ban_Giay.pptx";
//        aes.encryptFile(srcFileEncrypt, destFileEncrypt);
//        aes.decryptFile(destFileEncrypt, destFileDecrypt);
    }
}
