package model.services.ma_hoa_doi_xung;

import model.services.ma_hoa_doi_xung.interfaces.I_Decrypt;
import model.services.ma_hoa_doi_xung.interfaces.I_Encrypt;
import model.services.ma_hoa_doi_xung.interfaces.I_Export;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.TwofishEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class Cipher_TwoFish implements I_Encrypt, I_Decrypt, I_Export {
    private KeyParameter key;
    private TwofishEngine engine;

    public Cipher_TwoFish() {
        engine = new TwofishEngine(); // Tạo một engine TwoFish mới
    }

    public KeyParameter createKeyFromInput(String keyText) throws Exception {
        byte[] keyBytes = keyText.getBytes("UTF-8");

        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new Exception("Key must be 16, 24, or 32 bytes (128, 192, or 256 bits).");
        }
        key = new KeyParameter(keyBytes);
        return key;
    }

    public KeyParameter createKeyRandom(int key_size) throws Exception {

        if (key_size != 128 && key_size != 192 && key_size != 256) {
            throw new Exception("Key size must be 128, 192, or 256 bits.");
        }

        // Sử dụng SecureRandom để tạo một mảng byte ngẫu nhiên có độ dài key_size / 8
        SecureRandom secureRandom = new SecureRandom();
        byte[] arr_bytes = new byte[key_size / 8]; //=> 1 byte = 8 bits
        secureRandom.nextBytes(arr_bytes);

        // Tạo KeyParameter từ mảng byte ngẫu nhiên
        key = new KeyParameter(arr_bytes);

        return key;
    }

    @Override
    public byte[] encrypt(String text) throws Exception {
        if (key == null || engine == null) {
            return new byte[]{};
        }

        // Chuyển đổi văn bản thành mảng byte sử dụng UTF-8 encoding
        byte[] inputBytes = text.getBytes(StandardCharsets.UTF_8);

        // Tạo một đối tượng BufferedBlockCipher với engine đã cung cấp
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine);

        // Khởi tạo cipher cho quá trình mã hóa
        cipher.init(true, key);

        // Tính toán kích thước đầu ra dự kiến
        int maxOutputSize = cipher.getOutputSize(inputBytes.length);

        // Mã hóa dữ liệu và trả về dữ liệu đã mã hóa
        byte[] encryptedData = new byte[maxOutputSize];
        int length = cipher.processBytes(inputBytes, 0, inputBytes.length, encryptedData, 0);
        length += cipher.doFinal(encryptedData, length);

        // Cắt mảng đầu ra để loại bỏ dữ liệu thừa
        byte[] finalData = Arrays.copyOf(encryptedData, length);
        return finalData;
    }


    @Override
    public String encryptToBase64(String text) throws Exception {

        if (key == null || engine == null) return "";

        // Chuyển đổi văn bản thành mảng byte sử dụng UTF-8 encoding
        byte[] inputBytes = text.getBytes(StandardCharsets.UTF_8);

        // Tạo một đối tượng BufferedBlockCipher với engine đã cung cấp
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine);

        // Khởi tạo cipher cho quá trình mã hóa
        cipher.init(true, key);

        // Tính toán kích thước đầu ra dự kiến
        int maxOutputSize = cipher.getOutputSize(inputBytes.length);

        // Mã hóa dữ liệu và trả về dữ liệu đã mã hóa
        byte[] encryptedData = new byte[maxOutputSize];
        int length = cipher.processBytes(inputBytes, 0, inputBytes.length, encryptedData, 0);
        length += cipher.doFinal(encryptedData, length);

        // Cắt mảng đầu ra để loại bỏ dữ liệu thừa
        byte[] finalData = Arrays.copyOf(encryptedData, length);

        // Chuyển mảng byte thành chuỗi Base64 và trả về
        return Base64.getEncoder().encodeToString(finalData);
    }

    @Override
    public void encryptFile(String srcFile, String destFile) throws Exception {

    }

    @Override
    public String decrypt(byte[] encrypt) throws Exception {
        if (key == null || engine == null) return "";

        // Tạo đối tượng BufferedBlockCipher với engine đã cung cấp
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine);

        // Khởi tạo cipher cho quá trình giải mã
        cipher.init(false, key);

        // Tính toán kích thước đầu ra dự kiến
        int maxOutputSize = cipher.getOutputSize(encrypt.length);

        // Giải mã dữ liệu và trả về dữ liệu đã giải mã
        byte[] decryptedData = new byte[maxOutputSize];
        int length = cipher.processBytes(encrypt, 0, encrypt.length, decryptedData, 0);
        length += cipher.doFinal(decryptedData, length);

        // Chuyển mảng byte đã giải mã thành chuỗi UTF-8 và trả về
        return new String(decryptedData, 0, length, StandardCharsets.UTF_8);
    }

    @Override
    public String decryptFromBase64(String text) throws Exception {

        if (key == null || engine == null) return "";

        // Giải mã chuỗi Base64 thành mảng byte ban đầu
        byte[] encryptedData = Base64.getDecoder().decode(text);

        // Tạo đối tượng BufferedBlockCipher với engine đã cung cấp
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine);

        // Khởi tạo cipher cho quá trình giải mã
        cipher.init(false, key);

        // Tính toán kích thước đầu ra dự kiến
        int maxOutputSize = cipher.getOutputSize(encryptedData.length);

        // Giải mã dữ liệu và trả về dữ liệu đã giải mã
        byte[] decryptedData = new byte[maxOutputSize];
        int length = cipher.processBytes(encryptedData, 0, encryptedData.length, decryptedData, 0);
        length += cipher.doFinal(decryptedData, length);

        // Chuyển mảng byte đã giải mã thành chuỗi UTF-8 và trả về
        return new String(decryptedData, 0, length, StandardCharsets.UTF_8);
    }


    @Override
    public void decryptFile(String srcFile, String destFile) throws Exception {

    }

    @Override
    public String exportKey() throws Exception {
        if (key == null) return "";
        return Base64.getEncoder().encodeToString(key.getKey());
    }

    public KeyParameter importKey(String keyText) throws Exception {
        return null;
    }

    public static void main(String[] args) throws Exception {

        // CodeOfChatGPT();
        // TestCreateKey();

        Test_Encrypt_Decrypt_Text_With_Bytes();
        System.out.println("-----------------------------");
        Test_Encrypt_Decrypt_Text_With_Base64();

    }

    public static void CodeOfChatGPT() {
        String plaintext = "Khoa CNTT,Trường Đại Học Nông Lâm TPHCM";

        String keyString = "12345678";
        byte[] key = keyString.getBytes(StandardCharsets.UTF_8);

        // Tạo một engine Twofish mới
        TwofishEngine engine = new TwofishEngine();

        // Tạo một key parameter mới từ khóa bí mật của chúng ta
        KeyParameter keyParam = new KeyParameter(key);

        // Tạo một buffer cipher mới với Twofish engine
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine);

        // Khởi tạo buffer cipher với khóa của chúng ta
        cipher.init(true, keyParam);

        // Mã hóa plaintext
        byte[] ciphertext = new byte[cipher.getOutputSize(plaintext.getBytes().length)];
        int len = cipher.processBytes(plaintext.getBytes(), 0, plaintext.getBytes().length, ciphertext, 0);
        try {
            cipher.doFinal(ciphertext, len);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // In ra ciphertext
        System.out.println("Ciphertext: " + new String(ciphertext));

        // Giải mã ciphertext
        cipher.init(false, keyParam); // Sử dụng cùng một key
        byte[] decrypted = new byte[cipher.getOutputSize(ciphertext.length)];
        len = cipher.processBytes(ciphertext, 0, ciphertext.length, decrypted, 0);
        try {
            len += cipher.doFinal(decrypted, len);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // In ra plaintext đã giải mã
        System.out.println("Decrypted: " + new String(decrypted));
    }

    public static void TestCreateKey() throws Exception {
        Cipher_TwoFish twoFish = new Cipher_TwoFish();
        twoFish.createKeyFromInput("TRAN_MINH_TUYEN_");

        System.out.println("Key From Input: " + twoFish.exportKey());

        twoFish.createKeyRandom(128);
        System.out.println("Key 128 bit: " + twoFish.exportKey());

        twoFish.createKeyRandom(192);
        System.out.println("Key 192 bit: " + twoFish.exportKey());

        twoFish.createKeyRandom(256);
        System.out.println("Key 256 bit: " + twoFish.exportKey());
    }

    public static void Test_Encrypt_Decrypt_Text_With_Bytes() throws Exception {

        String plain_text = "Khoa CNTT,Trường Đại Học Nông Lâm TPHCM";
        Cipher_TwoFish twoFish = new Cipher_TwoFish();
        twoFish.createKeyRandom(128);

        byte[] encrypted_bytes = twoFish.encrypt(plain_text);
        var decrypted_text = twoFish.decrypt(encrypted_bytes);
        System.out.println("Encrypted Bytes: "+encrypted_bytes);
        System.out.println("Decrypted Text: " + decrypted_text);

    }

    public static void Test_Encrypt_Decrypt_Text_With_Base64() throws Exception {

        String plain_text = "Khoa CNTT,Trường Đại Học Nông Lâm TPHCM";
        Cipher_TwoFish twoFish = new Cipher_TwoFish();
        twoFish.createKeyFromInput("TRAN_MINH_TUYEN1");

        String encrypted_text = twoFish.encryptToBase64(plain_text);
        String decrypted_text = twoFish.decryptFromBase64(encrypted_text);

        System.out.println("Encrypted Text: " + encrypted_text);
        System.out.println("Decrypted Text: " + decrypted_text);

    }

}
