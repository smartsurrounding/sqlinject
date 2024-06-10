package hksarg.sgil.entity.highlight;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import hksarg.sgil.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@AuditOverride(forClass = BaseEntity.class)
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "pt_video_gallery")
public class PTVideoGalleryEntity extends BaseEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ignoreIfSet")
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "thumbnail_id")
	private Integer thumbnailId;
	
	@Column(name = "tc_thumbnail_id")
	private Integer tcThumbnailId;
	
	@Column(name = "sc_thumbnail_id")
	private Integer scThumbnailId;
	
	@Column(name = "title_en")
	private String titleEn;
	
	@Column(name = "title_tc")
	private String titleTc;
	
	@Column(name = "title_sc")
	private String titleSc;
	
	@Lob
	@Column(name = "content_en",  columnDefinition = "TEXT")
	private String contentEn;
	
	@Lob
	@Column(name = "content_tc",  columnDefinition = "TEXT")
	private String contentTc;
	
	@Lob
	@Column(name = "content_sc",  columnDefinition = "TEXT")
	private String contentSc;
	
	@Column(name = "news_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date newsDate;
	
//	@Column(name = "date_en")
//	private String dateEn;
//	
//	@Column(name = "date_tc")
//	private String dateTc;
//	
//	@Column(name = "date_sc")
//	private String dateSc;
//	
	

}
