#define PIN 8
# define nLen 13

short notes[nLen] = {262, 277, 294, 311, 330, 349, 370, 399, 415, 440, 466, 494, 523};
float times[nLen] = {};

void setup() {
  Serial.begin(9600);
  pinMode(PIN, OUTPUT);
  for(int i=0; i<nLen; i++){
    times[i] = 500000*(1/float(notes[i]));
    Serial.print(times[i]);
    Serial.print("\n");
  }
}

void loop() {
  char ch;
  if(Serial.available()){
    ch = Serial.read();
  switch(ch){
    case 'c':
      playNote(times[0]);
      break;
    case 'C':
      playNote(times[1]);
      break;  
    case 'd':
      playNote(times[2]);
      break;
    case 'D':
      playNote(times[3]);
      break;
    case 'e':
      playNote(times[4]);
      break;
    case 'f':
      playNote(times[5]);
      break;
    case 'F':
      playNote(times[6]);
      break;
    case 'g':
      playNote(times[7]);
      break;
    case 'G':
      playNote(times[8]);
      break;
    case 'a':
      playNote(times[9]);
      break;
    case 'A':
      playNote(times[10]);
      break;
    case 'b':
      playNote(times[11]);
      break;
    case 'o':
      playNote(times[12]);
      break;
    case ' ':
      delay(100);
      break;
    default:
      break;
  }
  }
  delay(10);
}

void playNote(float t){
  int osc = int(100000/t);
  for(int i = 0; i < osc; i++){
    digitalWrite(PIN, HIGH);
    delayMicroseconds(t);
    digitalWrite(PIN, LOW);
    delayMicroseconds(t);
  }
}
