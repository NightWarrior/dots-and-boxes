class Score {
  int p1, p2;
  Score() {
    p1 = 0;
    p2 = 0;
  }

  void display() {
    fill(color(10, 10, 200));
    textAlign(LEFT);
    text(p1, width/2-width/10, height/18);
    fill(color(200, 10, 10));
    textAlign(RIGHT);
    text(p2, width/2+width/10, height/18);
    noFill();


    textAlign(LEFT);
    if (player == clientPlayer) {
      fill(color(10, 10, 200));
      text("Your turn", 20, height/18);
    } else {
      fill(color(200, 10, 10));
      text("Opponent's turn", 20, height/18);
    }
    noFill();
  }

  void increaseScore(int n) {
    if (n==0)
      p1++;
    else
      p2++;
  }
}