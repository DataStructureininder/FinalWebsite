import java.io.IOException;
import java.util.ArrayList;

public class childURL{

	private double keywordsum;
	private double imgcount;
	private double timecount;
	private String url;
	public double sum;
	private Counter counter;
	public String title;


	public childURL(String url, ArrayList<Keyword> keywordList) throws IOException {
		this.url = url;
		counter = new Counter(url, keywordList);
		title = counter.setTitle();
	}

	public String getURL() {
		return url;
	}
	


	public double countSum() throws IOException {
		imgcount = counter.countImg();
		keywordsum = counter.countKeyword();
		timecount = counter.countTime();
		return sum = (imgcount * 2 + keywordsum * 1.5 + timecount * 1) / 4.5;
	}

}
