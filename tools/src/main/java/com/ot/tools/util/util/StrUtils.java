/**
 * Copyright (c) 2011-2017, James Zhan 詹波 (jfinal@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ot.tools.util.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Xie
 * @date 19-3-19
 */
public final class StrUtils {

    /**
     * 首字母变小写
     */
    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 首字母变大写
     */
    public static String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 字符串为 null 或者内部字符全部为 ' ' '\t' '\n' '\r' 这四类字符时返回 true
     */
    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        int len = str.length();
        if (len == 0) {
            return true;
        }
        for (int i = 0; i < len; i++) {
            switch (str.charAt(i)) {
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                    // case '\b':
                    // case '\f':
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    public static boolean notBlank(String... strings) {
        if (strings == null) {
            return false;
        }
        for (String str : strings) {
            if (isBlank(str)) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNull(Object... paras) {
        if (paras == null) {
            return false;
        }
        for (Object obj : paras) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    public static String toCamelCase(String stringWithUnderline) {
        if (stringWithUnderline.indexOf('_') == -1) {
            return stringWithUnderline;
        }

        stringWithUnderline = stringWithUnderline.toLowerCase();
        char[] fromArray = stringWithUnderline.toCharArray();
        char[] toArray = new char[fromArray.length];
        int j = 0;
        for (int i = 0; i < fromArray.length; i++) {
            if (fromArray[i] == '_') {
                // 当前字符为下划线时，将指针后移一位，将紧随下划线后面一个字符转成大写并存放
                i++;
                if (i < fromArray.length) {
                    toArray[j++] = Character.toUpperCase(fromArray[i]);
                }
            } else {
                toArray[j++] = fromArray[i];
            }
        }
        return new String(toArray, 0, j);
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        if (sb.charAt(0) == '_') {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    public static String join(String[] stringArray) {
        StringBuilder sb = new StringBuilder();
        for (String s : stringArray) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static String join(String[] stringArray, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringArray.length; i++) {
            if (i > 0) {
                sb.append(separator);
            }
            sb.append(stringArray[i]);
        }
        return sb.toString();
    }

    public static boolean equals(String a, String b) {
        return a == null ? b == null : a.equals(b);
    }

    public static String getRandomUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    public static List<Integer> getIDListFromStr(String idsStr) {
        List<Integer> ids = new ArrayList<>();
        if (notBlank(idsStr)) {
            String[] idStrs = idsStr.split(",");
            for (String idStr : idStrs) {
                ids.add(Integer.parseInt(idStr));
            }
        }
        return ids;
    }

    public static boolean isNullString(String str) {
        return !(str != null && str.length() > 0);
    }


    public static String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    /**
     * 去除数据里面的非法字符"\uFEFF"
     *
     * @param salesStr
     * @return
     */
    public static String removeIllegalChar(String salesStr) {
        if (salesStr.startsWith("\uFEFF")) {
            salesStr = salesStr.replace("\uFEFF", "");
        } else if (salesStr.endsWith("\uFEFF")) {
            salesStr = salesStr.replace("\uFEFF", "");
        }
        return salesStr.trim();
    }

    /**
     * 获取字符串中的数字
     */
    public static Integer getNumberFromStr(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String trim = m.replaceAll("").trim();
        if (!"".equals(trim)) {
            return Integer.parseInt(trim);
        }
        return 0;
    }

    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }

    /**
     * 校验字符串是否数字类型
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    /**
     * 校验字符串是否为日期类型
     *
     * @param dateStr
     * @return
     */
    public static boolean checkDateStr(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 设置日期转化成功标识
        boolean dateflag = true;
        // 这里要捕获一下异常信息
        try {
            Date date = format.parse(dateStr);
        } catch (ParseException e) {
            dateflag = false;
        } finally {
            // 成功：true ;失败:false
            System.out.println("日期是否满足要求:" + dateflag);
        }
        return dateflag;
    }

    /**
     * 字母开头
     * @param str
     * @return
     */
    public static boolean firstCharCheck(String str) {
        char c = str.charAt(0);
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        } else {
            return false;
        }
    }
}




