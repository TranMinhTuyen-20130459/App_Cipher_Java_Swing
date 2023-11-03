package model.services.ma_hoa_doi_xung;

import java.util.Random;

public class Cipher_Vigenere_Vietnamese {
    private static final String ALPHABET_UPPERCASE = "AĂÂBCDĐEÊFGHIJKLMNOÔƠPQRSTUƯVXY";
    private static final String ALPHABET_LOWERCASE = "aăâbcdđeêfghijklmnoôơpqrstuưvxy";
    private String key;

    public String createKeyRandom(int key_size) throws Exception {
        if (key_size <= 0) throw new Exception("Key size must be > 0");

        Random random = new Random();
        String characters = ALPHABET_UPPERCASE + ALPHABET_LOWERCASE;
        int length_string_random = random.nextInt(key_size) + 1;
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length_string_random; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        key = randomString.toString();
        return key;
    }

    public String encrypt(String plain_text) throws Exception {
        if (key == null || key.isEmpty()) createKeyRandom(plain_text.length());

        StringBuilder encrypted_text = new StringBuilder();
        int key_index = 0;

        for (int i = 0; i < plain_text.length(); i++) {
            char plain_char = plain_text.charAt(i);
            char key_char = key.charAt(key_index);

            // TH: Nếu là kí tự chữ cái hoa trong bảng chữ cái Tiếng Việt
            if (ALPHABET_UPPERCASE.contains(String.valueOf(plain_char))) {

                int plain_index = ALPHABET_UPPERCASE.indexOf(plain_char);
                int key_char_index = ALPHABET_UPPERCASE.indexOf(Character.toUpperCase(key_char));

                int encrypted_index = (plain_index + key_char_index) % ALPHABET_UPPERCASE.length();
                encrypted_text.append(ALPHABET_UPPERCASE.charAt(encrypted_index));

            }
            // TH: Nếu là kí tự chữ cái thường trong bảng chữ cái Tiếng Việt
            else if (ALPHABET_LOWERCASE.contains(String.valueOf(plain_char))) {

                int plain_index = ALPHABET_LOWERCASE.indexOf(plain_char);
                int key_char_index = ALPHABET_LOWERCASE.indexOf(Character.toLowerCase(key_char));

                int encrypted_index = (plain_index + key_char_index) % ALPHABET_LOWERCASE.length();
                encrypted_text.append(ALPHABET_LOWERCASE.charAt(encrypted_index));

            } else {
                // Ký tự không thuộc bảng chữ cái tiếng Việt, giữ nguyên
                encrypted_text.append(plain_char);
            }

            key_index = (key_index + 1) % key.length();
        }
        return encrypted_text.toString();
    }

    public String decrypt(String encrypted_text) throws Exception {
        if (key == null || key.isEmpty()) throw new Exception("Key is null or empty");

        StringBuilder decrypted_text = new StringBuilder();
        int key_index = 0;

        for (int i = 0; i < encrypted_text.length(); i++) {
            char encrypt_char = encrypted_text.charAt(i);
            char key_char = key.charAt(key_index);

            // TH: Nếu là kí tự chữ cái hoa trong bảng chữ cái Tiếng Việt
            if (ALPHABET_UPPERCASE.contains(String.valueOf(encrypt_char))) {

                int encrypt_index = ALPHABET_UPPERCASE.indexOf(encrypt_char);
                int key_char_index = ALPHABET_UPPERCASE.indexOf(Character.toUpperCase(key_char));

                int decrypted_index = (encrypt_index - key_char_index + ALPHABET_UPPERCASE.length()) % ALPHABET_UPPERCASE.length();
                decrypted_text.append(ALPHABET_UPPERCASE.charAt(decrypted_index));

            }
            // TH: Nếu là kí tự chữ cái thường trong bảng chữ cái Tiếng Việt
            else if (ALPHABET_LOWERCASE.contains(String.valueOf(encrypt_char))) {

                int encrypt_index = ALPHABET_LOWERCASE.indexOf(encrypt_char);
                int key_char_index = ALPHABET_LOWERCASE.indexOf(Character.toLowerCase(key_char));

                int decrypted_index = (encrypt_index - key_char_index + ALPHABET_LOWERCASE.length()) % ALPHABET_LOWERCASE.length();
                decrypted_text.append(ALPHABET_LOWERCASE.charAt(decrypted_index));

            } else {
                // Ký tự không thuộc bảng chữ cái tiếng Việt, giữ nguyên
                decrypted_text.append(encrypt_char);
            }

            key_index = (key_index + 1) % key.length();
        }
        return decrypted_text.toString();
    }


    public void encryptFile(String srcFile, String destFile) throws Exception {
        // Thực hiện mã hóa tệp và lưu kết quả vào tệp khác (để được triển khai)
    }

    public void decryptFile(String srcFile, String destFile) throws Exception {
        // Thực hiện giải mã tệp và lưu kết quả vào tệp khác (để được triển khai)
    }

    public String exportKey() {
        if (key == null) return "";
        return key;
    }

    public String importKey(String key_input) throws Exception {
        if (key_input == null || key_input.isEmpty()) throw new Exception("Key Invalid");
        key = key_input;
        return key;
    }

    public static void main(String[] args) throws Exception {
        String plaintext = "Bác sĩ Nguyễn Tri Thức: Khám sức khỏe tiền hôn nhân vì lợi ích thế hệ sau\n" +
                "Giám đốc Bệnh viện Chợ Rẫy TP HCM nói ông đề xuất bắt buộc khám sức khỏe tiền hôn nhân nhằm đảm bảo hạnh phúc gia đình, mang lại lợi ích cho xã hội và thế hệ mai sau.\n" +
                "\n" +
                "Tại phiên thảo luận ở Quốc hội ngày 1/11, bác sĩ Nguyễn Tri Thức, Giám đốc Bệnh viện Chợ Rẫy TP HCM đề xuất ban hành quy định bắt buộc người dân khám sức khỏe tiền hôn nhân. VnExpress phỏng vấn ông Thức về đề xuất này.\n" +
                "\n" +
                "- Tại sao ông lại đề xuất quy định bắt buộc khám sức khỏe tiền hôn nhân?\n" +
                "\n" +
                "- Trước khi làm Giám đốc Bệnh viện Chợ Rẫy TP HCM, tôi từng là bác sĩ tim mạch. Chúng tôi tiếp nhận nhiều ca sản phụ bị suy thận, suy tim nặng, hẹp van tim. Có người đến lúc sinh con mới biết bị bệnh bởi trước đó chưa từng khám sức khỏe. Vì có bệnh lý, khi sinh con họ bị suy tim cấp. Nếu chúng tôi can thiệp kịp thời và tình trạng mẹ tương đối tốt mới hy vọng cứu được cả mẹ và con.\n" +
                "\n" +
                "Nhưng đa số các ca sản phụ chúng tôi tiếp nhận đều bệnh nặng, phải quyết định cứu mẹ hoặc con. Nếu cứu con, bác sĩ tính toán không dùng nhiều thuốc. Nếu cứu mẹ, phải dùng nhiều thuốc, sẽ ảnh hưởng thai nhi. Bác sĩ đứng trước quyết định rất khó khăn và đau lòng. Trong khi đó, những ca bệnh này có thể tránh được nếu người dân khám sức khỏe tiền hôn nhân.\n" +
                "\n" +
                "Nhiều cặp vợ chồng sau khi kết hôn mới biết một trong hai người có bệnh lý liên quan đến tình dục và di truyền. Sau khi sinh con, nhiều người mới biết con mang bệnh di truyền từ gen lặn, để lại hậu quả lớn. Thời gian qua, nhiều vụ bạo lực gia đình xảy ra liên quan đến rối loạn hành vi. Không khám sức khỏe tâm thần tiền hôn nhân nên họ không hay biết và cũng không lý giải được vì sao.\n" +
                "\n" +
                "Nếu ban hành quy định bắt buộc khám sức khỏe tiền hôn nhân thì mỗi cặp vợ chồng trước khi kết hôn có thể chuẩn bị chu đáo cả về tâm lý và sức khỏe để tránh những hệ quả đáng tiếc sau này. Các thế hệ bác sĩ và nhà làm luật trước đây cũng từng nghĩ đến ý tưởng này nhưng trình độ y khoa, điều kiện kinh tế - xã hội, đời sống và nhận thức người dân đều chưa thích hợp.\n" +
                "\n" +
                "Tôi đặt vấn đề vào thời điểm này vì trình độ y khoa Việt Nam đã có nhiều tiến bộ, kinh phí khám bệnh không quá lớn, đời sống và nhận thức người dân đã được nâng cao.";

        Cipher_Vigenere_Vietnamese vigenere = new Cipher_Vigenere_Vietnamese();
        String encryptedText = "";
        String decryptedText = "";

//        for (int i = 0; i < 1000; i++) {
//            vigenere.key = null;
//            encryptedText = vigenere.encrypt(plaintext);
//            System.out.println("Khóa:" + vigenere.exportKey());
//            System.out.println("----------------------------------------------------");
//            decryptedText = vigenere.decrypt(encryptedText);
//            System.out.println("Kiểm tra Giải mã: " + plaintext.equals(decryptedText));
//            System.out.println("----------------------------------------------------");
//        }

        vigenere.importKey("tranminhtuyen");
        encryptedText = vigenere.encrypt(plaintext);
        System.out.println("Key: " + vigenere.exportKey());
        System.out.println("----------------------------------------------------");
        System.out.println("Encrypted: " + encryptedText);
        System.out.println("----------------------------------------------------");
        decryptedText = vigenere.decrypt(encryptedText);
        System.out.println("Decrypted: " + decryptedText);
        System.out.println("----------------------------------------------------");
        System.out.println("Check Decrypted: " + plaintext.equals(decryptedText));
    }
}
