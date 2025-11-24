package com.qy.region.app.infrastructure.persistence;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qy.region.app.application.query.AreaQuery;
import com.qy.region.app.infrastructure.persistence.mybatis.dataobject.AreaDO;

import java.util.List;

/**
 * 地区数据资源库
 *
 * @author legendjw
 */
public interface AreaDataRepository {
    /**
     * 根据查询获取地区
     *
     * @param query
     * @return
     */
    List<AreaDO> findByQuery(AreaQuery query);
    /**
     * 根据查询获取地区
     *
     * @param query
     * @return
     */
    IPage<AreaDO> findByQueryPage(AreaQuery query);

    /**
     * 根据id查询地区
     *
     * @param areaId
     * @return
     */
    AreaDO findById(Long areaId);

    AreaDO findByCode(String code);

    AreaDO findByCondition(AreaQuery query);

    void createArea(AreaDO areaDO);

    void updateArea(AreaDO areaDO);

    void deleteArea(Long id);

    boolean hasChildren(Long id);
}
