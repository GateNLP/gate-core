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
public class MavenIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -0.0f, -0.0f));
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
// _0_0 is ShapeNode
paint = new Color(255, 104, 4, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(40.1875, 34.125);
((GeneralPath)shape).quadTo(36.4375, 34.4375, 33.15625, 34.867188);
((GeneralPath)shape).quadTo(29.875, 35.296875, 27.57031, 36.117188);
((GeneralPath)shape).quadTo(25.1875, 36.976562, 23.9375, 38.539062);
((GeneralPath)shape).quadTo(22.6875, 40.101562, 22.6875, 42.679688);
((GeneralPath)shape).quadTo(22.6875, 44.945312, 24.32812, 45.882812);
((GeneralPath)shape).quadTo(26.007809, 46.78125, 29.17187, 46.78125);
((GeneralPath)shape).quadTo(31.24218, 46.78125, 33.58593, 45.84375);
((GeneralPath)shape).quadTo(35.96875, 44.867188, 38.03906, 43.382812);
((GeneralPath)shape).lineTo(40.1875, 34.125);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(36.47656, 50.179688);
((GeneralPath)shape).quadTo(35.14843, 51.039062, 33.27343, 52.25);
((GeneralPath)shape).quadTo(31.39843, 53.460938, 29.79687, 54.164062);
((GeneralPath)shape).quadTo(27.60937, 55.0625, 25.69531, 55.53125);
((GeneralPath)shape).quadTo(23.78125, 56.0, 20.38281, 56.0);
((GeneralPath)shape).quadTo(14.91406, 56.0, 11.4375, 52.953125);
((GeneralPath)shape).quadTo(8.0, 49.867188, 8.0, 44.945312);
((GeneralPath)shape).quadTo(8.0, 39.75, 10.46093, 36.15625);
((GeneralPath)shape).quadTo(12.921869, 32.523438, 17.76562, 30.375);
((GeneralPath)shape).quadTo(22.29687, 28.34375, 28.54687, 27.445312);
((GeneralPath)shape).quadTo(34.83593, 26.546875, 42.140617, 26.117188);
((GeneralPath)shape).quadTo(42.179718, 25.882812, 42.296867, 25.375);
((GeneralPath)shape).quadTo(42.41406, 24.828125, 42.41406, 24.164062);
((GeneralPath)shape).quadTo(42.41406, 21.390627, 39.83593, 20.296877);
((GeneralPath)shape).quadTo(37.25781, 19.164064, 32.14062, 19.164064);
((GeneralPath)shape).quadTo(28.66406, 19.164064, 24.44531, 20.33594);
((GeneralPath)shape).quadTo(20.265621, 21.507814, 18.15625, 22.289062);
((GeneralPath)shape).lineTo(16.86718, 22.289062);
((GeneralPath)shape).lineTo(18.9375, 11.8984375);
((GeneralPath)shape).quadTo(21.39843, 11.2734375, 26.55468, 10.4921875);
((GeneralPath)shape).quadTo(31.71093, 9.7109375, 36.82812, 9.7109375);
((GeneralPath)shape).quadTo(47.14062, 9.7109375, 51.945312, 12.406252);
((GeneralPath)shape).quadTo(56.789062, 15.1015625, 56.789062, 20.765627);
((GeneralPath)shape).quadTo(56.789062, 21.546877, 56.632812, 22.796875);
((GeneralPath)shape).quadTo(56.51562, 24.046875, 56.281254, 24.984375);
((GeneralPath)shape).lineTo(49.406254, 54.828125);
((GeneralPath)shape).lineTo(35.421875, 54.828125);
((GeneralPath)shape).lineTo(36.476566, 50.179688);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
        return 8;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 10;
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
	public MavenIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public MavenIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public MavenIcon(int width, int height) {
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

