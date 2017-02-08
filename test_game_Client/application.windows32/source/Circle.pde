class Circle{
  int x;
  int y;
  int radius;
  Circle(int _x, int _y){
    x=_x;
    y=_y;
    radius=5;
  }
  
  void display(){
    fill(90);
    ellipse(x, y, radius*2, radius*2);
    noFill();
  }
}