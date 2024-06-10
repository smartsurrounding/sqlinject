package hksarg.sgil.captcha;

import java.util.Locale;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;

import com.octo.captcha.component.sound.wordtosound.WordToSound;
import com.octo.captcha.component.word.worddecorator.SpellerWordDecorator;
import com.octo.captcha.component.word.worddecorator.WordDecorator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.sound.SoundCaptcha;
import com.octo.captcha.sound.speller.SpellerSound;
import com.octo.captcha.sound.speller.SpellerSoundFactory;

public class SampleSpellerSoundFactory extends SpellerSoundFactory {

	private String word;

	private WordGenerator wordGenerator;

	private WordToSound word2Sound;

	private WordDecorator wordDecorator;

	public SampleSpellerSoundFactory(WordGenerator wordGenerator, WordToSound word2Sound,
			SpellerWordDecorator wordDecorator, String word) {
		super(wordGenerator, word2Sound, wordDecorator);
		this.word = word;
		this.wordGenerator = wordGenerator;
		this.word2Sound = word2Sound;
		this.wordDecorator = wordDecorator;
	}

	@Override
	public SoundCaptcha getSoundCaptcha() {
		return getSoundCaptcha(Locale.getDefault());
	}

	@Override
	public SoundCaptcha getSoundCaptcha(Locale locale) {
		String soundWord = "";
		if (this.word != null && !this.word.equals("")) {
			soundWord = this.word;
		} else {
			soundWord = this.wordGenerator.getWord(getRandomLength(), locale);
		}

		AudioInputStream sound = this.word2Sound.getSound(wordDecorator.decorateWord(soundWord), locale);
		SoundCaptcha soundCaptcha = new SpellerSound(getQuestion(locale), sound, word);
		return soundCaptcha;
	}

}
