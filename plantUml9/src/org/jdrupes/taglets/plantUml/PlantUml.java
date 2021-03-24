/*
 * JDrupes MDoclet
 * Copyright (C) 2021 Michael N. Lipp
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.SimpleElementVisitor8;
import javax.tools.DocumentationTool;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;

import com.sun.source.doctree.CommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.doctree.TextTree;
import com.sun.source.doctree.UnknownBlockTagTree;
import com.sun.source.util.SimpleDocTreeVisitor;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Taglet;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.preproc.Defines;

/**
 * A JDK11 doclet that generates UML diagrams from PlantUML 
 * specifications in the comment.
 */
public class PlantUml implements Taglet {

    private DocletEnvironment env;
    private JavaFileManager fileManager;
    private List<String> plantConfigData;

    @Override
    public void init(DocletEnvironment env, Doclet doclet) {
        this.env = env;
        fileManager = env.getJavaFileManager();
    }

    @Override
    public String getName() {
        return "plantUml";
    }

    @Override
    public Set<Location> getAllowedLocations() {
        return Set.of(Taglet.Location.OVERVIEW, Taglet.Location.PACKAGE,
            Taglet.Location.TYPE);
    }

    @Override
    public boolean isInlineTag() {
        return false;
    }

    @Override
    public String toString(List<? extends DocTree> tags, Element element) {
        String plantUmlSource = extractPlantUmlSource(tags);
        String[] splitSource = plantUmlSource.split("\\s", 2);
        if (splitSource.length < 2) {
            throw new IllegalArgumentException("Invalid " + getName()
                + " tag: Expected filename and PlantUML source");
        }

        String packageName = extractPackageName(element);
        FileObject graphicsFile;
        try {
            graphicsFile = fileManager.getFileForOutput(
                DocumentationTool.Location.DOCUMENTATION_OUTPUT, packageName,
                splitSource[0], null);
        } catch (IOException e) {
            throw new RuntimeException(
                "Error generating output file for " + packageName + "/"
                    + splitSource[0] + ": " + e.getLocalizedMessage());
        }

        // render
        plantUmlSource = "@startuml\n" + splitSource[1].trim() + "\n@enduml";
        SourceStringReader reader = new SourceStringReader(
            Defines.createEmpty(), plantUmlSource, plantConfig());
        try {
            reader.outputImage(graphicsFile.openOutputStream(),
                new FileFormatOption(getFileFormat(splitSource[0])));
        } catch (IOException e) {
            throw new RuntimeException(
                "Error generating UML image " + graphicsFile.getName() + ": "
                    + e.getLocalizedMessage());
        }

        return "";
    }

    private String extractPlantUmlSource(List<? extends DocTree> tags) {
        StringBuilder source = new StringBuilder();
        new SimpleDocTreeVisitor<>() {

            @Override
            public Object visitUnknownBlockTag(UnknownBlockTagTree node,
                    Object p) {
                new SimpleDocTreeVisitor<>() {

                    @Override
                    public Object visitText(TextTree node, Object p) {
                        source.append(node.getBody());
                        return null;
                    }

                    @Override
                    public Object visitComment(CommentTree node, Object p) {
                        // TODO Auto-generated method stub
                        return super.visitComment(node, p);
                    }

                }.visit(node.getContent(), null);
                return null;
            }

            @Override
            public Object visitText(TextTree node, Object p) {
                source.append(node.getBody());
                return null;
            }

        }.visit(tags, null);
        return source.toString();
    }

    private String extractPackageName(Element element) {
        return element.accept(new SimpleElementVisitor8<>() {

            @Override
            public Name visitPackage(PackageElement e, Object p) {
                return e.getQualifiedName();
            }

            @Override
            public Name visitType(TypeElement e, Object p) {
                return env.getElementUtils().getPackageOf(e).getQualifiedName();
            }

        }, null).toString();
    }

    private FileFormat getFileFormat(String name) {
        for (FileFormat f : FileFormat.values()) {
            if (name.toLowerCase().endsWith(f.getFileSuffix())) {
                return f;
            }
        }
        String msg = "Unsupported file extension: " + name;
        throw new IllegalArgumentException(msg);
    }

    private List<String> plantConfig() {
        if (plantConfigData != null) {
            return plantConfigData;
        }
        plantConfigData = new ArrayList<>();
        String configFileName
            = System.getProperty(getClass().getName() + ".config");
        if (configFileName == null) {
            return plantConfigData;
        }
        try {
            plantConfigData = Collections.singletonList(
                new String(Files.readAllBytes(Paths.get(configFileName))));
        } catch (IOException e) {
            throw new RuntimeException(
                "Error loading PlantUML configuration file "
                    + configFileName + ": " + e.getLocalizedMessage());
        }

        return plantConfigData;
    }

}
