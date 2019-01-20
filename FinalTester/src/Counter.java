import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Counter {
    private String urlStr = "";
    private ArrayList<Keyword> keywords;
    private String content;
    private Document duck;

    public Counter(String url, ArrayList<Keyword> keywordList) {
        this.urlStr = url;
        this.keywords = keywordList;
        try {
			this.duck = Jsoup.connect(urlStr).userAgent("Mozilla/5.0").get();
		} catch (IOException e) {
			System.out.println("url not available");
		}
    }
    
	public String setTitle() {
		if (duck != null) {
			Elements ti = duck.getElementsByTag("title");
			String title = ti.get(0).toString();
			title = title.substring(title.indexOf(">") + 1, title.indexOf("</"));
			if(title.length()>30) {
				title = title.substring(0,30)+"...";
			}
			
			return title;
		}else {
			return null;
		}
		
	}

    public String fetchContent() throws IOException {
//        Document duck = 
        String retVal = duck.toString();
        return retVal;
    }

	public double countImg() throws IOException {
		// Document duck = Jsoup.connect(urlStr).get();
		if (duck != null) {
			Elements imgsEle = duck.getElementsByTag("img");
			return imgsEle.size();
		}
		return 0;
	}
	
	private double countSingle(String kword) {
		if (duck != null) {
			content = duck.toString();

			content = content.toUpperCase();
			kword = kword.toUpperCase();

			// to do : indexOf(keyword)
			int reVal = 0;
			int fromIndex = 0;
			int found = -1;

			while ((found = content.indexOf(kword, fromIndex)) != -1) {
				reVal++;
				fromIndex = found + kword.length();
			}

			return (double) reVal;
		}
		return 0;
	}

	public double countKeyword() throws IOException {
		double retVal = 0;
		for (Keyword ele : keywords) {
			ele.score *= countSingle(ele.keyword);
			retVal+=ele.score;
		}
		return retVal;
	}

    public double countTime() throws IOException {
//        Document duck = Jsoup.connect(urlStr).get();
        double retVal;
        String datestring="";
        ArrayList<String> dateArr = new ArrayList<>();
        if (urlStr.contains("walkerland")) {
            Elements date = duck.getElementsByClass("date");
            datestring = date.get(0).toString();
            datestring = datestring.substring(datestring.indexOf("dfn", datestring.indexOf("dfn") + 3));
            datestring = datestring.substring(datestring.indexOf("2") + 2, datestring.indexOf("/"))
                    + datestring.substring(datestring.indexOf("/") + 1, datestring.indexOf("/") + 3)
                    + datestring.substring(datestring.indexOf("/", datestring.indexOf("/")) + 1,
                    datestring.indexOf("/", datestring.indexOf("/")) + 3);
        } else if (urlStr.contains("pixnet")) {
            String year = duck.getElementsByClass("year").get(0).toString();
            dateArr.add(year.substring(year.indexOf(">")+3, year.indexOf(">")+5));
            String month = duck.getElementsByClass("month").get(0).toString();
            month = month.substring(month.indexOf(">")+1, month.indexOf(">")+4);
            switch (month) {
                case "Jan":	month = "01";	break;	case "Feb":	month = "02";	break;	case "Mar":	month = "03";	break;
                case "Apr":	month = "04";	break;	case "May":	month = "05";	break;	case "Jun":	month = "06";	break;
                case "Jul":	month = "07";	break;	case "Aug":	month = "08";	break;	case "Sep":	month = "09";	break;
                case "Oct":	month = "10";	break;	case "Nov":	month = "11";	break;	case "Dec":	month = "12";	break;
                default:		break;
            }
            dateArr.add(month);
            String date2 = duck.getElementsByClass("date").get(0).toString();
            date2 = date2.substring(date2.indexOf(">")+1, date2.indexOf(">")+3);
            dateArr.add(date2);

            datestring = dateArr.get(0)+dateArr.get(1)+dateArr.get(2);
        }else if(urlStr.contains("xuite")) {
            String year = duck.getElementsByClass("titledate-year").get(0).toString();
            dateArr.add(year.substring(year.indexOf(">")+3, year.indexOf(">")+5));
            String month = duck.getElementsByClass("titledate-month").get(0).toString();
            month = month.substring(month.indexOf(">")+1, month.indexOf(">")+3);
            dateArr.add(month);
            String date2 = duck.getElementsByClass("titledate-day").get(0).toString();
            date2 = date2.substring(date2.indexOf(">")+1, date2.indexOf(">")+3);
            dateArr.add(date2);

            datestring = dateArr.get(0)+dateArr.get(1)+dateArr.get(2);
        }else if (urlStr.contains("ptt")) {
            Elements elements = duck.getElementsByClass("article-meta-value");
            datestring = elements.get(3).toString();
            String year = datestring.substring(datestring.indexOf(">")+23,datestring.indexOf(">")+25);
            String month = datestring.substring(datestring.indexOf(">")+5,datestring.indexOf(">")+8);
            switch (month) {
                case "Jan":	month = "01";	break;	case "Feb":	month = "02";	break;	case "Mar":	month = "03";	break;
                case "Apr":	month = "04";	break;	case "May":	month = "05";	break;	case "Jun":	month = "06";	break;
                case "Jul":	month = "07";	break;	case "Aug":	month = "08";	break;	case "Sep":	month = "09";	break;
                case "Oct":	month = "10";	break;	case "Nov":	month = "11";	break;	case "Dec":	month = "12";	break;
                default:		break;
            }
            String date3 = datestring.substring(datestring.indexOf(">")+9,datestring.indexOf(">")+11);
            datestring = year+month+date3;
        }else {
            return 0;
        }

        retVal = Double.parseDouble(datestring) / 10000;
        return retVal;
    }
}
