package com.qy.system.app.autonumber.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.system.app.autonumber.dto.SnSetsDetailDTO;
import com.qy.system.app.autonumber.dto.SnSetsDetailFormDTO;
import com.qy.system.app.autonumber.dto.SnSetsDetailQueryDTO;
import com.qy.system.app.autonumber.entity.SnSetsDetailEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface SnSetsDetailService extends IService<SnSetsDetailEntity> {
    /**
     * 创建单个编号规则设置-子
     *
     * @param snSetsDetailFormDTO 表单对象
     * @param currentUser            当前登录用户
     * @return 新创建的编号规则-子行
     */
    SnSetsDetailEntity createSnSetsDetail(SnSetsDetailFormDTO snSetsDetailFormDTO, EmployeeIdentity currentUser);

    /**
     * 根据主表的noid删除子表数据
     *
     * @param noid
     * @param currentUser
     * @return
     */
    SnSetsDetailEntity deleteSnSetsDetailByNoId(String noid, EmployeeIdentity currentUser);

    /**
     * 获取编号规则设置-子列表
     *
     * @param snSetsDetailQueryDTO 查询条件
     * @return 编号规则设置-子列表
     */
    List<SnSetsDetailDTO> getSnSetsDetails(SnSetsDetailQueryDTO snSetsDetailQueryDTO);

    /**
     * 更新单个编号规则设置-子
     *
     * @param snSetsDetailFormDTO 表单对象
     * @param currentUser            当前登录用户
     * @return 更新编号规则-子行
     */
    SnSetsDetailEntity updateSnSetsDetail(SnSetsDetailFormDTO snSetsDetailFormDTO, EmployeeIdentity currentUser);

}
