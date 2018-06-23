package filip.projekt.bands.bandCRUD.repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import filip.projekt.bands.bandCRUD.domain.Band;
import  filip.projekt.bands.bandCRUD.repository.BandRepository;
@Component
public class BandRepositoryFactory implements BandRepository{

  private Connection con;
  private PreparedStatement addSt;
  private PreparedStatement getAllSt;
  private PreparedStatement deleteFromBandSt;
  private PreparedStatement selectFromBandSt;
  private PreparedStatement updateBandSt;
  private PreparedStatement deleteAllSt;
  private PreparedStatement searchBandSt;
  public final static String url = "jdbc:hsqldb:hsql://localhost/workdb";


  public BandRepositoryFactory (Connection con) throws SQLException {
    this.con = con;
    if (!isDataBaseReady()) {
      createTableBand();
	}
	this.setConnection(con);
  }
  public BandRepositoryFactory() throws SQLException{
	this(DriverManager.getConnection(url));
  }


	private boolean isDataBaseReady() {
    try {
      ResultSet rs = con.getMetaData().getTables(null,null,null,null);
      boolean tabExists = false;
      while (rs.next()) {
		 // System.out.println(rs.getString("TABLE_NAME"));
        if ("Band".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tabExists = true;
					break;
					
				}
      }
      return tabExists;
    } catch (SQLException e) {
      return false;
    }
}

	@Override
	public Connection getConnection() {
		return con;
	}

	@Override
	public void setConnection(Connection con) throws SQLException {
		this.con = con;
		//System.out.println("kokokok");
		addSt = con.prepareStatement("INSERT INTO Band(Name,Genre,NumberOfMembers)VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
		getAllSt = con.prepareStatement("SELECT * FROM Band ORDER BY Id");
		selectFromBandSt = con.prepareStatement("SELECT * FROM Band WHERE Id=?");
		deleteFromBandSt = con.prepareStatement("DELETE FROM Band WHERE Name=?");
		updateBandSt = con.prepareStatement("UPDATE Band SET Name=?,Genre=?,NumberOfMembers=?  WHERE Id=?");
		deleteAllSt = con.prepareStatement("DELETE  FROM Band;");

		searchBandSt = con.prepareStatement("SELECT * FROM Band WHERE Id=? OR Name=? OR NumberOfMembers=? OR Genre=?;");
		
		//+	"Name LIKE '%?%' OR Name LIKE '?%' OR Name LIKE '%?' OR "
		
		//+	"Genre LIKE '%?%' OR Genre LIKE '?%' OR Genre LIKE '%?'");



// +	"Id LIKE %?% OR Id LIKE ?% OR Id LIKE %? OR"
// "NuberOfMembers LIKE %?% OR NuberOfMembers  LIKE ?% OR NuberOfMembers  LIKE %? OR"


	}
	

	

	@Override
	public List<Band> getAll() {
		List<Band> Band = new LinkedList<>()	;
		try {
			ResultSet rs = getAllSt.executeQuery();
			while (rs.next()) {
				Band b = new Band()	;
				b.setId(rs.getInt("Id"));
				b.setName(rs.getString("Name"));
				b.setGenre(rs.getString("Genre"));
				b.setNumberOfMembers(rs.getInt("NumberOfMembers"));

				Band.add(b);			
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e.getMessage()+"\n"+e.getStackTrace().toString());
		}
		return Band;
		
		
	}

	@Override
	public int add(Band b) {
		
		int count = 0;
		if(isDataBaseReady()){
			try {
				addSt.setString(1, b.getName());
				addSt.setString(2, b.getGenre());
				addSt.setInt(3, b.getNumberOfMembers());
				
				count = addSt.executeUpdate();
				ResultSet genKeys = addSt.getGeneratedKeys();
				if (genKeys.next()) {
					b.setId(genKeys.getInt(1));
					
				}

				
			} catch (SQLException e) {
				throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
			}
	}
		return count ;
		
	}

	@Override
	public Band getById(int Id) throws SQLException {

		try {
			selectFromBandSt.setInt(1, Id);
			
			ResultSet rs = selectFromBandSt.executeQuery();
			
			if (rs.next()) 
			{
				Band b =  new Band();
				b.setId(rs.getInt("Id"));
				b.setName(rs.getString("Name"));
				b.setGenre(rs.getString("Genre"));
				b.setNumberOfMembers(rs.getInt("NumberOfMembers"));

				return b;
			} 
		
	}
	catch (SQLException e) {
		throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());

	}
	throw new SQLException("Zespół o identyfikarze "+ Id + "Nie istnieje");
}


	@Override
	public String getInfo() {
		return "To jest program na potrzeby przedmiotu testowanie automatyczne";
	}

	@Override
	public void createTableBand() throws SQLException {
		
		String cr = "CREATE TABLE   Band (Id integer GENERATED BY DEFAULT AS IDENTITY, Name varchar(20) NOT NULL,   Genre varchar(60) NOT NULL,  NumberOfMembers int NOT NULL)";
		if(!isDataBaseReady())
			con.createStatement().executeUpdate(cr);
												
		
	}

	@Override
	public void dropTableBand()throws SQLException {
		if(isDataBaseReady())
			con.createStatement().executeUpdate("DROP TABLE Band");
		
	}

	@Override
	public Band selectFromBand(int Id) throws SQLException {
		selectFromBandSt.setInt(1, Id);
		ResultSet rs = selectFromBandSt.executeQuery();
		try{
		if (rs.next()) {
			Band b = new Band();
			b.setId(rs.getInt("Id"));
			b.setName(rs.getString("Name"));
			b.setGenre(rs.getString("Genre"));
			b.setNumberOfMembers(rs.getInt("NumberOfMembers"));
			return b;
			
		}
	}
	catch(SQLException e){
	throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
	}
	throw new SQLException("Osoba o id " + Id + " nie istnieje");
		//return null;
}


	@Override
	public int deleteFromBand(Band b) {
		try {
			deleteFromBandSt.setInt(1, b.getId());
			return deleteFromBandSt.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());

		}
	}


	
	public int deleteFromBandByName(Band b) {
		try {
			deleteFromBandSt.setString(1, b.getName());
			return deleteFromBandSt.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());

		}
	}

	public int deleteFromBandById(Band b) {
		return this.deleteFromBand(b);
	}


	

	@Override
	public int updateBand(Band b) throws SQLException {
		int count = 0;
		try {
			updateBandSt.setString(1, b.getName() );
			
			updateBandSt.setString(2, b.getGenre());
			updateBandSt.setInt(3, b.getNumberOfMembers());
			updateBandSt.setInt(4, b.getId());
			
			

			count = updateBandSt.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());

		}
		if (count <= 0) {
			throw new SQLException("Nie można modyfikować");
			
		}


		return count;




  }
  
  public static BandRepository getInstance(){
    return null;
  }
@Override
public int deleteAll() {
	try {
		return deleteAllSt.executeUpdate();
	} catch (SQLException e) {
		throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
	}
}

public List<Band> searchBand(String s) throws SQLException{

	List<Band> Band = new LinkedList<>();
	s.replaceAll(" ", "");
		try {
			

			Integer y = searchFormater(s);

			int x=-1;


			if (! (y ==null)) {

				 x = y;

				
			}
		

			searchBandSt.setInt(1, x);
			searchBandSt.setString(2, s);
			searchBandSt.setInt(3,x);
			searchBandSt.setString(4, s);
			/*
			searchBandSt.setString(5, s);
			searchBandSt.setString(6, s);
			searchBandSt.setString(7, s);
			searchBandSt.setString(8, s);
			searchBandSt.setString(9, s);
			searchBandSt.setString(10, s);
			*/

			// 10 znakw zapytania opracj obrabianie stringa przed wyszukaniem.

			ResultSet rs = searchBandSt.executeQuery();
			
			while (rs.next()) {
				

				

				Band b = new Band()	;
				b.setId(rs.getInt("Id"));
				b.setName(rs.getString("Name"));
				b.setGenre(rs.getString("Genre"));
				b.setNumberOfMembers(rs.getInt("NumberOfMembers"));

				Band.add(b);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage()+"\n"+e.getStackTrace().toString());
			
		}
		return Band;

}	

	public List<Band>  szukajBand(String s) {
		List<Band> Band = this.getAll();
		List<Band> Wynik = new LinkedList<>();

		s.replaceAll(" ", "");

		Integer y = searchFormater(s);

			int x=-1;


			if (! (y ==null)) {

				 x = y;	
			}
			for (Band b : Band) {
				if (b.getName().replaceAll(" ", "")==s) {
					Wynik.add(b);
					
				}
				if (b.getGenre().replaceAll(" ", "")==s) {
					Wynik.add(b);
					
				}
				if (b.getId()==x) {
					Wynik.add(b);
					
				}
				if (b.getNumberOfMembers()==x) {
					Wynik.add(b);
					
				}
				
			}

		return Wynik;
		
	}


	public Integer searchFormater(String s) {


		//Integer x = Integer.valueOf(str);

		Integer y = null;

		try {
			 y = Integer.valueOf(s);
			
			
		} catch (Exception e) {
			//throw new IllegalStateException(e.getMessage()+"\n"+e.getStackTrace().toString());
			
			
		}
		return y;
		


		
		
	}





}
