package com.klein.urwidget;

import android.view.View;

import com.fubufan.widget.button.UrFloatActionButton;
import com.fubufan.widget.textview.UrClearEditText;
import com.klein.urwidget.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear_view:
                toJump(UrClearEditTextActivity.class);
                break;
            case R.id.btn_float_button:
                toJump(UrFloatActionButtonActivity.class);
                break;
        }
    }

}
