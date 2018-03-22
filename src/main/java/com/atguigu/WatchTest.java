package com.atguigu;

public class WatchTest {

	public static void main(String[] args) throws Exception {
		SonOfWatch sonOfWatch = new SonOfWatch("192.168.6.128:2181", 20*1000);
		sonOfWatch.startWatch("/柳林县委书记", false, "王宁");
	}
}
