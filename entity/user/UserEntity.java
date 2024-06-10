package hksarg.sgil.entity.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import hksarg.sgil.entity.BaseEntity;
import hksarg.sgil.valuetype.EUserRole;
import lombok.Data;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@AuditOverride(forClass = BaseEntity.class)
@Data
@SuppressWarnings("serial")
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@Column(name = "username", nullable = false, length = 255)
	private String username;

	@Column(name = "email", nullable = false, length = 1000)
	private String email;

	@Type(type = "number_enum")
	@Column(name = "role", nullable = false)
	private EUserRole role;

	@Enumerated(EnumType.STRING)
	@Column(name = "enable", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean enable;

	@Column(name = "freeze_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date freezeDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "sso_enable", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean ssoEnable;

	@Column(name = "sso_uid", length = 255)
	private String ssoUid;

	@Column(name = "sso_dp_dept_id", length = 255)
	private String ssoDpDeptId;

	@Column(name = "lastlogin", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;

	@Column(name = "login_fail_count")
	private Integer loginFailCount = 0;

	@Column(name = "salt")
	private String salt;

	@Enumerated(EnumType.STRING)
	@Column(name = "resume", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean resume = false;

	@Column(name = "resume_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date resumeDate;

	@Column(name = "totp_key", length = 255)
	private String totpKey;
}
