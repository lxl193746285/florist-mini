package com.qy.base.interfaces.system.web;

import com.alibaba.fastjson.JSON;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.system.app.comment.BaseController;
import com.qy.system.app.dismodel.dto.QrcodeModelDTO;
import com.qy.system.app.dismodel.dto.QrcodeModelFormDTO;
import com.qy.system.app.dismodel.dto.QrcodeModelQueryDTO;
import com.qy.system.app.dismodel.entity.QrcodeModelEntity;
import com.qy.system.app.dismodel.service.QrcodeModelService;
import com.qy.system.app.util.ParseUtils;
import com.qy.system.app.util.QRCodeUtil;
import com.qy.system.app.util.RestUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 二维码配置模板
 *
 * @author sxj
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/v4/system/qrcode-model")
public class QrcodeModelController extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private QrcodeModelService distributionQrcodeModelService;

    /**
     * 获取二维码配置模板列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    public List<QrcodeModelDTO> getDistributionQrcodeModels(
        @ModelAttribute QrcodeModelQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        IPage iPage = this.getPagination();
        if(queryDTO.getStartCreateDate()!=null){
            queryDTO.setStartCreateDate(queryDTO.getStartCreateDate()+" 00:00:00");
        }
        if(queryDTO.getEndCreateDate()!=null){
            queryDTO.setEndCreateDate(queryDTO.getEndCreateDate()+" 23:59:59");
        }
        IPage<QrcodeModelEntity> pm = distributionQrcodeModelService.getDistributionQrcodeModels(iPage, queryDTO, currentUser);

        RestUtils.initResponseByPage(pm, response);
        return distributionQrcodeModelService.mapperToDTO(pm.getRecords(), currentUser);
    }

    /**
     * 获取单个二维码配置模板
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    public QrcodeModelDTO getDistributionQrcodeModel(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
                EmployeeIdentity currentUser = context.getEmployee();

        QrcodeModelEntity distributionQrcodeModelEntity = distributionQrcodeModelService.getDistributionQrcodeModel(id, currentUser);

        return distributionQrcodeModelService.mapperToDTO(distributionQrcodeModelEntity, currentUser);
    }

    /**
     * 创建单个二维码配置模板
     *
     * @param distributionQrcodeModelFormDTO
     * @param response
     * @return
     */
    @PostMapping
    public ResponseEntity createDistributionQrcodeModel(
        @Valid @RequestBody QrcodeModelFormDTO distributionQrcodeModelFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
                EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        distributionQrcodeModelService.createDistributionQrcodeModel(distributionQrcodeModelFormDTO, currentUser);
        return ResponseUtils.ok("二维码配置模板新增成功").build();
    }

    /**
     * 修改单个二维码配置模板
     *
     * @param id
     * @param distributionQrcodeModelFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity updateDistributionQrcodeModel(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody QrcodeModelFormDTO distributionQrcodeModelFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
                EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        distributionQrcodeModelService.updateDistributionQrcodeModel(id, distributionQrcodeModelFormDTO, currentUser);
        return ResponseUtils.ok("二维码配置模板修改成功").build();
    }

    /**
     * 删除单个二维码配置模板
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDistributionQrcodeModel(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
                EmployeeIdentity currentUser = context.getEmployee();

        distributionQrcodeModelService.deleteDistributionQrcodeModel(id, currentUser);
        return ResponseUtils.ok("二维码配置模板删除成功").build();
    }

    /**
     * 生成二维码
     * @param scene
     * @param values
     * @param response
     */
    @GetMapping("/qrcode")
    public void getDistributionQrcode(@RequestParam String scene,@RequestParam String values,
                                      HttpServletResponse response){
        //根据模板id获取模板内容
        LambdaQueryWrapper<QrcodeModelEntity> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(QrcodeModelEntity::getScene,scene)
                .eq(QrcodeModelEntity::getIsDeleted,0);
        QrcodeModelEntity distributionQrcodeModelEntity = distributionQrcodeModelService.getOne(queryWrapper);
        if(distributionQrcodeModelEntity==null){
            throw new NotFoundException("未找到二维码模板！");
        }
        String qrStr = distributionQrcodeModelEntity.getContent();
        HashMap hashMap = JSON.parseObject(values, HashMap.class);
        BufferedImage image;
        qrStr = ParseUtils.getDataCondition(qrStr, hashMap);
        // 禁止图像缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        image = QRCodeUtil.createImage(qrStr);
        // 创建二进制的输出流
        try (ServletOutputStream sos = response.getOutputStream()) {
            ImageIO.write(image, "jpeg", sos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

