package com.example.chook.bttest;

import java.util.ArrayList;

/**
 * Created by chook on 17/03/17.
 */
public class Script {

    //Private variables
    private ArrayList<Command> cmdList;
    private int iDefaultDelay;

    //Constructors
    public Script(ArrayList<Command> cmdList, int defaultDelay) {
        this.cmdList = cmdList;
        iDefaultDelay = defaultDelay;
    }

    public Script() {
        cmdList = new ArrayList<Command>();
        iDefaultDelay = 500;
    }

    public Script(String sPayload){
        iDefaultDelay = 500;
        readInCommandList(sPayload);
    }

    //Convert string from txtview into commands, also set Default Delay
    public void readInCommandList(String sPayload){
        cmdList = new ArrayList<Command>();

        String mutliLines = sPayload;
        String[] lines;
        String delimiter = "\n";
        lines = mutliLines.split(delimiter);

        //Loop through each line
        for(String s: lines){

            if(s.trim() !="" && s.length()>0 ) {

                //Create command object
                Command c = new Command(s);

                //Check if default delay
                if (c.cmdType == CommandType.DEFAULT_DELAY) {
                    iDefaultDelay = c.getCmdInt();
                }

                //Check if it is a replay command
                if (c.cmdType == CommandType.REPLAY) {

                    //note not added if it is a replay command as it is saved in the previous command.
                    int previousIndex = cmdList.size() - 1;
                    Command previousCommand = cmdList.get(previousIndex);
                    previousCommand.setNumberOfTimesToExecute(c.getCmdInt());
                    cmdList.set(previousIndex, previousCommand);

                } else {
                    //Add it to the list
                    cmdList.add(c);
                }
            }
        }

    }


    //getters and setter
    public int getDefaultDelay() {
        return iDefaultDelay;
    }

    public void setDefaultDelay(int defaultDelay) {
        iDefaultDelay = defaultDelay;
    }

    public ArrayList<Command> getCmdList() {
        return cmdList;
    }

    public void setCmdList(ArrayList<Command> cmdList) {
        this.cmdList = cmdList;
    }

}
