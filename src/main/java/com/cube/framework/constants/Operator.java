package com.cube.framework.constants;

public enum Operator {
	/**
	 * 等于，=
	 */
	eq,
	/**
	 * 不等于，<>
	 */
	notEq,
	/**
	 * 包含，like '%value%'
	 */
	like,
	/**
	 * 以value结尾，like '%value'
	 */
	likeL,
	/**
	 * 以value开头，like 'value%'
	 */
	likeR,
	/**
	 * 不包含，not like '%value%'
	 */
	notLike,
	/**
	 * 不以value结尾，not like '%value'
	 */
	notLikeL,
	/**
	 * 不以value开头，not like 'value%'
	 */
	notLikeR,
	/**
	 * 在列表中，in (value)
	 */
	in,
	/**
	 * 不在列表中，not in (value)
	 */
	notIn,
	/**
	 * 大于，>
	 */
	gt,
	/**
	 * 小于，<
	 */
	lt,
	/**
	 * 大于等于，>=
	 */
	gte,
	/**
	 * 小于等于，<=
	 */
	lte;
}