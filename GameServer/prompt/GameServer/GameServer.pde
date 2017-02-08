ServerThread server;
   
Display d ;
  void setup(){
  size(800, 600);
  background(255);
  d = new Display();
  for(int i=0;i<20;i++){
  d.print("server"+i);
  }
  
 
}
void draw(){
  d.show();
  
}
void keyPressed(){
  d.print("Hello");
 if(key=='Q' || key=='q')
   exit();
}