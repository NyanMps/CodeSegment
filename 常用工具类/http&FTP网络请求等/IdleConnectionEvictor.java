package com.bfchengnuo.common.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;

/*
 * 简单的一个“定时任务”，每隔八秒清理 httpclient 的无效连接
 */
public class IdleConnectionEvictor extends Thread {

	private final HttpClientConnectionManager connMgr;

	private volatile boolean shutdown;

	public IdleConnectionEvictor(HttpClientConnectionManager connMgr) {
		this.connMgr = connMgr;
		this.start(); // 自启动
	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(8000);
					// 关闭失效的连接
					connMgr.closeExpiredConnections();
				}
			}
		} catch (InterruptedException ex) {
			// 结束
		}
	}

	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}