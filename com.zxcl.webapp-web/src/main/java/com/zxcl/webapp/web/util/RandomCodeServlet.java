package com.zxcl.webapp.web.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zxj
 *
 */

public class RandomCodeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	//图片验证码
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = SMSTBUtils.randomCode(4);
		req.getSession().setAttribute(SMSTBUtils.USERRANDOMCODE, code);
		ImageIO.write(getImageBuffer(code), "JPG", resp.getOutputStream());
	}
	private static Random r = new Random();
	private static BufferedImage getImageBuffer(String code){
		BufferedImage bi = new BufferedImage(80, 24, BufferedImage.TYPE_INT_BGR);
		Graphics g = bi.getGraphics();
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, 80, 24);
		
		for (int i = 0; i < code.length(); i++) {
			g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));
			g.setFont(new Font("Arial", Font.BOLD , 18));
			g.drawString(code.charAt(i)+"" , (i * 15) + 3, 18);
		}
		return bi;
	}
}
