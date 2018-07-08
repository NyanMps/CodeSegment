package com.bfchengnuo.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Redis 缓存的使用类
 * 
 * @author Kerronex
 * @version 创建时间：2017年10月24日 下午9:26:34
 */
@Service
public class RedisService {

	@Autowired(required=false)  // 设置为不是必须的，这样即使没有配相关的 bean 也不会抛异常
	private ShardedJedisPool shardedJedisPool;

	/**
	 * 对重复的代码进行封装
	 * 
	 * @param fun
	 * @return
	 */
	private <T> T execute(Function<T, ShardedJedis> fun) {
		ShardedJedis shardedJedis = null;
		try {
			// 从连接池中获取到jedis分片对象
			shardedJedis = shardedJedisPool.getResource();
			// 从redis中获取数据后进行回调，这时输入已经确定
			return fun.callback(shardedJedis);
		} finally {
			if (null != shardedJedis) {
				// 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
				shardedJedis.close();
			}
		}
	}

	/**
	 * 保存字符串到数据库（set操作）
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value) {
		// 这里就确定了返回值
		return this.execute(new Function<String, ShardedJedis>() {
			@Override
			public String callback(ShardedJedis e) {
				return e.set(key, value);
			}
		});
	}
	
	
	/**
	 * 保存字符串到数据库（set操作），并且设置生存时间
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value, int seconds) {
		return this.execute(new Function<String, ShardedJedis>() {
			@Override
			public String callback(ShardedJedis e) {
				String result = e.set(key, value); 
				e.expire(key, seconds);
				return result;
			}
		});
	}

	/**
	 * 从数据库获取数据
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return this.execute(new Function<String, ShardedJedis>() {
			@Override
			public String callback(ShardedJedis e) {
				return e.get(key);
			}
		});
	}
	
	/**
	 * 删除一条（缓存）数据
	 * @param key
	 * @return
	 */
	public Long del(String key) {
		return this.execute(new Function<Long, ShardedJedis>() {
			@Override
			public Long callback(ShardedJedis e) {
				return e.del(key);
			}
		});
	}
	
	/**
	 * 设置生存时间, 单位秒
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long expire(String key, int seconds) {
		return this.execute(new Function<Long, ShardedJedis>() {
			@Override
			public Long callback(ShardedJedis e) {
				return e.expire(key, seconds);
			}
		});
	}
	

	/**
	 * 从数据库获取数据(未抽取)
	 * 
	 * @param key
	 * @return
	 */
//	public String get(String key) {
//		ShardedJedis shardedJedis = null;
//		try {
//			// 从连接池中获取到jedis分片对象
//			shardedJedis = shardedJedisPool.getResource();
//
//			// 从redis中获取数据
//			String value = shardedJedis.get("mytest");
//			System.out.println(value);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != shardedJedis) {
//				// 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
//				shardedJedis.close();
//			}
//		}
//	}
}
