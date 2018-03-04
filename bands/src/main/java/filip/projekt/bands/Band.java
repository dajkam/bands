package filip.projekt.bands;

public class Band {
  private String _Name;
  private String _Genre;
  private  int _NumberOfMembers;


  public String play(){
    return _Name + "koncert został zagrany w" + _NumberOfMembers + "osób";
  }

  public int addMember(int i){
    //set_NumberOfMembers(get_NumberOfMembers()+i);
  return   _NumberOfMembers +=i;
  }

  public int removeMember(int i){
    //set_NumberOfMembers(get_NumberOfMembers()-i);
    return _NumberOfMembers-=i;
  }





	/**
	* Returns value of _Name
	* @return
	*/
	public String get_Name() {
		return this._Name;
	}

	/**
	* Returns value of _Genre
	* @return
	*/
	public String get_Genre() {
		return this._Genre;
	}

	/**
	* Returns value of _NumberOfMembers
	* @return
	*/
	public int get_NumberOfMembers() {
		return this._NumberOfMembers;
	}

	/**
	* Sets new value of _Name
	* @param
	*/
	public void set_Name(String _Name) {
		this._Name = _Name;
	}

	/**
	* Sets new value of _Genre
	* @param
	*/
	public void set_Genre(String _Genre) {
		this._Genre = _Genre;
	}

	/**
	* Sets new value of _NumberOfMembers
	* @param
	*/
	public void set_NumberOfMembers(int _NumberOfMembers) {
		this._NumberOfMembers = _NumberOfMembers;
	}

	/**
	* Default empty Band constructor
	*/
	public Band() {
		super();
	}

	/**
	* Default Band constructor
	*/
	public Band(String _Name, String _Genre, int _NumberOfMembers) {
		super();
		this._Name = _Name;
		this._Genre = _Genre;
		this._NumberOfMembers = _NumberOfMembers;
	}




	/**
	* Create string representation of Band for printing
	* @return
	*/

}
