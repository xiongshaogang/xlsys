package xlsys.base.io.util;

import xlsys.base.exception.NativeException;

public class VideoUtil
{
	/**
	 * 转换视频格式, 源格式和目标格式将根据文件名后缀自动识别
	 * @param srcFilePath 源文件路径
	 * @param targetFilePath 目标文件路径
	 * @return
	 * @throws NativeException
	 */
	public static native boolean remuxingVideoFile(String srcFilePath, String targetFilePath) throws NativeException;
}
