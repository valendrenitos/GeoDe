package back;

public class User {

	
	private String username;
	private int userrole;
	private String password;
	private int userid;
	private String nameRole;
	
	public String getNameRole() {
		return nameRole;
	}
	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUserrole() {
		return userrole;
	}
	public void setUserrole(int userrole) {
		this.userrole = userrole;
	}
	public User(String username, int userrole, String password, int userid) {
		super();
		this.username = username;
		this.userrole = userrole;
		this.password = password;
		this.userid = userid;
	}
	public User(String username, int userrole, String password, int userid, String nameRole) {
		super();
		this.username = username;
		this.userrole = userrole;
		this.password = password;
		this.userid = userid;
		this.nameRole = nameRole;
	}

	
	
}
