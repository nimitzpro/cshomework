#define RED 8
#define GREEN 9

void setup() {
  Serial.begin(9600);
  DDRD = B00000000; // Set Port D's pins to input
}

void loop() {
  int x = PIND>>1; // x = Port D's pins (shifted by 1 here, and 1 at the start of the program)
  int y = 1; // y is used to compare against each bit of Port D
  bool red = false;
  bool green = false;
  Serial.println(x, BIN);
  int prev = 0;
  for(int i = 0; i < 6; i++){
    x = x>>1; // shift x by 1
    int a = y & x; // AND x and y to get a pin's output
    Serial.print(prev, BIN);
    Serial.print(", ");
    Serial.println(a, BIN);
    if(a && prev){ // if current pin and previous are both on, then turn green on
      green = true;
    }
    if(i>0 && !a && !prev){ // if current pin is not first (as prev=0 on init, and both the previous and current are off, turn red on
      red = true;
    }
    prev = a;
  }
  
  if(red) digitalWrite(RED, HIGH);
  else digitalWrite(RED, LOW);
  if(green) digitalWrite(GREEN, HIGH);
  else digitalWrite(GREEN, LOW);

  delay(1000);
}
