package back;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class PicInput {
	String BDD = "geode_bd";
	String url = "jdbc:mysql://192.168.1.85:3306/" + BDD;
	String user = "username";
	String passwd = "password";
	Image image;
	public void InputBlob(String img, int id) {
		
		String query2 = "DELETE FROM `geode_bd`.`pics` WHERE (`idvehi` = '"+id+"')";
		String query = "INSERT INTO `geode_bd`.`pics` (`idvehi`, `picscol`) " + "VALUES ( '" + id + "','" +  img + "')";

		Connection conn;
		try {
			conn = DriverManager.getConnection(url, user, passwd);
			Statement stmt = conn.createStatement();
			stmt.execute(query2);
			stmt.execute(query);

			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Image GetBlob(int id) {
		String query = "SELECT * FROM `geode_bd`.`pics` WHERE idvehi = '" + id + "'";
		BufferedImage img = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB_PRE);
		int checkrs = 0;
		Connection conn;
		try {
			conn = DriverManager.getConnection(url, user, passwd);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				System.out.println(1);
				//InputStream binaryStream = rs.getBinaryStream("picscol");
				System.out.println(rs.getString(3)); 
				FileInputStream fis;
				try {
					fis = new FileInputStream(rs.getString(3).replace("\\","\\\\" ));
					 image = new Image( fis);
					 checkrs =1;
					 System.out.println(2);
					 return image;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (checkrs !=1) {
			
			image = new Image("/entrer.png");
		}
			
		System.out.println(3);
		return null;
	}
}
