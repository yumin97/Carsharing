package com.example.carsharingtest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class imageActivity extends AppCompatActivity {
    TextView tv_userID;
    ImageView iv;
    Bitmap bitmap;
    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        tv_userID=findViewById(R.id.tv_id);
        iv=findViewById(R.id.iv);

        tv_userID.setText(MainActivity.userID);

        //동적퍼미션 작업
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult== PackageManager.PERMISSION_DENIED){
                String[] permissions= new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,10);
            }
        }else{
            //cv.setVisibility(View.VISIBLE);
        }
    }

    public void clickBtn(View view) {

        //갤러리 or 사진 앱 실행하여 사진을 선택하도록..
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    //선택한 사진의 경로(Uri)객체 얻어오기
                    Uri uri= data.getData();
                    if(uri!=null){
                        iv.setImageURI(uri);

                        //갤러리앱에서 관리하는 DB정보가 있는데, 그것이 나온다 [실제 파일 경로가 아님!!]
                        //얻어온 Uri는 Gallery앱의 DB번호임. (content://-----/2854)
                        //업로드를 하려면 이미지의 절대경로(실제 경로: file:// -------/aaa.png 이런식)가 필요함
                        //Uri -->절대경로(String)로 변환
                        imgPath= getRealPathFromUri(uri);   //임의로 만든 메소드 (절대경로를 가져오는 메소드)
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //이미지 경로 uri 확인해보기
                        new AlertDialog.Builder(this).setMessage(uri.toString()+"\n"+imgPath).create().show();
                    }

                }else
                {
                    Toast.makeText(this, "이미지 선택을 하지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }//onActivityResult() ..

    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
    }

    public void clickUpload(View view) {

        //서버로 보낼 데이터
        String userID= tv_userID.getText().toString();

        //안드로이드에서 보낼 데이터를 받을 php 서버 주소
        String serverUrl="http://3.34.192.251/image/fileupload.php";

        //Volley plus Library를 이용해서
        //파일 전송하도록..
        //Volley+는 AndroidStudio에서 검색이 안됨 [google 검색 이용]

        //파일 전송 요청 객체 생성[결과를 String으로 받음]
        /*Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new AlertDialog.Builder(imageActivity.this).setMessage("응답:"+response).create().show();
            }
        };
        *//*Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(imageActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        };*//*
        // 서버로 Volley를 이용해서 요청을 함.
        imageMultipartRequest imageMultipartRequest = new imageMultipartRequest(serverUrl,imgPath,userID,responseListener);
        RequestQueue queue = Volley.newRequestQueue(imageActivity.this);
        queue.add(imageMultipartRequest);*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new AlertDialog.Builder(imageActivity.this).setMessage("응답:"+response).create().show();
                iv.setImageResource(0);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(imageActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userID", userID);
                params.put("img", imageToString(bitmap));

                return params;
            }
        };
        imagebitmap.getInstance(imageActivity.this).addToRequestQue(stringRequest);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
}