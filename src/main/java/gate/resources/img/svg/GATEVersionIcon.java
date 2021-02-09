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
// _0_0_0_1 is TextNode of '9.0'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(20.97841, 1049.3955);
((GeneralPath)shape).lineTo(20.97841, 1047.2393);
((GeneralPath)shape).quadTo(21.69716, 1047.5778, 22.35341, 1047.7444);
((GeneralPath)shape).quadTo(23.00966, 1047.9111, 23.650286, 1047.9111);
((GeneralPath)shape).quadTo(24.994036, 1047.9111, 25.744036, 1047.1663);
((GeneralPath)shape).quadTo(26.494036, 1046.4215, 26.624245, 1044.9528);
((GeneralPath)shape).quadTo(26.092995, 1045.3434, 25.491432, 1045.5387);
((GeneralPath)shape).quadTo(24.88987, 1045.734, 24.186745, 1045.734);
((GeneralPath)shape).quadTo(22.400286, 1045.734, 21.301329, 1044.6897);
((GeneralPath)shape).quadTo(20.20237, 1043.6455, 20.20237, 1041.9424);
((GeneralPath)shape).quadTo(20.20237, 1040.0621, 21.426329, 1038.9293);
((GeneralPath)shape).quadTo(22.650286, 1037.7965, 24.70237, 1037.7965);
((GeneralPath)shape).quadTo(26.98362, 1037.7965, 28.23362, 1039.3356);
((GeneralPath)shape).quadTo(29.48362, 1040.8746, 29.48362, 1043.6871);
((GeneralPath)shape).quadTo(29.48362, 1046.5778, 28.022682, 1048.2314);
((GeneralPath)shape).quadTo(26.561745, 1049.885, 24.01487, 1049.885);
((GeneralPath)shape).quadTo(23.19716, 1049.885, 22.44716, 1049.7627);
((GeneralPath)shape).quadTo(21.69716, 1049.6403, 20.97841, 1049.3955);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(24.686745, 1043.7809);
((GeneralPath)shape).quadTo(25.47841, 1043.7809, 25.876848, 1043.2678);
((GeneralPath)shape).quadTo(26.275286, 1042.7549, 26.275286, 1041.734);
((GeneralPath)shape).quadTo(26.275286, 1040.7184, 25.876848, 1040.2028);
((GeneralPath)shape).quadTo(25.47841, 1039.6871, 24.686745, 1039.6871);
((GeneralPath)shape).quadTo(23.900286, 1039.6871, 23.501848, 1040.2028);
((GeneralPath)shape).quadTo(23.10341, 1040.7184, 23.10341, 1041.734);
((GeneralPath)shape).quadTo(23.10341, 1042.7549, 23.501848, 1043.2678);
((GeneralPath)shape).quadTo(23.900286, 1043.7809, 24.686745, 1043.7809);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(32.142475, 1046.6299);
((GeneralPath)shape).lineTo(34.954975, 1046.6299);
((GeneralPath)shape).lineTo(34.954975, 1049.6559);
((GeneralPath)shape).lineTo(32.142475, 1049.6559);
((GeneralPath)shape).lineTo(32.142475, 1046.6299);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(43.944557, 1043.8121);
((GeneralPath)shape).quadTo(43.944557, 1041.6246, 43.5357, 1040.7288);
((GeneralPath)shape).quadTo(43.12685, 1039.833, 42.1581, 1039.833);
((GeneralPath)shape).quadTo(41.18935, 1039.833, 40.775288, 1040.7288);
((GeneralPath)shape).quadTo(40.361225, 1041.6246, 40.361225, 1043.8121);
((GeneralPath)shape).quadTo(40.361225, 1046.0205, 40.775288, 1046.9268);
((GeneralPath)shape).quadTo(41.18935, 1047.833, 42.1581, 1047.833);
((GeneralPath)shape).quadTo(43.116432, 1047.833, 43.530495, 1046.9268);
((GeneralPath)shape).quadTo(43.944557, 1046.0205, 43.944557, 1043.8121);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(46.954975, 1043.833);
((GeneralPath)shape).quadTo(46.954975, 1046.734, 45.704975, 1048.3096);
((GeneralPath)shape).quadTo(44.454975, 1049.885, 42.1581, 1049.885);
((GeneralPath)shape).quadTo(39.850807, 1049.885, 38.600807, 1048.3096);
((GeneralPath)shape).quadTo(37.350807, 1046.734, 37.350807, 1043.833);
((GeneralPath)shape).quadTo(37.350807, 1040.9268, 38.600807, 1039.3538);
((GeneralPath)shape).quadTo(39.850807, 1037.7809, 42.1581, 1037.7809);
((GeneralPath)shape).quadTo(44.454975, 1037.7809, 45.704975, 1039.3538);
((GeneralPath)shape).quadTo(46.954975, 1040.9268, 46.954975, 1043.833);
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

