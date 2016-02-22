package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Filscopy {
	
    public static void copy(File from, File to) {
        if (from.getAbsolutePath().equals(to.getAbsolutePath())) {
            return;
        }
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(from);
            os = new FileOutputStream(to);
            int read;
            byte[] buffer = new byte[2 * 1024];
            while ((read = is.read(buffer)) > 0) {
                os.write(buffer, 0, read);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (Exception ignored) {
            }
            try {
                os.close();
            } catch (Exception ignored) {
            }
        }
    }

    public static void saveUploadFile(File from, File dst) throws IOException {
    	InputStream input=new BufferedInputStream(new FileInputStream(from));
        OutputStream out = null;
        try {
            if (dst.exists()) {
                out = new BufferedOutputStream(new FileOutputStream(dst, true),
                		2 * 1024);
            } else {
                out = new BufferedOutputStream(new FileOutputStream(dst),
                		2 * 1024);
            }
 
            byte[] buffer = new byte[2 * 1024];
            int len = 0;
            while ((len = input.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
