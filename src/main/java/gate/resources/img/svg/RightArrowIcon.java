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
public class RightArrowIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 6.000000212225132E-7f, 2.4000000848900527E-6f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0 is ShapeNode
paint = getColor(255, 105, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.980795, 5.6512823);
((GeneralPath)shape).curveTo(13.588514, 12.335122, 18.469658, 17.89442, 23.148842, 23.289017);
((GeneralPath)shape).curveTo(26.29942, 25.620867, 29.718521, 26.974924, 32.886387, 29.236801);
((GeneralPath)shape).curveTo(34.257393, 30.116407, 35.64088, 30.939497, 37.0, 31.843424);
((GeneralPath)shape).lineTo(29.72529, 38.211636);
((GeneralPath)shape).curveTo(28.37573, 37.3847, 27.039936, 36.520565, 25.682573, 35.715267);
((GeneralPath)shape).curveTo(22.479568, 33.356033, 18.937826, 32.1422, 15.893375, 29.384539);
((GeneralPath)shape).curveTo(11.125596, 23.56273, 6.216308, 17.65201, 2.1252918, 11.012199);
((GeneralPath)shape).lineTo(9.980795, 5.6512823);
((GeneralPath)shape).closePath();
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
// _0_0_0_1 is ShapeNode
paint = getColor(255, 105, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.855503, 58.560352);
((GeneralPath)shape).curveTo(13.463222, 51.876514, 18.344366, 46.317215, 23.02355, 40.92262);
((GeneralPath)shape).curveTo(26.174128, 38.59077, 29.59323, 37.23671, 32.761097, 34.974834);
((GeneralPath)shape).curveTo(34.132095, 34.09523, 35.515587, 33.27214, 36.874706, 32.368214);
((GeneralPath)shape).lineTo(29.599998, 26.0);
((GeneralPath)shape).curveTo(28.250439, 26.826939, 26.914644, 27.69107, 25.557278, 28.496367);
((GeneralPath)shape).curveTo(22.35427, 30.855604, 18.812534, 32.069435, 15.768083, 34.8271);
((GeneralPath)shape).curveTo(11.000304, 40.648907, 6.0910163, 46.559628, 2.0, 53.199436);
((GeneralPath)shape).lineTo(9.855503, 58.560352);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_1;
g.setTransform(defaultTransform__0_0_0_1);
g.setClip(clip__0_0_0_1);
float alpha__0_0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_2 is ShapeNode
paint = getColor(255, 105, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(33.705154, 5.6512823);
((GeneralPath)shape).curveTo(37.237316, 12.335122, 42.01624, 17.89442, 46.59743, 23.289017);
((GeneralPath)shape).curveTo(49.682034, 25.620867, 53.02953, 26.974924, 56.131054, 29.236801);
((GeneralPath)shape).curveTo(57.473343, 30.116407, 58.82786, 30.939497, 60.15852, 31.843424);
((GeneralPath)shape).lineTo(53.036156, 38.211636);
((GeneralPath)shape).curveTo(51.714863, 37.3847, 50.40704, 36.520565, 49.0781, 35.715267);
((GeneralPath)shape).curveTo(45.942173, 33.356033, 42.474606, 32.1422, 39.49391, 29.384539);
((GeneralPath)shape).curveTo(34.825977, 23.56273, 30.019503, 17.65201, 26.014158, 11.012199);
((GeneralPath)shape).lineTo(33.705154, 5.6512823);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_2;
g.setTransform(defaultTransform__0_0_0_2);
g.setClip(clip__0_0_0_2);
float alpha__0_0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3 is ShapeNode
paint = getColor(255, 105, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(33.546635, 58.560352);
((GeneralPath)shape).curveTo(37.078796, 51.876514, 41.85772, 46.317215, 46.43891, 40.92262);
((GeneralPath)shape).curveTo(49.523514, 38.59077, 52.871014, 37.23671, 55.972534, 34.974834);
((GeneralPath)shape).curveTo(57.314823, 34.09523, 58.669342, 33.27214, 60.0, 32.368214);
((GeneralPath)shape).lineTo(52.87764, 26.0);
((GeneralPath)shape).curveTo(51.556343, 26.826939, 50.24852, 27.69107, 48.919582, 28.496367);
((GeneralPath)shape).curveTo(45.783653, 30.855604, 42.316086, 32.069435, 39.335392, 34.8271);
((GeneralPath)shape).curveTo(34.667458, 40.648907, 29.860983, 46.559628, 25.855639, 53.199436);
((GeneralPath)shape).lineTo(33.546635, 58.560352);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_3;
g.setTransform(defaultTransform__0_0_0_3);
g.setClip(clip__0_0_0_3);
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
	public RightArrowIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public RightArrowIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public RightArrowIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public RightArrowIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public RightArrowIcon(int width, int height) {
		this(width, height, false);
	}
	
	public RightArrowIcon(int width, int height, boolean disabled) {
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

