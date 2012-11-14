
package org.logginging.java.lib.clitools4j;

import java.util.Set;

public class Command<E extends Enum<E>> {

    private final E type;
    private CommandProcedure procedure;
    private Options options = new Options();

    public Command(E type, CommandProcedure procedure) {
        if (type == null) {
            throw new NullPointerException("type is null.");
        }
        if (procedure == null) {
            throw new NullPointerException("procedure is null.");
        }
        this.type = type;
        this.procedure = procedure;
    }

    /**
     * --helpと-hが追加される
     * 
     * @param description
     * @return
     */
    public Command<E> withHelpOption(String description) {
        if (description == null) {
            throw new NullPointerException("description is null.");
        }
        // @formatter:off
        options.addOption(Options.OPTION_HELP_LONG, description)
               .addOption(Options.OPTION_HELP_SHORT, description);
        // @formatter:on
        return this;
    }

    public E getType() {
        return type;
    }

    public Command<E> addOption(String key, String description) {
        options.addOption(key, description);
        return this;
    }

    /**
     * ライブラリ使用者からは見えないメソッドの為、nullチェックは行わない
     * 
     * @param args
     */
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

    private void parseOptions(String[] args) {
        Set<String> keySet = options.getOptionKeySet();
        /*
         * argsの0番目の要素は本来コマンドが指定されているので、 解析不要であるが、配列の一部が渡されたりすることを考慮して
         * 0番目の要素から全て解析する.
         */
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (keySet.contains(arg)) {

                String value = "";
                // (i+1) < args.length : 一番最後の要素だったら値の部分はないので、引数なしのオプションとして扱う
                // args[i + 1].startsWith("-") :
                // オプションの次の要素にオプションが来ていたら、引数なしのオプションとして扱う
                // オプションの条件は-で始まること。 -で始まるような値は取得できない(問題があったら設計し直す).
                if (hasNext(args, i) && nextIsValue(args, i)) {
                    i += 1;
                    value = args[i];
                }
                options.setValue(arg, value);
            }
        }
    }

    private boolean nextIsValue(String[] args, int index) {
        return !args[index + 1].startsWith("-");
    }

    private boolean hasNext(String[] args, int index) {
        return ((index + 1) < args.length);
    }

    private void proceedCommand() {
        try {
            procedure.onExecute(options);
        } catch (CommandProcedureException e) {
            procedure.onCatchException(e);
        }
    }
}
