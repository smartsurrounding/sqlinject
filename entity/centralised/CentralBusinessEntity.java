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
import lombok.Data;
import lombok.EqualsAndHashCode;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@AuditOverride(forClass = BaseEntity.class)
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "cent_business")
public class CentralBusinessEntity extends PostEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ignoreIfSet")
	@Column(name = "id")
	private Integer id;

	
	@Column(name = "temp_id")
	private Integer tempId;
	
	@Column(name = "ref_code")
	private String refCode;

	@Lob
	@Column(name = "title",  columnDefinition = "TEXT")
	private String title;
	
	@Lob
	@Column(name = "mission",  columnDefinition = "TEXT")
	private String mission;

	@Lob
	@Column(name = "outcome",  columnDefinition = "TEXT")
	private String outcome;
	
	@Lob
	@Column(name = "supplementary",  columnDefinition = "TEXT")
	private String supplementary;
	
	@Column(name = "bureau")
	private String bureau;
	
	@Column(name = "bd_id")
	private Integer bdId;

	@Column(name = "contact")
	private String contact;
	
	@Column(name = "job_title")
	private String jobTitle;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "other_category")
	private String otherCategory;
	
	@Column(name = "other_technology")
	private String otherTechnology;

	@Column(name = "target_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date targetDate;

	
	
	@Column(name = "expired_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredDate;


	@Column(name = "convert_case_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date convertCaseDate;
	

	@Column(name = "thumbnail_id")
	private Integer thumbnailId;
	
	@ElementCollection
	@CollectionTable(name = "cent_business_categories")
	@Column(name = "category")
	@ForeignKey(name = "none")
	private List<Integer> categories = new ArrayList<>();
	
	
	@ElementCollection
	@CollectionTable(name = "cent_business_technologies")
	@Column(name = "technology")
	@ForeignKey(name = "none")
	private List<Integer> technologies= new ArrayList<>();
	
	@AuditJoinTable(name="LOG_tags")
	@NotAudited
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "record_id")
	@Where(clause = "module = 'CB_FORM'")
	@OrderBy("tag asc")
	@ForeignKey(name = "none")	
	List<TagsEntity> tags;
	
	
	@Lob
	@Column(name = "title_tc",  columnDefinition = "TEXT")
	private String titleTc;
	
	@Lob
	@Column(name = "mission_tc",  columnDefinition = "TEXT")
	private String missionTc;

	@Lob
	@Column(name = "outcome_tc",  columnDefinition = "TEXT")
	private String outcomeTc;
	
	@Lob
	@Column(name = "supplementary_tc",  columnDefinition = "TEXT")
	private String supplementaryTc;
	
	@Column(name = "contact_tc")
	private String contactTc;
	
	@Column(name = "job_title_tc")
	private String jobTitleTc;
	
	@Column(name = "other_category_tc")
	private String otherCategoryTc;
	
	@Column(name = "other_technology_tc")
	private String otherTechnologyTc;
	
	
	
	@Lob
	@Column(name = "title_sc",  columnDefinition = "TEXT")
	private String titleSc;
	
	@Lob
	@Column(name = "mission_sc",  columnDefinition = "TEXT")
	private String missionSc;

	@Lob
	@Column(name = "outcome_sc",  columnDefinition = "TEXT")
	private String outcomeSc;
	
	@Lob
	@Column(name = "supplementary_sc",  columnDefinition = "TEXT")
	private String supplementarySc;
	
	@Column(name = "other_category_sc")
	private String otherCategorySc;
	
	@Column(name = "other_technology_sc")
	private String otherTechnologySc;
	
	@Enumerated(EnumType.STRING)
	@ColumnDefault("'N'")
	@Column(name = "`changed`", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean changed;
 
	@Column(name = "seq")
	private Integer seq;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "matched_solution", nullable = false, length = 1)
	@Type(type = "yes_no")
	@ColumnDefault("'N'")
	private boolean matchedSolution;
	
	public String getDisplayName() {		
		String name="";		
		if(this.refCode!=null) {
			name+=this.refCode+": ";
		}
		if(this.title!=null && this.title!="") {
			name +=this.title;
		}		
		if(!this.isPosted()) {
			name +=" *";
		}
		return name;
	}

	
}
