package pku.java.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.alibaba.fastjson.JSON;

public class ConvertToJson {
	
	/**
	 * 将Java对象转换为JSON字符串，并写到文件中去
	 * @param object
	 */
	public static void saveObjectToJSON(Object object, String filepath) {
		String jsonStr = JSON.toJSONString(object);
		
		File file = new File(filepath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		BufferedWriter bw = null;
		try {
			// 在JSON文件末尾追加写
			bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
