package com.packt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.shape.*;
import javafx.scene.effect.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import java.io.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import java.util.Iterator;
import javax.imageio.plugins.tiff.*;
import javax.imageio.metadata.*;

public class TiffReaderDemo {
	
	public static void main(String[] args) {

		Iterator iterator = ImageIO.getImageReadersByFormatName("tiff");
		ImageReader reader = (ImageReader) iterator.next();

		try(ImageInputStream is = new FileImageInputStream(new File("sample.tif"))) {
			reader.setInput(is, false, true);
			System.out.println("Number of Images: " + reader.getNumImages(true));
			System.out.println("Height: " + reader.getHeight(0));
			System.out.println("Width: " + reader.getWidth(0));
			System.out.println(reader.getFormatName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}