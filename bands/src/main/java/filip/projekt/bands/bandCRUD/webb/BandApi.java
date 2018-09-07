package filip.projekt.bands.bandCRUD.webb;
import filip.projekt.*;
import filip.projekt.bands.bandCRUD.domain.Band;
import filip.projekt.bands.bandCRUD.repository.BandRepository;
import filip.projekt.bands.bandCRUD.repository.BandRepositoryFactory;

import org.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.FormParam;
import javax.ws.rs.Path;

import java.util.Properties;

@RestController


public class BandApi {

    @Autowired
    BandRepository repo;

    @RequestMapping("/")
    public String index() throws IOException {
       // return "raz raz 606 słychać mnie?";
       String name = new Object(){}.getClass().getEnclosingMethod().getName(); // pobieranie nazwy metody,która się aktualnie wykonuje
        
       String html = graficzny(name);
       
     
        return html;
      
    }
    @RequestMapping(value = "/Band/{Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Band selectFromBand (@PathVariable("Id") int Id) throws SQLException{
        return repo.selectFromBand(Id);
    }

    @RequestMapping(value = "/Band", produces =MediaType.APPLICATION_JSON_VALUE,
    method = RequestMethod.GET)
    @ResponseBody
    public List<Band> getAllBands(@RequestParam(value = "filter", required =false)String f) throws SQLException {
       List <Band> bands = new LinkedList<Band>(); 
       for (Band b : repo.getAll()) {
           if (f == null) {
               bands.add(b);
               
           } else if(b.getName().contains(f)) {
               bands.add(b);
               
           }

           
       }
        return bands;
    }

    @RequestMapping(value = "/Band",
   method = RequestMethod.POST,
   consumes = MediaType.APPLICATION_JSON_VALUE,
   produces = MediaType.APPLICATION_JSON_VALUE )

   public Band addBand(@RequestBody Band b)
   {
       if (repo.add(b) < 1) {
           return null;
           
       }

       return b;
   }

   @RequestMapping(value ="/Band/{Id}",
   method = RequestMethod.DELETE)

   public int deleteFromBand(@PathVariable("Id") int Id) throws SQLException {
       return new Integer(repo.deleteFromBand(repo.selectFromBand(Id)));
   }
   @RequestMapping(value = "/Band/{Id}",
   method = RequestMethod.PUT)
   public int updateBand(@PathVariable("Id")int Id, @RequestBody Band b) throws SQLException {
       int c = 0;
       try {
        c = repo.updateBand(b);
       } catch (SQLException e) {
        throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
       }

       if (c < 1) {

        throw new SQLException("Nie można modifikować rekordu lub nie ma tekego zespołu");
           
       }

       return c;
      
       
   }



    @RequestMapping(value = "/Fill",
   method = RequestMethod.GET,
   
   produces = MediaType.APPLICATION_JSON_VALUE )
   public void fill(){
       
       Random r = new Random();
  for (int i = 0; i<100; i++) {
    Band b = new Band( "Zespół" + r.nextInt(100),"Gatunek" + r.nextInt(100), r.nextInt(100));
    repo.add(b);

   }}

   @RequestMapping(value = "/Fill2",
   method = RequestMethod.GET,
   
   produces = MediaType.APPLICATION_JSON_VALUE )
   public void fill2(){
       
    Band motorhead = new Band( "Motorhead","Rock And Roll",3);
    Band metallica = new Band( "Metallica","Metal",4);
    Band volbeat = new Band( "Volbeat","Groove Metal",5);
    Band archenemy = new Band( "ARCH ENEMY","melodic death metal",5);
    Band amonamarth = new Band( "amon amatrh","viking ",6);
    repo.add(motorhead);
    repo.add(metallica);
    repo.add(volbeat);
    repo.add(archenemy);
    repo.add(amonamarth);
 
   
} 
     @RequestMapping(value = "/Search",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE
) 
    
    @ResponseBody
   public List<Band> search(@RequestParam(value = "szukane") String s) throws SQLException {
    

    List <Band> bands = repo.searchBand(s); 
       

	return bands;
   }



@RequestMapping(value = "/Szukaj") 
    @ResponseBody
   public String szukaj() throws IOException {
        
    String name = new Object(){}.getClass().getEnclosingMethod().getName(); // pobieranie nazwy metody,która się aktualnie wykonuje
        
       String html = graficzny(name);
       
     
        return html;


}




public String graficzny (String name) throws IOException {
    String s = "/home/filip/Pulpit/tau/wrzesien/bands/bands/src/main/java/filip/projekt/bands/bandCRUD/webb/";

     // String name = new Object(){}.getClass().getEnclosingMethod().getName(); // pobieranie nazwy metody,która się aktualnie wykonuje

      s=s+name; 

      s=s+".html";

      String html2 = readFile(s, StandardCharsets.UTF_8);


      StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append(html2);

        String html = htmlBuilder.toString();

            return html;
    
}



static String readFile(String path, Charset encoding) 
  throws IOException 
{
  byte[] encoded = Files.readAllBytes(Paths.get(path));
  return new String(encoded, encoding);
}



public void fill2(char a){
       
    Band motorhead = new Band( "Motorhead","Rock And Roll",3);
    Band metallica = new Band( "Metallica","Metal",4);
    Band volbeat = new Band( "Volbeat","Groove Metal",5);
    Band archenemy = new Band( "ARCH ENEMY","melodic death metal",5);
    Band amonamarth = new Band( "amon amarth","viking ",6);
    repo.add(motorhead);
    repo.add(metallica);
    repo.add(volbeat);
    repo.add(archenemy);
    repo.add(amonamarth);
 
   
} 

}
