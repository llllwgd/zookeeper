package com.atguigu;

public class SonOfWatch extends BaseWatch {
	
	/**
	 * 构造器
	 * @param ip 需要连接的zookeeper的ip地址
	 * @param session_timeout 失效时间
	 */
	protected SonOfWatch(String ip, int session_timeout) {
		super(ip, session_timeout);
	}

	/**
	 * 开始观察
	 * @param path 节点路径
	 * @param flag 默认为false 不进行多次观察
	 * @param data 节点值
	 * @throws Exception
	 */
	public void startWatch(String path, boolean flag, String data) throws Exception {
		
		if (getCon() == null) {
			System.err.println("获取连接失败");
		}else {
			setZk(getCon());
		}
		
		if (zk.exists(path, null) == null) {
			createNode(path, data);
		} else {
			System.err.println("现任柳林县委书记是"+getNode(path, flag));
		}
		getNode(path, flag);
		Thread.sleep(Integer.MAX_VALUE);
	}
}
