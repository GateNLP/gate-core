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
public class NeTransducerIcon implements
		javax.swing.Icon {
	/**
	 * Paints the transcoded SVG image on the specified graphics context. You
	 * can install a custom transformation on the graphics context to scale the
	 * image.
	 * 
	 * @param g
	 *            Graphics context.
	 */
	public static void paint(Graphics2D g) {
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 6.066843032836914f, 2.331973075866699f));
// _0_0_0 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(21.636364, 28.545456);
((GeneralPath)shape).curveTo(34.691933, 13.657528, -9.460201, 22.705843, 9.852878, 18.335127);
((GeneralPath)shape).curveTo(29.165956, 13.964411, -14.579854, 24.808157, 3.613797, 32.62412);
((GeneralPath)shape).curveTo(21.807447, 40.44008, -0.44176996, 1.2449863, 9.683098, 18.26219);
((GeneralPath)shape).curveTo(19.807964, 35.279392, -4.023249, -2.974442, -5.834521, 16.744013);
((GeneralPath)shape).curveTo(-7.645793, 36.462467, 22.755568, 3.190255, 9.700001, 18.078182);
((GeneralPath)shape).curveTo(-3.3555663, 32.966106, 25.661743, -1.519809, 6.3486648, 2.8509066);
((GeneralPath)shape).curveTo(-12.964414, 7.2216225, 28.073877, 25.853354, 9.880226, 18.037395);
((GeneralPath)shape).curveTo(-8.313426, 10.221436, 33.451473, 27.161802, 23.326605, 10.144599);
((GeneralPath)shape).curveTo(13.201738, -6.8726053, 8.163438, 37.91465, 9.9747095, 18.196196);
((GeneralPath)shape).curveTo(11.78598, -1.522259, 8.580797, 43.43338, 21.636364, 28.545456);
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
// _0_0_1 is TextNode of 'NE'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(35.902626, 49.474262);
((GeneralPath)shape).quadTo(35.902626, 51.32452, 34.635696, 51.32452);
((GeneralPath)shape).quadTo(33.715126, 51.32452, 32.56669, 50.176083);
((GeneralPath)shape).lineTo(20.307573, 37.85317);
((GeneralPath)shape).lineTo(20.307573, 51.124);
((GeneralPath)shape).lineTo(16.99898, 51.124);
((GeneralPath)shape).lineTo(16.99898, 33.651344);
((GeneralPath)shape).quadTo(16.99898, 32.976864, 17.363562, 32.489235);
((GeneralPath)shape).quadTo(17.728146, 32.001606, 18.402624, 32.001606);
((GeneralPath)shape).quadTo(19.323198, 32.001606, 20.307573, 32.976864);
((GeneralPath)shape).lineTo(32.56669, 45.272438);
((GeneralPath)shape).lineTo(32.56669, 32.001606);
((GeneralPath)shape).lineTo(35.902626, 32.001606);
((GeneralPath)shape).lineTo(35.902626, 49.474262);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(56.06136, 43.084938);
((GeneralPath)shape).lineTo(45.14209, 43.084938);
((GeneralPath)shape).lineTo(45.14209, 39.758114);
((GeneralPath)shape).lineTo(56.06136, 39.758114);
((GeneralPath)shape).lineTo(56.06136, 43.084938);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(56.927246, 51.124);
((GeneralPath)shape).lineTo(40.41162, 51.124);
((GeneralPath)shape).lineTo(40.41162, 32.001606);
((GeneralPath)shape).lineTo(56.927246, 32.001606);
((GeneralPath)shape).lineTo(56.927246, 35.328426);
((GeneralPath)shape).lineTo(43.73844, 35.328426);
((GeneralPath)shape).lineTo(43.73844, 47.788063);
((GeneralPath)shape).lineTo(56.927246, 47.788063);
((GeneralPath)shape).lineTo(56.927246, 51.124);
((GeneralPath)shape).closePath();
paint = new Color(0, 0, 0, 255);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
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
        return 0;
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
	 * Creates a new transcoded SVG image.
	 */
	public NeTransducerIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public NeTransducerIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public NeTransducerIcon(int width, int height) {
	this.width = width;
	this.height = height;
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
		paint(g2d);
		g2d.dispose();
	}
}

