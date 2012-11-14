
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
        TestProcedure createProcedure = new TestProcedure();
        Command<Kind> create = new Command<Kind>(Kind.Create, createProcedure);

        CommandLineExecutor<Kind> executor = new CommandLineExecutor<Kind>(Kind.class) {

            @Override
            public void onAskHelp(Map<Kind, Command<Kind>> options) {

            }
        }.addCommand(create);

        assertThat(createProcedure.onAskHelpCalled, is(false));
        assertThat(createProcedure.onCatchErrorCalled, is(false));
        assertThat(createProcedure.onExecuteCalled, is(false));

        String[] args = new String[] {
            "create"
        };

        executor.execute(args);

        assertThat(createProcedure.onAskHelpCalled, is(false));
        assertThat(createProcedure.onCatchErrorCalled, is(false));
        assertThat(createProcedure.onExecuteCalled, is(true));
    }

    @Test
    public void executeEmptyArgs() {

        TestProcedure createProcedure = new TestProcedure();
        Command<Kind> create = new Command<Kind>(Kind.Create, createProcedure);

        TestCommandExecutor executor = new TestCommandExecutor(Kind.class);
        executor.addCommand(create);

        assertThat(createProcedure.onAskHelpCalled, is(false));
        assertThat(createProcedure.onCatchErrorCalled, is(false));
        assertThat(createProcedure.onExecuteCalled, is(false));

        assertThat(executor.onAskHelpCalled, is(false));

        String[] args = new String[] {};

        assertThat(args.length, is(0));

        executor.execute(args);

        assertThat(createProcedure.onAskHelpCalled, is(false));
        assertThat(createProcedure.onCatchErrorCalled, is(false));
        assertThat(createProcedure.onExecuteCalled, is(false));

        assertThat(executor.onAskHelpCalled, is(true));
    }

    @Test(expected = NullPointerException.class)
    public void executeNullArgs() {

        TestProcedure createProcedure = new TestProcedure();
        Command<Kind> create = new Command<Kind>(Kind.Create, createProcedure);

        TestCommandExecutor executor = new TestCommandExecutor(Kind.class);
        executor.addCommand(create);

        assertThat(createProcedure.onAskHelpCalled, is(false));
        assertThat(createProcedure.onCatchErrorCalled, is(false));
        assertThat(createProcedure.onExecuteCalled, is(false));

        assertThat(executor.onAskHelpCalled, is(false));

        String[] args = null;

        executor.execute(args);
    }

    @Test
    public void executeUnknownCommand() {
        TestProcedure createProcedure = new TestProcedure();
        Command<Kind> create = new Command<Kind>(Kind.Create, createProcedure);

        TestCommandExecutor executor = new TestCommandExecutor(Kind.class);
        executor.addCommand(create);

        assertThat(createProcedure.onAskHelpCalled, is(false));
        assertThat(createProcedure.onCatchErrorCalled, is(false));
        assertThat(createProcedure.onExecuteCalled, is(false));

        assertThat(executor.onAskHelpCalled, is(false));

        String[] args = new String[] {
            "hoge"
        };

        executor.execute(args);

        assertThat(createProcedure.onAskHelpCalled, is(false));
        assertThat(createProcedure.onCatchErrorCalled, is(false));
        assertThat(createProcedure.onExecuteCalled, is(false));

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
