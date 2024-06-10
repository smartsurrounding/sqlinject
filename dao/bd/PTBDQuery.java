package hksarg.sgil.dao.bd;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hksarg.sgil.dao.ConditionResult;
import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.BD.PTBDEntity;
import hksarg.sgil.form.BDSearchForm;
import hksarg.sgil.util.StringUtil;

@Stateless
@Local(PTBDQueryLocal.class)
public class PTBDQuery extends GenericDao<PTBDEntity> implements PTBDQueryLocal {

	private ConditionResult getCondition(BDSearchForm form) {
		ConditionResult conditions = new ConditionResult();

		conditions.add("o.deletedBy IS NULL");

		if (form != null) {
			if (!StringUtil.isNullOrEmpty(form.getName())) {
				conditions.add("(o.nameEn LIKE :name OR o.nameTc LIKE :name)", "name", "%" + form.getName() + "%");
			}
			
			if(form.getIds()!=null && !form.getIds().isEmpty()) {
				conditions.add("o.id in :idList");
				conditions.addValueMap("idList", form.getIds());
			}

		}

		return conditions;
	}

	@Override
	public Long count(BDSearchForm form) {
		String sql = "SELECT COUNT(o) FROM PTBDEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<PTBDEntity> search(BDSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList) {
		String sql = "SELECT o FROM PTBDEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		// Sorting
		List<String> orderBys = new ArrayList<String>();
		if (sortList != null && !sortList.isEmpty()) {
			for (SortMeta sort : sortList) {
                if(!StringUtil.isNullOrEmpty(sort.getSortField())) {
    				String orderBy = null;
    				switch (sort.getSortField()) {
    					case "nameEn":
    						orderBy = "o.nameEn";
    						break;
    					case "nameTc":
    						orderBy = "convertTC(o.nameTc)";
    						break;
    					case "nameSc":
    						orderBy = "convertSC(o.nameSc)";
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
		}

		if (!orderBys.isEmpty()) {
			sql += " ORDER BY " + String.join(" AND ", orderBys);
		} else {
			sql += " ORDER BY o.nameEn ASC";
		}

		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}

	

}
