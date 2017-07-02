package org.jabref.gui;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.collections4.*;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Siedg on 29/06/2017.
 */
public class GetImpactFactor {
    public ArrayList<String> GetImpactFactor (String entryName){
        ArrayList<String> iFactor = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(new File("src\\main\\resources\\databases\\Thomson Reuters - JCR 2015.xls"));
            Workbook workbook = new HSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);


            // Create Map from ColumnName -> Index
            Map<String, Integer> colMap = new HashMap<String, Integer>();
            Row r = sheet.getRow(0);
            short minColIndex = r.getFirstCellNum();
            short maxColIndex = r.getLastCellNum();
            for (short colIndex = minColIndex ; colIndex < maxColIndex ; colIndex++) {
                Cell c = r.getCell(colIndex);
                colMap.put(c.getStringCellValue(), c.getColumnIndex());
            }

            boolean found = false;
            // Iterate between rows
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                found = false;
                // Iterate between cells
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    // Compare the value of the first cell with the entryName
                    if (cell.getColumnIndex() == colMap.get("Full Journal Title") && cell.getStringCellValue().toLowerCase().equals(entryName.toLowerCase())) {
                        found = true;
                    }

                    // Journal Impact Factor
                    if (found && cell.getColumnIndex() == 5) {
                        iFactor.add(cell.getStringCellValue());
                    }

                    // Impact Factor without Journal Self Cites
                    if (found && cell.getColumnIndex() == 6) {
                        iFactor.add(cell.getStringCellValue());
                    }

                    // 5-Year Impact Factor
                    if (found && cell.getColumnIndex() == 7) {
                        iFactor.add(cell.getStringCellValue());
                    }
                }
            }

            if (iFactor.isEmpty()) {
                JDialog errorMessage = new JDialog();
                new Thread() {
                    public void run() {
                        errorMessage.setTitle("Error");
                        errorMessage.setModal(true);
                        errorMessage.setContentPane(new JOptionPane("Impact factor not found for current entry.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null));
                        errorMessage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        errorMessage.pack();
                        errorMessage.setLocationRelativeTo(null);
                        errorMessage.setVisible(true);
                        errorMessage.setResizable(false);
                    }
                }.start();
            } else {
                return iFactor;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
