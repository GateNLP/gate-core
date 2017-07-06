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
public class LeftArrowIcon implements
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
paint = getColor(255, 105, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(52.177727, 58.560356);
((GeneralPath)shape).curveTo(48.570007, 51.87652, 43.68886, 46.31722, 39.009678, 40.922623);
((GeneralPath)shape).curveTo(35.8591, 38.590775, 32.44, 37.236713, 29.272133, 34.97484);
((GeneralPath)shape).curveTo(27.901129, 34.09523, 26.517641, 33.272144, 25.15852, 32.368217);
((GeneralPath)shape).lineTo(32.43323, 26.000004);
((GeneralPath)shape).curveTo(33.78279, 26.82694, 35.118584, 27.691074, 36.47595, 28.49637);
((GeneralPath)shape).curveTo(39.678955, 30.855608, 43.220695, 32.06944, 46.26515, 34.827103);
((GeneralPath)shape).curveTo(51.03293, 40.64891, 55.942215, 46.55963, 60.03323, 53.19944);
((GeneralPath)shape).lineTo(52.177727, 58.560356);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1 is ShapeNode
paint = getColor(255, 105, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(52.303017, 5.651286);
((GeneralPath)shape).curveTo(48.695297, 12.335126, 43.814156, 17.894424, 39.13497, 23.28902);
((GeneralPath)shape).curveTo(35.984394, 25.62087, 32.565292, 26.97493, 29.397427, 29.236805);
((GeneralPath)shape).curveTo(28.026428, 30.116411, 26.642935, 30.9395, 25.283813, 31.843426);
((GeneralPath)shape).lineTo(32.558525, 38.21164);
((GeneralPath)shape).curveTo(33.908085, 37.3847, 35.243877, 36.52057, 36.601246, 35.71527);
((GeneralPath)shape).curveTo(39.804253, 33.356033, 43.34599, 32.142204, 46.39044, 29.38454);
((GeneralPath)shape).curveTo(51.158222, 23.562733, 56.06751, 17.652012, 60.158524, 11.012201);
((GeneralPath)shape).lineTo(52.303017, 5.651286);
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
paint = getColor(255, 105, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.453367, 58.560356);
((GeneralPath)shape).curveTo(24.921202, 51.87652, 20.14228, 46.31722, 15.561088, 40.922623);
((GeneralPath)shape).curveTo(12.476488, 38.590775, 9.128989, 37.236713, 6.027465, 34.97484);
((GeneralPath)shape).curveTo(4.685176, 34.09523, 3.330659, 33.272144, 2.000001, 32.368217);
((GeneralPath)shape).lineTo(9.122363, 26.000004);
((GeneralPath)shape).curveTo(10.443659, 26.82694, 11.751479, 27.691074, 13.08042, 28.49637);
((GeneralPath)shape).curveTo(16.216347, 30.855608, 19.683914, 32.06944, 22.664608, 34.827103);
((GeneralPath)shape).curveTo(27.33254, 40.64891, 32.139015, 46.55963, 36.14436, 53.19944);
((GeneralPath)shape).lineTo(28.453365, 58.560356);
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
paint = getColor(255, 105, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.611887, 5.651286);
((GeneralPath)shape).curveTo(25.079721, 12.335126, 20.3008, 17.894424, 15.719608, 23.28902);
((GeneralPath)shape).curveTo(12.635008, 25.62087, 9.287508, 26.97493, 6.1859846, 29.236805);
((GeneralPath)shape).curveTo(4.843696, 30.116411, 3.489178, 30.9395, 2.15852, 31.843426);
((GeneralPath)shape).lineTo(9.280882, 38.21164);
((GeneralPath)shape).curveTo(10.602178, 37.3847, 11.909999, 36.52057, 13.238939, 35.71527);
((GeneralPath)shape).curveTo(16.374866, 33.356033, 19.842436, 32.142204, 22.823128, 29.38454);
((GeneralPath)shape).curveTo(27.49106, 23.562733, 32.29754, 17.652012, 36.30288, 11.012201);
((GeneralPath)shape).lineTo(28.611887, 5.651286);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_3;
g.setTransform(defaultTransform__0_0_3);
g.setClip(clip__0_0_3);
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
        return 6;
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
	public LeftArrowIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public LeftArrowIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public LeftArrowIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public LeftArrowIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public LeftArrowIcon(int width, int height) {
		this(width, height, false);
	}
	
	public LeftArrowIcon(int width, int height, boolean disabled) {
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

