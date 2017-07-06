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
public class PronominalCoreferencerIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(19.933016, 17.229038);
((GeneralPath)shape).curveTo(20.684113, 19.724203, 22.384869, 21.94141, 23.576942, 24.272385);
((GeneralPath)shape).curveTo(25.389523, 28.064672, 27.145678, 31.662674, 27.512857, 35.854412);
((GeneralPath)shape).curveTo(28.07328, 39.026054, 27.226728, 41.409763, 24.858273, 43.597633);
((GeneralPath)shape).curveTo(22.822382, 45.38393, 20.75262, 45.894768, 18.198008, 45.795338);
((GeneralPath)shape).lineTo(19.690107, 44.73141);
((GeneralPath)shape).curveTo(23.059732, 44.866795, 22.71022, 44.49698, 23.217623, 44.25807);
((GeneralPath)shape).curveTo(25.637161, 42.17618, 26.499998, 39.782776, 25.993147, 36.645836);
((GeneralPath)shape).curveTo(25.618614, 32.51372, 24.01842, 28.778221, 22.187876, 25.066551);
((GeneralPath)shape).curveTo(20.934988, 22.717342, 19.238543, 20.53871, 18.248802, 18.087187);
((GeneralPath)shape).lineTo(19.933016, 17.229038);
((GeneralPath)shape).closePath();
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
// _0_0_1 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(20.422728, 47.080837);
((GeneralPath)shape).curveTo(21.983831, 47.133476, 23.539755, 47.155537, 25.088802, 46.950844);
((GeneralPath)shape).curveTo(27.187714, 46.491062, 29.32564, 46.33117, 31.457382, 46.11793);
((GeneralPath)shape).curveTo(33.153313, 46.03762, 34.82577, 45.778667, 36.49367, 45.476334);
((GeneralPath)shape).curveTo(38.78894, 45.13962, 40.837082, 44.110397, 42.875748, 43.073215);
((GeneralPath)shape).curveTo(43.34611, 42.814697, 45.39647, 41.863262, 47.73348, 38.79075);
((GeneralPath)shape).curveTo(49.17003, 36.569645, 50.389336, 34.143497, 50.93044, 31.540731);
((GeneralPath)shape).curveTo(51.057533, 30.372728, 51.012135, 29.190536, 51.00027, 28.016743);
((GeneralPath)shape).curveTo(50.991108, 27.131624, 50.99306, 26.246443, 50.993137, 25.36129);
((GeneralPath)shape).curveTo(50.961445, 24.974636, 51.19529, 24.72783, 51.358467, 24.41697);
((GeneralPath)shape).lineTo(53.062885, 23.686152);
((GeneralPath)shape).curveTo(52.881172, 23.959991, 52.650806, 24.160894, 52.643215, 24.520536);
((GeneralPath)shape).curveTo(52.643215, 25.40773, 52.644115, 26.294933, 52.637417, 27.182108);
((GeneralPath)shape).curveTo(52.633034, 28.371439, 52.719753, 29.569342, 52.554646, 30.750595);
((GeneralPath)shape).curveTo(51.95709, 33.354393, 50.776913, 35.85694, 49.34157, 38.099552);
((GeneralPath)shape).curveTo(47.26605, 40.79901, 44.550064, 42.725952, 41.481903, 44.18947);
((GeneralPath)shape).curveTo(39.43913, 45.243595, 37.35067, 46.207275, 35.054413, 46.560143);
((GeneralPath)shape).curveTo(33.38601, 46.864178, 31.71154, 47.114132, 30.014528, 47.18722);
((GeneralPath)shape).curveTo(27.892649, 47.393105, 25.76738, 47.569714, 23.676565, 48.010174);
((GeneralPath)shape).curveTo(22.091267, 48.246284, 20.49062, 48.1311, 18.893496, 48.19189);
((GeneralPath)shape).lineTo(20.422728, 47.080837);
((GeneralPath)shape).closePath();
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
g.transform(new AffineTransform(0.8104820251464844f, 0.0f, 0.0f, 0.7691500186920166f, 9.79211711883545f, 2.1042370796203613f));
// _0_0_2 is ShapeNode
paint = getColor(3, 184, 31, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(21.636364, 28.545456);
((GeneralPath)shape).curveTo(34.691933, 13.657528, -9.460201, 22.705843, 9.852878, 18.335127);
((GeneralPath)shape).curveTo(29.165956, 13.964411, -14.579854, 24.808157, 3.613797, 32.62412);
((GeneralPath)shape).curveTo(21.807447, 40.44008, -0.44176996, 1.2449863, 9.683098, 18.26219);
((GeneralPath)shape).curveTo(19.807964, 35.279392, -4.023249, -2.974442, -5.834521, 16.744013);
((GeneralPath)shape).curveTo(-7.645793, 36.462467, 22.755568, 3.190255, 9.700001, 18.078182);
((GeneralPath)shape).curveTo(-3.3555663, 32.966106, 25.661743, -1.519809, 6.3486648, 2.8509066);
((GeneralPath)shape).curveTo(-12.964414, 7.2216225, 28.073877, 25.853354, 9.880226, 18.037395);
((GeneralPath)shape).curveTo(-8.313426, 10.221436, 33.451473, 27.161802, 23.326605, 10.144599);
((GeneralPath)shape).curveTo(13.201738, -6.8726053, 8.163438, 37.91465, 9.9747095, 18.196196);
((GeneralPath)shape).curveTo(11.78598, -1.522259, 8.580797, 43.43338, 21.636364, 28.545456);
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
g.transform(new AffineTransform(0.7132239937782288f, 0.0f, 0.0f, 0.673005998134613f, 6.217073917388916f, 31.34122085571289f));
// _0_0_3 is ShapeNode
paint = getColor(3, 184, 31, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(21.636364, 28.545456);
((GeneralPath)shape).curveTo(34.691933, 13.657528, -9.460201, 22.705843, 9.852878, 18.335127);
((GeneralPath)shape).curveTo(29.165956, 13.964411, -14.579854, 24.808157, 3.613797, 32.62412);
((GeneralPath)shape).curveTo(21.807447, 40.44008, -0.44176996, 1.2449863, 9.683098, 18.26219);
((GeneralPath)shape).curveTo(19.807964, 35.279392, -4.023249, -2.974442, -5.834521, 16.744013);
((GeneralPath)shape).curveTo(-7.645793, 36.462467, 22.755568, 3.190255, 9.700001, 18.078182);
((GeneralPath)shape).curveTo(-3.3555663, 32.966106, 25.661743, -1.519809, 6.3486648, 2.8509066);
((GeneralPath)shape).curveTo(-12.964414, 7.2216225, 28.073877, 25.853354, 9.880226, 18.037395);
((GeneralPath)shape).curveTo(-8.313426, 10.221436, 33.451473, 27.161802, 23.326605, 10.144599);
((GeneralPath)shape).curveTo(13.201738, -6.8726053, 8.163438, 37.91465, 9.9747095, 18.196196);
((GeneralPath)shape).curveTo(11.78598, -1.522259, 8.580797, 43.43338, 21.636364, 28.545456);
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
g.transform(new AffineTransform(0.7406460046768188f, 0.0f, 0.0f, 0.673005998134613f, 45.37919998168945f, 10.34119987487793f));
// _0_0_4 is ShapeNode
paint = getColor(3, 184, 31, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(21.636364, 28.545456);
((GeneralPath)shape).curveTo(34.691933, 13.657528, -9.460201, 22.705843, 9.852878, 18.335127);
((GeneralPath)shape).curveTo(29.165956, 13.964411, -14.579854, 24.808157, 3.613797, 32.62412);
((GeneralPath)shape).curveTo(21.807447, 40.44008, -0.44176996, 1.2449863, 9.683098, 18.26219);
((GeneralPath)shape).curveTo(19.807964, 35.279392, -4.023249, -2.974442, -5.834521, 16.744013);
((GeneralPath)shape).curveTo(-7.645793, 36.462467, 22.755568, 3.190255, 9.700001, 18.078182);
((GeneralPath)shape).curveTo(-3.3555663, 32.966106, 25.661743, -1.519809, 6.3486648, 2.8509066);
((GeneralPath)shape).curveTo(-12.964414, 7.2216225, 28.073877, 25.853354, 9.880226, 18.037395);
((GeneralPath)shape).curveTo(-8.313426, 10.221436, 33.451473, 27.161802, 23.326605, 10.144599);
((GeneralPath)shape).curveTo(13.201738, -6.8726053, 8.163438, 37.91465, 9.9747095, 18.196196);
((GeneralPath)shape).curveTo(11.78598, -1.522259, 8.580797, 43.43338, 21.636364, 28.545456);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_4;
g.setTransform(defaultTransform__0_0_4);
g.setClip(clip__0_0_4);
float alpha__0_0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5 = g.getClip();
AffineTransform defaultTransform__0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_5 is TextNode of 'co'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.496437, 62.0);
((GeneralPath)shape).lineTo(30.25425, 62.0);
((GeneralPath)shape).quadTo(26.676125, 62.0, 24.33628, 59.625);
((GeneralPath)shape).quadTo(21.996437, 57.25, 21.996437, 53.625);
((GeneralPath)shape).quadTo(21.996437, 50.023438, 24.312843, 47.816406);
((GeneralPath)shape).quadTo(26.62925, 45.609375, 30.25425, 45.609375);
((GeneralPath)shape).lineTo(39.496437, 45.609375);
((GeneralPath)shape).lineTo(39.496437, 48.460938);
((GeneralPath)shape).lineTo(30.25425, 48.460938);
((GeneralPath)shape).quadTo(27.902687, 48.460938, 26.37925, 49.972656);
((GeneralPath)shape).quadTo(24.855812, 51.484375, 24.855812, 53.867188);
((GeneralPath)shape).quadTo(24.855812, 56.21875, 26.37925, 57.679688);
((GeneralPath)shape).quadTo(27.902687, 59.140625, 30.25425, 59.140625);
((GeneralPath)shape).lineTo(39.496437, 59.140625);
((GeneralPath)shape).lineTo(39.496437, 62.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(62.946037, 53.625);
((GeneralPath)shape).quadTo(62.946037, 57.25, 60.617912, 59.625);
((GeneralPath)shape).quadTo(58.289787, 62.0, 54.688225, 62.0);
((GeneralPath)shape).lineTo(50.102287, 62.0);
((GeneralPath)shape).quadTo(46.524162, 62.0, 44.18432, 59.625);
((GeneralPath)shape).quadTo(41.844475, 57.25, 41.844475, 53.625);
((GeneralPath)shape).quadTo(41.844475, 50.023438, 44.16088, 47.816406);
((GeneralPath)shape).quadTo(46.477287, 45.609375, 50.102287, 45.609375);
((GeneralPath)shape).lineTo(54.688225, 45.609375);
((GeneralPath)shape).quadTo(58.336662, 45.609375, 60.64135, 47.816406);
((GeneralPath)shape).quadTo(62.946037, 50.023438, 62.946037, 53.625);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(60.1101, 53.867188);
((GeneralPath)shape).quadTo(60.1101, 51.484375, 58.586662, 49.972656);
((GeneralPath)shape).quadTo(57.063225, 48.460938, 54.688225, 48.460938);
((GeneralPath)shape).lineTo(50.102287, 48.460938);
((GeneralPath)shape).quadTo(47.750725, 48.460938, 46.227287, 49.972656);
((GeneralPath)shape).quadTo(44.70385, 51.484375, 44.70385, 53.867188);
((GeneralPath)shape).quadTo(44.70385, 56.21875, 46.227287, 57.679688);
((GeneralPath)shape).quadTo(47.750725, 59.140625, 50.102287, 59.140625);
((GeneralPath)shape).lineTo(54.688225, 59.140625);
((GeneralPath)shape).quadTo(57.063225, 59.140625, 58.586662, 57.679688);
((GeneralPath)shape).quadTo(60.1101, 56.21875, 60.1101, 53.867188);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
float alpha__0_0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6 is TextNode of 'P'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(45.858498, 31.907623);
((GeneralPath)shape).quadTo(45.858498, 34.595123, 43.889748, 36.251373);
((GeneralPath)shape).quadTo(42.061623, 37.782623, 39.30381, 37.782623);
((GeneralPath)shape).lineTo(32.077248, 37.782623);
((GeneralPath)shape).lineTo(32.077248, 34.9545);
((GeneralPath)shape).lineTo(39.756935, 34.9545);
((GeneralPath)shape).quadTo(41.17881, 34.9545, 42.13584, 34.114655);
((GeneralPath)shape).quadTo(43.092873, 33.27481, 43.092873, 31.876373);
((GeneralPath)shape).quadTo(43.092873, 30.485748, 42.13584, 29.669342);
((GeneralPath)shape).quadTo(41.17881, 28.852936, 39.756935, 28.852936);
((GeneralPath)shape).lineTo(30.397562, 28.852936);
((GeneralPath)shape).lineTo(30.397562, 42.392);
((GeneralPath)shape).lineTo(27.546, 42.392);
((GeneralPath)shape).lineTo(27.546, 26.001373);
((GeneralPath)shape).lineTo(39.30381, 26.001373);
((GeneralPath)shape).quadTo(42.061623, 26.001373, 43.889748, 27.563873);
((GeneralPath)shape).quadTo(45.858498, 29.188873, 45.858498, 31.907623);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_6;
g.setTransform(defaultTransform__0_0_6);
g.setClip(clip__0_0_6);
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
	public PronominalCoreferencerIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public PronominalCoreferencerIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public PronominalCoreferencerIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public PronominalCoreferencerIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public PronominalCoreferencerIcon(int width, int height) {
		this(width, height, false);
	}
	
	public PronominalCoreferencerIcon(int width, int height, boolean disabled) {
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

