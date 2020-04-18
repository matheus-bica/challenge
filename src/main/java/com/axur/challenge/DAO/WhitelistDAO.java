package com.axur.challenge.DAO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
public class WhitelistDAO implements DAO<Whitelist> {
	
	@PersistenceContext
	private EntityManager em;
	//EntityManagerFactory emf;
	//EntityManager em = emf.createEntityManager();
	
	@Override
	public Collection<Whitelist> getAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Whitelist> criteria = cb.createQuery(Whitelist.class);
		Collection<Whitelist> resultList = em.createQuery(criteria).getResultList();
		return resultList;
	}
		
	@Override
	public List<Whitelist> getList(String[] params) {
		TypedQuery<Whitelist> query = em.createQuery("SELECT c FROM Whitelist c WHERE (c.client = ?1 OR c.client = 'global')", Whitelist.class);
		query.setParameter(1, params[0]);

		try {
			return query.getResultList();
		} catch (IllegalStateException | PersistenceException e) {
			return null;
		}
	}
	
	@Override
	public Whitelist getSpecific(Whitelist t) {
		TypedQuery<Whitelist> query = em.createQuery("SELECT c FROM Whitelist c WHERE (c.client = ?1 OR c.client = 'global') and regex = ?2", Whitelist.class);
		query.setParameter(1, t.getClient());
		query.setParameter(2, t.getRegex());
		query.setMaxResults(1);
		
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

	
	@Override
	@Transactional
	public boolean save(Whitelist t) {
		try {
			this.em.persist(t);
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	
	@Override
	public Optional<Whitelist> get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void update(Whitelist t, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public boolean delete(Whitelist t) {
		return false;
		
	}
}

