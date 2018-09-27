package com.zoke.springboot.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * XssFilter工具类，用于处理XSS脚本
 *
 * @author ZOKE
 * @date 2018-9-22 / 下午 05:20
 */
public class XssFilterUtils {

    private static final Pattern SCRIPTTAG = Pattern.compile("<script([^>]*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern ATAG = Pattern.compile("<a([^>]*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern BUTTAG = Pattern.compile("<button[^>]*?>", Pattern.CASE_INSENSITIVE);
    private static final Pattern SCRIPT_PATTERN = Pattern.compile("<script([^>]*?)>(.*?)</script(.*?)>",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern SRCTAG_PATTERN = Pattern.compile("SRC=(.*?)*?",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern ATAG_PATTERN = Pattern.compile("<a([^>]*?)>(.*?)</a(.*?)>",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern EVAL_PATTERN = Pattern.compile("eval\\s*\\((.*)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern ONLOAD_FUNCTION_PATTERN = Pattern.compile("onload\\s*\\((.*)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("expression\\s*\\((.*)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern BUTTAG_PATTERN = Pattern.compile("<button[^>]*?>(.*?)</button>", Pattern.CASE_INSENSITIVE);
    private static final Pattern STYLETAG_PATTERN = Pattern.compile("style\\s*=\\s*\"(.*?)\"",Pattern.CASE_INSENSITIVE);

    /**
     * 对请求参数进行XSS防注入处理
     *
     * @param param 请求参数
     * @return 处理后的字符串
     */
    public static String paramXssHandler(String param){
        if(StringUtils.isNotBlank(param)){
            int count = 0;
            while (isNeedXssHandler(param)){
                param = matchAndDispatcher(param);
                count++;
                if(count > 100){
                    return param;
                }
            }
        }
        return param;
    }

    /**
     * 对字符串进行XSS防注入处理
     *
     * @param input 待处理字符串
     * @return      处理后的字符串
     */
    private static String matchAndDispatcher(String input) {
        Matcher scriptMatcher = SCRIPT_PATTERN.matcher(input);
        Matcher scriptTagMatcher = SCRIPTTAG.matcher(input);
        if (scriptMatcher.find()){
            input = handlerXss(input, scriptMatcher, SCRIPT_PATTERN);
        }else if(scriptTagMatcher.find()){
            input = handlerXss(input, scriptTagMatcher, SCRIPTTAG);
        }
        Matcher amatcher = ATAG_PATTERN.matcher(input);
        Matcher aTagMatcher = ATAG.matcher(input);
        if (amatcher.find()){
            input = handlerXss(input, amatcher, ATAG_PATTERN);
        }else if(aTagMatcher.find()){
            input = handlerXss(input, aTagMatcher, ATAG);
        }
        Matcher butMatcher = BUTTAG_PATTERN.matcher(input);
        Matcher butTagMatcher = BUTTAG.matcher(input);
        if (butMatcher.find()){
            input = handlerXss(input, butMatcher, BUTTAG_PATTERN);
        }else if(butTagMatcher.find()){
            input = handlerXss(input, butTagMatcher, BUTTAG);
        }
        Matcher srcTagMatcher = SRCTAG_PATTERN.matcher(input);
        if (srcTagMatcher.find()){
            input = handlerXss(input, srcTagMatcher, SRCTAG_PATTERN);
        }
        Matcher evalMatcher = EVAL_PATTERN.matcher(input);
        if (evalMatcher.find()){
            input = handlerXss(input, evalMatcher, EVAL_PATTERN);
        }
        Matcher onLoadMatcher = ONLOAD_FUNCTION_PATTERN.matcher(input);
        if (onLoadMatcher.find()){
            input = handlerXss(input, onLoadMatcher, ONLOAD_FUNCTION_PATTERN);
        }
        Matcher expressionMatcher = EXPRESSION_PATTERN.matcher(input);
        if (expressionMatcher.find()){
            input = handlerXss(input, expressionMatcher, EXPRESSION_PATTERN);
        }
        Matcher styleTagMatcher = STYLETAG_PATTERN.matcher(input);
        if (styleTagMatcher.find()){
            input = handlerXss(input, styleTagMatcher, STYLETAG_PATTERN);
        }
        return input;
    }

    /**
     * 对输入的字符串进行转义以及特殊处理
     *
     * @param result    待处理字符串
     * @param matcher   匹配结果
     * @param pattern   正则表达式对象
     * @return          处理后的字符串
     */
    public static String handlerXss(String result, Matcher matcher, Pattern pattern) {
        String resultString;
        if(pattern == SRCTAG_PATTERN){
            return StringUtils.replaceFirst(result, "src", "s_rc");
        }else if(pattern == EVAL_PATTERN){
            return StringUtils.replaceFirst(result, "eval", "ev_al");
        }else if(pattern == ONLOAD_FUNCTION_PATTERN){
            return StringUtils.replaceFirst(result, "onload", "on_load");
        }else if(pattern == EXPRESSION_PATTERN){
            return StringUtils.replaceFirst(result, "expression", "ex_pression");
        }else if(pattern == STYLETAG_PATTERN){
            return StringUtils.replaceFirst(result, "style", "sty_le");
        }else {
            resultString = HtmlUtils.htmlEscape(matcher.group());
        }
        return StringUtils.replaceFirst(result, pattern.toString(), resultString);
    }

    /**
     * 判断该字符串是否为json格式
     *
     * @param value 请求参数字符串
     * @return boolean
     */
    public static boolean isJson(String value) {
        String jsonRegex = "\\[[^\\]]+\\]|\\{[^\\}]+\\}";
        if (StringUtils.isNotBlank(value) && value.matches(jsonRegex)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否需要继续处理
     *
     * @param result 请求参数
     * @return boolean
     */
    public static boolean isNeedXssHandler(String result) {
        if(StringUtils.isEmpty(result)){
            return false;
        }
        if (SCRIPT_PATTERN.matcher(result).find() || SRCTAG_PATTERN.matcher(result).find()
        || EVAL_PATTERN.matcher(result).find() || ATAG_PATTERN.matcher(result).find()
        || ONLOAD_FUNCTION_PATTERN.matcher(result).find() || EXPRESSION_PATTERN.matcher(result).find()
        || BUTTAG_PATTERN.matcher(result).find() || STYLETAG_PATTERN.matcher(result).find()
        || SCRIPTTAG.matcher(result).find() || ATAG.matcher(result).find() || BUTTAG.matcher(result).find()){
            return true;
        }
        return false;
    }

}
