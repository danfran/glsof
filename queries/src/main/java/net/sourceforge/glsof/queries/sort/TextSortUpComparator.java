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
package net.sourceforge.glsof.queries.sort;

import java.text.Collator;
import java.util.Locale;

public class TextSortUpComparator implements SortComparator {

    private final Collator _collator = Collator.getInstance(Locale.getDefault());

    public boolean compare(final String value1, final String value2) {
        return _collator.compare(value1, value2) > 0;
    }

}
