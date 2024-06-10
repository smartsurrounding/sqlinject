package hksarg.sgil.dao.centralised;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hksarg.sgil.dao.ConditionResult;
import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.centralised.CentralBusinessEntity;
import hksarg.sgil.form.CentralSearchForm;
import hksarg.sgil.util.StringUtil;

@Stateless
@Local(CentralBusinessQueryLocal.class)
public class CentralBusinessQuery extends GenericDao<CentralBusinessEntity> implements CentralBusinessQueryLocal {

	private ConditionResult getCondition(CentralSearchForm form) {
		ConditionResult conditions = new ConditionResult();

		conditions.add("o.deletedBy IS NULL");

		if (form != null) {
			
			if(form.getIds()!=null && !form.getIds().isEmpty()) {
				conditions.add("o.id IN :ids","ids",form.getIds());
			}

			if(!StringUtil.isNullOrEmpty(form.getRefcode())) {
				conditions.add("o.refCode=:refCode");
				conditions.addValueMap("refCode", form.getRefcode());
			}
		}

		return conditions;
	}

	@Override
	public Long count(CentralSearchForm form) {
		String sql = "SELECT COUNT(DISTINCT o.id) FROM CentralBusinessEntity o ";
		sql +=" LEFT JOIN WebEntity w ON o.refCode=w.refCode  ";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<CentralBusinessEntity> search(CentralSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList) {
		String sql = "SELECT DISTINCT(o) FROM CentralBusinessEntity o"; 
		sql +=" LEFT JOIN WebEntity w ON o.refCode=w.refCode  ";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		// Sorting
		List<String> orderBys = new ArrayList<String>();
		if (sortList != null && !sortList.isEmpty()) {
			for (SortMeta sort : sortList) {

				String orderBy = null;
				switch (sort.getSortField()) {
					case "mission":
						orderBy = "o.mission";
						break;
					case "description":
						orderBy = "o.description";
						break;
					case "refCode":
						orderBy = "o.refCode";
						break;
					case "posted":
						orderBy = "o.posted";
						break;
					case "lastUpdate":
						orderBy = "o.lastUpdate";
						break;
					case "postDate":
						orderBy = "o.postDate";
						break;
					case "targetDate":
						orderBy = "o.targetDate";
						break;
					case "seq":
						orderBy = "o.seq";
						break;
//					case "webDate":
//						orderBy = "w.lastUpdate";
//						break;
					case "webDate": 
						if (sort.getSortOrder().equals(SortOrder.DESCENDING)) 
						    orderBy = "w.lastUpdate DESC NULLS LAST";
						else
							orderBy = "w.lastUpdate ASC NULLS LAST";	
						break;
					default:
						continue;
				}
//				if (orderBy != null) {
//					orderBys.add(orderBy + " " + (sort.getSortOrder().equals(SortOrder.DESCENDING) ? "DESC" : "ASC"));
//				}
				
				if(!StringUtil.isNullOrEmpty(orderBy) && !orderBy.endsWith("LAST")){
					if (sort.getSortOrder().equals(SortOrder.DESCENDING)) {
						orderBy += " DESC";
					} else {
						orderBy += " ASC";
					}	
				} 
				if (orderBy != null) {
					orderBys.add(orderBy);
				}
			}
		}

//		if (!orderBys.isEmpty()) {
//			sql += " ORDER BY " + String.join(" AND ", orderBys);
//		} else {
//			sql += " ORDER BY o.lastUpdate DESC,o.postDate DESC,o.refCode ASC";
//		}
		if (!orderBys.isEmpty()) {
			sql += " ORDER BY " + String.join(" , ", orderBys);
		} else {
			sql += " ORDER BY o.lastUpdate DESC,o.postDate DESC,o.refCode ASC";
		}

		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}

	@Override
	public Integer getMaxSeq() {
		String sql ="SELECT MAX(o.seq) from CentralBusinessEntity o WHERE o.deletedBy IS NULL ";
		Query q = em.createQuery(sql);
		return (Integer) q.getSingleResult();
	}



	

}
