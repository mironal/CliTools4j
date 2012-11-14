
package org.logginging.java.lib.clitools4j;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public abstract class CommandLineExecutor<E extends Enum<E>> implements HasHelp<Map<E, Command<E>>> {

    private final Class<E> enumType;
    private Map<E, Command<E>> commands;

    public CommandLineExecutor(Class<E> type) {
        if (type == null) {
            throw new NullPointerException("type is null.");
        }
        enumType = type;
        commands = new EnumMap<E, Command<E>>(type);
    }

    public CommandLineExecutor<E> addCommand(Command<E> command) {
        if (command == null) {
            throw new NullPointerException("command is null.");
        }
        commands.put(command.getType(), command);
        return this;
    }

    private Map<E, Command<E>> getUnmodifiableCommandMap() {
        return Collections.unmodifiableMap(commands);
    }

    public void execute(String[] args) {
        if (args == null) {
            throw new NullPointerException("args is null.");
        }
        // 引数が空だったらhelpを出して終了
        if (args.length == 0) {
            onAskHelp(getUnmodifiableCommandMap());
            return;
        }
        String commandArg = args[0];
        Command<E> command = getCommandByString(commandArg);
        // 存在しないコマンドだったらhelpを出して終了
        if (command == null) {
            onAskHelp(getUnmodifiableCommandMap());
            return;
        }

        /* 処理を実行 */
        command.proceed(args);
    }

    private Command<E> getCommandByString(String arg) {
        for (E e : enumType.getEnumConstants()) {
            if (arg.equals(e.toString().toLowerCase())) {
                return commands.get(e);
            }
        }
        return null;
    }
}
