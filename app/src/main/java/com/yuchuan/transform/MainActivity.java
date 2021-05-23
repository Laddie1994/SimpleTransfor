package com.yuchuan.transform;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yuchuan.library.PrintTime;
import com.yuchuan.plugin.R;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private String className;
    private String methodName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
        testMethoid();
    }

    @PrintTime
    public void testMethoid(){
        Log.i("MainActivity", "test123");
    }
}
