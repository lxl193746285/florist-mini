package com.qy.crm.customer.app.application.service;

import com.qy.crm.customer.app.application.dto.OpenBankDTO;
import com.qy.crm.customer.app.application.query.OpenBankQuery;
import com.qy.rest.pagination.Page;
import com.qy.security.session.Identity;

import java.util.List;

/**
 * 开户行查询服务
 *
 * @author legendjw
 */
public interface OpenBankQueryService {
    /**
     * 查询客户开户行
     *
     * @param query
     * @param identity
     * @return
     */
    Page<OpenBankDTO> getOpenBanks(OpenBankQuery query, Identity identity);

    /**
     * 根据关联模块获取开户行
     *
     * @param relatedModuleId
     * @param relatedDataId
     * @return
     */
    List<OpenBankDTO> getOpenBanksByRelatedModule(String relatedModuleId, Long relatedDataId);

    /**
     * 根据ID查询客户开户行
     *
     * @param id
     * @return
     */
    OpenBankDTO getOpenBankById(Long id, Identity identity);

    /**
     * 根据ID查询客户开户行
     *
     * @param id
     * @return
     */
    OpenBankDTO getOpenBankById(Long id);
}