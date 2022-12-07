package classes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DrawPixel {

	private BufferedImage img;
	private Integer size;

	//tamanho padrao de imagem 480 * 480 pixels, fundo branco.
	public DrawPixel() {
		this(480);
//		size = 480;
//		img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
//		
//		for(int i = 0; i < size; i++) {
//			for(int j = 0; j < size; j++) {
//				img.setRGB(i, j, new Color(255,255,255).getRGB());
//			}
//		}
	}
	
	public DrawPixel(Integer size) {
		this.size = size;
		img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				img.setRGB(i, j, new Color(255,255,255).getRGB());
			}
		}
		
	}

	public Integer getSize() {
		return size;
	}
	
	public Integer getMaxPixel() {
		return (size-1);
	}
	
	public Integer getMinPixel() {
		return 50;
	}
	
	public Integer getCenter() {
		return size / 2;
	}

	public void  writePixel(int x, int y) throws Exception {
		img.setRGB(x, y, new Color(0,0,0).getRGB());
	}
	
	public void writePixel(int x, int y, Color cor) {
		img.setRGB(x, y, cor.getRGB());
	}

	public void showImage() {
		JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(img)));
	}
	
	public void imgWrite(String fileName) throws IOException {
		ImageIO.write(img, "png",  new File(fileName));
	}



}
