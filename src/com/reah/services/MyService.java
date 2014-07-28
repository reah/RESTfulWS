package com.reah.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class MyService {

	private static final String FILE_PATH = "/Users/reahmiyara/luna-workspace/RESTfulWS/src/tmp/test.pdf";

	@GET
	public String doGet() {
		return "Welcome to my RESTful WebService!";
	}

	@GET
	@Path("get-pdf")
	@Produces("application/pdf")
	public Response getFile() {
		File file = new File(FILE_PATH);
		if (!file.exists()) {
			return Response.status(500).entity("file not found").build();
		}
			// return Response.ok().entity(file).build();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			return Response.ok().entity(fis).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("take-pdf")
	@Consumes("application/pdf")
	@Produces("application/pdf")
	public Response takeFile(InputStream is) {
		File f = new File("src/tmp/fileMadeInTakeFileMethod.pdf");
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(f);
			int size = 0;
			byte[] bytes = new byte[1024];
			while ((size = is.read(bytes)) != -1) {
				outputStream.write(bytes, 0, size);
			}
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok().entity(f).build();
	}

	public static void main(String[] args) {
		MyService ms = new MyService();
//		try {
//			FileInputStream fis = new FileInputStream(fileSent);
//			FileOutputStream fos = new FileOutputStream(fileReturned);
//			Response r = ms.takeFile(fis);
//			InputStream returnedStream = (InputStream) r.getEntity();
//			int size = 0;
//			byte[] bytes = new byte[1024];
//			while ((size = returnedStream.read(bytes)) != -1) {
//				fos.write(bytes, 0, size);
//			}
//			fos.flush();
//			fos.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
