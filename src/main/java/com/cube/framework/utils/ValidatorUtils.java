package com.cube.framework.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类
 * 
 * @author 于洋
 * @version 0.1 
 * **版本说明** 
 * 2012-07-26 0.1ver 版本建立,初始化工具类方法.
 * 
 */
public class ValidatorUtils {

	private static Pattern PATTERN_EMAIL = Pattern
			.compile("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");// 邮件地址
	private static Pattern PATTERN_TEL = Pattern
			.compile("^([0-9]{3,4}-)?[0-9]{7,8}$");// 固定电话
	private static Pattern PATTERN_MOBILE = Pattern
			.compile("^(\\+86)?0?1[3|5]\\d{9}$");// 移动电话
	private static Pattern PATTERN_ALPHA = Pattern.compile("^[A-Za-z]+$");// 字母
	private static Pattern PATTERN_DIGITAL = Pattern.compile("^\\d+$");// 数字
	private static Pattern PATTERN_CHINESE = Pattern
			.compile("^[\\u4E00-\\u9FA5]+$");// 中文
	private static Pattern PATTERN_IDCARD_15 = Pattern
			.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");// 15位身份证格式
	private static Pattern PATTERN_IDCARD_18 = Pattern
			.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}[\\d|x|X]$");// 18位身份证格式
	private static Pattern PATTERN_IP = Pattern
			.compile("^((00\\d|1?\\d?\\d|(2([0-4]\\d|5[0-5])))\\.){3}(00\\d|1?\\d?\\d|(2([0-4]\\d|5[0-5])))$");// IP格式
	private static Pattern PATTERN_TIME = Pattern
			.compile("((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])(\\:([0-5]?[0-9]))");// 时间格式
	private static Pattern PATTERN_REPEAT = Pattern.compile(".*(.).*\\1.*");// 重复字符格式

	public static Map<String, String> addressCode;
	public static int idCoefficient[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9,
			10, 5, 8, 4, 2 };
	public static char idMod[] = { '1', '0', 'x', '9', '8', '7', '6', '5', '4',
			'3', '2' };
	static {
		addressCode = new HashMap<String, String>(35);
		addressCode.put("11", "\u5317\u4EAC");
		addressCode.put("12", "\u5929\u6D25");
		addressCode.put("13", "\u6CB3\u5317");
		addressCode.put("14", "\u5C71\u897F");
		addressCode.put("15", "\u5185\u8499\u53E4");
		addressCode.put("21", "\u8FBD\u5B81");
		addressCode.put("22", "\u5409\u6797");
		addressCode.put("23", "\u9ED1\u9F99\u6C5F");
		addressCode.put("31", "\u4E0A\u6D77");
		addressCode.put("32", "\u6C5F\u82CF");
		addressCode.put("33", "\u6D59\u6C5F");
		addressCode.put("34", "\u5B89\u5FBD");
		addressCode.put("35", "\u798F\u5EFA");
		addressCode.put("36", "\u6C5F\u897F");
		addressCode.put("37", "\u5C71\u4E1C");
		addressCode.put("41", "\u6CB3\u5357");
		addressCode.put("42", "\u6E56\u5317");
		addressCode.put("43", "\u6E56\u5357");
		addressCode.put("44", "\u5E7F\u4E1C");
		addressCode.put("45", "\u5E7F\u897F");
		addressCode.put("46", "\u6D77\u5357");
		addressCode.put("50", "\u91CD\u5E86");
		addressCode.put("51", "\u56DB\u5DDD");
		addressCode.put("52", "\u8D35\u5DDE");
		addressCode.put("53", "\u4E91\u5357");
		addressCode.put("54", "\u897F\u85CF");
		addressCode.put("61", "\u9655\u897F");
		addressCode.put("62", "\u7518\u8083");
		addressCode.put("63", "\u9752\u6D77");
		addressCode.put("64", "\u5B81\u590F");
		addressCode.put("65", "\u65B0\u7586");
		addressCode.put("71", "\u53F0\u6E7E");
		addressCode.put("81", "\u9999\u6E2F");
		addressCode.put("82", "\u6FB3\u95E8");
		addressCode.put("91", "\u56FD\u5916");
	}

	/**
	 * 判断目标对象是否为null或空字符串
	 * 
	 * @param targetStr
	 *            目标字符串
	 * @return Boolean true:为空字符串或null
	 */
	public static Boolean isEmpty(Object targetStr) {
		Boolean isEmpty = false;
		if (targetStr == null || "".equals(targetStr)) {
			isEmpty = true;
		}
		return isEmpty;
	}

	/**
	 * 判断目标对象数组是否为null或长度为0
	 * 
	 * @param strArray
	 *            目标字符串
	 * @return Boolean true:长度为0或为null
	 */
	public static Boolean isEmpty(Object[] strArray) {
		Boolean isEmpty = false;
		if (strArray == null || strArray.length == 0) {
			isEmpty = true;
		}
		return isEmpty;
	}

	/**
	 * 判断目标对象是否为String对象
	 * 
	 * @param obj
	 *            目标对象
	 * @return Boolean
	 */
	public static Boolean isString(Object obj) {
		Boolean isString = false;
		if (obj != null && obj.getClass().getName().equals("java.lang.String")) {
			isString = true;
		}
		return isString;
	}

	/**
	 * 判断目标对象是否为Integer对象
	 * 
	 * @param obj
	 *            目标对象
	 * @return Boolean
	 */
	public static Boolean isInteger(Object obj) {
		Boolean isInteger = false;
		if (obj != null && obj.getClass().getName().equals("java.lang.Integer")) {
			isInteger = true;
		}
		return isInteger;
	}

	/**
	 * 判断目标对象是否为Long对象
	 * 
	 * @param obj
	 *            目标对象
	 * @return Boolean
	 */
	public static Boolean isLong(Object obj) {
		Boolean isLong = false;
		if (obj != null && obj.getClass().getName().equals("java.lang.Long")) {
			isLong = true;
		}
		return isLong;
	}

	/**
	 * 判断目标对象是否为Float对象
	 * 
	 * @param obj
	 *            目标对象
	 * @return Boolean
	 */
	public static Boolean isFloat(Object obj) {
		Boolean isFloat = false;
		if (obj != null && obj.getClass().getName().equals("java.lang.Float")) {
			isFloat = true;
		}
		return isFloat;
	}

	/**
	 * 判断目标对象是否为Double对象
	 * 
	 * @param obj
	 *            目标对象
	 * @return Boolean
	 */
	public static Boolean isDouble(Object obj) {
		Boolean isDouble = false;
		if (obj != null && obj.getClass().getName().equals("java.lang.Double")) {
			isDouble = true;
		}
		return isDouble;
	}

	/**
	 * 判断目标对象是否为Short对象
	 * 
	 * @param obj
	 *            目标对象
	 * @return Boolean
	 */
	public static Boolean isShort(Object obj) {
		Boolean isShort = false;
		if (obj != null && obj.getClass().getName().equals("java.lang.Short")) {
			isShort = true;
		}
		return isShort;
	}

	/**
	 * 判断目标对象是否为指定类名的对象
	 * 
	 * @param obj
	 *            目标对象
	 * @param className
	 *            指定类名
	 * @return Boolean
	 */
	public static Boolean isClassName(Object obj, String className) {
		Boolean isClassName = false;
		if (obj != null && obj.getClass().getName().equals(className)) {
			isClassName = true;
		}
		return isClassName;
	}

	/**
	 * 正则表达式验证
	 * 
	 * @param regexstr
	 *            正则表达式
	 * @param str
	 *            验证字符串
	 * @return Boolean
	 */
	public static Boolean match(String regexstr, String str) {
		Pattern regex = Pattern.compile(regexstr, Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher matcher = regex.matcher(str);
		return matcher.matches();
	}

	/**
	 * 判断电子邮箱格式是否合法
	 * 
	 * @param mail
	 *            电子邮箱字符串
	 * @return Boolean
	 */
	public static Boolean isMail(String mail) {
		if (mail == null) {
			return false;
		} else {
			return PATTERN_EMAIL.matcher(mail).matches();
		}
	}

	/**
	 * 判断电话号码是否合法
	 * 
	 * @param telephone
	 *            电话号码字符串
	 * @return Boolean
	 */
	public static Boolean isTelephone(String telephone) {
		if (telephone == null) {
			return false;
		} else {
			return PATTERN_TEL.matcher(telephone).matches();
		}
	}

	/**
	 * 判断手机号码是否合法
	 * 
	 * @param mobile
	 *            手机号码字符串
	 * @return Boolean
	 */
	public static Boolean isMobile(String mobile) {
		if (mobile == null) {
			return false;
		} else {
			return PATTERN_MOBILE.matcher(mobile).matches();
		}
	}

	/**
	 * 判断是否为字母
	 * 
	 * @param alpha
	 *            字母字符串
	 * @return Boolean
	 */
	public static Boolean isAlpha(String alpha) {
		if (alpha == null) {
			return false;
		} else {
			return PATTERN_ALPHA.matcher(alpha).matches();
		}
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param digital
	 *            数字字符串
	 * @return Boolean
	 */
	public static Boolean isDigital(String digital) {
		if (digital == null) {
			return false;
		} else {
			return PATTERN_DIGITAL.matcher(digital).matches();
		}
	}

	/**
	 * 判断是否为中文
	 * 
	 * @param chinese
	 *            中文字符串
	 * @return Boolean
	 */
	public static Boolean isChinese(String chinese) {
		if (chinese == null) {
			return false;
		} else {
			return PATTERN_CHINESE.matcher(chinese).matches();
		}
	}

	/**
	 * 判断字符串是否为日期时间字符串(标准格式)
	 * 
	 * @param dateTime
	 *            日期时间字符串,例如2008-9-2 3:9:1可以不用0补齐
	 * @return Boolean
	 */
	public static Boolean isDateTime(String dateTime) {
		return isDateTime(dateTime, "-");
	}

	/**
	 * 判断字符串是否为日期时间字符串
	 * 
	 * @param dateTime
	 *            日期时间字符串,例如2008-9-2 3:9:1可以不用0补齐
	 * @param partition
	 *            分隔符,这里只支持 \、/、-、空格 四类分隔符，如果是长度大于1，则取第一个字符作为分隔符
	 * @return Boolean
	 */
	public static Boolean isDateTime(String dateTime, String partition) {
		if (dateTime == null || partition == null || "".equals(partition)) {
			return false;
		}
		String s = "";
		char split = partition.charAt(0);
		if (split != '\\' && split != '/' && split != '-' && split != ' ') {
			throw new IllegalArgumentException((new StringBuilder(
					"partition can not start with '")).append(partition)
					.append("'!").toString());
		}
		s = (new StringBuilder(String.valueOf(s))).append(split).toString();
		StringBuilder part = new StringBuilder(
				"^((\\d{2}(([02468][048])|([13579][26]))");
		part.append(s);
		part.append("((((0?[13578]");
		part.append(")|(1[02]))");
		part.append(s);
		part.append("((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[4");
		part.append("69])|(11))");
		part.append(s);
		part.append("((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\");
		part
				.append("s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([1");
		part.append("3579][01345789]))");
		part.append(s);
		part.append("((((0?[13578])|(1[02]))");
		part.append(s);
		part.append("((");
		part.append("0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		part.append(s);
		part.append("((");
		part.append("0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		part.append(s);
		part.append("((");
		part.append("0?[1-9])|([1-2][0-9])|(30)))|(0?2");
		part.append(s);
		part.append("((0?[1-9])|(1[0-9])|(2[0-8]))))))");
		part
				.append("(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])\\:([0-5]?[0-9])))?");
		return Pattern.matches(part.toString(), dateTime);
	}

	/**
	 * 判断字符串是否为日期时间字符串(标准格式)
	 * 
	 * @param date
	 *            日期时间字符串,例如2012-7-26可以不用0补齐
	 * @return Boolean
	 */
	public static Boolean isDate(String date) {
		return isDate(date, "-");
	}

	/**
	 * 判断字符串是否为日期字符串
	 * 
	 * @param date
	 *            日期字符串,例如2012-7-26可以不用0补齐
	 * @param partition
	 *            分隔符,这里只支持 \、/、-、空格 四类分隔符，如果是长度大于1，则取第一个字符作为分隔符
	 * @return Boolean
	 */
	public static Boolean isDate(String date, String partition) {
		if (date == null || partition == null || "".equals(partition)) {
			return false;
		}
		String s = "";
		char split = partition.charAt(0);
		if (split != '\\' && split != '/' && split != '-' && split != ' ') {
			throw new IllegalArgumentException((new StringBuilder(
					"partition can not start with '")).append(partition)
					.append("'!").toString());
		}
		s = (new StringBuilder(String.valueOf(s))).append(split).toString();
		StringBuilder part = new StringBuilder(
				"^((\\d{2}(([02468][048])|([13579][26]))");
		part.append(s);
		part.append("((((0?[13578]");
		part.append(")|(1[02]))");
		part.append(s);
		part.append("((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[4");
		part.append("69])|(11))");
		part.append(s);
		part.append("((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\");
		part
				.append("s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([1");
		part.append("3579][01345789]))");
		part.append(s);
		part.append("((((0?[13578])|(1[02]))");
		part.append(s);
		part.append("((");
		part.append("0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		part.append(s);
		part.append("((");
		part.append("0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		part.append(s);
		part.append("((");
		part.append("0?[1-9])|([1-2][0-9])|(30)))|(0?2");
		part.append(s);
		part.append("((0?[1-9])|(1[0-9])|(2[0-8]))))))$");
		return Pattern.matches(part.toString(), date);
	}

	/**
	 * 判断字符串是否为时间字符串
	 * 
	 * @param time
	 *            时间字符串,例如9:3:1可以不用0补齐
	 * @return Boolean
	 */
	public static Boolean isTime(String time) {
		if (time == null) {
			return false;
		} else {
			return PATTERN_TIME.matcher(time).matches();
		}
	}

	/**
	 * 判断身份证号码是否合法
	 * 
	 * @param card
	 *            身份证号码,15或18位
	 * @return Boolean
	 */
	public static Boolean isIdCard(String card) {
		if (card == null) {
			return false;
		}
		int length = card.length();
		if (length == 15) {// 15位号码身份证
			if (!PATTERN_IDCARD_15.matcher(card).matches()) {
				return false;
			}
			if (!addressCode.containsKey(card.substring(0, 2))) {
				return false;
			}
			String birthday = (new StringBuilder("19")).append(
					card.substring(6, 8)).append("-").append(
					card.substring(8, 10)).append("-").append(
					card.substring(10, 12)).toString();
			if (!isDate(birthday)) {
				return false;
			}
		} else if (length == 18) {// 18位号码身份证
			if (!PATTERN_IDCARD_18.matcher(card).matches()) {// 格式不匹配
				return false;
			}
			if (!addressCode.containsKey(card.substring(0, 2))) {// 不符合地区码
				return false;
			}
			String birthday = (new StringBuilder(card.substring(6, 10)))
					.append("-").append(card.substring(10, 12)).append("-")
					.append(card.substring(12, 14)).toString();
			if (!isDate(birthday)) {
				return false;
			}
			int sum = 0;
			for (int i = 0; i < length - 1; i++) {
				sum += (card.charAt(i) - 48) * idCoefficient[i];
			}
			char mod = idMod[sum % 11];
			if (mod != Character.toLowerCase(card.charAt(17))) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 判断IP地址是否合法
	 * 
	 * @param ip
	 *            IP地址字符串
	 * @return Boolean
	 */
	public static Boolean isIP(String ip) {
		if (ip == null) {
			return false;
		} else {
			return PATTERN_IP.matcher(ip).matches();
		}
	}

	/**
	 * 判断是否包含重复字符
	 * 
	 * @param repeat
	 *            目标字符串
	 * @return Boolean
	 */
	public static Boolean hasRepeat(String repeat) {
		if (repeat == null) {
			return false;
		} else {
			return PATTERN_REPEAT.matcher(repeat).matches();
		}
	}

}
