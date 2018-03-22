package com.atguigu;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;



/**
 * 1.通过java程序与zk连接,类似jdbc的connection
 * 2.新建一个节点
 * 3.获取当前节点的值
 * 4.关闭连接
 * @author llllwgd
 *
 */
public class HelloWorld {
	
	private static final String CONNECTSTRING="192.168.6.128:2181"; //连接地址
	private static final int SESSION_TIMEOUT = 20*1000; //连接超时时间
	private static final String PATH = "/atguigu"; //设置节点路径
	private static  final Logger logger = Logger.getLogger(HelloWorld.class); 
	
	/**
	 * 测试
	 * @param args
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
		logger.debug("我是debug");
		logger.info("我是info");
		logger.warn("我是warn");
		logger.error("我是error");
		
		HelloWorld helloWorld = new HelloWorld();
		ZooKeeper zk = helloWorld.getCon();
		String data = "王国栋";
		if (zk.exists(PATH, false) == null) {
			helloWorld.creatNode(zk, PATH, data);
		}
		System.out.println(helloWorld.getNode(zk, PATH));
		zk.close();
	}
	
	/**
	 * 关闭连接
	 * @param zk
	 * @throws InterruptedException
	 */
	public void stopZk(ZooKeeper zk) throws InterruptedException{
		if (zk != null) {
			zk.close();
		}
	}
	
	/**
	 * 获得节点 
	 * @param zK 连接
	 * @param PATH 节点路径
	 * @return
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public String getNode(ZooKeeper zK , String PATH) throws KeeperException, InterruptedException{
		String result = "";
		byte[] byteArray = zK.getData(PATH, false, new Stat());
		result = new String(byteArray);
		return result;
	}
	
	
	/**
	 * 新建一个节点
	 * @param zk 连接
	 * @param PATH 路径
	 * @param data 值
	 * @throws KeeperException
	 * @throws InterruptedException 中断异常
	 */
	public void creatNode(ZooKeeper zk , String path , String data) throws KeeperException, InterruptedException{
		
		zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}
	
	/**
	 * @return 获取连接
	 * @throws IOException
	 */
	public ZooKeeper getCon() throws IOException{
		return new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher(){

			public void process(WatchedEvent event) {
				
			}
			
		});
	}
}
