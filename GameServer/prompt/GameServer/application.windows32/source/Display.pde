import java.util.*;

class Display {
  Queue<String> qq;
  //PriorityQueue<String> qq;
  int i=0;
  public Display() {
    qq = new LinkedList<String>();
  }
  void print(String s) {
    qq.add(s);
    i++;
    if(i>32){
      qq.remove();
    }
  }
  void show() {
    fill(255);
    rect(50, 50, 700, 500);
    fill(0);
    int i = 0;
    for (String ss : qq) {
      text(ss, 60, 60+i, 720, 520);
      i+=15;
    }
  }
}