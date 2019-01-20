
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
	private ArrayList<Keyword> keywordList = new ArrayList<>();

	public SearchResult(String inputSearch) {
		this.inputSearch = inputSearch;
		this.searchText += inputSearch;
		setKeywords();
	}

	private void setKeywords() {
		if (inputSearch.contains(" ")) {
			String[] temp = inputSearch.split(" ");
			for (String ele : temp) {
				keywordList.add(new Keyword(ele, 3));
			}
		} else {
			keywordList.add(new Keyword(inputSearch, 3));
		}
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
					legalURL.add(new URLobj(ele2, keywordList));
				}
			}
			// added
			int i = 0;
			Elements intros = duckduckgo.getElementsByAttributeValue("class", "st");
			for (Element ele : intros) {
				String intro = ele.toString();
//			    System.out.println(intro);
				intro = intro.substring(intro.indexOf(">") + 1, intro.indexOf("</span>"));
				while (intro.contains("<br>")) {
					int begin = intro.indexOf("<br>");
					intro = intro.substring(0, begin) + intro.substring(begin + 4);
				}
				legalURL.get(i).intro = intro;
				i++;
			}
			// end of add

			for (URLobj ele : legalURL) {
				ele.countSum();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		URLobj[] toSort = new URLobj[legalURL.size()];
		for (int i = 0; i < legalURL.size(); i++) {
			toSort[i] = legalURL.get(i);
		}
		sort(toSort);
		for (int i = 0; i < toSort.length; i++) {
			legalURL.remove(0);
			legalURL.add(toSort[i]);
		}
	}

	public void sort(URLobj arr[]) {
		int n = arr.length;

		// Build heap (rearrange array)
		for (int i = n / 2 - 1; i >= 0; i--)
			heapify(arr, n, i);

		// One by one extract an element from heap
		for (int i = n - 1; i >= 0; i--) {
			// Move current root to end
			URLobj temp = arr[0];
			arr[0] = arr[i];
			arr[i] = temp;

			// call max heapify on the reduced heap
			heapify(arr, i, 0);
		}
	}

	// To heapify a subtree rooted with node i which is
	// an index in arr[]. n is size of heap
	void heapify(URLobj arr[], int n, int i) {
		int largest = i; // Initialize largest as root
		int l = 2 * i + 1; // left = 2*i + 1
		int r = 2 * i + 2; // right = 2*i + 2

		// If left child is larger than root
		if (l < n && arr[l].sum < arr[largest].sum)
			largest = l;

		// If right child is larger than largest so far
		if (r < n && arr[r].sum < arr[largest].sum)
			largest = r;

		// If largest is not root
		if (largest != i) {
			URLobj swap = arr[i];
			arr[i] = arr[largest];
			arr[largest] = swap;

			// Recursively heapify the affected sub-tree
			heapify(arr, n, largest);
		}
	}

	//added
	public String[][] getTreeResult() {
		// 0: parent or child; 1: title; 2: url; 3: intro
		String[][] retVal = new String[legalURL.size() * 4][4];
		int i = 0;
		for (URLobj ele : legalURL) {
			retVal[i][0] = "parent";
			retVal[i][1] = ele.title;
			retVal[i][2] = ele.getURL();
			retVal[i][3] = ele.intro;
			int j = 1;
			if (ele.children != null) {
				for (childURL child : ele.children) {
					retVal[i + j][0] = "child";
					retVal[i + j][1] = child.title;
					retVal[i + j][2] = ele.getURL();
					retVal[i + j][3] = null;
					j++;
				}
			}
			i += j;
		}
		return retVal;
	}
}
