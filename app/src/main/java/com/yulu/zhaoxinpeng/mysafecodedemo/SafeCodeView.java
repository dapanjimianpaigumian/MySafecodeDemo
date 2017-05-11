package com.yulu.zhaoxinpeng.mysafecodedemo;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/11.
 * 自定义五位验证码 V
 */

public class SafeCodeView extends RelativeLayout {
    EditText mEdittext;
    private TextView[] mCodes; //验证码显示框
    private StringBuffer mStringBuffer = new StringBuffer();//拼接String
    private int count = 5;//验证码长度
    private String inputContent;//最终的输入结果

    public SafeCodeView(Context context) {
        this(context, null);
    }

    public SafeCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SafeCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //填充布局
        View.inflate(getContext(), R.layout.safe_code_relativelayout, this);
        mEdittext = (EditText) findViewById(R.id.item_edittext);
        mCodes = new TextView[5];
        mCodes[0] = (TextView) findViewById(R.id.item_code_tv1);
        mCodes[1] = (TextView) findViewById(R.id.item_code_tv2);
        mCodes[2] = (TextView) findViewById(R.id.item_code_tv3);
        mCodes[3] = (TextView) findViewById(R.id.item_code_tv4);
        mCodes[4] = (TextView) findViewById(R.id.item_code_tv5);

        //输入光标隐藏
        mEdittext.setCursorVisible(false);
        //监听输入框
        mEdittext.addTextChangedListener(mTextWatcher);
        mEdittext.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //判断是否点击了删除按键
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 删除操作
                    if (onKeyDelete()) return true;
                    return true;
                }
                return false;
            }
        });
    }

    //当点击删除时触发
    public boolean onKeyDelete() {
        if (count == 0) {
            count = 5;
            return true;
        }

        if (mStringBuffer.length() > 0) {
            //删除相应位置的字符
            mStringBuffer.delete((count - 1), count);
            //string长度减一
            count--;
            //重新赋值
            inputContent = mStringBuffer.toString();
            //删除后的TextView置空
            mCodes[mStringBuffer.length()].setText("");
            //删除后的TextView背景改变
            mCodes[mStringBuffer.length()].setBackgroundResource(R.drawable.bg_verify);
            //删除的回调
            if (inputCompleteListener != null) {
                inputCompleteListener.deleteContent(true);
            }
        }
        return false;
    }

    //监听输入框
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //如果当前输入框内有内容时
            if (!s.toString().equals("")) {
                //如果当前输入框内的字符串长度 等于5时
                if (mStringBuffer.length() > 4) {
                    mEdittext.setText("");
                    return;
                } else {
                    //如果当前输入框内小于等于4
                    mStringBuffer.append(s);
                    mEdittext.setText("");
                    count = mStringBuffer.length();
                    inputContent = mStringBuffer.toString();
                    //当文字长度为5，则调用完成输入监听
                    if (mStringBuffer.length() == 5) {
                        if (inputCompleteListener != null) {
                            inputCompleteListener.inputComplete();
                        }
                    }
                }
                for (int i = 0; i < mStringBuffer.length(); i++) {
                    mCodes[i].setText(String.valueOf(inputContent.charAt(i)));
                    mCodes[i].setBackgroundResource(R.drawable.bg_verify_press);
                }
            }
        }
    };

    //对外提供两个方法
    //1.获取输入文本
    public String getEditContent() {
        return inputContent;
    }

    //2.清空输入内容
    public void clearEditText() {
        mStringBuffer.delete(0, mStringBuffer.length());
        inputContent = mStringBuffer.toString();
        for (int i = 0; i < mCodes.length; i++) {
            mCodes[i].setText("");
            mCodes[i].setBackgroundResource(R.drawable.bg_verify);
        }
    }

    //输入完成监听------接口回调
    public interface InputCompleteListener {

        //输入完成
        void inputComplete();

        //删除回调
        void deleteContent(boolean isDelete);
    }

    private InputCompleteListener inputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

}
