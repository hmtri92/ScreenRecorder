package com.ScreenRecoder;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameRecorder.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class Recorder {

	private Robot robot;
	private List<BufferedImage> lstScreenRecord;
	public static boolean videoComplete = false;
	public static String inputImgExt = "png";
	public static String inputImageDir = "inputImgFolder" + File.separator;
	private FFmpegFrameRecorder ffRecorder;
	private String outputVideo = "recording.mp4";
	private int videoWidth = 1920;
	private int videoHeight = 1080;
	private int videoFrameRate = 3;
	private int videoQuality = 0; // 0 is the max quality
	private int videoBitRate = 9000;
	private String videoFormat = "mp4";
	private int videoCodec = avcodec.AV_CODEC_ID_MPEG4;
	private Thread thread1;
	private Thread thread2;

	public void record() {
		record(FrameFactory.selectionBound);
	}

	public void takeScreenShot() {
		takeScreenShot(FrameFactory.selectionBound);
	}

	public void record(Rectangle area) {
		lstScreenRecord = new ArrayList<BufferedImage>();
	}

	public void takeScreenShot(Rectangle area) {
		try {
			BufferedImage bufferedImage = getRobot().createScreenCapture(area);

			File file = getSaveFile(inputImgExt);
			if (file != null) {
				ImageIO.write(bufferedImage, inputImgExt, file);
			}

			FrameFactory.getFrame(FrameFactory.FRAME_MAIN).setVisible(true);
			FrameFactory.getFrame(FrameFactory.FRAME_MAIN).setState(Frame.NORMAL);

		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getSaveFile(String subfix) {
		JFileChooser fc = new JFileChooser();
		int rValue = fc.showSaveDialog(FrameFactory.frmMain);

		if (rValue == JFileChooser.APPROVE_OPTION) {
			return new File(fc.getSelectedFile().getAbsolutePath() + "." + subfix);
		}

		return null;
	}

	private Robot getRobot() throws AWTException {
		if (robot == null) {
			robot = new Robot();
		}

		return robot;
	}

	public void startRecording() {
		Runnable runTakeScreenshot = () -> {
			try {
				takeScreenShotVideo(getRobot());
			} catch (AWTException e) {
				e.printStackTrace();
			}
		};

		Runnable runPrepareVideo = () -> {
			prepareVideo();
		};

		thread1 = new Thread(runTakeScreenshot);
		thread2 = new Thread(runPrepareVideo);

		thread1.start();
		thread2.start();
	}

	private FFmpegFrameRecorder getRecorder() {
		if (ffRecorder != null) {
			return ffRecorder;
		}

		ffRecorder = new FFmpegFrameRecorder(outputVideo, videoWidth, videoHeight);
		ffRecorder.setFrameRate(videoFrameRate);
		ffRecorder.setVideoBitrate(videoBitRate);
		ffRecorder.setFormat(videoFormat);
		ffRecorder.setVideoQuality(videoQuality);
		try {
			ffRecorder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ffRecorder;
	}
	
	private void addImageToVideo(String imgPath) {
		try {
			getRecorder().record(getFrameConverter().convert(cvLoadImage(imgPath)));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private OpenCVFrameConverter.ToIplImage getFrameConverter() {  
         OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();  
         return grabberConverter;  
    }
	
	private void takeScreenShotVideo(Robot r) {
		long counter = 0;
		while (!videoComplete) {
			counter++;
			BufferedImage bufferedImage = r.createScreenCapture(FrameFactory.selectionBound);
			try {
				ImageIO.write(bufferedImage, inputImgExt, new File(inputImageDir + counter + "." + inputImgExt));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	private void prepareVideo() {
		File scanFolder = new File(inputImageDir);  
        while(!videoComplete) {
             File[] inputFiles = scanFolder.listFiles();
             try {
                  getRobot().delay(500);
             } catch (AWTException e) {
				e.printStackTrace();
			}
             for(int i = 0; i < inputFiles.length; i++) {
                  addImageToVideo(inputFiles[i].getAbsolutePath());
                  inputFiles[i].delete();
             }  
        }
        File[] inputFiles = scanFolder.listFiles();  
        for(int i = 0; i < inputFiles.length; i++) {
             addImageToVideo(inputFiles[i].getAbsolutePath());
             inputFiles[i].delete();
        }  
	}

}
