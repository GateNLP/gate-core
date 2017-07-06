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
public class PosTaggerIcon implements
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
paint = getColor(255, 96, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.87066, 5.5269103);
((GeneralPath)shape).lineTo(14.62066, 11.74566);
((GeneralPath)shape).lineTo(6.5269103, 21.71441);
((GeneralPath)shape).lineTo(18.90191, 46.65191);
((GeneralPath)shape).lineTo(22.58941, 60.05816);
((GeneralPath)shape).lineTo(40.99566, 47.99566);
((GeneralPath)shape).lineTo(59.96441, 43.21441);
((GeneralPath)shape).lineTo(51.65191, 32.65191);
((GeneralPath)shape).lineTo(39.58941, 6.0269103);
((GeneralPath)shape).lineTo(27.55816, 6.1831603);
((GeneralPath)shape).lineTo(18.87066, 5.5269103);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.33941, 8.49566);
((GeneralPath)shape).curveTo(24.647774, 8.49566, 26.52691, 9.883228, 26.52691, 11.58941);
((GeneralPath)shape).curveTo(26.52691, 13.295592, 24.647774, 14.68316, 22.33941, 14.68316);
((GeneralPath)shape).curveTo(20.031046, 14.68316, 18.15191, 13.295592, 18.15191, 11.58941);
((GeneralPath)shape).curveTo(18.15191, 9.883228, 20.031046, 8.49566, 22.33941, 8.49566);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0538201f,0,2,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.87066, 5.5269103);
((GeneralPath)shape).lineTo(14.62066, 11.74566);
((GeneralPath)shape).lineTo(6.5269103, 21.71441);
((GeneralPath)shape).lineTo(18.90191, 46.65191);
((GeneralPath)shape).lineTo(22.58941, 60.05816);
((GeneralPath)shape).lineTo(40.99566, 47.99566);
((GeneralPath)shape).lineTo(59.96441, 43.21441);
((GeneralPath)shape).lineTo(51.65191, 32.65191);
((GeneralPath)shape).lineTo(39.58941, 6.0269103);
((GeneralPath)shape).lineTo(27.55816, 6.1831603);
((GeneralPath)shape).lineTo(18.87066, 5.5269103);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.33941, 8.49566);
((GeneralPath)shape).curveTo(24.647774, 8.49566, 26.52691, 9.883228, 26.52691, 11.58941);
((GeneralPath)shape).curveTo(26.52691, 13.295592, 24.647774, 14.68316, 22.33941, 14.68316);
((GeneralPath)shape).curveTo(20.031046, 14.68316, 18.15191, 13.295592, 18.15191, 11.58941);
((GeneralPath)shape).curveTo(18.15191, 9.883228, 20.031046, 8.49566, 22.33941, 8.49566);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.248802, 13.134598);
((GeneralPath)shape).curveTo(18.864735, 12.258546, 20.73572, 6.661993, 18.691874, 4.8040214);
((GeneralPath)shape).curveTo(16.27712, 3.0826528, 13.204764, 3.2431507, 10.374626, 3.2341182);
((GeneralPath)shape).curveTo(5.8652205, 3.5492685, 6.137818, 3.093267, 5.0362844, 5.280536);
((GeneralPath)shape).curveTo(4.01761, 7.6499643, 3.8287318, 10.206978, 3.623947, 12.741302);
((GeneralPath)shape).curveTo(3.54266, 14.49553, 3.3353076, 16.297752, 3.9033673, 17.982813);
((GeneralPath)shape).curveTo(4.3630333, 18.788765, 5.1507306, 18.721052, 5.9861503, 18.733496);
((GeneralPath)shape).curveTo(6.977091, 18.845398, 7.737371, 18.494995, 8.581484, 18.025984);
((GeneralPath)shape).curveTo(6.093556, 19.964603, 9.016995, 16.96169, 9.589193, 15.806418);
((GeneralPath)shape).curveTo(10.077704, 14.481013, 10.908282, 13.310221, 11.643441, 12.111846);
((GeneralPath)shape).lineTo(13.346401, 11.389376);
((GeneralPath)shape).curveTo(12.551548, 12.537958, 11.745927, 13.739984, 11.239951, 15.052452);
((GeneralPath)shape).curveTo(10.289003, 17.010778, 9.216036, 18.235622, 7.154622, 19.179321);
((GeneralPath)shape).curveTo(6.28371, 19.641565, 5.491494, 19.932596, 4.4877515, 19.821388);
((GeneralPath)shape).curveTo(3.5948422, 19.803518, 2.73567, 19.787008, 2.2995095, 18.885908);
((GeneralPath)shape).curveTo(1.7503449, 17.15864, 1.9297796, 15.325279, 2.0365553, 13.534745);
((GeneralPath)shape).curveTo(2.2494805, 10.98062, 2.4523208, 8.431785, 3.4198196, 6.0207777);
((GeneralPath)shape).curveTo(4.8563066, 2.884435, 8.633816, 2.1411111, 11.793167, 2.2036011);
((GeneralPath)shape).curveTo(14.644534, 2.1975472, 17.760326, 2.0842636, 20.13111, 3.9153817);
((GeneralPath)shape).curveTo(22.114399, 5.820967, 21.791563, 7.024051, 21.751198, 9.730996);
((GeneralPath)shape).lineTo(18.248802, 13.134598);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(13.150001, 11.080838);
((GeneralPath)shape).curveTo(13.848586, 11.051623, 14.485891, 11.076095, 15.114428, 11.321623);
((GeneralPath)shape).curveTo(15.148484, 11.35034, 15.18254, 11.379057, 15.216596, 11.407774);
((GeneralPath)shape).lineTo(13.636198, 12.396685);
((GeneralPath)shape).curveTo(13.603609, 12.370184, 13.57102, 12.343682, 13.538431, 12.317181);
((GeneralPath)shape).curveTo(12.918315, 12.194845, 12.276155, 12.129444, 11.620769, 12.19189);
((GeneralPath)shape).lineTo(13.150001, 11.080838);
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
g.transform(new AffineTransform(0.933426022529602f, -0.3587709963321686f, 0.3587709963321686f, 0.933426022529602f, 0.0f, 0.0f));
// _0_0_3 is TextNode of 'POSTAG'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.848661, 31.630566);
((GeneralPath)shape).quadTo(11.848661, 33.086296, 10.782254, 33.983433);
((GeneralPath)shape).quadTo(9.79202, 34.81286, 8.298204, 34.81286);
((GeneralPath)shape).lineTo(4.383815, 34.81286);
((GeneralPath)shape).lineTo(4.383815, 33.280956);
((GeneralPath)shape).lineTo(8.543647, 33.280956);
((GeneralPath)shape).quadTo(9.313829, 33.280956, 9.832222, 32.826042);
((GeneralPath)shape).quadTo(10.350614, 32.371128, 10.350614, 31.61364);
((GeneralPath)shape).quadTo(10.350614, 30.860384, 9.832222, 30.418163);
((GeneralPath)shape).quadTo(9.313829, 29.975943, 8.543647, 29.975943);
((GeneralPath)shape).lineTo(3.4739835, 29.975943);
((GeneralPath)shape).lineTo(3.4739835, 37.309605);
((GeneralPath)shape).lineTo(1.9293867, 37.309605);
((GeneralPath)shape).lineTo(1.9293867, 28.431347);
((GeneralPath)shape).lineTo(8.298204, 28.431347);
((GeneralPath)shape).quadTo(9.79202, 28.431347, 10.782254, 29.2777);
((GeneralPath)shape).quadTo(11.848661, 30.15791, 11.848661, 31.630566);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(24.172417, 32.773144);
((GeneralPath)shape).quadTo(24.172417, 34.736687, 22.91135, 36.023148);
((GeneralPath)shape).quadTo(21.650282, 37.309605, 19.699434, 37.309605);
((GeneralPath)shape).lineTo(17.215384, 37.309605);
((GeneralPath)shape).quadTo(15.277232, 37.309605, 14.009816, 36.023148);
((GeneralPath)shape).quadTo(12.7424, 34.736687, 12.7424, 32.773144);
((GeneralPath)shape).quadTo(12.7424, 30.822298, 13.997121, 29.626822);
((GeneralPath)shape).quadTo(15.251842, 28.431347, 17.215384, 28.431347);
((GeneralPath)shape).lineTo(19.699434, 28.431347);
((GeneralPath)shape).quadTo(21.675673, 28.431347, 22.924046, 29.626822);
((GeneralPath)shape).quadTo(24.172417, 30.822298, 24.172417, 32.773144);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.636284, 32.90433);
((GeneralPath)shape).quadTo(22.636284, 31.61364, 21.811089, 30.79479);
((GeneralPath)shape).quadTo(20.985893, 29.975943, 19.699434, 29.975943);
((GeneralPath)shape).lineTo(17.215384, 29.975943);
((GeneralPath)shape).quadTo(15.94162, 29.975943, 15.116425, 30.79479);
((GeneralPath)shape).quadTo(14.291229, 31.61364, 14.291229, 32.90433);
((GeneralPath)shape).quadTo(14.291229, 34.178093, 15.116425, 34.969433);
((GeneralPath)shape).quadTo(15.94162, 35.760777, 17.215384, 35.760777);
((GeneralPath)shape).lineTo(19.699434, 35.760777);
((GeneralPath)shape).quadTo(20.985893, 35.760777, 21.811089, 34.969433);
((GeneralPath)shape).quadTo(22.636284, 34.178093, 22.636284, 32.90433);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(34.94735, 34.567417);
((GeneralPath)shape).quadTo(34.94735, 35.828484, 34.050213, 36.594437);
((GeneralPath)shape).quadTo(33.195396, 37.309605, 31.904705, 37.309605);
((GeneralPath)shape).lineTo(25.13387, 37.309605);
((GeneralPath)shape).lineTo(25.13387, 35.760777);
((GeneralPath)shape).lineTo(31.904705, 35.760777);
((GeneralPath)shape).quadTo(32.5437, 35.760777, 33.013428, 35.477245);
((GeneralPath)shape).quadTo(33.5424, 35.12601, 33.5424, 34.52933);
((GeneralPath)shape).quadTo(33.5424, 33.928417, 32.996502, 33.602573);
((GeneralPath)shape).quadTo(32.5437, 33.331738, 31.904705, 33.331738);
((GeneralPath)shape).lineTo(27.719482, 33.331738);
((GeneralPath)shape).quadTo(26.564209, 33.331738, 25.78133, 32.69274);
((GeneralPath)shape).quadTo(24.951902, 32.007195, 24.951902, 30.87308);
((GeneralPath)shape).quadTo(24.951902, 29.755892, 25.78133, 29.066113);
((GeneralPath)shape).quadTo(26.564209, 28.431347, 27.719482, 28.431347);
((GeneralPath)shape).lineTo(34.414146, 28.431347);
((GeneralPath)shape).lineTo(34.414146, 29.975943);
((GeneralPath)shape).lineTo(27.719482, 29.975943);
((GeneralPath)shape).quadTo(27.279379, 29.975943, 26.966227, 30.223501);
((GeneralPath)shape).quadTo(26.653076, 30.47106, 26.653076, 30.885775);
((GeneralPath)shape).quadTo(26.653076, 31.317415, 26.966227, 31.550163);
((GeneralPath)shape).quadTo(27.279379, 31.78291, 27.719482, 31.78291);
((GeneralPath)shape).lineTo(31.904705, 31.78291);
((GeneralPath)shape).quadTo(33.195396, 31.78291, 34.050213, 32.527702);
((GeneralPath)shape).quadTo(34.94735, 33.306347, 34.94735, 34.567417);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(10.092476, 46.225952);
((GeneralPath)shape).lineTo(6.5970316, 46.225952);
((GeneralPath)shape).lineTo(6.5970316, 53.559612);
((GeneralPath)shape).lineTo(5.0608983, 53.559612);
((GeneralPath)shape).lineTo(5.0608983, 46.225952);
((GeneralPath)shape).lineTo(1.5527589, 46.225952);
((GeneralPath)shape).lineTo(1.5527589, 44.681355);
((GeneralPath)shape).lineTo(10.092476, 44.681355);
((GeneralPath)shape).lineTo(10.092476, 46.225952);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.560074, 53.559612);
((GeneralPath)shape).lineTo(20.57114, 53.546917);
((GeneralPath)shape).lineTo(19.077326, 51.062866);
((GeneralPath)shape).lineTo(14.86248, 51.062866);
((GeneralPath)shape).lineTo(15.797702, 49.530964);
((GeneralPath)shape).lineTo(18.150568, 49.530964);
((GeneralPath)shape).lineTo(16.60597, 46.96651);
((GeneralPath)shape).lineTo(12.602714, 53.546917);
((GeneralPath)shape).lineTo(10.613781, 53.546917);
((GeneralPath)shape).lineTo(15.433769, 45.52771);
((GeneralPath)shape).quadTo(15.615736, 45.214558, 15.941582, 44.952187);
((GeneralPath)shape).quadTo(16.330906, 44.655964, 16.644056, 44.655964);
((GeneralPath)shape).quadTo(16.982597, 44.655964, 17.34653, 44.93949);
((GeneralPath)shape).quadTo(17.659681, 45.189167, 17.854343, 45.52771);
((GeneralPath)shape).lineTo(22.560074, 53.559612);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(33.204365, 53.559612);
((GeneralPath)shape).lineTo(27.563412, 53.559612);
((GeneralPath)shape).quadTo(25.62526, 53.559612, 24.357843, 52.273155);
((GeneralPath)shape).quadTo(23.090427, 50.986694, 23.090427, 49.02315);
((GeneralPath)shape).quadTo(23.090427, 47.072304, 24.345148, 45.87683);
((GeneralPath)shape).quadTo(25.599869, 44.681355, 27.563412, 44.681355);
((GeneralPath)shape).lineTo(32.582294, 44.681355);
((GeneralPath)shape).lineTo(32.582294, 46.225952);
((GeneralPath)shape).lineTo(27.563412, 46.225952);
((GeneralPath)shape).quadTo(26.289648, 46.225952, 25.464453, 47.0448);
((GeneralPath)shape).quadTo(24.639256, 47.863647, 24.639256, 49.15434);
((GeneralPath)shape).quadTo(24.639256, 50.4281, 25.464453, 51.21944);
((GeneralPath)shape).quadTo(26.289648, 52.010784, 27.563412, 52.010784);
((GeneralPath)shape).lineTo(31.659767, 52.010784);
((GeneralPath)shape).lineTo(31.659767, 50.076862);
((GeneralPath)shape).lineTo(27.449154, 50.076862);
((GeneralPath)shape).lineTo(27.449154, 48.65922);
((GeneralPath)shape).lineTo(33.204365, 48.65922);
((GeneralPath)shape).lineTo(33.204365, 53.559612);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
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
        return 3;
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
	public PosTaggerIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public PosTaggerIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public PosTaggerIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public PosTaggerIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public PosTaggerIcon(int width, int height) {
		this(width, height, false);
	}
	
	public PosTaggerIcon(int width, int height, boolean disabled) {
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

