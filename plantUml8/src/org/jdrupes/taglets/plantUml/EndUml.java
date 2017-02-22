/*
 * JDrupes PlantUML Taglet
 * Copyright (C) 2017  Michael N. Lipp
 * 
 * This program is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation; either version 3 of the License, or 
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package org.jdrupes.taglets.plantUml;

import com.sun.javadoc.Doc;
import com.sun.javadoc.Tag;
import com.sun.tools.doclets.formats.html.markup.RawHtml;
import com.sun.tools.doclets.internal.toolkit.Content;
import com.sun.tools.doclets.internal.toolkit.taglets.Taglet;
import com.sun.tools.doclets.internal.toolkit.taglets.TagletWriter;

/**
 * A dummy taglet that provides no output.
 * 
 */
public class EndUml implements Taglet {

	/* (non-Javadoc)
	 * @see com.sun.tools.doclets.Taglet#getName()
	 */
	@Override
	public String getName() {
		return "enduml";
	}

	/* (non-Javadoc)
	 * @see com.sun.tools.doclets.Taglet#inConstructor()
	 */
	@Override
	public boolean inConstructor() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.sun.tools.doclets.Taglet#inField()
	 */
	@Override
	public boolean inField() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.sun.tools.doclets.Taglet#inMethod()
	 */
	@Override
	public boolean inMethod() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.sun.tools.doclets.Taglet#inOverview()
	 */
	@Override
	public boolean inOverview() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.sun.tools.doclets.Taglet#inPackage()
	 */
	@Override
	public boolean inPackage() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.sun.tools.doclets.Taglet#inType()
	 */
	@Override
	public boolean inType() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.sun.tools.doclets.Taglet#isInlineTag()
	 */
	@Override
	public boolean isInlineTag() {
		return false;
	}

	@Override
	public Content getTagletOutput(Tag tag, TagletWriter writer) throws IllegalArgumentException {
		return new RawHtml("");
	}

	@Override
	public Content getTagletOutput(Doc holder, TagletWriter writer) throws IllegalArgumentException {
		return new RawHtml("");
	}

}
