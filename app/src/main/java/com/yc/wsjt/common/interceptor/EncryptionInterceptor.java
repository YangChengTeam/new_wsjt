package com.yc.wsjt.common.interceptor;


import com.blankj.utilcode.util.EncryptUtils;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.util.Base64Utils;
import com.yc.wsjt.util.RSAUtils;


import java.io.IOException;


import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by admin on 2017/3/13.
 */

public class EncryptionInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            Request request = chain.request();
            RequestBody oldBody = request.body();

            Buffer buffer = new Buffer();
            oldBody.writeTo(buffer);
            byte[] strOldBody = buffer.readByteArray();
            Logger.e("加密之前的参数--->" + new String(strOldBody, "UTF-8"));

            MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
            byte[] strNewBody = RSAUtils.encryptByPublicKey(strOldBody, Constants.DEFAULT_PUBLIC_KEY);
            //byte[] strNewBody = EncryptUtils.encryptRSA(strOldBody,Base64Utils.decode(Constants.DEFAULT_PUBLIC_KEY),true,"RSA/ECB/PKCS1Padding");
            //Logger.e("加密之后的参数--->" + new String(strNewBody, "UTF-8"));

            byte[] res = RSAUtils.decryptByPrivateKey(strNewBody, Constants.DEFAULT_PRIVATE_KEY);
            Logger.e("加密之后再解密的参数--->" + new String(res, "UTF-8"));

            RequestBody body = RequestBody.create(mediaType, Base64Utils.encode(strNewBody));
            request = request.newBuilder().header("Content-Type", body.contentType().toString()).header("Content-Length", String.valueOf(body.contentLength())).method(request.method(), body).build();

            //request = request.newBuilder().post(RequestBody.create(mediaType, strNewBody)).build();

            Response response = chain.proceed(request);
//            if (response.code() == 200) {//只有约定的返回码才经过加密，才需要走解密的逻辑
//                ResponseBody oldResponseBody = response.body();
//                byte[] newResponseBodyStr = RSAUtils.decryptByPublicKey(oldResponseBody.bytes(),Constants.DEFAULT_PUBLIC_KEY);
//                //oldResponseBody.close();
//                Logger.i(" data--->" + new String(newResponseBodyStr));
//                //构造新的response
//                ResponseBody newResponseBody = ResponseBody.create(mediaType, newResponseBodyStr);
//                response = response.newBuilder().body(newResponseBody).build();
//                //newResponseBody.close();
//            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
