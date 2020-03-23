package com.fubufan.widget.textview;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author With You
 * @version 5.0.0
 * @date 2020-03-23 12:02
 * @email 1713397546@qq.com
 * @description
 */
public class ChineseAndEnglishInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Pattern p = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5]+");//只能输入中英文
        //Pattern p = Pattern.compile("[\\*|\u4e00-\u9fa5]+");//只能输入中文和*
        //Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");//只能输入中文
        Matcher m = p.matcher(source.toString());
        if (m.matches())//符合要求
            return dest.subSequence(dstart, dend);
        else
            return "";
    }

}
