package hksarg.sgil.entity.centralised;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import hksarg.sgil.entity.BaseEntity;
import hksarg.sgil.entity.PostEntity;
import hksarg.sgil.entity.tags.TagsEntity;
import hksarg.sgil.entity.usegov.UseGovEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@AuditOverride(forClass = BaseEntity.class)
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "cent_proposal")
public class CentralProposalEntity extends PostEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ignoreIfSet")
	@Column(name = "id")
	private Integer id;

	@Column(name = "temp_id")
	private Integer tempId;
	
	@Column(name = "ref_code")
	private String refCode;

	@Column(name = "solution_en")
	private String solutionEn;
	
	@Column(name = "solution_tc")
	private String solutionTc;

	@Lob
	@Column(name = "description",  columnDefinition = "TEXT")
	private String description;

	@Lob
	@Column(name = "use_case_public",  columnDefinition = "TEXT")
	private String usecasePublic;

	@Lob
	@Column(name = "use_case_gov",  columnDefinition = "TEXT")
	private String usecaseGov;
	

	@Lob
	@Column(name = "trial_explain",  columnDefinition = "TEXT")
	private String trialExplain;

	@Column(name = "trial_time")
	private String trialTime;
	
	@Lob
	@Column(name = "supplementary",  columnDefinition = "TEXT")
	private String supplementary;
	
	@Column(name = "company_en")
	private String companyEn;
	
	@Column(name = "company_tc")
	private String companyTc;
	
	@Column(name = "contact_en")
	private String contactEn;
	
	@Column(name = "contact_tc")
	private String contactTc;
	
	@Column(name = "job_title")
	private String jobTitle;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "website")
	private String website;
	

	@Lob
	@Column(name = "address",  columnDefinition = "TEXT")
	private String address;
	
	@Column(name = "located_hk")
	private String locatedHK;
	
	
	@Column(name = "other_category")
	private String otherCategory;
	

	@Column(name = "other_technology")
	private String otherTechnology;
	
	@Column(name = "other_trial_time")
	private String otherTrialTime;
	

	
	@Column(name = "re_name")
	private String representativeName;

	@Column(name = "re_job_title")
	private String representativeJobTitle;

	@Column(name = "signature_date")
	private Date signatureDate;

	
	@Column(name = "expired_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredDate;
	
	@Column(name = "thumbnail_id")
	private Integer thumbnailId;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "cent_proposal_categories")
	@Column(name = "category")
	@ForeignKey(name = "none")
	private List<Integer> categories=new ArrayList<>() ;
	
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "cent_proposal_technologies")
	@Column(name = "technology")
	@ForeignKey(name = "none")
	private List<Integer> technologies=new ArrayList<>() ;
	
	@AuditJoinTable(name="LOG_tags")
	@NotAudited
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "record_id")
	@Where(clause = "module = 'CIT_FORM'")
	@OrderBy("tag asc")
	@ForeignKey(name = "none")	
	List<TagsEntity> tags;
	
	@AuditJoinTable(name="LOG_use_gov")
	@NotAudited
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "record_id", referencedColumnName="id")
	@Where(clause = "module='CIT_FORM'")
	@OrderBy("implement_date asc")
	@ForeignKey(name = "none")	
	List<UseGovEntity> useGovs;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "cent_applicable_bd")
	@Column(name = "bd")
	@ForeignKey(name = "none")
	private List<Integer> applicableBDs=new ArrayList<>() ;
	/*
	@Column(name = "applicable_bd")
	private Integer applicableBD;
	*/
	
	
	@Column(name = "other_category_tc")
	private String otherCategoryTc;
	
	@Column(name = "other_technology_tc")
	private String otherTechnologyTc;
	
	@Column(name = "other_trial_time_tc")
	private String otherTrialTimeTc;
	
	@Enumerated(EnumType.STRING)
	@ColumnDefault("'N'")
	@Column(name = "`changed`", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean changed;
	 
	@Column(name = "seq")
	private Integer seq;
	
	public String getDisplayName() {		
		String name="";		
		if(this.refCode!=null) {
			name+=this.refCode+": ";
		}
		if(this.solutionEn!=null && this.solutionEn!="") {
			name +=this.solutionEn;
		}		
		if(!this.isPosted()) {
			name +=" *";
		}
		return name;
	}
}
