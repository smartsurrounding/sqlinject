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
import hksarg.sgil.entity.system.ApprovalEntity;
import hksarg.sgil.form.ApprovalSearchForm;
import hksarg.sgil.valuetype.EApprovalStatus;

@Stateless
@Local(ApprovalQueryLocal.class)
public class ApprovalQuery extends GenericDao<ApprovalEntity> implements ApprovalQueryLocal {

	private ConditionResult getCondition(ApprovalSearchForm form) {
		ConditionResult conditions = new ConditionResult();

		conditions.add("o.deletedBy IS NULL");		
		conditions.add("o.module IS NOT NULL");
		conditions.add("o.recordId IS NOT NULL");

		if (form != null) {
			if (form.getModule() != null) {
				conditions.add("o.module=:module", "module", form.getModule());
			}
			
			if (form.getModuleList() != null && !form.getModuleList().isEmpty()) {
				conditions.add("o.module in :modules", "modules", form.getModuleList());
			}
			
			if (form.getType() != null) {
				conditions.add("o.type=:type", "type", form.getType());
			}
			if (form.getRecordId() != null) {
				conditions.add("o.recordId=:recordId", "recordId", form.getRecordId());
			}
			if (form.getStatus() != null) {
				conditions.add("o.status=:status", "status", form.getStatus());
			}
			if (form.getStatuses() != null) {
				if (form.getStatuses().isEmpty())
					conditions.add("1=0");
				else
					conditions.add("o.status in (:statuses)", "statuses", form.getStatuses());
			}
		}

		return conditions;
	}

	@Override
	public Long count(ApprovalSearchForm form) {

		String sql = "SELECT COUNT(o) FROM ApprovalEntity o";
		ConditionResult condition = getCondition(form);
		sql += condition.getWhereStatement();

		return this.count(sql, condition.getValueMap());
	}

	@Override
	public List<ApprovalEntity> search(ApprovalSearchForm form, Integer offset, Integer maxResult,
			List<SortMeta> sortList) {

		String sql = "SELECT o FROM ApprovalEntity o";
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
			sql += " ORDER BY NULL_FIRST(o.approvedUserId),o.createDate DESC";
		}

		return this.search(sql, condition.getValueMap(), offset, maxResult);
	}

	@Override
	public List<Integer> getDequeueIds() {
		String sql = "SELECT o.id FROM ApprovalEntity o WHERE o.status=:status AND (o.immediately=:immediately OR o.effectDate<=NOW()) ORDER BY o.createDate ASC";
		Query q = em.createQuery(sql);
		q.setParameter("status", EApprovalStatus.APPROVE);
		q.setParameter("immediately", true);

		return q.getResultList();
	}

}