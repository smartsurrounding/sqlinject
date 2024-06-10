package hksarg.sgil.dao.pubsubmission;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hksarg.sgil.dao.ConditionResult;
import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.pubsubmission.PubSubmissionEntity;
import hksarg.sgil.form.PubSubmissionSearchForm;
import hksarg.sgil.util.StringUtil;

@Stateless
@Local(PubSubmissionQueryLocal.class)
public class PubSubmissionQuery extends GenericDao<PubSubmissionEntity> implements PubSubmissionQueryLocal {

	private ConditionResult getCondition(PubSubmissionSearchForm form) {
		ConditionResult conditions = new ConditionResult();

		conditions.add("o.deletedBy IS NULL");

		if (form != null) {
			
			if(form.getIds()!=null && !form.getIds().isEmpty()) {
				conditions.add("o.id IN :ids","ids",form.getIds());
			}
			
			if (!StringUtil.isNullOrEmpty(form.getKeyword())) {
				conditions.add("(o.nameEn LIKE :keyword OR o.nameTc LIKE :keyword OR o.nameSc LIKE :keyword)", "keyword", "%" + form.getKeyword() + "%");
			}
		
		}

		return conditions;
	}

	@Override
	public Long count(PubSubmissionSearchForm form) {
		String sql = "SELECT COUNT(o) FROM PubSubmissionEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<PubSubmissionEntity> search(PubSubmissionSearchForm form, Integer offset, Integer maxResult,
			List<SortMeta> sortList) {
		String sql = "SELECT o FROM PubSubmissionEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		// Sorting
		List<String> orderBys = new ArrayList<String>();
		if (sortList != null && !sortList.isEmpty()) {
			for (SortMeta sort : sortList) {

				String orderBy = null;
				switch (sort.getSortField()) {
					case "name":
						orderBy = "o.name";
						break;
					case "companyName":
						orderBy = "o.companyName";
						break;
					case "lastUpdate":
						orderBy = "o.lastUpdate";
						break;
					case "completedDate":
						orderBy = "o.completedDate";
						break;
					case "brief":
						orderBy = "o.brief";
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
			sql += " ORDER BY o.lastUpdate DESC";
		}

		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}

	@Override
	public List<Integer> getIdsByCode(String code) {
		if(!StringUtil.isNullOrEmpty(code)){
			String sql = "SELECT DISTINCT(o.id) FROM PubSubmissionEntity o where o.deletedBy IS NULL ";
			sql+="and o.code=:code ";
			
			Query q = em.createQuery(sql);
			q.setParameter("code", code);
			
			return q.getResultList();
			
		}

		return null;
	}

	

}
