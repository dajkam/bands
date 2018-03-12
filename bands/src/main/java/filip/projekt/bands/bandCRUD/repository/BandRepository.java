package filip.projekt.bands.bandCRUD.repository;

import filip.projekt.bands.bandCRUD.domain.Band;
import java.util.*;
public interface BandRepository {
  public String getInfo();
  public void createTableBand();
  public List<Band> getAll();
  public void selectFromBand(int _Id);
  public void deleteFromBand(int _Id);
  public void insertToBand(Band b);
  public void updateById(int _Id,Band b);
    public void dropTableBand();

}
