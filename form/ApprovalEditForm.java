package hksarg.sgil.form;

import java.io.Serializable;
import java.util.Date;

import hksarg.sgil.valuetype.EApprovalStatus;
import hksarg.sgil.valuetype.EApprovalType;
import hksarg.sgil.valuetype.EModule;
import lombok.Data;

@Data
@SuppressWarnings("serial")
public class ApprovalEditForm implements Serializable {

	private Integer id;

	private EModule module;

	private String recordId;

	private EApprovalType type;
	private EApprovalStatus status;

	private String reason;

	private String description;

	private boolean immediately = true;

	private Date effectDate;

}
