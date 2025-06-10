package back;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class invoicepdfgenerator {

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD | Font.UNDERLINE);

	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);

	private static Font vsmallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font vvsmallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private static Font vsmallBoldUnder = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD | Font.UNDERLINE);
	ParameterController pamcont = new ParameterController();
	static DataTaker datataker = new DataTaker();
	static InvoicePrev invprev = new InvoicePrev();
	static InvoiceTaker invoicetaker = new InvoiceTaker();
	FileTaker ft = new FileTaker();

	public void generateInvoice(Invoice inv) {

		String[] infocomp = pamcont.GetInfo();
		String FILE = "M:\\invoice\\" + inv.getInvoiceNum() + ".pdf";
		try {

			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));

			document.open();

			addTitlePage(document, infocomp, inv);

			PdfContentByte cb = writer.getDirectContent();

			LineSeparator ls = new LineSeparator();

			Paragraph line = new Paragraph();
			line.add(ls);

			Paragraph header1 = new Paragraph(infocomp[0], catFont);
			Paragraph header2 = new Paragraph(infocomp[1], subFont);
			Paragraph header3 = new Paragraph(infocomp[2], subFont);
			Paragraph header4 = new Paragraph(infocomp[3], vsmallBold);
			Paragraph header5 = new Paragraph("Téléphone : " + infocomp[4], vsmallBold);
			Paragraph header6 = new Paragraph("Fax : " + infocomp[5], vsmallBold);
			Paragraph header7 = new Paragraph("Email : " + infocomp[6], vsmallBold);

			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, header1, document.leftMargin(), document.top() + 10, 0);
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, header2, document.leftMargin(), document.top() - 10, 0);
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, header3, document.leftMargin(), document.top() - 26, 0);
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, header4, document.leftMargin(), document.top() - 44, 0);
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, header5, document.leftMargin(), document.top() - 58, 0);
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, header6, document.leftMargin(), document.top() - 72, 0);
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, header7, document.leftMargin(), document.top() - 86, 0);

			Paragraph footer = new Paragraph(infocomp[8] + "- SIRET " + infocomp[7] + "- " + infocomp[9], vsmallBold);
			Paragraph footer2 = new Paragraph(infocomp[10], vsmallBold);
			Paragraph footer3 = new Paragraph(
					"Conformément aux artivles 441-6c. com. et D. 441-5 com., tout retard de paiement entraine de plein droit, outre les pénalités de retard,",
					vvsmallBold);
			Paragraph footer4 = new Paragraph(
					"une obligation pour le débiteur de payer une indemnité forfétaire de 40e pour frais de recouvrement. Une indemnité complémentaire",
					vvsmallBold);
			Paragraph footer5 = new Paragraph(
					"pourra être réclamée, sur justificatif, lorsque les frais de recouvrement exposés sont supérieurs au montant de l'indeminté forfétaire.",
					vvsmallBold);

			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, line,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() + 30, 0);

			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() + 18, 0);
			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer2,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() + 8, 0);
			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer3,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 2, 0);
			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer4,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer5,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 18, 0);

			document.close();

			ft.fileOpener(FILE);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addTitlePage(Document document, String[] infocomp, Invoice inv)
			throws DocumentException, IOException {

		PdfPTable table1 = new PdfPTable(2);
		table1.setWidthPercentage(100);
		float[] columnWidths1 = new float[] { 300f, 200f };
		table1.setWidths(columnWidths1);
		table1.setTotalWidth(PageSize.A4.getWidth() - PageSize.A4.getBorderWidth() * 2);
		table1.setHorizontalAlignment(Element.ALIGN_LEFT);

		table1.getDefaultCell().setBorder(0);

		table1.addCell(" ");
		table1.addCell(" ");

		table1.addCell(" ");
		table1.addCell(" ");

		table1.addCell(" ");
		table1.addCell(" ");

		table1.addCell(" ");
		table1.addCell(" ");

		table1.addCell(" ");
		table1.addCell(new Paragraph(inv.getInvoiceClientType() + " " + inv.getInvoiceClient(), subFont));

		table1.addCell(" ");

		table1.addCell(new Paragraph(inv.getInvoiceAddress(), vsmallBold));
		table1.addCell(" ");

		table1.addCell(new Paragraph(inv.getInvoiceCp() + " " + inv.getInvoiceCity(), vsmallBold));
		table1.addCell("");

		table1.addCell(new Paragraph(inv.getCountry(), vsmallBold));
		table1.addCell(" ");
		table1.addCell(" ");

		if (inv.getCountry() != "" && !inv.getCountry().equals("France")) {
			table1.addCell(new Paragraph("N° T.V.A intracommunautaire : " + inv.getTva(), vsmallBold));
			table1.addCell(" ");
		}

		document.add(table1);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);

		table.setWidths(columnWidths1);
		table.getDefaultCell().setBorder(0);
		table.setTotalWidth(PageSize.A4.getWidth() - PageSize.A4.getBorderWidth() * 2);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(" ");
		table.addCell(" ");
		// t.setBorderColor(BaseColor.GRAY);
		// t.setPadding(4);
		// t.setSpacing(4);
		// t.setBorderWidth(0);

		table.addCell(new Paragraph("T.V.A payée sur la marge", vsmallBoldUnder));

		table.addCell(new Paragraph("Groslay, le " + inv.getInvoicedatecreation(), vsmallBold));

		table.addCell(" ");
		table.addCell(" ");
		if (inv.getInvoiceStatus() != "Avoir")
			table.addCell(new Paragraph("FACTURE " + inv.getInvoiceNum(), catFont));
		else
			table.addCell(new Paragraph("AVOIR " + inv.getInvoiceNum(), catFont));
		String srtatus = " ";
		if (inv.getInvoiceStatus() != "Définitive")
			srtatus = inv.getInvoiceStatus();
		table.addCell(new Paragraph(srtatus, catFont));

		table.addCell(" ");
		table.addCell(" ");
		java.util.List<String[]> invoicelines = invoicetaker.getInvoicelinetotal(inv.getInvoiceid());
		if (inv.getInvoicevehiculeid() != 0 && invoicelines.get(0).length != 0) {

			table.addCell(new Paragraph("Véhicule vendu dans l'état où il se trouve", vsmallBold));
			table.addCell(" ");

			table.addCell(new Paragraph("MT " + inv.getInvoicevehiculeid(), vsmallBold));
			table.addCell(" ");
			table.addCell(" ");
			table.addCell(" ");

		}

		document.add(table);

		if (inv.getInvoicevehiculeid() != 0) {
			vehicule vehi = datataker.retrieveVehiWithId(inv.getInvoicevehiculeid());
			// add a table
			PdfPTable table2 = new PdfPTable(6);

			table2.setWidthPercentage(100);
			float[] columnWidths = new float[] { 80f, 100f, 100f, 80f, 100f, 80f };
			table2.setTotalWidth(columnWidths);

			table2.addCell("Marque");
			table2.addCell("Modèle");
			table2.addCell("Immatriculation");
			table2.addCell("Date de 1ere mise en circulation");
			table2.addCell("N° de série");
			table2.addCell("Prix");
			PdfPCell cellvehi = new PdfPCell(new Paragraph(vehi.getMarque()));
			cellvehi.setPaddingBottom(5);
			cellvehi.setPaddingTop(5);

			table2.addCell(cellvehi);

			cellvehi = new PdfPCell(new Paragraph(vehi.getType()));
			cellvehi.setPaddingBottom(10);
			cellvehi.setPaddingTop(5);

			table2.addCell(cellvehi);

			cellvehi = new PdfPCell(new Paragraph(vehi.getImmatriculation()));
			cellvehi.setPaddingBottom(10);
			cellvehi.setPaddingTop(5);
			table2.addCell(cellvehi);

			cellvehi = new PdfPCell(new Paragraph(vehi.getMiseEnCircul() + ""));
			cellvehi.setPaddingBottom(10);
			cellvehi.setPaddingTop(5);
			table2.addCell(cellvehi);

			cellvehi = new PdfPCell(new Paragraph(vehi.getNumeroSerie()));
			cellvehi.setPaddingBottom(10);
			cellvehi.setPaddingTop(5);
			table2.addCell(cellvehi);

			cellvehi = new PdfPCell(new Paragraph(String.valueOf(inv.getTotalttc()) + " €"));
			cellvehi.setPaddingBottom(10);
			cellvehi.setPaddingTop(5);

			table2.addCell(cellvehi);

			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			document.add(table2);

			PdfPTable table5 = new PdfPTable(1);
			table5.getDefaultCell().setBorder(0);
			table5.setHorizontalAlignment(Element.ALIGN_LEFT);
			table5.addCell(" ");
			table5.addCell(new Paragraph("Kilométrage (non garanti) inscrit au compteur : " + vehi.getKlms() + "Km",
					vsmallBold));
			table5.addCell(" ");
			document.add(table5);

		} else if (inv.getListvehi().length() != 0) {

			PdfPTable table2 = new PdfPTable(2);

			table2.setWidthPercentage(100);
			float[] columnWidths = new float[] { 400f, 100f };
			table2.setTotalWidth(columnWidths);

			table2.addCell("Libellé");
			table2.addCell("Prix (en €)");

			PdfPTable table3 = new PdfPTable(3);
			table3.getDefaultCell().setBorder(0);
			table3.addCell("Vente de ferraille");
			table3.addCell(" selon la liste ");
			table3.addCell("ci dessous ");

			ObservableList<vehicule> multiSelect = FXCollections.observableArrayList();
			ObservableList<Integer> listids = invprev.getIds(inv.getListvehi());

			for (int i = 0; i < listids.size(); i++) {
				multiSelect = datataker.getnewMulti(multiSelect, listids.get(i));
			}

			for (int i = 0; i < multiSelect.size(); i++) {

				table3.addCell(new Paragraph(multiSelect.get(i).getMarque(), vsmallBold));
				table3.addCell(new Paragraph(multiSelect.get(i).getType(), vsmallBold));
				table3.addCell(new Paragraph(multiSelect.get(i).getImmatriculation(), vsmallBold));

			}
			table2.addCell(table3);
			table2.addCell(String.valueOf(inv.getTotalttc()));

			table2.setHorizontalAlignment(Element.ALIGN_LEFT);

			document.add(table2);

		} else if (invoicelines.get(0).length != 0) {
			PdfPTable table2 = new PdfPTable(4);

			table2.setWidthPercentage(100);
			float[] columnWidths = new float[] { 300f, 100f, 800f, 80f };
			table2.setTotalWidth(columnWidths);

			table2.addCell("Libellé");
			table2.addCell("Quantité");
			table2.addCell("Prix unitaire");
			table2.addCell("Total (en €)");
			PdfPTable table6 = new PdfPTable(1);
			table6.getDefaultCell().setBorder(0);
			PdfPTable table7 = new PdfPTable(1);
			table7.getDefaultCell().setBorder(0);
			PdfPTable table8 = new PdfPTable(1);
			table8.getDefaultCell().setBorder(0);
			PdfPTable table9 = new PdfPTable(1);
			table9.getDefaultCell().setBorder(0);

			for (int i = 0; i < invoicelines.get(0).length; i++) {
				table6.addCell(invoicelines.get(1)[i]);
				table7.addCell(invoicelines.get(3)[i]);
				table8.addCell(invoicelines.get(4)[i]);
				table9.addCell((Float.valueOf(invoicelines.get(4)[i]) * Float.valueOf(invoicelines.get(3)[i])) + "");

			}
			table2.addCell(table6);
			table2.addCell(table7);
			table2.addCell(table8);
			table2.addCell(table9);

			PdfPCell cellOne = new PdfPCell(new Phrase(" "));
			cellOne.setBorder(Rectangle.NO_BORDER);
			// cellOne.setBackgroundColor(new Color(255,255,45));

			table2.addCell(cellOne);
			table2.addCell(cellOne);
			table2.addCell("Total :");
			table2.addCell(inv.getTotalttc() + " €");

			document.add(table2);

		}
		PdfPTable table4 = new PdfPTable(1);
		table4.getDefaultCell().setBorder(0);
		table4.setHorizontalAlignment(Element.ALIGN_LEFT);
		if (inv.getInvoicepaytype() != null)
			table4.addCell(new Paragraph(
					"Paiement : " + inv.getInvoicepaytype() + " du " + inv.getInvoicedatecreation(), vsmallBold));
		else
			table4.addCell(new Paragraph("Paiement dû au plus tard le  " + inv.getInvoicedatecreation()+45, vsmallBold));
		table4.addCell(new Paragraph(" ", vsmallBold));
		table4.addCell(new Paragraph("En application des articles 297 A et 297 C du C.G.I. ", vvsmallBold));
		table4.addCell(new Paragraph("directive communautaire 2006/112/CE Régime de la marge. ", vvsmallBold));
		table4.addCell(new Paragraph("La T.V.A sur les ventes d'occasion n'est pas mentionnée sur les factures. ",
				vvsmallBold));

		document.add(table4);

	}

}
