package model.services.ma_hoa_doi_xung;

import java.util.Random;

public class Cipher_Vigenere_English {
    private static final String ALPHABET_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHABET_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private String key;

    public String createKeyRandom(int key_size) throws Exception {

        if (key_size <= 0) throw new Exception("Key size must be >0");

        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int length_string_random = random.nextInt(1, key_size);
        StringBuilder randomString = new StringBuilder(length_string_random);

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
        int key_index = 0; // Biến đếm vị trí trong chuỗi key
        for (int i = 0; i < plain_text.length(); i++) {
            char plain_char = plain_text.charAt(i);
            char key_char = key.charAt(key_index); // Sử dụng keyChar tại vị trí key_index

            // TH: Nếu là kí tự chữ cái hoa
            if (Character.isUpperCase(plain_char)) {
                int plain_index = ALPHABET_UPPERCASE.indexOf(plain_char);
                int key_char_index = ALPHABET_UPPERCASE.indexOf(Character.toUpperCase(key_char));
                int encrypted_index = (plain_index + key_char_index) % 26;
                encrypted_text.append(ALPHABET_UPPERCASE.charAt(encrypted_index));
            }
            // TH: Nếu là kí tự chữ kí thường
            else if (Character.isLowerCase(plain_char)) {
                int plain_index = ALPHABET_LOWERCASE.indexOf(plain_char);
                int key_char_index = ALPHABET_LOWERCASE.indexOf(Character.toLowerCase(key_char));
                int encrypted_index = (plain_index + key_char_index) % 26;
                encrypted_text.append(ALPHABET_LOWERCASE.charAt(encrypted_index));
            }
            // TH: Không phải là chữ cái
            else {
                // Keep non-alphabet characters unchanged
                encrypted_text.append(plain_char);
            }

            // Tăng biến đếm vị trí trong chuỗi key, lặp lại nếu cần
            key_index = (key_index + 1) % key.length();
        }
        return encrypted_text.toString();
    }

    public String decrypt(String encrypted_text) throws Exception {
        if (key == null || key.isEmpty()) throw new Exception("Key is null or empty");

        StringBuilder decrypted_text = new StringBuilder();
        int key_index = 0; // Biến đếm vị trí trong chuỗi key
        for (int i = 0; i < encrypted_text.length(); i++) {

            char encrypt_char = encrypted_text.charAt(i);
            char key_char = key.charAt(key_index); // Sử dụng keyChar tại vị trí keyIndex

            // TH: Nếu là kí tự chữ cái hoa
            if (Character.isUpperCase(encrypt_char)) {
                int encrypt_index = ALPHABET_UPPERCASE.indexOf(encrypt_char);
                int key_char_index = ALPHABET_UPPERCASE.indexOf(Character.toUpperCase(key_char));
                int decrypted_index = (encrypt_index - key_char_index + 26) % 26;
                decrypted_text.append(ALPHABET_UPPERCASE.charAt(decrypted_index));
            }
            // TH: Nếu là kí tự chữ cái thường
            else if (Character.isLowerCase(encrypt_char)) {
                int encrypt_index = ALPHABET_LOWERCASE.indexOf(encrypt_char);
                int key_char_index = ALPHABET_LOWERCASE.indexOf(Character.toLowerCase(key_char));
                int decrypted_index = (encrypt_index - key_char_index + 26) % 26;
                decrypted_text.append(ALPHABET_LOWERCASE.charAt(decrypted_index));
            }
            //TH: Nếu không phải là chữ cái
            else {
                // Keep non-alphabet characters unchanged
                decrypted_text.append(encrypt_char);
            }

            // Tăng biến đếm vị trí trong chuỗi key, lặp lại nếu cần
            key_index = (key_index + 1) % key.length();
        }
        return decrypted_text.toString();
    }

    public void encryptFile(String srcFile, String destFile) throws Exception {
    }

    public void decryptFile(String srcFile, String destFile) throws Exception {
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
        String plaintext = "TRAN MINH TUYEN 123@/  ";

        Cipher_Vigenere_English vigenere = new Cipher_Vigenere_English();
        String encryptedText = "";
        for (int i = 0; i < 1000; i++) {
            encryptedText = vigenere.encrypt(plaintext);
            System.out.println("Encrypted: " + encryptedText);
            System.out.println("Key:" + vigenere.exportKey());
            vigenere.key = null;
            System.out.println("----------------------------------------------------");
        }

        encryptedText = vigenere.encrypt(plaintext);
        System.out.println("Key: " + vigenere.exportKey());
        System.out.println("----------------------------------------------------");
        String decryptedText = vigenere.decrypt(encryptedText);
        System.out.println("Decrypted: " + decryptedText);
        System.out.println("----------------------------------------------------");
        System.out.println("Check Decrypted: " + plaintext.equals(decryptedText));
    }
}
