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

  public boolean solve(int startingRow, int startingCol){
    return solve(startingRow,startingCol,0);
  }

  public boolean solve(int startingRow, int startingCol,int level){
    if (startingRow < 0 || startingRow > board.length || startingCol < 0 || startingCol > board[0].length){
      return false;
    }
    if (level == board.length * board[0].length){
      return true;
    }
    for (int r = 0; r < board.length; r++){
      if (solve(startingRow-2,startingCol+1,level+1)){
        board[startingRow][startingCol] = level;
        return true;
      }
      if (solve(startingRow-2,startingCol-1,level+1)){
        board[startingRow][startingCol] = level;
        return true;
      }
      if (solve(startingRow+1,startingCol-2,level+1)){
        board[startingRow][startingCol] = level;
        return true;
      }
      if (solve(startingRow-1,startingCol-2,level+1)){
        board[startingRow][startingCol] = level;
        return true;
      }
      if (solve(startingRow-1,startingCol+2,level+1)){
        board[startingRow][startingCol] = level;
        return true;
      }
      if (solve(startingRow+1,startingCol+2,level+1)){
        board[startingRow][startingCol] = level;
        return true;
      }
      if (solve(startingRow-2,startingCol-1,level+1)){
        board[startingRow][startingCol] = level;
        return true;
      }
      if (solve(startingRow-2,startingCol+1,level+1)){
        board[startingRow][startingCol] = level;
        return true;
      }
    }
    return false;
  }
  public static void main(String[] args){
    KnightBoard k = new KnightBoard(4,4);
    k.board[0][1] = 10;
    k.board[0][2] = 1;
    System.out.print(k.toString());
  }
}
