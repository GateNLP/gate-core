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
public class SearchIcon implements
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
paint = new Color(255, 97, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.78125, 4.0625);
((GeneralPath)shape).curveTo(15.787691, 4.0625, 6.03125, 13.571562, 6.03125, 25.28125);
((GeneralPath)shape).curveTo(6.03125, 36.990936, 15.787691, 46.5, 27.78125, 46.5);
((GeneralPath)shape).curveTo(31.135649, 46.5, 34.256382, 45.702133, 37.09375, 44.375);
((GeneralPath)shape).lineTo(51.0, 62.03125);
((GeneralPath)shape).lineTo(61.53125, 53.53125);
((GeneralPath)shape).lineTo(46.96875, 35.03125);
((GeneralPath)shape).curveTo(48.53361, 32.10184, 49.5, 28.81177, 49.5, 25.28125);
((GeneralPath)shape).curveTo(49.5, 13.571562, 39.77481, 4.0625, 27.78125, 4.0625);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.78125, 4.0625);
((GeneralPath)shape).curveTo(15.787691, 4.0625, 6.03125, 13.571562, 6.03125, 25.28125);
((GeneralPath)shape).curveTo(6.03125, 36.990936, 15.787691, 46.5, 27.78125, 46.5);
((GeneralPath)shape).curveTo(31.135649, 46.5, 34.256382, 45.702133, 37.09375, 44.375);
((GeneralPath)shape).lineTo(51.0, 62.03125);
((GeneralPath)shape).lineTo(61.53125, 53.53125);
((GeneralPath)shape).lineTo(46.96875, 35.03125);
((GeneralPath)shape).curveTo(48.53361, 32.10184, 49.5, 28.81177, 49.5, 25.28125);
((GeneralPath)shape).curveTo(49.5, 13.571562, 39.77481, 4.0625, 27.78125, 4.0625);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(0.6617090106010437f, -0.5309789776802063f, 0.6213799715042114f, 0.5654399991035461f, -18.57513999938965f, 52.80791091918945f));
// _0_0_1 is ShapeNode
paint = new Color(255, 97, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(63.65396, 61.107292);
((GeneralPath)shape).curveTo(63.65567, 62.499535, 62.13404, 63.786377, 59.662655, 64.482735);
((GeneralPath)shape).curveTo(57.19127, 65.1791, 54.145905, 65.1791, 51.67452, 64.482735);
((GeneralPath)shape).curveTo(49.203133, 63.786377, 47.681503, 62.499535, 47.683212, 61.107292);
((GeneralPath)shape).curveTo(47.681503, 59.71505, 49.203133, 58.428207, 51.67452, 57.731846);
((GeneralPath)shape).curveTo(54.145905, 57.035484, 57.19127, 57.035484, 59.662655, 57.731846);
((GeneralPath)shape).curveTo(62.13404, 58.428207, 63.65567, 59.71505, 63.65396, 61.107292);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(63.65396, 61.107292);
((GeneralPath)shape).curveTo(63.65567, 62.499535, 62.13404, 63.786377, 59.662655, 64.482735);
((GeneralPath)shape).curveTo(57.19127, 65.1791, 54.145905, 65.1791, 51.67452, 64.482735);
((GeneralPath)shape).curveTo(49.203133, 63.786377, 47.681503, 62.499535, 47.683212, 61.107292);
((GeneralPath)shape).curveTo(47.681503, 59.71505, 49.203133, 58.428207, 51.67452, 57.731846);
((GeneralPath)shape).curveTo(54.145905, 57.035484, 57.19127, 57.035484, 59.662655, 57.731846);
((GeneralPath)shape).curveTo(62.13404, 58.428207, 63.65567, 59.71505, 63.65396, 61.107292);
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
g.transform(new AffineTransform(0.989890992641449f, 0.0f, 0.0f, 0.9789890050888062f, 5.574254989624023f, -10.336039543151855f));
// _0_0_2 is ShapeNode
paint = new Color(162, 249, 164, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(45.383396, 32.758736);
((GeneralPath)shape).curveTo(45.385696, 40.33898, 41.24497, 47.344376, 34.521523, 51.135147);
((GeneralPath)shape).curveTo(27.798079, 54.92592, 19.51379, 54.92592, 12.790344, 51.135147);
((GeneralPath)shape).curveTo(6.0669, 47.344376, 1.9261721, 40.33898, 1.9284725, 32.758736);
((GeneralPath)shape).curveTo(1.9261721, 25.178492, 6.0669, 18.173096, 12.790344, 14.382325);
((GeneralPath)shape).curveTo(19.51379, 10.591555, 27.798079, 10.591555, 34.521523, 14.382325);
((GeneralPath)shape).curveTo(41.24497, 18.173096, 45.385696, 25.178492, 45.383396, 32.758736);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(41, 165, 19, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(45.383396, 32.758736);
((GeneralPath)shape).curveTo(45.385696, 40.33898, 41.24497, 47.344376, 34.521523, 51.135147);
((GeneralPath)shape).curveTo(27.798079, 54.92592, 19.51379, 54.92592, 12.790344, 51.135147);
((GeneralPath)shape).curveTo(6.0669, 47.344376, 1.9261721, 40.33898, 1.9284725, 32.758736);
((GeneralPath)shape).curveTo(1.9261721, 25.178492, 6.0669, 18.173096, 12.790344, 14.382325);
((GeneralPath)shape).curveTo(19.51379, 10.591555, 27.798079, 10.591555, 34.521523, 14.382325);
((GeneralPath)shape).curveTo(41.24497, 18.173096, 45.385696, 25.178492, 45.383396, 32.758736);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3 is ShapeNode
paint = new Color(255, 97, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.355799, 32.82302);
((GeneralPath)shape).lineTo(26.355799, 32.82302);
((GeneralPath)shape).lineTo(26.355799, 32.82302);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.355799, 32.82302);
((GeneralPath)shape).lineTo(26.355799, 32.82302);
((GeneralPath)shape).lineTo(26.355799, 32.82302);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 5.571527004241943f, -10.471940040588379f));
// _0_0_4 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(27.87637710571289, 42.43628692626953), 22.227463f, new Point2D.Double(27.87637710571289, 42.43628692626953), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(150, 163, 150, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.8226919770240784f, 1.4098659753799438f, -0.9224249720573425f, 0.5382580161094666f, 47.480159759521484f, -37.554840087890625f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(45.383396, 32.758736);
((GeneralPath)shape).curveTo(45.385696, 40.33898, 41.24497, 47.344376, 34.521523, 51.135147);
((GeneralPath)shape).curveTo(27.798079, 54.92592, 19.51379, 54.92592, 12.790344, 51.135147);
((GeneralPath)shape).curveTo(6.0669, 47.344376, 1.9261721, 40.33898, 1.9284725, 32.758736);
((GeneralPath)shape).curveTo(1.9261721, 25.178492, 6.0669, 18.173096, 12.790344, 14.382325);
((GeneralPath)shape).curveTo(19.51379, 10.591555, 27.798079, 10.591555, 34.521523, 14.382325);
((GeneralPath)shape).curveTo(41.24497, 18.173096, 45.385696, 25.178492, 45.383396, 32.758736);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(45.383396, 32.758736);
((GeneralPath)shape).curveTo(45.385696, 40.33898, 41.24497, 47.344376, 34.521523, 51.135147);
((GeneralPath)shape).curveTo(27.798079, 54.92592, 19.51379, 54.92592, 12.790344, 51.135147);
((GeneralPath)shape).curveTo(6.0669, 47.344376, 1.9261721, 40.33898, 1.9284725, 32.758736);
((GeneralPath)shape).curveTo(1.9261721, 25.178492, 6.0669, 18.173096, 12.790344, 14.382325);
((GeneralPath)shape).curveTo(19.51379, 10.591555, 27.798079, 10.591555, 34.521523, 14.382325);
((GeneralPath)shape).curveTo(41.24497, 18.173096, 45.385696, 25.178492, 45.383396, 32.758736);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_4;
g.setTransform(defaultTransform__0_0_4);
g.setClip(clip__0_0_4);
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
        return 6;
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
	public SearchIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public SearchIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public SearchIcon(int width, int height) {
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

