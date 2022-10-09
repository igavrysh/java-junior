package com.gavrysh.demo.stream16.model;

public record UploadResult(
        int uploadedRecordsCount,
        int failedCount
) {
}
