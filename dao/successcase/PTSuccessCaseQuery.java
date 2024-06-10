package hksarg.sgil.dao.successcase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import hksarg.sgil.dao.ConditionResult;
import hksarg.sgil.dao.GenericDao;
import hksarg.sgil.entity.successcase.PTSuccessCaseEntity;
import hksarg.sgil.form.SuccessCaseSearchForm;
import hksarg.sgil.util.StringUtil;

@Stateless
@Local(PTSuccessCaseQueryLocal.class)
public class PTSuccessCaseQuery extends GenericDao<PTSuccessCaseEntity> implements PTSuccessCaseQueryLocal {

	private ConditionResult getCondition(SuccessCaseSearchForm form) {
		ConditionResult conditions = new ConditionResult();

		conditions.add("o.deletedBy IS NULL");

		if (form != null) {
			
			if(!StringUtil.isNullOrEmpty(form.getKeyword())) {
				conditions.add("(o.refCode like :keyword or o.title like :keyword or o.titleTc like :keyword or o.busRefCode like :keyword  "
						+ " or c IN (select id from CategoryEntity where nameEn like :keyword or nameTc like :keyword or nameSc like :keyword) " 
						+ "	or t IN (select id from TechnologyEntity where nameEn like :keyword or nameTc like :keyword or nameSc like :keyword) "
						+ " or g.tag like : :keyword "
						+ ") ");
				
				conditions.addValueMap("keyword", "%"+form.getKeyword()+"%");
			}
			
			
			String orCondition = "";
			if(form.getStrLikes()!=null && !form.getStrLikes().isEmpty()) {
				Integer indx = 1; 
				for(String str :form.getStrLikes()) {
					String keyword = "keyword_"+indx+" ";
					orCondition +="o.refCode like :"+keyword
						+ "or o.busRefCode like :"+keyword
						+ "or o.title like :"+keyword+" or o.titleTc like :"+keyword+" or o.titleSc like :"+keyword
						+ "or o.mission like :"+keyword+" or o.missionTc like :"+keyword+" or o.missionSc like :"+keyword
						+ "or o.outcome like :"+keyword+" or o.outcomeTc like :"+keyword+" or o.outcomeSc like :"+keyword
						+ "or o.comments like :"+keyword+" or o.commentsTc like :"+keyword+" or o.commentsSc like :"+keyword
						+ "or o.otherCategory like :"+keyword+" or o.otherCategoryTc like :"+keyword+" or o.otherCategorySc like :"+keyword
						+ "or o.otherTechnology like :"+keyword+"  or o.otherTechnologyTc like :"+keyword+" or o.otherTechnologySc like :"+keyword
						+ "or c IN (select pc.id from PTCategoryEntity pc where pc.nameEn like :"+keyword+" or pc.nameTc like :"+keyword+" or pc.nameSc like :"+keyword +") "
						+ "or t IN (select pt.id from PTTechnologyEntity pt where pt.nameEn like :"+keyword+" or pt.nameTc like :"+keyword+" or pt.nameSc like :"+keyword+") "
						+ "or g.tag like :"+keyword +" or ";
				
			    	conditions.addValueMap(keyword.trim(), "%"+str+"%");
			    	
			    	indx++;
			  }	
			  if(!StringUtil.isNullOrEmpty(orCondition)) {
				  conditions.add("("+orCondition.substring(0, orCondition.length()-3)+")");
			  }
		  }
		}

		return conditions;
	}

	@Override
	public Long count(SuccessCaseSearchForm form) {
		String sql = "SELECT COUNT(o) FROM PTSuccessCaseEntity o";
		if(form!=null && (!StringUtil.isNullOrEmpty(form.getKeyword()) || ( form.getStrLikes()!=null && !form.getStrLikes().isEmpty()))){
			sql+=" LEFT JOIN o.categories c ";
			sql+=" LEFT JOIN o.technologies t ";
			sql+=" LEFT JOIN o.tags g ";
		}
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<PTSuccessCaseEntity> search(SuccessCaseSearchForm form, Integer offset, Integer maxResult, List<SortMeta> sortList) {
		String sql = "SELECT o FROM PTSuccessCaseEntity o";
		if(form!=null && (!StringUtil.isNullOrEmpty(form.getKeyword()) || ( form.getStrLikes()!=null && !form.getStrLikes().isEmpty()))){
			sql+=" LEFT JOIN o.categories c ";
			sql+=" LEFT JOIN o.technologies t ";
			sql+=" LEFT JOIN o.tags g ";
		}
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
		sql+=" GROUP BY o.id ";
		if (!orderBys.isEmpty()) {
			sql += " ORDER BY " + String.join(" AND ", orderBys);
		} else {
			sql += " ORDER BY o.lastUpdate DESC";
		}
		
		logger.info("sql :"+sql);
		if(condition.getValueMap()!=null && !condition.getValueMap().isEmpty()) {
			for(Entry<String, Object> obj:condition.getValueMap().entrySet()) {
				logger.info("key  :"+obj.getKey()+", value :"+obj.getValue());
			}
		}

		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}

	

}
