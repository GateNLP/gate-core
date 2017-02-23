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
public class DownloadIcon implements
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
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,48.0,48.0)));
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
origAlpha = origAlpha * 0.7f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.030830979347229f, 0.0f, 0.0f, 1.1511470079421997f, -0.7360900044441223f, -7.574309825897217f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(-1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f));
// _0_0_0_0 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(0.0, 17.0), 2.0f, new Point2D.Double(0.0, 17.0), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(4.095821857452393f, 0.0f, 0.0f, 3.1012818813323975f, -9.539210319519043f, -94.54329681396484f));
shape = new Rectangle2D.Double(-9.539210319519043, -48.024085998535156, 8.191643714904785, 12.405125617980957);
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
// _0_0_0_1 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(0.0, 17.0), 2.0f, new Point2D.Double(0.0, 17.0), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(4.095821857452393f, 0.0f, 0.0f, 3.1012818813323975f, 38.2099609375f, -10.900250434875488f));
shape = new Rectangle2D.Double(38.209964752197266, 35.618961334228516, 8.191643714904785, 12.405125617980957);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_1;
g.setTransform(defaultTransform__0_0_0_1);
g.setClip(clip__0_0_0_1);
float alpha__0_0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(9.899495124816895, 20.0), new Point2D.Double(9.899495124816895, 13.97915267944336), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(0, 0, 0, 0),new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0479109287261963f, 0.0f, 0.0f, 2.067521095275879f, 1.34756600856781f, 6.673675060272217f));
shape = new Rectangle2D.Double(9.539210319519043, 35.618961334228516, 28.670753479003906, 12.405125617980957);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_2;
g.setTransform(defaultTransform__0_0_0_2);
g.setClip(clip__0_0_0_2);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -43.99747085571289f));
// _0_0_1 is CompositeGraphicsNode
float alpha__0_0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_0 = g.getClip();
AffineTransform defaultTransform__0_0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(53.81281280517578, 43.57323455810547), new Point2D.Double(-2.8138930797576904, 35.50001525878906), new float[] {0.0f,1.0f}, new Color[] {new Color(85, 87, 83, 255),new Color(46, 52, 54, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 50.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(4.5182285, 80.500015);
((GeneralPath)shape).lineTo(43.48177, 80.500015);
((GeneralPath)shape).curveTo(44.045868, 80.500015, 44.499996, 80.95415, 44.499996, 81.51824);
((GeneralPath)shape).lineTo(44.499996, 84.48179);
((GeneralPath)shape).curveTo(44.499996, 85.79737, 44.049767, 87.500015, 42.04427, 87.500015);
((GeneralPath)shape).lineTo(40.5, 87.5);
((GeneralPath)shape).lineTo(40.5, 88.5);
((GeneralPath)shape).lineTo(7.5, 88.5);
((GeneralPath)shape).lineTo(7.5, 87.5);
((GeneralPath)shape).lineTo(5.6432285, 87.500015);
((GeneralPath)shape).curveTo(4.3720236, 87.500015, 3.5000005, 86.61539, 3.5000005, 85.24435);
((GeneralPath)shape).lineTo(3.5000005, 81.51824);
((GeneralPath)shape).curveTo(3.5000005, 80.95415, 3.9541302, 80.500015, 4.5182285, 80.500015);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,2,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(4.5182285, 80.500015);
((GeneralPath)shape).lineTo(43.48177, 80.500015);
((GeneralPath)shape).curveTo(44.045868, 80.500015, 44.499996, 80.95415, 44.499996, 81.51824);
((GeneralPath)shape).lineTo(44.499996, 84.48179);
((GeneralPath)shape).curveTo(44.499996, 85.79737, 44.049767, 87.500015, 42.04427, 87.500015);
((GeneralPath)shape).lineTo(40.5, 87.5);
((GeneralPath)shape).lineTo(40.5, 88.5);
((GeneralPath)shape).lineTo(7.5, 88.5);
((GeneralPath)shape).lineTo(7.5, 87.5);
((GeneralPath)shape).lineTo(5.6432285, 87.500015);
((GeneralPath)shape).curveTo(4.3720236, 87.500015, 3.5000005, 86.61539, 3.5000005, 85.24435);
((GeneralPath)shape).lineTo(3.5000005, 81.51824);
((GeneralPath)shape).curveTo(3.5000005, 80.95415, 3.9541302, 80.500015, 4.5182285, 80.500015);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1_0;
g.setTransform(defaultTransform__0_0_1_0);
g.setClip(clip__0_0_1_0);
float alpha__0_0_1_1 = origAlpha;
origAlpha = origAlpha * 0.1f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_1 = g.getClip();
AffineTransform defaultTransform__0_0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 50.0f));
// _0_0_1_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(12.5, 43.1875), new Point2D.Double(12.5, 34.04551315307617), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.9999999f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(4.59375, 31.59375);
((GeneralPath)shape).lineTo(4.59375, 35.323494);
((GeneralPath)shape).curveTo(4.59375, 35.92311, 4.9694004, 36.42835, 5.4800777, 36.42835);
((GeneralPath)shape).lineTo(42.426407, 36.42835);
((GeneralPath)shape).curveTo(42.938877, 36.42835, 43.40625, 35.921112, 43.40625, 35.41188);
((GeneralPath)shape).lineTo(43.40625, 31.59375);
((GeneralPath)shape).lineTo(4.59375, 31.59375);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1_1;
g.setTransform(defaultTransform__0_0_1_1);
g.setClip(clip__0_0_1_1);
float alpha__0_0_1_2 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_2 = g.getClip();
AffineTransform defaultTransform__0_0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 50.0f));
// _0_0_1_2 is CompositeGraphicsNode
float alpha__0_0_1_2_0 = origAlpha;
origAlpha = origAlpha * 0.10952382f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_2_0 = g.getClip();
AffineTransform defaultTransform__0_0_1_2_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_2_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(15.335378646850586, 33.06237030029297), new Point2D.Double(20.329320907592773, 36.376930236816406), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(255, 255, 255, 0),new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.4229400157928467f, 10.5f, -14.957030296325684f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.144737, 32.088745);
((GeneralPath)shape).curveTo(26.144737, 32.088745, 24.642136, 37.622684, 22.918564, 38.0);
((GeneralPath)shape).curveTo(22.918564, 38.0, 29.14994, 37.87423, 29.14994, 37.87423);
((GeneralPath)shape).curveTo(30.537012, 37.556767, 32.508698, 32.088745, 32.508698, 32.088745);
((GeneralPath)shape).lineTo(26.144737, 32.088745);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1_2_0;
g.setTransform(defaultTransform__0_0_1_2_0);
g.setClip(clip__0_0_1_2_0);
float alpha__0_0_1_2_1 = origAlpha;
origAlpha = origAlpha * 0.10952382f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_2_1 = g.getClip();
AffineTransform defaultTransform__0_0_1_2_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_2_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(15.335378646850586, 33.06237030029297), new Point2D.Double(20.329320907592773, 36.376930236816406), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(255, 255, 255, 0),new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.4229400157928467f, -0.875f, -15.045780181884766f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(14.769738, 32.0);
((GeneralPath)shape).curveTo(14.769738, 32.0, 13.267136, 37.53394, 11.543563, 37.911255);
((GeneralPath)shape).curveTo(11.543563, 37.911255, 17.77494, 37.78548, 17.77494, 37.78548);
((GeneralPath)shape).curveTo(19.162012, 37.46802, 21.1337, 32.0, 21.1337, 32.0);
((GeneralPath)shape).lineTo(14.769738, 32.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1_2_1;
g.setTransform(defaultTransform__0_0_1_2_1);
g.setClip(clip__0_0_1_2_1);
float alpha__0_0_1_2_2 = origAlpha;
origAlpha = origAlpha * 0.10952382f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_2_2 = g.getClip();
AffineTransform defaultTransform__0_0_1_2_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_2_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(15.335378646850586, 33.06237030029297), new Point2D.Double(20.329320907592773, 36.376930236816406), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(255, 255, 255, 0),new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.4598329961299896f, 0.0f, -0.39116498827934265f, 1.3701050281524658f, 40.625030517578125f, -13.298919677734375f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.88614, 32.0);
((GeneralPath)shape).curveTo(34.88614, 32.0, 32.673916, 37.328457, 31.777636, 37.69176);
((GeneralPath)shape).curveTo(31.777636, 37.69176, 34.677605, 37.57066, 34.677605, 37.57066);
((GeneralPath)shape).curveTo(35.4027, 37.264988, 37.8125, 32.0, 37.8125, 32.0);
((GeneralPath)shape).lineTo(34.88614, 32.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1_2_2;
g.setTransform(defaultTransform__0_0_1_2_2);
g.setClip(clip__0_0_1_2_2);
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
// _0_0_2 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(25.251998901367188, 16.479909896850586), 21.980215f, new Point2D.Double(25.251998901367188, 16.479909896850586), new float[] {0.0f,1.0f}, new Color[] {new Color(208, 208, 203, 255),new Color(186, 189, 182, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0329910516738892f, -0.5963979959487915f, 0.5751209855079651f, 0.9961400032043457f, -12.234560012817383f, 16.554479598999023f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.693127, 15.498788);
((GeneralPath)shape).lineTo(36.265694, 15.498788);
((GeneralPath)shape).curveTo(37.949863, 15.498788, 38.66221, 15.616267, 39.305714, 17.883793);
((GeneralPath)shape).lineTo(44.380203, 35.76491);
((GeneralPath)shape).curveTo(44.881226, 37.530384, 43.024357, 38.50001, 41.340183, 38.50001);
((GeneralPath)shape).lineTo(6.6186314, 38.50001);
((GeneralPath)shape).curveTo(4.750223, 38.50001, 3.129248, 37.318596, 3.578613, 35.76491);
((GeneralPath)shape).lineTo(8.829045, 17.611498);
((GeneralPath)shape).curveTo(9.397346, 15.646592, 10.008957, 15.498788, 11.693127, 15.498788);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(136, 138, 133, 255);
stroke = new BasicStroke(1.0000002f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.693127, 15.498788);
((GeneralPath)shape).lineTo(36.265694, 15.498788);
((GeneralPath)shape).curveTo(37.949863, 15.498788, 38.66221, 15.616267, 39.305714, 17.883793);
((GeneralPath)shape).lineTo(44.380203, 35.76491);
((GeneralPath)shape).curveTo(44.881226, 37.530384, 43.024357, 38.50001, 41.340183, 38.50001);
((GeneralPath)shape).lineTo(6.6186314, 38.50001);
((GeneralPath)shape).curveTo(4.750223, 38.50001, 3.129248, 37.318596, 3.578613, 35.76491);
((GeneralPath)shape).lineTo(8.829045, 17.611498);
((GeneralPath)shape).curveTo(9.397346, 15.646592, 10.008957, 15.498788, 11.693127, 15.498788);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2;
g.setTransform(defaultTransform__0_0_2);
g.setClip(clip__0_0_2);
float alpha__0_0_3 = origAlpha;
origAlpha = origAlpha * 0.46240598f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 5.0f));
// _0_0_3 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.057796478271484, 12.669416427612305), new Point2D.Double(32.04289627075195, 31.21966552734375), new float[] {0.0f,0.5472973f,0.66243607f,0.875f,1.0f}, new Color[] {new Color(255, 255, 255, 66),new Color(255, 255, 255, 255),new Color(255, 255, 255, 63),new Color(255, 255, 255, 213),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.6875, 11.0);
((GeneralPath)shape).curveTo(10.861258, 11.0, 10.40275, 11.065232, 10.125, 11.25);
((GeneralPath)shape).curveTo(9.84725, 11.434768, 9.584799, 11.808525, 9.3125, 12.75);
((GeneralPath)shape).lineTo(4.0625, 30.875);
((GeneralPath)shape).curveTo(3.8916752, 31.465628, 4.0939746, 31.914316, 4.5625, 32.3125);
((GeneralPath)shape).curveTo(5.0310254, 32.710686, 5.7975106, 33.0, 6.625, 33.0);
((GeneralPath)shape).lineTo(41.34375, 33.0);
((GeneralPath)shape).curveTo(42.088406, 33.0, 42.88269, 32.7518, 43.375, 32.375);
((GeneralPath)shape).curveTo(43.86731, 31.9982, 44.106018, 31.578922, 43.90625, 30.875);
((GeneralPath)shape).lineTo(38.84375, 13.0);
((GeneralPath)shape).curveTo(38.5345, 11.910283, 38.220547, 11.458908, 37.90625, 11.25);
((GeneralPath)shape).curveTo(37.591953, 11.041092, 37.112698, 11.0, 36.28125, 11.0);
((GeneralPath)shape).lineTo(11.6875, 11.0);
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
g.transform(new AffineTransform(-2.6286020278930664f, 0.0f, 0.0f, 1.7777650356292725f, 27.7930908203125f, -18.777389526367188f));
// _0_0_4 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(7.519143581390381, 30.304250717163086), 0.53125f, new Point2D.Double(7.519143581390381, 30.304250717163086), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 255),new Color(204, 207, 202, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.389747977256775f, 0.0f, 0.0f, 1.3488719463348389f, -2.9198200702667236f, -10.638150215148926f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.15625, 30.578125);
((GeneralPath)shape).curveTo(8.15625, 30.862896, 7.9184012, 31.09375, 7.625, 31.09375);
((GeneralPath)shape).curveTo(7.3315988, 31.09375, 7.09375, 30.862896, 7.09375, 30.578125);
((GeneralPath)shape).curveTo(7.09375, 30.293354, 7.3315988, 30.0625, 7.625, 30.0625);
((GeneralPath)shape).curveTo(7.9184012, 30.0625, 8.15625, 30.293354, 8.15625, 30.578125);
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
// _0_0_5 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(18.048873901367188, 25.46134376525879), new Point2D.Double(22.211936950683594, 12.143077850341797), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(85, 87, 83, 153),new Color(85, 87, 83, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9402239918708801f, 0.0f, 0.0f, 0.9316319823265076f, 1.3318109512329102f, 6.40153694152832f));
stroke = new BasicStroke(1.0000001f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.110952, 17.552805);
((GeneralPath)shape).curveTo(15.537372, 17.552805, 15.082583, 17.984627, 15.082583, 18.542664);
((GeneralPath)shape).lineTo(14.14236, 21.773464);
((GeneralPath)shape).curveTo(11.282398, 23.049978, 9.500005, 24.872538, 9.500005, 26.897442);
((GeneralPath)shape).curveTo(9.500005, 30.754398, 15.979029, 33.88468, 23.985327, 33.88468);
((GeneralPath)shape).curveTo(31.991623, 33.88468, 38.50003, 30.754396, 38.50003, 26.897442);
((GeneralPath)shape).curveTo(38.50003, 24.858406, 36.66444, 23.022053, 33.76953, 21.744352);
((GeneralPath)shape).lineTo(32.829308, 18.542664);
((GeneralPath)shape).curveTo(32.829308, 17.984627, 32.37452, 17.552805, 31.800936, 17.552805);
((GeneralPath)shape).lineTo(16.110952, 17.552805);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
float alpha__0_0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(24.748737335205078, 35.35458755493164), new Point2D.Double(24.998737335205078, 14.997767448425293), new float[] {0.0f,1.0f}, new Color[] {new Color(253, 253, 252, 255),new Color(255, 255, 255, 95)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.9955559968948364f, 0.0f, 1.0688869953155518f));
stroke = new BasicStroke(1.0000004f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.6875, 16.500006);
((GeneralPath)shape).curveTo(10.884376, 16.500006, 10.590332, 16.570515, 10.46875, 16.655561);
((GeneralPath)shape).curveTo(10.347168, 16.740606, 10.111043, 17.05768, 9.78125, 17.962229);
((GeneralPath)shape).lineTo(4.53125, 36.100014);
((GeneralPath)shape).curveTo(4.3975296, 36.466778, 4.4764233, 36.63388, 4.84375, 36.940014);
((GeneralPath)shape).curveTo(5.2110767, 37.24615, 5.910443, 37.500015, 6.625, 37.500015);
((GeneralPath)shape).lineTo(41.34375, 37.500015);
((GeneralPath)shape).curveTo(41.983543, 37.500015, 42.737095, 37.262062, 43.125, 36.971127);
((GeneralPath)shape).curveTo(43.512905, 36.68019, 43.61331, 36.588318, 43.4375, 36.100014);
((GeneralPath)shape).lineTo(38.375, 18.242228);
((GeneralPath)shape).curveTo(37.997795, 17.194569, 37.69292, 16.80293, 37.53125, 16.686672);
((GeneralPath)shape).curveTo(37.36958, 16.570414, 37.08754, 16.500006, 36.28125, 16.500006);
((GeneralPath)shape).lineTo(11.6875, 16.500006);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_6;
g.setTransform(defaultTransform__0_0_6);
g.setClip(clip__0_0_6);
float alpha__0_0_7 = origAlpha;
origAlpha = origAlpha * 0.3028571f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 7.0f, 4.0f));
// _0_0_7 is CompositeGraphicsNode
float alpha__0_0_7_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_0 = g.getClip();
AffineTransform defaultTransform__0_0_7_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7_0 is ShapeNode
paint = new Color(255, 255, 255, 255);
shape = new RoundRectangle2D.Double(18.499996185302734, 35.50000762939453, 14.000003814697266, 1.9999923706054688, 1.5026018619537354, 1.4942450523376465);
g.setPaint(paint);
g.fill(shape);
paint = new Color(238, 238, 236, 255);
stroke = new BasicStroke(1.0000002f,2,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(18.499996185302734, 35.50000762939453, 14.000003814697266, 1.9999923706054688, 1.5026018619537354, 1.4942450523376465);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_7_0;
g.setTransform(defaultTransform__0_0_7_0);
g.setClip(clip__0_0_7_0);
float alpha__0_0_7_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_1 = g.getClip();
AffineTransform defaultTransform__0_0_7_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7_1 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(19.0, 36.0, 1.0, 1.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_7_1;
g.setTransform(defaultTransform__0_0_7_1);
g.setClip(clip__0_0_7_1);
float alpha__0_0_7_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_2 = g.getClip();
AffineTransform defaultTransform__0_0_7_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7_2 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(22.0, 36.0, 1.0, 1.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_7_2;
g.setTransform(defaultTransform__0_0_7_2);
g.setClip(clip__0_0_7_2);
float alpha__0_0_7_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_3 = g.getClip();
AffineTransform defaultTransform__0_0_7_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7_3 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(24.0, 36.0, 1.0, 1.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_7_3;
g.setTransform(defaultTransform__0_0_7_3);
g.setClip(clip__0_0_7_3);
float alpha__0_0_7_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_4 = g.getClip();
AffineTransform defaultTransform__0_0_7_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7_4 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(26.0, 36.0, 1.0, 1.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_7_4;
g.setTransform(defaultTransform__0_0_7_4);
g.setClip(clip__0_0_7_4);
float alpha__0_0_7_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7_5 = g.getClip();
AffineTransform defaultTransform__0_0_7_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7_5 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new Rectangle2D.Double(29.0, 36.0, 2.0, 1.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_7_5;
g.setTransform(defaultTransform__0_0_7_5);
g.setClip(clip__0_0_7_5);
origAlpha = alpha__0_0_7;
g.setTransform(defaultTransform__0_0_7);
g.setClip(clip__0_0_7);
float alpha__0_0_8 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_8 = g.getClip();
AffineTransform defaultTransform__0_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 5.0f));
// _0_0_8 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(24.0, 36.63838195800781), new Point2D.Double(25.818017959594727, 6.831476211547852), new float[] {0.0f,0.2f,1.0f}, new Color[] {new Color(238, 238, 236, 255),new Color(238, 238, 236, 255),new Color(186, 189, 182, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.125, 13.0);
((GeneralPath)shape).curveTo(15.762388, 13.0, 15.53125, 13.214848, 15.53125, 13.53125);
((GeneralPath)shape).curveTo(15.537172, 13.572706, 15.537172, 13.614794, 15.53125, 13.65625);
((GeneralPath)shape).lineTo(14.59375, 16.90625);
((GeneralPath)shape).curveTo(14.55648, 17.032564, 14.464823, 17.135677, 14.34375, 17.1875);
((GeneralPath)shape).curveTo(12.953848, 17.807869, 11.845241, 18.565737, 11.09375, 19.375);
((GeneralPath)shape).curveTo(10.342259, 20.184263, 9.9375, 21.02448, 9.9375, 21.90625);
((GeneralPath)shape).curveTo(9.9375, 23.586704, 11.392464, 25.21545, 13.9375, 26.4375);
((GeneralPath)shape).curveTo(16.482536, 27.65955, 20.054108, 28.4375, 24.0, 28.4375);
((GeneralPath)shape).curveTo(27.945892, 28.4375, 31.517464, 27.65955, 34.0625, 26.4375);
((GeneralPath)shape).curveTo(36.607536, 25.21545, 38.0625, 23.586704, 38.0625, 21.90625);
((GeneralPath)shape).curveTo(38.0625, 21.0185, 37.668724, 20.159037, 36.90625, 19.34375);
((GeneralPath)shape).curveTo(36.143776, 18.528463, 35.000896, 17.77731, 33.59375, 17.15625);
((GeneralPath)shape).curveTo(33.472675, 17.104427, 33.38102, 17.001314, 33.34375, 16.875);
((GeneralPath)shape).lineTo(32.40625, 13.65625);
((GeneralPath)shape).curveTo(32.40033, 13.614794, 32.40033, 13.572706, 32.40625, 13.53125);
((GeneralPath)shape).curveTo(32.40625, 13.214846, 32.175114, 13.0, 31.8125, 13.0);
((GeneralPath)shape).lineTo(16.125, 13.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_8;
g.setTransform(defaultTransform__0_0_8);
g.setClip(clip__0_0_8);
float alpha__0_0_9 = origAlpha;
origAlpha = origAlpha * 0.9f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_9 = g.getClip();
AffineTransform defaultTransform__0_0_9 = g.getTransform();
g.transform(new AffineTransform(0.4499779939651489f, 0.0f, 0.0f, 0.3499090075492859f, 16.363630294799805f, 17.214689254760742f));
// _0_0_9 is ShapeNode
paint = new Color(0, 0, 0, 14);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(24.748735, 25.107418);
((GeneralPath)shape).curveTo(24.749708, 26.639315, 23.26741, 28.055075, 20.860435, 28.821177);
((GeneralPath)shape).curveTo(18.45346, 29.58728, 15.487663, 29.58728, 13.080688, 28.821177);
((GeneralPath)shape).curveTo(10.673713, 28.055075, 9.191414, 26.639315, 9.192388, 25.107418);
((GeneralPath)shape).curveTo(9.191414, 23.575521, 10.673713, 22.159761, 13.080688, 21.39366);
((GeneralPath)shape).curveTo(15.487663, 20.627556, 18.45346, 20.627556, 20.860435, 21.39366);
((GeneralPath)shape).curveTo(23.26741, 22.159761, 24.749708, 23.575521, 24.748735, 25.107418);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(20.30403709411621, 24.035707473754883), new Point2D.Double(18.498414993286133, 40.64716720581055), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(136, 138, 133, 153)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(2.5201523f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(24.748735, 25.107418);
((GeneralPath)shape).curveTo(24.749708, 26.639315, 23.26741, 28.055075, 20.860435, 28.821177);
((GeneralPath)shape).curveTo(18.45346, 29.58728, 15.487663, 29.58728, 13.080688, 28.821177);
((GeneralPath)shape).curveTo(10.673713, 28.055075, 9.191414, 26.639315, 9.192388, 25.107418);
((GeneralPath)shape).curveTo(9.191414, 23.575521, 10.673713, 22.159761, 13.080688, 21.39366);
((GeneralPath)shape).curveTo(15.487663, 20.627556, 18.45346, 20.627556, 20.860435, 21.39366);
((GeneralPath)shape).curveTo(23.26741, 22.159761, 24.749708, 23.575521, 24.748735, 25.107418);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_9;
g.setTransform(defaultTransform__0_0_9);
g.setClip(clip__0_0_9);
float alpha__0_0_10 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_10 = g.getClip();
AffineTransform defaultTransform__0_0_10 = g.getTransform();
g.transform(new AffineTransform(1.4117720127105713f, 0.0f, 0.0f, 0.9696969985961914f, -3.0147669315338135f, 5.848484992980957f));
// _0_0_10 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(7.517772674560547, 30.57355499267578), 0.53125f, new Point2D.Double(7.517772674560547, 30.57355499267578), new float[] {0.0f,1.0f}, new Color[] {new Color(186, 189, 182, 255),new Color(46, 52, 54, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6624770164489746f, 0.0f, 0.0f, 1.6135799884796143f, -4.989174842834473f, -18.656469345092773f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.15625, 30.578125);
((GeneralPath)shape).curveTo(8.15625, 30.862896, 7.9184012, 31.09375, 7.625, 31.09375);
((GeneralPath)shape).curveTo(7.3315988, 31.09375, 7.09375, 30.862896, 7.09375, 30.578125);
((GeneralPath)shape).curveTo(7.09375, 30.293354, 7.3315988, 30.0625, 7.625, 30.0625);
((GeneralPath)shape).curveTo(7.9184012, 30.0625, 8.15625, 30.293354, 8.15625, 30.578125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_10;
g.setTransform(defaultTransform__0_0_10);
g.setClip(clip__0_0_10);
float alpha__0_0_11 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_11 = g.getClip();
AffineTransform defaultTransform__0_0_11 = g.getTransform();
g.transform(new AffineTransform(-2.6286020278930664f, 0.0f, 0.0f, 1.7777650356292725f, 60.7930908203125f, -18.777389526367188f));
// _0_0_11 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(7.533600807189941, 30.30756187438965), 0.53125f, new Point2D.Double(7.533600807189941, 30.30756187438965), new float[] {0.0f,1.0f}, new Color[] {new Color(238, 238, 236, 255),new Color(136, 138, 133, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.5694869756698608f, 0.0f, 0.0f, 1.523324966430664f, -4.2886271476745605f, -15.921070098876953f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.15625, 30.578125);
((GeneralPath)shape).curveTo(8.15625, 30.862896, 7.9184012, 31.09375, 7.625, 31.09375);
((GeneralPath)shape).curveTo(7.3315988, 31.09375, 7.09375, 30.862896, 7.09375, 30.578125);
((GeneralPath)shape).curveTo(7.09375, 30.293354, 7.3315988, 30.0625, 7.625, 30.0625);
((GeneralPath)shape).curveTo(7.9184012, 30.0625, 8.15625, 30.293354, 8.15625, 30.578125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_11;
g.setTransform(defaultTransform__0_0_11);
g.setClip(clip__0_0_11);
float alpha__0_0_12 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_12 = g.getClip();
AffineTransform defaultTransform__0_0_12 = g.getTransform();
g.transform(new AffineTransform(1.4117720127105713f, 0.0f, 0.0f, 0.9696969985961914f, 29.9852294921875f, 5.848484992980957f));
// _0_0_12 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(7.517772674560547, 30.57355499267578), 0.53125f, new Point2D.Double(7.517772674560547, 30.57355499267578), new float[] {0.0f,1.0f}, new Color[] {new Color(186, 189, 182, 255),new Color(46, 52, 54, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6624770164489746f, 0.0f, 0.0f, 1.6135799884796143f, -4.989174842834473f, -18.656469345092773f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.15625, 30.578125);
((GeneralPath)shape).curveTo(8.15625, 30.862896, 7.9184012, 31.09375, 7.625, 31.09375);
((GeneralPath)shape).curveTo(7.3315988, 31.09375, 7.09375, 30.862896, 7.09375, 30.578125);
((GeneralPath)shape).curveTo(7.09375, 30.293354, 7.3315988, 30.0625, 7.625, 30.0625);
((GeneralPath)shape).curveTo(7.9184012, 30.0625, 8.15625, 30.293354, 8.15625, 30.578125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_12;
g.setTransform(defaultTransform__0_0_12);
g.setClip(clip__0_0_12);
float alpha__0_0_13 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_13 = g.getClip();
AffineTransform defaultTransform__0_0_13 = g.getTransform();
g.transform(new AffineTransform(-2.6286020278930664f, 0.0f, 0.0f, 1.7777650356292725f, 31.7930908203125f, -35.77738952636719f));
// _0_0_13 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(7.479206085205078, 30.36071014404297), 0.53125f, new Point2D.Double(7.479206085205078, 30.36071014404297), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(186, 189, 182, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6624770164489746f, 0.0f, 0.0f, 1.6135799884796143f, -4.989174842834473f, -18.656469345092773f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.15625, 30.578125);
((GeneralPath)shape).curveTo(8.15625, 30.862896, 7.9184012, 31.09375, 7.625, 31.09375);
((GeneralPath)shape).curveTo(7.3315988, 31.09375, 7.09375, 30.862896, 7.09375, 30.578125);
((GeneralPath)shape).curveTo(7.09375, 30.293354, 7.3315988, 30.0625, 7.625, 30.0625);
((GeneralPath)shape).curveTo(7.9184012, 30.0625, 8.15625, 30.293354, 8.15625, 30.578125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_13;
g.setTransform(defaultTransform__0_0_13);
g.setClip(clip__0_0_13);
float alpha__0_0_14 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_14 = g.getClip();
AffineTransform defaultTransform__0_0_14 = g.getTransform();
g.transform(new AffineTransform(1.4117720127105713f, 0.0f, 0.0f, 0.9696969985961914f, 0.9852330088615417f, -11.151519775390625f));
// _0_0_14 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(7.517772674560547, 30.57355499267578), 0.53125f, new Point2D.Double(7.517772674560547, 30.57355499267578), new float[] {0.0f,1.0f}, new Color[] {new Color(186, 189, 182, 255),new Color(46, 52, 54, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6624770164489746f, 0.0f, 0.0f, 1.6135799884796143f, -4.989174842834473f, -18.656469345092773f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.15625, 30.578125);
((GeneralPath)shape).curveTo(8.15625, 30.862896, 7.9184012, 31.09375, 7.625, 31.09375);
((GeneralPath)shape).curveTo(7.3315988, 31.09375, 7.09375, 30.862896, 7.09375, 30.578125);
((GeneralPath)shape).curveTo(7.09375, 30.293354, 7.3315988, 30.0625, 7.625, 30.0625);
((GeneralPath)shape).curveTo(7.9184012, 30.0625, 8.15625, 30.293354, 8.15625, 30.578125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_14;
g.setTransform(defaultTransform__0_0_14);
g.setClip(clip__0_0_14);
float alpha__0_0_15 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_15 = g.getClip();
AffineTransform defaultTransform__0_0_15 = g.getTransform();
g.transform(new AffineTransform(-2.6286020278930664f, 0.0f, 0.0f, 1.7777650356292725f, 56.30289840698242f, -35.77738952636719f));
// _0_0_15 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(7.48931884765625, 30.337600708007812), 0.53125f, new Point2D.Double(7.48931884765625, 30.337600708007812), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(136, 138, 133, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6624770164489746f, 0.0f, 0.0f, 1.6135799884796143f, -4.989174842834473f, -18.656469345092773f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.15625, 30.578125);
((GeneralPath)shape).curveTo(8.15625, 30.862896, 7.9184012, 31.09375, 7.625, 31.09375);
((GeneralPath)shape).curveTo(7.3315988, 31.09375, 7.09375, 30.862896, 7.09375, 30.578125);
((GeneralPath)shape).curveTo(7.09375, 30.293354, 7.3315988, 30.0625, 7.625, 30.0625);
((GeneralPath)shape).curveTo(7.9184012, 30.0625, 8.15625, 30.293354, 8.15625, 30.578125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_15;
g.setTransform(defaultTransform__0_0_15);
g.setClip(clip__0_0_15);
float alpha__0_0_16 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_16 = g.getClip();
AffineTransform defaultTransform__0_0_16 = g.getTransform();
g.transform(new AffineTransform(1.4117720127105713f, 0.0f, 0.0f, 0.9696969985961914f, 25.495040893554688f, -11.151519775390625f));
// _0_0_16 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(7.517772674560547, 30.57355499267578), 0.53125f, new Point2D.Double(7.517772674560547, 30.57355499267578), new float[] {0.0f,1.0f}, new Color[] {new Color(186, 189, 182, 255),new Color(46, 52, 54, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.6624770164489746f, 0.0f, 0.0f, 1.6135799884796143f, -4.989174842834473f, -18.656469345092773f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(8.15625, 30.578125);
((GeneralPath)shape).curveTo(8.15625, 30.862896, 7.9184012, 31.09375, 7.625, 31.09375);
((GeneralPath)shape).curveTo(7.3315988, 31.09375, 7.09375, 30.862896, 7.09375, 30.578125);
((GeneralPath)shape).curveTo(7.09375, 30.293354, 7.3315988, 30.0625, 7.625, 30.0625);
((GeneralPath)shape).curveTo(7.9184012, 30.0625, 8.15625, 30.293354, 8.15625, 30.578125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_16;
g.setTransform(defaultTransform__0_0_16);
g.setClip(clip__0_0_16);
float alpha__0_0_17 = origAlpha;
origAlpha = origAlpha * 0.4f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_17 = g.getClip();
AffineTransform defaultTransform__0_0_17 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_17 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(40.6171875, 30.5546875), new Point2D.Double(40.7109375, 30.359375), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 5.0f));
stroke = new BasicStroke(0.29999995f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(40.32811, 35.261402);
((GeneralPath)shape).lineTo(41.20311, 35.691734);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_17;
g.setTransform(defaultTransform__0_0_17);
g.setClip(clip__0_0_17);
float alpha__0_0_18 = origAlpha;
origAlpha = origAlpha * 0.4f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_18 = g.getClip();
AffineTransform defaultTransform__0_0_18 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_18 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(40.6171875, 30.5546875), new Point2D.Double(40.7109375, 30.359375), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.6028670072555542f, -0.7978410124778748f, 0.7978410124778748f, 0.6028670072555542f, -41.1261100769043f, 49.62773132324219f));
stroke = new BasicStroke(0.29999995f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(7.330186, 35.695908);
((GeneralPath)shape).lineTo(8.201031, 35.25723);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_18;
g.setTransform(defaultTransform__0_0_18);
g.setClip(clip__0_0_18);
float alpha__0_0_19 = origAlpha;
origAlpha = origAlpha * 0.4f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_19 = g.getClip();
AffineTransform defaultTransform__0_0_19 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_19 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(40.6171875, 30.5546875), new Point2D.Double(40.7109375, 30.359375), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.8660249710083008f, -0.5f, 0.5f, 0.8660249710083008f, -38.79233169555664f, 12.403385162353516f));
stroke = new BasicStroke(0.29999995f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.263531, 18.446472);
((GeneralPath)shape).lineTo(12.236468, 18.38165);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_19;
g.setTransform(defaultTransform__0_0_19);
g.setClip(clip__0_0_19);
float alpha__0_0_20 = origAlpha;
origAlpha = origAlpha * 0.4f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_20 = g.getClip();
AffineTransform defaultTransform__0_0_20 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_20 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(40.6171875, 30.5546875), new Point2D.Double(40.7109375, 30.359375), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.7071070075035095f, 0.527554988861084f, -0.7071070075035095f, 0.527554988861084f, 29.005800247192383f, -19.091960906982422f));
stroke = new BasicStroke(0.29999998f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(36.12404, 18.147875);
((GeneralPath)shape).lineTo(36.438465, 18.836508);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_20;
g.setTransform(defaultTransform__0_0_20);
g.setClip(clip__0_0_20);
float alpha__0_0_21 = origAlpha;
origAlpha = origAlpha * 0.12000002f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_21 = g.getClip();
AffineTransform defaultTransform__0_0_21 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_21 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(21.9375, 39.0), new Point2D.Double(21.9375, 37.9956169128418), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 5.0f));
shape = new Rectangle2D.Double(8.0, 43.0, 32.03125, 1.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_21;
g.setTransform(defaultTransform__0_0_21);
g.setClip(clip__0_0_21);
float alpha__0_0_22 = origAlpha;
origAlpha = origAlpha * 0.12000002f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_22 = g.getClip();
AffineTransform defaultTransform__0_0_22 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_22 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(22.101398468017578, 27.658130645751953), new Point2D.Double(22.971141815185547, 20.90323829650879), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 7.0f));
stroke = new BasicStroke(1.0000004f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(10.460155, 20.082355);
((GeneralPath)shape).lineTo(6.851398, 32.675762);
((GeneralPath)shape).curveTo(8.298268, 33.37551, 10.625, 34.16706, 10.429825, 36.53313);
((GeneralPath)shape).lineTo(37.299885, 36.53313);
((GeneralPath)shape).curveTo(37.869396, 34.640915, 39.875, 33.375, 41.34614, 33.25);
((GeneralPath)shape).lineTo(37.498104, 20.082355);
((GeneralPath)shape).lineTo(32.350136, 17.523348);
((GeneralPath)shape).lineTo(14.318912, 17.523348);
((GeneralPath)shape).lineTo(10.460155, 20.082355);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_22;
g.setTransform(defaultTransform__0_0_22);
g.setClip(clip__0_0_22);
float alpha__0_0_23 = origAlpha;
origAlpha = origAlpha * 0.83428574f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_23 = g.getClip();
AffineTransform defaultTransform__0_0_23 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_23 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(24.847850799560547, 28.908397674560547), new Point2D.Double(24.847850799560547, 25.75717544555664), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 7.0f));
stroke = new BasicStroke(1.0000004f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(7.976398, 32.050762);
((GeneralPath)shape).curveTo(9.423268, 32.75051, 11.15533, 33.484, 11.402097, 35.40813);
((GeneralPath)shape).lineTo(36.85794, 35.40813);
((GeneralPath)shape).curveTo(37.427456, 33.515915, 38.875, 32.5, 40.34614, 32.375);
((GeneralPath)shape).lineTo(7.976398, 32.050762);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_23;
g.setTransform(defaultTransform__0_0_23);
g.setClip(clip__0_0_23);
float alpha__0_0_24 = origAlpha;
origAlpha = origAlpha * 0.2f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_24 = g.getClip();
AffineTransform defaultTransform__0_0_24 = g.getTransform();
g.transform(new AffineTransform(2.147160530090332f, 0.0f, 0.0f, 1.4314409494400024f, -11.580952644348145f, 1.2105393409729004f));
// _0_0_24 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(16.5, 15.0), 5.5f, new Point2D.Double(16.5, 15.0), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.5454545021057129f, 0.0f, 6.818181991577148f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.0, 15.0);
((GeneralPath)shape).curveTo(22.0, 16.656855, 19.537565, 18.0, 16.5, 18.0);
((GeneralPath)shape).curveTo(13.462434, 18.0, 11.0, 16.656855, 11.0, 15.0);
((GeneralPath)shape).curveTo(11.0, 13.343145, 13.462434, 12.0, 16.5, 12.0);
((GeneralPath)shape).curveTo(19.537565, 12.0, 22.0, 13.343145, 22.0, 15.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_24;
g.setTransform(defaultTransform__0_0_24);
g.setClip(clip__0_0_24);
float alpha__0_0_25 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_25 = g.getClip();
AffineTransform defaultTransform__0_0_25 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -1.0f));
// _0_0_25 is CompositeGraphicsNode
float alpha__0_0_25_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_25_0 = g.getClip();
AffineTransform defaultTransform__0_0_25_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_25_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(17.894716262817383, 14.069843292236328), new Point2D.Double(15.704280853271484, 4.725667953491211), new float[] {0.0f,1.0f}, new Color[] {new Color(138, 226, 52, 255),new Color(115, 210, 22, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.4314405918121338f, 0.0f, 0.0f, 1.4345911741256714f, 0.3327549993991852f, -5.779401779174805f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(29.5, 1.5);
((GeneralPath)shape).lineTo(18.5, 1.5);
((GeneralPath)shape).lineTo(18.5, 13.5);
((GeneralPath)shape).lineTo(12.5, 13.5);
((GeneralPath)shape).lineTo(24.0, 26.5);
((GeneralPath)shape).lineTo(35.5, 13.5);
((GeneralPath)shape).lineTo(29.5, 13.5);
((GeneralPath)shape).lineTo(29.5, 1.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(78, 154, 6, 255);
stroke = new BasicStroke(0.99999994f,0,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(29.5, 1.5);
((GeneralPath)shape).lineTo(18.5, 1.5);
((GeneralPath)shape).lineTo(18.5, 13.5);
((GeneralPath)shape).lineTo(12.5, 13.5);
((GeneralPath)shape).lineTo(24.0, 26.5);
((GeneralPath)shape).lineTo(35.5, 13.5);
((GeneralPath)shape).lineTo(29.5, 13.5);
((GeneralPath)shape).lineTo(29.5, 1.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_25_0;
g.setTransform(defaultTransform__0_0_25_0);
g.setClip(clip__0_0_25_0);
float alpha__0_0_25_1 = origAlpha;
origAlpha = origAlpha * 0.6f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_25_1 = g.getClip();
AffineTransform defaultTransform__0_0_25_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_25_1 is ShapeNode
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.5, 2.5);
((GeneralPath)shape).lineTo(19.5, 2.5);
((GeneralPath)shape).lineTo(19.5, 13.5);
((GeneralPath)shape).curveTo(19.500004, 14.052286, 19.052286, 14.500004, 18.5, 14.5);
((GeneralPath)shape).lineTo(14.71875, 14.5);
((GeneralPath)shape).lineTo(24.0, 25.0);
((GeneralPath)shape).lineTo(33.28125, 14.5);
((GeneralPath)shape).lineTo(29.5, 14.5);
((GeneralPath)shape).curveTo(28.947714, 14.500004, 28.499996, 14.052286, 28.5, 13.5);
((GeneralPath)shape).lineTo(28.5, 2.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_25_1;
g.setTransform(defaultTransform__0_0_25_1);
g.setClip(clip__0_0_25_1);
float alpha__0_0_25_2 = origAlpha;
origAlpha = origAlpha * 0.5f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_25_2 = g.getClip();
AffineTransform defaultTransform__0_0_25_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_25_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(23.5, 17.0625), new Point2D.Double(16.124319076538086, 17.0625), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(19.0, 2.0);
((GeneralPath)shape).lineTo(19.0, 13.5);
((GeneralPath)shape).curveTo(18.999971, 13.776131, 18.77613, 13.999972, 18.5, 14.0);
((GeneralPath)shape).lineTo(13.625, 14.0);
((GeneralPath)shape).lineTo(24.0, 25.71875);
((GeneralPath)shape).lineTo(24.0, 2.0);
((GeneralPath)shape).lineTo(19.0, 2.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_25_2;
g.setTransform(defaultTransform__0_0_25_2);
g.setClip(clip__0_0_25_2);
origAlpha = alpha__0_0_25;
g.setTransform(defaultTransform__0_0_25);
g.setClip(clip__0_0_25);
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
        return 1;
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
	public DownloadIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public DownloadIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public DownloadIcon(int width, int height) {
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

