public class KnightBoard{

  private int[][] board;

  public KnightBoard(int startingRows, int startingCols){
    if (startingRows <= 0 || startingCols <= 0) throw new IllegalArgumentException();
    board = new int[startingRows][startingCols];
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
  //check to make sure row or col not out of bounds
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
  public boolean solve(int startingRow, int startingCol){
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
    if (level == board.length * board[0].length + 1){
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
  public int countSolutions(int startingRow, int startingCol){
    if (!checkBoard()) throw new IllegalStateException();
    if (checkBounds(startingRow,startingCol)) throw new IllegalArgumentException();
    return countSolutions(startingRow,startingCol,1);
  }
  public int countSolutions(int row, int col, int level){
    int count = 0;
    return count;
  }
  public static void main(String[] args){
    KnightBoard k = new KnightBoard(2,5);
    //k.board[0][1] = 10;
    //k.board[0][2] = 1;
    //System.out.print(k.toString());
    System.out.println(k.solve(0,0));
    System.out.println(k.toString());
  }
}
