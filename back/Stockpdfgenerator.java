package back;

import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.scene.control.Cell;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

public class Stockpdfgenerator {
	private static String FILE = "C:\\Users\\valsm\\OneDrive\\Bureau\\CarShow.pdf";
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font vsmallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	FileTaker ft = new FileTaker();
	static DataTaker dt = new DataTaker();

	public void GenerateStockpdf(TableView stock) {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(FILE));

			document.open();

			addTitlePage(document, stock);

			document.close();

			ft.fileOpener(FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addTitlePage(Document document, TableView stock) throws DocumentException {

		List<vehicule> stocklist = stock.getItems();

		PdfPTable table = new PdfPTable(3);

		PdfPCell c1 = new PdfPCell(new Phrase("Marque"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("type"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Immat"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		table.setHeaderRows(1);

		for (int i = 0; i < stocklist.size(); i++) {
			if (dt.getBurnt(stocklist.get(i).getId())) {
				PdfPCell cell = new PdfPCell(new Phrase(stocklist.get(i).getMarque()));
				cell.setBackgroundColor(BaseColor.RED);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(stocklist.get(i).getType()));
				cell.setBackgroundColor(BaseColor.RED);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(stocklist.get(i).getImmatriculation()));
				cell.setBackgroundColor(BaseColor.RED);
				table.addCell(cell);

			} else if (stocklist.get(i).getHascg() == 1) {
				PdfPCell cell = new PdfPCell(new Phrase(stocklist.get(i).getMarque()));
				cell.setBackgroundColor(BaseColor.GREEN);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(stocklist.get(i).getType()));
				cell.setBackgroundColor(BaseColor.GREEN);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(stocklist.get(i).getImmatriculation()));
				cell.setBackgroundColor(BaseColor.GREEN);
				table.addCell(cell);

			} else {
				table.addCell(stocklist.get(i).getMarque());
				table.addCell(stocklist.get(i).getType());
				table.addCell(stocklist.get(i).getImmatriculation());
			}

		}

		document.add(table);
		document.close();
	}
}
