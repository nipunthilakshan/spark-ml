package com.lohika.morning.ml.api.controller;

import com.lohika.morning.ml.api.visual.Lyric;
import com.lohika.morning.ml.api.visual.PredictionResponse;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
public class UIController {

    @GetMapping("/home")
    public String lyricsForm(Model model) {

        model.addAttribute("lyric", new Lyric());
        return "home";
    }

    @PostMapping("/home")
    public String lyricsSubmit(@ModelAttribute Lyric lyric, Model model) {

        String uri = "http://localhost:9090/lyrics/predict";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(lyric.getContent());

        PredictionResponse predictionResponse = restTemplate.postForObject(uri, request, PredictionResponse.class);
        System.out.println("PredictionResponse: Genre: " + predictionResponse.getGenre());
        System.out.println("PredictionResponse: MetalProbability: " + predictionResponse.getMetalProbability());
        System.out.println("PredictionResponse: PopProbability: " + predictionResponse.getPopProbability());

        model.addAttribute("Genre", predictionResponse.getGenre());
        model.addAttribute("metalProbability", predictionResponse.getMetalProbability());
        model.addAttribute("popProbability", predictionResponse.getPopProbability());
        model.addAttribute("bluesProbability", predictionResponse.getBluesProbability());
        model.addAttribute("countryProbability", predictionResponse.getCountryProbability());
        model.addAttribute("hiphopProbability", predictionResponse.getHiphopProbability());
        model.addAttribute("jazzProbability", predictionResponse.getJazzProbability());
        model.addAttribute("reggaeProbability", predictionResponse.getReggaeProbability());
        model.addAttribute("rockProbability", predictionResponse.getRockProbability());

        List<String> lyricTypes = Arrays.asList("Metal", "Pop", "Blues", "Country", "Hiphop", "Jazz", "Reggae", "Rock");
        List<Double> lyricValues = Arrays.asList(predictionResponse.getMetalProbability(),
                predictionResponse.getPopProbability(),
                predictionResponse.getBluesProbability(),
                predictionResponse.getCountryProbability(),
                predictionResponse.getHiphopProbability(),
                predictionResponse.getJazzProbability(),
                predictionResponse.getReggaeProbability(),
                predictionResponse.getRockProbability()
        );
        model.addAttribute("lyricTypes", lyricTypes);
        model.addAttribute("lyricValues", lyricValues);

        return "pieChart";
    }

}
