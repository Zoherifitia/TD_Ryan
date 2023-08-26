package com.example.prog4.controller;

import com.example.prog4.config.CompanyConf;
import com.example.prog4.controller.mapper.EmployeeMapper;
import com.example.prog4.model.Employee;
import com.example.prog4.service.EmployeeService;
import com.example.prog4.service.ExportPdfService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class DownloadPdfController {
    private ExportPdfService exportPdfService;
    private EmployeeService employeeService;
    private EmployeeMapper employeeMapper;
    @GetMapping("/download-view/{eId}")
    public void downloadReceipt(HttpServletResponse response , @PathVariable String eId) throws IOException {
        Employee toShow = employeeMapper.toView(employeeService.getOne(eId));
        Map<String, Object> data = new HashMap<>();
        data.put("employee", toShow);
        data.put("company",new CompanyConf());
        ByteArrayInputStream exportedData = exportPdfService.exportReceivePdf("pdf", data);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=view.pdf");
        IOUtils.copy(exportedData, response.getOutputStream());
    }
}
