package services.ma_hoa_doi_xung.interfaces;

import javax.crypto.SecretKey;

public interface I_Encrypt {

    public byte[] encrypt(String text) throws Exception;

    // encrypt string and return base64 string
    /**
     * Base64 là một phương pháp mã hóa dữ liệu nhị phân thành văn bản bằng cách sử dụng một bảng mã 64 ký tự, gồm chữ cái, số và một số ký tự đặc biệt.
     */
    public String encryptToBase64(String text) throws Exception;

    public void encryptFile(String srcFile, String destFile) throws Exception;
}
