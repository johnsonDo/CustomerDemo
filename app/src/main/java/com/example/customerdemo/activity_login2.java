package com.example.customerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_login2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        Button button = findViewById(R.id.button2);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ComponentName componetName = new ComponentName(
                        //这个是另外一个应用程序的包名
                        "com.example.myapplication",
                        //这个参数是要启动的Activity
                        "com.example.myapplication.MainActivity");
                Intent intent= new Intent();
                intent.setComponent(componetName);
                startActivity(intent);
                overridePendingTransition(R.animator.zoomout, R.animator.zoomin);

                    }
                }
        );
    }
}
