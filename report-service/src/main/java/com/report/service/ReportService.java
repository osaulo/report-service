package com.report.service;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import com.report.dto.City;
import com.report.dto.Person;

import br.com.mv.report_bean.dto.DrugPrescriptionReportDTO;
import br.com.mv.report_bean.dto.PrescriptionReportDTO;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class ReportService {
	
	private final String REPORTDIRNAME = "/home/saulo/MV/workspace/clinic-microservice/src/main/resources/jrxml";
	
	public final byte[] generateReport (final String reportName, final Map<String, Object> reportParams) throws Exception {
		byte[] result = null;
		JasperReport report = this.getReport(reportName);
        if (report != null) {
        	
        	Person person = new Person();
        	person.setFullName("davi rabujinho");
        	person.setCities(this.generateListCities());
        	
        	PrescriptionReportDTO prescriptionReportDTO = new PrescriptionReportDTO();
        	
        	Collection<Person> coll = new ArrayList<Person>();
        	coll.add(person);
        	
//        	Collection<PrescriptionReportDTO> collPrescription = new ArrayList<PrescriptionReportDTO>();
//        	collPrescription.add(prescriptionReportDTO);
        	
//        	DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
//        	JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.font.name", "DejaVu Sans");
//        	JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.embedded", "true");
//        	JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.font.name", "DejaVu Sans");
        	
        	JasperPrint fillReport = JasperFillManager.fillReport(report, 
        			reportParams, 
        			new JRBeanCollectionDataSource(coll, false));
        	
        	JRPdfExporter exporter = new JRPdfExporter();

        	result = JasperExportManager.exportReportToPdf(fillReport);
        }
        
        return result;
    }

	private List<DrugPrescriptionReportDTO> generateListDrugs() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<City> generateListCities() {
		ArrayList<City> arrayList = new ArrayList<City>();
		
		arrayList.add(new City("Rio de Janeiro", "RJ"));
		arrayList.add(new City("Sao Paulo", "SP"));
		arrayList.add(new City("Brasilia", "DF"));
		arrayList.add(new City("Salvador", "BA"));
		arrayList.add(new City("Recife", "PE"));
		
		return arrayList;
	}

	private JasperReport getReport(String reportName) throws Exception {
		InputStream inputStream = this.getClass().getResourceAsStream(reportName);
//		InputStream inputStream = new FileInputStream(new File("/home/saulo/MV/workspace/clinic-microservice/src/main/resources/jrxml/" + reportName + ".jrxml"));
    	JasperDesign jd =JRXmlLoader.load(inputStream);
        
    	// Coloccar como parametro
    	jd.setTopMargin(20);
    	jd.setLeftMargin(20);
    	jd.setRightMargin(20);
    	jd.setBottomMargin(20);
    	
    	return JasperCompileManager.compileReport(jd);
	}
}
