package com.ensate.robotixcarrc;

/**
 * Defines several constants used between {@link BluetoothService} and the UI.
 */
public class Constants {

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    public static final String APP_UUID = "00001101-0000-1000-8000-00805f9b34fb";

    /**
     * Commands char constant
     */
    //base direction
    public static final char MOVE_UP_CMD = 'u';
    public static final char MOVE_DOWN_CMD = 'd';
    public static final char MOVE_RIGHT_CMD = 'r';
    public static final char MOVE_LEFT_CMD = 'l';
    //corner direction
    public static final char MOVE_UP_RIGHT_CMD = 'n';
    public static final char MOVE_DOWN_LEFT_CMD = 's';
    public static final char MOVE_UP_LEFT_CMD = 'e';
    public static final char MOVE_DOWN_RIGHT_CMD = 'w';
    //speed control
    // TODO: 2/13/19  slider value between 0 and 10
    public static final String SPEED_CMD="Slider int value between 0 and 10";
}