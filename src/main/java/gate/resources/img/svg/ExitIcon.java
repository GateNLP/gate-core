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
public class ExitIcon implements
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
g.transform(new AffineTransform(1.269832968711853f, 0.0f, 0.0f, 1.269832968711853f, -9.243559837341309f, -4.2254438400268555f));
// _0_0_0 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(25.785715103149414, 23.071428298950195), 18.214285f, new Point2D.Double(25.785715103149414, 23.071428298950195), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.0, 23.071428);
((GeneralPath)shape).curveTo(44.0, 33.1309, 35.84519, 41.285713, 25.785715, 41.285713);
((GeneralPath)shape).curveTo(15.726243, 41.285713, 7.57143, 33.1309, 7.57143, 23.071428);
((GeneralPath)shape).curveTo(7.57143, 13.011956, 15.726243, 4.8571434, 25.785715, 4.8571434);
((GeneralPath)shape).curveTo(35.84519, 4.8571434, 44.0, 13.011956, 44.0, 23.071428);
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
g.transform(new AffineTransform(1.1757149696350098f, 0.0f, 0.0f, 1.1757149696350098f, -6.81666898727417f, -3.911155939102173f));
// _0_0_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(7.1433796882629395, 23.071428298950195), new Point2D.Double(42.471248626708984, 23.071428298950195), new float[] {0.0f,0.26988637f,0.3790031f,0.75f,1.0f}, new Color[] {new Color(238, 238, 236, 255),new Color(198, 198, 191, 255),new Color(249, 249, 248, 255),new Color(200, 200, 194, 255),new Color(238, 238, 236, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.0, 23.071428);
((GeneralPath)shape).curveTo(44.0, 33.1309, 35.84519, 41.285713, 25.785715, 41.285713);
((GeneralPath)shape).curveTo(15.726243, 41.285713, 7.57143, 33.1309, 7.57143, 23.071428);
((GeneralPath)shape).curveTo(7.57143, 13.011956, 15.726243, 4.8571434, 25.785715, 4.8571434);
((GeneralPath)shape).curveTo(35.84519, 4.8571434, 44.0, 13.011956, 44.0, 23.071428);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(116, 121, 110, 255);
stroke = new BasicStroke(0.8505457f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.0, 23.071428);
((GeneralPath)shape).curveTo(44.0, 33.1309, 35.84519, 41.285713, 25.785715, 41.285713);
((GeneralPath)shape).curveTo(15.726243, 41.285713, 7.57143, 33.1309, 7.57143, 23.071428);
((GeneralPath)shape).curveTo(7.57143, 13.011956, 15.726243, 4.8571434, 25.785715, 4.8571434);
((GeneralPath)shape).curveTo(35.84519, 4.8571434, 44.0, 13.011956, 44.0, 23.071428);
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
g.transform(new AffineTransform(1.0684900283813477f, 0.0f, 0.0f, 1.0684900283813477f, -4.05177116394043f, -2.4372990131378174f));
// _0_0_2 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(24.67343521118164, 17.224811553955078), 18.672388f, new Point2D.Double(24.67343521118164, 17.224811553955078), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(209, 209, 204, 255),new Color(209, 209, 209, 255),new Color(185, 185, 185, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.364251971244812f, -9.115392045735946E-17f, 9.115392045735946E-17f, 1.364251971244812f, -9.479470252990723f, -5.618015766143799f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.0, 23.071428);
((GeneralPath)shape).curveTo(44.0, 33.1309, 35.84519, 41.285713, 25.785715, 41.285713);
((GeneralPath)shape).curveTo(15.726243, 41.285713, 7.57143, 33.1309, 7.57143, 23.071428);
((GeneralPath)shape).curveTo(7.57143, 13.011956, 15.726243, 4.8571434, 25.785715, 4.8571434);
((GeneralPath)shape).curveTo(35.84519, 4.8571434, 44.0, 13.011956, 44.0, 23.071428);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new Color(85, 87, 83, 255);
stroke = new BasicStroke(3.0f,1,0,10.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(19.1468, 14.674063);
((GeneralPath)shape).curveTo(12.154448, 18.4869, 15.316371, 29.412893, 23.310312, 29.412893);
((GeneralPath)shape).curveTo(31.220984, 29.412893, 34.76642, 19.054546, 27.473824, 14.674063);
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
paint = new Color(85, 87, 83, 255);
stroke = new BasicStroke(2.999999f,1,0,10.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.364832, 19.364035);
((GeneralPath)shape).lineTo(23.364832, 11.95091);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_4;
g.setTransform(defaultTransform__0_0_4);
g.setClip(clip__0_0_4);
float alpha__0_0_5 = origAlpha;
origAlpha = origAlpha * 0.88235295f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5 = g.getClip();
AffineTransform defaultTransform__0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.045549988746643f, 0.0f, 0.0f, 1.045549988746643f, -3.46024489402771f, -1.9080389738082886f));
// _0_0_5 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(25.785715103149414, 21.978363037109375), new Point2D.Double(25.785715103149414, 8.588278770446777), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.9564345f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.0, 23.071428);
((GeneralPath)shape).curveTo(44.0, 33.1309, 35.84519, 41.285713, 25.785715, 41.285713);
((GeneralPath)shape).curveTo(15.726243, 41.285713, 7.57143, 33.1309, 7.57143, 23.071428);
((GeneralPath)shape).curveTo(7.57143, 13.011956, 15.726243, 4.8571434, 25.785715, 4.8571434);
((GeneralPath)shape).curveTo(35.84519, 4.8571434, 44.0, 13.011956, 44.0, 23.071428);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
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
        return 1;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 2;
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
	public ExitIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public ExitIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public ExitIcon(int width, int height) {
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

