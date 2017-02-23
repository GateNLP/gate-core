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
public class InvalidIcon implements
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
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,48.0,48.0)));
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
g.transform(new AffineTransform(1.5666669607162476f, 0.0f, 0.0f, 1.5666669607162476f, -8.925565719604492f, -23.94763946533203f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(0.625f, 0.0f, 0.0f, 0.625f, 6.011172771453857f, 14.009129524230957f));
// _0_0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0_0 is ShapeNode
origAlpha = alpha__0_0_0_0_0;
g.setTransform(defaultTransform__0_0_0_0_0);
g.setClip(clip__0_0_0_0_0);
origAlpha = alpha__0_0_0_0;
g.setTransform(defaultTransform__0_0_0_0);
g.setClip(clip__0_0_0_0);
float alpha__0_0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1 is ShapeNode
paint = new Color(204, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(33.28278, 38.644745);
((GeneralPath)shape).lineTo(22.407791, 18.394766);
((GeneralPath)shape).curveTo(22.095291, 17.832266, 21.532791, 17.519768, 20.907793, 17.519768);
((GeneralPath)shape).curveTo(20.282793, 17.519768, 19.720295, 17.894766, 19.407795, 18.457266);
((GeneralPath)shape).lineTo(8.7828045, 38.707245);
((GeneralPath)shape).curveTo(8.5328045, 39.207245, 8.5328045, 39.894745, 8.8453045, 40.394745);
((GeneralPath)shape).curveTo(9.157804, 40.894745, 9.657804, 41.14474, 10.282804, 41.14474);
((GeneralPath)shape).lineTo(31.782782, 41.14474);
((GeneralPath)shape).curveTo(32.40778, 41.14474, 32.97028, 40.832245, 33.22028, 40.332245);
((GeneralPath)shape).curveTo(33.53278, 39.832245, 33.53278, 39.207245, 33.28278, 38.644745);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(159, 0, 0, 255);
stroke = new BasicStroke(0.6382978f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(33.28278, 38.644745);
((GeneralPath)shape).lineTo(22.407791, 18.394766);
((GeneralPath)shape).curveTo(22.095291, 17.832266, 21.532791, 17.519768, 20.907793, 17.519768);
((GeneralPath)shape).curveTo(20.282793, 17.519768, 19.720295, 17.894766, 19.407795, 18.457266);
((GeneralPath)shape).lineTo(8.7828045, 38.707245);
((GeneralPath)shape).curveTo(8.5328045, 39.207245, 8.5328045, 39.894745, 8.8453045, 40.394745);
((GeneralPath)shape).curveTo(9.157804, 40.894745, 9.657804, 41.14474, 10.282804, 41.14474);
((GeneralPath)shape).lineTo(31.782782, 41.14474);
((GeneralPath)shape).curveTo(32.40778, 41.14474, 32.97028, 40.832245, 33.22028, 40.332245);
((GeneralPath)shape).curveTo(33.53278, 39.832245, 33.53278, 39.207245, 33.28278, 38.644745);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_1;
g.setTransform(defaultTransform__0_0_0_1);
g.setClip(clip__0_0_0_1);
float alpha__0_0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(0.625f, 0.0f, 0.0f, 0.634253978729248f, 5.9078168869018555f, 14.483949661254883f));
// _0_0_0_2 is CompositeGraphicsNode
float alpha__0_0_0_2_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_2_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_2_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_2_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(4.191400051116943, 11.113300323486328), new Point2D.Double(47.319698333740234, 56.05229949951172), new float[] {0.0f,0.3982f,1.0f}, new Color[] {new Color(212, 212, 212, 255),new Color(226, 226, 226, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.5, 37.6);
((GeneralPath)shape).curveTo(9.2, 38.1, 9.5, 38.5, 10.0, 38.5);
((GeneralPath)shape).lineTo(38.2, 38.5);
((GeneralPath)shape).curveTo(38.7, 38.5, 39.0, 38.1, 38.7, 37.6);
((GeneralPath)shape).lineTo(24.4, 11.0);
((GeneralPath)shape).curveTo(24.1, 10.5, 23.7, 10.5, 23.5, 11.0);
((GeneralPath)shape).lineTo(9.5, 37.6);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_2_0;
g.setTransform(defaultTransform__0_0_0_2_0);
g.setClip(clip__0_0_0_2_0);
origAlpha = alpha__0_0_0_2;
g.setTransform(defaultTransform__0_0_0_2);
g.setClip(clip__0_0_0_2);
float alpha__0_0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_0_3 = g.getTransform();
g.transform(new AffineTransform(0.554872989654541f, 0.0f, 0.0f, 0.554872989654541f, 7.583837032318115f, 16.53179931640625f));
// _0_0_0_3 is CompositeGraphicsNode
float alpha__0_0_0_3_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_3_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.9, 36.5);
((GeneralPath)shape).curveTo(22.6, 36.5, 21.6, 35.5, 21.6, 34.2);
((GeneralPath)shape).curveTo(21.6, 32.8, 22.5, 31.9, 23.9, 31.9);
((GeneralPath)shape).curveTo(25.3, 31.9, 26.1, 32.8, 26.2, 34.2);
((GeneralPath)shape).curveTo(26.2, 35.5, 25.3, 36.5, 23.9, 36.5);
((GeneralPath)shape).lineTo(23.9, 36.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.5, 30.6);
((GeneralPath)shape).lineTo(21.9, 19.1);
((GeneralPath)shape).lineTo(25.9, 19.1);
((GeneralPath)shape).lineTo(25.3, 30.6);
((GeneralPath)shape).lineTo(22.4, 30.6);
((GeneralPath)shape).lineTo(22.5, 30.6);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_3_0;
g.setTransform(defaultTransform__0_0_0_3_0);
g.setClip(clip__0_0_0_3_0);
origAlpha = alpha__0_0_0_3;
g.setTransform(defaultTransform__0_0_0_3);
g.setClip(clip__0_0_0_3);
float alpha__0_0_0_4 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(8.546934127807617, 30.281681060791016), new Point2D.Double(30.850879669189453, 48.301883697509766), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 87)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.8990089893341064f, 0.0f, 0.0f, 0.9342349767684937f, 1.875108003616333f, 1.1936450004577637f));
stroke = new BasicStroke(0.6382979f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.323105, 38.183907);
((GeneralPath)shape).lineTo(22.15027, 19.265665);
((GeneralPath)shape).curveTo(21.71698, 18.45069, 21.561699, 18.189213, 20.908405, 18.189213);
((GeneralPath)shape).curveTo(20.346525, 18.189213, 20.054127, 18.57002, 19.651304, 19.33929);
((GeneralPath)shape).lineTo(9.748929, 38.242294);
((GeneralPath)shape).curveTo(9.173765, 39.30359, 9.1128235, 39.580227, 9.3937645, 40.047344);
((GeneralPath)shape).curveTo(9.674704, 40.51446, 10.032797, 40.48902, 11.356441, 40.51949);
((GeneralPath)shape).lineTo(30.974592, 40.51949);
((GeneralPath)shape).curveTo(32.206825, 40.534725, 32.48399, 40.440838, 32.70874, 39.97372);
((GeneralPath)shape).curveTo(32.98968, 39.506603, 32.867798, 39.136, 32.323105, 38.183907);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_4;
g.setTransform(defaultTransform__0_0_0_4);
g.setClip(clip__0_0_0_4);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
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
        return 4;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 4;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static int getOrigWidth() {
		return 48;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static int getOrigHeight() {
		return 48;
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
	public InvalidIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public InvalidIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public InvalidIcon(int width, int height) {
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

