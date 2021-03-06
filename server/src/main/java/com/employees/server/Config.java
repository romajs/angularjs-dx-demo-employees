package com.employees.server;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employees.lib.dao.GenericDAO;
import com.employees.lib.dao.JPAGenericDAO;

@ApplicationScoped
public class Config {

	private EntityManagerFactory entityManagerFactory;

	@Produces
	@RequestScoped
	public EntityManager createEntityManager(EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.createEntityManager();
	}

	@Produces
	@ApplicationScoped
	public EntityManagerFactory createEntityManagerFactory() {
		return entityManagerFactory;
	}
	
	@Produces
	@ApplicationScoped
	public GenericDAO createGenericDAO(EntityManager entityManager) {
		return new JPAGenericDAO(entityManager);
	}

	@Produces
	public Logger createLogger(InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}

	public void disposeEntityManager(@Disposes EntityManager manager) {
		manager.clear();
		manager.close();
	}

	@PostConstruct
	public void start() {
		entityManagerFactory = Persistence.createEntityManagerFactory("default");
	}

}
