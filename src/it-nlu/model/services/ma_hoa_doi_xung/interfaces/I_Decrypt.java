package model.services.ma_hoa_doi_xung.interfaces;

public interface I_Decrypt {

    public String decrypt(byte[] encrypt) throws Exception;

    // decrypt base64 string
    public String decryptFromBase64(String text) throws Exception;

    public void decryptFile(String srcFile, String destFile) throws Exception;
}
