
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class URLobj {

	private double keywordsum;
	private double imgcount;
	private double timecount;
	private String url;
	public double sum;
	private Counter counter;
	public String title;
	public String intro;
	public childURL[] children =new childURL[3];	// added

	public URLobj(String url, ArrayList<Keyword> keywordList) throws IOException {
		this.url = url;
		counter = new Counter(url, keywordList);
		title = counter.setTitle();
		setChildren(keywordList);	//added
	}

	public String getURL() {
		return url;
	}

	// changed
	public void countSum() throws IOException {
		imgcount = counter.countImg();
		keywordsum = counter.countKeyword();
		timecount = counter.countTime();
		sum = (imgcount * 2 + keywordsum * 1.5 + timecount * 1) / 4.5;
		if (children == null) {
			return;
		} else {
			for (childURL ele : children) {
				sum += ele.countSum();
			}
		}

	}

	//getChild
	private void setChildren(ArrayList<Keyword> keywordList) throws IOException {
		Document duck = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
		ArrayList<String> result = new ArrayList<>();
		if (url.contains("pixnet")) {
			Element recent = duck.getElementById("recent-article");
			Elements urlLi = recent.getElementsByTag("a");
			for (Element ele : urlLi) {
				String curl = ele.toString();
				String userName = url.substring(0, url.indexOf(".net") + 4);
				curl = curl.substring(curl.indexOf("/"), curl.indexOf("\"", curl.indexOf("/")));
				result.add(userName + curl);
			}
		}else if (url.contains("xuite")) {
			Element recent = duck.getElementById("sliceItemArticleList");
			Elements urlLi = recent.getElementsByTag("a");
			for (Element ele : urlLi) {
				String curl = ele.toString();
				curl = curl.substring(curl.indexOf("/"), curl.indexOf("\"", curl.indexOf("/")));
				result.add("https://blog.xuite.net" + curl);
			}

		}else {
			result=null;
		}
		
		if(result!=null) {
			for (int i =0 ; i<3;i++) {
//				children.add(new childURL(ele, keywordList));
				children[i]=new childURL(result.get(i), keywordList);
			}	
		}else {
			children = null;
		}
		
	}
}
