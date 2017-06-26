package com.example.xiaopengwang.iocaa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ioclibrary.CheckNet;
import com.example.ioclibrary.OnClick;
import com.example.ioclibrary.ViewById;
import com.example.ioclibrary.ViewUtils;

public class MainActivity extends AppCompatActivity {
    @ViewById(R.id.mtv)
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        mTextView.setText("IOC注解");
    }
    @OnClick({R.id.mtv})
    @CheckNet()     // 没网不执行
    public void onClick(View view){
        Toast.makeText(this,"IOC注解",Toast.LENGTH_LONG).show();
    }
}
