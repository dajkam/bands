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
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController


public class BandApi {

    @Autowired
    BandRepository repo;

    @RequestMapping("/")
    public String index() {
        return "raz raz 606 słychać mnie?";
        
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
   
   

    
}