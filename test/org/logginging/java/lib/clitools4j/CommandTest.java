
package org.logginging.java.lib.clitools4j;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CommandTest {

    @Test
    public void construct() {
        Command<Kind> command = new Command<Kind>(Kind.Create) {

            @Override
            public void onExecute(Options options) throws CommandProcedureException {
            }

            @Override
            public void onCatchException(CommandProcedureException e) {
            }

            @Override
            public void onAskHelp(Options options) {
            }

        };
        assertThat(command.getType(), is(Kind.Create));
    }

    @Test(expected = NullPointerException.class)
    public void constructNullKind() {
        Command<Kind> command = new Command<Kind>(null) {

            @Override
            public void onExecute(Options options) throws CommandProcedureException {
            }

            @Override
            public void onCatchException(CommandProcedureException e) {
            }

            @Override
            public void onAskHelp(Options options) {
            }

        };
        command.toString();
    }

    @Test(expected = NullPointerException.class)
    public void withHelpOptionNullArg() {
        Command<Kind> command = new Command<Kind>(Kind.Create) {

            @Override
            public void onExecute(Options options) throws CommandProcedureException {
            }

            @Override
            public void onCatchException(CommandProcedureException e) {
            }

            @Override
            public void onAskHelp(Options options) {
            }

        };
        command.withHelpOption(null);
    }

    @Test
    public void proceedExecCommand() {
        TestCommand command = new TestCommand(Kind.Create);

        assertThat(command.getType(), is(Kind.Create));

        command.addOption("--hoge", "hogehoge");
        String[] args = new String[] {
                "create", "--hoge", "hogevalue"
        };
        // まだ何も実行されていないことを確認
        assertThat(command.onAskHelpCalled, is(false));
        assertThat(command.onExecuteCalled, is(false));
        assertThat(command.onCatchErrorCalled, is(false));

        command.proceed(args);

        // 実行されたことを確認
        assertThat(command.onAskHelpCalled, is(false));
        assertThat(command.onExecuteCalled, is(true));
        assertThat(command.onCatchErrorCalled, is(false));

    }

    @Test
    public void proceedCatchError() {
        TestCommand command = new TestCommand(Kind.Create) {

            @Override
            public void onExecute(Options options) throws CommandProcedureException {
                super.onExecute(options);
                throw new CommandProcedureException("error");
            }

        };
        assertThat(command.getType(), is(Kind.Create));

        command.addOption("--hoge", "hogehoge");
        String[] args = new String[] {
                "create", "--hoge", "hogevalue"
        };
        // まだ何も実行されていないことを確認
        assertThat(command.onAskHelpCalled, is(false));
        assertThat(command.onExecuteCalled, is(false));
        assertThat(command.onCatchErrorCalled, is(false));

        command.proceed(args);

        // 実行されたことを確認
        assertThat(command.onAskHelpCalled, is(false));
        assertThat(command.onExecuteCalled, is(true));
        assertThat(command.onCatchErrorCalled, is(true));
    }

    @Test
    public void proceedHelp() {

        TestCommand command = new TestCommand(Kind.Create);
        command.withHelpOption("Help this command");
        
        assertThat(command.getType(), is(Kind.Create));

        command.addOption("--hoge", "hogehoge");
        String[] args = new String[] {
            "--help"
        };
        // まだ何も実行されていないことを確認
        assertThat(command.onAskHelpCalled, is(false));
        assertThat(command.onExecuteCalled, is(false));
        assertThat(command.onCatchErrorCalled, is(false));

        command.proceed(args);

        assertThat(command.onAskHelpCalled, is(true));
        assertThat(command.onExecuteCalled, is(false));
        assertThat(command.onCatchErrorCalled, is(false));

        command.reset();
        // まだ何も実行されていないことを確認
        assertThat(command.onAskHelpCalled, is(false));
        assertThat(command.onExecuteCalled, is(false));
        assertThat(command.onCatchErrorCalled, is(false));

        args = new String[] {
                "--help", "--hoge", "hogehoge"
        };
        command.proceed(args);
        assertThat(command.onAskHelpCalled, is(true));
        assertThat(command.onExecuteCalled, is(false));
        assertThat(command.onCatchErrorCalled, is(false));
    }

    @Test
    public void proceedHelpWithoutHelpOption() {
        // 引数で--helpを与えたけど、オプションにhelpが追加されていないので
        // helpは実行されないというシナリオ

        TestCommand command = new TestCommand(Kind.Create);
        assertThat(command.getType(), is(Kind.Create));

        command.addOption("--hoge", "hogehoge");
        String[] args = new String[] {
            "--help"
        };
        // まだ何も実行されていないことを確認
        assertThat(command.onAskHelpCalled, is(false));
        assertThat(command.onExecuteCalled, is(false));
        assertThat(command.onCatchErrorCalled, is(false));

        command.proceed(args);

        assertThat(command.onAskHelpCalled, is(false));
        assertThat(command.onExecuteCalled, is(true));
        assertThat(command.onCatchErrorCalled, is(false));

    }

}
