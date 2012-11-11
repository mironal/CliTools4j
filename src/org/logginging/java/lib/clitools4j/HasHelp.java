package org.logginging.java.lib.clitools4j;


public interface HasHelp<E>  {
    /**
     * helpの表示が必要になった時に呼ばれる.
     */
    void onAskHelp(E options);
}
