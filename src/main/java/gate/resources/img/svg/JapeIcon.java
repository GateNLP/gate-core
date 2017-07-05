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
public class JapeIcon implements
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
// _0_0_0 is TextNode of 'Jape'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(15.198459, 24.04854);
((GeneralPath)shape).quadTo(15.198459, 26.689165, 13.625542, 27.902708);
((GeneralPath)shape).quadTo(12.234917, 28.96, 9.484917, 28.96);
((GeneralPath)shape).lineTo(5.5005417, 28.96);
((GeneralPath)shape).lineTo(5.5005417, 27.05375);
((GeneralPath)shape).lineTo(9.484917, 27.05375);
((GeneralPath)shape).quadTo(11.339084, 27.05375, 12.219292, 26.496458);
((GeneralPath)shape).quadTo(13.292209, 25.793333, 13.292209, 24.04854);
((GeneralPath)shape).lineTo(13.292209, 18.032915);
((GeneralPath)shape).lineTo(15.198459, 18.032915);
((GeneralPath)shape).lineTo(15.198459, 24.04854);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.292942, 28.96);
((GeneralPath)shape).lineTo(28.845024, 28.944374);
((GeneralPath)shape).lineTo(27.006483, 25.887083);
((GeneralPath)shape).lineTo(21.818983, 25.887083);
((GeneralPath)shape).lineTo(22.970024, 24.001665);
((GeneralPath)shape).lineTo(25.865858, 24.001665);
((GeneralPath)shape).lineTo(23.964815, 20.845415);
((GeneralPath)shape).lineTo(19.037733, 28.944374);
((GeneralPath)shape).lineTo(16.589815, 28.944374);
((GeneralPath)shape).lineTo(22.522108, 19.074583);
((GeneralPath)shape).quadTo(22.746065, 18.689165, 23.147108, 18.36625);
((GeneralPath)shape).quadTo(23.626274, 18.001665, 24.01169, 18.001665);
((GeneralPath)shape).quadTo(24.428358, 18.001665, 24.876274, 18.350624);
((GeneralPath)shape).quadTo(25.26169, 18.657915, 25.501274, 19.074583);
((GeneralPath)shape).lineTo(31.292942, 28.96);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(44.221725, 21.970415);
((GeneralPath)shape).quadTo(44.221725, 23.762083, 42.909225, 24.86625);
((GeneralPath)shape).quadTo(41.690475, 25.887083, 39.851933, 25.887083);
((GeneralPath)shape).lineTo(35.034225, 25.887083);
((GeneralPath)shape).lineTo(35.034225, 24.001665);
((GeneralPath)shape).lineTo(40.154015, 24.001665);
((GeneralPath)shape).quadTo(41.101933, 24.001665, 41.739952, 23.44177);
((GeneralPath)shape).quadTo(42.377975, 22.881874, 42.377975, 21.949583);
((GeneralPath)shape).quadTo(42.377975, 21.0225, 41.739952, 20.478228);
((GeneralPath)shape).quadTo(41.101933, 19.933958, 40.154015, 19.933958);
((GeneralPath)shape).lineTo(33.914433, 19.933958);
((GeneralPath)shape).lineTo(33.914433, 28.96);
((GeneralPath)shape).lineTo(32.01339, 28.96);
((GeneralPath)shape).lineTo(32.01339, 18.032915);
((GeneralPath)shape).lineTo(39.851933, 18.032915);
((GeneralPath)shape).quadTo(41.690475, 18.032915, 42.909225, 19.074583);
((GeneralPath)shape).quadTo(44.221725, 20.157915, 44.221725, 21.970415);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(54.524834, 24.36625);
((GeneralPath)shape).lineTo(48.28525, 24.36625);
((GeneralPath)shape).lineTo(48.28525, 22.465208);
((GeneralPath)shape).lineTo(54.524834, 22.465208);
((GeneralPath)shape).lineTo(54.524834, 24.36625);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(55.019627, 28.96);
((GeneralPath)shape).lineTo(45.582127, 28.96);
((GeneralPath)shape).lineTo(45.582127, 18.032915);
((GeneralPath)shape).lineTo(55.019627, 18.032915);
((GeneralPath)shape).lineTo(55.019627, 19.933958);
((GeneralPath)shape).lineTo(47.483166, 19.933958);
((GeneralPath)shape).lineTo(47.483166, 27.05375);
((GeneralPath)shape).lineTo(55.019627, 27.05375);
((GeneralPath)shape).lineTo(55.019627, 28.96);
((GeneralPath)shape).closePath();
paint = new Color(0, 0, 0, 255);
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
// _0_0_1 is CompositeGraphicsNode
float alpha__0_0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_0 = g.getClip();
AffineTransform defaultTransform__0_0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_0 is ShapeNode
paint = new Color(255, 105, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.614834, 30.370502);
((GeneralPath)shape).curveTo(32.388325, 31.440418, 33.434837, 32.330322, 34.43805, 33.193863);
((GeneralPath)shape).curveTo(35.11353, 33.567135, 35.84658, 33.783886, 36.52577, 34.145954);
((GeneralPath)shape).curveTo(36.81971, 34.28676, 37.11633, 34.418514, 37.407722, 34.56321);
((GeneralPath)shape).lineTo(35.848034, 35.582603);
((GeneralPath)shape).curveTo(35.55869, 35.45023, 35.272297, 35.311905, 34.981277, 35.183);
((GeneralPath)shape).curveTo(34.294556, 34.805344, 33.53521, 34.61104, 32.882484, 34.169605);
((GeneralPath)shape).curveTo(31.860277, 33.23768, 30.807732, 32.29152, 29.93062, 31.228651);
((GeneralPath)shape).lineTo(31.614834, 30.370502);
((GeneralPath)shape).closePath();
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
paint = new Color(255, 105, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(12.5, 34.7879);
((GeneralPath)shape).curveTo(36.5, 34.333355, 36.5, 34.7879, 36.5, 34.7879);
g.setPaint(paint);
g.fill(shape);
paint = new Color(255, 97, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(12.5, 34.7879);
((GeneralPath)shape).curveTo(36.5, 34.333355, 36.5, 34.7879, 36.5, 34.7879);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1_1;
g.setTransform(defaultTransform__0_0_1_1);
g.setClip(clip__0_0_1_1);
float alpha__0_0_1_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_2 = g.getClip();
AffineTransform defaultTransform__0_0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_2 is ShapeNode
paint = new Color(255, 105, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.707111, 39.0);
((GeneralPath)shape).curveTo(32.480602, 37.930084, 33.527115, 37.04018, 34.530327, 36.17664);
((GeneralPath)shape).curveTo(35.205807, 35.803368, 35.938858, 35.586617, 36.618046, 35.22455);
((GeneralPath)shape).curveTo(36.911987, 35.083744, 37.208607, 34.95199, 37.5, 34.807293);
((GeneralPath)shape).lineTo(35.94031, 33.7879);
((GeneralPath)shape).curveTo(35.650967, 33.920273, 35.364574, 34.058598, 35.073555, 34.187508);
((GeneralPath)shape).curveTo(34.386833, 34.565163, 33.627487, 34.759464, 32.974762, 35.200897);
((GeneralPath)shape).curveTo(31.952555, 36.132824, 30.90001, 37.078983, 30.0229, 38.14185);
((GeneralPath)shape).lineTo(31.707111, 39.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1_2;
g.setTransform(defaultTransform__0_0_1_2);
g.setClip(clip__0_0_1_2);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2 is CompositeGraphicsNode
float alpha__0_0_2_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_0 = g.getClip();
AffineTransform defaultTransform__0_0_2_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_0 is ShapeNode
paint = new Color(255, 105, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(49.114834, 38.0);
((GeneralPath)shape).curveTo(49.888325, 39.069916, 50.934837, 39.95982, 51.93805, 40.82336);
((GeneralPath)shape).curveTo(52.61353, 41.196632, 53.34658, 41.413383, 54.02577, 41.77545);
((GeneralPath)shape).curveTo(54.31971, 41.916256, 54.61633, 42.04801, 54.907722, 42.192707);
((GeneralPath)shape).lineTo(53.348034, 43.2121);
((GeneralPath)shape).curveTo(53.05869, 43.079727, 52.772297, 42.941402, 52.481277, 42.812492);
((GeneralPath)shape).curveTo(51.794556, 42.434837, 51.03521, 42.240536, 50.382484, 41.799103);
((GeneralPath)shape).curveTo(49.360275, 40.867176, 48.30773, 39.921017, 47.430622, 38.85815);
((GeneralPath)shape).lineTo(49.114834, 38.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_0;
g.setTransform(defaultTransform__0_0_2_0);
g.setClip(clip__0_0_2_0);
float alpha__0_0_2_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_1 = g.getClip();
AffineTransform defaultTransform__0_0_2_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_1 is ShapeNode
paint = new Color(255, 105, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(30.0, 42.417397);
((GeneralPath)shape).curveTo(54.0, 41.962852, 54.0, 42.417397, 54.0, 42.417397);
g.setPaint(paint);
g.fill(shape);
paint = new Color(255, 97, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(30.0, 42.417397);
((GeneralPath)shape).curveTo(54.0, 41.962852, 54.0, 42.417397, 54.0, 42.417397);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_1;
g.setTransform(defaultTransform__0_0_2_1);
g.setClip(clip__0_0_2_1);
float alpha__0_0_2_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_2 = g.getClip();
AffineTransform defaultTransform__0_0_2_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_2 is ShapeNode
paint = new Color(255, 105, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(49.20711, 46.629498);
((GeneralPath)shape).curveTo(49.980602, 45.55958, 51.027115, 44.669678, 52.030327, 43.806137);
((GeneralPath)shape).curveTo(52.705807, 43.432865, 53.438858, 43.216114, 54.118046, 42.854046);
((GeneralPath)shape).curveTo(54.411987, 42.71324, 54.708607, 42.581486, 55.0, 42.43679);
((GeneralPath)shape).lineTo(53.44031, 41.417397);
((GeneralPath)shape).curveTo(53.150967, 41.54977, 52.864574, 41.688095, 52.573555, 41.817);
((GeneralPath)shape).curveTo(51.886833, 42.194656, 51.127487, 42.38896, 50.474762, 42.830395);
((GeneralPath)shape).curveTo(49.452557, 43.76232, 48.40001, 44.70848, 47.5229, 45.771347);
((GeneralPath)shape).lineTo(49.20711, 46.629498);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_2;
g.setTransform(defaultTransform__0_0_2_2);
g.setClip(clip__0_0_2_2);
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
        return 6;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 19;
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
	public JapeIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public JapeIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public JapeIcon(int width, int height) {
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

