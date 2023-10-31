package model.services.ma_hoa_hash;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class MD5 {

    public static final String MD5 = "MD5";

    public String hashText(String input_text) throws Exception {
        // Tạo một đối tượng MessageDigest với thuật toán MD5
        MessageDigest md = MessageDigest.getInstance("MD5");

        // Băm đoạn văn bản đầu vào thành một mảng byte
        byte[] message_digest = md.digest(input_text.getBytes());

        // Chuyển mảng byte thành số nguyên BigInteger
        BigInteger number = new BigInteger(1, message_digest);

        // Chuyển đổi số nguyên sang chuỗi theo hệ cơ số 16 (hex)
        return number.toString(16);
    }

    public String hashFile(String input_file) throws Exception {

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
            MessageDigest md = MessageDigest.getInstance("MD5");

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

        String input_text = "TRAN MINH TUYEN";
        String input_file = "C:/Users/tmt01/Downloads/CV_Tran_Minh_Tuyen_Fresher_Zalo_Pay.pdf";

        MD5 md5 = new MD5();

        System.out.println(md5.hashText(input_text));
        System.out.println(md5.hashFile(input_file));
    }

}
