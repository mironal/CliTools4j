
package org.logginging.java.lib.clitools4j;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.logginging.java.lib.clitools4j.Options.OptionInfo;

public class OptionsTest {

    @Test
    public void testOptionInfo() {
        OptionInfo info = new OptionInfo("hogehoge");
        assertThat(info, notNullValue());
        assertThat(info.description, is("hogehoge"));
        assertThat(info.value, nullValue());
        info.value = "hoge";
        assertThat(info.value, is("hoge"));
    }

    @Test(expected = NullPointerException.class)
    public void addOptionNullOption() {
        Options options = new Options();
        options.addOption(null, "");
    }

    @Test(expected = NullPointerException.class)
    public void addOptionNullDescription() {
        Options options = new Options();
        options.addOption("", null);
    }

    @Test
    public void hasValue() {
        Options options = new Options();
        assertThat(options.hasOption("--hoge"), is(false));
        assertThat(options.hasValue("--hoge"), is(false));

        options.addOption("--hoge", "hogehoge");
        assertThat(options.hasOption("--hoge"), is(true));
        assertThat(options.hasValue("--hoge"), is(false));

        options.setValue("--hoge", "hogehoge");

        assertThat(options.hasValue("--hoge"), is(true));
    }

    @Test
    public void setValue() {
        Options options = new Options();

        // まずは無いことを確認
        assertThat(options.hasOption("--hoge"), is(false));
        assertThat(options.hasValue("--hoge"), is(false));

        // オプションを登録
        options.addOption("--hoge", "hogehoge");

        // 登録されてるだけで、値は設定されていないことを確認
        assertThat(options.hasOption("--hoge"), is(true));
        assertThat(options.hasValue("--hoge"), is(false));

        // 登録されていないオプションであることを確認
        assertThat(options.hasValue("--piyo"), is(false));

        // 登録されいないオプションの値を設定
        options.setValue("--piyo", "piyopiyo");

        // 勝手にオプションや値が設定されていないことを確認
        assertThat(options.hasValue("--hoge"), is(false));
        assertThat(options.hasOption("--piyo"), is(false));
        assertThat(options.hasValue("--piyo"), is(false));

        // 存在するオプションは値が設定されることを確認
        options.setValue("--hoge", "hogehoge");
        assertThat(options.hasValue("--hoge"), is(true));
    }

    @Test
    public void getOptionValue() {
        Options options = new Options();

        // まずは無いことを確認
        assertThat(options.hasOption("--hoge"), is(false));
        assertThat(options.hasValue("--hoge"), is(false));

        // オプションを登録
        options.addOption("--hoge", "hogehoge");

        // 登録されてるだけで、値は設定されていないことを確認
        assertThat(options.hasOption("--hoge"), is(true));
        assertThat(options.hasValue("--hoge"), is(false));

        options.setValue("--hoge", "hhhhh");
        assertThat(options.getValue("--hoge"), is("hhhhh"));
    }

    @Test
    public void getOptionEntry() {
        Options options = new Options();
        assertThat(options.hasOption("--hoge"), is(false));
        assertThat(options.hasOption("--piyo"), is(false));
        options.addOption("--hoge", "hogehoge");
        options.addOption("--piyo", "piyopiyo");

        assertThat(options.hasOption("--hoge"), is(true));
        assertThat(options.hasOption("--piyo"), is(true));

        assertThat(options.getOptionEntry(), notNullValue());
        assertThat(options.getOptionEntry().toString(), is("{--hoge=hogehoge, --piyo=piyopiyo}"));
    }

    @Test
    public void getOptionKeySet() {
        Options options = new Options();
        assertThat(options.hasOption("--hoge"), is(false));
        assertThat(options.hasOption("--piyo"), is(false));
        options.addOption("--hoge", "hogehoge");
        options.addOption("--piyo", "piyopiyo");

        assertThat(options.hasOption("--hoge"), is(true));
        assertThat(options.hasOption("--piyo"), is(true));

        assertThat(options.getOptionKeySet(), notNullValue());
        assertThat(options.getOptionKeySet().toString(), is("[--hoge, --piyo]"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void getValueNotContainOption() {
        Options options = new Options();
        options.getValue("--hoge");
    }

    @Test(expected = NullPointerException.class)
    public void hasValueNullOption() {
        Options options = new Options();
        options.hasValue(null);
    }

    @Test(expected = NullPointerException.class)
    public void hasOptionNullOption() {
        Options options = new Options();
        options.hasOption(null);
    }

    @Test(expected = NullPointerException.class)
    public void setValueNullOption() {
        Options options = new Options();
        options.setValue(null, "");
    }

    @Test(expected = NullPointerException.class)
    public void setValueNullValue() {
        Options options = new Options();
        options.setValue("", null);
    }

    @Test
    public void requestHelpShort() {
        Options options = new Options();
        assertThat(options.hasOption("--help"), is(false));
        assertThat(options.hasOption("-h"), is(false));
        assertThat(options.hasValue("--help"), is(false));
        assertThat(options.hasValue("-h"), is(false));
        assertThat(options.requestHelp(), is(false));
       
        // オプション追加前にセットしてもセットされないこと
        options.setValue(Options.OPTION_HELP_SHORT, "");
        assertThat(options.hasValue(Options.OPTION_HELP_SHORT), is(false));
        assertThat(options.requestHelp(), is(false));

        options.addOption(Options.OPTION_HELP_SHORT, "help short");
        assertThat(options.hasOption("--help"), is(false));
        assertThat(options.hasOption("-h"), is(true));
        assertThat(options.hasValue("--help"), is(false));
        assertThat(options.hasValue("-h"), is(false));
        assertThat(options.requestHelp(), is(false));

        options.setValue(Options.OPTION_HELP_SHORT, "");
        assertThat(options.hasOption("--help"), is(false));
        assertThat(options.hasOption("-h"), is(true));
        assertThat(options.hasValue("--help"), is(false));
        assertThat(options.hasValue("-h"), is(true));
        assertThat(options.requestHelp(), is(true));
    }

    @Test
    public void requestHelpLong() {
        Options options = new Options();
        assertThat(options.hasOption("--help"), is(false));
        assertThat(options.hasOption("-h"), is(false));
        assertThat(options.hasValue("--help"), is(false));
        assertThat(options.hasValue("-h"), is(false));
        assertThat(options.requestHelp(), is(false));

        // オプション追加前にセットしてもセットされないこと
        options.setValue(Options.OPTION_HELP_LONG, "");
        assertThat(options.hasValue(Options.OPTION_HELP_LONG), is(false));
        assertThat(options.requestHelp(), is(false));
        
        options.addOption(Options.OPTION_HELP_LONG, "help long");
        assertThat(options.hasOption("--help"), is(true));
        assertThat(options.hasOption("-h"), is(false));
        assertThat(options.hasValue("--help"), is(false));
        assertThat(options.hasValue("-h"), is(false));
        assertThat(options.requestHelp(), is(false));

        options.setValue(Options.OPTION_HELP_LONG, "");
        assertThat(options.hasOption("--help"), is(true));
        assertThat(options.hasOption("-h"), is(false));
        assertThat(options.hasValue("--help"), is(true));
        assertThat(options.hasValue("-h"), is(false));
        assertThat(options.requestHelp(), is(true));
    }

}
