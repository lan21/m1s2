package rest.model;

import java.text.SimpleDateFormat;

import org.apache.commons.net.ftp.FTPFile;

public class ItemBuilder {

	public String buildList(String canonicalPath, FTPFile[] files,
			FTPRestServiceConfiguration configuration) {
		final StringBuilder menu = new StringBuilder();
		menu.append("<h1>Index of " + canonicalPath + "</h1>");
		menu.append("<pre><img src=\"/static/blank.gif\" alt=\"Icon\">");
		menu.append(" <a href=\"?C=N;O=D\">Name</a>");
		menu.append("                                        <a href= \"?C=M;O=A\">Last modified</a>");
		menu.append("            <a href=\"?C=S;O=A\">Size</a>");
		menu.append("      <a href=\"?C=D;O=A\">Description</a>");
		menu.append("<hr>");
		menu.append("<img src=\"/static/back.gif\" alt=\"[DIR]\"> <a href=\"..\">Parent Directory</a><br/>");
		for (final FTPFile file : files) {
			menu.append(buildItem(file, configuration));
			menu.append("<br>");
		}
		menu.append("<hr>");
		menu.append("<br><br>");
		menu.append(createUploadForm(canonicalPath));
		menu.append("</pre>");
		return menu.toString();
	}

	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit)
			return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1)
				+ (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	private String buildItem(FTPFile file,
			FTPRestServiceConfiguration configuration) {
		String itemPath = file.getName();
		if (file.isDirectory()) {
			itemPath += configuration.getDirectorySeparator();
		}
		final StringBuilder builder = new StringBuilder();
		final String icon = (file.isDirectory()) ? "folder.gif" : "text.gif";
		builder.append("<img src=\"/static/" + icon + "\" alt=\"[TXT]\">");
		builder.append(" <a href=\"" + itemPath + "\">" + file.getName()
				+ "</a>                    ");
		final SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm");
		String formattedDate = formatter.format(file.getTimestamp().getTime());
		for (int i = 0; i < 24 - file.getName().length(); i++) {
			builder.append(" ");
		}
		builder.append(formattedDate);
		builder.append("         "
				+ humanReadableByteCount(file.getSize(), true));
		return builder.toString();
	}
	
	public String createUploadForm(String pathname) {
		String uploadform="<form action =\"";
		uploadform += pathname;
		uploadform += "\" method=\"post\" enctype=\"multipart/form-data\">";
		uploadform += "<input type=\"file\" name=\"file\"/>";
		uploadform += "<input type=\"submit\" value=\"Upload file\" />";
		uploadform += "</form>";
		return uploadform;
	}
}
