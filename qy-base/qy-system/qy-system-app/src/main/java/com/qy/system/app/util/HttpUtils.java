package com.qy.system.app.util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    public final static String CHARSET_UTF8 = "UTF-8";

    /**
     * 列表接口 http返回头信息
     *
     * @param response
     * @param paramer
     * @author ran
     */
    public static void listHeader(HttpServletResponse response, Map<String, Object> paramer) {
        //默认httpCode 是201成功
        Integer httpCode = paramer.containsKey("httpCode") ? Integer.parseInt(paramer.get("httpCode").toString()) : 201;
        String message = paramer.containsKey("message") ? paramer.get("message").toString() : "";

        if (httpCode >= 200 && httpCode < 300) {
            response.setHeader("X-Pagination-Current-Page", paramer.get("page").toString());
            response.setHeader("X-Pagination-Per-Page", paramer.get("perPage").toString());
            response.setHeader("X-Pagination-Page-Count", paramer.containsKey("pageCount") ? paramer.get("pageCount").toString() : "0");
            response.setHeader("X-Pagination-Total-Count", paramer.containsKey("totalCount") ? paramer.get("totalCount").toString() : "0");
        }
        response.setHeader("X-Time", DateUtils.timeStamp() + "");
        response.setHeader("X-Message", StringToUnicode.stringToUnicode(message));
        response.setStatus(httpCode);
    }

    /**
     * 列表接口 http返回头信息
     *
     * @param response
     * @param pageOptions 分页参数 page,prePage 例：1,10
     * @param dataOptions 分页数据count参数 pageCount,totalCount 例：10,100
     */
    public static void listHeader(HttpServletResponse response, String pageOptions, String dataOptions) {
        Map<String, Object> paramer = new HashMap<>();
        String[] arrPage = pageOptions.split(",");
        String[] arrData = dataOptions.split(",");
        if (arrPage.length != 2) {
            modifyFailure(response, "分页参数错误！");
            return;
        } else if (arrPage.length != 2) {
            modifyFailure(response, "数据参数错误！");
            return;
        }

        paramer.put("httpCode", 200);
        paramer.put("page", Integer.parseInt(arrPage[0]));
        paramer.put("perPage", Integer.parseInt(arrPage[1]));
        paramer.put("message", "查询成功！");

        paramer.put("pageCount", Integer.parseInt(arrData[0]));
        paramer.put("totalCount", Integer.parseInt(arrData[1]));

        listHeader(response, paramer);
    }

    /**
     * 新增 更新 删除操作 http头设置
     *
     * @param response
     * @param message
     * @param httpCode
     */
    public static void setHeader(HttpServletResponse response, String message, Integer httpCode) {
        if (message == null) {
            message = "";
        }
        message = message.contains(";") ? message.substring(0, message.indexOf(";")).replace("\r", "").replace("\n", "<br/>") : message;

        String encodeMessage = null;
        try {
            encodeMessage = URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("编码头部消息出错");
        }

        response.setHeader("X-Message", encodeMessage);
        response.setStatus(HttpStatus.OK.value());
    }

    /**
     * 新增 更新 删除操作 成功头设置
     *
     * @param response
     * @param message
     */
    public static void modifySuccess(HttpServletResponse response, String message) {
        setHeader(response, message, ResponseCode.MODIFICATION_SUCCESS_CODE);
    }

    /**
     * 新增 更新 删除操作 成功头设置
     *
     * @param response
     * @param message
     * @param httpCode
     */
    public static void modifySuccess(HttpServletResponse response, String message, Integer httpCode) {
        setHeader(response, message, httpCode);
    }

    /**
     * 新增 更新 删除操作 失败头设置
     *
     * @param response
     * @param message
     */
    public static void modifyFailure(HttpServletResponse response, String message) {
        setHeader(response, message, ResponseCode.MODIFICATION_FAIL_CODE);
    }

    /**
     * 新增 更新 删除操作 失败头设置
     *
     * @param response
     * @param e
     */
    public static void modifyFailure(HttpServletResponse response, Exception e) {
        e.printStackTrace();
        setHeader(response, StringUtils.isNullOfEmpty(e.getMessage()) ? "服务器内部错误" : e.getMessage(), ResponseCode.MODIFICATION_FAIL_CODE);
    }

    /**
     * 新增 更新 删除操作 失败头设置
     *
     * @param response
     * @param message
     * @param httpCode
     */
    public static void modifyFailure(HttpServletResponse response, String message, Integer httpCode) {
        setHeader(response, message, httpCode);
    }

    /**
     * token 验证失败头设置
     *
     * @param response
     * @param message
     */
    public static void tokenFailure(HttpServletResponse response, String message) {
        setHeader(response, message, ResponseCode.TOKEN_INVALID_CODE);
    }

    /**
     * 下载文件
     *
     * @param response
     * @param fileName
     * @param suffix
     * @throws Exception
     */
    public static void download(HttpServletResponse response, String fileName, String suffix) throws Exception {
        fileHead(response, fileName, suffix);
    }

    /**
     * 生成二维码
     *
     * @param response
     * @param content
     * @throws Exception
     */
    public static void qrcode(HttpServletResponse response, String content) throws Exception {
        String type = "png";
        fileHead(response, type);
        OutputStream os = response.getOutputStream();
        QrcodeUtils.create(os, content);
    }

    /**
     * 生成并下载二维码
     *
     * @param response
     * @param content
     * @param fileName
     * @throws Exception
     */
    public static void qrcodeDownload(HttpServletResponse response, String content, String fileName) throws Exception {
        String type = "png";
        fileHead(response, fileName, type);
        OutputStream os = response.getOutputStream();
        QrcodeUtils.create(os, content);
    }

    /**
     * 文件头参数配置
     *
     * @param response
     * @param type
     */
    public static void fileHead(HttpServletResponse response, String type) {
        String fileType;

        switch (type.toLowerCase()) {
            case "pdf":
                fileType = "application/pdf";
                break;
            case "exe":
                fileType = "application/octet-stream";
                break;
            case "zip":
                fileType = "application/zip";
                break;
            case "doc":
                fileType = "application/msword";
                break;
            case "xls":
            case "xlsx":
                fileType = "application/vnd.ms-excel";
                break;
            case "ppt":
                fileType = "application/vnd.ms-powerpoint";
                break;
            case "gif":
                fileType = "image/gif";
                break;
            case "png":
                fileType = "image/png";
                break;
            case "jpg":
                fileType = "image/jp";
                break;
            default:
                fileType = "application/force-download";
                break;
        }

        response.setHeader("Content-Typ", fileType);
    }

    /**
     * 文件头 参数配置
     *
     * @param response
     * @param type
     * @param fileName
     */
    public static void fileHead(HttpServletResponse response, String fileName, String type) throws UnsupportedEncodingException {
        fileHead(response, type);

        response.setHeader("Pragma", "public");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Contro", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Content-Disposition", MessageFormat.format("attachment; filename={0}.{1};", URLEncoder.encode(fileName, "UTF-8"), type));
        response.setHeader("Content-Transfer-Encoding", "binary");
    }


    /**
     * 构造 application/x-www-form-urlencoded 提交参数
     *
     * @param obj
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    private static <T> List<NameValuePair> buildFormParams(T obj) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Map<String, String> params = ObjectUtils.toSnakeMap(obj);
        List<NameValuePair> paramList = new ArrayList();
        for (Map.Entry<String, String> m : params.entrySet()) {
            paramList.add(new BasicNameValuePair(m.getKey(), m.getValue()));
        }

        return paramList;
    }

    /**
     * URL转码
     *
     * @param input
     * @return
     */
    private static String URLEncode(Object input) {
        try {
            return URLEncoder.encode(input + "", CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * URL解码
     *
     * @param input
     * @return
     */
    private static String URLDecode(String input) {
        try {
            return URLDecoder.decode(input, CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Map转URL的参数字符串 转码
     *
     * @param params 请求参数
     * @return param string
     */
    public static String mapToGetParamStr(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), CHARSET_UTF8)).append("&");
        }

        return sb.toString();
    }

    /**
     * Map转URL参数字符串 不转码
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String mapToParamStr(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        return sb.toString();
    }

}

