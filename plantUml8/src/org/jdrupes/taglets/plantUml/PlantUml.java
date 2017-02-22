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

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javadoc.Doc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;
import com.sun.tools.doclets.formats.html.markup.RawHtml;
import com.sun.tools.doclets.internal.toolkit.Configuration;
import com.sun.tools.doclets.internal.toolkit.Content;
import com.sun.tools.doclets.internal.toolkit.taglets.TagletWriter;

import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.preproc.Defines;

/**
 * A JDK8 doclet that generates UML diagrams from PlantUML specifications in the comment.
 */
public class PlantUml implements com.sun.tools.doclets.internal.toolkit.taglets.Taglet {

	private List<String> plantConfigData = null; 
	
	private Charset getSourceEncoding(Configuration config) {
		if (config.encoding == null || config.encoding.isEmpty()) {
			return Charset.defaultCharset();
		}
		return Charset.forName(config.encoding);
	}
	
	private List<String> plantConfig(Configuration config) {
		if (plantConfigData != null) {
			return plantConfigData;
		}
		plantConfigData = new ArrayList<>();
		String configFileName = System.getProperty(getClass().getName() + ".config");
		if (configFileName == null) {
			return plantConfigData;
		}
		try {
			plantConfigData = Collections.singletonList
					(new String(Files.readAllBytes(Paths.get(configFileName)),
					 getSourceEncoding(config)));
		} catch (IOException e) {
			config.root.printError("Error loading PlantUML configuration file "
						+ configFileName + ": " + e.getLocalizedMessage());
		}
		
		return plantConfigData;
	}
	
	/* (non-Javadoc)
	 * @see com.sun.tools.doclets.Taglet#getName()
	 */
	@Override
	public String getName() {
		return "plantUml";
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
	public Content getTagletOutput(Tag tag, TagletWriter writer) 
			throws IllegalArgumentException {
		return new RawHtml("");
	}

	@Override
	public Content getTagletOutput(Doc holder, TagletWriter writer) 
			throws IllegalArgumentException {
		Configuration config = writer.configuration();
		for (Tag tag: holder.tags(getName())) {
			processTag(tag, config);
		}
		return new RawHtml("");
	}

	private void processTag(Tag tag, Configuration config) {		
		// Get package name for generating output directory
		String packageName;
		if ( tag.holder() instanceof ProgramElementDoc ) {
			packageName = ((ProgramElementDoc)tag.holder()).containingPackage().name();
		}
		else if ( tag.holder() instanceof PackageDoc ) {
			packageName = ((PackageDoc)(tag.holder())).name();
		}
		else if ( tag.holder() instanceof RootDoc ) {
			packageName = null;
		} else {
			config.root.printError
				(tag.position(), "Cannot handle tag for holder " + tag.holder());
			return;
		}
		String source = tag.text().trim();
		String[] nameSource = source.split("\\s", 2);
		if ( nameSource.length < 2 ) {
			config.root.printError
				(tag.position(), "Invalid " + getName() 
				+ " tag: Expected filename and PlantUML source");
          return;
		}
		String fileName = nameSource[0];
		source = "@startuml " + fileName + "\n" + nameSource[1].trim() + "\n@enduml";
		File outputDir = new File(config.destDirName);
		if (packageName != null) {
			outputDir = new File(outputDir, packageName.replace(".", File.separator));
		}
		outputDir.mkdirs();
		File outputFile;
		outputFile = new File(outputDir, fileName.replace("/", File.separator));
		config.root.printNotice("Generating UML diagram " + outputFile);
		// render
		SourceStringReader reader = new SourceStringReader
				(new Defines(), source, plantConfig(config));
		try {
			reader.generateImage(outputFile);
		}
		catch ( IOException e ) {
			config.root.printError
			(tag.position(), "Error generating UML image " + outputFile + ": " 
					+ e.getLocalizedMessage());
		}
	}
	
}
