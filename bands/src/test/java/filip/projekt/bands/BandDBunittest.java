package filip.projekt.bands;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeNoException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;

import org.hamcrest.core.IsEqual;
import org.hsqldb.persist.PersistentStoreCollection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import java.net.URL;
import java.sql.DriverManager;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;



import filip.projekt.bands.bandCRUD.domain.Band;
import filip.projekt.bands.bandCRUD.repository.BandRepository;
import filip.projekt.bands.bandCRUD.repository.BandRepositoryFactory;

@RunWith(JUnit4.class)

public class BandDBunittest extends DBTestCase {

	public static String url = "jdbc:hsqldb:hsql://localhost/workdb";

    BandRepository repo;

    @After
    public void tearDown() throws Exception {
      //  super.tearDown();
      //repo.deleteAll();
       
    }

    @Before
    public void setUp() throws Exception {
      // super.setUp();
        repo = new BandRepositoryFactory(DriverManager.getConnection(url));

       FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(
            BTest.class.getClassLoader().
                        getResource("ds-0.xml").openStream()
        );
        JdbcDatabaseTester databaseTester = new PropertiesBasedJdbcDatabaseTester();
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @Test
    public void doNothing() {
      // assertEquals(3, repo.getAll().size());
    }

    @Test
    public void checkAdding() throws Exception {
    
        Band band = new Band();
          
        band.setName("Pantera");
        band.setGenre("Groove Metal");
        band.setNumberOfMembers(4);

        assertEquals(1, repo.add(band));
        
        

        // Data verification

       

        IDataSet dbDataSet = this.getConnection().createDataSet();
        ITable actualTable = dbDataSet.getTable("Band");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[] { "ID" });
        IDataSet expectedDataSet = getDataSet("ds-2.xml");
        ITable expectedTable = expectedDataSet.getTable("Band");
        // (posortowane? proszę bardzo:) // Assertion.assertEquals(new SortedTable(expectedTable),
        // (posortowane? proszę bardzo:) //     new SortedTable(filteredTable, expectedTable.getTableMetaData()));
      // repo.deleteFromBand(band);
     // assertEquals(4, repo.getAll().size());
        Assertion.assertEquals(expectedTable , filteredTable);
        repo.deleteFromBand(band);
                                     // wyczyszczenie
    }
    
    @Test
    public void checkDeleteByName() throws Exception {
   
        Band band = new Band();

        band.setName("Def Leppard");
        band.setGenre("Glam Metal");
        band.setNumberOfMembers(5);

        if (!repo.searchBand(band.getName()).isEmpty()) {
            repo.deleteFromBandByName(band);
            
        }

      
       repo.deleteFromBandByName(band);

       Band band2 = new Band();
            band2.setName("Pantera");
            band2.setGenre("Groove Metal");
            band2.setNumberOfMembers(4);
            if (!repo.searchBand(band2.getName()).isEmpty()) {
                repo.deleteFromBandByName(band2);
                
            }
            
        

      

       

        IDataSet dbDataSet = this.getConnection().createDataSet();
        ITable actualTable = dbDataSet.getTable("Band");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[] { "ID" });
        IDataSet expectedDataSet = getDataSet("ds-3.xml");
        ITable expectedTable = expectedDataSet.getTable("Band");
        
       
      
        Assertion.assertEquals(expectedTable , filteredTable);
                                     // wyczyszczenie
    }

    @Test
    public void checkUpdating() throws Exception {
        


        Band band = new Band();
        
        band.setName("Def Leppard");
        band.setGenre("Glam Metal");
        band.setNumberOfMembers(5);

        Band band2 = new Band();
          
        band2.setName("Pantera");
        band2.setGenre("Groove Metal");
        band2.setNumberOfMembers(4);

     
        
        

       if (!repo.searchBand(band.getName()).isEmpty()) {
             repo.deleteFromBandByName(band);
           
       }

       if (!repo.searchBand(band2.getName()).isEmpty()) {
            repo.deleteFromBandByName(band2);
      
  }

        Band band3 = new Band();
                
        band3.setName("Descotica");
        band3.setGenre("Disco Polo");
        band3.setNumberOfMembers(4);
        band3.setId(repo.searchBand("Metallica").get(0).getId());

        Band band4 = new Band();
                
        band4.setName("Discohead");
        band4.setGenre("Rock Polo");
        band4.setNumberOfMembers(3);
        band4.setId(repo.searchBand("Motorhead").get(0).getId());

        repo.updateBand(band3);

        repo.updateBand(band4);
        

       

        IDataSet dbDataSet = this.getConnection().createDataSet();
        ITable actualTable = dbDataSet.getTable("Band");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[] { "ID" });
        IDataSet expectedDataSet = getDataSet("ds-4.xml");
        ITable expectedTable = expectedDataSet.getTable("Band");
       
   //   assertEquals(4, repo.getAll().size());
        Assertion.assertEquals(expectedTable , filteredTable);
       // repo.deleteFromBand(band);
                                     // wyczyszczenie
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.INSERT;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return this.getDataSet("ds-1.xml");
    }

    /**
    * Returns dataset for selected resource
    * @param datasetName filename in resources
    * @return flat xml data set
    * @throws Exception when there is a problem with opening dataset
    */
    protected IDataSet getDataSet(String datasetName) throws Exception {
        URL url = getClass().getClassLoader().getResource(datasetName);
        FlatXmlDataSet ret = new FlatXmlDataSetBuilder().build(url.openStream());
        return ret;
    }

}
