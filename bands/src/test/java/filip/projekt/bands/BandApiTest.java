package filip.projekt.bands;

import filip.projekt.bands.bandCRUD.domain.Band;
import filip.projekt.bands.bandCRUD.repository.BandRepository;
import filip.projekt.bands.bandCRUD.repository.BandRepositoryFactory;

import org.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;

@Ignore
public class BandApiTest {

    BandRepositoryFactory repo;

    private final static String B_NAME_1 = "Motorhead";
    private final static String B_Genre_1 = "Rock And Roll";
    private final static int B_MEMBERS_1 = 3;
    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";

    
   
   

    @Before

    public void start() throws SQLException {
        
        BandRepository repo = new BandRepositoryFactory(DriverManager.getConnection(url));
       //repo.createTableBand();

       repo = new BandRepositoryFactory();        
    }

    @After

    public void clear() throws SQLException {
      //repo.dropTableBand();
        
    }

    @Test
    public void checkCon() {
        System.out.println(repo.getConnection().toString());
        assertNotNull(repo.getConnection());
        
    }

    @Test
    public void checkAdding () throws SQLException {
        Band b = new Band();
        b.setName(B_NAME_1);
        b.setGenre(B_Genre_1);
       b.setNumberOfMembers(B_MEMBERS_1);

       repo.deleteAll();        
        assertEquals(1, repo.add(b));

        List<Band> bands = repo.getAll();

        Band b2 = bands.get(0);

        assertEquals(B_NAME_1, b2.getName());
        assertEquals(B_Genre_1, b2.getGenre());
        assertEquals(B_MEMBERS_1, b2.getNumberOfMembers());

        }




    
}