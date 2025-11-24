package com.qy.system.app.dismodel.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.system.app.dismodel.dto.QrcodeModelDTO;
import com.qy.system.app.dismodel.dto.QrcodeModelFormDTO;
import com.qy.system.app.dismodel.dto.QrcodeModelQueryDTO;
import com.qy.system.app.dismodel.entity.QrcodeModelEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 二维码配置模板 服务类
 *
 * @author sxj
 * @since 2022-03-17
 */
public interface QrcodeModelService extends IService<QrcodeModelEntity> {

    /**
     * 获取二维码配置模板分页列表
     *
     * @param iPage 分页
     * @param distributionQrcodeModelQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 二维码配置模板列表
     */
    IPage<QrcodeModelEntity> getDistributionQrcodeModels(IPage iPage, QrcodeModelQueryDTO distributionQrcodeModelQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取二维码配置模板列表
     *
     * @param distributionQrcodeModelQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 二维码配置模板列表
     */
    List<QrcodeModelEntity> getDistributionQrcodeModels(QrcodeModelQueryDTO distributionQrcodeModelQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取单个二维码配置模板
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个二维码配置模板
     */
    QrcodeModelEntity getDistributionQrcodeModel(Long id, EmployeeIdentity currentUser);

    /**
     * 创建单个二维码配置模板
     *
     * @param distributionQrcodeModelFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的二维码配置模板
     */
    QrcodeModelEntity createDistributionQrcodeModel(QrcodeModelFormDTO distributionQrcodeModelFormDTO, EmployeeIdentity currentUser);

    /**
     * 更新单个二维码配置模板
     *
     * @param distributionQrcodeModelFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的二维码配置模板
     */
    QrcodeModelEntity updateDistributionQrcodeModel(Long id, QrcodeModelFormDTO distributionQrcodeModelFormDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个二维码配置模板
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的二维码配置模板
     */
    QrcodeModelEntity deleteDistributionQrcodeModel(Long id, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param distributionQrcodeModelEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    QrcodeModelDTO mapperToDTO(QrcodeModelEntity distributionQrcodeModelEntity, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param distributionQrcodeModelEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<QrcodeModelDTO> mapperToDTO(List<QrcodeModelEntity> distributionQrcodeModelEntityList, EmployeeIdentity currentUser);
}
