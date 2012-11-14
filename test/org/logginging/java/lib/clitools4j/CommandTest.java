
package org.logginging.java.lib.clitools4j;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CommandTest {

    @Test
    public void construct() {
        Command<Kind> command = new Command<Kind>(Kind.Create, new TestProcedure());
        assertThat(command.getType(), is(Kind.Create));
    }

    @Test(expected = NullPointerException.class)
    public void constructNullKind() {
        Command<Kind> command = new Command<Kind>(null, new TestProcedure());
        command.toString();
    }

    @Test(expected = NullPointerException.class)
    public void constructNullProcedure() {
        Command<Kind> command = new Command<Kind>(Kind.Create, null);
        assertThat(command.getType(), is(Kind.Create));
    }

    @Test(expected = NullPointerException.class)
    public void withHelpOptionNullArg() {
        Command<Kind> command = new Command<Kind>(Kind.Create, new TestProcedure());
        command.withHelpOption(null);
    }

    @Test
    public void proceedExecCommand() {
        TestProcedure procedure = new TestProcedure();
        Command<Kind> command = new Command<Kind>(Kind.Create, procedure);
        assertThat(command.getType(), is(Kind.Create));

        command.addOption("--hoge", "hogehoge");
        String[] args = new String[] {
                "create", "--hoge", "hogevalue"
        };
        // まだ何も実行されていないことを確認
        assertThat(procedure.onAskHelpCalled, is(false));
        assertThat(procedure.onExecuteCalled, is(false));
        assertThat(procedure.onCatchErrorCalled, is(false));

        command.proceed(args);

        // 実行されたことを確認
        assertThat(procedure.onAskHelpCalled, is(false));
        assertThat(procedure.onExecuteCalled, is(true));
        assertThat(procedure.onCatchErrorCalled, is(false));

    }

    @Test
    public void proceedCatchError() {
        TestProcedure procedure = new TestProcedure() {

            @Override
            public void onExecute(Options options) throws CommandProcedureException {
                super.onExecute(options);
                throw new CommandProcedureException("error");
            }

        };
        Command<Kind> command = new Command<Kind>(Kind.Create, procedure);
        assertThat(command.getType(), is(Kind.Create));

        command.addOption("--hoge", "hogehoge");
        String[] args = new String[] {
                "create", "--hoge", "hogevalue"
        };
        // まだ何も実行されていないことを確認
        assertThat(procedure.onAskHelpCalled, is(false));
        assertThat(procedure.onExecuteCalled, is(false));
        assertThat(procedure.onCatchErrorCalled, is(false));

        command.proceed(args);

        // 実行されたことを確認
        assertThat(procedure.onAskHelpCalled, is(false));
        assertThat(procedure.onExecuteCalled, is(true));
        assertThat(procedure.onCatchErrorCalled, is(true));
    }

    @Test
    public void proceedHelp() {

        TestProcedure procedure = new TestProcedure();
        Command<Kind> command = new Command<Kind>(Kind.Create, procedure)
                .withHelpOption("Help this command");
        assertThat(command.getType(), is(Kind.Create));

        command.addOption("--hoge", "hogehoge");
        String[] args = new String[] {
            "--help"
        };
        // まだ何も実行されていないことを確認
        assertThat(procedure.onAskHelpCalled, is(false));
        assertThat(procedure.onExecuteCalled, is(false));
        assertThat(procedure.onCatchErrorCalled, is(false));

        command.proceed(args);

        assertThat(procedure.onAskHelpCalled, is(true));
        assertThat(procedure.onExecuteCalled, is(false));
        assertThat(procedure.onCatchErrorCalled, is(false));

        procedure.reset();
        // まだ何も実行されていないことを確認
        assertThat(procedure.onAskHelpCalled, is(false));
        assertThat(procedure.onExecuteCalled, is(false));
        assertThat(procedure.onCatchErrorCalled, is(false));

        args = new String[] {
                "--help", "--hoge", "hogehoge"
        };
        command.proceed(args);
        assertThat(procedure.onAskHelpCalled, is(true));
        assertThat(procedure.onExecuteCalled, is(false));
        assertThat(procedure.onCatchErrorCalled, is(false));
    }

    @Test
    public void proceedHelpWithoutHelpOption() {
        // 引数で--helpを与えたけど、オプションにhelpが追加されていないので
        // helpは実行されないというシナリオ

        TestProcedure procedure = new TestProcedure();
        Command<Kind> command = new Command<Kind>(Kind.Create, procedure);
        assertThat(command.getType(), is(Kind.Create));

        command.addOption("--hoge", "hogehoge");
        String[] args = new String[] {
            "--help"
        };
        // まだ何も実行されていないことを確認
        assertThat(procedure.onAskHelpCalled, is(false));
        assertThat(procedure.onExecuteCalled, is(false));
        assertThat(procedure.onCatchErrorCalled, is(false));

        command.proceed(args);

        assertThat(procedure.onAskHelpCalled, is(false));
        assertThat(procedure.onExecuteCalled, is(true));
        assertThat(procedure.onCatchErrorCalled, is(false));

    }

}
