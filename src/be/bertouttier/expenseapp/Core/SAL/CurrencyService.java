package be.bertouttier.expenseapp.Core.SAL;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

public class CurrencyService {
	private Date time;
	private Map<String, Float> currencyRates;

	public CurrencyService ()
	{
		// Always fetch when creating a new API instance
		this.fetchXml();
	}

	// Method for refreshing
	private void fetchXml()
	{
		currencyRates = new HashMap<String, Float>();

		HttpGet uri = new HttpGet("http://www.ecb.int/stats/eurofxref/eurofxref-daily.xml");    

		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse resp = null;
		try {
			resp = client.execute(uri);
			StatusLine status = resp.getStatusLine();
			if (status.getStatusCode() != 200) {
			    Log.d("!!", "HTTP error, invalid server status code: " + resp.getStatusLine());  
			}
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		

		try {	
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(resp.getEntity().getContent());
			
			NodeList nList = doc.getDocumentElement().getChildNodes().item(2).getChildNodes().item(0).getChildNodes();
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					currencyRates.put(eElement.getAttribute("currency"), Float.parseFloat(eElement.getAttribute("rate"))); 
				}
			}
	
	//		time = DateTime.ParseExact(xml.DocumentElement.ChildNodes[2].ChildNodes[0].Attributes["time"].Value, "yyyy-MM-dd", CultureInfo.InvariantCulture);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Set<String> getCurrencies ()
	{
		return currencyRates.keySet();
	}

	public int convertToEuro(Float amount, String currency)
	{
		Float rate = currencyRates.get(currency);
		return Math.round(amount / rate);
	}
}
