package utils;

import java.io.File;

public class FileUtil {

    public static boolean deleteFile(String path) {
        File file = new File(path);
        try {
            if (file.isFile() && file.exists()) return file.delete();
            return false;
        } catch (Exception e) {
            return false;
        }

    }

    public static void main(String[] args) {

        String path = "C:/Users/tmt01/Downloads/Nhom5_cho_thue_san_bong_da_mini.docx";
        System.out.println(FileUtil.deleteFile(path));
    }
}
