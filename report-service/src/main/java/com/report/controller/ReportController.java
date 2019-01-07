package com.report.controller;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.report.service.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	private ReportService reportService;

	@RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> gerarPdf() throws Exception {
		// parametros para o report
		HashMap params = this.getMap();
        
        final byte[] report = reportService.generateReport("helloworld.jrxml", params);

        ByteArrayInputStream bis = new ByteArrayInputStream(report);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

	private HashMap getMap() {
		HashMap result = new HashMap<>();
		
		SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyy");
		
		result.put("automatic_number", true);
		result.put("footer", true);
		result.put("header", true);
		result.put("logo_top_left", true);
		result.put("logo_top_right", false);
		result.put("signature", true);
		
		return result;
	}
}