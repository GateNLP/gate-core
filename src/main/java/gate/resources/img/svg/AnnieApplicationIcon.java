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
public class AnnieApplicationIcon implements
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
g.transform(new AffineTransform(0.7037367820739746f, 0.0f, 0.0f, 0.7037367820739746f, 1.4046995639801025f, 0.3803025186061859f));
// _0_0 is CompositeGraphicsNode
float alpha__0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(0.8068640232086182f, 0.0f, 0.0f, 0.7823050022125244f, 25.817129135131836f, 29.089130401611328f));
// _0_0_0 is ShapeNode
paint = new Color(255, 97, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.545454, 17.090908);
((GeneralPath)shape).curveTo(-9.273714, 73.29945, 77.83032, 12.325596, 12.7569, 26.74306);
((GeneralPath)shape).curveTo(-52.316517, 41.160522, 52.39036, 59.63735, -3.8181815, 23.818182);
((GeneralPath)shape).curveTo(-60.026722, -12.000987, 0.9471312, 75.10305, -13.470333, 10.029628);
((GeneralPath)shape).curveTo(-27.887796, -55.04379, -46.364624, 49.66309, -10.545454, -6.545454);
((GeneralPath)shape).curveTo(25.273714, -62.753998, -61.83032, -1.7801414, 3.2430997, -16.197605);
((GeneralPath)shape).curveTo(68.31652, -30.615068, -36.39036, -49.091896, 19.818182, -13.272727);
((GeneralPath)shape).curveTo(76.026726, 22.546442, 15.052869, -64.557594, 29.470333, 0.51582736);
((GeneralPath)shape).curveTo(43.8878, 65.58925, 62.364624, -39.117634, 26.545454, 17.090908);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.545454, 17.090908);
((GeneralPath)shape).curveTo(-9.273714, 73.29945, 77.83032, 12.325596, 12.7569, 26.74306);
((GeneralPath)shape).curveTo(-52.316517, 41.160522, 52.39036, 59.63735, -3.8181815, 23.818182);
((GeneralPath)shape).curveTo(-60.026722, -12.000987, 0.9471312, 75.10305, -13.470333, 10.029628);
((GeneralPath)shape).curveTo(-27.887796, -55.04379, -46.364624, 49.66309, -10.545454, -6.545454);
((GeneralPath)shape).curveTo(25.273714, -62.753998, -61.83032, -1.7801414, 3.2430997, -16.197605);
((GeneralPath)shape).curveTo(68.31652, -30.615068, -36.39036, -49.091896, 19.818182, -13.272727);
((GeneralPath)shape).curveTo(76.026726, 22.546442, 15.052869, -64.557594, 29.470333, 0.51582736);
((GeneralPath)shape).curveTo(43.8878, 65.58925, 62.364624, -39.117634, 26.545454, 17.090908);
((GeneralPath)shape).closePath();
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
g.transform(new AffineTransform(0.8912100195884705f, 0.0f, 0.0f, 0.8294900059700012f, 1.4678449630737305f, 0.8869709968566895f));
// _0_0_1 is ShapeNode
paint = new Color(0, 155, 0, 255);
shape = new Ellipse2D.Double(7.818181991577148, 10.36363410949707, 53.818180084228516, 56.90909194946289);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.013f,0,1,4.0f,null,0.0f);
shape = new Ellipse2D.Double(7.818181991577148, 10.36363410949707, 53.818180084228516, 56.90909194946289);
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
g.transform(new AffineTransform(0.8912100195884705f, 0.0f, 0.0f, 0.8294900059700012f, 1.4678449630737305f, 0.8389559984207153f));
// _0_0_2 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(46.931392669677734, 29.234111785888672), 27.41559f, new Point2D.Double(46.931392669677734, 29.234111785888672), new float[] {0.0f,1.0f}, new Color[] {new Color(183, 243, 123, 255),new Color(113, 167, 59, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7006450295448303f, -0.7006459832191467f, 0.5337550044059753f, 0.5801050066947937f, -1.8970630168914795f, 39.48955154418945f));
shape = new Ellipse2D.Double(7.818181991577148, 10.36363410949707, 53.818180084228516, 56.90909194946289);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.013f,0,1,4.0f,null,0.0f);
shape = new Ellipse2D.Double(7.818181991577148, 10.36363410949707, 53.818180084228516, 56.90909194946289);
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
g.transform(new AffineTransform(0.9018570184707642f, 0.0f, 0.0f, 1.1088229417800903f, 0.0f, 0.0f));
// _0_0_3 is TextNode of 'A'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(53.742775, 41.485428);
((GeneralPath)shape).lineTo(47.78809, 41.44742);
((GeneralPath)shape).lineTo(43.315742, 34.0104);
((GeneralPath)shape).lineTo(30.696878, 34.0104);
((GeneralPath)shape).lineTo(33.496845, 29.424025);
((GeneralPath)shape).lineTo(40.54111, 29.424025);
((GeneralPath)shape).lineTo(35.91673, 21.746283);
((GeneralPath)shape).lineTo(23.931343, 41.44742);
((GeneralPath)shape).lineTo(17.97666, 41.44742);
((GeneralPath)shape).lineTo(32.407265, 17.438639);
((GeneralPath)shape).quadTo(32.952057, 16.501093, 33.927612, 15.715582);
((GeneralPath)shape).quadTo(35.09321, 14.828713, 36.030754, 14.828713);
((GeneralPath)shape).quadTo(37.04432, 14.828713, 38.1339, 15.677573);
((GeneralPath)shape).quadTo(39.071445, 16.425076, 39.654243, 17.438639);
((GeneralPath)shape).lineTo(53.742775, 41.485428);
((GeneralPath)shape).closePath();
paint = new Color(0, 0, 0, 255);
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
	public AnnieApplicationIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public AnnieApplicationIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public AnnieApplicationIcon(int width, int height) {
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

