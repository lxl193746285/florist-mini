package com.qy.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.util.CollectionUtils;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 拼音工具类
 *
 * @author legendjw
 */
public class PinyinUtils {
    /**
     * 获得汉语拼音【首字母】
     *
     * @param chinesStr 汉字
     * @return
     */
    public static String getAlpha(String chinesStr) {
        String chines = StringFilter(chinesStr);
        if (chines == null || chines.length() == 0) {
            return "";
        }
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        if(nameChar == null || nameChar.length == 0){
            return "";
        }
        for (int i = 0; i < nameChar.length; i++) {

            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }catch (ArrayIndexOutOfBoundsException ae){
                    continue;
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    /**
     * 将字符串中的中文转化为【拼音】,英文字符不变
     *
     * @param inputStr 汉字
     * @return
     */
    public static String getPingYin(String inputStr) {
        String inputString = StringFilter(inputStr);
        if (inputString == null || inputString.length() == 0) {
            return "";
        }
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String output = "";
        if (inputString != null && inputString.length() > 0
                && !"null".equals(inputString)) {
            char[] input = inputString.trim().toCharArray();
            try {
                for (int i = 0; i < input.length; i++) {
                    if (Character.toString(input[i]).matches(
                            "[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(
                                input[i], format);
                        output += temp[0];
                    } else
                        output += Character.toString(input[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            return "*";
        }
        return output;
    }

    public static String StringFilter(String str) throws PatternSyntaxException {


        // 只允许字母和数字


        // String   regEx = "[^a-zA-Z0-9]";


        // 清除掉所有特殊字符


        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";


        Pattern p = Pattern.compile(regEx);


        Matcher m = p.matcher(str);


        return m.replaceAll("").trim();


    }
}