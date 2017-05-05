package com.zxcl.webapp.web.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class Base64ToImage {

	public static void base64ToImage(String base64Str,File target) throws IOException{

		// tokenize the data
		String[] parts = StringUtils.split(base64Str, ",");
		String imageString = parts[1];

		// create a buffered image
		BufferedImage image = null;
		byte[] imageByte;

		Base64 base64 = new Base64();
		imageByte = base64.decode(imageString.getBytes());
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		bis.close();

		// write the image to a file
		ImageIO.write(image, "jpg", target);
	}
}
