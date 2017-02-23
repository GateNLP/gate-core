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
public class UpdatesIcon implements
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
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,48.0,48.0)));
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
g.transform(new AffineTransform(-0.6902143955230713f, 0.1552145928144455f, 0.1541592925786972f, 0.6855536103248596f, 26.500173568725586f, 22.674951553344727f));
// _0_0_0 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(-9.624268531799316, -13.159472465515137), 29.191137f, new Point2D.Double(-9.624268531799316, -13.159472465515137), new float[] {0.0f,1.0f}, new Color[] {new Color(252, 175, 62, 255),new Color(245, 121, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.1446621417999268f, -0.2591603100299835f, 0.2642988860607147f, 1.183316946029663f, 6.5723700523376465f, 9.676287651062012f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(13.46404, 13.377538);
((GeneralPath)shape).lineTo(12.552308, 26.55842);
((GeneralPath)shape).lineTo(2.4276383, 18.069725);
((GeneralPath)shape).lineTo(-6.7432966, 27.580818);
((GeneralPath)shape).lineTo(-9.042815, 14.570088);
((GeneralPath)shape).lineTo(-22.18177, 15.961043);
((GeneralPath)shape).lineTo(-15.580168, 4.5161448);
((GeneralPath)shape).lineTo(-26.53928, -2.8638792);
((GeneralPath)shape).lineTo(-14.12552, -7.3877525);
((GeneralPath)shape).lineTo(-17.7769, -20.08556);
((GeneralPath)shape).lineTo(-5.359518, -15.571636);
((GeneralPath)shape).lineTo(0.0053567886, -27.645782);
((GeneralPath)shape).lineTo(6.6161294, -16.206179);
((GeneralPath)shape).lineTo(18.486973, -22.007036);
((GeneralPath)shape).lineTo(16.197884, -8.994468);
((GeneralPath)shape).lineTo(29.020199, -5.8077507);
((GeneralPath)shape).lineTo(18.902336, 2.6890564);
((GeneralPath)shape).lineTo(26.676416, 13.372249);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(206, 92, 0, 255);
stroke = new BasicStroke(1.4183232f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(13.46404, 13.377538);
((GeneralPath)shape).lineTo(12.552308, 26.55842);
((GeneralPath)shape).lineTo(2.4276383, 18.069725);
((GeneralPath)shape).lineTo(-6.7432966, 27.580818);
((GeneralPath)shape).lineTo(-9.042815, 14.570088);
((GeneralPath)shape).lineTo(-22.18177, 15.961043);
((GeneralPath)shape).lineTo(-15.580168, 4.5161448);
((GeneralPath)shape).lineTo(-26.53928, -2.8638792);
((GeneralPath)shape).lineTo(-14.12552, -7.3877525);
((GeneralPath)shape).lineTo(-17.7769, -20.08556);
((GeneralPath)shape).lineTo(-5.359518, -15.571636);
((GeneralPath)shape).lineTo(0.0053567886, -27.645782);
((GeneralPath)shape).lineTo(6.6161294, -16.206179);
((GeneralPath)shape).lineTo(18.486973, -22.007036);
((GeneralPath)shape).lineTo(16.197884, -8.994468);
((GeneralPath)shape).lineTo(29.020199, -5.8077507);
((GeneralPath)shape).lineTo(18.902336, 2.6890564);
((GeneralPath)shape).lineTo(26.676416, 13.372249);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 0.46551728f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(-0.6834225058555603f, 0.15368950366973877f, 0.15264229476451874f, 0.678817093372345f, 26.403013229370117f, 22.680437088012695f));
// _0_0_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-10.618273735046387, -9.039712905883789), new Point2D.Double(18.455230712890625, 35.77920913696289), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.4324083f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(0.15625, -24.84375);
((GeneralPath)shape).lineTo(-4.21875, -15.03125);
((GeneralPath)shape).curveTo(-4.490221, -14.422337, -5.183292, -14.123364, -5.8125, -14.34375);
((GeneralPath)shape).lineTo(-15.875, -18.03125);
((GeneralPath)shape).lineTo(-12.90625, -7.71875);
((GeneralPath)shape).curveTo(-12.716198, -7.0735936, -13.0573435, -6.391302, -13.6875, -6.15625);
((GeneralPath)shape).lineTo(-23.71875, -2.53125);
((GeneralPath)shape).lineTo(-14.875, 3.46875);
((GeneralPath)shape).curveTo(-14.321167, 3.8398852, -14.144502, 4.5737247, -14.46875, 5.15625);
((GeneralPath)shape).lineTo(-19.8125, 14.40625);
((GeneralPath)shape).lineTo(-9.15625, 13.28125);
((GeneralPath)shape).curveTo(-8.491077, 13.2230215, -7.8927035, 13.685401, -7.78125, 14.34375);
((GeneralPath)shape).lineTo(-5.9375, 24.90625);
((GeneralPath)shape).lineTo(1.5, 17.1875);
((GeneralPath)shape).curveTo(1.9674098, 16.69981, 2.7331622, 16.658787, 3.25, 17.09375);
((GeneralPath)shape).lineTo(11.4375, 23.96875);
((GeneralPath)shape).lineTo(12.1875, 13.28125);
((GeneralPath)shape).curveTo(12.236707, 12.610485, 12.7961855, 12.091944, 13.46875, 12.09375);
((GeneralPath)shape).lineTo(24.1875, 12.09375);
((GeneralPath)shape).lineTo(17.875, 3.4375);
((GeneralPath)shape).curveTo(17.490778, 2.8946424, 17.585796, 2.148058, 18.09375, 1.71875);
((GeneralPath)shape).lineTo(26.28125, -5.15625);
((GeneralPath)shape).lineTo(15.875, -7.75);
((GeneralPath)shape).curveTo(15.22645, -7.9190264, 14.817751, -8.55932, 14.9375, -9.21875);
((GeneralPath)shape).lineTo(16.8125, -19.75);
((GeneralPath)shape).lineTo(7.1875, -15.0625);
((GeneralPath)shape).curveTo(6.5892477, -14.774994, 5.8704762, -14.9919815, 5.53125, -15.5625);
((GeneralPath)shape).lineTo(0.15625, -24.84375);
((GeneralPath)shape).closePath();
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
g.transform(new AffineTransform(-1.0f, 0.0f, 0.0f, 1.0f, 49.951168060302734f, 0.0f));
// _0_0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.6875, 21.75), new Point2D.Double(28.4375, 26.4375), new float[] {0.0f,1.0f}, new Color[] {new Color(245, 121, 0, 255),new Color(252, 175, 62, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.90625, 15.0);
((GeneralPath)shape).curveTo(22.391886, 15.050546, 21.99983, 15.483158, 22.0, 16.0);
((GeneralPath)shape).lineTo(22.0, 22.0);
((GeneralPath)shape).lineTo(19.0, 22.0);
((GeneralPath)shape).curveTo(18.62271, 22.00037, 18.277443, 22.21213, 18.106083, 22.54826);
((GeneralPath)shape).curveTo(17.934721, 22.884392, 17.966167, 23.2882, 18.1875, 23.59375);
((GeneralPath)shape).lineTo(24.1875, 32.09375);
((GeneralPath)shape).curveTo(24.37651, 32.353764, 24.678547, 32.507614, 25.0, 32.507614);
((GeneralPath)shape).curveTo(25.321453, 32.507614, 25.62349, 32.353764, 25.8125, 32.09375);
((GeneralPath)shape).lineTo(31.8125, 23.59375);
((GeneralPath)shape).curveTo(32.033833, 23.2882, 32.065277, 22.884392, 31.893917, 22.54826);
((GeneralPath)shape).curveTo(31.722557, 22.21213, 31.37729, 22.00037, 31.0, 22.0);
((GeneralPath)shape).lineTo(28.0, 22.0);
((GeneralPath)shape).lineTo(28.0, 16.0);
((GeneralPath)shape).curveTo(27.997543, 15.448736, 27.551264, 15.002456, 27.0, 15.0);
((GeneralPath)shape).lineTo(23.0, 15.0);
((GeneralPath)shape).curveTo(22.968767, 14.998541, 22.937483, 14.998541, 22.90625, 15.0);
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
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(24.951168, 31.5);
((GeneralPath)shape).lineTo(30.951168, 23.0);
((GeneralPath)shape).lineTo(26.951168, 23.0);
((GeneralPath)shape).lineTo(26.951168, 16.0);
((GeneralPath)shape).lineTo(22.951168, 16.0);
((GeneralPath)shape).lineTo(22.951168, 23.0);
((GeneralPath)shape).lineTo(18.951168, 23.0);
((GeneralPath)shape).lineTo(24.951168, 31.5);
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
paint = new RadialGradientPaint(new Point2D.Double(27.5, 19.095653533935547), 0.50000006f, new Point2D.Double(27.5, 19.095653533935547), new float[] {0.0f,1.0f}, new Color[] {new Color(252, 175, 62, 255),new Color(252, 175, 62, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-15.000007629394531f, 0.0f, 0.0f, 19.2246150970459f, 434.951416015625f, -346.9815979003906f));
stroke = new BasicStroke(1.0000001f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.451168, 21.929392);
((GeneralPath)shape).lineTo(22.451168, 16.07061);
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
	 * Creates a new transcoded SVG image.
	 */
	public UpdatesIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public UpdatesIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public UpdatesIcon(int width, int height) {
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

