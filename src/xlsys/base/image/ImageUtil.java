package xlsys.base.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import xlsys.base.io.util.FileUtil;
import xlsys.base.log.LogUtil;

/**
 * 图片工具类
 * @author Lewis
 *
 */
public class ImageUtil
{
	private static Set<String> picSuffixSet;
	
	/**
	 * 使用多线程方式批量改变图片大小
	 * @param fromDir 图片原目录
	 * @param toDir 图片目标目录
	 * @param needWidth 需要的宽度
	 * @param needHeight 需要的高度
	 * @param threadCount 线程数量
	 */
	public static void changePicsSizeWithMultiThread(String fromDir, String toDir, int needWidth, int needHeight, int threadCount)
	{
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(threadCount, threadCount, threadCount, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.AbortPolicy());
		doChangePicsSize(fromDir, toDir, needWidth, needHeight, threadPoolExecutor);
		threadPoolExecutor.shutdown();
	}
	
	/**
	 * 批量改变图片大小
	 * @param fromDir 图片原目录
	 * @param toDir 图片目标目录
	 * @param needWidth 需要的宽度
	 * @param needHeight 需要的高度
	 */
	public static void changePicsSize(String fromDir, String toDir, int needWidth, int needHeight)
	{
		doChangePicsSize(fromDir, toDir, needWidth, needHeight, null);
	}
	
	private static void doChangePicsSize(final String fromDir, final String toDir, final int needWidth, final int needHeight, ThreadPoolExecutor threadPoolExecutor)
	{
		File srcFile = new File(fromDir);
		if(srcFile.isDirectory())
		{
			for(File subFile : srcFile.listFiles())
			{
				doChangePicsSize(fromDir+File.separator+subFile.getName(), toDir+File.separator+subFile.getName(), needWidth, needHeight, threadPoolExecutor);
			}
		}
		else
		{
			if(threadPoolExecutor!=null)
			{
				Thread thread = new Thread()
				{
					public void run()
					{
						changePicSize(fromDir, toDir, needWidth, needHeight);
					}
				};
				threadPoolExecutor.execute(thread);
			}
			else
			{
				changePicSize(fromDir, toDir, needWidth, needHeight);
			}
			
		}
	}
	
	/**
	 * 将当前大小按照比例缩放到允许的指定宽度
	 * @param curWidth
	 * @param curHeight
	 * @param needWidth
	 * @return 缩放后的宽和高
	 */
	public static int[] getScaleSizeWithFixWidth(int curWidth, int curHeight, int needWidth)
	{
		int tempWidth = needWidth;
		int tempHeight = (int) (curHeight/((0.0+curWidth)/needWidth));
		return new int[]{tempWidth, tempHeight};
	}
	
	/**
	 * 将当前大小按照比例缩放到允许的指定高度
	 * @param curWidth
	 * @param curHeight
	 * @param needWidth
	 * @return 缩放后的宽和高
	 */
	public static int[] getScaleSizeWithFixHeight(int curWidth, int curHeight, int needHeight)
	{
		int tempHeight = needHeight;
		int tempWidth = (int) (curWidth/((0.0+curHeight)/needHeight));
		return new int[]{tempWidth, tempHeight};
	}
	
	/**
	 * 将当前大小按照比例缩放到允许的指定大小内
	 * @param curWidth
	 * @param curHeight
	 * @param canWidth
	 * @param canHeight
	 * @return 缩放后的宽和高
	 */
	public static int[] getScaleSize(int curWidth, int curHeight, int canWidth, int canHeight)
	{
		int tempHeight = -1;
		int tempWidth = -1;
		if(canWidth<curWidth&&canHeight<curHeight)
		{
			// 需要缩放
			if(canHeight*curWidth<canWidth*curHeight)
			{
				// 按高的比例缩放
				tempHeight = canHeight;
				tempWidth = curWidth*canHeight/curHeight;
			}
			else
			{
				// 按宽的比例缩放
				tempWidth = canWidth;
				tempHeight = curHeight*canWidth/curWidth;
			}
		}
		else
		{
			// 需要拉伸
			if(canWidth*curHeight<canHeight*curWidth)
			{
				// 按宽的比例拉伸
				tempWidth = canWidth;
				tempHeight = curHeight*canWidth/curWidth;
			}
			else
			{
				// 按高的比例拉伸
				tempHeight = canHeight;
				tempWidth = curWidth*canHeight/curHeight;
			}
		}
		return new int[]{tempWidth, tempHeight};
	}
	
	/**
	 * 不改变图片形状(长宽比)的情况下改变图片的大小.先将原始图片拉伸或缩放到合适大小后再进行裁剪.
	 * @param fromPath
	 * @param toPath
	 * @param needWidth
	 * @param needHeight
	 */
	public static void changePicSize(String fromPath, String toPath, int needWidth, int needHeight)
	{
		try
		{
			File srcFile = new File(fromPath);
			// 判断当前文件是否为合法图片
			if(!isPicFile(srcFile)) return;
			// 获取图片格式
			ImageInputStream iis = ImageIO.createImageInputStream(srcFile);
			// System.out.println(Thread.currentThread().getId() + " : " + fromPath);
			ImageReader reader = ImageIO.getImageReaders(iis).next();
			iis.close();
			iis = ImageIO.createImageInputStream(srcFile);
			reader.setInput(iis, true);
			String formatName = reader.getFormatName();
			reader.dispose();
			// 先将图片进行拉伸或缩放，以满足需要的尺寸
			BufferedImage srcImg  = ImageIO.read(srcFile);
			int imageType = srcImg.getType();
			int width = srcImg.getWidth();
			int height = srcImg.getHeight();
			int tempHeight = 0;
			int tempWidth = 0;
			/*if(needWidth<width&&needHeight<height)
			{
				// 需要缩放
				if(needHeight*width>needWidth*height)
				{
					// 按高的比例缩放
					tempHeight = needHeight;
					tempWidth = width*needHeight/height;
				}
				else
				{
					// 按宽的比例缩放
					tempWidth = needWidth;
					tempHeight = height*needWidth/width;
				}
			}
			else
			{
				// 需要拉伸
				if(needWidth*height>needHeight*width)
				{
					// 按宽的比例拉伸
					tempWidth = needWidth;
					tempHeight = height*needWidth/width;
				}
				else
				{
					// 按高的比例拉伸
					tempHeight = needHeight;
					tempWidth = width*needHeight/height;
				}
			}*/
			int[] tempSize = getScaleSize(width, height, needWidth, needHeight);
			Image tempImg = srcImg.getScaledInstance(tempSize[0], tempSize[1], Image.SCALE_SMOOTH); // 缩放或拉伸
			BufferedImage scaledImage = new BufferedImage(tempImg.getWidth(null), tempImg.getHeight(null), imageType);
			Graphics g = scaledImage.createGraphics();
			g.drawImage(tempImg, 0, 0, null);
			g.dispose();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(scaledImage, formatName, baos);
			byte[] scaledBytes = baos.toByteArray();
			baos.close();
			ByteArrayInputStream bais = new ByteArrayInputStream(scaledBytes);
			// 开始裁剪图片
			width = tempWidth;
			height = tempHeight;
			int x = (width-needWidth)/2;
			//int y = (height-needHeight)/2;
			int y = 0;
			iis = ImageIO.createImageInputStream(bais);
			reader = ImageIO.getImageReaders(iis).next();
			iis.close();
			bais = new ByteArrayInputStream(scaledBytes);
			iis = ImageIO.createImageInputStream(bais);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, needWidth, needHeight);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			File toFile = new File(toPath);
			FileUtil.createParentPath(toFile);
			ImageIO.write(bi, formatName, toFile);
			reader.dispose();
		}
		catch(Exception e)
		{
			LogUtil.printlnError(fromPath);
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断文件是否为图片文件
	 * @param file
	 * @return
	 */
	public static boolean isPicFile(File file)
	{
		boolean isPic = false;
		if(picSuffixSet==null)
		{
			picSuffixSet = new HashSet<String>();
			for(String picSuffix : ImageIO.getWriterFileSuffixes())
			{
				picSuffixSet.add(picSuffix.toLowerCase());
			}
		}
		String fileName = file.getName();
		int idx = fileName.lastIndexOf('.');
		if(idx!=-1)
		{
			String suffix = fileName.substring(idx+1);
			isPic = picSuffixSet.contains(suffix.toLowerCase());
		}
		return isPic;
	}
	
	public static void main(String[] args)
	{
		changePicsSizeWithMultiThread("f:/website", "f:/website1", 600, 400, 10);
	}
}
