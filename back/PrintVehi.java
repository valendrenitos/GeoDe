package back;

import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import javafx.scene.image.Image;

public class PrintVehi {
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD | Font.UNDERLINE);

	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);

	private static Font vsmallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font vvsmallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private static Font vsmallBoldUnder = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	ParameterController pamcont = new ParameterController();
	static DataTaker datataker = new DataTaker();
	static InvoicePrev invprev = new InvoicePrev();
	static InvoiceTaker invoicetaker = new InvoiceTaker();
	FileTaker ft = new FileTaker();

	public void createVehiPrint(vehicule vehi) {

		String FILE = "C:\\Users\\valsm\\OneDrive\\Bureau\\FicheVehicule.pdf";
		List<String> pics = ft.Retrievepic(vehi.getId());
		try {

			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));

			document.open();

			PdfContentByte cb = writer.getDirectContent();

			Paragraph header1 = new Paragraph("Marque : " + vehi.getMarque(), catFont);
			Paragraph header2 = new Paragraph("Modèle : " + vehi.getType(), catFont);
			Paragraph header3 = new Paragraph("Immatriculation : " + vehi.getImmatriculation(), subFont);
			Paragraph header4 = new Paragraph("Numero de Série : " + vehi.getNumeroSerie(), vsmallBold);
			Paragraph header5 = new Paragraph("Couleur: " + vehi.getCouleur(), vsmallBold);
			Paragraph header6 = new Paragraph("Kilométrage : " + vehi.getKlms(), vsmallBold);

			document.add(header1);
			document.add(header2);
			document.add(header3);
			document.add(header4);
			document.add(header5);
			document.add(header6);
			
			


			document.close();

			ft.fileOpener(FILE);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
