
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

    @Test
    public void hasOption() {
        Options options = new Options();
        assertThat(options, notNullValue());
        assertThat(options.hasOption("--hoge"), is(false));

        options.addOption("--hoge", "hogehoge");
        assertThat(options.hasOptinos("--hoge"), is(true));

        assertThat(options.hasOptinos("--huga"), is(false));
        options.addOption("--huga", "hugahuga");
        assertThat(options.hasOption("--huga"), is(true));
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
    
    @Test(expected = NullPointerException.class)
    public void hasOptionNullKey(){
        Options options = new Options();
        options.hasOption(null);
    }
    
    @Test
    public void getOptionValue(){
        Options options = new Options();
        assertThat(options, notNullValue());
        assertThat(options.hasOption("--hoge"), is(false));
        options.addOption("--hoge", "hogehoge");
        assertThat(options.hasOption("--hoge"), is(true));
        
        options.setValue("--hoge", "hoge");
        assertThat(options.getOptionValue("--hoge"), is("hoge"));
    }
    
    @Test
    public void setValue(){
        Options options = new Options();
        assertThat(options.has)
        
    }

}