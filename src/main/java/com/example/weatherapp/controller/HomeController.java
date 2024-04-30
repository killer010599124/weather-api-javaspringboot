package com.example.weatherapp.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

@Controller
public class HomeController {
	
	
	@GetMapping("/")
	public String home() {
		return "index"; // Return the logical name of the Thymeleaf template
	}
	
	@PostMapping("/search")
	public String search(@ModelAttribute SearchRequest searchRequest, Model model) {
		String location = searchRequest.getLocation();

		// Replace with your actual weather API URL
		String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + location
				+ "?unitGroup=us&key=3C8TRCWYPKSPU83H6U8CJ5CUR&contentType=json";

		try {
			HttpResponse<JsonNode> response = Unirest.get(url).asJson();

			JsonNode jsonData = response.getBody();

			if (jsonData != null) {
			    model.addAttribute("data", jsonData);
			} else {
			    model.addAttribute("error", "Failed to retrieve weather data (empty response)");
			}

		} catch (Exception e) {
			model.addAttribute("error", "Failed to retrieve weather data"); // Set a more specific error message
		}

		return "index"; 
	}

}

class SearchRequest {
	private String location;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
