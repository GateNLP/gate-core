package gate.resources.img.svg;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class has been automatically generated using <a
 * href="http://englishjavadrinker.blogspot.com/search/label/SVGRoundTrip">SVGRoundTrip</a>.
 */
@SuppressWarnings("unused")
public class LRIcon implements
		javax.swing.Icon {
	/**
	 * Paints the transcoded SVG image on the specified graphics context. You
	 * can install a custom transformation on the graphics context to scale the
	 * image.
	 * 
	 * @param g
	 *            Graphics context.
	 */
	public static void paint(Graphics2D g) {
        Shape shape = null;
        Paint paint = null;
        Stroke stroke = null;
        Area clip = null;
         
        float origAlpha = 1.0f;
        Composite origComposite = g.getComposite();
        if (origComposite instanceof AlphaComposite) {
            AlphaComposite origAlphaComposite = 
                (AlphaComposite)origComposite;
            if (origAlphaComposite.getRule() == AlphaComposite.SRC_OVER) {
                origAlpha = origAlphaComposite.getAlpha();
            }
        }
        
	    Shape clip_ = g.getClip();
AffineTransform defaultTransform_ = g.getTransform();
//  is CompositeGraphicsNode
float alpha__0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0 = g.getClip();
AffineTransform defaultTransform__0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
clip = new Area(g.getClip());
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,64.0,64.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0 is CompositeGraphicsNode
float alpha__0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(5.0524583, 32.240707);
((GeneralPath)shape).curveTo(6.915303, 33.431885, 8.859203, 34.66524, 10.491922, 36.186295);
((GeneralPath)shape).curveTo(11.886881, 37.471813, 11.994406, 38.833916, 11.997412, 40.595398);
((GeneralPath)shape).curveTo(11.995306, 41.720547, 11.978867, 42.84694, 12.016185, 43.9708);
((GeneralPath)shape).curveTo(12.213796, 45.153645, 13.122118, 45.69885, 14.221764, 45.878708);
((GeneralPath)shape).curveTo(16.242958, 46.054974, 17.266466, 45.22585, 15.829596, 45.9133);
((GeneralPath)shape).curveTo(17.077576, 44.824924, 18.141602, 43.62627, 18.347767, 41.958168);
((GeneralPath)shape).curveTo(18.381378, 41.242832, 18.36135, 40.526337, 18.353834, 39.81057);
((GeneralPath)shape).lineTo(20.0, 38.970966);
((GeneralPath)shape).curveTo(19.999294, 39.691784, 20.001446, 40.412712, 19.991322, 41.133446);
((GeneralPath)shape).curveTo(19.821568, 42.86491, 18.75874, 44.08438, 17.494589, 45.24347);
((GeneralPath)shape).curveTo(15.770958, 46.340492, 14.716709, 47.2323, 12.714776, 46.94589);
((GeneralPath)shape).curveTo(11.56239, 46.69183, 10.625208, 46.05591, 10.377526, 44.82971);
((GeneralPath)shape).curveTo(10.330151, 43.696896, 10.3691, 42.556915, 10.373747, 41.421356);
((GeneralPath)shape).curveTo(10.392895, 39.731834, 10.294959, 38.366486, 8.999476, 37.106094);
((GeneralPath)shape).curveTo(7.4310613, 35.584637, 5.4865184, 34.243847, 3.523226, 33.35176);
((GeneralPath)shape).lineTo(5.0524583, 32.240707);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.77273, 22.32101);
((GeneralPath)shape).curveTo(22.863638, 41.822727, 40.226547, 49.52101, 20.772728, 40.835297);
((GeneralPath)shape).curveTo(10.32207, 36.169304, 2.5, 34.50844, 2.5, 30.09244);
((GeneralPath)shape).curveTo(2.5, 25.67644, 10.272939, 21.472744, 19.68182, 14.092437);
((GeneralPath)shape).curveTo(40.95382, -2.5932791, 23.227276, 9.447866, 23.77273, 22.32101);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.1212239f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.77273, 22.32101);
((GeneralPath)shape).curveTo(22.863638, 41.822727, 40.226547, 49.52101, 20.772728, 40.835297);
((GeneralPath)shape).curveTo(10.32207, 36.169304, 2.5, 34.50844, 2.5, 30.09244);
((GeneralPath)shape).curveTo(2.5, 25.67644, 10.272939, 21.472744, 19.68182, 14.092437);
((GeneralPath)shape).curveTo(40.95382, -2.5932791, 23.227276, 9.447866, 23.77273, 22.32101);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(0.523635983467102f, 0.0f, -0.028865160420536995f, 1.6813650131225586f, 0.46086999773979187f, -20.541589736938477f));
// _0_0_2 is ShapeNode
paint = new Color(7, 4, 4, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2;
g.setTransform(defaultTransform__0_0_2);
g.setClip(clip__0_0_2);
float alpha__0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(0.523635983467102f, 0.0f, -0.028865160420536995f, 1.6813650131225586f, 0.7362800240516663f, -20.392009735107422f));
// _0_0_3 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(75.63566589355469, 27.636363983154297), new Point2D.Double(36.353511810302734, 27.636363983154297), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 0, 0, 255),new Color(255, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(78.909096, 27.636364);
((GeneralPath)shape).curveTo(78.909096, 34.665443, 71.46074, 40.36364, 62.272728, 40.36364);
((GeneralPath)shape).curveTo(53.084717, 40.36364, 45.636364, 34.665443, 45.636364, 27.636364);
((GeneralPath)shape).curveTo(45.636364, 20.607285, 53.084717, 14.909089, 62.272728, 14.909089);
((GeneralPath)shape).curveTo(71.46074, 14.909089, 78.909096, 20.607285, 78.909096, 27.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_3;
g.setTransform(defaultTransform__0_0_3);
g.setClip(clip__0_0_3);
float alpha__0_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4 is ShapeNode
paint = new Color(253, 253, 253, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(7.045453, 25.52101);
((GeneralPath)shape).curveTo(8.760065, 21.688988, 13.084932, 19.671581, 11.40909, 22.321009);
((GeneralPath)shape).curveTo(8.40909, 27.063866, 11.40909, 36.49244, 11.40909, 36.49244);
((GeneralPath)shape).lineTo(7.954544, 34.663868);
((GeneralPath)shape).curveTo(7.954544, 34.663868, 5.409089, 29.178152, 7.045453, 25.52101);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_4;
g.setTransform(defaultTransform__0_0_4);
g.setClip(clip__0_0_4);
float alpha__0_0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5 = g.getClip();
AffineTransform defaultTransform__0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_5 is ShapeNode
paint = new Color(253, 253, 253, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(13.030176, 21.696106);
((GeneralPath)shape).curveTo(14.744788, 17.864084, 19.796928, 14.246679, 18.121086, 16.896107);
((GeneralPath)shape).curveTo(15.121086, 21.638964, 12.232409, 24.628113, 17.211994, 33.810394);
((GeneralPath)shape).curveTo(22.106625, 42.836014, 13.939267, 37.01039, 13.939267, 37.01039);
((GeneralPath)shape).curveTo(13.939267, 37.01039, 11.393812, 25.353249, 13.030176, 21.696106);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
float alpha__0_0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6 is TextNode of 'L'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.959034, 32.0);
((GeneralPath)shape).lineTo(26.996792, 32.0);
((GeneralPath)shape).lineTo(26.996792, 16.97526);
((GeneralPath)shape).lineTo(29.617886, 16.97526);
((GeneralPath)shape).lineTo(29.617886, 29.378906);
((GeneralPath)shape).lineTo(39.959034, 29.378906);
((GeneralPath)shape).lineTo(39.959034, 32.0);
((GeneralPath)shape).closePath();
paint = new Color(255, 249, 249, 255);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_6;
g.setTransform(defaultTransform__0_0_6);
g.setClip(clip__0_0_6);
float alpha__0_0_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7 is TextNode of 'R'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(59.997997, 32.0);
((GeneralPath)shape).lineTo(56.13081, 32.0);
((GeneralPath)shape).lineTo(51.927032, 27.77474);
((GeneralPath)shape).lineTo(46.26948, 27.77474);
((GeneralPath)shape).lineTo(46.26948, 25.182291);
((GeneralPath)shape).lineTo(53.309193, 25.182291);
((GeneralPath)shape).quadTo(54.76297, 25.182291, 55.75125, 24.544922);
((GeneralPath)shape).quadTo(56.8756, 23.792969, 56.8756, 22.410807);
((GeneralPath)shape).quadTo(56.8756, 19.589193, 53.309193, 19.589193);
((GeneralPath)shape).lineTo(44.729767, 19.589193);
((GeneralPath)shape).lineTo(44.729767, 32.0);
((GeneralPath)shape).lineTo(42.115833, 32.0);
((GeneralPath)shape).lineTo(42.115833, 16.97526);
((GeneralPath)shape).lineTo(52.89383, 16.97526);
((GeneralPath)shape).quadTo(55.80138, 16.97526, 57.577423, 18.228516);
((GeneralPath)shape).quadTo(59.6256, 19.660807, 59.6256, 22.432291);
((GeneralPath)shape).quadTo(59.6256, 24.322916, 58.4368, 25.683594);
((GeneralPath)shape).quadTo(57.341095, 26.965494, 55.44331, 27.466797);
((GeneralPath)shape).lineTo(59.997997, 32.0);
((GeneralPath)shape).closePath();
paint = new Color(0, 0, 0, 255);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_7;
g.setTransform(defaultTransform__0_0_7);
g.setClip(clip__0_0_7);
origAlpha = alpha__0_0;
g.setTransform(defaultTransform__0_0);
g.setClip(clip__0_0);
origAlpha = alpha__0;
g.setTransform(defaultTransform__0);
g.setClip(clip__0);
g.setTransform(defaultTransform_);
g.setClip(clip_);

	}
	
	public Image getImage() {
		BufferedImage image =
            new BufferedImage(getIconWidth(), getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g = image.createGraphics();
    	paintIcon(null, g, 0, 0);
    	g.dispose();
    	return image;
	}

    /**
     * Returns the X of the bounding box of the original SVG image.
     * 
     * @return The X of the bounding box of the original SVG image.
     */
    public static int getOrigX() {
        return 2;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 0;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static int getOrigWidth() {
		return 64;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static int getOrigHeight() {
		return 64;
	}

	/**
	 * The current width of this resizable icon.
	 */
	int width;

	/**
	 * The current height of this resizable icon.
	 */
	int height;

	/**
	 * Creates a new transcoded SVG image.
	 */
	public LRIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public LRIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public LRIcon(int width, int height) {
	this.width = width;
	this.height = height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconHeight()
	 */
    @Override
	public int getIconHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconWidth()
	 */
    @Override
	public int getIconWidth() {
		return width;
	}

	public void setDimension(Dimension newDimension) {
		this.width = newDimension.width;
		this.height = newDimension.height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics,
	 * int, int)
	 */
    @Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.translate(x, y);
						
		Area clip = new Area(new Rectangle(0, 0, this.width, this.height));		
		if (g2d.getClip() != null) clip.intersect(new Area(g2d.getClip()));		
		g2d.setClip(clip);

		double coef1 = (double) this.width / (double) getOrigWidth();
		double coef2 = (double) this.height / (double) getOrigHeight();
		double coef = Math.min(coef1, coef2);
		g2d.scale(coef, coef);
		paint(g2d);
		g2d.dispose();
	}
}

