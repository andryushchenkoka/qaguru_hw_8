package parsing;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileParser {
    private static final ClassLoader classLoader = FileParser.class.getClassLoader();
    String zipArchiveName;

    public FileParser(String zipArchiveName) {
        this.zipArchiveName = zipArchiveName;
    }

    public String readPdfFromZip(boolean toPrintContent) {

        String resultContent = "";

        try (
                InputStream resource = classLoader.getResourceAsStream(this.zipArchiveName);
                ZipInputStream zipInputStream = new ZipInputStream(resource);
        ) {
            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String filename = zipEntry.getName();
                if (filename.contains("pdf")) {
                    PDF content = new PDF(zipInputStream);
                    resultContent = content.text;
                    if (toPrintContent) {
                        System.out.printf("Название файла: %s\n\n", filename);
                        System.out.printf("Содержание файла:\n%s\n\n", resultContent);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

        return resultContent;
    }

    public String readXlsxFromZip(int sheet, int row, int cell, boolean toPrintContent) {

        if (row < 0 || cell < 0 || sheet < 0) {
            throw new IllegalArgumentException("Minimum value is less than zero 0");
        }

        String resultContent = "";

        try (
                InputStream resource = classLoader.getResourceAsStream(this.zipArchiveName);
                ZipInputStream zipInputStream = new ZipInputStream(resource);
        ) {
            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String filename = zipEntry.getName();
                if (filename.contains("xls") || filename.contains("xlsx")) {
                    XLS content = new XLS(zipInputStream);
                    resultContent = content.excel.getSheetAt(0).getRow(row).getCell(cell).getStringCellValue();
                    if (toPrintContent) {
                        System.out.printf("Название файла: %s\n\n", filename);
                        System.out.printf("Содержание ячейки (%d,%d):\n%s\n\n", row, cell, resultContent);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

        return resultContent;
    }

    public String readCsvFromZip(int row, int item, boolean toPrintContent) {

        String resultContent = "";

        try (
                InputStream resource = classLoader.getResourceAsStream(this.zipArchiveName);
                ZipInputStream zipInputStream = new ZipInputStream(resource);
        ) {
            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String filename = zipEntry.getName();
                if (filename.contains("csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zipInputStream));
                    List<String[]> content = reader.readAll();
                    resultContent = content.get(row)[item];
                    if (toPrintContent) {
                        System.out.printf("Название файла: %s\n\n", filename);
                        System.out.printf("Содержание файла:\n%s\n\n", resultContent);
                    }
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

        return resultContent;
    }
}
