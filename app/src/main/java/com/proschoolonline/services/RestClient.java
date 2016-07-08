package com.proschoolonline.services;

import com.proschoolonline.mob.BuildConfig;
import com.proschoolonline.model.CategoriesData;
import com.proschoolonline.model.NewsData;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;


/**
 * @purpose used for making api call for used in HttpRequestClient
 * @author ankit.agrawal
 *
 */
@Rest(rootUrl = BuildConfig.apiUrl, converters={StringHttpMessageConverter.class, MappingJacksonHttpMessageConverter.class, FormHttpMessageConverter.class})
public interface RestClient {

	//@Get("/blog/wp-json/wp/v2/posts/")
	@Get("/blog/wp-json/wp/v2/posts?filter[posts_per_page]=-1")
	List<NewsData> getNewsData();

	//@Get("/blog/wp-json/wp/v2/categories")
	@Get("/blog/wp-json/wp/v2/categories?per_page=100")
	List<CategoriesData> getCategoriesData();

	@Get("/blog/wp-json/wp/v2/posts?filter[cat]={catId}")
	List<NewsData> getNewsDataFilter(String catId);

	void setHeader(String name, String value);

	String getHeader(String name);

	RestTemplate getRestTemplate();
}
