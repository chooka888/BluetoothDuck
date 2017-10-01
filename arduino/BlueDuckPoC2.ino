#include <SoftSerial_INT0.h> //https://github.com/J-Rios/Digispark_SoftSerial-INT0
#include "DigiKeyboard.h"

//Pin Setup
//p0 - nothing
//p1 - connected to HC-05 RX pin
//p2 - Connected to HC-05 TX pin
//p3 - nothing
//p4 - nothing
//p5 - nothing

#define P_RX 2                        // Reception PIN (SoftSerial)
#define P_TX 1                        // Transmition PIN (SoftSerial)
#define BLE_TIMEOUT 10000             // Time for connect with the BLE module
#define KEY_ENTER 40                  // Keyboard usage values (ENTER Key)
#define KEY_ESC 41                    // Keyboard usage values (ESCAPE Key)

SoftSerial BLE(P_RX, P_TX);           // Software serial port for control the BLE module

bool special;
static char sCmd; // Get Command variable

void setup() 
{ 
  //SoftSerial BLE(P_RX, P_TX);           // Software serial port for control the BLE module
  //pinMode(P_RX, INPUT);
  //pinMode(P_TX, OUTPUT); 
  BLE.begin(9600); // Initialize the serial port
  BLE.print("at+conn00,15,83,00,12,51\r\n"); // Send the AT command to connect with the Slave module
  DigiKeyboard.delay(1000); // Wait the module for connect 
  special = false;
  sCmd = ' ';
  
} 
 
void loop()
{
    static char cmd; // Get Command variable
    
    if(BLE.available()) // If there is any data incoming from the serial port
    {
        cmd = BLE.read(); // Get command
        
        if (!special){
          //check if special character
          if(cmd == '#'){
            //toggle flag
            special = true;
          }else{
            DigiKeyboard.print(cmd);
          }
        }else{
          //check if special char is empty
          if (sCmd == ' '){
            //save the special command
            sCmd = cmd;
          } else {
            DigiKeyboard.sendKeyStroke(cmd, sCmd);
            special = false;
            sCmd = ' ';
          }
        }           
               
    }
    
    DigiKeyboard.update(); // Update the USB connection (maintain alive the connection)
}







