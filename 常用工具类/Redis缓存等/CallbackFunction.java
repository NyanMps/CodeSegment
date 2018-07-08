package com.bfchengnuo.common.service;

/**
 * 用来抽取 Redis 的操作代码，采用了 js 中的回调思想
 * @author Kerronex
 * @version 创建时间：2017年10月24日 下午9:46:22
 */
public interface Function<T, E> {
	// 简单理解为 T 为输出；E 为输入
	public T callback(E e);
}
