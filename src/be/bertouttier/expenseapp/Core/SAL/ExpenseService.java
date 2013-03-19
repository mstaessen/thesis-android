package be.bertouttier.expenseapp.Core.SAL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import be.bertouttier.expenseapp.Core.DAL.ExpenseForm;
import be.bertouttier.expenseapp.Core.DAL.SavedExpenseForm;

import android.os.AsyncTask;
import android.os.Environment;

public class ExpenseService {
	private ExpenseServiceListener listener;
	
	public ExpenseService(ExpenseServiceListener listener) {
		this.listener = listener;
    }
	
	public List<String> getProjectCodeSuggestion(String keyword)
	{
		GetProjectCodeSuggestionTask getProjectCodeSuggestionTask = new GetProjectCodeSuggestionTask();
		getProjectCodeSuggestionTask.execute(keyword);
		try {
			return getProjectCodeSuggestionTask.get(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void saveExpense(String token, ExpenseForm form)
	{
		SaveExpenseTask saveExpenseTask = new SaveExpenseTask();
		saveExpenseTask.execute(token, form.toJson());
	}
	
	public String getExpenseFormPDF(String token, int expenseFormId)
	{
		GetExpenseFormPDFTask getExpenseFormPDFTask = new GetExpenseFormPDFTask();
		getExpenseFormPDFTask.execute(token, String.valueOf(expenseFormId));
		return null;
	}

	public List<SavedExpenseForm> getExpenseForms(String token)
	{
		GetExpenseFormsTask getExpenseFormsTask = new GetExpenseFormsTask();
		getExpenseFormsTask.execute(token);
		return null;
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	private class GetProjectCodeSuggestionTask extends AsyncTask<String, Void, List<String>> {
		@Override
		protected List<String> doInBackground(String... params) {
			List<String> returnResult = new ArrayList<String>();
			byte[] result = null;
	        String json = "";
			
			String keyword = params[0];
			
			// TODO: attempt authentication against a network service.
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
	                result = EntityUtils.toByteArray(response.getEntity());
	                json = new String(result, "UTF-8");
	                
	                if (json != null && json.length() > 0) {
	    				JSONObject jObject;
	    				JSONArray jArray;
	    				
	    				try {
	    					jObject = new JSONObject(json);
	    					jArray = jObject.getJSONArray("data");
	    					
	    					for(int i = 0; i < jArray.length(); i++){
	    						returnResult.add(jArray.getString(i));
	    					}
	    				} catch (JSONException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}
	    				listener.onGetProjectCodeSuggestionCompleted(returnResult);
	    			} else {
	    				// throw some error?
	    			}
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

			// TODO: register the new account here.
			return returnResult;
		}

		@Override
		protected void onPostExecute(final List<String> projectCodes) {			
			if (projectCodes != null) {
				// Nothing
			} else {
				// throw some error?
			}
		}

		@Override
		protected void onCancelled() {
			// Throw some error?
		}
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	private class SaveExpenseTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			byte[] result = null;
	        String answer = "";
	        
	        String token = params[0];
			String json = params[1];

			// TODO: attempt authentication against a network service.
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://kulcapexpenseapp.appspot.com/resources/expenseService/saveExpense");

			try {
				JSONObject jObject = new JSONObject();
				jObject.put("token", token);
				jObject.put("expenseForm", json);
				String payload = jObject.toString();
				
				//passes the results to a string builder/entity
			    StringEntity se = new StringEntity(payload);

			    //sets the post request as the resulting string
			    httppost.setEntity(se);
			    
			    //sets a request header so the page receving the request
			    //will know what to do with it
			    httppost.setHeader("Accept", "application/json");
			    httppost.setHeader("Content-type", "application/json");

		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
	            StatusLine statusLine = response.getStatusLine();
	            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
	                result = EntityUtils.toByteArray(response.getEntity());
	                answer = new String(result, "UTF-8");
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
		    } catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// TODO: register the new account here.
			return answer;
		}

		@Override
		protected void onPostExecute(final String token) {
			if (token != null && token.length() > 0) {
//				finish();
				listener.onSaveExpenseCompleted();
			} else {
				// throw some error?
			}
		}

		@Override
		protected void onCancelled() {
			// Throw some error?
		}
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	private class GetExpenseFormsTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			byte[] result = null;
	        String json = "";
			
			String token = params[0];
			
			// TODO: attempt authentication against a network service.
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://kulcapexpenseapp.appspot.com/resources/expenseService/getExpenseForms");

			try {
				// Simulate network access.
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		        nameValuePairs.add(new BasicNameValuePair("token", token));
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
	            StatusLine statusLine = response.getStatusLine();
	            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
	                result = EntityUtils.toByteArray(response.getEntity());
	                json = new String(result, "UTF-8");
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

			// TODO: register the new account here.
			return token;
		}

		@Override
		protected void onPostExecute(final String json) {
			if (json != null && json.length() > 0) {
//				finish();
				
				Type collectionType = new TypeToken<Collection<SavedExpenseForm>>(){}.getType();
				Collection<SavedExpenseForm> expenseForms = new Gson().fromJson(json, collectionType);
				listener.onGetExpenseFormsCompleted(expenseForms);
			} else {
				// throw some error?
			}
		}

		@Override
		protected void onCancelled() {
			// Throw some error?
		}
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	private class GetExpenseFormPDFTask extends AsyncTask<String, Void, File> {
		@Override
		protected File doInBackground(String... params) {
			byte[] result = null;
	        File pdf = null;
			
			String token = params[0];
			String expenseFormId = params[1];
			
			// TODO: attempt authentication against a network service.
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://kulcapexpenseapp.appspot.com/resources/expenseService/getExpenseFormPDF");

			try {
				// Simulate network access.
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("token", token));
		        nameValuePairs.add(new BasicNameValuePair("expenseFormId", expenseFormId));
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
	            StatusLine statusLine = response.getStatusLine();
	            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
	                result = EntityUtils.toByteArray(response.getEntity());
	                
	                File dir = Environment.getExternalStorageDirectory();
	                pdf = new File(dir, "expenseForm_" + expenseFormId + ".pdf");
	                OutputStream op = new FileOutputStream(pdf);
	                op.write(result);
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

			// TODO: register the new account here.
			return pdf;
		}

		@Override
		protected void onPostExecute(final File pdfFile) {
			if (pdfFile != null) {
//				finish();
				listener.onGetExpenseFormPDFCompleted(pdfFile);
			} else {
				// throw some error?
			}
		}

		@Override
		protected void onCancelled() {
			// Throw some error?
		}
	}

}
