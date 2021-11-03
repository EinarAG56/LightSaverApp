/*
  Light Saver App
  
 */
 
// constantes
const int botonPin = D1;  // Pin de entrada GPIO 5 - D1
const int ledPin =  D2;    // Pin de salida GPIO 2 - D2
const int servoPin = D7;   // Pin de salida GPIO 2 - D3
// variable que contiene el estado del botón
int estadoboton = 0;  
int boton = 0;   
void setup() {
  // configura el GPIO 2 como salida
  pinMode(ledPin, OUTPUT);
  // configura el GPIO 0 como entrada
  pinMode(botonPin, INPUT_PULLUP);
  // configura el GPIO 0 como entrada 
  myservo.attach(servoPin);
}

void loop() {
  // lee el estado del botón conectado en GPIO 0
  estadoboton = digitalRead(botonPin);

  // si esta activado (para este ejemplo, logica negativa)
    if (estadoboton == LOW) {
      if(boton == HIGH){
        boton = 0;
      }else{
        boton = 1;
      }
    }
    if(boton == HIGH){
        digitalWrite(ledPin, LOW); // activa el Led:
      } else {
        digitalWrite(ledPin, HIGH); // apaga el Led:
      }
}
