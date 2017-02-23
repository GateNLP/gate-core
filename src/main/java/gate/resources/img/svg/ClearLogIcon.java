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
public class ClearLogIcon implements
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
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 0.4f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(0.9999989867210388f, 0.0f, 0.0f, 1.375f, -1.9999959468841553f, -16.375f));
// _0_0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(-1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f));
// _0_0_0_0_0 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(6.0, 43.00000762939453), 1.5f, new Point2D.Double(6.0, 43.00000762939453), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0f, 0.0f, 0.0f, 1.3333330154418945f, -21.0f, -100.33329772949219f));
shape = new Rectangle2D.Double(-9.0, -45.0, 3.0, 4.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_0_0;
g.setTransform(defaultTransform__0_0_0_0_0);
g.setClip(clip__0_0_0_0_0);
float alpha__0_0_0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0_1 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(6.0, 43.00000762939453), 1.5f, new Point2D.Double(6.0, 43.00000762939453), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(2.0f, 0.0f, 0.0f, 1.3333330154418945f, 33.0f, -14.33329963684082f));
shape = new Rectangle2D.Double(45.0, 41.0, 3.0, 4.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_0_1;
g.setTransform(defaultTransform__0_0_0_0_1);
g.setClip(clip__0_0_0_0_1);
float alpha__0_0_0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(10.53125, 40.999969482421875), new Point2D.Double(10.53125, 45.001102447509766), new float[] {0.0f,0.5f,1.0f}, new Color[] {new Color(0, 0, 0, 0),new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(9.0f, 0.0f, 0.0f, 1.0f, -72.0f, 0.0f));
shape = new Rectangle2D.Double(9.0, 41.0, 36.0, 4.0);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_0_2;
g.setTransform(defaultTransform__0_0_0_0_2);
g.setClip(clip__0_0_0_0_2);
origAlpha = alpha__0_0_0_0;
g.setTransform(defaultTransform__0_0_0_0);
g.setClip(clip__0_0_0_0);
float alpha__0_0_0_1 = origAlpha;
origAlpha = origAlpha * 0.08235294f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(0.8296719789505005f, 0.0f, 0.0f, 0.8110929727554321f, -12.99530029296875f, 17.827489852905273f));
// _0_0_0_1 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(28.284271240234375, 30.145553588867188), 13.258252f, new Point2D.Double(28.284271240234375, 30.145553588867188), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.3400000035762787f, 0.0f, 19.89607048034668f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(41.542522, 30.145554);
((GeneralPath)shape).curveTo(41.543793, 31.756325, 39.017063, 33.244926, 34.914425, 34.050434);
((GeneralPath)shape).curveTo(30.811785, 34.855946, 25.756758, 34.855946, 21.654118, 34.050434);
((GeneralPath)shape).curveTo(17.55148, 33.244926, 15.024749, 31.756325, 15.026019, 30.145554);
((GeneralPath)shape).curveTo(15.024749, 28.534782, 17.55148, 27.046183, 21.654118, 26.240671);
((GeneralPath)shape).curveTo(25.756758, 25.435162, 30.811785, 25.435162, 34.914425, 26.240671);
((GeneralPath)shape).curveTo(39.017063, 27.046183, 41.543793, 28.534782, 41.542522, 30.145554);
((GeneralPath)shape).closePath();
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
paint = new LinearGradientPaint(new Point2D.Double(17.5, 6.5), new Point2D.Double(17.5, 40.7536506652832), new float[] {0.0f,0.12643678f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(238, 238, 236, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.0, 4.5);
((GeneralPath)shape).curveTo(8.164637, 4.5, 7.5, 8.073056, 7.5, 12.5);
((GeneralPath)shape).lineTo(7.5, 43.5);
((GeneralPath)shape).lineTo(42.5, 43.5);
((GeneralPath)shape).lineTo(42.5, 12.5);
((GeneralPath)shape).curveTo(42.5, 8.073057, 41.835365, 4.5, 41.0, 4.5);
((GeneralPath)shape).lineTo(39.3125, 4.5);
((GeneralPath)shape).lineTo(39.75, 5.5);
((GeneralPath)shape).lineTo(37.75, 5.5);
((GeneralPath)shape).lineTo(37.3125, 4.5);
((GeneralPath)shape).lineTo(12.65625, 4.5);
((GeneralPath)shape).lineTo(12.21875, 5.5);
((GeneralPath)shape).lineTo(10.21875, 5.5);
((GeneralPath)shape).lineTo(10.65625, 4.5);
((GeneralPath)shape).lineTo(9.0, 4.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.75, 7.5);
((GeneralPath)shape).lineTo(11.75, 7.5);
((GeneralPath)shape).lineTo(11.5625, 9.5);
((GeneralPath)shape).lineTo(9.5625, 9.5);
((GeneralPath)shape).lineTo(9.75, 7.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.1875, 7.5);
((GeneralPath)shape).lineTo(40.1875, 7.5);
((GeneralPath)shape).lineTo(40.40625, 9.5);
((GeneralPath)shape).lineTo(38.40625, 9.5);
((GeneralPath)shape).lineTo(38.1875, 7.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 11.5);
((GeneralPath)shape).lineTo(11.5, 11.5);
((GeneralPath)shape).lineTo(11.5, 13.5);
((GeneralPath)shape).lineTo(9.5, 13.5);
((GeneralPath)shape).lineTo(9.5, 11.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 11.5);
((GeneralPath)shape).lineTo(40.5, 11.5);
((GeneralPath)shape).lineTo(40.5, 13.5);
((GeneralPath)shape).lineTo(38.5, 13.5);
((GeneralPath)shape).lineTo(38.5, 11.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 15.5);
((GeneralPath)shape).lineTo(11.5, 15.5);
((GeneralPath)shape).lineTo(11.5, 17.5);
((GeneralPath)shape).lineTo(9.5, 17.5);
((GeneralPath)shape).lineTo(9.5, 15.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 15.5);
((GeneralPath)shape).lineTo(40.5, 15.5);
((GeneralPath)shape).lineTo(40.5, 17.5);
((GeneralPath)shape).lineTo(38.5, 17.5);
((GeneralPath)shape).lineTo(38.5, 15.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 19.5);
((GeneralPath)shape).lineTo(11.5, 19.5);
((GeneralPath)shape).lineTo(11.5, 21.5);
((GeneralPath)shape).lineTo(9.5, 21.5);
((GeneralPath)shape).lineTo(9.5, 19.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 19.5);
((GeneralPath)shape).lineTo(40.5, 19.5);
((GeneralPath)shape).lineTo(40.5, 21.5);
((GeneralPath)shape).lineTo(38.5, 21.5);
((GeneralPath)shape).lineTo(38.5, 19.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 23.5);
((GeneralPath)shape).lineTo(11.5, 23.5);
((GeneralPath)shape).lineTo(11.5, 25.5);
((GeneralPath)shape).lineTo(9.5, 25.5);
((GeneralPath)shape).lineTo(9.5, 23.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 23.5);
((GeneralPath)shape).lineTo(40.5, 23.5);
((GeneralPath)shape).lineTo(40.5, 25.5);
((GeneralPath)shape).lineTo(38.5, 25.5);
((GeneralPath)shape).lineTo(38.5, 23.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 27.5);
((GeneralPath)shape).lineTo(11.5, 27.5);
((GeneralPath)shape).lineTo(11.5, 29.5);
((GeneralPath)shape).lineTo(9.5, 29.5);
((GeneralPath)shape).lineTo(9.5, 27.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 27.5);
((GeneralPath)shape).lineTo(40.5, 27.5);
((GeneralPath)shape).lineTo(40.5, 29.5);
((GeneralPath)shape).lineTo(38.5, 29.5);
((GeneralPath)shape).lineTo(38.5, 27.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 31.5);
((GeneralPath)shape).lineTo(11.5, 31.5);
((GeneralPath)shape).lineTo(11.5, 33.5);
((GeneralPath)shape).lineTo(9.5, 33.5);
((GeneralPath)shape).lineTo(9.5, 31.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 31.5);
((GeneralPath)shape).lineTo(40.5, 31.5);
((GeneralPath)shape).lineTo(40.5, 33.5);
((GeneralPath)shape).lineTo(38.5, 33.5);
((GeneralPath)shape).lineTo(38.5, 31.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 35.5);
((GeneralPath)shape).lineTo(11.5, 35.5);
((GeneralPath)shape).lineTo(11.5, 37.5);
((GeneralPath)shape).lineTo(9.5, 37.5);
((GeneralPath)shape).lineTo(9.5, 35.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 35.5);
((GeneralPath)shape).lineTo(40.5, 35.5);
((GeneralPath)shape).lineTo(40.5, 37.5);
((GeneralPath)shape).lineTo(38.5, 37.5);
((GeneralPath)shape).lineTo(38.5, 35.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(136, 138, 133, 255);
stroke = new BasicStroke(1.0000005f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.0, 4.5);
((GeneralPath)shape).curveTo(8.164637, 4.5, 7.5, 8.073056, 7.5, 12.5);
((GeneralPath)shape).lineTo(7.5, 43.5);
((GeneralPath)shape).lineTo(42.5, 43.5);
((GeneralPath)shape).lineTo(42.5, 12.5);
((GeneralPath)shape).curveTo(42.5, 8.073057, 41.835365, 4.5, 41.0, 4.5);
((GeneralPath)shape).lineTo(39.3125, 4.5);
((GeneralPath)shape).lineTo(39.75, 5.5);
((GeneralPath)shape).lineTo(37.75, 5.5);
((GeneralPath)shape).lineTo(37.3125, 4.5);
((GeneralPath)shape).lineTo(12.65625, 4.5);
((GeneralPath)shape).lineTo(12.21875, 5.5);
((GeneralPath)shape).lineTo(10.21875, 5.5);
((GeneralPath)shape).lineTo(10.65625, 4.5);
((GeneralPath)shape).lineTo(9.0, 4.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.75, 7.5);
((GeneralPath)shape).lineTo(11.75, 7.5);
((GeneralPath)shape).lineTo(11.5625, 9.5);
((GeneralPath)shape).lineTo(9.5625, 9.5);
((GeneralPath)shape).lineTo(9.75, 7.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.1875, 7.5);
((GeneralPath)shape).lineTo(40.1875, 7.5);
((GeneralPath)shape).lineTo(40.40625, 9.5);
((GeneralPath)shape).lineTo(38.40625, 9.5);
((GeneralPath)shape).lineTo(38.1875, 7.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 11.5);
((GeneralPath)shape).lineTo(11.5, 11.5);
((GeneralPath)shape).lineTo(11.5, 13.5);
((GeneralPath)shape).lineTo(9.5, 13.5);
((GeneralPath)shape).lineTo(9.5, 11.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 11.5);
((GeneralPath)shape).lineTo(40.5, 11.5);
((GeneralPath)shape).lineTo(40.5, 13.5);
((GeneralPath)shape).lineTo(38.5, 13.5);
((GeneralPath)shape).lineTo(38.5, 11.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 15.5);
((GeneralPath)shape).lineTo(11.5, 15.5);
((GeneralPath)shape).lineTo(11.5, 17.5);
((GeneralPath)shape).lineTo(9.5, 17.5);
((GeneralPath)shape).lineTo(9.5, 15.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 15.5);
((GeneralPath)shape).lineTo(40.5, 15.5);
((GeneralPath)shape).lineTo(40.5, 17.5);
((GeneralPath)shape).lineTo(38.5, 17.5);
((GeneralPath)shape).lineTo(38.5, 15.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 19.5);
((GeneralPath)shape).lineTo(11.5, 19.5);
((GeneralPath)shape).lineTo(11.5, 21.5);
((GeneralPath)shape).lineTo(9.5, 21.5);
((GeneralPath)shape).lineTo(9.5, 19.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 19.5);
((GeneralPath)shape).lineTo(40.5, 19.5);
((GeneralPath)shape).lineTo(40.5, 21.5);
((GeneralPath)shape).lineTo(38.5, 21.5);
((GeneralPath)shape).lineTo(38.5, 19.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 23.5);
((GeneralPath)shape).lineTo(11.5, 23.5);
((GeneralPath)shape).lineTo(11.5, 25.5);
((GeneralPath)shape).lineTo(9.5, 25.5);
((GeneralPath)shape).lineTo(9.5, 23.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 23.5);
((GeneralPath)shape).lineTo(40.5, 23.5);
((GeneralPath)shape).lineTo(40.5, 25.5);
((GeneralPath)shape).lineTo(38.5, 25.5);
((GeneralPath)shape).lineTo(38.5, 23.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 27.5);
((GeneralPath)shape).lineTo(11.5, 27.5);
((GeneralPath)shape).lineTo(11.5, 29.5);
((GeneralPath)shape).lineTo(9.5, 29.5);
((GeneralPath)shape).lineTo(9.5, 27.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 27.5);
((GeneralPath)shape).lineTo(40.5, 27.5);
((GeneralPath)shape).lineTo(40.5, 29.5);
((GeneralPath)shape).lineTo(38.5, 29.5);
((GeneralPath)shape).lineTo(38.5, 27.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 31.5);
((GeneralPath)shape).lineTo(11.5, 31.5);
((GeneralPath)shape).lineTo(11.5, 33.5);
((GeneralPath)shape).lineTo(9.5, 33.5);
((GeneralPath)shape).lineTo(9.5, 31.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 31.5);
((GeneralPath)shape).lineTo(40.5, 31.5);
((GeneralPath)shape).lineTo(40.5, 33.5);
((GeneralPath)shape).lineTo(38.5, 33.5);
((GeneralPath)shape).lineTo(38.5, 31.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(9.5, 35.5);
((GeneralPath)shape).lineTo(11.5, 35.5);
((GeneralPath)shape).lineTo(11.5, 37.5);
((GeneralPath)shape).lineTo(9.5, 37.5);
((GeneralPath)shape).lineTo(9.5, 35.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(38.5, 35.5);
((GeneralPath)shape).lineTo(40.5, 35.5);
((GeneralPath)shape).lineTo(40.5, 37.5);
((GeneralPath)shape).lineTo(38.5, 37.5);
((GeneralPath)shape).lineTo(38.5, 35.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_2;
g.setTransform(defaultTransform__0_0_0_2);
g.setClip(clip__0_0_0_2);
float alpha__0_0_0_3 = origAlpha;
origAlpha = origAlpha * 0.1f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3 is ShapeNode
paint = new Color(136, 138, 133, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.0625, 5.0);
((GeneralPath)shape).curveTo(9.022063, 5.043827, 8.951063, 5.172764, 8.875, 5.34375);
((GeneralPath)shape).curveTo(8.798937, 5.514736, 8.73023, 5.7318797, 8.65625, 6.0);
((GeneralPath)shape).lineTo(10.21875, 6.0);
((GeneralPath)shape).curveTo(10.051578, 6.00173, 9.906679, 5.9193797, 9.8125, 5.78125);
((GeneralPath)shape).curveTo(9.718321, 5.6431203, 9.687321, 5.467487, 9.75, 5.3125);
((GeneralPath)shape).lineTo(9.875, 5.0);
((GeneralPath)shape).lineTo(9.0625, 5.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(13.0, 5.0);
((GeneralPath)shape).lineTo(12.6875, 5.6875);
((GeneralPath)shape).curveTo(12.610377, 5.8781476, 12.424395, 6.0021358, 12.21875, 6.0);
((GeneralPath)shape).lineTo(37.75, 6.0);
((GeneralPath)shape).curveTo(37.544353, 6.0021358, 37.35837, 5.8781476, 37.28125, 5.6875);
((GeneralPath)shape).lineTo(36.96875, 5.0);
((GeneralPath)shape).lineTo(13.0, 5.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(40.09375, 5.0);
((GeneralPath)shape).lineTo(40.21875, 5.3125);
((GeneralPath)shape).curveTo(40.28143, 5.467487, 40.250427, 5.6431203, 40.15625, 5.78125);
((GeneralPath)shape).curveTo(40.06207, 5.9193797, 39.91717, 6.00173, 39.75, 6.0);
((GeneralPath)shape).lineTo(41.34375, 6.0);
((GeneralPath)shape).curveTo(41.26977, 5.7318797, 41.20106, 5.514736, 41.125, 5.34375);
((GeneralPath)shape).curveTo(41.04894, 5.172764, 40.977936, 5.043827, 40.9375, 5.0);
((GeneralPath)shape).lineTo(40.09375, 5.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.25, 8.0);
((GeneralPath)shape).curveTo(8.170528, 8.601359, 8.140286, 9.285213, 8.09375, 10.0);
((GeneralPath)shape).lineTo(9.5625, 10.0);
((GeneralPath)shape).curveTo(9.4243, 10.000255, 9.282217, 9.944387, 9.1875, 9.84375);
((GeneralPath)shape).curveTo(9.092783, 9.743113, 9.053877, 9.606681, 9.0625, 9.46875);
((GeneralPath)shape).lineTo(9.1875, 8.0);
((GeneralPath)shape).lineTo(8.25, 8.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.21875, 8.0);
((GeneralPath)shape).lineTo(12.0625, 9.53125);
((GeneralPath)shape).curveTo(12.045952, 9.795107, 11.826875, 10.000492, 11.5625, 10.0);
((GeneralPath)shape).lineTo(38.40625, 10.0);
((GeneralPath)shape).curveTo(38.15279, 10.001965, 37.937946, 9.813978, 37.90625, 9.5625);
((GeneralPath)shape).lineTo(37.75, 8.0);
((GeneralPath)shape).lineTo(12.21875, 8.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(40.75, 8.0);
((GeneralPath)shape).lineTo(40.90625, 9.4375);
((GeneralPath)shape).curveTo(40.92426, 9.580553, 40.87704, 9.735988, 40.78125, 9.84375);
((GeneralPath)shape).curveTo(40.68546, 9.951513, 40.550426, 10.001115, 40.40625, 10.0);
((GeneralPath)shape).lineTo(41.90625, 10.0);
((GeneralPath)shape).curveTo(41.859715, 9.285213, 41.82947, 8.601359, 41.75, 8.0);
((GeneralPath)shape).lineTo(40.75, 8.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 12.0);
((GeneralPath)shape).curveTo(7.997886, 12.168756, 8.0, 12.328423, 8.0, 12.5);
((GeneralPath)shape).lineTo(8.0, 14.0);
((GeneralPath)shape).lineTo(9.5, 14.0);
((GeneralPath)shape).curveTo(9.223869, 13.999972, 9.000028, 13.776131, 9.0, 13.5);
((GeneralPath)shape).lineTo(9.0, 12.0);
((GeneralPath)shape).lineTo(8.0, 12.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 12.0);
((GeneralPath)shape).lineTo(12.0, 13.5);
((GeneralPath)shape).curveTo(11.999972, 13.776131, 11.776131, 13.999972, 11.5, 14.0);
((GeneralPath)shape).lineTo(38.5, 14.0);
((GeneralPath)shape).curveTo(38.22387, 13.999972, 38.000027, 13.776131, 38.0, 13.5);
((GeneralPath)shape).lineTo(38.0, 12.0);
((GeneralPath)shape).lineTo(12.0, 12.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 12.0);
((GeneralPath)shape).lineTo(41.0, 13.5);
((GeneralPath)shape).curveTo(40.999973, 13.776131, 40.77613, 13.999972, 40.5, 14.0);
((GeneralPath)shape).lineTo(42.0, 14.0);
((GeneralPath)shape).lineTo(42.0, 12.5);
((GeneralPath)shape).curveTo(42.0, 12.328423, 42.002113, 12.168756, 42.0, 12.0);
((GeneralPath)shape).lineTo(41.0, 12.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 16.0);
((GeneralPath)shape).lineTo(8.0, 18.0);
((GeneralPath)shape).lineTo(9.5, 18.0);
((GeneralPath)shape).curveTo(9.223869, 17.999971, 9.000028, 17.77613, 9.0, 17.5);
((GeneralPath)shape).lineTo(9.0, 16.0);
((GeneralPath)shape).lineTo(8.0, 16.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 16.0);
((GeneralPath)shape).lineTo(12.0, 17.5);
((GeneralPath)shape).curveTo(11.999972, 17.77613, 11.776131, 17.999971, 11.5, 18.0);
((GeneralPath)shape).lineTo(38.5, 18.0);
((GeneralPath)shape).curveTo(38.22387, 17.999971, 38.000027, 17.77613, 38.0, 17.5);
((GeneralPath)shape).lineTo(38.0, 16.0);
((GeneralPath)shape).lineTo(12.0, 16.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 16.0);
((GeneralPath)shape).lineTo(41.0, 17.5);
((GeneralPath)shape).curveTo(40.999973, 17.77613, 40.77613, 17.999971, 40.5, 18.0);
((GeneralPath)shape).lineTo(42.0, 18.0);
((GeneralPath)shape).lineTo(42.0, 16.0);
((GeneralPath)shape).lineTo(41.0, 16.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 20.0);
((GeneralPath)shape).lineTo(8.0, 22.0);
((GeneralPath)shape).lineTo(9.5, 22.0);
((GeneralPath)shape).curveTo(9.223869, 21.999971, 9.000028, 21.77613, 9.0, 21.5);
((GeneralPath)shape).lineTo(9.0, 20.0);
((GeneralPath)shape).lineTo(8.0, 20.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 20.0);
((GeneralPath)shape).lineTo(12.0, 21.5);
((GeneralPath)shape).curveTo(11.999972, 21.77613, 11.776131, 21.999971, 11.5, 22.0);
((GeneralPath)shape).lineTo(38.5, 22.0);
((GeneralPath)shape).curveTo(38.22387, 21.999971, 38.000027, 21.77613, 38.0, 21.5);
((GeneralPath)shape).lineTo(38.0, 20.0);
((GeneralPath)shape).lineTo(12.0, 20.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 20.0);
((GeneralPath)shape).lineTo(41.0, 21.5);
((GeneralPath)shape).curveTo(40.999973, 21.77613, 40.77613, 21.999971, 40.5, 22.0);
((GeneralPath)shape).lineTo(42.0, 22.0);
((GeneralPath)shape).lineTo(42.0, 20.0);
((GeneralPath)shape).lineTo(41.0, 20.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 24.0);
((GeneralPath)shape).lineTo(8.0, 26.0);
((GeneralPath)shape).lineTo(9.5, 26.0);
((GeneralPath)shape).curveTo(9.223869, 25.999971, 9.000028, 25.77613, 9.0, 25.5);
((GeneralPath)shape).lineTo(9.0, 24.0);
((GeneralPath)shape).lineTo(8.0, 24.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 24.0);
((GeneralPath)shape).lineTo(12.0, 25.5);
((GeneralPath)shape).curveTo(11.999972, 25.77613, 11.776131, 25.999971, 11.5, 26.0);
((GeneralPath)shape).lineTo(38.5, 26.0);
((GeneralPath)shape).curveTo(38.22387, 25.999971, 38.000027, 25.77613, 38.0, 25.5);
((GeneralPath)shape).lineTo(38.0, 24.0);
((GeneralPath)shape).lineTo(12.0, 24.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 24.0);
((GeneralPath)shape).lineTo(41.0, 25.5);
((GeneralPath)shape).curveTo(40.999973, 25.77613, 40.77613, 25.999971, 40.5, 26.0);
((GeneralPath)shape).lineTo(42.0, 26.0);
((GeneralPath)shape).lineTo(42.0, 24.0);
((GeneralPath)shape).lineTo(41.0, 24.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 28.0);
((GeneralPath)shape).lineTo(8.0, 30.0);
((GeneralPath)shape).lineTo(9.5, 30.0);
((GeneralPath)shape).curveTo(9.223869, 29.999971, 9.000028, 29.77613, 9.0, 29.5);
((GeneralPath)shape).lineTo(9.0, 28.0);
((GeneralPath)shape).lineTo(8.0, 28.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 28.0);
((GeneralPath)shape).lineTo(12.0, 29.5);
((GeneralPath)shape).curveTo(11.999972, 29.77613, 11.776131, 29.999971, 11.5, 30.0);
((GeneralPath)shape).lineTo(38.5, 30.0);
((GeneralPath)shape).curveTo(38.22387, 29.999971, 38.000027, 29.77613, 38.0, 29.5);
((GeneralPath)shape).lineTo(38.0, 28.0);
((GeneralPath)shape).lineTo(12.0, 28.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 28.0);
((GeneralPath)shape).lineTo(41.0, 29.5);
((GeneralPath)shape).curveTo(40.999973, 29.77613, 40.77613, 29.999971, 40.5, 30.0);
((GeneralPath)shape).lineTo(42.0, 30.0);
((GeneralPath)shape).lineTo(42.0, 28.0);
((GeneralPath)shape).lineTo(41.0, 28.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 32.0);
((GeneralPath)shape).lineTo(8.0, 34.0);
((GeneralPath)shape).lineTo(9.5, 34.0);
((GeneralPath)shape).curveTo(9.223869, 33.999973, 9.000028, 33.77613, 9.0, 33.5);
((GeneralPath)shape).lineTo(9.0, 32.0);
((GeneralPath)shape).lineTo(8.0, 32.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 32.0);
((GeneralPath)shape).lineTo(12.0, 33.5);
((GeneralPath)shape).curveTo(11.999972, 33.77613, 11.776131, 33.999973, 11.5, 34.0);
((GeneralPath)shape).lineTo(38.5, 34.0);
((GeneralPath)shape).curveTo(38.22387, 33.999973, 38.000027, 33.77613, 38.0, 33.5);
((GeneralPath)shape).lineTo(38.0, 32.0);
((GeneralPath)shape).lineTo(12.0, 32.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 32.0);
((GeneralPath)shape).lineTo(41.0, 33.5);
((GeneralPath)shape).curveTo(40.999973, 33.77613, 40.77613, 33.999973, 40.5, 34.0);
((GeneralPath)shape).lineTo(42.0, 34.0);
((GeneralPath)shape).lineTo(42.0, 32.0);
((GeneralPath)shape).lineTo(41.0, 32.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(8.0, 36.0);
((GeneralPath)shape).lineTo(8.0, 38.0);
((GeneralPath)shape).lineTo(9.5, 38.0);
((GeneralPath)shape).curveTo(9.223869, 37.999973, 9.000028, 37.77613, 9.0, 37.5);
((GeneralPath)shape).lineTo(9.0, 36.0);
((GeneralPath)shape).lineTo(8.0, 36.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(12.0, 36.0);
((GeneralPath)shape).lineTo(12.0, 37.5);
((GeneralPath)shape).curveTo(11.999972, 37.77613, 11.776131, 37.999973, 11.5, 38.0);
((GeneralPath)shape).lineTo(38.5, 38.0);
((GeneralPath)shape).curveTo(38.22387, 37.999973, 38.000027, 37.77613, 38.0, 37.5);
((GeneralPath)shape).lineTo(38.0, 36.0);
((GeneralPath)shape).lineTo(12.0, 36.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(41.0, 36.0);
((GeneralPath)shape).lineTo(41.0, 37.5);
((GeneralPath)shape).curveTo(40.999973, 37.77613, 40.77613, 37.999973, 40.5, 38.0);
((GeneralPath)shape).lineTo(42.0, 38.0);
((GeneralPath)shape).lineTo(42.0, 36.0);
((GeneralPath)shape).lineTo(41.0, 36.0);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(20.625, 10.24703598022461), new Point2D.Double(21.0, 62.17327117919922), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(13.3125, 5.5);
((GeneralPath)shape).lineTo(13.125, 5.90625);
((GeneralPath)shape).curveTo(12.965567, 6.2648244, 12.61116, 6.4970217, 12.21875, 6.5);
((GeneralPath)shape).lineTo(10.21875, 6.5);
((GeneralPath)shape).curveTo(9.771428, 6.5040383, 9.375829, 6.2105293, 9.25, 5.78125);
((GeneralPath)shape).curveTo(9.138852, 6.0820355, 9.012011, 6.4658103, 8.90625, 7.03125);
((GeneralPath)shape).curveTo(8.652692, 8.38688, 8.5, 10.336574, 8.5, 12.5);
((GeneralPath)shape).lineTo(8.5, 42.5);
((GeneralPath)shape).lineTo(41.5, 42.5);
((GeneralPath)shape).lineTo(41.5, 12.5);
((GeneralPath)shape).curveTo(41.5, 10.336573, 41.34731, 8.38688, 41.09375, 7.03125);
((GeneralPath)shape).curveTo(40.979942, 6.422779, 40.835693, 5.984036, 40.71875, 5.6875);
((GeneralPath)shape).curveTo(40.629707, 6.153994, 40.224873, 6.4935346, 39.75, 6.5);
((GeneralPath)shape).lineTo(37.75, 6.5);
((GeneralPath)shape).curveTo(37.35759, 6.4970217, 37.00318, 6.2648244, 36.84375, 5.90625);
((GeneralPath)shape).lineTo(36.65625, 5.5);
((GeneralPath)shape).lineTo(13.3125, 5.5);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_5 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(12.75, 42.236083984375), new Point2D.Double(12.75, 39.04581069946289), new float[] {0.0f,1.0f}, new Color[] {new Color(186, 189, 182, 255),new Color(238, 238, 236, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(5.5, 37.5);
((GeneralPath)shape).curveTo(5.5, 40.824013, 6.0, 43.5, 7.5, 43.5);
((GeneralPath)shape).lineTo(42.5, 43.5);
((GeneralPath)shape).curveTo(44.0, 43.5, 44.5, 40.824013, 44.5, 37.5);
((GeneralPath)shape).lineTo(5.5, 37.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(7.5, 39.5);
((GeneralPath)shape).lineTo(9.5, 39.5);
((GeneralPath)shape).curveTo(9.472526, 40.53082, 9.585048, 41.10018, 9.75, 41.5);
((GeneralPath)shape).lineTo(7.75, 41.5);
((GeneralPath)shape).curveTo(7.5686812, 41.12729, 7.5356345, 40.51289, 7.5, 39.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(40.5, 39.5);
((GeneralPath)shape).lineTo(42.5, 39.5);
((GeneralPath)shape).curveTo(42.464363, 40.51289, 42.431316, 41.12729, 42.25, 41.5);
((GeneralPath)shape).lineTo(40.25, 41.5);
((GeneralPath)shape).curveTo(40.41495, 41.10018, 40.527473, 40.53082, 40.5, 39.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(136, 138, 133, 255);
stroke = new BasicStroke(1.0000008f,1,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(5.5, 37.5);
((GeneralPath)shape).curveTo(5.5, 40.824013, 6.0, 43.5, 7.5, 43.5);
((GeneralPath)shape).lineTo(42.5, 43.5);
((GeneralPath)shape).curveTo(44.0, 43.5, 44.5, 40.824013, 44.5, 37.5);
((GeneralPath)shape).lineTo(5.5, 37.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(7.5, 39.5);
((GeneralPath)shape).lineTo(9.5, 39.5);
((GeneralPath)shape).curveTo(9.472526, 40.53082, 9.585048, 41.10018, 9.75, 41.5);
((GeneralPath)shape).lineTo(7.75, 41.5);
((GeneralPath)shape).curveTo(7.5686812, 41.12729, 7.5356345, 40.51289, 7.5, 39.5);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(40.5, 39.5);
((GeneralPath)shape).lineTo(42.5, 39.5);
((GeneralPath)shape).curveTo(42.464363, 40.51289, 42.431316, 41.12729, 42.25, 41.5);
((GeneralPath)shape).lineTo(40.25, 41.5);
((GeneralPath)shape).curveTo(40.41495, 41.10018, 40.527473, 40.53082, 40.5, 39.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_5;
g.setTransform(defaultTransform__0_0_0_5);
g.setClip(clip__0_0_0_5);
float alpha__0_0_0_6 = origAlpha;
origAlpha = origAlpha * 0.1f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_6 is ShapeNode
paint = new Color(136, 138, 133, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(6.125, 40.0);
((GeneralPath)shape).curveTo(6.183456, 40.609367, 6.2778854, 41.148273, 6.40625, 41.59375);
((GeneralPath)shape).curveTo(6.450931, 41.748814, 6.5091805, 41.865986, 6.5625, 42.0);
((GeneralPath)shape).lineTo(7.75, 42.0);
((GeneralPath)shape).curveTo(7.563049, 41.995434, 7.3942704, 41.886932, 7.3125, 41.71875);
((GeneralPath)shape).curveTo(7.09567, 41.273045, 7.0607805, 40.72197, 7.03125, 40.0);
((GeneralPath)shape).lineTo(6.125, 40.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(10.0, 40.0);
((GeneralPath)shape).curveTo(10.018193, 40.6601, 10.112137, 41.054085, 10.21875, 41.3125);
((GeneralPath)shape).curveTo(10.281429, 41.467487, 10.250429, 41.64312, 10.15625, 41.78125);
((GeneralPath)shape).curveTo(10.062071, 41.91938, 9.917172, 42.00173, 9.75, 42.0);
((GeneralPath)shape).lineTo(40.25, 42.0);
((GeneralPath)shape).curveTo(40.08283, 42.00173, 39.937927, 41.91938, 39.84375, 41.78125);
((GeneralPath)shape).curveTo(39.749573, 41.64312, 39.71857, 41.467487, 39.78125, 41.3125);
((GeneralPath)shape).curveTo(39.887863, 41.054085, 39.981808, 40.6601, 40.0, 40.0);
((GeneralPath)shape).lineTo(10.0, 40.0);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(42.96875, 40.0);
((GeneralPath)shape).curveTo(42.93922, 40.72197, 42.904327, 41.273045, 42.6875, 41.71875);
((GeneralPath)shape).curveTo(42.60573, 41.886932, 42.43695, 41.995434, 42.25, 42.0);
((GeneralPath)shape).lineTo(43.4375, 42.0);
((GeneralPath)shape).curveTo(43.490818, 41.865986, 43.54907, 41.74881, 43.59375, 41.59375);
((GeneralPath)shape).curveTo(43.722115, 41.148273, 43.816544, 40.609367, 43.875, 40.0);
((GeneralPath)shape).lineTo(42.96875, 40.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_6;
g.setTransform(defaultTransform__0_0_0_6);
g.setClip(clip__0_0_0_6);
float alpha__0_0_0_7 = origAlpha;
origAlpha = origAlpha * 0.8f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.005094051361084f, 0.0f, 0.0f, 1.0f, -0.1273369938135147f, 0.0f));
// _0_0_0_7 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(13.682637214660645, 39.49958419799805), new Point2D.Double(13.682637214660645, 43.89316177368164), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(0.997463f,1,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(6.59375, 38.5);
((GeneralPath)shape).curveTo(6.644105, 39.625164, 6.7083464, 40.781944, 6.90625, 41.46875);
((GeneralPath)shape).curveTo(7.038494, 41.92769, 7.194645, 42.236572, 7.3125, 42.375);
((GeneralPath)shape).curveTo(7.430355, 42.513428, 7.420658, 42.5, 7.5, 42.5);
((GeneralPath)shape).lineTo(42.5, 42.5);
((GeneralPath)shape).curveTo(42.57934, 42.5, 42.569645, 42.513428, 42.6875, 42.375);
((GeneralPath)shape).curveTo(42.805355, 42.236572, 42.961506, 41.92769, 43.09375, 41.46875);
((GeneralPath)shape).curveTo(43.291653, 40.781944, 43.355896, 39.625164, 43.40625, 38.5);
((GeneralPath)shape).lineTo(6.59375, 38.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_7;
g.setTransform(defaultTransform__0_0_0_7);
g.setClip(clip__0_0_0_7);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -9.21660041809082f, 47.4345817565918f));
// _0_0_1 is CompositeGraphicsNode
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(0.8500000238418579f, 0.0f, 0.0f, 0.8500000238418579f, 10.536998748779297f, 8.04999828338623f));
// _0_0_2 is CompositeGraphicsNode
float alpha__0_0_2_0 = origAlpha;
origAlpha = origAlpha * 0.25f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_0 = g.getClip();
AffineTransform defaultTransform__0_0_2_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 0.6666668057441711f, -13.999999046325684f, -5.000008583068848f));
// _0_0_2_0 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(38.0, 69.0), 20.0f, new Point2D.Double(28.603322982788086, 69.0), new float[] {0.0f,1.0f}, new Color[] {new Color(0, 0, 0, 255),new Color(0, 0, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 0.44999998807907104f, 0.0f, 37.95000076293945f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(58.0, 69.0);
((GeneralPath)shape).curveTo(58.0, 73.970566, 49.045696, 78.0, 38.0, 78.0);
((GeneralPath)shape).curveTo(26.954304, 78.0, 18.0, 73.970566, 18.0, 69.0);
((GeneralPath)shape).curveTo(18.0, 64.029434, 26.954304, 60.0, 38.0, 60.0);
((GeneralPath)shape).curveTo(49.045696, 60.0, 58.0, 64.029434, 58.0, 69.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
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
paint = new LinearGradientPaint(new Point2D.Double(28.0, 16.0), new Point2D.Double(26.0, 8.0), new float[] {0.0f,1.0f}, new Color[] {new Color(193, 125, 17, 255),new Color(233, 185, 110, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.59375, 2.46875);
((GeneralPath)shape).curveTo(26.390533, 2.5744002, 25.196949, 18.716276, 22.84375, 21.625);
((GeneralPath)shape).lineTo(26.84375, 23.0625);
((GeneralPath)shape).curveTo(29.475622, 18.689953, 42.599747, 4.1545033, 35.40625, 2.5);
((GeneralPath)shape).curveTo(35.12676, 2.4690309, 34.85837, 2.4653418, 34.59375, 2.46875);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(33.5625, 4.53125);
((GeneralPath)shape).curveTo(33.75606, 4.512511, 33.930485, 4.53697, 34.09375, 4.625);
((GeneralPath)shape).curveTo(34.746807, 4.977123, 34.817406, 6.119877, 34.25, 7.15625);
((GeneralPath)shape).curveTo(33.682594, 8.192623, 32.684303, 8.758372, 32.03125, 8.40625);
((GeneralPath)shape).curveTo(31.378197, 8.054128, 31.307594, 6.911373, 31.875, 5.875);
((GeneralPath)shape).curveTo(32.300552, 5.09772, 32.98181, 4.587466, 33.5625, 4.53125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(30.3248291015625, 9.240796089172363), new Point2D.Double(34.0, 18.0), new float[] {0.0f,1.0f}, new Color[] {new Color(143, 89, 2, 255),new Color(115, 82, 30, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.0f, 0.0f));
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.59375, 2.46875);
((GeneralPath)shape).curveTo(26.390533, 2.5744002, 25.196949, 18.716276, 22.84375, 21.625);
((GeneralPath)shape).lineTo(26.84375, 23.0625);
((GeneralPath)shape).curveTo(29.475622, 18.689953, 42.599747, 4.1545033, 35.40625, 2.5);
((GeneralPath)shape).curveTo(35.12676, 2.4690309, 34.85837, 2.4653418, 34.59375, 2.46875);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(33.5625, 4.53125);
((GeneralPath)shape).curveTo(33.75606, 4.512511, 33.930485, 4.53697, 34.09375, 4.625);
((GeneralPath)shape).curveTo(34.746807, 4.977123, 34.817406, 6.119877, 34.25, 7.15625);
((GeneralPath)shape).curveTo(33.682594, 8.192623, 32.684303, 8.758372, 32.03125, 8.40625);
((GeneralPath)shape).curveTo(31.378197, 8.054128, 31.307594, 6.911373, 31.875, 5.875);
((GeneralPath)shape).curveTo(32.300552, 5.09772, 32.98181, 4.587466, 33.5625, 4.53125);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_1;
g.setTransform(defaultTransform__0_0_2_1);
g.setClip(clip__0_0_2_1);
float alpha__0_0_2_2 = origAlpha;
origAlpha = origAlpha * 0.26666668f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_2 = g.getClip();
AffineTransform defaultTransform__0_0_2_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.0f, 0.0f));
// _0_0_2_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(28.0, 8.0), new Point2D.Double(33.44710922241211, 16.685888290405273), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 34)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(36.59375, 3.46875);
((GeneralPath)shape).curveTo(34.87213, 3.490923, 33.585823, 4.324624, 32.40625, 5.75);
((GeneralPath)shape).curveTo(31.226675, 7.175376, 30.257915, 9.191635, 29.46875, 11.34375);
((GeneralPath)shape).curveTo(28.679585, 13.495865, 28.04471, 15.77802, 27.46875, 17.71875);
((GeneralPath)shape).curveTo(27.06886, 19.066206, 26.698893, 20.125198, 26.25, 21.0625);
((GeneralPath)shape).lineTo(28.4375, 21.84375);
((GeneralPath)shape).curveTo(30.056093, 19.348125, 33.4763, 15.252572, 35.96875, 11.21875);
((GeneralPath)shape).curveTo(37.29459, 9.072993, 38.25245, 7.040709, 38.46875, 5.65625);
((GeneralPath)shape).curveTo(38.5769, 4.9640207, 38.513817, 4.4833207, 38.34375, 4.1875);
((GeneralPath)shape).curveTo(38.179058, 3.901031, 37.880276, 3.6629145, 37.21875, 3.5);
((GeneralPath)shape).curveTo(37.019257, 3.4812162, 36.817917, 3.465863, 36.59375, 3.46875);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
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
paint = new LinearGradientPaint(new Point2D.Double(20.93370819091797, 25.060659408569336), new Point2D.Double(30.208114624023438, 30.74267578125), new float[] {0.0f,1.0f}, new Color[] {new Color(253, 239, 114, 255),new Color(226, 203, 11, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(20.5, 16.5);
((GeneralPath)shape).curveTo(21.209505, 18.503006, 19.958612, 20.237831, 19.5, 21.5);
((GeneralPath)shape).curveTo(14.308433, 23.045673, 10.713199, 31.203726, 5.3674173, 35.453587);
((GeneralPath)shape).curveTo(6.0352054, 36.15098, 6.819644, 36.897762, 7.5, 37.5);
((GeneralPath)shape).lineTo(11.5625, 33.96875);
((GeneralPath)shape).lineTo(8.494944, 38.4934);
((GeneralPath)shape).curveTo(10.704181, 40.28438, 13.0, 41.5, 14.5, 42.0);
((GeneralPath)shape).lineTo(17.25, 38.34375);
((GeneralPath)shape).lineTo(15.5, 42.5);
((GeneralPath)shape).curveTo(16.951994, 43.088882, 20.485287, 43.982025, 22.5, 44.0);
((GeneralPath)shape).lineTo(24.503891, 40.597504);
((GeneralPath)shape).lineTo(23.990723, 44.0625);
((GeneralPath)shape).curveTo(24.820286, 44.22086, 26.428888, 44.436714, 27.500002, 44.46875);
((GeneralPath)shape).curveTo(30.862188, 38.96875, 31.500002, 30.0, 29.500002, 26.0);
((GeneralPath)shape).curveTo(29.000002, 24.0, 31.000002, 21.5, 32.5, 20.5);
((GeneralPath)shape).curveTo(30.0, 18.5, 24.29441, 16.196274, 20.5, 16.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new LinearGradientPaint(new Point2D.Double(17.03207778930664, 27.446826934814453), new Point2D.Double(29.494455337524414, 37.8458137512207), new float[] {0.0f,1.0f}, new Color[] {new Color(215, 194, 15, 255),new Color(182, 151, 13, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.0f, 0.0f));
stroke = new BasicStroke(0.99999994f,0,0,20.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(20.5, 16.5);
((GeneralPath)shape).curveTo(21.209505, 18.503006, 19.958612, 20.237831, 19.5, 21.5);
((GeneralPath)shape).curveTo(14.308433, 23.045673, 10.713199, 31.203726, 5.3674173, 35.453587);
((GeneralPath)shape).curveTo(6.0352054, 36.15098, 6.819644, 36.897762, 7.5, 37.5);
((GeneralPath)shape).lineTo(11.5625, 33.96875);
((GeneralPath)shape).lineTo(8.494944, 38.4934);
((GeneralPath)shape).curveTo(10.704181, 40.28438, 13.0, 41.5, 14.5, 42.0);
((GeneralPath)shape).lineTo(17.25, 38.34375);
((GeneralPath)shape).lineTo(15.5, 42.5);
((GeneralPath)shape).curveTo(16.951994, 43.088882, 20.485287, 43.982025, 22.5, 44.0);
((GeneralPath)shape).lineTo(24.503891, 40.597504);
((GeneralPath)shape).lineTo(23.990723, 44.0625);
((GeneralPath)shape).curveTo(24.820286, 44.22086, 26.428888, 44.436714, 27.500002, 44.46875);
((GeneralPath)shape).curveTo(30.862188, 38.96875, 31.500002, 30.0, 29.500002, 26.0);
((GeneralPath)shape).curveTo(29.000002, 24.0, 31.000002, 21.5, 32.5, 20.5);
((GeneralPath)shape).curveTo(30.0, 18.5, 24.29441, 16.196274, 20.5, 16.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_3;
g.setTransform(defaultTransform__0_0_2_3);
g.setClip(clip__0_0_2_3);
float alpha__0_0_2_4 = origAlpha;
origAlpha = origAlpha * 0.26666668f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_4 = g.getClip();
AffineTransform defaultTransform__0_0_2_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_4 is ShapeNode
paint = new Color(196, 160, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(9.0, 38.5);
((GeneralPath)shape).curveTo(13.816495, 33.489105, 13.465023, 31.296074, 19.116117, 26.972273);
((GeneralPath)shape).curveTo(16.133675, 31.800703, 15.650278, 34.31233, 12.0, 40.5);
((GeneralPath)shape).lineTo(9.0, 38.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_4;
g.setTransform(defaultTransform__0_0_2_4);
g.setClip(clip__0_0_2_4);
float alpha__0_0_2_5 = origAlpha;
origAlpha = origAlpha * 0.41568628f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_5 = g.getClip();
AffineTransform defaultTransform__0_0_2_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_5 is ShapeNode
paint = new Color(196, 160, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.149809, 42.202454);
((GeneralPath)shape).lineTo(20.495836, 32.362305);
((GeneralPath)shape).curveTo(22.160349, 29.378578, 23.355509, 26.392254, 25.024809, 24.014952);
((GeneralPath)shape).curveTo(23.422855, 29.43299, 20.134119, 36.136745, 17.493559, 42.639954);
((GeneralPath)shape).lineTo(16.149809, 42.202454);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_5;
g.setTransform(defaultTransform__0_0_2_5);
g.setClip(clip__0_0_2_5);
float alpha__0_0_2_6 = origAlpha;
origAlpha = origAlpha * 0.47843137f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_6 = g.getClip();
AffineTransform defaultTransform__0_0_2_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_6 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(22.319766998291016, 41.95598602294922), new Point2D.Double(18.9857120513916, 37.02925491333008), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 177)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.0f, 0.0f));
stroke = new BasicStroke(0.99999994f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(21.625, 17.5);
((GeneralPath)shape).curveTo(21.72174, 19.415146, 20.702057, 21.029644, 20.40625, 21.84375);
((GeneralPath)shape).curveTo(20.302147, 22.12846, 20.070919, 22.348124, 19.78125, 22.4375);
((GeneralPath)shape).curveTo(14.327852, 24.672144, 12.151447, 31.011538, 6.7866116, 35.523666);
((GeneralPath)shape).curveTo(6.988254, 35.723522, 7.278642, 35.94097, 7.481141, 36.131897);
((GeneralPath)shape).lineTo(16.5, 28.5);
((GeneralPath)shape).lineTo(9.923385, 38.310314);
((GeneralPath)shape).curveTo(11.193418, 39.33793, 12.645586, 40.19486, 14.150041, 40.79948);
((GeneralPath)shape).lineTo(21.144394, 31.5);
((GeneralPath)shape).lineTo(16.869501, 41.911613);
((GeneralPath)shape).curveTo(18.46507, 42.437267, 19.967804, 42.738907, 21.81451, 43.0);
((GeneralPath)shape).lineTo(26.43324, 35.3125);
((GeneralPath)shape).lineTo(25.0625, 43.21932);
((GeneralPath)shape).lineTo(26.9375, 43.445312);
((GeneralPath)shape).curveTo(28.370712, 40.909817, 29.069881, 37.77878, 29.46875, 34.65625);
((GeneralPath)shape).curveTo(29.892694, 31.337404, 29.46375, 28.115072, 28.625, 26.4375);
((GeneralPath)shape).curveTo(28.59784, 26.37729, 28.5769, 26.314465, 28.5625, 26.25);
((GeneralPath)shape).curveTo(28.215643, 24.86257, 28.731642, 23.504374, 29.4375, 22.375);
((GeneralPath)shape).curveTo(29.864393, 21.69197, 30.367872, 21.08422, 30.902458, 20.573223);
((GeneralPath)shape).curveTo(29.730976, 19.790531, 28.315762, 19.113157, 26.53125, 18.46875);
((GeneralPath)shape).curveTo(24.769173, 17.832443, 23.033253, 17.518724, 21.625, 17.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_6;
g.setTransform(defaultTransform__0_0_2_6);
g.setClip(clip__0_0_2_6);
float alpha__0_0_2_7 = origAlpha;
origAlpha = origAlpha * 0.24705882f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_7 = g.getClip();
AffineTransform defaultTransform__0_0_2_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_7 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.354808807373047, 36.218421936035156), new Point2D.Double(23.489431381225586, 34.728424072265625), new float[] {0.0f,1.0f}, new Color[] {new Color(196, 160, 0, 255),new Color(196, 160, 0, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.0f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.96875, 43.0);
((GeneralPath)shape).curveTo(21.146317, 37.24813, 25.364666, 32.931057, 26.985664, 27.064587);
((GeneralPath)shape).curveTo(27.037205, 30.72666, 27.235384, 37.26831, 25.093752, 43.78125);
((GeneralPath)shape).curveTo(24.773987, 43.78305, 24.919825, 43.67044, 24.623873, 43.662697);
((GeneralPath)shape).lineTo(25.424665, 37.93818);
((GeneralPath)shape).lineTo(22.143179, 43.49256);
((GeneralPath)shape).curveTo(19.952368, 43.33624, 20.848564, 43.52516, 18.96875, 43.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_7;
g.setTransform(defaultTransform__0_0_2_7);
g.setClip(clip__0_0_2_7);
float alpha__0_0_2_8 = origAlpha;
origAlpha = origAlpha * 0.48235294f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_8 = g.getClip();
AffineTransform defaultTransform__0_0_2_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_8 is ShapeNode
paint = new Color(196, 160, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(21.003067, 22.610447);
((GeneralPath)shape).curveTo(19.751072, 23.226826, 18.940859, 24.137726, 18.01996, 24.974834);
((GeneralPath)shape).curveTo(19.246447, 24.266191, 20.398947, 23.52584, 22.019533, 22.986097);
((GeneralPath)shape).lineTo(21.003067, 22.610447);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_8;
g.setTransform(defaultTransform__0_0_2_8);
g.setClip(clip__0_0_2_8);
float alpha__0_0_2_9 = origAlpha;
origAlpha = origAlpha * 0.48235294f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_9 = g.getClip();
AffineTransform defaultTransform__0_0_2_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_9 is ShapeNode
paint = new Color(196, 160, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.008698, 23.061049);
((GeneralPath)shape).lineTo(21.992233, 25.049786);
((GeneralPath)shape).lineTo(24.972946, 23.461536);
((GeneralPath)shape).lineTo(23.008698, 23.061049);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_9;
g.setTransform(defaultTransform__0_0_2_9);
g.setClip(clip__0_0_2_9);
float alpha__0_0_2_10 = origAlpha;
origAlpha = origAlpha * 0.48235294f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_10 = g.getClip();
AffineTransform defaultTransform__0_0_2_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_10 is ShapeNode
paint = new Color(196, 160, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.939804, 17.9614);
((GeneralPath)shape).lineTo(22.044611, 19.66842);
((GeneralPath)shape).lineTo(23.610338, 20.170506);
((GeneralPath)shape).lineTo(22.939804, 17.9614);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_10;
g.setTransform(defaultTransform__0_0_2_10);
g.setClip(clip__0_0_2_10);
float alpha__0_0_2_11 = origAlpha;
origAlpha = origAlpha * 0.48235294f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_11 = g.getClip();
AffineTransform defaultTransform__0_0_2_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_11 is ShapeNode
paint = new Color(196, 160, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(29.923254, 19.88537);
((GeneralPath)shape).lineTo(27.463005, 21.720818);
((GeneralPath)shape).lineTo(29.028732, 22.222902);
((GeneralPath)shape).lineTo(29.923254, 19.885372);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_11;
g.setTransform(defaultTransform__0_0_2_11);
g.setClip(clip__0_0_2_11);
float alpha__0_0_2_12 = origAlpha;
origAlpha = origAlpha * 0.48235294f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_12 = g.getClip();
AffineTransform defaultTransform__0_0_2_12 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_12 is ShapeNode
paint = new Color(196, 160, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.012924, 17.938566);
((GeneralPath)shape).lineTo(24.499014, 20.46318);
((GeneralPath)shape).lineTo(26.064741, 20.965265);
((GeneralPath)shape).lineTo(26.01292, 17.938568);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_12;
g.setTransform(defaultTransform__0_0_2_12);
g.setClip(clip__0_0_2_12);
float alpha__0_0_2_13 = origAlpha;
origAlpha = origAlpha * 0.2f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_13 = g.getClip();
AffineTransform defaultTransform__0_0_2_13 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_13 is ShapeNode
paint = new Color(0, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.967726, 22.024698);
((GeneralPath)shape).curveTo(18.400946, 19.059214, 28.30056, 24.1776, 30.004547, 25.019068);
((GeneralPath)shape).curveTo(29.998047, 26.098135, 30.004547, 27.019068, 29.027729, 27.019068);
((GeneralPath)shape).curveTo(26.460192, 25.626087, 22.492474, 23.413925, 18.967726, 22.024698);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2_13;
g.setTransform(defaultTransform__0_0_2_13);
g.setClip(clip__0_0_2_13);
float alpha__0_0_2_14 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_14 = g.getClip();
AffineTransform defaultTransform__0_0_2_14 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_14 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(27.651777267456055, 23.145936965942383), new Point2D.Double(21.59099006652832, 20.61871910095215), new float[] {0.0f,1.0f}, new Color[] {new Color(173, 127, 168, 255),new Color(218, 198, 216, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9768192768096924f, 0.0f, 0.0f, 1.0f, -1.3746633529663086f, 0.0f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.650133, 21.5);
((GeneralPath)shape).curveTo(18.161724, 20.5, 18.650133, 19.5, 19.626953, 19.5);
((GeneralPath)shape).curveTo(23.618393, 20.475416, 26.951828, 21.706232, 30.371965, 23.5);
((GeneralPath)shape).curveTo(30.860374, 24.5, 30.371965, 25.5, 29.395147, 25.5);
((GeneralPath)shape).curveTo(25.861204, 23.63558, 22.528437, 22.425186, 18.650135, 21.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(92, 53, 102, 255);
stroke = new BasicStroke(1.0f,0,1,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.650133, 21.5);
((GeneralPath)shape).curveTo(18.161724, 20.5, 18.650133, 19.5, 19.626953, 19.5);
((GeneralPath)shape).curveTo(23.618393, 20.475416, 26.951828, 21.706232, 30.371965, 23.5);
((GeneralPath)shape).curveTo(30.860374, 24.5, 30.371965, 25.5, 29.395147, 25.5);
((GeneralPath)shape).curveTo(25.861204, 23.63558, 22.528437, 22.425186, 18.650135, 21.5);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_14;
g.setTransform(defaultTransform__0_0_2_14);
g.setClip(clip__0_0_2_14);
origAlpha = alpha__0_0_2;
g.setTransform(defaultTransform__0_0_2);
g.setClip(clip__0_0_2);
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
        return 4;
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
	public ClearLogIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public ClearLogIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public ClearLogIcon(int width, int height) {
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

