
package com.yc.wsjt.util;

import android.util.Log;

import org.json.JSONObject;

import java.text.DecimalFormat;


public final class DataUtils {

    public static final String TAG = "DataUtils";
    //封装的公钥和私钥   可以采用2对不配套的来处理(更安全 当然也可以使用一套rsa)
    //第一对公钥
    private static final String DEFAULT_PUBLIC_KEY = "your  first pair public key";

    //	private static final String DEFAULT_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOiy8fWAKlBNNktcYxxJ56oONa4NVjT4BpvVVu0YmpZ2Q93fYPiERTAWNUn4UlurtSm8UUNDeTbLaf/WvPFZqkWT974E00JIo/17r45/VIuzzdKjbt2CIB4AqZ1CjHB9euC8/X9BEwknSLqbcJFHJOVc75wwZtipG/WJNP25pGiVAgMBAAECgYEAv4PXY8hyCtkhYHDPGU8yHWHIiFFtq/ad6c9x1X00bbU0Mf1Q3/hswSDmBtUbY1s0pP7amtODhbdwrCFeK/0yBrOegb2fQeJs/QL6/y4/DPzRB21k9N8cQjgmv5tQb72fwdY8nDROXnzKQceMo6b/xkWaIhvhdUq6nCqPvoIGRIECQQD+lOKTQk769G9BQd7HW+2H2NioPbxri+V27daC1M5uBfBj8Wt3NDJ5IyMvOHz5yTlm8FsE2Zz1/aFdLJ/Rv4IRAkEA6f7ZOMcuxlRsAiN708+r3q3sxAyBood+qAJ1MKhOrdR94RcAPUkcjFTZ8j1v0eclj6+w2RChcpb5Ath93ia6RQJBAP3b6x+axHUcn4A8NfEn6vFGu6zwet3nT3bLbddia0JtK6wNhfMFGruO3TvuITlXfaT3UlvAv/LP6kOmBuw6AnECQQDR3r29awjM4ZMuJ908EJs6Ugx1mjH7MEOtNOcfCRXoWxm79QFF9nkgdEo2NlxAi2zo/s9DIONs/3O/1aSux1VxAkBkkOdc0f2ogWZHqtCYfVfYjwbMvlW/6lnbq0B76V1SVqogoSubwnF7EUBdmqpzWmzqM4xURBh9QqDnUUfBzPMW";
    //	private static final String DEFAULT_PRIVATE_KEY="";

    //第二队私钥
    private static final String DEFAULT_PRIVATE_KEY = "your second pair private key";

    public static String encodeRequest(JSONObject json) throws Exception {
        byte[] data = json.toString().getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, DEFAULT_PUBLIC_KEY);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        Log.i(TAG, "网络传输的字符串:" + Base64Utils.encode(encodedData));
        return Base64Utils.encode(encodedData);
    }

    public static String decodeAppResponse(String response) throws Exception {
        Log.i(TAG, "需要解密的字符串" + response);
        byte[] responseData = Base64Utils.decode(response);
        byte[] decodedData = RSAUtils.decryptByPrivateKey(responseData, DEFAULT_PRIVATE_KEY);
        String responseText = new String(decodedData);
        System.out.println("解密后文字: \r\n" + responseText);
        Log.i(TAG, "解密后的字符串" + response);
        return responseText;
    }

    /**
     * 获取金额，保留小数后2位
     *
     * @param inputMoney
     * @return
     */
    public static String getMoney(String inputMoney) {
        DecimalFormat df2 = new DecimalFormat(".00");
        String temp = df2.format(Double.parseDouble(inputMoney));
        return temp;
    }

}
