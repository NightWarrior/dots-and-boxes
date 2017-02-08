DotGrid dg;
BoxGrid bg;
ScoreGrid sg;
Score score;

int player;
int clientPlayer;
int gridSize;

ClientSocket client;
ClientWriter clientWriter;

LineRect l1, l2;
void setup() {      // TODO: Display dialogues for player to wait his turn and do his turn
  size(600, 600);
  background(255);
  textSize(26);

  textSize(25);
  fill(0);
  text("Connecting to Server...", 0, 0);

  clientWriter = new ClientWriter();
  while (clientWriter.ready() == null);
  client = new ClientSocket();
  client.start();
  while (client.ready() == null); // halting until the client thread has initialized.
  clientPlayer = client.gePlayerNumber();
  System.out.println("You got Player Number: "+clientPlayer);
  player = 0;

  gridSize = 7;

  dg  = new DotGrid(gridSize);
  bg = new BoxGrid(gridSize);
  sg = new ScoreGrid(gridSize);
  score = new Score();
}


void draw() {
  background(255);
  bg.display();
  dg.display();
  sg.display();
  score.display();
}


void mousePressed() {
  if (player == clientPlayer) {
    bg.checkHit();
  }
}

void keyPressed() {
  if (key == 'Q' || key == 'q') {
    client.closeSocket();
    exit();
  }
}