public class KnightBoard{

  private int[][] board;

  public KnightBoard(int startingRows, int startingCols){
    if (startingRows < 0 || startingCols < 0) throw new IllegalArgumentException();
    board = new int[startingRows][startingCols];
  }
  //string representation of the board
  public String toString(){
    String output = "";
    for (int r = 0; r < board.length; r++){
      for (int c = 0; c < board[r].length;r++){
        if (board[r][c] == 0){
          output += "_ ";
        }
        else if (board[r][c] < 10){
          output += " " + board[r][c] + " ";
        }
        else{
          output += board[r][c] + " ";
        }
      }
    }
    return output;
  }

  public static void main(String[] args){
    KnightBoard k = new KnightBoard(4,4);
    k.board[0][1] = 10;
    k.board[0][2] = 1;
    System.out.print(k.toString());
  }
}
