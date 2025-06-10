package back;

import java.sql.Date;

public class Invoice {
	public String invoiceNum;
public int invoiceid;
public String invoiceClient;
public String invoiceAddress;
public int invoiceCp;
public String invoiceCity;
public String invoicegar;
public String invoiceStatus;
public int invoicevehiculeid;
public String invoicepaytype;
public String invoiceText;
public String invoiceClientType;
public Date invoicedatecreation;
public float totalttc;
public String listvehi;
public String country;
public String tva;




public String getTva() {
	return tva;
}
public void setTva(String tva) {
	this.tva = tva;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getInvoiceNum() {
	return invoiceNum;
}
public void setInvoiceNum(String invoiceNum) {
	this.invoiceNum = invoiceNum;
}
public float getTotalttc() {
	return totalttc;
}
public void setTotalttc(float totalttc) {
	this.totalttc = totalttc;
}
public Date getInvoicedatecreation() {
	return invoicedatecreation;
}
public void setInvoicedatecreation(Date invoicedatecreation) {
	this.invoicedatecreation = invoicedatecreation;
}
public int getInvoiceid() {
	return invoiceid;
}
public void setInvoiceid(int invoiceid) {
	this.invoiceid = invoiceid;
}
public String getInvoiceClient() {
	return invoiceClient;
}
public void setInvoiceClient(String invoiceClient) {
	this.invoiceClient = invoiceClient;
}
public String getInvoiceAddress() {
	return invoiceAddress;
}
public void setInvoiceAddress(String invoiceAddress) {
	this.invoiceAddress = invoiceAddress;
}
public int getInvoiceCp() {
	return invoiceCp;
}
public void setInvoiceCp(int invoiceCp) {
	this.invoiceCp = invoiceCp;
}
public String getInvoiceCity() {
	return invoiceCity;
}
public void setInvoiceCity(String invoiceCity) {
	this.invoiceCity = invoiceCity;
}
public String getInvoicegar() {
	return invoicegar;
}
public void setInvoicegar(String invoicegar) {
	this.invoicegar = invoicegar;
}
public String getInvoiceStatus() {
	return invoiceStatus;
}
public void setInvoiceStatus(String invoiceStatus) {
	this.invoiceStatus = invoiceStatus;
}
public int getInvoicevehiculeid() {
	return invoicevehiculeid;
}
public void setInvoicevehiculeid(int invoicevehiculeid) {
	this.invoicevehiculeid = invoicevehiculeid;
}
public String getInvoicepaytype() {
	return invoicepaytype;
}
public void setInvoicepaytype(String invoicepaytype) {
	this.invoicepaytype = invoicepaytype;
}
public String getInvoiceText() {
	return invoiceText;
}
public void setInvoiceText(String invoiceText) {
	this.invoiceText = invoiceText;
}
public String getInvoiceClientType() {
	return invoiceClientType;
}
public void setInvoiceClientType(String invoiceClientType) {
	this.invoiceClientType = invoiceClientType;
}

public String getListvehi() {
	return listvehi;
}
public void setListvehi(String listvehi) {
	this.listvehi = listvehi;
}

public Invoice(String invoiceNum, int invoiceid, String invoiceClient, String invoiceAddress, int invoiceCp,
		String invoiceCity, String invoicegar, String invoiceStatus, int invoicevehiculeid, String invoicepaytype,
		String invoiceText, String invoiceClientType, Date invoicedatecreation, float totalttc, String listvehi, String country, String tva) {
	super();
	this.invoiceNum = invoiceNum;
	this.invoiceid = invoiceid;
	this.invoiceClient = invoiceClient;
	this.invoiceAddress = invoiceAddress;
	this.invoiceCp = invoiceCp;
	this.invoiceCity = invoiceCity;
	this.invoicegar = invoicegar;
	this.invoiceStatus = invoiceStatus;
	this.invoicevehiculeid = invoicevehiculeid;
	this.invoicepaytype = invoicepaytype;
	this.invoiceText = invoiceText;
	this.invoiceClientType = invoiceClientType;
	this.invoicedatecreation = invoicedatecreation;
	this.totalttc = totalttc;
	this.listvehi = listvehi;
	this.country = country;
	this.tva=tva;
}
public Invoice(int invoiceid, String invoiceClient, String invoiceAddress, int invoiceCp, String invoiceCity,
		String invoicegar, String invoiceStatus, int invoicevehiculeid, String invoicepaytype, String invoiceText,
		String invoiceClientType, float totalttc, String invoiceNum) {
	super();
	this.invoiceid = invoiceid;
	this.invoiceClient = invoiceClient;
	this.invoiceAddress = invoiceAddress;
	this.invoiceCp = invoiceCp;
	this.invoiceCity = invoiceCity;
	this.invoicegar = invoicegar;
	this.invoiceStatus = invoiceStatus;
	this.invoicevehiculeid = invoicevehiculeid;
	this.invoicepaytype = invoicepaytype;
	this.invoiceText = invoiceText;
	this.invoiceClientType = invoiceClientType;
	this.totalttc=totalttc;
	this.invoiceNum=invoiceNum;
}
public Invoice(String invoiceNum, int invoiceid, String invoiceClient, String invoiceAddress, int invoiceCp,
		String invoiceCity, String invoicegar, String invoiceStatus, int invoicevehiculeid, String invoicepaytype,
		String invoiceText, String invoiceClientType, Date invoicedatecreation, float totalttc) {
	super();
	this.invoiceNum = invoiceNum;
	this.invoiceid = invoiceid;
	this.invoiceClient = invoiceClient;
	this.invoiceAddress = invoiceAddress;
	this.invoiceCp = invoiceCp;
	this.invoiceCity = invoiceCity;
	this.invoicegar = invoicegar;
	this.invoiceStatus = invoiceStatus;
	this.invoicevehiculeid = invoicevehiculeid;
	this.invoicepaytype = invoicepaytype;
	this.invoiceText = invoiceText;
	this.invoiceClientType = invoiceClientType;
	this.invoicedatecreation = invoicedatecreation;
	this.totalttc = totalttc;
}

}
