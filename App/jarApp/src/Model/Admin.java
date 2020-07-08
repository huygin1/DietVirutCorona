
package Model;

  

public class Admin {
    public String Name;
    public String UserName;
    public String PassWord;
    public String Email;

    public Admin() {
    }

    public Admin(String Name, String UserName, String PassWord, String Email) {
        this.Name = Name;
        this.UserName = UserName;
        this.PassWord = PassWord;
        this.Email = Email;
    }
    public String get_name() {
		return Name;
	}

	public void set_Name(String Name) {
		this.Name = Name;
	}

    public String getUserName() {
		return UserName;
	}

	public void setUserName(String UserName) {
		this.UserName=UserName;
	}
        public String getEmail() {
		return Email;
	}

	public void setEmail(String Email) {
		this.Email=Email;
	}

	public String getPassWord() {
		return PassWord;
	}

	public void setPassWord(String PassWord) {
		this.PassWord=PassWord;
	}


    
}
