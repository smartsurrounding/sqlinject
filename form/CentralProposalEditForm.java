package hksarg.sgil.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import hksarg.sgil.entity.usegov.UseGovEntity;
import hksarg.sgil.util.StringUtil;
import lombok.Data;

@Data
public class CentralProposalEditForm implements Serializable {

	private Integer id;

	private Integer tempId;
	
	private String refCode;
	@Size(max = 255, message = "{form.solution.length}")
    private String solutionEn;

	@Size(max = 255, message = "{form.solutionTc.length}")
	private String solutionTc;

	@NotBlank(message = "{form.description.required}")
	private String description;

	@NotBlank(message = "{form.usecasePublic.required}")
	private String usecasePublic;

//	private String usecaseGov;

	@NotBlank(message = "{form.trialExplain.required}")
	private String trialExplain;

	private String trialTime;

	private String supplementary;
	
	@NotBlank(message = "{form.companyEn.required}")
	@Size(max = 255, message = "{form.companyEn.length}")
	private String companyEn;
	
//	@NotBlank(message = "{form.companyTc.required}")
	@Size(max = 255, message = "{form.companyTc.length}")
	private String companyTc;

	
	@NotBlank(message = "{form.contactEn.required}")
	@Size(max = 255, message = "{form.contactEn.length}")
	private String contactEn;
	
//	@NotBlank(message = "{form.contactTc.required}")
	@Size(max = 255, message = "{form.contactTc.length}")
	private String contactTc;
	
	@NotBlank( message = "{form.jobTitle.required}")
	@Size(max = 255, message = "{form.jobTitle.length}")
	private String jobTitle;
	
	@NotBlank(message = "{form.email.required}")
	@Size(max = 255, message = "{form.email.length}")
//	@Pattern(regexp="[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,20}", message="{form.email.invalid}")
	@Pattern(regexp=".+@.+\\..+", message="{form.email.invalid}")
	private String email;
	
	@NotBlank(message = "{form.tel.required}")
	@Size(max = 255, message = "{form.tel.length}")
	private String tel;
	
//	@NotBlank(message = "{form.website.required}")
	@Size(max = 255, message = "{form.website.length}")
	private String website;

	@NotBlank(message = "{form.address.required}")
	private String address;

	@NotBlank(message = "{form.locatedHK.required}")
	private String locatedHK;
	
	@Size(max = 255, message = "{form.otherCategory.length}")
	private String otherCategory;


	@Size(max = 255, message = "{form.otherTechnology.length}")
	private String otherTechnology;

	@Size(max = 255, message = "{form.otherTrialTime.length}")
	private String otherTrialTime;
	
	@NotBlank(message = "{form.representativeName.required}")
	@Size(max = 255, message = "{form.representativeName.length}")
	private String representativeName;

	@NotBlank(message = "{form.representativeJobTitle.required}")
	@Size(max = 255, message = "{form.representativeJobTitle.length}")
	private String representativeJobTitle;
	
	@NotNull(message = "{form.signatureDate.required}")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date signatureDate;

	private Date expiredDate;
	
	private Date lastUpdate;
	
	private Integer thumbnailId;
	
	private List<Integer> categories = new ArrayList<>();
	
	private List<Integer> technologies = new ArrayList<>();
	
	private List<UseGovEntity> useGovs = new ArrayList<>();

	private List<Integer> applicableBDs = new ArrayList<>();

//	private Integer applicableBD;

	private Set<String> tagEns = new LinkedHashSet<>();

	private Set<String> tagTcs = new LinkedHashSet<>();

	private Set<String> tagScs = new LinkedHashSet<>();
	
	private String otherCategoryTc;

	private String otherTechnologyTc;

	private String otherTrialTimeTc;
	
	private boolean changed;
	

	private Integer seq;
	
	private Date sortDate;
	
	public String getTagEn() {
		if (tagEns == null)
			return "";
		return String.join(", ", tagEns);
	}

	public void setTagEn(String tagEn) {
		tagEns.clear();
		if (!StringUtil.isNullOrEmpty(tagEn)) {
			String[] tags = tagEn.split(",");
			if (tags != null)
				for (String tag : tags) {
					if (!StringUtil.isNullOrEmpty(tag.trim()))
						tagEns.add(tag.trim());
				}
		}
	}

	public String getTagTc() {
		if (tagTcs == null)
			return "";
		return String.join(", ", tagTcs);
	}

	public void setTagTc(String tagTc) {
		tagTcs.clear();
		if (!StringUtil.isNullOrEmpty(tagTc)) {
			String[] tags = tagTc.split(",");
			if (tags != null)
				for (String tag : tags) {
					if (!StringUtil.isNullOrEmpty(tag.trim()))
						tagTcs.add(tag.trim());
				}
		}
	}

	public String getTagSc() {
		if (tagScs == null)
			return "";
		return String.join(", ", tagScs);
	}

	public void setTagSc(String tagSc) {
		tagScs.clear();
		if (!StringUtil.isNullOrEmpty(tagSc)) {
			String[] tags = tagSc.split(",");
			if (tags != null)
				for (String tag : tags) {
					if (!StringUtil.isNullOrEmpty(tag.trim()))
						tagScs.add(tag.trim());
				}
		}
	}



}
