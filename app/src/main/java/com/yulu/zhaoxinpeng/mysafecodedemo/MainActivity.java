package com.yulu.zhaoxinpeng.mysafecodedemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义验证码
 */
public class MainActivity extends AppCompatActivity {

    TextView mTvText;
    Button mDelBtn;
    SafeCodeView mEdittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEdittext = (SafeCodeView) findViewById(R.id.scv_edittext);
        mTvText = (TextView) findViewById(R.id.tv_text);
        mDelBtn= (Button) findViewById(R.id.main_btn);
        mEdittext.setInputCompleteListener(mListener);
        mDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdittext.clearEditText();
                mTvText.setText("输入验证码表示同意《用户协议》");
                mTvText.setTextColor(Color.BLACK);
            }
        });
    }

    private SafeCodeView.InputCompleteListener mListener=new SafeCodeView.InputCompleteListener() {
        @Override
        public void inputComplete() {
            Toast.makeText(MainActivity.this, "验证码："+mEdittext.getEditContent(), Toast.LENGTH_SHORT).show();
            if (!mEdittext.getEditContent().equals("12345")) {
                mTvText.setText("验证码错误");
            }
        }

        @Override
        public void deleteContent(boolean isDelete) {
            if (isDelete) {
                mTvText.setText("输入验证码表示同意《用户协议》");
            }
        }
    };

}
