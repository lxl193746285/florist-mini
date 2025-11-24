package com.qy.system.app.util;

import com.qy.rest.exception.ValidationException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 * Rest控制器相关公共方法
 *
 * @author FeiGuangFu
 * @since 2020-12-21
 */
public class RestUtils {
    final static String IS_DELETED = "is_deleted";
    final static String ID = "id";

    /**
     * 创建日期
     */
    public final static String CREATE_TIME = "create_time";

    /**
     * 更新日期
     */
    public final static String UPDATE_TIME = "update_time";

    /**
     * 验证录入校验
     *
     * @param result
     */
    public static void validation(BindingResult result) {
        if (result.hasErrors()) {
            FieldError firstError = ValidateUtils.getFirstFieldError(result);

            if (null == firstError) {
                firstError = result.getFieldError();
            }

            throw new ValidationException(firstError != null ? firstError.getDefaultMessage() : "未知验证错误");
        }
    }

    /**
     * 页面默认统一处理
     *
     * @param page
     * @param perPage
     * @return
     */
    public static IPage getPage(Integer page, Integer perPage, Integer paginationPerPage) {
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

    /**
     * 条件追加日期查询
     *
     * @param queryWrapper
     * @param fieldName
     * @param startDateString
     * @param endDateString
     */
    public static void appendDateConditionByDate(QueryWrapper queryWrapper, String fieldName, String startDateString, String endDateString) {
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (!StringUtils.isNullOfEmpty(startDateString)) {
            startDate = LocalDate.parse(startDateString);
        }

        if (!StringUtils.isNullOfEmpty(endDateString)) {
            endDate = LocalDate.parse(endDateString);
        }

        queryWrapper.ge(null != startDate, fieldName, startDate);
        if (null != endDate) {
            queryWrapper.le(fieldName, endDate.atTime(23, 59, 59));
        }
    }

    /**
     * 条件追加日期查询
     *
     * @param queryWrapper
     * @param fieldName
     * @param startDateString
     * @param endDateString
     */
    public static void appendDateConditionByInt(QueryWrapper queryWrapper, String fieldName, String startDateString, String endDateString) {
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (!StringUtils.isNullOfEmpty(startDateString)) {
            startDate = LocalDate.parse(startDateString);
            queryWrapper.ge(null != startDate, fieldName, DateUtils.localDateToTimeStamp(startDate));
        }


        if (!StringUtils.isNullOfEmpty(endDateString)) {
            endDate = LocalDate.parse(endDateString);
            queryWrapper.le(fieldName, DateUtils.localDateTimeToTimeStamp(endDate.atTime(23, 59, 59)));
        }

    }

    public static void initResponseByPage(IPage iPage, HttpServletResponse response) {
        HttpUtils.listHeader(
                response,
                String.format("%s,%s", iPage.getCurrent(), iPage.getSize()),
                String.format("%s,%s", iPage.getPages(), iPage.getTotal())
        );
    }

    public static LambdaQueryWrapper getLambdaQueryWrapper() {
        return getQueryWrapper().lambda();
    }

    /**
     * 默认过滤已删除数据
     *
     * @return
     */
    public static QueryWrapper getQueryWrapper() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(IS_DELETED, 0);

        return queryWrapper;
    }

    public static QueryWrapper getQueryWrapperById(Integer id) {
        QueryWrapper queryWrapper = getQueryWrapper();
        queryWrapper.eq(ID, id);

        return queryWrapper;
    }

    public static QueryWrapper getQueryWrapperById(Long id) {
        QueryWrapper queryWrapper = getQueryWrapper();
        queryWrapper.eq(ID, id);

        return queryWrapper;
    }
}