/*
MIT License 
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/*
 * Written By Dennis Ho 2018 Feb.
 */

package jaspgen;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class startgen {

	private String jrxmlFileName = "log.jrxml";
	private String jasperFileName = "log.jasper";
	private String pdfFileName = "log_report.pdf";
	private String xlsFileName = "log_report.xls";
	private String dbUrl = "";
	private String dbDriver = "com.mysql.jdbc.Driver";
	private String dbUname = "";
	private String dbPwd = "";	
	
	public startgen() {
	    Properties prop = new Properties ();
	    try {
	    	InputStream ris = getClass().getClassLoader().getResourceAsStream("files/config.properties");
	    	if (ris == null ) {
	    		System.out.println ("config not found");
	    		System.exit(-1);
	    	}
			prop.load(ris);
			dbUrl = prop.getProperty("dburl");
			dbUname = prop.getProperty("dbuser");
			dbPwd = prop.getProperty("dbpwd");
			System.out.println ("url=" + dbUrl);
			System.out.println ("user=" + dbUname);
		} catch (IOException e) {
			e.printStackTrace();
		}
		removeold ();
		genoutput2 ();
		
	}
	
	private void genoutput () {
		System.out.println("genstart");
		HashMap hm = null;
		try {
			System.out.println("Start ....");
			JasperCompileManager.compileReportToFile(jrxmlFileName, jasperFileName);
			Class.forName(dbDriver);
			Connection conn = DriverManager.getConnection(dbUrl, dbUname, dbPwd);
			Map params = new HashMap();
			hm = new HashMap();
			JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperFileName, hm, conn);
			JasperExportManager.exportReportToPdfFile(jprint, pdfFileName);
			System.out.println("Done exporting reports to pdf");
			JasperPrint jprint2 = (JasperPrint) JasperFillManager.fillReport(jasperFileName, hm, conn);
			JRXlsExporter xlsExporter = new JRXlsExporter();
			xlsExporter.setExporterInput(new SimpleExporterInput(jprint2));
			xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsFileName));
			SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
			SimpleXlsExporterConfiguration xlsExporterConfiguration = new SimpleXlsExporterConfiguration();
			xlsReportConfiguration.setOnePagePerSheet(false);
			xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
			xlsReportConfiguration.setDetectCellType(true);
			xlsReportConfiguration.setAutoFitPageHeight(true);
			xlsReportConfiguration.setRemoveEmptySpaceBetweenColumns(true);
			xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
			String worksheetnames[] = { "Test log sheet" };
			xlsReportConfiguration.setSheetNames(worksheetnames);
			xlsReportConfiguration.setWhitePageBackground(false);
			xlsExporter.setConfiguration(xlsReportConfiguration);
			xlsExporter.setConfiguration(xlsExporterConfiguration);
			xlsExporter.exportReport();
			System.out.println("xls report complete");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void genoutput2 () {
		System.out.println("genstart");
		HashMap hm = null;
		try {
			System.out.println("Start ....");
			Class.forName(dbDriver);
			Connection conn = DriverManager.getConnection(dbUrl, dbUname, dbPwd);
			Map params = new HashMap();
			hm = new HashMap();
			InputStream inputStream = startgen.class.getResourceAsStream("/files/tlog.jasper");
			JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(inputStream, hm, conn);			
			JasperExportManager.exportReportToPdfFile(jprint, pdfFileName);
			System.out.println("Done exporting reports to pdf");
			InputStream inputStream2 = startgen.class.getResourceAsStream("/files/tlog.jasper");			
			JasperPrint jprint2 = (JasperPrint) JasperFillManager.fillReport(inputStream2, hm, conn);
			JRXlsExporter xlsExporter = new JRXlsExporter();
			xlsExporter.setExporterInput(new SimpleExporterInput(jprint2));
			xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsFileName));
			SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
			SimpleXlsExporterConfiguration xlsExporterConfiguration = new SimpleXlsExporterConfiguration();
			xlsReportConfiguration.setOnePagePerSheet(false);
			xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
			xlsReportConfiguration.setDetectCellType(true);
			xlsReportConfiguration.setAutoFitPageHeight(true);
			xlsReportConfiguration.setRemoveEmptySpaceBetweenColumns(true);
			xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
			String worksheetnames[] = { "Test log sheet" };
			xlsReportConfiguration.setSheetNames(worksheetnames);
			xlsReportConfiguration.setWhitePageBackground(false);
			xlsExporter.setConfiguration(xlsReportConfiguration);
			xlsExporter.setConfiguration(xlsExporterConfiguration);
			xlsExporter.exportReport();
			System.out.println("xls report complete");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	private void removeold () {
			File f = new File(jasperFileName);
			if (f.isFile()) {
				f.delete();
				System.out.println("delete");
			}
			f = new File(pdfFileName);
			if (f.isFile()) {
				f.delete();
				System.out.println("delete");
			}
			f = new File(xlsFileName);
			if (f.isFile()) {
				f.delete();
				System.out.println("delete");
			}		
	}
	
	public static void main(String[] args) {
		new startgen();
	}


}
