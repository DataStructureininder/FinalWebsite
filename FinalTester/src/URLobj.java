import java.io.IOException;

public class URLobj {

	private double keywordcount;
	private double imgcount;
	private double timecount;
	private String url;
	public double sum;
	private Counter counter;
	public String title;
	public String intro;

	public URLobj(String url, String keyword) throws IOException {
		this.url = url;
		counter = new Counter(url, keyword);
		title = counter.setTitle();
	}

	public String getURL() {
		return url;
	}

	public void countSum() throws IOException {
		imgcount = counter.countImg();
		keywordcount = counter.countKeyword();
		timecount = counter.countTime();
		sum = (imgcount * 2 + keywordcount * 1.5 + timecount * 1) / 4.5;
	}

}
