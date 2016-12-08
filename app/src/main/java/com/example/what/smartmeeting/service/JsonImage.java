package com.example.what.smartmeeting.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by what on 05/04/2016.
 */
public class JsonImage {
//    private static InputStream inputStream = null;

    public JsonImage() {

    }

    public InputStream makeHttpRequest(String url, String imgFile, String name, String id, String email) {
        String json = "";
        InputStream inputStream = null;

        try {

            DefaultHttpClient client = new DefaultHttpClient();
            final HttpPost post = new HttpPost(url);


            MultipartEntityBuilder multipartEntity = MultipartEntityBuilder
                    .create();
//                multipartEntity.setCharset(Charset.forName(HTTP.UTF_8));
            multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//                Charset chars = Charset.forName(HTTP.UTF_8); // Setting up the encoding
            if (imgFile!=null&&imgFile.trim()!=""){
                multipartEntity.addPart("image", new FileBody(new File(imgFile)));
            }
            multipartEntity.addPart("id", new StringBody(id));
            multipartEntity.addPart("name", new StringBody(name, ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8)));
            multipartEntity.addPart("email", new StringBody(email));
            post.setEntity(multipartEntity.build());
            HttpResponse response = null;

            response = client.execute(post);

            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
            entity.consumeContent();
            client.getConnectionManager().shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

}
