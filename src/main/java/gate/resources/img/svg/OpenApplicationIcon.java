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
public class OpenApplicationIcon implements
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
g.transform(new AffineTransform(-0.8809540271759033f, -0.47320300340652466f, 0.365772008895874f, -0.9307050108909607f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = getColor(255, 97, 0, 255, disabled);
shape = new Rectangle2D.Double(-65.68597412109375, -33.63661575317383, 48.274662017822266, 40.721622467041016);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.1889937f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(-65.68597412109375, -33.63661575317383, 48.274662017822266, 40.721622467041016);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 18.7677001953125f, -2.2307469844818115f));
// _0_0_1 is CompositeGraphicsNode
float alpha__0_0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_0 = g.getClip();
AffineTransform defaultTransform__0_0_1_0 = g.getTransform();
g.transform(new AffineTransform(0.5496010184288025f, 0.7062069773674011f, -0.6977810263633728f, 0.5562379956245422f, 26.523630142211914f, -19.788820266723633f));
// _0_0_1_0 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
shape = new Ellipse2D.Double(29.454544067382812, 28.545455932617188, 6.0, 6.181818008422852);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1_0;
g.setTransform(defaultTransform__0_0_1_0);
g.setClip(clip__0_0_1_0);
float alpha__0_0_1_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_1 = g.getClip();
AffineTransform defaultTransform__0_0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_1 is ShapeNode
paint = getColor(255, 0, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.09375, 4.59375);
((GeneralPath)shape).curveTo(13.244158, 5.781618, 21.069134, 13.490205, 17.96875, 16.0625);
((GeneralPath)shape).curveTo(14.868366, 18.634794, 8.760565, 9.471451, 6.875, 13.03125);
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003635, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.54975, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339485, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876114, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817188, 20.04152, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.9311628, 26.792248, 14.944821, 22.78125, 14.5625);
((GeneralPath)shape).curveTo(18.770252, 14.180179, 20.943342, 3.405882, 17.09375, 4.59375);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.34375, 15.96875);
((GeneralPath)shape).curveTo(25.021523, 15.96875, 27.21875, 18.086975, 27.21875, 20.71875);
((GeneralPath)shape).curveTo(27.218752, 23.350523, 25.021523, 25.5, 22.34375, 25.5);
((GeneralPath)shape).curveTo(19.665981, 25.5, 17.5, 23.350525, 17.5, 20.71875);
((GeneralPath)shape).curveTo(17.5, 18.086975, 19.665981, 15.96875, 22.34375, 15.96875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.02955f,0,2,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.09375, 4.59375);
((GeneralPath)shape).curveTo(13.244158, 5.781618, 21.069134, 13.490205, 17.96875, 16.0625);
((GeneralPath)shape).curveTo(14.868366, 18.634794, 8.760565, 9.471451, 6.875, 13.03125);
((GeneralPath)shape).curveTo(4.9894347, 16.59105, 16.003635, 16.488544, 15.625, 20.5);
((GeneralPath)shape).curveTo(15.246366, 24.511457, 4.4121246, 22.367374, 5.59375, 26.21875);
((GeneralPath)shape).curveTo(6.7753754, 30.070126, 14.528686, 22.238714, 17.09375, 25.34375);
((GeneralPath)shape).curveTo(19.658813, 28.448786, 10.503517, 34.54975, 14.0625, 36.4375);
((GeneralPath)shape).curveTo(17.621483, 38.325253, 17.52045, 27.313276, 21.53125, 27.6875);
((GeneralPath)shape).curveTo(25.542051, 28.061724, 23.39731, 38.86644, 27.25, 37.6875);
((GeneralPath)shape).curveTo(31.102692, 36.50856, 23.274061, 28.788654, 26.375, 26.21875);
((GeneralPath)shape).curveTo(29.475939, 23.648846, 35.584667, 32.81367, 37.46875, 29.25);
((GeneralPath)shape).curveTo(39.352833, 25.686329, 28.339485, 25.762918, 28.71875, 21.75);
((GeneralPath)shape).curveTo(29.098017, 17.737082, 39.876114, 19.880157, 38.6875, 16.03125);
((GeneralPath)shape).curveTo(37.498886, 12.182344, 29.817188, 20.04152, 27.25, 16.9375);
((GeneralPath)shape).curveTo(24.682814, 13.833482, 33.812073, 7.693837, 30.25, 5.8125);
((GeneralPath)shape).curveTo(26.687927, 3.9311628, 26.792248, 14.944821, 22.78125, 14.5625);
((GeneralPath)shape).curveTo(18.770252, 14.180179, 20.943342, 3.405882, 17.09375, 4.59375);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.34375, 15.96875);
((GeneralPath)shape).curveTo(25.021523, 15.96875, 27.21875, 18.086975, 27.21875, 20.71875);
((GeneralPath)shape).curveTo(27.218752, 23.350523, 25.021523, 25.5, 22.34375, 25.5);
((GeneralPath)shape).curveTo(19.665981, 25.5, 17.5, 23.350525, 17.5, 20.71875);
((GeneralPath)shape).curveTo(17.5, 18.086975, 19.665981, 15.96875, 22.34375, 15.96875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1_1;
g.setTransform(defaultTransform__0_0_1_1);
g.setClip(clip__0_0_1_1);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(-0.8829659819602966f, -0.46943598985671997f, 0.4961700141429901f, -0.8682249784469604f, 0.0f, 0.0f));
// _0_0_2 is ShapeNode
paint = getColor(245, 176, 134, 255, disabled);
shape = new Rectangle2D.Double(-70.21683502197266, -33.98326873779297, 48.297786712646484, 36.089561462402344);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.1195971f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(-70.21683502197266, -33.98326873779297, 48.297786712646484, 36.089561462402344);
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
g.transform(new AffineTransform(-0.8829659819602966f, -0.46943598985671997f, 0.4961700141429901f, -0.8682249784469604f, 0.0f, 0.0f));
// _0_0_3 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-30.031288146972656, -25.22644805908203), new Point2D.Double(-49.839988708496094, 1.818079948425293), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 97, 0, 255, disabled),getColor(255, 97, 0, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new Rectangle2D.Double(-70.54749298095703, -33.77324676513672, 48.2977180480957, 36.08952713012695);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.1195956f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(-70.54749298095703, -33.77324676513672, 48.2977180480957, 36.08952713012695);
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
g.transform(new AffineTransform(0.8777790069580078f, 0.4790650010108948f, -0.4379430115222931f, 0.8990030288696289f, 0.0f, 0.0f));
// _0_0_4 is ShapeNode
paint = getColor(245, 176, 134, 255, disabled);
shape = new Rectangle2D.Double(21.096359252929688, 19.323244094848633, 47.253204345703125, 4.883510589599609);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.96075296f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(21.096359252929688, 19.323244094848633, 47.253204345703125, 4.883510589599609);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_4;
g.setTransform(defaultTransform__0_0_4);
g.setClip(clip__0_0_4);
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
        return 2;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 1;
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
	public OpenApplicationIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public OpenApplicationIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public OpenApplicationIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public OpenApplicationIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public OpenApplicationIcon(int width, int height) {
		this(width, height, false);
	}
	
	public OpenApplicationIcon(int width, int height, boolean disabled) {
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

