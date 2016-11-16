package be.vdab.services;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.EntityManager;

import be.vdab.entities.Docent;
import be.vdab.filters.JPAFilter;
import be.vdab.repositories.DocentRepository;

// enkele imports ...
public class DocentService {
	private final DocentRepository docentRepository = new DocentRepository();

	public Optional<Docent> read(long id) {
		EntityManager entityManager = JPAFilter.getEntityManager();
		try {
			return docentRepository.read(id);
		} finally {
			entityManager.close();
		}
	}

	public void create(Docent docent) {
		EntityManager entityManager = JPAFilter.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			docentRepository.create(docent, entityManager);
			entityManager.getTransaction().commit();
		} catch (RuntimeException ex) {
			entityManager.getTransaction().rollback();
			throw ex;
		} finally {
			entityManager.close();
		}
	}

	public void delete(long id) {
		EntityManager entityManager = JPAFilter.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			docentRepository.delete(id, entityManager);
			entityManager.getTransaction().commit();
		} catch (RuntimeException ex) {
			entityManager.getTransaction().rollback();
			throw ex;
		} finally {
			entityManager.close();
		}
	}

	public void opslag(long id, BigDecimal percentage) {
		EntityManager entityManager = JPAFilter.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			docentRepository.read(id).ifPresent(docent -> docent.opslag(percentage));
			entityManager.getTransaction().commit();
		} catch (RuntimeException ex) {
			entityManager.getTransaction().rollback();
			throw ex;
		} finally {
			entityManager.close();
		}
	}

}