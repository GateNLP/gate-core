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
public class prsIcon implements
		javax.swing.Icon {
		
	private static Color getColor(int red, int green, int blue, int alpha, boolean disabled) {
		
		if (!disabled) return new Color(red, green, blue, alpha);
		
		int gray = (int)(((0.30f * red) + (0.59f * green) + (0.11f * blue))/3f);
		
		gray = Math.min(255, Math.max(0, gray));
		
		//This brightens the image the same as GrayFilter
		int percent = 50;		
		gray = (255 - ((255 - gray) * (100 - percent) / 100));

		return new Color(gray, gray, gray, alpha);
	}
	
	/**
	 * Paints the transcoded SVG image on the specified graphics context. You
	 * can install a custom transformation on the graphics context to scale the
	 * image.
	 * 
	 * @param g
	 *            Graphics context.
	 */
	public static void paint(Graphics2D g, boolean disabled) {
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
paint = getColor(255, 97, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.46875, 2.4375);
((GeneralPath)shape).curveTo(16.60445, 3.8048713, 33.95515, 11.502349, 23.53125, 17.1875);
((GeneralPath)shape).curveTo(13.107349, 22.87265, 15.459059, 4.425082, 8.0625, 13.5625);
((GeneralPath)shape).curveTo(0.6659416, 22.699919, 18.524406, 16.176405, 15.28125, 27.375);
((GeneralPath)shape).curveTo(12.038093, 38.573597, 0.31476408, 23.91386, 1.71875, 35.46875);
((GeneralPath)shape).curveTo(3.122736, 47.02364, 11.006366, 30.129192, 16.84375, 40.28125);
((GeneralPath)shape).curveTo(22.681133, 50.433304, 3.742906, 48.171337, 13.125, 55.375);
((GeneralPath)shape).curveTo(22.507093, 62.578663, 15.845284, 45.15392, 27.34375, 48.3125);
((GeneralPath)shape).curveTo(38.842216, 51.471077, 23.79195, 62.89862, 35.65625, 61.53125);
((GeneralPath)shape).curveTo(47.52055, 60.16388, 30.1386, 52.466404, 40.5625, 46.78125);
((GeneralPath)shape).curveTo(50.9864, 41.0961, 48.665943, 59.543667, 56.0625, 50.40625);
((GeneralPath)shape).curveTo(63.459057, 41.268833, 45.569344, 47.792347, 48.8125, 36.59375);
((GeneralPath)shape).curveTo(52.055656, 25.395155, 63.810234, 40.05489, 62.40625, 28.5);
((GeneralPath)shape).curveTo(61.002262, 16.94511, 53.087387, 33.839554, 47.25, 23.6875);
((GeneralPath)shape).curveTo(41.412617, 13.535445, 60.35084, 15.828663, 50.96875, 8.625);
((GeneralPath)shape).curveTo(41.586655, 1.4213371, 48.279713, 18.814829, 36.78125, 15.65625);
((GeneralPath)shape).curveTo(25.282785, 12.497672, 40.33305, 1.0701288, 28.46875, 2.4375);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(32.21875, 20.6875);
((GeneralPath)shape).curveTo(37.889297, 20.6875, 42.5, 25.571932, 42.5, 31.59375);
((GeneralPath)shape).curveTo(42.5, 37.615566, 37.889294, 42.5, 32.21875, 42.5);
((GeneralPath)shape).curveTo(26.548206, 42.5, 21.9375, 37.615566, 21.9375, 31.59375);
((GeneralPath)shape).curveTo(21.9375, 25.571932, 26.548204, 20.6875, 32.21875, 20.6875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.013f,0,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.46875, 2.4375);
((GeneralPath)shape).curveTo(16.60445, 3.8048713, 33.95515, 11.502349, 23.53125, 17.1875);
((GeneralPath)shape).curveTo(13.107349, 22.87265, 15.459059, 4.425082, 8.0625, 13.5625);
((GeneralPath)shape).curveTo(0.6659416, 22.699919, 18.524406, 16.176405, 15.28125, 27.375);
((GeneralPath)shape).curveTo(12.038093, 38.573597, 0.31476408, 23.91386, 1.71875, 35.46875);
((GeneralPath)shape).curveTo(3.122736, 47.02364, 11.006366, 30.129192, 16.84375, 40.28125);
((GeneralPath)shape).curveTo(22.681133, 50.433304, 3.742906, 48.171337, 13.125, 55.375);
((GeneralPath)shape).curveTo(22.507093, 62.578663, 15.845284, 45.15392, 27.34375, 48.3125);
((GeneralPath)shape).curveTo(38.842216, 51.471077, 23.79195, 62.89862, 35.65625, 61.53125);
((GeneralPath)shape).curveTo(47.52055, 60.16388, 30.1386, 52.466404, 40.5625, 46.78125);
((GeneralPath)shape).curveTo(50.9864, 41.0961, 48.665943, 59.543667, 56.0625, 50.40625);
((GeneralPath)shape).curveTo(63.459057, 41.268833, 45.569344, 47.792347, 48.8125, 36.59375);
((GeneralPath)shape).curveTo(52.055656, 25.395155, 63.810234, 40.05489, 62.40625, 28.5);
((GeneralPath)shape).curveTo(61.002262, 16.94511, 53.087387, 33.839554, 47.25, 23.6875);
((GeneralPath)shape).curveTo(41.412617, 13.535445, 60.35084, 15.828663, 50.96875, 8.625);
((GeneralPath)shape).curveTo(41.586655, 1.4213371, 48.279713, 18.814829, 36.78125, 15.65625);
((GeneralPath)shape).curveTo(25.282785, 12.497672, 40.33305, 1.0701288, 28.46875, 2.4375);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(32.21875, 20.6875);
((GeneralPath)shape).curveTo(37.889297, 20.6875, 42.5, 25.571932, 42.5, 31.59375);
((GeneralPath)shape).curveTo(42.5, 37.615566, 37.889294, 42.5, 32.21875, 42.5);
((GeneralPath)shape).curveTo(26.548206, 42.5, 21.9375, 37.615566, 21.9375, 31.59375);
((GeneralPath)shape).curveTo(21.9375, 25.571932, 26.548204, 20.6875, 32.21875, 20.6875);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(41.272727966308594, 20.0), new Point2D.Double(9.090909004211426, 59.73617935180664), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 173, 93, 240, disabled),getColor(255, 173, 93, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.28125, 5.1875);
((GeneralPath)shape).curveTo(28.752836, 9.412957, 30.668612, 16.231962, 24.6875, 18.9375);
((GeneralPath)shape).curveTo(19.257027, 22.847935, 13.780784, 18.228525, 11.4375, 13.34375);
((GeneralPath)shape).curveTo(4.803059, 17.289799, 12.154485, 18.14585, 15.46875, 20.28125);
((GeneralPath)shape).curveTo(21.205275, 26.088879, 13.957686, 37.375126, 6.21875, 33.40625);
((GeneralPath)shape).curveTo(0.42767537, 32.149353, 4.9767256, 41.27217, 8.9375, 35.71875);
((GeneralPath)shape).curveTo(17.701715, 31.739937, 24.468254, 47.237877, 15.024176, 50.56227);
((GeneralPath)shape).curveTo(8.879634, 51.791164, 18.608456, 58.448063, 17.80427, 51.932964);
((GeneralPath)shape).curveTo(19.57184, 42.03195, 36.75406, 46.338974, 33.53171, 55.850883);
((GeneralPath)shape).curveTo(29.526875, 62.01235, 42.128445, 60.15446, 35.88682, 55.086147);
((GeneralPath)shape).curveTo(31.061392, 45.982044, 47.796383, 39.073555, 51.03125, 49.0);
((GeneralPath)shape).curveTo(54.560745, 53.77665, 57.59142, 44.060246, 50.4375, 45.34375);
((GeneralPath)shape).curveTo(41.580242, 41.364502, 48.91899, 26.233015, 57.6875, 30.9375);
((GeneralPath)shape).curveTo(63.697018, 31.879805, 58.626366, 22.950743, 54.53125, 28.78125);
((GeneralPath)shape).curveTo(45.513706, 32.101757, 39.67107, 16.484497, 49.3125, 13.5);
((GeneralPath)shape).curveTo(54.241394, 10.570394, 44.43786, 7.0738745, 45.625, 13.75);
((GeneralPath)shape).curveTo(42.03555, 22.91536, 26.030987, 16.22394, 30.71875, 7.0625);
((GeneralPath)shape).curveTo(32.857006, 3.006516, 28.174742, 4.9233522, 26.28125, 5.1875);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(32.53125, 18.90625);
((GeneralPath)shape).curveTo(44.647602, 19.098745, 48.68691, 37.32616, 38.086876, 42.864265);
((GeneralPath)shape).curveTo(28.453201, 49.290665, 16.283083, 37.266754, 20.6875, 27.15625);
((GeneralPath)shape).curveTo(22.603733, 22.257126, 27.208277, 18.852964, 32.53125, 18.90625);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(0.8440920114517212f, 1.037574052810669f, -1.071671962738037f, 0.8172360062599182f, 38.87282180786133f, -28.255640029907227f));
// _0_0_2 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(35.454544, 31.636364);
((GeneralPath)shape).curveTo(35.454544, 33.343426, 34.111397, 34.727272, 32.454544, 34.727272);
((GeneralPath)shape).curveTo(30.79769, 34.727272, 29.454544, 33.343426, 29.454544, 31.636364);
((GeneralPath)shape).curveTo(29.454544, 29.929302, 30.79769, 28.545456, 32.454544, 28.545456);
((GeneralPath)shape).curveTo(34.111397, 28.545456, 35.454544, 29.929302, 35.454544, 31.636364);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2;
g.setTransform(defaultTransform__0_0_2);
g.setClip(clip__0_0_2);
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
	 * Should this icon be drawn in a disabled state
	 */
	boolean disabled = false;

	/**
	 * Creates a new transcoded SVG image.
	 */
	public prsIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public prsIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public prsIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public prsIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public prsIcon(int width, int height) {
		this(width, height, false);
	}
	
	public prsIcon(int width, int height, boolean disabled) {
		this.width = width;
		this.height = height;
		this.disabled = disabled;
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
		paint(g2d, disabled);
		g2d.dispose();
	}
}

