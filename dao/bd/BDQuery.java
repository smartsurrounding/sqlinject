package hksarg.sgil.dao.bd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hksarg.sgil.dao.ConditionResult;
import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.BD.BDEntity;
import hksarg.sgil.entity.BD.BDEntity;
import hksarg.sgil.form.BDSearchForm;
import hksarg.sgil.util.StringUtil;

@Stateless
@Local(BDQueryLocal.class)
public class BDQuery extends GenericDao<BDEntity> implements BDQueryLocal {

	private ConditionResult getCondition(BDSearchForm form) {
		ConditionResult conditions = new ConditionResult();

		conditions.add("o.deletedBy IS NULL");

		if (form != null) {
			if (!StringUtil.isNullOrEmpty(form.getNameLike())) {
				conditions.add("(o.nameEn LIKE :nameLike OR o.nameTc LIKE :nameLike OR o.nameSc LIKE :nameLike)", "nameLike", "%" + form.getNameLike() + "%");
			}
			if (!StringUtil.isNullOrEmpty(form.getName())) {
				conditions.add("(o.nameEn =:name OR o.nameTc =:name OR o.nameSc =:name)", "name",  form.getName());
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
		String sql = "SELECT COUNT(o) FROM BDEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<BDEntity> search(BDSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList) {
		String sql = "SELECT o FROM BDEntity o";
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
		
		logger.info("search bd query :"+sql);

		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}
	
	
	
	@Override
	public BDEntity getByName(String name) {
		try {
			HashMap<String, Object> valueMap = new HashMap<String, Object>();

			String sql = "SELECT o FROM BDEntity o WHERE o.deletedBy IS NULL ";
	
			if (!StringUtil.isNullOrEmpty(name)) {
				sql += " AND (o.nameEn =:name or o.nameTc =:name ) ";
				valueMap.put("name", name);
			}

			List<BDEntity> list = this.search(sql, valueMap, null, null);
			if (list == null || list.isEmpty())
				return null;
			else
				return list.get(0);

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return null;

	}


	
}
