package com.project.saas_billing_system.controller.v1.file;

import com.project.saas_billing_system.dto.file.FileUploadResponse;
import com.project.saas_billing_system.service.file.FileStorageService;
import com.project.saas_billing_system.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "File upload", description = "Upload and download files (e.g. invoice attachments)")
public class FileUploadController {

    private final FileStorageService fileStorageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a file", description = "Upload a file. Returns stored name and download URL.")
    public ResponseEntity<ApiResponse<FileUploadResponse>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "organizationId", required = false)
            @Schema(description = "Optional organization ID to associate the file with") Long organizationId) {
        String storedName = fileStorageService.store(file);
        String downloadUrl = "/api/files/download/" + storedName;
        FileUploadResponse response = FileUploadResponse.builder()
                .fileName(file.getOriginalFilename() != null ? file.getOriginalFilename() : "file")
                .storedName(storedName)
                .size(file.getSize())
                .contentType(file.getContentType())
                .downloadUrl(downloadUrl)
                .build();
        return ResponseEntity.status(201).body(ApiResponse.success("File uploaded", response));
    }

    @GetMapping("/download/{storedName}")
    @Operation(summary = "Download a file", description = "Download a file by its stored name (from upload response).")
    public ResponseEntity<Resource> download(@PathVariable String storedName) {
        Resource resource = fileStorageService.loadAsResource(storedName);
        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
