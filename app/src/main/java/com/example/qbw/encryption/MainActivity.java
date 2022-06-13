package com.example.qbw.encryption;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qbw.encryption.AESUtil;
import com.qbw.encryption.Base64Util;
import com.qbw.encryption.Constant;
import com.qbw.encryption.MD5Util;
import com.qbw.encryption.RSAUtil;
import com.qbw.encryption.StringUtil;
import com.qbw.l.L;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.GL.setEnabled(true);
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

            L.GL.line(true);
            String s = AESUtil.encryptBase64(seeds[0], content);
            L.GL.d(s);
            s = AESUtil.decryptBase64(seeds[0], s);
            L.GL.d(s);
            L.GL.line(false);

            for (String seed : seeds) {

                L.GL.d(String.format("content=%s", content));
                byte[] be = AESUtil.encrypt(seed.getBytes("utf-8"), content.getBytes("utf-8"));
                String encryContent = StringUtil.byteToHexString(be);
                L.GL.d(String.format("encryContent=%s", encryContent));
                byte[] bd = AESUtil.decrypt(seed.getBytes("utf-8"), be);
                String decryContent = new String(bd, "utf-8");
                L.GL.d(String.format("decryContent=%s", decryContent));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void stringTest() {
        String content = "你好 AES!";
        //String content = "Hello AES!";
        try {
            L.GL.d(String.format("%s", content));
            byte[] contentBytes = content.getBytes("utf-8");
            String contentHex = StringUtil.byteToHexString(contentBytes, true);
            L.GL.d(String.format("%s", contentHex));
            byte[] contentBytesN = StringUtil.hexStringToByte(contentHex);
            String _content = new String(contentBytesN, "utf-8");
            L.GL.d(String.format("%s", _content));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void testRsaTest() {
        byte[][] bkeys = RSAUtil.randomGetKyes();
        String[] keys = new String[]{
                new String(Base64Util.encode(bkeys[0])), new String(Base64Util.encode(bkeys[1]))
        };
        L.GL.d("keys[%s] [%s]", keys[0], keys[1]);
        String content = "{\"password\":\"123456\",\"phone\":\"13048857563\"}";
        String s = RSAUtil.encrypt(RSAUtil.getPublicKey(bkeys[0]), content);
        L.GL.d(s);
        s = RSAUtil.decrypt(RSAUtil.getPrivateKey(bkeys[1]), s);
        L.GL.d(s);

        L.GL.line(true);
        String[] skeys = RSAUtil.randomGetKyesBase64();
        L.GL.d("keys[%s] [%s]", skeys[0], skeys[1]);
        s = RSAUtil.encryptBase64(skeys[0], content);
        L.GL.d(s);
        s = RSAUtil.decryptBase64(skeys[1], s);
        L.GL.d(s);
        L.GL.line(false);
    }

    private void md5Test() {
        String s = "哎呦，呵呵，hao";
        try {
            String md5s = StringUtil.byteToHexString(MD5Util.encrypt(s.getBytes("utf-8")));
            L.GL.d(md5s);
            md5s = MD5Util.encryptHex(s);
            L.GL.d(md5s);
            md5s = new String(Base64Util.encode(MD5Util.encrypt(s.getBytes(Constant.Charset.UTF_8))),
                              Constant.Charset.UTF_8);
            L.GL.d(md5s);
            md5s = MD5Util.encryptBase64(s);
            L.GL.d(md5s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
