package com.example.customerdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.yuyh.jsonviewer.library.JsonRecyclerView;

public class JsonViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_viewer);
        JsonRecyclerView mRecyclewView = findViewById(R.id.rv_json);

        Intent intent= getIntent();
        String value=intent.getStringExtra("result");
        mRecyclewView.bindJson(value);
    }


}
