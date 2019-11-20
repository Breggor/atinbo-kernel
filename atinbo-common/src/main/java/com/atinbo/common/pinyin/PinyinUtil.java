package com.atinbo.common.pinyin;

import com.atinbo.common.PropertyUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zenghao
 * @date 2019-11-19
 */
public class PinyinUtil {

    private static final String DUOYINZI_SETTING = "duoyinzi_pinyin.txt";

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtils.class);

    private static Map<String,String> dictionary = new HashMap<>();

    static{
        //加载自定义多音字词典
        BufferedReader br = null;
        try {
            InputStream inputStream = PinyinUtil.class.getClassLoader().getResourceAsStream(DUOYINZI_SETTING);
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split("#");
                if (StringUtils.isNotEmpty(arr[1])) {
                    String[] sems = arr[1].split(" ");
                    for (String sem : sems) {
                        if (StringUtils.isNotEmpty(sem)) {
                            dictionary.put(sem, arr[0]);
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("多音字配置文件编码格式错误", e);
        } catch (FileNotFoundException e) {
            LOGGER.error("无法找到多音字配置文件", e);
        } catch (IOException e) {
            LOGGER.error("读取多音字配置文件失败", e);
        } catch (Exception e) {
            LOGGER.error("多音字配置初始化异常", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOGGER.error("br close error:", e);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(toPinyin("我是宋朝人"));
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
    }

    /**
     * 中文转换为拼音
     * @param chineseCharacter
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    private static String[] toHanyuPinyin(char chineseCharacter) throws BadHanyuPinyinOutputFormatCombination {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        //ASCII >=33 ASCII<=125的直接返回 ,ASCII码表：http://www.asciitable.com/
        if(chineseCharacter>=32 && chineseCharacter<=125){
            return new String[]{String.valueOf(chineseCharacter)};
        }
        return PinyinHelper.toHanyuPinyinStringArray(chineseCharacter, outputFormat);
    }

    /**
     * 获取汉字拼音的全拼
     * @param chineseCharacter 中文字符串
     * @return 每个汉字的拼音
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toPinyin(String chineseCharacter) throws BadHanyuPinyinOutputFormatCombination{
        String[] result = toPinyinArray(chineseCharacter);
        return String.join("", result);
    }

    /**
     * 将中文字符串转换为拼音全拼大写
     * @param chineseCharacter
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toPinyinUpperCase(String chineseCharacter) throws BadHanyuPinyinOutputFormatCombination {
        return toPinyin(chineseCharacter).toUpperCase();
    }

    /**
     * 转换为拼音 首字母大写
     * @param chineseCharacter 中文字符串
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toPinYinFirstUpperCase(String chineseCharacter) throws BadHanyuPinyinOutputFormatCombination{
        String[] result = toPinyinArray(chineseCharacter);
        List<String> list = Stream.of(result).map(s -> s.substring(0, 1).toUpperCase()+s.substring(1)).collect(Collectors.toList());
        return String.join("", list);
    }

    /**
     * 转换为拼音首字母缩写
     * @param chineseCharacter 中文字符串
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toPinYinAcronym(String chineseCharacter) throws BadHanyuPinyinOutputFormatCombination{
        String[] result = toPinyinArray(chineseCharacter);
        List<String> list = Stream.of(result).map(s -> s.substring(0, 1)).collect(Collectors.toList());
        return String.join("", list);
    }

    /**
     * 转换为拼音首字母缩写大写
     * @param chineseCharacter 中文字符串
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toPinYinAcronymUpperCase(String chineseCharacter) throws BadHanyuPinyinOutputFormatCombination{
        return toPinYinAcronym(chineseCharacter).toUpperCase();
    }

    /**
     * 获取汉字拼音的全拼
     * @param chineseCharacter 中文字符串
     * @return 每个汉字的拼音 数组
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String[] toPinyinArray(String chineseCharacter) throws BadHanyuPinyinOutputFormatCombination{
        if(StringUtils.isEmpty(chineseCharacter)){
            return null;
        }
        char[] chs = chineseCharacter.toCharArray();
        int len = chs.length;
        String[] result = new String[len];
        for (int i = 0; i < len; i++) {
            String[] arr = toHanyuPinyin(chs[i]);
            if (arr != null) {
                if (arr.length == 1) {
                    //一个读音
                    result[i] = arr[0];
                } else if (arr[0].equals(arr[1])) {
                    //多个读音但拼音相同
                    result[i] = arr[0];
                } else {
                    //多音字
                    String prim = chineseCharacter.substring(i, i + 1);
                    String lst = null, rst = null;
                    //和后面的字组词
                    if (i <= chineseCharacter.length() - 2) {
                        rst = chineseCharacter.substring(i, i + 2);
                    }
                    //和前面的字组词
                    if (i >= 1 && i + 1 <= chineseCharacter.length()) {
                        lst = chineseCharacter.substring(i - 1, i + 1);
                    }
                    String answer = null;
                    for (String py : arr) {
                        if (StringUtils.isEmpty(py)) {
                            continue;
                        }
                        boolean flag = (lst != null && py.equals(dictionary.get(lst))) ||
                                (rst != null && py.equals(dictionary.get(rst)));
                        if (flag) {
                            answer = py;
                            break;
                        }
                        if (py.equals(dictionary.get(prim))) {
                            answer = py;
                        }
                    }
                    if (answer != null) {
                        result[i] = answer;
                    } else {
                        //多音字无法处理，返回首个拼音
                        result[i] = arr[0];
                    }
                }
            }
        }
        return result;
    }


}
