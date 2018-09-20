package ru.eosan.camunda;

import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class ProcessController {

    private final RepositoryService repositoryService;

    @Autowired
    public ProcessController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

//    @GetMapping("/process-definition/{id}")
//    public byte[] getProcessDiagram(@PathVariable String id) {
//        InputStream processDiagram = repositoryService.getProcessDiagram(id);
//        if (processDiagram == null) {
//            return "no diagram bytes is null".getBytes();
//        }
//        byte[] bytes = new byte[0];
//        try {
//            bytes = IOUtils.toByteArray(processDiagram);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return e.getMessage().getBytes();
//        }
//        return bytes;
//    }
}
