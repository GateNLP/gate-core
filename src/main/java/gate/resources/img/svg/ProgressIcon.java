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
public class ProgressIcon implements
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
g.transform(new AffineTransform(0.9659259915351868f, 0.25881901383399963f, -0.25881901383399963f, 0.9659259915351868f, 6.162129878997803f, 8.385499954223633f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(22.96537971496582, 35.31056594848633), new Point2D.Double(12.641190528869629, 11.423575401306152), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(211, 215, 207, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659259915351868f, -0.25881901383399963f, 0.25881901383399963f, 0.9659259915351868f, 7.281559944152832f, 0.7556419968605042f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 0.5);
((GeneralPath)shape).curveTo(30.438065, 0.5, 29.8842, 0.547001, 29.34375, 0.625);
((GeneralPath)shape).lineTo(29.125, 3.71875);
((GeneralPath)shape).curveTo(28.16536, 3.9344947, 27.275486, 4.3022814, 26.46875, 4.8125);
((GeneralPath)shape).lineTo(24.125, 2.78125);
((GeneralPath)shape).curveTo(22.999998, 3.500001, 22.5, 4.0, 21.78125, 5.125);
((GeneralPath)shape).lineTo(23.8125, 7.46875);
((GeneralPath)shape).curveTo(23.30228, 8.275486, 22.934496, 9.1653595, 22.71875, 10.125);
((GeneralPath)shape).lineTo(19.625, 10.34375);
((GeneralPath)shape).curveTo(19.547, 10.8842, 19.5, 11.438062, 19.5, 12.0);
((GeneralPath)shape).curveTo(19.499998, 12.561939, 19.547, 13.115801, 19.625, 13.65625);
((GeneralPath)shape).lineTo(22.71875, 13.875);
((GeneralPath)shape).curveTo(22.934496, 14.834641, 23.30228, 15.724514, 23.8125, 16.53125);
((GeneralPath)shape).lineTo(21.78125, 18.875);
((GeneralPath)shape).curveTo(22.5, 19.999998, 23.0, 20.5, 24.125, 21.21875);
((GeneralPath)shape).lineTo(26.46875, 19.1875);
((GeneralPath)shape).curveTo(27.275486, 19.69772, 28.16536, 20.065504, 29.125, 20.28125);
((GeneralPath)shape).lineTo(29.34375, 23.375);
((GeneralPath)shape).curveTo(29.8842, 23.453, 30.438063, 23.5, 31.0, 23.5);
((GeneralPath)shape).curveTo(31.561935, 23.5, 32.115803, 23.453, 32.65625, 23.375);
((GeneralPath)shape).lineTo(32.875, 20.28125);
((GeneralPath)shape).curveTo(33.83464, 20.065504, 34.724514, 19.69772, 35.53125, 19.1875);
((GeneralPath)shape).lineTo(37.875, 21.21875);
((GeneralPath)shape).curveTo(39.000004, 20.499998, 39.5, 20.0, 40.21875, 18.875);
((GeneralPath)shape).lineTo(38.1875, 16.53125);
((GeneralPath)shape).curveTo(38.69772, 15.724514, 39.065506, 14.834641, 39.28125, 13.875);
((GeneralPath)shape).lineTo(42.375, 13.65625);
((GeneralPath)shape).curveTo(42.453, 13.115801, 42.5, 12.561939, 42.5, 12.0);
((GeneralPath)shape).curveTo(42.5, 11.438063, 42.453, 10.884199, 42.375, 10.34375);
((GeneralPath)shape).lineTo(39.28125, 10.125);
((GeneralPath)shape).curveTo(39.065506, 9.1653595, 38.69772, 8.275486, 38.1875, 7.46875);
((GeneralPath)shape).lineTo(40.21875, 5.125);
((GeneralPath)shape).curveTo(39.56926, 4.0173154, 39.0, 3.5, 37.875, 2.78125);
((GeneralPath)shape).lineTo(35.53125, 4.8125);
((GeneralPath)shape).curveTo(34.724514, 4.3022814, 33.83464, 3.9344947, 32.875, 3.71875);
((GeneralPath)shape).lineTo(32.65625, 0.625);
((GeneralPath)shape).curveTo(32.1158, 0.547001, 31.561937, 0.5, 31.0, 0.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 9.5);
((GeneralPath)shape).curveTo(32.38, 9.5, 33.5, 10.619998, 33.5, 12.0);
((GeneralPath)shape).curveTo(33.5, 13.380003, 32.380005, 14.5, 31.0, 14.5);
((GeneralPath)shape).curveTo(29.619999, 14.5, 28.5, 13.380003, 28.5, 12.0);
((GeneralPath)shape).curveTo(28.5, 10.619998, 29.619999, 9.5, 31.0, 9.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0054358f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 0.5);
((GeneralPath)shape).curveTo(30.438065, 0.5, 29.8842, 0.547001, 29.34375, 0.625);
((GeneralPath)shape).lineTo(29.125, 3.71875);
((GeneralPath)shape).curveTo(28.16536, 3.9344947, 27.275486, 4.3022814, 26.46875, 4.8125);
((GeneralPath)shape).lineTo(24.125, 2.78125);
((GeneralPath)shape).curveTo(22.999998, 3.500001, 22.5, 4.0, 21.78125, 5.125);
((GeneralPath)shape).lineTo(23.8125, 7.46875);
((GeneralPath)shape).curveTo(23.30228, 8.275486, 22.934496, 9.1653595, 22.71875, 10.125);
((GeneralPath)shape).lineTo(19.625, 10.34375);
((GeneralPath)shape).curveTo(19.547, 10.8842, 19.5, 11.438062, 19.5, 12.0);
((GeneralPath)shape).curveTo(19.499998, 12.561939, 19.547, 13.115801, 19.625, 13.65625);
((GeneralPath)shape).lineTo(22.71875, 13.875);
((GeneralPath)shape).curveTo(22.934496, 14.834641, 23.30228, 15.724514, 23.8125, 16.53125);
((GeneralPath)shape).lineTo(21.78125, 18.875);
((GeneralPath)shape).curveTo(22.5, 19.999998, 23.0, 20.5, 24.125, 21.21875);
((GeneralPath)shape).lineTo(26.46875, 19.1875);
((GeneralPath)shape).curveTo(27.275486, 19.69772, 28.16536, 20.065504, 29.125, 20.28125);
((GeneralPath)shape).lineTo(29.34375, 23.375);
((GeneralPath)shape).curveTo(29.8842, 23.453, 30.438063, 23.5, 31.0, 23.5);
((GeneralPath)shape).curveTo(31.561935, 23.5, 32.115803, 23.453, 32.65625, 23.375);
((GeneralPath)shape).lineTo(32.875, 20.28125);
((GeneralPath)shape).curveTo(33.83464, 20.065504, 34.724514, 19.69772, 35.53125, 19.1875);
((GeneralPath)shape).lineTo(37.875, 21.21875);
((GeneralPath)shape).curveTo(39.000004, 20.499998, 39.5, 20.0, 40.21875, 18.875);
((GeneralPath)shape).lineTo(38.1875, 16.53125);
((GeneralPath)shape).curveTo(38.69772, 15.724514, 39.065506, 14.834641, 39.28125, 13.875);
((GeneralPath)shape).lineTo(42.375, 13.65625);
((GeneralPath)shape).curveTo(42.453, 13.115801, 42.5, 12.561939, 42.5, 12.0);
((GeneralPath)shape).curveTo(42.5, 11.438063, 42.453, 10.884199, 42.375, 10.34375);
((GeneralPath)shape).lineTo(39.28125, 10.125);
((GeneralPath)shape).curveTo(39.065506, 9.1653595, 38.69772, 8.275486, 38.1875, 7.46875);
((GeneralPath)shape).lineTo(40.21875, 5.125);
((GeneralPath)shape).curveTo(39.56926, 4.0173154, 39.0, 3.5, 37.875, 2.78125);
((GeneralPath)shape).lineTo(35.53125, 4.8125);
((GeneralPath)shape).curveTo(34.724514, 4.3022814, 33.83464, 3.9344947, 32.875, 3.71875);
((GeneralPath)shape).lineTo(32.65625, 0.625);
((GeneralPath)shape).curveTo(32.1158, 0.547001, 31.561937, 0.5, 31.0, 0.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 9.5);
((GeneralPath)shape).curveTo(32.38, 9.5, 33.5, 10.619998, 33.5, 12.0);
((GeneralPath)shape).curveTo(33.5, 13.380003, 32.380005, 14.5, 31.0, 14.5);
((GeneralPath)shape).curveTo(29.619999, 14.5, 28.5, 13.380003, 28.5, 12.0);
((GeneralPath)shape).curveTo(28.5, 10.619998, 29.619999, 9.5, 31.0, 9.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
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
paint = new LinearGradientPaint(new Point2D.Double(17.413135528564453, 7.870833396911621), new Point2D.Double(17.027729034423828, 52.50489044189453), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659259915351868f, -0.25881901383399963f, 0.25881901383399963f, 0.9659259915351868f, 7.281559944152832f, 0.7556419968605042f));
stroke = new BasicStroke(1.1333336f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.000008, 1.437503);
((GeneralPath)shape).curveTo(30.739935, 1.437503, 30.477873, 1.47946, 30.218758, 1.500003);
((GeneralPath)shape).lineTo(30.000008, 4.562504);
((GeneralPath)shape).curveTo(28.682978, 4.737208, 27.478296, 5.264549, 26.468756, 6.031255);
((GeneralPath)shape).lineTo(24.156256, 4.062504);
((GeneralPath)shape).curveTo(23.765211, 4.40014, 23.400139, 4.765213, 23.062504, 5.156255);
((GeneralPath)shape).lineTo(25.031256, 7.468756);
((GeneralPath)shape).curveTo(24.26455, 8.478295, 23.737207, 9.682977, 23.562504, 11.000006);
((GeneralPath)shape).lineTo(20.500004, 11.218757);
((GeneralPath)shape).curveTo(20.47946, 11.477873, 20.437504, 11.739933, 20.437504, 12.000007);
((GeneralPath)shape).curveTo(20.437504, 12.26008, 20.47946, 12.52214, 20.500004, 12.781257);
((GeneralPath)shape).lineTo(23.562504, 13.000007);
((GeneralPath)shape).curveTo(23.737207, 14.317036, 24.26455, 15.521718, 25.031256, 16.531258);
((GeneralPath)shape).lineTo(23.062504, 18.84376);
((GeneralPath)shape).curveTo(23.400139, 19.2348, 23.765211, 19.599874, 24.156256, 19.93751);
((GeneralPath)shape).lineTo(26.468756, 17.968758);
((GeneralPath)shape).curveTo(27.478294, 18.735464, 28.682978, 19.262806, 30.000006, 19.43751);
((GeneralPath)shape).lineTo(30.218758, 22.50001);
((GeneralPath)shape).curveTo(30.477873, 22.520554, 30.739935, 22.56251, 31.000008, 22.56251);
((GeneralPath)shape).curveTo(31.260078, 22.56251, 31.52214, 22.520554, 31.781258, 22.50001);
((GeneralPath)shape).lineTo(32.000008, 19.43751);
((GeneralPath)shape).curveTo(33.317036, 19.262806, 34.521717, 18.735464, 35.531258, 17.968758);
((GeneralPath)shape).lineTo(37.843758, 19.93751);
((GeneralPath)shape).curveTo(38.234802, 19.599874, 38.599873, 19.2348, 38.937508, 18.843758);
((GeneralPath)shape).lineTo(36.968758, 16.531258);
((GeneralPath)shape).curveTo(37.735462, 15.521718, 38.262806, 14.317036, 38.437508, 13.000007);
((GeneralPath)shape).lineTo(41.50001, 12.781257);
((GeneralPath)shape).curveTo(41.520554, 12.522141, 41.56251, 12.26008, 41.56251, 12.000006);
((GeneralPath)shape).curveTo(41.56251, 11.739934, 41.520554, 11.477873, 41.50001, 11.218757);
((GeneralPath)shape).lineTo(38.437508, 11.000007);
((GeneralPath)shape).curveTo(38.262806, 9.682977, 37.735462, 8.478296, 36.968758, 7.468755);
((GeneralPath)shape).lineTo(38.937508, 5.156254);
((GeneralPath)shape).curveTo(38.599876, 4.765213, 38.234802, 4.400139, 37.843758, 4.062504);
((GeneralPath)shape).lineTo(35.531258, 6.031255);
((GeneralPath)shape).curveTo(34.52172, 5.264549, 33.317036, 4.737207, 32.000008, 4.562504);
((GeneralPath)shape).lineTo(31.781258, 1.500003);
((GeneralPath)shape).curveTo(31.52214, 1.47946, 31.26008, 1.437504, 31.000008, 1.437503);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_1;
g.setTransform(defaultTransform__0_0_0_1);
g.setClip(clip__0_0_0_1);
float alpha__0_0_0_2 = origAlpha;
origAlpha = origAlpha * 0.8f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_2 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(31.0, 12.0), 11.125f, new Point2D.Double(31.0, 12.0), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9774659872055054f, -0.261911004781723f, 0.261911004781723f, 0.9774659872055054f, -2.4443750381469727f, 8.389657020568848f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 0.875);
((GeneralPath)shape).curveTo(30.67898, 0.87500006, 30.416025, 0.91938233, 30.1875, 0.9375);
((GeneralPath)shape).lineTo(29.6875, 0.96875);
((GeneralPath)shape).lineTo(29.65625, 1.46875);
((GeneralPath)shape).lineTo(29.46875, 4.125);
((GeneralPath)shape).curveTo(28.408657, 4.334056, 27.427074, 4.7655263, 26.5625, 5.34375);
((GeneralPath)shape).lineTo(24.15625, 3.3125);
((GeneralPath)shape).lineTo(22.3125, 5.15625);
((GeneralPath)shape).lineTo(24.34375, 7.5625);
((GeneralPath)shape).curveTo(23.765526, 8.427073, 23.334055, 9.408658, 23.125, 10.46875);
((GeneralPath)shape).lineTo(20.46875, 10.65625);
((GeneralPath)shape).lineTo(19.96875, 10.6875);
((GeneralPath)shape).lineTo(19.9375, 11.1875);
((GeneralPath)shape).curveTo(19.91938, 11.416028, 19.875, 11.678982, 19.875, 12.0);
((GeneralPath)shape).curveTo(19.875, 12.321017, 19.919382, 12.583973, 19.9375, 12.8125);
((GeneralPath)shape).lineTo(19.96875, 13.3125);
((GeneralPath)shape).lineTo(20.46875, 13.34375);
((GeneralPath)shape).lineTo(23.125, 13.53125);
((GeneralPath)shape).curveTo(23.334057, 14.591342, 23.765526, 15.572926, 24.34375, 16.4375);
((GeneralPath)shape).lineTo(22.625, 18.46875);
((GeneralPath)shape).lineTo(22.3125, 18.84375);
((GeneralPath)shape).lineTo(22.625, 19.21875);
((GeneralPath)shape).curveTo(22.98067, 19.630678, 23.36932, 20.01933, 23.78125, 20.375);
((GeneralPath)shape).lineTo(24.15625, 20.6875);
((GeneralPath)shape).lineTo(24.53125, 20.375);
((GeneralPath)shape).lineTo(26.5625, 18.65625);
((GeneralPath)shape).curveTo(27.427073, 19.234474, 28.408657, 19.665945, 29.46875, 19.875);
((GeneralPath)shape).lineTo(29.65625, 22.53125);
((GeneralPath)shape).lineTo(29.6875, 23.03125);
((GeneralPath)shape).lineTo(30.1875, 23.0625);
((GeneralPath)shape).curveTo(30.416027, 23.08062, 30.678982, 23.125, 31.0, 23.125);
((GeneralPath)shape).curveTo(31.321018, 23.125002, 31.583973, 23.08062, 31.8125, 23.0625);
((GeneralPath)shape).lineTo(32.3125, 23.03125);
((GeneralPath)shape).lineTo(32.34375, 22.53125);
((GeneralPath)shape).lineTo(32.53125, 19.875);
((GeneralPath)shape).curveTo(33.59134, 19.665943, 34.572926, 19.234474, 35.4375, 18.65625);
((GeneralPath)shape).lineTo(37.46875, 20.375);
((GeneralPath)shape).lineTo(37.84375, 20.6875);
((GeneralPath)shape).lineTo(38.21875, 20.375);
((GeneralPath)shape).curveTo(38.630684, 20.01933, 39.01933, 19.63068, 39.375, 19.21875);
((GeneralPath)shape).lineTo(39.6875, 18.84375);
((GeneralPath)shape).lineTo(39.375, 18.46875);
((GeneralPath)shape).lineTo(37.65625, 16.4375);
((GeneralPath)shape).curveTo(38.234474, 15.572927, 38.665943, 14.591342, 38.875, 13.53125);
((GeneralPath)shape).lineTo(41.53125, 13.34375);
((GeneralPath)shape).lineTo(42.03125, 13.3125);
((GeneralPath)shape).lineTo(42.0625, 12.8125);
((GeneralPath)shape).curveTo(42.080616, 12.583975, 42.125, 12.321018, 42.125, 12.0);
((GeneralPath)shape).curveTo(42.125, 11.678985, 42.08062, 11.416028, 42.0625, 11.1875);
((GeneralPath)shape).lineTo(42.03125, 10.6875);
((GeneralPath)shape).lineTo(41.53125, 10.65625);
((GeneralPath)shape).lineTo(38.875, 10.46875);
((GeneralPath)shape).curveTo(38.665943, 9.408658, 38.234474, 8.4270735, 37.65625, 7.5625);
((GeneralPath)shape).lineTo(39.375, 5.53125);
((GeneralPath)shape).lineTo(39.6875, 5.15625);
((GeneralPath)shape).lineTo(39.375, 4.78125);
((GeneralPath)shape).curveTo(39.019333, 4.3693213, 38.63068, 3.9806705, 38.21875, 3.625);
((GeneralPath)shape).lineTo(37.84375, 3.3125);
((GeneralPath)shape).lineTo(37.46875, 3.625);
((GeneralPath)shape).lineTo(35.4375, 5.34375);
((GeneralPath)shape).curveTo(34.572926, 4.7655263, 33.591343, 4.3340554, 32.53125, 4.125);
((GeneralPath)shape).lineTo(32.34375, 1.46875);
((GeneralPath)shape).lineTo(32.3125, 0.96875);
((GeneralPath)shape).lineTo(31.8125, 0.9375);
((GeneralPath)shape).curveTo(31.583973, 0.9193822, 31.321016, 0.87500125, 31.0, 0.875);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 8.5);
((GeneralPath)shape).curveTo(32.932, 8.499998, 34.499996, 10.067999, 34.5, 12.0);
((GeneralPath)shape).curveTo(34.500004, 13.932002, 32.932, 15.499998, 31.0, 15.5);
((GeneralPath)shape).curveTo(29.067999, 15.500002, 27.500002, 13.932001, 27.5, 12.0);
((GeneralPath)shape).curveTo(27.499998, 10.067998, 29.067999, 8.500002, 31.0, 8.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_2;
g.setTransform(defaultTransform__0_0_0_2);
g.setClip(clip__0_0_0_2);
float alpha__0_0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(21.78813362121582, 21.87696075439453), new Point2D.Double(10.211396217346191, 3.423278570175171), new float[] {0.0f,1.0f}, new Color[] {new Color(211, 215, 207, 255),new Color(136, 138, 133, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9091060161590576f, -0.2435940057039261f, 0.2435940057039261f, 0.9091060161590576f, 8.67676067352295f, 1.4170730113983154f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 4.0);
((GeneralPath)shape).curveTo(26.584003, 4.0, 23.0, 7.584, 23.0, 12.0);
((GeneralPath)shape).curveTo(23.0, 16.416, 26.584002, 20.000002, 31.0, 20.0);
((GeneralPath)shape).curveTo(35.416, 20.0, 39.0, 16.416, 39.0, 12.0);
((GeneralPath)shape).curveTo(39.0, 7.584, 35.416, 4.0, 31.0, 4.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 9.0);
((GeneralPath)shape).curveTo(32.656, 8.999998, 33.999996, 10.344006, 34.0, 12.0);
((GeneralPath)shape).curveTo(34.000004, 13.655994, 32.656, 14.999998, 31.0, 15.0);
((GeneralPath)shape).curveTo(29.344, 15.000002, 28.000002, 13.655994, 28.0, 12.0);
((GeneralPath)shape).curveTo(27.999998, 10.344006, 29.344002, 9.000002, 31.0, 9.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_3;
g.setTransform(defaultTransform__0_0_0_3);
g.setClip(clip__0_0_0_3);
float alpha__0_0_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_0_4 = g.getTransform();
g.transform(new AffineTransform(0.8823530077934265f, -1.0264300271956017E-6f, 1.0264300271956017E-6f, 0.8823530077934265f, 10.264690399169922f, -4.764686107635498f));
// _0_0_0_4 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(16.48750114440918, 13.970829010009766), new Point2D.Double(32.566654205322266, 30.758346557617188), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659259915351868f, -0.25881901383399963f, 0.25881901383399963f, 0.9659259915351868f, -4.116819858551025f, 6.729656219482422f));
stroke = new BasicStroke(1.1333332f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.0, 19.0);
((GeneralPath)shape).curveTo(32.0, 23.69442, 28.19442, 27.5, 23.5, 27.5);
((GeneralPath)shape).curveTo(18.80558, 27.5, 15.0, 23.69442, 15.0, 19.0);
((GeneralPath)shape).curveTo(15.0, 14.30558, 18.80558, 10.5, 23.5, 10.5);
((GeneralPath)shape).curveTo(28.19442, 10.5, 32.0, 14.30558, 32.0, 19.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_4;
g.setTransform(defaultTransform__0_0_0_4);
g.setClip(clip__0_0_0_4);
float alpha__0_0_0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_5 = g.getClip();
AffineTransform defaultTransform__0_0_0_5 = g.getTransform();
g.transform(new AffineTransform(0.4117650091648102f, -4.790002776644542E-7f, 4.790002776644542E-7f, 0.4117650091648102f, 21.323530197143555f, 4.176469802856445f));
// _0_0_0_5 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(28.357093811035156, 22.794660568237305), new Point2D.Double(17.73212432861328, 5.187518119812012), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659259915351868f, -0.25881901383399963f, 0.25881901383399963f, 0.9659259915351868f, -4.116824150085449f, 6.729647159576416f));
stroke = new BasicStroke(2.4285712f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.0, 19.0);
((GeneralPath)shape).curveTo(32.0, 23.69442, 28.19442, 27.5, 23.5, 27.5);
((GeneralPath)shape).curveTo(18.80558, 27.5, 15.0, 23.69442, 15.0, 19.0);
((GeneralPath)shape).curveTo(15.0, 14.30558, 18.80558, 10.5, 23.5, 10.5);
((GeneralPath)shape).curveTo(28.19442, 10.5, 32.0, 14.30558, 32.0, 19.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_5;
g.setTransform(defaultTransform__0_0_0_5);
g.setClip(clip__0_0_0_5);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(0.9659259915351868f, 0.25881901383399963f, -0.25881901383399963f, 0.9659259915351868f, -6.837870121002197f, -6.614500045776367f));
// _0_0_1 is CompositeGraphicsNode
float alpha__0_0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_0 = g.getClip();
AffineTransform defaultTransform__0_0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(22.96537971496582, 35.31056594848633), new Point2D.Double(12.641190528869629, 11.423575401306152), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(211, 215, 207, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659259915351868f, -0.25881901383399963f, 0.25881901383399963f, 0.9659259915351868f, 7.281559944152832f, 0.7556419968605042f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 0.5);
((GeneralPath)shape).curveTo(30.438065, 0.5, 29.8842, 0.547001, 29.34375, 0.625);
((GeneralPath)shape).lineTo(29.125, 3.71875);
((GeneralPath)shape).curveTo(28.16536, 3.9344947, 27.275486, 4.3022814, 26.46875, 4.8125);
((GeneralPath)shape).lineTo(24.125, 2.78125);
((GeneralPath)shape).curveTo(22.999998, 3.500001, 22.5, 4.0, 21.78125, 5.125);
((GeneralPath)shape).lineTo(23.8125, 7.46875);
((GeneralPath)shape).curveTo(23.30228, 8.275486, 22.934496, 9.1653595, 22.71875, 10.125);
((GeneralPath)shape).lineTo(19.625, 10.34375);
((GeneralPath)shape).curveTo(19.547, 10.8842, 19.5, 11.438062, 19.5, 12.0);
((GeneralPath)shape).curveTo(19.499998, 12.561939, 19.547, 13.115801, 19.625, 13.65625);
((GeneralPath)shape).lineTo(22.71875, 13.875);
((GeneralPath)shape).curveTo(22.934496, 14.834641, 23.30228, 15.724514, 23.8125, 16.53125);
((GeneralPath)shape).lineTo(21.78125, 18.875);
((GeneralPath)shape).curveTo(22.5, 19.999998, 23.0, 20.5, 24.125, 21.21875);
((GeneralPath)shape).lineTo(26.46875, 19.1875);
((GeneralPath)shape).curveTo(27.275486, 19.69772, 28.16536, 20.065504, 29.125, 20.28125);
((GeneralPath)shape).lineTo(29.34375, 23.375);
((GeneralPath)shape).curveTo(29.8842, 23.453, 30.438063, 23.5, 31.0, 23.5);
((GeneralPath)shape).curveTo(31.561935, 23.5, 32.115803, 23.453, 32.65625, 23.375);
((GeneralPath)shape).lineTo(32.875, 20.28125);
((GeneralPath)shape).curveTo(33.83464, 20.065504, 34.724514, 19.69772, 35.53125, 19.1875);
((GeneralPath)shape).lineTo(37.875, 21.21875);
((GeneralPath)shape).curveTo(39.000004, 20.499998, 39.5, 20.0, 40.21875, 18.875);
((GeneralPath)shape).lineTo(38.1875, 16.53125);
((GeneralPath)shape).curveTo(38.69772, 15.724514, 39.065506, 14.834641, 39.28125, 13.875);
((GeneralPath)shape).lineTo(42.375, 13.65625);
((GeneralPath)shape).curveTo(42.453, 13.115801, 42.5, 12.561939, 42.5, 12.0);
((GeneralPath)shape).curveTo(42.5, 11.438063, 42.453, 10.884199, 42.375, 10.34375);
((GeneralPath)shape).lineTo(39.28125, 10.125);
((GeneralPath)shape).curveTo(39.065506, 9.1653595, 38.69772, 8.275486, 38.1875, 7.46875);
((GeneralPath)shape).lineTo(40.21875, 5.125);
((GeneralPath)shape).curveTo(39.56926, 4.0173154, 39.0, 3.5, 37.875, 2.78125);
((GeneralPath)shape).lineTo(35.53125, 4.8125);
((GeneralPath)shape).curveTo(34.724514, 4.3022814, 33.83464, 3.9344947, 32.875, 3.71875);
((GeneralPath)shape).lineTo(32.65625, 0.625);
((GeneralPath)shape).curveTo(32.1158, 0.547001, 31.561937, 0.5, 31.0, 0.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 9.5);
((GeneralPath)shape).curveTo(32.38, 9.5, 33.5, 10.619998, 33.5, 12.0);
((GeneralPath)shape).curveTo(33.5, 13.380003, 32.380005, 14.5, 31.0, 14.5);
((GeneralPath)shape).curveTo(29.619999, 14.5, 28.5, 13.380003, 28.5, 12.0);
((GeneralPath)shape).curveTo(28.5, 10.619998, 29.619999, 9.5, 31.0, 9.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0054358f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 0.5);
((GeneralPath)shape).curveTo(30.438065, 0.5, 29.8842, 0.547001, 29.34375, 0.625);
((GeneralPath)shape).lineTo(29.125, 3.71875);
((GeneralPath)shape).curveTo(28.16536, 3.9344947, 27.275486, 4.3022814, 26.46875, 4.8125);
((GeneralPath)shape).lineTo(24.125, 2.78125);
((GeneralPath)shape).curveTo(22.999998, 3.500001, 22.5, 4.0, 21.78125, 5.125);
((GeneralPath)shape).lineTo(23.8125, 7.46875);
((GeneralPath)shape).curveTo(23.30228, 8.275486, 22.934496, 9.1653595, 22.71875, 10.125);
((GeneralPath)shape).lineTo(19.625, 10.34375);
((GeneralPath)shape).curveTo(19.547, 10.8842, 19.5, 11.438062, 19.5, 12.0);
((GeneralPath)shape).curveTo(19.499998, 12.561939, 19.547, 13.115801, 19.625, 13.65625);
((GeneralPath)shape).lineTo(22.71875, 13.875);
((GeneralPath)shape).curveTo(22.934496, 14.834641, 23.30228, 15.724514, 23.8125, 16.53125);
((GeneralPath)shape).lineTo(21.78125, 18.875);
((GeneralPath)shape).curveTo(22.5, 19.999998, 23.0, 20.5, 24.125, 21.21875);
((GeneralPath)shape).lineTo(26.46875, 19.1875);
((GeneralPath)shape).curveTo(27.275486, 19.69772, 28.16536, 20.065504, 29.125, 20.28125);
((GeneralPath)shape).lineTo(29.34375, 23.375);
((GeneralPath)shape).curveTo(29.8842, 23.453, 30.438063, 23.5, 31.0, 23.5);
((GeneralPath)shape).curveTo(31.561935, 23.5, 32.115803, 23.453, 32.65625, 23.375);
((GeneralPath)shape).lineTo(32.875, 20.28125);
((GeneralPath)shape).curveTo(33.83464, 20.065504, 34.724514, 19.69772, 35.53125, 19.1875);
((GeneralPath)shape).lineTo(37.875, 21.21875);
((GeneralPath)shape).curveTo(39.000004, 20.499998, 39.5, 20.0, 40.21875, 18.875);
((GeneralPath)shape).lineTo(38.1875, 16.53125);
((GeneralPath)shape).curveTo(38.69772, 15.724514, 39.065506, 14.834641, 39.28125, 13.875);
((GeneralPath)shape).lineTo(42.375, 13.65625);
((GeneralPath)shape).curveTo(42.453, 13.115801, 42.5, 12.561939, 42.5, 12.0);
((GeneralPath)shape).curveTo(42.5, 11.438063, 42.453, 10.884199, 42.375, 10.34375);
((GeneralPath)shape).lineTo(39.28125, 10.125);
((GeneralPath)shape).curveTo(39.065506, 9.1653595, 38.69772, 8.275486, 38.1875, 7.46875);
((GeneralPath)shape).lineTo(40.21875, 5.125);
((GeneralPath)shape).curveTo(39.56926, 4.0173154, 39.0, 3.5, 37.875, 2.78125);
((GeneralPath)shape).lineTo(35.53125, 4.8125);
((GeneralPath)shape).curveTo(34.724514, 4.3022814, 33.83464, 3.9344947, 32.875, 3.71875);
((GeneralPath)shape).lineTo(32.65625, 0.625);
((GeneralPath)shape).curveTo(32.1158, 0.547001, 31.561937, 0.5, 31.0, 0.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 9.5);
((GeneralPath)shape).curveTo(32.38, 9.5, 33.5, 10.619998, 33.5, 12.0);
((GeneralPath)shape).curveTo(33.5, 13.380003, 32.380005, 14.5, 31.0, 14.5);
((GeneralPath)shape).curveTo(29.619999, 14.5, 28.5, 13.380003, 28.5, 12.0);
((GeneralPath)shape).curveTo(28.5, 10.619998, 29.619999, 9.5, 31.0, 9.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1_0;
g.setTransform(defaultTransform__0_0_1_0);
g.setClip(clip__0_0_1_0);
float alpha__0_0_1_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_1 = g.getClip();
AffineTransform defaultTransform__0_0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(17.413135528564453, 7.870833396911621), new Point2D.Double(17.027729034423828, 52.50489044189453), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659259915351868f, -0.25881901383399963f, 0.25881901383399963f, 0.9659259915351868f, 7.281559944152832f, 0.7556419968605042f));
stroke = new BasicStroke(1.1333336f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.000008, 1.437503);
((GeneralPath)shape).curveTo(30.739935, 1.437503, 30.477873, 1.47946, 30.218758, 1.500003);
((GeneralPath)shape).lineTo(30.000008, 4.562504);
((GeneralPath)shape).curveTo(28.682978, 4.737208, 27.478296, 5.264549, 26.468756, 6.031255);
((GeneralPath)shape).lineTo(24.156256, 4.062504);
((GeneralPath)shape).curveTo(23.765211, 4.40014, 23.400139, 4.765213, 23.062504, 5.156255);
((GeneralPath)shape).lineTo(25.031256, 7.468756);
((GeneralPath)shape).curveTo(24.26455, 8.478295, 23.737207, 9.682977, 23.562504, 11.000006);
((GeneralPath)shape).lineTo(20.500004, 11.218757);
((GeneralPath)shape).curveTo(20.47946, 11.477873, 20.437504, 11.739933, 20.437504, 12.000007);
((GeneralPath)shape).curveTo(20.437504, 12.26008, 20.47946, 12.52214, 20.500004, 12.781257);
((GeneralPath)shape).lineTo(23.562504, 13.000007);
((GeneralPath)shape).curveTo(23.737207, 14.317036, 24.26455, 15.521718, 25.031256, 16.531258);
((GeneralPath)shape).lineTo(23.062504, 18.84376);
((GeneralPath)shape).curveTo(23.400139, 19.2348, 23.765211, 19.599874, 24.156256, 19.93751);
((GeneralPath)shape).lineTo(26.468756, 17.968758);
((GeneralPath)shape).curveTo(27.478294, 18.735464, 28.682978, 19.262806, 30.000006, 19.43751);
((GeneralPath)shape).lineTo(30.218758, 22.50001);
((GeneralPath)shape).curveTo(30.477873, 22.520554, 30.739935, 22.56251, 31.000008, 22.56251);
((GeneralPath)shape).curveTo(31.260078, 22.56251, 31.52214, 22.520554, 31.781258, 22.50001);
((GeneralPath)shape).lineTo(32.000008, 19.43751);
((GeneralPath)shape).curveTo(33.317036, 19.262806, 34.521717, 18.735464, 35.531258, 17.968758);
((GeneralPath)shape).lineTo(37.843758, 19.93751);
((GeneralPath)shape).curveTo(38.234802, 19.599874, 38.599873, 19.2348, 38.937508, 18.843758);
((GeneralPath)shape).lineTo(36.968758, 16.531258);
((GeneralPath)shape).curveTo(37.735462, 15.521718, 38.262806, 14.317036, 38.437508, 13.000007);
((GeneralPath)shape).lineTo(41.50001, 12.781257);
((GeneralPath)shape).curveTo(41.520554, 12.522141, 41.56251, 12.26008, 41.56251, 12.000006);
((GeneralPath)shape).curveTo(41.56251, 11.739934, 41.520554, 11.477873, 41.50001, 11.218757);
((GeneralPath)shape).lineTo(38.437508, 11.000007);
((GeneralPath)shape).curveTo(38.262806, 9.682977, 37.735462, 8.478296, 36.968758, 7.468755);
((GeneralPath)shape).lineTo(38.937508, 5.156254);
((GeneralPath)shape).curveTo(38.599876, 4.765213, 38.234802, 4.400139, 37.843758, 4.062504);
((GeneralPath)shape).lineTo(35.531258, 6.031255);
((GeneralPath)shape).curveTo(34.52172, 5.264549, 33.317036, 4.737207, 32.000008, 4.562504);
((GeneralPath)shape).lineTo(31.781258, 1.500003);
((GeneralPath)shape).curveTo(31.52214, 1.47946, 31.26008, 1.437504, 31.000008, 1.437503);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1_1;
g.setTransform(defaultTransform__0_0_1_1);
g.setClip(clip__0_0_1_1);
float alpha__0_0_1_2 = origAlpha;
origAlpha = origAlpha * 0.8f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_2 = g.getClip();
AffineTransform defaultTransform__0_0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_2 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(31.0, 12.0), 11.125f, new Point2D.Double(31.0, 12.0), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9774659872055054f, -0.261911004781723f, 0.261911004781723f, 0.9774659872055054f, -2.4443750381469727f, 8.389657020568848f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 0.875);
((GeneralPath)shape).curveTo(30.67898, 0.87500006, 30.416025, 0.91938233, 30.1875, 0.9375);
((GeneralPath)shape).lineTo(29.6875, 0.96875);
((GeneralPath)shape).lineTo(29.65625, 1.46875);
((GeneralPath)shape).lineTo(29.46875, 4.125);
((GeneralPath)shape).curveTo(28.408657, 4.334056, 27.427074, 4.7655263, 26.5625, 5.34375);
((GeneralPath)shape).lineTo(24.15625, 3.3125);
((GeneralPath)shape).lineTo(22.3125, 5.15625);
((GeneralPath)shape).lineTo(24.34375, 7.5625);
((GeneralPath)shape).curveTo(23.765526, 8.427073, 23.334055, 9.408658, 23.125, 10.46875);
((GeneralPath)shape).lineTo(20.46875, 10.65625);
((GeneralPath)shape).lineTo(19.96875, 10.6875);
((GeneralPath)shape).lineTo(19.9375, 11.1875);
((GeneralPath)shape).curveTo(19.91938, 11.416028, 19.875, 11.678982, 19.875, 12.0);
((GeneralPath)shape).curveTo(19.875, 12.321017, 19.919382, 12.583973, 19.9375, 12.8125);
((GeneralPath)shape).lineTo(19.96875, 13.3125);
((GeneralPath)shape).lineTo(20.46875, 13.34375);
((GeneralPath)shape).lineTo(23.125, 13.53125);
((GeneralPath)shape).curveTo(23.334057, 14.591342, 23.765526, 15.572926, 24.34375, 16.4375);
((GeneralPath)shape).lineTo(22.625, 18.46875);
((GeneralPath)shape).lineTo(22.3125, 18.84375);
((GeneralPath)shape).lineTo(22.625, 19.21875);
((GeneralPath)shape).curveTo(22.98067, 19.630678, 23.36932, 20.01933, 23.78125, 20.375);
((GeneralPath)shape).lineTo(24.15625, 20.6875);
((GeneralPath)shape).lineTo(24.53125, 20.375);
((GeneralPath)shape).lineTo(26.5625, 18.65625);
((GeneralPath)shape).curveTo(27.427073, 19.234474, 28.408657, 19.665945, 29.46875, 19.875);
((GeneralPath)shape).lineTo(29.65625, 22.53125);
((GeneralPath)shape).lineTo(29.6875, 23.03125);
((GeneralPath)shape).lineTo(30.1875, 23.0625);
((GeneralPath)shape).curveTo(30.416027, 23.08062, 30.678982, 23.125, 31.0, 23.125);
((GeneralPath)shape).curveTo(31.321018, 23.125002, 31.583973, 23.08062, 31.8125, 23.0625);
((GeneralPath)shape).lineTo(32.3125, 23.03125);
((GeneralPath)shape).lineTo(32.34375, 22.53125);
((GeneralPath)shape).lineTo(32.53125, 19.875);
((GeneralPath)shape).curveTo(33.59134, 19.665943, 34.572926, 19.234474, 35.4375, 18.65625);
((GeneralPath)shape).lineTo(37.46875, 20.375);
((GeneralPath)shape).lineTo(37.84375, 20.6875);
((GeneralPath)shape).lineTo(38.21875, 20.375);
((GeneralPath)shape).curveTo(38.630684, 20.01933, 39.01933, 19.63068, 39.375, 19.21875);
((GeneralPath)shape).lineTo(39.6875, 18.84375);
((GeneralPath)shape).lineTo(39.375, 18.46875);
((GeneralPath)shape).lineTo(37.65625, 16.4375);
((GeneralPath)shape).curveTo(38.234474, 15.572927, 38.665943, 14.591342, 38.875, 13.53125);
((GeneralPath)shape).lineTo(41.53125, 13.34375);
((GeneralPath)shape).lineTo(42.03125, 13.3125);
((GeneralPath)shape).lineTo(42.0625, 12.8125);
((GeneralPath)shape).curveTo(42.080616, 12.583975, 42.125, 12.321018, 42.125, 12.0);
((GeneralPath)shape).curveTo(42.125, 11.678985, 42.08062, 11.416028, 42.0625, 11.1875);
((GeneralPath)shape).lineTo(42.03125, 10.6875);
((GeneralPath)shape).lineTo(41.53125, 10.65625);
((GeneralPath)shape).lineTo(38.875, 10.46875);
((GeneralPath)shape).curveTo(38.665943, 9.408658, 38.234474, 8.4270735, 37.65625, 7.5625);
((GeneralPath)shape).lineTo(39.375, 5.53125);
((GeneralPath)shape).lineTo(39.6875, 5.15625);
((GeneralPath)shape).lineTo(39.375, 4.78125);
((GeneralPath)shape).curveTo(39.019333, 4.3693213, 38.63068, 3.9806705, 38.21875, 3.625);
((GeneralPath)shape).lineTo(37.84375, 3.3125);
((GeneralPath)shape).lineTo(37.46875, 3.625);
((GeneralPath)shape).lineTo(35.4375, 5.34375);
((GeneralPath)shape).curveTo(34.572926, 4.7655263, 33.591343, 4.3340554, 32.53125, 4.125);
((GeneralPath)shape).lineTo(32.34375, 1.46875);
((GeneralPath)shape).lineTo(32.3125, 0.96875);
((GeneralPath)shape).lineTo(31.8125, 0.9375);
((GeneralPath)shape).curveTo(31.583973, 0.9193822, 31.321016, 0.87500125, 31.0, 0.875);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 8.5);
((GeneralPath)shape).curveTo(32.932, 8.499998, 34.499996, 10.067999, 34.5, 12.0);
((GeneralPath)shape).curveTo(34.500004, 13.932002, 32.932, 15.499998, 31.0, 15.5);
((GeneralPath)shape).curveTo(29.067999, 15.500002, 27.500002, 13.932001, 27.5, 12.0);
((GeneralPath)shape).curveTo(27.499998, 10.067998, 29.067999, 8.500002, 31.0, 8.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1_2;
g.setTransform(defaultTransform__0_0_1_2);
g.setClip(clip__0_0_1_2);
float alpha__0_0_1_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_3 = g.getClip();
AffineTransform defaultTransform__0_0_1_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1_3 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(21.78813362121582, 21.87696075439453), new Point2D.Double(10.211396217346191, 3.423278570175171), new float[] {0.0f,1.0f}, new Color[] {new Color(211, 215, 207, 255),new Color(136, 138, 133, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9091060161590576f, -0.2435940057039261f, 0.2435940057039261f, 0.9091060161590576f, 8.67676067352295f, 1.4170730113983154f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 4.0);
((GeneralPath)shape).curveTo(26.584003, 4.0, 23.0, 7.584, 23.0, 12.0);
((GeneralPath)shape).curveTo(23.0, 16.416, 26.584002, 20.000002, 31.0, 20.0);
((GeneralPath)shape).curveTo(35.416, 20.0, 39.0, 16.416, 39.0, 12.0);
((GeneralPath)shape).curveTo(39.0, 7.584, 35.416, 4.0, 31.0, 4.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 9.0);
((GeneralPath)shape).curveTo(32.656, 8.999998, 33.999996, 10.344006, 34.0, 12.0);
((GeneralPath)shape).curveTo(34.000004, 13.655994, 32.656, 14.999998, 31.0, 15.0);
((GeneralPath)shape).curveTo(29.344, 15.000002, 28.000002, 13.655994, 28.0, 12.0);
((GeneralPath)shape).curveTo(27.999998, 10.344006, 29.344002, 9.000002, 31.0, 9.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1_3;
g.setTransform(defaultTransform__0_0_1_3);
g.setClip(clip__0_0_1_3);
float alpha__0_0_1_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_4 = g.getClip();
AffineTransform defaultTransform__0_0_1_4 = g.getTransform();
g.transform(new AffineTransform(0.8823530077934265f, -1.0264300271956017E-6f, 1.0264300271956017E-6f, 0.8823530077934265f, 10.264690399169922f, -4.764686107635498f));
// _0_0_1_4 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(16.48750114440918, 13.970829010009766), new Point2D.Double(32.566654205322266, 30.758346557617188), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659259915351868f, -0.25881901383399963f, 0.25881901383399963f, 0.9659259915351868f, -4.116819858551025f, 6.729656219482422f));
stroke = new BasicStroke(1.1333332f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.0, 19.0);
((GeneralPath)shape).curveTo(32.0, 23.69442, 28.19442, 27.5, 23.5, 27.5);
((GeneralPath)shape).curveTo(18.80558, 27.5, 15.0, 23.69442, 15.0, 19.0);
((GeneralPath)shape).curveTo(15.0, 14.30558, 18.80558, 10.5, 23.5, 10.5);
((GeneralPath)shape).curveTo(28.19442, 10.5, 32.0, 14.30558, 32.0, 19.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1_4;
g.setTransform(defaultTransform__0_0_1_4);
g.setClip(clip__0_0_1_4);
float alpha__0_0_1_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1_5 = g.getClip();
AffineTransform defaultTransform__0_0_1_5 = g.getTransform();
g.transform(new AffineTransform(0.4117650091648102f, -4.790002776644542E-7f, 4.790002776644542E-7f, 0.4117650091648102f, 21.323530197143555f, 4.176469802856445f));
// _0_0_1_5 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(28.357093811035156, 22.794660568237305), new Point2D.Double(17.73212432861328, 5.187518119812012), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659259915351868f, -0.25881901383399963f, 0.25881901383399963f, 0.9659259915351868f, -4.116824150085449f, 6.729647159576416f));
stroke = new BasicStroke(2.4285712f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.0, 19.0);
((GeneralPath)shape).curveTo(32.0, 23.69442, 28.19442, 27.5, 23.5, 27.5);
((GeneralPath)shape).curveTo(18.80558, 27.5, 15.0, 23.69442, 15.0, 19.0);
((GeneralPath)shape).curveTo(15.0, 14.30558, 18.80558, 10.5, 23.5, 10.5);
((GeneralPath)shape).curveTo(28.19442, 10.5, 32.0, 14.30558, 32.0, 19.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1_5;
g.setTransform(defaultTransform__0_0_1_5);
g.setClip(clip__0_0_1_5);
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
paint = new LinearGradientPaint(new Point2D.Double(14.495688438415527, 29.880390167236328), new Point2D.Double(26.737476348876953, 42.564117431640625), new float[] {0.0f,1.0f}, new Color[] {new Color(211, 215, 207, 255),new Color(136, 138, 133, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -3.0f, 1.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.15625, 26.5);
((GeneralPath)shape).curveTo(15.667854, 26.518776, 15.184245, 26.593794, 14.71875, 26.6875);
((GeneralPath)shape).lineTo(14.6875, 26.6875);
((GeneralPath)shape).curveTo(14.33804, 26.75907, 13.989707, 26.857916, 13.65625, 26.96875);
((GeneralPath)shape).curveTo(13.173912, 27.12907, 12.723532, 27.3266, 12.28125, 27.5625);
((GeneralPath)shape).lineTo(12.9375, 30.6875);
((GeneralPath)shape).curveTo(12.461256, 31.040215, 12.013429, 31.452682, 11.65625, 31.9375);
((GeneralPath)shape).lineTo(8.5625, 31.3125);
((GeneralPath)shape).curveTo(8.306103, 31.796885, 8.071723, 32.280857, 7.90625, 32.8125);
((GeneralPath)shape).curveTo(7.676383, 33.551033, 7.530997, 34.34993, 7.5, 35.15625);
((GeneralPath)shape).lineTo(10.5625, 36.1875);
((GeneralPath)shape).curveTo(10.632137, 36.77997, 10.764473, 37.36819, 11.0, 37.90625);
((GeneralPath)shape).lineTo(8.875, 40.28125);
((GeneralPath)shape).curveTo(9.152689, 40.72257, 9.464725, 41.145897, 9.8125, 41.53125);
((GeneralPath)shape).lineTo(10.4375, 42.125);
((GeneralPath)shape).curveTo(10.825721, 42.4804, 11.241422, 42.810104, 11.6875, 43.09375);
((GeneralPath)shape).lineTo(14.0625, 40.96875);
((GeneralPath)shape).curveTo(14.347986, 41.096024, 14.626055, 41.1978, 14.9375, 41.28125);
((GeneralPath)shape).curveTo(15.232216, 41.360218, 15.549433, 41.43545, 15.84375, 41.46875);
((GeneralPath)shape).lineTo(16.84375, 44.5);
((GeneralPath)shape).curveTo(17.332146, 44.481224, 17.815756, 44.406208, 18.28125, 44.3125);
((GeneralPath)shape).lineTo(18.3125, 44.3125);
((GeneralPath)shape).curveTo(18.66196, 44.24093, 19.010292, 44.142086, 19.34375, 44.03125);
((GeneralPath)shape).curveTo(19.826088, 43.87093, 20.276468, 43.6734, 20.71875, 43.4375);
((GeneralPath)shape).lineTo(20.0625, 40.3125);
((GeneralPath)shape).curveTo(20.538744, 39.959785, 20.98657, 39.547318, 21.34375, 39.0625);
((GeneralPath)shape).lineTo(24.4375, 39.6875);
((GeneralPath)shape).curveTo(24.693897, 39.203114, 24.928276, 38.719143, 25.09375, 38.1875);
((GeneralPath)shape).curveTo(25.323618, 37.448967, 25.469004, 36.65007, 25.5, 35.84375);
((GeneralPath)shape).lineTo(22.4375, 34.8125);
((GeneralPath)shape).curveTo(22.367863, 34.22003, 22.235527, 33.63181, 22.0, 33.09375);
((GeneralPath)shape).lineTo(24.125, 30.71875);
((GeneralPath)shape).curveTo(23.847311, 30.277433, 23.535275, 29.854103, 23.1875, 29.46875);
((GeneralPath)shape).lineTo(22.5625, 28.875);
((GeneralPath)shape).curveTo(22.174278, 28.5196, 21.758577, 28.189896, 21.3125, 27.90625);
((GeneralPath)shape).lineTo(18.9375, 30.03125);
((GeneralPath)shape).curveTo(18.652014, 29.903976, 18.373945, 29.8022, 18.0625, 29.71875);
((GeneralPath)shape).curveTo(17.767784, 29.639782, 17.450567, 29.564548, 17.15625, 29.53125);
((GeneralPath)shape).lineTo(16.15625, 26.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(16.28125, 33.5);
((GeneralPath)shape).curveTo(16.394758, 33.487328, 16.508957, 33.493053, 16.625, 33.5);
((GeneralPath)shape).curveTo(16.75762, 33.50794, 16.897951, 33.526783, 17.03125, 33.5625);
((GeneralPath)shape).curveTo(18.097631, 33.848232, 18.723236, 34.964867, 18.4375, 36.03125);
((GeneralPath)shape).curveTo(18.151764, 37.097637, 17.035131, 37.723236, 15.96875, 37.4375);
((GeneralPath)shape).curveTo(14.902369, 37.151768, 14.276764, 36.035133, 14.5625, 34.96875);
((GeneralPath)shape).curveTo(14.781268, 34.1523, 15.486686, 33.588696, 16.28125, 33.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0000001f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.15625, 26.5);
((GeneralPath)shape).curveTo(15.667854, 26.518776, 15.184245, 26.593794, 14.71875, 26.6875);
((GeneralPath)shape).lineTo(14.6875, 26.6875);
((GeneralPath)shape).curveTo(14.33804, 26.75907, 13.989707, 26.857916, 13.65625, 26.96875);
((GeneralPath)shape).curveTo(13.173912, 27.12907, 12.723532, 27.3266, 12.28125, 27.5625);
((GeneralPath)shape).lineTo(12.9375, 30.6875);
((GeneralPath)shape).curveTo(12.461256, 31.040215, 12.013429, 31.452682, 11.65625, 31.9375);
((GeneralPath)shape).lineTo(8.5625, 31.3125);
((GeneralPath)shape).curveTo(8.306103, 31.796885, 8.071723, 32.280857, 7.90625, 32.8125);
((GeneralPath)shape).curveTo(7.676383, 33.551033, 7.530997, 34.34993, 7.5, 35.15625);
((GeneralPath)shape).lineTo(10.5625, 36.1875);
((GeneralPath)shape).curveTo(10.632137, 36.77997, 10.764473, 37.36819, 11.0, 37.90625);
((GeneralPath)shape).lineTo(8.875, 40.28125);
((GeneralPath)shape).curveTo(9.152689, 40.72257, 9.464725, 41.145897, 9.8125, 41.53125);
((GeneralPath)shape).lineTo(10.4375, 42.125);
((GeneralPath)shape).curveTo(10.825721, 42.4804, 11.241422, 42.810104, 11.6875, 43.09375);
((GeneralPath)shape).lineTo(14.0625, 40.96875);
((GeneralPath)shape).curveTo(14.347986, 41.096024, 14.626055, 41.1978, 14.9375, 41.28125);
((GeneralPath)shape).curveTo(15.232216, 41.360218, 15.549433, 41.43545, 15.84375, 41.46875);
((GeneralPath)shape).lineTo(16.84375, 44.5);
((GeneralPath)shape).curveTo(17.332146, 44.481224, 17.815756, 44.406208, 18.28125, 44.3125);
((GeneralPath)shape).lineTo(18.3125, 44.3125);
((GeneralPath)shape).curveTo(18.66196, 44.24093, 19.010292, 44.142086, 19.34375, 44.03125);
((GeneralPath)shape).curveTo(19.826088, 43.87093, 20.276468, 43.6734, 20.71875, 43.4375);
((GeneralPath)shape).lineTo(20.0625, 40.3125);
((GeneralPath)shape).curveTo(20.538744, 39.959785, 20.98657, 39.547318, 21.34375, 39.0625);
((GeneralPath)shape).lineTo(24.4375, 39.6875);
((GeneralPath)shape).curveTo(24.693897, 39.203114, 24.928276, 38.719143, 25.09375, 38.1875);
((GeneralPath)shape).curveTo(25.323618, 37.448967, 25.469004, 36.65007, 25.5, 35.84375);
((GeneralPath)shape).lineTo(22.4375, 34.8125);
((GeneralPath)shape).curveTo(22.367863, 34.22003, 22.235527, 33.63181, 22.0, 33.09375);
((GeneralPath)shape).lineTo(24.125, 30.71875);
((GeneralPath)shape).curveTo(23.847311, 30.277433, 23.535275, 29.854103, 23.1875, 29.46875);
((GeneralPath)shape).lineTo(22.5625, 28.875);
((GeneralPath)shape).curveTo(22.174278, 28.5196, 21.758577, 28.189896, 21.3125, 27.90625);
((GeneralPath)shape).lineTo(18.9375, 30.03125);
((GeneralPath)shape).curveTo(18.652014, 29.903976, 18.373945, 29.8022, 18.0625, 29.71875);
((GeneralPath)shape).curveTo(17.767784, 29.639782, 17.450567, 29.564548, 17.15625, 29.53125);
((GeneralPath)shape).lineTo(16.15625, 26.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(16.28125, 33.5);
((GeneralPath)shape).curveTo(16.394758, 33.487328, 16.508957, 33.493053, 16.625, 33.5);
((GeneralPath)shape).curveTo(16.75762, 33.50794, 16.897951, 33.526783, 17.03125, 33.5625);
((GeneralPath)shape).curveTo(18.097631, 33.848232, 18.723236, 34.964867, 18.4375, 36.03125);
((GeneralPath)shape).curveTo(18.151764, 37.097637, 17.035131, 37.723236, 15.96875, 37.4375);
((GeneralPath)shape).curveTo(14.902369, 37.151768, 14.276764, 36.035133, 14.5625, 34.96875);
((GeneralPath)shape).curveTo(14.781268, 34.1523, 15.486686, 33.588696, 16.28125, 33.5);
((GeneralPath)shape).closePath();
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -3.0f, 1.0f));
// _0_0_3 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(14.375, 31.0625), new Point2D.Double(30.4375, 44.0625), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0000001f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.46875, 26.46875);
((GeneralPath)shape).lineTo(16.375, 27.0625);
((GeneralPath)shape).lineTo(17.21875, 31.09375);
((GeneralPath)shape).curveTo(17.274872, 31.40599, 17.174576, 31.725925, 16.95025, 31.95025);
((GeneralPath)shape).curveTo(16.725925, 32.174576, 16.40599, 32.27487, 16.09375, 32.21875);
((GeneralPath)shape).lineTo(12.03125, 31.375);
((GeneralPath)shape).lineTo(11.4375, 33.46875);
((GeneralPath)shape).lineTo(15.40625, 34.78125);
((GeneralPath)shape).curveTo(15.71319, 34.88695, 15.946796, 35.139347, 16.028484, 35.453533);
((GeneralPath)shape).curveTo(16.110172, 35.767715, 16.029085, 36.101933, 15.8125, 36.34375);
((GeneralPath)shape).lineTo(13.09375, 39.40625);
((GeneralPath)shape).lineTo(14.5625, 40.90625);
((GeneralPath)shape).lineTo(17.65625, 38.15625);
((GeneralPath)shape).curveTo(17.898067, 37.939667, 18.232285, 37.858578, 18.546469, 37.940266);
((GeneralPath)shape).curveTo(18.860653, 38.021954, 19.113049, 38.25556, 19.21875, 38.5625);
((GeneralPath)shape).lineTo(20.53125, 42.53125);
((GeneralPath)shape).lineTo(22.625, 41.9375);
((GeneralPath)shape).lineTo(21.78125, 37.90625);
((GeneralPath)shape).curveTo(21.725128, 37.59401, 21.825424, 37.274075, 22.04975, 37.049747);
((GeneralPath)shape).curveTo(22.274075, 36.825424, 22.59401, 36.72513, 22.90625, 36.78125);
((GeneralPath)shape).lineTo(26.96875, 37.625);
((GeneralPath)shape).lineTo(27.5625, 35.53125);
((GeneralPath)shape).lineTo(23.59375, 34.21875);
((GeneralPath)shape).curveTo(23.28681, 34.11305, 23.053204, 33.860653, 22.971516, 33.546467);
((GeneralPath)shape).curveTo(22.889828, 33.232285, 22.970915, 32.898067, 23.1875, 32.65625);
((GeneralPath)shape).lineTo(25.90625, 29.59375);
((GeneralPath)shape).lineTo(24.4375, 28.09375);
((GeneralPath)shape).lineTo(21.34375, 30.84375);
((GeneralPath)shape).curveTo(21.101933, 31.060335, 20.767715, 31.141422, 20.453531, 31.059734);
((GeneralPath)shape).curveTo(20.139347, 30.978046, 19.886951, 30.74444, 19.78125, 30.4375);
((GeneralPath)shape).lineTo(18.46875, 26.46875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_3;
g.setTransform(defaultTransform__0_0_3);
g.setClip(clip__0_0_3);
float alpha__0_0_4 = origAlpha;
origAlpha = origAlpha * 0.9372549f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(42.5, 34.5), 8.6875f, new Point2D.Double(42.5, 34.5), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.9964029788970947f, -26.0f, 1.1240999698638916f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(15.78125, 26.84375);
((GeneralPath)shape).lineTo(12.78125, 27.71875);
((GeneralPath)shape).lineTo(13.71875, 32.1875);
((GeneralPath)shape).curveTo(13.746003, 32.339127, 13.702685, 32.484814, 13.59375, 32.59375);
((GeneralPath)shape).curveTo(13.484816, 32.702686, 13.339128, 32.746002, 13.1875, 32.71875);
((GeneralPath)shape).lineTo(8.6875, 31.78125);
((GeneralPath)shape).lineTo(7.8125, 34.78125);
((GeneralPath)shape).lineTo(12.25, 36.25);
((GeneralPath)shape).curveTo(12.3991, 36.301346, 12.491569, 36.44113, 12.53125, 36.59375);
((GeneralPath)shape).curveTo(12.57093, 36.74637, 12.542708, 36.882534, 12.4375, 37.0);
((GeneralPath)shape).lineTo(9.40625, 40.4375);
((GeneralPath)shape).lineTo(11.53125, 42.59375);
((GeneralPath)shape).lineTo(15.0, 39.53125);
((GeneralPath)shape).curveTo(15.117466, 39.42604, 15.253631, 39.39782, 15.40625, 39.4375);
((GeneralPath)shape).curveTo(15.558869, 39.47718, 15.698654, 39.56965, 15.75, 39.71875);
((GeneralPath)shape).lineTo(17.21875, 44.15625);
((GeneralPath)shape).lineTo(20.21875, 43.28125);
((GeneralPath)shape).lineTo(19.28125, 38.8125);
((GeneralPath)shape).curveTo(19.253998, 38.660873, 19.297316, 38.515186, 19.40625, 38.40625);
((GeneralPath)shape).curveTo(19.515184, 38.297314, 19.660873, 38.253998, 19.8125, 38.28125);
((GeneralPath)shape).lineTo(24.3125, 39.21875);
((GeneralPath)shape).lineTo(25.1875, 36.21875);
((GeneralPath)shape).lineTo(20.75, 34.75);
((GeneralPath)shape).curveTo(20.6009, 34.698654, 20.50843, 34.55887, 20.46875, 34.40625);
((GeneralPath)shape).curveTo(20.42907, 34.25363, 20.457293, 34.117466, 20.5625, 34.0);
((GeneralPath)shape).lineTo(23.59375, 30.5625);
((GeneralPath)shape).lineTo(21.46875, 28.40625);
((GeneralPath)shape).lineTo(18.0, 31.46875);
((GeneralPath)shape).curveTo(17.882534, 31.57396, 17.746368, 31.60218, 17.59375, 31.5625);
((GeneralPath)shape).curveTo(17.441132, 31.52282, 17.301346, 31.43035, 17.25, 31.28125);
((GeneralPath)shape).lineTo(15.78125, 26.84375);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(16.5, 32.5);
((GeneralPath)shape).curveTo(18.155998, 32.5, 19.5, 33.844, 19.5, 35.5);
((GeneralPath)shape).curveTo(19.500002, 37.156002, 18.155998, 38.5, 16.5, 38.5);
((GeneralPath)shape).curveTo(14.844002, 38.5, 13.5, 37.156, 13.5, 35.5);
((GeneralPath)shape).curveTo(13.5, 33.844, 14.844002, 32.5, 16.5, 32.5);
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
paint = new LinearGradientPaint(new Point2D.Double(16.793787002563477, 30.675884246826172), new Point2D.Double(22.052892684936523, 37.12823486328125), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(211, 215, 207, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -3.0f, 1.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.5, 30.0);
((GeneralPath)shape).curveTo(13.464, 30.0, 11.0, 32.464, 11.0, 35.5);
((GeneralPath)shape).curveTo(11.0, 38.536, 13.464, 40.999996, 16.5, 41.0);
((GeneralPath)shape).curveTo(19.536, 41.0, 22.0, 38.536003, 22.0, 35.5);
((GeneralPath)shape).curveTo(22.0, 32.464, 19.536, 30.0, 16.5, 30.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(16.5, 33.0);
((GeneralPath)shape).curveTo(17.879997, 33.0, 18.999998, 34.120003, 19.0, 35.5);
((GeneralPath)shape).curveTo(19.0, 36.879997, 17.879997, 38.0, 16.5, 38.0);
((GeneralPath)shape).curveTo(15.120003, 38.0, 14.000001, 36.879997, 14.0, 35.5);
((GeneralPath)shape).curveTo(14.0, 34.120003, 15.120003, 33.0, 16.5, 33.0);
((GeneralPath)shape).closePath();
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
g.transform(new AffineTransform(0.9494010210037231f, 0.0f, 0.0f, 0.9494019746780396f, -2.0664169788360596f, 2.6629860401153564f));
// _0_0_6 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(20.806716918945312, 36.82530212402344), new Point2D.Double(17.4493350982666, 30.900535583496094), new float[] {0.0f,1.0f}, new Color[] {new Color(238, 238, 236, 255),new Color(238, 238, 236, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0532955f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.715805, 34.587067);
((GeneralPath)shape).curveTo(22.715805, 36.332222, 21.301077, 37.74695, 19.555922, 37.74695);
((GeneralPath)shape).curveTo(17.810766, 37.74695, 16.396038, 36.332222, 16.396038, 34.587067);
((GeneralPath)shape).curveTo(16.396038, 32.84191, 17.810766, 31.427183, 19.555922, 31.427183);
((GeneralPath)shape).curveTo(21.301077, 31.427183, 22.715805, 32.84191, 22.715805, 34.587067);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_6;
g.setTransform(defaultTransform__0_0_6);
g.setClip(clip__0_0_6);
float alpha__0_0_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.582334041595459f, 0.0f, 0.0f, -1.5823390483856201f, -14.444009780883789f, 90.22846984863281f));
// _0_0_7 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(17.65999412536621, 36.68048858642578), new Point2D.Double(23.03179359436035, 31.111194610595703), new float[] {0.0f,1.0f}, new Color[] {new Color(238, 238, 236, 255),new Color(238, 238, 236, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.631977f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.715805, 34.587067);
((GeneralPath)shape).curveTo(22.715805, 36.332222, 21.301077, 37.74695, 19.555922, 37.74695);
((GeneralPath)shape).curveTo(17.810766, 37.74695, 16.396038, 36.332222, 16.396038, 34.587067);
((GeneralPath)shape).curveTo(16.396038, 32.84191, 17.810766, 31.427183, 19.555922, 31.427183);
((GeneralPath)shape).curveTo(21.301077, 31.427183, 22.715805, 32.84191, 22.715805, 34.587067);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_7;
g.setTransform(defaultTransform__0_0_7);
g.setClip(clip__0_0_7);
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
        return 6;
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
	public ProgressIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public ProgressIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public ProgressIcon(int width, int height) {
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

