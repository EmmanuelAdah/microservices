package com.server.aiservice.controllers;

import com.server.aiservice.models.Recommendation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RecommendationController {

    public Recommendation getRecommendation(@PathVariable String id){

    }
}
