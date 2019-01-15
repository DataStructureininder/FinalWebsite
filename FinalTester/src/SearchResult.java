
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SearchResult {
	private String searchText = "菜單 ";
	private ArrayList<URLobj> legalURL = new ArrayList<>();
	private String inputSearch = "";

	public SearchResult(String inputSearch) {
		this.inputSearch = inputSearch;
		this.searchText += inputSearch;
	}

	public void setResultURL() {
		try {
			Document duckduckgo = Jsoup.connect("https://www.google.com/search?q=" + searchText)
					.userAgent("Mozilla/5.0").get();
			Elements searchResult = duckduckgo.getElementsByAttributeValue("class", "r");
			for (Element ele : searchResult) {
				String ele2 = ele.toString();
				if (ele2.contains("http")) {
					ele2 = ele2.substring(ele2.indexOf("http"), ele2.indexOf("&amp"));
					legalURL.add(new URLobj(ele2, inputSearch));
				}
			}
			   //added
			   int i = 0;
			   Elements intros = duckduckgo.getElementsByAttributeValue("class", "st");
			   for (Element ele : intros) {
			    String intro = ele.toString();
//			    System.out.println(intro);
			    intro=intro.substring(intro.indexOf(">")+1, intro.indexOf("</span>"));
			    while(intro.contains("<br>")) {
			     int begin = intro.indexOf("<br>");
			     intro = intro.substring(0,begin)+intro.substring(begin+4);
			    }
			    legalURL.get(i).intro = intro;
			    i++;
			   }
			   //end of add

			for (URLobj ele : legalURL) {
				ele.countSum();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		sortResult();
	}

	public void sortResult() {
		int n = legalURL.size();

		// Build heap (rearrange array)
		for (int i = n / 2 - 1; i >= 0; i--)
			heapify(legalURL, n, i);

		// One by one extract an element from heap
		for (int i = n - 1; i >= 0; i--) {
			// Move current root to end
			double temp = legalURL.get(0).sum;
			legalURL.get(0).sum = legalURL.get(i).sum;
			legalURL.get(i).sum = temp;

			// call max heapify on the reduced heap
			heapify(legalURL, i, 0);
		}
	}

	void heapify(ArrayList<URLobj> arr, int n, int i) {
		int largest = i; // Initialize largest as root
		int l = 2 * i + 1; // left = 2*i + 1
		int r = 2 * i + 2; // right = 2*i + 2

		// If left child is larger than root
		if (l < n && arr.get(l).sum < arr.get(largest).sum)
			largest = l;

		// If right child is larger than largest so far
		if (r < n && arr.get(r).sum < arr.get(largest).sum)
			largest = r;

		// If largest is not root
		if (largest != i) {
			double swap = arr.get(i).sum;
			arr.get(i).sum = arr.get(largest).sum;
			arr.get(largest).sum = swap;

			// Recursively heapify the affected sub-tree
			heapify(arr, n, largest);
		}
	}

	public ArrayList<URLobj> getArr(){
		for(int i = 0;i<legalURL.size();i++) {
			if(legalURL.get(i).title==null) {
				legalURL.remove(i);
			}
//			if(legalURL.get(i).getURL()==null) {
//				legalURL.remove(i);
//			}
		}
		return legalURL;
	}
	public String retResult() {
		String retVal = "";
		for (URLobj ele : legalURL) {
			retVal += (ele.title/* + ": " + ele.getURL()*/ + "\n");
//			retVal += ( "<html><a href='"+ele.getURL()+"'>"+ele.title+"</a></html>");

		}
		return retVal;
	}
}
