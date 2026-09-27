package com.azkh.blackbox.ui.util;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.XmlReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VectorDrawable {

    private static class Point {
        final float x, y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public static Texture createTextureFromFile(FileHandle file, int width, int height) {
        Pixmap pixmap = createPixmapFromFile(file, width, height);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    public static Pixmap createPixmapFromFile(FileHandle file, int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();

        try {
            XmlReader reader = new XmlReader();
            XmlReader.Element root = reader.parse(file);

            float viewportWidth = root.getFloatAttribute("android:viewportWidth", root.getFloatAttribute("viewportWidth", 16f));
            float viewportHeight = root.getFloatAttribute("android:viewportHeight", root.getFloatAttribute("viewportHeight", 16f));

            float scaleX = width / viewportWidth;
            float scaleY = height / viewportHeight;

            for (XmlReader.Element child : root.getChildrenByName("path")) {
                String colorStr = child.getAttribute("android:fillColor", child.getAttribute("fillColor", "#000000"));
                String pathData = child.getAttribute("android:pathData", child.getAttribute("pathData", ""));

                Color color = parseColor(colorStr);
                renderPath(pixmap, pathData, color, scaleX, scaleY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pixmap;
    }

    private static Color parseColor(String hex) {
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }
        if (hex.length() == 6) {
            float r = Integer.parseInt(hex.substring(0, 2), 16) / 255f;
            float g = Integer.parseInt(hex.substring(2, 4), 16) / 255f;
            float b = Integer.parseInt(hex.substring(4, 6), 16) / 255f;
            return new Color(r, g, b, 1f);
        } else if (hex.length() == 8) {
            float a = Integer.parseInt(hex.substring(0, 2), 16) / 255f;
            float r = Integer.parseInt(hex.substring(2, 4), 16) / 255f;
            float g = Integer.parseInt(hex.substring(4, 6), 16) / 255f;
            float b = Integer.parseInt(hex.substring(6, 8), 16) / 255f;
            return new Color(r, g, b, a);
        }
        return Color.BLACK;
    }

    private static void renderPath(Pixmap pixmap, String pathData, Color color, float scaleX, float scaleY) {
        List<List<Point>> subpaths = parsePathData(pathData);
        for (List<Point> subpath : subpaths) {
            if (subpath.size() < 3) continue;
            List<Point> scaled = new ArrayList<>(subpath.size());
            for (Point p : subpath) {
                scaled.add(new Point(p.x * scaleX, p.y * scaleY));
            }
            fillPolygon(pixmap, scaled, color);
        }
    }

    private static List<List<Point>> parsePathData(String pathData) {
        List<List<Point>> subpaths = new ArrayList<>();
        List<Point> currentSubpath = new ArrayList<>();

        PathTokenizer tokenizer = new PathTokenizer(pathData);
        float cx = 0, cy = 0;
        char cmd = 0;

        while (tokenizer.hasNext()) {
            if (tokenizer.isCommandNext()) {
                cmd = tokenizer.nextCommand();
            }

            switch (cmd) {
                case 'M':
                    cx = tokenizer.nextFloat();
                    cy = tokenizer.nextFloat();
                    currentSubpath = new ArrayList<>();
                    currentSubpath.add(new Point(cx, cy));
                    subpaths.add(currentSubpath);
                    break;
                case 'm':
                    cx += tokenizer.nextFloat();
                    cy += tokenizer.nextFloat();
                    currentSubpath = new ArrayList<>();
                    currentSubpath.add(new Point(cx, cy));
                    subpaths.add(currentSubpath);
                    break;
                case 'L':
                    cx = tokenizer.nextFloat();
                    cy = tokenizer.nextFloat();
                    currentSubpath.add(new Point(cx, cy));
                    break;
                case 'l':
                    cx += tokenizer.nextFloat();
                    cy += tokenizer.nextFloat();
                    currentSubpath.add(new Point(cx, cy));
                    break;
                case 'H':
                    cx = tokenizer.nextFloat();
                    currentSubpath.add(new Point(cx, cy));
                    break;
                case 'h':
                    cx += tokenizer.nextFloat();
                    currentSubpath.add(new Point(cx, cy));
                    break;
                case 'V':
                    cy = tokenizer.nextFloat();
                    currentSubpath.add(new Point(cx, cy));
                    break;
                case 'v':
                    cy += tokenizer.nextFloat();
                    currentSubpath.add(new Point(cx, cy));
                    break;
                case 'C': {
                    float x1 = tokenizer.nextFloat();
                    float y1 = tokenizer.nextFloat();
                    float x2 = tokenizer.nextFloat();
                    float y2 = tokenizer.nextFloat();
                    float x3 = tokenizer.nextFloat();
                    float y3 = tokenizer.nextFloat();
                    addCubicBezier(currentSubpath, cx, cy, x1, y1, x2, y2, x3, y3);
                    cx = x3;
                    cy = y3;
                    break;
                }
                case 'c': {
                    float x1 = cx + tokenizer.nextFloat();
                    float y1 = cy + tokenizer.nextFloat();
                    float x2 = cx + tokenizer.nextFloat();
                    float y2 = cy + tokenizer.nextFloat();
                    float x3 = cx + tokenizer.nextFloat();
                    float y3 = cy + tokenizer.nextFloat();
                    addCubicBezier(currentSubpath, cx, cy, x1, y1, x2, y2, x3, y3);
                    cx = x3;
                    cy = y3;
                    break;
                }
                case 'Z':
                case 'z':
                    if (!currentSubpath.isEmpty()) {
                        Point p0 = currentSubpath.get(0);
                        if (Math.abs(cx - p0.x) > 0.001f || Math.abs(cy - p0.y) > 0.001f) {
                            currentSubpath.add(new Point(p0.x, p0.y));
                        }
                        cx = p0.x;
                        cy = p0.y;
                    }
                    break;
            }
        }
        return subpaths;
    }

    private static void addCubicBezier(List<Point> subpath, float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3) {
        int steps = 16;
        for (int i = 1; i <= steps; i++) {
            float t = i / (float) steps;
            float u = 1 - t;
            float tt = t * t;
            float uu = u * u;
            float uuu = uu * u;
            float ttt = tt * t;

            float x = uuu * x0 + 3 * uu * t * x1 + 3 * u * tt * x2 + ttt * x3;
            float y = uuu * y0 + 3 * uu * t * y1 + 3 * u * tt * y2 + ttt * y3;
            subpath.add(new Point(x, y));
        }
    }

    private static void fillPolygon(Pixmap pixmap, List<Point> pts, Color color) {
        if (pts.size() < 3) return;

        int intColor = Color.rgba8888(color);

        float minY = Float.MAX_VALUE;
        float maxY = -Float.MAX_VALUE;
        for (Point p : pts) {
            if (p.y < minY) minY = p.y;
            if (p.y > maxY) maxY = p.y;
        }

        int startY = Math.max(0, (int) Math.floor(minY));
        int endY = Math.min(pixmap.getHeight() - 1, (int) Math.ceil(maxY));

        int numPts = pts.size();

        for (int y = startY; y <= endY; y++) {
            float scanY = y + 0.5f;
            List<Float> intersections = new ArrayList<>();

            for (int i = 0; i < numPts; i++) {
                Point p1 = pts.get(i);
                Point p2 = pts.get((i + 1) % numPts);

                if ((p1.y <= scanY && p2.y > scanY) || (p2.y <= scanY && p1.y > scanY)) {
                    float t = (scanY - p1.y) / (p2.y - p1.y);
                    float x = p1.x + t * (p2.x - p1.x);
                    intersections.add(x);
                }
            }

            Collections.sort(intersections);

            for (int i = 0; i + 1 < intersections.size(); i += 2) {
                int xStart = Math.max(0, (int) Math.ceil(intersections.get(i)));
                int xEnd = Math.min(pixmap.getWidth() - 1, (int) Math.floor(intersections.get(i + 1)));

                for (int x = xStart; x <= xEnd; x++) {
                    pixmap.drawPixel(x, y, intColor);
                }
            }
        }
    }

    private static class PathTokenizer {
        private final String str;
        private int index = 0;

        PathTokenizer(String str) {
            this.str = str;
        }

        boolean hasNext() {
            skipWhitespace();
            return index < str.length();
        }

        char nextCommand() {
            skipWhitespace();
            if (index < str.length() && Character.isLetter(str.charAt(index))) {
                return str.charAt(index++);
            }
            return 0;
        }

        boolean isCommandNext() {
            skipWhitespace();
            return index < str.length() && Character.isLetter(str.charAt(index));
        }

        float nextFloat() {
            skipWhitespace();
            int start = index;
            if (index < str.length() && (str.charAt(index) == '-' || str.charAt(index) == '+')) {
                index++;
            }
            boolean hasDot = false;
            while (index < str.length()) {
                char c = str.charAt(index);
                if (Character.isDigit(c)) {
                    index++;
                } else if (c == '.' && !hasDot) {
                    hasDot = true;
                    index++;
                } else {
                    break;
                }
            }
            if (start == index) return 0f;
            String valStr = str.substring(start, index);
            try {
                return Float.parseFloat(valStr);
            } catch (NumberFormatException e) {
                return 0f;
            }
        }

        private void skipWhitespace() {
            while (index < str.length()) {
                char c = str.charAt(index);
                if (c == ' ' || c == ',' || c == '\t' || c == '\n' || c == '\r') {
                    index++;
                } else {
                    break;
                }
            }
        }
    }
}
