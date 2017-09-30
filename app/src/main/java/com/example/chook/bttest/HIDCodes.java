package com.example.chook.bttest;
import java.util.HashMap;

/**
 * Created by chook on 18/03/17.
 */
public class HIDCodes {
    private static HIDCodes ourInstance = null;

    public static HIDCodes getInstance() {

        if (ourInstance == null){
            ourInstance = new HIDCodes();
        }

        return ourInstance;
    }

    private static HashMap<String, Integer> htHidCodes = new HashMap<String, Integer>();

    private HIDCodes() {
        //TODO: discover the code / technique fo break...

        //Load up HID CODES - all in lower case to simular case insensitive look up..
        htHidCodes.put("none",0);
        htHidCodes.put("a",4);
        htHidCodes.put("b",5);
        htHidCodes.put("c",6);
        htHidCodes.put("d",7);
        htHidCodes.put("e",8);
        htHidCodes.put("f",9);
        htHidCodes.put("g",10);
        htHidCodes.put("h",11);
        htHidCodes.put("I",12);
        htHidCodes.put("j",13);
        htHidCodes.put("k",14);
        htHidCodes.put("l",15);
        htHidCodes.put("m",16);
        htHidCodes.put("n",17);
        htHidCodes.put("o",18);
        htHidCodes.put("p",19);
        htHidCodes.put("q",20);
        htHidCodes.put("r",21);
        htHidCodes.put("s",22);
        htHidCodes.put("t",23);
        htHidCodes.put("u",24);
        htHidCodes.put("v",25);
        htHidCodes.put("w",26);
        htHidCodes.put("x",27);
        htHidCodes.put("y",28);
        htHidCodes.put("z",29);
        htHidCodes.put("1",30);
        htHidCodes.put("2",31);
        htHidCodes.put("3",32);
        htHidCodes.put("4",33);
        htHidCodes.put("5",34);
        htHidCodes.put("6",35);
        htHidCodes.put("7",36);
        htHidCodes.put("8",37);
        htHidCodes.put("9",38);
        htHidCodes.put("0",39);
        htHidCodes.put("enter",40);
        htHidCodes.put("esc",41);
        htHidCodes.put("escape",41);//extra for duckyscript
        htHidCodes.put("backspace",42);
        htHidCodes.put("tab",43);
        htHidCodes.put("space",44);
        htHidCodes.put("_",45);
        htHidCodes.put("'=",46);
        htHidCodes.put("[",47);
        htHidCodes.put("]",48);
        htHidCodes.put("\\",49);
        htHidCodes.put("#",50);
        htHidCodes.put(";",51);
        htHidCodes.put("'",52);
        htHidCodes.put("`",53);
        htHidCodes.put(",",54);
        htHidCodes.put(".",55);
        htHidCodes.put("/",56);
        htHidCodes.put("capslock",57);
        htHidCodes.put("f1",58);
        htHidCodes.put("f2",59);
        htHidCodes.put("f3",60);
        htHidCodes.put("f4",61);
        htHidCodes.put("f5",62);
        htHidCodes.put("f6",63);
        htHidCodes.put("f7",64);
        htHidCodes.put("f8",65);
        htHidCodes.put("f9",66);
        htHidCodes.put("f10",67);
        htHidCodes.put("f11",68);
        htHidCodes.put("f12",69);
        htHidCodes.put("printscreen",70);
        htHidCodes.put("scrolllock",71);
        htHidCodes.put("pause",72);
        htHidCodes.put("insert",73);
        htHidCodes.put("home",74);
        htHidCodes.put("pageup",75);
        htHidCodes.put("delete",76);
        htHidCodes.put("end",77);
        htHidCodes.put("pagedown",78);
        htHidCodes.put("rightarrow",79);
        htHidCodes.put("leftarrow",80);
        htHidCodes.put("downarrow",81);
        htHidCodes.put("uparrow",82);
        htHidCodes.put("pagedown",78);
        htHidCodes.put("right",79);//extra for ducky script
        htHidCodes.put("left",80);//extra for ducky script
        htHidCodes.put("down",81);//extra for ducky script
        htHidCodes.put("up",82);//extra for ducky script
        htHidCodes.put("numlock",83);
        //note put KP to signify key pad to diffentiate keys...
        htHidCodes.put("kp/",84);
        htHidCodes.put("kp*",85);
        htHidCodes.put("kp-",86);
        htHidCodes.put("kp+",87);
        htHidCodes.put("kpenter",88);
        htHidCodes.put("kp1",89);
        htHidCodes.put("kp2",90);
        htHidCodes.put("kp3",91);
        htHidCodes.put("kp4",92);
        htHidCodes.put("kp5",93);
        htHidCodes.put("kp6",94);
        htHidCodes.put("kp7",95);
        htHidCodes.put("kp8",96);
        htHidCodes.put("kp9",97);
        htHidCodes.put("kp0",98);
        htHidCodes.put("kp.",99);
        htHidCodes.put("|",100);
        htHidCodes.put("application",101);
        htHidCodes.put("power",102);
        htHidCodes.put("kp=",103);
        htHidCodes.put("f13",104);
        htHidCodes.put("f14",105);
        htHidCodes.put("f15",106);
        htHidCodes.put("f16",107);
        htHidCodes.put("f17",108);
        htHidCodes.put("f18",109);
        htHidCodes.put("f19",110);
        htHidCodes.put("f20",111);
        htHidCodes.put("f21",112);
        htHidCodes.put("f22",113);
        htHidCodes.put("f23",114);
        htHidCodes.put("f24",115);
        htHidCodes.put("execute",116);
        htHidCodes.put("help",117);
        htHidCodes.put("menu",118);
        htHidCodes.put("app",118); //Extra for ducky script
        htHidCodes.put("select",119);
        htHidCodes.put("stop",120);
        htHidCodes.put("again",121);
        htHidCodes.put("undo",122);
        htHidCodes.put("cut",123);
        htHidCodes.put("copy",124);
        htHidCodes.put("paste",125);
        htHidCodes.put("find",126);
        htHidCodes.put("mute",127);
        htHidCodes.put("volumeup",128);
        htHidCodes.put("volumedown",129);
        htHidCodes.put("lockingcapslock",130);
        htHidCodes.put("lockingnumlock",131);
        htHidCodes.put("lockingscolllock",132);
        htHidCodes.put("kpcomma",133);
        htHidCodes.put("kp=",134);

        //Modifier keys
        htHidCodes.put("leftcontrol",1);
        htHidCodes.put("leftshift",2);
        htHidCodes.put("leftalt",4);
        htHidCodes.put("leftgui",8);
        htHidCodes.put("rightcontrol",16);
        htHidCodes.put("rightshift",32);
        htHidCodes.put("rightalt",64);
        htHidCodes.put("rightgui",128);
        htHidCodes.put("windows",8);//extra for ducky script
        htHidCodes.put("control",1);//extra for ducky script

    }

    //TODO: Make this safe
    public Integer getHIDCodeForKey(String key){
        return htHidCodes.get(key.toLowerCase());
    }
}
