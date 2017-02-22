/**
 * 
 */
package org.jdrupes.taglets.plantUml;

import java.util.Map;

/**
 * This class provides the static method for registering the tags. 
 */
public class Taglet {

    /**
     * Register the taglet(s).
     * 
     * @param tagletMap  the map to register this tag to.
     */
    public static void register(Map<String,
    		com.sun.tools.doclets.internal.toolkit.taglets.Taglet> tagletMap) {
    	// This is a bit special, because we want two tags to share their options
    	register(tagletMap, new PlantUml());
    	register(tagletMap, new StartUml());
    	register(tagletMap, new EndUml());
    }

    private static void register(Map<String,
    		com.sun.tools.doclets.internal.toolkit.taglets.Taglet>
    	tagletMap, com.sun.tools.doclets.internal.toolkit.taglets.Taglet taglet) {
    	com.sun.tools.doclets.internal.toolkit.taglets.Taglet t 
    		= tagletMap.get(taglet.getName());
        if (t != null) {
            tagletMap.remove(taglet.getName());
        }
        tagletMap.put(taglet.getName(), taglet);
    }

}
