package hksarg.sgil.form;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.envers.RevisionType;

import hksarg.sgil.valuetype.EAuditLog;
import lombok.Data;

@Data
@SuppressWarnings("serial")
public class AuditSearchForm implements Serializable {

	private EAuditLog table;

	private RevisionType type;
	
	private Date startDate;
	private Date endDate;

}
