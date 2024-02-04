/**
 * 
 */
/**
 * 
 */
module HotelReservation {
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.base;
	requires java.sql;

	opens application to javafx.fxml,javafx.base;
	
	exports application to javafx.graphics,javafx.fxml;
}