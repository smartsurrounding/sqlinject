package hksarg.sgil.captcha;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.octo.captcha.service.sound.SoundCaptchaService;

@SuppressWarnings("serial")
public class CaptchaValidation extends HttpServlet {

	public static ImageCaptchaService service = SampleImageCaptchaService.getInstance();
	public static Logger logger = Logger.getLogger(CaptchaValidation.class.getName());
	public static boolean validateResponse(HttpServletRequest request, String userCaptchaResponse) {

		boolean validated = false;

		try {
			request.getSession().removeAttribute("stream");
			validated = service.validateResponseForID(request.getSession(true).getId(), userCaptchaResponse)
					.booleanValue();
			/*
			 * CaptchaStore captchaStore = null;
			 * if(!captchaStore.hasCaptcha(request.getSession(true).getId())){
			 * throw new
			 * CaptchaServiceException("Invalid ID, could not validate unexisting or already validated captcha"
			 * ); }
			 */
			/*
			 * if (!validated) { validated = validateSoundResponse(request,
			 * userCaptchaResponse); }
			 */
		} catch (Exception e) {
		}

		return validated;
	}

	/**
	 * Validate user's input
	 * 
	 * @param request
	 * @param userCaptchaResponse
	 * @return
	 */
	public static boolean validateSoundResponse(HttpServletRequest request, String userCaptchaResponse) {

		SoundCaptchaService service = (SoundCaptchaService) request.getSession().getAttribute("soundService");
		if (service == null) {
			return false;
		}

		boolean validated = false;
		try {
			validated = service.validateResponseForID(request.getSession(true).getId(), userCaptchaResponse)
					.booleanValue();
		} catch (CaptchaServiceException e) {
			logger.info("Validate Sound Captcha is fail!");
		}
		// remove sound service in the session
		service = null;
		request.getSession().removeAttribute("soundService");
		return validated;
	}

}
