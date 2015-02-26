package com.miniprojet.droplock2.tasks;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;


/**
 * Helper to interact with DropLock API for MyGroups.
 *
 */
public class MyBoxesHelper {

	private static final String MyBoxesUrl =
			"https://secure.bluehost.com/~naturds2/droplock/v1/droplock_api/include/boxes.php";
	private static final int HTTP_STATUS_OK = 200;
	private static byte[] buff = new byte[1024];
	private static final String logTag = "MyBoxesHelper";

	public static class ApiException extends Exception {
		private static final long serialVersionUID = 1L;

		public ApiException (String msg)
		{
			super (msg);
		}

		public ApiException (String msg, Throwable thr)
		{
			super (msg, thr);
		}
	}	

	/**
	 * Retrieve the groups the user is a member of
	 * @param params userID string
	 * @return Array of json strings returned by the API.
	 */
	protected static synchronized String downloadFromServer (String... params)
			throws ApiException
			{
		String retval = null;
		//String idUser = params[0]; WILL BE USED TO INSER THE USER ID INTO THE URL


		String url = MyBoxesUrl; //ADD - + "&idUser=" + idUser; for user specific groups


		Log.d(logTag,"Retrieving " + url);

		// creates an http client and a request object.
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		try {

			// execute the request
			HttpResponse response = client.execute(request);
			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() != HTTP_STATUS_OK) {
				// handle error here
				throw new ApiException("Invalid response from DropLock" +
						status.toString());
			}

			// process the content.
			HttpEntity entity = response.getEntity();
			InputStream ist = entity.getContent();
			ByteArrayOutputStream content = new ByteArrayOutputStream();

			int readCount = 0;
			while ((readCount = ist.read(buff)) != -1) {
				content.write(buff, 0, readCount);
			}
			retval = new String (content.toByteArray());

		} catch (Exception e) {
			throw new ApiException("Problem connecting to the server " +
					e.getMessage(), e);
		}

		return retval;
			}
}