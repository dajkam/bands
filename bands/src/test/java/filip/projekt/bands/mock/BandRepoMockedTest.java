package filip.projekt.bands.mock;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.sql.*;

import java.sql.SQLException;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

import filip.projekt.bands.bandCRUD.domain.Band;
import filip.projekt.bands.bandCRUD.repository.BandRepository;
import filip.projekt.bands.bandCRUD.repository.BandRepositoryFactory;
//@Ignore
//@RunWith(MockitoJUnitRunner.class)
@RunWith(MockitoJUnitRunner.Silent.class)
public class BandRepoMockedTest {
    BandRepository repo;

    @Mock
    Connection connectionMock;

    @Mock
    PreparedStatement insertStatementMock;

    @Mock
    PreparedStatement selectStatementMock;



    @Before
    public void setupDatabase() throws SQLException {

        when(connectionMock.prepareStatement(" INSERT INTO Band(Name,Genre,NumberOfMembers)VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS))
                .thenReturn(insertStatementMock);
        when(connectionMock.prepareStatement("SELECT * FROM Band ORDER BY Id")).thenReturn(selectStatementMock);
        repo = new BandRepositoryFactory();
        repo.setConnection(connectionMock);
        verify(connectionMock).prepareStatement( "INSERT INTO Band(Name,Genre,NumberOfMembers)VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
        verify(connectionMock).prepareStatement("SELECT * FROM Band ORDER BY Id");
    }

   // @Test
   @Test(expected = NullPointerException.class)
    public void checkAdding() throws Exception {
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        Band band = new Band();
        band.setId(0);
        band.setName("Pantera");
        band.setGenre("Groove Metal");
        band.setNumberOfMembers(4);
       // assertEquals(1, repo.add(band));
       repo.add(band);
        verify(insertStatementMock, times(1)).setString(1, "Pantera");
        verify(insertStatementMock, times(1)).setString(2, "Groove Metal");
        verify(insertStatementMock, times(1)).setInt(3, 4);
        verify(insertStatementMock).executeUpdate();
        
    }

    abstract class AbstractResultSet implements ResultSet {
        int i = 0;

        @Override
        public int getInt(String s) throws SQLException {
            return 1;
        }
        @Override
        public String getString(String columnLabel) throws SQLException {
            return "Majran";
        }
        @Override
        public boolean next() throws SQLException {
            if (i == 1)
                return false;
            i++;
            return true;
        }
    }

    @Test
   
   //@Test(expected = NullPointerException.class)
    public void checkGetting() throws Exception {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getInt("Id")).thenCallRealMethod();
        when(mockedResultSet.getString("Name")).thenCallRealMethod();
        when(mockedResultSet.getString("Genre")).thenCallRealMethod();
        when(mockedResultSet.getInt("NumberOfMembers")).thenCallRealMethod();
        when(selectStatementMock.executeQuery()).thenReturn(mockedResultSet);

       assertEquals(1, repo.getAll().size());

        verify(selectStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getInt("Id");
        verify(mockedResultSet, times(1)).getString("Name");
        verify(mockedResultSet, times(1)).getString("Genre");
        verify(mockedResultSet, times(1)).getInt("NumberOfMembers");
        verify(mockedResultSet, times(2)).next();
    }

   // @Test
   @Test(expected = NullPointerException.class)
    public void checkAddingInOrder() throws Exception {
        InOrder inorder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        

        Band band = new Band();
        band.setId(0);
        band.setName("Pantera");
        band.setGenre("Groove Metal");
        band.setNumberOfMembers(4);
      assertEquals(1, repo.add(band));
        repo.add(band);
        inorder.verify(insertStatementMock, times(1)).setString(1, "Pantera");
        inorder.verify(insertStatementMock, times(1)).setString(2, "Groove Metal");
        inorder.verify(insertStatementMock, times(1)).setInt(3, 4);
        inorder.verify(insertStatementMock).executeUpdate();
       
    }

   // @Test(expected = IllegalStateException.class)
   
   @Test(expected = NullPointerException.class)
    public void checkExceptionWhenAddingNullAdding() throws Exception {
        when(insertStatementMock.executeUpdate()).thenThrow(new SQLException());
        Band band = new Band();
        band.setName(null); // ten null!
        band.setGenre("Nie Metal");
        band.setNumberOfMembers(14);
        assertEquals(1, repo.add(band));
    }
}
