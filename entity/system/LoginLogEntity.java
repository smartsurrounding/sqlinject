package hksarg.sgil.entity.system;

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

import hksarg.sgil.valuetype.ELoginActionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "login_log")
public class LoginLogEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "input_username")
	private String inputUsername;

	@Column(name = "user_id")
	private Integer userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "action", length = 6)
	@Type(type = "label_enum")
	private ELoginActionType action;

	@Enumerated(EnumType.STRING)
	@Column(name = "success", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean success;

	@Column(name = "ip")
	private String ip;

	@Column(name = "create_date", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
}
