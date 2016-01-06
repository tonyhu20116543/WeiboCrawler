package pku.java.crawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import pku.java.bean.User;
import pku.java.bean.Weibo;
import pku.java.util.ConvertToJson;
import pku.java.util.LoginSina;

public class Crawler {
	
	// 存放用户名和密码的键值对
	private HashMap<String, String> map = new HashMap<String, String>();
	
	// 爬虫启动的具体时间
	private String startDate;
	
	// 爬虫入口URL
	private String startUrl;
	
	// 爬虫的深度
	private Integer depth;
	
	// 存放爬虫待访问的队列
	private Queue<String> queue = new LinkedList<String>();
	
	// 存放爬虫已经访问过的URL
	private HashSet<String> visitedUrlSet = new HashSet<String>();
	
	// 保存爬取数据的文件路径
	private String filepath;
	
	// 用户粉丝也关注的其他用户 xpath 
	private String moreXpath = 
			"//div[contains(@id,'Pl_Core_Ut1UserList')]/div/div/a[1]";
	// 微博列表 xpath
	private String weiboListXpath = 
			"//div[contains(@id,'Pl_Official_MyProfileFeed')]/div/div[2]";
	// 与微博用户相似的用户列表 xpath
	private String similarListXpath = 
			"//ul[@class='follow_list']";
	// 用户的关注，粉丝，微博条数
	private String baseInfoXpath = 
			"//div[contains(@id,'Pl_Core_T8CustomTriColumn')]";
	// 用户的详细信息
	private String moreInfoXpath =
			"//div[contains(@id, 'Pl_Core_UserInfo')]";
	
	// 滚动条没有移动底部进行Ajax异步加载时，页面显示的最大微博条数
	private final static int WEIBO_COUNT = 16;
	
	// 带爬用户粉丝量的最小值
	private final static int MIN_FANS_COUNT = 1000000;
	
	public Crawler() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		startDate = df.format(date);
	}
	
	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}
	
	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}
	
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public void start() {
		// 设置用户名和密码登录
		LoginSina.setUsername(map.get("username"));
		LoginSina.setPassword(map.get("password"));
		WebDriver webDriver = LoginSina.login();
		queue.offer(startUrl);
		
		// 当前爬虫的深度
		int currentDepth = 1;
		while (!queue.isEmpty()) {
			String currentUrl = queue.poll();
			webDriver.get(currentUrl);
			visitedUrlSet.add(currentUrl);
			
			User user = getUserInfo(webDriver);
			List<Weibo> list = getWeibos(webDriver);
			user.setWeibos(list);
			ConvertToJson.saveObjectToJSON(user, filepath);
			WebElement moreBtn = webDriver.findElement(By.xpath(moreXpath));
			String moreUrl = moreBtn.getAttribute("href");
			webDriver.get(moreUrl);
			WebElement listElment = webDriver.findElement(By.xpath(similarListXpath));
			
			String getLiCountJs = 
					"return document.getElementsByClassName(\"follow_list\")[0]."
					+ "getElementsByTagName(\"ul\").length";
			JavascriptExecutor executor = (JavascriptExecutor) webDriver;
			int listSize = ((Long) (executor.executeScript(getLiCountJs))).intValue();
			System.out.println("相似用户个数：" + listSize);
			for (int i = 1; i <= listSize; i++) {
				WebElement fansElement = listElment.findElement(
						By.xpath("./li[" + i + "]/dl/dd/div[2]/span[2]/em"));
				int numOfFans = Integer.parseInt(fansElement.getText());
				// 如果粉丝量小于阀值的用户不爬
				if (numOfFans < MIN_FANS_COUNT) {
					continue;
				}
				System.out.println(listElment.findElement(
						By.xpath("./li[" + i + "]/dl/dd/div[1]/a")).getText());
				System.out.println("相似人物：" + fansElement.getText());
				
				WebElement userElement = listElment.findElement(
						By.xpath("./li[" + i + "]/dl/dd/div/a"));
				String url = userElement.getAttribute("href");
				if (!visitedUrlSet.contains(url)) {
					System.out.println(url);
					queue.offer(url);
				}
			}
		}
	}
	
	/**
	 * 从网页上爬取用户的信息
	 * @param webDriver
	 * @return 用户对象
	 */
	public User getUserInfo(WebDriver webDriver) {
		WebElement username = webDriver.findElement(
				By.xpath("//div[@class='pf_username']/h1"));
		System.out.println(username.getText());
		WebElement intro = webDriver.findElement(
				By.xpath("//div[@class='pf_intro']"));
//		System.out.println(intro.getText());
		
		WebElement followingElement = webDriver.findElement(
				By.xpath(baseInfoXpath + "//table/tbody/tr/td[1]//strong"));
		WebElement followersElement = webDriver.findElement(
				By.xpath(baseInfoXpath + "//table/tbody/tr/td[2]//strong"));
		WebElement weiboNumElement = webDriver.findElement(
				By.xpath(baseInfoXpath + "//table/tbody/tr/td[3]//strong"));
		
		WebElement moreInfoElement = webDriver.findElement(
				By.xpath(moreInfoXpath + "//div[@class='detail']/ul"));
		WebElement location = moreInfoElement.findElement(By.xpath("./li[1]/span[2]"));
//		System.out.println(location.getText());
//		WebElement profile = infoElement.findElement(By.xpath("./li[2]/span[2]"));
////		System.out.println(profile.getText());
//		WebElement domain = infoElement.findElement(By.xpath("./li[3]/span[2]"));
////		System.out.println(domain.getText());
//		WebElement blog = infoElement.findElement(By.xpath("./li[4]/span[2]"));
////		System.out.println(blog.getText());
//		WebElement tag = infoElement.findElement(By.xpath("./li[5]/span[2]"));
////		System.out.println(tag.getText());
		
		User user = new User();
		user.setUsername(username.getText());
		user.setIntro(intro.getText());
		user.setFollowing(Integer.parseInt(followingElement.getText()));
		user.setFollowers(Integer.parseInt(followersElement.getText()));
		user.setWeiboNum(Integer.parseInt(weiboNumElement.getText()));
		user.setLocation(location.getText());
//		user.setProfile(profile.getText());
//		user.setDomain(domain.getText());
//		user.setBlog(blog.getText());
//		user.setTag(tag.getText());
		
		return user;
	}
	
	/**
	 * 从网页上爬取用户近30天内所发的微博
	 * @param webDriver - webDriver
	 * @return 近30天发的所有微博
	 */
	public List<Weibo> getWeibos(WebDriver webDriver) {
		List<Weibo> list = new ArrayList<Weibo>();
		
		// 获取采用异步加载的微博
		for (int i = 1; i < 3; i++) {
			loadMoreWeibo(webDriver, i);
		}
		
		int count = 1;
		while (true) {
			String xpath = weiboListXpath + "/div[" + count + "]";
			WebElement details = webDriver.findElement(
					By.xpath(xpath +"/div[1]/div[last()]"));
			// 获取微博的日期，如果微博发布的时间在一个月以前，则跳出循环
			WebElement dateElement = details.findElement(
					By.xpath("./div[last()]/a[1]"));
			String postDate = dateElement.getAttribute("title");
			int days = 0;
			if (!postDate.equals("")) {
				days = intervalDays(startDate, postDate);
			}
			if (days > 31) {
				break;
			}
			
			Weibo weibo = crawlWeibo(webDriver, details, xpath);
			list.add(weibo);
			count++;
		}
		
		return list;
	}
	
	public void loadMoreWeibo(WebDriver webDriver, int count) {
		String js = "window.scrollTo(0,document.body.scrollHeight)";
		((JavascriptExecutor) webDriver).executeScript(js);
		
		new WebDriverWait(webDriver, 10).until(new ExpectedCondition<Boolean>() {  
			@Override  
			public Boolean apply(WebDriver driver) {  
				Boolean result = false;  
				try {
					int index = (WEIBO_COUNT + 1) * count;
					//查找预期的空间是否生成
					driver.findElements(By.xpath(
							weiboListXpath + "/div[" + index + "]"));
					result = true;  
				} catch(Exception e){
					
				}  
				return result;  
			}  
		});
	}
	
	/**
	 * 在网页上爬取一条微博
	 * @param webDriver
	 * @param details
	 * @param xpath
	 * @return 微博对象
	 */
	public Weibo crawlWeibo(WebDriver webDriver, WebElement details, String xpath) {
		// 获取微博的内容
		WebElement contentElement = details.findElement(By.xpath("./div[1]"));
		System.out.println(contentElement.getText());
		
		// 获取微博的日期
		WebElement dateElement = details.findElement(By.xpath("./div[last()]/a[1]"));
		System.out.println(dateElement.getAttribute("title"));
		String postDate = dateElement.getAttribute("title");
		
		// 获取设备信息
		WebElement deviceElement = details.findElement(By.xpath("./div[last()]/a[2]"));
		System.out.println(deviceElement.getText());
		
		// 获取转发次数，评论条数，点赞的次数
		WebElement handle = webDriver.findElement(By.xpath(xpath + "/div[2]/div/ul"));
		WebElement shareElement = handle.findElement(By.xpath("./li[2]"));
		System.out.println(shareElement.getText());
		WebElement commentElement = handle.findElement(By.xpath("./li[3]"));
		System.out.println(commentElement.getText());
		WebElement likeElement = handle.findElement(By.xpath("./li[4]"));
		System.out.println(likeElement.getText());
		
		Weibo weibo = new Weibo();
		weibo.setContent(contentElement.getText());
		weibo.setPostDate(postDate);
		weibo.setDevice(deviceElement.getText());
		int shareNum = Integer.parseInt(
				shareElement.getText().split("\\s+")[1]);
		weibo.setShareNum(shareNum);
		int commentNum = Integer.parseInt(
				commentElement.getText().split("\\s+")[1]);
		weibo.setCommentNum(commentNum);
		int like = Integer.parseInt(likeElement.getText().split("\\s+")[0]);
		weibo.setLike(like);
		
		return weibo;
	}
	
	/**
	 * 计算微博发布与启动爬虫的天数
	 * @param beginDate - 启动爬虫的日期
	 * @param postDate - 发该条微博的日期
	 * @return 两个日期之间的天数
	 */
	public Integer intervalDays(String beginDate, String postDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date toDate = df.parse(beginDate);
			Date fromDate = df.parse(postDate);
			long days = (toDate.getTime() - fromDate.getTime()) /
					(1000 * 3600 * 24);
			return Integer.parseInt(days + "");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public boolean hasNextElement(WebDriver driver, String xpath) {
		boolean flag = false;
		try {
			driver.findElement(By.xpath(xpath));
			flag = true;
			return flag;
		} catch (Exception e) {
			
		}
		
		return flag;
	}
}
