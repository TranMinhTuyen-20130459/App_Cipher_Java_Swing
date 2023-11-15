package utils;

import helper.Algorithm;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CheckKey {


    public static boolean isValidPrivateKey(String algorithm, String private_key) {

        try {

            if (algorithm == null || private_key == null) return false;
            if (algorithm.isEmpty() || private_key.isEmpty()) return false;

            switch (algorithm.toUpperCase()) {

                case Algorithm.RSA:
                    return isValidBase64PrivateKey(algorithm, private_key);

                default:
                    return false;

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean isValidPublicKey(String algorithm, String public_key) {
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

    public static boolean isValidBase64PrivateKey(String algorithm, String private_key) {
        try {
            // Decode Base 64 private_key
            byte[] key_bytes_decoded = Base64.getDecoder().decode(private_key);

            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key_bytes_decoded);

            // Sử dụng KeyFactory để chuyển đổi PKCS8EncodedKeySpec thành PrivateKey
            keyFactory.generatePrivate(keySpec);

            // Nếu không xảy ra lỗi thì private_key hợp lệ
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false; // private_key không hợp lệ
        }
    }


    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());

        // TestCheckKeySymmetric();
        //TestCheckPublicKey();
        TestCheckPrivateKey();

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

            boolean checkKey = isValidPublicKey(algorithm, public_key);
            System.out.println(checkKey + " Check Key " + public_key + "_" + algorithm);
            System.out.println("--------------------------------");

        }

    }

    public static void TestCheckPrivateKey() {
        String algorithm = "RSA";
        String[] arr_private_key = {
                "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAvbWbYbe2VMUDf7cYa0MZ4I4/igOD5wFJdbZPRpapEWLFEzj7s8dDiyqu7MBhw7kL82fWCSspgsN+WIgYrw7Y8wIDAQABAkAVq83Es2ZON7VHHSTad+9YUhebDfy+4kxAeKOEtkg49oOgOeSCBC2bk0O7lkDmeHtDWo4r8vBCV/iLcXfUixIJAiEA218zuuSsfTeFt6y4cALznfGnzHHGLlb+Rcz88Q96j1sCIQDdYoQxlAstd4lKqBHGm8bBXMzcE+2vZw5z+o6MPc1oSQIgIGKWlXGq0TWAJRR3ifm1k0yYlAL72d133kAb206Irl8CIFPZWmRExC8GVVOXcJQesBrQgS7hxWZnzAuVXKQ1TDUxAiAivnZqWnDug+fIj28KvbXuPAawB5vQT+SwtC0Yea5Sbw=="
                , "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI+UUfurn9Hl8M+UXIlO4OuLhj7rymkhfj7Ns+zrQ8R0Ia7QmugWTPxmyIbh06bvMjSpvuoe8fkl3VzkeRi6Oa2grXjSI2jWN05iHLnZ3eUMgi5VPhyLFcoTgN0ltgAaw0RqpH+nfPK9lNQGV8EllI1Es07KbvZT+e6cN81kRvNPAgMBAAECgYAM8lVgKa4cGa1YsmqTsQqkAq1EwDM8vpnpJNhMDTzLmqMs5b+KHPStL8F2NCfHHjt7NzExYD/2dN0ckrd+rNAlflRSigVis23y/WNVrnfAuZbVedd0ZfIHT2lq4cZ8WesM/aTTJ0jkSuMADflrCugkZWsDkSFtbEdkEHPMunG6gQJBAKKzh73FRuBeHZwNVoi+mQ2h3PEhUkmoliMQJSHVrLVU23dh6CrJPVuV44Q9lXdC2Mkth2mSkdEdlo/3RCYLJIECQQDh6bPPYj0LHK/LkcG4SreF2+sKLtgZxSzFt4NDvF1Xo1ndzZJDJjjIPX8ISY/jSqcB4jl6KPruY6Orex5iMO/PAkB4/IOuLGreIV5nUsvMd98ccgb4EE0+lOzAOhtCo8AAP4/LoLSiTG8iEb3T8m+Y9c57Gy4c5W8lT5lMiRTWLWcBAkBh93qmQC/AldwWJ0sepCgZwFiYvd+wrTTRONePlCvWSVw7N15qCDUvgJuJ+HyRUFpEEtpiA0tt/X+IaYevhRE5AkAj1cAtGFOIo6iv8AlLciXCu2k55ualWOTBAqdefqKLHqLRn6ge8VJCHVONjG/s0xToQ5JYcBeirMYhlkMtWOvA"
                , "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC1u7X2/0j5ST+Fj710fQHZ13u9PXtN3vKYqphMamtORh4maYvcWzy57x8K8hdWu+Qs2Zs8/CoxFD2jfJU9xiaT0KyWoxkiLe7OtaPxK+rezYPwvxevA+i6ifRWiSfd6m8+brmvLebV1tebpYdlYUkH1by+xHiebPOkfW1ner1VKzIqHwn6AXqIFb5Ho3lEjGIyP6oUToBj08oasXKSHimdV5YWXSW013Kghj8UkOYmPI8dNJLfIP42UTNe8TdsNR/oWK/QH0N97siZhDlCNSNZka8xwLnynU1E9evI6Y08/wZiRafWHkPAOrOQsYxzTXQEhqfpT20d1T0ryr6Wqa6jAgMBAAECggEAS1HjFt2zRu5ez2JFqTPuQnUWqdYDdsDCVxfNS06H7scM0+rGtqtxvbYnbYfsuFvjM9t0hZAVhIXZDhsiV5n6fyTrgvtR/WKAKVgK/HexPmp7Fpp8nGLIYBexFvR9e3IgbQXDG6GwnEwQzEeMmC378K1bLs2LGE4bpL7/SvYJ5nPvrLBkVj9y7FhxSCk/yFX16yD2YSncchwwUcH6ialrRnOpYpKqMbvYsyEDMWoHXvQmE864CszgT304WFTKFGLtAzxqRgvgoIonqEmywacJlnDJEMNRXHPWGJutZpDPdAu579Gywl6Ebi4p9u1wp3zaQEZ0/YP/dntaOUay1/GSIQKBgQDjIGw85FjCLy3sbAHne93OF4865oels/rYMYooabxlTUXtKl+JUnohQbGqyW/D6aa9t3fncG4ZRzCM+N1CTAoHKwN6SvpkgyM3AgYe8Vi37btngD71teI+OpTLcU30+3+wqFYeha0/89aC9dx9/d+zV2dRbAo0Ah/ezqQBRC//iQKBgQDM1gLw6YPDagIjPvcENN0dz6O77yySYQyNRZckWSpvBLLKPwYa3PHemm+16uiAWELoEv9X0ablMc3OcKkwIi0BklKmbSpjPsd+y1X2Kv5bRZZO6sm78glHz+jFFnXuux/cR+qSy4mzXAWP6xTse4WZpXtGiuiMRPG88lIJW0FlywKBgEV7eMFUFfcp2cG29Qos8ZkHyhB5z8uYeMfEuABPAIx5DEhWqy1mZuhfoYue3iswAouNYggSdAwVpDusMnwCqRqyRPkXkOxI1fARFXBH4x05hBhQunIOt2zUHQrz2aZ1fgW8KcRinrKu1NPxhQeiL9o8heX5yFFKyRcvXirpAkoJAoGAet9yGjPpz4I60AvIP6S6c1SIIodBlHlU/zWcibhXJQuLeJi/v8OD74Vyt4y3da38TJyAwZxI4jmy/EBGrVIC72tn2t+nSqtxHlU5Npbpx7SBist6whxZhtNoDToA+nkTjp8xTmd8LbRovcFF5und5aIHxfQztVjyLeaqYWR1YWsCgYEAjCxCtosL7y7RvQ9KP3BBEogBAD7HhBTW+ldlE3f9UgqOOlP0/U43UPKYMtHzNTcaUDX6Z1uTLtPGmwrETFpH/qbgtesMmShPM4ulZI+92mDxfMQ22vkdLqOwWh4MYkUVE4za9qZVlLjPPVbq/CGlFRFSXCpAC8Uypd3nWFkm89U="
                , "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"
                , "1MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAvbWbYbe2VMUDf7cYa0MZ4I4/igOD5wFJdbZPRpapEWLFEzj7s8dDiyqu7MBhw7kL82fWCSspgsN+WIgYrw7Y8wIDAQABAkAVq83Es2ZON7VHHSTad+9YUhebDfy+4kxAeKOEtkg49oOgOeSCBC2bk0O7lkDmeHtDWo4r8vBCV/iLcXfUixIJAiEA218zuuSsfTeFt6y4cALznfGnzHHGLlb+Rcz88Q96j1sCIQDdYoQxlAstd4lKqBHGm8bBXMzcE+2vZw5z+o6MPc1oSQIgIGKWlXGq0TWAJRR3ifm1k0yYlAL72d133kAb206Irl8CIFPZWmRExC8GVVOXcJQesBrQgS7hxWZnzAuVXKQ1TDUxAiAivnZqWnDug+fIj28KvbXuPAawB5vQT+SwtC0Yea5Sbw=="
                , "12MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI+UUfurn9Hl8M+UXIlO4OuLhj7rymkhfj7Ns+zrQ8R0Ia7QmugWTPxmyIbh06bvMjSpvuoe8fkl3VzkeRi6Oa2grXjSI2jWN05iHLnZ3eUMgi5VPhyLFcoTgN0ltgAaw0RqpH+nfPK9lNQGV8EllI1Es07KbvZT+e6cN81kRvNPAgMBAAECgYAM8lVgKa4cGa1YsmqTsQqkAq1EwDM8vpnpJNhMDTzLmqMs5b+KHPStL8F2NCfHHjt7NzExYD/2dN0ckrd+rNAlflRSigVis23y/WNVrnfAuZbVedd0ZfIHT2lq4cZ8WesM/aTTJ0jkSuMADflrCugkZWsDkSFtbEdkEHPMunG6gQJBAKKzh73FRuBeHZwNVoi+mQ2h3PEhUkmoliMQJSHVrLVU23dh6CrJPVuV44Q9lXdC2Mkth2mSkdEdlo/3RCYLJIECQQDh6bPPYj0LHK/LkcG4SreF2+sKLtgZxSzFt4NDvF1Xo1ndzZJDJjjIPX8ISY/jSqcB4jl6KPruY6Orex5iMO/PAkB4/IOuLGreIV5nUsvMd98ccgb4EE0+lOzAOhtCo8AAP4/LoLSiTG8iEb3T8m+Y9c57Gy4c5W8lT5lMiRTWLWcBAkBh93qmQC/AldwWJ0sepCgZwFiYvd+wrTTRONePlCvWSVw7N15qCDUvgJuJ+HyRUFpEEtpiA0tt/X+IaYevhRE5AkAj1cAtGFOIo6iv8AlLciXCu2k55ualWOTBAqdefqKLHqLRn6ge8VJCHVONjG/s0xToQ5JYcBeirMYhlkMtWOvA"
                , "123MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC1u7X2/0j5ST+Fj710fQHZ13u9PXtN3vKYqphMamtORh4maYvcWzy57x8K8hdWu+Qs2Zs8/CoxFD2jfJU9xiaT0KyWoxkiLe7OtaPxK+rezYPwvxevA+i6ifRWiSfd6m8+brmvLebV1tebpYdlYUkH1by+xHiebPOkfW1ner1VKzIqHwn6AXqIFb5Ho3lEjGIyP6oUToBj08oasXKSHimdV5YWXSW013Kghj8UkOYmPI8dNJLfIP42UTNe8TdsNR/oWK/QH0N97siZhDlCNSNZka8xwLnynU1E9evI6Y08/wZiRafWHkPAOrOQsYxzTXQEhqfpT20d1T0ryr6Wqa6jAgMBAAECggEAS1HjFt2zRu5ez2JFqTPuQnUWqdYDdsDCVxfNS06H7scM0+rGtqtxvbYnbYfsuFvjM9t0hZAVhIXZDhsiV5n6fyTrgvtR/WKAKVgK/HexPmp7Fpp8nGLIYBexFvR9e3IgbQXDG6GwnEwQzEeMmC378K1bLs2LGE4bpL7/SvYJ5nPvrLBkVj9y7FhxSCk/yFX16yD2YSncchwwUcH6ialrRnOpYpKqMbvYsyEDMWoHXvQmE864CszgT304WFTKFGLtAzxqRgvgoIonqEmywacJlnDJEMNRXHPWGJutZpDPdAu579Gywl6Ebi4p9u1wp3zaQEZ0/YP/dntaOUay1/GSIQKBgQDjIGw85FjCLy3sbAHne93OF4865oels/rYMYooabxlTUXtKl+JUnohQbGqyW/D6aa9t3fncG4ZRzCM+N1CTAoHKwN6SvpkgyM3AgYe8Vi37btngD71teI+OpTLcU30+3+wqFYeha0/89aC9dx9/d+zV2dRbAo0Ah/ezqQBRC//iQKBgQDM1gLw6YPDagIjPvcENN0dz6O77yySYQyNRZckWSpvBLLKPwYa3PHemm+16uiAWELoEv9X0ablMc3OcKkwIi0BklKmbSpjPsd+y1X2Kv5bRZZO6sm78glHz+jFFnXuux/cR+qSy4mzXAWP6xTse4WZpXtGiuiMRPG88lIJW0FlywKBgEV7eMFUFfcp2cG29Qos8ZkHyhB5z8uYeMfEuABPAIx5DEhWqy1mZuhfoYue3iswAouNYggSdAwVpDusMnwCqRqyRPkXkOxI1fARFXBH4x05hBhQunIOt2zUHQrz2aZ1fgW8KcRinrKu1NPxhQeiL9o8heX5yFFKyRcvXirpAkoJAoGAet9yGjPpz4I60AvIP6S6c1SIIodBlHlU/zWcibhXJQuLeJi/v8OD74Vyt4y3da38TJyAwZxI4jmy/EBGrVIC72tn2t+nSqtxHlU5Npbpx7SBist6whxZhtNoDToA+nkTjp8xTmd8LbRovcFF5und5aIHxfQztVjyLeaqYWR1YWsCgYEAjCxCtosL7y7RvQ9KP3BBEogBAD7HhBTW+ldlE3f9UgqOOlP0/U43UPKYMtHzNTcaUDX6Z1uTLtPGmwrETFpH/qbgtesMmShPM4ulZI+92mDxfMQ22vkdLqOwWh4MYkUVE4za9qZVlLjPPVbq/CGlFRFSXCpAC8Uypd3nWFkm89U="
                , "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"
        };

        for (String private_key : arr_private_key) {

            boolean checkKey = isValidPrivateKey(algorithm, private_key);
            System.out.println(checkKey + ": " + private_key + "_" + algorithm);
            System.out.println("--------------------------------");
        }
    }
}
