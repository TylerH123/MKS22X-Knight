import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class KnightBoard{
  class Spot implements Comparable<Spot>{
    int r,c,num;
    //new class spot for a spot on board
    public Spot(int row, int col){
      r = row;
      c = col;
      num = position[r][c];
    }
    //return row
    public int getRow(){
      return r;
    }
    //return col
    public int getCol(){
      return c;
    }
    //return value of num
    public int getVal(){
      return num;
    }
    //change value of num
    public void changeNum(int n) {
      if (n > 0){
        position[r][c] += n;
        num += n;
      }
    }
    //compare num
    public int compareTo(Spot sp) {
      if (getVal() > sp.getVal()){
        return 1;
      }
      if (getVal() == sp.getVal()){
        return 0;
      }
      return -1;
    }
    //string representain of spot
    public String toString(){
      return "[" + r + ", " + c + "]";
    }
  }
  private int[][] board, position;
  int rows,cols;
  private int[] rowDir = new int[]{1,1,-1,-1,2,2,-2,-2};
  private int[] colDir = new int[]{2,-2,2,-2,1,-1,1,-1};
  public KnightBoard(int startingRows, int startingCols){
    if (startingRows <= 0 || startingCols <= 0) throw new IllegalArgumentException();
    rows = startingRows;
    cols = startingCols;
    board = new int[rows][cols];
    position = new int[rows][cols];
    fillPosition();
  }
  //string representation of the board
  public String toString(){
    String output = "";
    for (int r = 0; r < board.length; r++){
      for (int c = 0; c < board[r].length;c++){
        if (board[r][c] == 0){
          output += " _ ";
        }
        else if (board[r][c] < 10){
          output += " " + board[r][c] + " ";
        }
        else{
          output += board[r][c] + " ";
        }
      }
      output += "\n";
    }
    return output;
  }
  private void fillPosition(){
    int n = 0;
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (r > 1 && c > 1 && r < (rows - 2) && c < (cols - 2)) {
          position[r][c] = 8;
        }
        else {
          for (int i = 0; i < 8; i++) {
            int newPosX = r + rowDir[i];
            int newPosY = c + colDir[i];
            if (checkSpot(newPosX, newPosY)) {
              n++;
            }
          }
          position[r][c] = n;
          n = 0;
        }
      }
    }
  }
  //check if the spot is taken
  private boolean checkSpot(int r, int c) {
    return ((r >= 0) && (r < rows) && (c >= 0) && (c < cols) && (board[r][c] == 0));
  }
  //reorders the moves
  private ArrayList<Spot> reorder(int r, int c) {
    ArrayList<Spot> ary = new ArrayList<Spot>();
    for (int i = 0; i < 8; i++) {
      int newPosX = r + rowDir[i];
      int newPosY = c + colDir[i];
      if (checkSpot(newPosX, newPosY)) {
        ary.add(new Spot(newPosX, newPosY));
      }
    }
    Collections.sort(ary);
    return ary;
  }
  //changes the value of the spots
  private void changeSpot(int n, ArrayList<Spot> ary) {
    for (Spot sp : ary) {
      sp.changeNum(n);
    }
  }
  //check to make see if row and col is inside or outside of the board
  public boolean checkBounds(int row, int col){
    return (row >= board.length || row < 0 || col >= board[0].length || col < 0);
  }
  //check board to make sure it starts with zeros
  public boolean checkBoard(){
    for (int r = 0; r < board.length;r++){
      for (int c = 0; c < board[r].length;c++){
        if (board[r][c] != 0) return false;
      }
    }
    return true;
  }
  public boolean solveSlow(int startingRow, int startingCol){
    if (!checkBoard()) throw new IllegalStateException();
    if (checkBounds(startingRow,startingCol)) throw new IllegalArgumentException();
    return solveH(startingRow,startingCol,1);
  }
  private boolean solveH(int row, int col, int level){
    //if loc is outside board, return false
    if (checkBounds(row,col)){
      return false;
    }
    //if all pieces on the board have been visited then level must be equal to 1 greater than size of board
    if (level == board.length * board[0].length + 1 ){
      return true;
    }
    //if location r,c is occupied return false
    if (board[row][col] != 0){
      return false;
    }
    //if location r,c is not occupied, then move knight there
    board[row][col] = level;
    //go through all possible locations the knight can go to
    if (solveH(row-2,col+1,level+1) ||
        solveH(row-2,col-1,level+1) ||
        solveH(row+2,col-1,level+1) ||
        solveH(row+2,col+1,level+1) ||
        solveH(row-1,col+2,level+1) ||
        solveH(row+1,col+2,level+1) ||
        solveH(row-1,col-2,level+1) ||
        solveH(row+1,col-2,level+1))
        return true;
    else{
      //if none of the moves are possible, then backtrack and remove the knight
      board[row][col] = 0;
    }
    return false;
  }
  //optimized solve
  public boolean solve(int startingRow, int startingCol){
    if (!checkBoard()) throw new IllegalStateException();
    if (checkBounds(startingRow,startingCol)) throw new IllegalArgumentException();
    fillPosition();
    return solveO(startingRow, startingCol, 1);
  }
  public boolean solveO(int row, int col, int level){
    //base case
    if (level - 1 == rows * cols) {
      return true;
    }
    if (level == 1) {
      board[row][col] = level;
      return solveO(row, col, level + 1);
    }
    ArrayList<Spot> om = reorder(row, col);
    changeSpot(-1, om);
    int oldNum = position[row][col];
    position[row][col] = 0;
    for (int i = 0; i < om.size(); i++) {
      Spot sp = om.get(i);
      int newPosX = sp.getRow();
      int newPosY = sp.getCol();
      board[newPosX][newPosY] = level;
      if (solveO(newPosX, newPosY, level + 1)) {
        return true;
      }
      board[newPosX][newPosY] = 0;
    }
    position[row][col] = oldNum;
    return false;
  }
  public int countSolutions(int startingRow, int startingCol){
    if (!checkBoard()) throw new IllegalStateException();
    if (checkBounds(startingRow,startingCol)) throw new IllegalArgumentException();
    return countSolutions(startingRow,startingCol,1);
  }
  public int countSolutions(int row, int col, int level){
    //if loc is outside board, return 0
    if (checkBounds(row,col)){
      return 0;
    }
    //if location r,c is occupied return 0
    if (board[row][col] != 0){
      return 0;
    }
    //if all pieces on the board have been traveled to, level will be equal to the area
    //return 1
    if (level == board.length * board[0].length){
      return 1;
    }
    int count = 0;
    for (int i = 0; i < 8; i++){
      board[row][col] = level;
      count += countSolutions(row+rowDir[i], col+colDir[i], level + 1);
      board[row][col] = 0;
    }
    return count;
  }
  public static void main(String[] args){
    KnightBoard k = new KnightBoard(5,5);

  }
}
