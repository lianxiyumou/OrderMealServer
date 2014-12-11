package com.luopeng.comm;

import java.io.UnsupportedEncodingException;

public class RC4 {
	 
    public static final String KEY = "5oiR5piv5LiA5LiqQkFTRTY05Zyo57q/57yW56CBL+ino+eggeeahOWwj+W3peWFt++8jOivt+i/memHjOi+k+WFpeimgeWkhOeQhueahOWtl+espuS4sg==";
    
    public static String decryptionRC4(byte[] data, String key, int from, int size) {
        if (data == null || key == null || data.length < size + from) {
            return null;
        }
        byte[] d = new byte[size]; /* do copy */
        int i = 0;
        while(size-- > 0) {
        	d[i++] = data[from++];
        }
        return new String(RC4Base(d, key));
    }
 
    public static String decryptionRC4(byte[] data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return new String(RC4Base(data, key));
    }
    
    /**
     * 解密十六进制字符串的密文
     * @param data: 十六进制字符串
     * @param key
     * @return
     */
    public static String decryptionRC4(String data, String key) {  
        if (data == null || key == null) {  
            return null;  
        }  
        return new String(RC4Base(HexString2Bytes(data), key));  
    }  
    
    /**
     * 把十六进制字符串转化成byte[]
     * @param src
     * @return
     */
    private static byte[] HexString2Bytes(String src) {  
        int size = src.length();  
        byte[] ret = new byte[size / 2];  
        byte[] tmp = src.getBytes();  
        for (int i = 0; i < size / 2; i++) {  
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);  
        }  
        return ret;  
    } 
    
    private static byte uniteBytes(byte src0, byte src1) {  
        char _b0 = (char) Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();  
        _b0 = (char) (_b0 << 4);  
        char _b1 = (char) Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();  
        byte ret = (byte) (_b0 ^ _b1);  
        return ret;  
    }     
 
    public static byte[] encryptionRC4Byte(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        byte b_data[] = null;
        try {
            b_data = data.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return RC4Base(b_data, key);
    }
    
    /**
     * 获得十六进制字符串的密文（用于网络的传输）
     * @param data
     * @param key
     * @return 十六进制字符串的密文
     */
    public static String encryptionRC4String(String data, String key) {  
        if (data == null || key == null) {  
            return null;  
        }  
        return toHexString(asString(encryptionRC4Byte(data, key)));  
    }     
    
    private static String asString(byte[] buf) {  
        StringBuffer strbuf = new StringBuffer(buf.length);  
        for (int i = 0; i < buf.length; i++) {  
            strbuf.append((char) buf[i]);  
        }  
        return strbuf.toString();  
    }  

    /**
     * 转化成十六进制的字符串
     * @param s
     * @return
     */
    private static String toHexString(String s) {  
        String str = "";  
        for (int i = 0; i < s.length(); i++) {  
            int ch = (int) s.charAt(i);  
            String s4 = Integer.toHexString(ch & 0xFF);  
            if (s4.length() == 1) {  
                s4 = '0' + s4;  
            }  
            str = str + s4;  
        }  
        return str;// 0x表示十六进制  
    }      
 
    private static byte[] initKey(String aKey) {
        byte[] b_key = null;
        try {
            b_key = aKey.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte state[] = new byte[256];
        for (int i = 0; i < 256; i++) {
            state[i] = (byte) i;
        }
        int index1 = 0;
        int index2 = 0;
        if (b_key == null || b_key.length == 0) {
            return null;
        }
        for (int i = 0; i < 256; i++) {
            index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
            byte tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;
            index1 = (index1 + 1) % b_key.length;
        }
        return state;
    }
 
    private static byte[] RC4Base(byte[] input, String mKkey) {
        int x = 0;
        int y = 0;
        byte key[] = initKey(mKkey);
        int xorIndex;
        byte[] result = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            x = (x + 1) & 0xff;
            y = ((key[x] & 0xff) + y) & 0xff;
            byte tmp = key[x];
            key[x] = key[y];
            key[y] = tmp;
            xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
            result[i] = (byte) (input[i] ^ key[xorIndex]);
        }
        return result;
    }
 
}
