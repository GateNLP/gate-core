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
public class AdvancedIcon implements
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
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(19.64834213256836, 42.25360107421875), new Point2D.Double(20.631223678588867, 6.775803089141846), new float[] {0.0f,0.5f,0.6761296f,0.8405172f,0.875f,1.0f}, new Color[] {new Color(182, 182, 182, 255),new Color(242, 242, 242, 255),new Color(250, 250, 250, 255),new Color(216, 216, 216, 255),new Color(242, 242, 242, 255),new Color(219, 219, 219, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.8029641509056091f, 0.0f, 0.0f, 0.8029641509056091f, 1.9504512548446655f, 5.5874714851379395f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.002323, 20.442308);
((GeneralPath)shape).lineTo(33.366425, 38.20789);
((GeneralPath)shape).curveTo(34.06902, 39.010853, 36.295254, 39.631443, 37.782726, 38.20789);
((GeneralPath)shape).curveTo(39.21914, 36.833202, 38.886803, 34.895664, 37.481617, 33.490475);
((GeneralPath)shape).lineTo(20.820108, 15.624523);
((GeneralPath)shape).curveTo(22.877186, 9.910419, 18.713762, 5.110967, 13.342505, 6.139506);
((GeneralPath)shape).lineTo(12.188245, 7.193396);
((GeneralPath)shape).lineTo(15.801582, 10.605993);
((GeneralPath)shape).lineTo(16.002325, 13.617112);
((GeneralPath)shape).lineTo(13.304498, 16.079756);
((GeneralPath)shape).lineTo(10.080463, 15.724893);
((GeneralPath)shape).lineTo(6.768237, 12.613407);
((GeneralPath)shape).curveTo(6.768237, 12.613407, 5.6070194, 13.760712, 5.6070194, 13.760712);
((GeneralPath)shape).curveTo(5.0669103, 18.918324, 10.459642, 23.527924, 16.002323, 20.442307);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(136, 138, 133, 255);
stroke = new BasicStroke(0.91425633f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.002323, 20.442308);
((GeneralPath)shape).lineTo(33.366425, 38.20789);
((GeneralPath)shape).curveTo(34.06902, 39.010853, 36.295254, 39.631443, 37.782726, 38.20789);
((GeneralPath)shape).curveTo(39.21914, 36.833202, 38.886803, 34.895664, 37.481617, 33.490475);
((GeneralPath)shape).lineTo(20.820108, 15.624523);
((GeneralPath)shape).curveTo(22.877186, 9.910419, 18.713762, 5.110967, 13.342505, 6.139506);
((GeneralPath)shape).lineTo(12.188245, 7.193396);
((GeneralPath)shape).lineTo(15.801582, 10.605993);
((GeneralPath)shape).lineTo(16.002325, 13.617112);
((GeneralPath)shape).lineTo(13.304498, 16.079756);
((GeneralPath)shape).lineTo(10.080463, 15.724893);
((GeneralPath)shape).lineTo(6.768237, 12.613407);
((GeneralPath)shape).curveTo(6.768237, 12.613407, 5.6070194, 13.760712, 5.6070194, 13.760712);
((GeneralPath)shape).curveTo(5.0669103, 18.918324, 10.459642, 23.527924, 16.002323, 20.442307);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 0.4261364f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1 is ShapeNode
paint = new Color(255, 255, 255, 255);
stroke = new BasicStroke(0.91425586f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.194933, 19.276104);
((GeneralPath)shape).lineTo(33.751297, 37.58648);
((GeneralPath)shape).curveTo(34.295193, 38.208076, 36.018585, 38.68849, 37.17008, 37.58648);
((GeneralPath)shape).curveTo(38.282047, 36.522293, 38.024773, 35.022392, 36.93698, 33.934597);
((GeneralPath)shape).lineTo(20.030973, 15.995827);
((GeneralPath)shape).curveTo(21.402357, 10.05316, 18.331564, 6.8490214, 13.760281, 6.9633036);
((GeneralPath)shape).lineTo(13.513311, 7.21324);
((GeneralPath)shape).lineTo(16.807308, 10.172018);
((GeneralPath)shape).lineTo(16.926313, 13.995251);
((GeneralPath)shape).lineTo(13.622267, 17.01091);
((GeneralPath)shape).lineTo(9.74371, 16.59199);
((GeneralPath)shape).lineTo(6.839499, 13.857009);
((GeneralPath)shape).lineTo(6.517097, 14.250193);
((GeneralPath)shape).curveTo(6.231392, 19.70716, 12.452194, 22.190296, 16.194933, 19.276104);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 0.17045456f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(0.697938084602356f, 0.7161580920219421f, -0.7161580920219421f, 0.697938084602356f, 0.0f, 0.0f));
// _0_0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(50.152931213378906, -3.6324477195739746), new Point2D.Double(25.291086196899414, -4.300265312194824), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(0, 0, 0, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.8029640316963196f, 0.0f, 0.0f, 0.8029640316963196f, 5.362800598144531f, 2.5028727054595947f));
stroke = new BasicStroke(0.91425395f,0,0,4.0f,null,0.0f);
shape = new RoundRectangle2D.Double(26.25999641418457, -1.399808406829834, 21.27317237854004, 1.8792462348937988, 1.616187334060669, 1.616187334060669);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2;
g.setTransform(defaultTransform__0_0_2);
g.setClip(clip__0_0_2);
float alpha__0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(0.8029641509056091f, 0.0f, 0.0f, 0.8029641509056091f, 1.8500804901123047f, 5.687841415405273f));
// _0_0_3 is ShapeNode
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(43.25, 37.5);
((GeneralPath)shape).curveTo(43.25, 38.25939, 42.63439, 38.875, 41.875, 38.875);
((GeneralPath)shape).curveTo(41.11561, 38.875, 40.5, 38.25939, 40.5, 37.5);
((GeneralPath)shape).curveTo(40.5, 36.74061, 41.11561, 36.125, 41.875, 36.125);
((GeneralPath)shape).curveTo(42.63439, 36.125, 43.25, 36.74061, 43.25, 37.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(161, 161, 161, 255);
stroke = new BasicStroke(1.1386017f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(43.25, 37.5);
((GeneralPath)shape).curveTo(43.25, 38.25939, 42.63439, 38.875, 41.875, 38.875);
((GeneralPath)shape).curveTo(41.11561, 38.875, 40.5, 38.25939, 40.5, 37.5);
((GeneralPath)shape).curveTo(40.5, 36.74061, 41.11561, 36.125, 41.875, 36.125);
((GeneralPath)shape).curveTo(42.63439, 36.125, 43.25, 36.74061, 43.25, 37.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_3;
g.setTransform(defaultTransform__0_0_3);
g.setClip(clip__0_0_3);
float alpha__0_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(0.6464770436286926f, 0.6464770436286926f, -0.6464770436286926f, 0.6464770436286926f, -57.99017333984375f, -112.1613998413086f));
// _0_0_4 is CompositeGraphicsNode
float alpha__0_0_4_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4_0 = g.getClip();
AffineTransform defaultTransform__0_0_4_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4_0 is ShapeNode
paint = new Color(233, 185, 110, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(171.04228, 65.702995);
((GeneralPath)shape).curveTo(173.32448, 65.651665, 173.29129, 63.09213, 173.22289, 60.35885);
((GeneralPath)shape).curveTo(173.1033, 55.577446, 172.3936, 56.139324, 172.24411, 50.162567);
((GeneralPath)shape).curveTo(172.09464, 44.185814, 172.63795, 32.554317, 172.63795, 32.554317);
((GeneralPath)shape).curveTo(172.59035, 30.64945, 172.92076, 28.116953, 169.35207, 28.350822);
((GeneralPath)shape).curveTo(165.78337, 28.116953, 166.11382, 30.649448, 166.06618, 32.554317);
((GeneralPath)shape).curveTo(166.06618, 32.554317, 166.60951, 44.185814, 166.46002, 50.162567);
((GeneralPath)shape).curveTo(166.31053, 56.139328, 165.60081, 55.57745, 165.48125, 60.35885);
((GeneralPath)shape).curveTo(165.41284, 63.092125, 165.37967, 65.651665, 167.66185, 65.702995);
((GeneralPath)shape).curveTo(167.66185, 65.702995, 167.4959, 65.72186, 169.35207, 65.72186);
((GeneralPath)shape).curveTo(171.20822, 65.72186, 171.04228, 65.702995, 171.04228, 65.702995);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(143, 89, 2, 255);
stroke = new BasicStroke(0.9999997f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(171.04228, 65.702995);
((GeneralPath)shape).curveTo(173.32448, 65.651665, 173.29129, 63.09213, 173.22289, 60.35885);
((GeneralPath)shape).curveTo(173.1033, 55.577446, 172.3936, 56.139324, 172.24411, 50.162567);
((GeneralPath)shape).curveTo(172.09464, 44.185814, 172.63795, 32.554317, 172.63795, 32.554317);
((GeneralPath)shape).curveTo(172.59035, 30.64945, 172.92076, 28.116953, 169.35207, 28.350822);
((GeneralPath)shape).curveTo(165.78337, 28.116953, 166.11382, 30.649448, 166.06618, 32.554317);
((GeneralPath)shape).curveTo(166.06618, 32.554317, 166.60951, 44.185814, 166.46002, 50.162567);
((GeneralPath)shape).curveTo(166.31053, 56.139328, 165.60081, 55.57745, 165.48125, 60.35885);
((GeneralPath)shape).curveTo(165.41284, 63.092125, 165.37967, 65.651665, 167.66185, 65.702995);
((GeneralPath)shape).curveTo(167.66185, 65.702995, 167.4959, 65.72186, 169.35207, 65.72186);
((GeneralPath)shape).curveTo(171.20822, 65.72186, 171.04228, 65.702995, 171.04228, 65.702995);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_4_0;
g.setTransform(defaultTransform__0_0_4_0);
g.setClip(clip__0_0_4_0);
float alpha__0_0_4_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4_1 = g.getClip();
AffineTransform defaultTransform__0_0_4_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(166.95419311523438, 48.5126953125), new Point2D.Double(171.73304748535156, 48.5126953125), new float[] {0.0f,0.2687897f,1.0f}, new Color[] {new Color(194, 126, 19, 255),new Color(194, 126, 19, 127),new Color(194, 126, 19, 248)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(169.25, 29.84375);
((GeneralPath)shape).lineTo(169.3437, 29.84375);
((GeneralPath)shape).lineTo(169.43741, 29.84375);
((GeneralPath)shape).curveTo(170.15482, 29.79674, 170.53218, 29.92288, 170.68741, 30.0);
((GeneralPath)shape).curveTo(170.84264, 30.07712, 170.86421, 30.08319, 170.93741, 30.25);
((GeneralPath)shape).curveTo(171.07603, 30.565935, 171.09985, 31.481052, 171.12491, 32.46875);
((GeneralPath)shape).curveTo(171.12491, 32.46875, 171.12495, 32.5926, 171.12491, 32.59375);
((GeneralPath)shape).curveTo(171.11691, 32.75534, 170.59802, 44.114426, 170.74991, 50.1875);
((GeneralPath)shape).curveTo(170.8262, 53.23637, 171.06699, 54.746635, 171.28116, 55.96875);
((GeneralPath)shape).curveTo(171.49533, 57.190865, 171.66075, 58.091164, 171.71866, 60.40625);
((GeneralPath)shape).curveTo(171.75276, 61.767246, 171.73576, 63.018414, 171.56241, 63.65625);
((GeneralPath)shape).curveTo(171.47581, 63.97517, 171.38142, 64.09043, 171.34366, 64.125);
((GeneralPath)shape).lineTo(167.3748, 64.125);
((GeneralPath)shape).curveTo(167.337, 64.09043, 167.21146, 63.97517, 167.1248, 63.65625);
((GeneralPath)shape).curveTo(166.9515, 63.018414, 166.9345, 61.767246, 166.96855, 60.40625);
((GeneralPath)shape).curveTo(167.02646, 58.091164, 167.22311, 57.190865, 167.4373, 55.96875);
((GeneralPath)shape).curveTo(167.65149, 54.746635, 167.89229, 53.23637, 167.96855, 50.1875);
((GeneralPath)shape).curveTo(168.12044, 44.114426, 167.56992, 32.75533, 167.5623, 32.59375);
((GeneralPath)shape).curveTo(167.56265, 32.57976, 167.56195, 32.57645, 167.5623, 32.5625);
((GeneralPath)shape).curveTo(167.56227, 32.561855, 167.5623, 32.46875, 167.5623, 32.46875);
((GeneralPath)shape).curveTo(167.5874, 31.481031, 167.6112, 30.565939, 167.7498, 30.25);
((GeneralPath)shape).curveTo(167.823, 30.08319, 167.8446, 30.077116, 167.9998, 30.0);
((GeneralPath)shape).curveTo(168.15503, 29.92288, 168.5324, 29.796736, 169.2498, 29.84375);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_4_1;
g.setTransform(defaultTransform__0_0_4_1);
g.setClip(clip__0_0_4_1);
origAlpha = alpha__0_0_4;
g.setTransform(defaultTransform__0_0_4);
g.setClip(clip__0_0_4);
float alpha__0_0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5 = g.getClip();
AffineTransform defaultTransform__0_0_5 = g.getTransform();
g.transform(new AffineTransform(0.9142045378684998f, 0.009755309671163559f, -0.009755309671163559f, 0.9142045378684998f, 2.127701997756958f, 2.5549278259277344f));
// _0_0_5 is CompositeGraphicsNode
float alpha__0_0_5_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5_0 = g.getClip();
AffineTransform defaultTransform__0_0_5_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_5_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(-32.163665771484375, 11.98286247253418), new Point2D.Double(-34.73264694213867, 14.757363319396973), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(102, 104, 100, 255),new Color(73, 74, 71, 127),new Color(102, 104, 100, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 69.51960754394531f, -1.6848770380020142f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(21.733036, 4.289578);
((GeneralPath)shape).lineTo(22.010368, 4.332122);
((GeneralPath)shape).curveTo(25.14036, 3.0951848, 32.527985, 6.87619, 31.139639, 10.68694);
((GeneralPath)shape).curveTo(30.76798, 11.293646, 30.894001, 12.328698, 31.417088, 12.954636);
((GeneralPath)shape).lineTo(34.806065, 15.940214);
((GeneralPath)shape).curveTo(35.80204, 16.742601, 36.70207, 16.766914, 37.539093, 16.48728);
((GeneralPath)shape).curveTo(39.15567, 15.947207, 40.15954, 17.502483, 41.2066, 18.621653);
((GeneralPath)shape).lineTo(40.78708, 19.082663);
((GeneralPath)shape).curveTo(40.167316, 19.782696, 40.290512, 20.897503, 41.06708, 21.592634);
((GeneralPath)shape).curveTo(41.843647, 22.287762, 42.96885, 22.287006, 43.589012, 21.594234);
((GeneralPath)shape).lineTo(46.36291, 18.499186);
((GeneralPath)shape).curveTo(46.982677, 17.799152, 46.859478, 16.684343, 46.079502, 15.993021);
((GeneralPath)shape).curveTo(45.37887, 15.362435, 44.410984, 15.32916, 43.76641, 15.842423);
((GeneralPath)shape).curveTo(41.628696, 13.695759, 42.666817, 13.298207, 39.84931, 10.505331);
((GeneralPath)shape).lineTo(34.593884, 5.6981835);
((GeneralPath)shape).curveTo(30.81762, 2.3830767, 26.164661, 2.6441734, 21.733036, 4.2896256);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_5_0;
g.setTransform(defaultTransform__0_0_5_0);
g.setClip(clip__0_0_5_0);
float alpha__0_0_5_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5_1 = g.getClip();
AffineTransform defaultTransform__0_0_5_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_5_1 is ShapeNode
paint = new Color(186, 189, 182, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.582108, 3.096373);
((GeneralPath)shape).curveTo(25.650492, 3.1027632, 23.677193, 3.5639877, 21.738358, 4.283873);
((GeneralPath)shape).lineTo(22.019608, 4.346373);
((GeneralPath)shape).curveTo(25.1496, 3.109436, 32.53295, 6.8793726, 31.144608, 10.690123);
((GeneralPath)shape).curveTo(30.77295, 11.296828, 30.902771, 12.314184, 31.425858, 12.940123);
((GeneralPath)shape).lineTo(34.800858, 15.940123);
((GeneralPath)shape).curveTo(35.796833, 16.74251, 36.713833, 16.782257, 37.550858, 16.502623);
((GeneralPath)shape).curveTo(39.167435, 15.96255, 40.160046, 17.508453, 41.207108, 18.627623);
((GeneralPath)shape).lineTo(40.800858, 19.096373);
((GeneralPath)shape).curveTo(40.181095, 19.796406, 40.30554, 20.901243, 41.082108, 21.596373);
((GeneralPath)shape).curveTo(41.858673, 22.2915, 42.961945, 22.289145, 43.582108, 21.596373);
((GeneralPath)shape).lineTo(46.363358, 18.502623);
((GeneralPath)shape).curveTo(46.983124, 17.80259, 46.862083, 16.693945, 46.082108, 16.002623);
((GeneralPath)shape).curveTo(45.381474, 15.372037, 44.41418, 15.33311, 43.769608, 15.846373);
((GeneralPath)shape).curveTo(41.631893, 13.699707, 42.68086, 13.295499, 39.863358, 10.502623);
((GeneralPath)shape).lineTo(34.582108, 5.6901226);
((GeneralPath)shape).curveTo(32.45796, 3.825375, 30.065613, 3.0881557, 27.582108, 3.0963726);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(29.894608, 4.971373);
((GeneralPath)shape).curveTo(31.181202, 5.2943683, 32.43051, 5.809723, 33.582108, 6.815123);
((GeneralPath)shape).curveTo(33.589306, 6.821403, 33.606167, 6.808813, 33.613358, 6.815123);
((GeneralPath)shape).lineTo(38.832108, 11.627623);
((GeneralPath)shape).curveTo(40.07775, 12.873689, 40.376766, 13.438362, 40.707108, 14.096373);
((GeneralPath)shape).curveTo(41.044773, 14.76897, 41.519917, 15.716709, 42.707108, 16.908873);
((GeneralPath)shape).lineTo(43.644608, 17.846373);
((GeneralPath)shape).lineTo(44.707108, 17.002623);
((GeneralPath)shape).curveTo(44.754547, 16.964853, 44.87254, 16.907753, 45.082108, 17.096373);
((GeneralPath)shape).lineTo(45.082108, 17.127623);
((GeneralPath)shape).curveTo(45.32173, 17.340012, 45.268795, 17.46824, 45.238358, 17.502623);
((GeneralPath)shape).lineTo(42.457108, 20.596373);
((GeneralPath)shape).curveTo(42.43797, 20.617752, 42.321766, 20.685904, 42.082108, 20.471373);
((GeneralPath)shape).curveTo(41.855934, 20.268917, 41.864532, 20.14184, 41.894608, 20.096373);
((GeneralPath)shape).curveTo(41.896606, 20.093273, 41.892708, 20.067303, 41.894608, 20.065123);
((GeneralPath)shape).lineTo(42.300858, 19.627623);
((GeneralPath)shape).lineTo(43.238358, 18.596373);
((GeneralPath)shape).lineTo(42.300858, 17.596373);
((GeneralPath)shape).curveTo(41.850765, 17.115282, 41.321716, 16.38616, 40.519608, 15.752623);
((GeneralPath)shape).curveTo(39.7175, 15.119084, 38.358093, 14.628395, 37.050858, 15.065123);
((GeneralPath)shape).curveTo(36.566505, 15.226936, 36.396202, 15.278884, 35.800858, 14.815123);
((GeneralPath)shape).lineTo(35.738358, 14.783873);
((GeneralPath)shape).lineTo(32.582108, 12.002623);
((GeneralPath)shape).curveTo(32.577908, 11.9976225, 32.58571, 11.979153, 32.582108, 11.971373);
((GeneralPath)shape).curveTo(32.527317, 11.852411, 32.52305, 11.434682, 32.457108, 11.440123);
((GeneralPath)shape).lineTo(32.488358, 11.346373);
((GeneralPath)shape).lineTo(32.550858, 11.190123);
((GeneralPath)shape).curveTo(33.07172, 9.760458, 32.738747, 8.274313, 32.019608, 7.1276226);
((GeneralPath)shape).curveTo(31.484072, 6.2736955, 30.715925, 5.58267, 29.894608, 4.9713726);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_5_1;
g.setTransform(defaultTransform__0_0_5_1);
g.setClip(clip__0_0_5_1);
float alpha__0_0_5_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5_2 = g.getClip();
AffineTransform defaultTransform__0_0_5_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_5_2 is ShapeNode
paint = new Color(85, 87, 83, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.02517, 3.8662307);
((GeneralPath)shape).lineTo(22.300884, 3.9182417);
((GeneralPath)shape).curveTo(25.471375, 2.7891524, 32.72527, 6.8207827, 31.207314, 10.581784);
((GeneralPath)shape).curveTo(30.81511, 11.175414, 30.90563, 12.214173, 31.406988, 12.857647);
((GeneralPath)shape).lineTo(34.6918, 15.957463);
((GeneralPath)shape).curveTo(35.65973, 16.793467, 36.5584, 16.84857, 37.404503, 16.597746);
((GeneralPath)shape).curveTo(39.038616, 16.113317, 39.98867, 17.702038, 40.99681, 18.856388);
((GeneralPath)shape).lineTo(40.561756, 19.30277);
((GeneralPath)shape).curveTo(39.918396, 19.981182, 40.00337, 21.099554, 40.75569, 21.820854);
((GeneralPath)shape).curveTo(41.50801, 22.542152, 42.63258, 22.579906, 43.276093, 21.908764);
((GeneralPath)shape).lineTo(46.154297, 18.910463);
((GeneralPath)shape).curveTo(46.797657, 18.23205, 46.712685, 17.11368, 45.95683, 16.396069);
((GeneralPath)shape).curveTo(45.278187, 15.741873, 44.312008, 15.675491, 43.650246, 16.166393);
((GeneralPath)shape).curveTo(41.587254, 13.947824, 42.63837, 13.586035, 39.918102, 10.698366);
((GeneralPath)shape).lineTo(34.830273, 5.7141705);
((GeneralPath)shape).curveTo(31.16968, 2.271764, 26.510513, 2.3734615, 22.025167, 3.8662784);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_5_2;
g.setTransform(defaultTransform__0_0_5_2);
g.setClip(clip__0_0_5_2);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
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
        return 5;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 5;
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
	public AdvancedIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public AdvancedIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public AdvancedIcon(int width, int height) {
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

