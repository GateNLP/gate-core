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
public class GATEXMLIcon implements
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
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,48.0003547668457,48.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0 is ShapeNode
paint = getColor(0, 150, 65, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(37.250168, 26.347782);
((GeneralPath)shape).lineTo(37.250168, 18.789976);
((GeneralPath)shape).lineTo(19.559023, 18.789976);
((GeneralPath)shape).lineTo(19.559023, 24.77957);
((GeneralPath)shape).lineTo(29.877855, 24.77957);
((GeneralPath)shape).curveTo(28.642595, 28.535686, 25.839447, 30.677065, 21.796923, 30.677065);
((GeneralPath)shape).curveTo(15.9466095, 30.677065, 12.426611, 27.06432, 12.426611, 20.880402);
((GeneralPath)shape).curveTo(12.426611, 15.080385, 15.900085, 11.371112, 21.13103, 11.371112);
((GeneralPath)shape).curveTo(24.174395, 11.371112, 26.454704, 12.559851, 27.689964, 14.793645);
((GeneralPath)shape).lineTo(36.680805, 14.793645);
((GeneralPath)shape).curveTo(34.919224, 7.948261, 29.118599, 3.855398, 21.13103, 3.855398);
((GeneralPath)shape).curveTo(11.145144, 3.855398, 4.152302, 10.894476, 4.152302, 20.880402);
((GeneralPath)shape).curveTo(4.152302, 30.866325, 11.191351, 37.8554, 21.177237, 37.8554);
((GeneralPath)shape).curveTo(29.974384, 37.8554, 35.53891, 32.10191, 37.250168, 26.347786);
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
g.transform(new AffineTransform(0.944444477558136f, 0.0f, 0.0f, 0.944444477558136f, 11.0f, 31.77777671813965f));
// _0_1 is CompositeGraphicsNode
float alpha__0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_0 = g.getClip();
AffineTransform defaultTransform__0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_0 is ShapeNode
paint = getColor(128, 51, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(36.0, 14.0);
((GeneralPath)shape).lineTo(35.0, 13.0);
((GeneralPath)shape).lineTo(35.0, 1.0);
((GeneralPath)shape).lineTo(36.0, 0.0);
((GeneralPath)shape).lineTo(36.0, 14.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_0;
g.setTransform(defaultTransform__0_1_0);
g.setClip(clip__0_1_0);
float alpha__0_1_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_1 = g.getClip();
AffineTransform defaultTransform__0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_1 is ShapeNode
paint = getColor(255, 102, 0, 255, disabled);
shape = new Rectangle2D.Double(1.0, 1.0, 34.0, 12.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_1;
g.setTransform(defaultTransform__0_1_1);
g.setClip(clip__0_1_1);
float alpha__0_1_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_2 = g.getClip();
AffineTransform defaultTransform__0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_2 is ShapeNode
paint = getColor(255, 204, 170, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(0.0, 0.0);
((GeneralPath)shape).lineTo(36.0, 0.0);
((GeneralPath)shape).lineTo(35.0, 1.0);
((GeneralPath)shape).lineTo(1.0, 1.0);
((GeneralPath)shape).lineTo(0.0, 0.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_2;
g.setTransform(defaultTransform__0_1_2);
g.setClip(clip__0_1_2);
float alpha__0_1_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_3 = g.getClip();
AffineTransform defaultTransform__0_1_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_3 is ShapeNode
paint = getColor(85, 34, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(0.0, 14.0);
((GeneralPath)shape).lineTo(36.0, 14.0);
((GeneralPath)shape).lineTo(35.0, 13.0);
((GeneralPath)shape).lineTo(1.0, 13.0);
((GeneralPath)shape).lineTo(0.0, 14.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_3;
g.setTransform(defaultTransform__0_1_3);
g.setClip(clip__0_1_3);
float alpha__0_1_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_4 = g.getClip();
AffineTransform defaultTransform__0_1_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_4 is ShapeNode
paint = getColor(255, 153, 85, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(1.0, 13.0);
((GeneralPath)shape).lineTo(0.0, 14.0);
((GeneralPath)shape).lineTo(0.0, 0.0);
((GeneralPath)shape).lineTo(1.0, 1.0);
((GeneralPath)shape).lineTo(1.0, 13.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_4;
g.setTransform(defaultTransform__0_1_4);
g.setClip(clip__0_1_4);
float alpha__0_1_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_5 = g.getClip();
AffineTransform defaultTransform__0_1_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f));
// _0_1_5 is CompositeGraphicsNode
float alpha__0_1_5_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_5_0 = g.getClip();
AffineTransform defaultTransform__0_1_5_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_5_0 is ShapeNode
paint = getColor(128, 51, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(4.2424884, 2.9393196);
((GeneralPath)shape).lineTo(7.1663165, 2.9393196);
((GeneralPath)shape).lineTo(8.6897545, 5.5818977);
((GeneralPath)shape).lineTo(10.166317, 2.9393196);
((GeneralPath)shape).lineTo(13.060848, 2.9393196);
((GeneralPath)shape).lineTo(10.388973, 7.099476);
((GeneralPath)shape).lineTo(13.312801, 11.529163);
((GeneralPath)shape).lineTo(10.3303795, 11.529163);
((GeneralPath)shape).lineTo(8.63702, 8.769398);
((GeneralPath)shape).lineTo(6.9378014, 11.529163);
((GeneralPath)shape).lineTo(3.9729576, 11.529163);
((GeneralPath)shape).lineTo(6.937801, 7.052601);
((GeneralPath)shape).lineTo(4.2424884, 2.9393196);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(14.133114, 2.9393196);
((GeneralPath)shape).lineTo(17.625301, 2.9393196);
((GeneralPath)shape).lineTo(18.972958, 8.165882);
((GeneralPath)shape).lineTo(20.314754, 2.9393196);
((GeneralPath)shape).lineTo(23.795223, 2.9393196);
((GeneralPath)shape).lineTo(23.795223, 11.529163);
((GeneralPath)shape).lineTo(21.627254, 11.529163);
((GeneralPath)shape).lineTo(21.627254, 4.9783826);
((GeneralPath)shape).lineTo(19.945614, 11.529163);
((GeneralPath)shape).lineTo(17.982723, 11.529163);
((GeneralPath)shape).lineTo(16.306942, 4.9783826);
((GeneralPath)shape).lineTo(16.306942, 11.529163);
((GeneralPath)shape).lineTo(14.133113, 11.529163);
((GeneralPath)shape).lineTo(14.133113, 2.9393196);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(25.482723, 2.9393196);
((GeneralPath)shape).lineTo(28.13702, 2.9393196);
((GeneralPath)shape).lineTo(28.13702, 9.413929);
((GeneralPath)shape).lineTo(32.2796, 9.413929);
((GeneralPath)shape).lineTo(32.2796, 11.529163);
((GeneralPath)shape).lineTo(25.482723, 11.529163);
((GeneralPath)shape).lineTo(25.482723, 2.9393196);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_5_0;
g.setTransform(defaultTransform__0_1_5_0);
g.setClip(clip__0_1_5_0);
origAlpha = alpha__0_1_5;
g.setTransform(defaultTransform__0_1_5);
g.setClip(clip__0_1_5);
float alpha__0_1_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_6 = g.getClip();
AffineTransform defaultTransform__0_1_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_6 is CompositeGraphicsNode
float alpha__0_1_6_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_6_0 = g.getClip();
AffineTransform defaultTransform__0_1_6_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_6_0 is ShapeNode
paint = getColor(255, 255, 255, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(4.2424884, 2.9393196);
((GeneralPath)shape).lineTo(7.1663165, 2.9393196);
((GeneralPath)shape).lineTo(8.6897545, 5.5818977);
((GeneralPath)shape).lineTo(10.166317, 2.9393196);
((GeneralPath)shape).lineTo(13.060848, 2.9393196);
((GeneralPath)shape).lineTo(10.388973, 7.099476);
((GeneralPath)shape).lineTo(13.312801, 11.529163);
((GeneralPath)shape).lineTo(10.3303795, 11.529163);
((GeneralPath)shape).lineTo(8.63702, 8.769398);
((GeneralPath)shape).lineTo(6.9378014, 11.529163);
((GeneralPath)shape).lineTo(3.9729576, 11.529163);
((GeneralPath)shape).lineTo(6.937801, 7.052601);
((GeneralPath)shape).lineTo(4.2424884, 2.9393196);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(14.133114, 2.9393196);
((GeneralPath)shape).lineTo(17.625301, 2.9393196);
((GeneralPath)shape).lineTo(18.972958, 8.165882);
((GeneralPath)shape).lineTo(20.314754, 2.9393196);
((GeneralPath)shape).lineTo(23.795223, 2.9393196);
((GeneralPath)shape).lineTo(23.795223, 11.529163);
((GeneralPath)shape).lineTo(21.627254, 11.529163);
((GeneralPath)shape).lineTo(21.627254, 4.9783826);
((GeneralPath)shape).lineTo(19.945614, 11.529163);
((GeneralPath)shape).lineTo(17.982723, 11.529163);
((GeneralPath)shape).lineTo(16.306942, 4.9783826);
((GeneralPath)shape).lineTo(16.306942, 11.529163);
((GeneralPath)shape).lineTo(14.133113, 11.529163);
((GeneralPath)shape).lineTo(14.133113, 2.9393196);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(25.482723, 2.9393196);
((GeneralPath)shape).lineTo(28.13702, 2.9393196);
((GeneralPath)shape).lineTo(28.13702, 9.413929);
((GeneralPath)shape).lineTo(32.2796, 9.413929);
((GeneralPath)shape).lineTo(32.2796, 11.529163);
((GeneralPath)shape).lineTo(25.482723, 11.529163);
((GeneralPath)shape).lineTo(25.482723, 2.9393196);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_6_0;
g.setTransform(defaultTransform__0_1_6_0);
g.setClip(clip__0_1_6_0);
origAlpha = alpha__0_1_6;
g.setTransform(defaultTransform__0_1_6);
g.setClip(clip__0_1_6);
origAlpha = alpha__0_1;
g.setTransform(defaultTransform__0_1);
g.setClip(clip__0_1);
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
        return 5;
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
	public GATEXMLIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public GATEXMLIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public GATEXMLIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public GATEXMLIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public GATEXMLIcon(int width, int height) {
		this(width, height, false);
	}
	
	public GATEXMLIcon(int width, int height, boolean disabled) {
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

