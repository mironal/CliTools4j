
package org.logginging.java.lib.clitools4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Options {

    static String OPTION_HELP_SHORT = "-h";
    static String OPTION_HELP_LONG = "--help";

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

    public boolean requestHelp() {
        if (hasOption(OPTION_HELP_LONG) && hasValue(OPTION_HELP_LONG)) {
            return true;
        }
        if (hasOption(OPTION_HELP_SHORT) && hasValue(OPTION_HELP_SHORT)) {
            return true;
        }
        return false;
    }

    /**
     * optionとdescriptionのmapを返す
     * 
     * @return
     */
    public Map<String, String> getOptionEntry() {
        Map<String, String> entry = new HashMap<String, String>();
        for (Entry<String, OptionInfo> e : options.entrySet()) {
            entry.put(e.getKey(), e.getValue().description);
        }
        return entry;
    }

    /**
     * @param option
     * @return
     */
    public boolean hasValue(String option) {
        if (option == null) {
            throw new NullPointerException("option is null.");
        }
        if (options.containsKey(option)) {
            return options.get(option).value != null;

        }
        return false;
    }

    /**
     * @param key
     * @return
     */
    public String getValue(String key) {
        if (options.containsKey(key)) {
            return options.get(key).value;
        }
        throw new IllegalArgumentException(key + " is not contain.");
    }

    boolean hasOption(String option) {
        if (option == null) {
            throw new NullPointerException("option is null.");
        }
        return options.containsKey(option);
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

    Options setValue(String option, String value) {
        if (option == null) {
            throw new NullPointerException("key is null.");
        }
        if (value == null) {
            throw new NullPointerException("value is null.");
        }
        if (options.containsKey(option)) {
            options.get(option).value = value;
        }
        return this;
    }
}
