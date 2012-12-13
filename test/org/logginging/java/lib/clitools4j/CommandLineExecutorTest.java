
package org.logginging.java.lib.clitools4j;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

public class CommandLineExecutorTest {

    static class TestCommandExecutor extends CommandLineExecutor<Kind> {

        boolean onAskHelpCalled = false;

        public TestCommandExecutor(Class<Kind> type) {
            super(type);
        }

        @Override
        public void onAskHelp(Map<Kind, Command<Kind>> options) {
            onAskHelpCalled = true;
        }

    }

    @Test
    public void singleCommand() {
        TestCommand create = new TestCommand(Kind.Create);

        CommandLineExecutor<Kind> executor = new CommandLineExecutor<Kind>(Kind.class) {

            @Override
            public void onAskHelp(Map<Kind, Command<Kind>> options) {

            }
        }.addCommand(create);

        assertThat(create.onAskHelpCalled, is(false));
        assertThat(create.onCatchErrorCalled, is(false));
        assertThat(create.onExecuteCalled, is(false));

        String[] args = new String[] {
            "create"
        };

        executor.execute(args);

        assertThat(create.onAskHelpCalled, is(false));
        assertThat(create.onCatchErrorCalled, is(false));
        assertThat(create.onExecuteCalled, is(true));
    }

    @Test
    public void executeEmptyArgs() {
        TestCommand create = new TestCommand(Kind.Create);
        
        TestCommandExecutor executor = new TestCommandExecutor(Kind.class);
        executor.addCommand(create);

        assertThat(create.onAskHelpCalled, is(false));
        assertThat(create.onCatchErrorCalled, is(false));
        assertThat(create.onExecuteCalled, is(false));

        assertThat(executor.onAskHelpCalled, is(false));

        String[] args = new String[] {};

        assertThat(args.length, is(0));

        executor.execute(args);

        assertThat(create.onAskHelpCalled, is(false));
        assertThat(create.onCatchErrorCalled, is(false));
        assertThat(create.onExecuteCalled, is(false));

        assertThat(executor.onAskHelpCalled, is(true));
    }

    @Test(expected = NullPointerException.class)
    public void executeNullArgs() {

        TestCommand create = new TestCommand(Kind.Create);

        TestCommandExecutor executor = new TestCommandExecutor(Kind.class);
        executor.addCommand(create);

        assertThat(create.onAskHelpCalled, is(false));
        assertThat(create.onCatchErrorCalled, is(false));
        assertThat(create.onExecuteCalled, is(false));

        assertThat(executor.onAskHelpCalled, is(false));

        String[] args = null;

        executor.execute(args);
    }

    @Test
    public void executeUnknownCommand() {
        TestCommand create = new TestCommand(Kind.Create);

        TestCommandExecutor executor = new TestCommandExecutor(Kind.class);
        executor.addCommand(create);

        assertThat(create.onAskHelpCalled, is(false));
        assertThat(create.onCatchErrorCalled, is(false));
        assertThat(create.onExecuteCalled, is(false));

        assertThat(executor.onAskHelpCalled, is(false));

        String[] args = new String[] {
            "hoge"
        };

        executor.execute(args);

        assertThat(create.onAskHelpCalled, is(false));
        assertThat(create.onCatchErrorCalled, is(false));
        assertThat(create.onExecuteCalled, is(false));

        assertThat(executor.onAskHelpCalled, is(true));
    }

    @Test(expected = NullPointerException.class)
    public void constuctNullType() {
        CommandLineExecutor<Kind> executor = new CommandLineExecutor<Kind>(null) {

            @Override
            public void onAskHelp(Map<Kind, Command<Kind>> options) {

            }
        };
        executor.toString();
    }

    @Test(expected = NullPointerException.class)
    public void addCommandNullArg() {
        CommandLineExecutor<Kind> executor = new CommandLineExecutor<Kind>(Kind.class) {

            @Override
            public void onAskHelp(Map<Kind, Command<Kind>> options) {

            }
        };
        executor.addCommand(null);

    }
}
