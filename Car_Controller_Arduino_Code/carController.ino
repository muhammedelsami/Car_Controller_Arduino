
//L298N Bağlantısı
const int SolMotor1  = 5;  // L298N'in IN1 Girişi
const int SolMotor2  = 6;  // L298N'in IN2 Girişi
const int SolMotorEN  = 9; // L298N'in ENA Girişi
const int SagMotor1  = 7;  // L298N'in IN3 Girişi
const int SagMotor2 = 8; // L298'in IN4 Girişi
const int SagMotorEN = 10; // L298'in ENB Girişi


//Ön ve Arka ledler Bağlantısı

const int onled2 = 11; 
const int onled1 = 12;
const int arkaled1 = A2;
const int arkaled2 = A3;

// gece ledleri
const int gece1 = 2;
const int gece2 = A5;


unsigned long simdikizaman,eskizaman=0;

//sinyaller

const int p2 = A0;
const int p4 = A1;
const int p1 = 3;
const int p3 = 4;

const int zil = 13;
int state;

int durum=0, sag_sinyal=0,sol_sinyal=0;

bool durum1=false;
bool sag_sinyal_durum=false, sol_sinyal_durum=false;
bool kilit=true;

int Hiz = 100;  // Motorumuzun hız değişkenini tanımlıyoruz

void setup()
{
  pinMode(SolMotorEN, OUTPUT);
  pinMode(SagMotorEN, OUTPUT);
  pinMode(SolMotor1, OUTPUT);
  pinMode(SagMotor1, OUTPUT);
  pinMode(SolMotor2, OUTPUT);
  pinMode(SagMotor2, OUTPUT);
  pinMode(zil, OUTPUT);
  
  pinMode(onled2, OUTPUT);
   pinMode(onled1, OUTPUT);
  pinMode(arkaled1, OUTPUT);
  pinMode(arkaled2, OUTPUT);
  
  pinMode(gece1, OUTPUT);
  pinMode(gece2, OUTPUT);
  
  pinMode(p1, OUTPUT);
  pinMode(p2, OUTPUT);
  pinMode(p3, OUTPUT);
  pinMode(p4, OUTPUT);
  
  digitalWrite(SolMotorEN, LOW);
  digitalWrite(SagMotorEN, LOW);
  digitalWrite(SolMotor1, LOW);
  digitalWrite(SagMotor1, LOW);
  digitalWrite(SolMotor2, LOW);
  digitalWrite(SagMotor2, LOW);
  digitalWrite(onled2, LOW);
   digitalWrite(onled1, LOW);
  digitalWrite(arkaled1, LOW);
  digitalWrite(arkaled2, LOW);

  digitalWrite(gece1, LOW);
  digitalWrite(gece2, LOW);

  digitalWrite(p1, LOW);
  digitalWrite(p2, LOW);
  digitalWrite(p3, LOW);
  digitalWrite(p4, LOW);
  
  Serial.begin(9600);
}

void loop() {
      simdikizaman=millis();
      int isik = analogRead(A4);
      int isikyuzde = map(isik,0,150,0,100);
      Serial.println(isikyuzde);
      
      if(isikyuzde<=10 && kilit==true){
          digitalWrite(gece1, HIGH);
          digitalWrite(gece2, HIGH);
      }
      else{
          digitalWrite(gece1, LOW);
          digitalWrite(gece2, LOW);
      }
      
      if(Serial.available() > 0){
        state = Serial.read(); 
        Serial.println(state);
        if (state == 75) // kilit butonuna bastığımızda araç kilitle.
          {
              acKapat();        
              kilit=false;    
          }
        if (state == 107) // kilit butonuna tekrar bastığımızda araç kilidini aç.
          {
              acKapat(); 
              kilit=true;
          }
        if(kilit){
              //araç hız kontrolü
              if (state == 0){
                Hiz=0;}
              else if (state == 1){
                Hiz=50;}
              else if (state == 2){
                Hiz=75;}
              else if (state == 3){
                Hiz=100;}
              else if (state == 4){
                Hiz=125;}
              else if (state == 5){
                Hiz=150;}
              else if (state == 6){
                Hiz=175;}
              else if (state == 7){
                Hiz=200;}
              else if (state == 8){
                Hiz=225;}
              else if (state == 9){
                Hiz=255;
              }
        
        
              
              if (state == 70) // ileri bastığımızda araç  ileri yönde hareket etsin.
              {
                duzgit();
              }
              if (state == 66) // geri'e bastığımızda araç geri yönde hareket etsin.
              {
                gerigit();
              }
              if (state == 82) // sol'a bastığımızda araç sola doğru hareket etsin.
              {
                soladon();
              }
              if (state == 76) // sağ'a bastığımızda araç sağa doğru hareket etsin.
              {
                sagadon();
              }
              if (state == 83) //  hiç birine basmadığımızda araç dursun.
              {
                dur();
              }
              if (state == 0) // kornaya bastığımızda araç korna çal.
              {
                digitalWrite(zil, HIGH);
                delay(500);
                 digitalWrite(zil, LOW);
              }
              if (state == 87) // ön far'a bastığımızda araç ön farı yak.
              {
                digitalWrite(onled2, HIGH);
                digitalWrite(onled1, HIGH);
                
              }
               if (state == 119) // ön far'a tekrar bastığımızda araç ön farı söndür.
              {
                digitalWrite(onled2, LOW);
                digitalWrite(onled1, LOW);
               
              }
              if (state == 69) // arka far'a bastığımızda araç arka farı yak.
              {
                
                digitalWrite(arkaled1, HIGH);
                digitalWrite(arkaled2, HIGH);
              }
               if (state == 101) // arka far'a tekrar bastığımızda araç arka farı söndür.
              {
                digitalWrite(arkaled1, LOW);
                digitalWrite(arkaled2, LOW);
              }
        
        
              
              if (state == 85) // sağ sinyal'e bastığımızda araç sağ sinyali aç.
              {
                sag_sinyal_durum=true;
                
              }
             
              if (state == 117) // sağ sinyal'e tekrar bastığımızda araç sağ sinyali kapat.
              {
                sag_sinyal_durum=false;
                digitalWrite(p1, LOW);
                digitalWrite(p2, LOW);
                digitalWrite(zil, LOW);
        
              }
              if (state == 86) // sol sinyal'e bastığımızda araç sol sinyali aç.
              {
                sol_sinyal_durum=true;
                
              }
             
              if (state == 118) // sol sinyal'e tekrar bastığımızda araç sol sinyali kapat.
              {
                sol_sinyal_durum=false;
                digitalWrite(p3, LOW);
                digitalWrite(p4, LOW);
                digitalWrite(zil, LOW);
              }
            
              
        
              if (state == 80) // full butonuna bastığımızda araç tüm ledleri yak.
              {
                digitalWrite(onled2, HIGH);
                digitalWrite(onled1, HIGH);
                digitalWrite(arkaled1, HIGH);
                digitalWrite(arkaled2, HIGH);
                digitalWrite(p1, HIGH);
                digitalWrite(p2, HIGH);
                digitalWrite(p3, HIGH);
                digitalWrite(p4, HIGH);
                
                
              }
        
              if (state == 112) // full butonuna tekrar bastığımızda araç tüm ledleri söndür.
              {
                digitalWrite(onled2, LOW);
                digitalWrite(onled1, LOW);
                digitalWrite(arkaled1, LOW);
                digitalWrite(arkaled2, LOW);
                digitalWrite(p1, LOW);
                digitalWrite(p2, LOW);
                digitalWrite(p3, LOW);
                digitalWrite(p4, LOW);
              }
                
              
              if (state == 90) // korna'a bastığımızda korna çal.
              {
                tone(zil,450);
                //digitalWrite(zil, HIGH);
              }
        
              if (state == 122) // ellimizi çektiğimizde korna kapat.
              {
                // digitalWrite(zil, LOW);
                noTone(zil);
                
              }
               if (state == 72) // park'a bastığımızda araç park sinyali aç.
              {
                  durum1=true;
              }
              if (state == 104) // park'a tekrar bastığımızda araç park sinyali kapat.
              {
                  durum1=false;
                  digitalWrite(zil, LOW);
                  digitalWrite(p1, LOW);
                  digitalWrite(p2, LOW);
                  digitalWrite(p3, LOW);
                  digitalWrite(p4, LOW);
                  
              }
              
               
        }
      
     

   }
   if(durum1){
        
          if(simdikizaman-eskizaman>=700)
          {
                if(durum==0){
                    digitalWrite(zil, HIGH);
                    digitalWrite(p1, HIGH);
                    digitalWrite(p2, HIGH);
                    digitalWrite(p3, HIGH);
                    digitalWrite(p4, HIGH);
                    durum=1;
                }
                else{
                    digitalWrite(zil, LOW);
                    digitalWrite(p1, LOW);
                    digitalWrite(p2, LOW);
                    digitalWrite(p3, LOW);
                    digitalWrite(p4, LOW);
                    durum=0;
                }
            
                    eskizaman=millis();
          }
      }
   if(sag_sinyal_durum){
      if(simdikizaman-eskizaman>=500)
          {
                if(sag_sinyal==0){
                    digitalWrite(zil, HIGH);
                    digitalWrite(p1, HIGH);
                    digitalWrite(p2, HIGH);
                    sag_sinyal=1;
                }
                else{
                    digitalWrite(zil, LOW);
                    digitalWrite(p1, LOW);
                    digitalWrite(p2, LOW);
                    sag_sinyal=0;
                }
            
                    eskizaman=millis();
          }
   }
   if(sol_sinyal_durum){
      if(simdikizaman-eskizaman>=500)
          {
                if(sol_sinyal==0){
                    digitalWrite(zil, HIGH);
                    digitalWrite(p3, HIGH);
                    digitalWrite(p4, HIGH);
                    sol_sinyal=1;
                }
                else{
                    digitalWrite(zil, LOW);
                    digitalWrite(p3, LOW);
                    digitalWrite(p4, LOW);
                    sol_sinyal=0;
                }
            
                    eskizaman=millis();
          }
   }
   
}

void acKapat(){
            digitalWrite(zil, HIGH);
            digitalWrite(p1, HIGH);
            digitalWrite(p2, HIGH);
            digitalWrite(p3, HIGH);
            digitalWrite(p4, HIGH);

                        delay(100);
            digitalWrite(zil, LOW);
            digitalWrite(p1, LOW);
            digitalWrite(p2, LOW);
            digitalWrite(p3, LOW);
            digitalWrite(p4, LOW);

            delay(100);
            digitalWrite(zil, HIGH);
            digitalWrite(p1, HIGH);
            digitalWrite(p2, HIGH);
            digitalWrite(p3, HIGH);
            digitalWrite(p4, HIGH);

                        delay(150);                       

            digitalWrite(zil, LOW);
            digitalWrite(p1, LOW);
            digitalWrite(p2, LOW);
            digitalWrite(p3, LOW);
            digitalWrite(p4, LOW);
            digitalWrite(onled2, LOW);
            digitalWrite(onled1, LOW);
            digitalWrite(arkaled1, LOW);
            digitalWrite(arkaled2, LOW);
            digitalWrite(gece1, LOW);
            digitalWrite(gece2, LOW);
}


void sol_motor(String dir, int spd) // sol motor kontrolü
{
  // yön(dir) ve hız(spd) için değişkenler.
  if ( dir == "ILERI")             // ILERI komutu ileri hareket için.
  {
    digitalWrite(SolMotor1, HIGH);
    digitalWrite(SolMotor2, LOW);
    analogWrite(SolMotorEN, spd);
  }
  if ( dir == "GERI")           //GERI komutu geriye hareket için.
  {
    digitalWrite(SolMotor1, LOW);
    digitalWrite(SolMotor2, HIGH);
    analogWrite(SolMotorEN, spd);
  }
}

void sag_motor(String dir, int spd)// sağ motor kontrolü
{ // yön(dir) ve hız(spd) için değişkenler.
  if ( dir == "ILERI")             // ILERI komutu ileri hareket için.
  {
    digitalWrite(SagMotor1, HIGH);
    digitalWrite(SagMotor2, LOW);
    analogWrite(SagMotorEN, spd);
  }

  if ( dir == "GERI")           //GERI komutu geriye hareket için.
  {
    digitalWrite(SagMotor1, LOW);
    digitalWrite(SagMotor2, HIGH);
    analogWrite(SagMotorEN, spd);
  }
}

void duzgit() // Robot ileri gitsin.
{
  sag_motor("ILERI", Hiz); // Sağ ve sol motorlar ileri yönde 200 hızında
  sol_motor("ILERI", Hiz);
}
void sagadon() // Araç sağa dönsün.
{
  sag_motor("GERI", Hiz); //Sağ motor 200 hızında geri
  sol_motor("ILERI", Hiz); //Sol motor 200 hızında ileri

}

void gerigit() // Araç geri gitsin.
{
  sag_motor("GERI", Hiz); //Sağ motor 200 hızında geri
  sol_motor("GERI", Hiz); //Sol motor 200 hızında geri
  
}

void soladon()
{
  sag_motor("ILERI", Hiz);
  sol_motor("GERI", Hiz);

}

void dur()
{
  sag_motor("ILERI", 0);
  sol_motor("ILERI", 0);

}
