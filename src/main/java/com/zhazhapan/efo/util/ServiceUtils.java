package com.zhazhapan.efo.util;

import com.zhazhapan.efo.service.ICategoryService;
import com.zhazhapan.efo.service.IFileService;
import com.zhazhapan.efo.service.IUserService;
import com.zhazhapan.efo.service.impl.UserServiceImpl;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author pantao
 * @since 2018/2/28
 */
@Component
public class ServiceUtils {

    private static IUserService userService;

    private static IFileService fileService;

    private static ICategoryService categoryService;

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
}
