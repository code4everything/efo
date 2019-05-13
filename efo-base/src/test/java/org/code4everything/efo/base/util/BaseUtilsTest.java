package org.code4everything.efo.base.util;

import cn.hutool.core.lang.Console;
import org.junit.Test;

public class BaseUtilsTest {

    @Test
    public void loadCodeTemplate() {
        BaseUtils.loadCodeTemplate();
        Console.log(BaseUtils.codeTitle);
        Console.log(BaseUtils.codeTemplate);
    }
}
