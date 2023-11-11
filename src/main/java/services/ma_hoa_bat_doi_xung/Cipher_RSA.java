package services.ma_hoa_bat_doi_xung;

import helper.Algorithm;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

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

    /**
     * Mã hóa một tập tin sử dụng thuật toán đối xứng và khóa, sau đó lưu trữ kết quả vào tập tin đích.
     *
     * @param srcFile            Tên tập tin nguồn cần được mã hóa
     * @param destFile           Tên tập tin đích sau khi mã hóa
     * @param symmetry_algorithm Thuật toán đối xứng được sử dụng (ví dụ: AES, DES, TwoFish)
     * @param transformation     Chuỗi biểu diễn cách mã hóa (ví dụ: "AES/CBC/PKCS5Padding")
     * @param symmetry_key       Khóa đối xứng cần sử dụng để mã hóa tập tin
     * @param public_key         Khóa công khai (Public Key) dùng để mã hóa khóa của các thuật toán đối xứng, được lưu trong file mã hóa
     * @throws Exception Ném ra nếu có lỗi xảy ra trong quá trình mã hóa
     */
    public void encryptFile(String srcFile, String destFile, String symmetry_algorithm, String transformation, String symmetry_key, String public_key) throws Exception {
        // Kiểm tra các tham số đầu vào
        if (srcFile == null || srcFile.isEmpty()) throw new Exception("srcFile is null or empty");
        if (destFile == null || destFile.isEmpty()) throw new Exception("destFile is null or empty");
        if (symmetry_algorithm == null || symmetry_algorithm.isEmpty())
            throw new Exception("symmetry_algorithm is null or empty");
        if (transformation == null || transformation.isEmpty()) throw new Exception("transformation is null or empty");
        if (symmetry_key == null || symmetry_key.isEmpty()) throw new Exception("symmetry_key is null or empty");
        if (public_key == null || public_key.isEmpty()) throw new Exception("public_key is null or empty");

        if (!transformation.contains(symmetry_algorithm))
            throw new Exception("Algorithm is " + symmetry_algorithm + " while Transformation is " + transformation);

        importPublicKey(public_key); // Đây là Public Key của RSA dùng để mã hóa Key của các thuật toán mã hóa đối xứng như AES, DES, TwoFish, BlowFish, Serpent

        // Chuẩn bị khóa đối xứng từ dữ liệu đã cho và chuẩn bị IV tương ứng với từng thuật toán
        var key_bytes = Base64.getDecoder().decode(symmetry_key);
        var key = new SecretKeySpec(key_bytes, symmetry_algorithm);
        var IV = new byte[0];

        switch (symmetry_algorithm.toUpperCase()) {
            case Algorithm.AES:
            case Algorithm.TWO_FISH:
            case Algorithm.SERPENT: {
                IV = new byte[16];
                break;
            }
            case Algorithm.DES:
            case Algorithm.BLOW_FISH: {
                IV = new byte[8];
                break;
            }
            default:
                IV = new byte[0];
        }

        // Tạo đối tượng Cipher để mã hóa dữ liệu
        Cipher cipher = Cipher.getInstance(transformation);
        if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
        else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));

        File fileInput = new File(srcFile);
        if (!fileInput.isFile()) throw new Exception("srcFile is not a file or not found");

        // Sử dụng luồng để đọc và ghi dữ liệu đã được mã hóa
        try (CipherInputStream cis = new CipherInputStream(new BufferedInputStream(new FileInputStream(srcFile)), cipher);
             DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(destFile)))) {

            // Ghi các thông tin cần thiết vào tập tin đích (ví dụ: khóa đã mã hóa, độ dài tập tin, IV nếu có)
            dos.writeUTF(encryptToBase64(symmetry_key, "RSA/ECB/PKCS1Padding/"));
            dos.writeLong(fileInput.length());
            dos.writeUTF(Base64.getEncoder().encodeToString(IV));

            // Đọc dữ liệu và ghi dữ liệu đã mã hóa vào tập tin đích
            byte[] input_byte = new byte[1024];
            int bytes_read;

            while ((bytes_read = cis.read(input_byte)) != -1) {
                dos.write(input_byte, 0, bytes_read);
            }

            dos.flush(); // Đảm bảo dữ liệu được ghi hết vào tập tin đích
            System.out.println("Done Encrypted File");
        }
    }


    /**
     * Giải mã một tập tin đã được mã hóa với thuật toán đối xứng bằng cách sử dụng khóa riêng (Private Key) từ RSA.
     *
     * @param srcFile             Tên tập tin nguồn cần giải mã
     * @param destFile            Tên tập tin đích sau khi giải mã
     * @param symmetric_algorithm Thuật toán đối xứng được sử dụng (ví dụ: AES, DES, TwoFish)
     * @param transformation      Chuỗi biểu diễn cách mã hóa (ví dụ: "AES/CBC/PKCS5Padding")
     * @param private_key         Khóa riêng (Private Key) dùng để giải mã khóa của thuật toán đối xứng, được lưu trong file mã hóa
     * @throws Exception Ném ra nếu có lỗi xảy ra trong quá trình giải mã
     */
    public void decryptFile(String srcFile, String destFile, String symmetric_algorithm, String transformation, String private_key) throws Exception {
        // Kiểm tra các tham số đầu vào
        if (srcFile == null || srcFile.isEmpty()) throw new Exception("srcFile is null or empty");
        if (destFile == null || destFile.isEmpty()) throw new Exception("destFile is null or empty");
        if (symmetric_algorithm == null || symmetric_algorithm.isEmpty())
            throw new Exception("symmetric_algorithm is null or empty");
        if (transformation == null || transformation.isEmpty()) throw new Exception("transformation is null or empty");
        if (private_key == null || private_key.isEmpty()) throw new Exception("private_key is null or empty");

        importPrivateKey(private_key); // Đây là Private Key của RSA dùng để giải mã Key của các thuật toán đối xứng như AES, DES, TwoFish được lưu vào file mã hóa

        File fileInput = new File(srcFile);
        if (!fileInput.isFile()) throw new Exception("srcFile is not a file or not found");

        // Chuẩn bị các luồng để đọc và ghi dữ liệu
        CipherInputStream cis = null;
        try (
                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(srcFile)));
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(destFile)))
        ) {
            // Đọc khóa đối xứng đã được mã hóa từ tập tin, và giải mã bằng khóa RSA
            String symmetry_key = decryptFromBase64(dis.readUTF(), "RSA/ECB/PKCS1Padding/");
            long lengthFile = dis.readLong(); // Đọc độ dài của tập tin đã mã hóa
            byte[] IV = Base64.getDecoder().decode(dis.readUTF()); // Đọc vector khởi đầu (Initialization Vector) nếu cần thiết

            // Chuyển đổi khóa đã giải mã thành đối tượng SecretKey
            SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(symmetry_key), symmetric_algorithm);

            // Khởi tạo đối tượng Cipher để giải mã dữ liệu
            Cipher cipher = Cipher.getInstance(transformation);
            if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key); // Đối với ECB, không cần IV
            else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV)); // Sử dụng IV nếu không phải ECB mode

            cis = new CipherInputStream(dis, cipher); // Tạo luồng để đọc dữ liệu đã được giải mã

            // Đọc và ghi dữ liệu đã giải mã
            byte[] input_byte = new byte[1024];
            int bytes_read;

            while ((bytes_read = cis.read(input_byte)) != -1) {
                dos.write(input_byte, 0, bytes_read);
            }
            dos.flush(); // Đảm bảo dữ liệu được ghi hết vào tập tin đích
            System.out.println("Done Decrypted File");

        } finally {
            if (cis != null) cis.close(); // Đóng luồng CipherInputStream sau khi sử dụng
        }
    }


    public static void main(String[] args) throws Exception {

        Cipher_RSA rsa = new Cipher_RSA();

//        rsa.generateKey(560);

//        System.out.println("Public Key: " + rsa.exportPublicKey());
//        System.out.println("Private Key: " + rsa.exportPrivateKey());
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

        String symmetric_algorithm = "AES";
        String[] arr_mode_paddings = {
                "ECB/PKCS5",
                "CBC/PKCS5",
                "CFB/PKCS5",
                "OFB/PKCS5",
                "ECB/ISO10126",
                "CBC/ISO10126",
                "CFB/ISO10126",
                "OFB/ISO10126"
        };

        String key = "yve54uq4BLsxr1Gl3bIz4A==";
        String srcFile = "C:/Users/tmt01/Downloads/Nhom5-Dich_vu_cho_thue_san_bong_da_mini.docx";

        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMh/iN6UkyGTMWBkYkhqLeXL6fgpcQSgbPopNeUCTA9UDZZJmZn0dlrY8k15jLMTXqNPBtnYh33UWM9PZAw/y4MCAwEAAQ==";
        String privateKey = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAyH+I3pSTIZMxYGRiSGot5cvp+ClxBKBs+ik15QJMD1QNlkmZmfR2WtjyTXmMsxNeo08G2diHfdRYz09kDD/LgwIDAQABAkAHWf3Hw4Tdj0sZMLUV2KbULTTn/4UAWW2FgqtSOVJWoXqwB1jREB8w5ZWWVzXufl4j6jeh5g6RhDwb0Wj9P65pAiEA9NOTo0ySsoIt436xtFYOXTnZesrgz35qVK1YpUrQurUCIQDRpgyyZZJRNt4kcSjC+0vjqLO0UNakwrYDs27vek34VwIhAM9PEOboAhgRz1WhhEwVypf6UkN94xsQCm2lUzizv0c5AiEAiwdoIiIznwIY3OfiFTPbRtVInwqA3XB2jgu/RLbB4C8CIQCyLprbUlp13YvBas4r+2RquNFlqstgjVgQKY6pBJaWYA==";

        int index = 0;
        for (String mode_padding : arr_mode_paddings) {

            String transformation = symmetric_algorithm + "/" + mode_padding + "Padding";

            String destFileEncrypt = "C:/Users/tmt01/Downloads/RSA_MA_HOA_KEY_" + symmetric_algorithm + "_" + index + "_Nhom5-Dich_vu_cho_thue_san_bong_da_mini.docx";
            rsa.encryptFile(srcFile, destFileEncrypt, symmetric_algorithm, transformation, key, publicKey);

            String destFileDecrypt = "C:/Users/tmt01/Downloads/RSA_GIAI_MA_KEY_" + symmetric_algorithm + "_" + index + "_Nhom5-Dich_vu_cho_thue_san_bong_da_mini.docx";
            rsa.decryptFile(destFileEncrypt, destFileDecrypt, symmetric_algorithm, transformation, privateKey);

            index = index + 1;
        }
    }

}
