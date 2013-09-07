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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Preferences implements Serializable {

    @XmlAttribute(name = "name")
    private String _name;

    @XmlElement(name = "location", required = false)
    private Location _location = new Location();

    @XmlElementWrapper(name = "columns")
    @XmlElement(name = "column")
    private List<Boolean> _columns = new LinkedList<Boolean>();

    @XmlElementWrapper(name = "filters")
    @XmlElement(name = "filter")
    private List<Filter> _filters = new LinkedList<Filter>();

    @XmlElement(name = "otherPreferences")
    private OtherPreferences _otherPreferences;

    public static Preferences from(String name) {
        Preferences preferences = new Preferences();
        preferences.setName(name);
        preferences.setLocation(new Location());
        preferences.setColumns(Arrays.asList(
                true, true, true, true,
                true, true, true, true,
                true, true, true, true, true));
        preferences.setOtherPreferences(new OtherPreferences());
        return preferences;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public Location getLocation() {
        return _location;
    }

    public void setLocation(Location location) {
        _location = location;
    }

    public List<Boolean> getColumns() {
        return _columns;
    }

    public void setColumns(List<Boolean> columns) {
        _columns = columns;
    }

    public List<Filter> getFilters() {
        return _filters;
    }

    public void setFilters(List<Filter> filters) {
        _filters = filters;
    }

    public OtherPreferences getOtherPreferences() {
        return _otherPreferences;
    }

    public void setOtherPreferences(OtherPreferences otherPreferences) {
        _otherPreferences = otherPreferences;
    }

}
