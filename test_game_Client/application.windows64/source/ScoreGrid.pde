class ScoreGrid {
  ScoreBall [] balls;
  //boolean [][] marks; 
  int row;
  int col;
  ScoreGrid(int size) {
    row = col = size-1;
    balls = new ScoreBall[row*col];
    //marks = new boolean[row][col];
    int count=0;
    int distanceFactor = width/(size+1);


    for (int i =1; i<=row; i++)
      for (int j = 1; j<=col; j++) {
        balls[count++] = new ScoreBall(distanceFactor/2+i*distanceFactor, distanceFactor/2+j*distanceFactor, (int)(distanceFactor/4));
        //marks[i-1][j-1] = false;
      }
  }

  void display() {
    for (int i =0; i<balls.length; i++)
      balls[i].display();
  }

  boolean checkScore() {
    int count = 0;
    boolean scored = false;
    for (int i =0; i<row; i++) {
      for (int j =0; j<col; j++) {
        if (!balls[count].isMarked()) {
          if (bg.checkBox(count)) {
            scored = true;
            balls[count].mark();
          }
        }

        count++;
      }
    }
    return scored;
  }
}