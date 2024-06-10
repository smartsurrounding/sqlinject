package hksarg.sgil.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import hksarg.sgil.util.StringUtil;
import lombok.Data;

@Data
public class CentralBusinessEditForm  implements Serializable {

	private Integer id;

	private Integer tempId;
	
	private String refCode;
	
	@NotBlank(message = "{form.title.required}")
	private String title;

	@NotBlank(message = "{form.mission.required}")
	private String mission;

	private String outcome;
	
	private String supplementary;
	
	@Size(max = 255, message = "{form.bureau.length}")
	private String bureau;

	@NotNull(message = "{form.bureau.required}")
	private Integer bdId;

	@NotBlank(message = "{form.contact.required}")
	@Size(max = 255, message = "{form.contact.length}")
	private String contact;

	@NotBlank(message = "{form.jobTitle.required}")
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

	@Size(max = 255, message = "{form.title.length}")
	private String otherCategory;

	@Size(max = 255, message = "{form.title.length}")
	private String otherTechnology;
	 

	@NotNull(message = "{form.targetDate.required}")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date targetDate;
	
	
	@NotNull(message = "{form.expiredDate.required}")
	private Date expiredDate;
	
	private Date convertCaseDate;

	private Integer thumbnailId;
	
	
	@NotBlank(message = "{form.titleTc.required}")
	private String titleTc;

	@NotBlank(message = "{form.missionTc.required}")
	private String missionTc;

	private String outcomeTc;
	
	private String supplementaryTc;
	
//	@NotBlank(message = "{form.contactTc.required}")
//	@Size(max = 255, message = "{form.contactTc.length}")
	private String contactTc;
	
//	@NotBlank(message = "{form.jobTitleTc.required}")
//	@Size(max = 255, message = "{form.jobTitleTc.length}")
	private String jobTitleTc;

	private List<Integer> categories = new ArrayList<>();

	private List<Integer> technologies= new ArrayList<>();

	
	private Date lastUpdate;
	
	private Set<String> tagEns = new LinkedHashSet<>();

	private Set<String> tagTcs = new LinkedHashSet<>();

	private Set<String> tagScs = new LinkedHashSet<>();
	
	
	private String otherCategoryTc;

	private String otherTechnologyTc;
	
	
	private String titleSc;

	private String missionSc;

	private String outcomeSc;
	
	private String supplementarySc;

	private String otherCategorySc;

	private String otherTechnologySc;

	private boolean changed;
	
	private Integer seq;
	
	private Date sortDate;

	private boolean matchedSolution;
	
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
