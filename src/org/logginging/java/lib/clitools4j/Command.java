
package org.logginging.java.lib.clitools4j;

import java.util.Set;


public class Command<E extends Enum<E>> {

    private final E type;
    private CommandProcedure procedure;
    private Options options = new Options();

    public Command(E type) {
        this.type = type;
        options.addOption("--help", "Help this command.").addOption("-h", "Help this command.");
    }

    public E getType() {
        return type;
    }

    public Command<E> withProcedure(CommandProcedure procedure) {
        if (procedure == null) {
            throw new NullPointerException("procedure is null.");
        }
        this.procedure = procedure;
        return this;
    }
    
    public Command<E> addOption(String key, String description){
        options.addOption(key, description);
        return this;
    }
    
    Set<String> getOptionKeySet() {
        return options.getOptionKeySet();
    }

    private void parseOptions(String[] args){
        Set<String> keySet = options.getOptionKeySet(); 
        for (int i = 1; i < args.length; i++) {
            String arg = args[i];
            if (keySet.contains(arg)) {
                /*
                 * --hoge hogehoge --foo --baa オプション指定の後に値が続き場合のみiをインクリメントする.
                 * --foo --baaのような値を持たないオプションの場合はインクリメントしない
                 */
                String value = "";
                if (((i + 1) < args.length) && (args[i + 1].startsWith("-"))) {
                    i += 1;
                    value = args[i];
                }
                options.setValue(arg, value);
            }
        }
    }
    
    void proceed(String[] args) {
        // オプションの解析
        parseOptions(args);
       
        /* 実行 */
        if (options.requestHelp()) {
            procedure.onAskHelp(options);
        } else {
            proceedCommand();
        }
    }

    private void proceedCommand() {
        try {
            procedure.onExecute(options);
        } catch (CommandProcedureException e) {
            procedure.onCatchError(e);
        }
    }
}
