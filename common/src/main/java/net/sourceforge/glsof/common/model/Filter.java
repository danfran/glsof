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
import java.util.LinkedList;
import java.util.List;

@XmlType
@XmlAccessorType(XmlAccessType.NONE)
public class Filter implements Serializable {

    @XmlElement(name = "type")
    private String _type = "";

    @XmlElementWrapper(name = "values")
    @XmlElement(name = "value")
    private List<String> _values = new LinkedList<String>();

    public Filter() {
    }

    public Filter(String _type, List<String> _values) {
        this._type = _type;
        this._values = _values;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

    public List<String> getValues() {
        return _values;
    }

    public void setValues(List<String> values) {
        _values = values;
    }

}
