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
public class DatastoreIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = new Color(206, 38, 38, 255);
shape = new Rectangle2D.Double(19.092864990234375, 1.8962687253952026, 36.287330627441406, 53.14672088623047);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.2325734f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(19.092864990234375, 1.8962687253952026, 36.287330627441406, 53.14672088623047);
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
paint = new Color(106, 18, 18, 255);
shape = new Rectangle2D.Double(10.880569458007812, 9.222550392150879, 36.287330627441406, 53.14672088623047);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.2325734f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(10.880569458007812, 9.222550392150879, 36.287330627441406, 53.14672088623047);
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
paint = new Color(106, 18, 18, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.740345, 1.8628063);
((GeneralPath)shape).lineTo(55.34392, 1.5540226);
((GeneralPath)shape).lineTo(48.05711, 8.674797);
((GeneralPath)shape).lineTo(11.164414, 8.520404);
((GeneralPath)shape).lineTo(18.740345, 1.8628063);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.8041758f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.740345, 1.8628063);
((GeneralPath)shape).lineTo(55.34392, 1.5540226);
((GeneralPath)shape).lineTo(48.05711, 8.674797);
((GeneralPath)shape).lineTo(11.164414, 8.520404);
((GeneralPath)shape).lineTo(18.740345, 1.8628063);
((GeneralPath)shape).closePath();
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
paint = new Color(106, 18, 18, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(48.192608, 9.700176);
((GeneralPath)shape).lineTo(55.633034, 2.4948509);
((GeneralPath)shape).lineTo(55.633034, 55.204884);
((GeneralPath)shape).lineTo(48.258106, 61.75518);
((GeneralPath)shape).lineTo(48.192608, 9.700176);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.2325734f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(48.192608, 9.700176);
((GeneralPath)shape).lineTo(55.633034, 2.4948509);
((GeneralPath)shape).lineTo(55.633034, 55.204884);
((GeneralPath)shape).lineTo(48.258106, 61.75518);
((GeneralPath)shape).lineTo(48.192608, 9.700176);
((GeneralPath)shape).closePath();
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
paint = new LinearGradientPaint(new Point2D.Double(41.546146392822266, 16.15620231628418), new Point2D.Double(16.45088768005371, 32.46992874145508), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 0, 0, 255),new Color(255, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2473779916763306f, 0.0f, 0.0f, 1.217947006225586f, -8.576225280761719f, -6.74007511138916f));
shape = new Rectangle2D.Double(10.988222122192383, 10.975580215454102, 34.3333740234375, 23.574337005615234);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.2110325f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(10.988222122192383, 10.975580215454102, 34.3333740234375, 23.574337005615234);
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
paint = new Color(106, 18, 18, 255);
shape = new Rectangle2D.Double(20.83461570739746, 23.964509963989258, 10.27798080444336, 2.779055118560791);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.2485968f,0,1,4.0f,null,0.0f);
shape = new Rectangle2D.Double(20.83461570739746, 23.964509963989258, 10.27798080444336, 2.779055118560791);
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
paint = new LinearGradientPaint(new Point2D.Double(62.028011322021484, 15.417367935180664), new Point2D.Double(17.210084915161133, 54.31932830810547), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 0, 0, 255),new Color(255, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2473779916763306f, 0.0f, 0.0f, 1.217947006225586f, -8.576225280761719f, -6.74007511138916f));
shape = new Rectangle2D.Double(11.099699974060059, 36.880062103271484, 34.3333740234375, 23.574337005615234);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.2110325f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(11.099699974060059, 36.880062103271484, 34.3333740234375, 23.574337005615234);
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
paint = new Color(106, 18, 18, 255);
shape = new Rectangle2D.Double(20.946090698242188, 49.86898422241211, 10.27798080444336, 2.779055118560791);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.2485968f,0,1,4.0f,null,0.0f);
shape = new Rectangle2D.Double(20.946090698242188, 49.86898422241211, 10.27798080444336, 2.779055118560791);
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
paint = new LinearGradientPaint(new Point2D.Double(41.77030944824219, 10.756301879882812), new Point2D.Double(54.14005661010742, 13.624650001525879), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 0, 0, 255),new Color(255, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2473779916763306f, 0.0f, 0.0f, 1.217947006225586f, -8.576225280761719f, -6.74007511138916f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(48.304417, 9.91852);
((GeneralPath)shape).lineTo(55.744846, 2.7131944);
((GeneralPath)shape).lineTo(55.744846, 55.423225);
((GeneralPath)shape).lineTo(48.369915, 61.973522);
((GeneralPath)shape).lineTo(48.304417, 9.91852);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.2325734f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(48.304417, 9.91852);
((GeneralPath)shape).lineTo(55.744846, 2.7131944);
((GeneralPath)shape).lineTo(55.744846, 55.423225);
((GeneralPath)shape).lineTo(48.369915, 61.973522);
((GeneralPath)shape).lineTo(48.304417, 9.91852);
((GeneralPath)shape).closePath();
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
paint = new LinearGradientPaint(new Point2D.Double(44.45938491821289, 12.750892639160156), new Point2D.Double(42.84593963623047, 6.251910209655762), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 0, 0, 255),new Color(255, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.2473779916763306f, 0.0f, 0.0f, 1.217947006225586f, -8.576225280761719f, -6.74007511138916f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.72678, 1.580513);
((GeneralPath)shape).lineTo(55.330357, 1.2717291);
((GeneralPath)shape).lineTo(48.043545, 8.392504);
((GeneralPath)shape).lineTo(11.150849, 8.23811);
((GeneralPath)shape).lineTo(18.72678, 1.580513);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.8041758f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.72678, 1.580513);
((GeneralPath)shape).lineTo(55.330357, 1.2717291);
((GeneralPath)shape).lineTo(48.043545, 8.392504);
((GeneralPath)shape).lineTo(11.150849, 8.23811);
((GeneralPath)shape).lineTo(18.72678, 1.580513);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_9;
g.setTransform(defaultTransform__0_0_9);
g.setClip(clip__0_0_9);
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
        return 11;
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
	 * Creates a new transcoded SVG image.
	 */
	public DatastoreIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public DatastoreIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public DatastoreIcon(int width, int height) {
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

