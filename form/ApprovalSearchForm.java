package hksarg.sgil.form;

import java.io.Serializable;
import java.util.List;

import hksarg.sgil.valuetype.EApprovalStatus;
import hksarg.sgil.valuetype.EApprovalType;
import hksarg.sgil.valuetype.EModule;
import lombok.Data;

@Data
@SuppressWarnings("serial")
public class ApprovalSearchForm implements Serializable {

	private EModule module;
	private String recordId;
	private EApprovalType type;
	private EApprovalStatus status;
	private List<EApprovalStatus> statuses;
	private String title;
	private List<EModule> moduleList;

}
