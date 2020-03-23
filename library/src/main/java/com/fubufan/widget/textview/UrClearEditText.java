package com.fubufan.widget.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import androidx.appcompat.widget.AppCompatEditText;

import com.fubufan.widget.R;

/**
 * 带删除小图标的 EditText  可设置带空格符的身份证、手机号、银行卡号
 *
 * @author With You
 * @version 5.0.0
 * @date 2020-03-23 11:59
 * @email 1713397546@qq.com
 * @description
 */
public class UrClearEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {

    public final static int TYPE_NORMAL = 0;//文本类型不做任何限制
    public final static int TYPE_PHONE = 1;//手机号类型
    public final static int TYPE_BANK_CARD = 2;//银行卡类型
    public final static int TYPE_ID_CARD = 3;//身份证号码类型
    private int clearInputType = TYPE_NORMAL;
    private Drawable mClearDrawable;
    private float textSizeHint = 14;
    private float textSizeDisplay = 14;
    private boolean isAutoSpace;//是否开启自动空格
    private boolean isDisplayDeleteIcon;//是否显示删除图标

    public UrClearEditText(Context context) {
        this(context, null);
    }

    public UrClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public UrClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UrClearEditText);
        clearInputType = typedArray.getInt(R.styleable.UrClearEditText_clearInputType, 0);
        isAutoSpace = typedArray.getBoolean(R.styleable.UrClearEditText_isAutoSpace, true);//默认为开启
        isDisplayDeleteIcon = typedArray.getBoolean(R.styleable.UrClearEditText_isDisplayDeleteIcon, true);//默认显示
        textSizeHint = typedArray.getDimensionPixelSize(R.styleable.UrClearEditText_textSizeHint, 14);
        textSizeDisplay = typedArray.getDimensionPixelSize(R.styleable.UrClearEditText_textSizeDisplay, 14);
        typedArray.recycle();
        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeHint);
        switch (clearInputType) {
            case 0://normal
                setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 1://phone
                setInputType(InputType.TYPE_CLASS_PHONE);
                int maxLength = 11;
                if (isAutoSpace)
                    maxLength = 13;
                String accepted = "0123456789";
                super.setInputType(InputType.TYPE_CLASS_PHONE);
                setKeyListener(DigitsKeyListener.getInstance(accepted));
                setTransformationMethod(new UrTransformationMethod());
                setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                break;
            case 2://bank_card
                maxLength = 19;
                if (isAutoSpace)
                    maxLength = 23;
                accepted = "0123456789";
                super.setInputType(InputType.TYPE_CLASS_NUMBER);
                setKeyListener(DigitsKeyListener.getInstance(accepted));
                setTransformationMethod(new UrTransformationMethod());
                setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                break;
            case 3://id_card
                maxLength = 18;
                if (isAutoSpace)
                    maxLength = 21;
                accepted = "0123456789xyzXYZ";
                super.setInputType(InputType.TYPE_CLASS_TEXT);
                setKeyListener(DigitsKeyListener.getInstance(accepted));
                setTransformationMethod(new UrTransformationMethod());
                setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                break;
        }
        intDrawable();
    }

    private void intDrawable() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.ur_clear);
        }
        int clearWidth = dip2px(getContext(), 34);
        mClearDrawable.setBounds(0, 0, clearWidth / 2, clearWidth / 2);
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                boolean touchable = event.getX() > (getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    public void setClearIconVisible(boolean visible) {
        Drawable right = (visible && isDisplayDeleteIcon) ? mClearDrawable : null;
        //是否为禁用状态
        if (isEnabled() == false)
            right = null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
        setCompoundDrawablePadding(dip2px(getContext(), 14));
    }


    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }


    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    /**
     * 获取文本内容
     *
     * @return
     */
    public String getCharSequence() {
        String text = getText().toString().trim();
        if (text.equals("") || text == null)
            return "";
        else
            return text.replace(" ", "");
    }

    /**
     * 设置按钮是否启用
     *
     * @param enabled true 表示启用
     */
    public void setEnabled(boolean enabled) {
        setClearIconVisible(false);
        super.setEnabled(enabled);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        setClearIconVisible(charSequence.length() > 0);
        if (charSequence == null || charSequence.length() == 0) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeHint);
            return;
        } else {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeDisplay);
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (clearInputType == TYPE_PHONE)//如果是手机号码格式 158 7916 7154
        {
            if (!isAutoSpace)
                stringBuilder.append(charSequence);
            else
                for (int i = 0; i < charSequence.length(); i++) {
                    if (i != 3 && i != 8 && charSequence.charAt(i) == ' ')
                        continue;
                    else {
                        stringBuilder.append(charSequence.charAt(i));
                        if ((stringBuilder.length() == 4 || stringBuilder.length() == 9) && stringBuilder.charAt(stringBuilder.length() - 1) != ' ') {
                            stringBuilder.insert(stringBuilder.length() - 1, ' ');
                        }
                    }
                }
        }
        if (clearInputType == TYPE_BANK_CARD)//如果是银行卡格式  //1234 6789 1234 6789 123
        {
            if (!isAutoSpace)
                stringBuilder.append(charSequence);
            else
                for (int i = 0; i < charSequence.length(); i++) {
                    if (i != 4 && i != 9 && i != 14 && i != 19 && i != 24 && i != 26 && charSequence.charAt(i) == ' ')
                        continue;
                    else {
                        stringBuilder.append(charSequence.charAt(i));
                        if ((stringBuilder.length() == 5 || stringBuilder.length() == 10 || stringBuilder.length() == 15 || stringBuilder.length() == 20 || stringBuilder.length() == 25 || stringBuilder.length() == 27) && stringBuilder.charAt(stringBuilder.length() - 1) != ' ') {
                            stringBuilder.insert(stringBuilder.length() - 1, ' ');
                        }
                    }
                }
        }
        if (clearInputType == TYPE_ID_CARD)//如果是身份证号码格式 //123456 8901 3456 8901
        {
            if (!isAutoSpace)
                stringBuilder.append(charSequence);
            else
                for (int i = 0; i < charSequence.length(); i++) {
                    if (i != 6 && i != 11 && i != 16 && i != 21 && charSequence.charAt(i) == ' ')
                        continue;
                    else {
                        stringBuilder.append(charSequence.charAt(i));
                        if ((stringBuilder.length() == 7 || stringBuilder.length() == 12 || stringBuilder.length() == 17 || stringBuilder.length() == 22) && stringBuilder.charAt(stringBuilder.length() - 1) != ' ') {
                            stringBuilder.insert(stringBuilder.length() - 1, ' ');
                        }
                    }
                }
        }
        if (!stringBuilder.toString().equals(charSequence.toString())) {
            setText(stringBuilder.toString());
            setSelection(stringBuilder.toString().length());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
