package model.services.ma_hoa_hash;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class SHA {
    public static final String SHA_1 = "SHA-1";
    public static final String SHA_224 = "SHA-224";
    public static final String SHA_256 = "SHA-256";
    public static final String SHA_384 = "SHA-384";
    public static final String SHA_512 = "SHA-512";

    public String hashText(String input_text, String algorithm) throws Exception {
        // Tạo một đối tượng MessageDigest với thuật toán MD5
        MessageDigest md = MessageDigest.getInstance(algorithm);

        // Băm đoạn văn bản đầu vào thành một mảng byte
        byte[] message_digest = md.digest(input_text.getBytes());

        // Chuyển mảng byte thành số nguyên BigInteger
        BigInteger number = new BigInteger(1, message_digest);

        // Chuyển đổi số nguyên sang chuỗi theo hệ cơ số 16 (hex)
        return number.toString(16);
    }

    public String hashFile(String input_file, String algorithm) throws Exception {

        FileInputStream fis = null;
        InputStream is = null;
        DigestInputStream dis = null;

        try {
            File file = new File(input_file);

            if (!file.isFile()) {
                return "";
            }

            fis = new FileInputStream(file);
            is = new BufferedInputStream(fis);

            // Tạo đối tượng MessageDigest với thuật toán MD5
            MessageDigest md = MessageDigest.getInstance(algorithm);

            // Tạo DigestInputStream để đọc và băm dữ liệu đồng thời
            dis = new DigestInputStream(is, md);

            byte[] input_byte = new byte[1024];
            int read_byte;

            /**
             Khối mã này sẽ được thực thi ít nhất một lần,
             sau đó kiểm tra điều kiện và lặp lại nếu điều kiện còn đúng.
             */
            do {
                read_byte = dis.read(input_byte);
            } while (read_byte != -1);

            // Chuyển đổi giá trị băm thành số nguyên BigInteger
            BigInteger number = new BigInteger(1, dis.getMessageDigest().digest());

            // Chuyển số nguyên thành chuỗi hex (hệ cơ số 16) và trả về
            return number.toString(16);


        } finally {
            if (fis != null) fis.close();
            if (is != null) is.close();
            if (dis != null) dis.close();
        }
    }

    public static void main(String[] args) throws Exception {

        String input_text = "Khoa CNTT-Trường Đại Học Nông Lâm";
        String input_file = "C:/Users/tmt01/Downloads/CV_Tran_Minh_Tuyen_Fresher_Zalo_Pay.pdf";

        SHA sha = new SHA();

        System.out.println(SHA.SHA_1 + ": " + sha.hashText(input_text, SHA.SHA_1));
        System.out.println(SHA.SHA_224 + ": " + sha.hashText(input_text, SHA.SHA_224));
        System.out.println(SHA.SHA_256 + ": " + sha.hashText(input_text, SHA.SHA_256));
        System.out.println(SHA.SHA_384 + ": " + sha.hashText(input_text, SHA.SHA_384));
        System.out.println(SHA.SHA_512 + ": " + sha.hashText(input_text, SHA.SHA_512));
        System.out.println("-----------------------------------------");
        System.out.println(SHA.SHA_1 + ": " + sha.hashFile(input_file, SHA.SHA_1));
        System.out.println(SHA.SHA_224 + ": " + sha.hashFile(input_file, SHA.SHA_224));
        System.out.println(SHA.SHA_256 + ": " + sha.hashFile(input_file, SHA.SHA_256));
        System.out.println(SHA.SHA_384 + ": " + sha.hashFile(input_file, SHA.SHA_384));
        System.out.println(SHA.SHA_512 + ": " + sha.hashFile(input_file, SHA.SHA_512));
    }
}
