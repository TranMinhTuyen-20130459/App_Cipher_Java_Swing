package controller;

import model.services.ma_hoa_hash.*;

public class Controller_MA_HOA_HASH {

    public static String hashText(String algorithm, String input_text) {
        try {
            if (algorithm == null || input_text == null) return "";
            if (algorithm.isEmpty() || input_text.isEmpty()) return "";

            switch (algorithm.toUpperCase()) {
                case MD5.MD5: {
                    MD5 md5 = new MD5();
                    return md5.hashText(input_text);
                }
                case SHA.SHA_1:
                case SHA.SHA_224:
                case SHA.SHA_256:
                case SHA.SHA_384:
                case SHA.SHA_512: {
                    SHA sha = new SHA();
                    return sha.hashText(input_text, algorithm);
                }
                default:
                    return "NOT_FOUND_ALGORITHM";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
        }
    }

    public static String hashFile(String algorithm, String srcFile) {

        try {

            if (algorithm == null || srcFile == null) return "";
            if (algorithm.isEmpty() || srcFile.isEmpty()) return "";

            switch (algorithm.toUpperCase()) {
                case MD5.MD5: {
                    MD5 md5 = new MD5();
                    return md5.hashFile(srcFile);
                }
                case SHA.SHA_1:
                case SHA.SHA_224:
                case SHA.SHA_256:
                case SHA.SHA_384:
                case SHA.SHA_512: {
                    SHA sha = new SHA();
                    return sha.hashFile(srcFile, algorithm);
                }

                default:
                    return "NOT_FOUND_ALGORITHM";

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
        }

    }


}
