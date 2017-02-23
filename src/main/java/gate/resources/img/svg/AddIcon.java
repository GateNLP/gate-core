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
public class AddIcon implements
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
paint = new Color(117, 161, 208, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.514357, 37.542683);
((GeneralPath)shape).lineTo(27.514357, 28.515722);
((GeneralPath)shape).lineTo(37.49282, 28.475542);
((GeneralPath)shape).lineTo(37.49282, 21.480219);
((GeneralPath)shape).lineTo(27.523285, 21.480219);
((GeneralPath)shape).lineTo(27.514357, 11.520049);
((GeneralPath)shape).lineTo(20.498081, 11.53121);
((GeneralPath)shape).lineTo(20.502546, 21.462362);
((GeneralPath)shape).lineTo(10.51292, 21.536022);
((GeneralPath)shape).lineTo(10.477206, 28.50456);
((GeneralPath)shape).lineTo(20.511475, 28.475542);
((GeneralPath)shape).lineTo(20.518171, 37.515896);
((GeneralPath)shape).lineTo(27.514357, 37.542683);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(52, 101, 164, 255);
stroke = new BasicStroke(1.0000004f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.514357, 37.542683);
((GeneralPath)shape).lineTo(27.514357, 28.515722);
((GeneralPath)shape).lineTo(37.49282, 28.475542);
((GeneralPath)shape).lineTo(37.49282, 21.480219);
((GeneralPath)shape).lineTo(27.523285, 21.480219);
((GeneralPath)shape).lineTo(27.514357, 11.520049);
((GeneralPath)shape).lineTo(20.498081, 11.53121);
((GeneralPath)shape).lineTo(20.502546, 21.462362);
((GeneralPath)shape).lineTo(10.51292, 21.536022);
((GeneralPath)shape).lineTo(10.477206, 28.50456);
((GeneralPath)shape).lineTo(20.511475, 28.475542);
((GeneralPath)shape).lineTo(20.518171, 37.515896);
((GeneralPath)shape).lineTo(27.514357, 37.542683);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 0.40860215f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(34.89284896850586, 36.42298889160156), new Point2D.Double(45.918697357177734, 48.54798889160156), new float[] {0.0f,1.0f}, new Color[] {new Color(114, 159, 207, 255),new Color(81, 135, 214, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -18.017850875854492f, -13.571189880371094f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.498701, 36.53392);
((GeneralPath)shape).lineTo(26.498701, 27.499739);
((GeneralPath)shape).lineTo(36.501305, 27.499739);
((GeneralPath)shape).lineTo(36.494606, 22.47531);
((GeneralPath)shape).lineTo(26.50763, 22.47531);
((GeneralPath)shape).lineTo(26.50763, 12.480335);
((GeneralPath)shape).lineTo(21.512796, 12.498193);
((GeneralPath)shape).lineTo(21.521725, 22.47531);
((GeneralPath)shape).lineTo(11.495536, 22.493166);
((GeneralPath)shape).lineTo(11.46875, 27.466255);
((GeneralPath)shape).lineTo(21.533142, 27.475185);
((GeneralPath)shape).lineTo(21.51975, 36.50267);
((GeneralPath)shape).lineTo(26.498701, 36.53392);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(16.874998092651367, 22.85179901123047), new Point2D.Double(27.900846481323242, 34.97679901123047), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 87)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0000006f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.498701, 36.53392);
((GeneralPath)shape).lineTo(26.498701, 27.499739);
((GeneralPath)shape).lineTo(36.501305, 27.499739);
((GeneralPath)shape).lineTo(36.494606, 22.47531);
((GeneralPath)shape).lineTo(26.50763, 22.47531);
((GeneralPath)shape).lineTo(26.50763, 12.480335);
((GeneralPath)shape).lineTo(21.512796, 12.498193);
((GeneralPath)shape).lineTo(21.521725, 22.47531);
((GeneralPath)shape).lineTo(11.495536, 22.493166);
((GeneralPath)shape).lineTo(11.46875, 27.466255);
((GeneralPath)shape).lineTo(21.533142, 27.475185);
((GeneralPath)shape).lineTo(21.51975, 36.50267);
((GeneralPath)shape).lineTo(26.498701, 36.53392);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 0.31182796f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2 is ShapeNode
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.0, 25.0);
((GeneralPath)shape).curveTo(11.0, 26.9375, 36.984375, 24.03125, 36.984375, 24.96875);
((GeneralPath)shape).lineTo(36.984375, 21.96875);
((GeneralPath)shape).lineTo(27.0, 22.0);
((GeneralPath)shape).lineTo(27.0, 12.034772);
((GeneralPath)shape).lineTo(21.0, 12.034772);
((GeneralPath)shape).lineTo(21.0, 22.0);
((GeneralPath)shape).lineTo(11.0, 22.0);
((GeneralPath)shape).lineTo(11.0, 25.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2;
g.setTransform(defaultTransform__0_0_2);
g.setClip(clip__0_0_2);
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
        return 10;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 12;
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
	public AddIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public AddIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public AddIcon(int width, int height) {
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

