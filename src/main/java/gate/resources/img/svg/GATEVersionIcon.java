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
public class GATEVersionIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -0.0f, -0.0f));
clip = new Area(g.getClip());
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,60.0,60.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -992.3621826171875f));
// _0_0 is CompositeGraphicsNode
float alpha__0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 11.230769157409668f, -2.153846263885498f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0 is ShapeNode
paint = getColor(0, 150, 65, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(43.280495, 1030.9531);
((GeneralPath)shape).lineTo(43.280495, 1019.83875);
((GeneralPath)shape).lineTo(17.264095, 1019.83875);
((GeneralPath)shape).lineTo(17.264095, 1028.647);
((GeneralPath)shape).lineTo(32.438854, 1028.647);
((GeneralPath)shape).curveTo(30.622305, 1034.1707, 26.500015, 1037.3198, 20.555134, 1037.3198);
((GeneralPath)shape).curveTo(11.951724, 1037.3198, 6.7752542, 1032.007, 6.7752542, 1022.9129);
((GeneralPath)shape).curveTo(6.7752542, 1014.3835, 11.883304, 1008.9287, 19.575874, 1008.9287);
((GeneralPath)shape).curveTo(24.051405, 1008.9287, 27.404804, 1010.6768, 29.221363, 1013.9618);
((GeneralPath)shape).lineTo(42.44319, 1013.9618);
((GeneralPath)shape).curveTo(39.852623, 1003.8951, 31.322302, 997.8762, 19.575872, 997.8762);
((GeneralPath)shape).curveTo(4.8907433, 997.8762, -5.392847, 1008.2277, -5.392847, 1022.9129);
((GeneralPath)shape).curveTo(-5.392847, 1037.5981, 4.9586926, 1047.8762, 19.643822, 1047.8762);
((GeneralPath)shape).curveTo(32.580803, 1047.8762, 40.76393, 1039.4152, 43.28049, 1030.9532);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_0;
g.setTransform(defaultTransform__0_0_0_0);
g.setClip(clip__0_0_0_0);
float alpha__0_0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1 is TextNode of '9.0.1'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(6.978411, 1051.3955);
((GeneralPath)shape).lineTo(6.978411, 1049.2393);
((GeneralPath)shape).quadTo(7.697161, 1049.5778, 8.353412, 1049.7444);
((GeneralPath)shape).quadTo(9.009662, 1049.9111, 9.650287, 1049.9111);
((GeneralPath)shape).quadTo(10.994037, 1049.9111, 11.744037, 1049.1663);
((GeneralPath)shape).quadTo(12.494037, 1048.4215, 12.624245, 1046.9528);
((GeneralPath)shape).quadTo(12.092995, 1047.3434, 11.491432, 1047.5387);
((GeneralPath)shape).quadTo(10.88987, 1047.734, 10.186745, 1047.734);
((GeneralPath)shape).quadTo(8.400287, 1047.734, 7.301328, 1046.6897);
((GeneralPath)shape).quadTo(6.2023697, 1045.6455, 6.2023697, 1043.9424);
((GeneralPath)shape).quadTo(6.2023697, 1042.0621, 7.426328, 1040.9293);
((GeneralPath)shape).quadTo(8.650287, 1039.7965, 10.70237, 1039.7965);
((GeneralPath)shape).quadTo(12.98362, 1039.7965, 14.23362, 1041.3356);
((GeneralPath)shape).quadTo(15.48362, 1042.8746, 15.48362, 1045.6871);
((GeneralPath)shape).quadTo(15.48362, 1048.5778, 14.022682, 1050.2314);
((GeneralPath)shape).quadTo(12.561745, 1051.885, 10.01487, 1051.885);
((GeneralPath)shape).quadTo(9.197162, 1051.885, 8.447162, 1051.7627);
((GeneralPath)shape).quadTo(7.697161, 1051.6403, 6.978411, 1051.3955);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(10.686745, 1045.7809);
((GeneralPath)shape).quadTo(11.478412, 1045.7809, 11.876849, 1045.2678);
((GeneralPath)shape).quadTo(12.275287, 1044.7549, 12.275287, 1043.734);
((GeneralPath)shape).quadTo(12.275287, 1042.7184, 11.876849, 1042.2028);
((GeneralPath)shape).quadTo(11.478412, 1041.6871, 10.686745, 1041.6871);
((GeneralPath)shape).quadTo(9.900287, 1041.6871, 9.501849, 1042.2028);
((GeneralPath)shape).quadTo(9.103412, 1042.7184, 9.103412, 1043.734);
((GeneralPath)shape).quadTo(9.103412, 1044.7549, 9.501849, 1045.2678);
((GeneralPath)shape).quadTo(9.900287, 1045.7809, 10.686745, 1045.7809);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(18.142473, 1048.6299);
((GeneralPath)shape).lineTo(20.954973, 1048.6299);
((GeneralPath)shape).lineTo(20.954973, 1051.6559);
((GeneralPath)shape).lineTo(18.142473, 1051.6559);
((GeneralPath)shape).lineTo(18.142473, 1048.6299);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(29.944557, 1045.8121);
((GeneralPath)shape).quadTo(29.944557, 1043.6246, 29.535704, 1042.7288);
((GeneralPath)shape).quadTo(29.126848, 1041.833, 28.158098, 1041.833);
((GeneralPath)shape).quadTo(27.189348, 1041.833, 26.775286, 1042.7288);
((GeneralPath)shape).quadTo(26.361223, 1043.6246, 26.361223, 1045.8121);
((GeneralPath)shape).quadTo(26.361223, 1048.0205, 26.775286, 1048.9268);
((GeneralPath)shape).quadTo(27.189348, 1049.833, 28.158098, 1049.833);
((GeneralPath)shape).quadTo(29.116432, 1049.833, 29.530495, 1048.9268);
((GeneralPath)shape).quadTo(29.944557, 1048.0205, 29.944557, 1045.8121);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(32.954975, 1045.833);
((GeneralPath)shape).quadTo(32.954975, 1048.734, 31.704973, 1050.3096);
((GeneralPath)shape).quadTo(30.454973, 1051.885, 28.158098, 1051.885);
((GeneralPath)shape).quadTo(25.850807, 1051.885, 24.600807, 1050.3096);
((GeneralPath)shape).quadTo(23.350807, 1048.734, 23.350807, 1045.833);
((GeneralPath)shape).quadTo(23.350807, 1042.9268, 24.600807, 1041.3538);
((GeneralPath)shape).quadTo(25.850807, 1039.7809, 28.158098, 1039.7809);
((GeneralPath)shape).quadTo(30.454973, 1039.7809, 31.704973, 1041.3538);
((GeneralPath)shape).quadTo(32.954975, 1042.9268, 32.954975, 1045.833);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(35.353413, 1048.6299);
((GeneralPath)shape).lineTo(38.165913, 1048.6299);
((GeneralPath)shape).lineTo(38.165913, 1051.6559);
((GeneralPath)shape).lineTo(35.353413, 1051.6559);
((GeneralPath)shape).lineTo(35.353413, 1048.6299);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.67112, 1049.5778);
((GeneralPath)shape).lineTo(44.32737, 1049.5778);
((GeneralPath)shape).lineTo(44.32737, 1042.0361);
((GeneralPath)shape).lineTo(41.603413, 1042.5986);
((GeneralPath)shape).lineTo(41.603413, 1040.5518);
((GeneralPath)shape).lineTo(44.311745, 1039.9893);
((GeneralPath)shape).lineTo(47.17112, 1039.9893);
((GeneralPath)shape).lineTo(47.17112, 1049.5778);
((GeneralPath)shape).lineTo(49.82737, 1049.5778);
((GeneralPath)shape).lineTo(49.82737, 1051.6559);
((GeneralPath)shape).lineTo(41.67112, 1051.6559);
((GeneralPath)shape).lineTo(41.67112, 1049.5778);
((GeneralPath)shape).closePath();
paint = getColor(230, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_1;
g.setTransform(defaultTransform__0_0_0_1);
g.setClip(clip__0_0_0_1);
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
        return 6;
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
		return 60;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static int getOrigHeight() {
		return 60;
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
	public GATEVersionIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public GATEVersionIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public GATEVersionIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public GATEVersionIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public GATEVersionIcon(int width, int height) {
		this(width, height, false);
	}
	
	public GATEVersionIcon(int width, int height, boolean disabled) {
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

