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
public class WindowNewIcon implements
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
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,48.0,48.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 0.56725144f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(17.368310928344727, 25.681941986083984), 11.799845f, new Point2D.Double(17.368310928344727, 25.681941986083984), new float[] {0.0f,1.0f}, new Color[] {getColor(0, 0, 0, 255, disabled),getColor(0, 0, 0, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.9457340240478516f, 0.0f, 0.0f, 0.6272739768028259f, -9.62845516204834f, 24.25921058654785f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(47.12502, 40.368813);
((GeneralPath)shape).curveTo(47.12667, 44.457054, 36.846928, 47.77151, 24.165659, 47.77151);
((GeneralPath)shape).curveTo(11.484387, 47.77151, 1.2046481, 44.457054, 1.206299, 40.368813);
((GeneralPath)shape).curveTo(1.2046481, 36.28057, 11.484387, 32.966114, 24.165659, 32.966114);
((GeneralPath)shape).curveTo(36.846928, 32.966114, 47.12667, 36.28057, 47.12502, 40.368813);
((GeneralPath)shape).lineTo(47.12502, 40.368813);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0;
g.setTransform(defaultTransform__0_0);
g.setClip(clip__0_0);
float alpha__0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1 = g.getClip();
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(17.880680084228516, 11.072587966918945), new Point2D.Double(17.880680084228516, 21.767578125), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 255, 255, 255, disabled),getColor(224, 224, 224, 255, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.797614097595215f, 0.0f, 0.0f, 2.5816709995269775f, -13.889039993286133f, -17.13620948791504f));
shape = new RoundRectangle2D.Double(1.497833013534546, 3.496351480484009, 45.00431442260742, 41.00729751586914, 2.2123091220855713, 2.2123100757598877);
g.setPaint(paint);
g.fill(shape);
paint = getColor(141, 141, 141, 255, disabled);
stroke = new BasicStroke(1.0000004f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(1.497833013534546, 3.496351480484009, 45.00431442260742, 41.00729751586914, 2.2123091220855713, 2.2123100757598877);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_1;
g.setTransform(defaultTransform__0_1);
g.setClip(clip__0_1);
float alpha__0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_2 = g.getClip();
AffineTransform defaultTransform__0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(13.267746925354004, 7.7190704345703125), new Point2D.Double(13.267746925354004, 12.48076057434082), new float[] {0.0f,1.0f}, new Color[] {getColor(32, 74, 135, 255, disabled),getColor(32, 74, 135, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.930757999420166f, 0.0f, 0.0f, 2.7985050678253174f, -14.884559631347656f, -18.229450225830078f));
shape = new RoundRectangle2D.Double(3.0, 5.0, 42.0, 7.0, 0.4445417821407318, 0.44454169273376465);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_2;
g.setTransform(defaultTransform__0_2);
g.setClip(clip__0_2);
float alpha__0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_3 = g.getClip();
AffineTransform defaultTransform__0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_3 is ShapeNode
paint = getColor(255, 255, 255, 122, disabled);
stroke = new BasicStroke(1.000001f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(2.5085136890411377, 4.507357597351074, 42.97444534301758, 38.97792434692383, 0.8081382513046265, 0.7123106718063354);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_3;
g.setTransform(defaultTransform__0_3);
g.setClip(clip__0_3);
float alpha__0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4 = g.getClip();
AffineTransform defaultTransform__0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_4 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(55.0, 125.0), 14.375f, new Point2D.Double(55.0, 125.0), new float[] {0.0f,0.5f,1.0f}, new Color[] {getColor(255, 255, 255, 255, disabled),getColor(255, 245, 32, 227, disabled),getColor(255, 243, 0, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.6260870099067688f, 0.0f, 0.0f, 0.6372050046920776f, 4.565217971801758f, -70.8104476928711f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(48.000004, 8.840175);
((GeneralPath)shape).curveTo(48.000004, 13.899003, 43.970566, 17.999996, 39.000004, 17.999996);
((GeneralPath)shape).curveTo(34.02944, 17.999996, 30.000002, 13.899003, 30.000002, 8.840175);
((GeneralPath)shape).curveTo(30.000002, 3.7813473, 34.02944, -0.31964687, 39.000004, -0.31964687);
((GeneralPath)shape).curveTo(43.970566, -0.31964687, 48.000004, 3.7813473, 48.000004, 8.840175);
((GeneralPath)shape).lineTo(48.000004, 8.840175);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_4;
g.setTransform(defaultTransform__0_4);
g.setClip(clip__0_4);
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
        return 0;
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
	 * Should this icon be drawn in a disabled state
	 */
	boolean disabled = false;

	/**
	 * Creates a new transcoded SVG image.
	 */
	public WindowNewIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public WindowNewIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public WindowNewIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public WindowNewIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public WindowNewIcon(int width, int height) {
		this(width, height, false);
	}
	
	public WindowNewIcon(int width, int height, boolean disabled) {
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

