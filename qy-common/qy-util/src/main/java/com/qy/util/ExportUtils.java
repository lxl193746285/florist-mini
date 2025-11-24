package com.qy.util;

/**
 * Created by GYJ on 2017-08-24.
 * Updated by XSJ on 2020-06-09.
 */

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 导出Excel公共方法
 * @version 1.0
 *
 *
 */
public class ExportUtils {

    //显示的导出表的标题
    private String title;
    //导出表的列名
    private String[] rowName ;

    private List<Object[]> dataList = new ArrayList<Object[]>();

    //副标题
    private String[] subtitle;

    //列宽,如果内容较长可以设置列宽，参数为key为列下标，value为列宽，计算时列宽会乘以256，传值大概为每一个字加一，中文字符加二
    private Map<Integer,Integer> columnWidth = new HashMap<>();

    private boolean isImage = true;

    //构造方法，传入要导出的数据
    public ExportUtils(String title, String[] rowName, List<Object[]>  dataList){
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
    }
    public ExportUtils(String title, String[] subtitle, String[] rowName, List<Object[]>  dataList){
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
        this.subtitle=subtitle;
    }
    public ExportUtils(String title, String[] subtitle, String[] rowName, List<Object[]>  dataList, Map<Integer,Integer> columnWidth){
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
        this.subtitle=subtitle;
        this.columnWidth=columnWidth;
    }
    public ExportUtils(String title, String[] subtitle, String[] rowName, List<Object[]>  dataList, Map<Integer,Integer> columnWidth, boolean isImage){
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
        this.subtitle=subtitle;
        this.columnWidth=columnWidth;
        this.isImage = isImage;
    }
    /*
     * 导出数据
     * */
    public void export(HttpServletResponse response) throws Exception{
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();    // 创建工作簿对象
            HSSFSheet sheet=null;
            int a=1;
            String str=title;
            if(dataList.size()==0 || dataList==null){
                // 创建工作表
                // 产生表格标题行

                sheet = workbook.createSheet(title);
//                sheet = workbook.createSheet();
//                workbook.setSheetName(0, title);
//                sheet.autoSizeColumn(1, true);
                HSSFRow rowm = sheet.createRow(0);
                HSSFCell cellTiltle = rowm.createCell(0);

                //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
                HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象
                HSSFCellStyle style = this.getStyle(workbook);                  //单元格样式对象

                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (rowName.length - 1)));
                cellTiltle.setCellStyle(columnTopStyle);
                cellTiltle.setCellValue(title);

                int initial = 2;
                if (subtitle != null) {
                    initial = subtitle.length + 1;
                    for (int i = 0; i < subtitle.length; i++) {
                        //产生多行副标题
                        int num = i + 1;
                        HSSFCellStyle columnTopStyles = this.getColumnTopStyles(workbook);

                        HSSFRow rows = sheet.createRow(num);
                        HSSFCell cellSubtitle = rows.createCell(0);
                        cellSubtitle.setCellStyle(columnTopStyles);
                        sheet.addMergedRegion(new CellRangeAddress(num, num, 0, (rowName.length - 1)));
                        cellSubtitle.setCellStyle(columnTopStyles);
                        cellSubtitle.setCellValue(subtitle[i]);

                    }
                } else {
                    // 产生时间标题行t
                    HSSFRow rowt = sheet.createRow(1);
                    HSSFCell cellTime = rowt.createCell(0);

                    sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, (rowName.length - 1)));
                    cellTime.setCellStyle(columnTopStyle);
                    Calendar now = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    cellTime.setCellValue(new StringBuilder("日期:").append(df.format(new Date())).toString());
                }
                // 定义所需列数
                int columnNum = rowName.length;
                HSSFRow rowRowName = sheet.createRow(initial);                // 在索引2的位置创建行(最顶端的行开始的第二行)
            }
            for(int x=0;x<dataList.size();x++) {

                if(x == 0 || x%60000 == 0){
                    if(x!=0){
                        title=str+"("+a+++")";
                    }
                    response.setContentType("application/x-download");//下面三行是关键代码，处理乱码问题
                    response.setCharacterEncoding("utf-8");
                    response.setHeader("Content-Disposition", "attachment;filename="+new String(title.getBytes("gbk"), "iso8859-1")+".xls");
                    sheet = workbook.createSheet(title);
//                    sheet = workbook.createSheet();
//                    workbook.setSheetName(0, title);
                }else {
                    continue;
                }
                // 创建工作表
                // 产生表格标题行
                HSSFRow rowm = sheet.createRow(0);
                HSSFCell cellTiltle = rowm.createCell(0);

                //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
                HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象
                HSSFCellStyle style = this.getStyle(workbook);                  //单元格样式对象

                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (rowName.length - 1)));
                cellTiltle.setCellStyle(columnTopStyle);
                cellTiltle.setCellValue(title);

                int initial = 2;
                if (subtitle != null) {
                    initial = subtitle.length + 1;
                    for (int i = 0; i < subtitle.length; i++) {
                        //产生多行副标题
                        int num = i + 1;
                        HSSFCellStyle columnTopStyles = this.getColumnTopStyles(workbook);

                        HSSFRow rows = sheet.createRow(num);
                        HSSFCell cellSubtitle = rows.createCell(0);
                        cellSubtitle.setCellStyle(columnTopStyles);
                        sheet.addMergedRegion(new CellRangeAddress(num, num, 0, (rowName.length - 1)));
                        cellSubtitle.setCellStyle(columnTopStyles);
                        cellSubtitle.setCellValue(subtitle[i]);

                    }
                } else {
                    // 产生时间标题行t
                    HSSFRow rowt = sheet.createRow(1);
                    HSSFCell cellTime = rowt.createCell(0);

                    sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, (rowName.length - 1)));
                    cellTime.setCellStyle(columnTopStyle);
                    Calendar now = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    cellTime.setCellValue(new StringBuilder("日期:").append(df.format(new Date())).toString());
                }
                // 定义所需列数
                int columnNum = rowName.length;
                HSSFRow rowRowName = sheet.createRow(initial);                // 在索引2的位置创建行(最顶端的行开始的第二行)

                // 将列头设置到sheet的单元格中
                for (int n = 0; n < columnNum; n++) {
                    HSSFCell cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格
                    cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);             //设置列头单元格的数据类型
                    HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                    cellRowName.setCellValue(text);                                 //设置列头单元格的值
                    cellRowName.setCellStyle(columnTopStyle);                       //设置列头单元格样式
                }

                HSSFClientAnchor anchor = null;
                HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                //将查询出的数据设置到sheet对应的单元格中
                for (int i = x; i < dataList.size(); i++) {
                    Object[] obj = dataList.get(i);//遍历每个对象
                    int temp=i;
                    if(i>=60000){
                        temp =i%60000;
                    }
                    HSSFRow row = sheet.createRow(temp + initial + 1);//创建所需的行数

                    for (int j = 0; j < obj.length; j++) {
                        HSSFCell cell = null;   //设置单元格的数据类型
                        if (j == 0) {
                            cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
                            cell.setCellValue(i + 1);
                        }else if(obj[j] instanceof BigDecimal){
                            cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
                            cell.setCellValue(Double.valueOf(obj[j].toString()));
                        } else if(obj[j]!=null&&obj[j].toString().startsWith("http") && isImage){
//                            if(isImage){
                                cell = row.createCell(j);
                                row.setHeightInPoints(65);
                                URL url = new URL(obj[j].toString());
                                HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
                                httpUrl.connect();
                                httpUrl.getInputStream();
                                InputStream is = httpUrl.getInputStream();
                                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                                //创建一个Buffer字符串
                                byte[] buffer = new byte[1024];
                                //每次读取的字符串长度，如果为-1，代表全部读取完毕
                                int len = 0;
                                //使用一个输入流从buffer里把数据读取出来
                                while( (len=is.read(buffer)) != -1 ){
                                    //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                                    outStream.write(buffer, 0, len);
                                }
                                anchor = new HSSFClientAnchor(0, 0, 1023, 255,(short) j, i+3, (short) j, i+3);
                                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_DONT_RESIZE);
                                patriarch.createPicture(anchor, workbook.addPicture(outStream.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
                                columnWidth.put(j,30);
//                            }else{
//                                CellStyle linkStyle= getLinkStyle(workbook);
//                                cell = row.createCell(j, HSSFCell.CELL_TYPE_FORMULA);
//                                cell.setCellFormula("HYPERLINK(\"" + obj[j].toString()+ "\",\"图片展示\")");
//                                cell.setCellStyle(linkStyle);
//                                continue;
//                            }

                        }else{
                            cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                            if (obj[j] == null) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue(obj[j].toString());                       //设置单元格的值
                            }
                        }
                        cell.setCellStyle(style);                                   //设置单元格样式
                    }
                    if(temp==(59999)){
                        break;
                    }
                }
            }
            //让列宽随着导出的列长自动适应
            /*for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING&&currentCell.getStringCellValue()!=null) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if(colNum == 0){
                    sheet.setColumnWidth(colNum, 10);
                }else{
                    sheet.setColumnWidth(colNum, 10);
                }
            }*/
            if(columnWidth!=null){
                Set<Integer> k = columnWidth.keySet();
                for(Integer c : k){
                    sheet.setColumnWidth(c,columnWidth.get(c)*256);
                }
            }else{
                if(columnWidth!=null){
                    Set<Integer> k = columnWidth.keySet();
                    for(Integer c : k){
                        sheet.setColumnWidth(c,columnWidth.get(c)*256);
                    }
                }

            }


            if(workbook !=null){
                try
                {
                    Format format = new SimpleDateFormat("yyyyMMdd");
                    String fileName = "Excel-" +format.format(new Date()) + title.toString();
                    String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
                    // String headStr = "attachment; filename=\"" + fileName + "\"";
//                    response.setCharacterEncoding("utf-8");
//                    response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("gbk"), "iso8859-1")+".xls");
                    response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
                    response.setContentType("application/x-msdownload");
                    // response.setHeader("Content-Disposition", headStr);
                    OutputStream out = response.getOutputStream();
                    workbook.write(out);
                }
                catch (IOException e)
                {

                }
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    /*
     * 列头单元格样式
     */
    public static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)11);
        //字体加粗
        font.setBold(true);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;

    }
    //设置副标题样式
    public HSSFCellStyle getColumnTopStyles(HSSFWorkbook workbook){
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)11);
        //字体加粗
        font.setBold(true);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为靠左;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    /*
     * 列数据信息单元格样式
     */
    public static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        style.setWrapText(true);//设置自动换行
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为靠左;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFDataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("@"));
        return style;

    }

    public CellStyle getLinkStyle(Workbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
        // 字体加粗
        // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("Courier New");
        font.setColor(HSSFColor.BLUE.index);
        // 设置样式;
        CellStyle style = workbook.createCellStyle();

        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);

        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(true);
        //设置水平对齐的样式为靠左;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;

    }
}