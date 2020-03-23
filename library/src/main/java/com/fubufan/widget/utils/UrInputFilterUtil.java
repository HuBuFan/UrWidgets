package com.fubufan.widget.utils;

import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author With You
 * @version 5.0.0
 * @date 2020-03-23 14:26
 * @email 1713397546@qq.com
 * @description
 */
public class UrInputFilterUtil {

    public static void setFilter(EditText editText, InputFilter filter) {
        InputFilter[] filters = {filter};
        editText.setFilters(filters);
    }

    private static boolean match(String regex, String str) {
        if (TextUtils.isEmpty(str))
            return false;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 输入 监听电话号码格式
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isInputPhoneFormat(String phoneNumber) {
        String regular = "^1[0-9]{0,10}$";
        return match(regular, phoneNumber);
    }

    /**
     * 输入时 是仅为字母或者数字组合
     * @param content
     * @return
     */
    public static boolean isInputOnlyLetterOrNumberFormat(String content) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("^").append("[a-zA-Z0-9]{0,20}").append("$");
        String regular = stringBuffer.toString();
        return match(regular, content);
    }

}
