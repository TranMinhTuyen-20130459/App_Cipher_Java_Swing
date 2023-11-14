package utils;

import helper.Algorithm;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CheckKey {


    public static boolean isValidPublicKeyAsymmetric(String algorithm, String public_key) {
        try {
            if (algorithm == null || public_key == null) return false;
            if (algorithm.isEmpty() || public_key.isEmpty()) return false;

            switch (algorithm.toUpperCase()) {
                case Algorithm.RSA: {
                    return isValidBase64PublicKey(algorithm, public_key);
                }
                default:
                    return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

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

    public static boolean isValidBase64PublicKey(String algorithm, String public_key) {
        try {
            // Decode Base64 public key
            byte[] key_bytes_decoded = Base64.getDecoder().decode(public_key);

            // Initialize KeyFactory with RSA algorithm
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

            // Create X509EncodedKeySpec from decoded key bytes
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key_bytes_decoded);

            // Try to generate public key
            keyFactory.generatePublic(keySpec);

            // If no exception is thrown, the public key is valid
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());

        // TestCheckKeySymmetric();
        TestCheckPublicKey();

    }

    public static void TestCheckKeySymmetric() {

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

    public static void TestCheckPublicKey() {

        String algorithm = "RSA";
        String[] arr_public_key = {"MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALlWgaHJ/LDynRq0xhSl7mQRAXnINIj2IZezy3x6nNclsea0qRlY4u48fYycaUGSWXTfi1QMExIglPX7GXEHOb8CAwEAAQ==",
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0Y7CsKIqbkPDs0fTUZnRAr7eaGYcu620p32HNH63drWzMZXv5/1VOPYExHwTKdWR8q/ML7sGRHm34aGxuaGw1+X4IrPcBQPZPERUH494VBdwzB3jSqeAlwXBCbNkHip7k4NXVvCEtL58aHNz3cAtMRCbiiHi5R8mOk8tcGUgCLwIDAQAB"
                , "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzviZlgA9rbTglYyjT3fbzRQN8Xl0UmQTvk9CWdMOdGMVsuLJsah12r0umA7HsSOUH7eEQ57ZV1kWG2W5XzIyC5aLKw2yCtBUGY8qoehGymxAxvtmSEFpVpWwDFtnv1bzxT2tyvhAGi3bRc+QtKs/FruIVaR/eeOEVLCSw66DKprSGsX1XQFwxBrbBywymLY9vfwp9c4vZ4nGU3Q2B3dfb7ljCVuYNqGK/FOCHQx+J16VwgzOEtMLs/DG74VXjN0oi6/sBYAG+fnsSZlcVXnMP1V1npBLOhrD6gxA8OAPb4l5aoLTqHSayESzDYAKcg1a47SxvtYiqrSeMYDyNIvGgQIDAQAB"
                , "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAwk209zqJnj1hVx38KZ/I581D8HsA9tH00M6tLiaGzpqd1Ur7o/gPU2PwlPuwUKTEb/5n9nkOLEpuufdYmvUh69ikkOCJUw5DPeybdQXu2usaiSJ88vfiUQH4BxQdEsBJfB1LAvXz66s/86chiFgLGKfaSSpp1k5kUB/ln86jv+dagv7irhBfSq+Zwhrx4DbwGzMeg1SP2+S4KB+9grehoo/RjaR/jEnEvAIOqqLaBrKMS+7l4wU5JeaUcaxvNc/Zg/jHg0aLqMLPoiOI5kg8dlxKUhGMdwxWeBXVmj0UCDf6vyT5CxLhRh4DAPf3+RD9e9ikxJl83rBGiC2dAvHgXEbCPi1W+jSlZp3aH9SxXwjylEYdiwkocZN/SRPIXWSEemThlSCBEgo0zaSp78J8d8n2ldIad2xd1S8A7dWEQOM1EP0jaI0eZ1ZsKtBooSbjuSkWl56UmXNf/AMzC9eWUz8XuczbSPKooI3YI/qh9b/DOrxRKETKHiy1L8kAT8xfezN88xnrHiWl7NTZzomEvNn40fKkqTXy7suhKGVSFTSoKkHx669otl1ZOwAeHeOficOtHfHQZ0NYiVdjcv+e81nzRUCxYHgYVMFbZCACjiW1iIV+Kha39nLm9JpLaMKwkAIBFtvnv9dSX2tFkzzz6ief7Qskzu37WeBKmKoZL5ECAwEAAQ=="
                , "1MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALlWgaHJ/LDynRq0xhSl7mQRAXnINIj2IZezy3x6nNclsea0qRlY4u48fYycaUGSWXTfi1QMExIglPX7GXEHOb8CAwEAAQ=="
                , "12MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0Y7CsKIqbkPDs0fTUZnRAr7eaGYcu620p32HNH63drWzMZXv5/1VOPYExHwTKdWR8q/ML7sGRHm34aGxuaGw1+X4IrPcBQPZPERUH494VBdwzB3jSqeAlwXBCbNkHip7k4NXVvCEtL58aHNz3cAtMRCbiiHi5R8mOk8tcGUgCLwIDAQAB"
                , "123MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAwk209zqJnj1hVx38KZ/I581D8HsA9tH00M6tLiaGzpqd1Ur7o/gPU2PwlPuwUKTEb/5n9nkOLEpuufdYmvUh69ikkOCJUw5DPeybdQXu2usaiSJ88vfiUQH4BxQdEsBJfB1LAvXz66s/86chiFgLGKfaSSpp1k5kUB/ln86jv+dagv7irhBfSq+Zwhrx4DbwGzMeg1SP2+S4KB+9grehoo/RjaR/jEnEvAIOqqLaBrKMS+7l4wU5JeaUcaxvNc/Zg/jHg0aLqMLPoiOI5kg8dlxKUhGMdwxWeBXVmj0UCDf6vyT5CxLhRh4DAPf3+RD9e9ikxJl83rBGiC2dAvHgXEbCPi1W+jSlZp3aH9SxXwjylEYdiwkocZN/SRPIXWSEemThlSCBEgo0zaSp78J8d8n2ldIad2xd1S8A7dWEQOM1EP0jaI0eZ1ZsKtBooSbjuSkWl56UmXNf/AMzC9eWUz8XuczbSPKooI3YI/qh9b/DOrxRKETKHiy1L8kAT8xfezN88xnrHiWl7NTZzomEvNn40fKkqTXy7suhKGVSFTSoKkHx669otl1ZOwAeHeOficOtHfHQZ0NYiVdjcv+e81nzRUCxYHgYVMFbZCACjiW1iIV+Kha39nLm9JpLaMKwkAIBFtvnv9dSX2tFkzzz6ief7Qskzu37WeBKmKoZL5ECAwEAAQ=="
        };

        for (String public_key : arr_public_key) {

            boolean checkKey = isValidPublicKeyAsymmetric(algorithm, public_key);
            System.out.println(checkKey + " Check Key " + public_key + "_" + algorithm);
            System.out.println("--------------------------------");

        }

    }
}
