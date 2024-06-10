package hksarg.sgil.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.octo.captcha.service.sound.SoundCaptchaService;

public class ImageCaptchaServlet extends HttpServlet implements Serializable{

	public static ImageCaptchaService service = SampleImageCaptchaService.getInstance();
	public static Logger logger = Logger.getLogger(ImageCaptchaServlet.class.getName());
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws ServletException, IOException {

		httpServletRequest.getSession().removeAttribute("soundService");
		httpServletRequest.getSession().removeAttribute("stream");
		// render the captcha challenge as a JPEG image in the response
		httpServletResponse.setHeader("Cache-Control", "no-store");
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setDateHeader("Expires", 0);
		httpServletResponse.setContentType("image/jpeg");

		// create the image using session ID
		BufferedImage bufferedImage = service.getImageChallengeForID(httpServletRequest.getSession(true).getId());
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		// write the image to the servlet output stream
		ImageIO.write(bufferedImage, "jpg", servletOutputStream);		
		httpServletRequest.getSession().setAttribute("generatedWord", WordMap.getWordsMap().get(httpServletRequest.getSession(true).getId())); 
		try {
			servletOutputStream.flush();
		} finally {
			servletOutputStream.close();
		}
	}

	/**
	 * Validate user's input
	 * 
	 * @param sessionID
	 * @param userCaptchaResponse
	 * @return
	 */
	public static boolean validateResponse(String sessionID, String userCaptchaResponse) {

		boolean validated = false;
		try {
			validated = service.validateResponseForID(sessionID, userCaptchaResponse).booleanValue();
		} catch (Exception e) {
			logger.info("validate image cpatcha is fail !");
		}

		return validated;
	}

}
