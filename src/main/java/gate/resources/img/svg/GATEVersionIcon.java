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
public class GATEVersionIcon implements
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
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,60.0,60.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -116.65451049804688f, -204.4719696044922f));
// _0_0 is CompositeGraphicsNode
origAlpha = alpha__0_0;
g.setTransform(defaultTransform__0_0);
g.setClip(clip__0_0);
float alpha__0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1 = g.getClip();
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -116.65451049804688f, -204.4719696044922f));
// _0_1 is CompositeGraphicsNode
float alpha__0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_0 = g.getClip();
AffineTransform defaultTransform__0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_0 is ShapeNode
origAlpha = alpha__0_1_0;
g.setTransform(defaultTransform__0_1_0);
g.setClip(clip__0_1_0);
float alpha__0_1_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_1 = g.getClip();
AffineTransform defaultTransform__0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_1 is ShapeNode
paint = getColor(255, 255, 255, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.63737, 234.47195);
((GeneralPath)shape).curveTo(170.80554, 263.7357, 122.67123, 258.0005, 122.67123, 258.0005);
((GeneralPath)shape).lineTo(122.67123, 210.94342);
((GeneralPath)shape).curveTo(122.67123, 210.94342, 170.46924, 205.20825, 170.63737, 234.47195);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 128, 0, 255, disabled);
stroke = new BasicStroke(2.0333996f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.63737, 234.47195);
((GeneralPath)shape).curveTo(170.80554, 263.7357, 122.67123, 258.0005, 122.67123, 258.0005);
((GeneralPath)shape).lineTo(122.67123, 210.94342);
((GeneralPath)shape).curveTo(122.67123, 210.94342, 170.46924, 205.20825, 170.63737, 234.47195);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_1_1;
g.setTransform(defaultTransform__0_1_1);
g.setClip(clip__0_1_1);
float alpha__0_1_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_2 = g.getClip();
AffineTransform defaultTransform__0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_2 is CompositeGraphicsNode
float alpha__0_1_2_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_2_0 = g.getClip();
AffineTransform defaultTransform__0_1_2_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_2_0 is ShapeNode
paint = getColor(255, 0, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(159.5505, 249.17284);
((GeneralPath)shape).lineTo(140.4545, 249.17284);
((GeneralPath)shape).curveTo(136.08382, 249.17284, 132.46115, 247.72084, 129.5865, 244.81683);
((GeneralPath)shape).curveTo(126.74117, 241.91283, 125.318504, 238.24617, 125.318504, 233.81683);
((GeneralPath)shape).curveTo(125.318504, 229.41685, 126.7265, 225.86752, 129.54251, 223.16884);
((GeneralPath)shape).curveTo(132.38785, 220.4702, 136.02518, 219.12086, 140.45451, 219.12083);
((GeneralPath)shape).lineTo(157.4385, 219.12083);
((GeneralPath)shape).lineTo(157.4385, 224.35684);
((GeneralPath)shape).lineTo(140.45451, 224.35684);
((GeneralPath)shape).curveTo(137.57983, 224.35686, 135.20383, 225.28087, 133.3265, 227.12885);
((GeneralPath)shape).curveTo(131.4785, 228.97687, 130.5545, 231.35286, 130.5545, 234.25685);
((GeneralPath)shape).curveTo(130.5545, 237.13153, 131.4785, 239.46352, 133.3265, 241.25285);
((GeneralPath)shape).curveTo(135.20383, 243.04219, 137.57983, 243.93686, 140.45451, 243.93686);
((GeneralPath)shape).lineTo(154.31451, 243.93686);
((GeneralPath)shape).lineTo(154.31451, 237.38086);
((GeneralPath)shape).lineTo(140.05852, 237.38086);
((GeneralPath)shape).lineTo(140.05852, 232.58485);
((GeneralPath)shape).lineTo(159.55052, 232.58485);
((GeneralPath)shape).lineTo(159.55052, 249.17285);
g.setPaint(paint);
g.fill(shape);
paint = getColor(128, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(159.5505, 249.17284);
((GeneralPath)shape).lineTo(140.4545, 249.17284);
((GeneralPath)shape).curveTo(136.08382, 249.17284, 132.46115, 247.72084, 129.5865, 244.81683);
((GeneralPath)shape).curveTo(126.74117, 241.91283, 125.318504, 238.24617, 125.318504, 233.81683);
((GeneralPath)shape).curveTo(125.318504, 229.41685, 126.7265, 225.86752, 129.54251, 223.16884);
((GeneralPath)shape).curveTo(132.38785, 220.4702, 136.02518, 219.12086, 140.45451, 219.12083);
((GeneralPath)shape).lineTo(157.4385, 219.12083);
((GeneralPath)shape).lineTo(157.4385, 224.35684);
((GeneralPath)shape).lineTo(140.45451, 224.35684);
((GeneralPath)shape).curveTo(137.57983, 224.35686, 135.20383, 225.28087, 133.3265, 227.12885);
((GeneralPath)shape).curveTo(131.4785, 228.97687, 130.5545, 231.35286, 130.5545, 234.25685);
((GeneralPath)shape).curveTo(130.5545, 237.13153, 131.4785, 239.46352, 133.3265, 241.25285);
((GeneralPath)shape).curveTo(135.20383, 243.04219, 137.57983, 243.93686, 140.45451, 243.93686);
((GeneralPath)shape).lineTo(154.31451, 243.93686);
((GeneralPath)shape).lineTo(154.31451, 237.38086);
((GeneralPath)shape).lineTo(140.05852, 237.38086);
((GeneralPath)shape).lineTo(140.05852, 232.58485);
((GeneralPath)shape).lineTo(159.55052, 232.58485);
((GeneralPath)shape).lineTo(159.55052, 249.17285);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_1_2_0;
g.setTransform(defaultTransform__0_1_2_0);
g.setClip(clip__0_1_2_0);
origAlpha = alpha__0_1_2;
g.setTransform(defaultTransform__0_1_2);
g.setClip(clip__0_1_2);
float alpha__0_1_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_3 = g.getClip();
AffineTransform defaultTransform__0_1_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_3 is ShapeNode
paint = getColor(255, 255, 255, 255, disabled);
shape = new Rectangle2D.Double(136.4081268310547, 250.3676300048828, 40.24637985229492, 14.104340553283691);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_3;
g.setTransform(defaultTransform__0_1_3);
g.setClip(clip__0_1_3);
float alpha__0_1_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_4 = g.getClip();
AffineTransform defaultTransform__0_1_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_4 is TextNode of '8.6.1'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(141.97844, 258.03308);
((GeneralPath)shape).quadTo(141.13469, 258.03308, 140.68156, 258.49402);
((GeneralPath)shape).quadTo(140.22844, 258.95496, 140.22844, 259.81433);
((GeneralPath)shape).quadTo(140.22844, 260.6737, 140.68156, 261.13074);
((GeneralPath)shape).quadTo(141.13469, 261.58777, 141.97844, 261.58777);
((GeneralPath)shape).quadTo(142.81438, 261.58777, 143.25969, 261.13074);
((GeneralPath)shape).quadTo(143.705, 260.6737, 143.705, 259.81433);
((GeneralPath)shape).quadTo(143.705, 258.94714, 143.25969, 258.4901);
((GeneralPath)shape).quadTo(142.81438, 258.03308, 141.97844, 258.03308);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(139.78313, 257.0409);
((GeneralPath)shape).quadTo(138.72063, 256.72058, 138.18156, 256.05652);
((GeneralPath)shape).quadTo(137.6425, 255.39246, 137.6425, 254.40027);
((GeneralPath)shape).quadTo(137.6425, 252.9237, 138.74406, 252.15027);
((GeneralPath)shape).quadTo(139.84563, 251.37683, 141.97844, 251.37683);
((GeneralPath)shape).quadTo(144.09563, 251.37683, 145.19719, 252.14636);
((GeneralPath)shape).quadTo(146.29875, 252.9159, 146.29875, 254.40027);
((GeneralPath)shape).quadTo(146.29875, 255.39246, 145.75578, 256.05652);
((GeneralPath)shape).quadTo(145.21281, 256.72058, 144.15031, 257.0409);
((GeneralPath)shape).quadTo(145.33781, 257.36902, 145.94328, 258.1073);
((GeneralPath)shape).quadTo(146.54875, 258.84558, 146.54875, 259.97058);
((GeneralPath)shape).quadTo(146.54875, 261.70496, 145.39641, 262.59167);
((GeneralPath)shape).quadTo(144.24406, 263.4784, 141.97844, 263.4784);
((GeneralPath)shape).quadTo(139.705, 263.4784, 138.54485, 262.59167);
((GeneralPath)shape).quadTo(137.38469, 261.70496, 137.38469, 259.97058);
((GeneralPath)shape).quadTo(137.38469, 258.84558, 137.99016, 258.1073);
((GeneralPath)shape).quadTo(138.59563, 257.36902, 139.78313, 257.0409);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(140.48625, 254.70496);
((GeneralPath)shape).quadTo(140.48625, 255.40027, 140.87297, 255.77527);
((GeneralPath)shape).quadTo(141.25969, 256.15027, 141.97844, 256.15027);
((GeneralPath)shape).quadTo(142.68156, 256.15027, 143.06438, 255.77527);
((GeneralPath)shape).quadTo(143.44719, 255.40027, 143.44719, 254.70496);
((GeneralPath)shape).quadTo(143.44719, 254.00964, 143.06438, 253.63855);
((GeneralPath)shape).quadTo(142.68156, 253.26746, 141.97844, 253.26746);
((GeneralPath)shape).quadTo(141.25969, 253.26746, 140.87297, 253.64246);
((GeneralPath)shape).quadTo(140.48625, 254.01746, 140.48625, 254.70496);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(147.67375, 260.2284);
((GeneralPath)shape).lineTo(150.48625, 260.2284);
((GeneralPath)shape).lineTo(150.48625, 263.25183);
((GeneralPath)shape).lineTo(147.67375, 263.25183);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(156.40813, 257.49402);
((GeneralPath)shape).quadTo(155.61906, 257.49402, 155.22453, 258.00574);
((GeneralPath)shape).quadTo(154.83, 258.51746, 154.83, 259.5409);
((GeneralPath)shape).quadTo(154.83, 260.56433, 155.22453, 261.07605);
((GeneralPath)shape).quadTo(155.61906, 261.58777, 156.40813, 261.58777);
((GeneralPath)shape).quadTo(157.205, 261.58777, 157.59953, 261.07605);
((GeneralPath)shape).quadTo(157.99406, 260.56433, 157.99406, 259.5409);
((GeneralPath)shape).quadTo(157.99406, 258.51746, 157.59953, 258.00574);
((GeneralPath)shape).quadTo(157.205, 257.49402, 156.40813, 257.49402);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(160.12688, 251.89246);
((GeneralPath)shape).lineTo(160.12688, 254.0487);
((GeneralPath)shape).quadTo(159.38469, 253.69714, 158.72844, 253.52917);
((GeneralPath)shape).quadTo(158.07219, 253.3612, 157.44719, 253.3612);
((GeneralPath)shape).quadTo(156.10344, 253.3612, 155.35344, 254.1073);
((GeneralPath)shape).quadTo(154.60344, 254.8534, 154.47844, 256.32214);
((GeneralPath)shape).quadTo(154.99406, 255.93933, 155.59563, 255.74792);
((GeneralPath)shape).quadTo(156.19719, 255.55652, 156.90813, 255.55652);
((GeneralPath)shape).quadTo(158.69719, 255.55652, 159.79485, 256.6034);
((GeneralPath)shape).quadTo(160.8925, 257.65027, 160.8925, 259.34558);
((GeneralPath)shape).quadTo(160.8925, 261.22058, 159.66594, 262.3495);
((GeneralPath)shape).quadTo(158.43938, 263.4784, 156.37688, 263.4784);
((GeneralPath)shape).quadTo(154.10344, 263.4784, 152.85735, 261.94324);
((GeneralPath)shape).quadTo(151.61125, 260.40808, 151.61125, 257.58777);
((GeneralPath)shape).quadTo(151.61125, 254.69714, 153.06828, 253.0448);
((GeneralPath)shape).quadTo(154.52531, 251.39246, 157.06438, 251.39246);
((GeneralPath)shape).quadTo(157.86906, 251.39246, 158.62688, 251.51746);
((GeneralPath)shape).quadTo(159.38469, 251.64246, 160.12688, 251.89246);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(161.88469, 260.2284);
((GeneralPath)shape).lineTo(164.69719, 260.2284);
((GeneralPath)shape).lineTo(164.69719, 263.25183);
((GeneralPath)shape).lineTo(161.88469, 263.25183);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(166.705, 261.1737);
((GeneralPath)shape).lineTo(169.36125, 261.1737);
((GeneralPath)shape).lineTo(169.36125, 253.63464);
((GeneralPath)shape).lineTo(166.63469, 254.19714);
((GeneralPath)shape).lineTo(166.63469, 252.15027);
((GeneralPath)shape).lineTo(169.34563, 251.58777);
((GeneralPath)shape).lineTo(172.205, 251.58777);
((GeneralPath)shape).lineTo(172.205, 261.1737);
((GeneralPath)shape).lineTo(174.86125, 261.1737);
((GeneralPath)shape).lineTo(174.86125, 263.25183);
((GeneralPath)shape).lineTo(166.705, 263.25183);
((GeneralPath)shape).closePath();
paint = getColor(0, 128, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 128, 0, 255, disabled);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
g.setStroke(stroke);
g.setPaint(paint);
g.draw(shape);
origAlpha = alpha__0_1_4;
g.setTransform(defaultTransform__0_1_4);
g.setClip(clip__0_1_4);
origAlpha = alpha__0_1;
g.setTransform(defaultTransform__0_1);
g.setClip(clip__0_1);
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
		return 60;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static int getOrigHeight() {
		return 60;
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
	public GATEVersionIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public GATEVersionIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public GATEVersionIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public GATEVersionIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public GATEVersionIcon(int width, int height) {
		this(width, height, false);
	}
	
	public GATEVersionIcon(int width, int height, boolean disabled) {
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

