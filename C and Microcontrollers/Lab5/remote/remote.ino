#include <IRremote.h>

#define BUT_0 0xFF6897 // Defining IR number button codes
#define BUT_1 0xFF30CF
#define BUT_2 0xFF18E7
#define BUT_3 0xFF7A85
#define BUT_4 0xFF10EF
#define BUT_5 0xFF38C7
#define BUT_6 0xFF5AA5
#define BUT_7 0xFF42BD
#define BUT_8 0xFF4AB5
#define BUT_9 0xFF52AD

int RECV_PIN = 2;
IRrecv irrecv(RECV_PIN);
decode_results results;

void setup()
{
  Serial.begin(9600);
  irrecv.enableIRIn(); // Start the receiver
  irrecv.blink13(true); // Set inbuilt LED to blink when the IR receives a signal
  pinMode(4, OUTPUT); // Configure 7-segment pins to output state
  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
}

void writeCode(int code){ // Take in a button code and pass corresponding 7-segment byte to print7seg() through switch statement
  switch(code){
  case BUT_0: {
    Serial.println("0");
    print7seg(B11111100);
    break;
  }
  case BUT_1: {
    Serial.println("1");
    print7seg(B01100000);
    break;
  }
  case BUT_2: {
    Serial.println("2");
    print7seg(B11011010);
    break;
  }
  case BUT_3: {
    Serial.println("3");
    print7seg(B11110010);
    break;
  }
  case BUT_4: {
    Serial.println("4");
    print7seg(B01100110);
    break;
  }
  case BUT_5: {
    Serial.println("5");
    print7seg(B10110110);
    break;
  }
  case BUT_6: {
    Serial.println("6");
    print7seg(B10111110);
    break;
  }
  case BUT_7: {
    Serial.println("7");
    print7seg(B11100000);
    break;
  }
  case BUT_8: {
    Serial.println("8");
    print7seg(B11111110);
    break;
  }
  case BUT_9: {
    Serial.println("9");
    print7seg(B11110110);
    break;
  }
  default :Serial.println("unknown");
  }
}

void print7seg(int pins){// Loops through bits to decide whether to light each segment (from right to left, hence decreasing for loop)
  for(int i = 11; i > 3; i--){
    if(1 & pins){
      digitalWrite(i, LOW);
    }
    else{
      digitalWrite(i, HIGH);
    }
    pins = pins >> 1;
  }
}

void loop() {
  if (irrecv.decode(&results)) {
    Serial.println(results.value, HEX);
    writeCode(results.value);
    irrecv.resume(); // Receive the next value
  }
}
