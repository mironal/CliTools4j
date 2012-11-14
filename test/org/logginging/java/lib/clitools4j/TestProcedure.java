
package org.logginging.java.lib.clitools4j;

public class TestProcedure implements CommandProcedure {

    boolean onAskHelpCalled = false;
    boolean onExecuteCalled = false;
    boolean onCatchErrorCalled = false;

    @Override
    public void onAskHelp(Options options) {
        onAskHelpCalled = true;
    }

    @Override
    public void onExecute(Options options) throws CommandProcedureException {
        onExecuteCalled = true;
    }

    @Override
    public void onCatchException(CommandProcedureException e) {
        onCatchErrorCalled = true;
    }

    void reset() {
        onAskHelpCalled = false;
        onExecuteCalled = false;
        onCatchErrorCalled = false;
    }
}
