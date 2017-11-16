import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class PercolationTest {

  private Percolation grid10; 

  @Before
  public void setup() {
    grid10 = new Percolation(10);
  }

  @Test
  public void testCloseSites() {
    for(int i = 1; i <= 10; i++) {
      for(int j = 1; j <= 10; j++) {
        assertFalse("Site " + i + "," + j + " should start closed", 
            grid10.isOpen(i, j));
      }
    }
  }

  @Test
  public void testOpen() {
    grid10.open(1, 1);
    assertTrue(grid10.isOpen(1, 1));
  }

  @Test
  public void testOpenAdjacentClosed() {
    grid10.open(1, 1);
    assertFalse("You should not open an adjacent site, you should union them",
        grid10.isOpen(1, 2));
  }

  @Test
  public void testNumberOfOpenSites() {
    grid10.open(1, 1);
    assertEquals("Should have 1 site open", 1, grid10.numberOfOpenSites());
  }

  @Test
  public void testIsFull() {
    grid10.open(1, 1);
    assertTrue("1,1 should translate to the top left corner (traditionally 0,0)"
        + "and be full if it is opened", grid10.isFull(1, 1));
  }

  @Test
  public void testIsFullFalse() {
    grid10.open(1, 1);
    grid10.open(1, 3);
    assertFalse("1,3 should not be full as it is not connected to top",
        grid10.isFull(1, 3));
  }

  @Test
  public void testIsFullConnection() {
    grid10.open(1, 1);
    grid10.open(1, 3);
    grid10.open(1, 2);
    assertTrue("1, 3 is connected to the top and thus should be full", 
        grid10.isFull(1, 3));
  }

  private Percolation generatePercolation(String filename) {
    In in = new In("percolation-test-files/" + filename);      // input file
    int n = in.readInt();         // n-by-n percolation system  
    // repeatedly read in sites to open 
    Percolation perc = new Percolation(n); 
    while (!in.isEmpty()) {
      int i = in.readInt();
      int j = in.readInt();
      perc.open(i, j);
    }
    return perc;
  }

  @Test
  public void testPercolatesFalse() {
    File folder = new File("percolation-test-files");

    for (File file : folder.listFiles()) {
      if (file.isFile()&&(file.getName().substring(file.getName().lastIndexOf('.')+1).equals("txt"))) {
        if (file.getName().contains("no")) {
          Percolation perc = generatePercolation(file.getName());
          assertFalse(file.getName() + " should not percolate", perc.percolates());
        }
      }
    }
  }
  
  @Test
  public void testPercolates() {
    File folder = new File("percolation-test-files");
    for (File file : folder.listFiles()) {
      if (file.isFile()&&(file.getName().substring(file.getName().lastIndexOf('.')+1).equals("txt"))) {
        if (!file.getName().contains("no")) {
          Percolation perc = generatePercolation(file.getName());
          assertTrue(file.getName() + " should percolate", perc.percolates());
        }
      }
    }
  }
  
  @Test
  public void testBackwash() {
    Percolation input10 = generatePercolation("input10.txt");
    assertFalse("Bottom left site is not connected to the top so should not be full",
                  input10.isFull(10, 1));
  }



}
