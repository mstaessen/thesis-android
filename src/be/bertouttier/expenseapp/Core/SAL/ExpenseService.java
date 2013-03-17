package be.bertouttier.expenseapp.Core.SAL;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExpenseService {

	public ExpenseService() {
		// TODO Auto-generated constructor stub
	}
	
	public List<String> getProjectCodeSuggestion(String keyword)
	{
		byte[] bytes = null;
        String projectCodesJson = "";
		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://kulcapexpenseapp.appspot.com/resources/expenseService/getProjectCodeSuggestion");

		try {
			// Simulate network access.
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        nameValuePairs.add(new BasicNameValuePair("keyword", keyword));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
            	bytes = EntityUtils.toByteArray(response.getEntity());
                projectCodesJson = new String(bytes, "UTF-8");
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
			return null;
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    	return null;
	    }

		JSONObject jObject;
		JSONArray jArray;
		List<String> result = new ArrayList<String>();
		try {
			jObject = new JSONObject(projectCodesJson);
			jArray = jObject.getJSONArray("data");
			
			for(int i = 0; i < jArray.length(); i++){
				result.add(jArray.getString(i));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
//	public void saveExpense(String token, ExpenseForm form)
//	{
//		
//	}
	
	public String getExpenseFormPDF(String token, int expenseFormId, String fileName)
	{
		return null;
	}

//	public List<SavedExpenseForm> getExpenseForms(String token)
//	{
//		return null;
//	}

}
