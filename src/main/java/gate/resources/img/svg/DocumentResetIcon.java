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
public class DocumentResetIcon implements
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
g.transform(new AffineTransform(0.721455991268158f, 0.6924600005149841f, -0.6917759776115417f, 0.7221130132675171f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = getColor(224, 224, 224, 255, disabled);
shape = new RoundRectangle2D.Double(24.970857620239258, -29.098051071166992, 37.46781921386719, 57.838626861572266, 27.15900993347168, 27.15900993347168);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.3830689f,0,2,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(24.970857620239258, -29.098051071166992, 37.46781921386719, 57.838626861572266, 27.15900993347168, 27.15900993347168);
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
g.transform(new AffineTransform(0.721455991268158f, 0.6924600005149841f, -0.6917759776115417f, 0.7221130132675171f, 0.0f, 0.0f));
// _0_0_1 is ShapeNode
paint = getColor(1, 186, 0, 255, disabled);
shape = new Rectangle2D.Double(23.59037208557129, -18.894363403320312, 40.86254119873047, 36.715003967285156);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.3830694f,0,2,4.0f,null,0.0f);
shape = new Rectangle2D.Double(23.59037208557129, -18.894363403320312, 40.86254119873047, 36.715003967285156);
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
g.transform(new AffineTransform(1.005079984664917f, 0.0f, 0.0f, 0.9949460029602051f, 0.0f, 0.0f));
// _0_0_2 is TextNode of 'DR'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(30.865099, 29.163803);
((GeneralPath)shape).quadTo(30.865099, 32.72009, 28.581104, 35.05007);
((GeneralPath)shape).quadTo(26.29711, 37.38005, 22.763817, 37.38005);
((GeneralPath)shape).lineTo(13.696819, 37.38005);
((GeneralPath)shape).lineTo(13.696819, 21.300117);
((GeneralPath)shape).lineTo(22.763817, 21.300117);
((GeneralPath)shape).quadTo(26.320103, 21.300117, 28.5926, 23.465313);
((GeneralPath)shape).quadTo(30.865099, 25.63051, 30.865099, 29.163803);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(28.059925, 29.4014);
((GeneralPath)shape).quadTo(28.059925, 27.063755, 26.565365, 25.58069);
((GeneralPath)shape).quadTo(25.070805, 24.097628, 22.763817, 24.097628);
((GeneralPath)shape).lineTo(16.479, 24.097628);
((GeneralPath)shape).lineTo(16.479, 34.574875);
((GeneralPath)shape).lineTo(22.763817, 34.574875);
((GeneralPath)shape).quadTo(25.070805, 34.574875, 26.565365, 33.141632);
((GeneralPath)shape).quadTo(28.059925, 31.708387, 28.059925, 29.4014);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(52.00622, 37.38005);
((GeneralPath)shape).lineTo(47.86744, 37.38005);
((GeneralPath)shape).lineTo(43.36843, 32.858047);
((GeneralPath)shape).lineTo(37.31355, 32.858047);
((GeneralPath)shape).lineTo(37.31355, 30.083532);
((GeneralPath)shape).lineTo(44.847664, 30.083532);
((GeneralPath)shape).quadTo(46.403538, 30.083532, 47.461227, 29.4014);
((GeneralPath)shape).quadTo(48.66454, 28.596636, 48.66454, 27.117405);
((GeneralPath)shape).quadTo(48.66454, 24.097628, 44.847664, 24.097628);
((GeneralPath)shape).lineTo(35.6657, 24.097628);
((GeneralPath)shape).lineTo(35.6657, 37.38005);
((GeneralPath)shape).lineTo(32.86819, 37.38005);
((GeneralPath)shape).lineTo(32.86819, 21.300117);
((GeneralPath)shape).lineTo(44.403126, 21.300117);
((GeneralPath)shape).quadTo(47.514877, 21.300117, 49.415653, 22.64139);
((GeneralPath)shape).quadTo(51.607674, 24.17427, 51.607674, 27.140398);
((GeneralPath)shape).quadTo(51.607674, 29.163803, 50.33538, 30.62004);
((GeneralPath)shape).quadTo(49.162727, 31.99197, 47.131657, 32.52848);
((GeneralPath)shape).lineTo(52.00622, 37.38005);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
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
paint = getColor(0, 0, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.284906, 59.826717);
((GeneralPath)shape).curveTo(25.141272, 59.599327, 22.079853, 60.49338, 19.037302, 61.147083);
((GeneralPath)shape).curveTo(18.144255, 61.33896, 15.62074, 62.304688, 16.37251, 61.78585);
((GeneralPath)shape).curveTo(16.797794, 61.492336, 17.283373, 61.297596, 17.738806, 61.053467);
((GeneralPath)shape).curveTo(21.28613, 61.3858, 22.821169, 63.637722, 18.199366, 63.7786);
((GeneralPath)shape).curveTo(16.330343, 63.818848, 14.460825, 63.807117, 12.591535, 63.80718);
((GeneralPath)shape).curveTo(12.203924, 63.806046, 11.816326, 63.809647, 11.428727, 63.81169);
((GeneralPath)shape).lineTo(12.912538, 62.733643);
((GeneralPath)shape).curveTo(13.295869, 62.735783, 13.679199, 62.739323, 14.062541, 62.738564);
((GeneralPath)shape).curveTo(15.914763, 62.74134, 17.767344, 62.776726, 19.619204, 62.73823);
((GeneralPath)shape).curveTo(22.731527, 62.581703, 18.029259, 63.833008, 16.031939, 61.73803);
((GeneralPath)shape).curveTo(20.02787, 59.375275, 25.261883, 59.15743, 29.809917, 58.718735);
((GeneralPath)shape).lineTo(28.284906, 59.826717);
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
	 * Should this icon be drawn in a disabled state
	 */
	boolean disabled = false;

	/**
	 * Creates a new transcoded SVG image.
	 */
	public DocumentResetIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public DocumentResetIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public DocumentResetIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public DocumentResetIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public DocumentResetIcon(int width, int height) {
		this(width, height, false);
	}
	
	public DocumentResetIcon(int width, int height, boolean disabled) {
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

