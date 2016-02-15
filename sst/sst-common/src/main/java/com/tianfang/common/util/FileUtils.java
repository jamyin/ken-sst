package com.tianfang.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

import com.tianfang.common.digest.MD5Coder;


public class FileUtils extends org.apache.commons.io.FileUtils{
	
	protected static final Log logger = LogFactory.getLog(FileUtils.class);
	
	protected static final String dataImageBase[] = {"data:image/png;base64,","data:image/gif;base64,","data:image/jpeg;base64,","data:image/bmp;base64,","data:image/x-icon;base64,"};
	
	public static String getUploadFileName(String fileName) {
		String tempFile = fileName.substring(fileName.lastIndexOf(".")+1);
		return UUIDGenerator.getUUID32Bit() + "." + tempFile;
	}
	
	public static String getUploadFileNameBybase64(String fileName) throws Exception {
		return MD5Coder.encodeMD5Hex(fileName);
	}
	
	public static String uploadImage(MultipartFile myfile) throws IOException {
		//如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解  
        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解  
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String context = "upload" + "/" + format.format(new Date());
        String realPath = PropertiesUtils.getProperty("upload.url");
        String fileName = FileUtils.getUploadFileName(myfile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath + "/" + context, fileName));  
        return context + "/" + fileName;
	}
	
	public static void main(String[] args) {
		
		
		System.out.print(Math.ceil(15/10));
		//data:image/jpg;base64,
		///2Q==
//		String ddd = "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ceaLj3NsPV9jWhVtflaf3O58xaHr2v8AhwznSZZLfz9vmfuFfdtzj7ynHU1sf8LG8cf9BGT/AMA4v/iK+haK41hJxVlN/wBfM+gqZ/h6kuephotvq7N/+knzbpXhnxD4x1gyvDOfOfdPeTqQq++T1OOgH8q6X4ieGdR0TxBb65p0DvaRJDtkVd3lPGAo3D0wq89K9toqlg48rV9e5nLiKs68aiglFJrl8nbr8ux4YPjN4jA/489LP/bKT/4uuT8TeJr3xVqSX99FbxypEIQIFIXAJPcnn5jX0/RSlhak1aU/w/4JVDPMLQnz0sMk/wDF/wAA8P8AiP8A8if4K/68v/acVeh/DP8A5J5pX0l/9GvXW0VtChy1HO/SxwYnM/bYSOF5bWk3e/e+lreZ82eN/CV34W1ubET/ANnyuWtpwvy4PO3PZh0x7ZrpdH+M+o2VkkGo6dHfyIoUTCYxM3u3BBP5V7XNDFcRNFNGkkbDDI6ggj3BqlBoOjWpzb6TYRH1jtkX+Qr6R5tSrUlDFUuZrrex4nsmneLPH/EPjvV/GGn2kem+HLuGS2u0uo54Waf5lDYGAg9c/hWq/wAZb+xRI9R8MNHPj5sztEG9wrISB+Jr1sAAAAYAqKe3guojFcQxzRnqkihgfwNYfXsK0oSoe6v7zvr5j5Jb3Pnfxz45/wCEz+wf8S77H9k8z/lv5m/ft/2RjG39a6z4O+GbqO7m1+6haOAxGK23jG/JGWHsAMZ75PpXpsfhnQIpBJHoemo453LaRg/nitQAAAAYArXEZrB4b6th4csfW/mKNJ83NJnnnxl/5E22/wCv5P8A0B65Hw38Vv8AhH/D9ppX9i/aPs6keb9q2bssT02HHX1r3GivnJ0ZOfPGVvke9h8yoQwqw1ejzpO/xNfkjyP/AIXf/wBS9/5O/wD2uuL8S+JJPGfiezvI7BoHCJAsKv5hbDE56D+90xX0hRUTw9SatKenob4fNsJhp+0o4a0v8bf5o8i+N/39D+k//tOm+IviTplwt3ol/wCGxeJBI0IL3GMlSV3DC5U8djmvX6KqVCTlKUZWv5GNHNKMaNOlVpc3Jez5mt3foj5wsPDvi6XQ724sLa/h048vArsvmj2T+PHfitfwv480rwpa+UnhgC+A2zXHn/O579VJX6DiveKKiOEcGnGWvpc6qufxrxlCvRvFvpJx++25BZ3H2uxt7nbs86JZNuc4yM4zU9FFdh867N6BRRRQIKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAP";
//		  String ddd = "iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAFHklEQVRoQ+1ZXWwbRRCeubNLktotPwJBRftAGycivEDb2OIlQjROKkgckPIaVFRBa0tI/DzwJ0CA4AkqFM5VhSraR4qgsoMUYUdVFKmKnQRSfqISu0GlQIsiKJSzaZOcb9Altbk77/n2HGK1lf3m3dlv5ptvdnbXRrhBPniD8IA6kWtNyboi170iPzzc2y2IOEwAF1oSsU3XCiFHpZUJhj4HgMf0wfsSMUcYa0XcURCZYIjMgTTK802bx8cvr1WAvLirJoLiYkPz8PACr8O1snNE5Myu0IAqwNHrvrQ0AjNtbevcm7ZOE1K8JTH00lpl2CmuI0WcgtfSnptIZlfoFRDgLX1wCPB1cyK2QxvzBCJLCOByGrycktAbiJQ1kUo4SoG2XJ6M/myKhc81q2OpedrQejIuawhOgyl6JYK3EeFVvihWrDTyZnt+RRit13yGmMmYHdrNs8iUJQhprzwePVwVkWyw51kC4X3zYjsidlkuEmWoOSenpG1l4wR9clqKsXC5FGGVFSmFu1tOfPFrEbRxZ3izS8RzdsHr5/WK2ZWmqijB/NShpBV+1UTK1PBHfgeE24qOzGXV1P70DlFwTRbnC4rwwD9Tg9PF702BcI8IGGcFukiKbyF9KFspSbZEsl09bUTC92tVVnpcVudbXBK2LXw1OGentC0RVlkBUIcvER/TgzNK40+T81usyurquNaGVVbArC7leLOziLBuvHoiBKDkUpK7ElFjcP2iN3CHYpV1Qng9Ny69WXVpZXfv3kCFdZf+77JaxlPhcXlCOg5QTmKpoG51i4KhnOxUqVhamWDoCgDcpCdCRC+0JOPv/bdJI3tFgI/satg8L6ckAaBfMCshk7AR0oN/e/zh44jYZ1OOpWk7ImVXh2oeUk3t+x4VBXGo1NEaZtwwOqqUHZC/5Rvh7BEtecsfbyCsAKDIQ8aSCAFgNhgq23zVEHGqVjX2lkQynb1zgHiPAZTgY18y9qRNt6omDu41VnvFmgjH3Urz7glEfuKNAgG2GPYbgKObAKnqB/mJg2VXJQ1z1UTMJDyByDH9GBJMy2np3ZWaN17Xl6/w/sg7hj2Qll7Wvq9vDw8IiK3FOQJScunoa1ZJYxKZ7QoNIBmftEBw2peM3WsF5PGHVUQ04K10Jig1DCYR81uEKAuIzcaSpjE5He2opDyTCO8heLWzTAHgdr0TtUD35yejpwyOtz+10et2/1XKsIrP5CY+HFzfvv8JQRCOWAUpp24XAd5gnvgG5VkAvEQ8gfAvRjD8RE5Jz3v9kVm9EoDgUgnGBIQ9RXs5Ne8C+LTAKjltjIgol45qinJ9+BQhIl8ybgvqDUTKDlDZ6szQvfI8/vBDiHjCsFcYr0BHpXUm+Mh9Kri+My266EvESld0M6A3sL8XQDA9eOiAnIo+V7S1ex2a51n3NUdEsp09HYTCqHmR1UHo8UcO6G0JYSGfkl40r/cEIhdKYwTf5NJSd0UbAMilpLu46orVfq2IqEQ7W5PxKV7gWtuV7ZGZrq5b3dTwh1UgqEJ380jsy1oHauePb7PbodR4HlW1r3lkyLAn2UQ6ew8C4r4ax8d2R0SAoP1IPouE3xKow0vnf/ysbWZm0fYc0QwywdB5AODebA5JKwB0FghGVIDDN+OV03cmEnmHGAbziu8R6u8Xs5cWLZ+gZsdIcI6AjgpYODYvX5x7sIb/m9j++LCaLNVybZ1ILbPN46uuCE+WamlTV6SW2ebxVVeEJ0u1tPkXhJsSUYFTREgAAAAASUVORK5CYII=";
//						iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAFHklEQVRoQ+1ZXWwbRRCeubNLktotPwJBRftAGycivEDb2OIlQjROKkgckPIaVFRBa0tI/DzwJ0CA4AkqFM5VhSraR4qgsoMUYUdVFKmKnQRSfqISu0GlQIsiKJSzaZOcb9Altbk77/n2HGK1lf3m3dlv5ptvdnbXRrhBPniD8IA6kWtNyboi170iPzzc2y2IOEwAF1oSsU3XCiFHpZUJhj4HgMf0wfsSMUcYa0XcURCZYIjMgTTK802bx8cvr1WAvLirJoLiYkPz8PACr8O1snNE5Myu0IAqwNHrvrQ0AjNtbevcm7ZOE1K8JTH00lpl2CmuI0WcgtfSnptIZlfoFRDgLX1wCPB1cyK2QxvzBCJLCOByGrycktAbiJQ1kUo4SoG2XJ6M/myKhc81q2OpedrQejIuawhOgyl6JYK3EeFVvihWrDTyZnt+RRit13yGmMmYHdrNs8iUJQhprzwePVwVkWyw51kC4X3zYjsidlkuEmWoOSenpG1l4wR9clqKsXC5FGGVFSmFu1tOfPFrEbRxZ3izS8RzdsHr5/WK2ZWmqijB/NShpBV+1UTK1PBHfgeE24qOzGXV1P70DlFwTRbnC4rwwD9Tg9PF702BcI8IGGcFukiKbyF9KFspSbZEsl09bUTC92tVVnpcVudbXBK2LXw1OGentC0RVlkBUIcvER/TgzNK40+T81usyurquNaGVVbArC7leLOziLBuvHoiBKDkUpK7ElFjcP2iN3CHYpV1Qng9Ny69WXVpZXfv3kCFdZf+77JaxlPhcXlCOg5QTmKpoG51i4KhnOxUqVhamWDoCgDcpCdCRC+0JOPv/bdJI3tFgI/satg8L6ckAaBfMCshk7AR0oN/e/zh44jYZ1OOpWk7ImVXh2oeUk3t+x4VBXGo1NEaZtwwOqqUHZC/5Rvh7BEtecsfbyCsAKDIQ8aSCAFgNhgq23zVEHGqVjX2lkQynb1zgHiPAZTgY18y9qRNt6omDu41VnvFmgjH3Urz7glEfuKNAgG2GPYbgKObAKnqB/mJg2VXJQ1z1UTMJDyByDH9GBJMy2np3ZWaN17Xl6/w/sg7hj2Qll7Wvq9vDw8IiK3FOQJScunoa1ZJYxKZ7QoNIBmftEBw2peM3WsF5PGHVUQ04K10Jig1DCYR81uEKAuIzcaSpjE5He2opDyTCO8heLWzTAHgdr0TtUD35yejpwyOtz+10et2/1XKsIrP5CY+HFzfvv8JQRCOWAUpp24XAd5gnvgG5VkAvEQ8gfAvRjD8RE5Jz3v9kVm9EoDgUgnGBIQ9RXs5Ne8C+LTAKjltjIgol45qinJ9+BQhIl8ybgvqDUTKDlDZ6szQvfI8/vBDiHjCsFcYr0BHpXUm+Mh9Kri+My266EvESld0M6A3sL8XQDA9eOiAnIo+V7S1ex2a51n3NUdEsp09HYTCqHmR1UHo8UcO6G0JYSGfkl40r/cEIhdKYwTf5NJSd0UbAMilpLu46orVfq2IqEQ7W5PxKV7gWtuV7ZGZrq5b3dTwh1UgqEJ380jsy1oHauePb7PbodR4HlW1r3lkyLAn2UQ6ew8C4r4ax8d2R0SAoP1IPouE3xKow0vnf/ysbWZm0fYc0QwywdB5AODebA5JKwB0FghGVIDDN+OV03cmEnmHGAbziu8R6u8Xs5cWLZ+gZsdIcI6AjgpYODYvX5x7sIb/m9j++LCaLNVybZ1ILbPN46uuCE+WamlTV6SW2ebxVVeEJ0u1tPkXhJsSUYFTREgAAAAASUVORK5CYII=
		String baseCode = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAFHklEQVRoQ+1ZXWwbRRCeubNLktotPwJBRftAGycivEDb2OIlQjROKkgckPIaVFRBa0tI/DzwJ0CA4AkqFM5VhSraR4qgsoMUYUdVFKmKnQRSfqISu0GlQIsiKJSzaZOcb9Altbk77/n2HGK1lf3m3dlv5ptvdnbXRrhBPniD8IA6kWtNyboi170iPzzc2y2IOEwAF1oSsU3XCiFHpZUJhj4HgMf0wfsSMUcYa0XcURCZYIjMgTTK802bx8cvr1WAvLirJoLiYkPz8PACr8O1snNE5Myu0IAqwNHrvrQ0AjNtbevcm7ZOE1K8JTH00lpl2CmuI0WcgtfSnptIZlfoFRDgLX1wCPB1cyK2QxvzBCJLCOByGrycktAbiJQ1kUo4SoG2XJ6M/myKhc81q2OpedrQejIuawhOgyl6JYK3EeFVvihWrDTyZnt+RRit13yGmMmYHdrNs8iUJQhprzwePVwVkWyw51kC4X3zYjsidlkuEmWoOSenpG1l4wR9clqKsXC5FGGVFSmFu1tOfPFrEbRxZ3izS8RzdsHr5/WK2ZWmqijB/NShpBV+1UTK1PBHfgeE24qOzGXV1P70DlFwTRbnC4rwwD9Tg9PF702BcI8IGGcFukiKbyF9KFspSbZEsl09bUTC92tVVnpcVudbXBK2LXw1OGentC0RVlkBUIcvER/TgzNK40+T81usyurquNaGVVbArC7leLOziLBuvHoiBKDkUpK7ElFjcP2iN3CHYpV1Qng9Ny69WXVpZXfv3kCFdZf+77JaxlPhcXlCOg5QTmKpoG51i4KhnOxUqVhamWDoCgDcpCdCRC+0JOPv/bdJI3tFgI/satg8L6ckAaBfMCshk7AR0oN/e/zh44jYZ1OOpWk7ImVXh2oeUk3t+x4VBXGo1NEaZtwwOqqUHZC/5Rvh7BEtecsfbyCsAKDIQ8aSCAFgNhgq23zVEHGqVjX2lkQynb1zgHiPAZTgY18y9qRNt6omDu41VnvFmgjH3Urz7glEfuKNAgG2GPYbgKObAKnqB/mJg2VXJQ1z1UTMJDyByDH9GBJMy2np3ZWaN17Xl6/w/sg7hj2Qll7Wvq9vDw8IiK3FOQJScunoa1ZJYxKZ7QoNIBmftEBw2peM3WsF5PGHVUQ04K10Jig1DCYR81uEKAuIzcaSpjE5He2opDyTCO8heLWzTAHgdr0TtUD35yejpwyOtz+10et2/1XKsIrP5CY+HFzfvv8JQRCOWAUpp24XAd5gnvgG5VkAvEQ8gfAvRjD8RE5Jz3v9kVm9EoDgUgnGBIQ9RXs5Ne8C+LTAKjltjIgol45qinJ9+BQhIl8ybgvqDUTKDlDZ6szQvfI8/vBDiHjCsFcYr0BHpXUm+Mh9Kri+My266EvESld0M6A3sL8XQDA9eOiAnIo+V7S1ex2a51n3NUdEsp09HYTCqHmR1UHo8UcO6G0JYSGfkl40r/cEIhdKYwTf5NJSd0UbAMilpLu46orVfq2IqEQ7W5PxKV7gWtuV7ZGZrq5b3dTwh1UgqEJ380jsy1oHauePb7PbodR4HlW1r3lkyLAn2UQ6ew8C4r4ax8d2R0SAoP1IPouE3xKow0vnf/ysbWZm0fYc0QwywdB5AODebA5JKwB0FghGVIDDN+OV03cmEnmHGAbziu8R6u8Xs5cWLZ+gZsdIcI6AjgpYODYvX5x7sIb/m9j++LCaLNVybZ1ILbPN46uuCE+WamlTV6SW2ebxVVeEJ0u1tPkXhJsSUYFTREgAAAAASUVORK5CYII=";
		try {
//			for(String data : dataImageBase){
//				if(baseCode.indexOf(data)>-1){
//					String indexCode = baseCode.replace(data, "");
//					int start = data.indexOf("/");
//					int end  = data.indexOf(";");
//					System.out.println(data.substring(start+1,end));
//				}
//			}
//			System.out.println(ddd.indexOf("data:image/"));
//			System.out.println(ddd.indexOf("base64,"));
//			System.out.println(ddd.substring(ddd.lastIndexOf("base64,")));
//			System.out.println(uploadImage(ddd));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println(FileUtils.getUploadFileName("aaa.jsp"));
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//		System.out.println(format.format(new Date()));
	}
	/**
	 * 
		 * 此方法描述的是：base64转换成图片
		 * @author: cwftalus@163.com
		 * @version: 2015年10月21日 下午1:38:34
	 * @throws Exception 
	 */
	public static String uploadImage(String base64Img) throws Exception {
		if(StringUtils.isEmpty(base64Img)){
			return "";
		}
		base64Img = base64Img.substring(4, base64Img.length()-1);
		String prefix = "jpg";
		String baseImg  = "";
		for(String data : dataImageBase){
			if(base64Img.indexOf(data)>-1){
				baseImg = base64Img.replace(data, "");
				int start = data.indexOf("/");
				int end  = data.indexOf(";");				
				prefix = data.substring(start+1,end);
				break;
			}
		}		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String context = "upload" + File.separator + format.format(new Date());
        String fileName = FileUtils.getUploadFileNameBybase64(baseImg)+"."+prefix;
        
        StringBuffer rootPath = new StringBuffer(PropertiesUtils.getProperty("upload.url"));
        rootPath.append(File.separator).append(context);
        File file =new File(rootPath.toString());
        //如果文件夹不存在则创建    
        if(!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] bytes = decoder.decodeBuffer(baseImg);
            for(int i=0;i<bytes.length;++i){
                if(bytes[i]<0) {//调整异常数据
                	bytes[i]+=256;
                }
            }
            //生成jpeg图片
            String filePath = rootPath.append(File.separator).append(fileName).toString();
            OutputStream out = new FileOutputStream(filePath);    
            out.write(bytes);
            out.flush();
            out.close();
        } catch (Exception e){
        	logger.debug(e);
        }
		return File.separator+context + File.separator + fileName;
	}
	
}
