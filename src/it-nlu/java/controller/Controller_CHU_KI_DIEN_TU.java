package controller;

public class Controller_CHU_KI_DIEN_TU {

    public static boolean checkFileValid(String algorithm, String srcFile, String hash_text_input) {
        if (algorithm == null || srcFile == null || hash_text_input == null) return false;
        if (algorithm.isEmpty() || srcFile.isEmpty() || hash_text_input.isEmpty()) return false;
        try {
            String hash_text = Controller_MA_HOA_HASH.hashFile(algorithm, srcFile);

            if (hash_text == null || hash_text.isEmpty()
                    || hash_text.equalsIgnoreCase("Error")
                    || hash_text.equalsIgnoreCase("NOT_FOUND_ALGORITHM"))
                return false;

            if (hash_text_input.equals(hash_text)) return true;
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
