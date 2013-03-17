package be.bertouttier.expenseapp.Core.SAL;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import be.bertouttier.expenseapp.Core.DAL.Employee;
import android.os.AsyncTask;

public class UserService {
	private UserServiceListener listener;
	
	public UserService(UserServiceListener listener) {
		this.listener = listener;
    }
	
	public String login(String email, String password) {
		LoginTask loginTask = new LoginTask();
		loginTask.execute(email, password);
		return null;
	} 
	
	public Employee getEmployee(String token) {
		GetEmployeeTask getEmployeeTask = new GetEmployeeTask();
		getEmployeeTask.execute(token);
		return null;
	} 
	
	public void logout(String token) {
		LogoutTask logoutTask = new LogoutTask();
		logoutTask.execute(token);
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	private class LoginTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			byte[] result = null;
	        String token = "";
			
			String email = params[0];
			String password = params[1];
			
			// TODO: attempt authentication against a network service.
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://kulcapexpenseapp.appspot.com/resources/userService/login");

			try {
				// Simulate network access.
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("email", email));
		        nameValuePairs.add(new BasicNameValuePair("password", password));
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
	            StatusLine statusLine = response.getStatusLine();
	            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
	                result = EntityUtils.toByteArray(response.getEntity());
	                token = new String(result, "UTF-8");
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
		protected void onPostExecute(final String token) {
			if (token != null && token.length() > 0) {
//				finish();
				listener.onLoginCompleted(token);
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
	private class GetEmployeeTask extends AsyncTask<String, Void, String[]> {
		@Override
		protected String[] doInBackground(String... params) {
			byte[] result = null;
			String employee = null;
			
			String token = params[0];
			
			// TODO: attempt authentication against a network service.
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://kulcapexpenseapp.appspot.com/resources/userService/getEmployee");

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
	                employee = new String(result, "UTF-8");
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
			return new String[] {token, employee};
		}

		@Override
		protected void onPostExecute(final String[] params) {
			String token = params[0];
			String userJson = params[1];
			
			if (userJson != null && userJson.length() > 0) {
//				finish();
				
				Employee user = new Employee();
				JSONObject jObject;
				try {
					jObject = new JSONObject(userJson);
					
					user.setId(jObject.getInt("id"));
					user.setEmail(jObject.getString("email"));
					user.setEmployeeNumber(jObject.getInt("employeeNumber"));
					user.setFirstName(jObject.getString("firstName"));
					user.setLastName(jObject.getString("lastName"));
					user.setPassword(jObject.getString("password"));
					user.setUnitId(jObject.getInt("unitId"));
					user.setToken(token);
					
					listener.onGetEmployeeCompleted(user);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			} else {
				//Throw some error?
			}
		}

		@Override
		protected void onCancelled() {
//			mAuthTask = null;
//			showProgress(false);
		}
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	private class LogoutTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			
			String token = params[0];
			
			// TODO: attempt authentication against a network service.
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://kulcapexpenseapp.appspot.com/resources/userService/logout");

			try {
				// Simulate network access.
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		        nameValuePairs.add(new BasicNameValuePair("token", token));
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
	            StatusLine statusLine = response.getStatusLine();
	            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
	            	//Alles ok
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
		protected void onPostExecute(final String token) {
			if (token != null && token.length() > 0) {
//				finish();
				listener.onLogoutCompleted();
			} else {
				// Throw some error?
			}
		}

		@Override
		protected void onCancelled() {
			// Throw some error?
		}
	}
}
