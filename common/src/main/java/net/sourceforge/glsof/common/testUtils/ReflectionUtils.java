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
package net.sourceforge.glsof.common.testUtils;

import java.lang.reflect.Field;

public class ReflectionUtils {

    public static Object getField(Object o, String fieldName) {
        Field field = getAccessibleField(fieldName, o.getClass());
        Object fieldValue = null;
        try {
            fieldValue = field.get(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fieldValue;
    }

    public static void setField(Object o, String fieldName, Object inject) {
        Field field = getAccessibleField(fieldName, o.getClass());
        try {
            field.set(o, inject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // search in class, in case superclasses as well
    private static Field getAccessibleField(String fieldName, Class<?> c) {
        Field field = null;
        while (c != null) {
            try {
                field = c.getDeclaredField(fieldName);
                field.setAccessible(true);
                break;
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            }
        }
        return field;
    }
}
