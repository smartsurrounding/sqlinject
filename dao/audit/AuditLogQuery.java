package hksarg.sgil.dao.audit;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.service.ServiceRegistry;
import org.jboss.logging.Logger;

import hksarg.sgil.form.AuditSearchForm;

@Stateless
@Local(AuditLogQueryLocal.class)
public class AuditLogQuery implements AuditLogQueryLocal {
	@PersistenceContext
	protected EntityManager em;
	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public String[] getColumnNames(String className) {
		Session session = em.unwrap(Session.class);
		SessionFactory sessionFactory = session.getSessionFactory();
		ClassMetadata hibernateMetadata = sessionFactory.getClassMetadata(className);

		if (hibernateMetadata == null) {
			return null;
		}

		if (hibernateMetadata instanceof AbstractEntityPersister) {
			AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
			String tableName = persister.getTableName();
			String[] columnNames = Arrays.asList(persister.getPropertyNames()).stream()
					.map(x -> persister.getPropertyColumnNames(x)[0]).toArray(String[]::new);

			return columnNames;
		}
		return null;
	}

	@Override
	public String getTableName(String className) {
		Session session = em.unwrap(Session.class);
		SessionFactory sessionFactory = session.getSessionFactory();
		ClassMetadata hibernateMetadata = sessionFactory.getClassMetadata(className);

		if (hibernateMetadata == null) {
			return null;
		}

		if (hibernateMetadata instanceof AbstractEntityPersister) {
			AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
			String tableName = persister.getTableName();

			String[] columnNames = persister.getKeyColumnNames();

			return tableName;
		}
		return null;
	}

	@Override
	public <T> String getAuditEntityName(Class<T> clazz) {
		Session session = em.unwrap(Session.class);
		SessionImplementor sessionImpl;
		if (!(session instanceof SessionImplementor)) {
			sessionImpl = (SessionImplementor) session.getSessionFactory().getCurrentSession();
		} else {
			sessionImpl = (SessionImplementor) session;
		}
		final ServiceRegistry serviceRegistry = sessionImpl.getFactory().getServiceRegistry();
		final EnversService enversService = serviceRegistry.getService(EnversService.class);

		return enversService.getAuditEntitiesConfiguration().getAuditEntityName(clazz.getName());
	}

	@Override
	public <T> Map getWithMap(Class<T> clazz, int id, int revision,List<String> fieldNames) {
		
		String fieldName =String.join(",", fieldNames)+",rev ";

		String sql = "SELECT "+fieldName+" FROM " + getTableName(getAuditEntityName(clazz)) + " o WHERE o.id=:id AND o.REV=:rev";
		Query query = em.createNativeQuery(sql);
		query.setParameter("rev", revision);
		query.setParameter("id", id);
		query.setMaxResults(1);

		
		List results = query.getResultList();
		
		Map<String, Object> fieldValue = new LinkedHashMap<String, Object>();
		
		if (results != null && !results.isEmpty()){
			 Object[] objs = (Object[]) results.get(0);
			 for (int i = 0; fieldNames != null && i < fieldNames.size(); i++) {
				 fieldValue.put(fieldNames.get(i),objs[i]);
			}				
		}

		return fieldValue;
	}

	@Override
	public <T> Map getPreviousWithMap(Class<T> clazz, int id, int revision,List<String> fieldNames) {
		
		String fieldName =String.join(",", fieldNames)+",rev ";logger.info("fieldName :"+fieldName);
		String sql = "SELECT "+fieldName+" FROM " + getTableName(getAuditEntityName(clazz))+ " o WHERE o.id=:id AND o.REV<:rev ORDER BY o.REV DESC";
		Query query = em.createNativeQuery(sql);
		
		query.setParameter("rev", revision);
		query.setParameter("id", id);
		query.setMaxResults(1);
		/*
		org.hibernate.Query q = ((HibernateQuery) query).getHibernateQuery();
		q.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> list = q.list();

		if (list.size() == 0)
			return null;

		return list.get(0);
		*/
		
		List results = query.getResultList();
		
		Map<String, Object> fieldValue = new LinkedHashMap<String, Object>();
		
		if (results != null && !results.isEmpty()){
			 Object[] objs = (Object[]) results.get(0);
			 for (int i = 0; fieldNames != null && i < fieldNames.size(); i++) {
				 fieldValue.put(fieldNames.get(i),objs[i]);
			}				
		}

		return fieldValue;

	}

	@Override
	public <T> T get(Class<T> clazz, int revision) {
		AuditReader auditReader = AuditReaderFactory.get(em);
		T auditLog = auditReader.findRevision(clazz, revision);

		return auditLog;
	}

	@Override
	public List<Object[]> search(AuditSearchForm form, Integer firstResult, Integer maxResults) {
		AuditReader auditReader = AuditReaderFactory.get(em);
		AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(form.getTable().getClazz(), false, true);
		if (form.getType() != null) {
			query.add(AuditEntity.revisionType().eq(form.getType()));
		}
		if(form.getStartDate()!=null) {
			query.add(AuditEntity.property("lastUpdate").ge(form.getStartDate()));
		}
		if(form.getEndDate()!=null) {
			query.add(AuditEntity.property("lastUpdate").le(form.getEndDate()));
		}
		query.addOrder(AuditEntity.revisionNumber().desc());
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.getResultList();
	}

	@Override
	public Long count(AuditSearchForm form) {
		AuditReader auditReader = AuditReaderFactory.get(em);
		AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(form.getTable().getClazz(), false, true);
		query.addProjection(AuditEntity.id().count());
		if (form.getType() != null) {
			query.add(AuditEntity.revisionType().eq(form.getType()));
		}
		if(form.getStartDate()!=null) {
			query.add(AuditEntity.property("lastUpdate").ge(form.getStartDate()));
		}
		if(form.getEndDate()!=null) {
			query.add(AuditEntity.property("lastUpdate").le(form.getEndDate()));
		}

		return (Long) query.getSingleResult();
	}

	@Override
	public <T> List<Object> get(Class<T> clazz, Integer id, Integer revision) {
		AuditReader auditReader = AuditReaderFactory.get(em);
		AuditQuery query = auditReader.createQuery().forRevisionsOfEntity(clazz, false, true);
		query.add(AuditEntity.revisionNumber().le(revision));
		query.add(AuditEntity.id().eq(id));
		query.addOrder(AuditEntity.revisionNumber().desc());
		query.setMaxResults(2);

		return query.getResultList();
	}
	

}
