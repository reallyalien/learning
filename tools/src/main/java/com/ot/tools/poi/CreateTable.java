package com.ot.tools.poi;


import java.io.FileOutputStream;
import java.math.BigInteger;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

public class CreateTable {

    public void createSimpleTable(String savePath) throws Exception {
        XWPFDocument xdoc = new XWPFDocument();
        XWPFParagraph xp = xdoc.createParagraph();
        xp.setSpacingBefore(1);
        XWPFRun r1 = xp.createRun();
        r1.setText("湖北省人民检察院");
        r1.addBreak(); // 换行
        r1.setText("证据清单");
        r1.setFontFamily("宋体");
        r1.setFontSize(16);
        r1.setTextPosition(10);
        r1.setBold(true);
        xp.setAlignment(ParagraphAlignment.CENTER);

        Integer col_total_count = 4; // 表格最多的列数
        Integer data_count = 10; // 需要创建的总条数

        XWPFTable xTable = xdoc.createTable(1, col_total_count);

        CTTbl ttbl = xTable.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl
                .getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr
                .addNewTblW();
        tblWidth.setW(new BigInteger("8600"));
        tblWidth.setType(STTblWidth.DXA);

        // 创建表头数据
        int i = 0;
        xTable.getRow(i).setHeight(500);
        setCellText(xdoc, xTable.getRow(i).getCell(0), "序号", "FFFFFF", getCellWidth(0));
        setCellText(xdoc, xTable.getRow(i).getCell(1), "类别", "FFFFFF", getCellWidth(1));
        setCellText(xdoc, xTable.getRow(i).getCell(2), "证据名称", "FFFFFF", getCellWidth(2));
        setCellText(xdoc, xTable.getRow(i).getCell(3), "备注", "FFFFFF", getCellWidth(3));

        // 创建表格内容
        i++;
        for (int i2 = i; i2 < data_count; i2++) {
            XWPFTableRow row = xTable.insertNewTableRow(i2);
            row.setHeight(450);
            for (int j = 0, j2 = 0; j < col_total_count; j++, j2++) {
                XWPFTableCell cell = row.createCell();
                CTTc cttc = cell.getCTTc();
                CTTcPr cellPr = cttc.addNewTcPr();
                cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
                cellPr.addNewTcW().setW(BigInteger.valueOf(getCellWidth(j2)));
                cell.setText("测试" + j);
            }
        }
        FileOutputStream fos = new FileOutputStream(savePath);
        xdoc.createStyles();
        xdoc.write(fos);
        fos.close();
    }

    /**
     * 设置表头内容
     *
     * @param xDocument
     * @param cell
     * @param text
     * @param bgcolor
     * @param width
     */
    private static void setCellText(XWPFDocument xDocument, XWPFTableCell cell,
                                    String text, String bgcolor, int width) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        cell.setColor(bgcolor);
        cell.setVerticalAlignment(XWPFVertAlign.CENTER);
        CTTcPr ctPr = cttc.addNewTcPr();
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);//设置垂直居中
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);//设置水平居中
        cell.setText(text);
    }

    /**
     * 设置列宽
     *
     * @param index
     * @return
     */
    private static int getCellWidth(int index) {
        int cwidth = 1000;
        if (index == 0) {
            cwidth = 2100;
        } else if (index == 1) {
            cwidth = 2100;
        } else if (index == 2) {
            cwidth = 2100;
        } else if (index == 3) {
            cwidth = 2100;
        }
        return cwidth;
    }

    public static void main(String[] args) throws Exception {
        CreateTable t = new CreateTable();
        t.createSimpleTable("D:/2.docx");
    }
}