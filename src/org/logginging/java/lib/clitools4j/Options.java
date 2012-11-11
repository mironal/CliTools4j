
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

    Options addOption(String option, String description) {
        return this;
    }

    public Set<String> getOptionKeySet() {
        return Collections.unmodifiableSet(options.keySet());
    }

    public boolean hasOption(String key) {
        if (key == null) {
            throw new NullPointerException("key is null.");
        }
        return options.containsKey(key);
    }

    public boolean hasOptinos(String... keys) {
        for (String key : keys) {
            if (!hasOption(key)) {
                return false;
            }
        }
        return true;
    }

    public String getOptionValue(String key) {
        return "";
    }

    public boolean requestHelp() {
        // TODO Auto-generated method stub
        return false;
    }

    public Map<String, String> getKeyDescription() {
        Map<String, String> entry = new HashMap<String, String>();
        for (Entry<String, OptionInfo> e : options.entrySet()) {
            entry.put(e.getKey(), e.getValue().description);
        }
        return entry;
    }

}
