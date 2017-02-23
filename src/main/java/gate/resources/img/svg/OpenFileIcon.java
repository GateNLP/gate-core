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
public class OpenFileIcon implements
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
g.transform(new AffineTransform(0.9666919112205505f, 0.0f, 0.0f, 0.9666919112205505f, 0.13174408674240112f, 1.072211503982544f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(-0.8809540271759033f, -0.47320300340652466f, 0.365772008895874f, -0.9307050108909607f, 0.0f, 0.0f));
// _0_0_0_0 is ShapeNode
paint = new Color(255, 97, 0, 255);
shape = new Rectangle2D.Double(-65.68597412109375, -33.63661575317383, 48.274662017822266, 40.721622467041016);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.1889937f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(-65.68597412109375, -33.63661575317383, 48.274662017822266, 40.721622467041016);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_0;
g.setTransform(defaultTransform__0_0_0_0);
g.setClip(clip__0_0_0_0);
float alpha__0_0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(-0.8829659819602966f, -0.46943598985671997f, 0.4961700141429901f, -0.8682249784469604f, 0.0f, 0.0f));
// _0_0_0_1 is ShapeNode
paint = new Color(245, 176, 134, 255);
shape = new Rectangle2D.Double(-70.21683502197266, -33.98326873779297, 48.297786712646484, 36.089561462402344);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.1195971f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(-70.21683502197266, -33.98326873779297, 48.297786712646484, 36.089561462402344);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_1;
g.setTransform(defaultTransform__0_0_0_1);
g.setClip(clip__0_0_0_1);
float alpha__0_0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(-0.8829659819602966f, -0.46943598985671997f, 0.4961700141429901f, -0.8682249784469604f, 0.0f, 0.0f));
// _0_0_0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-30.031288146972656, -25.22644805908203), new Point2D.Double(-49.839988708496094, 1.818079948425293), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 97, 0, 255),new Color(255, 97, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new Rectangle2D.Double(-70.54749298095703, -33.77324676513672, 48.2977180480957, 36.08952713012695);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.1195956f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(-70.54749298095703, -33.77324676513672, 48.2977180480957, 36.08952713012695);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_2;
g.setTransform(defaultTransform__0_0_0_2);
g.setClip(clip__0_0_0_2);
float alpha__0_0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_0_3 = g.getTransform();
g.transform(new AffineTransform(0.8777790069580078f, 0.4790650010108948f, -0.4379430115222931f, 0.8990030288696289f, 0.0f, 0.0f));
// _0_0_0_3 is ShapeNode
paint = new Color(245, 176, 134, 255);
shape = new Rectangle2D.Double(21.096359252929688, 19.323244094848633, 47.253204345703125, 4.883510589599609);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.96075296f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(21.096359252929688, 19.323244094848633, 47.253204345703125, 4.883510589599609);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_3;
g.setTransform(defaultTransform__0_0_0_3);
g.setClip(clip__0_0_0_3);
float alpha__0_0_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_0_4 = g.getTransform();
g.transform(new AffineTransform(0.8818719983100891f, 0.47148799896240234f, -0.47148799896240234f, 0.8818719983100891f, 0.0f, 0.0f));
// _0_0_0_4 is CompositeGraphicsNode
float alpha__0_0_0_4_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_4_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_4_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.939804, 9.99171);
((GeneralPath)shape).lineTo(49.967144, 9.99171);
((GeneralPath)shape).lineTo(49.967144, 10.9878025);
((GeneralPath)shape).lineTo(46.123398, 10.9878025);
((GeneralPath)shape).lineTo(46.123398, 13.565926);
((GeneralPath)shape).lineTo(49.592144, 13.565926);
((GeneralPath)shape).lineTo(49.592144, 14.562018);
((GeneralPath)shape).lineTo(46.123398, 14.562018);
((GeneralPath)shape).lineTo(46.123398, 18.73975);
((GeneralPath)shape).lineTo(44.939804, 18.73975);
((GeneralPath)shape).lineTo(44.939804, 9.99171);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_4_0;
g.setTransform(defaultTransform__0_0_0_4_0);
g.setClip(clip__0_0_0_4_0);
float alpha__0_0_0_4_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_4_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_4_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4_1 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(51.85386, 9.99171);
((GeneralPath)shape).lineTo(53.037453, 9.99171);
((GeneralPath)shape).lineTo(53.037453, 18.73975);
((GeneralPath)shape).lineTo(51.85386, 18.73975);
((GeneralPath)shape).lineTo(51.85386, 9.99171);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_4_1;
g.setTransform(defaultTransform__0_0_0_4_1);
g.setClip(clip__0_0_0_4_1);
float alpha__0_0_0_4_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_4_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_4_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4_2 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(55.39292, 9.99171);
((GeneralPath)shape).lineTo(56.576515, 9.99171);
((GeneralPath)shape).lineTo(56.576515, 17.743656);
((GeneralPath)shape).lineTo(60.836277, 17.743656);
((GeneralPath)shape).lineTo(60.836277, 18.73975);
((GeneralPath)shape).lineTo(55.39292, 18.73975);
((GeneralPath)shape).lineTo(55.39292, 9.99171);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_4_2;
g.setTransform(defaultTransform__0_0_0_4_2);
g.setClip(clip__0_0_0_4_2);
float alpha__0_0_0_4_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_4_3 = g.getClip();
AffineTransform defaultTransform__0_0_0_4_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4_3 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(62.0726, 9.99171);
((GeneralPath)shape).lineTo(67.60385, 9.99171);
((GeneralPath)shape).lineTo(67.60385, 10.9878025);
((GeneralPath)shape).lineTo(63.2562, 10.9878025);
((GeneralPath)shape).lineTo(63.2562, 13.577644);
((GeneralPath)shape).lineTo(67.42221, 13.577644);
((GeneralPath)shape).lineTo(67.42221, 14.573737);
((GeneralPath)shape).lineTo(63.2562, 14.573737);
((GeneralPath)shape).lineTo(63.2562, 17.743656);
((GeneralPath)shape).lineTo(67.70932, 17.743656);
((GeneralPath)shape).lineTo(67.70932, 18.73975);
((GeneralPath)shape).lineTo(62.072605, 18.73975);
((GeneralPath)shape).lineTo(62.072605, 9.99171);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_4_3;
g.setTransform(defaultTransform__0_0_0_4_3);
g.setClip(clip__0_0_0_4_3);
origAlpha = alpha__0_0_0_4;
g.setTransform(defaultTransform__0_0_0_4);
g.setClip(clip__0_0_0_4);
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
        return 2;
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
	public OpenFileIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public OpenFileIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public OpenFileIcon(int width, int height) {
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

