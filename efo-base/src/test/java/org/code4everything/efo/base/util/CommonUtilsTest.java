package org.code4everything.efo.base.util;

import cn.hutool.core.lang.Console;
import org.junit.Test;

public class CommonUtilsTest {

    @Test
    public void loadCodeTemplate() {
        CommonUtils.loadCodeTemplate();
        Console.log(CommonUtils.codeTitle);
        Console.log(CommonUtils.codeTemplate);
    }
}
