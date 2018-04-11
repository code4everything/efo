package com.zhazhapan.efo.util;

import com.zhazhapan.efo.service.ICategoryService;
import com.zhazhapan.efo.service.IFileService;
import com.zhazhapan.efo.service.IUserService;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.ReflectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * @author pantao
 * @since 2018/2/28
 */
@Component
public class ServiceUtils {

    private static IUserService userService;

    private static IFileService fileService;

    private static ICategoryService categoryService;

    private static Logger logger = Logger.getLogger(ServiceUtils.class);

    @Autowired
    public ServiceUtils(IUserService userService, IFileService fileService, ICategoryService categoryService) {
        ServiceUtils.userService = userService;
        ServiceUtils.fileService = fileService;
        ServiceUtils.categoryService = categoryService;
    }

    public static int getUserId(String usernameOrEmail) {
        return Checker.isEmpty(usernameOrEmail) ? ValueConsts.ZERO_INT : userService.getUserId(usernameOrEmail);
    }

    public static long getFileId(String fileName) {
        return Checker.isEmpty(fileName) ? ValueConsts.ZERO_INT : fileService.getFileId(fileName);
    }

    public static int getCategoryId(String categoryName) {
        return Checker.isEmpty(categoryName) ? ValueConsts.ZERO_INT : categoryService.getIdByName(categoryName);
    }

    public static Object invokeFileFilter(Object object, String methodName, String user, String file, String
            category, int offset) {
        try {
            return ReflectUtils.invokeMethodUseBasicType(object, methodName, new Object[]{getUserId(user), getFileId
                    (file), file, getCategoryId(category), offset});
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
