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
package net.sourceforge.glsof.filemonitor.thread;

import com.google.common.base.Equivalence;

import java.util.Arrays;
import java.util.LinkedList;

public class StatusDetector {

    private LinkedList<Equivalence.Wrapper<String[]>> _newCache = new LinkedList<Equivalence.Wrapper<String[]>>();
    private LinkedList<Equivalence.Wrapper<String[]>> _oldCache = new LinkedList<Equivalence.Wrapper<String[]>>();

    public void swapCaches() {
        _oldCache = _newCache;
        _newCache = new LinkedList<Equivalence.Wrapper<String[]>>();
    }

    public LinkedList<Equivalence.Wrapper<String[]>> getOldCache() {
        return _oldCache;
    }

    public void add(String[] row) {
        _newCache.add(_rowEquivalence.wrap(row));
    }

    public boolean isOpenRow(String[] row) {
        return !_oldCache.remove(_rowEquivalence.wrap(row));
    }

    private final Equivalence _rowEquivalence = new Equivalence<String[]>() {
        @Override
        protected boolean doEquivalent(final String[] row1, final String[] row2) {
            return Arrays.equals(row1, row2);
        }

        @Override
        protected int doHash(final String[] row) {
            return Arrays.hashCode(row);
        }
    };

}
