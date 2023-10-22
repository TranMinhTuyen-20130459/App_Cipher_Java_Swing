package model.services.ma_hoa_doi_xung;

import model.services.ma_hoa_doi_xung.interfaces.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

        key = new SecretKeySpec(textBytes, "DES");
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

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            if (key == null) throw new Exception("Key Not Found");
            File fileSrc = new File(srcFile);
            if (fileSrc.isFile()) {
                Cipher cipher = Cipher.getInstance("DES");
                cipher.init(Cipher.ENCRYPT_MODE, key);

                fis = new FileInputStream(fileSrc);
                fos = new FileOutputStream(destFile);

                byte[] input_byte = new byte[1024];
                int bytes_read;
                while ((bytes_read = fis.read(input_byte)) != -1) {

                    byte[] output_byte = cipher.update(input_byte, 0, bytes_read);
                    if (output_byte != null) fos.write(output_byte);

                }

                /**
                 - cipher.update() chỉ thực hiện mã hóa một phần của dữ liệu và trả về kết quả tương ứng với phần đó.
                 - cipher.doFinal() được sử dụng để xử lý phần còn lại của dữ liệu và đảm bảo rằng không có dữ liệu nào bị bỏ sót.
                 => Điều này đặc biệt quan trọng trong trường hợp mã hóa dữ liệu lớn chia thành nhiều khối.
                 */

                byte[] output = cipher.doFinal();
                if (output != null) fos.write(output);

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

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            if (key == null) throw new Exception("Key Not Found");
            File fileSrc = new File(srcFile);
            if (fileSrc.isFile()) {

                Cipher cipher = Cipher.getInstance("DES");
                cipher.init(Cipher.DECRYPT_MODE, key);

                fis = new FileInputStream(fileSrc);
                fos = new FileOutputStream(destFile);

                byte[] input_byte = new byte[1024];
                int bytes_read;
                while ((bytes_read = fis.read(input_byte)) != -1) {

                    byte[] output_byte = cipher.update(input_byte, 0, bytes_read);
                    if (output_byte != null) fos.write(output_byte);

                }
                /**
                 - cipher.update() chỉ thực hiện mã hóa một phần của dữ liệu và trả về kết quả tương ứng với phần đó.
                 - cipher.doFinal() được sử dụng để xử lý phần còn lại của dữ liệu và đảm bảo rằng không có dữ liệu nào bị bỏ sót.
                 => Điều này đặc biệt quan trọng trong trường hợp mã hóa dữ liệu lớn chia thành nhiều khối.
                 */
                byte[] output = cipher.doFinal();
                if (output != null) fos.write(output);

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

//        var encrypt_bytes = des.encrypt(plain_text);
//        var encrypt_text = des.encryptToBase64(plain_text);
//
//        System.out.println("Key: " + des.exportKey());
//        System.out.println("------------------------------------");
//        System.out.println("Encrypt To Base64: " + encrypt_text);
//        System.out.println(des.decryptFromBase64(encrypt_text));
//        System.out.println("------------------------------------");
//        System.out.println("Encrypt To Bytes: " + encrypt_bytes);
//        System.out.println(des.decrypt(encrypt_bytes));

        String src_file = "C:\\Users\\tmt01\\Downloads\\Nhóm3-LTTTBDĐ.pptx";
        String dest_file_encrypted = "C:\\Users\\tmt01\\Downloads\\DES_Encrypted_File.pptx";
        String dest_file_decrypted = "C:\\Users\\tmt01\\Downloads\\DES_Decrypted_File.pptx";

        des.encryptFile(src_file, dest_file_encrypted);
        des.decryptFile(dest_file_encrypted, dest_file_decrypted);
        des.encryptFile(dest_file_encrypted, "C:\\Users\\tmt01\\Downloads\\DES_Encrypted_File-2.pptx");
        des.decryptFile("C:\\Users\\tmt01\\Downloads\\DES_Encrypted_File-2.pptx", "C:\\Users\\tmt01\\Downloads\\DES_Decrypted_File-2.pptx");
        des.decryptFile("C:\\Users\\tmt01\\Downloads\\DES_Decrypted_File-2.pptx", "C:\\Users\\tmt01\\Downloads\\DES_Decrypted_File-3.pptx");
    }

}
