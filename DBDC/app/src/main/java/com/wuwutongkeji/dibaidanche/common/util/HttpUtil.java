package com.wuwutongkeji.dibaidanche.common.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


/**
 * Created by Mr.Bai on 2017/9/12.
 */
public class HttpUtil {

    public static String getBody(Response response) throws IOException {

        final ResponseBody responseBody = response.body();
        final long contentLength = responseBody.contentLength();

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(charset);
            } catch (UnsupportedCharsetException e) {
            }
        }
        String body = buffer.clone().readString(charset);

        return body;
    }
    public static String getUrl(Request request){
        return request.url().toString();
    }


    public static MultipartBody getFormRequestBody(File file){

        String fileName = System.currentTimeMillis() + "_" + file.getName();
        MultipartBody.Builder mFileBuilder = new MultipartBody.Builder();
//        mFileBuilder.setType(MediaType.parse("multipart/form-data"));
        mFileBuilder.addFormDataPart("name",fileName);
        mFileBuilder.addFormDataPart("file", fileName, getFormRequest(file));

        return mFileBuilder.build();
    }

    public static RequestBody getFormRequest(File file){
        return RequestBody.create(MediaType.parse("multipart/form-data"),file);
    }

}
