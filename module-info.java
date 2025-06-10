module com.geodeApp {

	requires itextpdf;
	requires java.base;
	requires java.sql;
	requires mysql.connector.j;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.swing;
	requires javafx.graphics;
	
	
	exports front to  javafx.graphics;
	exports back;
}
