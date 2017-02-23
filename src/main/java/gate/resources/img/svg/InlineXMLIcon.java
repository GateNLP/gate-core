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
public class InlineXMLIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 150.0f, 60.0f));
// _0_0 is CompositeGraphicsNode
float alpha__0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -60.0f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 0.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(-150.0, 0.0, 48.0, 48.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_0;
g.setTransform(defaultTransform__0_0_0_0);
g.setClip(clip__0_0_0_0);
float alpha__0_0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1 is CompositeGraphicsNode
float alpha__0_0_0_1_0 = origAlpha;
origAlpha = origAlpha * 0.65587044f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0464280843734741f, 0.0f, 0.0f, 0.8888888955116272f, -151.1857147216797f, 5.7222394943237305f));
// _0_0_0_1_0 is CompositeGraphicsNode
float alpha__0_0_0_1_0_0 = origAlpha;
origAlpha = origAlpha * 0.4f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0526319742202759f, 0.0f, 0.0f, 1.2857129573822021f, -1.2631579637527466f, -13.428540229797363f));
// _0_0_0_1_0_0 is CompositeGraphicsNode
float alpha__0_0_0_1_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_0_0_0 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(4.992978572845459, 43.5), 2.5f, new Point2D.Double(4.992978572845459, 43.5), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.003783941268921f, 0.0f, 0.0f, 1.399999976158142f, 27.988130569458008f, -17.399999618530273f));
shape = new Rectangle2D.Double(38.0, 40.0, 5.0, 7.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_1_0_0_0;
g.setTransform(defaultTransform__0_0_0_1_0_0_0);
g.setClip(clip__0_0_0_1_0_0_0);
float alpha__0_0_0_1_0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_0_0_1 = g.getTransform();
g.transform(new AffineTransform(-1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f));
// _0_0_0_1_0_0_1 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(4.992978572845459, 43.5), 2.5f, new Point2D.Double(4.992978572845459, 43.5), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.003783941268921f, 0.0f, 0.0f, 1.399999976158142f, -20.011869430541992f, -104.4000015258789f));
shape = new Rectangle2D.Double(-10.0, -47.0, 5.0, 7.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_1_0_0_1;
g.setTransform(defaultTransform__0_0_0_1_0_0_1);
g.setClip(clip__0_0_0_1_0_0_1);
float alpha__0_0_0_1_0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_0_0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(25.058095932006836, 47.02772903442383), new Point2D.Double(25.058095932006836, 39.99944305419922), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(0, 0, 0, 0),new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new Rectangle2D.Double(10.0, 40.0, 28.0, 7.000000476837158);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_1_0_0_2;
g.setTransform(defaultTransform__0_0_0_1_0_0_2);
g.setClip(clip__0_0_0_1_0_0_2);
origAlpha = alpha__0_0_0_1_0_0;
g.setTransform(defaultTransform__0_0_0_1_0_0);
g.setClip(clip__0_0_0_1_0_0);
origAlpha = alpha__0_0_0_1_0;
g.setTransform(defaultTransform__0_0_0_1_0);
g.setClip(clip__0_0_0_1_0);
float alpha__0_0_0_1_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_1 = g.getTransform();
g.transform(new AffineTransform(0.9548466205596924f, 0.0f, 0.0f, 0.5555561780929565f, -148.98776245117188f, 19.888874053955078f));
// _0_0_0_1_1 is CompositeGraphicsNode
float alpha__0_0_0_1_1_0 = origAlpha;
origAlpha = origAlpha * 0.4f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_1_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0526319742202759f, 0.0f, 0.0f, 1.2857129573822021f, -1.2631579637527466f, -13.428540229797363f));
// _0_0_0_1_1_0 is CompositeGraphicsNode
float alpha__0_0_0_1_1_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_1_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_1_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_1_0_0 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(4.992978572845459, 43.5), 2.5f, new Point2D.Double(4.992978572845459, 43.5), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.003783941268921f, 0.0f, 0.0f, 1.399999976158142f, 27.988130569458008f, -17.399999618530273f));
shape = new Rectangle2D.Double(38.0, 40.0, 5.0, 7.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_1_1_0_0;
g.setTransform(defaultTransform__0_0_0_1_1_0_0);
g.setClip(clip__0_0_0_1_1_0_0);
float alpha__0_0_0_1_1_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_1_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_1_0_1 = g.getTransform();
g.transform(new AffineTransform(-1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f));
// _0_0_0_1_1_0_1 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(4.992978572845459, 43.5), 2.5f, new Point2D.Double(4.992978572845459, 43.5), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.003783941268921f, 0.0f, 0.0f, 1.399999976158142f, -20.011869430541992f, -104.4000015258789f));
shape = new Rectangle2D.Double(-10.0, -47.0, 5.0, 7.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_1_1_0_1;
g.setTransform(defaultTransform__0_0_0_1_1_0_1);
g.setClip(clip__0_0_0_1_1_0_1);
float alpha__0_0_0_1_1_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_1_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_1_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1_1_0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(25.058095932006836, 47.02772903442383), new Point2D.Double(25.058095932006836, 39.99944305419922), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(0, 0, 0, 0),new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new Rectangle2D.Double(10.0, 40.0, 28.0, 7.000000476837158);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_1_1_0_2;
g.setTransform(defaultTransform__0_0_0_1_1_0_2);
g.setClip(clip__0_0_0_1_1_0_2);
origAlpha = alpha__0_0_0_1_1_0;
g.setTransform(defaultTransform__0_0_0_1_1_0);
g.setClip(clip__0_0_0_1_1_0);
origAlpha = alpha__0_0_0_1_1;
g.setTransform(defaultTransform__0_0_0_1_1);
g.setClip(clip__0_0_0_1_1);
float alpha__0_0_0_1_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 60.0f));
// _0_0_0_1_2 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(-30.249996185302734, 35.357208251953125), 18.000002f, new Point2D.Double(-30.249996185302734, 35.357208251953125), new float[] {0.0f,1.0f}, new Color[] {new Color(246, 246, 245, 255),new Color(211, 215, 207, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(3.9957492351531982f, 0.0f, 0.0f, 1.9350366592407227f, 0.6214100122451782f, -91.16741943359375f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-141.46875, -56.5);
((GeneralPath)shape).curveTo(-142.58997, -56.5, -143.5, -55.589966, -143.5, -54.46875);
((GeneralPath)shape).lineTo(-143.5, -17.53125);
((GeneralPath)shape).curveTo(-143.5, -16.410032, -142.58997, -15.5, -141.46875, -15.5);
((GeneralPath)shape).lineTo(-110.53125, -15.5);
((GeneralPath)shape).curveTo(-109.41004, -15.5, -108.5, -16.410034, -108.5, -17.53125);
((GeneralPath)shape).curveTo(-108.5, -27.447918, -108.5, -37.364582, -108.5, -47.28125);
((GeneralPath)shape).curveTo(-108.75793, -49.746834, -110.13765, -51.573875, -111.78125, -53.21875);
((GeneralPath)shape).curveTo(-113.42485, -54.863625, -115.16982, -56.452007, -117.71875, -56.5);
((GeneralPath)shape).curveTo(-125.63542, -56.5, -133.55208, -56.5, -141.46875, -56.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(-47.5, 49.02068328857422), new Point2D.Double(-62.75, -22.5020751953125), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(186, 189, 182, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -90.0f, -60.0f));
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-141.46875, -56.5);
((GeneralPath)shape).curveTo(-142.58997, -56.5, -143.5, -55.589966, -143.5, -54.46875);
((GeneralPath)shape).lineTo(-143.5, -17.53125);
((GeneralPath)shape).curveTo(-143.5, -16.410032, -142.58997, -15.5, -141.46875, -15.5);
((GeneralPath)shape).lineTo(-110.53125, -15.5);
((GeneralPath)shape).curveTo(-109.41004, -15.5, -108.5, -16.410034, -108.5, -17.53125);
((GeneralPath)shape).curveTo(-108.5, -27.447918, -108.5, -37.364582, -108.5, -47.28125);
((GeneralPath)shape).curveTo(-108.75793, -49.746834, -110.13765, -51.573875, -111.78125, -53.21875);
((GeneralPath)shape).curveTo(-113.42485, -54.863625, -115.16982, -56.452007, -117.71875, -56.5);
((GeneralPath)shape).curveTo(-125.63542, -56.5, -133.55208, -56.5, -141.46875, -56.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_1_2;
g.setTransform(defaultTransform__0_0_0_1_2);
g.setClip(clip__0_0_0_1_2);
float alpha__0_0_0_1_3 = origAlpha;
origAlpha = origAlpha * 0.68016195f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_3 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -150.0f, 0.0f));
// _0_0_0_1_3 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(4.0, 5.299999713897705), 17.0f, new Point2D.Double(4.0, 5.299999713897705), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.8860257863998413f, 0.0f, 0.0f, 1.1764706373214722f, -3.5441033840179443f, -4.235293865203857f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.53125, 4.0);
((GeneralPath)shape).curveTo(7.6730804, 4.0, 7.0, 4.67308, 7.0, 5.53125);
((GeneralPath)shape).lineTo(7.0, 42.46875);
((GeneralPath)shape).curveTo(7.0, 43.32692, 7.6730804, 44.0, 8.53125, 44.0);
((GeneralPath)shape).lineTo(39.46875, 44.0);
((GeneralPath)shape).curveTo(40.32692, 44.0, 41.0, 43.32692, 41.0, 42.46875);
((GeneralPath)shape).lineTo(41.0, 17.5);
((GeneralPath)shape).curveTo(41.0, 16.10803, 40.51302, 13.200521, 38.65625, 11.34375);
((GeneralPath)shape).lineTo(33.65625, 6.34375);
((GeneralPath)shape).curveTo(31.799479, 4.486979, 28.89197, 4.0, 27.5, 4.0);
((GeneralPath)shape).lineTo(8.53125, 4.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_1_3;
g.setTransform(defaultTransform__0_0_0_1_3);
g.setClip(clip__0_0_0_1_3);
float alpha__0_0_0_1_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1_4 = g.getClip();
AffineTransform defaultTransform__0_0_0_1_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 60.0f));
// _0_0_0_1_4 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-24.75696563720703, 9.569466590881348), new Point2D.Double(-22.611663818359375, 7.424164295196533), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -90.0f, -60.0f));
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-141.46875, -55.5);
((GeneralPath)shape).curveTo(-142.05193, -55.5, -142.5, -55.051914, -142.5, -54.46875);
((GeneralPath)shape).lineTo(-142.5, -17.53125);
((GeneralPath)shape).curveTo(-142.5, -16.948086, -142.05191, -16.5, -141.46875, -16.5);
((GeneralPath)shape).lineTo(-110.53125, -16.5);
((GeneralPath)shape).curveTo(-109.94808, -16.5, -109.5, -16.948086, -109.5, -17.53125);
((GeneralPath)shape).curveTo(-109.5, -17.53125, -109.5, -41.0, -109.5, -42.5);
((GeneralPath)shape).curveTo(-109.5, -42.606586, -109.5242, -42.779716, -109.5312, -42.90625);
((GeneralPath)shape).curveTo(-109.5312, -44.65825, -109.5312, -47.01807, -109.5312, -47.28125);
((GeneralPath)shape).curveTo(-109.5312, -48.038628, -109.86417, -49.800938, -110.87495, -50.8125);
((GeneralPath)shape).curveTo(-112.18983, -52.1284, -112.84132, -52.8091, -114.1562, -54.125);
((GeneralPath)shape).curveTo(-115.166985, -55.136562, -116.9619, -55.46875, -117.7187, -55.46875);
((GeneralPath)shape).curveTo(-118.06472, -55.46875, -120.11227, -55.46875, -122.0937, -55.46875);
((GeneralPath)shape).curveTo(-122.22023, -55.47585, -122.39336, -55.5, -122.49995, -55.5);
((GeneralPath)shape).curveTo(-123.99995, -55.5, -141.46869, -55.5, -141.46869, -55.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_1_4;
g.setTransform(defaultTransform__0_0_0_1_4);
g.setClip(clip__0_0_0_1_4);
origAlpha = alpha__0_0_0_1;
g.setTransform(defaultTransform__0_0_0_1);
g.setClip(clip__0_0_0_1);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -36.00694274902344f, -8.993440628051758f));
// _0_0_1 is CompositeGraphicsNode
float alpha__0_0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_0 = g.getClip();
AffineTransform defaultTransform__0_0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_0 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(-26.305402755737305, 10.108011245727539), 7.042104f, new Point2D.Double(-26.305402755737305, 10.108011245727539), new float[] {0.0f,0.47533694f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.2626416087150574f, -0.18058030307292938f, 0.4842473864555359f, 0.7055032849311829f, -76.98798370361328f, -55.02649688720703f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-81.70464, -47.0);
((GeneralPath)shape).curveTo(-82.60017, -47.0, -81.731766, -46.679214, -80.83822, -46.274006);
((GeneralPath)shape).curveTo(-79.94467, -45.868797, -78.47346, -44.361862, -79.0, -42.0);
((GeneralPath)shape).curveTo(-74.43068, -42.707108, -73.8518, -39.82243, -73.64491, -39.074577);
((GeneralPath)shape).curveTo(-73.43803, -38.326725, -73.00014, -37.391796, -73.00014, -38.288086);
((GeneralPath)shape).curveTo(-72.981865, -40.74393, -74.83481, -42.438637, -76.12328, -43.8742);
((GeneralPath)shape).curveTo(-77.41176, -45.30976, -79.34876, -46.692333, -81.70464, -47.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1_0;
g.setTransform(defaultTransform__0_0_1_0);
g.setClip(clip__0_0_1_0);
float alpha__0_0_1_1 = origAlpha;
origAlpha = origAlpha * 0.8785425f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_1 = g.getClip();
AffineTransform defaultTransform__0_0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-18.618724822998047, 10.211341857910156), new Point2D.Double(-30.55854606628418, 12.189650535583496), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 105),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.5946828722953796f, 0.0f, 0.0f, 0.5927814245223999f, -62.327354431152344f, -48.725799560546875f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-80.99482, -46.34541);
((GeneralPath)shape).curveTo(-80.40014, -46.34541, -80.0, -42.5, -80.0, -40.0);
((GeneralPath)shape).curveTo(-77.0, -40.0, -74.29634, -39.548187, -74.0, -39.0);
((GeneralPath)shape).curveTo(-74.20689, -39.747852, -74.53857, -42.7734, -79.0, -42.0);
((GeneralPath)shape).curveTo(-78.44248, -44.500797, -80.21299, -46.107704, -80.99482, -46.34541);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1_1;
g.setTransform(defaultTransform__0_0_1_1);
g.setClip(clip__0_0_1_1);
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
	public InlineXMLIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public InlineXMLIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public InlineXMLIcon(int width, int height) {
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

