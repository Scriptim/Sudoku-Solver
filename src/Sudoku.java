/**
 * @author Scriptim
 */
public class Sudoku {

  /**
   * Representation of the board. the first dimension contains the rows, the second dimension contains the columns.
   */
  private byte[][] board;

  /**
   * Constructor with input board.
   *
   * @param board is used to set the input board, that is going to be solved.
   */
  public Sudoku(byte[][] board) {
    // TODO: implement
  }

  /**
   * Solve the sudoku riddle in this.board using nondeterministic backtracking algorithm.
   *
   * @return whether the sudoku could be solved.
   * @see <a href="https://en.wikipedia.org/wiki/Sudoku_solving_algorithms#Backtracking">Wikipedia</a>
   */
  public boolean solve() {
    // TODO: implement
    return false;
  }

  /**
   * Getter for the board field.
   *
   * @return the board.
   */
  public byte[][] getBoard() {
    // TODO: implement
    return new byte[0][0];
  }

  /**
   * Check all cells.
   *
   * @return whether all cells are valid.
   */
  private boolean checkAll() {
    // TODO: implement
    return false;
  }

  /**
   * Check if number is valid in specific cell.
   *
   * @param n   the number (1-9).
   * @param row the row of the cell (0-8).
   * @param col the column of the cell (0-8).
   * @return whether the cell is valid.
   */
  private boolean checkCell(byte n, byte row, byte col) {
    // TODO: implement
    return false;
  }

  /**
   * Check if a number is valid in a specific row.
   *
   * @param n   the number (1-9).
   * @param row the row (0-8).
   * @return whether the row is valid.
   */
  private boolean checkRow(byte n, byte row) {
    // TODO: implement
    return false;
  }

  /**
   * Check if a number is valid in a specific column.
   *
   * @param n   the number (1-9).
   * @param col the column (0-8).
   * @return whether the column is valid.
   */
  private boolean checkCol(byte n, byte col) {
    // TODO: implement
    return false;
  }

  /**
   * Check if a number is valid in a specific box.
   *
   * @param n   the number (1-9).
   * @param box the box (0-8).
   * @return whether the box is valid.
   * @see #getBoxNum(byte, byte) for details on the box numbering.
   */
  private boolean checkBox(byte n, byte box) {
    // TODO: implement
    return false;
  }

  /**
   * Get the number of a box by the row and column. The boxes are numbered from left to right and top to 
bottom:
   * <table>
   * <tr><td>0</td><td>1</td><td>2</td></tr>
   * <tr><td>3</td><td>4</td><td>5</td></tr>
   * <tr><td>6</td><td>7</td><td>8</td></tr>
   * </table>
   *
   * @param row the row (0-8)
   * @param col the col (0-8)
   * @return the number of the box
   */
  private byte getBoxNum(byte row, byte col) {
    // TODO: implement
    return -1;
  }

}
