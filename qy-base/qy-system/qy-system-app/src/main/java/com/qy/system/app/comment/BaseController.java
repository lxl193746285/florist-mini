package com.qy.system.app.comment;

import com.qy.rest.constant.RestHeaderConstant;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.security.session.OrganizationSessionContext;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired(required = false)
    protected OrganizationSessionContext sessionContext;

    @Autowired(required = false)
    protected MemberSystemSessionContext memberSystemSessionContext;

    public HttpServletRequest getHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return requestAttributes != null ? ((ServletRequestAttributes) requestAttributes).getRequest() : null;
    }

    public ResponseEntity.BodyBuilder ok(IPage page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(RestHeaderConstant.PAGINATION_CURRENT_PAGE, String.valueOf(page.getCurrent()));
        headers.add(RestHeaderConstant.PAGINATION_PER_PAGE, String.valueOf(page.getSize()));
        headers.add(RestHeaderConstant.PAGINATION_PAGE_COUNT, String.valueOf(page.getPages()));
        headers.add(RestHeaderConstant.PAGINATION_TOTAL_COUNT, String.valueOf(page.getTotal()));
        return ResponseUtils.status(HttpStatus.OK).headers(headers);
    }

    protected <T> IPage<T> getPagination() {
        HttpServletRequest request = getHttpRequest();
        if (request != null) {
            String pageSize = request.getParameter("per_page");
            if (StringUtils.isEmpty(pageSize)) {
                pageSize = "10";
            }

            String currentPage = request.getParameter("page");
            if (StringUtils.isEmpty(currentPage)) {
                currentPage = "1";
            }

            Assert.isTrue(StringUtils.isNumeric(pageSize), "分页参数：per_page参数不是数字");
            Assert.isTrue(StringUtils.isNumeric(currentPage), "分页参数：page参数不算数字");
            int size = Integer.parseInt(pageSize);
            if (size > 1000) {
                size = 1000;
            }

            int current = Integer.parseInt(currentPage);
            Page<T> page = new Page((long) current, (long) size);

            return page;
        } else {
            throw new RuntimeException("request 为空");
        }
    }
}
