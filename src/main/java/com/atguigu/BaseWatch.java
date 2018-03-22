package com.atguigu;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import lombok.Getter;
import lombok.Setter;

public class BaseWatch {

	/**
	 * 要连接的zookeeper地址+端口号
	 */
	protected String ip ; 
	
	/**
	 * 过时时间
	 */
	protected int session_timeerr; 
	
	/**
	 * 得到的连接
	 */
	@Setter @Getter
	protected static ZooKeeper zk;  
	
	/**
	 *  设置是否连续放置观察者默认为false
	 */
	@Setter @Getter
	protected boolean flag = false; 
	
	/**
	 * 没有修改之前的值
	 */
	protected String oldValue;
	
	/**
	 * 修改之后的值
	 */
	protected String newValue;
	
	/**
	 * 构造器
	 * @param ip 需要连接的zookeeper的ip地址  
	 * @param session_timeerr  设置失效时间
	 */
	protected BaseWatch(String ip , int session_timeerr){
		this.ip = ip;
		this.session_timeerr = session_timeerr;
	}
	
	/**
	 * 观察者
	 * @param path 节点路径
	 * @param flag 默认为false 不进行多次观察
	 * @throws Exception
	 */
	public void trigger(final String path , final boolean flag) throws Exception{
		String result = "";
		byte[] data = zk.getData(path, new Watcher() {
			
			public void process(WatchedEvent event) {
				if (flag) {
						try {
							trigger(path, flag);
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
			}
		}, new Stat());
		
		result = new String(data);
		newValue = result;
		
		if (!oldValue.equals(newValue)) {
			System.err.println("-----新任柳林县委书记是"+result);
			oldValue = newValue ;
		}
	}
	
	/**
	 * 获取节点的值
	 * @param path 节点路径
	 * @param flag  是否开始循环放置观察者
	 * @return 返回节点的值
	 * @throws Exception
	 */
	public String getNode(final String path, final boolean flag) throws Exception{
		final boolean newFlag = flag;
		String result = "";
		byte[] data = zk.getData(path, new Watcher() {
			
			public void process(WatchedEvent event) {
				try {
					trigger(path, newFlag);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, new Stat());
		result = new String(data);
		oldValue = result;
		return result;
	}
	
	/**
	 * 创建节点
	 * @param path 节点路径
	 * @param data 节点的值
	 * @throws Exception
	 */
	public void createNode(String path , String data) throws Exception{
		zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
	}

	/**
	 * 获取连接
	 * @return  返回一个zookeeper 对象
	 * @throws Exception
	 */
	public  ZooKeeper getCon() throws Exception{
		return new ZooKeeper(ip, session_timeerr, new Watcher() {
			public void process(WatchedEvent event) {}});
	}
}
