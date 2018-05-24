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
shape = new Rectangle2D.Double(151.00753784179688, 250.3676300048828, 25.646968841552734, 14.104340553283691);
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
// _0_1_4 is TextNode of '8.5'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(156.65524, 258.03308);
((GeneralPath)shape).quadTo(155.8115, 258.03308, 155.35837, 258.49402);
((GeneralPath)shape).quadTo(154.90524, 258.95496, 154.90524, 259.81433);
((GeneralPath)shape).quadTo(154.90524, 260.6737, 155.35837, 261.13074);
((GeneralPath)shape).quadTo(155.8115, 261.58777, 156.65524, 261.58777);
((GeneralPath)shape).quadTo(157.49118, 261.58777, 157.9365, 261.13074);
((GeneralPath)shape).quadTo(158.3818, 260.6737, 158.3818, 259.81433);
((GeneralPath)shape).quadTo(158.3818, 258.94714, 157.9365, 258.4901);
((GeneralPath)shape).quadTo(157.49118, 258.03308, 156.65524, 258.03308);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(154.45993, 257.0409);
((GeneralPath)shape).quadTo(153.39743, 256.72058, 152.85837, 256.05652);
((GeneralPath)shape).quadTo(152.3193, 255.39246, 152.3193, 254.40027);
((GeneralPath)shape).quadTo(152.3193, 252.9237, 153.42087, 252.15027);
((GeneralPath)shape).quadTo(154.52243, 251.37683, 156.65524, 251.37683);
((GeneralPath)shape).quadTo(158.77243, 251.37683, 159.874, 252.14636);
((GeneralPath)shape).quadTo(160.97556, 252.9159, 160.97556, 254.40027);
((GeneralPath)shape).quadTo(160.97556, 255.39246, 160.43259, 256.05652);
((GeneralPath)shape).quadTo(159.88962, 256.72058, 158.82712, 257.0409);
((GeneralPath)shape).quadTo(160.01462, 257.36902, 160.62009, 258.1073);
((GeneralPath)shape).quadTo(161.22556, 258.84558, 161.22556, 259.97058);
((GeneralPath)shape).quadTo(161.22556, 261.70496, 160.07321, 262.59167);
((GeneralPath)shape).quadTo(158.92087, 263.4784, 156.65524, 263.4784);
((GeneralPath)shape).quadTo(154.3818, 263.4784, 153.22165, 262.59167);
((GeneralPath)shape).quadTo(152.0615, 261.70496, 152.0615, 259.97058);
((GeneralPath)shape).quadTo(152.0615, 258.84558, 152.66696, 258.1073);
((GeneralPath)shape).quadTo(153.27243, 257.36902, 154.45993, 257.0409);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(155.16306, 254.70496);
((GeneralPath)shape).quadTo(155.16306, 255.40027, 155.54977, 255.77527);
((GeneralPath)shape).quadTo(155.9365, 256.15027, 156.65524, 256.15027);
((GeneralPath)shape).quadTo(157.35837, 256.15027, 157.74118, 255.77527);
((GeneralPath)shape).quadTo(158.124, 255.40027, 158.124, 254.70496);
((GeneralPath)shape).quadTo(158.124, 254.00964, 157.74118, 253.63855);
((GeneralPath)shape).quadTo(157.35837, 253.26746, 156.65524, 253.26746);
((GeneralPath)shape).quadTo(155.9365, 253.26746, 155.54977, 253.64246);
((GeneralPath)shape).quadTo(155.16306, 254.01746, 155.16306, 254.70496);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(162.35056, 260.2284);
((GeneralPath)shape).lineTo(165.16306, 260.2284);
((GeneralPath)shape).lineTo(165.16306, 263.25183);
((GeneralPath)shape).lineTo(162.35056, 263.25183);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(166.99118, 251.58777);
((GeneralPath)shape).lineTo(174.46774, 251.58777);
((GeneralPath)shape).lineTo(174.46774, 253.7987);
((GeneralPath)shape).lineTo(169.38962, 253.7987);
((GeneralPath)shape).lineTo(169.38962, 255.6034);
((GeneralPath)shape).quadTo(169.73337, 255.50964, 170.08102, 255.45886);
((GeneralPath)shape).quadTo(170.42868, 255.40808, 170.80368, 255.40808);
((GeneralPath)shape).quadTo(172.9365, 255.40808, 174.124, 256.4745);
((GeneralPath)shape).quadTo(175.3115, 257.5409, 175.3115, 259.44714);
((GeneralPath)shape).quadTo(175.3115, 261.33777, 174.01852, 262.40808);
((GeneralPath)shape).quadTo(172.72556, 263.4784, 170.42868, 263.4784);
((GeneralPath)shape).quadTo(169.4365, 263.4784, 168.46384, 263.287);
((GeneralPath)shape).quadTo(167.49118, 263.09558, 166.53024, 262.70496);
((GeneralPath)shape).lineTo(166.53024, 260.33777);
((GeneralPath)shape).quadTo(167.48337, 260.88464, 168.33884, 261.15808);
((GeneralPath)shape).quadTo(169.1943, 261.43152, 169.95212, 261.43152);
((GeneralPath)shape).quadTo(171.04587, 261.43152, 171.67477, 260.89636);
((GeneralPath)shape).quadTo(172.30368, 260.3612, 172.30368, 259.44714);
((GeneralPath)shape).quadTo(172.30368, 258.52527, 171.67477, 257.99402);
((GeneralPath)shape).quadTo(171.04587, 257.46277, 169.95212, 257.46277);
((GeneralPath)shape).quadTo(169.30368, 257.46277, 168.5693, 257.63074);
((GeneralPath)shape).quadTo(167.83493, 257.7987, 166.99118, 258.15027);
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

