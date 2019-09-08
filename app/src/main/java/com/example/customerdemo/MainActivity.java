package com.example.customerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.customerdemo.util.HttpURLConnectionUtil;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //改变文字内容，标志这是从activity跳转过来的
        final TextView texView= (TextView) findViewById(R.id.text1);
        texView.setText("hello world");
        Button button1 = findViewById(R.id.button);

        Intent intent= getIntent();
        String value=intent.getStringExtra("code");
        if(value!=null&&!value.equals("")){
            texView.setText(value);//这里将显示“这是跳转过来的！来自apk1”
        }else{
            System.out.println("空的参数");
        }

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 获取授权码
                String oauthCode = texView.getText().toString();
                // 通过授权码获取accessToken
                String accessToken = getTokenByCode(oauthCode);

                String apiURL = "http://gateway.cn1-dev.mindsphere-in.cn/api/gatewayregistry/v3/myInstallations";
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                String resultStr = HttpURLConnectionUtil.sendGet(apiURL,"GET",new HashMap<String,String>(), headers);

                Intent intent1 = new Intent(MainActivity.this,JsonViewerActivity.class);
                intent1.putExtra("result",resultStr);
//                Bundle bundle = new Bundle();
//                bundle.putString("result",resultStr);
                startActivity(intent1);
            }
        });

    }

    /**
     *  通过code获取token
     *
     * */
    public String getTokenByCode(String code){

        String access_token = "";
        String url = "http://core.piam.cn1-dev.mindsphere-in.cn/uaa/oauth/token";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type","application/x-www-form-urlencoded");
        headers.put("Authorization","Basic YWRtaW5fY2xpX2NsaWVudDpwbGFjZWhvbGRlcg==");
        headers.put("Accept","application/json");

        Map<String, String> paramters = new HashMap<>();
        paramters.put("grant_type","password");
        paramters.put("passcode", code);

        String tokenJsonStr = HttpURLConnectionUtil.sendGet(url,"POST", paramters, headers);
        boolean isJson = JSONObject.isValid(tokenJsonStr);
        JSONObject tokenJsonObj = new JSONObject();
        if(isJson){
            tokenJsonObj = JSONObject.parseObject(tokenJsonStr);
        }

        boolean containToken = tokenJsonObj.containsKey("access_token");

        if(containToken){
            access_token = tokenJsonObj.getString("access_token");
        }
        return access_token;
    }
}
