package hksarg.sgil.dao.successcase;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hksarg.sgil.dao.ConditionResult;
import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.successcase.SuccessCaseEntity;
import hksarg.sgil.form.SuccessCaseSearchForm;
import hksarg.sgil.util.StringUtil;

@Stateless
@Local(SuccessCaseQueryLocal.class)
public class SuccessCaseQuery extends GenericDao<SuccessCaseEntity> implements SuccessCaseQueryLocal {

	private ConditionResult getCondition(SuccessCaseSearchForm form) {
		ConditionResult conditions = new ConditionResult();

		if(form == null || StringUtil.isNullOrEmpty(form.getIndcludeDeleted()))
		  conditions.add("o.deletedBy IS NULL");

		if (form != null) {
			if (!StringUtil.isNullOrEmpty(form.getKeyword())) {
				conditions.add("(o.title LIKE :keyword OR o.titleTc LIKE :keyword)", "keyword", "%" + form.getKeyword() + "%");
			}
			if (!StringUtil.isNullOrEmpty(form.getRefCode())) {
				conditions.add("o.refCode = :refCode", "refCode", form.getRefCode());
			}
		
		}

		return conditions;
	}

	@Override
	public Long count(SuccessCaseSearchForm form) {
		String sql = "SELECT COUNT(o) FROM SuccessCaseEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<SuccessCaseEntity> search(SuccessCaseSearchForm form, Integer offset, Integer maxResult,
			List<SortMeta> sortList) {
		String sql = "SELECT o FROM SuccessCaseEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		// Sorting
		List<String> orderBys = new ArrayList<String>();
		if (sortList != null && !sortList.isEmpty()) {
			for (SortMeta sort : sortList) {

				String orderBy = null;
				switch (sort.getSortField()) {
			       case "refCode":
				       orderBy = "o.refCode";
				       break;
				    case "title":
					    orderBy = "o.title";
					    break;
					case "titleTc":
						orderBy = "o.titleTc";
						break;
					case "lastUpdate":
						orderBy = "o.lastUpdate";
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
			sql += " ORDER BY o.lastUpdate DESC,o.refCode ASC";
		}

		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}
}
