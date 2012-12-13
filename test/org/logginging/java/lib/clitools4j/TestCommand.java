
package org.logginging.java.lib.clitools4j;

public class TestCommand extends Command<Kind> {

    boolean onAskHelpCalled = false;
    boolean onExecuteCalled = false;
    boolean onCatchErrorCalled = false;

    public TestCommand(Kind type) {
        super(type);
    }

    public void onAskHelp(Options options) {
        onAskHelpCalled = true;
    }

    public void onExecute(Options options) throws CommandProcedureException {
        onExecuteCalled = true;
    }

    public void onCatchException(CommandProcedureException e) {
        onCatchErrorCalled = true;
    }

    void reset() {
        onAskHelpCalled = false;
        onExecuteCalled = false;
        onCatchErrorCalled = false;
    }
}
