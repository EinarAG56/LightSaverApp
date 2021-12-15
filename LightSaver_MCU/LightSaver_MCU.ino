#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

// WiFi Definitions
const char* ssid = "Esp8266TestNet";
const char* password = "Esp8266Test"; // has to be longer than 7 chars
const char* value = "";

const int lightSensor = A0;
const int redPin =  D1;
const int greenPin =  D2;
const int bluePin =  D3;// Pin de salida GPIO 2 - D2
WiFiServer server(80);

int sensor = 0;
int count = 0;
int isNotif = 0;
void setup() {

   Serial.begin(115200);
   delay(10);
   pinMode(redPin, OUTPUT);
   pinMode(greenPin, OUTPUT);
   pinMode(bluePin, OUTPUT);  
   writeRGB(255,0,0); // turn on
   writeRGB(0,255,0); // turn on
   writeRGB(0,0,255); // turn on
   writeRGB(0,0,0); // turn on

   WiFi.mode(WIFI_AP);
   WiFi.softAP(ssid, password, 1, 1);
   Serial.println("WiFi client is on");
   server.begin();
}

void loop() {
  // Check of client has connected
  WiFiClient client = server.available();

  if(!client) {
    delay(1);
    return;
  }
  
  // Read the request line
  String request = client.readStringUntil('\r');
  Serial.println(request);
  client.flush();
  
  // Match request
  if(request.indexOf("/led/red") != -1) {
      writeRGB(255,0,0);
     value = "on";
  }else if(request.indexOf("/led/green") != -1) {
      writeRGB(0,255,0);
     value = "on";
  } else if(request.indexOf("/led/blue") != -1) {
      writeRGB(0,0,255);
     value = "on";
  }else if(request.indexOf("/led/yellow") != -1) {
      writeRGB(255,255,0);
     value = "on";
  }else if(request.indexOf("/led/purple") != -1) {
      writeRGB(138,43,226);
     value = "on";
  }else if (request.indexOf("/led/off") != -1) {
     writeRGB(0,0,0);
      value = "off";
  }
  
  client.flush();
   
  // JSON response
  String s = "HTTP/1.1 200 OK\r\n";
  s += "Content-Type: application/json\r\n\r\n";
  s += "{\"data\":{\"message\":\"success\",\"value\":\"";
  s += value;
  s += "\"}}\r\n";
  s += "\n";

  // Send the response to the client
  client.print(s);
  delay(1);
  Serial.println("Client disconnected");

  // The client will actually be disconnected when the function returns and the client object is destroyed
}

void writeRGB(int r, int g, int b){
  analogWrite(redPin, r);
  analogWrite(greenPin, g);
  analogWrite(bluePin, b);
}
