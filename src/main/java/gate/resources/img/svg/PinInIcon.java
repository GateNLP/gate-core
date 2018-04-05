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
public class PinInIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -0.0f, -0.0f));
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
// _0_0 is ShapeNode
paint = getColor(163, 28, 9, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.023819, 28.759077);
((GeneralPath)shape).lineTo(33.85676, 25.18674);
((GeneralPath)shape).curveTo(31.976868, 24.220177, 30.27683, 22.636793, 28.941504, 20.600145);
((GeneralPath)shape).curveTo(27.605608, 18.562626, 26.901945, 16.369022, 26.861649, 14.276489);
((GeneralPath)shape).lineTo(20.917284, 17.916527);
((GeneralPath)shape).lineTo(16.068378, 20.886198);
((GeneralPath)shape).curveTo(16.109228, 22.97968, 17.044155, 25.031036, 18.380213, 27.067848);
((GeneralPath)shape).curveTo(19.715376, 29.105206, 21.296776, 30.761246, 23.176666, 31.727812);
((GeneralPath)shape).lineTo(28.023815, 28.759079);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0;
g.setTransform(defaultTransform__0_0);
g.setClip(clip__0_0);
float alpha__0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1 = g.getClip();
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1 is ShapeNode
paint = getColor(163, 28, 9, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(42.866093, 27.17258);
((GeneralPath)shape).curveTo(41.288837, 28.127844, 36.962227, 24.305407, 33.202328, 18.634388);
((GeneralPath)shape).curveTo(29.44243, 12.96337, 27.673464, 7.591896, 29.250717, 6.636633);
((GeneralPath)shape).curveTo(30.82797, 5.68137, 35.154587, 9.503807, 38.914482, 15.174825);
((GeneralPath)shape).curveTo(42.674377, 20.845844, 44.443325, 26.21741, 42.866093, 27.17258);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1;
g.setTransform(defaultTransform__0_1);
g.setClip(clip__0_1);
float alpha__0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_2 = g.getClip();
AffineTransform defaultTransform__0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_2 is ShapeNode
paint = getColor(163, 28, 9, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.023819, 28.759077);
((GeneralPath)shape).lineTo(33.85676, 25.18674);
((GeneralPath)shape).curveTo(31.976868, 24.220177, 30.27683, 22.636793, 28.941504, 20.600145);
((GeneralPath)shape).curveTo(27.605608, 18.562626, 26.901945, 16.369022, 26.861649, 14.276489);
((GeneralPath)shape).lineTo(20.917284, 17.916527);
((GeneralPath)shape).lineTo(16.068378, 20.886198);
((GeneralPath)shape).curveTo(16.109228, 22.97968, 17.044155, 25.031036, 18.380213, 27.067848);
((GeneralPath)shape).curveTo(19.715376, 29.105206, 21.296776, 30.761246, 23.176666, 31.727812);
((GeneralPath)shape).lineTo(28.023815, 28.759079);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_2;
g.setTransform(defaultTransform__0_2);
g.setClip(clip__0_2);
float alpha__0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_3 = g.getClip();
AffineTransform defaultTransform__0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_3 is ShapeNode
paint = getColor(206, 57, 41, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(33.31296, 18.588861);
((GeneralPath)shape).curveTo(29.548697, 12.931026, 27.777674, 7.5720377, 29.356758, 6.618995);
((GeneralPath)shape).curveTo(23.631899, 10.07418, 23.11522, 16.813812, 26.879484, 22.47165);
((GeneralPath)shape).curveTo(30.64375, 28.129486, 36.906273, 30.777668, 42.630013, 27.323088);
((GeneralPath)shape).lineTo(42.573532, 27.233967);
((GeneralPath)shape).curveTo(40.74406, 27.457012, 36.789333, 23.813982, 33.312965, 18.588863);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_3;
g.setTransform(defaultTransform__0_3);
g.setClip(clip__0_3);
float alpha__0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4 = g.getClip();
AffineTransform defaultTransform__0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_4 is ShapeNode
paint = getColor(206, 57, 41, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.511385, 36.93523);
((GeneralPath)shape).curveTo(24.092081, 35.2855, 24.182472, 33.425056, 23.78549, 31.35454);
((GeneralPath)shape).lineTo(23.176432, 31.728092);
((GeneralPath)shape).curveTo(21.296688, 30.760897, 19.715305, 29.104776, 18.37998, 27.068129);
((GeneralPath)shape).curveTo(17.04392, 25.031319, 16.109154, 22.979252, 16.068144, 20.88648);
((GeneralPath)shape).lineTo(16.689299, 20.506058);
((GeneralPath)shape).lineTo(16.672108, 20.478989);
((GeneralPath)shape).curveTo(14.8945465, 19.235537, 13.105276, 18.514307, 11.303403, 18.316595);
((GeneralPath)shape).curveTo(7.95539, 17.947725, 5.2564316, 20.789759, 5.925139, 23.987339);
((GeneralPath)shape).curveTo(6.434333, 26.419525, 7.718871, 29.400473, 9.664658, 32.368095);
((GeneralPath)shape).curveTo(11.609774, 35.334908, 13.850848, 37.731247, 15.909161, 39.21408);
((GeneralPath)shape).curveTo(18.614735, 41.163723, 22.426203, 40.018497, 23.511385, 36.93523);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_4;
g.setTransform(defaultTransform__0_4);
g.setClip(clip__0_4);
float alpha__0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_5 = g.getClip();
AffineTransform defaultTransform__0_5 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_5 is CompositeGraphicsNode
origAlpha = alpha__0_5;
g.setTransform(defaultTransform__0_5);
g.setClip(clip__0_5);
float alpha__0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_6 = g.getClip();
AffineTransform defaultTransform__0_6 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_6 is CompositeGraphicsNode
origAlpha = alpha__0_6;
g.setTransform(defaultTransform__0_6);
g.setClip(clip__0_6);
float alpha__0_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_7 = g.getClip();
AffineTransform defaultTransform__0_7 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_7 is CompositeGraphicsNode
origAlpha = alpha__0_7;
g.setTransform(defaultTransform__0_7);
g.setClip(clip__0_7);
float alpha__0_8 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_8 = g.getClip();
AffineTransform defaultTransform__0_8 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_8 is CompositeGraphicsNode
origAlpha = alpha__0_8;
g.setTransform(defaultTransform__0_8);
g.setClip(clip__0_8);
float alpha__0_9 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_9 = g.getClip();
AffineTransform defaultTransform__0_9 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_9 is CompositeGraphicsNode
origAlpha = alpha__0_9;
g.setTransform(defaultTransform__0_9);
g.setClip(clip__0_9);
float alpha__0_10 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_10 = g.getClip();
AffineTransform defaultTransform__0_10 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_10 is CompositeGraphicsNode
origAlpha = alpha__0_10;
g.setTransform(defaultTransform__0_10);
g.setClip(clip__0_10);
float alpha__0_11 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_11 = g.getClip();
AffineTransform defaultTransform__0_11 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_11 is CompositeGraphicsNode
origAlpha = alpha__0_11;
g.setTransform(defaultTransform__0_11);
g.setClip(clip__0_11);
float alpha__0_12 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_12 = g.getClip();
AffineTransform defaultTransform__0_12 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_12 is CompositeGraphicsNode
origAlpha = alpha__0_12;
g.setTransform(defaultTransform__0_12);
g.setClip(clip__0_12);
float alpha__0_13 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_13 = g.getClip();
AffineTransform defaultTransform__0_13 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_13 is CompositeGraphicsNode
origAlpha = alpha__0_13;
g.setTransform(defaultTransform__0_13);
g.setClip(clip__0_13);
float alpha__0_14 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_14 = g.getClip();
AffineTransform defaultTransform__0_14 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_14 is CompositeGraphicsNode
origAlpha = alpha__0_14;
g.setTransform(defaultTransform__0_14);
g.setClip(clip__0_14);
float alpha__0_15 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_15 = g.getClip();
AffineTransform defaultTransform__0_15 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_15 is CompositeGraphicsNode
origAlpha = alpha__0_15;
g.setTransform(defaultTransform__0_15);
g.setClip(clip__0_15);
float alpha__0_16 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_16 = g.getClip();
AffineTransform defaultTransform__0_16 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_16 is CompositeGraphicsNode
origAlpha = alpha__0_16;
g.setTransform(defaultTransform__0_16);
g.setClip(clip__0_16);
float alpha__0_17 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_17 = g.getClip();
AffineTransform defaultTransform__0_17 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_17 is CompositeGraphicsNode
origAlpha = alpha__0_17;
g.setTransform(defaultTransform__0_17);
g.setClip(clip__0_17);
float alpha__0_18 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_18 = g.getClip();
AffineTransform defaultTransform__0_18 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_18 is CompositeGraphicsNode
origAlpha = alpha__0_18;
g.setTransform(defaultTransform__0_18);
g.setClip(clip__0_18);
float alpha__0_19 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_19 = g.getClip();
AffineTransform defaultTransform__0_19 = g.getTransform();
g.transform(new AffineTransform(0.08139632642269135f, 0.017768269404768944f, -0.01813168078660965f, 0.07872340828180313f, 5.241711139678955f, 0.7031430006027222f));
// _0_19 is CompositeGraphicsNode
origAlpha = alpha__0_19;
g.setTransform(defaultTransform__0_19);
g.setClip(clip__0_19);
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
        return 6;
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
	 * Should this icon be drawn in a disabled state
	 */
	boolean disabled = false;

	/**
	 * Creates a new transcoded SVG image.
	 */
	public PinInIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public PinInIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public PinInIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public PinInIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public PinInIcon(int width, int height) {
		this(width, height, false);
	}
	
	public PinInIcon(int width, int height, boolean disabled) {
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

