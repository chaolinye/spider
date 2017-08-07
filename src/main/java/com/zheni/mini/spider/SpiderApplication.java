package com.zheni.mini.spider;

import com.zheni.mini.spider.bean.MusicInfo;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Scanner;

public class SpiderApplication {

	private static final String MUSICINFO_URL="http://www.ximalaya.com/tracks/";
	private static final String FILE_PATH_PREFIX="E:/mini/download/music/";

	private static final String ALBUM_URL="http://www.ximalaya.com/5638858/album/";
	private static String[] findMusicFromAlbum(String albumId,int num){
		String[] musics=new String[num];
		HttpClient client=new DefaultHttpClient();
		HttpGet get=new HttpGet(ALBUM_URL+albumId);
		try{
			HttpResponse response=client.execute(get);
			String html=EntityUtils.toString(response.getEntity());
			Document doc=Jsoup.parse(html);
			Element e=doc.getElementsByClass("personal_body").get(0);
			String idString=e.attr("sound_ids");
//			System.out.println(idString+"\n");
			String[] ids=idString.split(",");
			musics=Arrays.copyOfRange(ids,0,10);
		}catch (Exception e){
			e.printStackTrace();
		}
		return musics;
	}

	private static MusicInfo getMusicInfo(String url){
		HttpClient client=new DefaultHttpClient();
		HttpGet get=new HttpGet(url);
		try{
			HttpResponse response=client.execute(get);
			String json=EntityUtils.toString(response.getEntity());
			System.out.println(json);
			return MusicInfo.parseFromJSON(json);
		}catch (IOException e){
			e.printStackTrace();
		}
		return  null;
	}

	private static void getMusic(String urlString,String name){
		File dest=new File(FILE_PATH_PREFIX+name);
		if(!dest.getParentFile().exists()){
			dest.getParentFile().mkdirs();
		}
		InputStream is=null;
		OutputStream os=null;
		try {
			URL url= new URL(urlString);
			URLConnection con=url.openConnection();
			is=con.getInputStream();
			os=new FileOutputStream(dest);
			IOUtils.copy(is, os);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
	}

	public static void main(String[] args)throws Exception{
//		MusicInfo musicInfo=getMusicInfo(MUSICINFO_URL+"45233415.json");
//		System.out.println(musicInfo);
//		String url=musicInfo.getUrl();
//		System.out.println(url);
//		getMusic(musicInfo.getUrl(),musicInfo.getId()+".m4a");
		FileInputStream inputStream=new FileInputStream("E:/spider/albumIds.txt");
		PrintStream ps=new PrintStream("E:/spider/musicInfo.xlsx");
		Scanner scanner=new Scanner(inputStream);
		HSSFWorkbook workbook= new HSSFWorkbook();
		HSSFSheet sheet=workbook.createSheet("sheet1");
		HSSFRow row=sheet.createRow(0);
		row.createCell(0).setCellValue("id");
		row.createCell(1).setCellValue("title");
		row.createCell(2).setCellValue("cover");
		row.createCell(3).setCellValue("url");
		row.createCell(4).setCellValue("createTime");
		row.createCell(5).setCellValue("playCount");
		row.createCell(6).setCellValue("introduction");
		row.createCell(7).setCellValue("duration");
		int n=1;
		while(scanner.hasNext()){
			String albumId=scanner.nextLine().trim();
			String[] ids=findMusicFromAlbum(albumId,10);
			row=sheet.createRow(n);
			row.createCell(0).setCellValue("专辑("+albumId+")");
			for(int i=0;i<ids.length;i++){
				if(ids[i]==null) continue;
				MusicInfo info=getMusicInfo(MUSICINFO_URL+ids[i]+".json");
				if(i==0) {
					row.createCell(1).setCellValue(info.getAlbumTitle());
					n++;
				}
				row=sheet.createRow(n);
				row.createCell(0).setCellValue(info.getId());
				row.createCell(1).setCellValue(info.getTitle());
				row.createCell(2).setCellValue(info.getCover());
				row.createCell(3).setCellValue(info.getUrl());
				row.createCell(4).setCellValue(info.getCreatTime());
				row.createCell(5).setCellValue(info.getPlayCount());
				row.createCell(6).setCellValue(info.getIntroduction());
				row.createCell(7).setCellValue(info.getDuration());
				n++;
			}
		}
		workbook.write(ps);
		scanner.close();
		ps.close();
	}
}
