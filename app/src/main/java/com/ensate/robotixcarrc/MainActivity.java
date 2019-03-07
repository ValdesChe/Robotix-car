package com.ensate.robotixcarrc;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int UPDATE_SETTING = 3;



    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;

    /**
     * Array adapter for the conversation thread
     */
    private ArrayAdapter<String> mConversationArrayAdapter;

    /**
     * String buffer for outgoing messages
     */
    private StringBuffer mOutStringBuffer;

    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    /**
     * My options menu
     */
    private MenuItem mConnectScanMenuItem;

    /**
     * bluetooth service
     */
    private BluetoothService mBluetoothService;


    /**
     * control buttons
     */
    private ImageButton mMoveRightButton;
    private ImageButton mMoveLeftButton;
    private ImageButton mMoveUpButton;
    private ImageButton mMoveDownButton;

    private View mDecorView;

    private SharedPreferences mPrefs;


    private boolean doubleBackToExitPressedOnce = false;

    private boolean isVertical = false;
    private char isVerticalVal = ' ';
    private boolean isHorizontal = false;
    private char isHorizontalVal = ' ';



    private View.OnTouchListener mControlsButtonOnTouchListener = new View.OnTouchListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ImageButton imageButton = (ImageButton) v;
            Resources res = getResources();
            if ( mBluetoothService != null &&  event.getAction() == MotionEvent.ACTION_DOWN) {
                ConstraintLayout constraintLayout = findViewById(R.id.statusBoard);
                // System.out.println(isHorizontalVal + " | " + isVerticalVal);
               switch (v.getId()) {
                    case R.id.upButton:
                        isVertical = true;
                        if(isHorizontal && isHorizontalVal == 'L'){
                            System.out.println("TOP - LEFT");
                            sendBluetoothData(mPrefs.getString(res.getString(R.string.key_pref_pos_up_left),
                                                     res.getString(R.string.pref_default_pos_up_left)));
                            constraintLayout.setBackgroundColor(Color.rgb(55,32,5));
                        }
                        else if(isHorizontal && isHorizontalVal == 'R'){
                            System.out.println("TOP - RIGHT");
                            constraintLayout.setBackgroundColor(Color.rgb(155,132,45));
                            sendBluetoothData(mPrefs.getString(res.getString(R.string.key_pref_pos_up_right),
                                    res.getString(R.string.pref_default_pos_up_right)));
                            constraintLayout.setBackgroundColor(Color.rgb(55,32,5));
                        }
                        else{
                            System.out.println("TOP");
                            isVerticalVal = 'T';
                            sendBluetoothData(mPrefs.getString(res.getString(R.string.key_pref_pos_up),
                                    res.getString(R.string.pref_default_pos_up)));
                            constraintLayout.setBackgroundColor(Color.rgb(5,2,0));
                        }
                        break;
                    case R.id.downButton:
                        isVertical = true;
                        if(isHorizontal && isHorizontalVal == 'L'){
                            System.out.println("DOWN - LEFT");
                            sendBluetoothData(mPrefs.getString(res.getString(R.string.key_pref_pos_down_left),
                                    res.getString(R.string.pref_default_pos_down_left)));
                            constraintLayout.setBackgroundColor(Color.rgb(205,3,53));
                        }
                        else if(isHorizontal && isHorizontalVal == 'R'){
                            System.out.println("DOWN - RIGHT");
                            sendBluetoothData(mPrefs.getString(res.getString(R.string.key_pref_pos_down_right),
                                    res.getString(R.string.pref_default_pos_down_right)));
                            constraintLayout.setBackgroundColor(Color.rgb(155,132,45));
                        }
                        else{
                            System.out.println("DOWN");
                            isVerticalVal = 'D';
                            sendBluetoothData(mPrefs.getString(res.getString(R.string.key_pref_pos_down),
                                    res.getString(R.string.pref_default_pos_down)));
                            constraintLayout.setBackgroundColor(Color.rgb(105,52,205));
                        }
                        break;
                    case R.id.leftButton:
                        isHorizontal = true;
                        if(isVertical && isVerticalVal == 'T'){
                            System.out.println("TOP - LEFT");
                            sendBluetoothData(mPrefs.getString(res.getString(R.string.key_pref_pos_up_left),
                                    res.getString(R.string.pref_default_pos_up_left)));
                            constraintLayout.setBackgroundColor(Color.rgb(145,3,3));
                        }
                        else if(isVertical && isVerticalVal == 'D'){
                            System.out.println("DOWN - LEFT");
                            sendBluetoothData(mPrefs.getString(res.getString(R.string.pref_default_pos_down_left),
                                    res.getString(R.string.pref_default_pos_down_left)));
                            constraintLayout.setBackgroundColor(Color.rgb(15,132,245));
                        }
                        else{
                            System.out.println("LEFT");
                            sendBluetoothData(mPrefs.getString(res.getString(R.string.key_pref_pos_up),
                                    res.getString(R.string.pref_default_pos_up)));
                            isHorizontalVal = 'L';
                            constraintLayout.setBackgroundColor(Color.rgb(105,92,25));
                        }
                        break;
                        case R.id.rightButton:
                            isHorizontal = true;
                            if(isVertical && isVerticalVal == 'T'){
                                System.out.println("TOP - RIGHT");
                                sendBluetoothData(mPrefs.getString(res.getString(R.string.pref_default_pos_up_right),
                                        res.getString(R.string.pref_default_pos_up_right)));
                                constraintLayout.setBackgroundColor(Color.rgb(15,30,123));
                            }
                            else if(isVertical && isVerticalVal == 'D'){
                                System.out.println("DOWN - RIGHT");
                                sendBluetoothData(mPrefs.getString(res.getString(R.string.key_pref_pos_down_right),
                                        res.getString(R.string.pref_default_pos_down_right)));
                                constraintLayout.setBackgroundColor(Color.rgb(105,32,5));
                            }
                            else{
                                System.out.println("RIGHT");
                                isHorizontalVal = 'R';
                                sendBluetoothData(mPrefs.getString(res.getString(R.string.key_pref_pos_right),
                                        res.getString(R.string.pref_default_pos_right)));
                                constraintLayout.setBackgroundColor(Color.rgb(205,9,125));
                            }
                        break;
               }
               imageButton.setBackgroundColor(getResources().getColor(R.color.btn_bg_pressed));
               if (mPrefs.getBoolean(getResources().getString(R.string.key_pref_vibrate_switch), true)) {
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(50);
               }
                // sendBluetoothData(button);
                return true;
            } else if ( mBluetoothService != null && event.getAction() == MotionEvent.ACTION_UP) {
                switch (v.getId()) {
                    case R.id.upButton:
                        isVertical = false;
                        isVerticalVal = ' ';
                        break;
                    case R.id.downButton:
                        isVertical = false;
                        isVerticalVal = ' ';
                        break;
                    case R.id.leftButton:
                        isHorizontal = false;
                        isHorizontalVal = ' ';
                        break;
                    case R.id.rightButton:
                        isHorizontal = false;
                        isHorizontalVal = ' ';
                        break;
                }
                imageButton.setBackgroundColor(getResources().getColor(R.color.btn_bg_default));
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set view
        mDecorView = getWindow().getDecorView();
        // hide system ui
        hideSystemUI();
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(MainActivity.this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            MainActivity.this.finish();
        }
        //initMembers and setup control pad
        setupControlPadView();
        //load user prefs
        loadPreference();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mConnectScanMenuItem = menu.getItem(0);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect_scan_menu_item:
            case R.id.action_connect_scan: {
                openConnectActivity();
                return true;
            }
            case R.id.full_screen_menu_item:
            case R.id.action_full_screen: {
                hideSystemUI();
                return true;
            }
            case R.id.discoverable: {
                // TODO: 2/13/19
                return true;
            }
            case R.id.action_settings: {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(i, UPDATE_SETTING);
                return true;
            }
            case R.id.action_help: {
                openHelpActivity();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadPreference();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.

        if (mBluetoothService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mBluetoothService.getState() == mBluetoothService.STATE_NONE) {
                // Start the Bluetooth chat services
                mBluetoothService.start();
            }
            loadPreference();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus)
            hideSystemUI();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBluetoothService != null) {
            mBluetoothService.stop();
        }
    }

    /**
     * Menu methods
     */
    private void openHelpActivity() {
        Intent helpIntent = new Intent(MainActivity.this, HelpActivity.class);
        startActivity(helpIntent);
    }


    /******========Private methods===========******/
    /**
     * hide SystemUI to make App full screen
     */

    private void hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
            );
        }
    }

    /**
     * showSystemUI
     */
    private void showSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }


    /**
     * setup Control pad View
     */
    private void setupControlPadView() {

        mMoveUpButton = findViewById(R.id.upButton);
        mMoveDownButton = findViewById(R.id.downButton);
        mMoveLeftButton = findViewById(R.id.leftButton);
        mMoveRightButton = findViewById(R.id.rightButton);

        mMoveUpButton.setOnTouchListener(mControlsButtonOnTouchListener);
        mMoveDownButton.setOnTouchListener(mControlsButtonOnTouchListener);
        mMoveLeftButton.setOnTouchListener(mControlsButtonOnTouchListener);
        mMoveRightButton.setOnTouchListener(mControlsButtonOnTouchListener);
    }

    private void openConnectActivity() {
        //enable bluetooth first
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            // Bluetooth is now enabled
            mConnectScanMenuItem.setIcon(R.drawable.ic_bluetooth_blue_24dp);
        }
        // Launch the ConnectDeviceActivity to see devices and do scan
        Intent serverIntent = new Intent(MainActivity.this, ConnectDeviceActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);

        //setup  bluetooth Service  to make exchange possible
        if (mBluetoothService == null) {
            setupBluetoothService();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled
                    mConnectScanMenuItem.setIcon(R.drawable.ic_bluetooth_blue_24dp);
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Toast.makeText(MainActivity.this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    MainActivity.this.finish();
                }
        }
    }

    /**
     * Establish connection with other device
     *
     * @param data   An {@link Intent} with {@link ConnectDeviceActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras().getString(ConnectDeviceActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mBluetoothService.connect(device);



        //update the icon when connected
        mConnectScanMenuItem.setIcon(R.drawable.ic_bluetooth_connected_green_24dp);
        TextView textView = findViewById(R.id.textView);
        textView.setText("DEVICE : "+ device.getAddress()+"/"+address+"\n MAC : "+ device.getAddress()+" \n STATE "+ mBluetoothService.getState());

    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            // mConversationArrayAdapter.clear();
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    //mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    // mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    Toast.makeText(MainActivity.this, "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case Constants.MESSAGE_TOAST:
                    Toast.makeText(MainActivity.this, msg.getData().getString(Constants.TOAST),
                            Toast.LENGTH_SHORT).show();

                    TextView textView = findViewById(R.id.textView);
                    textView.setText(msg.getData().getString(Constants.TOAST));

                    break;
            }
        }
    };

    /**
     * Set up the UI and background operations for chat.
     */
    private void setupBluetoothService() {
        Log.d(TAG, "setupBluetoothService()");


        // Initialize the BluetoothChatService to perform bluetooth connections
        mBluetoothService = new BluetoothService(MainActivity.this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer();
    }

    /**
     * Sends a data.
     *
     * @param data A string of text to send.
     */
    private void sendBluetoothData(final String data) {
        // Check that we're actually connected before trying anything
        if (mBluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(MainActivity.this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO: 2/20/19  to set switch user pref
        final int delay = 50;

        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                send(data, true);
            }
        };
        handler.postDelayed(r, delay);
    }

    /**
     * Sends a data.
     *
     * @param data A string of text to send.     *
     * @param CRLF \r\n"  separator
     */

    public void send(String data, boolean CRLF) {
        if (CRLF)
            data += "\r\n";

        mBluetoothService.write(data.getBytes());
    }


    /**
     * Updates the status on the action bar.
     *
     * @param resId a string resource ID
     */
    private void setStatus(int resId) {

        final ActionBar actionBar = MainActivity.this.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }

    /**
     * Updates the status on the action bar.
     *
     * @param subTitle status
     */
    private void setStatus(CharSequence subTitle) {
        final ActionBar actionBar = MainActivity.this.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    public void loadPreference() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Map<String, ?> keys = mPrefs.getAll();
        Log.d("LOG", "Keys = " + keys.size() + "");
        if (keys.size() >= 11) {
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Log.d("LOG", entry.getKey() + ": " +
                        entry.getValue().toString());
            }
        }
    }

}
