package com.chengdu.jiq.service.fileupload.controller;

import com.chengdu.jiq.service.fileupload.service.StorageService;
import com.chengdu.jiq.common.annotation.ControllerAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jiyiqin on 2017/9/16.
 */
@ControllerAspect
@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model, HttpServletRequest request) throws IOException {
        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        storageService.init(filePath);
        Stream<Path> all = storageService.loadAll(filePath);
        if (all != null) {
            //MvcUriComponentsBuilder转换Path为http://127.0.0.1:8010/springboot-service/fileupload/files/test1.txt格式
            model.addAttribute("files", all.map(
                    path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                            "serveFile", request, path.getFileName().toString()).build().toString())
                    .collect(Collectors.toList()));
        }
        return "upload/uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(HttpServletRequest request, @PathVariable String filename) throws FileNotFoundException {
        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        Resource file = storageService.loadAsResource(filePath, filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        storageService.store(file, filePath);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/fileupload/";
    }
}
