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
public class GATEXMLIcon implements
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
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,48.0003547668457,48.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(0.8500000238418579f, 0.0f, 0.0f, 0.8500000238418579f, 4.0f, -2.7999989986419678f));
// _0_0 is CompositeGraphicsNode
float alpha__0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.186287, 27.999989);
((GeneralPath)shape).curveTo(39.320816, 51.410988, 0.81336004, 46.822826, 0.81336004, 46.822826);
((GeneralPath)shape).lineTo(0.81336004, 9.177162);
((GeneralPath)shape).curveTo(0.81336004, 9.177162, 39.051773, 4.589025, 39.186287, 27.99999);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 128, 0, 255);
stroke = new BasicStroke(1.6267201f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.186287, 27.999989);
((GeneralPath)shape).curveTo(39.320816, 51.410988, 0.81336004, 46.822826, 0.81336004, 46.822826);
((GeneralPath)shape).lineTo(0.81336004, 9.177162);
((GeneralPath)shape).curveTo(0.81336004, 9.177162, 39.051773, 4.589025, 39.186287, 27.99999);
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
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(30.316797, 39.760696);
((GeneralPath)shape).lineTo(15.039994, 39.760696);
((GeneralPath)shape).curveTo(11.543449, 39.760696, 8.645321, 38.599094, 6.3455925, 36.275894);
((GeneralPath)shape).curveTo(4.0693283, 33.952694, 2.9311917, 31.019365, 2.9311917, 27.475891);
((GeneralPath)shape).curveTo(2.9311917, 23.955906, 4.057592, 21.116442, 6.3103924, 18.957489);
((GeneralPath)shape).curveTo(8.586657, 16.798576, 11.496521, 15.719112, 15.039994, 15.719088);
((GeneralPath)shape).lineTo(28.627197, 15.719088);
((GeneralPath)shape).lineTo(28.627197, 19.907888);
((GeneralPath)shape).lineTo(15.039994, 19.907888);
((GeneralPath)shape).curveTo(12.740251, 19.907904, 10.839449, 20.647104, 9.337593, 22.12549);
((GeneralPath)shape).curveTo(7.8591847, 23.603907, 7.1199923, 25.504698, 7.1199923, 27.827892);
((GeneralPath)shape).curveTo(7.1199923, 30.127636, 7.8591843, 31.993229, 9.337593, 33.424694);
((GeneralPath)shape).curveTo(10.839449, 34.856167, 12.74025, 35.571896, 15.039994, 35.571896);
((GeneralPath)shape).lineTo(26.127998, 35.571896);
((GeneralPath)shape).lineTo(26.127998, 30.327095);
((GeneralPath)shape).lineTo(14.723195, 30.327095);
((GeneralPath)shape).lineTo(14.723195, 26.490294);
((GeneralPath)shape).lineTo(30.3168, 26.490294);
((GeneralPath)shape).lineTo(30.3168, 39.760696);
g.setPaint(paint);
g.fill(shape);
paint = new Color(128, 0, 0, 255);
stroke = new BasicStroke(0.8000002f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(30.316797, 39.760696);
((GeneralPath)shape).lineTo(15.039994, 39.760696);
((GeneralPath)shape).curveTo(11.543449, 39.760696, 8.645321, 38.599094, 6.3455925, 36.275894);
((GeneralPath)shape).curveTo(4.0693283, 33.952694, 2.9311917, 31.019365, 2.9311917, 27.475891);
((GeneralPath)shape).curveTo(2.9311917, 23.955906, 4.057592, 21.116442, 6.3103924, 18.957489);
((GeneralPath)shape).curveTo(8.586657, 16.798576, 11.496521, 15.719112, 15.039994, 15.719088);
((GeneralPath)shape).lineTo(28.627197, 15.719088);
((GeneralPath)shape).lineTo(28.627197, 19.907888);
((GeneralPath)shape).lineTo(15.039994, 19.907888);
((GeneralPath)shape).curveTo(12.740251, 19.907904, 10.839449, 20.647104, 9.337593, 22.12549);
((GeneralPath)shape).curveTo(7.8591847, 23.603907, 7.1199923, 25.504698, 7.1199923, 27.827892);
((GeneralPath)shape).curveTo(7.1199923, 30.127636, 7.8591843, 31.993229, 9.337593, 33.424694);
((GeneralPath)shape).curveTo(10.839449, 34.856167, 12.74025, 35.571896, 15.039994, 35.571896);
((GeneralPath)shape).lineTo(26.127998, 35.571896);
((GeneralPath)shape).lineTo(26.127998, 30.327095);
((GeneralPath)shape).lineTo(14.723195, 30.327095);
((GeneralPath)shape).lineTo(14.723195, 26.490294);
((GeneralPath)shape).lineTo(30.3168, 26.490294);
((GeneralPath)shape).lineTo(30.3168, 39.760696);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
origAlpha = alpha__0_0;
g.setTransform(defaultTransform__0_0);
g.setClip(clip__0_0);
float alpha__0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1 = g.getClip();
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(0.944444477558136f, 0.0f, 0.0f, 0.944444477558136f, 11.0f, 31.77777671813965f));
// _0_1 is CompositeGraphicsNode
float alpha__0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_0 = g.getClip();
AffineTransform defaultTransform__0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_0 is ShapeNode
paint = new Color(128, 51, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(36.0, 14.0);
((GeneralPath)shape).lineTo(35.0, 13.0);
((GeneralPath)shape).lineTo(35.0, 1.0);
((GeneralPath)shape).lineTo(36.0, 0.0);
((GeneralPath)shape).lineTo(36.0, 14.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new Color(255, 102, 0, 255);
shape = new Rectangle2D.Double(1.0, 1.0, 34.0, 12.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_1;
g.setTransform(defaultTransform__0_1_1);
g.setClip(clip__0_1_1);
float alpha__0_1_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_2 = g.getClip();
AffineTransform defaultTransform__0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_2 is ShapeNode
paint = new Color(255, 204, 170, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(0.0, 0.0);
((GeneralPath)shape).lineTo(36.0, 0.0);
((GeneralPath)shape).lineTo(35.0, 1.0);
((GeneralPath)shape).lineTo(1.0, 1.0);
((GeneralPath)shape).lineTo(0.0, 0.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new Color(85, 34, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(0.0, 14.0);
((GeneralPath)shape).lineTo(36.0, 14.0);
((GeneralPath)shape).lineTo(35.0, 13.0);
((GeneralPath)shape).lineTo(1.0, 13.0);
((GeneralPath)shape).lineTo(0.0, 14.0);
((GeneralPath)shape).closePath();
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
// _0_1_4 is ShapeNode
paint = new Color(255, 153, 85, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(1.0, 13.0);
((GeneralPath)shape).lineTo(0.0, 14.0);
((GeneralPath)shape).lineTo(0.0, 0.0);
((GeneralPath)shape).lineTo(1.0, 1.0);
((GeneralPath)shape).lineTo(1.0, 13.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_4;
g.setTransform(defaultTransform__0_1_4);
g.setClip(clip__0_1_4);
float alpha__0_1_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_5 = g.getClip();
AffineTransform defaultTransform__0_1_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f));
// _0_1_5 is CompositeGraphicsNode
float alpha__0_1_5_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_5_0 = g.getClip();
AffineTransform defaultTransform__0_1_5_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_5_0 is ShapeNode
paint = new Color(128, 51, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(4.2424884, 2.9393196);
((GeneralPath)shape).lineTo(7.1663165, 2.9393196);
((GeneralPath)shape).lineTo(8.6897545, 5.5818977);
((GeneralPath)shape).lineTo(10.166317, 2.9393196);
((GeneralPath)shape).lineTo(13.060848, 2.9393196);
((GeneralPath)shape).lineTo(10.388973, 7.099476);
((GeneralPath)shape).lineTo(13.312801, 11.529163);
((GeneralPath)shape).lineTo(10.3303795, 11.529163);
((GeneralPath)shape).lineTo(8.63702, 8.769398);
((GeneralPath)shape).lineTo(6.9378014, 11.529163);
((GeneralPath)shape).lineTo(3.9729576, 11.529163);
((GeneralPath)shape).lineTo(6.937801, 7.052601);
((GeneralPath)shape).lineTo(4.2424884, 2.9393196);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(14.133114, 2.9393196);
((GeneralPath)shape).lineTo(17.625301, 2.9393196);
((GeneralPath)shape).lineTo(18.972958, 8.165882);
((GeneralPath)shape).lineTo(20.314754, 2.9393196);
((GeneralPath)shape).lineTo(23.795223, 2.9393196);
((GeneralPath)shape).lineTo(23.795223, 11.529163);
((GeneralPath)shape).lineTo(21.627254, 11.529163);
((GeneralPath)shape).lineTo(21.627254, 4.9783826);
((GeneralPath)shape).lineTo(19.945614, 11.529163);
((GeneralPath)shape).lineTo(17.982723, 11.529163);
((GeneralPath)shape).lineTo(16.306942, 4.9783826);
((GeneralPath)shape).lineTo(16.306942, 11.529163);
((GeneralPath)shape).lineTo(14.133113, 11.529163);
((GeneralPath)shape).lineTo(14.133113, 2.9393196);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(25.482723, 2.9393196);
((GeneralPath)shape).lineTo(28.13702, 2.9393196);
((GeneralPath)shape).lineTo(28.13702, 9.413929);
((GeneralPath)shape).lineTo(32.2796, 9.413929);
((GeneralPath)shape).lineTo(32.2796, 11.529163);
((GeneralPath)shape).lineTo(25.482723, 11.529163);
((GeneralPath)shape).lineTo(25.482723, 2.9393196);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_5_0;
g.setTransform(defaultTransform__0_1_5_0);
g.setClip(clip__0_1_5_0);
origAlpha = alpha__0_1_5;
g.setTransform(defaultTransform__0_1_5);
g.setClip(clip__0_1_5);
float alpha__0_1_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_6 = g.getClip();
AffineTransform defaultTransform__0_1_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_6 is CompositeGraphicsNode
float alpha__0_1_6_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_6_0 = g.getClip();
AffineTransform defaultTransform__0_1_6_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_6_0 is ShapeNode
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(4.2424884, 2.9393196);
((GeneralPath)shape).lineTo(7.1663165, 2.9393196);
((GeneralPath)shape).lineTo(8.6897545, 5.5818977);
((GeneralPath)shape).lineTo(10.166317, 2.9393196);
((GeneralPath)shape).lineTo(13.060848, 2.9393196);
((GeneralPath)shape).lineTo(10.388973, 7.099476);
((GeneralPath)shape).lineTo(13.312801, 11.529163);
((GeneralPath)shape).lineTo(10.3303795, 11.529163);
((GeneralPath)shape).lineTo(8.63702, 8.769398);
((GeneralPath)shape).lineTo(6.9378014, 11.529163);
((GeneralPath)shape).lineTo(3.9729576, 11.529163);
((GeneralPath)shape).lineTo(6.937801, 7.052601);
((GeneralPath)shape).lineTo(4.2424884, 2.9393196);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(14.133114, 2.9393196);
((GeneralPath)shape).lineTo(17.625301, 2.9393196);
((GeneralPath)shape).lineTo(18.972958, 8.165882);
((GeneralPath)shape).lineTo(20.314754, 2.9393196);
((GeneralPath)shape).lineTo(23.795223, 2.9393196);
((GeneralPath)shape).lineTo(23.795223, 11.529163);
((GeneralPath)shape).lineTo(21.627254, 11.529163);
((GeneralPath)shape).lineTo(21.627254, 4.9783826);
((GeneralPath)shape).lineTo(19.945614, 11.529163);
((GeneralPath)shape).lineTo(17.982723, 11.529163);
((GeneralPath)shape).lineTo(16.306942, 4.9783826);
((GeneralPath)shape).lineTo(16.306942, 11.529163);
((GeneralPath)shape).lineTo(14.133113, 11.529163);
((GeneralPath)shape).lineTo(14.133113, 2.9393196);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(25.482723, 2.9393196);
((GeneralPath)shape).lineTo(28.13702, 2.9393196);
((GeneralPath)shape).lineTo(28.13702, 9.413929);
((GeneralPath)shape).lineTo(32.2796, 9.413929);
((GeneralPath)shape).lineTo(32.2796, 11.529163);
((GeneralPath)shape).lineTo(25.482723, 11.529163);
((GeneralPath)shape).lineTo(25.482723, 2.9393196);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_6_0;
g.setTransform(defaultTransform__0_1_6_0);
g.setClip(clip__0_1_6_0);
origAlpha = alpha__0_1_6;
g.setTransform(defaultTransform__0_1_6);
g.setClip(clip__0_1_6);
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
        return 4;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 2;
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
	public GATEXMLIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public GATEXMLIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public GATEXMLIcon(int width, int height) {
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

