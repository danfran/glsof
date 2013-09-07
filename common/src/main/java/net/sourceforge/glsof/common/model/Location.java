package net.sourceforge.glsof.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class Location  implements Serializable {
	
	@XmlElement(name="remote")
    private boolean _remote;

	@XmlElement(name="address")
    private String _address = "localhost"; 

    @XmlElement(name="port")
    private int _port = 1099;

    public boolean isRemote() {
		return _remote;
	}
    
    public void setRemote(boolean remote) {
		_remote = remote;
	}
    
	public String getAddress() {
		return _address;
	}

	public void setAddress(String address) {
		_address = address;
	}

	public int getPort() {
		return _port;
	}

	public void setPort(int port) {
		_port = port;
	}

    public String getRmiAddress() {
        return "//" + _address + ":" + _port + "/lsofExecutor";
    }
	
}
