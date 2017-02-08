LobbyThread lobby;
Display d;

void setup() {
  size(800, 600);
  background(255);
  d = new Display();
  lobby = new LobbyThread();
  lobby.start();
}

void draw() {
  d.show();
}

void keyPressed() {
  if (key=='Q' || key=='q')
    exit();
}