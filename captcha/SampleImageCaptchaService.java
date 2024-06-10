package hksarg.sgil.captcha;

import java.awt.image.BufferedImage;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.AbstractManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

public class SampleImageCaptchaService extends AbstractManageableImageCaptchaService implements ImageCaptchaService {

	private static SampleImageCaptchaService instance;

	private static ListImageCaptchaEngine engine;

	public static SampleImageCaptchaService getInstance() {
		if (instance == null) {
			engine = new SampleListImageCaptchaEngine();
			instance = new SampleImageCaptchaService(new FastHashMapCaptchaStore(), engine, 180, 100000, 75000);
		}
		return instance;
	}

	public SampleImageCaptchaService(CaptchaStore captchaStore, CaptchaEngine captchaEngine,
			int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize,
			int captchaStoreLoadBeforeGarbageCollection) {
		super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize,
				captchaStoreLoadBeforeGarbageCollection);
	}

	@Override
	public BufferedImage getImageChallengeForID(String ID) throws CaptchaServiceException {
		BufferedImage image = super.getImageChallengeForID(ID);
		String generatedWord = ((SampleListImageCaptchaEngine) engine).getWordBridge().getGeneratedWord();
		

		WordMap.getWordsMap().put(ID, generatedWord);
		return image;
	}
}
