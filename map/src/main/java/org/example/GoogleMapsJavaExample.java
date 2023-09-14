package org.example;

import com.google.maps.model.LatLng;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class GoogleMapsJavaExample {
    private static int zoom = 12; // Default zoom level

    public static void main(String[] args) {
        System.out.println("Fetching and displaying Google Map...");

        JFrame frame = new JFrame("Google Maps Example");
        frame.setSize(1366, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel controlPanel = new JPanel();
        JSlider zoomSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, zoom);
        zoomSlider.setMajorTickSpacing(5);
        zoomSlider.setMinorTickSpacing(1);
        zoomSlider.setPaintTicks(true);
        zoomSlider.setPaintLabels(true);

        zoomSlider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                zoom = source.getValue();
                refreshMapImage(frame);
            }
        });

        controlPanel.add(zoomSlider);

        frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);

        try {
            BufferedImage mapImage = getGoogleMapImage();
            JLabel mapLabel = new JLabel(new ImageIcon(mapImage));

            frame.getContentPane().add(mapLabel, BorderLayout.CENTER);
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage getGoogleMapImage() throws IOException {
        LatLng location = new LatLng(6.9271, 79.8612); // coordinates of Sri Lanka
        int width = 1366;
        int height = 768;
        String apiKey = "?";

        String imageUrl = constructImageUrl(location, zoom, width, height, apiKey);

        try {
            URL imageUrlObj = new URL(imageUrl);
            System.out.println("Map image fetched successfully!");
            return ImageIO.read(imageUrlObj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String constructImageUrl(LatLng location, int zoom, int width, int height, String apiKey) {
        return "https://maps.googleapis.com/maps/api/staticmap?"
                + "center=" + location.lat + "," + location.lng
                + "&zoom=" + zoom
                + "&size=" + width + "x" + height
                + "&key=" + apiKey;
    }

    private static void refreshMapImage(JFrame frame) {
        try {
            BufferedImage mapImage = getGoogleMapImage();
            JLabel mapLabel = new JLabel(new ImageIcon(mapImage));
            frame.getContentPane().remove(1); // Remove the previous map label
            frame.getContentPane().add(mapLabel, BorderLayout.CENTER); // Add the updated map label
            frame.validate();
            frame.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
