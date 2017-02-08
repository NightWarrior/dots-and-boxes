class BoxGrid {
  LineRect[] linesh;
  LineRect[] linesv;
  int distanceFactor;
  int row;
  int col;

  BoxGrid(int size) {
    int count = 0;
    row = col = size;
    distanceFactor = (width/(row+1));

    linesh = new LineRect[(row-1)*(col)];
    linesv = new LineRect[(row)*(col-1)];

    for (int i =1; i<=row-1; i++)
      for (int j =1; j<=col; j++) {
        linesh[count++] = new LineRect(i*distanceFactor, j*distanceFactor, distanceFactor, 0);
      }

    count = 0;
    for (int i =1; i<=row; i++)
      for (int j =1; j<=col-1; j++) {
        linesv[count++] = new LineRect(i*distanceFactor, j*distanceFactor, distanceFactor, 1);
      }
  }

  void display() {
    for (int i =0; i<linesh.length; i++) {
      linesh[i].display();
      linesv[i].display();
    }
  }

  void checkHit() {
    for (int i =0; i<linesh.length; i++) {
      if (linesh[i].checkHit()) {
        //clientWriter.sendHit(100+i); // supposing 100<=x<200 number are horizontal lines
        linesh[i].mark(100+i);
      }
      if (linesv[i].checkHit()) {
        //clientWriter.sendHit(200+i); // supposing 200<=x<300 number are vertical lines
        linesv[i].mark(200+i);
      }
    }
  }

  void hit(int pos) {
    System.out.println("Received: "+pos);
    if (pos>=100 && pos<200) { // meaning if position is 100s  
      linesh[pos-100].mark(-1);
    } else if (pos>=200 && pos<300) {  
      linesv[pos-200].mark(-1);
    }
  }

  boolean checkBox(int num) {
    int hnum = num;
    if (num!=0 && num!=1)
      hnum+=num/(row-1);

    if (linesh[hnum].isMarked() && linesh[hnum+1].isMarked()
      && linesv[num].isMarked() && linesv[num+col-1].isMarked())
      return true;

    return false;
  }
}