package com.klein.urwidget.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author With You
 * @version 5.0.0
 * @date 2020-03-23 15:59
 * @email 1713397546@qq.com
 * @description
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initData();
    }

    protected abstract int getLayoutId();

    protected void initData() {
    }

    protected void toJump(Class<?> aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }

}
