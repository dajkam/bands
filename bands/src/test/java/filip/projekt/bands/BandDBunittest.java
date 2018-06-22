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
        //super.tearDown();
       
    }

    @Before
    public void setUp() throws Exception {
       // super.setUp();
        repo = new BandRepositoryFactory(DriverManager.getConnection(url));
    }

    @Test
    public void doNothing() {
      // assertEquals(4, repo.getAll().size());
    }

    @Test
    public void checkAdding() throws Exception {
    
        Band band = new Band();
          /*
        band.setName("Pantera");
        band.setGenre("Groove Metal");
        band.setNumberOfMembers(4);

        assertEquals(1, repo.add(band));
        */
        

        // Data verification

       

        IDataSet dbDataSet = this.getConnection().createDataSet();
        ITable actualTable = dbDataSet.getTable("Band");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[] { "ID" });
        IDataSet expectedDataSet = getDataSet("ds-0.xml");
        ITable expectedTable = expectedDataSet.getTable("Band");
        // (posortowane? proszę bardzo:) // Assertion.assertEquals(new SortedTable(expectedTable),
        // (posortowane? proszę bardzo:) //     new SortedTable(filteredTable, expectedTable.getTableMetaData()));
      // repo.deleteFromBand(band);
        Assertion.assertEquals(expectedTable , filteredTable);
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
