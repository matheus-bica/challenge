package com.axur.challenge.DAO;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.axur.challenge.model.Whitelist;

@Repository
public class WhitelistDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public Collection<Whitelist> getAllWhitelist() {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();
		CriteriaQuery<Whitelist> criteria = cb.createQuery(Whitelist.class);
		Collection<Whitelist> resultList = em.createQuery(criteria).getResultList();
		return resultList;
	}
	
	public Whitelist getSpecificWhitelist(String client, String regex) {
		TypedQuery<Whitelist> query = this.em.createQuery("SELECT c FROM Whitelist c WHERE (c.client = ?1 OR c.client = 'global') and regex = ?2", Whitelist.class);
		query.setParameter(1, client);
		query.setParameter(2, regex);
		query.setMaxResults(1);
		
		System.out.println("Trying get");
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Null1");
			return null;
		} catch (ListenerExecutionFailedException e) {
			System.out.println("Error");
			System.err.println(e);
		}
		System.out.println("Null2");
		return null;
	}
	
	public List<Whitelist> getWhitelist(String client) {
		TypedQuery<Whitelist> query = this.em.createQuery("SELECT c FROM Whitelist c WHERE (c.client = ?1 OR c.client = 'global')", Whitelist.class);
		query.setParameter(1, client);

		try {
			return query.getResultList();
		} catch (IllegalStateException | PersistenceException e) {
			return null;
		}		
	}

	@Transactional
	public boolean insertWhitelist(Whitelist whitelist) {
		try {
			this.em.persist(whitelist);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
