import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;

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
    this.board = board;

    // check dimensions
    if (this.board.length != 9) {
      throw new InvalidParameterException("Expected board of size 9x9");
    }
    for (byte[] r : this.board) {
      if (r.length != 9) {
        throw new InvalidParameterException("Expected board of size 9x9");
      }
    }

    // check board
    if (!checkAll()) {
      throw new InvalidParameterException("Invalid board");
    }
  }

  /**
   * Solve the sudoku riddle in this.board using nondeterministic backtracking algorithm.
   *
   * @return whether the sudoku could be solved.
   * @see <a href="https://en.wikipedia.org/wiki/Sudoku_solving_algorithms#Backtracking">Wikipedia</a>
   */
  public boolean solve() {
    byte[][] originalBoard = this.board.clone(); // clone to reset array if no solution found
    boolean success = this.solve(this.board);
    if (!success) {
      this.board = originalBoard;
    }
    return success;
  }

  /**
   * Solves a given board. Used for recursive backtracking.
   *
   * @param board the board.
   * @return whether the the sudoku could be solved.
   */
  private boolean solve(byte[][] board) {
    this.board = board;
    for (byte row = 0; row < board.length; row++) {
      for (byte col = 0; col < board[row].length; col++) {
        if (board[row][col] == 0) {
          ArrayList<Byte> candidates = this.getCandidates(row, col);
          Collections.shuffle(candidates);
          for (byte n = 0; n < candidates.size(); n++) {
            board[row][col] = candidates.get((byte) n);
            if (this.solve(board)) {
              return true;
            }
            board[row][col] = 0;
          }
          return false;
        }
      }
    }
    return true; // sudoku solved
  }

  /**
   * Getter for the board field.
   *
   * @return the board.
   */
  public byte[][] getBoard() {
    return this.board;
  }

  /**
   * Check all cells.
   *
   * @return whether all cells are valid.
   */
  private boolean checkAll() {
    for (byte r = 0; r < this.board.length; r++) {
      for (byte c = 0; c < this.board[r].length; c++) {
        // set temporary to 0, otherwise would always be invalid because row, column and box already contain
        byte n = this.board[r][c];
        if (n == 0) {
          continue;
        }
        this.board[r][c] = 0;
        boolean valid = checkCell(n, r, c);
        this.board[r][c] = n;
        if (!valid) {
          return false;
        }
      }
    }
    return true;
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
    return this.checkRow(n, row) && this.checkCol(n, col) && this.checkBox(n, getBoxNum(row, col));
  }

  /**
   * Check if a number is valid in a specific row.
   *
   * @param n   the number (1-9).
   * @param row the row (0-8).
   * @return whether the row is valid.
   */
  private boolean checkRow(byte n, byte row) {
    for (byte c = 0; c < this.board[row].length; c++) {
      if (this.board[row][c] == n) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if a number is valid in a specific column.
   *
   * @param n   the number (1-9).
   * @param col the column (0-8).
   * @return whether the column is valid.
   */
  private boolean checkCol(byte n, byte col) {
    for (byte r = 0; r < this.board.length; r++) {
      if (this.board[r][col] == n) {
        return false;
      }
    }
    return true;
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
    for (byte r = 0; r < this.board.length; r++) {
      for (byte c = 0; c < this.board[r].length; c++) {
        if (this.getBoxNum(r, c) == box && n == this.board[r][c]) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Get the number of a box by the row and column. The boxes are numbered from left to right and top to bottom:
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
    if (row < 3) {
      if (col < 3) {
        return 0;
      } else if (col < 6) {
        return 1;
      } else if (col < 9) {
        return 2;
      }
    } else if (row < 6) {
      if (col < 3) {
        return 3;
      } else if (col < 6) {
        return 4;
      } else if (col < 9) {
        return 5;
      }
    } else if (row < 9) {
      if (col < 3) {
        return 6;
      } else if (col < 6) {
        return 7;
      } else if (col < 9) {
        return 8;
      }
    }
    return -1;
  }

  /**
   * Get the candidates (valid numbers) by row and column.
   *
   * @param row the row (0-9).
   * @param col the column (0-9).
   * @return an byte array containing all valid numbers.
   */
  private ArrayList<Byte> getCandidates(byte row, byte col) {
    ArrayList<Byte> candidates = new ArrayList<>();
    for (byte n = 1; n <= 9; n++) {
      if (this.checkCell(n, row, col)) {
        candidates.add(n);
      }
    }
    return candidates;
  }

}
