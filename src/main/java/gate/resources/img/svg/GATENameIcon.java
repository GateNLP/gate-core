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
public class GATENameIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0085601806640625f, -0.0f));
clip = new Area(g.getClip());
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,193.98287963867188,64.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.25f, 0.0f, 0.0f, -1.25f, -6.85812520980835f, 83.44012451171875f));
// _0_0 is CompositeGraphicsNode
float alpha__0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(0.44681090116500854f, 0.0f, 0.0f, 0.44681090116500854f, 3.9201745986938477f, 9.488354682922363f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 144.7803955078125f, 123.36849975585938f));
// _0_0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0_0 is ShapeNode
paint = getColor(230, 0, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(0.0, 0.0);
((GeneralPath)shape).lineTo(-38.157, -105.471);
((GeneralPath)shape).lineTo(-11.267002, -105.471);
((GeneralPath)shape).lineTo(-4.9550023, -86.837);
((GeneralPath)shape).lineTo(34.403, -86.837);
((GeneralPath)shape).lineTo(40.716, -105.471);
((GeneralPath)shape).lineTo(67.914, -105.471);
((GeneralPath)shape).lineTo(29.753, 0.0);
((GeneralPath)shape).lineTo(0.0, 0.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(2.241, -3.193);
((GeneralPath)shape).lineTo(27.512, -3.193);
((GeneralPath)shape).lineTo(63.36, -102.277);
((GeneralPath)shape).lineTo(43.005, -102.277);
((GeneralPath)shape).lineTo(37.428, -85.813);
((GeneralPath)shape).lineTo(36.693, -83.645004);
((GeneralPath)shape).lineTo(34.403, -83.645004);
((GeneralPath)shape).lineTo(-4.955002, -83.645004);
((GeneralPath)shape).lineTo(-7.244002, -83.645004);
((GeneralPath)shape).lineTo(-7.980002, -85.813);
((GeneralPath)shape).lineTo(-13.557002, -102.27701);
((GeneralPath)shape).lineTo(-33.605003, -102.27701);
((GeneralPath)shape).lineTo(2.2409973, -3.1930084);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(11.689, -27.381);
((GeneralPath)shape).lineTo(-1.2299995, -65.993);
((GeneralPath)shape).lineTo(-2.6369996, -70.2);
((GeneralPath)shape).lineTo(1.7990003, -70.2);
((GeneralPath)shape).lineTo(27.796999, -70.2);
((GeneralPath)shape).lineTo(32.25, -70.2);
((GeneralPath)shape).lineTo(30.823, -65.980995);
((GeneralPath)shape).lineTo(17.743, -27.370995);
((GeneralPath)shape).lineTo(14.7, -18.384995);
((GeneralPath)shape).lineTo(11.689, -27.380995);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(14.717, -28.396);
((GeneralPath)shape).lineTo(27.797, -67.006);
((GeneralPath)shape).lineTo(1.7990017, -67.006);
((GeneralPath)shape).lineTo(14.717002, -28.395996);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(74.908005, -105.47099);
((GeneralPath)shape).lineTo(100.601006, -105.47099);
((GeneralPath)shape).lineTo(100.601006, -41.86299);
((GeneralPath)shape).lineTo(74.908005, -41.86299);
((GeneralPath)shape).lineTo(74.908005, -105.47099);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(78.102005, -45.055992);
((GeneralPath)shape).lineTo(97.406006, -45.055992);
((GeneralPath)shape).lineTo(97.406006, -102.27799);
((GeneralPath)shape).lineTo(78.102005, -102.27799);
((GeneralPath)shape).lineTo(78.102005, -45.055992);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
// _0_0_0_1 is CompositeGraphicsNode
float alpha__0_0_0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
clip = new Area(g.getClip());
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,354.33099365234375,141.73199462890625)));
g.setClip(clip);
// _0_0_0_1_0 is CompositeGraphicsNode
float alpha__0_0_0_1_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 308.3385009765625f, 38.48809814453125f));
// _0_0_0_1_0_0 is CompositeGraphicsNode
float alpha__0_0_0_1_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_0_0_0 is ShapeNode
paint = getColor(0, 150, 65, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(0.0, 0.0);
((GeneralPath)shape).lineTo(0.0, 23.135);
((GeneralPath)shape).lineTo(34.403, 23.135);
((GeneralPath)shape).lineTo(34.403, 42.967003);
((GeneralPath)shape).lineTo(0.0, 42.967003);
((GeneralPath)shape).lineTo(0.0, 64.303);
((GeneralPath)shape).lineTo(38.906, 64.303);
((GeneralPath)shape).lineTo(38.906, 84.881);
((GeneralPath)shape).lineTo(-25.704002, 84.881);
((GeneralPath)shape).lineTo(-25.704002, -20.591003);
((GeneralPath)shape).lineTo(38.906, -20.591003);
((GeneralPath)shape).lineTo(38.906, 0.0);
((GeneralPath)shape).lineTo(0.0, 0.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(-116.592, 64.303);
((GeneralPath)shape).lineTo(-35.015, 64.303);
((GeneralPath)shape).lineTo(-35.015, 84.880005);
((GeneralPath)shape).lineTo(-116.592, 84.880005);
((GeneralPath)shape).lineTo(-116.592, 64.30301);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(-196.67401, 15.024002);
((GeneralPath)shape).lineTo(-196.67401, 38.904);
((GeneralPath)shape).lineTo(-252.572, 38.904);
((GeneralPath)shape).lineTo(-252.572, 19.979);
((GeneralPath)shape).lineTo(-219.968, 19.979);
((GeneralPath)shape).curveTo(-223.871, 8.111, -232.728, 1.3449993, -245.501, 1.3449993);
((GeneralPath)shape).curveTo(-263.98602, 1.3449993, -275.108, 12.759999, -275.108, 32.299);
((GeneralPath)shape).curveTo(-275.108, 50.625, -264.133, 62.345, -247.605, 62.345);
((GeneralPath)shape).curveTo(-237.989, 62.345, -230.784, 58.589, -226.881, 51.531002);
((GeneralPath)shape).lineTo(-198.47299, 51.531002);
((GeneralPath)shape).curveTo(-204.03899, 73.16, -222.36699, 86.092, -247.605, 86.092);
((GeneralPath)shape).curveTo(-279.15698, 86.092, -301.25198, 63.851006, -301.25198, 32.299004);
((GeneralPath)shape).curveTo(-301.25198, 0.74700356, -279.011, -21.335995, -247.45898, -21.335995);
((GeneralPath)shape).curveTo(-219.66298, -21.335995, -202.081, -3.1569939, -196.67398, 15.024006);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_1_0_0_0;
g.setTransform(defaultTransform__0_0_0_1_0_0_0);
g.setClip(clip__0_0_0_1_0_0_0);
origAlpha = alpha__0_0_0_1_0_0;
g.setTransform(defaultTransform__0_0_0_1_0_0);
g.setClip(clip__0_0_0_1_0_0);
origAlpha = alpha__0_0_0_1_0;
g.setTransform(defaultTransform__0_0_0_1_0);
g.setClip(clip__0_0_0_1_0);
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
        return 3;
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
		return 194;
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
	public GATENameIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public GATENameIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public GATENameIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public GATENameIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public GATENameIcon(int width, int height) {
		this(width, height, false);
	}
	
	public GATENameIcon(int width, int height, boolean disabled) {
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

