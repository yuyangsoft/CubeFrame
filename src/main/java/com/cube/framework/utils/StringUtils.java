package com.cube.framework.utils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * 字符串工具类
 * @author 于洋
 * @version 0.3
 * **版本说明**
 * 2012-07-24 0.1ver 版本建立,初始化工具类方法.
 * 2012-07-26 0.2ver 新增方法：
 * 						首字母大写方法[upCaseFirstChar(String)]
 * 						首字母大写方法[upCaseFirstChar(String[])]
 * 						左截取方法[subStrLeft(String, Integer)]
 * 						右截取方法[subStrRight(String, Integer)]
 * 2013-06-27 0.3ver 新增方法：
 * 						获取32位UUID[getUUID32()]
 */
public class StringUtils {
	
	/**
	 * 将指定字符首字母大写
	 * @param targetStr 指定字符串
	 * @return String 首字母大写字符串
	 */
	public static String upCaseFirstChar(String targetStr){
		String resultStr = null;
		if(ValidatorUtils.isEmpty(targetStr)){
			resultStr = "";
		}else{
			String firstChar = StringUtils.subStrLeft(targetStr, 1).toUpperCase();
			resultStr = firstChar + StringUtils.subStrRight(targetStr, targetStr.length() - 1);
		}
		return resultStr;
	}
	
	/**
	 * 将指定字符串数组中的所有字符串首字母大写
	 * @param strArray 指定字符串数组
	 * @return String[] 首字母大写字符串数组
	 */
	@SuppressWarnings("null")
	public static String[] upCaseFirstChar(String[] strArray){
		String[] strNewArray = null;
		if(ValidatorUtils.isEmpty(strArray)){
			strNewArray = new String[strArray.length];
		}else{
			for(int i = 0; i < strArray.length; i++){
				String firstChar = StringUtils.subStrLeft(strArray[i], 1).toUpperCase();
				strNewArray[i] = firstChar + StringUtils.subStrRight(strArray[i], strArray[i].length() - 1);
			}
		}
		return strNewArray;
	}
	
	/**
	 * 从指定字符串开头向后截取指定长度字符串
	 * @param targetStr 指定字符串
	 * @param subLength 指定截取长度,小于0时默认为0,大于指定字符串长度时默认为字符串长度
	 * @return String 截取后字符串
	 */
	public static String subStrLeft(String targetStr, Integer subLength){
		String resultStr = null;
		if(ValidatorUtils.isEmpty(targetStr)){
			resultStr = "";
		}else{
			subLength = subLength < 0 ? 0 : targetStr.length() < subLength ? targetStr.length() : subLength;
			resultStr = targetStr.substring(0, subLength);
		}
		return resultStr;
	}
	
	/**
	 * 从指定字符串结尾向前截取指定长度字符串
	 * @param targetStr 指定字符串
	 * @param subLength 指定截取长度,小于0时默认为0,大于指定字符串长度时默认为字符串长度
	 * @return String 截取后字符串
	 */
	public static String subStrRight(String targetStr, Integer subLength){
		String resultStr = null;
		if(ValidatorUtils.isEmpty(targetStr)){
			resultStr = "";
		}else{
			subLength = subLength < 0 ? 0 : targetStr.length() < subLength ? targetStr.length() : subLength;
			resultStr = targetStr.substring(targetStr.length() - subLength);
		}
		return resultStr;
	}
	
	/**
	 * 将目标对象数组转换为指定间隔的字符串
	 * @param strArray 目标对象数组,自动过滤数组中的null或空字符串
	 * @param spliter 指定间隔符,为null或空字符串时,结果将无间隔
	 * @return String 指定间隔的字符串
	 */
	public static String buildSplit(Object[] strArray, String spliter){
		String resultStr = null;
		if(ValidatorUtils.isEmpty(strArray)){
			resultStr = "";
		}else if(strArray.length == 1){
			resultStr = strArray[0].toString();
		}else{
			StringBuffer strBuff = new StringBuffer();
			for(Object str : strArray){
				if(ValidatorUtils.isEmpty(str)){
					continue;
				}else{
					if(ValidatorUtils.isEmpty(spliter)){
						strBuff.append(str);
					}else{
						strBuff.append(str).append(spliter);
					}
				}
			}
			if(!ValidatorUtils.isEmpty(spliter)){
				strBuff.deleteCharAt(strBuff.lastIndexOf(spliter));
			}
		}
		return resultStr;
	}
	
	/**
	 * 将指定字符串编码修改为GBK
	 * @param targetStr 指定字符串
	 * @return String GBK编码字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String toGBK(String targetStr) throws UnsupportedEncodingException {
		String str = null;
		if(ValidatorUtils.isEmpty(targetStr)){
			str = "";
		}else{
			str = new String(targetStr.getBytes("ISO-8859-1"),"GBK");
		}
		return str;
	}
	
	/**
	 * 将指定字符串编码修改为UTF8
	 * @param targetStr 指定字符串
	 * @return String UTF8编码字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String toUTF8(String targetStr) throws UnsupportedEncodingException {
		String str = null;
		if(ValidatorUtils.isEmpty(targetStr)){
			str = "";
		}else{
			str = new String(targetStr.getBytes("ISO-8859-1"),"UTF8");
		}
		return str;
	}
	
	/**
	 * 将指定字符串数组中的指定字符串替换为新字符串
	 * @param strArray 指定字符串数组
	 * @param oldStr 需替换的字符串
	 * @param newStr 替换为的字符串
	 * @return String[] 替换后的字符串数组
	 */
	public static String[] replaceStrArray(String[] strArray, String oldStr, String newStr){
		String[] strNewArray = null;
		if(ValidatorUtils.isEmpty(strArray)){
			strNewArray = strArray;
		}else{
			strNewArray = new String[strArray.length];
			for(int i = 0; i < strArray.length; i++){
				String str = strArray[i];
				if(ValidatorUtils.isEmpty(str)){
					strNewArray[i] = str;
				}else{
					strNewArray[i] = str.replace(oldStr, newStr);
				}
			}
		}
		return strNewArray;
	}
	
	/**
	 * 将指定字符串数组中点的字符串按指定下标截取
	 * @param strArray 指定字符串数组
	 * @param beginIndex 截取起始下标,起始下标必须小于等于截止下标
	 * @param endIndex 截取截止下标,截止下标对应字符不算在截取范围内
	 * @return String[] 截取后的字符串数组
	 */
	public static String[] subStrArray(String[] strArray, Integer beginIndex, Integer endIndex){
		String[] strNewArray = null;
		if(ValidatorUtils.isEmpty(strArray)){
			strNewArray = strArray;
		}else{
			strNewArray = new String[strArray.length];
			if(beginIndex <= endIndex){
				beginIndex = beginIndex < 0 ? 0 : beginIndex;
				for(int i = 0; i < strArray.length; i++){
					String str = strArray[i];
					if(ValidatorUtils.isEmpty(str)){
						strNewArray[i] = str;
					}else{
						Integer ei = endIndex > str.length() ? str.length() : endIndex;
						strNewArray[i] = str.substring(beginIndex, ei);
					}
				}
			}
		}
		return strNewArray;
	}
	
	/**
	 * 将指定字符串数组中的字符串按指定方向和指定截取标志截取
	 * @param strArray 指定字符串数组
	 * @param spliter 截取标志字符串
	 * @param type 截取方向,0：从开始截取到指定截取标志;1：从指定截取标志截取到结尾;
	 * @return String[] 截取后的字符串数组
	 */
	public static String[] subStrArray(String[] strArray, String spliter, Integer type){
		String[] strNewArray = null;
		if(ValidatorUtils.isEmpty(strArray)){
			strNewArray = strArray;
		}else{
			strNewArray = new String[strArray.length];
			for(int i = 0; i < strArray.length; i++){
				String str = strArray[i];
				if(ValidatorUtils.isEmpty(str)){
					strNewArray[i] = str;
				}else{
					type = type == null ? 0 : type;
					if(str.indexOf(spliter) < 0){
						strNewArray[i] = str;
					}else if(type == 0){
						strNewArray[i] = str.substring(0, str.indexOf(spliter));
					}else{
						strNewArray[i] = str.substring(str.indexOf(spliter) + spliter.length());
					}
				}
			}
		}
		return strNewArray;
	}
	
	/**
	 * 获取32为UUID
	 * @return 32位不重复字符串
	 */
	public static String getUUID32(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
