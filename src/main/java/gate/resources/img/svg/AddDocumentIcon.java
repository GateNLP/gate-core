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
public class AddDocumentIcon implements
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
g.transform(new AffineTransform(0.9105449914932251f, 0.4134100079536438f, -0.6455870270729065f, 0.763687014579773f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(42.18181610107422, 29.27272605895996), new Point2D.Double(10.363636016845703, 60.54545211791992), new float[] {0.0f,1.0f}, new Color[] {getColor(255, 255, 255, 255, disabled),getColor(6, 1, 1, 0, disabled)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9779120087623596f, -2.3235840274082875E-7f, -3.2074518685476505E-7f, 1.084488034248352f, 15.320890426635742f, -20.449399948120117f));
shape = new Rectangle2D.Double(30.289077758789062, -8.372633934020996, 33.21522903442383, 47.521305084228516);
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.4769979f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(30.289077758789062, -8.372633934020996, 33.21522903442383, 47.521305084228516);
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
g.transform(new AffineTransform(0.8727239966392517f, 0.4041920006275177f, -0.6862050294876099f, 0.828029990196228f, 0.0f, 0.0f));
// _0_0_1 is TextNode of 'GATE DOC'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.18803, -1.4418952);
((GeneralPath)shape).lineTo(39.18803, -2.2247365);
((GeneralPath)shape).lineTo(38.543262, -2.2247365);
((GeneralPath)shape).lineTo(38.543262, -2.5490751);
((GeneralPath)shape).lineTo(39.5788, -2.5490751);
((GeneralPath)shape).lineTo(39.5788, -1.2973106);
((GeneralPath)shape).quadTo(39.34955, -1.13449, 39.07406, -1.0511259);
((GeneralPath)shape).quadTo(38.798565, -0.96776175, 38.48595, -0.96776175);
((GeneralPath)shape).quadTo(37.8021, -0.96776175, 37.416542, -1.3676491);
((GeneralPath)shape).quadTo(37.030983, -1.7675364, 37.030983, -2.4813418);
((GeneralPath)shape).quadTo(37.030983, -3.1964498, 37.416542, -3.5956857);
((GeneralPath)shape).quadTo(37.8021, -3.9949217, 38.48595, -3.9949217);
((GeneralPath)shape).quadTo(38.77121, -3.9949217, 39.02847, -3.9245832);
((GeneralPath)shape).quadTo(39.285725, -3.8542447, 39.50195, -3.7174754);
((GeneralPath)shape).lineTo(39.50195, -3.2980497);
((GeneralPath)shape).quadTo(39.28312, -3.4830139, 39.036934, -3.5767984);
((GeneralPath)shape).quadTo(38.79075, -3.6705832, 38.519817, -3.6705832);
((GeneralPath)shape).quadTo(37.984463, -3.6705832, 37.71548, -3.3716445);
((GeneralPath)shape).quadTo(37.446503, -3.072706, 37.446503, -2.4813418);
((GeneralPath)shape).quadTo(37.446503, -1.89128, 37.71548, -1.5923414);
((GeneralPath)shape).quadTo(37.984463, -1.2934029, 38.519817, -1.2934029);
((GeneralPath)shape).quadTo(38.728226, -1.2934029, 38.89235, -1.3292234);
((GeneralPath)shape).quadTo(39.056473, -1.365044, 39.18803, -1.4418952);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.274086, -3.5533524);
((GeneralPath)shape).lineTo(40.738735, -2.1022954);
((GeneralPath)shape).lineTo(41.812046, -2.1022954);
((GeneralPath)shape).lineTo(41.274086, -3.5533524);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.05135, -3.942819);
((GeneralPath)shape).lineTo(41.49943, -3.942819);
((GeneralPath)shape).lineTo(42.61052, -1.0250746);
((GeneralPath)shape).lineTo(42.20021, -1.0250746);
((GeneralPath)shape).lineTo(41.934486, -1.7740492);
((GeneralPath)shape).lineTo(40.6202, -1.7740492);
((GeneralPath)shape).lineTo(40.354477, -1.0250746);
((GeneralPath)shape).lineTo(39.937656, -1.0250746);
((GeneralPath)shape).lineTo(41.05135, -3.942819);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(42.63201, -3.942819);
((GeneralPath)shape).lineTo(45.100372, -3.942819);
((GeneralPath)shape).lineTo(45.100372, -3.610665);
((GeneralPath)shape).lineTo(44.06483, -3.610665);
((GeneralPath)shape).lineTo(44.06483, -1.0250746);
((GeneralPath)shape).lineTo(43.66755, -1.0250746);
((GeneralPath)shape).lineTo(43.66755, -3.610665);
((GeneralPath)shape).lineTo(42.63201, -3.610665);
((GeneralPath)shape).lineTo(42.63201, -3.942819);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(45.481373, -3.942819);
((GeneralPath)shape).lineTo(47.3258, -3.942819);
((GeneralPath)shape).lineTo(47.3258, -3.610665);
((GeneralPath)shape).lineTo(45.87605, -3.610665);
((GeneralPath)shape).lineTo(45.87605, -2.7470648);
((GeneralPath)shape).lineTo(47.264584, -2.7470648);
((GeneralPath)shape).lineTo(47.264584, -2.414911);
((GeneralPath)shape).lineTo(45.87605, -2.414911);
((GeneralPath)shape).lineTo(45.87605, -1.3572285);
((GeneralPath)shape).lineTo(47.360973, -1.3572285);
((GeneralPath)shape).lineTo(47.360973, -1.0250746);
((GeneralPath)shape).lineTo(45.481373, -1.0250746);
((GeneralPath)shape).lineTo(45.481373, -3.942819);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(49.67628, -3.6184804);
((GeneralPath)shape).lineTo(49.67628, -1.3494132);
((GeneralPath)shape).lineTo(50.15302, -1.3494132);
((GeneralPath)shape).quadTo(50.756107, -1.3494132, 51.036808, -1.6229517);
((GeneralPath)shape).quadTo(51.317513, -1.8964902, 51.317513, -2.486552);
((GeneralPath)shape).quadTo(51.317513, -3.072706, 51.036808, -3.3455932);
((GeneralPath)shape).quadTo(50.756107, -3.6184804, 50.15302, -3.6184804);
((GeneralPath)shape).lineTo(49.67628, -3.6184804);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(49.281605, -3.942819);
((GeneralPath)shape).lineTo(50.0918, -3.942819);
((GeneralPath)shape).quadTo(50.93977, -3.942819, 51.3364, -3.5898242);
((GeneralPath)shape).quadTo(51.73303, -3.236829, 51.73303, -2.486552);
((GeneralPath)shape).quadTo(51.73303, -1.7323672, 51.334446, -1.3787209);
((GeneralPath)shape).quadTo(50.93586, -1.0250746, 50.0918, -1.0250746);
((GeneralPath)shape).lineTo(49.281605, -1.0250746);
((GeneralPath)shape).lineTo(49.281605, -3.942819);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(53.54685, -3.674491);
((GeneralPath)shape).quadTo(53.117004, -3.674491, 52.863655, -3.35406);
((GeneralPath)shape).quadTo(52.610306, -3.0336292, 52.610306, -2.4813418);
((GeneralPath)shape).quadTo(52.610306, -1.930357, 52.863655, -1.6099261);
((GeneralPath)shape).quadTo(53.117004, -1.2894952, 53.54685, -1.2894952);
((GeneralPath)shape).quadTo(53.976696, -1.2894952, 54.22744, -1.6099261);
((GeneralPath)shape).quadTo(54.478184, -1.930357, 54.478184, -2.4813418);
((GeneralPath)shape).quadTo(54.478184, -3.0336292, 54.22744, -3.35406);
((GeneralPath)shape).quadTo(53.976696, -3.674491, 53.54685, -3.674491);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(53.54685, -3.9949217);
((GeneralPath)shape).quadTo(54.16036, -3.9949217, 54.52768, -3.5839627);
((GeneralPath)shape).quadTo(54.895004, -3.1730034, 54.895004, -2.4813418);
((GeneralPath)shape).quadTo(54.895004, -1.7909825, 54.52768, -1.3793721);
((GeneralPath)shape).quadTo(54.16036, -0.96776175, 53.54685, -0.96776175);
((GeneralPath)shape).quadTo(52.930737, -0.96776175, 52.562763, -1.3787209);
((GeneralPath)shape).quadTo(52.194786, -1.78968, 52.194786, -2.4813418);
((GeneralPath)shape).quadTo(52.194786, -3.1730034, 52.562763, -3.5839627);
((GeneralPath)shape).quadTo(52.930737, -3.9949217, 53.54685, -3.9949217);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(57.696823, -3.7174754);
((GeneralPath)shape).lineTo(57.696823, -3.3019574);
((GeneralPath)shape).quadTo(57.49753, -3.4869215, 57.271538, -3.5787523);
((GeneralPath)shape).quadTo(57.045544, -3.6705832, 56.791542, -3.6705832);
((GeneralPath)shape).quadTo(56.29136, -3.6705832, 56.025635, -3.3651319);
((GeneralPath)shape).quadTo(55.75991, -3.0596805, 55.75991, -2.4813418);
((GeneralPath)shape).quadTo(55.75991, -1.9043057, 56.025635, -1.5988543);
((GeneralPath)shape).quadTo(56.29136, -1.2934029, 56.791542, -1.2934029);
((GeneralPath)shape).quadTo(57.045544, -1.2934029, 57.271538, -1.3852336);
((GeneralPath)shape).quadTo(57.49753, -1.4770645, 57.696823, -1.6620287);
((GeneralPath)shape).lineTo(57.696823, -1.2504183);
((GeneralPath)shape).quadTo(57.489716, -1.1097413, 57.25786, -1.0387515);
((GeneralPath)shape).quadTo(57.026005, -0.96776175, 56.768097, -0.96776175);
((GeneralPath)shape).quadTo(56.10639, -0.96776175, 55.72539, -1.3735106);
((GeneralPath)shape).quadTo(55.34439, -1.7792594, 55.34439, -2.4813418);
((GeneralPath)shape).quadTo(55.34439, -3.1847265, 55.72539, -3.5898242);
((GeneralPath)shape).quadTo(56.10639, -3.9949217, 56.768097, -3.9949217);
((GeneralPath)shape).quadTo(57.02991, -3.9949217, 57.26177, -3.9258857);
((GeneralPath)shape).quadTo(57.493626, -3.85685, 57.696823, -3.7174754);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(0.8727239966392517f, 0.4041920006275177f, -0.6862050294876099f, 0.828029990196228f, 0.0f, 0.0f));
// _0_0_2 is TextNode of 'g'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(60.93025, 27.016533);
((GeneralPath)shape).lineTo(47.039703, 27.016533);
((GeneralPath)shape).quadTo(42.267105, 27.016533, 39.14616, 23.848696);
((GeneralPath)shape).quadTo(36.025215, 20.680859, 36.025215, 15.845739);
((GeneralPath)shape).quadTo(36.025215, 11.041882, 39.1149, 8.098085);
((GeneralPath)shape).quadTo(42.204582, 5.15429, 47.039703, 5.15429);
((GeneralPath)shape).lineTo(59.367172, 5.15429);
((GeneralPath)shape).lineTo(59.367172, 8.957778);
((GeneralPath)shape).lineTo(47.039703, 8.957778);
((GeneralPath)shape).quadTo(43.903126, 8.957778, 41.871124, 10.974149);
((GeneralPath)shape).quadTo(39.839127, 12.990519, 39.839127, 16.168776);
((GeneralPath)shape).quadTo(39.839127, 19.305351, 41.871124, 21.253988);
((GeneralPath)shape).quadTo(43.903126, 23.202623, 47.039703, 23.202623);
((GeneralPath)shape).lineTo(57.126762, 23.202623);
((GeneralPath)shape).lineTo(57.126762, 18.440449);
((GeneralPath)shape).lineTo(46.758347, 18.440449);
((GeneralPath)shape).lineTo(46.758347, 14.949575);
((GeneralPath)shape).lineTo(60.93025, 14.949575);
((GeneralPath)shape).lineTo(60.93025, 27.016533);
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
// _0_0_3 is TextNode of '+'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(64.00584, 50.095497);
((GeneralPath)shape).lineTo(56.71417, 50.095497);
((GeneralPath)shape).lineTo(56.71417, 58.095497);
((GeneralPath)shape).lineTo(49.08917, 58.095497);
((GeneralPath)shape).lineTo(49.08917, 50.095497);
((GeneralPath)shape).lineTo(41.46417, 50.095497);
((GeneralPath)shape).lineTo(41.46417, 42.55383);
((GeneralPath)shape).lineTo(49.08917, 42.55383);
((GeneralPath)shape).lineTo(49.08917, 34.99133);
((GeneralPath)shape).lineTo(56.71417, 34.99133);
((GeneralPath)shape).lineTo(56.71417, 42.55383);
((GeneralPath)shape).lineTo(64.00584, 42.55383);
((GeneralPath)shape).lineTo(64.00584, 50.095497);
((GeneralPath)shape).closePath();
paint = getColor(0, 155, 0, 255, disabled);
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
        return 2;
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
	public AddDocumentIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public AddDocumentIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public AddDocumentIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public AddDocumentIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public AddDocumentIcon(int width, int height) {
		this(width, height, false);
	}
	
	public AddDocumentIcon(int width, int height, boolean disabled) {
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

