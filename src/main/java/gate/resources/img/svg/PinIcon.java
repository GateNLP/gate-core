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
public class PinIcon implements
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
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,48.0,48.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(0.09134901314973831f, 0.0f, 0.0f, 0.09134901314973831f, 0.15568938851356506f, 0.749992847442627f));
// _0_0 is CompositeGraphicsNode
float alpha__0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = getColor(163, 28, 9, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(505.605, 190.556);
((GeneralPath)shape).curveTo(491.816, 204.345, 438.718, 173.607, 387.006, 121.895);
((GeneralPath)shape).curveTo(335.294, 70.183, 304.556, 17.085, 318.345, 3.296);
((GeneralPath)shape).curveTo(332.134, -10.493, 385.232, 20.244999, 436.944, 71.957);
((GeneralPath)shape).curveTo(488.656, 123.66901, 519.394, 176.768, 505.605, 190.556);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 9.242690086364746f, -5.336719036102295f));
// _0_0_1 is ShapeNode
paint = getColor(217, 219, 232, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(112.358, 346.605);
((GeneralPath)shape).lineTo(162.295, 396.543);
((GeneralPath)shape).lineTo(0.0, 508.9);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2 is ShapeNode
paint = getColor(206, 57, 41, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(387.007, 121.894);
((GeneralPath)shape).curveTo(335.295, 70.182, 304.557, 17.084, 318.346, 3.295);
((GeneralPath)shape).curveTo(268.355, 53.286003, 279.116, 126.36, 330.828, 178.07199);
((GeneralPath)shape).curveTo(382.54, 229.784, 452.499, 243.66098, 502.47998, 193.67899);
((GeneralPath)shape).lineTo(501.69397, 192.85799);
((GeneralPath)shape).curveTo(483.62497, 199.43498, 434.76398, 169.65099, 387.00696, 121.89399);
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
paint = getColor(163, 28, 9, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(342.08, 279.177);
((GeneralPath)shape).lineTo(400.68597, 220.571);
((GeneralPath)shape).curveTo(376.09198, 213.844, 351.93997, 199.182, 330.83298, 178.075);
((GeneralPath)shape).curveTo(309.71698, 156.959, 295.576, 132.286, 289.46698, 107.084);
((GeneralPath)shape).lineTo(229.73997, 166.80301);
((GeneralPath)shape).lineTo(181.02097, 215.522);
((GeneralPath)shape).curveTo(187.13896, 240.73401, 203.60196, 263.076, 224.71796, 284.183);
((GeneralPath)shape).curveTo(245.82495, 305.299, 268.78497, 321.153, 293.37897, 327.88);
((GeneralPath)shape).lineTo(342.07996, 279.177);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = getColor(163, 28, 9, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(342.08, 279.177);
((GeneralPath)shape).lineTo(400.68597, 220.571);
((GeneralPath)shape).curveTo(376.09198, 213.844, 351.93997, 199.182, 330.83298, 178.075);
((GeneralPath)shape).curveTo(309.71698, 156.959, 295.576, 132.286, 289.46698, 107.084);
((GeneralPath)shape).lineTo(229.73997, 166.80301);
((GeneralPath)shape).lineTo(181.02097, 215.522);
((GeneralPath)shape).curveTo(187.13896, 240.73401, 203.60196, 263.076, 224.71796, 284.183);
((GeneralPath)shape).curveTo(245.82495, 305.299, 268.78497, 321.153, 293.37897, 327.88);
((GeneralPath)shape).lineTo(342.07996, 279.177);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = getColor(206, 57, 41, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(311.324, 389.978);
((GeneralPath)shape).curveTo(313.672, 368.492, 309.717, 345.75198, 299.495, 321.758);
((GeneralPath)shape).lineTo(293.37698, 327.884);
((GeneralPath)shape).curveTo(268.783, 321.149, 245.823, 305.294, 224.716, 284.187);
((GeneralPath)shape).curveTo(203.6, 263.08, 187.137, 240.729, 181.019, 215.526);
((GeneralPath)shape).lineTo(187.26, 209.285);
((GeneralPath)shape).lineTo(186.986, 209.003);
((GeneralPath)shape).curveTo(162.84299, 198.65701, 139.97, 194.658, 118.35999, 197.024);
((GeneralPath)shape).curveTo(78.20299, 201.40201, 54.288994, 242.901, 70.726, 279.80902);
((GeneralPath)shape).curveTo(83.235, 307.881, 106.292, 340.54303, 137.048, 371.29803);
((GeneralPath)shape).curveTo(167.794, 402.04504, 200.465, 425.11102, 228.537, 437.61102);
((GeneralPath)shape).curveTo(265.438, 454.04803, 306.937, 430.13403, 311.324, 389.97803);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
float alpha__0_0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6 is CompositeGraphicsNode
origAlpha = alpha__0_0_6;
g.setTransform(defaultTransform__0_0_6);
g.setClip(clip__0_0_6);
float alpha__0_0_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7 is CompositeGraphicsNode
origAlpha = alpha__0_0_7;
g.setTransform(defaultTransform__0_0_7);
g.setClip(clip__0_0_7);
float alpha__0_0_8 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_8 = g.getClip();
AffineTransform defaultTransform__0_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8 is CompositeGraphicsNode
origAlpha = alpha__0_0_8;
g.setTransform(defaultTransform__0_0_8);
g.setClip(clip__0_0_8);
float alpha__0_0_9 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_9 = g.getClip();
AffineTransform defaultTransform__0_0_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_9 is CompositeGraphicsNode
origAlpha = alpha__0_0_9;
g.setTransform(defaultTransform__0_0_9);
g.setClip(clip__0_0_9);
float alpha__0_0_10 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_10 = g.getClip();
AffineTransform defaultTransform__0_0_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_10 is CompositeGraphicsNode
origAlpha = alpha__0_0_10;
g.setTransform(defaultTransform__0_0_10);
g.setClip(clip__0_0_10);
float alpha__0_0_11 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_11 = g.getClip();
AffineTransform defaultTransform__0_0_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_11 is CompositeGraphicsNode
origAlpha = alpha__0_0_11;
g.setTransform(defaultTransform__0_0_11);
g.setClip(clip__0_0_11);
float alpha__0_0_12 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_12 = g.getClip();
AffineTransform defaultTransform__0_0_12 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_12 is CompositeGraphicsNode
origAlpha = alpha__0_0_12;
g.setTransform(defaultTransform__0_0_12);
g.setClip(clip__0_0_12);
float alpha__0_0_13 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_13 = g.getClip();
AffineTransform defaultTransform__0_0_13 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_13 is CompositeGraphicsNode
origAlpha = alpha__0_0_13;
g.setTransform(defaultTransform__0_0_13);
g.setClip(clip__0_0_13);
float alpha__0_0_14 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_14 = g.getClip();
AffineTransform defaultTransform__0_0_14 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_14 is CompositeGraphicsNode
origAlpha = alpha__0_0_14;
g.setTransform(defaultTransform__0_0_14);
g.setClip(clip__0_0_14);
float alpha__0_0_15 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_15 = g.getClip();
AffineTransform defaultTransform__0_0_15 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_15 is CompositeGraphicsNode
origAlpha = alpha__0_0_15;
g.setTransform(defaultTransform__0_0_15);
g.setClip(clip__0_0_15);
float alpha__0_0_16 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_16 = g.getClip();
AffineTransform defaultTransform__0_0_16 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_16 is CompositeGraphicsNode
origAlpha = alpha__0_0_16;
g.setTransform(defaultTransform__0_0_16);
g.setClip(clip__0_0_16);
float alpha__0_0_17 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_17 = g.getClip();
AffineTransform defaultTransform__0_0_17 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_17 is CompositeGraphicsNode
origAlpha = alpha__0_0_17;
g.setTransform(defaultTransform__0_0_17);
g.setClip(clip__0_0_17);
float alpha__0_0_18 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_18 = g.getClip();
AffineTransform defaultTransform__0_0_18 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_18 is CompositeGraphicsNode
origAlpha = alpha__0_0_18;
g.setTransform(defaultTransform__0_0_18);
g.setClip(clip__0_0_18);
float alpha__0_0_19 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_19 = g.getClip();
AffineTransform defaultTransform__0_0_19 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_19 is CompositeGraphicsNode
origAlpha = alpha__0_0_19;
g.setTransform(defaultTransform__0_0_19);
g.setClip(clip__0_0_19);
float alpha__0_0_20 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_20 = g.getClip();
AffineTransform defaultTransform__0_0_20 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_20 is CompositeGraphicsNode
origAlpha = alpha__0_0_20;
g.setTransform(defaultTransform__0_0_20);
g.setClip(clip__0_0_20);
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
	public PinIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public PinIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public PinIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public PinIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public PinIcon(int width, int height) {
		this(width, height, false);
	}
	
	public PinIcon(int width, int height, boolean disabled) {
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

