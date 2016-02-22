package utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import play.Play;
import play.libs.Files;
import play.libs.Images;

public class UploadUtils {
	
	public static String image(File file){
		String ext = file.getName().substring(file.getName().lastIndexOf("."));	//该文件的后缀名
		String name = System.currentTimeMillis()+ext;
		String filepath = "/public/uploads/" + DateUtils.format(new Date(), "yyyy-MM")+"/"+DateUtils.format(new Date(), "dd");
		filepath = filepath + "/";
		File to = new File(Play.applicationPath,filepath);
		if(!to.exists()){
			to.mkdirs();
		}
		to = new File(to,name);
		Files.copy(file,to);
		return filepath + name;
	}
	
	public static String resizeImage(File file,String filename,int w,int h){
		String ext = file.getName().substring(file.getName().lastIndexOf("."));	//该文件的后缀名
//		String name = file.getName().substring(0,file.getName().lastIndexOf(".")) + "_" + filename + ext;
		String name = filename + ext;
		String filepath = "/public/uploads/" + DateUtils.format(new Date(), "yyyy-MM")+"/"+DateUtils.format(new Date(), "dd");
		filepath = filepath + "/";
		File to = new File(Play.applicationPath,filepath+name);
		Images.resize(file, to, w, h);
		return filepath + name;
	}
	
	public static String upload(File files){
		String ext = files.getName().substring(files.getName().lastIndexOf("."));
		String folderpath = "/public/uploads/" + DateUtils.format(new Date(), "yyyy-MM")+"/"+DateUtils.format(new Date(), "dd");
		File folder = new File(Play.applicationPath,folderpath);
		if(!folder.exists()) folder.mkdirs();
		String filename = System.currentTimeMillis()+ext;
		File to = new File(folder,filename);
		Files.copy(files,to);
		String imgPath =folderpath + "/" + filename;
		return imgPath;
	}
	
	public static boolean delFile(String filePathAndName) {
		String message = "";
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(Play.applicationPath, filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
				message = (filePathAndName + "删除文件操作出错");
			}
		} catch (Exception e) {
			message = e.toString();
		}
		return bea;
	}
	
	
	/**
	 * 检查文件类型是否是jpeg，JPG
	 * 
	 * @author ZhangLiLonG
	 * 
	 */
	public static boolean checkImageTypeVailable(File file) {
		if (file == null) {
			return false;
		}
		try {
			byte[] imgContent = FileUtils.readFileToByteArray(file);
			int len = imgContent.length;
			byte n1 = imgContent[len - 2];
			byte n2 = imgContent[len - 1];
			byte b0 = imgContent[0];
			byte b1 = imgContent[1];
			byte b6 = imgContent[6];
			byte b7 = imgContent[7];
			byte b8 = imgContent[8];
			byte b9 = imgContent[9];
			// JPG JPEG(FF D8 --- FF D9)
			if (b0 == -1 && b1 == -40 && n1 == -1 && n2 == -39) {
				return true;
			} else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I'
					&& b9 == (byte) 'F') {
				return true;
			} else if (b6 == (byte) 'E' && b7 == (byte) 'x' && b8 == (byte) 'i'
					&& b9 == (byte) 'f') {
				return true;
			} else {
				return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
	
}
