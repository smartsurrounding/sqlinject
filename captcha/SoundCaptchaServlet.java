package hksarg.sgil.captcha;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.octo.captcha.service.sound.SoundCaptchaService;
//import com.sun.speech.freetts.VoiceManager;

@SuppressWarnings("serial")
public class SoundCaptchaServlet extends HttpServlet implements Serializable{

	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws ServletException, IOException {

		httpServletResponse.setContentType("audio/x-wav");
		// create SoundCaptchaService everytime the doGet is called

		SoundCaptchaService service = null; 
		if (httpServletRequest.getSession().getAttribute("soundService") != null) { 
			service = (SoundCaptchaService) httpServletRequest.getSession().getAttribute("soundService");
		} else {
			String word = "";
			if (httpServletRequest.getSession().getAttribute("generatedWord") != null) {
				word = (String) httpServletRequest.getSession().getAttribute("generatedWord");
			} 
			service = new SampleSoundCaptchaService(word);
		}
		// get AudioInputStream using session
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(); 
		if (httpServletRequest.getSession().getAttribute("stream") == null) {
			AudioInputStream audioInputStream = service.getSoundChallengeForID(httpServletRequest.getSession().getId(),
					httpServletRequest.getLocale());

			AudioSystem.write(audioInputStream, javax.sound.sampled.AudioFileFormat.Type.WAVE, byteOutputStream);
		} else {
			byteOutputStream = (ByteArrayOutputStream) httpServletRequest.getSession().getAttribute("stream");
		}

		// output to servlet
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		servletOutputStream.write(byteOutputStream.toByteArray());
		// save the service object to session, will use it for validation
		// purpose
		httpServletRequest.getSession().setAttribute("soundService", service);
		httpServletRequest.getSession().setAttribute("stream", byteOutputStream);

		// output to servlet response stream
		try {
			servletOutputStream.flush();
		} finally {
			servletOutputStream.close();
		}
	}

}
