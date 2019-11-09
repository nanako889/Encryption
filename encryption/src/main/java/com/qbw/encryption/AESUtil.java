package com.qbw.encryption;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author QBW
 * @date 2019/11/9
 */
public class AESUtil {

    private static byte[] doFinal(boolean isEncrypt,
                                  byte[] keySecret,
                                  byte[] keyIv,
                                  byte[] contentNeedEncrypt) {
        byte[] result = null;
        try {
            //IvParameterSpec增加加密算法的强度
            SecretKeySpec key = new SecretKeySpec(keySecret, Constant.Algorithm.AES);
            Cipher cipher = Cipher.getInstance(Constant.CipherMode.AES_CBC_PKCS5Padding);
            if (keyIv != null) {
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
                            key,
                            new IvParameterSpec(keyIv));
            } else {
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, key);
            }
            if (isEncrypt) {
                result = cipher.doFinal(contentNeedEncrypt);
            } else {
                result = cipher.doFinal(contentNeedEncrypt);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
