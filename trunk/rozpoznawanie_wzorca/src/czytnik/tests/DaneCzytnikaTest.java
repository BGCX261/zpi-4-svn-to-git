package czytnik.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import czytnik.DaneCzytnika;

public class DaneCzytnikaTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testGetId() {
    int id = 0;
    DaneCzytnika czyt = new DaneCzytnika(id);
    assertEquals(id, czyt.getId());
  }

  @Test
  public void testSetId() {
    int id = 0;
    DaneCzytnika czyt = new DaneCzytnika(id);
    int noweId = 10;
    czyt.setId(10);
    assertEquals(noweId, czyt.getId());
  }
}
