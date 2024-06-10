package hksarg.sgil.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class LoginForm implements Serializable {

	/**
	 * check JSR 303 , JSF Validation.
	 */
	@NotNull(message = "{user.username.required}")
	private String username;

	@NotNull(message = "{user.password.required}")
	private String password;

	private String referer;

}
