package com.qgm;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.MultimediaInfo;
import it.sauronsoftware.jave.VideoInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Test {
	
	//截图
	public static void main(String[] args){
		Encoder encoder = new Encoder();
		try {
			encoder.getImage(new File("E:\\D\\javaXM\\easydoc_pix\\public\\uploads\\2013-08\\09\\p181gt9l7v7pb1l96111v1bti1e845.mp4"), new File("E:\\test.jpg"), 1);
		} catch (EncoderException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			MultimediaInfo info= encoder.getInfo(new File("E:\\D\\javaXM\\easydoc_pix\\public\\uploads\\2013-08\\09\\p181gt9l7v7pb1l96111v1bti1e845.mp4"));
			info.getVideo().getSize().getHeight();
		} catch (InputFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}