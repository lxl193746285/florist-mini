package com.qy.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;

/**
 * 自动生成代码相关公共方法
 * @author FeiGuangFu
 * @since 2020-12-21
 */
public class AutoGenerateUtils {

    /**
     * 验证录入校验
     * @param result
     */
    public static void validation(BindingResult result) {
        if (result.hasErrors()) {
            FieldError firstError = ValidateUtils.getFirstFieldError(result);

            if(null == firstError)
            {
                firstError = result.getFieldError();
            }

            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, firstError.getDefaultMessage());
        }
    }

    /**
     * 页面默认统一处理
     *
     * @param page
     * @param perPage
     * @return
     */
    public static IPage getPage(Integer page, Integer perPage,Integer paginationPerPage) {
        perPage = perPage == null ? paginationPerPage : perPage;
        page = page == null ? 1 : page;

        if (perPage > 1000) {
            perPage = 1000;
        }

        if (page <= 0) {
            page = 1;
        }

        return new Page(page, perPage);
    }

    public static void initResponseByPage(IPage iPage, HttpServletResponse response)
    {
        HttpUtils.listHeader(
                response,
                String.format("%s,%s", iPage.getCurrent(), iPage.getSize()),
                String.format("%s,%s", iPage.getPages(), iPage.getTotal())
        );
    }

    final static String IS_DELETED = "is_deleted";
    final static String ID = "id";

    public static LambdaQueryWrapper getLambdaQueryWrapper()
    {
        return getQueryWrapper().lambda();
    }

    private static QueryWrapper getQueryWrapper()
    {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(IS_DELETED,0);

        return queryWrapper;
    }

    public static QueryWrapper getQueryWrapperById(Long id)
    {
        QueryWrapper queryWrapper = getQueryWrapper();
        queryWrapper.eq(ID,id);

        return queryWrapper;
    }

}
