package com.example.capstone.service;

public class CodeInfo {
    private final String code;
    private final long timestamp;

    public CodeInfo(String code, long timestamp) {
        this.code = code;
        this.timestamp = timestamp;
    }

    public String getCode() { return code; }

    public long getTimestamp() { return timestamp; }
}
