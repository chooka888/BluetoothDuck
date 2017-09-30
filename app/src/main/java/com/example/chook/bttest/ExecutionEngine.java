package com.example.chook.bttest;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

/**
 * Created by chook on 17/03/17.
 */
public class ExecutionEngine {

    //Variables
    BluetoothSocket btSocket = null;

    //Constructor
    public ExecutionEngine(BluetoothSocket btSocket) {
        this.btSocket = btSocket;
    }

    public boolean executeScript(Script script) throws Exception {

        try{

            //loop through the Script and execute each line based on the keywords
            for(Command c : script.getCmdList()){

                //Implement logic for REPLAY...
                for(int i=0; i<c.numberOfTimesToExecute;i++) {
                    //execute command
                    executeCommand(c);
                }

                if (c.cmdType != CommandType.DELAY){
                    Thread.sleep(script.getDefaultDelay());
                }

            }

        }catch (Exception ex){
            throw new Exception( String.format("Script execution error - %s",ex.getMessage()));
        }

        return true;
    }

    private boolean executeCommand(Command c) throws InterruptedException, IOException {

        //debug
        Log.d("executeCommand", c.getCmd());

        switch (c.cmdType){

            case REM:
                //Comment - do nothing
                break;
            case DEFAULT_DELAY:
                //Do nothing
                break;
            case DELAY:
                Thread.sleep(c.getCmdInt());
                break;
            case STRING:
                //sendText(c.getCmd());
                sendText(c.getCmd());
                break;
            case GUI:
                sendCodeSequence("leftgui",c.getCmd());
                break;
            case MENU:
                //TODO: MENU appears not to work on my laptop. need to test on diff computer.
                sendCodeSequence("none","menu");
                break;
            case ALT:
                sendCodeSequence("leftalt",c.getCmd());
                break;
            case CONTROL:
            case CTRL:
                sendCodeSequence("leftcontrol",c.getCmd());
                break;
            case DOWNARROW:
            case DOWN:
                sendCodeSequence("none","downarrow");
                break;
            case LEFTARROW:
            case LEFT:
                sendCodeSequence("none","leftarrow");
                break;
            case RIGHTARROW:
            case RIGHT:
                sendCodeSequence("none","rightarrow");
                break;
            case UPARROW:
            case UP:
                sendCodeSequence("none","uparrow");
                break;
            case REPLAY:
                break;
            case ENTER:
                sendCodeSequence("none","enter");
                break;
            case PRINTSCREEN:
                sendCodeSequence("none","printscreen");
                break;
            case KEY:
                sendCodeSequence("none",c.getCmd().toLowerCase());
                break;
        }

        return true;
    }

    //Method to send content
    private void sendText(String s) throws IOException {

        if(this.btSocket != null){

            //Formally this function passed sent the raw string to the duck, but it can flood the duck and cause errors.
            //this.btSocket.getOutputStream().write(s.getBytes());
            //So this has been re-written to loop through the Chars and send and wait 10ms.
            //TODO: make the ms wait configurable.

            //Loop through the characters in the string, Send each once with a delay to avoid flooding the duck.
            for (char c:s.toCharArray()){
                this.btSocket.getOutputStream().write(c);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private String getNextSegment(String s,int iSegmentSize, int iLastPosition){
        //String a;
        if (s.length()>iSegmentSize){
            return s.substring(iLastPosition,iSegmentSize);
        }else{
            return s;
        }

    }



    //method to send raw codes
    private void sendCode(int a) throws IOException {
        if(this.btSocket != null){
            this.btSocket.getOutputStream().write(a);
        }
    }

    //method to send special characters
    private void sendCodeSequence(String modifierKey,String sCmd) throws IOException {

        //send # to denote next 2 codes are Modifier then key
        sendText("#");

        if (sCmd != ""){
            //Send Modifier
            sendCode(HIDCodes.getInstance().getHIDCodeForKey(modifierKey));

            //Send Command
            sendCode(HIDCodes.getInstance().getHIDCodeForKey(sCmd));
        }else{
            //if command is empty, the intent must be to send the modifier as the key - e.g. send ALT keystroke to open a menu.
            //so send key stroke, with no modifier.
            //Send Modifier as none
            sendCode(HIDCodes.getInstance().getHIDCodeForKey(modifierKey));

            //Send Command
            sendCode(HIDCodes.getInstance().getHIDCodeForKey(modifierKey));
        }


    }
}
