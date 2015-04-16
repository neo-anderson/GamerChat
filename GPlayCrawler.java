package com.ghq.crawler.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.firebase.client.Firebase;
import com.firebase.client.Logger;

public class GPlayCrawler {
	static List<Map> games = new ArrayList<Map>();
	static List<String> descriptions = new ArrayList<String>();
	
	public static void crawl(String link){
		Document doc = null;
		try {
			doc = Jsoup.connect(link).timeout(10*1000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements cards = doc.getElementsByClass("card-content");
		
		for (Element card : cards){
			Element details = card.select("div.details").get(0);
			String url = "https://play.google.com"+details.select("a.title").attr("href");
			String title = details.select("a.title").attr("title");
			String rank = details.select("h2").text().split(". ")[0];
			String dev = details.select("a.subtitle").text();
			String devUrl = "https://play.google.com"+details.select("a.subtitle").attr("href");
			String price = details.select("span.display-price").text();
			String description = details.select("div.description").text();
			Element rating = card.select("div.reason-set").get(0);
			String ratingString = rating.select("div.tiny-star").attr("aria-label");
			String ratingValue = rating.select("div.current-rating").attr("style").split(": ")[1].replace(";", "");
			Element image = card.select("img.cover-image").get(0);
			String imageLarge = image.attr("data-cover-large");
			String imageSmall = image.attr("data-cover-small");
			System.out.println("URL : "+url);
			System.out.println("Name : "+title);
			System.out.println("Rank : "+rank);
			System.out.println("Developer : "+dev+", "+devUrl);
			System.out.println("Price : "+price);
			System.out.println(description);
			System.out.println("Rating : "+ratingValue+ratingString);
			System.out.println("Image : "+imageSmall);
			System.out.println();
			descriptions.add(description);
			games.add(toMaps(title,url,imageLarge,imageSmall,rank,dev,devUrl,price,ratingString,ratingValue));
		}
	}
	
	private static Map toMaps(String title, String url, String imageLarge,
			String imageSmall, String rank, String dev, String devUrl,
			String price, String ratingString,
			String ratingValue) {
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("url", url);
		map.put("imageLarge", imageLarge);
		map.put("imageSmall", imageSmall);
		map.put("rank", rank);
		map.put("dev", dev);
		map.put("devUrl", devUrl);
		map.put("price", price);
		map.put("ratingString", ratingString);
		map.put("ratingValue", ratingValue);
		return map;
	}
	
	public static void main(String[] args) {
		crawl("https://play.google.com/store/apps/category/GAME/collection/topselling_free");
		crawl("https://play.google.com/store/apps/category/GAME/collection/topselling_paid");
		Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);
		Firebase fb = new Firebase("https://sweltering-inferno-7806.firebaseio.com/");
		System.out.println(games);
		// Writing denormalized data
		fb.child("games").setValue(games);
		fb.child("descriptions").setValue(descriptions);
		// Keep the thread alive until the operation completes.
		while(true) {
		     try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} //Sleep 10 seconds
		}
	}
}
