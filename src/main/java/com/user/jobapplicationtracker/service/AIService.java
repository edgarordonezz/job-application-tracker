package com.user.jobapplicationtracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.user.jobapplicationtracker.models.JobApplication;

@Service
public class AIService {

    @Value("${gemini.api-key}")
    private String apiKey;

    private static final String API_URL = 
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    public String generateCoverLetter(JobApplication app) {
        return apiKey;
    }
}
