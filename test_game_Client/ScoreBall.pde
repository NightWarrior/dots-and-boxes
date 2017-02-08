class ScoreBall {
  int x, y, r;
  color clr;
  boolean marked;
  ScoreBall(int _x, int _y, int _r) {
    x = _x;
    y = _y;
    r = _r;
    clr = color(240);
    marked = false;
  }

  void display() {
    fill(clr);
    noStroke();
    ellipse(x, y, r*2, r*2);
    noFill();
    stroke(0);
  }

  void mark() {
    if (marked)
      return;
    marked = true;
    score.increaseScore(player);
    if (player == 1)
      clr = color(200, 10, 10);
    else
      clr = color(10, 10, 200);
  }
  
  boolean isMarked(){
    return marked;
  }
}