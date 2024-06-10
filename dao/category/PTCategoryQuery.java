package hksarg.sgil.dao.category;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hksarg.sgil.dao.ConditionResult;
import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.category.PTCategoryEntity;
import hksarg.sgil.form.CategorySearchForm;
import hksarg.sgil.util.StringUtil;

@Stateless
@Local(PTCategoryQueryLocal.class)
public class PTCategoryQuery extends GenericDao<PTCategoryEntity> implements PTCategoryQueryLocal {

	private ConditionResult getCondition(CategorySearchForm form) {
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
			

			if(!StringUtil.isNullOrEmpty(form.getCode())) {
				conditions.add("o.code =:code", "code",  form.getCode() );
			}


		}

		return conditions;
	}

	@Override
	public Long count(CategorySearchForm form) {
		String sql = "SELECT COUNT(o) FROM PTCategoryEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<PTCategoryEntity> search(CategorySearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList) {
		String sql = "SELECT o FROM PTCategoryEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		// Sorting
		List<String> orderBys = new ArrayList<String>();
		if (sortList != null && !sortList.isEmpty()) {
			for (SortMeta sort : sortList) {
//                logger.info("sort field :"+sort.getSortField());
				if(!StringUtil.isNullOrEmpty(sort.getSortField())) {
					String orderBy = null;
					switch (sort.getSortField()) {
						case "nameEn":
							orderBy = "o.nameEn";
							break;
						case "nameTc" :
							orderBy = "convertTC(o.nameTc)";
							break;
						case "nameSc" :
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

		if (orderBys!=null && !orderBys.isEmpty()) {
			sql += " ORDER BY " + String.join(" AND ", orderBys);
		} else {
			sql += " ORDER BY o.seq ASC";
		}

//		logger.info("************* category query :"+sql);
		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}

	

}
