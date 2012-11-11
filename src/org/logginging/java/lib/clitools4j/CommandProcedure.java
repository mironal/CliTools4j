
package org.logginging.java.lib.clitools4j;


/**
 * CommandLineExecutorに文字列を返すようにする.
 * 
 * @author miro
 */
public interface CommandProcedure extends HasHelp<Options> {
    
    /**
     * 
     * @param options
     * @throws CommandProcedureException
     */
    void onExecute(Options options) throws CommandProcedureException;

    /**
     * 
     * @param e
     */
    void onCatchError(CommandProcedureException e);
    
}
