///*
// * Filename RSAUtil.java
// * Company 上海来伊份电子商务有限公司。
// * @author kongweixiang
// * @version 1.0.0
// */
//package com.laiyifen.openapi.gateway.utils.encrypt;
//
//import com.laiyifen.openapi.auth.util.message.Base64Util;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.crypto.Cipher;
//import java.math.BigInteger;
//import java.security.InvalidKeyException;
//import java.security.KeyFactory;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.Security;
//import java.security.Signature;
//import java.security.SignatureException;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.RSAPrivateKeySpec;
//import java.security.spec.RSAPublicKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Arrays;
//import java.util.HashMap;
//
///**
// * @author kongweixiang
// * @since 1.0.0_2018/7/27
// */
//public class RSAUtil {
//    private static Logger log = LoggerFactory.getLogger(RSAUtil.class);
//    public static int keySize = 1024;
//
//    public RSAUtil() {
//    }
//
//    public static HashMap<String, Object> getRsaKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
//        HashMap<String, Object> map = new HashMap();
//        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
//        keyPairGen.initialize(keySize);
//        KeyPair keyPair = keyPairGen.generateKeyPair();
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        map.put("public", publicKey);
//        map.put("private", privateKey);
//        return map;
//    }
//
//    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
//        try {
//            BigInteger b1 = new BigInteger(modulus);
//            BigInteger b2 = new BigInteger(exponent);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
//            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
//        } catch (Exception var6) {
//            var6.printStackTrace();
//            return null;
//        }
//    }
//
//    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
//        try {
//            BigInteger b1 = new BigInteger(modulus);
//            BigInteger b2 = new BigInteger(exponent);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
//            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
//        } catch (Exception var6) {
//            var6.printStackTrace();
//            return null;
//        }
//    }
//
//    public static String encryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(1, publicKey);
//        int key_len = publicKey.getModulus().bitLength() / 8;
//        String[] datas = splitString(data, key_len - 11);
//        String mi = "";
//        String[] arr$ = datas;
//        int len$ = datas.length;
//
//        for (int i$ = 0; i$ < len$; ++i$) {
//            String s = arr$[i$];
//            mi = mi + bcd2Str(cipher.doFinal(s.getBytes()));
//        }
//
//        return mi;
//    }
//
//    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey) throws Exception {
//        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
//        cipher.init(2, privateKey);
//        int key_len = privateKey.getModulus().bitLength() / 8;
//        byte[] bytes = data.getBytes();
//        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
//        System.err.println(bcd.length);
//        String ming = "";
//        byte[][] arrays = splitArray(bcd, key_len);
//        byte[][] arr$ = arrays;
//        int len$ = arrays.length;
//
//        for (int i$ = 0; i$ < len$; ++i$) {
//            byte[] arr = arr$[i$];
//            ming = ming + new String(cipher.doFinal(arr));
//        }
//
//        return ming;
//    }
//
//    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
//        byte[] bcd = new byte[asc_len / 2];
//        int j = 0;
//
//        for (int i = 0; i < (asc_len + 1) / 2; ++i) {
//            bcd[i] = asc_to_bcd(ascii[j++]);
//            bcd[i] = (byte) ((j >= asc_len ? 0 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
//        }
//
//        return bcd;
//    }
//
//    public static byte asc_to_bcd(byte asc) {
//        byte bcd;
//        if (asc >= 48 && asc <= 57) {
//            bcd = (byte) (asc - 48);
//        } else if (asc >= 65 && asc <= 70) {
//            bcd = (byte) (asc - 65 + 10);
//        } else if (asc >= 97 && asc <= 102) {
//            bcd = (byte) (asc - 97 + 10);
//        } else {
//            bcd = (byte) (asc - 48);
//        }
//
//        return bcd;
//    }
//
//    public static String bcd2Str(byte[] bytes) {
//        char[] temp = new char[bytes.length * 2];
//
//        for (int i = 0; i < bytes.length; ++i) {
//            char val = (char) ((bytes[i] & 240) >> 4 & 15);
//            temp[i * 2] = (char) (val > '\t' ? val + 65 - 10 : val + 48);
//            val = (char) (bytes[i] & 15);
//            temp[i * 2 + 1] = (char) (val > '\t' ? val + 65 - 10 : val + 48);
//        }
//
//        return new String(temp);
//    }
//
//    public static String[] splitString(String string, int len) {
//        int x = string.length() / len;
//        int y = string.length() % len;
//        int z = 0;
//        if (y != 0) {
//            z = 1;
//        }
//
//        String[] strings = new String[x + z];
//        String str = "";
//
//        for (int i = 0; i < x + z; ++i) {
//            if (i == x + z - 1 && y != 0) {
//                str = string.substring(i * len, i * len + y);
//            } else {
//                str = string.substring(i * len, i * len + len);
//            }
//
//            strings[i] = str;
//        }
//
//        return strings;
//    }
//
//    public static byte[][] splitArray(byte[] data, int len) {
//        int x = data.length / len;
//        int y = data.length % len;
//        int z = 0;
//        if (y != 0) {
//            z = 1;
//        }
//
//        byte[][] arrays = new byte[x + z][];
//
//        for (int i = 0; i < x + z; ++i) {
//            byte[] arr = new byte[len];
//            if (i == x + z - 1 && y != 0) {
//                System.arraycopy(data, i * len, arr, 0, y);
//            } else {
//                System.arraycopy(data, i * len, arr, 0, len);
//            }
//
//            arrays[i] = arr;
//        }
//
//        return arrays;
//    }
//
//    private static boolean validateSignBySoft(PublicKey publicKey, byte[] signData, byte[] srcData) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
//        Signature st = Signature.getInstance("SHA1withRSA");
//        st.initVerify(publicKey);
//        st.update(srcData);
//        return st.verify(signData);
//    }
//
//    public static void main(String[] args) {
//        String jdkVendor = System.getProperty("java.vm.vendor");
//        String javaVersion = System.getProperty("java.version");
//        log.debug("java.vm.vendor=[ " + jdkVendor + " ]");
//        log.debug("java.version=[ " + javaVersion + " ]");
//        if (null != jdkVendor && jdkVendor.startsWith("IBM")) {
//            log.info("证书加载模式: [ IBM JDK ]");
//            Security.insertProviderAt(new BouncyCastleProvider(), 1);
//        } else {
//            log.info("证书加载模式: [ SUM JDK ]");
//            Security.addProvider(new BouncyCastleProvider());
//        }
//
//        try {
//            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
//            generator.initialize(keySize, new SecureRandom());
//            KeyPair pair = generator.generateKeyPair();
//            PublicKey pubKey = pair.getPublic();
//            PrivateKey privKey = pair.getPrivate();
//            byte[] pk = pubKey.getEncoded();
//            byte[] privk = privKey.getEncoded();
//            String strpk = new String(Base64Util.encodeBase64(pk));
//            String strprivk = new String(Base64Util.encodeBase64(privk));
//            System.out.println("公钥:" + Arrays.toString(pk));
//            System.out.println("私钥:" + Arrays.toString(privk));
//            System.out.println("公钥Base64编码:" + strpk);
//            System.out.println("私钥Base64编码:" + strprivk);
//            X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64Util.decodebyteBase64(strpk.getBytes()));
//            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Util.decodebyteBase64(strprivk.getBytes()));
//            KeyFactory keyf = KeyFactory.getInstance("RSA", "BC");
//
//            try {
//                PublicKey pubkey2 = keyf.generatePublic(pubX509);
//                System.out.println(pubKey.equals(pubkey2));
//            } catch (InvalidKeySpecException var18) {
//                var18.printStackTrace();
//            }
//
//            try {
//                PrivateKey privkey2 = keyf.generatePrivate(priPKCS8);
//                System.out.println(privKey.equals(privkey2));
//            } catch (InvalidKeySpecException var17) {
//                var17.printStackTrace();
//            }
//        } catch (NoSuchProviderException | NoSuchAlgorithmException var19) {
//            var19.printStackTrace();
//        }
//
//    }
//}
