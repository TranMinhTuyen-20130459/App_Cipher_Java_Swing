package services.ma_hoa_doi_xung.interfaces;

import javax.crypto.SecretKey;

public interface I_Create {
    public SecretKey createKeyRandom(int key_size) throws Exception;
}
