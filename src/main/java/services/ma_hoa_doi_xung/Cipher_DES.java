package services.ma_hoa_doi_xung;

import services.ma_hoa_doi_xung.interfaces.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

public class Cipher_DES implements I_Encrypt, I_Decrypt, I_Export, I_Import, I_Create {
    private SecretKey key;
    private String transformation;
    @Override
    public SecretKey createKeyRandom(int key_size) throws Exception {
        KeyGenerator key_generator = KeyGenerator.getInstance("DES");
        key_generator.init(key_size);
        /*
         *  DES yêu cầu một khóa đầy đủ có 64 bit,
         *  nhưng chỉ sử dụng 56 bit trong số đó để thực hiện mã hóa,
         *  và 8 bit còn lại được sử dụng để kiểm tra tính chính xác của khóa.
         */
        key = key_generator.generateKey();
        return key;
    }

    @Override
    public byte[] encrypt(String text) throws Exception {
        if (key == null) return new byte[]{};
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
        else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[8]));

        var text_bytes = text.getBytes("UTF-8");
        return cipher.doFinal(text_bytes);
    }

    @Override
    public String encryptToBase64(String text) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
        else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[8]));

        var text_bytes = text.getBytes("UTF-8");
        var encrypted_text_bytes = cipher.doFinal(text_bytes);
        return Base64.getEncoder().encodeToString(encrypted_text_bytes);
    }

    @Override
    public void encryptFile(String srcFile, String destFile) throws Exception {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            if (key == null) throw new Exception("Key Not Found");
            File fileSrc = new File(srcFile);
            if (fileSrc.isFile()) {
                Cipher cipher = Cipher.getInstance(transformation);
                if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
                else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[8]));

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
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
        else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8]));

        var decrypted_text_bytes = cipher.doFinal(encrypt);
        return new String(decrypted_text_bytes,"UTF-8");
    }

    @Override
    public String decryptFromBase64(String text) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
        else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8]));

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

                Cipher cipher = Cipher.getInstance(transformation);
                if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
                else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8]));

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

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public static void main(String[] args) throws Exception {

        var plain_text = "I am a student. I study at Đại Học Nông Lâm";
        Cipher_DES des = new Cipher_DES();
        des.setTransformation("DES/CBC/PKCS5Padding");
        des.createKeyRandom(56);

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

        String srcFileEncrypt = "C:/Users/tmt01/Downloads/Nhom5_Ionic_App_Ban_Giay.pptx";
        String destFileEncrypt = "C:/Users/tmt01/Downloads/DES_FILE_ENCRYPT_Nhom5_Ionic_App_Ban_Giay.pptx";
        String destFileDecrypt = "C:/Users/tmt01/Downloads/DES_FILE_DECRYPT_Nhom5_Ionic_App_Ban_Giay.pptx";
        des.encryptFile(srcFileEncrypt, destFileEncrypt);
        des.decryptFile(destFileEncrypt, destFileDecrypt);
    }

}
