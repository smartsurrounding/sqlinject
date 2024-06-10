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
import hksarg.sgil.entity.system.EmailTemplateEntity;
import hksarg.sgil.form.EmailTemplateSearchForm;

@Stateless
@Local(EmailTemplateQueryLocal.class)
public class EmailTemplateQuery extends GenericDao<EmailTemplateEntity> implements EmailTemplateQueryLocal {

	private ConditionResult getCondition(EmailTemplateSearchForm form) {
		ConditionResult conditions = new ConditionResult();

		conditions.add("o.deletedBy IS NULL");

		if (form != null) {
		}

		return conditions;
	}

	@Override
	public Long count(EmailTemplateSearchForm form) {

		String sql = "SELECT COUNT(o) FROM EmailTemplateEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<EmailTemplateEntity> search(EmailTemplateSearchForm form, Integer offset, Integer maxResult,
			List<SortMeta> sortList) {

		String sql = "SELECT o FROM EmailTemplateEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		// Sorting
		List<String> orderBys = new ArrayList<String>();
		if (sortList != null && !sortList.isEmpty()) {
			for (SortMeta sort : sortList) {

				String orderBy = null;
				switch (sort.getSortField()) {
					case "code":
						orderBy = "o.code";
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
			sql += " ORDER BY o.code ASC";
		}

		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}

	@Override
	public EmailTemplateEntity getByCode(String code) {
		String sql = "SELECT o FROM EmailTemplateEntity o WHERE o.code=:code";
		Query q = em.createQuery(sql);
		q.setParameter("code", code);
		return (EmailTemplateEntity) q.getSingleResult();
	}
}
