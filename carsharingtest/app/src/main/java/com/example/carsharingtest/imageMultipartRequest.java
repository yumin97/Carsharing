package com.example.carsharingtest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("unchecked")
public class imageMultipartRequest extends Request<String> {

    public static final String KEY_PICTURE = "img";
    public static final String KEY_PICTURE_NAME = "filename";
    public static final String userID = "userID";

    private HttpEntity mHttpEntity;

    private String muserID;
    private Listener mListener;

    public imageMultipartRequest(String url, String filePath, String userID,
                                 Listener<String> listener) {
        super(Method.POST, url, null);

        muserID = userID;
        mListener = listener;
        mHttpEntity = buildMultipartEntity(filePath);
    }

    public imageMultipartRequest(String url, File file, String userID,
                                 Listener<String> listener) {
        super(Method.POST, url, null);

        muserID = userID;
        mListener = listener;
        mHttpEntity = buildMultipartEntity(file);
    }

    private HttpEntity buildMultipartEntity(String filePath) {
        File file = new File(filePath);
        return buildMultipartEntity(file);
    }

    private HttpEntity buildMultipartEntity(File file) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        String fileName = file.getName();
        FileBody fileBody = new FileBody(file);
        builder.addPart(KEY_PICTURE, fileBody);
        //builder.addTextBody(KEY_PICTURE_NAME, fileName);
        builder.addTextBody(userID, muserID);
        return builder.build();
    }

    @Override
    public String getBodyContentType() {
        return mHttpEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mHttpEntity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return Response.success("Uploaded", getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}
