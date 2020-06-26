package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.customview.View.TitleBar;

public class MainActivity extends AppCompatActivity {
private TitleBar mTitleBar;
private Button btn_ViewGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mTitleBar=findViewById(R.id.title);
        mTitleBar.setTitle("自定义组合控件");
        mTitleBar.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"点击左键",Toast.LENGTH_SHORT).show();
            }
        });
        mTitleBar.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"点击右键",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void init(){
        btn_ViewGroup=findViewById(R.id.btn_ViewGroup);
        btn_ViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MoonActivity.class));
            }
        });
    }
}
