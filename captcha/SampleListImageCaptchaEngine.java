package hksarg.sgil.captcha;

import java.awt.Color;
import java.awt.Font;

import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptchaFactory;

public class SampleListImageCaptchaEngine extends ListImageCaptchaEngine {
	private WordBridge wordBridge;

	public SampleListImageCaptchaEngine() {
		super();
	}

	protected void buildInitialFactories() {
		
	   Font[] fontsList = new Font[] {
		            new Font("Arial", 0, 10),
		            new Font("Tahoma", 0, 10),
		            new Font("Verdana", 0, 10),
		 };
		// create text parser
		/*
		  TextPaster randomPaster = new DecoratedRandomTextPaster(new Integer(4), new Integer(4), new SingleColorGenerator(Color.BLACK),
				new TextDecorator[] { new BaffleTextDecorator(new Integer(1), Color.WHITE) });
		*/
		  TextPaster randomPaster = new DecoratedRandomTextPaster(new Integer(4), new Integer(4), new SingleColorGenerator(Color.BLACK),new TextDecorator[] {});
		
		 //create font generator	       
	      FontGenerator fontGenerator = new RandomFontGenerator(new Integer(30), new Integer(30),fontsList);
	       
	      //create background generator
	      FunkyBackgroundGenerator background = new FunkyBackgroundGenerator(new Integer(260), new Integer(70));
	       
		// create image captcha factory
		ImageCaptchaFactory factory = new SampleGimpyFactory(new RandomWordGenerator("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
				new ComposedWordToImage(fontGenerator, background, randomPaster));
		wordBridge = ((SampleGimpyFactory) factory).getWordBridge();

		ImageCaptchaFactory characterFactory[] = { factory };
		this.addFactories(characterFactory);

	}

	public WordBridge getWordBridge() {
		return wordBridge;
	}

}
