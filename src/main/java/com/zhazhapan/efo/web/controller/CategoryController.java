package com.zhazhapan.efo.web.controller;

import com.zhazhapan.efo.annotation.AuthInterceptor;
import com.zhazhapan.efo.entity.Category;
import com.zhazhapan.efo.enums.InterceptorLevel;
import com.zhazhapan.efo.service.ICategoryService;
import com.zhazhapan.efo.util.ControllerUtils;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2018/1/30
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {this.categoryService = categoryService;}

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public String add(@PathVariable("name") String name) {
        return ControllerUtils.getResponse(categoryService.insert(name));
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable("id") int id, String name) {
        boolean isSuccess = Checker.isNotEmpty(name) && categoryService.update(id, name);
        return ControllerUtils.getResponse(isSuccess);
    }

    @AuthInterceptor(InterceptorLevel.ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String remove(@PathVariable("id") int id) {
        return ControllerUtils.getResponse(categoryService.remove(id));
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable("id") int id) {
        Category category = categoryService.getById(id);
        if (Checker.isNull(category)) {
            return ControllerUtils.getResponse(ValueConsts.FALSE);
        } else {
            return category.toString();
        }
    }

    @AuthInterceptor(InterceptorLevel.NONE)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAll() {
        return Formatter.listToJson(categoryService.getAll());
    }
}
