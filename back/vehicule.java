package back;

import java.sql.Date;

public class vehicule {
private int id;
private String marque;
private String type;
private String carrosserie;
private String numeroSerie;
private String energie;
private int puissance;
private int klms;
private Date miseEnCircul;
private String immatriculation;
private String couleur;
private String destination;
private int hascg;

public int getHascg() {
	return hascg;
}
public void setHascg(int hascg) {
	this.hascg = hascg;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCarrosserie() {
	return carrosserie;
}
public void setCarrosserie(String carrosserie) {
	this.carrosserie = carrosserie;
}
public String getNumeroSerie() {
	return numeroSerie;
}
public void setNumeroSerie(String numeroSerie) {
	this.numeroSerie = numeroSerie;
}
public String getEnergie() {
	return energie;
}
public void setEnergie(String energie) {
	this.energie = energie;
}


public int getPuissance() {
	return puissance;
}
public void setPuissance(int puissance) {
	this.puissance = puissance;
}
public int getKlms() {
	return klms;
}
public void setKlms(int klms) {
	this.klms = klms;
}
public Date getMiseEnCircul() {
	return miseEnCircul;
}
public void setMiseEnCircul(Date miseEnCircul) {
	this.miseEnCircul = miseEnCircul;
}
public String getCouleur() {
	return couleur;
}
public void setCouleur(String couleur) {
	this.couleur = couleur;
}
public Date getCertifActuel() {
	return certifActuel;
}
public void setCertifActuel(Date certifActuel) {
	this.certifActuel = certifActuel;
}
public Date getDateEntree() {
	return dateEntree;
}
public void setDateEntree(Date dateEntree) {
	this.dateEntree = dateEntree;
}
public Date getDateSortie() {
	return dateSortie;
}
public void setDateSortie(Date dateSortie) {
	this.dateSortie = dateSortie;
}
private Date certifActuel;
private Date dateEntree;
public Date dateSortie;

public String getDestination() {
	return destination;
}
public void setDestination(String destination) {
	this.destination = destination;
}
public vehicule() {
}
public vehicule(String marque, String type, String immatriculation) {
	super();
	this.marque = marque;
	this.type = type;
	this.immatriculation = immatriculation;
}
public String getMarque() {
	return marque;
}
public void setMarque(String marque) {
	this.marque = marque;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getImmatriculation() {
	return immatriculation;
}
public void setImmatriculation(String immatriculation) {
	this.immatriculation = immatriculation;
}
public vehicule(String marque, String type, String carrosserie, String numeroSerie, String energie, int puissance,
		int klms, Date miseEnCircul, String immatriculation, String couleur, String destination, Date certifActuel,
		Date dateEntree, Date dateSortie, int id) {
	super();
	this.marque = marque;
	this.type = type;
	this.carrosserie = carrosserie;
	this.numeroSerie = numeroSerie;
	this.energie = energie;
	this.puissance = puissance;
	this.klms = klms;
	this.miseEnCircul = miseEnCircul;
	this.immatriculation = immatriculation;
	this.couleur = couleur;
	this.destination = destination;
	this.certifActuel = certifActuel;
	this.dateEntree = dateEntree;
	this.dateSortie = dateSortie;
	this.id=id;
}
public vehicule(String marque, String type, String carrosserie, String numeroSerie, String energie, int puissance,
		int klms, Date miseEnCircul, String immatriculation, String couleur, String destination, Date certifActuel,
		Date dateEntree, Date dateSortie, int id, int hascg) {
	super();
	this.marque = marque;
	this.type = type;
	this.carrosserie = carrosserie;
	this.numeroSerie = numeroSerie;
	this.energie = energie;
	this.puissance = puissance;
	this.klms = klms;
	this.miseEnCircul = miseEnCircul;
	this.immatriculation = immatriculation;
	this.couleur = couleur;
	this.destination = destination;
	this.certifActuel = certifActuel;
	this.dateEntree = dateEntree;
	this.dateSortie = dateSortie;
	this.id=id;
	this.hascg=hascg;
}


//public vehicule(String marque, String type, String carrosserie, String numeroSerie, String energie, String puissance,
//		String klms, String miseEnCircul, String immatriculation, String couleur, String cF, String certifActuel,
//		String dateEntree, String dateSortie) {
//	super();
//	this.marque = marque;
//	this.type = type;
//	this.carrosserie = carrosserie;
//	this.numeroSerie = numeroSerie;
//	this.energie = energie;
//	this.puissance = puissance;
//	this.klms = klms;
//	this.miseEnCircul = miseEnCircul;
//	this.immatriculation = immatriculation;
//	this.couleur = couleur;
//	CF = cF;
//	this.certifActuel = certifActuel;
//	this.dateEntree = dateEntree;
//	this.dateSortie = dateSortie;
//}


}
