package kamisado_util;

import java.net.*;
import java.io.*;

public class MultiplayerClient {
	//"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySUQiOiI1OGI0YTllNmFlYjExZjRhMjhiZmRiNDYiLCJuaWNrbmFtZSI6ImFkbWluIiwiaWF0IjoxNDg4MjQ2NTk0LCJleHAiOjE0ODgzMzI5OTR9.qos2jr-dz93emr__dG4iygg_qTXu3UMM0VHfAaN42WM"
	public static void main(String[] args) {
		try {

			URL url = new URL("http://localhost:3000/games/58b4c47bd3481c5aa96820ab");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySUQiOiI1OGI0YTllNmFlYjExZjRhMjhiZmRiNDYiLCJuaWNrbmFtZSI6ImFkbWluIiwiaWF0IjoxNDg4MjQ2NTk0LCJleHAiOjE0ODgzMzI5OTR9.qos2jr-dz93emr__dG4iygg_qTXu3UMM0VHfAaN42WM");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		  

		}
	
	void chuj(){
		try {

			URL url = new URL("http://localhost:3000/auth");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			String s = "{\"email\": \"admin@admin.com\",\"password\": \"admin\"}";
			
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			
			OutputStream os = conn.getOutputStream();
	        os.write(s.getBytes());
	        os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
	}
}
