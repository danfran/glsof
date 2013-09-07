/* This file is part of Glsof.

   Glsof is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Glsof is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Glsof.  If not, see <http://www.gnu.org/licenses/>. */
package net.sourceforge.glsof.common.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class OtherPreferences implements Serializable {

	// ID OR LOGIN
	@XmlElement(name="idNumber")
	private boolean _idNumber = false;
	
	// OFFSET OR SIZE
	@XmlElement(name="size")
	private boolean _size = false;
	
	// DANGEROUS KERNEL'S FUNCTIONS
	@XmlElement(name="timeout")
	private boolean _timeout = false;
	@XmlElement(name="timeoutValue")
	private int _timeoutValue = 2;
	@XmlElement(name="avoid")
	private boolean _avoid = false;
	
	// NETWORK CONTROL
	@XmlElement(name="ipFormat")
	private boolean _ipFormat = true;
	@XmlElement(name="nfs")
	private boolean _nfs = false;
	@XmlElement(name="portNumbers")
	private boolean _portNumbers = true;
	@XmlElement(name="domainSocket")
	private boolean _domainSocket = false;
	
	// DIRECTORIES AND FILES
	@XmlElement(name="linksFile")
	private boolean _linksFile = false;
	@XmlElement(name="linksFileValue")
	private int _linksFileValue = 1;
	
	// VARIOUS
	@XmlElement(name="and")
	private boolean _and = false;


	public boolean isIdNumber() {
		return _idNumber;
	}

	public void setIdNumber(final boolean idNumber) {
		_idNumber = idNumber;
	}

	public boolean isSize() {
		return _size;
	}

	public void setSize(final boolean size) {
		_size = size;
	}

	public boolean isTimeout() {
		return _timeout;
	}

	public void setTimeout(final boolean timeout) {
		_timeout = timeout;
	}

	public int getTimeoutValue() {
		return _timeoutValue;
	}

	public void setTimeoutValue(final int timeoutValue) {
		_timeoutValue = timeoutValue;
	}

	public boolean isAvoid() {
		return _avoid;
	}

	public void setAvoid(final boolean avoid) {
		_avoid = avoid;
	}

	public boolean isIpFormat() {
		return _ipFormat;
	}

	public void setIpFormat(final boolean ipFormat) {
		_ipFormat = ipFormat;
	}

	public boolean isNfs() {
		return _nfs;
	}

	public void setNfs(final boolean nfs) {
		_nfs = nfs;
	}

	public boolean isPortNumbers() {
		return _portNumbers;
	}

	public void setPortNumbers(final boolean portNumbers) {
		_portNumbers = portNumbers;
	}

	public boolean isDomainSocket() {
		return _domainSocket;
	}

	public void setDomainSocket(final boolean domainSocket) {
		_domainSocket = domainSocket;
	}

	public boolean isLinksFile() {
		return _linksFile;
	}

	public void setLinksFile(final boolean linksFile) {
		_linksFile = linksFile;
	}

	public int getLinksFileValue() {
		return _linksFileValue;
	}

	public void setLinksFileValue(final int linksFileValue) {
		_linksFileValue = linksFileValue;
	}

	public boolean isAnd() {
		return _and;
	}

	public void setAnd(final boolean and) {
		_and = and;
	}

    public void toParameters(final StringBuilder sb) {
        if (isIdNumber()) sb.append(" -l");
        sb.append(isSize() ? " -s" : " -o");
        if (isAnd()) sb.append(" -a");
        if (isLinksFile()) sb.append(" +L").append(getLinksFileValue() == 0 ? "" : " " + getLinksFileValue());
        if (isIpFormat()) sb.append(" -n");
        if (isNfs()) sb.append(" -N");
        if (isPortNumbers()) sb.append(" -P");
        if (isDomainSocket()) sb.append(" -U");
        if (isTimeout()) sb.append(" -S ").append(getTimeoutValue());
        if (isAvoid()) sb.append(" -b");
    }
	
}