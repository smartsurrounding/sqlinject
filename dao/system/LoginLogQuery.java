package hksarg.sgil.dao.system;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hksarg.sgil.dao.ConditionResult;
import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.system.LoginLogEntity;
import hksarg.sgil.form.LoginLogSearchForm;
import hksarg.sgil.util.StringUtil;

@Stateless
@Local(LoginLogQueryLocal.class)
public class LoginLogQuery extends GenericDao<LoginLogEntity> implements LoginLogQueryLocal {

	private ConditionResult getCondition(LoginLogSearchForm form) {
		ConditionResult conditions = new ConditionResult();

	//	conditions.add("o.deletedBy IS NULL");

		if (form != null) {
			
			if(!StringUtil.isNullOrEmpty(form.getUsername())) {
				conditions.add("o.inputUsername =:username", "username", form.getUsername());
			}
			if(form.getToDate()!=null) {
				conditions.add("o.createDate <:createDate", "createDate", form.getToDate());
			}
			if(!StringUtil.isNullOrEmpty(form.getSuccessed())) {
				if("Y".contentEquals(form.getSuccessed())) {
					conditions.add("o.success =:success", "success", true);
				}else
				  conditions.add("o.success =:success", "success", false);
			}
		}

		return conditions;
	}

	@Override
	public Long count(LoginLogSearchForm form) {

		String sql = "SELECT COUNT(o) FROM LoginLogEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<LoginLogEntity> search(LoginLogSearchForm form, Integer offset, Integer maxResult,
			List<SortMeta> sortList) {

		String sql = "SELECT o FROM LoginLogEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		// Sorting
		List<String> orderBys = new ArrayList<String>();
		if (sortList != null && !sortList.isEmpty()) {
			for (SortMeta sort : sortList) {

				String orderBy = null;
				switch (sort.getSortField()) {
					case "createDate":
						orderBy = "o.createDate";
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
			sql += " ORDER BY o.createDate DESC";
		}

		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}

}