package com.miniprojet.droplock2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UnlockBoxActivity extends Activity implements BluetoothAdapter.LeScanCallback {
	
	private static final String TAG = "BLEScan"; 
	private static final String DEVICE_NAME_HM = "HMSoft";
	private static final String DEVICE_NAME_RBL = "BLE Shield";
	private BluetoothAdapter mBluetoothAdapter;
	private ArrayList<BluetoothDevice> mDevices;
	private ArrayList<String> devices_name;
	//private BluetoothGatt mConnectGatt;
	private Button scanButton, unlockButton, lockButton;
	private ListView bleDevicesListView;
	private ArrayAdapter<String> bleDevicesAdapter;
	private int first_time = 1;
	private BluetoothGatt mConnectGatt, buttonGatt = null;
	private ProgressDialog mProgress;
	BluetoothGattCharacteristic characteristicTx, characteristicRx;
	private static final String unlockKey = "sesameouvretoi";
	private static final UUID CHAT_SERVICE = UUID.fromString    ("713d0000-503e-4c75-ba94-3148f18d941e");
	private static final UUID CHAT_SERVICE_TX = UUID.fromString("713d0003-503e-4c75-ba94-3148f18d941e");
	private static final UUID CHAT_SERVICE_RX = UUID.fromString("713d0002-503e-4c75-ba94-3148f18d941e");
	public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
	
	private TextView  mPressure;
	
	/*
	//Bellow is for the HTTPGET
	private ProgressDialog pDialog;
	
	JSONParser jsonParser = new JSONParser();
	
	private static final String url_box_key = "http://naturalbornkennedy.com/droplock/v1/droplock_api/include/get_box_key.php";
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_IDBOX = "idBox";
	private static final String TAG_BOX_KEY = "box_key";
	private static String box_key;
	*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.unlock_box_layout);
		Log.i(TAG, "START");
		
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		mDevices = new ArrayList<BluetoothDevice>();
		devices_name = new ArrayList<String>();
		
		
		startScan();
		
		/**
		scanButton = (Button)findViewById(R.id.scan);
		scanButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startScan();
			}
		});*/
		
		unlockButton = (Button)findViewById(R.id.unlock);
		unlockButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					unlockBle();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		lockButton = (Button)findViewById(R.id.lock);
		lockButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					lockBle();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		bleDevicesListView = (ListView)findViewById(R.id.listOfBleDevices);
		bleDevicesAdapter = new ArrayAdapter<String>(this, R.layout.list_ble_devices, devices_name);
		bleDevicesListView.setAdapter(bleDevicesAdapter);


		//Set listener on listview Items
		bleDevicesListView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
			{
				//Toast.makeText(MainActivity.this, "Checked  "+ bleDevicesListView.getItemAtPosition(position), Toast.LENGTH_LONG).show();
				BluetoothDevice device = mDevices.get(position);
				Toast.makeText(UnlockBoxActivity.this, "Connecting to  "+ device.getName(), Toast.LENGTH_LONG).show();
				Log.i(TAG, "Connecting to "+device.getName());

				mConnectGatt = device.connectGatt(getApplicationContext(), false, mGattCallback);
				
				//new GetBoxKey().execute();
				
				
				
			}
		});

		/*
		 * A progress dialog will be needed while the connection process is
		 * taking place
		 */
		mProgress = new ProgressDialog(this);
		mProgress.setIndeterminate(true);
		mProgress.setCancelable(false);

		//mPressure = (TextView) findViewById(R.id.text_pressure);
	}


	@Override
	protected void onResume(){
		super.onResume();
		// Ensures Bluetooth is available on the device and it is enabled. If not,
		// displays a dialog requesting user permission to enable Bluetooth.
		if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
			//Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			//startActivity(enableBtIntent);
			finish();
			return;
		}

	}

	private Runnable mStopRunnable = new Runnable() {
		@Override
		public void run() {
			stopScan();
		}
	};
	private Runnable mStartRunnable = new Runnable() {
		@Override
		public void run() {
			startScan();
		}
	};

	public void startScan(){
		Log.i(TAG, "start scanning " );
		mBluetoothAdapter.startLeScan(this);
		try {
			
		Thread.sleep(2000);
		}catch(InterruptedException e){
			Log.i(TAG, "Could not go to sleep ");
		}
		
		stopScan();
		
		setProgressBarIndeterminateVisibility(true);
		
	}

	public void stopScan(){
		mBluetoothAdapter.stopLeScan(this);
		setProgressBarIndeterminateVisibility(false);
		Log.i(TAG, "Stop scan  ");
	}
	
	public void unlockBle() throws IOException{
		String str = "Hello world !";
		str = str.toString()+ "\r\n";
		byte commandType = 0x01;
		byte [] key = {0x01, 0x02, 0x03, 0x04}; //unlockKey.getBytes();
		byte loc, eoc = (byte)0x7f; // length of the command , end of the command
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
		
		loc = (byte)(key.length +3);
		outputStream.write( loc );
		outputStream.write( commandType );
		outputStream.write( key );
		outputStream.write( eoc );
		
		byte tx[] = outputStream.toByteArray();
		//final byte[] tx = new byte[1 + key.length];/*str.getBytes()*/;
		//System.arraycopy(command, 0, tx, 0, 1);
		//System.arraycopy(command, 0, tx, 1, key.length);
		Log.i(TAG, "outputStream"+outputStream);
		characteristicTx.setValue(tx);
		buttonGatt.writeCharacteristic(characteristicTx);
		Log.i(TAG, "Message sent to BLE"+tx);
	}
	
	public void lockBle() throws IOException{
		//String str = box_key;
		//str = str.toString()+ "\r\n";
		byte commandType = 0x02;
		byte [] key = {0x01, 0x02, 0x03, 0x04}; //unlockKey.getBytes();
		byte loc, eoc = (byte)0x7f; // length of the command , end of the command
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
		
		loc = (byte)(key.length +3);
		outputStream.write( loc );
		outputStream.write( commandType );
		outputStream.write( key );
		outputStream.write( eoc );
		
		byte tx[] = outputStream.toByteArray();
		//final byte[] tx = new byte[1 + key.length];/*str.getBytes()*/;
		//System.arraycopy(command, 0, tx, 0, 1);
		//System.arraycopy(command, 0, tx, 1, key.length);
		Log.i(TAG, "outputStream"+outputStream);
		characteristicTx.setValue(tx);
		buttonGatt.writeCharacteristic(characteristicTx);
		Log.i(TAG, "Message sent to BLE"+tx);
	}



	public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord){

		//put(device.hashCode(), device);
		if (DEVICE_NAME_HM.equals(device.getName()) && first_time !=0 ) {
			Log.i(TAG, "New BLE device  " + device.getName() + " hashcode " + device.hashCode() );
			devices_name.add( device.getName());
			mDevices.add(device);

			Log.i(TAG, "Number of devices  " + mDevices.size());
			first_time = 0;
			bleDevicesAdapter.notifyDataSetChanged();

		}else if (DEVICE_NAME_RBL.equals(device.getName()) && first_time !=0 ) {
			Log.i(TAG, "New BLE device  " + device.getName() + " hashcode " + device.hashCode() );
			devices_name.add( device.getName());
			mDevices.add(device);

			Log.i(TAG, "Number of devices  " + mDevices.size());
			first_time = 0;
			bleDevicesAdapter.notifyDataSetChanged();

		}

	}


	/*
	 * In this callback, we've created a bit of a state machine to enforce that only
	 * one characteristic be read or written at a time until all of our sensors
	 * are enabled and we are registered to get notifications.
	 */
	private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {



		/*
		 * Send an enable command to each sensor by writing a configuration
		 * characteristic.  This is specific to the SensorTag to keep power
		 * low by disabling sensors you aren't using.
		 */

		private void enableNextSensor(BluetoothGatt gatt) {

			BluetoothGattService service = gatt.getService(CHAT_SERVICE);
			if(service == null) Log.d(TAG, "the requested service is not offered by the remote device");
			Log.d(TAG, "CHAT_SERVICE " + CHAT_SERVICE);
			characteristicTx = service.getCharacteristic(CHAT_SERVICE_TX);
			if(characteristicTx == null) Log.d(TAG, "the requested characteristicTx is not offered by the remote device");

			characteristicRx = service.getCharacteristic(CHAT_SERVICE_RX);
			if(characteristicRx == null) Log.d(TAG, "the requested characteristicRx is not offered by the remote device");
			
			setNotifyNextSensor(gatt);
			Log.d(TAG, "readCharacteristic RX cal " + gatt.readCharacteristic(characteristicRx));

		}

		// Enable notification of changes on the data characteristic for each sensor
		//by writing the ENABLE_NOTIFICATION_VALUE flag to that characteristic's
		// configuration descriptor.

		private void setNotifyNextSensor(BluetoothGatt gatt) {
			BluetoothGattCharacteristic characteristic;

			
			characteristic = gatt.getService(CHAT_SERVICE).getCharacteristic(CHAT_SERVICE_RX);

			//Enable local notifications
			Log.d(TAG, "Set notify RX cal " + gatt.setCharacteristicNotification(characteristic, true));
			//Enabled remote notifications
			BluetoothGattDescriptor desc = characteristic.getDescriptor(UUID.fromString(CLIENT_CHARACTERISTIC_CONFIG));
			if(desc == null) Log.d(TAG, "no descriptor with the given UUID was found");
			
			desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
			gatt.writeDescriptor(desc);
		}

		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
			Log.d(TAG, "Connection State Change: ");
			Log.d(TAG, "Connection State Change: "+status+" -> "+connectionState(newState));
			if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_CONNECTED) {

				//  Once successfully connected, we must next discover all the services on the
				//device before we can read and write their characteristics.

				gatt.discoverServices();
				//mHandler.sendMessage(Message.obtain(null, MSG_PROGRESS, "Discovering Services..."));
			} else if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_DISCONNECTED) {

				// If at any point we disconnect, send a message to clear the weather values
				//out of the UI

				mHandler.sendEmptyMessage(MSG_CLEAR);
			} else if (status != BluetoothGatt.GATT_SUCCESS) {

				// If there is a failure at any stage, simply disconnect

				gatt.disconnect();
			}
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			Log.d(TAG, " New Service Discovered: " +status);
			//mHandler.sendMessage(Message.obtain(null, MSG_PROGRESS, "Enabling Sensors..."));

			// With services discovered, we are going to reset our state machine and start
			// working through the sensors we need to enable
			buttonGatt = gatt;
			enableNextSensor(gatt);
		}



		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			//After writing the enable flag, next we read the initial value
			//readNextSensor(gatt);
			Log.i(TAG, " onCharacteristicWrite " );
		}

		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

			// After notifications are enabled, all updates from the device on characteristic
			// value changes will be posted here.  Similar to read, we hand these up to the
			// UI thread to update the display.
			Log.i(TAG, " onCharacteristicChanged " );
			if (CHAT_SERVICE_RX.equals(characteristic.getUuid())) {
				final byte[] rx = characteristic.getValue();
				String data = new String(rx);
				Log.i(TAG, " amir: " +data);
			}
			

		}

		@Override
		public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
			//Once notifications are enabled, we move to the next sensor and start over with enable
			//advance();
			Log.i(TAG, " onDescriptorWrite " );
		}
		/*
		@Override
		public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
			Log.d(TAG, "Remote RSSI: "+rssi);
		}
		 */
		private String connectionState(int status) {
			switch (status) {
			case BluetoothProfile.STATE_CONNECTED:
				return "Connected";
			case BluetoothProfile.STATE_DISCONNECTED:
				return "Disconnected";
			case BluetoothProfile.STATE_CONNECTING:
				return "Connecting";
			case BluetoothProfile.STATE_DISCONNECTING:
				return "Disconnecting";
			default:
				return String.valueOf(status);
			}
		}
	};

	private static final int MSG_HUMIDITY = 101;
	private static final int MSG_PRESSURE = 102;
	private static final int MSG_PRESSURE_CAL = 103;
	private static final int MSG_PROGRESS = 201;
	private static final int MSG_DISMISS = 202;
	private static final int MSG_CLEAR = 301;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			BluetoothGattCharacteristic characteristic;
			switch (msg.what) {
			case MSG_HUMIDITY:
				characteristic = (BluetoothGattCharacteristic) msg.obj;
				if (characteristic.getValue() == null) {
					Log.w(TAG, "Error obtaining humidity value");
					return;
				}
				//updateHumidityValues(characteristic);
				break;
			case MSG_PRESSURE:
				characteristic = (BluetoothGattCharacteristic) msg.obj;
				if (characteristic.getValue() == null) {
					Log.w(TAG, "Error obtaining pressure value");
					return;
				}
				updatePressureValue(characteristic);
				break;
			case MSG_PRESSURE_CAL:
				characteristic = (BluetoothGattCharacteristic) msg.obj;
				if (characteristic.getValue() == null) {
					Log.w(TAG, "Error obtaining cal value");
					return;
				}
				//updatePressureCals(characteristic);
				break;
			case MSG_PROGRESS:
				mProgress.setMessage((String) msg.obj);
				if (!mProgress.isShowing()) {
					mProgress.show();
				}
				break;
			case MSG_DISMISS:
				mProgress.hide();
				break;
			case MSG_CLEAR:
				//clearDisplayValues();
				break;
			}
		}
	};



	private void updatePressureValue(BluetoothGattCharacteristic characteristic) {


		mPressure.setText(String.format("%#034x", characteristic.getUuid()));
	}

	@Override
	protected void onPause() {
		super.onPause();
		//Make sure dialog is hidden
		mProgress.dismiss();
		//Cancel any scans in progress
		mHandler.removeCallbacks(mStopRunnable);
		mHandler.removeCallbacks(mStartRunnable);
		mBluetoothAdapter.stopLeScan(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		//Disconnect from any active tag connection
		if (mConnectGatt != null) {
			mConnectGatt.disconnect();
			mConnectGatt = null;
		}
	}
	
	/**
     * Background Async Task to Get complete user details
     
    class GetBoxKey extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
       
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UnlockBoxActivity.this);
            pDialog.setMessage("Verifying Credentials. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

		@Override
		protected String doInBackground(String... params) {
			// Updating UI from Background Thread
						runOnUiThread(new Runnable() {
							public void run() {
								// Check for success tag
								int success;
								try {
									// Building Parameters
									List<NameValuePair> params = new ArrayList<NameValuePair>();
									params.add(new BasicNameValuePair("idBoxes", "1"));
									
									// Getting user details by making HTTP request
									// NOTE - user details url will use GET request

									
									JSONObject json = jsonParser.makeHttpRequest(url_box_key, "GET", params);
									
									
									//JSON success tag
									success = json.getInt(TAG_SUCCESS);
									if (success == 1) {
										// successfully received box details
										JSONArray userObj = json.getJSONArray(TAG_IDBOX); // JSON Array !!(used below)
										
										//get first product object from JSON Array
										JSONObject user = userObj.getJSONObject(0);
										
										
			 
			                            // display product data in EditText
										box_key = user.getString(TAG_BOX_KEY);
			                            
			                            
			                            
									} else {
										// Product with pid not found !!!
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						});
						
						return null;
					}
		/**
         * After completing background task Dismiss the progress dialog
         *
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }
    * */


}
