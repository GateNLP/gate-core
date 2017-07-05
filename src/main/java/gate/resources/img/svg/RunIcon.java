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
public class RunIcon implements
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
origAlpha = origAlpha * 0.4902f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.388200044631958f, 0.0f, 0.0f, 0.4983600080013275f, -4.756199836730957f, 25.479000091552734f));
// _0_0_0 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(15.312000274658203, 34.65599822998047), 9.5f, new Point2D.Double(15.312000274658203, 34.65599822998047), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0032999515533447f, 0.0f, -0.11400000005960464f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(24.812, 34.656);
((GeneralPath)shape).curveTo(24.812, 39.919933, 20.558706, 44.1872, 15.312, 44.1872);
((GeneralPath)shape).curveTo(10.065295, 44.1872, 5.8120003, 39.919933, 5.8120003, 34.656);
((GeneralPath)shape).curveTo(5.8120003, 29.392061, 10.065295, 25.124798, 15.312, 25.124798);
((GeneralPath)shape).curveTo(20.558706, 25.124798, 24.812, 29.392061, 24.812, 34.656);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 0.24706f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.4737000465393066f, 0.0f, 0.0f, 0.6557400226593018f, 10.434000015258789f, 15.524999618530273f));
// _0_0_1 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(15.312000274658203, 34.65599822998047), 9.5f, new Point2D.Double(15.312000274658203, 34.65599822998047), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0032999515533447f, 0.0f, -0.11400000005960464f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(24.812, 34.656);
((GeneralPath)shape).curveTo(24.812, 39.919933, 20.558706, 44.1872, 15.312, 44.1872);
((GeneralPath)shape).curveTo(10.065295, 44.1872, 5.8120003, 39.919933, 5.8120003, 34.656);
((GeneralPath)shape).curveTo(5.8120003, 29.392061, 10.065295, 25.124798, 15.312, 25.124798);
((GeneralPath)shape).curveTo(20.558706, 25.124798, 24.812, 29.392061, 24.812, 34.656);
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
g.transform(new AffineTransform(0.9659299850463867f, 0.2588199973106384f, -0.2588199973106384f, 0.9659299850463867f, 6.162099838256836f, 8.385499954223633f));
// _0_0_2 is CompositeGraphicsNode
float alpha__0_0_2_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_0 = g.getClip();
AffineTransform defaultTransform__0_0_2_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(22.96500015258789, 35.31100082397461), new Point2D.Double(12.640999794006348, 11.423999786376953), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(211, 215, 207, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659299850463867f, -0.2588199973106384f, 0.2588199973106384f, 0.9659299850463867f, 7.281599998474121f, 0.7556399703025818f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 0.5);
((GeneralPath)shape).curveTo(30.438, 0.5, 29.884, 0.547, 29.344, 0.625);
((GeneralPath)shape).lineTo(29.125, 3.7188);
((GeneralPath)shape).curveTo(28.165, 3.9345, 27.275, 4.3023, 26.469, 4.8125);
((GeneralPath)shape).lineTo(24.125, 2.7812);
((GeneralPath)shape).curveTo(23.0, 3.5, 22.5, 4.0, 21.781, 5.125);
((GeneralPath)shape).lineTo(23.812, 7.4688);
((GeneralPath)shape).curveTo(23.302, 8.2755, 22.934, 9.1654, 22.719, 10.125);
((GeneralPath)shape).lineTo(19.625, 10.344);
((GeneralPath)shape).curveTo(19.547, 10.884, 19.5, 11.438, 19.5, 12.0);
((GeneralPath)shape).curveTo(19.5, 12.562, 19.547, 13.116, 19.625, 13.656);
((GeneralPath)shape).lineTo(22.719, 13.875);
((GeneralPath)shape).curveTo(22.934, 14.835, 23.302, 15.725, 23.812, 16.531);
((GeneralPath)shape).lineTo(21.781, 18.875);
((GeneralPath)shape).curveTo(22.5, 20.0, 23.0, 20.5, 24.125, 21.219);
((GeneralPath)shape).lineTo(26.469, 19.188);
((GeneralPath)shape).curveTo(27.275, 19.698, 28.164999, 20.066, 29.125, 20.281);
((GeneralPath)shape).lineTo(29.344, 23.375);
((GeneralPath)shape).curveTo(29.884, 23.453, 30.438, 23.5, 31.0, 23.5);
((GeneralPath)shape).curveTo(31.562, 23.5, 32.116, 23.453, 32.656, 23.375);
((GeneralPath)shape).lineTo(32.875, 20.281);
((GeneralPath)shape).curveTo(33.835, 20.066, 34.725, 19.698, 35.531, 19.188);
((GeneralPath)shape).lineTo(37.875, 21.219);
((GeneralPath)shape).curveTo(39.0, 20.5, 39.5, 20.0, 40.219, 18.875);
((GeneralPath)shape).lineTo(38.188004, 16.531);
((GeneralPath)shape).curveTo(38.698, 15.725, 39.066, 14.835, 39.281002, 13.875);
((GeneralPath)shape).lineTo(42.375004, 13.656);
((GeneralPath)shape).curveTo(42.453003, 13.116, 42.500004, 12.562, 42.500004, 12.0);
((GeneralPath)shape).curveTo(42.500004, 11.438, 42.453003, 10.884, 42.375004, 10.344);
((GeneralPath)shape).lineTo(39.281002, 10.125);
((GeneralPath)shape).curveTo(39.066, 9.1654, 38.698, 8.2755, 38.188004, 7.4688);
((GeneralPath)shape).lineTo(40.219, 5.125);
((GeneralPath)shape).curveTo(39.569, 4.0173, 39.0, 3.5, 37.875, 2.7812);
((GeneralPath)shape).lineTo(35.531, 4.8125);
((GeneralPath)shape).curveTo(34.725, 4.3023, 33.835, 3.9345, 32.875, 3.7188);
((GeneralPath)shape).lineTo(32.656, 0.625);
((GeneralPath)shape).curveTo(32.115997, 0.547, 31.561998, 0.5, 30.999998, 0.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 9.5);
((GeneralPath)shape).curveTo(32.38, 9.5, 33.5, 10.62, 33.5, 12.0);
((GeneralPath)shape).curveTo(33.5, 13.38, 32.38, 14.5, 31.0, 14.5);
((GeneralPath)shape).curveTo(29.619999, 14.5, 28.5, 13.38, 28.5, 12.0);
((GeneralPath)shape).curveTo(28.5, 10.62, 29.62, 9.5, 31.0, 9.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0054f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 0.5);
((GeneralPath)shape).curveTo(30.438, 0.5, 29.884, 0.547, 29.344, 0.625);
((GeneralPath)shape).lineTo(29.125, 3.7188);
((GeneralPath)shape).curveTo(28.165, 3.9345, 27.275, 4.3023, 26.469, 4.8125);
((GeneralPath)shape).lineTo(24.125, 2.7812);
((GeneralPath)shape).curveTo(23.0, 3.5, 22.5, 4.0, 21.781, 5.125);
((GeneralPath)shape).lineTo(23.812, 7.4688);
((GeneralPath)shape).curveTo(23.302, 8.2755, 22.934, 9.1654, 22.719, 10.125);
((GeneralPath)shape).lineTo(19.625, 10.344);
((GeneralPath)shape).curveTo(19.547, 10.884, 19.5, 11.438, 19.5, 12.0);
((GeneralPath)shape).curveTo(19.5, 12.562, 19.547, 13.116, 19.625, 13.656);
((GeneralPath)shape).lineTo(22.719, 13.875);
((GeneralPath)shape).curveTo(22.934, 14.835, 23.302, 15.725, 23.812, 16.531);
((GeneralPath)shape).lineTo(21.781, 18.875);
((GeneralPath)shape).curveTo(22.5, 20.0, 23.0, 20.5, 24.125, 21.219);
((GeneralPath)shape).lineTo(26.469, 19.188);
((GeneralPath)shape).curveTo(27.275, 19.698, 28.164999, 20.066, 29.125, 20.281);
((GeneralPath)shape).lineTo(29.344, 23.375);
((GeneralPath)shape).curveTo(29.884, 23.453, 30.438, 23.5, 31.0, 23.5);
((GeneralPath)shape).curveTo(31.562, 23.5, 32.116, 23.453, 32.656, 23.375);
((GeneralPath)shape).lineTo(32.875, 20.281);
((GeneralPath)shape).curveTo(33.835, 20.066, 34.725, 19.698, 35.531, 19.188);
((GeneralPath)shape).lineTo(37.875, 21.219);
((GeneralPath)shape).curveTo(39.0, 20.5, 39.5, 20.0, 40.219, 18.875);
((GeneralPath)shape).lineTo(38.188004, 16.531);
((GeneralPath)shape).curveTo(38.698, 15.725, 39.066, 14.835, 39.281002, 13.875);
((GeneralPath)shape).lineTo(42.375004, 13.656);
((GeneralPath)shape).curveTo(42.453003, 13.116, 42.500004, 12.562, 42.500004, 12.0);
((GeneralPath)shape).curveTo(42.500004, 11.438, 42.453003, 10.884, 42.375004, 10.344);
((GeneralPath)shape).lineTo(39.281002, 10.125);
((GeneralPath)shape).curveTo(39.066, 9.1654, 38.698, 8.2755, 38.188004, 7.4688);
((GeneralPath)shape).lineTo(40.219, 5.125);
((GeneralPath)shape).curveTo(39.569, 4.0173, 39.0, 3.5, 37.875, 2.7812);
((GeneralPath)shape).lineTo(35.531, 4.8125);
((GeneralPath)shape).curveTo(34.725, 4.3023, 33.835, 3.9345, 32.875, 3.7188);
((GeneralPath)shape).lineTo(32.656, 0.625);
((GeneralPath)shape).curveTo(32.115997, 0.547, 31.561998, 0.5, 30.999998, 0.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 9.5);
((GeneralPath)shape).curveTo(32.38, 9.5, 33.5, 10.62, 33.5, 12.0);
((GeneralPath)shape).curveTo(33.5, 13.38, 32.38, 14.5, 31.0, 14.5);
((GeneralPath)shape).curveTo(29.619999, 14.5, 28.5, 13.38, 28.5, 12.0);
((GeneralPath)shape).curveTo(28.5, 10.62, 29.62, 9.5, 31.0, 9.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_0;
g.setTransform(defaultTransform__0_0_2_0);
g.setClip(clip__0_0_2_0);
float alpha__0_0_2_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_1 = g.getClip();
AffineTransform defaultTransform__0_0_2_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(17.413000106811523, 7.870800018310547), new Point2D.Double(17.027999877929688, 52.505001068115234), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659299850463867f, -0.2588199973106384f, 0.2588199973106384f, 0.9659299850463867f, 7.281599998474121f, 0.7556399703025818f));
stroke = new BasicStroke(1.1333f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 1.4375);
((GeneralPath)shape).curveTo(30.74, 1.4375, 30.478, 1.4795, 30.219, 1.5);
((GeneralPath)shape).lineTo(30.0, 4.5625);
((GeneralPath)shape).curveTo(28.683, 4.7372, 27.478, 5.2645, 26.469, 6.0313);
((GeneralPath)shape).lineTo(24.156, 4.0625);
((GeneralPath)shape).curveTo(23.765, 4.4000998, 23.4, 4.7652, 23.063, 5.1563);
((GeneralPath)shape).lineTo(25.031, 7.4688);
((GeneralPath)shape).curveTo(24.265, 8.4783, 23.737, 9.683001, 23.563, 11.0);
((GeneralPath)shape).lineTo(20.5, 11.219);
((GeneralPath)shape).curveTo(20.479, 11.478, 20.438, 11.74, 20.438, 12.0);
((GeneralPath)shape).curveTo(20.438, 12.26, 20.479, 12.522, 20.5, 12.781);
((GeneralPath)shape).lineTo(23.563, 13.0);
((GeneralPath)shape).curveTo(23.737, 14.317, 24.265, 15.522, 25.031, 16.531);
((GeneralPath)shape).lineTo(23.063, 18.844);
((GeneralPath)shape).curveTo(23.4, 19.235, 23.765, 19.6, 24.156, 19.938);
((GeneralPath)shape).lineTo(26.469, 17.969);
((GeneralPath)shape).curveTo(27.478, 18.735, 28.683, 19.263, 30.0, 19.438);
((GeneralPath)shape).lineTo(30.219, 22.5);
((GeneralPath)shape).curveTo(30.478, 22.521, 30.74, 22.563, 31.0, 22.563);
((GeneralPath)shape).curveTo(31.26, 22.563, 31.522, 22.521, 31.781, 22.5);
((GeneralPath)shape).lineTo(32.0, 19.438);
((GeneralPath)shape).curveTo(33.317, 19.263, 34.522, 18.735, 35.531, 17.969);
((GeneralPath)shape).lineTo(37.843998, 19.938);
((GeneralPath)shape).curveTo(38.234997, 19.6, 38.6, 19.235, 38.938, 18.844);
((GeneralPath)shape).lineTo(36.968998, 16.531);
((GeneralPath)shape).curveTo(37.734997, 15.522, 38.262997, 14.317, 38.438, 13.0);
((GeneralPath)shape).lineTo(41.5, 12.781);
((GeneralPath)shape).curveTo(41.521, 12.522, 41.563, 12.26, 41.563, 12.0);
((GeneralPath)shape).curveTo(41.563, 11.74, 41.521, 11.478, 41.5, 11.219);
((GeneralPath)shape).lineTo(38.438, 11.0);
((GeneralPath)shape).curveTo(38.263, 9.683, 37.735, 8.4783, 36.968998, 7.4688);
((GeneralPath)shape).lineTo(38.938, 5.1563);
((GeneralPath)shape).curveTo(38.6, 4.7652, 38.235, 4.4001, 37.843998, 4.0625);
((GeneralPath)shape).lineTo(35.531, 6.0313);
((GeneralPath)shape).curveTo(34.522, 5.2645, 33.316998, 4.7372, 31.999998, 4.5625);
((GeneralPath)shape).lineTo(31.780998, 1.5);
((GeneralPath)shape).curveTo(31.521997, 1.4795, 31.259998, 1.4375, 30.999998, 1.4375);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_1;
g.setTransform(defaultTransform__0_0_2_1);
g.setClip(clip__0_0_2_1);
float alpha__0_0_2_2 = origAlpha;
origAlpha = origAlpha * 0.8f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_2 = g.getClip();
AffineTransform defaultTransform__0_0_2_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_2 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(31.0, 12.0), 11.125f, new Point2D.Double(31.0, 12.0), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9774699807167053f, -0.26190999150276184f, 0.26190999150276184f, 0.9774699807167053f, -2.4444000720977783f, 8.389699935913086f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 0.875);
((GeneralPath)shape).curveTo(30.679, 0.875, 30.416, 0.91938, 30.188, 0.9375);
((GeneralPath)shape).lineTo(29.688, 0.96875);
((GeneralPath)shape).lineTo(29.656, 1.4688001);
((GeneralPath)shape).lineTo(29.469, 4.125);
((GeneralPath)shape).curveTo(28.409, 4.3341, 27.427, 4.7655, 26.562, 5.3438);
((GeneralPath)shape).lineTo(24.156, 3.3125);
((GeneralPath)shape).lineTo(22.312, 5.1562);
((GeneralPath)shape).lineTo(24.344, 7.5625);
((GeneralPath)shape).curveTo(23.766, 8.4271, 23.334, 9.4087, 23.125, 10.469);
((GeneralPath)shape).lineTo(20.469, 10.656);
((GeneralPath)shape).lineTo(19.969, 10.688);
((GeneralPath)shape).lineTo(19.938, 11.188);
((GeneralPath)shape).curveTo(19.919, 11.415999, 19.875, 11.679, 19.875, 12.0);
((GeneralPath)shape).curveTo(19.875, 12.321, 19.919, 12.584, 19.938, 12.812);
((GeneralPath)shape).lineTo(19.969, 13.312);
((GeneralPath)shape).lineTo(20.469, 13.344);
((GeneralPath)shape).lineTo(23.125, 13.531);
((GeneralPath)shape).curveTo(23.334, 14.591, 23.766, 15.573, 24.344, 16.438);
((GeneralPath)shape).lineTo(22.625, 18.469);
((GeneralPath)shape).lineTo(22.312, 18.844);
((GeneralPath)shape).lineTo(22.625, 19.219);
((GeneralPath)shape).curveTo(22.981, 19.631, 23.369, 20.019, 23.781, 20.375);
((GeneralPath)shape).lineTo(24.156, 20.688);
((GeneralPath)shape).lineTo(24.531, 20.375);
((GeneralPath)shape).lineTo(26.562, 18.656);
((GeneralPath)shape).curveTo(27.427, 19.234, 28.409, 19.666, 29.469, 19.875);
((GeneralPath)shape).lineTo(29.656, 22.531);
((GeneralPath)shape).lineTo(29.688, 23.031);
((GeneralPath)shape).lineTo(30.188, 23.062);
((GeneralPath)shape).curveTo(30.416, 23.081, 30.678999, 23.125, 31.0, 23.125);
((GeneralPath)shape).curveTo(31.321001, 23.125, 31.584, 23.081, 31.812, 23.062);
((GeneralPath)shape).lineTo(32.312, 23.031);
((GeneralPath)shape).lineTo(32.344, 22.531);
((GeneralPath)shape).lineTo(32.531002, 19.875);
((GeneralPath)shape).curveTo(33.591003, 19.666, 34.573, 19.234, 35.438004, 18.656);
((GeneralPath)shape).lineTo(37.469, 20.375);
((GeneralPath)shape).lineTo(37.844, 20.688);
((GeneralPath)shape).lineTo(38.219, 20.375);
((GeneralPath)shape).curveTo(38.631, 20.019, 39.019, 19.631, 39.375, 19.219);
((GeneralPath)shape).lineTo(39.688, 18.844);
((GeneralPath)shape).lineTo(39.375, 18.469);
((GeneralPath)shape).lineTo(37.656, 16.438);
((GeneralPath)shape).curveTo(38.233997, 15.573, 38.665997, 14.591, 38.875, 13.531);
((GeneralPath)shape).lineTo(41.531, 13.344);
((GeneralPath)shape).lineTo(42.031, 13.312);
((GeneralPath)shape).lineTo(42.061996, 12.812);
((GeneralPath)shape).curveTo(42.080997, 12.584001, 42.124996, 12.321, 42.124996, 12.0);
((GeneralPath)shape).curveTo(42.124996, 11.679, 42.080997, 11.416, 42.061996, 11.188);
((GeneralPath)shape).lineTo(42.031, 10.688);
((GeneralPath)shape).lineTo(41.531, 10.656);
((GeneralPath)shape).lineTo(38.875, 10.469);
((GeneralPath)shape).curveTo(38.666, 9.4087, 38.234, 8.4271, 37.656, 7.5625);
((GeneralPath)shape).lineTo(39.375, 5.5312);
((GeneralPath)shape).lineTo(39.688, 5.1562);
((GeneralPath)shape).lineTo(39.375, 4.7812);
((GeneralPath)shape).curveTo(39.019, 4.3693, 38.631, 3.9807, 38.219, 3.625);
((GeneralPath)shape).lineTo(37.844, 3.3125);
((GeneralPath)shape).lineTo(37.469, 3.625);
((GeneralPath)shape).lineTo(35.438004, 5.3438);
((GeneralPath)shape).curveTo(34.573, 4.7655, 33.591003, 4.3341002, 32.531002, 4.125);
((GeneralPath)shape).lineTo(32.344, 1.4688001);
((GeneralPath)shape).lineTo(32.312, 0.96875006);
((GeneralPath)shape).lineTo(31.812, 0.93750006);
((GeneralPath)shape).curveTo(31.584, 0.91938007, 31.321001, 0.87500006, 31.0, 0.87500006);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 8.5);
((GeneralPath)shape).curveTo(32.932, 8.5, 34.5, 10.068, 34.5, 12.0);
((GeneralPath)shape).curveTo(34.5, 13.932, 32.932, 15.5, 31.0, 15.5);
((GeneralPath)shape).curveTo(29.068, 15.5, 27.5, 13.932, 27.5, 12.0);
((GeneralPath)shape).curveTo(27.5, 10.068, 29.068, 8.5, 31.0, 8.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_2;
g.setTransform(defaultTransform__0_0_2_2);
g.setClip(clip__0_0_2_2);
float alpha__0_0_2_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_3 = g.getClip();
AffineTransform defaultTransform__0_0_2_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_3 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(21.788000106811523, 21.87700080871582), new Point2D.Double(10.211000442504883, 3.42330002784729), new float[] {0.0f,1.0f}, new Color[] {new Color(211, 215, 207, 255),new Color(136, 138, 133, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9091100096702576f, -0.24358999729156494f, 0.24358999729156494f, 0.9091100096702576f, 8.676799774169922f, 1.417099952697754f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 4.0);
((GeneralPath)shape).curveTo(26.584, 4.0, 23.0, 7.584, 23.0, 12.0);
((GeneralPath)shape).curveTo(23.0, 16.416, 26.584, 20.0, 31.0, 20.0);
((GeneralPath)shape).curveTo(35.416, 20.0, 39.0, 16.416, 39.0, 12.0);
((GeneralPath)shape).curveTo(39.0, 7.5839996, 35.416, 4.0, 31.0, 4.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 9.0);
((GeneralPath)shape).curveTo(32.656, 9.0, 34.0, 10.344, 34.0, 12.0);
((GeneralPath)shape).curveTo(34.0, 13.656, 32.656, 15.0, 31.0, 15.0);
((GeneralPath)shape).curveTo(29.344002, 15.0, 28.0, 13.656, 28.0, 12.0);
((GeneralPath)shape).curveTo(28.0, 10.344, 29.344, 9.0, 31.0, 9.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_3;
g.setTransform(defaultTransform__0_0_2_3);
g.setClip(clip__0_0_2_3);
float alpha__0_0_2_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_4 = g.getClip();
AffineTransform defaultTransform__0_0_2_4 = g.getTransform();
g.transform(new AffineTransform(0.8823500275611877f, -1.0264000138704432E-6f, 1.0264000138704432E-6f, 0.8823500275611877f, 10.265000343322754f, -4.764699935913086f));
// _0_0_2_4 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(16.488000869750977, 13.970999717712402), new Point2D.Double(32.56700134277344, 30.757999420166016), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659299850463867f, -0.2588199973106384f, 0.2588199973106384f, 0.9659299850463867f, -4.116799831390381f, 6.729700088500977f));
stroke = new BasicStroke(1.1333f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.0, 19.0);
((GeneralPath)shape).curveTo(32.0, 23.69442, 28.19442, 27.5, 23.5, 27.5);
((GeneralPath)shape).curveTo(18.80558, 27.5, 15.0, 23.69442, 15.0, 19.0);
((GeneralPath)shape).curveTo(15.0, 14.305579, 18.80558, 10.5, 23.5, 10.5);
((GeneralPath)shape).curveTo(28.19442, 10.5, 32.0, 14.305579, 32.0, 19.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_4;
g.setTransform(defaultTransform__0_0_2_4);
g.setClip(clip__0_0_2_4);
float alpha__0_0_2_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_5 = g.getClip();
AffineTransform defaultTransform__0_0_2_5 = g.getTransform();
g.transform(new AffineTransform(0.41176000237464905f, -4.789999934473599E-7f, 4.789999934473599E-7f, 0.41176000237464905f, 21.323999404907227f, 4.176499843597412f));
// _0_0_2_5 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(28.35700035095215, 22.795000076293945), new Point2D.Double(17.73200035095215, 5.1875), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659299850463867f, -0.2588199973106384f, 0.2588199973106384f, 0.9659299850463867f, -4.116799831390381f, 6.729599952697754f));
stroke = new BasicStroke(2.4286f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.0, 19.0);
((GeneralPath)shape).curveTo(32.0, 23.69442, 28.19442, 27.5, 23.5, 27.5);
((GeneralPath)shape).curveTo(18.80558, 27.5, 15.0, 23.69442, 15.0, 19.0);
((GeneralPath)shape).curveTo(15.0, 14.305579, 18.80558, 10.5, 23.5, 10.5);
((GeneralPath)shape).curveTo(28.19442, 10.5, 32.0, 14.305579, 32.0, 19.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_5;
g.setTransform(defaultTransform__0_0_2_5);
g.setClip(clip__0_0_2_5);
origAlpha = alpha__0_0_2;
g.setTransform(defaultTransform__0_0_2);
g.setClip(clip__0_0_2);
float alpha__0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(0.9659299850463867f, 0.2588199973106384f, -0.2588199973106384f, 0.9659299850463867f, -6.837900161743164f, -6.614500045776367f));
// _0_0_3 is CompositeGraphicsNode
float alpha__0_0_3_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3_0 = g.getClip();
AffineTransform defaultTransform__0_0_3_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(22.96500015258789, 35.31100082397461), new Point2D.Double(12.640999794006348, 11.423999786376953), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(211, 215, 207, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659299850463867f, -0.2588199973106384f, 0.2588199973106384f, 0.9659299850463867f, 7.281599998474121f, 0.7556399703025818f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 0.5);
((GeneralPath)shape).curveTo(30.438, 0.5, 29.884, 0.547, 29.344, 0.625);
((GeneralPath)shape).lineTo(29.125, 3.7188);
((GeneralPath)shape).curveTo(28.165, 3.9345, 27.275, 4.3023, 26.469, 4.8125);
((GeneralPath)shape).lineTo(24.125, 2.7812);
((GeneralPath)shape).curveTo(23.0, 3.5, 22.5, 4.0, 21.781, 5.125);
((GeneralPath)shape).lineTo(23.812, 7.4688);
((GeneralPath)shape).curveTo(23.302, 8.2755, 22.934, 9.1654, 22.719, 10.125);
((GeneralPath)shape).lineTo(19.625, 10.344);
((GeneralPath)shape).curveTo(19.547, 10.884, 19.5, 11.438, 19.5, 12.0);
((GeneralPath)shape).curveTo(19.5, 12.562, 19.547, 13.116, 19.625, 13.656);
((GeneralPath)shape).lineTo(22.719, 13.875);
((GeneralPath)shape).curveTo(22.934, 14.835, 23.302, 15.725, 23.812, 16.531);
((GeneralPath)shape).lineTo(21.781, 18.875);
((GeneralPath)shape).curveTo(22.5, 20.0, 23.0, 20.5, 24.125, 21.219);
((GeneralPath)shape).lineTo(26.469, 19.188);
((GeneralPath)shape).curveTo(27.275, 19.698, 28.164999, 20.066, 29.125, 20.281);
((GeneralPath)shape).lineTo(29.344, 23.375);
((GeneralPath)shape).curveTo(29.884, 23.453, 30.438, 23.5, 31.0, 23.5);
((GeneralPath)shape).curveTo(31.562, 23.5, 32.116, 23.453, 32.656, 23.375);
((GeneralPath)shape).lineTo(32.875, 20.281);
((GeneralPath)shape).curveTo(33.835, 20.066, 34.725, 19.698, 35.531, 19.188);
((GeneralPath)shape).lineTo(37.875, 21.219);
((GeneralPath)shape).curveTo(39.0, 20.5, 39.5, 20.0, 40.219, 18.875);
((GeneralPath)shape).lineTo(38.188004, 16.531);
((GeneralPath)shape).curveTo(38.698, 15.725, 39.066, 14.835, 39.281002, 13.875);
((GeneralPath)shape).lineTo(42.375004, 13.656);
((GeneralPath)shape).curveTo(42.453003, 13.116, 42.500004, 12.562, 42.500004, 12.0);
((GeneralPath)shape).curveTo(42.500004, 11.438, 42.453003, 10.884, 42.375004, 10.344);
((GeneralPath)shape).lineTo(39.281002, 10.125);
((GeneralPath)shape).curveTo(39.066, 9.1654, 38.698, 8.2755, 38.188004, 7.4688);
((GeneralPath)shape).lineTo(40.219, 5.125);
((GeneralPath)shape).curveTo(39.569, 4.0173, 39.0, 3.5, 37.875, 2.7812);
((GeneralPath)shape).lineTo(35.531, 4.8125);
((GeneralPath)shape).curveTo(34.725, 4.3023, 33.835, 3.9345, 32.875, 3.7188);
((GeneralPath)shape).lineTo(32.656, 0.625);
((GeneralPath)shape).curveTo(32.115997, 0.547, 31.561998, 0.5, 30.999998, 0.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 9.5);
((GeneralPath)shape).curveTo(32.38, 9.5, 33.5, 10.62, 33.5, 12.0);
((GeneralPath)shape).curveTo(33.5, 13.38, 32.38, 14.5, 31.0, 14.5);
((GeneralPath)shape).curveTo(29.619999, 14.5, 28.5, 13.38, 28.5, 12.0);
((GeneralPath)shape).curveTo(28.5, 10.62, 29.62, 9.5, 31.0, 9.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0054f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 0.5);
((GeneralPath)shape).curveTo(30.438, 0.5, 29.884, 0.547, 29.344, 0.625);
((GeneralPath)shape).lineTo(29.125, 3.7188);
((GeneralPath)shape).curveTo(28.165, 3.9345, 27.275, 4.3023, 26.469, 4.8125);
((GeneralPath)shape).lineTo(24.125, 2.7812);
((GeneralPath)shape).curveTo(23.0, 3.5, 22.5, 4.0, 21.781, 5.125);
((GeneralPath)shape).lineTo(23.812, 7.4688);
((GeneralPath)shape).curveTo(23.302, 8.2755, 22.934, 9.1654, 22.719, 10.125);
((GeneralPath)shape).lineTo(19.625, 10.344);
((GeneralPath)shape).curveTo(19.547, 10.884, 19.5, 11.438, 19.5, 12.0);
((GeneralPath)shape).curveTo(19.5, 12.562, 19.547, 13.116, 19.625, 13.656);
((GeneralPath)shape).lineTo(22.719, 13.875);
((GeneralPath)shape).curveTo(22.934, 14.835, 23.302, 15.725, 23.812, 16.531);
((GeneralPath)shape).lineTo(21.781, 18.875);
((GeneralPath)shape).curveTo(22.5, 20.0, 23.0, 20.5, 24.125, 21.219);
((GeneralPath)shape).lineTo(26.469, 19.188);
((GeneralPath)shape).curveTo(27.275, 19.698, 28.164999, 20.066, 29.125, 20.281);
((GeneralPath)shape).lineTo(29.344, 23.375);
((GeneralPath)shape).curveTo(29.884, 23.453, 30.438, 23.5, 31.0, 23.5);
((GeneralPath)shape).curveTo(31.562, 23.5, 32.116, 23.453, 32.656, 23.375);
((GeneralPath)shape).lineTo(32.875, 20.281);
((GeneralPath)shape).curveTo(33.835, 20.066, 34.725, 19.698, 35.531, 19.188);
((GeneralPath)shape).lineTo(37.875, 21.219);
((GeneralPath)shape).curveTo(39.0, 20.5, 39.5, 20.0, 40.219, 18.875);
((GeneralPath)shape).lineTo(38.188004, 16.531);
((GeneralPath)shape).curveTo(38.698, 15.725, 39.066, 14.835, 39.281002, 13.875);
((GeneralPath)shape).lineTo(42.375004, 13.656);
((GeneralPath)shape).curveTo(42.453003, 13.116, 42.500004, 12.562, 42.500004, 12.0);
((GeneralPath)shape).curveTo(42.500004, 11.438, 42.453003, 10.884, 42.375004, 10.344);
((GeneralPath)shape).lineTo(39.281002, 10.125);
((GeneralPath)shape).curveTo(39.066, 9.1654, 38.698, 8.2755, 38.188004, 7.4688);
((GeneralPath)shape).lineTo(40.219, 5.125);
((GeneralPath)shape).curveTo(39.569, 4.0173, 39.0, 3.5, 37.875, 2.7812);
((GeneralPath)shape).lineTo(35.531, 4.8125);
((GeneralPath)shape).curveTo(34.725, 4.3023, 33.835, 3.9345, 32.875, 3.7188);
((GeneralPath)shape).lineTo(32.656, 0.625);
((GeneralPath)shape).curveTo(32.115997, 0.547, 31.561998, 0.5, 30.999998, 0.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 9.5);
((GeneralPath)shape).curveTo(32.38, 9.5, 33.5, 10.62, 33.5, 12.0);
((GeneralPath)shape).curveTo(33.5, 13.38, 32.38, 14.5, 31.0, 14.5);
((GeneralPath)shape).curveTo(29.619999, 14.5, 28.5, 13.38, 28.5, 12.0);
((GeneralPath)shape).curveTo(28.5, 10.62, 29.62, 9.5, 31.0, 9.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_3_0;
g.setTransform(defaultTransform__0_0_3_0);
g.setClip(clip__0_0_3_0);
float alpha__0_0_3_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3_1 = g.getClip();
AffineTransform defaultTransform__0_0_3_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(17.413000106811523, 7.870800018310547), new Point2D.Double(17.027999877929688, 52.505001068115234), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659299850463867f, -0.2588199973106384f, 0.2588199973106384f, 0.9659299850463867f, 7.281599998474121f, 0.7556399703025818f));
stroke = new BasicStroke(1.1333f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 1.4375);
((GeneralPath)shape).curveTo(30.74, 1.4375, 30.478, 1.4795, 30.219, 1.5);
((GeneralPath)shape).lineTo(30.0, 4.5625);
((GeneralPath)shape).curveTo(28.683, 4.7372, 27.478, 5.2645, 26.469, 6.0313);
((GeneralPath)shape).lineTo(24.156, 4.0625);
((GeneralPath)shape).curveTo(23.765, 4.4000998, 23.4, 4.7652, 23.063, 5.1563);
((GeneralPath)shape).lineTo(25.031, 7.4688);
((GeneralPath)shape).curveTo(24.265, 8.4783, 23.737, 9.683001, 23.563, 11.0);
((GeneralPath)shape).lineTo(20.5, 11.219);
((GeneralPath)shape).curveTo(20.479, 11.478, 20.438, 11.74, 20.438, 12.0);
((GeneralPath)shape).curveTo(20.438, 12.26, 20.479, 12.522, 20.5, 12.781);
((GeneralPath)shape).lineTo(23.563, 13.0);
((GeneralPath)shape).curveTo(23.737, 14.317, 24.265, 15.522, 25.031, 16.531);
((GeneralPath)shape).lineTo(23.063, 18.844);
((GeneralPath)shape).curveTo(23.4, 19.235, 23.765, 19.6, 24.156, 19.938);
((GeneralPath)shape).lineTo(26.469, 17.969);
((GeneralPath)shape).curveTo(27.478, 18.735, 28.683, 19.263, 30.0, 19.438);
((GeneralPath)shape).lineTo(30.219, 22.5);
((GeneralPath)shape).curveTo(30.478, 22.521, 30.74, 22.563, 31.0, 22.563);
((GeneralPath)shape).curveTo(31.26, 22.563, 31.522, 22.521, 31.781, 22.5);
((GeneralPath)shape).lineTo(32.0, 19.438);
((GeneralPath)shape).curveTo(33.317, 19.263, 34.522, 18.735, 35.531, 17.969);
((GeneralPath)shape).lineTo(37.843998, 19.938);
((GeneralPath)shape).curveTo(38.234997, 19.6, 38.6, 19.235, 38.938, 18.844);
((GeneralPath)shape).lineTo(36.968998, 16.531);
((GeneralPath)shape).curveTo(37.734997, 15.522, 38.262997, 14.317, 38.438, 13.0);
((GeneralPath)shape).lineTo(41.5, 12.781);
((GeneralPath)shape).curveTo(41.521, 12.522, 41.563, 12.26, 41.563, 12.0);
((GeneralPath)shape).curveTo(41.563, 11.74, 41.521, 11.478, 41.5, 11.219);
((GeneralPath)shape).lineTo(38.438, 11.0);
((GeneralPath)shape).curveTo(38.263, 9.683, 37.735, 8.4783, 36.968998, 7.4688);
((GeneralPath)shape).lineTo(38.938, 5.1563);
((GeneralPath)shape).curveTo(38.6, 4.7652, 38.235, 4.4001, 37.843998, 4.0625);
((GeneralPath)shape).lineTo(35.531, 6.0313);
((GeneralPath)shape).curveTo(34.522, 5.2645, 33.316998, 4.7372, 31.999998, 4.5625);
((GeneralPath)shape).lineTo(31.780998, 1.5);
((GeneralPath)shape).curveTo(31.521997, 1.4795, 31.259998, 1.4375, 30.999998, 1.4375);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_3_1;
g.setTransform(defaultTransform__0_0_3_1);
g.setClip(clip__0_0_3_1);
float alpha__0_0_3_2 = origAlpha;
origAlpha = origAlpha * 0.8f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3_2 = g.getClip();
AffineTransform defaultTransform__0_0_3_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3_2 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(31.0, 12.0), 11.125f, new Point2D.Double(31.0, 12.0), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9774699807167053f, -0.26190999150276184f, 0.26190999150276184f, 0.9774699807167053f, -2.4444000720977783f, 8.389699935913086f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 0.875);
((GeneralPath)shape).curveTo(30.679, 0.875, 30.416, 0.91938, 30.188, 0.9375);
((GeneralPath)shape).lineTo(29.688, 0.96875);
((GeneralPath)shape).lineTo(29.656, 1.4688001);
((GeneralPath)shape).lineTo(29.469, 4.125);
((GeneralPath)shape).curveTo(28.409, 4.3341, 27.427, 4.7655, 26.562, 5.3438);
((GeneralPath)shape).lineTo(24.156, 3.3125);
((GeneralPath)shape).lineTo(22.312, 5.1562);
((GeneralPath)shape).lineTo(24.344, 7.5625);
((GeneralPath)shape).curveTo(23.766, 8.4271, 23.334, 9.4087, 23.125, 10.469);
((GeneralPath)shape).lineTo(20.469, 10.656);
((GeneralPath)shape).lineTo(19.969, 10.688);
((GeneralPath)shape).lineTo(19.938, 11.188);
((GeneralPath)shape).curveTo(19.919, 11.415999, 19.875, 11.679, 19.875, 12.0);
((GeneralPath)shape).curveTo(19.875, 12.321, 19.919, 12.584, 19.938, 12.812);
((GeneralPath)shape).lineTo(19.969, 13.312);
((GeneralPath)shape).lineTo(20.469, 13.344);
((GeneralPath)shape).lineTo(23.125, 13.531);
((GeneralPath)shape).curveTo(23.334, 14.591, 23.766, 15.573, 24.344, 16.438);
((GeneralPath)shape).lineTo(22.625, 18.469);
((GeneralPath)shape).lineTo(22.312, 18.844);
((GeneralPath)shape).lineTo(22.625, 19.219);
((GeneralPath)shape).curveTo(22.981, 19.631, 23.369, 20.019, 23.781, 20.375);
((GeneralPath)shape).lineTo(24.156, 20.688);
((GeneralPath)shape).lineTo(24.531, 20.375);
((GeneralPath)shape).lineTo(26.562, 18.656);
((GeneralPath)shape).curveTo(27.427, 19.234, 28.409, 19.666, 29.469, 19.875);
((GeneralPath)shape).lineTo(29.656, 22.531);
((GeneralPath)shape).lineTo(29.688, 23.031);
((GeneralPath)shape).lineTo(30.188, 23.062);
((GeneralPath)shape).curveTo(30.416, 23.081, 30.678999, 23.125, 31.0, 23.125);
((GeneralPath)shape).curveTo(31.321001, 23.125, 31.584, 23.081, 31.812, 23.062);
((GeneralPath)shape).lineTo(32.312, 23.031);
((GeneralPath)shape).lineTo(32.344, 22.531);
((GeneralPath)shape).lineTo(32.531002, 19.875);
((GeneralPath)shape).curveTo(33.591003, 19.666, 34.573, 19.234, 35.438004, 18.656);
((GeneralPath)shape).lineTo(37.469, 20.375);
((GeneralPath)shape).lineTo(37.844, 20.688);
((GeneralPath)shape).lineTo(38.219, 20.375);
((GeneralPath)shape).curveTo(38.631, 20.019, 39.019, 19.631, 39.375, 19.219);
((GeneralPath)shape).lineTo(39.688, 18.844);
((GeneralPath)shape).lineTo(39.375, 18.469);
((GeneralPath)shape).lineTo(37.656, 16.438);
((GeneralPath)shape).curveTo(38.233997, 15.573, 38.665997, 14.591, 38.875, 13.531);
((GeneralPath)shape).lineTo(41.531, 13.344);
((GeneralPath)shape).lineTo(42.031, 13.312);
((GeneralPath)shape).lineTo(42.061996, 12.812);
((GeneralPath)shape).curveTo(42.080997, 12.584001, 42.124996, 12.321, 42.124996, 12.0);
((GeneralPath)shape).curveTo(42.124996, 11.679, 42.080997, 11.416, 42.061996, 11.188);
((GeneralPath)shape).lineTo(42.031, 10.688);
((GeneralPath)shape).lineTo(41.531, 10.656);
((GeneralPath)shape).lineTo(38.875, 10.469);
((GeneralPath)shape).curveTo(38.666, 9.4087, 38.234, 8.4271, 37.656, 7.5625);
((GeneralPath)shape).lineTo(39.375, 5.5312);
((GeneralPath)shape).lineTo(39.688, 5.1562);
((GeneralPath)shape).lineTo(39.375, 4.7812);
((GeneralPath)shape).curveTo(39.019, 4.3693, 38.631, 3.9807, 38.219, 3.625);
((GeneralPath)shape).lineTo(37.844, 3.3125);
((GeneralPath)shape).lineTo(37.469, 3.625);
((GeneralPath)shape).lineTo(35.438004, 5.3438);
((GeneralPath)shape).curveTo(34.573, 4.7655, 33.591003, 4.3341002, 32.531002, 4.125);
((GeneralPath)shape).lineTo(32.344, 1.4688001);
((GeneralPath)shape).lineTo(32.312, 0.96875006);
((GeneralPath)shape).lineTo(31.812, 0.93750006);
((GeneralPath)shape).curveTo(31.584, 0.91938007, 31.321001, 0.87500006, 31.0, 0.87500006);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 8.5);
((GeneralPath)shape).curveTo(32.932, 8.5, 34.5, 10.068, 34.5, 12.0);
((GeneralPath)shape).curveTo(34.5, 13.932, 32.932, 15.5, 31.0, 15.5);
((GeneralPath)shape).curveTo(29.068, 15.5, 27.5, 13.932, 27.5, 12.0);
((GeneralPath)shape).curveTo(27.5, 10.068, 29.068, 8.5, 31.0, 8.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_3_2;
g.setTransform(defaultTransform__0_0_3_2);
g.setClip(clip__0_0_3_2);
float alpha__0_0_3_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3_3 = g.getClip();
AffineTransform defaultTransform__0_0_3_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3_3 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(21.788000106811523, 21.87700080871582), new Point2D.Double(10.211000442504883, 3.42330002784729), new float[] {0.0f,1.0f}, new Color[] {new Color(211, 215, 207, 255),new Color(136, 138, 133, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9091100096702576f, -0.24358999729156494f, 0.24358999729156494f, 0.9091100096702576f, 8.676799774169922f, 1.417099952697754f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.0, 4.0);
((GeneralPath)shape).curveTo(26.584, 4.0, 23.0, 7.584, 23.0, 12.0);
((GeneralPath)shape).curveTo(23.0, 16.416, 26.584, 20.0, 31.0, 20.0);
((GeneralPath)shape).curveTo(35.416, 20.0, 39.0, 16.416, 39.0, 12.0);
((GeneralPath)shape).curveTo(39.0, 7.5839996, 35.416, 4.0, 31.0, 4.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.0, 9.0);
((GeneralPath)shape).curveTo(32.656, 9.0, 34.0, 10.344, 34.0, 12.0);
((GeneralPath)shape).curveTo(34.0, 13.656, 32.656, 15.0, 31.0, 15.0);
((GeneralPath)shape).curveTo(29.344002, 15.0, 28.0, 13.656, 28.0, 12.0);
((GeneralPath)shape).curveTo(28.0, 10.344, 29.344, 9.0, 31.0, 9.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_3_3;
g.setTransform(defaultTransform__0_0_3_3);
g.setClip(clip__0_0_3_3);
float alpha__0_0_3_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3_4 = g.getClip();
AffineTransform defaultTransform__0_0_3_4 = g.getTransform();
g.transform(new AffineTransform(0.8823500275611877f, -1.0264000138704432E-6f, 1.0264000138704432E-6f, 0.8823500275611877f, 10.265000343322754f, -4.764699935913086f));
// _0_0_3_4 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(16.488000869750977, 13.970999717712402), new Point2D.Double(32.56700134277344, 30.757999420166016), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659299850463867f, -0.2588199973106384f, 0.2588199973106384f, 0.9659299850463867f, -4.116799831390381f, 6.729700088500977f));
stroke = new BasicStroke(1.1333f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.0, 19.0);
((GeneralPath)shape).curveTo(32.0, 23.69442, 28.19442, 27.5, 23.5, 27.5);
((GeneralPath)shape).curveTo(18.80558, 27.5, 15.0, 23.69442, 15.0, 19.0);
((GeneralPath)shape).curveTo(15.0, 14.305579, 18.80558, 10.5, 23.5, 10.5);
((GeneralPath)shape).curveTo(28.19442, 10.5, 32.0, 14.305579, 32.0, 19.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_3_4;
g.setTransform(defaultTransform__0_0_3_4);
g.setClip(clip__0_0_3_4);
float alpha__0_0_3_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3_5 = g.getClip();
AffineTransform defaultTransform__0_0_3_5 = g.getTransform();
g.transform(new AffineTransform(0.41176000237464905f, -4.789999934473599E-7f, 4.789999934473599E-7f, 0.41176000237464905f, 21.323999404907227f, 4.176499843597412f));
// _0_0_3_5 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(28.35700035095215, 22.795000076293945), new Point2D.Double(17.73200035095215, 5.1875), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9659299850463867f, -0.2588199973106384f, 0.2588199973106384f, 0.9659299850463867f, -4.116799831390381f, 6.729599952697754f));
stroke = new BasicStroke(2.4286f,2,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(32.0, 19.0);
((GeneralPath)shape).curveTo(32.0, 23.69442, 28.19442, 27.5, 23.5, 27.5);
((GeneralPath)shape).curveTo(18.80558, 27.5, 15.0, 23.69442, 15.0, 19.0);
((GeneralPath)shape).curveTo(15.0, 14.305579, 18.80558, 10.5, 23.5, 10.5);
((GeneralPath)shape).curveTo(28.19442, 10.5, 32.0, 14.305579, 32.0, 19.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_3_5;
g.setTransform(defaultTransform__0_0_3_5);
g.setClip(clip__0_0_3_5);
origAlpha = alpha__0_0_3;
g.setTransform(defaultTransform__0_0_3);
g.setClip(clip__0_0_3);
float alpha__0_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(14.496000289916992, 29.8799991607666), new Point2D.Double(26.73699951171875, 42.56399917602539), new float[] {0.0f,1.0f}, new Color[] {new Color(211, 215, 207, 255),new Color(136, 138, 133, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -3.0f, 1.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.156, 26.5);
((GeneralPath)shape).curveTo(15.668, 26.519, 15.184, 26.594, 14.719, 26.688);
((GeneralPath)shape).lineTo(14.688, 26.688);
((GeneralPath)shape).curveTo(14.337999, 26.758999, 13.99, 26.858, 13.656, 26.969);
((GeneralPath)shape).curveTo(13.174, 27.129, 12.724, 27.327, 12.281, 27.562);
((GeneralPath)shape).lineTo(12.938, 30.688);
((GeneralPath)shape).curveTo(12.4609995, 31.039999, 12.013, 31.453, 11.656, 31.938);
((GeneralPath)shape).lineTo(8.5625, 31.312);
((GeneralPath)shape).curveTo(8.3061, 31.797, 8.0717, 32.281002, 7.9062, 32.812);
((GeneralPath)shape).curveTo(7.6763997, 33.551, 7.531, 34.35, 7.5, 35.156002);
((GeneralPath)shape).lineTo(10.562, 36.188004);
((GeneralPath)shape).curveTo(10.632, 36.780003, 10.764, 37.368004, 11.0, 37.906002);
((GeneralPath)shape).lineTo(8.875, 40.281002);
((GeneralPath)shape).curveTo(9.1527, 40.723003, 9.4647, 41.146004, 9.8125, 41.531002);
((GeneralPath)shape).lineTo(10.438, 42.125004);
((GeneralPath)shape).curveTo(10.826, 42.480003, 11.240999, 42.810005, 11.688, 43.094006);
((GeneralPath)shape).lineTo(14.062, 40.969006);
((GeneralPath)shape).curveTo(14.348001, 41.096004, 14.626, 41.198006, 14.938001, 41.281006);
((GeneralPath)shape).curveTo(15.232, 41.360004, 15.549001, 41.435005, 15.844001, 41.469006);
((GeneralPath)shape).lineTo(16.844002, 44.500004);
((GeneralPath)shape).curveTo(17.332003, 44.481003, 17.816002, 44.406002, 18.281002, 44.312004);
((GeneralPath)shape).lineTo(18.312002, 44.312004);
((GeneralPath)shape).curveTo(18.662003, 44.241005, 19.010002, 44.142006, 19.344002, 44.031006);
((GeneralPath)shape).curveTo(19.826002, 43.871006, 20.276001, 43.673004, 20.719002, 43.438007);
((GeneralPath)shape).lineTo(20.062002, 40.312008);
((GeneralPath)shape).curveTo(20.539001, 39.960007, 20.987001, 39.54701, 21.344002, 39.062008);
((GeneralPath)shape).lineTo(24.438002, 39.688007);
((GeneralPath)shape).curveTo(24.694002, 39.203007, 24.928001, 38.719006, 25.094002, 38.188007);
((GeneralPath)shape).curveTo(25.324001, 37.44901, 25.469002, 36.65001, 25.500002, 35.844006);
((GeneralPath)shape).lineTo(22.438002, 34.812004);
((GeneralPath)shape).curveTo(22.368002, 34.220005, 22.236002, 33.632004, 22.000002, 33.094006);
((GeneralPath)shape).lineTo(24.125002, 30.719006);
((GeneralPath)shape).curveTo(23.847002, 30.277006, 23.535002, 29.854006, 23.188002, 29.469006);
((GeneralPath)shape).lineTo(22.562002, 28.875006);
((GeneralPath)shape).curveTo(22.174002, 28.520006, 21.759003, 28.190006, 21.312002, 27.906006);
((GeneralPath)shape).lineTo(18.938002, 30.031006);
((GeneralPath)shape).curveTo(18.652002, 29.904005, 18.374002, 29.802006, 18.062002, 29.719006);
((GeneralPath)shape).curveTo(17.768002, 29.640005, 17.451002, 29.565006, 17.156002, 29.531006);
((GeneralPath)shape).lineTo(16.156002, 26.500006);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(16.281, 33.5);
((GeneralPath)shape).curveTo(16.395, 33.487, 16.509, 33.493, 16.625, 33.5);
((GeneralPath)shape).curveTo(16.758, 33.508, 16.898, 33.527, 17.031, 33.562);
((GeneralPath)shape).curveTo(18.098, 33.848, 18.723, 34.965, 18.438, 36.031002);
((GeneralPath)shape).curveTo(18.152, 37.098003, 17.035, 37.723003, 15.969, 37.438004);
((GeneralPath)shape).curveTo(14.901999, 37.152004, 14.276999, 36.035004, 14.562, 34.969);
((GeneralPath)shape).curveTo(14.781, 34.152, 15.487, 33.589, 16.281, 33.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(46, 52, 54, 255);
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.156, 26.5);
((GeneralPath)shape).curveTo(15.668, 26.519, 15.184, 26.594, 14.719, 26.688);
((GeneralPath)shape).lineTo(14.688, 26.688);
((GeneralPath)shape).curveTo(14.337999, 26.758999, 13.99, 26.858, 13.656, 26.969);
((GeneralPath)shape).curveTo(13.174, 27.129, 12.724, 27.327, 12.281, 27.562);
((GeneralPath)shape).lineTo(12.938, 30.688);
((GeneralPath)shape).curveTo(12.4609995, 31.039999, 12.013, 31.453, 11.656, 31.938);
((GeneralPath)shape).lineTo(8.5625, 31.312);
((GeneralPath)shape).curveTo(8.3061, 31.797, 8.0717, 32.281002, 7.9062, 32.812);
((GeneralPath)shape).curveTo(7.6763997, 33.551, 7.531, 34.35, 7.5, 35.156002);
((GeneralPath)shape).lineTo(10.562, 36.188004);
((GeneralPath)shape).curveTo(10.632, 36.780003, 10.764, 37.368004, 11.0, 37.906002);
((GeneralPath)shape).lineTo(8.875, 40.281002);
((GeneralPath)shape).curveTo(9.1527, 40.723003, 9.4647, 41.146004, 9.8125, 41.531002);
((GeneralPath)shape).lineTo(10.438, 42.125004);
((GeneralPath)shape).curveTo(10.826, 42.480003, 11.240999, 42.810005, 11.688, 43.094006);
((GeneralPath)shape).lineTo(14.062, 40.969006);
((GeneralPath)shape).curveTo(14.348001, 41.096004, 14.626, 41.198006, 14.938001, 41.281006);
((GeneralPath)shape).curveTo(15.232, 41.360004, 15.549001, 41.435005, 15.844001, 41.469006);
((GeneralPath)shape).lineTo(16.844002, 44.500004);
((GeneralPath)shape).curveTo(17.332003, 44.481003, 17.816002, 44.406002, 18.281002, 44.312004);
((GeneralPath)shape).lineTo(18.312002, 44.312004);
((GeneralPath)shape).curveTo(18.662003, 44.241005, 19.010002, 44.142006, 19.344002, 44.031006);
((GeneralPath)shape).curveTo(19.826002, 43.871006, 20.276001, 43.673004, 20.719002, 43.438007);
((GeneralPath)shape).lineTo(20.062002, 40.312008);
((GeneralPath)shape).curveTo(20.539001, 39.960007, 20.987001, 39.54701, 21.344002, 39.062008);
((GeneralPath)shape).lineTo(24.438002, 39.688007);
((GeneralPath)shape).curveTo(24.694002, 39.203007, 24.928001, 38.719006, 25.094002, 38.188007);
((GeneralPath)shape).curveTo(25.324001, 37.44901, 25.469002, 36.65001, 25.500002, 35.844006);
((GeneralPath)shape).lineTo(22.438002, 34.812004);
((GeneralPath)shape).curveTo(22.368002, 34.220005, 22.236002, 33.632004, 22.000002, 33.094006);
((GeneralPath)shape).lineTo(24.125002, 30.719006);
((GeneralPath)shape).curveTo(23.847002, 30.277006, 23.535002, 29.854006, 23.188002, 29.469006);
((GeneralPath)shape).lineTo(22.562002, 28.875006);
((GeneralPath)shape).curveTo(22.174002, 28.520006, 21.759003, 28.190006, 21.312002, 27.906006);
((GeneralPath)shape).lineTo(18.938002, 30.031006);
((GeneralPath)shape).curveTo(18.652002, 29.904005, 18.374002, 29.802006, 18.062002, 29.719006);
((GeneralPath)shape).curveTo(17.768002, 29.640005, 17.451002, 29.565006, 17.156002, 29.531006);
((GeneralPath)shape).lineTo(16.156002, 26.500006);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(16.281, 33.5);
((GeneralPath)shape).curveTo(16.395, 33.487, 16.509, 33.493, 16.625, 33.5);
((GeneralPath)shape).curveTo(16.758, 33.508, 16.898, 33.527, 17.031, 33.562);
((GeneralPath)shape).curveTo(18.098, 33.848, 18.723, 34.965, 18.438, 36.031002);
((GeneralPath)shape).curveTo(18.152, 37.098003, 17.035, 37.723003, 15.969, 37.438004);
((GeneralPath)shape).curveTo(14.901999, 37.152004, 14.276999, 36.035004, 14.562, 34.969);
((GeneralPath)shape).curveTo(14.781, 34.152, 15.487, 33.589, 16.281, 33.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_4;
g.setTransform(defaultTransform__0_0_4);
g.setClip(clip__0_0_4);
float alpha__0_0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5 = g.getClip();
AffineTransform defaultTransform__0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -3.0f, 1.0f));
// _0_0_5 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(14.375, 31.062000274658203), new Point2D.Double(30.437999725341797, 44.0620002746582), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.469, 26.469);
((GeneralPath)shape).lineTo(16.375, 27.062);
((GeneralPath)shape).lineTo(17.219, 31.094);
((GeneralPath)shape).curveTo(17.275, 31.406, 17.175, 31.726, 16.95, 31.95);
((GeneralPath)shape).curveTo(16.726, 32.175, 16.406, 32.275, 16.094, 32.219);
((GeneralPath)shape).lineTo(12.031, 31.375002);
((GeneralPath)shape).lineTo(11.438, 33.469);
((GeneralPath)shape).lineTo(15.406, 34.781002);
((GeneralPath)shape).curveTo(15.713, 34.887, 15.9470005, 35.139004, 16.028, 35.454002);
((GeneralPath)shape).curveTo(16.11, 35.768, 16.029, 36.102, 15.812, 36.344);
((GeneralPath)shape).lineTo(13.094, 39.406002);
((GeneralPath)shape).lineTo(14.562, 40.906002);
((GeneralPath)shape).lineTo(17.656, 38.156002);
((GeneralPath)shape).curveTo(17.898, 37.940002, 18.232, 37.859, 18.546, 37.940002);
((GeneralPath)shape).curveTo(18.861, 38.022003, 19.112999, 38.256004, 19.219, 38.562004);
((GeneralPath)shape).lineTo(20.531, 42.531006);
((GeneralPath)shape).lineTo(22.625, 41.938007);
((GeneralPath)shape).lineTo(21.781, 37.906006);
((GeneralPath)shape).curveTo(21.725, 37.594006, 21.825, 37.274006, 22.05, 37.050007);
((GeneralPath)shape).curveTo(22.274, 36.82501, 22.594, 36.725006, 22.906, 36.781006);
((GeneralPath)shape).lineTo(26.969, 37.625008);
((GeneralPath)shape).lineTo(27.562, 35.531006);
((GeneralPath)shape).lineTo(23.594, 34.219006);
((GeneralPath)shape).curveTo(23.287, 34.113007, 23.053, 33.861004, 22.972, 33.546005);
((GeneralPath)shape).curveTo(22.89, 33.232006, 22.971, 32.898006, 23.188, 32.656006);
((GeneralPath)shape).lineTo(25.906, 29.594006);
((GeneralPath)shape).lineTo(24.438, 28.094006);
((GeneralPath)shape).lineTo(21.344, 30.844006);
((GeneralPath)shape).curveTo(21.102, 31.060005, 20.768, 31.141006, 20.454, 31.060005);
((GeneralPath)shape).curveTo(20.139, 30.978004, 19.887001, 30.744005, 19.781, 30.438005);
((GeneralPath)shape).lineTo(18.469, 26.469006);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
float alpha__0_0_6 = origAlpha;
origAlpha = origAlpha * 0.93725f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_6 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(42.5, 34.5), 8.6875f, new Point2D.Double(42.5, 34.5), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.996399998664856f, -26.0f, 1.1240999698638916f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(15.781, 26.844);
((GeneralPath)shape).lineTo(12.781, 27.719);
((GeneralPath)shape).lineTo(13.719, 32.188);
((GeneralPath)shape).curveTo(13.746, 32.339, 13.703, 32.485, 13.594, 32.593998);
((GeneralPath)shape).curveTo(13.485, 32.702995, 13.339, 32.746, 13.188, 32.718998);
((GeneralPath)shape).lineTo(8.6875, 31.780998);
((GeneralPath)shape).lineTo(7.8125, 34.781);
((GeneralPath)shape).lineTo(12.25, 36.25);
((GeneralPath)shape).curveTo(12.399, 36.301, 12.492, 36.441, 12.531, 36.594);
((GeneralPath)shape).curveTo(12.571, 36.746002, 12.543, 36.883003, 12.438, 37.0);
((GeneralPath)shape).lineTo(9.406199, 40.438);
((GeneralPath)shape).lineTo(11.530999, 42.593998);
((GeneralPath)shape).lineTo(14.999999, 39.531);
((GeneralPath)shape).curveTo(15.116999, 39.426, 15.253999, 39.398, 15.405999, 39.438);
((GeneralPath)shape).curveTo(15.558999, 39.477, 15.698999, 39.57, 15.749999, 39.718998);
((GeneralPath)shape).lineTo(17.219, 44.156);
((GeneralPath)shape).lineTo(20.219, 43.281);
((GeneralPath)shape).lineTo(19.281, 38.811996);
((GeneralPath)shape).curveTo(19.254, 38.660995, 19.297, 38.514996, 19.406, 38.406);
((GeneralPath)shape).curveTo(19.515, 38.297, 19.661, 38.253998, 19.812, 38.281);
((GeneralPath)shape).lineTo(24.312, 39.218998);
((GeneralPath)shape).lineTo(25.188, 36.218998);
((GeneralPath)shape).lineTo(20.75, 34.749996);
((GeneralPath)shape).curveTo(20.601, 34.698997, 20.508, 34.558994, 20.469, 34.405994);
((GeneralPath)shape).curveTo(20.428999, 34.253994, 20.457, 34.116993, 20.562, 33.999996);
((GeneralPath)shape).lineTo(23.594, 30.561996);
((GeneralPath)shape).lineTo(21.469, 28.405996);
((GeneralPath)shape).lineTo(18.0, 31.468996);
((GeneralPath)shape).curveTo(17.883, 31.573996, 17.746, 31.601995, 17.594, 31.561996);
((GeneralPath)shape).curveTo(17.441, 31.522997, 17.301, 31.429996, 17.25, 31.280996);
((GeneralPath)shape).lineTo(15.781, 26.843996);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(16.5, 32.5);
((GeneralPath)shape).curveTo(18.156, 32.5, 19.5, 33.844, 19.5, 35.5);
((GeneralPath)shape).curveTo(19.5, 37.156, 18.156, 38.5, 16.5, 38.5);
((GeneralPath)shape).curveTo(14.844, 38.5, 13.5, 37.156, 13.5, 35.5);
((GeneralPath)shape).curveTo(13.5, 33.844, 14.844, 32.5, 16.5, 32.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_6;
g.setTransform(defaultTransform__0_0_6);
g.setClip(clip__0_0_6);
float alpha__0_0_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(16.79400062561035, 30.676000595092773), new Point2D.Double(22.05299949645996, 37.12799835205078), new float[] {0.0f,1.0f}, new Color[] {new Color(136, 138, 133, 255),new Color(211, 215, 207, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -3.0f, 1.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.5, 30.0);
((GeneralPath)shape).curveTo(13.464, 30.0, 11.0, 32.464, 11.0, 35.5);
((GeneralPath)shape).curveTo(11.0, 38.536, 13.464, 41.0, 16.5, 41.0);
((GeneralPath)shape).curveTo(19.536, 41.0, 22.0, 38.536, 22.0, 35.5);
((GeneralPath)shape).curveTo(22.0, 32.464, 19.536, 30.0, 16.5, 30.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(16.5, 33.0);
((GeneralPath)shape).curveTo(17.88, 33.0, 19.0, 34.12, 19.0, 35.5);
((GeneralPath)shape).curveTo(19.0, 36.88, 17.88, 38.0, 16.5, 38.0);
((GeneralPath)shape).curveTo(15.120001, 38.0, 14.0, 36.88, 14.0, 35.5);
((GeneralPath)shape).curveTo(14.0, 34.12, 15.12, 33.0, 16.5, 33.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_7;
g.setTransform(defaultTransform__0_0_7);
g.setClip(clip__0_0_7);
float alpha__0_0_8 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_8 = g.getClip();
AffineTransform defaultTransform__0_0_8 = g.getTransform();
g.transform(new AffineTransform(0.949400007724762f, 0.0f, 0.0f, 0.949400007724762f, -2.0664000511169434f, 2.6630001068115234f));
// _0_0_8 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(20.80699920654297, 36.82500076293945), new Point2D.Double(17.448999404907227, 30.900999069213867), new float[] {0.0f,1.0f}, new Color[] {new Color(238, 238, 236, 255),new Color(238, 238, 236, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0533f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.716, 34.587);
((GeneralPath)shape).curveTo(22.716, 36.332222, 21.30122, 37.747, 19.556, 37.747);
((GeneralPath)shape).curveTo(17.81078, 37.747, 16.396, 36.332222, 16.396, 34.587);
((GeneralPath)shape).curveTo(16.396, 32.84178, 17.81078, 31.427002, 19.556, 31.427002);
((GeneralPath)shape).curveTo(21.30122, 31.427002, 22.716, 32.84178, 22.716, 34.587);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_8;
g.setTransform(defaultTransform__0_0_8);
g.setClip(clip__0_0_8);
float alpha__0_0_9 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_9 = g.getClip();
AffineTransform defaultTransform__0_0_9 = g.getTransform();
g.transform(new AffineTransform(1.5822999477386475f, 0.0f, 0.0f, -1.5822999477386475f, -14.444000244140625f, 90.22799682617188f));
// _0_0_9 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(17.65999984741211, 36.68000030517578), new Point2D.Double(23.031999588012695, 31.111000061035156), new float[] {0.0f,1.0f}, new Color[] {new Color(238, 238, 236, 255),new Color(238, 238, 236, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.63198f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.716, 34.587);
((GeneralPath)shape).curveTo(22.716, 36.332222, 21.30122, 37.747, 19.556, 37.747);
((GeneralPath)shape).curveTo(17.81078, 37.747, 16.396, 36.332222, 16.396, 34.587);
((GeneralPath)shape).curveTo(16.396, 32.84178, 17.81078, 31.427002, 19.556, 31.427002);
((GeneralPath)shape).curveTo(21.30122, 31.427002, 22.716, 32.84178, 22.716, 34.587);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_9;
g.setTransform(defaultTransform__0_0_9);
g.setClip(clip__0_0_9);
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
        return 4;
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
	public RunIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public RunIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public RunIcon(int width, int height) {
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

