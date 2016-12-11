package com.example.wlw.today1_2;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Login extends AppCompatActivity {

    private Button mbtn_login;
    private Button mbtn_back;
    private String username;
    private String pwd;
    private Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String re= (String) msg.obj;

                    if ("1".equals(re)){
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);

                    }else if("-1".equals(re)){
                        Toast.makeText(Login.this, "密码错误,登录失败！", Toast.LENGTH_SHORT).show();

                    }else if("0".equals(re)){
                        Toast.makeText(Login.this, "用户名不存在！", Toast.LENGTH_SHORT).show();

                    }
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mbtn_back= (Button) findViewById(R.id.btn_back);
        mbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        mbtn_login= (Button) findViewById(R.id.btn_login);

        mbtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = ((EditText) findViewById(R.id.Login_user)).getText().toString();
                pwd = ((EditText) findViewById(R.id.Login_pwd)).getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("sadasd","asd");
                        HttpURLConnection connection=null;
                        try {
                            URL url = new URL("http://172.16.33.154/do_login.php");
                            connection= (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setConnectTimeout(8000);
                            connection.setReadTimeout(8000);
                            String postData="username="+username+"&"+"password="+pwd;
                            byte[]myData=postData.getBytes();
                            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                            outputStream.write(myData,0,myData.length);
                            outputStream.close();
                            InputStream inputStream = connection.getInputStream();
                            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                            StringBuilder response = new StringBuilder();
                            String line= reader.readLine();
                            while (line!=null){
                                response.append(line);
                                line=reader.readLine();
                            }
                            Message message=new Message();
                            message.what=1;
                            message.obj=response.toString();
                            handler.sendMessage(message);
                            inputStream.close();
                            reader.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(connection!=null){
                            connection.disconnect();
                        }

                    }
                }).start();
            }
        });
    }
}
