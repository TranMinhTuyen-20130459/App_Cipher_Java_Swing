package services.ma_hoa_doi_xung.interfaces;

import javax.crypto.SecretKey;

public interface I_Import {

    public SecretKey importKey(String keyText) throws Exception;

}
