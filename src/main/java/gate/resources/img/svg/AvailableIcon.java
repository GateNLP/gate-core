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
public class AvailableIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(35.39246368408203, 39.27751541137695), new Point2D.Double(14.344165802001953, 16.685270309448242), new float[] {0.0f,1.0f}, new Color[] {new Color(84, 154, 16, 255),new Color(138, 226, 52, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.8936172127723694f, 0.0f, 0.0f, 0.8936172127723694f, 46.13726806640625f, 2.1063826084136963f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.40295, 6.1635723);
((GeneralPath)shape).curveTo(31.46335, 7.3352723, 29.531975, 9.249755, 29.52396, 10.328466);
((GeneralPath)shape).curveTo(29.52056, 10.786796, 29.59641, 11.970337, 29.77529, 12.382988);
((GeneralPath)shape).lineTo(37.678215, 12.382988);
((GeneralPath)shape).lineTo(37.700787, 22.245045);
((GeneralPath)shape).curveTo(41.07614, 22.186525, 41.815166, 20.866453, 43.93389, 21.394648);
((GeneralPath)shape).curveTo(45.287807, 22.840673, 45.478245, 28.458887, 41.28859, 28.483158);
((GeneralPath)shape).curveTo(39.741554, 28.513008, 38.71129, 27.58351, 37.678215, 27.561615);
((GeneralPath)shape).lineTo(37.678215, 40.994698);
((GeneralPath)shape).lineTo(27.82449, 40.962788);
((GeneralPath)shape).curveTo(27.671076, 39.547867, 29.66375, 36.71553, 29.63167, 34.88699);
((GeneralPath)shape).curveTo(29.5683, 30.70475, 20.658886, 29.280792, 20.671562, 34.88699);
((GeneralPath)shape).curveTo(20.608192, 37.51548, 22.357033, 36.77182, 22.46036, 40.9947);
((GeneralPath)shape).curveTo(22.12214, 40.9947, 15.823156, 40.9947, 12.469433, 40.9947);
((GeneralPath)shape).curveTo(12.469433, 40.9947, 12.508703, 28.872433, 12.497903, 28.554577);
((GeneralPath)shape).curveTo(12.407633, 25.852243, 9.479917, 29.261396, 7.1484437, 29.31815);
((GeneralPath)shape).curveTo(3.9746277, 29.23703, 3.1399207, 21.366337, 7.1536436, 21.268126);
((GeneralPath)shape).curveTo(9.008282, 21.368618, 10.146192, 22.544739, 12.492897, 22.47608);
((GeneralPath)shape).lineTo(12.501397, 12.382993);
((GeneralPath)shape).lineTo(23.336506, 12.382993);
((GeneralPath)shape).curveTo(23.3806, 10.95123, 21.565939, 7.989194, 21.597126, 6.2114453);
((GeneralPath)shape).curveTo(21.630297, 4.022305, 23.668034, 3.4443445, 26.184893, 3.4468172);
((GeneralPath)shape).curveTo(29.44792, 3.4499872, 31.40883, 4.267865, 31.402979, 6.1635733);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(66, 132, 4, 255);
stroke = new BasicStroke(0.8936167f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.40295, 6.1635723);
((GeneralPath)shape).curveTo(31.46335, 7.3352723, 29.531975, 9.249755, 29.52396, 10.328466);
((GeneralPath)shape).curveTo(29.52056, 10.786796, 29.59641, 11.970337, 29.77529, 12.382988);
((GeneralPath)shape).lineTo(37.678215, 12.382988);
((GeneralPath)shape).lineTo(37.700787, 22.245045);
((GeneralPath)shape).curveTo(41.07614, 22.186525, 41.815166, 20.866453, 43.93389, 21.394648);
((GeneralPath)shape).curveTo(45.287807, 22.840673, 45.478245, 28.458887, 41.28859, 28.483158);
((GeneralPath)shape).curveTo(39.741554, 28.513008, 38.71129, 27.58351, 37.678215, 27.561615);
((GeneralPath)shape).lineTo(37.678215, 40.994698);
((GeneralPath)shape).lineTo(27.82449, 40.962788);
((GeneralPath)shape).curveTo(27.671076, 39.547867, 29.66375, 36.71553, 29.63167, 34.88699);
((GeneralPath)shape).curveTo(29.5683, 30.70475, 20.658886, 29.280792, 20.671562, 34.88699);
((GeneralPath)shape).curveTo(20.608192, 37.51548, 22.357033, 36.77182, 22.46036, 40.9947);
((GeneralPath)shape).curveTo(22.12214, 40.9947, 15.823156, 40.9947, 12.469433, 40.9947);
((GeneralPath)shape).curveTo(12.469433, 40.9947, 12.508703, 28.872433, 12.497903, 28.554577);
((GeneralPath)shape).curveTo(12.407633, 25.852243, 9.479917, 29.261396, 7.1484437, 29.31815);
((GeneralPath)shape).curveTo(3.9746277, 29.23703, 3.1399207, 21.366337, 7.1536436, 21.268126);
((GeneralPath)shape).curveTo(9.008282, 21.368618, 10.146192, 22.544739, 12.492897, 22.47608);
((GeneralPath)shape).lineTo(12.501397, 12.382993);
((GeneralPath)shape).lineTo(23.336506, 12.382993);
((GeneralPath)shape).curveTo(23.3806, 10.95123, 21.565939, 7.989194, 21.597126, 6.2114453);
((GeneralPath)shape).curveTo(21.630297, 4.022305, 23.668034, 3.4443445, 26.184893, 3.4468172);
((GeneralPath)shape).curveTo(29.44792, 3.4499872, 31.40883, 4.267865, 31.402979, 6.1635733);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 0.7f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(16.46140480041504, 15.329671859741211), new Point2D.Double(35.40412902832031, 43.02977752685547), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.8936172127723694f, 0.0f, 0.0f, 0.8936172127723694f, 46.13726806640625f, 2.1063826084136963f));
stroke = new BasicStroke(0.8936171f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(30.502682, 6.15792);
((GeneralPath)shape).curveTo(30.432901, 7.4562697, 28.517963, 8.324206, 28.523886, 10.996136);
((GeneralPath)shape).curveTo(28.52398, 11.037886, 29.03464, 13.233776, 29.115795, 13.268136);
((GeneralPath)shape).lineTo(36.702206, 13.284096);
((GeneralPath)shape).lineTo(36.690117, 23.04264);
((GeneralPath)shape).curveTo(40.86615, 23.401447, 40.074345, 22.327942, 43.301483, 22.100058);
((GeneralPath)shape).curveTo(44.056362, 23.45384, 44.47935, 27.545666, 41.209206, 27.568924);
((GeneralPath)shape).curveTo(40.020084, 27.642654, 38.912888, 26.639105, 36.72685, 26.685825);
((GeneralPath)shape).lineTo(36.66264, 40.082546);
((GeneralPath)shape).curveTo(33.314754, 40.072346, 30.769432, 40.107285, 28.885332, 40.097095);
((GeneralPath)shape).curveTo(29.073425, 39.092266, 30.538803, 36.701233, 30.50806, 34.677704);
((GeneralPath)shape).curveTo(30.399466, 29.409306, 19.800608, 28.411774, 19.732965, 34.645794);
((GeneralPath)shape).curveTo(19.815855, 37.46782, 21.587463, 38.442677, 21.510948, 40.095768);
((GeneralPath)shape).curveTo(21.18684, 40.095768, 16.679934, 40.11173, 13.466125, 40.11173);
((GeneralPath)shape).curveTo(13.466125, 40.11173, 13.455034, 27.513283, 13.448275, 27.208588);
((GeneralPath)shape).curveTo(13.384335, 24.190216, 9.721873, 28.246996, 7.4876676, 28.30138);
((GeneralPath)shape).curveTo(5.0555744, 28.22364, 4.5716324, 22.373854, 7.2444115, 22.27974);
((GeneralPath)shape).curveTo(9.021675, 22.37604, 11.239811, 23.706198, 13.488613, 23.640404);
((GeneralPath)shape).lineTo(13.477353, 13.268141);
((GeneralPath)shape).lineTo(24.237494, 13.268141);
((GeneralPath)shape).curveTo(24.550562, 11.106256, 22.426434, 7.8455467, 22.45632, 6.141964);
((GeneralPath)shape).curveTo(22.42701, 4.3733134, 25.169569, 4.341993, 26.069422, 4.321795);
((GeneralPath)shape).curveTo(29.195534, 4.251627, 30.460426, 4.947684, 30.50269, 6.157922);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
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
        return 4;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 3;
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
	public AvailableIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public AvailableIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public AvailableIcon(int width, int height) {
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

