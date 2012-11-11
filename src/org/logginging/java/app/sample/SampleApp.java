
package org.logginging.java.app.sample;

import java.util.Map;

import org.logginging.java.lib.clitools4j.Command;
import org.logginging.java.lib.clitools4j.CommandLineExecutor;
import org.logginging.java.lib.clitools4j.CommandProcedure;
import org.logginging.java.lib.clitools4j.CommandProcedureException;
import org.logginging.java.lib.clitools4j.Options;

/**
 * @author miro
 */
public class SampleApp {
    enum Kind {
        Create, Delete
    }

    public static void main(String[] args) {
        Command<Kind> create = new Command<SampleApp.Kind>(Kind.Create)
                .withProcedure(new CommandProcedure() {

                    @Override
                    public void onExecute(Options options) throws CommandProcedureException {
                        if (!options.hasOption("--name")) {
                            throw new CommandProcedureException("--name is required.");
                        }
                        StringBuilder builder = new StringBuilder();
                        builder.append("create ").append(options.getOptionValue("--name"));
                        builder.append("\n");
                        builder.append("success!.");

                        System.out.println(builder.toString());
                    }

                    @Override
                    public void onCatchError(CommandProcedureException e) {
                        System.err.println(e.getMessage());
                    }

                    @Override
                    public void onAskHelp(Options options) {
                        String help = "Something help.\n" + "hogehoge : hogehogehogehoge";
                        System.out.println(help);
                    }

                })
                .addOption("--name", "object name.")
                .addOption("--desc", "object description");

        Command<Kind> delete = new Command<Kind>(Kind.Delete).withProcedure(new CommandProcedure() {

            @Override
            public void onCatchError(CommandProcedureException e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAskHelp(Options options) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onExecute(Options options) throws CommandProcedureException {
                // TODO Auto-generated method stub
            }
        }).addOption("--name", "object name.");

        CommandLineExecutor<Kind> executor = new CommandLineExecutor<SampleApp.Kind>(Kind.class) {

            @Override
            public void onAskHelp(Map<Kind, Command<Kind>> options) {

            }
        };

        executor.addCommand(create).addCommand(delete);
        executor.execute(args);

    }
}
