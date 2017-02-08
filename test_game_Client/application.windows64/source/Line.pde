class LineRect {
  int x;
  int y;
  int w;
  int h;
  float angle;
  color clr;
  boolean marked;

  LineRect(int _x, int _y, int len, int rot) {
    x = _x;
    y = _y-3;
    h=5;
    w=len;
    clr = color(210);
    marked= false;
    if (rot == 0)
      angle = 0;
    else if (rot==1) {
      angle = PI/2;
      x+=3;
      y+=3;
    }
  }


  void display() {
    noStroke();
    if (angle==PI/2) {
      pushMatrix();
      fill(clr);
      translate(x, y);
      rotate(angle);
      rect(0, 0, w, h);
      noFill();
      popMatrix();
    } else {
      fill(clr);
      rect(x, y, w, h);
      noFill();
    }
    stroke(1);
  }

  void mark(int pos) {
    marked = true;
    if (player == 1)
      clr = color(200, 10, 10);
    else
      clr = color(10, 10, 200);
    if (!sg.checkScore()) {
      try {
        if (pos != -1)
          clientWriter.sendHit(pos);
      }
      catch(Exception e) {
        System.out.println(e);
      }
      player = (player+1)%2;
      System.out.println("Player changed to: "+player);
      System.out.println("ClientPlayer is:   "+clientPlayer);
    } else {
      try {
        if (pos != -1)
          clientWriter.sendHit(pos+200);
      }
      catch(Exception e) {
        System.out.println(e);
      }
    }
  }

  boolean checkHit() {
    if (marked==true)
      return false;
    if (angle==0) {
      if (mouseX>=x+6 && mouseX<=x+w-6 && mouseY>=y-1 && mouseY<=y+h+1)
        return true;
    } else {
      boolean rotCheck = false;
      if (mouseX>=x-h-1 && mouseX<=x+1 && mouseY>=y+6 && mouseY<=y+w-6)
        rotCheck = true;
      return rotCheck;
    }
    return false;
  }

  boolean isMarked() {
    return marked;
  }
}