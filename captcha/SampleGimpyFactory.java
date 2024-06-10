package hksarg.sgil.captcha;

import java.awt.image.BufferedImage;
import java.util.Locale;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.CaptchaQuestionHelper;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class SampleGimpyFactory extends GimpyFactory {

	private WordBridge wordBridge = new WordBridge();

	public SampleGimpyFactory(WordGenerator generator, WordToImage word2image) {
		super(generator, word2image);
	}

	public SampleGimpyFactory(WordGenerator generator, WordToImage word2image, WordBridge wordBridge) {
		super(generator, word2image);
	}

	public ImageCaptcha getImageCaptcha(Locale locale) {
		// length
		Integer wordLength = getRandomLength();

		String word = getWordGenerator().getWord(wordLength, locale);
		if (this.wordBridge != null) {
			this.wordBridge.setGeneratedWord(word);
		}
		BufferedImage image = null;
		try {
			image = getWordToImage().getImage(word);
		} catch (Throwable e) {
			throw new CaptchaException(e);
		}

		ImageCaptcha captcha = new SampleGimpy(CaptchaQuestionHelper.getQuestion(locale, BUNDLE_QUESTION_KEY), image,
				word);
		return captcha;
	}

	public WordBridge getWordBridge() {
		return this.wordBridge;
	}

}
