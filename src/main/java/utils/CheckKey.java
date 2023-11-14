package utils;

import helper.Algorithm;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Base64;

public class CheckKey {

    public static boolean isValidKeySymmetric(String algorithm, String key) {
        try {
            if (algorithm == null || key == null) return false;
            if (algorithm.isEmpty() || key.isEmpty()) return false;

            switch (algorithm.toUpperCase()) {
                case Algorithm.AES:
                case Algorithm.BLOW_FISH:
                case Algorithm.DES:
                case Algorithm.TWO_FISH:
                case Algorithm.SERPENT:
                    return isValidBase64Key(algorithm, key);
                case Algorithm.VIGENERE:
                    return true;
                default:
                    return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean isValidBase64Key(String algorithm, String key) {
        try {
            byte[] key_bytes_decoded = Base64.getDecoder().decode(key);
            var secret_key = new SecretKeySpec(key_bytes_decoded, algorithm);

            // Kiểm tra key với Cipher
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secret_key);

            // Nếu không có lỗi, key được xem là hợp lệ
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        String[] arr_algorithms = {"AES", "DES", "TwoFish", "Blowfish", "Serpent"};
        String[] arr_key = {"TRANMINHTUYEN", "NfT6ufuqOKKNHcc0cewYUg==", "/479mm8wQhtf3CvnEV6HNbBafDkGBGdP", "mej856AxITP1QbpU3yVQnSsZkulC4ko6Ajuk41JVpeI=", "wq3PFw==", "CGFwUMVnbEQ=", "xYIGgi87d76eXmmciS5p1A==", "CNeQdQVwgqCaq6pyBwKN7j4VsQRLWb80PSTdCMlo4MY=", "YUDmfJZeHwKnX2GMgyTn9JY1IvaO/yd5oxGWyp7Ibqq71LQK2zSstSyWwoSmeF8rPBUeIHx6v0s=", "YUDmfJZeHwKnX2GMgyTn9JY1IvaO/yd5oxGWyp7Ibqq71LQK2zSstSyWwoSmeF8rPBUeIHx6v0s=", "WGi7DXfX7vHW6wzrvMXNjQ==", "Mg9ABYPUHpN8wN/UJllERVwYoyJKaZ7i", "wIF3Vq/TOIOtVVwGTR3FAt5Dxbry0trowgIUefgsNA8=", "ztA5M2HONbkrllDTMVBYFA==", "mbk2pS/htgaCmSy2MF9VH/yoF6Vf96/n", "KUWIMmc5E2f2BzBGyTk6PgD6x6UC0BdpWtaOJ7dWrVs="};

        for (String algorithm : arr_algorithms) {

            for (String key : arr_key) {
                boolean checkKey = isValidKeySymmetric(algorithm, key);
                System.out.println("Check Key " + key + "_" + algorithm + ": " + checkKey);
                System.out.println("--------------------------------");
            }

        }
    }
}
