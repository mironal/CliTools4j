
package org.logginging.java.lib.clitools4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Options {

    static class OptionInfo {
        String description;
        String value = null;

        public OptionInfo(String desc) {
            description = desc;
        }
    }

    private final Map<String, OptionInfo> options = new HashMap<String, OptionInfo>();

    public Set<String> getOptionKeySet() {
        return Collections.unmodifiableSet(options.keySet());
    }


    public String getOptionValue(String key) {
        return options.get(key).value;
    }

    public boolean requestHelp() {
        // TODO Auto-generated method stub
        return false;
    }

    public Map<String, String> getOptionEntry() {
        Map<String, String> entry = new HashMap<String, String>();
        for (Entry<String, OptionInfo> e : options.entrySet()) {
            entry.put(e.getKey(), e.getValue().description);
        }
        return entry;
    }

    Options addOption(String option, String description) {
        if (option == null) {
            throw new NullPointerException("option is null.");
        }
        if (description == null) {
            throw new NullPointerException("description is null.");
        }
        options.put(option, new OptionInfo(description));
        
        return this;
    }

    Options setValue(String key, String value) {
        if (key == null) {
            throw new NullPointerException("key is null.");
        }
        if (value == null) {
            throw new NullPointerException("value is null.");
        }
        if (options.containsKey(key)) {
            options.get(key).value = value;
        }
        return this;
    }
}
