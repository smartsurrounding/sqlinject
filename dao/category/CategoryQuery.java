package hksarg.sgil.dao.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hksarg.sgil.dao.ConditionResult;
import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.category.CategoryEntity;
import hksarg.sgil.form.CategorySearchForm;
import hksarg.sgil.util.StringUtil;

@Stateless
@Local(CategoryQueryLocal.class)
public class CategoryQuery extends GenericDao<CategoryEntity> implements CategoryQueryLocal {

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

		}

		return conditions;
	}

	@Override
	public Long count(CategorySearchForm form) {
		String sql = "SELECT COUNT(o) FROM CategoryEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<CategoryEntity> search(CategorySearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList) {
		String sql = "SELECT o FROM CategoryEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		// Sorting
		List<String> orderBys = new ArrayList<String>();
		if (sortList != null && !sortList.isEmpty()) {
			for (SortMeta sort : sortList) {
               logger.info("sort field :"+sort.getSortField());
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
			sql += " ORDER BY o.lastUpdate DESC,o.seq ASC";
		}

		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}

	@Override
	public CategoryEntity getByName(String name) {
		try {
			HashMap<String, Object> valueMap = new HashMap<String, Object>();

			String sql = "SELECT o FROM CategoryEntity o WHERE o.deletedBy IS NULL ";
	
			if (!StringUtil.isNullOrEmpty(name)) {
				sql += " AND (o.nameEn =:name or o.nameTc =:name ) ";
				valueMap.put("name", name);
			}

			List<CategoryEntity> list = this.search(sql, valueMap, null, null);
			if (list == null || list.isEmpty())
				return null;
			else
				return list.get(0);

		} catch (Exception e) { 
			logger.info(e.getMessage());
		}
		return null;

	}

	@Override
	public String ConcatCategoryName(List<Integer> bdId) {
		String sql = " SELECT group_concat(name_en order by name_en asc) from category WHERE deletedBy IS NULL AND  id in :bdIds ";
		Query q = em.createNativeQuery(sql);
		q.setParameter("bdIds", bdId);
		return (String) q.getSingleResult();
	}

	@Override
	public List<Integer> getIdsByCode(String code) {
		
		
		if(!StringUtil.isNullOrEmpty(code)){
			String sql = "SELECT DISTINCT(o.id) FROM CategoryEntity o where o.deletedBy IS NULL ";
			sql+="and o.code=:code ";
			
			Query q = em.createQuery(sql);
			q.setParameter("code", code);
			
			return q.getResultList();
			
		}

		return null;
	}

}
