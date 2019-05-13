package org.code4everything.efo.base.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import org.code4everything.boot.message.VerifyCodeUtils;
import org.code4everything.boot.web.mvc.BaseController;
import org.code4everything.boot.web.mvc.Response;
import org.code4everything.efo.base.constant.EfoError;

import javax.mail.MessagingException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pantao
 * @since 2019-04-11
 */
public class BaseUtils {

    static String codeTitle = "";

    static String codeTemplate = "";

    private static boolean firstLoad = true;

    private BaseUtils() {}

    public static Response sendCode(BaseController controller, String email) throws MessagingException {
        if (VerifyCodeUtils.isFrequently(email)) {
            return controller.errorResult(EfoError.CODE_FREQUENTLY);
        }

        if (firstLoad) {
            synchronized (BaseUtils.class) {
                if (firstLoad) {
                    loadCodeTemplate();
                }
            }
        }

        VerifyCodeUtils.sendByMail(email, codeTitle, codeTemplate);
        return controller.successResult("发送成功，请查收");
    }

    static void loadCodeTemplate() {
        // 加载验证码模板
        InputStream is = BaseUtils.class.getResourceAsStream("/code-template.txt");
        List<String> lines = IoUtil.readLines(is, CharsetUtil.UTF_8, new ArrayList<>());

        StringBuilder builder = new StringBuilder();
        Boolean isTitle = null;

        for (String line : lines) {
            // #title和#content的顺序是不重要的
            if (line.startsWith("#title")) {
                isTitle = Boolean.TRUE;
            } else if (line.startsWith("#content")) {
                isTitle = Boolean.FALSE;
            } else if (Boolean.TRUE.equals(isTitle)) {
                // 解析主题，采用覆盖策略
                if (StrUtil.isNotEmpty(line)) {
                    codeTitle = line;
                }
            } else if (Boolean.FALSE.equals(isTitle)) {
                // 解析内容模板，采用追加模式
                builder.append(line);
            }
        }
        codeTemplate = builder.toString();
        firstLoad = false;
    }
}
