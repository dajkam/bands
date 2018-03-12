import org.junit.Test;
import static org.junit.Assert.*;



import filip.projekt.bands.bandCRUD.domain.Band;

public class BandTest {
  @Test
  public void testBand(){
    Band motorhead = new Band("Motorhead","Rock And Roll",3);
    Band metallica = new Band("Metallica","Metal",4);
    Band volbeat = new Band("Volbeat","Groove Metal",5);

    motorhead.addMember(1);
    volbeat.removeMember(2);
    metallica.addMember(1);

    motorhead.play();
    volbeat.play();
    metallica.play();

    assertNotNull(motorhead);
    assertNotNull(metallica);
    assertNotNull(volbeat);

    Integer r = motorhead.get_NumberOfMembers();

    assertEquals(r, new Integer(4));

    r = metallica.get_NumberOfMembers();

    assertEquals(r, new Integer(5));

    r = volbeat.get_NumberOfMembers();

    assertEquals(r, new Integer(3));


  }

}
