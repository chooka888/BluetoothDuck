package com.example.chook.bttest;

/**
 * Created by chook on 14/03/17.
 */
public class Command {

    protected CommandType cmdType;
    protected String cmd;

    public int getNumberOfTimesToExecute() {
        return numberOfTimesToExecute;
    }

    public void setNumberOfTimesToExecute(int numberOfTimesToExecute) {
        this.numberOfTimesToExecute = numberOfTimesToExecute;
    }

    protected int numberOfTimesToExecute;

    //Constructor
    public Command(CommandType cmdType, String cmd) {
        this.cmdType = cmdType;
        this.cmd = cmd;
        this.numberOfTimesToExecute = 1;
    }

    //Default constructor
    public Command() {
        this.cmdType = CommandType.REM;
        this.cmd = "";
        this.numberOfTimesToExecute = 1;
    }

    //Constructor that accept the raw text field.
    public Command(String rawTextCommand) throws IllegalArgumentException {

        this.numberOfTimesToExecute = 1;
        int firstSpacePosition = 0;
        try {

            //find the first space
            firstSpacePosition = rawTextCommand.indexOf(" ");

            if (firstSpacePosition > -1) {

                //capture the first word = command
                //NOTE VALUE OF is an expensive command if perf is a problem use a switch, of hash table match.

                this.cmdType = CommandType.valueOf(rawTextCommand.substring(0, firstSpacePosition));
                this.cmd = rawTextCommand.substring(firstSpacePosition + 1);

            }else{
                this.cmdType = CommandType.valueOf(rawTextCommand);
                this.cmd = "";
            }
        }catch(Exception ex){
            throw new IllegalArgumentException(String.format("Invalid ducky script keyword - %s",rawTextCommand.substring(0, firstSpacePosition)));
        }

     }

    //Getters and Setters
    public CommandType getCmdType() {
        return cmdType;
    }

    public void setCmdType(CommandType cmdType) {
        this.cmdType = cmdType;
    }

    public String getCmd() {
        return cmd;
    }

    public int getCmdInt(){
        //TODO: Check the safety of that conversion.
        return Integer.parseInt(cmd.trim());
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

}
