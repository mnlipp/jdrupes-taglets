/*
 * JDrupes MDoclet
 * Copyright (C) 2023 Michael N. Lipp
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package org.jdrupes.taglets.plantUml;

/**
 * A JDK11 doclet that generates UML diagrams from PlantUML
 * specifications in the comment.
 */
public class InlinePlantUml extends PlantUml {

    @Override
    public String getName() {
        return "PlantUml";
    }

    @Override
    public boolean isInlineTag() {
        return true;
    }

}
