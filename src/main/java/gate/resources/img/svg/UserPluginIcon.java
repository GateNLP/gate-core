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
public class UserPluginIcon implements
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
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,40.0,40.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.4831149578094482f, -3.8465631008148193f));
// _0_0 is CompositeGraphicsNode
origAlpha = alpha__0_0;
g.setTransform(defaultTransform__0_0);
g.setClip(clip__0_0);
float alpha__0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1 = g.getClip();
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.4831149578094482f, -3.8465631008148193f));
// _0_1 is CompositeGraphicsNode
origAlpha = alpha__0_1;
g.setTransform(defaultTransform__0_1);
g.setClip(clip__0_1);
float alpha__0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_2 = g.getClip();
AffineTransform defaultTransform__0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.4831149578094482f, -3.8465631008148193f));
// _0_2 is CompositeGraphicsNode
origAlpha = alpha__0_2;
g.setTransform(defaultTransform__0_2);
g.setClip(clip__0_2);
float alpha__0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_3 = g.getClip();
AffineTransform defaultTransform__0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.4831149578094482f, -3.8465631008148193f));
// _0_3 is CompositeGraphicsNode
origAlpha = alpha__0_3;
g.setTransform(defaultTransform__0_3);
g.setClip(clip__0_3);
float alpha__0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4 = g.getClip();
AffineTransform defaultTransform__0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.4831149578094482f, -3.8465631008148193f));
// _0_4 is CompositeGraphicsNode
float alpha__0_4_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4_0 = g.getClip();
AffineTransform defaultTransform__0_4_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -8.504570960998535f, -3.331191062927246f));
// _0_4_0 is CompositeGraphicsNode
float alpha__0_4_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4_0_0 = g.getClip();
AffineTransform defaultTransform__0_4_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_4_0_0 is ShapeNode
paint = new Color(136, 138, 133, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(35.94331, 41.63604);
((GeneralPath)shape).lineTo(25.336708, 41.63604);
((GeneralPath)shape).curveTo(22.331505, 41.63604, 19.356224, 40.534107, 18.26564, 37.3934);
((GeneralPath)shape).curveTo(17.23, 34.410923, 18.088861, 28.73134, 24.806376, 24.135145);
((GeneralPath)shape).lineTo(37.35752, 24.135145);
((GeneralPath)shape).curveTo(44.075035, 28.377785, 44.914513, 34.179977, 43.367928, 37.570175);
((GeneralPath)shape).curveTo(41.79233, 41.023964, 39.125286, 41.63604, 35.943306, 41.63604);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(85, 87, 83, 255);
stroke = new BasicStroke(1.0f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(35.94331, 41.63604);
((GeneralPath)shape).lineTo(25.336708, 41.63604);
((GeneralPath)shape).curveTo(22.331505, 41.63604, 19.356224, 40.534107, 18.26564, 37.3934);
((GeneralPath)shape).curveTo(17.23, 34.410923, 18.088861, 28.73134, 24.806376, 24.135145);
((GeneralPath)shape).lineTo(37.35752, 24.135145);
((GeneralPath)shape).curveTo(44.075035, 28.377785, 44.914513, 34.179977, 43.367928, 37.570175);
((GeneralPath)shape).curveTo(41.79233, 41.023964, 39.125286, 41.63604, 35.943306, 41.63604);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_4_0_0;
g.setTransform(defaultTransform__0_4_0_0);
g.setClip(clip__0_4_0_0);
float alpha__0_4_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4_0_1 = g.getClip();
AffineTransform defaultTransform__0_4_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_4_0_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(30.93592071533203, 29.553485870361328), new Point2D.Double(30.93592071533203, 35.80348587036133), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(201, 201, 201, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-1.0f, 0.0f, 0.0f, 1.0f, 61.929481506347656f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(35.236202, 25.726135);
((GeneralPath)shape).curveTo(32.054222, 28.554562, 30.640009, 38.80761, 30.640009, 38.80761);
((GeneralPath)shape).curveTo(30.640009, 38.80761, 29.225796, 28.554562, 26.750921, 25.549358);
((GeneralPath)shape).lineTo(35.236202, 25.726135);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_4_0_1;
g.setTransform(defaultTransform__0_4_0_1);
g.setClip(clip__0_4_0_1);
float alpha__0_4_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4_0_2 = g.getClip();
AffineTransform defaultTransform__0_4_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_4_0_2 is ShapeNode
paint = new Color(211, 215, 207, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.95676, 26.786797);
((GeneralPath)shape).curveTo(32.95676, 26.786797, 35.10808, 28.447132, 34.922752, 30.44733);
((GeneralPath)shape).curveTo(36.963978, 28.646536, 37.022625, 25.195807, 37.022625, 25.195807);
((GeneralPath)shape).lineTo(32.95676, 26.786797);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_4_0_2;
g.setTransform(defaultTransform__0_4_0_2);
g.setClip(clip__0_4_0_2);
float alpha__0_4_0_3 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4_0_3 = g.getClip();
AffineTransform defaultTransform__0_4_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_4_0_3 is ShapeNode
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(0.99999976f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(36.01462, 40.593933);
((GeneralPath)shape).lineTo(25.521175, 40.571835);
((GeneralPath)shape).curveTo(22.881453, 40.571835, 20.205515, 39.822666, 19.247562, 37.06392);
((GeneralPath)shape).curveTo(18.337873, 34.44416, 19.217283, 29.236555, 25.117842, 25.199333);
((GeneralPath)shape).lineTo(36.830067, 24.956268);
((GeneralPath)shape).curveTo(42.730625, 28.682936, 43.87269, 33.779488, 42.492096, 37.00045);
((GeneralPath)shape).curveTo(41.111504, 40.221413, 39.096886, 40.57184, 36.01462, 40.593937);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_4_0_3;
g.setTransform(defaultTransform__0_4_0_3);
g.setClip(clip__0_4_0_3);
float alpha__0_4_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4_0_4 = g.getClip();
AffineTransform defaultTransform__0_4_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_4_0_4 is ShapeNode
paint = new Color(211, 215, 207, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.518686, 26.786797);
((GeneralPath)shape).curveTo(28.518686, 26.786797, 26.367363, 28.447132, 26.552696, 30.44733);
((GeneralPath)shape).curveTo(24.51147, 28.646536, 24.452824, 25.195807, 24.452824, 25.195807);
((GeneralPath)shape).lineTo(28.518688, 26.786797);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_4_0_4;
g.setTransform(defaultTransform__0_4_0_4);
g.setClip(clip__0_4_0_4);
float alpha__0_4_0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4_0_5 = g.getClip();
AffineTransform defaultTransform__0_4_0_5 = g.getTransform();
g.transform(new AffineTransform(-1.0f, 0.0f, 0.0f, 1.0f, 62.054481506347656f, 3.5f));
// _0_4_0_5 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(31.11269760131836, 19.008621215820312), 8.662058f, new Point2D.Double(31.11269760131836, 19.008621215820312), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.774754, 19.008621);
((GeneralPath)shape).curveTo(39.774754, 23.792543, 35.896618, 27.67068, 31.112696, 27.67068);
((GeneralPath)shape).curveTo(26.328773, 27.67068, 22.450638, 23.792543, 22.450638, 19.008621);
((GeneralPath)shape).curveTo(22.450638, 14.224699, 26.328773, 10.346563, 31.112696, 10.346563);
((GeneralPath)shape).curveTo(35.896618, 10.346563, 39.774754, 14.224699, 39.774754, 19.008621);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_4_0_5;
g.setTransform(defaultTransform__0_4_0_5);
g.setClip(clip__0_4_0_5);
float alpha__0_4_0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4_0_6 = g.getClip();
AffineTransform defaultTransform__0_4_0_6 = g.getTransform();
g.transform(new AffineTransform(-1.0f, 0.0f, 0.0f, 1.0f, 61.929481506347656f, 0.0f));
// _0_4_0_6 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(29.34493064880371, 17.064077377319336), 9.162058f, new Point2D.Double(29.34493064880371, 17.064077377319336), new float[] {0.0f,1.0f}, new Color[] {new Color(244, 217, 177, 255),new Color(223, 151, 37, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7879980206489563f, 0.0f, 0.0f, 0.7879980206489563f, 6.221198081970215f, 3.617626905441284f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.774754, 19.008621);
((GeneralPath)shape).curveTo(39.774754, 23.792543, 35.896618, 27.67068, 31.112696, 27.67068);
((GeneralPath)shape).curveTo(26.328773, 27.67068, 22.450638, 23.792543, 22.450638, 19.008621);
((GeneralPath)shape).curveTo(22.450638, 14.224699, 26.328773, 10.346563, 31.112696, 10.346563);
((GeneralPath)shape).curveTo(35.896618, 10.346563, 39.774754, 14.224699, 39.774754, 19.008621);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(193, 125, 17, 255);
stroke = new BasicStroke(1.0f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.774754, 19.008621);
((GeneralPath)shape).curveTo(39.774754, 23.792543, 35.896618, 27.67068, 31.112696, 27.67068);
((GeneralPath)shape).curveTo(26.328773, 27.67068, 22.450638, 23.792543, 22.450638, 19.008621);
((GeneralPath)shape).curveTo(22.450638, 14.224699, 26.328773, 10.346563, 31.112696, 10.346563);
((GeneralPath)shape).curveTo(35.896618, 10.346563, 39.774754, 14.224699, 39.774754, 19.008621);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_4_0_6;
g.setTransform(defaultTransform__0_4_0_6);
g.setClip(clip__0_4_0_6);
float alpha__0_4_0_7 = origAlpha;
origAlpha = origAlpha * 0.19620255f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4_0_7 = g.getClip();
AffineTransform defaultTransform__0_4_0_7 = g.getTransform();
g.transform(new AffineTransform(-0.8770949840545654f, 0.0f, 0.0f, 0.8770949840545654f, 58.10555648803711f, 2.3362669944763184f));
// _0_4_0_7 is ShapeNode
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.1401283f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(39.774754, 19.008621);
((GeneralPath)shape).curveTo(39.774754, 23.792543, 35.896618, 27.67068, 31.112696, 27.67068);
((GeneralPath)shape).curveTo(26.328773, 27.67068, 22.450638, 23.792543, 22.450638, 19.008621);
((GeneralPath)shape).curveTo(22.450638, 14.224699, 26.328773, 10.346563, 31.112696, 10.346563);
((GeneralPath)shape).curveTo(35.896618, 10.346563, 39.774754, 14.224699, 39.774754, 19.008621);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_4_0_7;
g.setTransform(defaultTransform__0_4_0_7);
g.setClip(clip__0_4_0_7);
float alpha__0_4_0_8 = origAlpha;
origAlpha = origAlpha * 0.22784807f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4_0_8 = g.getClip();
AffineTransform defaultTransform__0_4_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_4_0_8 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(20.66169548034668, 35.81797409057617), new Point2D.Double(22.626924514770508, 36.21775817871094), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.9833750128746033f, 0.18158799409866333f, 0.18158799409866333f, 0.9833750128746033f, 55.6977653503418f, -2.651465892791748f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(40.07769, 40.775196);
((GeneralPath)shape).curveTo(41.3253, 40.23023, 41.883682, 38.91692, 41.883682, 38.91692);
((GeneralPath)shape).curveTo(41.0424, 34.847782, 38.163757, 31.870705, 38.163757, 31.870705);
((GeneralPath)shape).curveTo(38.163757, 31.870705, 40.443077, 38.28222, 40.07769, 40.7752);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_4_0_8;
g.setTransform(defaultTransform__0_4_0_8);
g.setClip(clip__0_4_0_8);
float alpha__0_4_0_9 = origAlpha;
origAlpha = origAlpha * 0.22784807f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4_0_9 = g.getClip();
AffineTransform defaultTransform__0_4_0_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_4_0_9 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(22.686765670776367, 36.39039993286133), new Point2D.Double(21.40845489501953, 35.73963165283203), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9776849746704102f, 0.2100750058889389f, -0.2100750058889389f, 0.9776849746704102f, 6.382382392883301f, -4.00770902633667f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(20.734486, 39.854347);
((GeneralPath)shape).curveTo(19.39386, 38.99212, 18.94573, 37.7114, 18.94573, 37.7114);
((GeneralPath)shape).curveTo(19.90485, 33.668415, 22.906254, 31.009203, 22.906254, 31.009203);
((GeneralPath)shape).curveTo(22.906254, 31.009203, 20.441662, 37.351807, 20.734486, 39.854347);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_4_0_9;
g.setTransform(defaultTransform__0_4_0_9);
g.setClip(clip__0_4_0_9);
origAlpha = alpha__0_4_0;
g.setTransform(defaultTransform__0_4_0);
g.setClip(clip__0_4_0);
origAlpha = alpha__0_4;
g.setTransform(defaultTransform__0_4);
g.setClip(clip__0_4);
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
        return 3;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static int getOrigWidth() {
		return 40;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static int getOrigHeight() {
		return 40;
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
	public UserPluginIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public UserPluginIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public UserPluginIcon(int width, int height) {
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

