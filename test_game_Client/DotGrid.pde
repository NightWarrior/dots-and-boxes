class DotGrid{
  Circle[] dots;
  int distanceFactor;
  int row;
  int col;
  
  DotGrid(int size){
    int count = 0;
    row = col = size;
    distanceFactor = (width/(row+1));
    
    dots = new Circle[row*col];
    for(int i =1; i<=row; i++)
      for(int j =1; j<=col; j++){
        dots[count++] = new Circle(i*distanceFactor, j*distanceFactor);
      }
  }
  
  void display(){
   for(int i =0; i<dots.length; i++)
     dots[i].display();
  }
}