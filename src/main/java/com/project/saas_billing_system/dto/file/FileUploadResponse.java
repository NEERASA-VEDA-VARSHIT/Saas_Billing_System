package com.project.saas_billing_system.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "File upload result")
public class FileUploadResponse {

    @Schema(description = "Original file name")
    private String fileName;

    @Schema(description = "Stored file name (use for download)")
    private String storedName;

    @Schema(description = "File size in bytes")
    private long size;

    @Schema(description = "Content type")
    private String contentType;

    @Schema(description = "Download path: GET /api/files/download/{storedName}")
    private String downloadUrl;
}
