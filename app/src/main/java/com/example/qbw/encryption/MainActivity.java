package com.example.qbw.encryption;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qbw.encryption.AESUtil1;
import com.qbw.encryption.Base64Util;
import com.qbw.encryption.Constant;
import com.qbw.encryption.MD5Util;
import com.qbw.encryption.RSAUtil;
import com.qbw.encryption.StringUtil;
import com.qbw.log.XLog;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XLog.setDebug(true);
        aes128Test();
        testRsaTest();
        md5Test();
        stringTest();
    }

    private void aes128Test() {
        try {
            String[] seeds = new String[]{
                    "0123456789abcdef", "0123456789abcdef0", "0123456789abcd哦", "aa我"
            };

            String content = "你好AES!";

            XLog.line(true);
            String s = AESUtil1.encryptBase64(seeds[0], content);
            XLog.d(s);
            s = AESUtil1.decryptBase64(seeds[0], s);
            XLog.d(s);
            XLog.line(false);

            for (String seed : seeds) {

                XLog.d(String.format("content=%s", content));
                byte[] be = AESUtil1.encrypt(seed.getBytes("utf-8"), content.getBytes("utf-8"));
                String encryContent = StringUtil.byteToHexString(be);
                XLog.d(String.format("encryContent=%s", encryContent));
                byte[] bd = AESUtil1.decrypt(seed.getBytes("utf-8"), be);
                String decryContent = new String(bd, "utf-8");
                XLog.d(String.format("decryContent=%s", decryContent));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void stringTest() {
        String content = "你好 AES!";
        //String content = "Hello AES!";
        try {
            XLog.d(String.format("%s", content));
            byte[] contentBytes = content.getBytes("utf-8");
            String contentHex = StringUtil.byteToHexString(contentBytes, true);
            XLog.d(String.format("%s", contentHex));
            byte[] contentBytesN = StringUtil.hexStringToByte(contentHex);
            String _content = new String(contentBytesN, "utf-8");
            XLog.d(String.format("%s", _content));


            String test = new String(AESUtil1.decrypt("6sa175asc@qq.com".getBytes(),
                                                      "2kxqG45Lj2BOq4chQV2I2qoicv4UxIK7orAzcTBHMOI5iybh5VGWeaH61qm4kWAK],license[qP/gy1MHhyiYyhUoSfEqex7zHBtZONMySsCkllZYh/J3JbslVemYlWo6UZgtR8F5UQTM/CjZO6cp57i8T3/b85tb96BoIrg3SOQxecDuN7T6Bg+9nWVpuNO8JhKf7UQu"
                                                  .getBytes(),
                                                      AESUtil1.SecretLen.LEN_NONE));
            XLog.w("test[%s]", test);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void testRsaTest() {
        byte[][] bkeys = RSAUtil.randomGetKyes();
        String[] keys = new String[]{
                new String(Base64Util.encode(bkeys[0])), new String(Base64Util.encode(bkeys[1]))
        };
        XLog.d("keys[%s] [%s]", keys[0], keys[1]);
        String content = "{\"password\":\"123456\",\"phone\":\"13048857563\"}";
        String s = RSAUtil.encrypt(RSAUtil.getPublicKey(bkeys[0]), content);
        XLog.d(s);
        s = RSAUtil.decrypt(RSAUtil.getPrivateKey(bkeys[1]), s);
        XLog.d(s);

        XLog.line(true);
        String[] skeys = RSAUtil.randomGetKyesBase64();
        XLog.d("keys[%s] [%s]", skeys[0], skeys[1]);
        s = RSAUtil.encryptBase64(skeys[0], content);
        XLog.d(s);
        s = RSAUtil.decryptBase64(skeys[1], s);
        XLog.d(s);
        XLog.line(false);
    }

    private void md5Test() {
        String s = "哎呦，呵呵，hao";
        try {
            String md5s = StringUtil.byteToHexString(MD5Util.encrypt(s.getBytes("utf-8")));
            XLog.d(md5s);
            md5s = MD5Util.encryptHex(s);
            XLog.d(md5s);
            md5s = new String(Base64Util.encode(MD5Util.encrypt(s.getBytes(Constant.Charset.UTF_8))),
                              Constant.Charset.UTF_8);
            XLog.d(md5s);
            md5s = MD5Util.encryptBase64(s);
            XLog.d(md5s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
