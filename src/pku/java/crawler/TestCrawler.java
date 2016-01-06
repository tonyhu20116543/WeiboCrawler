package pku.java.crawler;

import java.util.HashMap;

public class TestCrawler {
	public static void main(String[] args) {
		// 填入用户名和密码登录新浪
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", "18655106031");
		map.put("password", "");
		
		// 爬虫的入口
		String startUrl = "http://weibo.com/huangbo";
		// 保存爬取数据的文件路径
		String filepath = "/Users/tony/Desktop/weibo.json";
		// 爬虫爬微博的深度
		Integer crawlerDepth = 6;
		
		Crawler crawler = new Crawler();
		crawler.setMap(map);
		crawler.setStartUrl(startUrl);
		crawler.setDepth(crawlerDepth);
		crawler.setFilepath(filepath);
		crawler.start();
		
	}
}
