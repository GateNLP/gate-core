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
public class OntologyIcon implements
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
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(37.045452, 17.544512);
((GeneralPath)shape).curveTo(54.045452, 39.544514, 54.045452, 39.544514, 54.045452, 39.544514);
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
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0455467f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(10.116883, 40.56625);
((GeneralPath)shape).lineTo(24.974026, 19.522774);
((GeneralPath)shape).lineTo(24.974026, 19.522774);
((GeneralPath)shape).lineTo(24.974026, 19.522774);
((GeneralPath)shape).lineTo(24.974026, 19.522774);
((GeneralPath)shape).lineTo(24.974026, 19.522774);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2 is ShapeNode
paint = getColor(255, 0, 0, 255, disabled);
shape = new Rectangle2D.Double(43.16930389404297, 35.69498062133789, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.3269536f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(43.16930389404297, 35.69498062133789, 20.667221069335938, 21.18605613708496);
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
paint = new RadialGradientPaint(new Point2D.Double(57.67424011230469, 18.084186553955078), 32.5f, new Point2D.Double(57.67424011230469, 18.084186553955078), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 146, 0, 255, disabled),getColor(255, 0, 0, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.0523889996111393f, 4.064496897626668E-4f, -0.0036129949148744345f, -0.4893690049648285f, 65.58753967285156f, 48.62900924682617f));
shape = new Rectangle2D.Double(43.16930389404297, 35.69498062133789, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.3269536f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(43.16930389404297, 35.69498062133789, 20.667221069335938, 21.18605613708496);
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
paint = new RadialGradientPaint(new Point2D.Double(68.20856475830078, 14.831014633178711), 32.5f, new Point2D.Double(68.20856475830078, 14.831014633178711), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 146, 0, 255, disabled),getColor(255, 0, 0, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2512589455582201E-4f, -0.05370550975203514f, 0.567264974117279f, 0.0013888339744880795f, 48.73617172241211f, 40.55625915527344f));
shape = new Rectangle2D.Double(43.16930389404297, 35.69498062133789, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.3269536f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(43.16930389404297, 35.69498062133789, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
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
paint = getColor(255, 0, 0, 255, disabled);
shape = new Rectangle2D.Double(21.71475601196289, 3.694979429244995, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.3269536f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(21.71475601196289, 3.694979429244995, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
float alpha__0_0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(57.67424011230469, 18.084186553955078), 32.5f, new Point2D.Double(57.67424011230469, 18.084186553955078), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 146, 0, 255, disabled),getColor(255, 0, 0, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.0523889996111393f, 4.064496897626668E-4f, -0.0036129949148744345f, -0.4893690049648285f, 44.132999420166016f, 16.629009246826172f));
shape = new Rectangle2D.Double(21.71475601196289, 3.694979429244995, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.3269536f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(21.71475601196289, 3.694979429244995, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_6;
g.setTransform(defaultTransform__0_0_6);
g.setClip(clip__0_0_6);
float alpha__0_0_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(68.20856475830078, 14.831014633178711), 32.5f, new Point2D.Double(68.20856475830078, 14.831014633178711), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 146, 0, 255, disabled),getColor(255, 0, 0, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2512589455582201E-4f, -0.05370550975203514f, 0.567264974117279f, 0.0013888339744880795f, 27.28162956237793f, 8.556262016296387f));
shape = new Rectangle2D.Double(21.71475601196289, 3.694979429244995, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.3269536f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(21.71475601196289, 3.694979429244995, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_7;
g.setTransform(defaultTransform__0_0_7);
g.setClip(clip__0_0_7);
float alpha__0_0_8 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_8 = g.getClip();
AffineTransform defaultTransform__0_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8 is ShapeNode
paint = getColor(255, 0, 0, 255, disabled);
shape = new Rectangle2D.Double(0.25438550114631653, 35.69498062133789, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.3269536f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(0.25438550114631653, 35.69498062133789, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_8;
g.setTransform(defaultTransform__0_0_8);
g.setClip(clip__0_0_8);
float alpha__0_0_9 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_9 = g.getClip();
AffineTransform defaultTransform__0_0_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_9 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(57.67424011230469, 18.084186553955078), 32.5f, new Point2D.Double(57.67424011230469, 18.084186553955078), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 146, 0, 255, disabled),getColor(255, 0, 0, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.0523889996111393f, 4.064496897626668E-4f, -0.0036129949148744345f, -0.4893690049648285f, 22.672630310058594f, 48.62900924682617f));
shape = new Rectangle2D.Double(0.25438550114631653, 35.69498062133789, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.3269536f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(0.25438550114631653, 35.69498062133789, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_9;
g.setTransform(defaultTransform__0_0_9);
g.setClip(clip__0_0_9);
float alpha__0_0_10 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_10 = g.getClip();
AffineTransform defaultTransform__0_0_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_10 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(68.20856475830078, 14.831014633178711), 32.5f, new Point2D.Double(68.20856475830078, 14.831014633178711), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 146, 0, 255, disabled),getColor(255, 0, 0, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2512589455582201E-4f, -0.05370550975203514f, 0.567264974117279f, 0.0013888339744880795f, 5.869625091552734f, 40.55625915527344f));
shape = new Rectangle2D.Double(0.3027527630329132, 35.69498062133789, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.3269536f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(0.3027527630329132, 35.69498062133789, 20.667221069335938, 21.18605613708496);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_10;
g.setTransform(defaultTransform__0_0_10);
g.setClip(clip__0_0_10);
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
        return 4;
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
	public OntologyIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public OntologyIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public OntologyIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public OntologyIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public OntologyIcon(int width, int height) {
		this(width, height, false);
	}
	
	public OntologyIcon(int width, int height, boolean disabled) {
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

