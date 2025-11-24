package com.qy.utils.excel;

import com.qy.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel导入导出工具类
 *
 * @author sunnyzyq
 * @date 2021/12/17
 */
@Slf4j
public class ExcelLargeUtils {

    private static final String XLSX = ".xlsx";
    private static final String XLS = ".xls";
    public static final String ROW_MERGE = "row_merge";
    public static final String COLUMN_MERGE = "column_merge";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String ROW_NUM = "rowNum";
    private static final String ROW_DATA = "rowData";
    private static final String ROW_TIPS = "rowTips";
    private static final String CELL_STRING = "";
    private static final int CELL_OTHER = 0;
    private static final int CELL_ROW_MERGE = 1;
    private static final int CELL_COLUMN_MERGE = 2;
    private static final int IMG_HEIGHT = 30;
    private static final int IMG_WIDTH = 30;
    private static final char LEAN_LINE = '/';
    private static final int BYTES_DEFAULT_LENGTH = 10240;
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
    // 整个 Excel 表格 book 对象
    SXSSFWorkbook book = new SXSSFWorkbook();
    // 每个 Sheet 页
    Sheet sheet = null;
    CellStyle headStyle = null;
    CellStyle rowStyle = null;
    int pageSize = 20000;
    Drawing<?> patriarch = null;
    boolean isAddIndex = true;//是否自动序号
    String indexName = "序号";//当自动序号时会根据此列名找到列索引
    int indexCol = 0;//自动序号列索引

    public ExcelLargeUtils(String sheetName, int pageSize) {
        this.pageSize = pageSize;
        String theSheetName = "";
        try {
            theSheetName = URLDecoder.decode(sheetName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 整个 Excel 表格 book 对象
        book.setCompressTempFiles(false); //是否压缩临时文件，否则写入速度更快，但更占磁盘，但程序最后是会将临时文件删掉的

        // 设置表头背景色（灰色）
        headStyle = book.createCellStyle();
        headStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.index);
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        // 设置表身背景色（默认色）
        rowStyle = book.createCellStyle();
        rowStyle.setAlignment(HorizontalAlignment.CENTER);
        rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 每个 Sheet 页
        sheet = book.createSheet(theSheetName);
        // 设置表格列宽度（默认为15个字节）
        sheet.setDefaultColumnWidth(15);
        patriarch = sheet.createDrawingPatriarch();
    }

    /**
     * 整理导出数据
     *
     * @param list
     * @param pageNo
     */
    public <T> void setExportData(List<T> list, int pageNo) {
        if (list == null || list.size() == 0) {
            return;
        }
        List<List<Object>> sheetDataList = getSheetData(list, pageNo);
        for (int i = 0; i < sheetDataList.size(); i++) {
            // 每个 Sheet 页中的行数据
            int col = i + pageSize * (pageNo - 1);
            if (pageNo > 1) {
                col = i + pageSize * (pageNo - 1) + 1;
            }
            Row row = sheet.createRow(col);
            List<Object> rowList = sheetDataList.get(i);
            for (int j = 0; j < rowList.size(); j++) {
                // 每个行数据中的单元格数据
                Object o = rowList.get(j);
                int v = 0;
                if (o instanceof URL) {
                    // 如果要导出图片的话, 链接需要传递 URL 对象
                    setCellPicture(book, row, patriarch, col, j, (URL) o);
                } else {
                    Cell cell = row.createCell(j);
                    if (col == 0) {
                        // 第一行为表头行，采用灰色底背景
                        v = setCellValue(cell, o, headStyle);
                    } else {
                        // 其他行为数据行，默认白底色
                        if (isAddIndex && j == indexCol) {//序号
                            o = col;
                        }
                        v = setCellValue(cell, o, rowStyle);
                    }
                }
//                    mergeArray[col][j] = v;
            }
        }
    }

    /**
     * 导出Excel
     *
     * @param response
     * @param file
     * @param fileName
     */
    public void exportExcel(HttpServletResponse response, File file, String fileName) {
        // 写数据
        if (response != null) {
            // 前端导出
            try {
                write(response, book, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (book != null) {
                    book.dispose();// 删除临时文件，很重要，否则磁盘可能会被写满
                }
            }
        } else {
            // 本地导出
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);
                ByteArrayOutputStream ops = new ByteArrayOutputStream();
                book.write(ops);
                fos.write(ops.toByteArray());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (book != null) {
                    book.dispose();
                }

            }
        }
    }

    public void setCellPicture(SXSSFWorkbook wb, Row sr, Drawing<?> patriarch, int x, int y, URL url) {
        // 设置图片宽高
        sr.setHeight((short) (IMG_WIDTH * IMG_HEIGHT));
        // （jdk1.7版本try中定义流可自动关闭）
        try (InputStream is = url.openStream(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buff = new byte[BYTES_DEFAULT_LENGTH];
            int rc;
            while ((rc = is.read(buff, 0, BYTES_DEFAULT_LENGTH)) > 0) {
                outputStream.write(buff, 0, rc);
            }
            // 设置图片位置
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, y, x, y + 1, x + 1);
            // 设置这个，图片会自动填满单元格的长宽
            anchor.setAnchorType(AnchorType.MOVE_AND_RESIZE);
            patriarch.createPicture(anchor, wb.addPicture(outputStream.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int setCellValue(Cell cell, Object o, CellStyle style) {
        // 设置样式
        cell.setCellStyle(style);
        // 数据为空时
        if (o == null) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue("");
            return CELL_OTHER;
        }
        // 是否为字符串
        if (o instanceof String) {
            String s = o.toString();
            cell.setCellType(CellType.STRING);
            cell.setCellValue(s);
            if (s.equals(ROW_MERGE)) {
                return CELL_ROW_MERGE;
            } else if (s.equals(COLUMN_MERGE)) {
                return CELL_COLUMN_MERGE;
            } else {
                return CELL_OTHER;
            }
        }
        // 是否为字符串
        if (o instanceof Integer || o instanceof Long || o instanceof Double || o instanceof Float) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(Double.parseDouble(o.toString()));
            return CELL_OTHER;
        }
        // 是否为Boolean
        if (o instanceof Boolean) {
            cell.setCellType(CellType.BOOLEAN);
            cell.setCellValue((Boolean) o);
            return CELL_OTHER;
        }
        // 如果是BigDecimal，则默认3位小数
        if (o instanceof BigDecimal) {
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(((BigDecimal) o).setScale(3, RoundingMode.HALF_UP).doubleValue());
            return CELL_OTHER;
        }
        // 如果是Date数据，则显示格式化数据
        if (o instanceof Date) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(formatDate((Date) o));
            return CELL_OTHER;
        }
        // 如果是其他，则默认字符串类型
        cell.setCellType(CellType.STRING);
        cell.setCellValue(o.toString());
        return CELL_OTHER;
    }

    public <T> List<List<Object>> getSheetData(List<T> list, int pageNo) {
        // 获取表头字段
        List<ExcelClassField> excelClassFieldList = getExcelClassFieldList(list.get(0).getClass());
        List<String> headFieldList = new ArrayList<>();
        List<Object> headList = new ArrayList<>();
        Map<String, ExcelClassField> headFieldMap = new HashMap<>();
        boolean hasIndexName = false;
        int i = 0;
        for (ExcelClassField each : excelClassFieldList) {
            String fieldName = each.getFieldName();
            headFieldList.add(fieldName);
            headFieldMap.put(fieldName, each);
            headList.add(each.getName());
            if (isAddIndex && indexName.equals(each.getName())) {
                hasIndexName = true;
                indexCol = i;
            }
            i++;
        }
        isAddIndex = hasIndexName;
        // 添加表头名称
        List<List<Object>> sheetDataList = new ArrayList<>();
        if (pageNo == 1) {
            sheetDataList.add(headList);
        }

        // 获取表数据
        for (T t : list) {
            Map<String, Object> fieldDataMap = getFieldDataMap(t);
            Set<String> fieldDataKeys = fieldDataMap.keySet();
            List<Object> rowList = new ArrayList<>();
            for (String headField : headFieldList) {
                if (!fieldDataKeys.contains(headField)) {
                    continue;
                }
                Object data = fieldDataMap.get(headField);
                if (data == null) {
                    rowList.add("");
                    continue;
                }
                ExcelClassField cf = headFieldMap.get(headField);
                // 判断是否有映射关系
                LinkedHashMap<String, String> kvMap = cf.getKvMap();
                if (kvMap == null || kvMap.isEmpty()) {
                    rowList.add(data);
                    continue;
                }
                String val = kvMap.get(data.toString());
                if (StringUtils.isNotEmpty(val) && isNumeric(val)) {
                    rowList.add(Double.valueOf(val));
                } else {
                    rowList.add(val);
                }
            }
            sheetDataList.add(rowList);
        }
        return sheetDataList;
    }

    private <T> Map<String, Object> getFieldDataMap(T t) {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = t.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                String fieldName = field.getName();
                field.setAccessible(true);
                Object object = field.get(t);
                map.put(fieldName, object);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    private <T> List<ExcelClassField> getExcelClassFieldList(Class<T> clazz) {
        // 解析所有字段
        Field[] fields = clazz.getDeclaredFields();
        boolean hasExportAnnotation = false;
        Map<Integer, List<ExcelClassField>> map = new LinkedHashMap<>();
        List<Integer> sortList = new ArrayList<>();
        for (Field field : fields) {
            ExcelClassField cf = getExcelClassField(field);
            if (cf.getHasAnnotation() == 1) {
                hasExportAnnotation = true;
            }
            int sort = cf.getSort();
            if (map.containsKey(sort)) {
                map.get(sort).add(cf);
            } else {
                List<ExcelClassField> list = new ArrayList<>();
                list.add(cf);
                sortList.add(sort);
                map.put(sort, list);
            }
        }
        Collections.sort(sortList);
        // 获取表头
        List<ExcelClassField> headFieldList = new ArrayList<>();
        if (hasExportAnnotation) {
            for (Integer sort : sortList) {
                for (ExcelClassField cf : map.get(sort)) {
                    if (cf.getHasAnnotation() == 1) {
                        headFieldList.add(cf);
                    }
                }
            }
        } else {
            headFieldList.addAll(map.get(0));
        }
        return headFieldList;
    }

    private ExcelClassField getExcelClassField(Field field) {
        ExcelClassField cf = new ExcelClassField();
        String fieldName = field.getName();
        cf.setFieldName(fieldName);
        ExcelExport annotation = field.getAnnotation(ExcelExport.class);
        // 无 ExcelExport 注解情况
        if (annotation == null) {
            cf.setHasAnnotation(0);
            cf.setName(fieldName);
            cf.setSort(0);
            return cf;
        }
        // 有 ExcelExport 注解情况
        cf.setHasAnnotation(1);
        cf.setName(annotation.value());
        String example = getString(annotation.example());
        if (!example.isEmpty()) {
            if (isNumeric(example)) {
                cf.setExample(Double.valueOf(example));
            } else {
                cf.setExample(example);
            }
        } else {
            cf.setExample("");
        }
        cf.setSort(annotation.sort());
        // 解析映射
        String kv = getString(annotation.kv());
        cf.setKvMap(getKvMap(kv));
        return cf;
    }

    private String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }

    private boolean isNumeric(String str) {
        if ("0.0".equals(str)) {
            return true;
        }
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private String getString(String s) {
        if (s == null) {
            return "";
        }
        if (s.isEmpty()) {
            return s;
        }
        return s.trim();
    }

    private LinkedHashMap<String, String> getKvMap(String kv) {
        LinkedHashMap<String, String> kvMap = new LinkedHashMap<>();
        if (kv.isEmpty()) {
            return kvMap;
        }
        String[] kvs = kv.split(";");
        if (kvs.length == 0) {
            return kvMap;
        }
        for (String each : kvs) {
            String[] eachKv = getString(each).split("-");
            if (eachKv.length != 2) {
                continue;
            }
            String k = eachKv[0];
            String v = eachKv[1];
            if (k.isEmpty() || v.isEmpty()) {
                continue;
            }
            kvMap.put(k, v);
        }
        return kvMap;
    }

    private void write(HttpServletResponse response, SXSSFWorkbook book, String fileName) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.addHeader("X-Message", URLEncoder.encode("操作成功", "UTF-8").replaceAll("\\+", "%20"));
        fileName = URLEncoder.encode(fileName + ".xlsx", "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ";" + "filename*=utf-8''" + fileName);
        ServletOutputStream out = response.getOutputStream();
        book.write(out);
        out.flush();
        out.close();
    }
}