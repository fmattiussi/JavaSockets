package JavaSockets;

public class AddressBookItem {
	
	private String hostName;
	private String hostAddress;
	private String hostPort;
	private String hostUsername;
	private String hostPassword;
	
	public AddressBookItem() {}
	
	public AddressBookItem(String hostName, String hostAddress, String hostPort, String hostUsername, String hostPassword) {
		
		this.hostName = hostName;
		this.hostAddress = hostAddress;
		this.hostPort = hostPort;
		this.hostUsername = hostUsername;
		this.hostPassword = hostPassword;
	}
	
	public Boolean isEmpty() {
		
		if (this.hostName == null && this.hostAddress == null && this.hostPort == null && this.hostUsername == null && this.hostPassword == null) {
			
			return true;
		} else {
			
			return false;
		}
	}
	
	String getHostName() {
		return this.hostName;
	}
	
	String getHostAddress() {
		return this.hostAddress;
	}
	
	String getHostPort() {
		return this.hostPort;
	}
	
	String getHostUsername() {
		return this.hostUsername;
	}
	
	String getHostPassword() {
		return this.hostPassword;
	}
	
	public void set(AddressBookItem item) {
		this.hostName = item.getHostName();
		this.hostAddress = item.getHostAddress();
		this.hostPort = item.getHostPort();
		this.hostUsername = item.getHostUsername();
		this.hostPassword = item.getHostPassword();
	}
	
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}
	
	public void setHostPort(String hostPort) {
		this.hostPort = hostPort;
	}
	
	public void setHostUsername(String hostUsername) {
		this.hostUsername = hostUsername;
	}
	
	public void setHostPassword(String hostPassword) {
		this.hostPassword = hostPassword;
	}

}
