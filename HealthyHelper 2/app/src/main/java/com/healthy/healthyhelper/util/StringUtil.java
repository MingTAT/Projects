package com.healthy.healthyhelper.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.DynamicDrawableSpan;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ming on 2016/4/3.
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "null".equalsIgnoreCase(str)) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static String getString(Map map, String split) {
        if (map == null) {
            return "[]";
        }

        if (split.length() > 1) {
            split = split.substring(0, 1);
        }
        Iterator it = map.keySet().iterator();
        StringBuffer buffer = new StringBuffer("[");

        while (it.hasNext()) {
            Object next = it.next();
            buffer.append(next).append("=").append(map.get(next)).append(split);
        }
        if (buffer.length() > 1) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        buffer.append("]");
        return buffer.toString();
    }


    /**
     * @Description: 将值为null的字符串替换为""
     * @param:参数描述
     * @return：返回结果描述
     * @throws：异常描述
     * @version: v1.0.0
     */
    public static String replaceNull(String string) {
        return string == null ? "" : string;
    }

    /**
     * @Description: 去掉字符串中的所有空格 ，回车换行符和水平制表符
     * @param:参数描述
     * @return：返回结果描述
     * @throws：异常描述
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * @Description: 将数字 字符等类型转换成字符串类型
     * @param:参数描述
     * @return：返回结果描述
     * @throws：异常描述
     * @version: v1.0.0
     */
    public static String toString(Object str) {
        if (str == null) {
            return "";
        }
        return str + "";
    }

    public static String toString(List<String> list) {
        String returnStr = "";
        if (list != null && list.size() > 0) {
            for (String id : list) {
                returnStr += id + ",";
            }
            if (returnStr.length() > 0) {
                returnStr = returnStr.substring(0, returnStr.length() - 1);
            }
        }
        return returnStr;
    }

    public static List<String> toString(List<String> list, int n) {
        List<String> returnList = new ArrayList<String>();
        String str = "";
        int i = 0;
        if (list != null && list.size() > 0) {
            for (String id : list) {
                str += id + ",";
                if (++i % n == 0) {
                    if (str.length() > 0) {
                        str = str.substring(0, str.length() - 1);
                    }
                    returnList.add(str);
                    str = "";
                }
            }
            if (str.length() > 0) {
                if (str.length() > 0) {
                    str = str.substring(0, str.length() - 1);
                }
                returnList.add(str);
            }
        }
        return returnList;
    }

    /**
     * @Description: 根据字符串长度拆分字符串
     * @param:参数描述
     * @return：返回结果描述
     * @throws：异常描述
     * @version: v1.0.0
     */
    public static List<String> splitStringByLength(String str, int MaxLength) {
        List<String> listStr = new ArrayList<String>();
        if (str.length() > MaxLength) {
            String str1 = str.substring(0, MaxLength);
            listStr.add(str1);
            String rStr = str.substring(MaxLength);
            while (rStr.length() > MaxLength) {
                String str3 = new String(rStr.substring(0, MaxLength));
                listStr.add(str3);
                rStr = rStr.substring(MaxLength);
            }
            listStr.add(rStr);

        } else {
            listStr.add(str);
        }
        return listStr;
    }

    /**
     * @Description: 用指定字符代替空
     * @param:参数描述
     * @return：返回结果描述
     * @throws：异常描述
     * @version: v1.0.0
     */
    public static String replaceNull(String string, String replaceStr) {
        return string == null ? replaceStr : string;
    }

    public static String toString(StringBuffer sb, int beginIndex, int endIndex) {
        String str = null;
        if (sb != null) {
            str = sb.toString();
        }
        if (str != null && beginIndex < endIndex && beginIndex < str.length() && endIndex < str.length()) {
            str = str.substring(beginIndex, endIndex);
        }
        return str;
    }

    public static Long toLong(Object str) {
        Long l = null;
        try {
            l = Long.parseLong(str.toString());
        } catch (Exception e) {
            l = 0L;
        }
        return l;
    }

    /**
     * @Description: 保留到小数点后N位
     * @param:参数描述
     * @return：返回结果描述
     * @throws：异常描述
     * @version: v1.0.0
     */
    public static String format(Object obj, int N) {
        String str = "";
        if (obj != null) {
            try {
                double d = Double.parseDouble(obj + "");
                String format = "%." + N + "f";
                str = String.format(format, d);
            } catch (Exception e) {
                str = "";
            }
        }
        return str;
    }

    public static int toInt(Object str) {

        if ("".equals(str + "") || "null".equals(str + "")) {
            return 0;
        } else {
            try {
                return Integer.parseInt(str + "");
            } catch (Exception e) {
                return 0;
            }
        }
    }

    public static Integer toInteger(Object str) {
        Integer n = null;
        try {
            n = Integer.parseInt(str + "");
        } catch (Exception e) {
            n = 0;
        }
        return n;
    }

    public static Float toFloat(Object str) {
        Float f = null;
        try {
            f = Float.parseFloat((String) str);
        } catch (Exception e) {
            f = 0f;
        }
        return f;
    }

    public static Double toDouble(Object str) {
        if ("".equals(str + "") || "null".equals(str + "")) {
            return null;
        }
        return Double.parseDouble(str + "");
    }


    public static double getDouble(Double d) {
        if (d == null) {
            return 0.00;
        }
        return d;
    }

    public static int getInteger(Integer i) {
        if (i == null) {
            return 0;
        }
        return i;
    }

    public static long getLong(Long l) {
        if (l == null) {
            return 0;
        }
        return l;
    }


    public String changeCharset(String str, String oldCharset, String newCharset)
            throws UnsupportedEncodingException {
        if (str != null) {
            //用旧的字符编码解码字符串。解码可能会出现异常。
            byte[] bs = str.getBytes(oldCharset);
            //用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * @param str        待转换编码的字符串
     * @param newCharset 目标编码
     * @Description: * 字符串编码转换的实现方法
     * @return：返回结果描述
     * @throws：异常描述
     */
    public String changeCharset(String str, String newCharset)
            throws UnsupportedEncodingException {
        if (str != null) {
            //用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            //用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    public static boolean isEmail(String userEmail) {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(userEmail);
        return matcher.matches();
    }

    public static boolean isNickName(String nickName) {
        String regex = "^[a-zA-Z0-9]+[a-zA-Z0-9]{6,19}+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nickName);
        if (matcher.matches())
            return true;

        return false;
    }

    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    public static SpannableString setAtImageSpan(final Context context,String nameStr,String content,int color,int textSize) {

        if (content.endsWith("@") || content.endsWith("＠")) {
            content = content.substring(0, content.length() - 1);
        }
        String tmp = content;

        SpannableString ss = new SpannableString(tmp);

        if (nameStr != null) {
            String[] names = nameStr.split(" ");
            if (names != null && names.length > 0) {
                for (String name : names) {
                    if (name != null && name.trim().length() > 0) {
                        final Bitmap bmp = getNameBitmap(context,name,color,textSize);

                        // 这里会出现删除过的用户，需要做判断，过滤掉
                        if (tmp.indexOf(name) >= 0
                                && (tmp.indexOf(name) + name.length()) <= tmp
                                .length()) {

                            // 把取到的要@的人名，用DynamicDrawableSpan代替
                            ss.setSpan(
                                    new DynamicDrawableSpan(
                                            DynamicDrawableSpan.ALIGN_BASELINE) {

                                        @Override
                                        public Drawable getDrawable() {
                                            // TODO Auto-generated method stub
                                            BitmapDrawable drawable = new BitmapDrawable(
                                                    context.getResources(), bmp);
                                            drawable.setBounds(0, 0,
                                                    bmp.getWidth(),
                                                    bmp.getHeight());
                                            return drawable;
                                        }
                                    }, tmp.indexOf(name),
                                    tmp.indexOf(name) + name.length(),
                                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                }
            }
        }
        return ss;
    }

    /**
     * 把返回的人名，转换成bitmap
     *
     * @param name
     * @return
     */
    public static Bitmap getNameBitmap(Context context,String name,int color,int textSize) {

		/* 把@相关的字符串转换成bitmap 然后使用DynamicDrawableSpan加入输入框中 */

        name = "" + name;
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(color));
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.LEFT);
        Rect rect = new Rect();

        paint.getTextBounds(name, 0, name.length(), rect);

        // 获取字符串在屏幕上的长度
        int width = (int) (paint.measureText(name));

        final Bitmap bmp = Bitmap.createBitmap(width, rect.height(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        canvas.drawText(name, rect.left, rect.height() - rect.bottom, paint);

        return bmp;
    }
}
