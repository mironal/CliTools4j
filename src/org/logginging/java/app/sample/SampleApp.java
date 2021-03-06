
package org.logginging.java.app.sample;

import java.util.Map;

import org.logginging.java.lib.clitools4j.Command;
import org.logginging.java.lib.clitools4j.CommandLineExecutor;
import org.logginging.java.lib.clitools4j.CommandProcedureException;
import org.logginging.java.lib.clitools4j.Options;

/**
 * @author miro
 */
public class SampleApp {

    // @formatter:off
    // コマンド毎にenumを宣言
    enum Kind {
        Create,
        List,
        Delete
    }
    // @formatter:on

    public static void main(String[] args) {

        /*
         * gitのような形式のコマンド向けのライブラリ. git commit -aみたいなやつ.
         * 各コマンド毎にオプションの宣言位置と、実際に行われる処理、エラー処理、helpの 宣言位置が離れないところが特徴.
         * 例外を使用することで、コマンド実行処理中に何かエラーが発生した場合に 即座に処理を中断し、エラー処理に映ることが出来る.
         */

        // createコマンドの定義
        Command<Kind> create = new Command<SampleApp.Kind>(Kind.Create) {
            @Override
            // コマンドが実行される時に呼ばれる
            public void onExecute(Options options) throws CommandProcedureException {

                // まずはオプションのチェック
                // 何かオプションに問題があったらメッセージを含めて例外を投げる.
                if (!options.hasValue("--name")) {
                    // ex. 必須のオプションが指定されていないとき.
                    throw new CommandProcedureException("--name is required.");
                }
                // 問題なければコマンドの実行を続ける
                StringBuilder builder = new StringBuilder();
                builder.append("create ").append(options.getValue("--name"));
                builder.append("\n");
                builder.append("success!.");

                System.out.println(builder.toString());
            }

            @Override
            // onExecuteで例外を投げた時はここに飛ぶ.
            public void onCatchException(CommandProcedureException e) {
                // 表示するメッセージはonExecuteで生成済みなので、printするだけ
                System.err.println(e.getMessage());
            }

            @Override
            // --helpなどでhelpが要求されたときはここに飛ぶ
            public void onAskHelp(Options options) {
                String help = "Something help.\n +" +
                              "hogehogehogehoge.";
                System.out.println(help);
            }
        };
        // ヘルプコマンドを追加する(-hと--helpが自動的に追加される
        create.withHelpOption("help this command.");
         // createコマンドが取るオプションを登録
        create.addOption("--name", "object name.");
        create.addOption("--desc", "object description");

        // listコマンドの定義
        Command<Kind> list = new Command<SampleApp.Kind>(Kind.List) {

            @Override
            public void onAskHelp(Options options) {
                // do something
            }

            @Override
            public void onExecute(Options options) throws CommandProcedureException {
                // do something
            }

            @Override
            public void onCatchException(CommandProcedureException e) {
                // do something
            }
        };

        // deleteコマンドの定義
        Command<Kind> delete = new Command<Kind>(Kind.Delete) {

            @Override
            public void onAskHelp(Options options) {
                // do something
            }

            @Override
            public void onExecute(Options options) throws CommandProcedureException {
                // do something
            }

            @Override
            public void onCatchException(CommandProcedureException e) {
                // do something

            }
        };
        delete.addOption("--name", "object name.");

        CommandLineExecutor<Kind> executor = new CommandLineExecutor<SampleApp.Kind>(Kind.class) {
            @Override
            // コマンドそのもののhelp.
            // 何も引数が指定されたかった時などに呼ばれる
            public void onAskHelp(Map<Kind, Command<Kind>> options) {
                System.out.println("create | list | delete");
            }
        };

        // 上で作ったコマンドを登録.
        executor.addCommand(create).addCommand(list).addCommand(delete);

        // 実行
        executor.execute(args);
    }
}
