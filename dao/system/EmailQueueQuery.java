package hksarg.sgil.dao.system;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hksarg.sgil.dao.ConditionResult;
import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.system.EmailQueueEntity;
import hksarg.sgil.entity.system.EmailTemplateEntity;
import hksarg.sgil.form.EmailQueueSearchForm;
import hksarg.sgil.form.EmailTemplateSearchForm;

@Stateless
@Local(EmailQueueQueryLocal.class)
public class EmailQueueQuery extends GenericDao<EmailQueueEntity> implements EmailQueueQueryLocal {

	@Override
	public List<Integer> getDequeueEmailIds(Integer failCount) {
		String sql = "SELECT o.id FROM EmailQueueEntity o WHERE deletedBy IS NULL and ( o.sentDate IS NULL OR o.sentDate ='0000-00-00 00:00:00') ";
		if(failCount !=null && failCount >0) {
			sql += " and (o.failCount is null or o.failCount<=:failCount)  ";
		}
		sql += "  ORDER BY o.createDate ASC";
		Query q = em.createQuery(sql);
		if(failCount !=null && failCount >0) { 
	        q.setParameter("failCount", failCount);
		}
		return q.getResultList();
	}

	private ConditionResult getCondition(EmailQueueSearchForm form) {
		ConditionResult conditions = new ConditionResult();

		conditions.add("o.deletedBy IS NULL");

		if (form != null) {
		}

		return conditions;
	}

	@Override
	public Long count(EmailQueueSearchForm form) {

		String sql = "SELECT COUNT(o) FROM EmailQueueEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<EmailQueueEntity> search(EmailQueueSearchForm form, Integer offset, Integer maxResult,
			List<SortMeta> sortList) {

		String sql = "SELECT o FROM EmailQueueEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		// Sorting
		List<String> orderBys = new ArrayList<String>();
		if (sortList != null && !sortList.isEmpty()) {
			for (SortMeta sort : sortList) {

				String orderBy = null;
				switch (sort.getSortField()) {
					case "sentDate":
						orderBy = "o.sentDate";
						break;
					case "lastUpdate":
						orderBy = "o.lastUpdate";
						break;
					case "to":
						orderBy = "o.to";
						break;
					case "subject":
						orderBy = "o.subject";
						break;
					default:
						continue;
				}
				if (orderBy != null) {
					orderBys.add(orderBy + " " + (sort.getSortOrder().equals(SortOrder.DESCENDING) ? "DESC" : "ASC"));
				}
			}
		}

		if (!orderBys.isEmpty()) {
			sql += " ORDER BY " + String.join(" AND ", orderBys);
		} else {
			sql += " ORDER BY o.lastUpdate DESC,o.sentDate DESC";
		}

		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}

}