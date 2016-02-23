package xlsys.base.io.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * 条形码工具
 * @author Lewis
 *
 */
public class BarcodeUtil
{
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private static BufferedImage toBufferedImage(BitMatrix matrix)
	{
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}
	
	public static byte[] get1DBarcodeBytes(String url, int width, int height) throws WriterException, IOException
	{
		return get1DBarcodeBytes(url, width, height, "png");
	}
	
	public static byte[] get1DBarcodeBytes(String url, int width, int height, String format) throws WriterException, IOException
	{
		return get1DBarcodeBytes(url, width, height, "UTF-8", format);
	}
	
	public static byte[] get1DBarcodeBytes(String url, int width, int height, String charset, String format) throws WriterException, IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		write1DBarcode(url, width, height, charset, format, baos);
		return baos.toByteArray();
	}
	
	public static void write1DBarcode(String url, int width, int height, OutputStream out) throws WriterException, IOException
	{
		write1DBarcode(url, width, height, "png", out);
	}
	
	public static void write1DBarcode(String url, int width, int height, String format, OutputStream out) throws WriterException, IOException
	{
		write1DBarcode(url, width, height, "UTF-8", format, out);
	}
	
	public static void write1DBarcode(String url, int width, int height, String charset, String picFormat, OutputStream out) throws WriterException, IOException
	{
		writeBarcode(url, width, height, charset, picFormat, BarcodeFormat.CODE_128, out);
	}
	
	public static byte[] get2DBarcodeBytes(String url, int width, int height) throws WriterException, IOException
	{
		return get2DBarcodeBytes(url, width, height, "png");
	}
	
	public static byte[] get2DBarcodeBytes(String url, int width, int height, String format) throws WriterException, IOException
	{
		return get2DBarcodeBytes(url, width, height, "UTF-8", format);
	}
	
	public static byte[] get2DBarcodeBytes(String url, int width, int height, String charset, String format) throws WriterException, IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		write2DBarcode(url, width, height, charset, format, baos);
		return baos.toByteArray();
	}
	
	public static void write2DBarcode(String url, int width, int height, OutputStream out) throws WriterException, IOException
	{
		write2DBarcode(url, width, height, "png", out);
	}
	
	public static void write2DBarcode(String url, int width, int height, String format, OutputStream out) throws WriterException, IOException
	{
		write2DBarcode(url, width, height, "UTF-8", format, out);
	}
	
	public static void write2DBarcode(String url, int width, int height, String charset, String picFormat, OutputStream out) throws WriterException, IOException
	{
		writeBarcode(url, width, height, charset, picFormat, BarcodeFormat.QR_CODE, out);
	}
	
	/**
	 * 生成条码
	 * @param str 要生成条码的字符串
	 * @param width 生成条码的宽度
	 * @param height 生成条码的高度
	 * @param charset 字符串对应的字符集
	 * @param picFormat 生成条码图片的格式
	 * @param barcodeFormat 条码格式
	 * @param out 输出流
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void writeBarcode(String str, int width, int height, String charset, String picFormat, BarcodeFormat barcodeFormat, OutputStream out) throws WriterException, IOException
	{
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, charset);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(str, barcodeFormat, width, height, hints);
		BufferedImage image = toBufferedImage(bitMatrix);
		if(!ImageIO.write(image, picFormat, out)) throw new IOException("Could not write an image of format " + picFormat);
	}
	
	public static String readBarcode(InputStream in) throws NotFoundException, IOException
	{
		return readBarcode("UTF-8", in);
	}
	
	/**
	 * 获取条码信息
	 * @param charset
	 * @param in
	 * @return
	 * @throws NotFoundException
	 * @throws IOException
	 */
	public static String readBarcode(String charset, InputStream in) throws NotFoundException, IOException
	{
		BufferedImage image = ImageIO.read(in);
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, charset);
		Result result = new MultiFormatReader().decode(bitmap, hints);
		return result.getText();
	}
	
	public static void main(String[] args) throws WriterException, IOException, NotFoundException
	{
		String filePath = "d:/test.png";
		FileOutputStream fos = IOUtil.getFileOutputStream(filePath, false);
		write1DBarcode("1234567891234", 300, 100, fos);
		IOUtil.close(fos);
		FileInputStream fis = IOUtil.getFileInputStream(filePath);
		System.out.println(readBarcode(fis));
		IOUtil.close(fis);
	}
}
