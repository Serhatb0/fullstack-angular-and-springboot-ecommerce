package com.biricik.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.biricik.ecommerce.entity.Country;
import com.biricik.ecommerce.entity.Order;
import com.biricik.ecommerce.entity.Product;
import com.biricik.ecommerce.entity.ProductCategory;
import com.biricik.ecommerce.entity.State;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	
	@Value("${allowed.origins}")
	private String[] theAllowedOrigins;
	
	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager entityManager) {

		this.entityManager = entityManager;
	}



	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

		HttpMethod[] theUnsupportedActions = { HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE,
											   HttpMethod.PATCH	};

		// disable Http methods for Product: PUT,POST and DELETE
		disableHttpMethods(Product.class,config, theUnsupportedActions);
		
		// disable Http methods for ProductCategory: PUT,POST and DELETE
		disableHttpMethods(ProductCategory.class,config, theUnsupportedActions);
		
		// disable Http methods for Country: PUT,POST and DELETE
		disableHttpMethods(Country.class,config, theUnsupportedActions);
		
		// disable Http methods for State: PUT,POST and DELETE
		disableHttpMethods(State.class,config, theUnsupportedActions);
		
		// disable Http methods for Order: PUT,POST and DELETE
				disableHttpMethods(Order.class,config, theUnsupportedActions);
		
		// call an internal helper method
		exposeIds(config);
		
		// configure cors mapping
		cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
		
		RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
	}



	private void disableHttpMethods(Class theClass,RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
		config.getExposureConfiguration().forDomainType(theClass)
				.withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
	}



	private void exposeIds(RepositoryRestConfiguration config) {
		// expose entity ids
		
		// - get a list of all entity classes from the entity manager
		
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		
		// - create an array of the entity types
		List<Class> entityClasses = new ArrayList<>();
		
		// - get the entity types for the entities
		for (EntityType tempEntityType : entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}
		
		// - expose the entity ids for the array of entity/domain types
		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
		
		
		
	}
	
	

}
