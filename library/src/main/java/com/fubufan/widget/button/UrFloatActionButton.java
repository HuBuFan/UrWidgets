package com.fubufan.widget.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.widget.AppCompatImageView;

import com.fubufan.widget.R;

/**
 * 可以拖拽的悬浮按钮
 *
 * @author With You
 * @version 5.0.0
 * @date 2020-03-23 11:33
 * @email 1713397546@qq.com
 * @description
 */
public class UrFloatActionButton extends AppCompatImageView {

    private static final String TAG = UrFloatActionButton.class.getSimpleName();
    private int parentHeight;
    private int parentWidth;
    private int lastX;
    private int lastY;
    private boolean isDrag;
    private ViewGroup parent;
    private float dragMargin = 10;

    public UrFloatActionButton(Context context) {
        super(context);
    }

    public UrFloatActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public UrFloatActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UrFloatActionButton, defStyleAttr, 0);
        dragMargin = typedArray.getDimensionPixelSize(R.styleable.UrFloatActionButton_float_margin, 10);
        setClickable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isDrag = false;
                this.setAlpha(0.9f);
                setPressed(true);
                getParent().requestDisallowInterceptTouchEvent(true);
                lastX = rawX;
                lastY = rawY;
                if (getParent() != null) {
                    parent = (ViewGroup) getParent();
                    parentHeight = parent.getHeight();
                    parentWidth = parent.getWidth();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                this.setAlpha(0.9f);
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                int distance = (int) Math.sqrt(dx * dx + dy * dy);
                if (distance > 2 && !isDrag) {
                    isDrag = true;
                }
                float x = getX() + dx;
                float y = getY() + dy;
                //检测是否到达边缘 左上右下
                x = x < 0 ? 0 : x > parentWidth - getWidth() ? parentWidth - getWidth() : x;
                y = getY() < 0 ? 0 : getY() + getHeight() > parentHeight ? parentHeight - getHeight() : y;
                setX(x);
                setY(y);
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_UP:
                if (isDrag) //恢复按压效果
                {
                    setPressed(false);
                    moveHide(rawX);
                }
                break;
        }
        //如果是拖拽则消耗事件，否则正常传递即可。
        return isDrag || super.onTouchEvent(event);
    }

    private void moveHide(int rawX) {
        if (rawX >= parentWidth / 2) //靠右吸附
        {
            animate().setInterpolator(new DecelerateInterpolator()).setDuration(500).xBy(parentWidth - getWidth() - getX() - dragMargin).start();
        } else //靠左吸附
        {
            animate().setInterpolator(new DecelerateInterpolator()).setDuration(500).x(dragMargin).start();
        }
    }

}
